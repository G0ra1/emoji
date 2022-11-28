package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.ScrapRegister;
import com.netwisd.biz.asset.entity.ScrapRegisterDetail;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.ScrapRegisterMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
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
 * @Description 报废登记 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-05 10:14:01
 */
@Service
@Slf4j
public class ScrapRegisterServiceImpl extends BatchServiceImpl<ScrapRegisterMapper, ScrapRegister> implements ScrapRegisterService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ScrapRegisterMapper scrapRegisterMapper;

    @Autowired
    private ScrapRegisterDetailService scrapRegisterDetailService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private ScrapApplyService scrapApplyService;

    @Autowired
    private ScrapApplyDetailService scrapApplyDetailService;

    /**
    * 单表简单查询操作
    * @param scrapRegisterDto
    * @return
    */
    @Override
    public Page list(ScrapRegisterDto scrapRegisterDto) {
        LambdaQueryWrapper<ScrapRegister> queryWrapper = new LambdaQueryWrapper<>();
        //根据申请人增加过滤条件
        if (ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())) {
            queryWrapper.eq(ScrapRegister::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(ScrapRegister::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .orderByDesc(ScrapRegister::getApplyTime);
        }
        //指定的全局模糊查询字段
        String searchCondition = scrapRegisterDto.getSearchCondition();
        if (ObjectUtil.isNotEmpty(searchCondition)) {
            queryWrapper.and(q -> {
                q.like(ScrapRegister::getCode, searchCondition)
                        .or(wrapper -> wrapper.like(ScrapRegister::getSumTotalAmount, searchCondition))
                        .or(wrapper -> wrapper.like(ScrapRegister::getApplyUserName, searchCondition))
                        .or(wrapper -> wrapper.like(ScrapRegister::getApplyUserDeptName, searchCondition))
                        .or(wrapper -> wrapper.like(ScrapRegister::getApplyUserOrgName, searchCondition));
            });
        }
        //根据实际业务构建具体的参数做查询
        Page<ScrapRegister> page = scrapRegisterMapper.selectPage(scrapRegisterDto.getPage(),queryWrapper);
        Page<ScrapRegisterVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ScrapRegisterVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param scrapRegisterDto
    * @return
    */
    @Override
    public Page lists(ScrapRegisterDto scrapRegisterDto) {
        Page<ScrapRegisterVo> pageVo = scrapRegisterMapper.getPageList(scrapRegisterDto.getPage(),scrapRegisterDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ScrapRegisterVo get(Long id) {
        ScrapRegister scrapRegister = super.getById(id);
        ScrapRegisterVo scrapRegisterVo = null;
        if(scrapRegister !=null){
            scrapRegisterVo = dozerMapper.map(scrapRegister,ScrapRegisterVo.class);
            //根据id获取scrapRegisterDetailVoList
            List<ScrapRegisterDetailVo> scrapRegisterDetailVoList = scrapRegisterDetailService.getByFkIdVo(id);
            //设置scrapRegisterVo，以便构建其对应的子表数据
            scrapRegisterVo.setDetailList(scrapRegisterDetailVoList);
        }
        return scrapRegisterVo;
    }

    /**
    * 保存实体
    * @param scrapRegisterDto
    * @return
    */
    @Transactional
    @Override
    public void save(ScrapRegisterDto scrapRegisterDto) {
        if(StringUtils.isBlank(scrapRegisterDto.getCode())){
            getMaxCode(scrapRegisterDto);
        }
        ScrapRegister scrapRegister = dozerMapper.map(scrapRegisterDto,ScrapRegister.class);
        super.save(scrapRegister);
        saveSubList(scrapRegisterDto);
    }

    //获取编码规则
    private void getMaxCode(ScrapRegisterDto scrapRegisterDto){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(scrapRegisterDto, HashMap.class);
        String code = dictClient.getRuleValue("scrap_register", "code", entityMap);
        log.debug("规则值{}",code);
        if (StringUtils.isNotBlank(code)){
            scrapRegisterDto.setCode(code);
        }
    }


    /**
     * 保存集合
     * @param scrapRegisterDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<ScrapRegisterDto> scrapRegisterDtos){
        List<ScrapRegister> ScrapRegisters = DozerUtils.mapList(dozerMapper, scrapRegisterDtos, ScrapRegister.class);
        super.saveBatch(ScrapRegisters);
        for (ScrapRegisterDto scrapRegisterDto : scrapRegisterDtos){
            saveSubList(scrapRegisterDto);
        }
    }

    /**
     * 保存子表集合
     * @param scrapRegisterDto
     * @return
     */
    @Transactional
    void saveSubList(ScrapRegisterDto scrapRegisterDto){
        //获取其子表集合
        List<ScrapRegisterDetailDto> scrapRegisterDetailDtoList = scrapRegisterDto.getDetailList();
        if(scrapRegisterDetailDtoList != null && !scrapRegisterDetailDtoList.isEmpty()){
            for (ScrapRegisterDetailDto scrapRegisterDetailDto : scrapRegisterDetailDtoList){
                scrapRegisterDetailDto.setRegisterId(scrapRegisterDto.getId());
            }
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(scrapRegisterDto,scrapRegisterDetailDtoList);
            //调用保存子表的集合方法
            scrapRegisterDetailService.saveList(scrapRegisterDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param scrapRegisterDto
     * @return
     */
    @Transactional
    @Override
    public void update(ScrapRegisterDto scrapRegisterDto) {
        scrapRegisterDto.setUpdateTime(LocalDateTime.now());
        ScrapRegister scrapRegister = dozerMapper.map(scrapRegisterDto,ScrapRegister.class);
        super.updateById(scrapRegister);
        updateSub(scrapRegisterDto);
    }

    /**
    * 修改子类实体
    * @param scrapRegisterDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(ScrapRegisterDto scrapRegisterDto) {
        List<ScrapRegisterDetailDto> scrapRegisterDetailDtoList = scrapRegisterDto.getDetailList();
        if(scrapRegisterDetailDtoList != null && !scrapRegisterDetailDtoList.isEmpty()){
            LambdaQueryWrapper<ScrapRegisterDetail> scrapRegisterDetailListQueryWrapper = new LambdaQueryWrapper<>();
            scrapRegisterDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(scrapRegisterDto.getId()),ScrapRegisterDetail::getRegisterId,scrapRegisterDto.getId());
            //根据主表的id给子表的关联id进行赋值
            scrapRegisterDetailDtoList.forEach(detailList->{
                detailList.setRegisterId(scrapRegisterDto.getId());
            });
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(scrapRegisterDto,scrapRegisterDetailDtoList);
            List<ScrapRegisterDetail> scrapRegisterDetails = DozerUtils.mapList(dozerMapper, scrapRegisterDetailDtoList, ScrapRegisterDetail.class);
            //调用更新方法
            scrapRegisterDetailService.saveOrUpdateOrDeleteBatch(scrapRegisterDetailListQueryWrapper,scrapRegisterDetails,scrapRegisterDetails.size());
            //如果子可能还有子，那么遍历子，然后调用其子方法的updateSub
            if(scrapRegisterDetailDtoList != null && !scrapRegisterDetailDtoList.isEmpty()){
                for(ScrapRegisterDetailDto scrapRegisterDetailDto : scrapRegisterDetailDtoList){
                    scrapRegisterDetailService.updateSub(scrapRegisterDetailDto);
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
        scrapRegisterDetailService.deleteByFkId(id);
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
    public List<ScrapRegisterVo> getByFkIdVo(Long id){
        return null;
    }

    /**
     * 流程中新增或修改
     * @param scrapRegisterDto
     * @return
     */
    @Transactional
    @Override
    public ScrapRegisterVo procSaveOrUpdate(ScrapRegisterDto scrapRegisterDto) {
        //获取编号
        if(StringUtils.isBlank(scrapRegisterDto.getCode())){
            getMaxCode(scrapRegisterDto);
        }
        ScrapRegister scrapRegister = dozerMapper.map(scrapRegisterDto, ScrapRegister.class);
        //通过外建删除子表
        scrapRegisterDetailService.deleteByFkId(scrapRegisterDto.getId());
        //获取子表内容
        List<ScrapRegisterDetailDto> detailList = scrapRegisterDto.getDetailList();
        if (CollectionUtils.isNotEmpty(detailList)){
            List<ScrapRegisterDetail> scrapRegisterDetails = DozerUtils.mapList(dozerMapper, detailList, ScrapRegisterDetail.class);
            //给子表的关联id进行赋值
            for(ScrapRegisterDetail detail : scrapRegisterDetails){
                detail.setRegisterId(scrapRegister.getId());
            }
            scrapRegisterDetailService.saveOrUpdateBatch(scrapRegisterDetails);
        }
        super.saveOrUpdate(scrapRegister);
        return this.get(scrapRegister.getId());
    }
    /**
     * 通过流程实例id进行查询
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public ScrapRegisterVo getByProc(String procInstId) {
        LambdaQueryWrapper<ScrapRegister> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(procInstId),ScrapRegister::getCamundaProcinsId,procInstId);
        List<ScrapRegister> scrapRegisters = super.list(queryWrapper);
        ScrapRegisterVo scrapRegisterVo =null;
        if (scrapRegisters != null && scrapRegisters.size()>0){
            scrapRegisterVo = dozerMapper.map(scrapRegisters.get(0), ScrapRegisterVo.class);
            //根据id获取子表信息
            List<ScrapRegisterDetailVo> detailVoList = scrapRegisterDetailService.getByFkIdVo(scrapRegisterVo.getId());
            //构建对应的子表数据
            scrapRegisterVo.setDetailList(detailVoList);
        }
        return scrapRegisterVo;
    }

    /**
     * 通过流程实例id进行删除
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        ScrapRegisterVo scrapRegisterVo = this.getByProc(procInstId);
        if (scrapRegisterVo != null) {
            this.delete(scrapRegisterVo.getId());
        }
    }
    /**
     * 报废登记流程结束
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public Boolean procAuditSuccess(String procInstId) {
        ScrapRegisterVo scrapRegisterVo = this.getByProc(procInstId);
        List<ScrapRegisterDetailVo> detailList = scrapRegisterVo.getDetailList();
        if (detailList != null && detailList.size()>0){
            for (ScrapRegisterDetailVo scrapRegisterDetailVo : detailList) {
                BigDecimal scrapRegisterNumber = scrapRegisterDetailVo.getScrapRegisterNumber();
                if (ObjectUtil.isNotEmpty(scrapRegisterDetailVo.getAssetsId())){
                    AssetsVo assetsVo = assetsService.get(scrapRegisterDetailVo.getAssetsId());
                    AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                    //获取库存台账报废数量
                    BigDecimal oldScrappedNumber = assetsDto.getScrappedNumber();
                    //更新库存台账报废数量
                    BigDecimal newScrappedNumber = oldScrappedNumber.add(scrapRegisterNumber);
                    assetsDto.setScrappedNumber(newScrappedNumber);
                    assetsService.update(assetsDto);
                }
                if (ObjectUtil.isNotEmpty(scrapRegisterDetailVo.getAssetsDetailId())){
                    //获取库存明细账数据
                    AssetsDetailVo assetsDetailVo = assetsDetailService.get(scrapRegisterDetailVo.getAssetsDetailId());
                    //将vo转换成dto
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                    //获取库存明细账当前报废数量
                    BigDecimal oldScrappedNumber = assetsDetailDto.getScrappedNumber();
                    //更新库存明细账报废数量
                    BigDecimal newScrappedNumber = oldScrappedNumber.add(scrapRegisterNumber);
                    assetsDetailDto.setScrappedNumber(newScrappedNumber);
                    //修改该条物资状态为已报废
                    assetsDetailDto.setStatus("已报废");
                    assetsDetailService.update(assetsDetailDto);
                }
            }
        }

        return true;
    }

    /**
     * 报废登记保存后
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public Boolean procSaveBefore(String procInstId) {
        //通过流程实例id获取数据
        ScrapRegisterVo scrapRegisterVo = this.getByProc(procInstId);
        //获取子表数据
        List<ScrapRegisterDetailVo> detailList = scrapRegisterVo.getDetailList();
        if(detailList != null && detailList.size()>0){
            for (ScrapRegisterDetailVo detailVo : detailList){
                //获取本次报废登记数量
                BigDecimal scrapRegisterNumber = detailVo.getScrapRegisterNumber();
                //通过报废登记applyId查询报废申请
                if (ObjectUtil.isNotEmpty(detailVo.getApplyId())){
                    ScrapApplyVo scrapApplyVo = scrapApplyService.get(detailVo.getApplyId());
                    if (ObjectUtil.isNotEmpty(scrapApplyVo)){
                        ScrapApplyDto scrapApplyDto = dozerMapper.map(scrapApplyVo, ScrapApplyDto.class);
                        //获取当前报废申请未登记数量
                        BigDecimal oldNotRegisterNumber = scrapApplyDto.getNotScrapRegisterNumber();
                        //更新未登记数量
                        BigDecimal newNotRegisterNumber = oldNotRegisterNumber.add(scrapRegisterNumber);
                        scrapApplyDto.setNotScrapRegisterNumber(newNotRegisterNumber);
                        //获取采购登记数量
                        BigDecimal oldApplyRegisterNumber = scrapApplyDto.getScrapRegisterNumber();
                        //更新采购登记数量
                        BigDecimal newApplyRegisterNumber = oldApplyRegisterNumber.subtract(scrapRegisterNumber);
                        scrapApplyDto.setScrapRegisterNumber(newApplyRegisterNumber);
                        scrapApplyService.update(scrapApplyDto);
                    }
                }
                //通过报废登记applyDetailId查询报废申请
                if (ObjectUtil.isNotEmpty(detailVo.getApplyDetailId())){
                    ScrapApplyDetailVo scrapApplyDetailVo = scrapApplyDetailService.get(detailVo.getApplyDetailId());
                    if (ObjectUtil.isNotEmpty(scrapApplyDetailVo)){
                        ScrapApplyDetailDto scrapApplyDetailDto = dozerMapper.map(scrapApplyDetailVo, ScrapApplyDetailDto.class);
                        //获取当前报废申请未登记数量
                        BigDecimal oldNotRegisterNumber = scrapApplyDetailDto.getNotScrapRegisterNumber();
                        //更新未登记数量
                        BigDecimal newNotRegisterNumber = oldNotRegisterNumber.add(scrapRegisterNumber);
                        scrapApplyDetailDto.setNotScrapRegisterNumber(newNotRegisterNumber);
                        //获取采购登记数量
                        BigDecimal oldApplyRegisterNumber = scrapApplyDetailDto.getScrapRegisterNumber();
                        //更新采购登记数量
                        BigDecimal newApplyRegisterNumber = oldApplyRegisterNumber.subtract(scrapRegisterNumber);
                        scrapApplyDetailDto.setScrapRegisterNumber(newApplyRegisterNumber);
                        scrapApplyDetailService.update(scrapApplyDetailDto);
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
        //通过流程实例id获取数据
        ScrapRegisterVo scrapRegisterVo = this.getByProc(procInstId);
        //获取子表数据
        List<ScrapRegisterDetailVo> detailList = scrapRegisterVo.getDetailList();
        if(detailList != null && detailList.size()>0){
            for (ScrapRegisterDetailVo detailVo : detailList){
                //获取本次报废登记数量
                BigDecimal scrapRegisterNumber = detailVo.getScrapRegisterNumber();
                //通过报废登记applyId查询报废申请
                if (ObjectUtil.isNotEmpty(detailVo.getApplyId())){
                    ScrapApplyVo scrapApplyVo = scrapApplyService.get(detailVo.getApplyId());
                    if (ObjectUtil.isNotEmpty(scrapApplyVo)){
                        ScrapApplyDto scrapApplyDto = dozerMapper.map(scrapApplyVo, ScrapApplyDto.class);
                        //获取当前报废申请未登记数量
                        BigDecimal oldNotRegisterNumber = scrapApplyDto.getNotScrapRegisterNumber();
                        //更新未登记数量
                        BigDecimal newNotRegisterNumber = oldNotRegisterNumber.subtract(scrapRegisterNumber);
                        scrapApplyDto.setNotScrapRegisterNumber(newNotRegisterNumber);
                        //获取采购登记数量
                        BigDecimal oldApplyRegisterNumber = scrapApplyDto.getScrapRegisterNumber();
                        //更新采购登记数量
                        BigDecimal newApplyRegisterNumber = oldApplyRegisterNumber.add(scrapRegisterNumber);
                        scrapApplyDto.setScrapRegisterNumber(newApplyRegisterNumber);
                        scrapApplyService.update(scrapApplyDto);
                    }
                }
                //通过报废登记applyDetailId查询报废申请
                if (ObjectUtil.isNotEmpty(detailVo.getApplyDetailId())){
                    ScrapApplyDetailVo scrapApplyDetailVo = scrapApplyDetailService.get(detailVo.getApplyDetailId());
                    if (ObjectUtil.isNotEmpty(scrapApplyDetailVo)){
                        ScrapApplyDetailDto scrapApplyDetailDto = dozerMapper.map(scrapApplyDetailVo, ScrapApplyDetailDto.class);
                        //获取当前报废申请未登记数量
                        BigDecimal oldNotRegisterNumber = scrapApplyDetailDto.getNotScrapRegisterNumber();
                        //更新未登记数量
                        BigDecimal newNotRegisterNumber = oldNotRegisterNumber.subtract(scrapRegisterNumber);
                        scrapApplyDetailDto.setNotScrapRegisterNumber(newNotRegisterNumber);
                        //获取采购登记数量
                        BigDecimal oldApplyRegisterNumber = scrapApplyDetailDto.getScrapRegisterNumber();
                        //更新采购登记数量
                        BigDecimal newApplyRegisterNumber = oldApplyRegisterNumber.add(scrapRegisterNumber);
                        scrapApplyDetailDto.setScrapRegisterNumber(newApplyRegisterNumber);
                        scrapApplyDetailService.update(scrapApplyDetailDto);
                    }
                }

            }
        }
        return true;
    }
}
