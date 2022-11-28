package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.base.wf.starter.expression.HandleExpressionParam;
import com.netwisd.biz.asset.constants.*;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.AssetsDetailMapper;
import com.netwisd.biz.asset.mapper.SuppliesDetailMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.asset.mapper.PurchaseStorageMapper;
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
import java.util.stream.Collectors;

import com.netwisd.common.db.util.EntityListConvert;

/**
 * @Description 物资验收入库 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-20 18:03:40
 */
@Service
@Slf4j
public class PurchaseStorageServiceImpl extends BatchServiceImpl<PurchaseStorageMapper, PurchaseStorage> implements PurchaseStorageService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseStorageMapper purchaseStorageMapper;

    @Autowired
    private PurchaseStorageDetailService purchaseStorageDetailService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private DaybookService daybookService;

    @Autowired
    private DaybookSuppliesService daybookSuppliesService;

    @Autowired
    private AssetsDetailMapper assetsDetailMapper;

    @Autowired
    private SuppliesDetailMapper suppliesDetailMapper;

    @Autowired
    private SuppliesService suppliesService;

    @Autowired
    private SuppliesDetailService suppliesDetailService;

    @Autowired
    private PurchaseRegisterResultService purchaseRegisterResultService;

    @Autowired
    private PurchaseRegisterDetailService purchaseRegisterDetailService;

    @Autowired
    private PurchaseRegisterAssetsService purchaseRegisterAssetsService;

    @Autowired
    private PurchaseRegisterService purchaseRegisterService;

    @Autowired
    private PurchaseAcceptAssetService purchaseAcceptAssetService;

    @Autowired
    private PurchaseAcceptDetailService purchaseAcceptDetailService;

    @Autowired
    private PurchaseApplyDetailService purchaseApplyDetailService;

    @Autowired
    private PurchaseApplyService purchaseApplyService;

    @Autowired
    private PurchaseAcceptService purchaseAcceptService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private ViewerService viewerService;
    /**
     * 单表简单查询操作
     *
     * @param purchaseStorageDto
     * @return
     */
    @Override
    public Page list(PurchaseStorageDto purchaseStorageDto) {
        LambdaQueryWrapper<PurchaseStorage> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
//        queryWrapper.eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getApplyUserOrgName()), PurchaseStorage::getApplyUserOrgName,purchaseStorageDto.getApplyUserOrgName())
//                .eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getApplyUserDeptName()),PurchaseStorage::getApplyUserDeptName,purchaseStorageDto.getApplyUserDeptName())
//                .eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getApplyUserName()),PurchaseStorage::getApplyUserName,purchaseStorageDto.getApplyUserName())
//                .eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getCode()),PurchaseStorage::getCode,purchaseStorageDto.getCode())
//                .eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getPurchaseType()),PurchaseStorage::getPurchaseType,purchaseStorageDto.getPurchaseType());
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(PurchaseStorage::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(PurchaseStorage::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(PurchaseStorage::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        queryWrapper.orderByDesc(PurchaseStorage::getApplyTime);
        //指定的查询字段
        String searchCondition= purchaseStorageDto.getSearchCondition();

        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(PurchaseStorage::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(PurchaseStorage::getAcceptanceCode,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseStorage::getAssetSourceName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseStorage::getSumTotalNumber,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseStorage::getSumTotalAmount,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseStorage::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseStorage::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseStorage::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseStorage::getApplyTime,searchCondition));
            });
        }
        queryWrapper.eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getApplyUserOrgId()),PurchaseStorage::getApplyUserOrgName,purchaseStorageDto.getApplyUserOrgName())
                .eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getApplyUserDeptId()),PurchaseStorage::getApplyUserDeptName,purchaseStorageDto.getApplyUserDeptName())
                .eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getApplyUserName()),PurchaseStorage::getApplyUserName,purchaseStorageDto.getApplyUserName())
                .eq(ObjectUtil.isNotEmpty(purchaseStorageDto.getCode()),PurchaseStorage::getCode,purchaseStorageDto.getCode());


        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        //查看人员查看范围
        ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.LIST.getCode());

        if (ObjectUtil.isNotEmpty(viewerVo)) {
            queryWrapper.and(q -> {
                q
                        //可看人员List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseStorage::getApplyUserId, viewerVo.getUserList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseStorage::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseStorage::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseStorage::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseStorage::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseStorage::getCreateUserParentOrgId, viewerVo.getOrgList()));
            });
        }

        Page<PurchaseStorage> page = purchaseStorageMapper.selectPage(purchaseStorageDto.getPage(), queryWrapper);
        Page<PurchaseStorageVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseStorageVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param purchaseStorageDto
     * @return
     */
    @Override
    public Page lists(PurchaseStorageDto purchaseStorageDto) {
        Page<PurchaseStorageVo> pageVo = purchaseStorageMapper.getPageList(purchaseStorageDto.getPage(), purchaseStorageDto);
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
    public PurchaseStorageVo get(Long id) {
        PurchaseStorage purchaseStorage = super.getById(id);
        PurchaseStorageVo purchaseStorageVo = null;
        if (purchaseStorage != null) {
            purchaseStorageVo = dozerMapper.map(purchaseStorage, PurchaseStorageVo.class);
            //根据id获取purchaseStorageDetailVoList
            List<PurchaseStorageDetailVo> purchaseStorageDetailVoList = purchaseStorageDetailService.getByStorageId(id);
            //设置purchaseStorageVo，以便构建其对应的子表数据
            purchaseStorageVo.setDetailList(purchaseStorageDetailVoList);
        }
        return purchaseStorageVo;
    }

    /**
     * 保存实体
     *
     * @param purchaseStorageDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PurchaseStorageDto purchaseStorageDto) {
        //Boolean result = true , resultList = true;
        //获取申请编号
        if(StringUtils.isBlank(purchaseStorageDto.getCode())){
            getMaxCode(purchaseStorageDto);
        }
        PurchaseStorage purchaseStorage = dozerMapper.map(purchaseStorageDto, PurchaseStorage.class);
        Boolean result = super.save(purchaseStorage);
        Boolean resultList = saveSubList(purchaseStorageDto);
        if (result && resultList) {
            log.debug("保存成功");
        }
        return result && resultList;
    }


    /**
     * 获取申请编号
     * @return
     */
    private void getMaxCode(PurchaseStorageDto purchaseStorageDto){
        //获取申请编号
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(purchaseStorageDto,HashMap.class);
        String code = dictClient.getRuleValue("purchase_storage","code",entityMap);
        log.debug("规则值:{}",code);
        if(StringUtils.isNotBlank(code)){
            purchaseStorageDto.setCode(code);
        }
    }

    /**
     * 保存集合
     *
     * @param purchaseStorageDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseStorageDto> purchaseStorageDtos) {
        List<PurchaseStorage> PurchaseStorages = DozerUtils.mapList(dozerMapper, purchaseStorageDtos, PurchaseStorage.class);
        Boolean result = true, resultList = true;
        for (PurchaseStorageDto purchaseStorageDto : purchaseStorageDtos) {
            //获取申请编号
            if(StringUtils.isBlank(purchaseStorageDto.getCode())){
                getMaxCode(purchaseStorageDto);
            }
            resultList = saveSubList(purchaseStorageDto);
        }
        result = super.saveBatch(PurchaseStorages);
        return result && resultList;
    }

    /**
     * 保存子表集合
     *
     * @param purchaseStorageDto
     * @return
     */
    @Transactional
    Boolean saveSubList(PurchaseStorageDto purchaseStorageDto) {
        Boolean result = true;
        //获取其子表集合
        List<PurchaseStorageDetailDto> purchaseStorageDetailDtoList = purchaseStorageDto.getDetailList();
        if (purchaseStorageDetailDtoList != null && !purchaseStorageDetailDtoList.isEmpty()) {
            //根据主实体DTO映射其子表的关联键为其赋值
            purchaseStorageDetailDtoList.forEach(purchaseStorageDetailDto -> {
                purchaseStorageDetailDto.setStorageId(purchaseStorageDto.getId());
            });
            //调用保存子表的集合方法
            result = purchaseStorageDetailService.saveList(purchaseStorageDetailDtoList);
        }
        return result;
    }

    /**
     * 修改实体
     *
     * @param purchaseStorageDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PurchaseStorageDto purchaseStorageDto) {
        Boolean result = true, resultList = true;
        purchaseStorageDto.setUpdateTime(LocalDateTime.now());
        PurchaseStorage purchaseStorage = dozerMapper.map(purchaseStorageDto, PurchaseStorage.class);
        result = super.updateById(purchaseStorage);
        resultList = updateSub(purchaseStorageDto);
        return result && resultList;
    }

    /**
     * 修改子类实体
     *
     * @param purchaseStorageDto
     * @return
     */
    @Transactional
    @Override
    public Boolean updateSub(PurchaseStorageDto purchaseStorageDto) {
        Boolean result = true, detailResult = true;

        PurchaseStorage storage = dozerMapper.map(purchaseStorageDto,PurchaseStorage.class);
        purchaseStorageDetailService.deleteByStorageId(purchaseStorageDto.getId());

        List<PurchaseStorageDetailDto> detailDtoList = purchaseStorageDto.getDetailList();
        if(CollectionUtils.isNotEmpty(detailDtoList)){
            List<PurchaseStorageDetail> detailList = DozerUtils.mapList(dozerMapper,detailDtoList,PurchaseStorageDetail.class);
            detailList.forEach(detail -> {
                detail.setStorageId(storage.getId());
            });
            detailResult = purchaseStorageDetailService.saveOrUpdateBatch(detailList);
        }
        return result && detailResult;
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public void delete(Long id) {
        super.removeById(id);
        purchaseStorageDetailService.deleteByStorageId(id);
    }


    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        PurchaseStorageVo purchaseStorageVo = this.getByProc(procInstId);
        if (purchaseStorageVo != null) {
            this.delete(purchaseStorageVo.getId());
        }
    }

    @Override
    public PurchaseStorageVo getByProc(String procInstId) {
        LambdaQueryWrapper<PurchaseStorage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseStorage::getCamundaProcinsId, procInstId);
        List<PurchaseStorage> purchaseStorageList = super.list(queryWrapper);
        PurchaseStorageVo purchaseStorageVo = null;
        if (CollectionUtils.isNotEmpty(purchaseStorageList) && purchaseStorageList.size() > 0) {
            purchaseStorageVo = dozerMapper.map(purchaseStorageList.get(0), PurchaseStorageVo.class);
            //根据id获取maintStorageDetailVoList
            List<PurchaseStorageDetailVo> purchaseStorageDetailVoList = purchaseStorageDetailService.getByStorageId(purchaseStorageVo.getId());
            //设置maintStorageVo，以便构建其对应的子表数据
            purchaseStorageVo.setDetailList(purchaseStorageDetailVoList);
        }
        return purchaseStorageVo;
    }

    /**
     * 获取待入库库存物资信息
     *
     * @param assetsDetailDto assetsDetailDto
     * @return
     */
    @Override
    public Page getDetailByAssets(AssetsDetailDto assetsDetailDto) {
        SuppliesDetailDto suppliesDetailDto = new SuppliesDetailDto();


        Page<AssetsDetailVo> assetsDetailVoPage = assetsDetailMapper.getDetailByAssets(assetsDetailDto.getPage(),assetsDetailDto);
        List<SuppliesDetailVo> suppliesDetailVoPage = suppliesDetailMapper.getDetailBySupplies();
        List<AssetsDetailVo> suppliesDetailVoPage1 = DozerUtils.mapList(dozerMapper, suppliesDetailVoPage, AssetsDetailVo.class);

        List<AssetsDetailVo> records = assetsDetailVoPage.getRecords();
        records.addAll(suppliesDetailVoPage1);
        assetsDetailVoPage.setTotal(records.size());
        //List<AssetsDetailVo> collect = records.stream().filter(e -> e.getAcceptNumber().compareTo(e.getStorageNumber()) == 1).collect(Collectors.toList());
        //assetsDetailVoPage.setRecords(collect);
        log.debug("查询成功："+assetsDetailVoPage.getTotal());
        return assetsDetailVoPage;
    }


    private void verify(PurchaseStorageDto storageDto){
        PurchaseAcceptVo acceptVo = purchaseAcceptService.get(storageDto.getAcceptanceId());
        List<PurchaseStorageDetailDto> storageDetailDtoList = storageDto.getDetailList();

        BigDecimal sumStorageNumber = BigDecimal.ZERO;
        BigDecimal sumStorageAmount = BigDecimal.ZERO;

        for (PurchaseStorageDetailDto storageDetailDto : storageDetailDtoList){
            BigDecimal storageNumber = storageDetailDto.getStorageNumber();
            BigDecimal storageAmount = storageDetailDto.getStorageSumAmount();
            sumStorageNumber.add(storageNumber);
            sumStorageAmount.add(storageAmount);
            PurchaseAcceptDetailVo acceptDetailVo = purchaseAcceptDetailService.get(storageDetailDto.getSourceDetailId());
            if (ObjectUtil.isNotEmpty(acceptDetailVo)){
                String itemCode = storageDetailDto.getItemCode();
                if(ObjectUtil.isNotEmpty(itemCode)){
                    if(itemCode.equals(ItemTypeEnum.ASSET.getCode()) || itemCode.equals(ItemTypeEnum.INVENTORY.getCode())){
                        AssetsDetailVo assetsDetailVo = assetsDetailService.get(acceptDetailVo.getSourceId());
                        BigDecimal oldAcceptanceNumber = assetsDetailVo.getAcceptanceNumber();
                        BigDecimal oldStorageNumber = assetsDetailVo.getStorageNumber();
                        BigDecimal notStorageNUmber = oldAcceptanceNumber.subtract(oldStorageNumber);
                        if(storageNumber.compareTo(notStorageNUmber)==1){
                            throw new IncloudException("物资编码：" + storageDetailDto.getItemCode() + ",入库数量大于未入库数量！！");
                        }
                    }
                    if(itemCode.equals(ItemTypeEnum.ARTICLES.getCode()) || itemCode.equals(ItemTypeEnum.MATERIAL.getCode())){
                        SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get(acceptDetailVo.getSourceId());
                        BigDecimal oldAcceptanceNumber = suppliesDetailVo.getAcceptanceNumber();
                        BigDecimal oldStorageNumber = suppliesDetailVo.getStorageNumber();
                        BigDecimal notStorageNUmber = oldAcceptanceNumber.subtract(oldStorageNumber);
                        if(storageNumber.compareTo(notStorageNUmber)==1){
                            throw new IncloudException("物资编码：" + storageDetailDto.getItemCode() + ",入库数量大于未入库数量！！");
                        }
                    }
                }else{
                    throw new IncloudException("物资编码：" + storageDetailDto.getItemCode() + ",物资类型为空！！");
                }
//                BigDecimal notStorageNumber = acceptDetailVo.getNot
            }else{
                throw new IncloudException("物资编码：" + storageDetailDto.getItemCode() + ",没有查询到相关验收详情数据！！");
            }
        }
        BigDecimal notStorageNumber = acceptVo.getNotStorageNumber();
        BigDecimal notStorageAmount = acceptVo.getNotStorageAmount();
        if(sumStorageNumber.compareTo(notStorageNumber)==1){
            throw new IncloudException("申请单："+acceptVo.getCode()+"入库总数量大于验收单未入库数量!!");
        }
        if(sumStorageAmount.compareTo(notStorageAmount)==1){
            throw new IncloudException("申请单："+acceptVo.getCode()+"入库总金额大于验收单未入库金额!!");
        }

    }

    /**
     * 流程购置入库保存前，修改数量
     *
     * @param procInstId procInstId
     * @return Result
     */
    @Override
    @Transactional
    public Boolean purStorageSaveBefore(String procInstId) {
        Boolean result = true;
        //PurchaseStorageVo storageVo = this.getByProc(procInstId);
        return result;
    }


    /**
     * 流程购置入库保存后，修改数量
     *
     * @param procInstId procInstId
     * @return Result
     */
    @Override
    @Transactional
    public Boolean purStorageSaveAfter(String procInstId) {
        Boolean result = true;
        PurchaseStorageVo storageVo = this.getByProc(procInstId);
        PurchaseStorageDto storageDto = dozerMapper.map(storageVo,PurchaseStorageDto.class);
        Integer assetSource = storageDto.getAssetSource();
        if (ObjectUtil.isNotEmpty(assetSource) && assetSource == AssetSource.CAIGOUR.getCode()){
            this.verify(storageDto);
        }
        return result;
    }

    /**
     * 生成或添加  来源甲供、历史数据的 库存、耗材明细账
     * @param storageDetailVo
     */
    private void buildAsset(PurchaseStorageVo storageVo,PurchaseStorageDetailVo storageDetailVo) {
        String itemType = storageDetailVo.getItemType();
        BigDecimal storageNumber = storageDetailVo.getStorageNumber();
        if (StringUtils.isNotBlank(itemType)) {
            if (itemType.equals(ItemTypeEnum.ASSET.code) || itemType.equals(ItemTypeEnum.INVENTORY.code)) {

                LambdaQueryWrapper<Assets> assetsQueryWrapper = new LambdaQueryWrapper<>();
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(storageVo.getApplyUserOrgId()), Assets::getAssetOrgId, storageVo.getApplyUserOrgId());
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(storageVo.getApplyUserDeptId()), Assets::getAssetDeptId, storageVo.getApplyUserDeptId());
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(storageDetailVo.getItemId()), Assets::getItemId, storageDetailVo.getItemId());
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(storageDetailVo.getItemCode()), Assets::getItemCode, storageDetailVo.getItemCode());
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), Assets::getItemType, itemType);
//                assetsQueryWrapper.eq(Assets::getAssetSource, AssetSource.CAIGOUR.getCode());

                List<Assets> assetsList = assetsService.list(assetsQueryWrapper);
                AssetsDto assetsDto = new AssetsDto();
                if (CollectionUtils.isNotEmpty(assetsList) && assetsList.size() > 0) {//该类资产已经存在
                    Assets assets = assetsList.get(0);
                    assetsDto = dozerMapper.map(assets, AssetsDto.class);
                    //资产数量累加
                    BigDecimal oldStorageNumber = assetsDto.getStorageNumber();
                    oldStorageNumber = ObjectUtil.isEmpty(oldStorageNumber) ? BigDecimal.ZERO : oldStorageNumber;
                    BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
                    assetsDto.setStorageNumber(newStorageNumber);

                    BigDecimal oldStockNumber = assetsDto.getStockNumber();
                    oldStockNumber = ObjectUtil.isEmpty(oldStockNumber) ? BigDecimal.ZERO : oldStockNumber;
                    BigDecimal newStockNumber = oldStockNumber.add(storageNumber);
                    assetsDto.setStorageNumber(newStockNumber);

                    BigDecimal oldUsableNUmber = assetsDto.getUsableNumber();
                    oldUsableNUmber = ObjectUtil.isEmpty(oldUsableNUmber) ? BigDecimal.ZERO : oldUsableNUmber;
                    BigDecimal newUsableNUmber = oldUsableNUmber.add(storageNumber);
                    assetsDto.setStorageNumber(newUsableNUmber);
                    //回填资产类型
                    assetsDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                    assetsDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());
                    assetsService.update(assetsDto);

                } else {//不存在
                    assetsDto = dozerMapper.map(storageDetailVo, AssetsDto.class);
                    assetsDto.setId(null);
                    assetsDto.setSourceId(storageDetailVo.getId());

                    assetsDto.setAssetOrgId(storageVo.getApplyUserOrgId());
                    assetsDto.setAssetOrgName(storageVo.getApplyUserOrgName());
                    assetsDto.setAssetDeptId(storageVo.getApplyUserDeptId());
                    assetsDto.setAssetDeptName(storageVo.getApplyUserDeptName());

                    assetsDto.setAssetsNumber(BigDecimal.ZERO);
                    assetsDto.setStorageNumber(storageNumber);
                    assetsDto.setStockNumber(storageNumber);
                    assetsDto.setDeliveryNumber(BigDecimal.ZERO);
                    assetsDto.setAcceptNumber(BigDecimal.ZERO);
                    assetsDto.setBorrowNumber(BigDecimal.ZERO);
                    assetsDto.setTransferNumber(BigDecimal.ZERO);
                    assetsDto.setRepairNumber(BigDecimal.ZERO);
                    assetsDto.setScrappedNumber(BigDecimal.ZERO);
                    assetsDto.setAcceptanceNumber(BigDecimal.ZERO);
                    assetsDto.setUsableNumber(storageNumber);
                    assetsDto.setEntryNumber(BigDecimal.ZERO);
                    assetsDto.setLendNumber(BigDecimal.ZERO);
                    assetsDto.setAssetSource(storageDetailVo.getAssetSource());

                    assetsDto.setApplyTime(storageVo.getApplyTime());
                    assetsDto.setApplyUserId(storageVo.getApplyUserId());
                    assetsDto.setApplyUserName(storageVo.getApplyUserName());
                    assetsDto.setApplyUserOrgId(storageVo.getApplyUserOrgId());
                    assetsDto.setApplyUserOrgName(storageVo.getApplyUserOrgName());
                    assetsDto.setApplyUserDeptId(storageVo.getApplyUserDeptId());
                    //回填资产类型
                    assetsDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                    assetsDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());
                    assetsService.save(assetsDto);
                }

//                LambdaQueryWrapper<AssetsSku> assetsSkuQueryWrapper = new LambdaQueryWrapper<>();
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(storageVo.getApplyUserOrgId()), AssetsSku::getAssetOrgId, storageVo.getApplyUserOrgId());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(storageVo.getApplyUserDeptId()), AssetsSku::getAssetDeptId, storageVo.getApplyUserDeptId());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(storageDetailVo.getItemId()), AssetsSku::getItemId, storageDetailVo.getItemId());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(storageDetailVo.getItemCode()), AssetsSku::getItemCode, storageDetailVo.getItemCode());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), AssetsSku::getItemType, itemType);
//                assetsSkuQueryWrapper.eq(AssetsSku::getAssetSource, AssetSource.CAIGOUR.getCode());
////                assetsSkuQueryWrapper.in(ObjectUtil.isNotEmpty(storageDetailVo.getSkuCode()), storageDetailVo.getSkuCode(), AssetsSku::getSkuCodes);
//
//                List<AssetsSku> assetsSkuList = assetsSkuService.list(assetsSkuQueryWrapper);
//                AssetsSkuDto assetsSkuDto = new AssetsSkuDto();
//                if (assetsSkuList.size() > 0) {//该类资产已经存在
//                    AssetsSku assetsSku = assetsSkuList.get(0);
//                    assetsSkuDto = dozerMapper.map(assetsSku, AssetsSkuDto.class);
//                    //资产数量累加
//                    BigDecimal assetsNumber = assetsSku.getAssetsNumber().add(registerNumber);
//                    assetsSkuDto.setAssetsNumber(assetsNumber);
//                    result = assetsSkuService.update(assetsSkuDto);
//                } else {//不存在
//                    assetsSkuDto = dozerMapper.map(assetsVo, AssetsSkuDto.class);
//                    assetsSkuDto.setId(null);
//                    assetsSkuDto.setSourceId(assetsVo.getId());
//                    assetsSkuDto.setAssetsId(assetsDto.getId());
//
//                    assetsSkuDto.setAssetOrgId(purchaseApplyDetailDto.getApplyUserOrgId());
//                    assetsSkuDto.setAssetOrgName(purchaseApplyDetailDto.getApplyUserOrgName());
//                    assetsSkuDto.setAssetDeptId(purchaseApplyDetailDto.getApplyUserDeptId());
//                    assetsSkuDto.setAssetDeptName(purchaseApplyDetailDto.getApplyUserDeptName());
//
//                    assetsSkuDto.setAssetsNumber(registerNumber);
//                    assetsSkuDto.setStorageNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setStockNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setDeliveryNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setAcceptNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setBorrowNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setTransferNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setRepairNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setScrappedNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setAcceptanceNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setUsableNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setEntryNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setLendNumber(BigDecimal.ZERO);
//                    assetsSkuDto.setAssetSource(AssetSource.CAIGOUR.getCode());
//
//                    assetsSkuDto.setApplyTime(registerVo.getApplyTime());
//                    assetsSkuDto.setApplyUserId(registerVo.getApplyUserId());
//                    assetsSkuDto.setApplyUserName(registerVo.getApplyUserName());
//                    assetsSkuDto.setApplyUserOrgId(registerVo.getApplyUserOrgId());
//                    assetsSkuDto.setApplyUserOrgName(registerVo.getApplyUserOrgName());
//                    assetsSkuDto.setApplyUserDeptId(registerVo.getApplyUserDeptId());
//                    assetsSkuDto.setApplyUserDeptName(registerVo.getApplyUserDeptName());
//
//                    result = assetsSkuService.save(assetsSkuDto);


                Integer storageNumberCount = storageNumber.intValue();
                AssetsDetailDto assetsDetail = dozerMapper.map(storageDetailVo, AssetsDetailDto.class);
                assetsDetail.setSourceId(storageDetailVo.getId());
                assetsDetail.setAssetsId(assetsDto.getId());
//                assetsDetail.setAssetsSkuId(assetsSkuDto.getId());

                assetsDetail.setAssetOrgId(storageVo.getApplyUserOrgId());
                assetsDetail.setAssetOrgName(storageVo.getApplyUserOrgName());
                assetsDetail.setAssetDeptId(storageVo.getApplyUserDeptId());
                assetsDetail.setAssetDeptName(storageVo.getApplyUserDeptName());

                assetsDetail.setItemType(itemType);
                assetsDetail.setAssetsNumber(BigDecimal.ONE);
                assetsDetail.setAcceptanceNumber(BigDecimal.ZERO);
                assetsDetail.setDeliveryNumber(BigDecimal.ZERO);
                assetsDetail.setAcceptNumber(BigDecimal.ZERO);
                assetsDetail.setBorrowNumber(BigDecimal.ZERO);
                assetsDetail.setTransferNumber(BigDecimal.ZERO);
                assetsDetail.setRepairNumber(BigDecimal.ZERO);
                assetsDetail.setScrappedNumber(BigDecimal.ZERO);
                assetsDetail.setAcceptanceNumber(BigDecimal.ZERO);
                assetsDetail.setEntryNumber(BigDecimal.ZERO);
                assetsDetail.setLendNumber(BigDecimal.ZERO);

                assetsDetail.setTaxRate(storageDetailVo.getStorageTaxRate());
                assetsDetail.setAssetsAmount(storageDetailVo.getStorageAmount());
                assetsDetail.setAssetsTaxAmount(storageDetailVo.getStorageTaxAmount());
                assetsDetail.setAssetsUntaxedAmount(storageDetailVo.getStorageUntaxedAmount());
                assetsDetail.setAssetsSumAmount(storageDetailVo.getStorageSumAmount());
                assetsDetail.setAssetsSumTaxAmount(storageDetailVo.getStorageSumTaxAmount());
                assetsDetail.setAssetsSumUntaxedAmount(storageDetailVo.getStorageSumUntaxedAmount());
                assetsDetail.setAssetSource(storageDetailVo.getAssetSource());

                assetsDetail.setApplyTime(storageVo.getApplyTime());
                assetsDetail.setApplyUserId(storageVo.getApplyUserId());
                assetsDetail.setApplyUserName(storageVo.getApplyUserName());
                assetsDetail.setApplyUserOrgId(storageVo.getApplyUserOrgId());
                assetsDetail.setApplyUserOrgName(storageVo.getApplyUserOrgName());
                assetsDetail.setApplyUserDeptId(storageVo.getApplyUserDeptId());
                assetsDetail.setApplyUserDeptName(storageVo.getApplyUserDeptName());
                //回填资产类型
                assetsDetail.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                assetsDetail.setClassifyTypeName(storageDetailVo.getClassifyTypeName());
                assetsDetail.setStatus("已入库");

//                assetsDetailList.add(assetsDetail);
                for(int i = 0 ; i < storageNumberCount ; i ++){
                    assetsDetail.setId(null);
                    assetsDetail.setStorageNumber(BigDecimal.ONE);
                    assetsDetail.setStockNumber(BigDecimal.ONE);
                    assetsDetail.setUsableNumber(BigDecimal.ONE);
                    assetsDetailService.save(assetsDetail);
                }

                daybookService.log(storageVo.getId(),storageVo.getCamundaProcinsId(),
                        dozerMapper.map(storageDetailVo, AssetsDetailDto.class),DayBookType.STORAGE);
            } else if (itemType.equals(ItemTypeEnum.ARTICLES.code) || itemType.equals(ItemTypeEnum.MATERIAL.code)) {

                LambdaQueryWrapper<Supplies> assetsQueryWrapper = new LambdaQueryWrapper<>();
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(storageVo.getApplyUserOrgId()), Supplies::getAssetOrgId, storageVo.getApplyUserOrgId());
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(storageVo.getApplyUserDeptId()), Supplies::getAssetDeptId, storageVo.getApplyUserDeptId());
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(storageDetailVo.getItemId()), Supplies::getItemId, storageDetailVo.getItemId());
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(storageDetailVo.getItemCode()), Supplies::getItemCode, storageDetailVo.getItemCode());
                assetsQueryWrapper.eq(Supplies::getAssetSource, AssetSource.CAIGOUR.getCode());
                assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), Supplies::getItemType, itemType);

                List<Supplies> suppliesList = suppliesService.list(assetsQueryWrapper);
                SuppliesDto suppliesDto = new SuppliesDto();
                if (suppliesList.size() > 0) {//该类资产已经存在
                    Supplies supplies = suppliesList.get(0);
                    suppliesDto = dozerMapper.map(supplies, SuppliesDto.class);

                    //资产数量累加
                    BigDecimal oldStorageNumber = suppliesDto.getStorageNumber();
                    oldStorageNumber = ObjectUtil.isEmpty(oldStorageNumber) ? BigDecimal.ZERO : oldStorageNumber;
                    BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
                    suppliesDto.setStorageNumber(newStorageNumber);

                    BigDecimal oldStockNumber = suppliesDto.getStockNumber();
                    oldStockNumber = ObjectUtil.isEmpty(oldStockNumber) ? BigDecimal.ZERO : oldStockNumber;
                    BigDecimal newStockNumber = oldStockNumber.add(storageNumber);
                    suppliesDto.setStorageNumber(newStockNumber);

                    BigDecimal oldUsableNUmber = suppliesDto.getUsableNumber();
                    oldUsableNUmber = ObjectUtil.isEmpty(oldUsableNUmber) ? BigDecimal.ZERO : oldUsableNUmber;
                    BigDecimal newUsableNUmber = oldUsableNUmber.add(storageNumber);
                    suppliesDto.setStorageNumber(newUsableNUmber);
                    //回填资产类型
                    suppliesDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                    suppliesDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());
                    suppliesService.update(suppliesDto);

                } else {//不存在
                    suppliesDto = dozerMapper.map(storageDetailVo, SuppliesDto.class);
                    suppliesDto.setId(null);
                    suppliesDto.setSourceId(storageDetailVo.getId());

                    suppliesDto.setAssetOrgId(storageVo.getApplyUserOrgId());
                    suppliesDto.setAssetOrgName(storageVo.getApplyUserOrgName());
                    suppliesDto.setAssetDeptId(storageVo.getApplyUserDeptId());
                    suppliesDto.setAssetDeptName(storageVo.getApplyUserDeptName());

                    suppliesDto.setAssetsNumber(BigDecimal.ZERO);
                    suppliesDto.setStorageNumber(storageNumber);
                    suppliesDto.setStockNumber(storageNumber);

                    suppliesDto.setDeliveryNumber(BigDecimal.ZERO);
                    suppliesDto.setAcceptNumber(BigDecimal.ZERO);
                    suppliesDto.setBorrowNumber(BigDecimal.ZERO);
                    suppliesDto.setTransferNumber(BigDecimal.ZERO);
                    suppliesDto.setRepairNumber(BigDecimal.ZERO);
                    suppliesDto.setScrappedNumber(BigDecimal.ZERO);
                    suppliesDto.setAcceptanceNumber(BigDecimal.ZERO);
                    suppliesDto.setUsableNumber(storageNumber);
                    suppliesDto.setEntryNumber(BigDecimal.ZERO);
                    suppliesDto.setLendNumber(BigDecimal.ZERO);
                    suppliesDto.setAssetSource(storageDetailVo.getAssetSource());

                    suppliesDto.setApplyTime(storageVo.getApplyTime());
                    suppliesDto.setApplyUserId(storageVo.getApplyUserId());
                    suppliesDto.setApplyUserName(storageVo.getApplyUserName());
                    suppliesDto.setApplyUserOrgId(storageVo.getApplyUserOrgId());
                    suppliesDto.setApplyUserOrgName(storageVo.getApplyUserOrgName());
                    suppliesDto.setApplyUserDeptId(storageVo.getApplyUserDeptId());
                    suppliesDto.setApplyUserDeptName(storageVo.getApplyUserDeptName());

                    //回填资产类型
                    suppliesDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                    suppliesDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());

                    suppliesService.save(suppliesDto);
                }


//                LambdaQueryWrapper<SuppliesSku> assetsSkuQueryWrapper = new LambdaQueryWrapper<>();
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserOrgId()), SuppliesSku::getAssetOrgId, acceptVo.getApplyUserOrgId());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(acceptVo.getApplyUserDeptId()), SuppliesSku::getAssetDeptId, acceptVo.getApplyUserDeptId());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemId()), SuppliesSku::getItemId, detailVo.getItemId());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getItemCode()), SuppliesSku::getItemCode, detailVo.getItemCode());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), SuppliesSku::getItemType, itemType);
//                assetsSkuQueryWrapper.eq(SuppliesSku::getAssetSource, AssetSource.CAIGOUR.getCode());
//                assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(detailVo.getSkuCode()), SuppliesSku::getSkuCodes, detailVo.getSkuCode());
//
//                List<SuppliesSku> suppliesSkuList = suppliesSkuService.list(assetsSkuQueryWrapper);
//                SuppliesSkuDto suppliesSkuDto = new SuppliesSkuDto();
//                if (suppliesSkuList.size() > 0) {//该类资产已经存在
//                    SuppliesSku suppliesSku = suppliesSkuList.get(0);
//                    suppliesSkuDto = dozerMapper.map(suppliesSku, SuppliesSkuDto.class);
//                    //资产数量累加
//                    BigDecimal assetsNumber = suppliesSkuDto.getAssetsNumber().add(acceptanceNumber);
//                    suppliesSkuDto.setAssetsNumber(assetsNumber);
//
//                    //验收数量累加
//                    BigDecimal newAcceptanceNumber = suppliesSkuDto.getAcceptanceNumber().add(acceptanceNumber);
//                    suppliesSkuDto.setAcceptanceNumber(newAcceptanceNumber);
//
//                    result = suppliesSkuService.update(suppliesSkuDto);
//                } else {//不存在
//                    suppliesSkuDto = dozerMapper.map(detailVo, SuppliesSkuDto.class);
//                    suppliesSkuDto.setId(null);
//                    suppliesSkuDto.setSourceId(detailVo.getAcceptanceAssetsId());
//                    suppliesSkuDto.setAssetsId(suppliesDto.getId());
//
//                    suppliesSkuDto.setAssetOrgId(acceptVo.getApplyUserOrgId());
//                    suppliesSkuDto.setAssetOrgName(acceptVo.getApplyUserOrgName());
//                    suppliesSkuDto.setAssetDeptId(acceptVo.getApplyUserDeptId());
//                    suppliesSkuDto.setAssetDeptName(acceptVo.getApplyUserDeptName());
//
//                    suppliesSkuDto.setAssetsNumber(acceptanceNumber);
//                    suppliesSkuDto.setStorageNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setStockNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setDeliveryNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setAcceptNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setBorrowNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setTransferNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setRepairNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setScrappedNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setAcceptanceNumber(acceptanceNumber);
//                    suppliesSkuDto.setUsableNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setEntryNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setLendNumber(BigDecimal.ZERO);
//                    suppliesSkuDto.setAssetSource(AssetSource.CAIGOUR.getCode());
//
//                    suppliesSkuDto.setApplyTime(acceptVo.getApplyTime());
//                    suppliesSkuDto.setApplyUserId(acceptVo.getApplyUserId());
//                    suppliesSkuDto.setApplyUserName(acceptVo.getApplyUserName());
//                    suppliesSkuDto.setApplyUserOrgId(acceptVo.getApplyUserOrgId());
//                    suppliesSkuDto.setApplyUserOrgName(acceptVo.getApplyUserOrgName());
//                    suppliesSkuDto.setApplyUserDeptId(acceptVo.getApplyUserDeptId());
//                    suppliesSkuDto.setApplyUserDeptName(acceptVo.getApplyUserDeptName());
//
//                    result = suppliesSkuService.save(suppliesSkuDto);
//                }
                //List<PurchaseAcceptDetailVo> detailList = purchaseAcceptDetailService.getByAcceptanceAssetsId(detailVo.getAcceptanceAssetsId());
                //物资来源
                //Integer assetSrource = acceptVo.getAssetSource();
//                    if(detailList.size()>0){
//                        for (PurchaseAcceptDetailVo detailVo : detailList) {

                SuppliesDetailDto suppliesDetailDto = dozerMapper.map(storageDetailVo, SuppliesDetailDto.class);
                suppliesDetailDto.setId(null);
                suppliesDetailDto.setSourceId(storageDetailVo.getId());
                suppliesDetailDto.setAssetsId(suppliesDto.getId());
//                suppliesDetailDto.setAssetsSkuId(suppliesSkuDto.getId());
                suppliesDetailDto.setItemType(itemType);

                suppliesDetailDto.setAssetOrgId(storageVo.getApplyUserOrgId());
                suppliesDetailDto.setAssetOrgName(storageVo.getApplyUserOrgName());
                suppliesDetailDto.setAssetDeptId(storageVo.getApplyUserDeptId());
                suppliesDetailDto.setAssetDeptName(storageVo.getApplyUserDeptName());

                suppliesDetailDto.setAssetsNumber(BigDecimal.ZERO);
                suppliesDetailDto.setAcceptanceNumber(BigDecimal.ZERO);
                suppliesDetailDto.setStorageNumber(storageNumber);
                suppliesDetailDto.setStockNumber(storageNumber);
                suppliesDetailDto.setDeliveryNumber(BigDecimal.ZERO);
                suppliesDetailDto.setAcceptNumber(BigDecimal.ZERO);
                suppliesDetailDto.setBorrowNumber(BigDecimal.ZERO);
                suppliesDetailDto.setTransferNumber(BigDecimal.ZERO);
                suppliesDetailDto.setRepairNumber(BigDecimal.ZERO);
                suppliesDetailDto.setScrappedNumber(BigDecimal.ZERO);
                suppliesDetailDto.setUsableNumber(storageNumber);
                suppliesDetailDto.setEntryNumber(BigDecimal.ZERO);
                suppliesDetailDto.setLendNumber(BigDecimal.ZERO);

                suppliesDetailDto.setTaxRate(storageDetailVo.getStorageTaxRate());
                suppliesDetailDto.setAssetsAmount(storageDetailVo.getStorageAmount());
                suppliesDetailDto.setAssetsTaxAmount(storageDetailVo.getStorageTaxAmount());
                suppliesDetailDto.setAssetsUntaxedAmount(storageDetailVo.getStorageUntaxedAmount());
                suppliesDetailDto.setAssetsSumAmount(storageDetailVo.getStorageSumAmount());
                suppliesDetailDto.setAssetsSumTaxAmount(storageDetailVo.getStorageSumTaxAmount());
                suppliesDetailDto.setAssetsSumUntaxedAmount(storageDetailVo.getStorageSumUntaxedAmount());

                suppliesDetailDto.setAssetSource(storageDetailVo.getAssetSource());

                suppliesDetailDto.setApplyTime(storageVo.getApplyTime());
                suppliesDetailDto.setApplyUserId(storageVo.getApplyUserId());
                suppliesDetailDto.setApplyUserName(storageVo.getApplyUserName());
                suppliesDetailDto.setApplyUserOrgId(storageVo.getApplyUserOrgId());
                suppliesDetailDto.setApplyUserOrgName(storageVo.getApplyUserOrgName());
                suppliesDetailDto.setApplyUserDeptId(storageVo.getApplyUserDeptId());
                suppliesDetailDto.setApplyUserDeptName(storageVo.getApplyUserDeptName());
                //回填资产类型
                suppliesDetailDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                suppliesDetailDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());
                suppliesDetailService.save(suppliesDetailDto);

                daybookSuppliesService.log(storageVo.getId(), storageVo.getCamundaProcinsId(), suppliesDetailDto, DayBookType.STORAGE);
            }
        }
    }

    /**
     * 回填验收单数量
     * @param storageVo
     * @return
     */
    private Boolean setAcceptanceStatus(PurchaseStorageVo storageVo){
        BigDecimal storageNumber = storageVo.getSumTotalNumber();
        BigDecimal storageAmount = storageVo.getSumTotalAmount();

        PurchaseAcceptVo acceptVo = purchaseAcceptService.get(storageVo.getAcceptanceId());
        if(ObjectUtil.isNotEmpty(acceptVo)){
            PurchaseAcceptDto acceptDto = dozerMapper.map(acceptVo,PurchaseAcceptDto.class);


            BigDecimal oldStorageNumber = acceptDto.getStorageNumber();
            oldStorageNumber = ObjectUtil.isEmpty(oldStorageNumber) ? BigDecimal.ZERO : oldStorageNumber;
            acceptDto.setStorageNumber(oldStorageNumber.add(storageNumber));

            BigDecimal notStorageNumber = acceptDto.getNotStorageNumber();
            notStorageNumber = ObjectUtil.isEmpty(notStorageNumber) ? acceptDto.getSumTotalNumber() : notStorageNumber;
            acceptDto.setNotStorageNumber(notStorageNumber.subtract(storageNumber));

            BigDecimal notStorageAmount = acceptDto.getNotStorageAmount();
            notStorageAmount = ObjectUtil.isEmpty(notStorageAmount) ? acceptDto.getSumTotalAmount() : notStorageAmount;
            acceptDto.setNotStorageAmount(notStorageAmount.subtract(storageAmount));

            if(acceptDto.getNotStorageNumber().compareTo(BigDecimal.ZERO) == 0){
                acceptDto.setStatus(1);
            }else{
                acceptDto.setStatus(2);
            }
            return purchaseAcceptService.update(acceptDto);
        }
        return true;
    }
    /**
     * 购置入库流程结束后，修改数量
     *
     * @param procInstId procInstId
     * @return Result
     */
    @Override
    @Transactional
    public Boolean purStorageAuditSuccess(String procInstId) {
        PurchaseStorageVo storageVo = this.getByProc(procInstId);

        List<PurchaseStorageDetailVo> storageDetailVoList = storageVo.getDetailList();
        Boolean result = true;


        String assetSourceStr = storageVo.getAssetSource();
        Integer assetSource = Integer.valueOf(assetSourceStr);
        setAcceptanceStatus(storageVo);
        //流程结束后，修改入库数量和可用数量
        for (PurchaseStorageDetailVo storageDetailVo : storageDetailVoList) {
            String itemType = storageDetailVo.getItemType();
            BigDecimal storageNumber = storageDetailVo.getStorageNumber();
            //物资来源于甲供或者历史数据，生成资产明细账
            if(ObjectUtil.isNotEmpty(assetSource)
                    && (assetSource==AssetSource.JIAGONG.getCode()
                        || assetSource==AssetSource.HISTORY.getCode())){
                buildAsset(storageVo,storageDetailVo);
            }else {

                if (!StringUtils.isBlank(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {
                    AssetsVo assetsVo = assetsService.get(storageDetailVo.getAssetsId());
                    if (ObjectUtil.isNotEmpty(assetsVo)) {

//                        PurchaseRegisterAssetsVo registerAssetsVo = purchaseRegisterAssetsService.get(assetsVo.getSourceId());
//                        if (ObjectUtil.isNotEmpty(registerAssetsVo)){
//                            PurchaseApplyDetailVo purchaseApplyDetailVo = purchaseApplyDetailService.get(registerAssetsVo.getApplyDetailId());
//                            PurchaseApplyDetailDto applyDetailDto = dozerMapper.map(purchaseApplyDetailVo, PurchaseApplyDetailDto.class);
//                            BigDecimal oldStorageNumber = applyDetailDto.getStorageNumber();
//                            BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
//                            applyDetailDto.setStorageNumber(newStorageNumber);
//                            log.debug("购置申请详情:{},入库数量:->{}", applyDetailDto.getId(), newStorageNumber, applyDetailDto);
//                            result = purchaseApplyDetailService.update(applyDetailDto);
//                        }

                        AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                        BigDecimal oldStorageNumber = assetsDto.getStorageNumber();
                        BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
                        assetsDto.setStorageNumber(newStorageNumber);

                        BigDecimal oldUsableNumber = assetsDto.getUsableNumber();
                        BigDecimal newUsableNumber = oldUsableNumber.add(storageNumber);
                        assetsDto.setUsableNumber(newUsableNumber);

                        BigDecimal oldStockNumber = assetsDto.getStockNumber();
                        BigDecimal newStockNumber = oldStockNumber.add(storageNumber);
                        assetsDto.setStockNumber(newStockNumber);
                        //回填资产类型
                        assetsDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                        assetsDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());

                        log.debug("资产台账:{},现有入库数量:->{},库存数量->{}", assetsDto.getId(), newStorageNumber, newStockNumber);
                        result = assetsService.update(assetsDto);
                    }


                    if (ObjectUtil.isNotEmpty(storageDetailVo.getSourceAssetsId())) {
                        PurchaseAcceptAssetVo purchaseAcceptAssetVo = purchaseAcceptAssetService.get(storageDetailVo.getSourceAssetsId());
                        if (ObjectUtil.isNotEmpty(purchaseAcceptAssetVo)){
                            PurchaseRegisterResultVo registerResultVo = purchaseRegisterResultService.get(purchaseAcceptAssetVo.getRegisterResultId());
                            if (ObjectUtil.isNotEmpty(registerResultVo)){
                                PurchaseRegisterAssetsVo registerAssetsVo = purchaseRegisterAssetsService.get(registerResultVo.getRegisterAssetsId());
                                if (ObjectUtil.isNotEmpty(registerAssetsVo)){
                                    PurchaseApplyDetailVo applyDetailVo = purchaseApplyDetailService.get(registerAssetsVo.getApplyDetailId());
                                    if (ObjectUtil.isNotEmpty(applyDetailVo)) {
                                        PurchaseApplyDetailDto applyDetailDto = dozerMapper.map(applyDetailVo, PurchaseApplyDetailDto.class);
                                        BigDecimal oldStorageNumber = applyDetailDto.getStorageNumber();
                                        BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
                                        applyDetailDto.setStorageNumber(newStorageNumber);
                                        log.debug("购置申请详情:{},入库数量:->{}", applyDetailDto.getId(), newStorageNumber, applyDetailDto);
                                        result = purchaseApplyDetailService.update(applyDetailDto);
                                    }
                                }
                            }
                        }

                        AssetsDetailVo assetsDetailVo = assetsDetailService.get(storageDetailVo.getAssetsDetailId());
                        AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                        BigDecimal oldStorageNumber = assetsDetailDto.getStorageNumber();
                        BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
                        assetsDetailDto.setStorageNumber(newStorageNumber);

                        BigDecimal oldUsableNumber = assetsDetailDto.getUsableNumber();
                        BigDecimal newUsableNumber = oldUsableNumber.add(storageNumber);
                        assetsDetailDto.setUsableNumber(newUsableNumber);

                        BigDecimal oldStockNumber = assetsDetailDto.getStockNumber();
                        BigDecimal newStockNumber = oldStockNumber.add(storageNumber);
                        assetsDetailDto.setStockNumber(newStockNumber);
                        assetsDetailDto.setStatus("已入库");
                        assetsDetailDto.setAssetSelfCode(storageDetailVo.getAssetSelfCode());
                        assetsDetailDto.setBarCode(storageDetailVo.getBarCode());
                        assetsDetailDto.setBarCodeFile(storageDetailVo.getBarCodeFile());
                        //回填资产类型
                        assetsDetailDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                        assetsDetailDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());

                        log.debug("资产明细:{},现有验收数量:->{},库存数量->{}", assetsDetailDto.getId(), newStorageNumber, newStockNumber);
                        result = assetsDetailService.update(assetsDetailDto);

                        daybookService.log(storageVo.getId(), storageVo.getCamundaProcinsId(), assetsDetailDto, DayBookType.STORAGE);
                    }
                } else {
                    SuppliesVo suppliesVo = suppliesService.get(storageDetailVo.getAssetsId());
                    if (ObjectUtil.isNotEmpty(suppliesVo)) {
                        SuppliesDto suppliesDto = dozerMapper.map(suppliesVo, SuppliesDto.class);
                        BigDecimal oldStorageNumber = suppliesDto.getStorageNumber();
                        BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
                        suppliesDto.setStorageNumber(newStorageNumber);

                        BigDecimal oldUsableNumber = suppliesDto.getUsableNumber();
                        BigDecimal newUsableNumber = oldUsableNumber.add(storageNumber);
                        suppliesDto.setUsableNumber(newUsableNumber);

                        BigDecimal oldStockNumber = suppliesDto.getStockNumber();
                        BigDecimal newStockNumber = oldStockNumber.add(storageNumber);
                        suppliesDto.setStockNumber(newStockNumber);
                        //回填资产类型
                        suppliesDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                        suppliesDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());
                        log.debug("资产台账:{},现有入库数量:->{},库存数量->{}", suppliesDto.getId(), newStorageNumber, newStockNumber);
                        result = suppliesService.update(suppliesDto);
                    }
                    SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get(storageDetailVo.getAssetsDetailId());
                    if (ObjectUtil.isNotEmpty(suppliesDetailVo)) {

                        PurchaseAcceptDetailVo acceptDetailVo = purchaseAcceptDetailService.get(suppliesDetailVo.getSourceId());
                        if (ObjectUtil.isNotEmpty(acceptDetailVo)) {
                            PurchaseRegisterResultVo purchaseRegisterResultVo = purchaseRegisterResultService.get(acceptDetailVo.getRegisterResultId());
                            if (ObjectUtil.isNotEmpty(purchaseRegisterResultVo)) {
                                PurchaseRegisterAssetsVo purchaseRegisterAssetsVo = purchaseRegisterAssetsService.get(purchaseRegisterResultVo.getRegisterAssetsId());
                                if (ObjectUtil.isNotEmpty(purchaseRegisterAssetsVo)) {
                                    PurchaseApplyDetailVo purchaseApplyDetailVo = purchaseApplyDetailService.get(purchaseRegisterAssetsVo.getApplyDetailId());
                                    PurchaseApplyDetailDto applyDetailDto = dozerMapper.map(purchaseApplyDetailVo, PurchaseApplyDetailDto.class);
                                    BigDecimal oldStorageNumber = applyDetailDto.getStorageNumber();
                                    BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
                                    applyDetailDto.setStorageNumber(newStorageNumber);
                                    result = purchaseApplyDetailService.update(applyDetailDto);
                                }
                            }
                        }

                        SuppliesDetailDto suppliesDetailDto = dozerMapper.map(suppliesDetailVo, SuppliesDetailDto.class);
                        BigDecimal oldStorageNumber = suppliesDetailDto.getStorageNumber();
                        BigDecimal newStorageNumber = oldStorageNumber.add(storageNumber);
                        suppliesDetailDto.setStorageNumber(newStorageNumber);

                        BigDecimal oldUsableNumber = suppliesDetailDto.getUsableNumber();
                        BigDecimal newUsableNumber = oldUsableNumber.add(storageNumber);
                        suppliesDetailDto.setUsableNumber(newUsableNumber);

                        BigDecimal oldStockNumber = suppliesDetailDto.getStockNumber();
                        BigDecimal newStockNumber = oldStockNumber.add(storageNumber);
                        suppliesDetailDto.setStockNumber(newStockNumber);
                        suppliesDetailDto.setBarCode(storageDetailVo.getBarCode());
                        suppliesDetailDto.setBarCodeFile(storageDetailVo.getBarCodeFile());
                        //回填资产类型
                        suppliesDetailDto.setClassifyTypeCode(storageDetailVo.getClassifyTypeCode());
                        suppliesDetailDto.setClassifyTypeName(storageDetailVo.getClassifyTypeName());
                        log.debug("资产明细:{},现有验收数量:->{},库存数量->{}", suppliesDetailDto.getId(), newStorageNumber, newStockNumber);
                        result = suppliesDetailService.update(suppliesDetailDto);

                        daybookSuppliesService.log(storageVo.getId(), storageVo.getCamundaProcinsId(), suppliesDetailDto, DayBookType.STORAGE);
                    }
                }
            }
        }
        return result;
    }

    @Override
    @Transactional
    public PurchaseStorageVo procSaveOrUpdate(PurchaseStorageDto purchaseStorageDto) {

        PurchaseStorageVo purchaseStorageVo = this.get(purchaseStorageDto.getId());
        //判断是否未编辑、是否是起草
        //若不是起草(流程中保存)，手动调用保存前方法
        if (ObjectUtil.isNotEmpty(purchaseStorageVo)
                && ObjectUtil.isNotEmpty(purchaseStorageVo.getAuditStatus())
                && purchaseStorageVo.getAuditStatus() != WfProcessEnum.DRAFT.getType()){
            this.purStorageSaveBefore(purchaseStorageVo.getCamundaProcinsId());
        }
        //获取申请编号
        if(StringUtils.isBlank(purchaseStorageDto.getCode())){
            getMaxCode(purchaseStorageDto);
        }

        PurchaseStorage storage = dozerMapper.map(purchaseStorageDto,PurchaseStorage.class);
        super.saveOrUpdate(storage);
        purchaseStorageDetailService.deleteByStorageId(purchaseStorageDto.getId());

        List<PurchaseStorageDetailDto> detailDtoList = purchaseStorageDto.getDetailList();
        if(CollectionUtils.isNotEmpty(detailDtoList)){
            List<PurchaseStorageDetail> detailList = DozerUtils.mapList(dozerMapper,detailDtoList,PurchaseStorageDetail.class);
            detailList.forEach(detail -> {
                detail.setStorageId(storage.getId());
                detail.setAssetSource(purchaseStorageDto.getAssetSource());
                String assetsCode = detail.getAssetsCode();
                String itemType = detail.getItemType();
                if (ObjectUtil.isEmpty(assetsCode) && ObjectUtil.isNotEmpty(itemType)
                        && (itemType.equals(ItemTypeEnum.ASSET.code) || itemType.equals(ItemTypeEnum.INVENTORY.code)) ){
                    ObjectMapper objectMapper = new ObjectMapper();
                    HashMap entityMap = objectMapper.convertValue(detail, HashMap.class);
                    try {
                        assetsCode = dictClient.getRuleValue("purchase_accept_detail", "assetsCode", entityMap);
                        detail.setAssetsCode(assetsCode);
                        log.debug("资产编号:{}", assetsCode);
                    } catch (Exception e) {
                        log.debug("资产编码远程调用失败！！");
                    }
                }
            });
            purchaseStorageDetailService.saveOrUpdateBatch(detailList);
        }

        return this.getByProc(storage.getCamundaProcinsId());
    }

    /**
     * 获取验收后待入库物资
     *
     * @param purchaseStorageDto
     * @return
     */
    @Override
    public Page getAcceptList(PurchaseStorageDto purchaseStorageDto) {
        LambdaQueryWrapper<PurchaseAccept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseAccept::getAuditStatus, WfProcessEnum.DONE.getType());
        queryWrapper.ne(PurchaseAccept::getStatus,1);
        queryWrapper.orderByDesc(PurchaseAccept::getApplyTime);

        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(PurchaseAccept::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(PurchaseAccept::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(PurchaseAccept::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        queryWrapper.orderByDesc(PurchaseAccept::getApplyTime);
        //指定的查询字段
        String  searchCondition= purchaseStorageDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.like(PurchaseAccept::getCode,searchCondition)
                    .or()
                    .like(PurchaseAccept::getApplyUserName,searchCondition)
                    .or()
                    .like(PurchaseAccept::getApplyUserDeptName,searchCondition)
                    .or()
                    .like(PurchaseAccept::getApplyUserOrgName,searchCondition)
                    .or()
                    .like(PurchaseAccept::getApplyTime,searchCondition)
                    .or()
                    .like(PurchaseAccept::getSumTotalAmount,searchCondition)
                    .or()
                    .like(PurchaseAccept::getPurchaseType,searchCondition);

        }

        Page<PurchaseAccept> page = purchaseAcceptService.page(purchaseStorageDto.getPage(),queryWrapper);
        Page<PurchaseAcceptVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseAcceptVo.class);
        return pageVo;
    }

    @Override
    public Boolean isEndByDraft(ExpressionEntity expressionEntity) {
        Boolean result = false;
//        List<HandleExpressionParam> argList = expressionEntity.getArgList();
//        if(CollectionUtils.isNotEmpty(argList) && argList.size() > 0) {
//            for (HandleExpressionParam arg : argList) {
//                String paramId = arg.getParamId();
//                Object paramValue = arg.getParamValue();
//                String paramType = arg.getParamType();
//                if(ObjectUtil.isNotEmpty(paramValue)){
//                    try {
//                        Integer assetSource = Integer.valueOf(paramValue.toString());
//                        if(ObjectUtil.isNotEmpty(assetSource) && assetSource == AssetSource.CAIGOUR.getCode()){
//                            result = true;
//                        }
//                    }catch (NumberFormatException e){
//                        result = false;
//                    }
//                }
//            }
//        }
        try {
            Map<String,String> map = expressionEntity.getArgs();
            String assetSourceStr = map.get("assetSource");
            Integer assetSource = Integer.valueOf(assetSourceStr);
            if(assetSource == AssetSource.CAIGOUR.getCode()){
                result = true;
                return true;
            }
        }catch (Exception e){
            result = false;
            return false;
        }
        return result;
    }

    @Override
    public Boolean testCondition(String procInstId) {
        PurchaseStorageVo storageVo = this.getByProc(procInstId);
        if(ObjectUtil.isNotEmpty(storageVo) && ObjectUtil.isNotEmpty(storageVo.getAssetSource())){
            String assetSourceStr = storageVo.getAssetSource();
            Integer assetsSource = Integer.valueOf(assetSourceStr);
            if(assetsSource == AssetSource.CAIGOUR.getCode()){
                return true;
            }
        }
        return false;
    }
}
