package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.MaterialDelivery;
import com.netwisd.biz.asset.entity.MaterialDeliveryDetail;
import com.netwisd.biz.asset.mapper.MaterialDeliveryMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 资产出库管理 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-24 14:37:41
 */
@Service
@Slf4j
public class MaterialDeliveryServiceImpl extends BatchServiceImpl<MaterialDeliveryMapper, MaterialDelivery> implements MaterialDeliveryService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialDeliveryMapper materialDeliveryMapper;

    @Autowired
    private MaterialDeliveryDetailService materialDeliveryDetailService;

    @Autowired
    private MaterialDeliveryResultService materialDeliveryResultService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private DaybookService daybookService;

    /**
    * 单表简单查询操作
    * @param materialDeliveryDto
    * @return
    */
    @Override
    public Page list(MaterialDeliveryDto materialDeliveryDto) {
        LambdaQueryWrapper<MaterialDelivery> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialDelivery> page = materialDeliveryMapper.selectPage(materialDeliveryDto.getPage(),queryWrapper);
        Page<MaterialDeliveryVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDeliveryVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialDeliveryDto
    * @return
    */
    @Override
    public Page lists(MaterialDeliveryDto materialDeliveryDto) {
        Page<MaterialDeliveryVo> pageVo = materialDeliveryMapper.getPageList(materialDeliveryDto.getPage(),materialDeliveryDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDeliveryVo get(Long id) {
        MaterialDelivery materialDelivery = super.getById(id);
        MaterialDeliveryVo materialDeliveryVo = null;
        if(materialDelivery !=null){
            materialDeliveryVo = dozerMapper.map(materialDelivery,MaterialDeliveryVo.class);

            //根据id获取materialDeliveryDetailVoList
            List<MaterialDeliveryDetailVo> materialDeliveryDetailVoList = materialDeliveryDetailService.getByDeliveryId(id);
            //设置materialDeliveryVo，以便构建其对应的子表数据
            materialDeliveryVo.setDetailList(materialDeliveryDetailVoList);
        }
        return materialDeliveryVo;
    }

    /**
    * 保存实体
    * @param materialDeliveryDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialDeliveryDto materialDeliveryDto) {
        //获取申请编号
        if(StringUtils.isBlank(materialDeliveryDto.getCode())){
            materialDeliveryDto.setCode(getMaxCode());
        }
        MaterialDelivery materialDelivery = dozerMapper.map(materialDeliveryDto,MaterialDelivery.class);
        super.save(materialDelivery);
        saveSubList(materialDeliveryDto);
    }

    /**
     * 获取出库编号
     * @return
     */
    private String getMaxCode(){
        String maxCode = materialDeliveryMapper.getMaxCode();
        String applyCode = "CKSQ-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"-";
        if(StringUtils.isBlank(maxCode)){
            applyCode += "0001";
        }else {
            DecimalFormat df = new DecimalFormat("0000");
            applyCode += df.format(Long.valueOf(maxCode)+1);
        }
        log.debug("资产出库申请编号===>{}",applyCode);
        return applyCode;
    }

    /**
     * 保存集合
     * @param materialDeliveryDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialDeliveryDto> materialDeliveryDtos){
        materialDeliveryDtos.forEach(materialDeliveryDto -> {
            //获取申请编号
            if(StringUtils.isBlank(materialDeliveryDto.getCode())){
                materialDeliveryDto.setCode(getMaxCode());
            }
        });
        List<MaterialDelivery> MaterialDeliverys = DozerUtils.mapList(dozerMapper, materialDeliveryDtos, MaterialDelivery.class);
        super.saveBatch(MaterialDeliverys);
        for (MaterialDeliveryDto materialDeliveryDto : materialDeliveryDtos){
            saveSubList(materialDeliveryDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialDeliveryDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialDeliveryDto materialDeliveryDto){
        //获取其子表集合
        List<MaterialDeliveryDetailDto> materialDeliveryDetailDtoList = materialDeliveryDto.getDetailList();
        for (MaterialDeliveryDetailDto materialDeliveryDetailDto : materialDeliveryDetailDtoList) {
            materialDeliveryDetailDto.setDeliveryId(materialDeliveryDto.getId());
        }
        //调用保存子表的集合方法
        materialDeliveryDetailService.saveList(materialDeliveryDetailDtoList);
    }

    /**
     * 修改实体
     * @param materialDeliveryDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialDeliveryDto materialDeliveryDto) {
        materialDeliveryDto.setUpdateTime(LocalDateTime.now());
        MaterialDelivery materialDelivery = dozerMapper.map(materialDeliveryDto,MaterialDelivery.class);
        super.updateById(materialDelivery);
        updateSub(materialDeliveryDto);
    }

    /**
    * 修改子类实体
    * @param materialDeliveryDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialDeliveryDto materialDeliveryDto) {
        List<MaterialDeliveryDetailDto> materialDeliveryDetailDtoList = materialDeliveryDto.getDetailList();
        //设置主子表关联关系，外键
        for (MaterialDeliveryDetailDto materialDeliveryDetailDto : materialDeliveryDetailDtoList) {
            materialDeliveryDetailDto.setDeliveryId(materialDeliveryDto.getId());
        }
        if(materialDeliveryDetailDtoList != null && !materialDeliveryDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialDeliveryDetail> materialDeliveryDetailListQueryWrapper = new LambdaQueryWrapper<>();
            materialDeliveryDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialDeliveryDto.getId()),MaterialDeliveryDetail::getDeliveryId,materialDeliveryDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            materialDeliveryDetailDtoList.forEach(materialDeliveryDetailDto -> {
                materialDeliveryDetailDto.setDeliveryId(materialDeliveryDto.getId());
            });
            List<MaterialDeliveryDetail> materialDeliveryDetails = DozerUtils.mapList(dozerMapper, materialDeliveryDetailDtoList, MaterialDeliveryDetail.class);
            //调用更新方法
            materialDeliveryDetailService.saveOrUpdateOrDeleteBatch(materialDeliveryDetailListQueryWrapper,materialDeliveryDetails,materialDeliveryDetails.size());
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
        materialDeliveryDetailService.deleteByDeliveryId(id);
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        LambdaQueryWrapper<MaterialDelivery> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialDelivery::getCamundaProcinsId,procInstId);
        MaterialDelivery materialDelivery = materialDeliveryMapper.selectOne(queryWrapper);
        if(materialDelivery !=null){
            this.delete(materialDelivery.getId());
        }
    }

    @Override
    public MaterialDeliveryVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaterialDelivery> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialDelivery::getCamundaProcinsId,procInstId);
        MaterialDelivery materialDelivery = materialDeliveryMapper.selectOne(queryWrapper);
        MaterialDeliveryVo materialDeliveryVo = null;
        if(materialDelivery !=null){
            materialDeliveryVo = dozerMapper.map(materialDelivery,MaterialDeliveryVo.class);
            //根据id获取materialDeliveryDetailVoList
            List<MaterialDeliveryDetailVo> materialDeliveryDetailVoList = materialDeliveryDetailService.getByDeliveryId(materialDelivery.getId());
            //设置materialDeliveryVo，以便构建其对应的子表数据
            materialDeliveryVo.setDetailList(materialDeliveryDetailVoList);

        }
        return materialDeliveryVo;
    }

    /**
     * 流程保存前调用
     *      保存前根据数据库信息，恢复资产数值
     *      增加库存数量，减少出库数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result deliverySaveBefore(String processInstanceId) {
//        MaterialDeliveryVo materialDeliveryVo = this.getByProc(processInstanceId);
//        for (MaterialDeliveryAssetsVo materialDeliveryAssetsVo : materialDeliveryVo.getMaterialDeliveryAssetsList()) {
//            //原出库数量
//            BigDecimal deliveryNumber = materialDeliveryAssetsVo.getDeliveryNumber();
//            AssetsVo assetsVo = assetsService.get(materialDeliveryAssetsVo.getAssetsId());
//            AssetsDto assetsDto = dozerMapper.map(assetsVo,AssetsDto.class);
//            BigDecimal oldStockNumber = assetsVo.getStockNumber();
//            BigDecimal oldDeliveryNumber = assetsVo.getDeliveryNumber();
//            BigDecimal newStockNumber = oldStockNumber.add(deliveryNumber);
//            BigDecimal newDeliveryNumber = oldDeliveryNumber.subtract(deliveryNumber);
//            assetsDto.setStockNumber(newStockNumber);
//            assetsDto.setDeliveryNumber(newDeliveryNumber);
//            assetsService.update(assetsDto);
//            log.debug("资产-{}---->>>>保存前库存为：{}\t\t保存前出库数量：{}",assetsDto.getId(),newStockNumber ,newDeliveryNumber);
//        }
//        for (MaterialDeliveryDetailVo materialDeliveryDetailVo : materialDeliveryVo.getDtailList()) {
//            //原出库数量
//            BigDecimal deliveryNumber = materialDeliveryDetailVo.getDeliveryNumber();
//            AssetsDetailVo assetsDetailVo = assetsDetailService.get(materialDeliveryDetailVo.getAssetsDetailId());
//            AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo,AssetsDetailDto.class);
//            BigDecimal oldStockNumber = assetsDetailVo.getStockNumber();
//            BigDecimal oldDeliveryNumber = assetsDetailVo.getDeliveryNumber();
//            BigDecimal newStockNumber = oldStockNumber.add(deliveryNumber);
//            BigDecimal newDeliveryNumber = oldDeliveryNumber.subtract(deliveryNumber);
//            assetsDetailDto.setStockNumber(newStockNumber);
//            assetsDetailDto.setDeliveryNumber(newDeliveryNumber);
//            assetsDetailService.update(assetsDetailDto);
//            log.debug("资产详情-{}----->>>>保存前库存为：{}\t\t保存前出库数量：{}",assetsDetailDto.getId(),newStockNumber ,newDeliveryNumber);
//        }
        return Result.success();
    }

    /**
     * 流程保存后调用
     *      保存后根据当前数量，计算资产数值
     *      增加出库数量，减少库存数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result deliverySaveAfter(String processInstanceId) {
//        MaterialDeliveryVo materialDeliveryVo = this.getByProc(processInstanceId);
//        for (MaterialDeliveryAssetsVo materialDeliveryAssetsVo : materialDeliveryVo.getMaterialDeliveryAssetsList()) {
//            AssetsVo assetsVo = assetsService.get(materialDeliveryAssetsVo.getAssetsId());
//            AssetsDto assetsDto = dozerMapper.map(assetsVo,AssetsDto.class);
//            BigDecimal oldStockNumber = assetsVo.getStockNumber();
//            BigDecimal oldDeliveryNumber = assetsVo.getDeliveryNumber();
//            BigDecimal newStockNumber = oldStockNumber.subtract(materialDeliveryAssetsVo.getDeliveryNumber());
//            BigDecimal newDeliveryNumber = oldDeliveryNumber.add(materialDeliveryAssetsVo.getDeliveryNumber());
//            assetsDto.setStockNumber(newStockNumber);
//            assetsDto.setDeliveryNumber(newDeliveryNumber);
//            assetsService.update(assetsDto);
//            log.debug("资产-{}---->>>>保存后库存为：{}\t\t保存后出库数量：{}",assetsDto.getId(),newStockNumber ,newDeliveryNumber);
//        }
//        for (MaterialDeliveryDetailVo materialDeliveryDetailVo : materialDeliveryVo.getMaterialDeliveryDetailList()) {
//            AssetsDetailVo assetsDetailVo = assetsDetailService.get(materialDeliveryDetailVo.getAssetsDetailId());
//            AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo,AssetsDetailDto.class);
//            BigDecimal oldStockNumber = assetsDetailVo.getStockNumber();
//            BigDecimal oldDeliveryNumber = assetsDetailVo.getDeliveryNumber();
//            BigDecimal newStockNumber = oldStockNumber.subtract(materialDeliveryDetailVo.getDeliveryNumber());
//            BigDecimal newDeliveryNumber = oldDeliveryNumber.add(materialDeliveryDetailVo.getDeliveryNumber());
//            assetsDetailDto.setStockNumber(newStockNumber);
//            assetsDetailDto.setDeliveryNumber(newDeliveryNumber);
//            assetsDetailService.update(assetsDetailDto);
//            log.debug("资产详情-{}----->>>>保存后库存为：{}\t\t保存后出库数量：{}",assetsDetailDto.getId(),newStockNumber ,newDeliveryNumber);
//        }
        return Result.success();
    }

    /**
     * 出库流程完成后
     *      生成资产出库详情数据结果表
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result deliveryAuditSuccess(String processInstanceId) {
//        MaterialDeliveryVo materialDeliveryVo = this.getByProc(processInstanceId);
//        List<MaterialDeliveryDetailVo> deliveryDetails = materialDeliveryVo.getMaterialDeliveryDetailList();
//        deliveryDetails.forEach(deliveryDetail -> {
//            AssetsDetailVo assetsDetailVo = assetsDetailService.get(deliveryDetail.getAssetsDetailId());
//            if(assetsDetailVo != null){
//                AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo,AssetsDetailDto.class);
//                daybookService.log(materialDeliveryVo.getId(),materialDeliveryVo.getCamundaProcinsId(),assetsDetailDto, DayBookType.DELIVERY);
//            }
//        });
//
//        //通过资产出库详情生成 资产出库结果数据
//        List<MaterialDeliveryResultDto> materialDeliveryResultDtos = DozerUtils.mapList(dozerMapper,deliveryDetails,MaterialDeliveryResultDto.class);
//        //补充结果表字段
//        materialDeliveryResultDtos.forEach(resultDto->{
//            resultDto.setDeliveryCode(materialDeliveryVo.getCode());
//            resultDto.setDeliveryDetailId(resultDto.getId());
//            resultDto.setWithdrawalNumber(BigDecimal.ZERO);
//            resultDto.setNotWithdrawalNumber(resultDto.getDeliveryNumber());
//            resultDto.setId(null);
//        });
//        materialDeliveryResultService.saveList(materialDeliveryResultDtos);
        return Result.success();
    }

}
