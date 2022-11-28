package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaterialChangeMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.biz.asset.vo.MaterialChangeDetailVo;
import com.netwisd.biz.asset.vo.MaterialChangeVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.common.db.util.EntityListConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
 * @Description 资产信息变更 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:11:54
 */
@Service
@Slf4j
public class MaterialChangeServiceImpl extends BatchServiceImpl<MaterialChangeMapper, MaterialChange> implements MaterialChangeService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialChangeMapper materialChangeMapper;

    @Autowired
    private MaterialChangeDetailService materialChangeDetailService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private DaybookService daybookService;

    @Autowired
    private MaterialAcceptResultService materialAcceptResultService;

    /**
    * 单表简单查询操作
    * @param materialChangeDto
    * @return
    */
    @Override
    public Page list(MaterialChangeDto materialChangeDto) {
        LambdaQueryWrapper<MaterialChange> queryWrapper = new LambdaQueryWrapper<>();

        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(MaterialChange::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(MaterialChange::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(MaterialChange::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        queryWrapper.orderByDesc(MaterialChange::getApplyTime);
        //指定的查询字段
        String  searchCondition= materialChangeDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(MaterialChange::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(MaterialChange::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialChange::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialChange::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialChange::getExplanation,searchCondition));
            });
        }
        //根据实际业务构建具体的参数做查询
        Page<MaterialChange> page = materialChangeMapper.selectPage(materialChangeDto.getPage(),queryWrapper);
        Page<MaterialChangeVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialChangeVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialChangeDto
    * @return
    */
    @Override
    public Page lists(MaterialChangeDto materialChangeDto) {
        Page<MaterialChangeVo> pageVo = materialChangeMapper.getPageList(materialChangeDto.getPage(),materialChangeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialChangeVo get(Long id) {
        MaterialChange materialChange = super.getById(id);
        MaterialChangeVo materialChangeVo = null;
        if(materialChange !=null){
            materialChangeVo = dozerMapper.map(materialChange,MaterialChangeVo.class);
            //根据id获取materialChangeDetailVoList
            List<MaterialChangeDetailVo> materialChangeDetailVoList = materialChangeDetailService.getByChangeId(id);
            //设置materialChangeVo，以便构建其对应的子表数据
            materialChangeVo.setDetailList(materialChangeDetailVoList);
        }
        return materialChangeVo;
    }

    /**
    * 保存实体
    * @param materialChangeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialChangeDto materialChangeDto) {
        MaterialChange materialChange = dozerMapper.map(materialChangeDto,MaterialChange.class);
        return super.save(materialChange) && saveSubList(materialChangeDto);
    }

    /**
     * 保存集合
     * @param materialChangeDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<MaterialChangeDto> materialChangeDtos){
        List<MaterialChange> MaterialChanges = DozerUtils.mapList(dozerMapper, materialChangeDtos, MaterialChange.class);
        return super.saveBatch(MaterialChanges);
//        for (MaterialChangeDto materialChangeDto : materialChangeDtos){
//            saveSubList(materialChangeDto);
//        }
    }

    /**
     * 保存子表集合
     * @param materialChangeDto
     * @return
     */
    @Transactional
    Boolean saveSubList(MaterialChangeDto materialChangeDto){
        //获取其子表集合
        List<MaterialChangeDetailDto> materialChangeDetailDtoList = materialChangeDto.getDetailList();
        if(materialChangeDetailDtoList != null && !materialChangeDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(materialChangeDto,materialChangeDetailDtoList);
            for (MaterialChangeDetailDto detailDto : materialChangeDetailDtoList) {
                detailDto.setChangeId(materialChangeDto.getId());
            }
            //调用保存子表的集合方法
            return materialChangeDetailService.saveList(materialChangeDetailDtoList);
        }
        return false;
    }

    /**
     * 修改实体
     * @param materialChangeDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MaterialChangeDto materialChangeDto) {
        materialChangeDto.setUpdateTime(LocalDateTime.now());
        MaterialChange materialChange = dozerMapper.map(materialChangeDto,MaterialChange.class);
        return super.updateById(materialChange);
//        updateSub(materialChangeDto);
    }

    /**
    * 修改子类实体
    * @param materialChangeDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialChangeDto materialChangeDto) {
        List<MaterialChangeDetailDto> materialChangeDetailDtoList = materialChangeDto.getDetailList();
        if(materialChangeDetailDtoList != null && !materialChangeDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialChangeDetail> materialChangeDetailListQueryWrapper = new LambdaQueryWrapper<>();
            materialChangeDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialChangeDto.getId()),MaterialChangeDetail::getChangeId,materialChangeDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            EntityListConvert.convert(materialChangeDto,materialChangeDetailDtoList);
            List<MaterialChangeDetail> materialChangeDetails = DozerUtils.mapList(dozerMapper, materialChangeDetailDtoList, MaterialChangeDetail.class);
            //调用更新方法
            materialChangeDetailService.saveOrUpdateOrDeleteBatch(materialChangeDetailListQueryWrapper,materialChangeDetails,materialChangeDetails.size());
            //如果子可能还有子，那么遍历子，然后调用其子方法的updateSub
            if(materialChangeDetailDtoList != null && !materialChangeDetailDtoList.isEmpty()){
                for(MaterialChangeDetailDto materialChangeDetailDto : materialChangeDetailDtoList){
                    materialChangeDetailService.updateSub(materialChangeDetailDto);
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
    public Boolean delete(Long id) {
        return super.removeById(id) && materialChangeDetailService.deleteByChangeId(id);
    }

    @Override
    @Transactional
    public Boolean deleteByProc(String procInstId) {
        if(ObjectUtil.isEmpty(procInstId)){
            return false;
        }
        Boolean result =false;
        LambdaQueryWrapper<MaterialChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialChange::getCamundaProcinsId,procInstId);
        List<MaterialChange> list = this.list(queryWrapper);
        for (MaterialChange change : list) {
            result = this.delete(change.getId()) && materialChangeDetailService.deleteByChangeId(change.getId());
        }
        return result;
    }

    @Override
    public MaterialChangeVo getByProcId(String procInstId) {
        LambdaQueryWrapper<MaterialChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialChange::getCamundaProcinsId,procInstId);
        List<MaterialChange> list = this.list(queryWrapper);
        MaterialChangeVo changeVo = new MaterialChangeVo();
        if (CollectionUtils.isNotEmpty(list) && list.size()>0){
            MaterialChange change = list.get(0);
            changeVo = dozerMapper.map(change,MaterialChangeVo.class);
            List<MaterialChangeDetailVo> detailVoList = materialChangeDetailService.getByChangeId(changeVo.getId());
            changeVo.setDetailList(detailVoList);
        }
        return changeVo;
    }

    @Override
    @Transactional
    public MaterialChangeVo procSaveOrUpdate(MaterialChangeDto materialChangeDto) {
        if(ObjectUtil.isEmpty(materialChangeDto.getCode())){
            getMaxCode(materialChangeDto);
        }
        MaterialChange materialChange = dozerMapper.map(materialChangeDto,MaterialChange.class);
        materialChangeDetailService.deleteByChangeId(materialChangeDto.getId());

        List<MaterialChangeDetailDto> changeDetailDtoList = materialChangeDto.getDetailList();
        if(CollectionUtils.isNotEmpty(changeDetailDtoList)) {
            changeDetailDtoList.forEach(changeDetailDto -> {
                changeDetailDto.setChangeId(materialChangeDto.getId());
                materialChangeDetailService.save(changeDetailDto);
            });
        }
        this.saveOrUpdate(materialChange);
        return this.getByProcId(materialChangeDto.getCamundaProcinsId());
    }

    /**
     * 获取申请编号
     * @return
     */
    private void getMaxCode(MaterialChangeDto materialChangeDto){
        //获取申请编号
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(materialChangeDto,HashMap.class);
        String code = dictClient.getRuleValue("material_change","code",entityMap);
        log.debug("规则值:{}",code);
        if(StringUtils.isNotBlank(code)){
            materialChangeDto.setCode(code);
        }
    }

    @Override
    @Transactional
    public Boolean procSaveBefore(String procInstId) {
        Boolean result = false;
        MaterialChangeVo changeVo = this.getByProcId(procInstId);
        List<MaterialChangeDetailVo> detailVoList = changeVo.getDetailList();
        if(CollectionUtils.isNotEmpty(detailVoList) && detailVoList.size()>0){
            for (MaterialChangeDetailVo detailVo : detailVoList) {
                AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                if(ObjectUtil.isNotEmpty(assetsDetailVo)) {
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
//                BigDecimal oldUsableNumber = assetsDetailVo.getUsableNumber();
                    assetsDetailDto.setStatus(detailVo.getStatus());
                    result = assetsDetailService.update(assetsDetailDto);
                }
            }
        }
        return result;
    }

    /**
     * 保存后处理
     *  减少领用数量
     * @param procInstId
     * @return
     */
    @Override
    @Transactional
    public Boolean procSaveAfter(String procInstId) {
        Boolean result = false;
        MaterialChangeVo changeVo = this.getByProcId(procInstId);
        List<MaterialChangeDetailVo> detailVoList = changeVo.getDetailList();
        if(CollectionUtils.isNotEmpty(detailVoList) && detailVoList.size()>0){
            for (MaterialChangeDetailVo detailVo : detailVoList) {
                BigDecimal changeNumber = detailVo.getChangeNumber();

                AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                if(ObjectUtil.isNotEmpty(assetsDetailVo)) {
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
//                BigDecimal oldUsableNumber = assetsDetailVo.getUsableNumber();
//                oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
//                BigDecimal newUsableNumber = oldUsableNumber.subtract(changeNumber);
                    assetsDetailDto.setStatus("领用人变更中");
//                assetsDetailDto.setUsableNumber(newUsableNumber);
                    result = assetsDetailService.update(assetsDetailDto);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean procAuditSuccess(String procInstId) {
        Boolean result = false;
        MaterialChangeVo changeVo = this.getByProcId(procInstId);
        List<MaterialChangeDetailVo> detailVoList = changeVo.getDetailList();
        if(CollectionUtils.isNotEmpty(detailVoList) && detailVoList.size()>0) {
            for (MaterialChangeDetailVo detailVo : detailVoList) {
                Long assetUserId = detailVo.getAssetUserId();
                String assetUserName = detailVo.getAssetUserName();

                AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                if(ObjectUtil.isNotEmpty(assetsDetailVo)) {
                    MaterialChangeDetailDto detailDto = dozerMapper.map(assetsDetailVo, MaterialChangeDetailDto.class);
                    detailDto.setId(null);
                    detailDto.setType(0);//历史数据--未修改前资产数据
                    result = materialChangeDetailService.save(detailDto);


                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                    assetsDetailDto.setAssetUserId(assetUserId);
                    assetsDetailDto.setAssetUserName(assetUserName);
                    result = assetsDetailService.update(assetsDetailDto);
                    daybookService.log(changeVo.getId(), changeVo.getCamundaProcinsId(), assetsDetailDto, DayBookType.CHANGE);
                }

                List<MaterialAcceptResult> acceptResultList = materialAcceptResultService.list(Wrappers.<MaterialAcceptResult>lambdaQuery()
                        .eq(MaterialAcceptResult::getAssetsDetailId,detailVo.getAssetsDetailId())
                        .gt(MaterialAcceptResult::getNotRefundNumber,BigDecimal.ZERO));
                if(CollectionUtils.isNotEmpty(acceptResultList)){
                    MaterialAcceptResult acceptResult = acceptResultList.get(0);
                    MaterialAcceptResultDto acceptResultDto = dozerMapper.map(acceptResult,MaterialAcceptResultDto.class);

//                    acceptResult.setAssetOrgId();
//                    acceptResult.setAssetOrgName();
//                    acceptResult.setAssetDeptId();
//                    acceptResult.setAssetDeptName();
                    acceptResultDto.setAssetUserId(assetUserId);
                    acceptResultDto.setAssetUserName(assetUserName);
                    result = materialAcceptResultService.update(acceptResultDto);
                }
            }
        }
        return result;
    }

    @Override
    public List<MaterialChangeDetailVo> getDetailList(MaterialChangeDetailDto materialChangeDetailDto) {
        List<MaterialChangeDetailVo> changeDetailVoList = new ArrayList<>();

        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())) {
            queryWrapper.eq(AssetsDetail::getAssetOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(AssetsDetail::getAssetDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(AssetsDetail::getAssetUserId, AppUserUtil.getLoginAppUser().getId());

            queryWrapper.orderByDesc(AssetsDetail::getApplyTime);

            String searchCondition = materialChangeDetailDto.getSearchCondition();
            if (ObjectUtil.isNotEmpty(searchCondition)) {
                queryWrapper.and(q -> {
                    q.like(AssetsDetail::getItemName, searchCondition)
                            .or(wrapper -> wrapper.like(AssetsDetail::getItemCode, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getAssetOrgName, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getDescshort, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getAssetDeptName, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getClassifyCode, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getClassifyName, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getUnitName, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getMaterialQuality, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getStandard, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getSpecs, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getManufacturer, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getProductionDate, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getBatchNumber, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getFactoryCode, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getServiceLife, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getWarehouseName, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getShelfName, searchCondition))
                            .or(wrapper -> wrapper.like(AssetsDetail::getStatus, searchCondition));
                });
            }
            List<AssetsDetail> assetsDetailList = assetsDetailService.list(queryWrapper);
            assetsDetailList.forEach(assetsDetail -> {
                MaterialChangeDetailVo changeDetailVo = dozerMapper.map(assetsDetail, MaterialChangeDetailVo.class);
                changeDetailVo.setId(null);
                changeDetailVo.setType(1);//表单数据
                changeDetailVoList.add(changeDetailVo);
            });
        }
//        AssetsDetailDto assetsDetailDto = new AssetsDetailDto();
//        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
//            assetsDetailDto.setAssetOrgId(AppUserUtil.getLoginAppUser().getParentOrgId());
//            assetsDetailDto.setAssetDeptId(AppUserUtil.getLoginAppUser().getParentDeptId());
//            assetsDetailDto.setAssetUserId(AppUserUtil.getLoginAppUser().getId());
//        }
//        String searchCondition = materialChangeDetailDto.getSearchCondition();
//        if (ObjectUtil.isNotEmpty(searchCondition)){
//            assetsDetailDto.setSearchCondition(searchCondition);
//        }
//        assetsDetailService.list(assetsDetailDto);
        return changeDetailVoList;
    }
}
