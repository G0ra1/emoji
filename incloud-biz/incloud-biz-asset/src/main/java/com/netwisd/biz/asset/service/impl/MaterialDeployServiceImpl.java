package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaterialDeployMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.exception.IncloudException;
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
import java.util.Optional;

/**
 * @Description 资产调配 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-06 10:35:24
 */
@Service
@Slf4j
public class MaterialDeployServiceImpl extends BatchServiceImpl<MaterialDeployMapper, MaterialDeploy> implements MaterialDeployService {
    @Autowired
    private Mapper dozerMapper;

    //@Autowired
    //private MaterialDeployMapper materialDeployMapper;

    @Autowired
    private MaterialDeployDetailService materialDeployDetailService;

    @Autowired
    private MaterialDeployResultService materialDeployResultService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private ViewerService viewerService;

    @Autowired
    private AssetsService assetsService;

    /**
     * 单表简单查询操作
     * @param materialDeployDto
     * @return
     */
    @Override
    public Page<MaterialDeployVo> page(MaterialDeployDto materialDeployDto) {
        LambdaQueryWrapper<MaterialDeploy> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialDeployVo> pageVo = DozerUtils.mapPage(dozerMapper, super.page(materialDeployDto.getPage(),queryWrapper), MaterialDeployVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param materialDeployDto
     * @return
     */
    @Override
    public List<MaterialDeployVo> list(MaterialDeployDto materialDeployDto) {
        LambdaQueryWrapper<MaterialDeploy> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        List<MaterialDeployVo> materialDeployVos = DozerUtils.mapList(dozerMapper, super.list(queryWrapper), MaterialDeployVo.class);
        log.debug("查询条数:"+materialDeployVos.size());
        return materialDeployVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDeployVo get(Long id) {
        MaterialDeploy materialDeploy = Optional.ofNullable(super.getById(id)).orElse(null);
        MaterialDeployVo materialDeployVo = ObjectUtil.isNotEmpty(materialDeploy) ? dozerMapper.map(materialDeploy,MaterialDeployVo.class) : null;
        //根据id获取materialDeployDetailVoList
        List<MaterialDeployDetailVo> materialDeployDetailVoList = materialDeployDetailService.getByDeployId(id);
        //设置materialDeployVo，以便构建其对应的子表数据
        materialDeployVo.setDetailList(materialDeployDetailVoList);
        return materialDeployVo;
    }

    /**
    * 保存实体
    * @param materialDeployDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialDeployDto materialDeployDto) {
        //获取申请编号
        if(StringUtils.isBlank(materialDeployDto.getCode())){
            getMaxCode(materialDeployDto);
        }
        return super.save(dozerMapper.map(materialDeployDto,MaterialDeploy.class)) &&  saveSubList(materialDeployDto);
    }

    /**
     * 获取申请编号
     * @return
     */
    private void getMaxCode(MaterialDeployDto materialDeployDto){
        //获取申请编号
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(materialDeployDto,HashMap.class);
        String code = dictClient.getRuleValue("material_deploy","code",entityMap);
        log.debug("规则值:{}",code);
        if(StringUtils.isNotBlank(code)){
            materialDeployDto.setCode(code);
        }
    }

    /**
     * 保存集合
     * @param materialDeployDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<MaterialDeployDto> materialDeployDtos){
        Boolean flag = true;
        List<MaterialDeploy> MaterialDeploys = DozerUtils.mapList(dozerMapper, materialDeployDtos, MaterialDeploy.class);
        for (MaterialDeployDto materialDeployDto : materialDeployDtos){
            //获取申请编号
            if(StringUtils.isBlank(materialDeployDto.getCode())){
                getMaxCode(materialDeployDto);
            }
            flag = saveSubList(materialDeployDto);
        }
        return super.saveBatch(MaterialDeploys) && flag;
    }

    /**
     * 保存子表集合
     * @param materialDeployDto
     * @return
     */
    @Transactional
    Boolean saveSubList(MaterialDeployDto materialDeployDto){
        Boolean flag = true;
        //获取其子表集合
        List<MaterialDeployDetailDto> detailDtoList = materialDeployDto.getDetailList();
        if(detailDtoList != null && !detailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(materialDeployDto,detailDtoList);
            detailDtoList.forEach(detailDto -> {
                detailDto.setDeployId(materialDeployDto.getId());
            });
            //调用保存子表的集合方法
            flag = materialDeployDetailService.saveList(detailDtoList);
        }
        return flag;
    }

    /**
     * 修改实体
     * @param materialDeployDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MaterialDeployDto materialDeployDto) {
        materialDeployDto.setUpdateTime(LocalDateTime.now());
        return super.updateById(dozerMapper.map(materialDeployDto,MaterialDeploy.class)) && updateSub(materialDeployDto);
    }

    /**
    * 修改子类实体
    * @param materialDeployDto
    * @return
    */
    @Transactional
    @Override
    public Boolean updateSub(MaterialDeployDto materialDeployDto) {
        Boolean flag = true;
        List<MaterialDeployDetailDto> materialDeployDetailDtoList = materialDeployDto.getDetailList();
        if(materialDeployDetailDtoList != null && !materialDeployDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialDeployDetail> materialDeployDetailListQueryWrapper = new LambdaQueryWrapper<>();
            materialDeployDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialDeployDto.getId()),MaterialDeployDetail::getDeployId,materialDeployDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(materialDeployDto,materialDeployDetailDtoList);
            List<MaterialDeployDetail> materialDeployDetails = DozerUtils.mapList(dozerMapper, materialDeployDetailDtoList, MaterialDeployDetail.class);
            materialDeployDetails.forEach(detail -> {
                detail.setDeployId(materialDeployDto.getId());
            });
            //调用更新方法
            flag = materialDeployDetailService.saveOrUpdateOrDeleteBatch(materialDeployDetailListQueryWrapper,materialDeployDetails,materialDeployDetails.size());
            //如果子可能还有子，那么遍历子，然后调用其子方法的updateSub
        }
        return flag;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        return super.removeById(id) && materialDeployDetailService.deleteByDeployId(id);
    }

    @Override
    public MaterialDeployVo getByProcId(String procInstId) {
        List<MaterialDeploy> deployList = super.list(Wrappers.<MaterialDeploy>lambdaQuery().eq(MaterialDeploy::getCamundaProcinsId, procInstId));
        MaterialDeployVo deployVo = null;
        if(CollectionUtils.isNotEmpty(deployList) && deployList.size()>0){
            deployVo = dozerMapper.map(deployList.get(0),MaterialDeployVo.class);
            List<MaterialDeployDetailVo> detailVos = materialDeployDetailService.getByDeployId(deployVo.getId());
            deployVo.setDetailList(detailVos);
        }
        log.debug("查询成功");
        return deployVo;
    }

    @Override
    public Boolean deleteByProc(String procInstId) {
        MaterialDeployVo deployVo = this.getByProcId(procInstId);
        return this.delete(deployVo.getId())
                &&  materialDeployDetailService.remove(Wrappers.<MaterialDeployDetail>lambdaQuery().eq(MaterialDeployDetail::getDeployId,deployVo.getId()));
    }

    @Override
    public MaterialDeployVo procSaveOrUpdate(MaterialDeployDto materialDeployDto) {
        //获取申请编号
        if(StringUtils.isBlank(materialDeployDto.getCode())){
            getMaxCode(materialDeployDto);
        }
        if(ObjectUtil.isNotEmpty(materialDeployDto.getId())){
            materialDeployDetailService.deleteByDeployId(materialDeployDto.getId());
        }

        MaterialDeploy materialDeploy = dozerMapper.map(materialDeployDto,MaterialDeploy.class);
        super.saveOrUpdate(materialDeploy);

        List<MaterialDeployDetailDto> detailDtoList = materialDeployDto.getDetailList();
        List<MaterialDeployDetail> detailList = DozerUtils.mapList(dozerMapper,detailDtoList,MaterialDeployDetail.class);
        if(CollectionUtils.isNotEmpty(detailList)){
            detailList.forEach(detailDto -> {
                detailDto.setDeployId(materialDeployDto.getId());
            });
        }
        materialDeployDetailService.saveOrUpdateBatch(detailList);
        return this.getByProcId(materialDeploy.getCamundaProcinsId());
    }

    @Override
    @Transactional
    public Boolean procSaveBefore(String procInstId) {
        MaterialDeployVo deployVo = this.getByProcId(procInstId);
        Integer deployType = deployVo.getType();
        List<MaterialDeployDetailVo> detailVoList = deployVo.getDetailList();

        if(CollectionUtils.isNotEmpty(detailVoList) && detailVoList.size()>0){
            detailVoList.forEach(detailVo -> {
                //调配数量
                BigDecimal deployNumber = detailVo.getDeployNumber();

                AssetsVo assetsVo = assetsService.get(detailVo.getAssetsId());
                if(ObjectUtil.isNotEmpty(assetsVo)) {
                    AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                    //增加可用数量
                    BigDecimal oldUsableNumber = assetsDto.getUsableNumber();
                    oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
                    BigDecimal newUsableNumber = oldUsableNumber.add(deployNumber);
                    assetsDto.setUsableNumber(newUsableNumber);

//                    if(ObjectUtil.isNotEmpty(deployType) && deployType == DeployTypEnum.BORROW.getCode()){
//                        //增加借出数量
//                        BigDecimal oldUsableNumber = assetsDto.getUsableNumber();
//                        oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
//                        BigDecimal newUsableNumber = oldUsableNumber.subtract(deployNumber);
//                        assetsDto.setUsableNumber(newUsableNumber);
//                    }else if(ObjectUtil.isNotEmpty(deployType) && deployType == DeployTypEnum.TRANSFER.getCode()){
//                        //增加调拨数量
//                        BigDecimal oldUsableNumber = assetsDto.getUsableNumber();
//                        oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
//                        BigDecimal newUsableNumber = oldUsableNumber.subtract(deployNumber);
//                        assetsDto.setUsableNumber(newUsableNumber);
//                    }

                    assetsService.update(assetsDto);
                }
            });
        }

        return true;
    }

    @Override
    @Transactional
    public Boolean procSaveAfter(String procInstId) {
        MaterialDeployVo deployVo = this.getByProcId(procInstId);
        Integer deployType = deployVo.getType();
        List<MaterialDeployDetailVo> detailVoList = deployVo.getDetailList();

        if(CollectionUtils.isNotEmpty(detailVoList) && detailVoList.size()>0){
            detailVoList.forEach(detailVo -> {
                //调配数量
                BigDecimal deployNumber = detailVo.getDeployNumber();

                AssetsVo assetsVo = assetsService.get(detailVo.getAssetsId());
                if(ObjectUtil.isNotEmpty(assetsVo)) {
                    AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                    //减少可用数量
                    BigDecimal oldUsableNumber = assetsDto.getUsableNumber();
                    oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
                    BigDecimal newUsableNumber = oldUsableNumber.subtract(deployNumber);
                    assetsDto.setUsableNumber(newUsableNumber);
                    assetsService.update(assetsDto);
                }
            });
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean procAuditSuccess(String procInstId) {
        MaterialDeployVo deployVo = this.getByProcId(procInstId);
        Integer deployType = deployVo.getType();
        List<MaterialDeployDetailVo> detailVoList = deployVo.getDetailList();

        if(CollectionUtils.isNotEmpty(detailVoList) && detailVoList.size()>0){
            detailVoList.forEach(detailVo -> {
                //调配数量
                BigDecimal deployNumber = detailVo.getDeployNumber();

                MaterialDeployResultDto deployResultDto = dozerMapper.map(detailVo,MaterialDeployResultDto.class);
                deployResultDto.setId(null);
                deployResultDto.setDeployId(deployVo.getId());
                deployResultDto.setDeployCode(deployVo.getCode());
                deployResultDto.setDeployDetailId(detailVo.getId());
                deployResultDto.setType(deployVo.getType());
                deployResultDto.setTypeName(deployVo.getTypeName());
                deployResultDto.setDeliveryNumber(BigDecimal.ZERO);
                deployResultDto.setNotDeliveryNumber(deployNumber);
                deployResultDto.setDeliveryAmount(BigDecimal.ZERO);
                deployResultDto.setNotDeliveryAmount(detailVo.getAssetsSumAmount());

                deployResultDto.setApplyTime(deployVo.getApplyTime());
                deployResultDto.setApplyUserId(deployVo.getApplyUserId());
                deployResultDto.setApplyUserName(deployVo.getApplyUserName());
                deployResultDto.setApplyUserOrgId(deployVo.getApplyUserOrgId());
                deployResultDto.setApplyUserOrgName(deployVo.getApplyUserOrgName());
                deployResultDto.setApplyUserDeptId(deployVo.getApplyUserDeptId());
                deployResultDto.setApplyUserDeptName(deployVo.getApplyUserDeptName());
                deployResultDto.setDeployState(0);

                materialDeployResultService.save(deployResultDto);
//                AssetsVo assetsVo = assetsService.get(detailVo.getAssetsId());
//                if(ObjectUtil.isNotEmpty(assetsVo)) {

//                    AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
//
//                    if(ObjectUtil.isNotEmpty(deployType) && deployType == DeployTypEnum.BORROW.getCode()){
//                        //增加借出数量
//                        BigDecimal oldLendNumber = assetsDto.getLendNumber();
//                        oldLendNumber = ObjectUtil.isEmpty(oldLendNumber) ? BigDecimal.ZERO : oldLendNumber;
//                        BigDecimal newUsableNumber = oldLendNumber.add(deployNumber);
//                        assetsDto.setUsableNumber(newUsableNumber);
//                    }else if(ObjectUtil.isNotEmpty(deployType) && deployType == DeployTypEnum.TRANSFER.getCode()){
//                        //增加调拨数量
//                        BigDecimal oldTransferNumber = assetsDto.getTransferNumber();
//                        oldTransferNumber = ObjectUtil.isEmpty(oldTransferNumber) ? BigDecimal.ZERO : oldTransferNumber;
//                        BigDecimal newUsableNumber = oldTransferNumber.add(deployNumber);
//                        assetsDto.setUsableNumber(newUsableNumber);
//                    }
//                    assetsService.update(assetsDto);
//                }
            });
        }
        return true;
    }

    @Override
    public Page listForDelivery(MaterialDeployDto materialDeployDto) {
        Integer type = materialDeployDto.getType();
        if(ObjectUtil.isEmpty(type)){
            throw new IncloudException("请选择调配类型！");
        }
        Page pageVo = materialDeployDto.getPage();
        if(ObjectUtil.isNotEmpty(type)){
            LambdaQueryWrapper<MaterialDeploy> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MaterialDeploy::getType,type);
            queryWrapper.gt(MaterialDeploy::getNotDeliveryNumber, BigDecimal.ZERO);
            queryWrapper.gt(MaterialDeploy::getNotDeliveryAmount, BigDecimal.ZERO);
            pageVo = DozerUtils.mapPage(dozerMapper, super.page(materialDeployDto.getPage(),queryWrapper), MaterialDeployVo.class);
        }
        return pageVo;
    }

    @Override
    public Page getResultByDeploy(MaterialDeployResultDto materialDeployResultDto) {
        Long deployId = materialDeployResultDto.getId();
        if(ObjectUtil.isEmpty(deployId)){
            throw new IncloudException("请选择调配单！");
        }
        LambdaQueryWrapper<MaterialDeployResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialDeployResult::getDeployId,deployId);
        queryWrapper.gt(MaterialDeployResult::getNotDeliveryNumber,BigDecimal.ZERO);
        Page<MaterialDeployResult> page = materialDeployResultService.page(materialDeployResultDto.getPage(),queryWrapper);
        Page<MaterialDeployResultVo> pageVo = DozerUtils.mapPage(dozerMapper,page,MaterialDeployResultVo.class);
        //Page<MaterialDeployResultVo> pageVo = materialDeployResultService.getResultByDeploy(materialDeployResultDto);
        return pageVo;
    }

    @Override
    public Page getDetailByAssets(AssetsDto assetsDto) {
        Long assetsOrgId = assetsDto.getAssetOrgId();
        if (ObjectUtil.isEmpty(assetsOrgId)){
            throw new IncloudException("请先选择调配单位!");
        }
        Page pageVo = assetsDto.getPage();
        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.ASSETS.getCode());
        if (ObjectUtil.isNotEmpty(viewerVo)) {
            List<Long> orgList = viewerVo.getOrgList();
            if (orgList.contains(appUserId)){
                LambdaQueryWrapper<Assets> assetsQueryWrapper = new LambdaQueryWrapper<>();
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(assetsDto.getItemName()), Assets::getItemName,assetsDto.getItemName())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getAssetOrgId()),Assets::getAssetOrgId,assetsDto.getAssetOrgId())
                        .or()
                        .likeRight(ObjectUtil.isNotEmpty(assetsDto.getAssetOrgId()),Assets::getAssetOrgFullId,assetsDto.getAssetOrgId()+",")
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getAssetDeptId()),Assets::getAssetDeptId,assetsDto.getAssetDeptId())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getAssetOrgName()),Assets::getAssetOrgName,assetsDto.getAssetOrgName())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getAssetDeptName()),Assets::getAssetDeptName,assetsDto.getAssetDeptName())
                        .like(ObjectUtil.isNotEmpty(assetsDto.getMaterialQuality()),Assets::getMaterialQuality,assetsDto.getMaterialQuality())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getClassifyName()),Assets::getClassifyName,assetsDto.getClassifyName())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getClassifyCode()),Assets::getClassifyCode,assetsDto.getClassifyCode())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getItemCode()),Assets::getItemCode,assetsDto.getItemCode())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getItemType()),Assets::getItemType,assetsDto.getItemType());
                String  searchCondition= assetsDto.getSearchCondition();
                //全局模糊查询
                if(ObjectUtil.isNotEmpty(searchCondition)){
                    assetsQueryWrapper.like(Assets::getItemName,searchCondition)
                            .or()
                            .like(Assets::getItemCode,searchCondition)
                            .or()
                            .like(Assets::getApplyUserName,searchCondition)
                            .or()
                            .like(Assets::getAssetOrgId,searchCondition)
                            .or()
                            .like(Assets::getAssetDeptId,searchCondition)
                            .or()
                            .like(Assets::getMaterialQuality,searchCondition)
                            .or()
                            .like(Assets::getClassifyName,searchCondition)
                            .or()
                            .like(Assets::getClassifyCode,searchCondition);
                }
                Page<Assets> assetsPage = assetsService.page(assetsDto.getPage(), assetsQueryWrapper);
                pageVo = DozerUtils.mapPage(dozerMapper,assetsPage,AssetsVo.class);
            }
        }
        return pageVo;
    }
}
