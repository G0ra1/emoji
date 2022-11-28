package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.biz.asset.dto.ScrapApplyDetailDto;
import com.netwisd.biz.asset.dto.ScrapApplyDto;
import com.netwisd.biz.asset.entity.ScrapApply;
import com.netwisd.biz.asset.entity.ScrapApplyDetail;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.ScrapApplyMapper;
import com.netwisd.biz.asset.service.AssetsDetailService;
import com.netwisd.biz.asset.service.AssetsService;
import com.netwisd.biz.asset.service.ScrapApplyDetailService;
import com.netwisd.biz.asset.service.ScrapApplyService;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.common.db.util.EntityListConvert;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 报废申请 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 14:54:19
 */
@Service
@Slf4j
public class ScrapApplyServiceImpl extends BatchServiceImpl<ScrapApplyMapper, ScrapApply> implements ScrapApplyService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ScrapApplyMapper scrapApplyMapper;

    @Autowired
    private ScrapApplyDetailService scrapApplyDetailService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private AssetsService assetsService;


    /**
    * 单表简单查询操作
    * @param scrapApplyDto
    * @return
    */
    @Override
    public Page list(ScrapApplyDto scrapApplyDto) {
        LambdaQueryWrapper<ScrapApply> queryWrapper = new LambdaQueryWrapper<>();
        //根据申请人增加过滤条件
        if (ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())) {
            queryWrapper.eq(ScrapApply::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(ScrapApply::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(ScrapApply::getApplyUserId, AppUserUtil.getLoginAppUser().getId())
                    .orderByDesc(ScrapApply::getApplyTime);
        }
        //指定的全局模糊查询字段
        String searchCondition = scrapApplyDto.getSearchCondition();
        if (ObjectUtil.isNotEmpty(searchCondition)) {
            queryWrapper.and(q -> {
                q.like(ScrapApply::getCode, searchCondition)
                        .or(wrapper -> wrapper.like(ScrapApply::getSumTotalAmount, searchCondition))
                        .or(wrapper -> wrapper.like(ScrapApply::getApplyUserName, searchCondition))
                        .or(wrapper -> wrapper.like(ScrapApply::getApplyUserDeptName, searchCondition))
                        .or(wrapper -> wrapper.like(ScrapApply::getApplyUserOrgName, searchCondition));
            });
        }
        //根据实际业务构建具体的参数做查询
        Page<ScrapApply> page = scrapApplyMapper.selectPage(scrapApplyDto.getPage(),queryWrapper);
        Page<ScrapApplyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ScrapApplyVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param scrapApplyDto
    * @return
    */
    @Override
    public Page lists(ScrapApplyDto scrapApplyDto) {
        Page<ScrapApplyVo> pageVo = scrapApplyMapper.getPageList(scrapApplyDto.getPage(),scrapApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ScrapApplyVo get(Long id) {
        ScrapApply scrapApply = super.getById(id);
        ScrapApplyVo scrapApplyVo = null;
        if(scrapApply !=null){
            scrapApplyVo = dozerMapper.map(scrapApply,ScrapApplyVo.class);
            //根据id获取scrapApplyDetailVoList
            List<ScrapApplyDetailVo> scrapApplyDetailVoList = scrapApplyDetailService.getByFkIdVo(id);
            //设置scrapApplyVo，以便构建其对应的子表数据
            scrapApplyVo.setDetailList(scrapApplyDetailVoList);
        }
        return scrapApplyVo;
    }

    /**
    * 保存实体
    * @param scrapApplyDto
    * @return
    */
    @Transactional
    @Override
    public void save(ScrapApplyDto scrapApplyDto) {
        if(StringUtils.isBlank(scrapApplyDto.getCode())){
            getMaxCode(scrapApplyDto);
        }
        ScrapApply scrapApply = dozerMapper.map(scrapApplyDto,ScrapApply.class);
        super.save(scrapApply);
        saveSubList(scrapApplyDto);
    }

    //创建编码规则
    private void getMaxCode(ScrapApplyDto scrapApplyDto){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(scrapApplyDto, HashMap.class);
        String code = dictClient.getRuleValue("scrap_apply", "code", entityMap);
        log.debug("规则值{}",code);
        if (StringUtils.isNotBlank(code)){
            scrapApplyDto.setCode(code);
        }
    }

    /**
     * 保存集合
     * @param scrapApplyDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<ScrapApplyDto> scrapApplyDtos){
        List<ScrapApply> ScrapApplys = DozerUtils.mapList(dozerMapper, scrapApplyDtos, ScrapApply.class);
        super.saveBatch(ScrapApplys);
        for (ScrapApplyDto scrapApplyDto : scrapApplyDtos){
            saveSubList(scrapApplyDto);
        }
    }

    /**
     * 保存子表集合
     * @param scrapApplyDto
     * @return
     */
    @Transactional
    void saveSubList(ScrapApplyDto scrapApplyDto){
        //获取其子表集合
        List<ScrapApplyDetailDto> scrapApplyDetailDtoList = scrapApplyDto.getDetailList();
        if(scrapApplyDetailDtoList != null && !scrapApplyDetailDtoList.isEmpty()){
            for (ScrapApplyDetailDto scrapApplyDetailDto :scrapApplyDetailDtoList){
                //给子表关联id进行赋值
                scrapApplyDetailDto.setApplyId(scrapApplyDto.getId());
            }
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(scrapApplyDto,scrapApplyDetailDtoList);
            //调用保存子表的集合方法
            scrapApplyDetailService.saveList(scrapApplyDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param scrapApplyDto
     * @return
     */
    @Transactional
    @Override
    public void update(ScrapApplyDto scrapApplyDto) {
        scrapApplyDto.setUpdateTime(LocalDateTime.now());
        ScrapApply scrapApply = dozerMapper.map(scrapApplyDto,ScrapApply.class);
        super.updateById(scrapApply);
        updateSub(scrapApplyDto);
    }

    /**
    * 修改子类实体
    * @param scrapApplyDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(ScrapApplyDto scrapApplyDto) {
        List<ScrapApplyDetailDto> scrapApplyDetailDtoList = scrapApplyDto.getDetailList();
        if(scrapApplyDetailDtoList != null && !scrapApplyDetailDtoList.isEmpty()){
            LambdaQueryWrapper<ScrapApplyDetail> scrapApplyDetailListQueryWrapper = new LambdaQueryWrapper<>();
            scrapApplyDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(scrapApplyDto.getId()),ScrapApplyDetail::getApplyId,scrapApplyDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            EntityListConvert.convert(scrapApplyDto,scrapApplyDetailDtoList);
            List<ScrapApplyDetail> scrapApplyDetails = DozerUtils.mapList(dozerMapper, scrapApplyDetailDtoList, ScrapApplyDetail.class);
            //调用更新方法
            scrapApplyDetailService.saveOrUpdateOrDeleteBatch(scrapApplyDetailListQueryWrapper,scrapApplyDetails,scrapApplyDetails.size());
            //如果子可能还有子，那么遍历子，然后调用其子方法的updateSub
            if(scrapApplyDetailDtoList != null && !scrapApplyDetailDtoList.isEmpty()){
                for(ScrapApplyDetailDto scrapApplyDetailDto : scrapApplyDetailDtoList){
                    scrapApplyDetailService.updateSub(scrapApplyDetailDto);
                }
            }
        }
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public void delete(Long id) {
        super.removeById(id);
        scrapApplyDetailService.deleteByFkId(id);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public void deleteByFkId(Long id){
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<ScrapApplyVo> getByFkIdVo(Long id){
        return null;
    }

    /**
     * 流程中新增或修改
     * @param scrapApplyDto
     * @return
     */
    @Transactional
    @Override
    public ScrapApplyVo procSaveOrUpdate(ScrapApplyDto scrapApplyDto) {
        ScrapApplyVo scrapApplyVo = this.get(scrapApplyDto.getId());
        //判断是否未编辑、是否是起草
        //若不是起草(流程中保存)，手动调用保存前方法
        if (ObjectUtil.isNotEmpty(scrapApplyVo)
                && ObjectUtil.isNotEmpty(scrapApplyVo.getAuditStatus())
                && scrapApplyVo.getAuditStatus() != WfProcessEnum.DRAFT.getType()){
            this.procSaveBefore(scrapApplyVo.getCamundaProcinsId());
        }
        //获取编码规则
        if(StringUtils.isBlank(scrapApplyDto.getCode())){
            getMaxCode(scrapApplyDto);
        }
        ScrapApply scrapApply = dozerMapper.map(scrapApplyDto, ScrapApply.class);
        //通过外建删除子表
        scrapApplyDetailService.deleteByFkId(scrapApplyDto.getId());
        //获取子表内容
        List<ScrapApplyDetailDto> detailList = scrapApplyDto.getDetailList();
        if (CollectionUtils.isNotEmpty(detailList)){
            List<ScrapApplyDetail> scrapApplyList = DozerUtils.mapList(dozerMapper, detailList, ScrapApplyDetail.class);
            //给子表的关联id进行赋值
            for(ScrapApplyDetail detail : scrapApplyList){
                detail.setApplyId(scrapApply.getId());
            }
            scrapApplyDetailService.saveOrUpdateBatch(scrapApplyList);
        }
        super.saveOrUpdate(scrapApply);
        return this.get(scrapApply.getId());
    }
    /**
     * 通过流程实例id进行查询
     * @param procInstId
     * @return
     */
    @Override
    public ScrapApplyVo getByProc(String procInstId) {
        LambdaQueryWrapper<ScrapApply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(procInstId),ScrapApply::getCamundaProcinsId,procInstId);
        List<ScrapApply> scrapApplies = super.list(queryWrapper);
        ScrapApplyVo scrapApplyVo =null;
        if (scrapApplies != null && scrapApplies.size()>0){
            scrapApplyVo = dozerMapper.map(scrapApplies.get(0), ScrapApplyVo.class);
            //根据id获取子表信息
            List<ScrapApplyDetailVo> detailVoList = scrapApplyDetailService.getByFkIdVo(scrapApplyVo.getId());
            //构建对应的子表数据
            scrapApplyVo.setDetailList(detailVoList);
        }
        return scrapApplyVo;
    }

    /**
     * 通过流程实例id进行删除
     *
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        ScrapApplyVo scrapApplyVo = this.getByProc(procInstId);
        if (scrapApplyVo != null) {
            this.delete(scrapApplyVo.getId());
        }
    }
    /**
     * 报废申请保存前
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public Boolean procSaveBefore(String procInstId) {
        ScrapApplyVo scrapApplyVo = this.getByProc(procInstId);
        List<ScrapApplyDetailVo> detailList = scrapApplyVo.getDetailList();
        if (detailList.size()>0){
            for (ScrapApplyDetailVo detailVo : detailList){
                //获取本次报废申请数量
                BigDecimal scrapNumber = detailVo.getScrapNumber();
                //根据assetsDetailId查询资产明细表
                AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                if (ObjectUtil.isNotEmpty(assetsDetailVo)){
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                    //获取资产明细表当前可用数量
                    BigDecimal oldUsableNumber = assetsDetailDto.getUsableNumber();
                    //更新资产明细可用数量
                    BigDecimal newUsableNumber = oldUsableNumber.add(scrapNumber);
                    assetsDetailDto.setUsableNumber(newUsableNumber);
                    //获取库存明细账当前库存数量
                    BigDecimal oldStockNumber = assetsDetailDto.getStockNumber();
                    //更新库存数量
                    BigDecimal newStockNumber = oldStockNumber.add(scrapNumber);
                    assetsDetailDto.setStockNumber(newStockNumber);

                    assetsDetailService.update(assetsDetailDto);

                    //根据assetsId查询库存台账
                    AssetsVo assetsVo = assetsService.get(detailVo.getAssetsId());
                    if (ObjectUtil.isNotEmpty(assetsVo)){
                        AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                        //获取库存台账当前可用数量
                        BigDecimal oldAssetsUsableNumber = assetsDto.getUsableNumber();
                        //更新库存台账可用数量
                        BigDecimal newAssetsUsableNumber = oldAssetsUsableNumber.add(scrapNumber);
                        assetsDto.setUsableNumber(newAssetsUsableNumber);
                        //获取库存台账当前库存数量
                        BigDecimal oldAssetsStockNumber = assetsDto.getStockNumber();
                        //更新库存数量
                        BigDecimal newAssetsStockNumber = oldAssetsStockNumber.add(scrapNumber);
                        assetsDto.setStockNumber(newAssetsStockNumber);
                        assetsService.update(assetsDto);

                    }
                }
            }
        }
        return true;
    }

    /**
     * 报废申请保存后
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public Boolean procSaveAfter(String procInstId) {
        ScrapApplyVo scrapApplyVo = this.getByProc(procInstId);
        List<ScrapApplyDetailVo> detailList = scrapApplyVo.getDetailList();
        if (detailList.size() > 0) {
            for (ScrapApplyDetailVo detailVo : detailList) {
                //获取本次报废申请数量
                BigDecimal scrapNumber = detailVo.getScrapNumber();
                //根据assetsDetailId查询资产明细表
                AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                if (ObjectUtil.isNotEmpty(assetsDetailVo)) {
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                    //获取资产明细表当前可用数量
                    BigDecimal oldUsableNumber = assetsDetailDto.getUsableNumber();
                    //更新资产明细可用数量
                    BigDecimal newUsableNumber = oldUsableNumber.subtract(scrapNumber);
                    assetsDetailDto.setUsableNumber(newUsableNumber);
                    //获取资产明细表当前库存数量
                    BigDecimal oldStockNumber = assetsDetailDto.getStockNumber();
                    //更新库存数量
                    BigDecimal newStockNumber = oldStockNumber.subtract(scrapNumber);
                    assetsDetailDto.setStockNumber(newStockNumber);
                    assetsDetailService.update(assetsDetailDto);

                    //根据assetsId查询库存台账
                    AssetsVo assetsVo = assetsService.get(detailVo.getAssetsId());
                    if (ObjectUtil.isNotEmpty(assetsVo)) {
                        AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                        //获取库存台账当前可用数量
                        BigDecimal oldAssetsUsableNumber = assetsDto.getUsableNumber();
                        //更新库存台账可用数量
                        BigDecimal newAssetsUsableNumber = oldAssetsUsableNumber.subtract(scrapNumber);
                        assetsDto.setUsableNumber(newAssetsUsableNumber);
                        //获取库存台账当前库存数量
                        BigDecimal oldAssetsStockNumber = assetsDto.getStockNumber();
                        BigDecimal newAssetsStockNumber = oldAssetsStockNumber.subtract(scrapNumber);
                        assetsDto.setStockNumber(newAssetsStockNumber);
                        assetsService.update(assetsDto);
                    }
                }
            }
        }
        return true;
    }
    /**
     * 报废申请流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @Transactional
    @Override
    public Boolean procAuditSuccess(String procInstId) {
        ScrapApplyVo scrapApplyVo = this.getByProc(procInstId);
        List<ScrapApplyDetailVo> detailList = scrapApplyVo.getDetailList();
        //设置报废申请主表登记数量与未登记数量
        ScrapApplyDto scrapApplyDto = dozerMapper.map(scrapApplyVo, ScrapApplyDto.class);
        scrapApplyDto.setScrapRegisterNumber(BigDecimal.ZERO);
        scrapApplyDto.setNotScrapRegisterNumber(scrapApplyVo.getSumTotalNumber());
        this.update(scrapApplyDto);
        if (detailList.size()>0){
            for(ScrapApplyDetailVo detailVo : detailList){

                AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                if (ObjectUtil.isNotEmpty(assetsDetailVo)){
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                    assetsDetailDto.setStatus("待报废");
                    assetsDetailService.update(assetsDetailDto);
                }
                //设置报废申请详情表报废登记数量和未登记数量
                ScrapApplyDetailDto scrapApplyDetailDto = dozerMapper.map(detailVo, ScrapApplyDetailDto.class);
                scrapApplyDetailDto.setScrapRegisterNumber(BigDecimal.ZERO);
                scrapApplyDetailDto.setNotScrapRegisterNumber(detailVo.getScrapNumber());
                scrapApplyDetailService.update(scrapApplyDetailDto);
            }
        }
        return true;
    }
    /**
     * 单表简单查询操作
     * @param scrapApplyDto
     * @return
     */
    @Override
    public Page getRegisterList(ScrapApplyDto scrapApplyDto) {
        LambdaQueryWrapper<ScrapApply> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(ScrapApply::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(ScrapApply::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId());
        }
        //状态筛选字段
        queryWrapper.eq(ScrapApply::getAuditStatus, WfProcessEnum.DONE.getType());
        queryWrapper.gt(ScrapApply::getNotScrapRegisterNumber, YesNo.NO.getCode());
        queryWrapper.orderByDesc(ScrapApply::getApplyTime);
//根据实际业务构建具体的参数做查询
        //指定的查询字段
        String searchCondition= scrapApplyDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(ScrapApply::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(ScrapApply::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(ScrapApply::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(ScrapApply::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(ScrapApply::getApplyTime,searchCondition))
                        .or(wrapper ->wrapper.like(ScrapApply::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(ScrapApply::getSumTotalAmount,searchCondition))
                        .or(wrapper ->wrapper.like(ScrapApply::getSumTotalNumber,searchCondition));
            });
        }
        Page<ScrapApply> page = scrapApplyMapper.selectPage(scrapApplyDto.getPage(), queryWrapper);
        Page<ScrapApplyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ScrapApplyVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }
}
