package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.biz.asset.dto.MaterialStorageDetailDto;
import com.netwisd.biz.asset.dto.MaterialStorageDto;
import com.netwisd.biz.asset.entity.MaterialStorage;
import com.netwisd.biz.asset.entity.MaterialStorageDetail;
import com.netwisd.biz.asset.mapper.MaterialStorageMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.biz.asset.vo.AssetsVo;
import com.netwisd.biz.asset.vo.MaterialStorageDetailVo;
import com.netwisd.biz.asset.vo.MaterialStorageVo;
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
 * @Description 资产入库管理 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:44:47
 */
@Service
@Slf4j
public class MaterialStorageServiceImpl extends BatchServiceImpl<MaterialStorageMapper, MaterialStorage> implements MaterialStorageService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialStorageMapper materialStorageMapper;

    @Autowired
    private MaterialStorageDetailService materialStorageDetailService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private DaybookService daybookService;

    /**
    * 单表简单查询操作
    * @param materialStorageDto
    * @return
    */
    @Override
    public Page list(MaterialStorageDto materialStorageDto) {
        LambdaQueryWrapper<MaterialStorage> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialStorage> page = materialStorageMapper.selectPage(materialStorageDto.getPage(),queryWrapper);
        Page<MaterialStorageVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialStorageVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialStorageDto
    * @return
    */
    @Override
    public Page lists(MaterialStorageDto materialStorageDto) {
        Page<MaterialStorageVo> pageVo = materialStorageMapper.getPageList(materialStorageDto.getPage(),materialStorageDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialStorageVo get(Long id) {
        MaterialStorage materialStorage = super.getById(id);
        MaterialStorageVo materialStorageVo = null;
        if(materialStorage !=null){
            materialStorageVo = dozerMapper.map(materialStorage,MaterialStorageVo.class);
            //根据id获取materialStorageDetailVoList
            List<MaterialStorageDetailVo> materialStorageDetailVoList = materialStorageDetailService.getByFkIdVo(id);
            //设置materialStorageVo，以便构建其对应的子表数据
            materialStorageVo.setMaterialStorageDetailList(materialStorageDetailVoList);
        }
        return materialStorageVo;
    }

    /**
    * 保存实体
    * @param materialStorageDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialStorageDto materialStorageDto) {
        //获取申请编号
        if(StringUtils.isBlank(materialStorageDto.getCode())){
            materialStorageDto.setCode(getMaxCode());
        }
        MaterialStorage materialStorage = dozerMapper.map(materialStorageDto,MaterialStorage.class);
        super.save(materialStorage);
        saveSubList(materialStorageDto);
    }

    /**
     * 获取入库编号
     * @return
     */
    private String getMaxCode(){
        String maxCode = materialStorageMapper.getMaxCode();
        String applyCode = "RZSQ-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"-";
        if(StringUtils.isBlank(maxCode)){
            applyCode += "0001";
        }else {
            DecimalFormat df = new DecimalFormat("0000");
            applyCode += df.format(Long.valueOf(maxCode)+1);
        }
        log.debug("资产入账申请编号===>{}",applyCode);
        return applyCode;
    }

    /**
     * 保存集合
     * @param materialStorageDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialStorageDto> materialStorageDtos){
        materialStorageDtos.forEach(materialStorageDto -> {
            //获取申请编号
            if(StringUtils.isBlank(materialStorageDto.getCode())){
                materialStorageDto.setCode(getMaxCode());
            }
        });
        List<MaterialStorage> MaterialStorages = DozerUtils.mapList(dozerMapper, materialStorageDtos, MaterialStorage.class);
        super.saveBatch(MaterialStorages);
        for (MaterialStorageDto materialStorageDto : materialStorageDtos){
            saveSubList(materialStorageDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialStorageDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialStorageDto materialStorageDto){
        //获取其子表集合
        List<MaterialStorageDetailDto> materialStorageDetailDtoList = materialStorageDto.getMaterialStorageDetailList();
        if(materialStorageDetailDtoList != null && !materialStorageDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            materialStorageDetailDtoList.forEach(materialStorageDetailDto -> {
                materialStorageDetailDto.setStorageId(materialStorageDto.getId());
            });
            //调用保存子表的集合方法
            materialStorageDetailService.saveList(materialStorageDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param materialStorageDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialStorageDto materialStorageDto) {
        materialStorageDto.setUpdateTime(LocalDateTime.now());
        MaterialStorage materialStorage = dozerMapper.map(materialStorageDto,MaterialStorage.class);
        super.updateById(materialStorage);
        updateSub(materialStorageDto);
    }

    /**
    * 修改子类实体
    * @param materialStorageDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialStorageDto materialStorageDto) {
        List<MaterialStorageDetailDto> materialStorageDetailDtoList = materialStorageDto.getMaterialStorageDetailList();
        if(materialStorageDetailDtoList != null && !materialStorageDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialStorageDetail> materialStorageDetailListQueryWrapper = new LambdaQueryWrapper<>();
            //根据主实体DTO映射其子表的关联键为其赋值
            materialStorageDetailDtoList.forEach(materialStorageDetailDto -> {
                materialStorageDetailDto.setStorageId(materialStorageDto.getId());
            });
            List<MaterialStorageDetail> materialStorageDetails = DozerUtils.mapList(dozerMapper, materialStorageDetailDtoList, MaterialStorageDetail.class);
            //调用更新方法
            materialStorageDetailService.saveOrUpdateOrDeleteBatch(materialStorageDetailListQueryWrapper,materialStorageDetails,materialStorageDetails.size());
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
        materialStorageDetailService.deleteByFkId(id);
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
    public List<MaterialStorageVo> getByFkIdVo(Long id){
        return null;
    }

    /**
     * 流程删除--删除业务数据
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        LambdaQueryWrapper<MaterialStorage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialStorage::getCamundaProcinsId,procInstId);
        MaterialStorage materialStorage = materialStorageMapper.selectOne(queryWrapper);
        if(materialStorage !=null){
            this.delete(materialStorage.getId());
        }
    }

    @Override
    public MaterialStorageVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaterialStorage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialStorage::getCamundaProcinsId,procInstId);
        MaterialStorage materialStorage = materialStorageMapper.selectOne(queryWrapper);
        MaterialStorageVo materialStorageVo = null;
        if(materialStorage !=null){
            materialStorageVo = dozerMapper.map(materialStorage,MaterialStorageVo.class);
            //根据id获取maintStorageDetailVoList
            List<MaterialStorageDetailVo> maintStorageDetailVoList = materialStorageDetailService.getByFkIdVo(materialStorage.getId());
            //设置maintStorageVo，以便构建其对应的子表数据
            materialStorageVo.setMaterialStorageDetailList(maintStorageDetailVoList);
        }
        return materialStorageVo;
    }

    /**
     * 入库流程保存前方法
     *      减少入库数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result storageSaveBefore(String processInstanceId) {
//        MaterialStorageVo materialStorageVo = this.getByProc(processInstanceId);
//        for (MaterialStorageDetailVo materialStorageDetailVo: materialStorageVo.getMaterialStorageDetailList()) {
//            //此次入库数量
//            BigDecimal storageNumber = materialStorageDetailVo.getAssetsNumber();
//            log.debug("storageSaveBefore ---> 此次入库数量：{}",storageNumber);
//
//            //资产信息
//            AssetsDto assetsDto = dozerMapper.map(assetsService.get(materialStorageDetailVo.getAssetsId()),AssetsDto.class);
//            //资产，原有入库数量
//            BigDecimal oldStorageNumber1 = assetsDto.getStorageNumber();
//            //资产，更新后入库数量
//            BigDecimal newStoragenUmber1 = oldStorageNumber1.subtract(storageNumber);
//            log.debug("storageSaveBefore ---> 资产：{},更新前入库数量：{},更新后入库数量：{}",assetsDto.getId(),oldStorageNumber1,newStoragenUmber1);
//            //更新资产入库数量
//            assetsDto.setStorageNumber(newStoragenUmber1);
//            assetsService.update(assetsDto);
//
//            //资产明细信息
//            AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailService.get(materialStorageDetailVo.getAssetsDetailId()),AssetsDetailDto.class);
//            //资产明细，原有入库数量
//            BigDecimal oldStorageNumber2 = assetsDetailDto.getStorageNumber();
//            //资产明细，更新后入库数量
//            BigDecimal newStoragenUmber2 = oldStorageNumber2.subtract(storageNumber);
//            log.debug("storageSaveBefore --->资产明细：{},更新前入库数量：{},更新后入库数量：{}",assetsDetailDto.getId(),oldStorageNumber2,newStoragenUmber2);
//            //更新资产明细入库数量
//            assetsDetailDto.setStorageNumber(newStoragenUmber2);
//            assetsDetailService.update(assetsDetailDto);
//        }
        return Result.success();
    }

    /**
     * 入库流程保存前方法
     *      增加入库数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result storageSaveAfter(String processInstanceId) {
//        MaterialStorageVo materialStorageVo = this.getByProc(processInstanceId);
//        for (MaterialStorageDetailVo materialStorageDetailVo: materialStorageVo.getMaterialStorageDetailList()) {
//            //此次入库数量
//            BigDecimal storageNumber = materialStorageDetailVo.getAssetsNumber();
//            log.debug("storageSaveAfter ---> 此次入库数量：{}",storageNumber);
//
//            //资产信息
//            AssetsDto assetsDto = dozerMapper.map(assetsService.get(materialStorageDetailVo.getAssetsId()),AssetsDto.class);
//            //资产，原有入库数量
//            BigDecimal oldStorageNumber1 = assetsDto.getStorageNumber();
//            //资产，更新后入库数量
//            BigDecimal newStoragenUmber1 = oldStorageNumber1.add(storageNumber);
//            log.debug("storageSaveBefore ---> 资产：{},更新前入库数量：{},更新后入库数量：{}",assetsDto.getId(),oldStorageNumber1,newStoragenUmber1);
//            //更新资产入库数量
//            assetsDto.setStorageNumber(newStoragenUmber1);
//            assetsService.update(assetsDto);
//
//            //资产明细信息
//            AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailService.get(materialStorageDetailVo.getAssetsDetailId()),AssetsDetailDto.class);
//            //资产明细，原有入库数量
//            BigDecimal oldStorageNumber2 = assetsDetailDto.getStorageNumber();
//            //资产明细，更新后入库数量
//            BigDecimal newStoragenUmber2 = oldStorageNumber2.add(storageNumber);
//            log.debug("storageSaveBefore --->资产明细：{},更新前入库数量：{},更新后入库数量：{}",assetsDetailDto.getId(),oldStorageNumber2,newStoragenUmber2);
//            //更新资产明细入库数量
//            assetsDetailDto.setStorageNumber(newStoragenUmber2);
//            assetsDetailService.update(assetsDetailDto);
//        }
        return Result.success();
    }

    /**
     * 流程完成后调用
     *      生成资产台账和资产明细
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result storageAuditSuccess(String processInstanceId) {
        MaterialStorageVo storageVo = this.getByProc(processInstanceId);
        List<MaterialStorageDetailVo> storageDetailVos = storageVo.getMaterialStorageDetailList();
        if(storageDetailVos.size()>0){
            for(MaterialStorageDetailVo storageDetailVo : storageDetailVos){
                AssetsVo assetsVo = assetsService.get(storageDetailVo.getAssetsId());
                if(assetsVo!=null) {
                    AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);

                    BigDecimal oldEntryNumber = assetsDto.getEntryNumber();
                    BigDecimal entryNumber = storageDetailVo.getAssetsNumber();
                    BigDecimal newEntryNumber = oldEntryNumber.add(entryNumber);
                    assetsDto.setEntryNumber(newEntryNumber);
                    assetsService.save(assetsDto);
                    log.debug("资产台账:{},现有入账数量:->{}", assetsDto.getId(), newEntryNumber);
                }
                AssetsDetailVo assetsDetailVo = assetsDetailService.get(storageDetailVo.getAssetsDetailId());
                if(assetsDetailVo != null){
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                    BigDecimal oldEntryNumber = assetsDetailDto.getEntryNumber();
                    BigDecimal entryNumber = storageDetailVo.getAssetsNumber();
                    BigDecimal newEntryNumber = oldEntryNumber.add(entryNumber);
                    assetsDetailDto.setEntryNumber(newEntryNumber);
                    assetsDetailService.save(assetsDetailDto);
                    log.debug("资产明细:{},现有入账数量:->{}", assetsDetailDto.getId(), newEntryNumber);

                    daybookService.log(storageVo.getId(), storageVo.getCamundaProcinsId(), assetsDetailDto, DayBookType.ENTRY);
                }
            }
        }
        return Result.success();
    }

    @Override
    public Page<AssetsDetailVo> getAssetsDetails(AssetsDetailDto assetsDetailDto) {
        return null;
    }
}
