package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.MaterialRefund;
import com.netwisd.biz.asset.entity.MaterialRefundDetail;
import com.netwisd.biz.asset.mapper.MaterialRefundMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 资产退还 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
@Service
@Slf4j
public class MaterialRefundServiceImpl extends BatchServiceImpl<MaterialRefundMapper, MaterialRefund> implements MaterialRefundService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialRefundMapper materialRefundMapper;

    @Autowired
    private MaterialRefundDetailService materialRefundDetailService;

    @Autowired
    private MaterialAcceptResultService materialAcceptResultService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private DaybookService daybookService;

    /**
    * 单表简单查询操作
    * @param materialRefundDto
    * @return
    */
    @Override
    public Page list(MaterialRefundDto materialRefundDto) {
        LambdaQueryWrapper<MaterialRefund> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialRefund> page = materialRefundMapper.selectPage(materialRefundDto.getPage(),queryWrapper);
        Page<MaterialRefundVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialRefundVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialRefundDto
    * @return
    */
    @Override
    public Page lists(MaterialRefundDto materialRefundDto) {
        Page<MaterialRefundVo> pageVo = materialRefundMapper.getPageList(materialRefundDto.getPage(),materialRefundDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialRefundVo get(Long id) {
        MaterialRefund materialRefund = super.getById(id);
        MaterialRefundVo materialRefundVo = null;
        if(materialRefund !=null){
            materialRefundVo = dozerMapper.map(materialRefund,MaterialRefundVo.class);
            //根据id获取materialRefundDetailVoList
            List<MaterialRefundDetailVo> materialRefundDetailVoList = materialRefundDetailService.getByFkIdVo(id);
            //设置materialRefundVo，以便构建其对应的子表数据
            materialRefundVo.setMaterialRefundDetailList(materialRefundDetailVoList);
        }
        return materialRefundVo;
    }

    /**
    * 保存实体
    * @param materialRefundDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialRefundDto materialRefundDto) {
        //获取申请编号
        if(StringUtils.isBlank(materialRefundDto.getCode())){
            materialRefundDto.setCode(getMaxCode());
        }
        MaterialRefund materialRefund = dozerMapper.map(materialRefundDto,MaterialRefund.class);
        super.save(materialRefund);
        saveSubList(materialRefundDto);
    }

    /**
     * 获取入库编号
     * @return
     */
    private String getMaxCode(){
        String maxCode = materialRefundMapper.getMaxCode();
        String applyCode = "THSQ-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"-";
        if(StringUtils.isBlank(maxCode)){
            applyCode += "0001";
        }else {
            DecimalFormat df = new DecimalFormat("0000");
            applyCode += df.format(Long.valueOf(maxCode)+1);
        }
        log.debug("资产入库申请编号===>{}",applyCode);
        return applyCode;
    }

    /**
     * 保存集合
     * @param materialRefundDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialRefundDto> materialRefundDtos){
        materialRefundDtos.forEach(materialRefundDto -> {
            //获取申请编号
            if(StringUtils.isBlank(materialRefundDto.getCode())){
                materialRefundDto.setCode(getMaxCode());
            }
        });
        List<MaterialRefund> MaterialRefunds = DozerUtils.mapList(dozerMapper, materialRefundDtos, MaterialRefund.class);
        super.saveBatch(MaterialRefunds);
        for (MaterialRefundDto materialRefundDto : materialRefundDtos){
            saveSubList(materialRefundDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialRefundDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialRefundDto materialRefundDto){
        //获取其子表集合
        List<MaterialRefundDetailDto> materialRefundDetailDtoList = materialRefundDto.getMaterialRefundDetailList();
        if(materialRefundDetailDtoList != null && !materialRefundDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            materialRefundDetailDtoList.forEach(materialRefundDetailDto -> {
                materialRefundDetailDto.setRefundId(materialRefundDto.getId());
            });
            //调用保存子表的集合方法
            materialRefundDetailService.saveList(materialRefundDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param materialRefundDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialRefundDto materialRefundDto) {
        materialRefundDto.setUpdateTime(LocalDateTime.now());
        MaterialRefund materialRefund = dozerMapper.map(materialRefundDto,MaterialRefund.class);
        super.updateById(materialRefund);
        updateSub(materialRefundDto);
    }

    /**
    * 修改子类实体
    * @param materialRefundDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialRefundDto materialRefundDto) {
        List<MaterialRefundDetailDto> materialRefundDetailDtoList = materialRefundDto.getMaterialRefundDetailList();
        if(materialRefundDetailDtoList != null && !materialRefundDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialRefundDetail> materialRefundDetailListQueryWrapper = new LambdaQueryWrapper<>();
            //根据主实体DTO映射其子表的关联键为其赋值
            materialRefundDetailDtoList.forEach(materialRefundDetailDto -> {
                materialRefundDetailDto.setRefundId(materialRefundDto.getId());
            });
            List<MaterialRefundDetail> materialRefundDetails = DozerUtils.mapList(dozerMapper, materialRefundDetailDtoList, MaterialRefundDetail.class);
            //调用更新方法
            materialRefundDetailService.saveOrUpdateOrDeleteBatch(materialRefundDetailListQueryWrapper,materialRefundDetails,materialRefundDetails.size());
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
        materialRefundDetailService.deleteByFkId(id);
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
    public List<MaterialRefundVo> getByFkIdVo(Long id){
        return null;
    }

    /**
     * 根据流程实例id删除业务数据
     * @param procInstId
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        LambdaQueryWrapper<MaterialRefund> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialRefund::getCamundaProcinsId,procInstId);
        MaterialRefund materialRefund = materialRefundMapper.selectOne(queryWrapper);
        if(materialRefund != null){
            this.delete(materialRefund.getId());
        }
    }

    /**
     * 根据流程实例id获取数据
     * @param procInstId
     */
    @Override
    public MaterialRefundVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaterialRefund> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialRefund::getCamundaProcinsId,procInstId);
        MaterialRefund materialRefund = materialRefundMapper.selectOne(queryWrapper);
        MaterialRefundVo materialRefundVo = null;
        if(materialRefund != null){
            materialRefundVo = dozerMapper.map(materialRefund,MaterialRefundVo.class);
            //根据id获取materialRefundDetailVoList
            List<MaterialRefundDetailVo> materialRefundDetailVoList = materialRefundDetailService.getByFkIdVo(materialRefund.getId());
            //设置materialRefundVo，以便构建其对应的子表数据
            materialRefundVo.setMaterialRefundDetailList(materialRefundDetailVoList);
        }
        return materialRefundVo;
    }

    /**
     * 退还流程保存前方法
     *      修改领用详情结果表
     *      减资产退还数量，加资产未退还数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result refundSaveBefore(String processInstanceId) {
        MaterialRefundVo materialRefundVo = this.getByProc(processInstanceId);
        List<MaterialRefundDetailVo> materialRefundDetailVos = materialRefundVo.getMaterialRefundDetailList();
        boolean result = true;
        for (MaterialRefundDetailVo materialRefundDetailVo: materialRefundDetailVos) {
            MaterialAcceptResultVo materialAcceptResultVo = materialAcceptResultService.get(materialRefundDetailVo.getAssetsAcceptId());
            MaterialAcceptResultDto materialAcceptResultDto = dozerMapper.map(materialAcceptResultVo,MaterialAcceptResultDto.class);
            //此次修改数量
            BigDecimal refundNumber = materialRefundDetailVo.getRefundNumber();
            //原退还数量
            BigDecimal oldRefundNumber = materialAcceptResultVo.getRefundNumber();
            //原未退还数量
            BigDecimal oldNotRefundNumber = materialAcceptResultVo.getNotRefundNumber();
            //修改后退还数量
            BigDecimal newRefundNumber = oldRefundNumber.subtract(refundNumber);
            //修改后未退还数量
            BigDecimal newNotRefundNumber = oldNotRefundNumber.add(refundNumber);

            materialAcceptResultDto.setRefundNumber(newRefundNumber);
            materialAcceptResultDto.setNotRefundNumber(newNotRefundNumber);
            result = materialAcceptResultService.update(materialAcceptResultDto);
            log.debug("资产出库详情-{}----->>>>保存前退还数量为：{}\t\t保存前未退还库数量：{}",
                    materialAcceptResultDto.getId(),newRefundNumber ,newNotRefundNumber);
        }
        return Result.success(result);
    }

    /**
     * 退库流程保存后方法
     *      修改领用详情结果表
     *      加资产退还数量，减资产未退还数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result refundSaveAfter(String processInstanceId) {
        MaterialRefundVo materialRefundVo = this.getByProc(processInstanceId);
        List<MaterialRefundDetailVo> materialRefundDetailVos = materialRefundVo.getMaterialRefundDetailList();
        boolean result = true;
        for (MaterialRefundDetailVo materialRefundDetailVo: materialRefundDetailVos) {
            MaterialAcceptResultVo materialAcceptResultVo = materialAcceptResultService.get(materialRefundDetailVo.getAssetsAcceptId());
            MaterialAcceptResultDto materialAcceptResultDto = dozerMapper.map(materialAcceptResultVo,MaterialAcceptResultDto.class);
            //此次修改数量
            BigDecimal refundNumber = materialRefundDetailVo.getRefundNumber();
            //原退还数量
            BigDecimal oldRefundNumber = materialAcceptResultVo.getRefundNumber();
            //原未退还数量
            BigDecimal oldNotRefundNumber = materialAcceptResultVo.getNotRefundNumber();
            //修改后退还数量
            BigDecimal newRefundNumber = oldRefundNumber.add(refundNumber);
            //修改后未退还数量
            BigDecimal newNotRefundNumber = oldNotRefundNumber.subtract(refundNumber);

            materialAcceptResultDto.setRefundNumber(newRefundNumber);
            materialAcceptResultDto.setNotRefundNumber(newNotRefundNumber);
            result = materialAcceptResultService.update(materialAcceptResultDto);
            log.debug("资产出库详情-{}----->>>>保存后退还数量为：{}\t\t保存后未退还库数量：{}",
                    materialAcceptResultDto.getId(),newRefundNumber ,newNotRefundNumber);
        }
        return Result.success(result);
    }

    /**
     * 退还流程完成后
     *      修改资产信息、资产明细信息
     *      加资产可用数量，减资产领用数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result refundAuditSuccess(String processInstanceId) {
        MaterialRefundVo materialRefundVo = this.getByProc(processInstanceId);
        List<MaterialRefundDetailVo> materialRefundDetailVos = materialRefundVo.getMaterialRefundDetailList();
        boolean result = true;
        for (MaterialRefundDetailVo materialRefundDetailVo: materialRefundDetailVos) {
            //此次修改数量
            BigDecimal refundNumber = materialRefundDetailVo.getRefundNumber();

            AssetsVo assetsVo = assetsService.get(materialRefundDetailVo.getAssetsId());
            if(assetsVo != null) {
                AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                //原资产可用数量
                BigDecimal oldUsableNumber = assetsDto.getUsableNumber();
                //原资产出库数量
                BigDecimal oldAcceptNumber = assetsDto.getAcceptNumber();
                //修改后资产库存数量
                BigDecimal newUsableNumber = oldUsableNumber.add(refundNumber);
                //修改后资产出库数量
                BigDecimal newAcceptNumber = oldAcceptNumber.subtract(refundNumber);

                assetsDto.setUsableNumber(newUsableNumber);
                assetsDto.setAcceptNumber(newAcceptNumber);
                assetsService.update(assetsDto);
                log.debug("资产:{},before----->>>>可用数量为：{},领用数量：{}",
                        assetsDto.getId(), oldUsableNumber, oldAcceptNumber);
                log.debug("资产:{},after----->>>>可用数量为：{},领用数量：{}",
                        assetsDto.getId(), newUsableNumber, newAcceptNumber);
            }
            AssetsDetailVo assetsDetailVo = assetsDetailService.get(materialRefundDetailVo.getAssetsDetailId());
            if(assetsDetailVo != null) {
                AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                //原资产可用数量
                BigDecimal oldUsableNumber = assetsDetailDto.getUsableNumber();
                //原资产出库数量
                BigDecimal oldAcceptNumber = assetsDetailDto.getAcceptNumber();
                //修改后资产库存数量
                BigDecimal newUsableNumber = oldUsableNumber.add(refundNumber);
                //修改后资产出库数量
                BigDecimal newAcceptNumber = oldAcceptNumber.subtract(refundNumber);

                assetsDetailDto.setUsableNumber(newUsableNumber);
                assetsDetailDto.setAcceptNumber(newAcceptNumber);

                assetsDetailService.update(assetsDetailDto);
                log.debug("资产详情:{},before----->>>>库存数量为：{},领用库数量：{}",
                        assetsDetailDto.getId(), oldUsableNumber, oldAcceptNumber);
                log.debug("资产详情:{},after----->>>>库存数量为：{},领用库数量：{}",
                        assetsDetailDto.getId(), newUsableNumber, newAcceptNumber);

                daybookService.log(materialRefundVo.getId(), materialRefundVo.getCamundaProcinsId(), assetsDetailDto, DayBookType.REFUND);
            }
        }
        return Result.success();
    }
}
