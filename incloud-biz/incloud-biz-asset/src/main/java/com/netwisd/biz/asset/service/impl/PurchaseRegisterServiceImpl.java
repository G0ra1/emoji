package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.AppUserUtil;
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
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.asset.mapper.PurchaseRegisterMapper;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 采购信息登记 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-09 09:41:26
 */
@Service
@Slf4j
public class PurchaseRegisterServiceImpl extends BatchServiceImpl<PurchaseRegisterMapper, PurchaseRegister> implements PurchaseRegisterService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseRegisterMapper purchaseRegisterMapper;

    @Autowired
    private PurchaseRegisterAssetsService purchaseRegisterAssetsService;

    @Autowired
    private PurchaseRegisterDetailService purchaseRegisterDetailService;

    @Autowired
    private PurchaseRegisterResultService purchaseRegisterResultService;

    @Autowired
    private PurchaseApplyService purchaseApplyService;

    @Autowired
    private PurchaseApplyDetailService purchaseApplyDetailService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsSkuService assetsSkuService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private DaybookService daybookService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private ViewerService viewerService;


    /**
     * 单表简单查询操作
     * @param purchaseRegisterDto
     * @return
     */
    @Override
    public Page list(PurchaseRegisterDto purchaseRegisterDto) {
        LambdaQueryWrapper<PurchaseRegister> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
//        queryWrapper.eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getApplyUserOrgName()),PurchaseRegister::getApplyUserOrgName,purchaseRegisterDto.getApplyUserOrgName())
//                .eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getApplyUserDeptName()),PurchaseRegister::getApplyUserDeptName,purchaseRegisterDto.getApplyUserDeptName())
//                .eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getApplyUserName()),PurchaseRegister::getApplyUserName,purchaseRegisterDto.getApplyUserName())
//                .eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getCode()),PurchaseRegister::getCode,purchaseRegisterDto.getCode())
//                .eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getPlanYear()),PurchaseRegister::getPlanYear,purchaseRegisterDto.getPlanYear());
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(PurchaseRegister::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                        .eq(PurchaseRegister::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                        .eq(PurchaseRegister::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        queryWrapper.orderByDesc(PurchaseRegister::getApplyTime);
        //指定的查询字段
        String  searchCondition= purchaseRegisterDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(PurchaseRegister::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(PurchaseRegister::getApplyCode,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getSumTotalNumber,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getSumTotalAmount,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getExplanation,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getContractCode,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getContractFileName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getSupplierName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getApplyTime,searchCondition));
            });
        }

        queryWrapper.eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getApplyUserOrgId()),PurchaseRegister::getApplyUserOrgName,purchaseRegisterDto.getApplyUserOrgName())
                .eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getApplyUserDeptId()),PurchaseRegister::getApplyUserDeptName,purchaseRegisterDto.getApplyUserDeptName())
                .eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getApplyUserName()),PurchaseRegister::getApplyUserName,purchaseRegisterDto.getApplyUserName())
                .eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getCode()),PurchaseRegister::getCode,purchaseRegisterDto.getCode())
                .eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getPlanYear()),PurchaseRegister::getPlanYear,purchaseRegisterDto.getPlanYear());



        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        //查看人员查看范围
        ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.LIST.getCode());

        if (ObjectUtil.isNotEmpty(viewerVo)) {
            queryWrapper.and(q -> {
                q
                        //可看人员List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseRegister::getApplyUserId, viewerVo.getUserList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseRegister::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseRegister::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseRegister::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseRegister::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseRegister::getCreateUserParentOrgId, viewerVo.getOrgList()));
            });
        }

       Page<PurchaseRegister> page = purchaseRegisterMapper.selectPage(purchaseRegisterDto.getPage(),queryWrapper);
        Page<PurchaseRegisterVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseRegisterVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param purchaseRegisterDto
     * @return
     */
    @Override
    public Page lists(PurchaseRegisterDto purchaseRegisterDto) {
        Page<PurchaseRegisterVo> pageVo = purchaseRegisterMapper.getPageList(purchaseRegisterDto.getPage(),purchaseRegisterDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public PurchaseRegisterVo get(Long id) {
        PurchaseRegister purchaseRegister = super.getById(id);
        PurchaseRegisterVo purchaseRegisterVo = null;
        if(purchaseRegister !=null){
            purchaseRegisterVo = dozerMapper.map(purchaseRegister,PurchaseRegisterVo.class);
            //根据id获取purchaseRegisterAssetsVoList
            List<PurchaseRegisterAssetsVo> purchaseRegisterAssetsVoList = purchaseRegisterAssetsService.getByRegisterId(id);
            //设置purchaseRegisterVo，以便构建其对应的子表数据
            purchaseRegisterVo.setAssetsList(purchaseRegisterAssetsVoList);

            //根据id获取purchaseRegisterDetailVoList
            List<PurchaseRegisterDetailVo> purchaseRegisterDetailVoList = purchaseRegisterDetailService.getByRegisterId(id);
            //设置purchaseRegisterVo，以便构建其对应的子表数据
            purchaseRegisterVo.setDetailList(purchaseRegisterDetailVoList);
        }
        return purchaseRegisterVo;
    }

    /**
     * 保存实体
     * @param purchaseRegisterDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PurchaseRegisterDto purchaseRegisterDto) {
        Boolean result = true , resultList = true;
        //获取申请编号
        if(StringUtils.isBlank(purchaseRegisterDto.getCode())){
            getMaxCode(purchaseRegisterDto);
        }
        PurchaseRegister purchaseRegister = dozerMapper.map(purchaseRegisterDto,PurchaseRegister.class);
        result = super.save(purchaseRegister);
        resultList = saveSubList(purchaseRegisterDto);
        return result && resultList;
    }

    /**
     * 获取申请编号
     * @return
     */
    private void getMaxCode(PurchaseRegisterDto purchaseRegisterDto){
        //获取申请编号
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(purchaseRegisterDto,HashMap.class);
        String code = dictClient.getRuleValue("purchase_register","code",entityMap);
        log.debug("规则值:{}",code);
        if(StringUtils.isNotBlank(code)){
            purchaseRegisterDto.setCode(code);
        }
    }

    /**
     * 保存集合
     * @param purchaseRegisterDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseRegisterDto> purchaseRegisterDtos){
        Boolean result = true , resultList = true;
        List<PurchaseRegister> PurchaseRegisters = DozerUtils.mapList(dozerMapper, purchaseRegisterDtos, PurchaseRegister.class);
        result = super.saveBatch(PurchaseRegisters);
        for (PurchaseRegisterDto purchaseRegisterDto : purchaseRegisterDtos){
            //获取申请编号
            if(StringUtils.isBlank(purchaseRegisterDto.getCode())){
                getMaxCode(purchaseRegisterDto);
            }
            resultList = saveSubList(purchaseRegisterDto);
        }
        return result && resultList;
    }

    /**
     * 保存子表集合
     * @param purchaseRegisterDto
     * @return
     */
    @Transactional
    Boolean saveSubList(PurchaseRegisterDto purchaseRegisterDto){
        Boolean result = true;
        //获取其子表集合
        List<PurchaseRegisterAssetsDto> purchaseRegisterAssetsDtoList = purchaseRegisterDto.getAssetsList();
        if(purchaseRegisterAssetsDtoList != null && !purchaseRegisterAssetsDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            purchaseRegisterAssetsDtoList.forEach(assetsDto -> {
                assetsDto.setRegisterId(purchaseRegisterDto.getId());
            });
            //调用保存子表的集合方法
            result = purchaseRegisterAssetsService.saveList(purchaseRegisterAssetsDtoList);
        }
        //获取其子表集合
        List<PurchaseRegisterDetailDto> purchaseRegisterDetailDtoList = purchaseRegisterDto.getDetailList();
        if(purchaseRegisterDetailDtoList != null && !purchaseRegisterDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            purchaseRegisterDetailDtoList.forEach(detailDto -> {
                detailDto.setRegisterId(purchaseRegisterDto.getId());

                purchaseRegisterAssetsDtoList.forEach(assetsDto -> {
                    if(assetsDto.getApplyDetailId().compareTo(detailDto.getApplyDetailId())==0){
                        detailDto.setRegisterAssetsId(assetsDto.getId());
                    }
                });
            });
            //调用保存子表的集合方法
            result = purchaseRegisterDetailService.saveList(purchaseRegisterDetailDtoList);
        }

        return result;
    }

    /**
     * 修改实体
     * @param purchaseRegisterDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PurchaseRegisterDto purchaseRegisterDto) {
        Boolean result = true , resultList = true;
        purchaseRegisterDto.setUpdateTime(LocalDateTime.now());
        PurchaseRegister purchaseRegister = dozerMapper.map(purchaseRegisterDto,PurchaseRegister.class);
        result = super.updateById(purchaseRegister);
        resultList = updateSub(purchaseRegisterDto);
        return result && resultList;
    }

    /**
     * 修改子类实体
     * @param purchaseRegisterDto
     * @return
     */
    @Transactional
    @Override
    public Boolean updateSub(PurchaseRegisterDto purchaseRegisterDto) {
        Boolean result = true;
        List<PurchaseRegisterAssetsDto> purchaseRegisterAssetsDtoList = purchaseRegisterDto.getAssetsList();
        List<PurchaseRegisterDetailDto> purchaseRegisterDetailDtoList = purchaseRegisterDto.getDetailList();
        if(purchaseRegisterAssetsDtoList != null && !purchaseRegisterAssetsDtoList.isEmpty()){
            purchaseRegisterAssetsDtoList.forEach(assetsDto -> {
                assetsDto.setRegisterId(purchaseRegisterDto.getId());
            });

            List<PurchaseRegisterAssets> purchaseRegisterAssetss = DozerUtils.mapList(dozerMapper, purchaseRegisterAssetsDtoList, PurchaseRegisterAssets.class);


            LambdaQueryWrapper<PurchaseRegisterAssets> purchaseRegisterDetailListQueryWrapper = new LambdaQueryWrapper<>();
            purchaseRegisterDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getId()),PurchaseRegisterAssets::getRegisterId,purchaseRegisterDto.getId());
            //调用更新方法
            result = purchaseRegisterAssetsService.saveOrUpdateOrDeleteBatch(purchaseRegisterDetailListQueryWrapper,purchaseRegisterAssetss,purchaseRegisterAssetss.size());
        }

        if(purchaseRegisterDetailDtoList != null && !purchaseRegisterDetailDtoList.isEmpty()){
            purchaseRegisterDetailDtoList.forEach(detailDto -> {
                detailDto.setRegisterId(purchaseRegisterDto.getId());

                purchaseRegisterAssetsDtoList.forEach(assetsDto -> {
                    if(assetsDto.getApplyDetailId().compareTo(detailDto.getApplyDetailId())==0){
                        detailDto.setRegisterAssetsId(assetsDto.getId());
                    }
                });
            });
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(purchaseRegisterDto,purchaseRegisterDetailDtoList);
            List<PurchaseRegisterDetail> purchaseRegisterDetails = DozerUtils.mapList(dozerMapper, purchaseRegisterDetailDtoList, PurchaseRegisterDetail.class);


            LambdaQueryWrapper<PurchaseRegisterDetail> purchaseRegisterDetailListQueryWrapper = new LambdaQueryWrapper<>();
            purchaseRegisterDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(purchaseRegisterDto.getId()),PurchaseRegisterDetail::getRegisterId,purchaseRegisterDto.getId());
            //调用更新方法
            result = purchaseRegisterDetailService.saveOrUpdateOrDeleteBatch(purchaseRegisterDetailListQueryWrapper,purchaseRegisterDetails,purchaseRegisterDetails.size());
        }
        return result;
    }

    /**
     * 通过ID删除
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        return super.removeById(id) && purchaseRegisterAssetsService.deleteByRegisterId(id) &&  purchaseRegisterDetailService.deleteByRegisterId(id);
    }

    /**
     * 流程删除--删除业务数据
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        LambdaQueryWrapper<PurchaseRegister> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseRegister::getCamundaProcinsId,procInstId);
        List<PurchaseRegister> purchaseRegisters = purchaseRegisterMapper.selectList(queryWrapper);
        purchaseRegisters.forEach(purchaseRegister -> {
            this.delete(purchaseRegister.getId());
        });
    }

    @Override
    public PurchaseRegisterVo getByProc(String procInstId) {
        LambdaQueryWrapper<PurchaseRegister> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PurchaseRegister::getCamundaProcinsId,procInstId);
        List<PurchaseRegister> purchaseRegisterList = purchaseRegisterMapper.selectList(queryWrapper);
        PurchaseRegisterVo purchaseRegisterVo = null;
        if(purchaseRegisterList.size()>0){
            purchaseRegisterVo = dozerMapper.map(purchaseRegisterList.get(0),PurchaseRegisterVo.class);

            //根据id获取子集
            List<PurchaseRegisterAssetsVo> purchaseRegisterAssetsVos = purchaseRegisterAssetsService.getByRegisterId(purchaseRegisterVo.getId());
            purchaseRegisterAssetsVos.forEach(assetsVo -> {
                List<PurchaseRegisterDetailVo> detailVos = purchaseRegisterDetailService.getByRegisterAssetsId(assetsVo.getId());
                assetsVo.setDetailList(detailVos);
            });
            purchaseRegisterVo.setAssetsList(purchaseRegisterAssetsVos);

            List<PurchaseRegisterDetailVo> purchaseRegisterDetailVos = purchaseRegisterDetailService.getByRegisterId(purchaseRegisterVo.getId());
            purchaseRegisterVo.setDetailList(purchaseRegisterDetailVos);
        }
        return purchaseRegisterVo;
    }

//    @Override
//    public Page getRegisterList(PurchaseApplyDetailDto purchaseApplyDetailDto) {
//        return purchaseApplyDetailService.getRegisterList(purchaseApplyDetailDto);
//    }

    /**
     * 通过采购登记明细数量，构建生成采购登记详情信息
     * @param purchaseRegisterAssetsDto
     * @return PurchaseRegisterAssetsVo
     *          生成的详情数据在子集list中
     */
    @Override
    public List<PurchaseRegisterDetailVo> getDetailByAssets(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto){
        PurchaseRegisterAssetsVo purchaseRegisterAssetsVo = dozerMapper.map(purchaseRegisterAssetsDto,PurchaseRegisterAssetsVo.class);

        List<PurchaseRegisterDetailVo> purchaseRegisterDetailVos = new ArrayList<>();
        if (!ObjectUtil.isEmpty(purchaseRegisterAssetsDto)){
            String itemType = purchaseRegisterAssetsDto.getItemType();
            //登记数量、单价等、重新计算生成
            BigDecimal registerNumber = purchaseRegisterAssetsDto.getRegisterNumber();
//            BigDecimal registerAmount = purchaseRegisterAssetsDto.getRegisterAmount();
//            BigDecimal registerTaxAmount = purchaseRegisterAssetsDto.getRegisterTaxAmount();
//            BigDecimal registerUntaxedAmount = purchaseRegisterAssetsDto.getRegisterUntaxedAmount();

//            BigDecimal registerSumAmount = registerAmount.multiply(registerNumber);
//            BigDecimal registerSumTaxAmount = registerTaxAmount.multiply(registerNumber);
//            BigDecimal registerSumUntaxedAmount = registerUntaxedAmount.multiply(registerNumber);

            purchaseRegisterAssetsVo.setId(null);
//            purchaseRegisterAssetsDto.setRegisterSumAmount(registerSumAmount);
//            purchaseRegisterAssetsDto.setRegisterSumTaxAmount(registerSumTaxAmount);
//            purchaseRegisterAssetsDto.setRegisterSumUntaxedAmount(registerSumUntaxedAmount);

            //库存或者资产，目前按台套数管理的
            if(!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))){
                //获取填写的登记数量，通过登记数量生成详情数
                Integer registerNumberCount = registerNumber.intValue();
                PurchaseRegisterDetailVo purchaseRegisterDetailVo = dozerMapper.map(purchaseRegisterAssetsDto,PurchaseRegisterDetailVo.class);
                purchaseRegisterDetailVo.setId(null);
                purchaseRegisterDetailVo.setRegisterAssetsId(purchaseRegisterAssetsDto.getId());
//                purchaseRegisterDetailVo.setSupplierName(supplierName);
//                purchaseRegisterDetailVo.setRegisterSumAmount(registerAmount);
//                purchaseRegisterDetailVo.setRegisterSumTaxAmount(registerTaxAmount);
//                purchaseRegisterDetailVo.setRegisterSumUntaxedAmount(registerUntaxedAmount);

                for(int i = 0 ; i < registerNumberCount ; i ++){
                    purchaseRegisterDetailVo.setRegisterNumber(BigDecimal.ONE);
                    purchaseRegisterDetailVos.add(purchaseRegisterDetailVo);
                    //purchaseRegisterAssetsVo.setSkuCodes(purchaseRegisterDetailVo.getSkuCode());
                }
            }else{//低值易耗品或原材料
                PurchaseRegisterDetailVo purchaseRegisterDetailVo = dozerMapper.map(purchaseRegisterAssetsDto,PurchaseRegisterDetailVo.class);
                purchaseRegisterDetailVo.setId(null);
                purchaseRegisterDetailVos.add(purchaseRegisterDetailVo);
            }
        }
//        purchaseRegisterAssetsVo.setDetailList(purchaseRegisterDetailVos);
        return purchaseRegisterDetailVos;
    }


    /**
     * 购置登记保存前
     *  增加申请详情未登记数量,减少登记数量
     *      2022-06-10 15:06 修改不减少登记数量
     *      2022-06-16 9:36 修改(增加):增加未采购登记金额
     * @param procInstId
     * @return
     */
    @Override
    @Transactional
    public Boolean purRegSaveBefore(String procInstId){

        PurchaseRegisterVo registerVo = this.getByProc(procInstId);
        PurchaseRegisterDto registerDto = dozerMapper.map(registerVo,PurchaseRegisterDto.class);

        List<PurchaseRegisterAssetsVo> purchaseRegisterAssetsVos = registerVo.getAssetsList();
//        List<PurchaseRegisterDetailVo> purchaseRegisterDetailVos = registerVo.getDetailList();

        Boolean result = true;
        for (PurchaseRegisterAssetsVo purchaseRegisterAssetsVo : purchaseRegisterAssetsVos) {
            BigDecimal registerNumber = purchaseRegisterAssetsVo.getRegisterNumber();
            BigDecimal registerSumAmount = purchaseRegisterAssetsVo.getRegisterSumAmount();

            //获取申请详情
            PurchaseApplyDetailVo purchaseApplyDetailVo = purchaseApplyDetailService.get(purchaseRegisterAssetsVo.getApplyDetailId());
            PurchaseApplyDetailDto purchaseApplyDetailDto = dozerMapper.map(purchaseApplyDetailVo,PurchaseApplyDetailDto.class);
            //获取申请详情的登记数量
            //BigDecimal oldRegisterNumber = purchaseApplyDetailVo.getRegisterNumber();
            BigDecimal oldNotRegisterNumber = purchaseApplyDetailVo.getNotRegisterNumber();

            //计算新的登记数量
            //BigDecimal newRegisterNumber = oldRegisterNumber.subtract(registerNumber);
            BigDecimal newNotRegisterNumber = oldNotRegisterNumber.add(registerNumber);

            //获取申请详情的未登记金额
            BigDecimal oldNotRegisterSumAmount = purchaseApplyDetailVo.getNotRegisterSumAmount();
            //计算新的未登记金额
            BigDecimal newNotRegisterSumAmount = oldNotRegisterSumAmount.add(registerSumAmount);

            //设置新的值，并更新
            //purchaseApplyDetailDto.setRegisterNumber(newRegisterNumber);
            purchaseApplyDetailDto.setNotRegisterNumber(newNotRegisterNumber);
            purchaseApplyDetailDto.setNotRegisterSumAmount(newNotRegisterSumAmount);
            result = purchaseApplyDetailService.update(purchaseApplyDetailDto);
            log.debug("购置申请详情:{},未登记数量为:{}",purchaseApplyDetailDto.getId(),newNotRegisterNumber);
        }

        return result;
    }

    /**
     * 购置申请保存后
     *  增加申请详情登记数量,减少未登记数量
     *      2022-06-10 15:06 修改(减少条件):不增加登记数量
     *      2022-06-16 9:36 修改(增加条件):减少未采购登记金额
     * @param procInstId
     * @return
     */
    @Override
    @Transactional
    public Boolean purRegSaveAfter(String procInstId) {
        PurchaseRegisterVo registerVo = this.getByProc(procInstId);
        this.verify(dozerMapper.map(registerVo,PurchaseRegisterDto.class));

        List<PurchaseRegisterAssetsVo> purchaseRegisterAssetsVos = registerVo.getAssetsList();
        List<PurchaseRegisterDetailVo> purchaseRegisterDetailVos = registerVo.getDetailList();

        Boolean result = true;
        for (PurchaseRegisterAssetsVo purchaseRegisterAssetsVo : purchaseRegisterAssetsVos) {
            BigDecimal registerNumber = purchaseRegisterAssetsVo.getRegisterNumber();
            BigDecimal registerSumAmount = purchaseRegisterAssetsVo.getRegisterSumAmount();

            //获取申请详情
            PurchaseApplyDetailVo purchaseApplyDetailVo = purchaseApplyDetailService.get(purchaseRegisterAssetsVo.getApplyDetailId());
            PurchaseApplyDetailDto purchaseApplyDetailDto = dozerMapper.map(purchaseApplyDetailVo,PurchaseApplyDetailDto.class);

            //获取申请详情的登记数量
            //BigDecimal oldRegisterNumber = purchaseApplyDetailVo.getRegisterNumber();
            BigDecimal oldNotRegisterNumber = purchaseApplyDetailVo.getNotRegisterNumber();
            //计算新的登记数量
            //BigDecimal newRegisterNumber = oldRegisterNumber.add(registerNumber);
            BigDecimal newNotRegisterNumber = oldNotRegisterNumber.subtract(registerNumber);
            //设置新的值，并更新
            //purchaseApplyDetailDto.setRegisterNumber(newRegisterNumber);
            purchaseApplyDetailDto.setNotRegisterNumber(newNotRegisterNumber);


            //获取申请详情的未登记金额
            BigDecimal oldNotRegisterSumAmount = purchaseApplyDetailVo.getNotRegisterSumAmount();
            //计算新的未登记金额
            BigDecimal newNotRegisterSumAmount = oldNotRegisterSumAmount.subtract(registerSumAmount);
            purchaseApplyDetailDto.setNotRegisterSumAmount(newNotRegisterSumAmount);

            result = purchaseApplyDetailService.update(purchaseApplyDetailDto);
            log.debug("购置申请详情:{},未登记数量为:{}",purchaseApplyDetailDto.getId(),newNotRegisterNumber);
        }
        return result;
    }

    @Override
    @Transactional
    public PurchaseRegisterVo procSaveOrUpdate(PurchaseRegisterDto purchaseRegisterDto) {

        PurchaseRegisterVo purchaseRegisterVo = this.get(purchaseRegisterDto.getId());
        //判断是否未编辑、是否是起草
        //若不是起草(流程中保存)，手动调用保存前方法
        if (ObjectUtil.isNotEmpty(purchaseRegisterVo)
                && ObjectUtil.isNotEmpty(purchaseRegisterVo.getAuditStatus())
                && purchaseRegisterVo.getAuditStatus() != WfProcessEnum.DRAFT.getType()){
            this.purRegSaveBefore(purchaseRegisterVo.getCamundaProcinsId());
        }

        //获取申请编号
        if(StringUtils.isBlank(purchaseRegisterDto.getCode())){
            getMaxCode(purchaseRegisterDto);
        }
        if(ObjectUtil.isEmpty(purchaseRegisterDto.getStatus())){
            purchaseRegisterDto.setStatus(0);
        }
        PurchaseRegister purchaseRegister = dozerMapper.map(purchaseRegisterDto,PurchaseRegister.class);
        super.saveOrUpdate(purchaseRegister);
        purchaseRegisterAssetsService.deleteByRegisterId(purchaseRegisterDto.getId());
        purchaseRegisterDetailService.deleteByRegisterId(purchaseRegisterDto.getId());

        List<PurchaseRegisterAssetsDto> assetsDtoList = purchaseRegisterDto.getAssetsList();
        if(CollectionUtils.isNotEmpty(assetsDtoList)){
            assetsDtoList.forEach(assetsDto -> {
                BigDecimal registerNumber = assetsDto.getRegisterNumber();
                if(ObjectUtil.isNotEmpty(registerNumber)) {
                    assetsDto.setRegisterId(purchaseRegister.getId());
                    if (ObjectUtil.isEmpty(assetsDto.getPurchaseType())) {
                        assetsDto.setPurchaseType(AssetSource.CAIGOUR.getCode());
                    }

                    List<PurchaseRegisterDetailDto> detailDtoList = assetsDto.getDetailList();
                    if (CollectionUtils.isNotEmpty(detailDtoList)) {
                        List<PurchaseRegisterDetail> detailList = DozerUtils.mapList(dozerMapper, detailDtoList, PurchaseRegisterDetail.class);
                        purchaseRegisterDetailService.saveOrUpdateBatch(detailList);
                    }
                }
            });

            List<PurchaseRegisterAssets> assetsList = DozerUtils.mapList(dozerMapper,assetsDtoList,PurchaseRegisterAssets.class);
            purchaseRegisterAssetsService.saveOrUpdateBatch(assetsList);
        }
        return this.getByProc(purchaseRegister.getCamundaProcinsId());
    }

    private void setApplyStatus(PurchaseRegisterAssetsVo assetsVo){
        BigDecimal registerNumber = assetsVo.getRegisterNumber();
        BigDecimal registerAmount = assetsVo.getRegisterSumAmount();

        PurchaseApplyVo applyVo = purchaseApplyService.get(assetsVo.getApplyId());
        PurchaseApplyDto applyDto = dozerMapper.map(applyVo,PurchaseApplyDto.class);

        BigDecimal oldRegisterNumber = applyDto.getRegisterNumber();
        oldRegisterNumber = ObjectUtil.isEmpty(oldRegisterNumber) ? BigDecimal.ZERO : oldRegisterNumber;
        applyDto.setRegisterNumber(oldRegisterNumber.add(registerNumber));

        BigDecimal notRegisterNumber = applyDto.getNotRegisterNumber();
        notRegisterNumber = ObjectUtil.isEmpty(notRegisterNumber) ? applyDto.getSumTotalNumber() : notRegisterNumber;
        applyDto.setNotRegisterNumber(notRegisterNumber.subtract(registerNumber));

        BigDecimal notRegisterAmount = applyVo.getNotRegisterAmount();
        notRegisterAmount = ObjectUtil.isEmpty(notRegisterAmount) ? applyDto.getSumTotalAmount() : notRegisterAmount;
        applyDto.setNotRegisterAmount(notRegisterAmount.subtract(registerAmount));

        if(applyDto.getNotRegisterNumber().compareTo(BigDecimal.ZERO) == 0){
            applyDto.setStatus("1");
        }else{
            applyDto.setStatus("2");
        }

        purchaseApplyService.update(applyDto);
    }

    /**
     * 采购登记完成
     *  1.增加待验收数量
     *  2.根据itemType生成资产台账及资产明细信息
     *      2022-06-10 15:06 修改增加采购申请详情登记数量
     *      2022-06-16 9:36 修改(增加条件):增加采购登记金额
     * @param procInstId
     * @return
     */
    @Override
    @Transactional
    public Boolean purRegAuditSuccess(String procInstId) {
        PurchaseRegisterVo registerVo = this.getByProc(procInstId);

        PurchaseRegisterDto registerDto = dozerMapper.map(registerVo,PurchaseRegisterDto.class);
        registerDto.setAcceptanceNumber(BigDecimal.ZERO);
        registerDto.setNotAcceptanceNumber(registerDto.getSumTotalNumber());
        registerDto.setNotAcceptanceAmount(registerDto.getSumTotalAmount());

        List<PurchaseRegisterAssetsVo> assetsVos = registerVo.getAssetsList();
        //List<PurchaseRegisterDetailVo> detailVos = registerVo.getDetailList();
        Boolean result = true;

        List<AssetsDetail> assetsDetailList = new ArrayList<>();
        if(assetsVos.size()>0) {
            for (PurchaseRegisterAssetsVo assetsVo : assetsVos) {
                setApplyStatus(assetsVo);

                //资产类型  1-资产（自购,2-办公耗材（低值易耗皮,3-库存商品（销售）,4-原材料（自制）
                String itemType = assetsVo.getItemType();
                //BigDecimal applyNumber = assetsVo.getApplyNumber();
                BigDecimal registerNumber = assetsVo.getRegisterNumber();
                BigDecimal registerSumAmount = assetsVo.getRegisterSumAmount();



                //获取申请详情,修改购置申请详情登记数量
                PurchaseApplyDetailVo applyDetailVo = purchaseApplyDetailService.get(assetsVo.getApplyDetailId());
                PurchaseApplyDetailDto applyDetailDto = dozerMapper.map(applyDetailVo,PurchaseApplyDetailDto.class);

                //获取申请详情的登记数量
                BigDecimal oldRegisterNumber = applyDetailDto.getRegisterNumber();
                //计算新的登记数量
                BigDecimal newRegisterNumber = oldRegisterNumber.add(registerNumber);
                applyDetailDto.setRegisterNumber(newRegisterNumber);


                //获取申请详情的登记金额
                BigDecimal oldRegisterSumAmount = applyDetailDto.getRegisterSumAmount();
                //计算新的登记金额
                BigDecimal newRegisterSumAmount = oldRegisterSumAmount.add(registerSumAmount);
                applyDetailDto.setRegisterSumAmount(newRegisterSumAmount);
                result = purchaseApplyDetailService.update(applyDetailDto);
                log.debug("购置申请详情:{},登记数量为:{}",applyDetailDto.getId(),newRegisterNumber);

                //获取购置申请单
//                PurchaseApplyVo applyVo = purchaseApplyService.get(applyDetailDto.getApplyId());

                //生成登记结果信息
                PurchaseRegisterResultDto registerResultDto = dozerMapper.map(assetsVo, PurchaseRegisterResultDto.class);

                registerResultDto.setRegisterId(registerVo.getId());
                registerResultDto.setRegisterCode(registerVo.getCode());
                registerResultDto.setRegisterAssetsId(assetsVo.getId());
                //registerResultDto.setRegisterDetailId(detailVo.getId());
                registerResultDto.setItemType(itemType);
                registerResultDto.setRegisterNumber(registerNumber);
                registerResultDto.setAcceptanceNumber(BigDecimal.ZERO);
                registerResultDto.setNotAcceptanceNumber(registerNumber);
                registerResultDto.setStorageNumber(BigDecimal.ZERO);
                registerResultDto.setNotStorageNumber(BigDecimal.ZERO);

                registerResultDto.setApplyTime(registerVo.getApplyTime());
                registerResultDto.setApplyUserId(registerVo.getApplyUserId());
                registerResultDto.setApplyUserName(registerVo.getApplyUserName());
                registerResultDto.setApplyUserOrgId(registerVo.getApplyUserOrgId());
                registerResultDto.setApplyUserOrgName(registerVo.getApplyUserOrgName());
                registerResultDto.setApplyUserDeptId(registerVo.getApplyUserDeptId());
                registerResultDto.setApplyUserDeptName(registerVo.getApplyUserDeptName());

                registerResultDto.setId(null);

                result = purchaseRegisterResultService.save(registerResultDto);

                if(!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.ASSET.code) || itemType.equals(ItemTypeEnum.INVENTORY.code))){

                    LambdaQueryWrapper<Assets> assetsQueryWrapper = new LambdaQueryWrapper<>();
                    assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(registerVo.getApplyUserOrgId()), Assets::getAssetOrgId, registerVo.getApplyUserOrgId());
                    assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(registerVo.getApplyUserDeptId()), Assets::getAssetDeptId, registerVo.getApplyUserDeptId());
                    assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(assetsVo.getItemId()), Assets::getItemId, assetsVo.getItemId());
                    assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(assetsVo.getItemCode()), Assets::getItemCode, assetsVo.getItemCode());
                    assetsQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), Assets::getItemType, itemType);
//                    assetsQueryWrapper.eq(Assets::getAssetSource, AssetSource.CAIGOUR.getCode());

                    List<Assets> assetsList = assetsService.list(assetsQueryWrapper);
                    AssetsDto assetsDto = new AssetsDto();
                    if (assetsList.size() > 0) {//该类资产已经存在
                        Assets assets = assetsList.get(0);
                        assetsDto = dozerMapper.map(assets, AssetsDto.class);
                        //资产数量累加
                        BigDecimal assetsNumber = assets.getAssetsNumber().add(registerNumber);
                        assetsDto.setAssetsNumber(assetsNumber);
                        result = assetsService.update(assetsDto);

                    } else {//不存在
                        assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                        assetsDto.setId(null);
                        assetsDto.setSourceId(assetsVo.getId());

                        //资产所属人（责任人相关）
                        assetsDto.setAssetOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsDto.setAssetOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsDto.setAssetDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsDto.setAssetDeptName(applyDetailDto.getApplyUserDeptName());

                        assetsDto.setAssetsNumber(assetsVo.getRegisterNumber());
                        assetsDto.setStorageNumber(BigDecimal.ZERO);
                        assetsDto.setStockNumber(BigDecimal.ZERO);
                        assetsDto.setDeliveryNumber(BigDecimal.ZERO);
                        assetsDto.setAcceptNumber(BigDecimal.ZERO);
                        assetsDto.setBorrowNumber(BigDecimal.ZERO);
                        assetsDto.setTransferNumber(BigDecimal.ZERO);
                        assetsDto.setRepairNumber(BigDecimal.ZERO);
                        assetsDto.setScrappedNumber(BigDecimal.ZERO);
                        assetsDto.setAcceptanceNumber(BigDecimal.ZERO);
                        assetsDto.setUsableNumber(BigDecimal.ZERO);
                        assetsDto.setEntryNumber(BigDecimal.ZERO);
                        assetsDto.setLendNumber(BigDecimal.ZERO);
                        assetsDto.setAssetSource(AssetSource.CAIGOUR.getCode());

                        //资产所属人（申请人相关）
                        assetsDto.setApplyTime(applyDetailDto.getApplyTime());
                        assetsDto.setApplyUserId(applyDetailDto.getApplyUserId());
                        assetsDto.setApplyUserName(applyDetailDto.getApplyUserName());
                        assetsDto.setApplyUserOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsDto.setApplyUserOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsDto.setApplyUserDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsDto.setApplyUserDeptName(applyDetailDto.getApplyUserDeptName());

                        //资产所属人（使用人相关）
                        assetsDto.setUseUserOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsDto.setUseUserOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsDto.setUseUserDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsDto.setUseUserDeptName(applyDetailDto.getApplyUserDeptName());

                        assetsDto.setRegisterUserId(registerDto.getApplyUserId());
                        assetsDto.setRegisterUserName(registerDto.getApplyUserName());

                        result = assetsService.save(assetsDto);
                    }

                    LambdaQueryWrapper<AssetsSku> assetsSkuQueryWrapper = new LambdaQueryWrapper<>();
                    assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(registerVo.getApplyUserOrgId()), AssetsSku::getAssetOrgId, registerVo.getApplyUserOrgId());
                    assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(registerVo.getApplyUserDeptId()), AssetsSku::getAssetDeptId, registerVo.getApplyUserDeptId());
                    assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(assetsVo.getItemId()), AssetsSku::getItemId, assetsVo.getItemId());
                    assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(assetsVo.getItemCode()), AssetsSku::getItemCode, assetsVo.getItemCode());
                    assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(itemType), AssetsSku::getItemType, itemType);
//                    assetsSkuQueryWrapper.eq(AssetsSku::getAssetSource, AssetSource.CAIGOUR.getCode());
                    assetsSkuQueryWrapper.eq(ObjectUtil.isNotEmpty(assetsVo.getSkuCodes()), AssetsSku::getSkuCodes, assetsVo.getSkuCodes());

                    List<AssetsSku> assetsSkuList = assetsSkuService.list(assetsSkuQueryWrapper);
                    AssetsSkuDto assetsSkuDto = new AssetsSkuDto();
                    if (assetsSkuList.size() > 0) {//该类资产已经存在
                        AssetsSku assetsSku = assetsSkuList.get(0);
                        assetsSkuDto = dozerMapper.map(assetsSku, AssetsSkuDto.class);
                        //资产数量累加
                        BigDecimal assetsNumber = assetsSku.getAssetsNumber().add(registerNumber);
                        assetsSkuDto.setAssetsNumber(assetsNumber);
                        result = assetsSkuService.update(assetsSkuDto);
                    } else {//不存在
                        assetsSkuDto = dozerMapper.map(assetsVo, AssetsSkuDto.class);
                        assetsSkuDto.setId(null);
                        assetsSkuDto.setSourceId(assetsVo.getId());
                        assetsSkuDto.setAssetsId(assetsDto.getId());

                        assetsSkuDto.setAssetOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsSkuDto.setAssetOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsSkuDto.setAssetDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsSkuDto.setAssetDeptName(applyDetailDto.getApplyUserDeptName());

                        assetsSkuDto.setAssetsNumber(registerNumber);
                        assetsSkuDto.setStorageNumber(BigDecimal.ZERO);
                        assetsSkuDto.setStockNumber(BigDecimal.ZERO);
                        assetsSkuDto.setDeliveryNumber(BigDecimal.ZERO);
                        assetsSkuDto.setAcceptNumber(BigDecimal.ZERO);
                        assetsSkuDto.setBorrowNumber(BigDecimal.ZERO);
                        assetsSkuDto.setTransferNumber(BigDecimal.ZERO);
                        assetsSkuDto.setRepairNumber(BigDecimal.ZERO);
                        assetsSkuDto.setScrappedNumber(BigDecimal.ZERO);
                        assetsSkuDto.setAcceptanceNumber(BigDecimal.ZERO);
                        assetsSkuDto.setUsableNumber(BigDecimal.ZERO);
                        assetsSkuDto.setEntryNumber(BigDecimal.ZERO);
                        assetsSkuDto.setLendNumber(BigDecimal.ZERO);
                        assetsSkuDto.setAssetSource(AssetSource.CAIGOUR.getCode());

                        assetsSkuDto.setApplyTime(applyDetailDto.getApplyTime());
                        assetsSkuDto.setApplyUserId(applyDetailDto.getApplyUserId());
                        assetsSkuDto.setApplyUserName(applyDetailDto.getApplyUserName());
                        assetsSkuDto.setApplyUserOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsSkuDto.setApplyUserOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsSkuDto.setApplyUserDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsSkuDto.setApplyUserDeptName(applyDetailDto.getApplyUserDeptName());

                        assetsSkuDto.setUseUserOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsSkuDto.setUseUserOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsSkuDto.setUseUserDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsSkuDto.setUseUserDeptName(applyDetailDto.getApplyUserDeptName());

                        assetsSkuDto.setRegisterUserId(registerDto.getApplyUserId());
                        assetsSkuDto.setRegisterUserName(registerDto.getApplyUserName());

                        result = assetsSkuService.save(assetsSkuDto);
                    }


                    List<PurchaseRegisterDetailVo> registerDetailVoList = assetsVo.getDetailList();
                    if(CollectionUtils.isEmpty(registerDetailVoList)){
                        PurchaseRegisterAssetsDto registerAssetsDto = dozerMapper.map(assetsVo,PurchaseRegisterAssetsDto.class);
                        registerDetailVoList = this.getDetailByAssets(registerAssetsDto);
                        List<PurchaseRegisterDetail> registerDetailList = DozerUtils.mapList(dozerMapper,registerDetailVoList,PurchaseRegisterDetail.class);
                        purchaseRegisterDetailService.saveBatch(registerDetailList);
                    }

                    for (PurchaseRegisterDetailVo detailVo : registerDetailVoList) {

                        //资产类型  1-资产（自购,2-办公耗材（低值易耗皮,3-库存商品（销售）,4-原材料（自制）
                        itemType = detailVo.getItemType();
                        AssetsDetailDto assetsDetail = dozerMapper.map(detailVo, AssetsDetailDto.class);
                        assetsDetail.setId(null);
                        assetsDetail.setSourceId(detailVo.getId());
                        assetsDetail.setAssetsId(assetsDto.getId());
                        assetsDetail.setAssetsSkuId(assetsSkuDto.getId());

                        assetsDetail.setAssetOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsDetail.setAssetOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsDetail.setAssetDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsDetail.setAssetDeptName(applyDetailDto.getApplyUserDeptName());

                        assetsDetail.setItemType(itemType);
                        assetsDetail.setAssetsNumber(BigDecimal.ONE);
                        assetsDetail.setAcceptanceNumber(BigDecimal.ZERO);
                        assetsDetail.setStorageNumber(BigDecimal.ZERO);
                        assetsDetail.setStockNumber(BigDecimal.ZERO);
                        assetsDetail.setDeliveryNumber(BigDecimal.ZERO);
                        assetsDetail.setAcceptNumber(BigDecimal.ZERO);
                        assetsDetail.setBorrowNumber(BigDecimal.ZERO);
                        assetsDetail.setTransferNumber(BigDecimal.ZERO);
                        assetsDetail.setRepairNumber(BigDecimal.ZERO);
                        assetsDetail.setScrappedNumber(BigDecimal.ZERO);
                        assetsDetail.setAcceptanceNumber(BigDecimal.ZERO);
                        assetsDetail.setUsableNumber(BigDecimal.ZERO);
                        assetsDetail.setEntryNumber(BigDecimal.ZERO);
                        assetsDetail.setLendNumber(BigDecimal.ZERO);

                        assetsDetail.setTaxRate(detailVo.getRegisterTaxRate());
                        assetsDetail.setAssetsAmount(detailVo.getRegisterAmount());
                        assetsDetail.setAssetsTaxAmount(detailVo.getRegisterTaxAmount());
                        assetsDetail.setAssetsUntaxedAmount(detailVo.getRegisterUntaxedAmount());
                        assetsDetail.setAssetsSumAmount(detailVo.getRegisterSumAmount());
                        assetsDetail.setAssetsSumTaxAmount(detailVo.getRegisterSumAmount().subtract(detailVo.getRegisterSumUntaxedAmount()));
                        assetsDetail.setAssetsSumUntaxedAmount(detailVo.getRegisterSumUntaxedAmount());
                        assetsDetail.setAssetSource(AssetSource.CAIGOUR.getCode());

                        assetsDetail.setApplyTime(applyDetailDto.getApplyTime());
                        assetsDetail.setApplyUserId(applyDetailDto.getApplyUserId());
                        assetsDetail.setApplyUserName(applyDetailDto.getApplyUserName());
                        assetsDetail.setApplyUserOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsDetail.setApplyUserOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsDetail.setApplyUserDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsDetail.setApplyUserDeptName(applyDetailDto.getApplyUserDeptName());
                        assetsDetail.setStatus("已采购");

                        assetsDetail.setUseUserId(applyDetailDto.getApplyUserId());
                        assetsDetail.setUseUserName(applyDetailDto.getApplyUserName());
                        assetsDetail.setUseUserOrgId(applyDetailDto.getApplyUserOrgId());
                        assetsDetail.setUseUserOrgName(applyDetailDto.getApplyUserOrgName());
                        assetsDetail.setUseUserDeptId(applyDetailDto.getApplyUserDeptId());
                        assetsDetail.setUseUserDeptName(applyDetailDto.getApplyUserDeptName());

                        assetsDetail.setRegisterUserId(registerDto.getApplyUserId());
                        assetsDetail.setRegisterUserName(registerDto.getApplyUserName());

//                        assetsDetailList.add(assetsDetail);
                        result = assetsDetailService.save(assetsDetail);

                        daybookService.log(registerVo.getId(), registerVo.getCamundaProcinsId(),
                                dozerMapper.map(detailVo, AssetsDetailDto.class),DayBookType.REGISTER);
                        //}
                    }
                }
            }
//            result = assetsDetailService.saveOrUpdateBatch(assetsDetailList);
        }
        return result && this.update(registerDto);
    }

    @Override
    public void verifyRegisterNumberAndAmount(String procInstId) {
        PurchaseRegisterVo registerVo = this.getByProc(procInstId);
        PurchaseRegisterDto registerDto = dozerMapper.map(registerVo,PurchaseRegisterDto.class);
        this.verify(registerDto);
    }

    @Override
    public void verifyRegisterNumberAndAmount(PurchaseRegisterDto registerDto) {
        this.verify(registerDto);
    }

    public void verify(PurchaseRegisterDto registerDto) {
        List<PurchaseApplyVo> applyVoList = purchaseApplyService.getByIds(registerDto.getApplyId());
        List<PurchaseRegisterAssetsDto> assetsDtoList = registerDto.getAssetsList();
        for (PurchaseApplyVo applyVo : applyVoList) {

            BigDecimal sumRegisterNumber = BigDecimal.ZERO;
            BigDecimal sumRegisterAmount = BigDecimal.ZERO;

            for (PurchaseRegisterAssetsDto assetsDto : assetsDtoList){
                if(ObjectUtil.isNotEmpty(assetsDto) && assetsDto.getApplyId() == applyVo.getId()) {
                    //此次采购数量
                    BigDecimal registerNumber = assetsDto.getRegisterNumber();
                    sumRegisterNumber.add(registerNumber);
                    //此次采购金额
                    BigDecimal registerSumAmount = assetsDto.getRegisterSumAmount();
                    sumRegisterAmount.add(registerSumAmount);

                    PurchaseApplyDetailVo applyDetailVo = purchaseApplyDetailService.get(assetsDto.getApplyDetailId());
                    if (ObjectUtil.isNotEmpty(applyDetailVo)) {
                        //未采购数量
                        BigDecimal notRegisterNumber = applyDetailVo.getNotRegisterNumber();
                        //未采购总金额
                        BigDecimal notRegisterSumAmount = applyDetailVo.getNotRegisterSumAmount();

                        //采购数量大于未采购数量
                        if (registerNumber.compareTo(notRegisterNumber) == 1) {
                            throw new IncloudException("物资编码：" + assetsDto.getItemCode() + ",采购数量大于未采购数量!!");
                        }

                        //采购金额大于未采购金额
                        if (registerSumAmount.compareTo(notRegisterSumAmount) == 1) {
                            throw new IncloudException("物资编码：" + assetsDto.getItemCode() + ",采购金额大于未采购金额!!");
                        }
                    } else {
                        throw new IncloudException("物资编码：" + assetsDto.getItemCode() + ",没有查询到相关申请详情数据");
                    }
                }
            }

            BigDecimal notRegisterNumber = applyVo.getNotRegisterNumber();
            BigDecimal notRegisterAmount = applyVo.getNotRegisterAmount();
            if(sumRegisterNumber.compareTo(notRegisterNumber)==1){
                throw new IncloudException("申请单："+applyVo.getCode()+"采购总数量大于申请单未采购数量!!");
            }
            if(sumRegisterAmount.compareTo(notRegisterAmount)==1){
                throw new IncloudException("申请单："+applyVo.getCode()+"采购总金额大于未申请单采购金额!!");
            }
        }
    }

    public String verify11(PurchaseRegisterDto registerDto) {
        String message = null;
        List<PurchaseRegisterAssetsDto> assetsDtoList = registerDto.getAssetsList();
        BigDecimal sumRegisterNumber = BigDecimal.ZERO;
        BigDecimal sumRegisterAmount = BigDecimal.ZERO;
        for (PurchaseRegisterAssetsDto assetsDto : assetsDtoList){
            //此次采购数量
            BigDecimal registerNumber = assetsDto.getRegisterNumber();
            sumRegisterNumber.add(registerNumber);
            //此次采购金额
            BigDecimal registerSumAmount = assetsDto.getRegisterSumAmount();
            sumRegisterAmount.add(registerSumAmount);

            PurchaseApplyDetailVo applyDetailVo = purchaseApplyDetailService.get(assetsDto.getApplyDetailId());
            if(ObjectUtil.isNotEmpty(applyDetailVo)){
                //未采购数量
                BigDecimal notRegisterNumber = applyDetailVo.getNotRegisterNumber();
                //未采购总金额
                BigDecimal notRegisterSumAmount = applyDetailVo.getNotRegisterSumAmount();

                //采购数量大于未采购数量
                if(registerNumber.compareTo(notRegisterNumber) == 1){
                    message = "物资编码："+assetsDto.getItemCode()+",采购数量大于未采购数量!!";
                }

                //采购金额大于未采购金额
                if(registerSumAmount.compareTo(notRegisterSumAmount) == 1){
                    message = "物资编码："+assetsDto.getItemCode()+",采购金额大于未采购金额!!";
                }
            }else{
                message = "没有查询到相关申请详情数据";
            }
        }

        //List<PurchaseApplyVo> applyVoList = purchaseApplyService.getByIds(registerDto.getApplyId());

        return message;
    }


    /**
     * 获取登记待验收数据
     * @param registerDto
     * @return
     */
    @Override
    public Page forAcceptanceList(PurchaseRegisterDto registerDto) {
        LambdaQueryWrapper<PurchaseRegister> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(PurchaseRegister::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(PurchaseRegister::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(PurchaseRegister::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        queryWrapper.orderByDesc(PurchaseRegister::getApplyTime);

        queryWrapper.eq(ObjectUtil.isNotEmpty(registerDto.getCode()),PurchaseRegister::getCode,registerDto.getCode());
        queryWrapper.eq(PurchaseRegister::getAuditStatus, WfProcessEnum.DONE.getType());
        queryWrapper.ne(PurchaseRegister::getStatus,"1");

        //指定的查询字段
        String  searchCondition= registerDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(PurchaseRegister::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(PurchaseRegister::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getPlanYear,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getApplyTime,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getExplanation,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseRegister::getSumTotalAmount,searchCondition));
            });
        }
        /*if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.like(PurchaseRegister::getCode,searchCondition)
                    .or()
                    .like(PurchaseRegister::getApplyUserName,searchCondition)
                    .or()
                    .like(PurchaseRegister::getApplyUserDeptName,searchCondition)
                    .or()
                    .like(PurchaseRegister::getApplyUserOrgName,searchCondition)
                    .or()
                    .like(PurchaseRegister::getPlanYear,searchCondition)
                    .or()
                    .like(PurchaseRegister::getApplyTime,searchCondition)
                    .or()
                    .like(PurchaseRegister::getExplanation,searchCondition)
                    .or()
                    .like(PurchaseRegister::getSumTotalAmount,searchCondition);
        }*/

        Page<PurchaseRegister> page = this.page(registerDto.getPage(),queryWrapper);
        Page<PurchaseRegisterVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseRegisterVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }
}
