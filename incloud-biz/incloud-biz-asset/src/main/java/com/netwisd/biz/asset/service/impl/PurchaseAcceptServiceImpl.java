package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.biz.asset.constants.AssetSource;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.constants.ItemTypeEnum;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.data.FieldVm;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.asset.mapper.PurchaseAcceptMapper;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

import com.netwisd.common.db.util.EntityListConvert;
import cn.hutool.core.util.ObjectUtil;

/**
 * @Description 物资购置验收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
@Service
@Slf4j
public class PurchaseAcceptServiceImpl extends BatchServiceImpl<PurchaseAcceptMapper, PurchaseAccept> implements PurchaseAcceptService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseAcceptMapper purchaseAcceptMapper;

    @Autowired
    private PurchaseAcceptDetailService purchaseAcceptDetailService;

    @Autowired
    private PurchaseAcceptAssetService purchaseAcceptAssetService;

    @Autowired
    private PurchaseApplyService purchaseApplyService;

    @Autowired
    private PurchaseApplyDetailService purchaseApplyDetailService;

    @Autowired
    private PurchaseRegisterService purchaseRegisterService;

    @Autowired
    private PurchaseRegisterAssetsService purchaseRegisterAssetsService;

    @Autowired
    private PurchaseRegisterDetailService purchaseRegisterDetailService;

    @Autowired
    private PurchaseRegisterResultService purchaseRegisterResultService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsSkuService assetsSkuService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private DaybookService daybookService;

    @Autowired
    private SuppliesService suppliesService;

    @Autowired
    private SuppliesSkuService suppliesSkuService;

    @Autowired
    private SuppliesDetailService suppliesDetailService;

    @Autowired
    private DaybookSuppliesService daybookSuppliesService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private ViewerService viewerService;

    /**
     * 单表简单查询操作
     *
     * @param purchaseAcceptDto
     * @return
     */
    @Override
    public Page list(PurchaseAcceptDto purchaseAcceptDto) {
        LambdaQueryWrapper<PurchaseAccept> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
//        queryWrapper.eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getApplyUserOrgId()),PurchaseAccept::getApplyUserOrgName,purchaseAcceptDto.getApplyUserOrgName())
//                .eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getApplyUserDeptId()),PurchaseAccept::getApplyUserDeptName,purchaseAcceptDto.getApplyUserDeptName())
//                .eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getApplyUserId()),PurchaseAccept::getApplyUserName,purchaseAcceptDto.getApplyUserName())
//                .eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getCode()),PurchaseAccept::getCode,purchaseAcceptDto.getCode())
//                .eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getPurchaseType()),PurchaseAccept::getPurchaseType,purchaseAcceptDto.getPurchaseType());
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(PurchaseAccept::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(PurchaseAccept::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(PurchaseAccept::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        queryWrapper.orderByDesc(PurchaseAccept::getApplyTime);
        //指定的查询字段
        String  searchCondition= purchaseAcceptDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(PurchaseAccept::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(PurchaseAccept::getRegisterCode,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getAssetSource,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getSumTotalAmount,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getSumTotalNumber,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getExplanation,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseAccept::getApplyTime,searchCondition));
            });
        }

        queryWrapper.eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getApplyUserOrgId()),PurchaseAccept::getApplyUserOrgName,purchaseAcceptDto.getApplyUserOrgName())
                .eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getApplyUserDeptId()),PurchaseAccept::getApplyUserDeptName,purchaseAcceptDto.getApplyUserDeptName())
                .eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getApplyUserName()),PurchaseAccept::getApplyUserName,purchaseAcceptDto.getApplyUserName())
                .eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getCode()),PurchaseAccept::getCode,purchaseAcceptDto.getCode());


        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        //查看人员查看范围
        ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.LIST.getCode());

        if (ObjectUtil.isNotEmpty(viewerVo)) {
            queryWrapper.and(q -> {
                q
                        //可看人员List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseAccept::getApplyUserId, viewerVo.getUserList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseAccept::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseAccept::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseAccept::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseAccept::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseAccept::getCreateUserParentOrgId, viewerVo.getOrgList()));

            });
        }



        Page<PurchaseAccept> page = purchaseAcceptMapper.selectPage(purchaseAcceptDto.getPage(), queryWrapper);
        Page<PurchaseAcceptVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseAcceptVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param purchaseAcceptDto
     * @return
     */
    @Override
    public Page lists(PurchaseAcceptDto purchaseAcceptDto) {
        Page<PurchaseAcceptVo> pageVo = purchaseAcceptMapper.getPageList(purchaseAcceptDto.getPage(), purchaseAcceptDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public PurchaseAcceptVo get(Long id) {
        PurchaseAccept purchaseAccept = super.getById(id);
        PurchaseAcceptVo purchaseAcceptVo = null;
        if (purchaseAccept != null) {
            purchaseAcceptVo = dozerMapper.map(purchaseAccept, PurchaseAcceptVo.class);

            //根据id获取purchaseAcceptAssetVoList
            List<PurchaseAcceptAssetVo> purchaseAcceptAssetVoList = purchaseAcceptAssetService.getByAcceptanceId(id);
            purchaseAcceptAssetVoList.forEach(assetsVo -> {
                List<PurchaseAcceptDetailVo> detailVos = purchaseAcceptDetailService.getByAcceptanceId(assetsVo.getId());
                assetsVo.setDetailList(detailVos);
            });
            //设置purchaseAcceptVo，以便构建其对应的子表数据
            purchaseAcceptVo.setAssetsList(purchaseAcceptAssetVoList);


            //根据id获取purchaseAcceptDetailVoList
            List<PurchaseAcceptDetailVo> purchaseAcceptDetailVoList = purchaseAcceptDetailService.getByAcceptanceId(id);
            //设置purchaseAcceptVo，以便构建其对应的子表数据
            purchaseAcceptVo.setDetailList(purchaseAcceptDetailVoList);
        }
        return purchaseAcceptVo;
    }

    /**
     * 保存实体
     *
     * @param purchaseAcceptDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PurchaseAcceptDto purchaseAcceptDto) {
        Boolean result = true, resultList = true;
        //获取申请编号
        if(StringUtils.isBlank(purchaseAcceptDto.getCode())){
            getMaxCode(purchaseAcceptDto);
        }
        PurchaseAccept purchaseAccept = dozerMapper.map(purchaseAcceptDto, PurchaseAccept.class);
        result = super.save(purchaseAccept);
        resultList = saveSubList(purchaseAcceptDto);
        return result && resultList;
    }

    /**
     * 获取申请编号
     * @return
     */
    private void getMaxCode(PurchaseAcceptDto purchaseAcceptDto){
        //获取申请编号
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(purchaseAcceptDto,HashMap.class);
        String code = dictClient.getRuleValue("purchase_accept","code",entityMap);
        log.debug("规则值:{}",code);
        if(StringUtils.isNotBlank(code)){
            purchaseAcceptDto.setCode(code);
        }
    }

    /**
     * 保存集合
     *
     * @param purchaseAcceptDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseAcceptDto> purchaseAcceptDtos) {
        Boolean result = true, resultList = true;
        List<PurchaseAccept> PurchaseAccepts = DozerUtils.mapList(dozerMapper, purchaseAcceptDtos, PurchaseAccept.class);
        result = super.saveBatch(PurchaseAccepts);
        for (PurchaseAcceptDto purchaseAcceptDto : purchaseAcceptDtos) {
            //获取申请编号
            if(StringUtils.isBlank(purchaseAcceptDto.getCode())){
                getMaxCode(purchaseAcceptDto);
            }
            resultList = saveSubList(purchaseAcceptDto);
        }
        return result && resultList;
    }

    /**
     * 保存子表集合
     *
     * @param purchaseAcceptDto
     * @return
     */
    @Transactional
    Boolean saveSubList(PurchaseAcceptDto purchaseAcceptDto) {
        Boolean resultAssets = true, resultDetail = true;
        //获取其子表集合
        List<PurchaseAcceptAssetDto> assetDtoList = purchaseAcceptDto.getAssetsList();
        List<PurchaseAcceptDetailDto> detailDtoList = purchaseAcceptDto.getDetailList();

        if (assetDtoList != null && !assetDtoList.isEmpty()) {
            //根据主实体DTO映射其子表的关联键为其赋值
            //根据主实体DTO映射其子表的关联键为其赋值
            assetDtoList.forEach(assetsDto -> {
                assetsDto.setAcceptanceId(purchaseAcceptDto.getId());
                assetsDto.setAcceptanceCode(purchaseAcceptDto.getCode());

                //和detailDtoList相反，只保存一个detailList
                if(CollectionUtils.isEmpty(detailDtoList)){
                    List<PurchaseAcceptDetailDto> detailDtos = assetsDto.getDetailList();
                    if(CollectionUtils.isNotEmpty(detailDtos)){
                        detailDtos.forEach(detailDto -> {
                            detailDto.setAcceptanceId(purchaseAcceptDto.getId());
                            detailDto.setAcceptanceCode(purchaseAcceptDto.getCode());
                            detailDto.setAcceptanceAssetsId(assetsDto.getId());
                        });
                    }
                }
            });
            //调用保存子表的集合方法
            resultAssets = purchaseAcceptAssetService.saveList(assetDtoList);
        }

        if (detailDtoList != null && !detailDtoList.isEmpty()) {
            //根据主实体DTO映射其子表的关联键为其赋值
            detailDtoList.forEach(detailDto -> {
                detailDto.setAcceptanceId(purchaseAcceptDto.getId());
                detailDto.setAcceptanceCode(purchaseAcceptDto.getCode());

                assetDtoList.forEach(assetsDto -> {
                    if(assetsDto.getRegisterResultId().compareTo(detailDto.getRegisterResultId())==0){
                        detailDto.setAcceptanceAssetsId(assetsDto.getId());
                    }
                });
            });
            //调用保存子表的集合方法
            resultDetail = purchaseAcceptDetailService.saveList(detailDtoList);
        }
        return resultAssets && resultDetail;
    }

    /**
     * 修改实体
     *
     * @param purchaseAcceptDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PurchaseAcceptDto purchaseAcceptDto) {
        purchaseAcceptDto.setUpdateTime(LocalDateTime.now());
        PurchaseAccept purchaseAccept = dozerMapper.map(purchaseAcceptDto, PurchaseAccept.class);
        return super.updateById(purchaseAccept) &&  updateSub(purchaseAcceptDto);

    }

    /**
     * 修改子类实体
     *
     * @param purchaseAcceptDto
     * @return
     */
    @Transactional
    @Override
    public Boolean updateSub(PurchaseAcceptDto purchaseAcceptDto) {
        Boolean resultAssets = true, resultDetail = true;
        List<PurchaseAcceptAssetDto> assetDtoList = purchaseAcceptDto.getAssetsList();
        if (assetDtoList != null && !assetDtoList.isEmpty()) {
            //根据主实体DTO映射其子表的关联键为其赋值
            //根据主实体DTO映射其子表的关联键为其赋值
            assetDtoList.forEach(assetDto -> {
                assetDto.setAcceptanceId(purchaseAcceptDto.getId());
                assetDto.setAcceptanceCode(purchaseAcceptDto.getCode());

                //登记数量、单价等、重新计算生成
                BigDecimal acceptanceNumber = assetDto.getAcceptanceNumber();
                BigDecimal acceptanceAmount = assetDto.getAcceptanceAmount();
                BigDecimal acceptanceTaxAmount = assetDto.getAcceptanceTaxAmount();
                BigDecimal acceptanceUntaxedAmount = assetDto.getAcceptanceUntaxedAmount();

                BigDecimal acceptanceSumAmount = acceptanceAmount.multiply(acceptanceNumber);
                BigDecimal acceptanceSumTaxAmount = acceptanceTaxAmount.multiply(acceptanceNumber);
                BigDecimal acceptanceSumUntaxedAmount = acceptanceUntaxedAmount.multiply(acceptanceNumber);

                assetDto.setAcceptanceSumAmount(acceptanceSumAmount);
                assetDto.setAcceptanceSumTaxAmount(acceptanceSumTaxAmount);
                assetDto.setAcceptanceSumUntaxedAmount(acceptanceSumUntaxedAmount);


            });
            List<PurchaseAcceptAsset> purchaseAcceptAssets = DozerUtils.mapList(dozerMapper, assetDtoList, PurchaseAcceptAsset.class);


            //执行更新
            LambdaQueryWrapper<PurchaseAcceptAsset> purchaseAcceptAssetListQueryWrapper = new LambdaQueryWrapper<>();
            purchaseAcceptAssetListQueryWrapper.eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getId()), PurchaseAcceptAsset::getAcceptanceId, purchaseAcceptDto.getId());
            //调用更新方法
            resultDetail = purchaseAcceptAssetService.saveOrUpdateOrDeleteBatch(purchaseAcceptAssetListQueryWrapper, purchaseAcceptAssets, purchaseAcceptAssets.size());

        }


        List<PurchaseAcceptDetailDto> detailDtoList = purchaseAcceptDto.getDetailList();
        if (detailDtoList != null && !detailDtoList.isEmpty()) {
            //根据主实体DTO映射其子表的关联键为其赋值
            detailDtoList.forEach(detailDto -> {
                detailDto.setAcceptanceId(purchaseAcceptDto.getId());
                detailDto.setAcceptanceCode(purchaseAcceptDto.getCode());

                //登记数量、单价等、重新计算生成
                BigDecimal acceptanceNumber = detailDto.getAcceptanceNumber();
                BigDecimal acceptanceAmount = detailDto.getAcceptanceAmount();
                BigDecimal acceptanceTaxAmount = detailDto.getAcceptanceTaxAmount();
                BigDecimal acceptanceUntaxedAmount = detailDto.getAcceptanceUntaxedAmount();

                BigDecimal acceptanceSumAmount = acceptanceAmount.multiply(acceptanceNumber);
                BigDecimal acceptanceSumTaxAmount = acceptanceTaxAmount.multiply(acceptanceNumber);
                BigDecimal acceptanceSumUntaxedAmount = acceptanceUntaxedAmount.multiply(acceptanceNumber);

                detailDto.setAcceptanceSumAmount(acceptanceSumAmount);
                detailDto.setAcceptanceSumTaxAmount(acceptanceSumTaxAmount);
                detailDto.setAcceptanceSumUntaxedAmount(acceptanceSumUntaxedAmount);

                assetDtoList.forEach(assetsDto -> {
                    if(assetsDto.getRegisterResultId().compareTo(detailDto.getRegisterResultId())==0){
                        detailDto.setAcceptanceAssetsId(assetsDto.getId());
                    }
                });
            });

            List<PurchaseAcceptDetail> purchaseAcceptDetails = DozerUtils.mapList(dozerMapper, detailDtoList, PurchaseAcceptDetail.class);

            //执行更新
            LambdaQueryWrapper<PurchaseAcceptDetail> purchaseAcceptDetailListQueryWrapper = new LambdaQueryWrapper<>();
            purchaseAcceptDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getId()), PurchaseAcceptDetail::getAcceptanceId, purchaseAcceptDto.getId());
            //调用更新方法
            resultAssets =  purchaseAcceptDetailService.saveOrUpdateOrDeleteBatch(purchaseAcceptDetailListQueryWrapper, purchaseAcceptDetails, purchaseAcceptDetails.size());
        }

        return resultAssets && resultDetail;
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        return super.removeById(id)
                && purchaseAcceptDetailService.deleteByAcceptanceId(id)
                && purchaseAcceptAssetService.deleteByAcceptanceId(id);
    }


    @Override
    @Transactional
    public Boolean deleteByProc(String procInstId) {
        PurchaseAcceptVo purchaseAcceptVo = this.getByProcId(procInstId);
        if (purchaseAcceptVo != null) {
            return this.delete(purchaseAcceptVo.getId());
        }
        return true;
    }

    @Override
    public PurchaseAcceptVo getByProcId(String procInstId) {
        LambdaQueryWrapper<PurchaseAccept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseAccept::getCamundaProcinsId, procInstId);
        List<PurchaseAccept> purchaseAcceptList = super.list(queryWrapper);
        PurchaseAcceptVo purchaseAcceptVo = null;
        if (CollectionUtils.isNotEmpty(purchaseAcceptList)) {

            purchaseAcceptVo = dozerMapper.map(purchaseAcceptList.get(0), PurchaseAcceptVo.class);

            //根据id获取子集
            List<PurchaseAcceptAssetVo> purchaseAcceptAssetVoList = purchaseAcceptAssetService.getByAcceptanceId(purchaseAcceptVo.getId());
            purchaseAcceptAssetVoList.forEach(assetsVo -> {
                List<PurchaseAcceptDetailVo> detailVos = purchaseAcceptDetailService.getByAcceptanceId(assetsVo.getId());
                assetsVo.setDetailList(detailVos);
            });
            //构建其对应的子表数据
            purchaseAcceptVo.setAssetsList(purchaseAcceptAssetVoList);

            //根据id获取子集
            List<PurchaseAcceptDetailVo> purchaseAcceptDetailVoList = purchaseAcceptDetailService.getByAcceptanceId(purchaseAcceptVo.getId());
            //构建其对应的子表数据
            purchaseAcceptVo.setDetailList(purchaseAcceptDetailVoList);
        }
        return purchaseAcceptVo;
    }

    @Override
    @Transactional
    public PurchaseAcceptVo procSaveOrUpdate(PurchaseAcceptDto purchaseAcceptDto) {

        PurchaseAcceptVo purchaseAcceptVo = this.get(purchaseAcceptDto.getId());
        //判断是否未编辑、是否是起草
        //若不是起草(流程中保存)，手动调用保存前方法
        if (ObjectUtil.isNotEmpty(purchaseAcceptVo)
                && ObjectUtil.isNotEmpty(purchaseAcceptVo.getAuditStatus())
                && purchaseAcceptVo.getAuditStatus() != WfProcessEnum.DRAFT.getType()){
            this.purAcceptSaveBefore(purchaseAcceptVo.getCamundaProcinsId());
        }

        //获取申请编号
        if(StringUtils.isBlank(purchaseAcceptDto.getCode())){
            getMaxCode(purchaseAcceptDto);
        }
        if(ObjectUtil.isEmpty(purchaseAcceptDto.getAssetSource())){
            purchaseAcceptDto.setAssetSource(AssetSource.CAIGOUR.getCode());
        }
        if(ObjectUtil.isEmpty(purchaseAcceptDto.getPurchaseType())){
            purchaseAcceptDto.setPurchaseType(AssetSource.CAIGOUR.getCode());
        }
        PurchaseAccept purchaseAccept = dozerMapper.map(purchaseAcceptDto,PurchaseAccept.class);
        super.saveOrUpdate(purchaseAccept);

        purchaseAcceptAssetService.deleteByAcceptanceId(purchaseAcceptDto.getId());
        purchaseAcceptDetailService.deleteByAcceptanceId(purchaseAcceptDto.getId());


        List<PurchaseAcceptAssetDto> assetsDtoList = purchaseAcceptDto.getAssetsList();

        if(CollectionUtils.isNotEmpty(assetsDtoList)){
            assetsDtoList.forEach(assetsDto -> {
                assetsDto.setAcceptanceId(purchaseAcceptDto.getId());
                assetsDto.setAcceptanceCode(purchaseAcceptDto.getCode());
            });
            List<PurchaseAcceptAsset> assetsList = DozerUtils.mapList(dozerMapper,assetsDtoList,PurchaseAcceptAsset.class);
            purchaseAcceptAssetService.saveOrUpdateBatch(assetsList);
        }


        List<PurchaseAcceptDetailDto> detailDtoList = purchaseAcceptDto.getDetailList();
        if(CollectionUtils.isNotEmpty(detailDtoList)){
            List<PurchaseAcceptDetail> detailList = DozerUtils.mapList(dozerMapper,detailDtoList,PurchaseAcceptDetail.class);
            detailList.forEach(detail -> {
                detail.setAcceptanceId(purchaseAcceptDto.getId());
                detail.setAcceptanceCode(purchaseAcceptDto.getCode());
                detail.setAcceptanceDate(LocalDateTime.now());
            });
            purchaseAcceptDetailService.saveOrUpdateBatch(detailList);
        }



        return this.getByProcId(purchaseAccept.getCamundaProcinsId());
    }

    /**
     * 获取采购后待验收数据
     *
     * @param purchaseRegisterResultDto
     * @return
     */
    @Override
    public Page getAcceptList(PurchaseRegisterResultDto purchaseRegisterResultDto) {
        return purchaseRegisterResultService.getAcceptList(purchaseRegisterResultDto);
    }

    /**
     * 获取购置验收详情信息
     *
     * @param purchaseAcceptAssetDto
     * @return Result
     */
    @Override
    public PurchaseAcceptAssetVo getDetailByAssets(PurchaseAcceptAssetDto purchaseAcceptAssetDto) {

        PurchaseAcceptAssetVo acceptAssetVo = dozerMapper.map(purchaseAcceptAssetDto, PurchaseAcceptAssetVo.class);
        List<PurchaseAcceptDetailVo> detailVos = new ArrayList<>();

        if (!ObjectUtil.isEmpty(acceptAssetVo)) {
            String itemType = acceptAssetVo.getItemType();

            //登记数量、单价等、重新计算生成
            BigDecimal acceptanceNumber = acceptAssetVo.getAcceptanceNumber();
//            BigDecimal acceptanceAmount = acceptAssetVo.getAcceptanceAmount();
//            BigDecimal acceptanceTaxAmount = acceptAssetVo.getAcceptanceTaxAmount();
//            BigDecimal acceptanceUntaxedAmount = acceptAssetVo.getAcceptanceUntaxedAmount();
//
//            BigDecimal acceptanceSumAmount = acceptanceAmount.multiply(acceptanceNumber);
//            BigDecimal acceptanceSumTaxAmount = acceptanceTaxAmount.multiply(acceptanceNumber);
//            BigDecimal acceptanceSumUntaxedAmount = acceptanceUntaxedAmount.multiply(acceptanceNumber);

            //acceptAssetVo.setAcceptanceSumAmount(acceptanceSumAmount);
            //acceptAssetVo.setAcceptanceSumTaxAmount(acceptanceSumTaxAmount);
            //acceptAssetVo.setAcceptanceSumUntaxedAmount(acceptanceSumUntaxedAmount);
            acceptAssetVo.setId(null);

            PurchaseAcceptDetailVo acceptDetailVo = dozerMapper.map(acceptAssetVo, PurchaseAcceptDetailVo.class);
            acceptDetailVo.setId(null);
            //库存或者资产，目前按台套数管理的
            if (!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {
                //获取填写的登记数量，通过登记数量生成详情数
                Integer acceptanceNumberCount = acceptanceNumber.intValue();

                for (int i = 0; i < acceptanceNumberCount; i++) {
                    acceptDetailVo.setAcceptanceNumber(BigDecimal.ONE);

                    //acceptDetailVo.setAcceptanceSumAmount(acceptanceAmount);
                    //acceptDetailVo.setAcceptanceSumTaxAmount(acceptanceTaxAmount);
                    //acceptDetailVo.setAcceptanceSumUntaxedAmount(acceptanceUntaxedAmount);

                    detailVos.add(acceptDetailVo);
                }
            } else {//低值易耗品或原材料
                detailVos.add(acceptDetailVo);
            }
        }
        acceptAssetVo.setDetailList(detailVos);
        return acceptAssetVo;
    }

    private void verify(PurchaseAcceptVo acceptVo){
        PurchaseRegisterVo registerVo = purchaseRegisterService.get(acceptVo.getRegisterId());
        List<PurchaseAcceptDetailVo> acceptDetailVoList = acceptVo.getDetailList();

        BigDecimal sumAcceptanceNumber = BigDecimal.ZERO;
        BigDecimal sumAcceptanceAmount = BigDecimal.ZERO;
        for (PurchaseAcceptDetailVo acceptDetailVo : acceptDetailVoList) {
            BigDecimal acceptanceNumber = acceptDetailVo.getAcceptanceNumber();
            BigDecimal acceptanceAmount = acceptDetailVo.getAcceptanceSumAmount();
            sumAcceptanceNumber.add(acceptanceNumber);
            sumAcceptanceAmount.add(acceptanceAmount);

//            PurchaseRegisterAssets registerAssets =
            PurchaseRegisterResultVo registerResultVo = purchaseRegisterResultService.get(acceptDetailVo.getRegisterResultId());
            if(ObjectUtil.isNotEmpty(registerResultVo)) {
                BigDecimal notAcceptanceNumber = registerResultVo.getNotAcceptanceNumber();
//            BigDecimal notAccpetanceAmount = registerResultVo.getRegisterSumAmount();

                if (acceptanceNumber.compareTo(notAcceptanceNumber) == 1) {
                    throw new IncloudException("物资编码：" + acceptDetailVo.getItemCode() + ",验收数量大于未验收数量！！");
                }
            }else{
                throw new IncloudException("物资编码：" + acceptDetailVo.getItemCode() + ",没有查询到相关采购详情数据");
            }
        }

        BigDecimal notAcceptanceNumber = registerVo.getNotAcceptanceNumber();
        BigDecimal notAcceptanceAmount = registerVo.getNotAcceptanceAmount();
        if(sumAcceptanceNumber.compareTo(notAcceptanceNumber)==1){
            throw new IncloudException("申请单："+registerVo.getCode()+"验收总数量大于登记单未验收数量!!");
        }
        if(sumAcceptanceAmount.compareTo(notAcceptanceAmount)==1){
            throw new IncloudException("申请单："+registerVo.getCode()+"验收总金额大于登记单未验收金额!!");
        }
    }

    /**
     * 购置验收保存前
     * 减少购置采购验收数量，增加未验收数量
     * @param procInstId
     * @return
     */
    @Override
    @Transactional
    public Boolean purAcceptSaveBefore(String procInstId) {
        Boolean result = true;
        PurchaseAcceptVo acceptVo = this.getByProcId(procInstId);

        List<PurchaseAcceptDetailVo> detailVoList = acceptVo.getDetailList();
        //重新生成验收明细
        purchaseAcceptAssetService.deleteByAcceptanceId(acceptVo.getId());

        for (PurchaseAcceptDetailVo detailVo : detailVoList) {
            BigDecimal acceptanceNumber = detailVo.getAcceptanceNumber();

            //获取采购等级结果
            PurchaseRegisterResult registerResult = purchaseRegisterResultService.getById(detailVo.getRegisterResultId());
            PurchaseRegisterResultDto registerResultDto = dozerMapper.map(registerResult,PurchaseRegisterResultDto.class);

            //BigDecimal oldAcceptanceNumber = registerResultDto.getAcceptanceNumber();
            BigDecimal oldNotAcceptanceNumber = registerResultDto.getNotAcceptanceNumber();

            //BigDecimal newAcceptanceNumber = oldAcceptanceNumber.subtract(acceptanceNumber);
            BigDecimal newNotAcceptanceNumber = oldNotAcceptanceNumber.add(acceptanceNumber);

            //registerResultDto.setAcceptanceNumber(newAcceptanceNumber);
            registerResultDto.setNotAcceptanceNumber(newNotAcceptanceNumber);
            result = purchaseRegisterResultService.update(registerResultDto);
            log.debug("before--购置登记结果:{},未验收数量为:{}",registerResultDto.getId(),newNotAcceptanceNumber);
        }
        return result;
    }

    /**
     * 购置验收保存前
     * 减少购置未采购验收数量，增加验收数量
     * @param procInstId
     * @return
     */
    @Override
    @Transactional
    public Boolean purAcceptSaveAfter(String procInstId) {
        //log.debug("流程保存后");
        Boolean result = true;
        PurchaseAcceptVo acceptVo = this.getByProcId(procInstId);
        this.verify(acceptVo);

        List<PurchaseAcceptDetailVo> detailVoList = acceptVo.getDetailList();

        for (PurchaseAcceptDetailVo detailVo : detailVoList) {
            BigDecimal acceptanceNumber = detailVo.getAcceptanceNumber();

            LambdaQueryWrapper<PurchaseAcceptAsset> assetsQueryWrapper = new LambdaQueryWrapper<>();
            //assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(purchaseAcceptDto.getApplyUserOrgId()), PurchaseAcceptAsset::ge, purchaseAcceptDto.getApplyUserOrgId());
            assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getAcceptanceId()), PurchaseAcceptAsset::getAcceptanceId, detailVo.getAcceptanceId());
            assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemCode()), PurchaseAcceptAsset::getItemCode, detailVo.getItemCode());
            assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemType()), PurchaseAcceptAsset::getItemType, detailVo.getItemType());

            List<PurchaseAcceptAsset> assetVoList = purchaseAcceptAssetService.list(assetsQueryWrapper);
            if(CollectionUtils.isNotEmpty(assetVoList)){
                PurchaseAcceptAssetDto assetDto = dozerMapper.map(assetVoList.get(0),PurchaseAcceptAssetDto.class);
                BigDecimal oldAcceptanceNumber = assetDto.getAcceptanceNumber();
                BigDecimal newAcceptanceNumber = acceptanceNumber.add(oldAcceptanceNumber);
                assetDto.setAcceptanceNumber(newAcceptanceNumber);
                purchaseAcceptAssetService.update(assetDto);
                detailVo.setAcceptanceAssetsId(assetDto.getId());
            }else{
                PurchaseAcceptAssetDto assetDto = dozerMapper.map(detailVo,PurchaseAcceptAssetDto.class);

                purchaseAcceptAssetService.save(assetDto);
                detailVo.setAcceptanceAssetsId(assetDto.getId());
            }

            //获取采购等级结果
            PurchaseRegisterResultVo registerResultVo = purchaseRegisterResultService.get(detailVo.getRegisterResultId());
            PurchaseRegisterResultDto registerResultDto = dozerMapper.map(registerResultVo,PurchaseRegisterResultDto.class);

            //BigDecimal oldAcceptanceNumber = registerResultDto.getAcceptanceNumber();
            BigDecimal oldNotAcceptanceNumber = registerResultDto.getNotAcceptanceNumber();

            //BigDecimal newAcceptanceNumber = oldAcceptanceNumber.add(acceptanceNumber);
            BigDecimal newNotAcceptanceNumber = oldNotAcceptanceNumber.subtract(acceptanceNumber);

            //registerResultDto.setAcceptanceNumber(newAcceptanceNumber);
            registerResultDto.setNotAcceptanceNumber(newNotAcceptanceNumber);
            result = purchaseRegisterResultService.update(registerResultDto);
            log.debug("after--购置登记结果:{},未验收数量为:{}",registerResultDto.getId(),newNotAcceptanceNumber);
        }
        List<PurchaseAcceptDetail> detailList = DozerUtils.mapList(dozerMapper,detailVoList,PurchaseAcceptDetail.class);
        purchaseAcceptDetailService.saveOrUpdateBatch(detailList);
        return result;
    }

    @Override
    @Transactional
    public Boolean purAcceptAuditSuccess(String procInstId) {
        Boolean result = true;
        PurchaseAcceptVo acceptVo = this.getByProcId(procInstId);
        PurchaseAcceptDto acceptDto = dozerMapper.map(acceptVo,PurchaseAcceptDto.class);
        acceptDto.setStorageNumber(BigDecimal.ZERO);
        acceptDto.setNotStorageNumber(acceptDto.getSumTotalNumber());
        acceptDto.setNotStorageAmount(acceptDto.getSumTotalAmount());

        if(ObjectUtil.isEmpty(acceptVo.getStatus())){
            acceptDto.setStatus(0);
        }
        setRegisterStatus(acceptVo);

        //List<PurchaseAcceptAssetVo> assetsVos = acceptVo.getAssetsList();
        List<PurchaseAcceptDetailVo> detailVos = acceptVo.getDetailList();

        if(detailVos.size()>0) {
            for (PurchaseAcceptDetailVo detailVo : detailVos) {
                //资产类型  1-资产（自购,2-办公耗材（低值易耗皮,3-库存商品（销售）,4-原材料（自制）
                String itemType = detailVo.getItemType();
                BigDecimal acceptanceNumber = detailVo.getAcceptanceNumber();

                Integer acceptanceStatus = detailVo.getAcceptanceStatus();
                if (ObjectUtil.isNotEmpty(acceptanceStatus) && acceptanceStatus == YesNo.YES.getCode()) {

                    PurchaseRegisterResultVo resultVo = purchaseRegisterResultService.get(detailVo.getRegisterResultId());
                    //获取采购登记结果,修改采购登记结果验收数量
                    PurchaseRegisterResultDto resultDto = dozerMapper.map(resultVo, PurchaseRegisterResultDto.class);
                    BigDecimal oldAcceptanceNumber = ObjectUtil.isEmpty(resultDto.getAcceptanceNumber()) ? BigDecimal.ZERO : resultDto.getAcceptanceNumber();
                    BigDecimal newAcceptanceNUmber = oldAcceptanceNumber.add(acceptanceNumber);
                    resultDto.setAcceptanceNumber(newAcceptanceNUmber);
                    purchaseRegisterResultService.update(resultDto);

                    //PurchaseRegisterVo registerVo = purchaseRegisterService.get(resultVo.getRegisterId());
                    PurchaseRegisterAssetsVo registerAssetsVo = purchaseRegisterAssetsService.get(resultVo.getRegisterAssetsId());
                    //PurchaseRegisterDetailVo registerDetailVo = purchaseRegisterDetailService.get(resultVo.getRegisterDetailId());

                    //获取申请详情,修改购置申请详情验收数量
                    PurchaseApplyDetailVo applyDetailVo = purchaseApplyDetailService.get(registerAssetsVo.getApplyDetailId());
                    PurchaseApplyDetailDto applyDetailDto = dozerMapper.map(applyDetailVo, PurchaseApplyDetailDto.class);

                    oldAcceptanceNumber = ObjectUtil.isEmpty(applyDetailDto.getAcceptanceNumber()) ? BigDecimal.ZERO : applyDetailDto.getAcceptanceNumber();
                    newAcceptanceNUmber = oldAcceptanceNumber.add(acceptanceNumber);
                    applyDetailDto.setAcceptanceNumber(newAcceptanceNUmber);
                    purchaseApplyDetailService.update(applyDetailDto);

                    //地址易耗品，目前按台套数管理的
                    if (!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.ARTICLES.code) || itemType.equals(ItemTypeEnum.MATERIAL.code))) {

                        LambdaQueryWrapper<Supplies> assetsQueryWrapper = new LambdaQueryWrapper<>();
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserOrgId()), Supplies::getAssetOrgId, acceptVo.getApplyUserOrgId());
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserDeptId()), Supplies::getAssetDeptId, acceptVo.getApplyUserDeptId());
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemId()), Supplies::getItemId, detailVo.getItemId());
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemCode()), Supplies::getItemCode, detailVo.getItemCode());
                        assetsQueryWrapper.eq(Supplies::getAssetSource, AssetSource.CAIGOUR.getCode());
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), Supplies::getItemType, itemType);

                        List<Supplies> suppliesList = suppliesService.list(assetsQueryWrapper);
                        SuppliesDto suppliesDto = new SuppliesDto();
                        if (suppliesList.size() > 0) {//该类资产已经存在
                            Supplies supplies = suppliesList.get(0);
                            suppliesDto = dozerMapper.map(supplies, SuppliesDto.class);
                            //资产数量累加
                            BigDecimal assetsNumber = suppliesDto.getAssetsNumber().add(acceptanceNumber);
                            suppliesDto.setAssetsNumber(assetsNumber);
                            //验收数量累加
                            BigDecimal newAcceptanceNumber = suppliesDto.getAcceptanceNumber().add(acceptanceNumber);
                            suppliesDto.setAcceptanceNumber(newAcceptanceNumber);

                            result = suppliesService.update(suppliesDto);

                        } else {//不存在
                            suppliesDto = dozerMapper.map(detailVo, SuppliesDto.class);
                            suppliesDto.setId(null);
                            suppliesDto.setSourceId(detailVo.getAcceptanceAssetsId());

                            suppliesDto.setAssetOrgId(applyDetailDto.getApplyUserOrgId());
                            suppliesDto.setAssetOrgName(applyDetailDto.getApplyUserOrgName());
                            suppliesDto.setAssetDeptId(applyDetailDto.getApplyUserDeptId());
                            suppliesDto.setAssetDeptName(applyDetailDto.getApplyUserDeptName());

                            suppliesDto.setAssetsNumber(acceptanceNumber);
                            suppliesDto.setStorageNumber(BigDecimal.ZERO);
                            suppliesDto.setStockNumber(BigDecimal.ZERO);
                            suppliesDto.setDeliveryNumber(BigDecimal.ZERO);
                            suppliesDto.setAcceptNumber(BigDecimal.ZERO);
                            suppliesDto.setBorrowNumber(BigDecimal.ZERO);
                            suppliesDto.setTransferNumber(BigDecimal.ZERO);
                            suppliesDto.setRepairNumber(BigDecimal.ZERO);
                            suppliesDto.setScrappedNumber(BigDecimal.ZERO);
                            suppliesDto.setAcceptanceNumber(acceptanceNumber);
                            suppliesDto.setUsableNumber(BigDecimal.ZERO);
                            suppliesDto.setEntryNumber(BigDecimal.ZERO);
                            suppliesDto.setLendNumber(BigDecimal.ZERO);
                            suppliesDto.setAssetSource(AssetSource.CAIGOUR.getCode());

                            suppliesDto.setApplyTime(applyDetailDto.getApplyTime());
                            suppliesDto.setApplyUserId(applyDetailDto.getApplyUserId());
                            suppliesDto.setApplyUserName(applyDetailDto.getApplyUserName());
                            suppliesDto.setApplyUserOrgId(applyDetailDto.getApplyUserOrgId());
                            suppliesDto.setApplyUserOrgName(applyDetailDto.getApplyUserOrgName());
                            suppliesDto.setApplyUserDeptId(applyDetailDto.getApplyUserDeptId());
                            suppliesDto.setApplyUserDeptName(applyDetailDto.getApplyUserDeptName());

                            suppliesDto.setUseUserOrgId(applyDetailDto.getApplyUserOrgId());
                            suppliesDto.setUseUserOrgName(applyDetailDto.getApplyUserOrgName());
                            suppliesDto.setUseUserDeptId(applyDetailDto.getApplyUserDeptId());
                            suppliesDto.setUseUserDeptName(applyDetailDto.getApplyUserDeptName());

                            result = suppliesService.save(suppliesDto);
                        }


                        LambdaQueryWrapper<SuppliesSku> assetsSkuQueryWrapper = new LambdaQueryWrapper<>();
                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserOrgId()), SuppliesSku::getAssetOrgId, acceptVo.getApplyUserOrgId());
                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserDeptId()), SuppliesSku::getAssetDeptId, acceptVo.getApplyUserDeptId());
                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemId()), SuppliesSku::getItemId, detailVo.getItemId());
                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemCode()), SuppliesSku::getItemCode, detailVo.getItemCode());
                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), SuppliesSku::getItemType, itemType);
                        assetsSkuQueryWrapper.eq(SuppliesSku::getAssetSource, AssetSource.CAIGOUR.getCode());
                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getSkuCode()), SuppliesSku::getSkuCodes, detailVo.getSkuCode());

                        List<SuppliesSku> suppliesSkuList = suppliesSkuService.list(assetsSkuQueryWrapper);
                        SuppliesSkuDto suppliesSkuDto = new SuppliesSkuDto();
                        if (suppliesSkuList.size() > 0) {//该类资产已经存在
                            SuppliesSku suppliesSku = suppliesSkuList.get(0);
                            suppliesSkuDto = dozerMapper.map(suppliesSku, SuppliesSkuDto.class);
                            //资产数量累加
                            BigDecimal assetsNumber = suppliesSkuDto.getAssetsNumber().add(acceptanceNumber);
                            suppliesSkuDto.setAssetsNumber(assetsNumber);

                            //验收数量累加
                            BigDecimal newAcceptanceNumber = suppliesSkuDto.getAcceptanceNumber().add(acceptanceNumber);
                            suppliesSkuDto.setAcceptanceNumber(newAcceptanceNumber);

                            result = suppliesSkuService.update(suppliesSkuDto);
                        } else {//不存在
                            suppliesSkuDto = dozerMapper.map(detailVo, SuppliesSkuDto.class);
                            suppliesSkuDto.setId(null);
                            suppliesSkuDto.setSourceId(detailVo.getAcceptanceAssetsId());
                            suppliesSkuDto.setAssetsId(suppliesDto.getId());

                            suppliesSkuDto.setAssetOrgId(acceptVo.getApplyUserOrgId());
                            suppliesSkuDto.setAssetOrgName(acceptVo.getApplyUserOrgName());
                            suppliesSkuDto.setAssetDeptId(acceptVo.getApplyUserDeptId());
                            suppliesSkuDto.setAssetDeptName(acceptVo.getApplyUserDeptName());

                            suppliesSkuDto.setAssetsNumber(acceptanceNumber);
                            suppliesSkuDto.setStorageNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setStockNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setDeliveryNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setAcceptNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setBorrowNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setTransferNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setRepairNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setScrappedNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setAcceptanceNumber(acceptanceNumber);
                            suppliesSkuDto.setUsableNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setEntryNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setLendNumber(BigDecimal.ZERO);
                            suppliesSkuDto.setAssetSource(AssetSource.CAIGOUR.getCode());

                            suppliesSkuDto.setApplyTime(applyDetailDto.getApplyTime());
                            suppliesSkuDto.setApplyUserId(applyDetailDto.getApplyUserId());
                            suppliesSkuDto.setApplyUserName(applyDetailDto.getApplyUserName());
                            suppliesSkuDto.setApplyUserOrgId(applyDetailDto.getApplyUserOrgId());
                            suppliesSkuDto.setApplyUserOrgName(applyDetailDto.getApplyUserOrgName());
                            suppliesSkuDto.setApplyUserDeptId(applyDetailDto.getApplyUserDeptId());
                            suppliesSkuDto.setApplyUserDeptName(applyDetailDto.getApplyUserDeptName());

                            suppliesSkuDto.setUseUserOrgId(applyDetailDto.getApplyUserOrgId());
                            suppliesSkuDto.setUseUserOrgName(applyDetailDto.getApplyUserOrgName());
                            suppliesSkuDto.setUseUserDeptId(applyDetailDto.getApplyUserDeptId());
                            suppliesSkuDto.setUseUserDeptName(applyDetailDto.getApplyUserDeptName());

                            result = suppliesSkuService.save(suppliesSkuDto);
                        }

                        SuppliesDetailDto suppliesDetailDto = dozerMapper.map(detailVo, SuppliesDetailDto.class);
                        suppliesDetailDto.setId(null);
                        suppliesDetailDto.setSourceId(detailVo.getId());
                        suppliesDetailDto.setAssetsId(suppliesDto.getId());
                        suppliesDetailDto.setAssetsSkuId(suppliesSkuDto.getId());
                        suppliesDetailDto.setItemType(itemType);

                        suppliesDetailDto.setAssetOrgId(applyDetailDto.getApplyUserOrgId());
                        suppliesDetailDto.setAssetOrgName(applyDetailDto.getApplyUserOrgName());
                        suppliesDetailDto.setAssetDeptId(applyDetailDto.getApplyUserDeptId());
                        suppliesDetailDto.setAssetDeptName(applyDetailDto.getApplyUserDeptName());

                        suppliesDetailDto.setAssetsNumber(detailVo.getAcceptanceNumber());
                        suppliesDetailDto.setAcceptanceNumber(detailVo.getAcceptanceNumber());
                        suppliesDetailDto.setStorageNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setStockNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setDeliveryNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setAcceptNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setBorrowNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setTransferNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setRepairNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setScrappedNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setUsableNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setEntryNumber(BigDecimal.ZERO);
                        suppliesDetailDto.setLendNumber(BigDecimal.ZERO);

                        suppliesDetailDto.setTaxRate(detailVo.getAcceptanceTaxRate());
                        suppliesDetailDto.setAssetsAmount(detailVo.getAcceptanceAmount());
                        suppliesDetailDto.setAssetsTaxAmount(detailVo.getAcceptanceTaxAmount());
                        suppliesDetailDto.setAssetsUntaxedAmount(detailVo.getAcceptanceUntaxedAmount());
                        suppliesDetailDto.setAssetsSumAmount(detailVo.getAcceptanceSumAmount());
                        suppliesDetailDto.setAssetsSumTaxAmount(detailVo.getAcceptanceSumAmount().subtract(detailVo.getAcceptanceSumUntaxedAmount()));
                        suppliesDetailDto.setAssetsSumUntaxedAmount(detailVo.getAcceptanceSumUntaxedAmount());
                        suppliesDetailDto.setAssetSource(AssetSource.CAIGOUR.getCode());

                        suppliesDetailDto.setApplyTime(applyDetailDto.getApplyTime());
                        suppliesDetailDto.setApplyUserId(applyDetailDto.getApplyUserId());
                        suppliesDetailDto.setApplyUserName(applyDetailDto.getApplyUserName());
                        suppliesDetailDto.setApplyUserOrgId(applyDetailDto.getApplyUserOrgId());
                        suppliesDetailDto.setApplyUserOrgName(applyDetailDto.getApplyUserOrgName());
                        suppliesDetailDto.setApplyUserDeptId(applyDetailDto.getApplyUserDeptId());
                        suppliesDetailDto.setApplyUserDeptName(applyDetailDto.getApplyUserDeptName());

                        suppliesDetailDto.setUseUserOrgId(applyDetailDto.getApplyUserOrgId());
                        suppliesDetailDto.setUseUserOrgName(applyDetailDto.getApplyUserOrgName());
                        suppliesDetailDto.setUseUserDeptId(applyDetailDto.getApplyUserDeptId());
                        suppliesDetailDto.setUseUserDeptName(applyDetailDto.getApplyUserDeptName());

                        suppliesDto.setAcceptUserId(acceptDto.getApplyUserId());
                        suppliesDto.setAcceptUserName(acceptDto.getApplyUserName());

                        result = suppliesDetailService.save(suppliesDetailDto);
                        daybookSuppliesService.log(acceptVo.getId(), acceptVo.getCamundaProcinsId(), suppliesDetailDto, DayBookType.ACCEPTANCE);
                        if(ObjectUtil.isEmpty(detailVo.getSourceId())){
                            detailVo.setSourceId(suppliesDetailDto.getId());
                        }
                    } else {//回填验收数量
//                        LambdaQueryWrapper<Assets> assetsQueryWrapper = new LambdaQueryWrapper<>();
//                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserOrgId()), Assets::getAssetOrgId, acceptVo.getApplyUserOrgId());
//                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserDeptId()), Assets::getAssetDeptId, acceptVo.getApplyUserDeptId());
//                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemId()), Assets::getItemId, detailVo.getItemId());
//                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemCode()), Assets::getItemCode, detailVo.getItemCode());
//                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), Assets::getItemType, itemType);
//                        List<Assets> assetsList = assetsService.list(assetsQueryWrapper);
//                        if (ObjectUtil.isNotEmpty(assetsList)) {
//                            AssetsDto assetsDto = dozerMapper.map(assetsList.get(0), AssetsDto.class);
//                            BigDecimal oldAssetAcceptanceNumber = ObjectUtil.isEmpty(assetsDto.getAcceptanceNumber()) ? BigDecimal.ZERO : assetsDto.getAcceptanceNumber();
//                            BigDecimal newAssetAcceptanceNUmber = oldAssetAcceptanceNumber.add(acceptanceNumber);
//                            assetsDto.setAcceptanceNumber(newAssetAcceptanceNUmber);
//                            assetsService.update(assetsDto);
//                        }
//
//                        LambdaQueryWrapper<AssetsSku> assetsSkuQueryWrapper = new LambdaQueryWrapper<>();
//                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserOrgId()), AssetsSku::getAssetOrgId, acceptVo.getApplyUserOrgId());
//                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserDeptId()), AssetsSku::getAssetDeptId, acceptVo.getApplyUserDeptId());
//                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemId()), AssetsSku::getItemId, detailVo.getItemId());
//                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemCode()), AssetsSku::getItemCode, detailVo.getItemCode());
//                        assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), AssetsSku::getItemType, itemType);
//                        List<AssetsSku> assetsSkuList = assetsSkuService.list(assetsSkuQueryWrapper);
//                        if (ObjectUtil.isNotEmpty(assetsSkuList)) {
//                            AssetsSkuDto assetsSkuDto = dozerMapper.map(assetsSkuList.get(0), AssetsSkuDto.class);
//                            BigDecimal oldAssetAcceptanceNumber = ObjectUtil.isEmpty(assetsSkuDto.getAcceptanceNumber()) ? BigDecimal.ZERO : assetsSkuDto.getAcceptanceNumber();
//                            BigDecimal newAssetAcceptanceNUmber = oldAssetAcceptanceNumber.add(acceptanceNumber);
//                            assetsSkuDto.setAcceptanceNumber(newAssetAcceptanceNUmber);
//                            assetsSkuService.update(assetsSkuDto);
//                        }

                        List<AssetsDetail> assetsDetailList = assetsDetailService.list(Wrappers.<AssetsDetail>lambdaQuery()
                                .eq(AssetsDetail::getId, detailVo.getSourceId()));
                        if (CollectionUtils.isNotEmpty(assetsDetailList)) {
                            AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailList.get(0), AssetsDetailDto.class);

                            if(ObjectUtil.isNotEmpty(assetsDetailDto)) {

                                AssetsVo assetsVo = assetsService.get(assetsDetailDto.getAssetsId());
                                if(ObjectUtil.isNotEmpty(assetsVo)){
                                    AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                                    BigDecimal oldAssetAcceptanceNumber = ObjectUtil.isEmpty(assetsDto.getAcceptanceNumber()) ? BigDecimal.ZERO : assetsDto.getAcceptanceNumber();
                                    BigDecimal newAssetAcceptanceNUmber = oldAssetAcceptanceNumber.add(acceptanceNumber);
                                    assetsDto.setAcceptanceNumber(newAssetAcceptanceNUmber);
                                    assetsService.update(assetsDto);
                                }

                                AssetsSkuVo assetsSkuVo = assetsSkuService.get(assetsDetailDto.getAssetsSkuId());
                                if(ObjectUtil.isNotEmpty(assetsSkuVo)){
                                    AssetsSkuDto assetsSkuDto = dozerMapper.map(assetsSkuVo, AssetsSkuDto.class);
                                    BigDecimal oldAssetAcceptanceNumber = ObjectUtil.isEmpty(assetsSkuDto.getAcceptanceNumber()) ? BigDecimal.ZERO : assetsSkuDto.getAcceptanceNumber();
                                    BigDecimal newAssetAcceptanceNUmber = oldAssetAcceptanceNumber.add(acceptanceNumber);
                                    assetsSkuDto.setAcceptanceNumber(newAssetAcceptanceNUmber);
                                    assetsSkuService.update(assetsSkuDto);
                                }

                                BigDecimal oldAssetAcceptanceNumber = ObjectUtil.isEmpty(assetsDetailDto.getAcceptanceNumber()) ? BigDecimal.ZERO : assetsDetailDto.getAcceptanceNumber();
                                BigDecimal newAssetAcceptanceNUmber = oldAssetAcceptanceNumber.add(acceptanceNumber);
                                assetsDetailDto.setAcceptanceNumber(newAssetAcceptanceNUmber);

                                assetsDetailDto.setAssetsCode(detailVo.getAssetsCode());
//                                assetsDetailDto.setAssetsLabel(detailVo.getAssetsLabel());
                                assetsDetailDto.setQualityAssuranceLevel(detailVo.getQualityAssuranceLevel());
                                assetsDetailDto.setManufacturer(detailVo.getManufacturer());
                                assetsDetailDto.setProductionDate(detailVo.getProductionDate());
                                assetsDetailDto.setServiceLife(detailVo.getServiceLife());
                                assetsDetailDto.setValidPeriod(detailVo.getValidPeriod());
                                assetsDetailDto.setBatchNumber(detailVo.getBatchNumber());
                                assetsDetailDto.setAcceptanceDate(detailVo.getAcceptanceDate());
                                assetsDetailDto.setFactoryDate(detailVo.getFactoryDate());
                                assetsDetailDto.setFactoryCode(detailVo.getFactoryCode());
                                assetsDetailDto.setApproachDate(detailVo.getApproachDate());
                                assetsDetailDto.setWarehouseId(detailVo.getWarehouseId());
                                assetsDetailDto.setWarehouseName(detailVo.getWarehouseName());
                                assetsDetailDto.setShelfId(detailVo.getShelfId());
                                assetsDetailDto.setShelfName(detailVo.getShelfName());
                                assetsDetailDto.setStatus("已验收");
                                assetsDetailDto.setAcceptUserId(acceptDto.getApplyUserId());
                                assetsDetailDto.setAcceptUserName(acceptDto.getApplyUserName());
                                assetsDetailService.update(assetsDetailDto);
                                daybookService.log(acceptVo.getId(), acceptVo.getCamundaProcinsId(), assetsDetailDto, DayBookType.ACCEPTANCE);
                            }
                        }
                    }
                }
            }
        }
        return result && this.update(acceptDto);
    }

    //设置采购单状态
    private Boolean setRegisterStatus(PurchaseAcceptVo acceptVo){
        BigDecimal acceptanceNumber = acceptVo.getSumTotalNumber();
        BigDecimal acceptanceAmount = acceptVo.getSumTotalAmount();

        //获取验收的采购单
        PurchaseRegisterVo registerVo = purchaseRegisterService.get(acceptVo.getRegisterId());
        PurchaseRegisterDto registerDto = dozerMapper.map(registerVo,PurchaseRegisterDto.class);

        BigDecimal oldAcceptanceNumber = registerDto.getAcceptanceNumber();
        oldAcceptanceNumber = ObjectUtil.isEmpty(oldAcceptanceNumber) ? BigDecimal.ZERO : oldAcceptanceNumber;
        registerDto.setAcceptanceNumber(oldAcceptanceNumber.add(acceptanceNumber));

        BigDecimal notAcceptanceNumber = registerDto.getNotAcceptanceNumber();
        notAcceptanceNumber = ObjectUtil.isEmpty(notAcceptanceNumber) ? registerDto.getSumTotalNumber() : notAcceptanceNumber;
        registerDto.setNotAcceptanceNumber(notAcceptanceNumber.subtract(acceptanceNumber));

        BigDecimal notAcceptanceAmount = registerDto.getNotAcceptanceAmount();
        notAcceptanceAmount = ObjectUtil.isEmpty(notAcceptanceAmount) ? registerDto.getSumTotalAmount() : notAcceptanceAmount;
        registerDto.setNotAcceptanceAmount(notAcceptanceAmount.subtract(acceptanceAmount));

        if(registerDto.getNotAcceptanceNumber().compareTo(BigDecimal.ZERO) == 0){
            registerDto.setStatus(1);
        }else{
            registerDto.setStatus(2);
        }
//        PurchaseAcceptDto acceptDto = new PurchaseAcceptDto();
//        acceptDto.setRegisterId(acceptVo.getRegisterId());
//        List<PurchaseAccept> acceptVoList = this.list(Wrappers.<PurchaseAccept>lambdaQuery()
//                .eq(PurchaseAccept::getRegisterId, acceptVo.getRegisterId())
//                .eq(PurchaseAccept::getAuditStatus,WfProcessEnum.DONE.getType()));
//        for (PurchaseAccept accept : acceptVoList){
//            sumAcceptanceNumber.add(accept.getSumTotalNumber());
//        }
//        if(sumRegisterNumber.compareTo(sumAcceptanceNumber)==0||sumAcceptanceNumber.compareTo(sumRegisterNumber)==1){
//            registerDto.setStatus(1);//全部采购
//        }else if(sumRegisterNumber.compareTo(sumAcceptanceNumber)==1){//采购数量大于验收数量 （）
//            registerDto.setStatus(2);//部分采购
//        }
        return purchaseRegisterService.update(registerDto);
    }

    @Override
    public List<PurchaseAcceptDetailVo> getDetailByAssetsList(List<PurchaseRegisterResultDto> resultDtoList) {
        Map<String,String> acceptancBatchMap = new HashMap<>();
        List<PurchaseAcceptDetailVo> detailVoList = new ArrayList<>();
        resultDtoList.forEach(resultDto -> {

            if (!ObjectUtil.isEmpty(resultDto)) {
                BigDecimal acceptanceNumber = resultDto.getAcceptanceNumber();
                if(ObjectUtil.isNotEmpty(acceptanceNumber) && acceptanceNumber.compareTo(BigDecimal.ZERO)==1){

                    String itemCode = resultDto.getItemCode();
                    String itemType = resultDto.getItemType();

                    Integer acceptanceNumberCount = acceptanceNumber.intValue();
                    //登记数量、单价等、重新计算生成
                    BigDecimal acceptanceAmount = resultDto.getRegisterAmount();
                    BigDecimal acceptanceUntaxedAmount = resultDto.getRegisterUntaxedAmount();
                    BigDecimal acceptanceTaxAmount = resultDto.getRegisterTaxAmount();
                    BigDecimal accpetanceSumAmount = resultDto.getRegisterSumAmount();
                    BigDecimal acceptanceSumUntaxedAmount = resultDto.getRegisterSumUntaxedAmount();
                    BigDecimal acceptanceSumTaxAmount = resultDto.getRegisterSumTaxAmount();
                    BigDecimal acceptanceTaxRate = resultDto.getRegisterTaxRate();

                    PurchaseAcceptDetailDto acceptDetailDto = dozerMapper.map(resultDto, PurchaseAcceptDetailDto.class);
                    //设置规则值
                    ObjectMapper objectMapper = new ObjectMapper();
                    HashMap entityMap = objectMapper.convertValue(acceptDetailDto,HashMap.class);

                    PurchaseAcceptDetailVo acceptDetailVo = new PurchaseAcceptDetailVo();


                    if (!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {
                        LambdaQueryWrapper<Assets> assetsQueryWrapper = new LambdaQueryWrapper<>();
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(resultDto.getApplyUserOrgId()), Assets::getAssetOrgId, resultDto.getApplyUserOrgId());
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(resultDto.getApplyUserDeptId()), Assets::getAssetDeptId, resultDto.getApplyUserDeptId());
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(resultDto.getItemId()), Assets::getItemId, resultDto.getItemId());
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(resultDto.getItemCode()), Assets::getItemCode, resultDto.getItemCode());
                        assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), Assets::getItemType, itemType);
                        assetsQueryWrapper.eq(Assets::getAssetSource, resultDto.getPurchaseType());
                        List<Assets> assetsList = assetsService.list(assetsQueryWrapper);
                        if(ObjectUtil.isNotEmpty(assetsList)){
                            Assets assets = assetsList.get(0);
                            List<AssetsDetailVo> assetsDetailVoList = assetsDetailService.getByAssetsId(assets.getId());
                            Integer count   = 0;
                            for (AssetsDetailVo assetsDetailVo : assetsDetailVoList) {
                                if(assetsDetailVo.getAcceptanceNumber().compareTo(BigDecimal.ZERO) == 0 &&count<acceptanceNumberCount){
                                    acceptDetailVo = dozerMapper.map(assetsDetailVo, PurchaseAcceptDetailVo.class);

                                    acceptDetailVo.setSourceId(assetsDetailVo.getId());
                                    acceptDetailVo.setId(null);
                                    acceptDetailVo.setRegisterResultId(assets.getId());
                                    acceptDetailVo.setAcceptanceNumber(BigDecimal.ONE);
//                                    try{
//                                        String assetsCode = dictClient.getRuleValue("purchase_accept_detail","assetsCode",entityMap);
//                                        acceptDetailVo.setAssetsCode(assetsCode);
//                                        log.debug("资产编号:{}",assetsCode);
//                                    }catch (Exception e){
//                                        log.debug("资产编码远程调用失败！！");
//                                    }

                                    acceptDetailVo.setRegisterResultId(resultDto.getId());
                                    acceptDetailVo.setAcceptanceAmount(acceptanceAmount);
                                    acceptDetailVo.setAcceptanceUntaxedAmount(acceptanceUntaxedAmount);
                                    acceptDetailVo.setAcceptanceTaxAmount(acceptanceTaxAmount);
                                    acceptDetailVo.setAcceptanceSumAmount(accpetanceSumAmount);
                                    acceptDetailVo.setAcceptanceSumUntaxedAmount(acceptanceSumUntaxedAmount);
                                    acceptDetailVo.setAcceptanceSumTaxAmount(acceptanceSumTaxAmount);
                                    acceptDetailVo.setAcceptanceTaxRate(acceptanceTaxRate);

                                    String acceptanceBatch = acceptancBatchMap.get(itemCode);
                                    if (ObjectUtil.isEmpty(acceptanceBatch)) {
                                        try {
                                            //获取验收批次号
                                            acceptanceBatch = dictClient.getRuleValue("purchase_accept_detail", "acceptanceBatch", entityMap);
                                            acceptancBatchMap.put(acceptDetailVo.getItemCode(), acceptanceBatch);
                                            log.debug("验收批次号:{}", acceptanceBatch);
                                        } catch (Exception e) {
                                            //e.printStackTrace();
                                            log.debug("验收批次远程调用失败！！");
                                        }
                                    }
                                    acceptDetailVo.setAcceptanceBatch(acceptanceBatch);
                                    count++;
                                    detailVoList.add(acceptDetailVo);
                                }
                            }
                        }
                    }else {
                        acceptDetailVo = dozerMapper.map(resultDto, PurchaseAcceptDetailVo.class);
                        acceptDetailVo.setId(null);
                        acceptDetailVo.setRegisterResultId(resultDto.getId());
                        acceptDetailVo.setAcceptanceAmount(acceptanceAmount);
                        acceptDetailVo.setAcceptanceUntaxedAmount(acceptanceUntaxedAmount);
                        acceptDetailVo.setAcceptanceTaxAmount(acceptanceTaxAmount);
                        acceptDetailVo.setAcceptanceSumAmount(accpetanceSumAmount);
                        acceptDetailVo.setAcceptanceSumUntaxedAmount(acceptanceSumUntaxedAmount);
                        acceptDetailVo.setAcceptanceSumTaxAmount(acceptanceSumTaxAmount);
                        acceptDetailVo.setAcceptanceTaxRate(acceptanceTaxRate);

                        String acceptanceBatch = acceptancBatchMap.get(itemCode);
                        if (ObjectUtil.isEmpty(acceptanceBatch)) {
                            try {
                                //获取验收批次号
                                acceptanceBatch = dictClient.getRuleValue("purchase_accept_detail", "acceptanceBatch", entityMap);
                                acceptancBatchMap.put(acceptDetailVo.getItemCode(), acceptanceBatch);
                                log.debug("验收批次号:{}", acceptanceBatch);
                            } catch (Exception e) {
                                //e.printStackTrace();
                                log.debug("验收批次远程调用失败！！");
                            }
                        }
                        acceptDetailVo.setAcceptanceBatch(acceptanceBatch);
                        detailVoList.add(acceptDetailVo);
                    }
                }

            }
        });
        return detailVoList;
    }

//    private void getAsstsCode(PurchaseAcceptDetailVo acceptDetailVo){
//        LambdaQueryWrapper<PurchaseAcceptD>
//    }
//    private void getAsstsCode(PurchaseAcceptDetailDto acceptDetailDto){
//
//    }


    @Override
    public List<PurchaseStorageDetailVo> forStorageList(PurchaseAcceptDetailDto purchaseAcceptDetailDto) {
        LambdaQueryWrapper<PurchaseAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseAcceptDetail::getAcceptanceId,purchaseAcceptDetailDto.getAcceptanceId());
        queryWrapper.gt(PurchaseAcceptDetail::getAcceptanceNumber, BigDecimal.ZERO);

        List<PurchaseStorageDetailVo> storageDetailVoList = new ArrayList<>();

        List<PurchaseAcceptDetail> acceptDetailList = purchaseAcceptDetailService.list(queryWrapper);
        acceptDetailList.forEach(acceptDetail -> {
            //acceptDetail.getStatus();
            PurchaseStorageDetailVo storageDetailVo = new PurchaseStorageDetailVo();

            String itemType = acceptDetail.getItemType();
            //库存或者资产，目前按台套数管理的
            if (!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {

//                PurchaseRegisterResultVo resultVo = purchaseRegisterResultService.get(acceptDetail.getRegisterResultId());
//                List<AssetsDetail> assetsDetailList = assetsDetailService.list(Wrappers.<AssetsDetail>lambdaQuery()
//                        .eq(AssetsDetail::getSourceId,resultVo.getRegisterDetailId())
//                        .gt(AssetsDetail::getAcceptanceNumber,BigDecimal.ZERO));
                List<AssetsDetail> assetsDetailList = assetsDetailService.list(Wrappers.<AssetsDetail>lambdaQuery()
                        .eq(AssetsDetail::getId, acceptDetail.getSourceId()));
                if(CollectionUtils.isNotEmpty(assetsDetailList)){
                    AssetsDetail assetsDetail = assetsDetailList.get(0);
                    BigDecimal acceptanceNumber = assetsDetail.getAcceptanceNumber();
                    BigDecimal storageNumber = ObjectUtil.isEmpty(assetsDetail.getStorageNumber()) ? BigDecimal.ZERO : assetsDetail.getStorageNumber();
                    if(acceptanceNumber.compareTo(storageNumber)==1 ){
                        storageDetailVo = dozerMapper.map(assetsDetail,PurchaseStorageDetailVo.class);
                        storageDetailVo.setId(null);
                        storageDetailVo.setSourceId(acceptDetail.getAcceptanceId());
                        storageDetailVo.setSourceAssetsId(acceptDetail.getAcceptanceAssetsId());
                        storageDetailVo.setSourceDetailId(acceptDetail.getId());

                        storageDetailVo.setAssetsId(assetsDetail.getAssetsId());
                        storageDetailVo.setAssetsSkuId(assetsDetail.getAssetsSkuId());
                        storageDetailVo.setAssetsDetailId(assetsDetail.getId());
                        if(ObjectUtil.isEmpty(storageDetailVo.getAssetsCode())) {
                            //设置规则值
                            ObjectMapper objectMapper = new ObjectMapper();
                            HashMap entityMap = objectMapper.convertValue(storageDetailVo, HashMap.class);
                            try {
                                String assetsCode = dictClient.getRuleValue("purchase_accept_detail", "assetsCode", entityMap);
                                storageDetailVo.setAssetsCode(assetsCode);
                                log.debug("资产编号:{}", assetsCode);
                            } catch (Exception e) {
                                log.debug("资产编码远程调用失败！！");
                            }
                        }

                        storageDetailVo.setAccpetanceTime(acceptDetail.getAcceptanceDate());
                        storageDetailVo.setAccpetanceCode(acceptDetail.getAcceptanceCode());
                        storageDetailVo.setStorageTaxRate(assetsDetail.getTaxRate());
//                        storageDetailVo.setStorageNumber(acceptanceNumber);
                        storageDetailVo.setStorageNumber(acceptanceNumber.subtract(storageNumber));
                        storageDetailVo.setStorageAmount(assetsDetail.getAssetsAmount());
                        storageDetailVo.setStorageTaxAmount(assetsDetail.getAssetsTaxAmount());
                        storageDetailVo.setStorageUntaxedAmount(assetsDetail.getAssetsUntaxedAmount());
                        storageDetailVo.setStorageSumAmount(assetsDetail.getAssetsSumAmount());
                        storageDetailVo.setStorageSumTaxAmount(assetsDetail.getAssetsSumTaxAmount());
                        storageDetailVo.setStorageSumUntaxedAmount(assetsDetail.getAssetsSumUntaxedAmount());
                    }
                }
            }else{
                List<SuppliesDetail> suppliesDetailList = suppliesDetailService.list(Wrappers.<SuppliesDetail>lambdaQuery()
                        .eq(SuppliesDetail::getId,acceptDetail.getSourceId())
                        .gt(SuppliesDetail::getAcceptanceNumber,BigDecimal.ZERO));
                if(CollectionUtils.isNotEmpty(suppliesDetailList)){
                    SuppliesDetail suppliesDetail = suppliesDetailList.get(0);
                    BigDecimal acceptanceNumber = suppliesDetail.getAcceptanceNumber();
                    BigDecimal storageNumber = ObjectUtil.isEmpty(suppliesDetail.getStorageNumber()) ? BigDecimal.ZERO : suppliesDetail.getStorageNumber();
                    if(suppliesDetail.getAcceptanceNumber().compareTo(suppliesDetail.getStorageNumber())==1 ){
                        storageDetailVo = dozerMapper.map(suppliesDetail,PurchaseStorageDetailVo.class);
                        storageDetailVo.setId(null);
                        storageDetailVo.setSourceId(acceptDetail.getAcceptanceId());
                        storageDetailVo.setSourceAssetsId(acceptDetail.getAcceptanceAssetsId());
                        storageDetailVo.setSourceDetailId(acceptDetail.getId());

                        storageDetailVo.setAssetsId(suppliesDetail.getAssetsId());
                        storageDetailVo.setAssetsSkuId(suppliesDetail.getAssetsSkuId());
                        storageDetailVo.setAssetsDetailId(suppliesDetail.getId());

                        storageDetailVo.setAccpetanceTime(acceptDetail.getAcceptanceDate());
                        storageDetailVo.setAccpetanceCode(acceptDetail.getAcceptanceCode());


                        storageDetailVo.setStorageTaxRate(suppliesDetail.getTaxRate());
                        storageDetailVo.setStorageNumber(acceptanceNumber);
                        storageDetailVo.setStorageNumber(acceptanceNumber.subtract(storageNumber));
                        storageDetailVo.setStorageAmount(suppliesDetail.getAssetsAmount());
                        storageDetailVo.setStorageTaxAmount(suppliesDetail.getAssetsTaxAmount());
                        storageDetailVo.setStorageUntaxedAmount(suppliesDetail.getAssetsUntaxedAmount());
                        storageDetailVo.setStorageSumAmount(suppliesDetail.getAssetsSumAmount());
                        storageDetailVo.setStorageSumTaxAmount(suppliesDetail.getAssetsSumTaxAmount());
                        storageDetailVo.setStorageSumUntaxedAmount(suppliesDetail.getAssetsSumUntaxedAmount());
                    }
                }
            }
            if(ObjectUtil.isNotEmpty(storageDetailVo) && ObjectUtil.isNotEmpty(storageDetailVo.getStorageNumber())){
                storageDetailVoList.add(storageDetailVo);
            }
        });

        log.debug("查询条数:"+storageDetailVoList.size());
        return storageDetailVoList;
    }
}
