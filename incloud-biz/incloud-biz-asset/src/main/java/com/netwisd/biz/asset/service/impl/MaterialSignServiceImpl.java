package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.constants.ItemTypeEnum;
import com.netwisd.biz.asset.constants.TaskType;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaterialSignMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.common.db.util.EntityListConvert;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 签收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
@Service
@Slf4j
public class MaterialSignServiceImpl extends BatchServiceImpl<MaterialSignMapper, MaterialSign> implements MaterialSignService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialSignMapper materialSignMapper;

    @Autowired
    private MaterialSignDetailService materialSignDetailService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private SuppliesService suppliesService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private MaterialAcceptService materialAcceptService;

    @Autowired
    private MaterialAcceptAssetsService materialAcceptAssetsService;

    @Autowired
    private MaterialAcceptDetailService materialAcceptDetailService;

    @Autowired
    private MaterialAcceptResultService materialAcceptResultService;

    @Autowired
    private MaterialDistributeService materialDistributeService;

    @Autowired
    private MaterialDistributeDetailService materialDistributeDetailService;

    @Autowired
    private SuppliesDetailService suppliesDetailService;

    @Autowired
    private AssetsSkuService assetsSkuService;

    @Autowired
    private SuppliesSkuService suppliesSkuService;

    @Autowired
    private DaybookService daybookService;

    @Autowired
    private DaybookSuppliesService daybookSuppliesService;


    /**
    * 单表简单查询操作
    * @param materialSignDto
    * @return
    */
    @Override
    public Page list(MaterialSignDto materialSignDto) {
        LambdaQueryWrapper<MaterialSign> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(MaterialSign::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(MaterialSign::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(MaterialSign::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        //根据实际业务构建具体的参数做查询
        String  searchCondition= materialSignDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(MaterialSign::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(MaterialSign::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialSign::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialSign::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialSign::getApplyTime,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialSign::getExplanation,searchCondition));
            });
        }
        queryWrapper.orderByDesc(MaterialSign::getApplyTime);
        Page<MaterialSign> page = materialSignMapper.selectPage(materialSignDto.getPage(),queryWrapper);
        Page<MaterialSignVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialSignVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialSignDto
    * @return
    */
    @Override
    public Page lists(MaterialSignDto materialSignDto) {
        Page<MaterialSignVo> pageVo = materialSignMapper.getPageList(materialSignDto.getPage(),materialSignDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialSignVo get(Long id) {
        MaterialSign materialSign = super.getById(id);
        MaterialSignVo materialSignVo = null;
        if(materialSign !=null){
            materialSignVo = dozerMapper.map(materialSign,MaterialSignVo.class);
            //根据id获取materialSignDetailVoList
            List<MaterialSignDetailVo> materialSignDetailVoList = materialSignDetailService.getByFkIdVo(id);
            //设置materialSignVo，以便构建其对应的子表数据
            materialSignVo.setDetailList(materialSignDetailVoList);
        }
        return materialSignVo;
    }

    /**
    * 保存实体
    * @param materialSignDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialSignDto materialSignDto) {
        if(StringUtils.isBlank(materialSignDto.getCode())){
            getMaxCode(materialSignDto);
        }
        MaterialSign materialSign = dozerMapper.map(materialSignDto,MaterialSign.class);
        Boolean result = super.save(materialSign);
        Boolean resultList = saveSubList(materialSignDto);
        if (result && resultList) {
            log.debug("debugger");
        }
        return result && resultList;
    }
    /**
     * 获取编码规则值
     * @param materialSignDto
     * @return
     */
    private void getMaxCode(MaterialSignDto materialSignDto){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(materialSignDto, HashMap.class);
        String code = dictClient.getRuleValue("material_sign","code", entityMap);
        log.debug("规则值：{}",code);
        if (StringUtils.isNotBlank(code)){
            materialSignDto.setCode(code);
        }

    }
    /**
     * 保存集合
     * @param materialSignDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialSignDto> materialSignDtos){
        List<MaterialSign> MaterialSigns = DozerUtils.mapList(dozerMapper, materialSignDtos, MaterialSign.class);
        super.saveBatch(MaterialSigns);
        for (MaterialSignDto materialSignDto : materialSignDtos){
            saveSubList(materialSignDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialSignDto
     * @return
     */
    @Transactional
    Boolean saveSubList(MaterialSignDto materialSignDto){
        Boolean result = true;
        //获取其子表集合
        List<MaterialSignDetailDto> materialSignDetailDtoList = materialSignDto.getDetailList();
        if(materialSignDetailDtoList != null && !materialSignDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(materialSignDto,materialSignDetailDtoList);
            //给子表关联id赋值
            materialSignDetailDtoList.forEach(materialSignDetailDto -> {
                materialSignDetailDto.setSignId(materialSignDto.getId());
                materialSignDetailDto.setSignCode(materialSignDto.getCode());
            });
            //调用保存子表的集合方法
           result = materialSignDetailService.saveList(materialSignDetailDtoList);
        }
        return result;
    }

    /**
     * 修改实体
     * @param materialSignDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialSignDto materialSignDto) {
        materialSignDto.setUpdateTime(LocalDateTime.now());
        MaterialSign materialSign = dozerMapper.map(materialSignDto,MaterialSign.class);
        super.updateById(materialSign);
        updateSub(materialSignDto);
    }

    /**
    * 修改子类实体
    * @param materialSignDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialSignDto materialSignDto) {
        List<MaterialSignDetailDto> materialSignDetailDtoList = materialSignDto.getDetailList();
        if(materialSignDetailDtoList != null && !materialSignDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialSignDetail> materialSignDetailListQueryWrapper = new LambdaQueryWrapper<>();
            materialSignDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialSignDto.getId()),MaterialSignDetail::getSignId,materialSignDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            EntityListConvert.convert(materialSignDto,materialSignDetailDtoList);
            List<MaterialSignDetail> materialSignDetails = DozerUtils.mapList(dozerMapper, materialSignDetailDtoList, MaterialSignDetail.class);
            //调用更新方法
            materialSignDetailService.saveOrUpdateOrDeleteBatch(materialSignDetailListQueryWrapper,materialSignDetails,materialSignDetails.size());
            //如果子可能还有子，那么遍历子，然后调用其子方法的updateSub
            if(materialSignDetailDtoList != null && !materialSignDetailDtoList.isEmpty()){
                for(MaterialSignDetailDto materialSignDetailDto : materialSignDetailDtoList){
                    materialSignDetailService.updateSub(materialSignDetailDto);
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
        materialSignDetailService.deleteByFkId(id);
    }

    /**
     * 流程中新增或修改
     * @param materialSignDto
     * @return
     */
    @Transactional
    @Override
    public MaterialSignVo procSaveOrUpdate(MaterialSignDto materialSignDto) {
        MaterialSignVo signVo = this.get(materialSignDto.getId());
        if (ObjectUtils.isNotEmpty(signVo)
                && ObjectUtils.isNotEmpty(signVo.getAuditStatus())
                && signVo.getAuditStatus() != WfProcessEnum.DRAFT.getType()){
            this.procSaveBefore(signVo.getCamundaProcinsId());
        }

        if (StringUtils.isBlank(materialSignDto.getCode())){
            getMaxCode(materialSignDto);
        }
        MaterialSign materialSign = dozerMapper.map(materialSignDto, MaterialSign.class);
        BigDecimal sumTotalNumber = BigDecimal.ZERO;

        materialSignDetailService.deleteByFkId(materialSignDto.getId());
        List<MaterialSignDetailDto> detailDtoList = materialSignDto.getDetailList();
        if (CollectionUtils.isNotEmpty(detailDtoList)){
            List<MaterialSignDetail> detailList = DozerUtils.mapList(dozerMapper, detailDtoList, MaterialSignDetail.class);
            for (MaterialSignDetail detail: detailList) {
                detail.setSignId(materialSign.getId());
                detail.setSignCode(materialSign.getCode());

                BigDecimal signNumber = detail.getSignNumber();
                sumTotalNumber  = sumTotalNumber.add(signNumber);
            }
            materialSignDetailService.saveOrUpdateBatch(detailList);
        }
        if(ObjectUtil.isEmpty(materialSign.getSignType())){
            materialSign.setSignType(TaskType.ACCEPT.getCode());
        }
        materialSign.setSumTotalNumber(sumTotalNumber);
        super.saveOrUpdate(materialSign);
        return this.getByProc(materialSign.getCamundaProcinsId());
    }

    /**
     * 通过流程实例id进行查询
     * @param procInstId
     * @return
     */
    @Override
    public MaterialSignVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaterialSign> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialSign::getCamundaProcinsId,procInstId);
        List<MaterialSign> materialSignList = super.list(queryWrapper);
        MaterialSignVo materialSignVo =null;
        if (materialSignList != null && materialSignList.size()>0){
            materialSignVo = dozerMapper.map(materialSignList.get(0), MaterialSignVo.class);
            //根据id获取materialSignDetailVoList
            List<MaterialSignDetailVo> materialSignDetailVoList = materialSignDetailService.getByFkIdVo(materialSignVo.getId());
            //设置maintStorageVo，以便构建其对应的子表数据
            materialSignVo.setDetailList(materialSignDetailVoList);
        }
        return materialSignVo;
    }

    /**
     * 通过流程实例id进行删除
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        MaterialSignVo materialSignVo = this.getByProc(procInstId);
        if (materialSignVo != null) {
            this.delete(materialSignVo.getId());
        }
    }

    @Override
    @Transactional
    public Boolean procSaveBefore(String procInstId) {
        MaterialSignVo signVo = this.getByProc(procInstId);
        List<MaterialSignDetailVo> detailVoList = signVo.getDetailList();
        for (MaterialSignDetailVo detailVo : detailVoList) {
            BigDecimal signNumber = detailVo.getSignNumber();

            MaterialAcceptVo acceptVo = materialAcceptService.get(detailVo.getSourceId());
            if(ObjectUtil.isNotEmpty(acceptVo)){
                MaterialAcceptDto acceptDto = dozerMapper.map(acceptVo,MaterialAcceptDto.class);
                BigDecimal oldNotSignNumber= acceptDto.getNotSignNumber();
                oldNotSignNumber = ObjectUtil.isEmpty(oldNotSignNumber) ? BigDecimal.ZERO : oldNotSignNumber;
                BigDecimal newNotSignNumber = oldNotSignNumber.add(signNumber);
                acceptDto.setNotSignNumber(newNotSignNumber);
                materialAcceptService.update(acceptDto);
            }
            MaterialAcceptAssetsVo acceptAssetsVo = materialAcceptAssetsService.get(detailVo.getSourceAssetsId());
            if(ObjectUtil.isNotEmpty(acceptAssetsVo)){
                MaterialAcceptAssetsDto acceptAssetsDto = dozerMapper.map(acceptAssetsVo,MaterialAcceptAssetsDto.class);
                BigDecimal oldNotSignNumber= acceptAssetsDto.getNotSignNumber();
                oldNotSignNumber = ObjectUtil.isEmpty(oldNotSignNumber) ? BigDecimal.ZERO : oldNotSignNumber;
                BigDecimal newNotSignNumber = oldNotSignNumber.add(signNumber);
                acceptAssetsDto.setNotSignNumber(newNotSignNumber);
                materialAcceptAssetsService.update(acceptAssetsDto);
            }

            MaterialDistributeVo distributeVo = materialDistributeService.get(detailVo.getBusinessId());
            if(ObjectUtil.isNotEmpty(distributeVo)){
                MaterialDistributeDto distributeDto = dozerMapper.map(distributeVo,MaterialDistributeDto.class);
                BigDecimal oldNotSignNumber= distributeDto.getNotSignNumber();
                oldNotSignNumber = ObjectUtil.isEmpty(oldNotSignNumber) ? BigDecimal.ZERO : oldNotSignNumber;
                BigDecimal newNotSignNumber = oldNotSignNumber.add(signNumber);
                distributeDto.setNotSignNumber(newNotSignNumber);
                materialDistributeService.update(distributeDto);
            }
            MaterialDistributeDetailVo distributeDetailVo = materialDistributeDetailService.get(detailVo.getBusinessDetailId());
            if(ObjectUtil.isNotEmpty(distributeDetailVo)){
                MaterialDistributeDetailDto distributeDetailDto = dozerMapper.map(distributeDetailVo,MaterialDistributeDetailDto.class);
                BigDecimal oldNotSignNumber= distributeDetailDto.getNotSignNumber();
                oldNotSignNumber = ObjectUtil.isEmpty(oldNotSignNumber) ? BigDecimal.ZERO : oldNotSignNumber;
                BigDecimal newNotSignNumber = oldNotSignNumber.add(signNumber);
                distributeDetailDto.setNotSignNumber(newNotSignNumber);
                materialDistributeDetailService.update(distributeDetailDto);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean procSaveAfter(String procInstId) {
        MaterialSignVo signVo = this.getByProc(procInstId);
        List<MaterialSignDetailVo> detailVoList = signVo.getDetailList();
        for (MaterialSignDetailVo detailVo : detailVoList) {
            BigDecimal signNumber = detailVo.getSignNumber();

            MaterialAcceptVo acceptVo = materialAcceptService.get(detailVo.getSourceId());
            if(ObjectUtil.isNotEmpty(acceptVo)){
                MaterialAcceptDto acceptDto = dozerMapper.map(acceptVo,MaterialAcceptDto.class);
                BigDecimal oldNotSignNumber= acceptDto.getNotSignNumber();
                oldNotSignNumber = ObjectUtil.isEmpty(oldNotSignNumber) ? BigDecimal.ZERO : oldNotSignNumber;
                BigDecimal newNotSignNumber = oldNotSignNumber.subtract(signNumber);
                acceptDto.setNotSignNumber(newNotSignNumber);
                materialAcceptService.update(acceptDto);
            }
            MaterialAcceptAssetsVo acceptAssetsVo = materialAcceptAssetsService.get(detailVo.getSourceAssetsId());
            if(ObjectUtil.isNotEmpty(acceptAssetsVo)){
                MaterialAcceptAssetsDto acceptAssetsDto = dozerMapper.map(acceptAssetsVo,MaterialAcceptAssetsDto.class);
                BigDecimal oldNotSignNumber= acceptAssetsDto.getNotSignNumber();
                oldNotSignNumber = ObjectUtil.isEmpty(oldNotSignNumber) ? BigDecimal.ZERO : oldNotSignNumber;
                BigDecimal newNotSignNumber = oldNotSignNumber.subtract(signNumber);
                acceptAssetsDto.setNotSignNumber(newNotSignNumber);
                materialAcceptAssetsService.update(acceptAssetsDto);
            }

            MaterialDistributeVo distributeVo = materialDistributeService.get(detailVo.getBusinessId());
            if(ObjectUtil.isNotEmpty(distributeVo)){
                MaterialDistributeDto distributeDto = dozerMapper.map(distributeVo,MaterialDistributeDto.class);
                BigDecimal oldNotSignNumber= distributeDto.getNotSignNumber();
                oldNotSignNumber = ObjectUtil.isEmpty(oldNotSignNumber) ? BigDecimal.ZERO : oldNotSignNumber;
                BigDecimal newNotSignNumber = oldNotSignNumber.subtract(signNumber);
                distributeDto.setNotSignNumber(newNotSignNumber);
                materialDistributeService.update(distributeDto);
            }
            MaterialDistributeDetailVo distributeDetailVo = materialDistributeDetailService.get(detailVo.getBusinessDetailId());
            if(ObjectUtil.isNotEmpty(distributeDetailVo)){
                MaterialDistributeDetailDto distributeDetailDto = dozerMapper.map(distributeDetailVo,MaterialDistributeDetailDto.class);
                BigDecimal oldNotSignNumber= distributeDetailDto.getNotSignNumber();
                oldNotSignNumber = ObjectUtil.isEmpty(oldNotSignNumber) ? BigDecimal.ZERO : oldNotSignNumber;
                BigDecimal newNotSignNumber = oldNotSignNumber.subtract(signNumber);
                distributeDetailDto.setNotSignNumber(newNotSignNumber);
                materialDistributeDetailService.update(distributeDetailDto);
            }
        }
        return true;
    }

    /**
     * 签收流程结束
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public Boolean procAuditSuccess(String procInstId) {
        Boolean result=true;
        MaterialSignVo signVo = this.getByProc(procInstId);
        for (MaterialSignDetailVo detailVo : signVo.getDetailList()) {
            String itemType = detailVo.getItemType();
            //获取本次签收的数量
            BigDecimal signNumber = detailVo.getSignNumber();

            //生成领用结果表
            MaterialAcceptResultDto resultDto = dozerMapper.map(detailVo,MaterialAcceptResultDto.class);
            resultDto.setId(null);
            resultDto.setAcceptNumber(detailVo.getSignNumber());
            resultDto.setRefundNumber(BigDecimal.ZERO);
            resultDto.setNotRefundNumber(detailVo.getSignNumber());
            resultDto.setAcceptId(detailVo.getSourceId());
            resultDto.setAcceptCode(detailVo.getSourceCode());
            resultDto.setAcceptAssetsId(detailVo.getSourceAssetsId());

            //对库存台账的物资可用数量进行修改
            if (detailVo.getSignStatus()== YesNo.YES.code){
                MaterialAcceptVo acceptVo = materialAcceptService.get(detailVo.getSourceId());
                if(ObjectUtil.isNotEmpty(acceptVo)){
                    MaterialAcceptDto acceptDto = dozerMapper.map(acceptVo,MaterialAcceptDto.class);
                    BigDecimal oldSignNumber= acceptDto.getSignNumber();
                    oldSignNumber = ObjectUtil.isEmpty(oldSignNumber) ? BigDecimal.ZERO : oldSignNumber;
                    BigDecimal newSignNumber = oldSignNumber.add(signNumber);
                    acceptDto.setSignNumber(newSignNumber);
                    materialAcceptService.update(acceptDto);

                    resultDto.setApplyTime(acceptDto.getApplyTime());
                    resultDto.setApplyUserId(acceptDto.getApplyUserId());
                    resultDto.setApplyUserName(acceptDto.getApplyUserName());
                    resultDto.setApplyUserOrgId(acceptDto.getApplyUserOrgId());
                    resultDto.setApplyUserOrgName(acceptDto.getApplyUserOrgName());
                    resultDto.setApplyUserDeptId(acceptDto.getApplyUserDeptId());
                    resultDto.setApplyUserDeptName(acceptDto.getApplyUserDeptName());

                    resultDto.setAssetUserId(acceptDto.getApplyUserId());
                    resultDto.setAssetUserName(acceptDto.getApplyUserName());
                    resultDto.setAssetOrgId(acceptDto.getApplyUserOrgId());
                    resultDto.setAssetOrgName(acceptDto.getApplyUserOrgName());
                    resultDto.setAssetDeptId(acceptDto.getApplyUserDeptId());
                    resultDto.setAssetDeptName(acceptDto.getApplyUserDeptName());
                }
                MaterialAcceptAssetsVo acceptAssetsVo = materialAcceptAssetsService.get(detailVo.getSourceAssetsId());
                if(ObjectUtil.isNotEmpty(acceptAssetsVo)){
                    MaterialAcceptAssetsDto acceptAssetsDto = dozerMapper.map(acceptAssetsVo,MaterialAcceptAssetsDto.class);
                    BigDecimal oldSignNumber= acceptAssetsDto.getSignNumber();
                    oldSignNumber = ObjectUtil.isEmpty(oldSignNumber) ? BigDecimal.ZERO : oldSignNumber;
                    BigDecimal newSignNumber = oldSignNumber.add(signNumber);
                    acceptAssetsDto.setSignNumber(newSignNumber);
                    materialAcceptAssetsService.update(acceptAssetsDto);
                }

//                MaterialAcceptDetailVo acceptDetailVo = materialAcceptDetailService.get(materialSignDetailVo.getAcc);

                materialAcceptResultService.save(resultDto);

                if (!StringUtils.isBlank(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {
                    //此次领用修改数量
                    AssetsVo assetsVo = assetsService.get(detailVo.getAssetsId());
                    AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                    BigDecimal oldAcceptNumber = assetsDto.getAcceptNumber();
                    BigDecimal newAcceptNumber = oldAcceptNumber.add(signNumber);
                    assetsDto.setAcceptNumber(newAcceptNumber);
                    assetsService.update(assetsDto);
                    log.debug("资产:{}，保存后可用数量为:{}，保存后领用数量:{}", assetsDto.getId(), newAcceptNumber);
                    //修改sku表数量
//                    AssetsSkuVo assetsSkuVo = (AssetsSkuVo) assetsSkuService.getByFkIdVo(materialSignDetailVo.getAssetsSkuId());
//                    AssetsSkuDto assetsSkuDto = dozerMapper.map(assetsSkuVo, AssetsSkuDto.class);
//                    BigDecimal oldSkuAcceptNumber = assetsSkuVo.getAcceptNumber();
//                    BigDecimal newSkuAcceptNumber = oldSkuAcceptNumber.add(signNumber);
//                    assetsSkuDto.setAcceptNumber(newSkuAcceptNumber);
//                    assetsSkuService.update(assetsSkuDto);
//                    log.debug("sku资产:{}，保存后领用数量为:{}", assetsDto.getId(), newSkuAcceptNumber);
                    //修改库存详情信息
                    AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                    BigDecimal oldDetailAcceptNumber = assetsDetailDto.getAcceptNumber();
                    BigDecimal newDetailAcceptNumber = oldDetailAcceptNumber.add(signNumber);
                    assetsDetailDto.setAcceptNumber(newDetailAcceptNumber);
                    assetsDetailDto.setUseUserId(acceptVo.getAssetUserId());
                    assetsDetailDto.setUseUserName(acceptVo.getAssetUserName());
                    assetsDetailDto.setStatus("已领用");
                    assetsDetailService.update(assetsDetailDto);
                    log.debug("资产详情:{}，保存后领用数量为:{}", assetsDto.getId(), newDetailAcceptNumber);
                    daybookService.log(signVo.getId(),signVo.getCamundaProcinsId(),assetsDetailDto, DayBookType.SIGN);

                    //修改sku表数量
                    AssetsSkuVo assetsSkuVo = assetsSkuService.get(assetsDetailVo.getAssetsSkuId());
                    if(ObjectUtil.isNotEmpty(assetsSkuVo)){
                        AssetsSkuDto assetsSkuDto = dozerMapper.map(assetsSkuVo, AssetsSkuDto.class);
                        BigDecimal oldSkuAcceptNumber = assetsSkuDto.getAcceptNumber();
                        BigDecimal newSkuAcceptNumber = oldSkuAcceptNumber.add(signNumber);
                        assetsSkuDto.setAcceptNumber(newSkuAcceptNumber);
                        assetsSkuService.update(assetsSkuDto);
                        log.debug("sku资产:{}，保存后领用数量为:{}", assetsDto.getId(), newSkuAcceptNumber);
                    }
                }
                if (!StringUtils.isBlank(itemType) && (itemType.equals(ItemTypeEnum.ARTICLES.code) || itemType.equals(ItemTypeEnum.MATERIAL.code))) {//对耗材台账的数量进行修改
                    SuppliesVo suppliesVo = suppliesService.get(detailVo.getAssetsId());
                    SuppliesDto suppliesDto = dozerMapper.map(suppliesVo, SuppliesDto.class);
                    BigDecimal oldSupAcceptNumber = suppliesDto.getAcceptNumber();
                    BigDecimal newSupAcceptNumber = oldSupAcceptNumber.subtract(signNumber);
                    suppliesDto.setAcceptNumber(newSupAcceptNumber);
                    suppliesService.update(suppliesDto);
                    log.debug("资产:{}，保存后可用数量为:{}，保存后领用数量:{}", suppliesDto.getId(), suppliesDto);
                    //修改耗材sku表数量
//                    SuppliesSkuVo suppliesSkuVo = (SuppliesSkuVo) suppliesSkuService.getByFkIdVo(materialSignDetailVo.getAssetsId());
//                    SuppliesSkuDto suppliesSkuDto = dozerMapper.map(suppliesSkuVo, SuppliesSkuDto.class);
//                    BigDecimal oldSkuSuppliesacceptNumber = suppliesSkuVo.getAcceptNumber();
//                    BigDecimal newSkuSuppliesacceptNumber = oldSkuSuppliesacceptNumber.add(signNumber);
//                    suppliesSkuDto.setAcceptNumber(newSkuSuppliesacceptNumber);
//                    suppliesSkuService.update(suppliesSkuDto);
//                    log.debug("耗材sku资产:{}，保存后领用数量为:{}", suppliesSkuDto.getId(), newSkuSuppliesacceptNumber);
                    //修改耗材详情信息
                    SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get(detailVo.getAssetsDetailId());
                    if(ObjectUtil.isNotEmpty(suppliesDetailVo)) {
                        SuppliesDetailDto suppliesDetailDto = dozerMapper.map(suppliesDetailVo, SuppliesDetailDto.class);
                        BigDecimal oldSupDetailAcceptNumber = suppliesDetailDto.getAcceptNumber();
                        BigDecimal newSupDetailAcceptNumber = oldSupDetailAcceptNumber.add(signNumber);
                        suppliesDetailDto.setAcceptNumber(newSupDetailAcceptNumber);
                        suppliesDetailService.update(suppliesDetailDto);
                        log.debug("耗材详情:{}，保存后领用数量为:{}", suppliesDetailDto.getId(), newSupDetailAcceptNumber);
                        daybookSuppliesService.log(signVo.getId(), signVo.getCamundaProcinsId(), suppliesDetailDto, DayBookType.SIGN);
                    }
                    //修改耗材sku表数量
                    SuppliesSkuVo suppliesSkuVo = suppliesSkuService.get(suppliesDetailVo.getAssetsSkuId());
                    SuppliesSkuDto suppliesSkuDto = dozerMapper.map(suppliesSkuVo, SuppliesSkuDto.class);
                    BigDecimal oldSkuSuppliesacceptNumber = suppliesSkuDto.getAcceptNumber();
                    BigDecimal newSkuSuppliesacceptNumber = oldSkuSuppliesacceptNumber.add(signNumber);
                    suppliesSkuDto.setAcceptNumber(newSkuSuppliesacceptNumber);
                    suppliesSkuService.update(suppliesSkuDto);
                    log.debug("耗材sku资产:{}，保存后领用数量为:{}", suppliesSkuDto.getId(), newSkuSuppliesacceptNumber);
                }
            }else{//拒绝签收

            }

        }
        return result;
    }

    @Override
    public Page getOrders(MaterialSignDto signDto) {
        Integer signType = signDto.getSignType();
        signType = ObjectUtil.isEmpty(signType) ? TaskType.ACCEPT.getCode() : signType;
        //领用
        if(signType == TaskType.ACCEPT.code){
            LambdaQueryWrapper<MaterialAccept> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MaterialAccept::getAuditStatus, WfProcessEnum.DONE.getType());
            queryWrapper.gt(MaterialAccept::getNotSignNumber,BigDecimal.ZERO);

            if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
                queryWrapper.eq(MaterialAccept::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                        .eq(MaterialAccept::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                        .eq(MaterialAccept::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
            }
            queryWrapper.orderByDesc(MaterialAccept::getApplyTime);

            //根据实际业务构建具体的参数做查询
            String  searchCondition= signDto.getSearchCondition();
            //全局模糊查询
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
            Page<MaterialAccept> page = materialAcceptService.page(signDto.getPage(),queryWrapper);
            Page<MaterialAcceptVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialAcceptVo.class);
            return pageVo;
        }
        return null;
    }

    /**
     * 获取待签收领用单编号
     * @param materialSignDto
     * @return
     */
    @Override
    public Page getAcceptList(MaterialSignDto materialSignDto) {
        Integer distributeType = materialSignDto.getDistributeType();

        //领用
        if(distributeType == TaskType.ACCEPT.code){
            LambdaQueryWrapper<MaterialAccept> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(materialSignDto.getApplyUserOrgId()),MaterialAccept::getApplyUserOrgId,materialSignDto.getApplyUserOrgId());
            queryWrapper.eq(ObjectUtil.isNotEmpty(materialSignDto.getApplyUserDeptId()),MaterialAccept::getApplyUserDeptId,materialSignDto.getApplyUserDeptId());
            queryWrapper.eq(MaterialAccept::getAuditStatus, WfProcessEnum.DONE.getType());
            queryWrapper.gt(MaterialAccept::getNotSignNumber,BigDecimal.ZERO);
            queryWrapper.orderByDesc(MaterialAccept::getApplyTime);
            Page<MaterialAccept> page = materialAcceptService.page(materialSignDto.getPage(),queryWrapper);
            Page<MaterialAcceptVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialAcceptVo.class);
            return pageVo;
        }
        return null;
    }

    @Override
    public List<AssetsDetailVo> getSignDetailList(MaterialSignDto materialSignDto) {
        Integer distributeType = materialSignDto.getDistributeType();

        List<AssetsDetailVo> materialSignAssetsVoList = new ArrayList<>();
        if(distributeType == TaskType.ACCEPT.code){
            LambdaQueryWrapper<MaterialDistributeDetail> queryWrapper = new LambdaQueryWrapper<>();
            //查询派发详情信息
            queryWrapper.eq(MaterialDistributeDetail::getSourceId,materialSignDto.getSourceId());
            queryWrapper.gt(MaterialDistributeDetail::getNotSignNumber,BigDecimal.ZERO);
            List<MaterialDistributeDetail> materialDistributeDetails = materialDistributeDetailService.list(queryWrapper);
            List<MaterialDistributeDetailVo> materialDistributeDetailVoList = DozerUtils.mapList(dozerMapper, materialDistributeDetails,MaterialDistributeDetailVo.class);
            materialDistributeDetailVoList.forEach(materialDistributeDetailList ->{
                //获取物资类型
                String itemType = materialDistributeDetailList.getItemType();
                AssetsDetailVo assetsDetailVo = new AssetsDetailVo();
                //获取存台账对应的信息
                if (!StringUtils.isBlank(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))){
                    assetsDetailVo = assetsDetailService.get( materialDistributeDetailList.getAssetsDetailId());
                    materialSignAssetsVoList.add(assetsDetailVo);
                }else{
                   SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get( materialDistributeDetailList.getAssetsDetailId());
                    assetsDetailVo = dozerMapper.map(suppliesDetailVo,AssetsDetailVo.class);
                    materialSignAssetsVoList.add(assetsDetailVo);
                }

            });
        };
        return materialSignAssetsVoList;
      }

    @Override
    public List<MaterialSignDetailVo> getDetailList(MaterialSignDto materialSignDto) {
        Integer distributeType = materialSignDto.getDistributeType();
        List<MaterialSignDetailVo> detailVoList = new ArrayList<>();
        Long sourceId = materialSignDto.getSourceId();
        if(distributeType == TaskType.ACCEPT.code){
//            MaterialDistributeVo distributeVo = materialDistributeService.get(sourceId);
//            List<MaterialDistributeVo> distributeVoList = materialDistributeService.getBySourceId(sourceId);
//            List<MaterialDistributeDetailVo> distributeDetailVoList = materialDistributeDetailService.getByDistributeId(distributeVo.getId());

            LambdaQueryWrapper<MaterialDistributeDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MaterialDistributeDetail::getSourceId,materialSignDto.getSourceId()).gt(MaterialDistributeDetail::getNotSignNumber,BigDecimal.ZERO);
            List<MaterialDistributeDetail> distributeDetailList = materialDistributeDetailService.list(queryWrapper);
            distributeDetailList.forEach(distributeDetailVo -> {
                MaterialSignDetailVo detailVo = dozerMapper.map(distributeDetailVo,MaterialSignDetailVo.class);
                detailVo.setId(null);
                detailVo.setCreateTime(null);
                detailVo.setSignNumber(detailVo.getDistributeNumber());
                detailVo.setBusinessId(distributeDetailVo.getDistributeId());
                detailVo.setBusinessAssetsId(distributeDetailVo.getDistributeAssetsId());
                detailVo.setBusinessDetailId(distributeDetailVo.getId());

                detailVo.setSourceId(distributeDetailVo.getSourceId());
                detailVo.setSourceCode(distributeDetailVo.getSourceCode());
                detailVo.setSourceAssetsId(distributeDetailVo.getSourceAssetsId());
                detailVoList.add(detailVo);
            });

        }
        return detailVoList;
    }
}
