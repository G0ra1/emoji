package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.MaintainApply;
import com.netwisd.biz.asset.entity.MaintainApplyDetail;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaintainApplyMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
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
 * @Description 维修申请 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-27 10:28:25
 */
@Service
@Slf4j
public class MaintainApplyServiceImpl extends BatchServiceImpl<MaintainApplyMapper, MaintainApply> implements MaintainApplyService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaintainApplyMapper maintainApplyMapper;

    @Autowired
    private MaintainApplyDetailService maintainApplyDetailService;

    @Autowired
    private MaintainAssetsResultService maintainAssetsResultService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private DictClient dictClient;

    /**
    * 单表简单查询操作
    * @param maintainApplyDto
    * @return
    */
    @Override
    public Page list(MaintainApplyDto maintainApplyDto) {
        LambdaQueryWrapper<MaintainApply> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaintainApply> page = maintainApplyMapper.selectPage(maintainApplyDto.getPage(),queryWrapper);
        Page<MaintainApplyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaintainApplyVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param maintainApplyDto
    * @return
    */
    @Override
    public Page lists(MaintainApplyDto maintainApplyDto) {
        Page<MaintainApplyVo> pageVo = maintainApplyMapper.getPageList(maintainApplyDto.getPage(),maintainApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaintainApplyVo get(Long id) {
        MaintainApply maintainApply = super.getById(id);
        MaintainApplyVo maintainApplyVo = null;
        if(maintainApply !=null){
            maintainApplyVo = dozerMapper.map(maintainApply,MaintainApplyVo.class);
            //根据id获取maintainApplyDetailVoList
            List<MaintainApplyDetailVo> maintainApplyDetailVoList = maintainApplyDetailService.getByFkIdVo(id);
            //设置maintainApplyVo，以便构建其对应的子表数据
            maintainApplyVo.setMaintainApplyDetailList(maintainApplyDetailVoList);
        }
        return maintainApplyVo;
    }

    /**
    * 保存实体
    * @param maintainApplyDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaintainApplyDto maintainApplyDto) {
        //设置维修申请编号
        if (StringUtils.isBlank(maintainApplyDto.getCode())){
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap entityMap = objectMapper.convertValue(maintainApplyDto, HashMap.class);
            String code = dictClient.getRuleValue("maintain_apply","code",entityMap);
            log.debug("规则值:{}",code);
            if (StringUtils.isNotBlank(code)){
                maintainApplyDto.setCode(code);
            }
        }
        MaintainApply maintainApply = dozerMapper.map(maintainApplyDto,MaintainApply.class);
        super.save(maintainApply);
        saveSubList(maintainApplyDto);
    }
    /**
     * 保存集合
     * @param maintainApplyDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaintainApplyDto> maintainApplyDtos){
        List<MaintainApply> MaintainApplys = DozerUtils.mapList(dozerMapper, maintainApplyDtos, MaintainApply.class);
        super.saveBatch(MaintainApplys);
        for (MaintainApplyDto maintainApplyDto : maintainApplyDtos){
            saveSubList(maintainApplyDto);
        }
    }

    /**
     * 保存子表集合
     * @param maintainApplyDto
     * @return
     */
    @Transactional
    void saveSubList(MaintainApplyDto maintainApplyDto){
        //获取其子表集合
        List<MaintainApplyDetailDto> maintainApplyDetailDtoList = maintainApplyDto.getMaintainApplyDetailList();
        if(maintainApplyDetailDtoList != null && !maintainApplyDetailDtoList.isEmpty()){
            //给子表关联建赋值
            maintainApplyDetailDtoList.forEach(maintainApplyDetailDto -> {
                maintainApplyDetailDto.setMaintainApplyId(maintainApplyDto.getId());
            });
            //调用保存子表的集合方法
            maintainApplyDetailService.saveList(maintainApplyDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param maintainApplyDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaintainApplyDto maintainApplyDto) {
        maintainApplyDto.setUpdateTime(LocalDateTime.now());
        MaintainApply maintainApply = dozerMapper.map(maintainApplyDto,MaintainApply.class);
        super.updateById(maintainApply);
        updateSub(maintainApplyDto);
    }

    /**
    * 修改子类实体
    * @param maintainApplyDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaintainApplyDto maintainApplyDto) {
        List<MaintainApplyDetailDto> maintainApplyDetailDtoList = maintainApplyDto.getMaintainApplyDetailList();
        if(maintainApplyDetailDtoList != null && !maintainApplyDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaintainApplyDetail> maintainApplyDetailListQueryWrapper = new LambdaQueryWrapper<>();
            maintainApplyDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(maintainApplyDto.getId()),MaintainApplyDetail::getMaintainApplyId,maintainApplyDto.getId());
            //给子表的关联键赋值
            maintainApplyDetailDtoList.forEach(maintainApplyDetailDto -> {
                maintainApplyDetailDto.setMaintainApplyId(maintainApplyDto.getId());
            });
            List<MaintainApplyDetail> maintainApplyDetails = DozerUtils.mapList(dozerMapper, maintainApplyDetailDtoList, MaintainApplyDetail.class);
            //调用更新方法
            maintainApplyDetailService.saveOrUpdateOrDeleteBatch(maintainApplyDetailListQueryWrapper,maintainApplyDetails,maintainApplyDetails.size());
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
        maintainApplyDetailService.deleteByFkId(id);
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
    public List<MaintainApplyVo> getByFkIdVo(Long id){
        return null;
    }

    /**
     * 根据流程实例id获取数据
     * @param procInstId
     * @return
     */
    @Override
    public MaintainApplyVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaintainApply> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(MaintainApply::getCamundaProcinsId,procInstId);
        MaintainApply maintainApply = maintainApplyMapper.selectOne(lambdaQueryWrapper);
        MaintainApplyVo maintainApplyVo = null;
        if (maintainApply != null){
            maintainApplyVo = dozerMapper.map(maintainApply,MaintainApplyVo.class);
            //根据id获取子表
            List<MaintainApplyDetailVo> maintainApplyDetailVoList = maintainApplyDetailService.getByFkIdVo(maintainApplyVo.getId());
            //设置maintStorageVo，以便构建其对应的子表数据
            maintainApplyVo.setMaintainApplyDetailList(maintainApplyDetailVoList);
        }
        return maintainApplyVo;
    }
    /**
     * 维修申请流程保存前方法
     *      减少维修资产明细表的当前库存数量
     *      计算未维修数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result maintainApplySaveBefore(String processInstanceId){
        MaintainApplyVo maintainApplyVo = this.getByProc(processInstanceId);
        //获取子表数据
        List<MaintainApplyDetailVo> maintainApplyDetailVos = maintainApplyVo.getMaintainApplyDetailList();
        Boolean result = true;
        //判断是否是计划内 0计划内，1计划外
        if (maintainApplyVo.getApplyType()==0){
            for (MaintainApplyDetailVo maintainApplyDetailVo:maintainApplyDetailVos){
                MaintainAssetsResultVo maintainAssetsResultVo = maintainAssetsResultService.get(maintainApplyDetailVo.getMaintainAssetsDetailId());
                MaintainAssetsResultDto maintainAssetsResultDto = dozerMapper.map(maintainAssetsResultVo,MaintainAssetsResultDto.class);
                //获取当前维修申请数量
                BigDecimal maintainApplyNumber = maintainApplyDetailVo.getMaintainApplyNumber();
                //获取结果表的未维修数量
                BigDecimal notMaintainNumber = maintainAssetsResultVo.getNotMaintainNumber();
                //获取结果表原库存数量
                BigDecimal stockNumber = maintainAssetsResultVo.getStockNumber();
                //更新结果表未维修数量
                BigDecimal newNotMaintainNumber = notMaintainNumber.add(maintainApplyNumber);
                //更新结果表库存数量
                BigDecimal newStockNumber = stockNumber.add(maintainApplyNumber);
                //更新数据库数据
                maintainAssetsResultDto.setNotMaintainNumber(newNotMaintainNumber);
                maintainAssetsResultDto.setStockNumber(newStockNumber);
                result = maintainAssetsResultService.update(maintainAssetsResultDto);
             }
        }
        return Result.success(result);

    }
    /**
     * 维修申请流程保存后方法
     *      计算维修资产明细表的当前库存数量
     *      计算未维修数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result maintainApplySaveAfter(String processInstanceId) {
        MaintainApplyVo maintainApplyVo = this.getByProc(processInstanceId);
        //获取子表数据
        List<MaintainApplyDetailVo> maintainApplyDetailVos = maintainApplyVo.getMaintainApplyDetailList();
        Boolean result = true;
        //判断是否是计划内 0计划内，1计划外
        if (maintainApplyVo.getApplyType() == 0){
            for (MaintainApplyDetailVo maintainApplyDetailVo:maintainApplyDetailVos){
                MaintainAssetsResultVo maintainAssetsResultVo = maintainAssetsResultService.get(maintainApplyDetailVo.getMaintainAssetsDetailId());
                MaintainAssetsResultDto maintainAssetsResultDto = dozerMapper.map(maintainAssetsResultVo,MaintainAssetsResultDto.class);
                //获取当前维修申请数量
                BigDecimal maintainApplyNumber = maintainApplyDetailVo.getMaintainApplyNumber();
                //获取结果表的未维修数量
                BigDecimal notMaintainNumber = maintainAssetsResultVo.getNotMaintainNumber();
                //获取结果表原库存数量
                BigDecimal stockNumber = maintainAssetsResultVo.getStockNumber();
                //更新结果表未维修数量
                BigDecimal newNotMaintainNumber = notMaintainNumber.subtract(maintainApplyNumber);
                //更新结果表库存数量
                BigDecimal newStockNumber = stockNumber.subtract(maintainApplyNumber);
                //更新数据库数据
                maintainAssetsResultDto.setNotMaintainNumber(newNotMaintainNumber);
                maintainAssetsResultDto.setStockNumber(newStockNumber);
                result = maintainAssetsResultService.update(maintainAssetsResultDto);
            }
        }
        return Result.success(result);
     }

    /**
     * 维修申请流程审批成功后
     *      修改资产信息，资产明细信息
     *      减库存数量，加维修数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result maintainApplyProcSuccess(String processInstanceId) {
        MaintainApplyVo maintainApplyVo = this.getByProc(processInstanceId);

        List<MaintainApplyDetailVo> maintainApplyDetailVos = maintainApplyVo.getMaintainApplyDetailList();
        for (MaintainApplyDetailVo maintainApplyDetailVo:maintainApplyDetailVos){
            //获得本次维修申请数量
            BigDecimal maintianApplyNumber = maintainApplyDetailVo.getMaintainApplyNumber();
            AssetsVo assetsVo = assetsService.get(maintainApplyDetailVo.getAssetsId());
            AssetsDto assetsDto = dozerMapper.map(assetsVo,AssetsDto.class);
            //获取原资产库存数量
            BigDecimal assetsStockNumber = assetsDto.getStockNumber();
            //获取原资产维修数量
            BigDecimal assetsRepairNumber = assetsDto.getRepairNumber();
            //更新资产库存数量
            BigDecimal newAssetsStockNumber = assetsStockNumber.subtract(maintianApplyNumber);
            //更新资产维修数量
            BigDecimal newAssetsRepairNumber = assetsRepairNumber.add(maintianApplyNumber);

            assetsDto.setStockNumber(newAssetsStockNumber);
            assetsDto.setRepairNumber(newAssetsRepairNumber);
            assetsService.update(assetsDto);

            AssetsDetailVo assetsDetailVo = assetsDetailService.get(maintainApplyDetailVo.getAssetsDetailId());
            AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo,AssetsDetailDto.class);
            //获取原资产详情库存数量
            BigDecimal assetsDetailStockNumber = assetsDetailDto.getStockNumber();
            //获取原资产详情维修数量
            BigDecimal assetsDetailRepairNumber = assetsDetailVo.getRepairNumber();
            //更新后资产详情库存数量
            BigDecimal newAssetsDetailStockNumber = assetsDetailStockNumber.subtract(maintianApplyNumber);
            //更新后资产详情维修数量
            BigDecimal newAssetsDetailRepairNumber = assetsDetailRepairNumber.add(maintianApplyNumber);
            assetsDetailDto.setStockNumber(newAssetsDetailStockNumber);
            assetsDetailDto.setRepairNumber(newAssetsDetailRepairNumber);
            assetsDetailService.update(assetsDetailDto);

        }
        return Result.success();
    }
}
