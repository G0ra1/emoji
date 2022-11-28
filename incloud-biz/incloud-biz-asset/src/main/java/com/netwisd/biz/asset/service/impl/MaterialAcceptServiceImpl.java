package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.center.dto.TodoDto;
import com.netwisd.base.common.center.vo.TodoVo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.biz.asset.constants.ItemTypeEnum;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.fegin.CenterClient;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaterialAcceptMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 资产领用 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-26 17:25:03
 */
@Service
@Slf4j
public class MaterialAcceptServiceImpl extends BatchServiceImpl<MaterialAcceptMapper, MaterialAccept> implements MaterialAcceptService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialAcceptMapper materialAcceptMapper;

    @Autowired
    private MaterialAcceptAssetsService materialAcceptAssetsService;

    @Autowired
    private MaterialAcceptDetailService materialAcceptDetailService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private SuppliesService suppliesService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private MaterialAcceptResultService materialAcceptResultService;

    @Autowired
    private DaybookService daybookService;

    @Autowired
    private DaybookSuppliesService daybookSuppliesService;

    @Autowired
    private MaterialDistributeService materialDistributeService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private CenterClient centerClient;

//    @Autowired
//    private CeneterService ceneterService;

    @Autowired
    private ViewerService viewerService;

    /**
    * 单表简单查询操作
    * @param materialAcceptDto
    * @return
    */
    @Override
    public Page list(MaterialAcceptDto materialAcceptDto) {
        LambdaQueryWrapper<MaterialAccept> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(MaterialAccept::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(MaterialAccept::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(MaterialAccept::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        //指定的查询字段
        String  searchCondition= materialAcceptDto.getSearchCondition();
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(MaterialAccept::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(MaterialAccept::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialAccept::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialAccept::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialAccept::getApplyTime,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialAccept::getSumTotalAmount,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialAccept::getExplanation,searchCondition));
            });
        }


        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        //查看人员查看范围
        ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.LIST.getCode());

        if (ObjectUtil.isNotEmpty(viewerVo)) {
            queryWrapper.and(q -> {
                q
                        //可看人员List
                        .or(CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(MaterialAccept::getApplyUserId, viewerVo.getUserList()))
                        .or(CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(MaterialAccept::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(MaterialAccept::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(MaterialAccept::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(MaterialAccept::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(MaterialAccept::getCreateUserParentOrgId, viewerVo.getOrgList()));
            });
        }

        queryWrapper.orderByDesc(MaterialAccept::getApplyTime);
        //根据实际业务构建具体的参数做查询
        Page<MaterialAccept> page = materialAcceptMapper.selectPage(materialAcceptDto.getPage(),queryWrapper);
        Page<MaterialAcceptVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialAcceptVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialAcceptDto
    * @return
    */
    @Override
    public Page lists(MaterialAcceptDto materialAcceptDto) {
        Page<MaterialAcceptVo> pageVo = materialAcceptMapper.getPageList(materialAcceptDto.getPage(),materialAcceptDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialAcceptVo get(Long id) {
        MaterialAccept materialAccept = super.getById(id);
        MaterialAcceptVo materialAcceptVo = null;
        if(materialAccept !=null){
            materialAcceptVo = dozerMapper.map(materialAccept,MaterialAcceptVo.class);
            //根据id获取materialAcceptAssetsVoList
            List<MaterialAcceptAssetsVo> materialAcceptAssetsVoList = materialAcceptAssetsService.getByAcceptId(id);
            //设置materialAcceptVo，以便构建其对应的子表数据
            materialAcceptVo.setMaterialAcceptAssetsList(materialAcceptAssetsVoList);

            //根据id获取materialAcceptDetailVoList
            List<MaterialAcceptDetailVo> materialAcceptDetailVoList = materialAcceptDetailService.getByAcceptId(id);
            //设置materialAcceptVo，以便构建其对应的子表数据
            materialAcceptVo.setMaterialAcceptDetailList(materialAcceptDetailVoList);
        }
        return materialAcceptVo;
    }

    /**
    * 保存实体
    * @param materialAcceptDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialAcceptDto materialAcceptDto) {
        //获取申请编号
        if(StringUtils.isBlank(materialAcceptDto.getCode())){
           getMaxCode(materialAcceptDto);
        }
        MaterialAccept accept = dozerMapper.map(materialAcceptDto,MaterialAccept.class);

        if(ObjectUtil.isEmpty(accept.getAssetUserId())){
            accept.setAssetUserId(accept.getApplyUserId());
            accept.setAssetUserName(accept.getApplyUserName());
        }
        if(ObjectUtil.isEmpty(accept.getAssetDeptId())){
            accept.setAssetDeptId(accept.getApplyUserDeptId());
            accept.setAssetDeptName(accept.getApplyUserDeptName());
        }
        if(ObjectUtil.isEmpty(accept.getAssetOrgId())){
            accept.setAssetOrgId(accept.getApplyUserOrgId());
            accept.setAssetOrgName(accept.getApplyUserOrgName());
        }

        Boolean result = super.save(accept);
        Boolean resultList = saveSubList(materialAcceptDto);
        if (result && resultList) {
            log.debug("debugger");
        }
        return result && resultList;
    }

    /**
     * 获取申请编号
     * @return
     */
    private void getMaxCode(MaterialAcceptDto materialAcceptDto){
        //获取申请编号
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(materialAcceptDto,HashMap.class);
        String code = dictClient.getRuleValue("material_accept","code",entityMap);
        log.debug("规则值:{}",code);
        if(StringUtils.isNotBlank(code)){
            materialAcceptDto.setCode(code);
        }
    }
    /**
     * 保存集合
     * @param materialAcceptDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialAcceptDto> materialAcceptDtos){
        List<MaterialAccept> MaterialAccepts = DozerUtils.mapList(dozerMapper, materialAcceptDtos, MaterialAccept.class);
        super.saveBatch(MaterialAccepts);
        for (MaterialAcceptDto materialAcceptDto : materialAcceptDtos){
            saveSubList(materialAcceptDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialAcceptDto
     * @return
     */
    @Transactional
    Boolean saveSubList(MaterialAcceptDto materialAcceptDto){
        //获取其子表集合
        List<MaterialAcceptAssetsDto> materialAcceptAssetsDtoList = materialAcceptDto.getMaterialAcceptAssetsList();
        //获取其子表集合
        List<MaterialAcceptDetailDto> materialAcceptDetailDtoList = materialAcceptDto.getMaterialAcceptDetailList();

        //设置主子表关联关系，外键
        for (MaterialAcceptAssetsDto materialAcceptAssetsDto : materialAcceptAssetsDtoList ) {
            materialAcceptAssetsDto.setAcceptId(materialAcceptDto.getId());
            for (MaterialAcceptDetailDto materialAcceptDetailDto : materialAcceptDetailDtoList) {
                materialAcceptDetailDto.setAcceptId(materialAcceptDto.getId());
                if(materialAcceptDetailDto.getAssetsId().equals(materialAcceptAssetsDto.getAssetsId())){
                    materialAcceptDetailDto.setAcceptAssetsId(materialAcceptAssetsDto.getId());
                }
            }
        }
        //调用保存子表的集合方法
        materialAcceptAssetsService.saveList(materialAcceptAssetsDtoList);
        //调用保存子表的集合方法
        materialAcceptDetailService.saveList(materialAcceptDetailDtoList);
        return null;
    }

    /**
     * 修改实体
     * @param materialAcceptDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MaterialAcceptDto materialAcceptDto) {
        materialAcceptDto.setUpdateTime(LocalDateTime.now());
        MaterialAccept materialAccept = dozerMapper.map(materialAcceptDto,MaterialAccept.class);
        return super.updateById(materialAccept);
    }

    /**
    * 修改子类实体
    * @param materialAcceptDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialAcceptDto materialAcceptDto) {
        List<MaterialAcceptAssetsDto> materialAcceptAssetsDtoList = materialAcceptDto.getMaterialAcceptAssetsList();
        List<MaterialAcceptDetailDto> materialAcceptDetailDtoList = materialAcceptDto.getMaterialAcceptDetailList();
        //设置主子表关联关系，外键
        for (MaterialAcceptAssetsDto materialAcceptAssetsDto : materialAcceptAssetsDtoList ) {
            materialAcceptAssetsDto.setAcceptId(materialAcceptDto.getId());
            for (MaterialAcceptDetailDto materialAcceptDetailDto : materialAcceptDetailDtoList) {
                materialAcceptDetailDto.setAcceptId(materialAcceptDto.getId());
                if(materialAcceptDetailDto.getAssetsId().equals(materialAcceptAssetsDto.getAssetsId())){
                    materialAcceptDetailDto.setAcceptAssetsId(materialAcceptAssetsDto.getId());
                }
            }
        }

        if(materialAcceptAssetsDtoList != null && !materialAcceptAssetsDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialAcceptAssets> materialAcceptAssetsListQueryWrapper = new LambdaQueryWrapper<>();
            materialAcceptAssetsListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialAcceptDto.getId()),MaterialAcceptAssets::getAcceptId,materialAcceptDto.getId());
            List<MaterialAcceptAssets> materialAcceptAssetss = DozerUtils.mapList(dozerMapper, materialAcceptAssetsDtoList, MaterialAcceptAssets.class);
            //调用更新方法
            materialAcceptAssetsService.saveOrUpdateOrDeleteBatch(materialAcceptAssetsListQueryWrapper,materialAcceptAssetss,materialAcceptAssetss.size());
        }
        if(materialAcceptDetailDtoList != null && !materialAcceptDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialAcceptDetail> materialAcceptDetailListQueryWrapper = new LambdaQueryWrapper<>();
            materialAcceptDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialAcceptDto.getId()),MaterialAcceptDetail::getAcceptId,materialAcceptDto.getId());
            List<MaterialAcceptDetail> materialAcceptDetails = DozerUtils.mapList(dozerMapper, materialAcceptDetailDtoList, MaterialAcceptDetail.class);
            //调用更新方法
            materialAcceptDetailService.saveOrUpdateOrDeleteBatch(materialAcceptDetailListQueryWrapper,materialAcceptDetails,materialAcceptDetails.size());
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
        materialAcceptAssetsService.deleteByFkId(id);
        materialAcceptDetailService.deleteByAcceptId(id);
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
    public List<MaterialAcceptVo> getByFkIdVo(Long id){
        return null;
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        LambdaQueryWrapper<MaterialAccept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialAccept::getCamundaProcinsId,procInstId);
        MaterialAccept materialAccept = materialAcceptMapper.selectOne(queryWrapper);
        if(materialAccept !=null){
            this.delete(materialAccept.getId());
        }
    }

    /**
     * 根据流程实例id获取数据
     * @param procInstId
     * @return
     */
    @Override
    public MaterialAcceptVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaterialAccept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialAccept::getCamundaProcinsId,procInstId);
        MaterialAccept materialAccept = materialAcceptMapper.selectOne(queryWrapper);
        MaterialAcceptVo materialAcceptVo = null;
        if(materialAccept !=null){
            materialAcceptVo = dozerMapper.map(materialAccept,MaterialAcceptVo.class);
            //根据id获取materialAcceptAssetsVoList
            List<MaterialAcceptAssetsVo> materialAcceptAssetsVoList = materialAcceptAssetsService.getByAcceptId(materialAccept.getId());
            //设置materialAcceptVo，以便构建其对应的子表数据
            materialAcceptVo.setMaterialAcceptAssetsList(materialAcceptAssetsVoList);

            //根据id获取materialAcceptDetailVoList
            //List<MaterialAcceptDetailVo> materialAcceptDetailVoList = materialAcceptDetailService.getByAcceptId(materialAccept.getId());
            //设置materialAcceptVo，以便构建其对应的子表数据
            //materialAcceptVo.setMaterialAcceptDetailList(materialAcceptDetailVoList);

        }
        return materialAcceptVo;
    }

    /**
     * 领用流程保存前方法
     *      保存前根据数据库信息，恢复资产数值
     *      增加可用数量，减少领用数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Boolean acceptSaveBefore(String processInstanceId) {
        Boolean result = true;
        MaterialAcceptVo materialAcceptVo = this.getByProc(processInstanceId);
        for (MaterialAcceptAssetsVo materialAcceptAssetsVo : materialAcceptVo.getMaterialAcceptAssetsList()) {
            String itemType = materialAcceptAssetsVo.getItemType();
            //获取本次领用的数量
            BigDecimal acceptNumber = materialAcceptAssetsVo.getAcceptNumber();
            //对库存台账的物资可用数量进行修改
            if (!StringUtils.isBlank(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {
                //此次领用数量
                AssetsVo assetsVo = assetsService.get(materialAcceptAssetsVo.getAssetsId());
                AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                BigDecimal oldUsableNumber = assetsVo.getUsableNumber();
                BigDecimal newUsableNumber = oldUsableNumber.add(acceptNumber);
                assetsDto.setUsableNumber(newUsableNumber);
                assetsService.update(assetsDto);
                log.debug("资产:{}，保存后可用数量为:{}，保存后领用数量:{}", assetsDto.getId(), newUsableNumber);
            }else{//对耗材台账的数量进行修改
                SuppliesVo suppliesVo = suppliesService.get(materialAcceptAssetsVo.getAssetsId());
                SuppliesDto suppliesDto = dozerMapper.map(suppliesVo, SuppliesDto.class);
                BigDecimal oldSupUsableNumber = suppliesDto.getUsableNumber();
                BigDecimal newSupUsableNumber = oldSupUsableNumber.add(acceptNumber);
                suppliesDto.setUsableNumber(newSupUsableNumber);
                suppliesService.update(suppliesDto);
                log.debug("资产:{}，保存后可用数量为:{}，保存后领用数量:{}", suppliesDto.getId(), suppliesDto);

            }
        }
        return result;

    }

    /**
     * 领用流程保存后方法
     *      减少可用数量
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Result acceptSaveAfter(String processInstanceId) {
        MaterialAcceptVo materialAcceptVo = this.getByProc(processInstanceId);
        for (MaterialAcceptAssetsVo materialAcceptAssetsVo : materialAcceptVo.getMaterialAcceptAssetsList()) {
            String itemType = materialAcceptAssetsVo.getItemType();
            //获取本次领用的数量
            BigDecimal acceptNumber = materialAcceptAssetsVo.getAcceptNumber();
            //对库存台账的物资可用数量进行修改
            if (!StringUtils.isBlank(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {
                //此次领用修改数量
                AssetsVo assetsVo = assetsService.get(materialAcceptAssetsVo.getAssetsId());
                AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                BigDecimal oldUsableNumber = assetsVo.getUsableNumber();
                BigDecimal newUsableNumber = oldUsableNumber.subtract(acceptNumber);
                assetsDto.setUsableNumber(newUsableNumber);
                assetsService.update(assetsDto);
                log.debug("资产:{}，保存后可用数量为:{}，保存后领用数量:{}", assetsDto.getId(), newUsableNumber);
            }else{//对耗材台账的数量进行修改
                SuppliesVo suppliesVo = suppliesService.get(materialAcceptAssetsVo.getAssetsId());
                SuppliesDto suppliesDto = dozerMapper.map(suppliesVo, SuppliesDto.class);
                BigDecimal oldSupUsableNumber = suppliesDto.getUsableNumber();
                BigDecimal newSupUsableNumber = oldSupUsableNumber.subtract(acceptNumber);
                suppliesDto.setUsableNumber(newSupUsableNumber);
                suppliesService.update(suppliesDto);
                log.debug("资产:{}，保存后可用数量为:{}，保存后领用数量:{}", suppliesDto.getId(), suppliesDto);

            }
        }
        return Result.success();
    }

    /**
     * 领用流程完成后
     *      生成领用结果数据
     * @param processInstanceId
     * @return
     */
    @Transactional
    @Override
    public Boolean acceptAuditSuccess(String processInstanceId) {
        Boolean result=true;

        MaterialAcceptVo materialAcceptVo = this.getByProc(processInstanceId);

        for (MaterialAcceptAssetsVo materialAcceptAssetsVo : materialAcceptVo.getMaterialAcceptAssetsList()) {
            String itemType = materialAcceptAssetsVo.getItemType();
            //获取本次领用的数量
            BigDecimal acceptNumber = materialAcceptAssetsVo.getAcceptNumber();
            //对库存台账的物资可用数量进行修改
            if (!StringUtils.isBlank(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {
                //此次领用修改数量
//                AssetsVo assetsVo = assetsService.get(materialAcceptAssetsVo.getAssetsId());
//                AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
//                BigDecimal oldUsableNumber = assetsVo.getUsableNumber();
//                BigDecimal newUsableNumber = oldUsableNumber.subtract(acceptNumber);
//                assetsDto.setUsableNumber(newUsableNumber);
//                assetsService.update(assetsDto);
//                log.debug("资产:{}，保存后可用数量为:{}，保存后领用数量:{}", assetsDto.getId(), newUsableNumber);

            }else{//对耗材台账的数量进行修改
//                SuppliesVo suppliesVo = suppliesService.get(materialAcceptAssetsVo.getAssetsId());
//                SuppliesDto suppliesDto = dozerMapper.map(suppliesVo, SuppliesDto.class);
//                BigDecimal oldSupUsableNumber = suppliesDto.getUsableNumber();
//                BigDecimal newSupUsableNumber = oldSupUsableNumber.subtract(acceptNumber);
//                suppliesDto.setUsableNumber(newSupUsableNumber);
//                suppliesService.update(suppliesDto);
//                log.debug("资产:{}，保存后可用数量为:{}，保存后领用数量:{}", suppliesDto.getId(), suppliesDto);

            }

            if(ObjectUtil.isEmpty(materialAcceptAssetsVo.getDistributeNumber())){
                materialAcceptAssetsVo.setDistributeNumber(BigDecimal.ZERO);
            }
            if(ObjectUtil.isEmpty(materialAcceptAssetsVo.getNotDistributeNumber())){
                materialAcceptAssetsVo.setNotDistributeNumber(materialAcceptAssetsVo.getAcceptNumber());
            }
            if(ObjectUtil.isEmpty(materialAcceptAssetsVo.getSignNumber())){
                materialAcceptAssetsVo.setSignNumber(BigDecimal.ZERO);
            }
            if(ObjectUtil.isEmpty(materialAcceptAssetsVo.getNotSignNumber())){
                materialAcceptAssetsVo.setNotSignNumber(BigDecimal.ZERO);
            }
            MaterialAcceptAssetsDto acceptAssetsDto = dozerMapper.map(materialAcceptAssetsVo,MaterialAcceptAssetsDto.class);
            materialAcceptAssetsService.update(acceptAssetsDto);
        }
        MaterialAcceptDto acceptDto = dozerMapper.map(materialAcceptVo,MaterialAcceptDto.class);
        this.update(acceptDto);
        return result;
    }

    @Override
    @Transactional
    public MaterialAcceptVo procSaveOrUpdate(MaterialAcceptDto materialAcceptDto) {
        //判断是否未编辑、是否是起草
        //若不是起草(流程中保存)，手动调用保存前方法
        MaterialAcceptVo materialAcceptVo = this.get(materialAcceptDto.getId());
        if (ObjectUtils.isNotEmpty(materialAcceptVo)
                && ObjectUtils.isNotEmpty(materialAcceptVo.getAuditStatus())
                && materialAcceptVo.getAuditStatus() != WfProcessEnum.DRAFT.getType()){
            this.acceptSaveBefore(materialAcceptVo.getCamundaProcinsId());
        }
        Long assetUserId = materialAcceptDto.getAssetUserId();
        if(ObjectUtil.isNotEmpty(assetUserId)){
            materialAcceptDto.setAssetUserId(materialAcceptDto.getApplyUserId());
            materialAcceptDto.setAssetUserName(materialAcceptDto.getApplyUserName());
        }
        materialAcceptAssetsService.deleteByAcceptId(materialAcceptDto.getId());


        BigDecimal sumTotalNumber = BigDecimal.ZERO;
//        BigDecimal sumAcceptNumber = BigDecimal.ZERO;

        List<MaterialAcceptAssetsDto> acceptAssetsDtoList = materialAcceptDto.getMaterialAcceptAssetsList();
        if(CollectionUtils.isNotEmpty(acceptAssetsDtoList)){
            List<MaterialAcceptAssets> acceptAssetsList = DozerUtils.mapList(dozerMapper,acceptAssetsDtoList,MaterialAcceptAssets.class);
            for (MaterialAcceptAssets acceptAssets : acceptAssetsList) {
                acceptAssets.setAcceptId(materialAcceptDto.getId());

                BigDecimal acceptNumber = acceptAssets.getAcceptNumber();
                acceptNumber = ObjectUtil.isEmpty(acceptNumber) ? BigDecimal.ZERO : acceptNumber ;
//                sumAcceptNumber = sumAcceptNumber.add(acceptAssets.getAcceptNumber());
                sumTotalNumber = sumTotalNumber.add(acceptNumber);

            }
            materialAcceptAssetsService.saveOrUpdateBatch(acceptAssetsList);
        }

        //获取申请编号
        if(StringUtils.isBlank(materialAcceptDto.getCode())){
            getMaxCode(materialAcceptDto);
        }
        MaterialAccept materialAccept = dozerMapper.map(materialAcceptDto,MaterialAccept.class);
//        if(ObjectUtil.isEmpty(materialAccept.getSumTotalNumber())){
            materialAccept.setSumTotalNumber(sumTotalNumber);
//        }
        if(ObjectUtil.isEmpty(materialAccept.getSumTotalAmount())){
            materialAccept.setSumTotalAmount(BigDecimal.ZERO);
        }
        if(ObjectUtil.isEmpty(materialAccept.getSumTotalTaxAmount())){
            materialAccept.setSumTotalTaxAmount(BigDecimal.ZERO);
        }
        if(ObjectUtil.isEmpty(materialAccept.getSumTotalUntaxedAmount())){
            materialAccept.setSumTotalUntaxedAmount(BigDecimal.ZERO);
        }
        materialAccept.setDistributeNumber(BigDecimal.ZERO);
        materialAccept.setNotDistributeNumber(sumTotalNumber);
        materialAccept.setSignNumber(BigDecimal.ZERO);
        materialAccept.setNotSignNumber(BigDecimal.ZERO);

        super.saveOrUpdate(materialAccept);
        return this.getByProc(materialAccept.getCamundaProcinsId());
    }

    @Override
    public Result sendTask(String procInstId) {
        MaterialAcceptVo materialAcceptVo = this.getByProc(procInstId);
        log.debug("主任务开始---------------------------申请信息:{}",materialAcceptVo);
        TodoDto todoDto=materialAcceptVo.getTodo();
        TodoVo todoVo=centerClient.send(todoDto);
        log.debug("主任务开始---------------------------任务信息:{}",todoVo);
        Boolean result=true;
        if(ObjectUtils.isNotEmpty(todoVo)){
            MaterialAccept materialAccept = new MaterialAccept();
            materialAccept.setId(materialAcceptVo.getId());
            materialAccept.setTaskInstId(todoVo.getTaskInstId());
            materialAccept.setParentTaskNodeId(todoVo.getParentTaskNodeId());
            result=super.updateById(materialAccept);
        }
        return Result.success(result);


//        StartTodoDto startTodoDto = new StartTodoDto();
//        startTodoDto.setSysCode("asset");
//        startTodoDto.setSysName("物资");
//        startTodoDto.setTaskSource("领用");
//        startTodoDto.setTaskName(AppUserUtil.getLoginAppUser().getUserNameCh() + "领用申请");
//        startTodoDto.setTaskReceivePersonType(TaskTodoTypeEnum.POST.getType());
//
//
//        startTodoDto.setTaskReceivePersonId("1553943331635990530");
//        startTodoDto.setTaskReceivePersonName("物项管理员");
//        startTodoDto.setApplyCode("Asset_Accept" + System.currentTimeMillis());
//        startTodoDto.setApplyReason(AppUserUtil.getLoginAppUser().getUserNameCh() + "时间：" + LocalDate.now() + "提交了领用申请");
//        com.netwisd.base.center.starter.vo.TodoVo todoVo = ceneterService.startTask(startTodoDto);
//        log.info("启动任务成功:{}", todoVo);
//
//        SendTodoDto sendTodoDto = new SendTodoDto();
//        sendTodoDto.setTaskNodeInstId(todoVo.getTaskNodeInstId());
//        sendTodoDto.setTaskSource("派发");
//        sendTodoDto.setTaskName(AppUserUtil.getLoginAppUser().getUserNameCh() + "开始派发");
//        sendTodoDto.setTaskReceivePersonType(TaskTodoTypeEnum.USER.getType());
//        sendTodoDto.setTaskReceivePersonId("0");
//        sendTodoDto.setTaskReceivePersonName("超级管理员");
//        sendTodoDto.setApplyCode("paifa" + System.currentTimeMillis());
//        sendTodoDto.setApplyReason(AppUserUtil.getLoginAppUser().getUserNameCh() + "时间：" + LocalDate.now() + "收到派发任务");
//
//        List<TodoFunDto> list = new ArrayList<>();
//        TodoFunDto todoFunDto = new TodoFunDto();
//        todoFunDto.setFunUnique("model-asset-distribute");
//        Map funParam = new HashMap();
//        funParam.put("acceptId", materialAcceptVo.getId());
//        todoFunDto.setFuncParam(JSONUtil.toJsonStr(funParam));
//        todoFunDto.setFullName("派发");
//        list.add(todoFunDto);
//        sendTodoDto.setTodoFunList(list);
//
//        com.netwisd.base.center.starter.vo.TodoVo sendTodoVo = ceneterService.sendTask(sendTodoDto);
//
//        return Result.success(sendTodoVo);
    }

    @Override
    public Boolean processTask(String procInstId) {
        MaterialAcceptVo materialAcceptVo = this.getByProc(procInstId);
        log.debug("派发任务sssssssssssssssssssssssssssssssssss申请信息:{}",materialAcceptVo);
        TodoDto todoDto=new TodoDto();
        todoDto.setSysCode("asset");
        todoDto.setSysName("物资");
        todoDto.setTaskSource("领用申请");
        todoDto.setTaskStartUserid(materialAcceptVo.getApplyUserId());
        todoDto.setTaskStartUserName(materialAcceptVo.getApplyUserName());
        todoDto.setTaskName("派发");
        todoDto.setTaskTodoPersonType(1);
        todoDto.setTaskTodoTypeId(0l);
        todoDto.setApplyCode(materialAcceptVo.getCode());
        todoDto.setApplyWfId(materialAcceptVo.getCamundaTaskId());
        todoDto.setApplyReason(materialAcceptVo.getReason());
        todoDto.setParentTaskNodeId(materialAcceptVo.getTaskInstId());
        todoDto.setTaskInstId(materialAcceptVo.getTaskInstId());
        log.debug("派发任务sssssssssssssssssssssssssssssssssss:{}",todoDto);
        TodoVo todoVo=centerClient.toProcess(todoDto);
        Boolean result=true;
        if(ObjectUtils.isNotEmpty(todoVo)){
            MaterialAccept materialAccept = new MaterialAccept();
            materialAccept.setId(materialAcceptVo.getId());
            materialAccept.setTaskInstId(todoVo.getTaskInstId());
            materialAccept.setParentTaskNodeId(todoVo.getParentTaskNodeId());
            materialAccept.setTaskId(todoVo.getId());
            result=super.updateById(materialAccept);
        }
        return result;
    }

    @Override
    public Page getAcceptList(MaterialAcceptDto materialAcceptDto) {
        return null;
    }

    @Override
    public Boolean sendDistriButeTask(String processInstanceId) {


//        StartTodoDto startTodoDto = new StartTodoDto();
//        startTodoDto.setSysCode("asset");
//        startTodoDto.setSysName("物资");
//        startTodoDto.setTaskSource("领用");
//        startTodoDto.setTaskName(AppUserUtil.getLoginAppUser().getUserNameCh() + "领用申请");
//        startTodoDto.setTaskReceivePersonType(TaskTodoTypeEnum.USER.getType());
//        startTodoDto.setTaskReceivePersonId("0");
//        startTodoDto.setTaskReceivePersonName("超级管理员");
//        startTodoDto.setApplyCode("lingyong" + System.currentTimeMillis());
//        startTodoDto.setApplyReason(AppUserUtil.getLoginAppUser().getUserNameCh() + "时间：" + LocalDate.now() + "提交了领用申请");
//        TodoVo todoVo = ceneterService.startTask(startTodoDto);
//        log.info("启动任务成功:{}", todoVo);
        return null;
    }

    @Override
    public Boolean stopAccept(String procInstId) {
        Boolean result = true;
        MaterialAcceptVo acceptVo = this.getByProc(procInstId);
        if(ObjectUtil.isNotEmpty(acceptVo)){
            if (acceptVo.getAuditStatus() != WfProcessEnum.DONE.getType()){
                throw new IncloudException("该领用单还在流程中，请从流程终止!!");
            }

            List<MaterialDistributeVo> distributeVoList = materialDistributeService.getBySourceId(acceptVo.getId());

            if(CollectionUtils.isNotEmpty(distributeVoList) && distributeVoList.size()>0){
                distributeVoList.forEach(distributeVo -> {
                    if(distributeVo.getAuditStatus() != WfProcessEnum.DRAFT.getType())
                        throw new IncloudException("已经存在派发单,不能终止!!");
                });
            }else {
                result = true;
            }

            if(result){
                this.acceptSaveBefore(procInstId);
                MaterialAcceptDto acceptDto = dozerMapper.map(acceptVo,MaterialAcceptDto.class);
                acceptDto.setNotDistributeNumber(BigDecimal.ZERO);
                acceptDto.setNotSignNumber(BigDecimal.ZERO);
                result = this.update(acceptDto);
            }
        }

        return result;
    }
}
