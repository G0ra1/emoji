package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.constants.ItemTypeEnum;
import com.netwisd.biz.asset.constants.TaskType;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaterialAcceptMapper;
import com.netwisd.biz.asset.mapper.MaterialDistributeMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
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
 * @Description 资产派发 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:46:22
 */
@Service
@Slf4j
public class MaterialDistributeServiceImpl extends BatchServiceImpl<MaterialDistributeMapper, MaterialDistribute> implements MaterialDistributeService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialDistributeMapper materialDistributeMapper;

    @Autowired
    private MaterialDistributeDetailService materialDistributeDetailService;

    @Autowired
    private MaterialDistributeAssetsService materialDistributeAssetsService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsSkuService assetsSkuService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private SuppliesService suppliesService;

    @Autowired
    private SuppliesSkuService suppliesSkuService;

    @Autowired
    private SuppliesDetailService suppliesDetailService;

    @Autowired
    private MaterialAcceptMapper materialAcceptMapper;

    @Autowired
    private MaterialAcceptAssetsService materialAcceptAssetsService;

    @Autowired
    private DaybookService daybookService;

    @Autowired
    private DaybookSuppliesService daybookSuppliesService;

    /**
    * 单表简单查询操作
    * @param materialDistributeDto
    * @return
    */
    @Override
    public Page list(MaterialDistributeDto materialDistributeDto) {
        LambdaQueryWrapper<MaterialDistribute> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(MaterialDistribute::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(MaterialDistribute::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(MaterialDistribute::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        queryWrapper.orderByDesc(MaterialDistribute::getApplyTime);

        //根据实际业务构建具体的参数做查询
        String  searchCondition= materialDistributeDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(MaterialDistribute::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(MaterialDistribute::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialDistribute::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialDistribute::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialDistribute::getApplyTime,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialDistribute::getSumTotalAmount,searchCondition))
                        .or(wrapper ->wrapper.like(MaterialDistribute::getReason,searchCondition));
            });
        }
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.like(MaterialDistribute::getCode,searchCondition)
                    .or()
                    .like(MaterialDistribute::getApplyUserName,searchCondition)
                    .or()
                    .like(MaterialDistribute::getApplyUserDeptName,searchCondition)
                    .or()
                    .like(MaterialDistribute::getApplyUserOrgName,searchCondition)
                    .or()
                    .like(MaterialDistribute::getApplyTime,searchCondition)
                    .or()
                    .like(MaterialDistribute::getSumTotalAmount,searchCondition);
        }

        Page<MaterialDistribute> page = materialDistributeMapper.selectPage(materialDistributeDto.getPage(),queryWrapper);
        Page<MaterialDistributeVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDistributeVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialDistributeDto
    * @return
    */
    @Override
    public Page lists(MaterialDistributeDto materialDistributeDto) {
        Page<MaterialDistributeVo> pageVo = materialDistributeMapper.getPageList(materialDistributeDto.getPage(),materialDistributeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDistributeVo get(Long id) {
        MaterialDistribute materialDistribute = super.getById(id);
        MaterialDistributeVo materialDistributeVo = null;
        if(materialDistribute !=null){
            materialDistributeVo = dozerMapper.map(materialDistribute,MaterialDistributeVo.class);

            //根据id获取materialDistributeAssetsVoList
            List<MaterialDistributeAssetsVo> materialDistributeAssetsVoList = materialDistributeAssetsService.getByDistributeId(id);
            //设置materialDistributeVo，以便构建其对应的子表数据
            materialDistributeVo.setAssetsList(materialDistributeAssetsVoList);

            //根据id获取materialDistributeDetailVoList
            List<MaterialDistributeDetailVo> materialDistributeDetailVoList = materialDistributeDetailService.getByDistributeId(id);
            //设置materialDistributeVo，以便构建其对应的子表数据
            materialDistributeVo.setDetailList(materialDistributeDetailVoList);
        }
        return materialDistributeVo;
    }

    /**
    * 保存实体
    * @param materialDistributeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialDistributeDto materialDistributeDto) {
        if(ObjectUtil.isEmpty(materialDistributeDto.getCode())){
            getMaxCode(materialDistributeDto);
        }
        MaterialDistribute materialDistribute = dozerMapper.map(materialDistributeDto,MaterialDistribute.class);
        return super.save(materialDistribute) && saveSubList(materialDistributeDto);
    }

    private void getMaxCode(MaterialDistributeDto distributeDto){
        //获取申请编号
        if(StringUtils.isBlank(distributeDto.getCode())){
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap entityMap = objectMapper.convertValue(distributeDto,HashMap.class);
            String code = dictClient.getRuleValue("material_distribute","code",entityMap);
            log.debug("规则值:{}",code);
            if(StringUtils.isNotBlank(code)){
                distributeDto.setCode(code);
            }
        }
    }

    /**
     * 保存集合
     * @param materialDistributeDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<MaterialDistributeDto> materialDistributeDtos){
        Boolean result = true;
        List<MaterialDistribute> MaterialDistributes = DozerUtils.mapList(dozerMapper, materialDistributeDtos, MaterialDistribute.class);
        super.saveBatch(MaterialDistributes);
        for (MaterialDistributeDto materialDistributeDto : materialDistributeDtos){
            result = saveSubList(materialDistributeDto);
        }
        return result;
    }

    /**
     * 保存子表集合
     * @param materialDistributeDto
     * @return
     */
    @Transactional
    Boolean saveSubList(MaterialDistributeDto materialDistributeDto){
        Boolean result = true;

        //获取其子表集合
        List<MaterialDistributeAssetsDto> materialDistributeAssetsDtoList = materialDistributeDto.getAssetsList();
        if(materialDistributeAssetsDtoList != null && !materialDistributeAssetsDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            materialDistributeAssetsDtoList.forEach(assetsDto -> {
                assetsDto.setDistributeId(materialDistributeDto.getId());
                //assetsDto.setDistributeCode(materialDistributeDto.getCode());

                //获取其子表集合
                List<MaterialDistributeDetailDto> detailDtoList = assetsDto.getDetailList();
                if(detailDtoList != null && !detailDtoList.isEmpty()){
                    //根据主实体DTO映射其子表的关联键为其赋值
                    detailDtoList.forEach(detailDto -> {
                        detailDto.setDistributeId(materialDistributeDto.getId());
                        detailDto.setDistributeCode(materialDistributeDto.getCode());
                        detailDto.setDistributeAssetsId(assetsDto.getId());
                    });
                    //调用保存子表的集合方法
                    materialDistributeDetailService.saveList(detailDtoList);
                }
            });
            //调用保存子表的集合方法
            materialDistributeAssetsService.saveList(materialDistributeAssetsDtoList);
        }

        //获取其子表集合
        List<MaterialDistributeDetailDto> materialDistributeDetailDtoList = materialDistributeDto.getDetailList();
        if(materialDistributeDetailDtoList != null && !materialDistributeDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            materialDistributeDetailDtoList.forEach(detailDto -> {
                detailDto.setDistributeId(materialDistributeDto.getId());
                detailDto.setDistributeCode(materialDistributeDto.getCode());
            });
            //调用保存子表的集合方法
            materialDistributeDetailService.saveList(materialDistributeDetailDtoList);
        }
        return result;
    }

    /**
     * 修改实体
     * @param materialDistributeDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MaterialDistributeDto materialDistributeDto) {
        materialDistributeDto.setUpdateTime(LocalDateTime.now());
        MaterialDistribute materialDistribute = dozerMapper.map(materialDistributeDto,MaterialDistribute.class);
        return super.updateById(materialDistribute) && updateSub(materialDistributeDto);
    }

    /**
    * 修改子类实体
    * @param materialDistributeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean updateSub(MaterialDistributeDto materialDistributeDto) {
        Boolean result = true;
        List<MaterialDistributeDetailDto> materialDistributeDetailDtoList = materialDistributeDto.getDetailList();

        List<MaterialDistributeAssetsDto> materialDistributeAssetsDtoList = materialDistributeDto.getAssetsList();
        if(materialDistributeAssetsDtoList != null && !materialDistributeAssetsDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialDistributeAssets> materialDistributeAssetsListQueryWrapper = new LambdaQueryWrapper<>();
            materialDistributeAssetsListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialDistributeDto.getId()),MaterialDistributeAssets::getDistributeId,materialDistributeDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            materialDistributeAssetsDtoList.forEach(assetsDto -> {
                assetsDto.setDistributeId(materialDistributeDto.getId());
            });
            List<MaterialDistributeAssets> materialDistributeAssetss = DozerUtils.mapList(dozerMapper, materialDistributeAssetsDtoList, MaterialDistributeAssets.class);
            //调用更新方法
            materialDistributeAssetsService.saveOrUpdateOrDeleteBatch(materialDistributeAssetsListQueryWrapper,materialDistributeAssetss,materialDistributeAssetss.size());
        }

        if(materialDistributeDetailDtoList != null && !materialDistributeDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaterialDistributeDetail> materialDistributeDetailListQueryWrapper = new LambdaQueryWrapper<>();
            materialDistributeDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialDistributeDto.getId()),MaterialDistributeDetail::getDistributeId,materialDistributeDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            materialDistributeDetailDtoList.forEach(detailDto -> {
                detailDto.setDistributeId(materialDistributeDto.getId());
                detailDto.setDistributeCode(materialDistributeDto.getCode());
            });
            List<MaterialDistributeDetail> materialDistributeDetails = DozerUtils.mapList(dozerMapper, materialDistributeDetailDtoList, MaterialDistributeDetail.class);
            //调用更新方法
            materialDistributeDetailService.saveOrUpdateOrDeleteBatch(materialDistributeDetailListQueryWrapper,materialDistributeDetails,materialDistributeDetails.size());

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
        return super.removeById(id) && materialDistributeDetailService.deleteByDistributeId(id) && materialDistributeAssetsService.deleteByDistributeId(id);
    }

    @Override
    public List<MaterialDistributeVo> getBySourceId(Long sourceId) {
        List<MaterialDistribute> distributeList = this.list(Wrappers.<MaterialDistribute>lambdaQuery().eq(MaterialDistribute::getSourceId,sourceId));
        return DozerUtils.mapList(dozerMapper,distributeList,MaterialDistributeVo.class);
    }

    @Override
    public MaterialDistributeVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaterialDistribute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(procInstId),MaterialDistribute::getCamundaProcinsId,procInstId);

        List<MaterialDistribute> distributes = this.list(queryWrapper);
        MaterialDistributeVo distributeVo = new MaterialDistributeVo();
        if(CollectionUtils.isNotEmpty(distributes)){
            distributeVo = dozerMapper.map(distributes.get(0),MaterialDistributeVo.class);

            //获取派发明细
            List<MaterialDistributeAssetsVo> assetsVoList = materialDistributeAssetsService.getByDistributeId(distributeVo.getId());
            if(CollectionUtils.isNotEmpty(assetsVoList)){
                assetsVoList.forEach(assetsVo ->{
                    //获取派发详情
                    List<MaterialDistributeDetailVo> detailVoList = materialDistributeDetailService.getByDistributeAssetsId(assetsVo.getId());
                    if(CollectionUtils.isNotEmpty(detailVoList)){
                        assetsVo.setDetailList(detailVoList);
                    }
                });
                distributeVo.setAssetsList(assetsVoList);
            }
        }
        return distributeVo;
    }

    @Override
    public Boolean deleteByProc(String procInstId) {
        LambdaQueryWrapper<MaterialDistribute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(procInstId),MaterialDistribute::getCamundaProcinsId,procInstId);
        return super.remove(queryWrapper);
    }



    @Override
    @Transactional
    public Boolean procSaveOrUpdate(MaterialDistributeDto distributeDto) {
        MaterialDistributeVo materialDistributeVo = this.get(distributeDto.getId());
        if(ObjectUtil.isEmpty(distributeDto.getDistributeType())){
            distributeDto.setDistributeType(TaskType.ACCEPT.getCode());
        }

        //判断是否未编辑、是否是起草
        //若不是起草(流程中保存)，手动调用保存前方法
        if (ObjectUtils.isNotEmpty(materialDistributeVo)
                && ObjectUtils.isNotEmpty(materialDistributeVo.getAuditStatus())
                && materialDistributeVo.getAuditStatus() != WfProcessEnum.DRAFT.getType()){
            this.procSaveBefore(materialDistributeVo.getCamundaProcinsId());
        }

        Boolean result = true, assetsResult = true,detailResult = true;
        if(ObjectUtil.isEmpty(distributeDto.getCode())){
            getMaxCode(distributeDto);
        }

        assetsResult = materialDistributeAssetsService.deleteByDistributeId(distributeDto.getId());
        detailResult = materialDistributeDetailService.deleteByDistributeId(distributeDto.getId());

        List<MaterialDistributeAssetsDto> assetsDtoList = distributeDto.getAssetsList();
//        List<MaterialDistributeDetailDto> detailDtoList = distributeDto.getDetailList();
        BigDecimal sumTotalNumber = BigDecimal.ZERO;
        if(CollectionUtils.isNotEmpty(assetsDtoList)){
            for (MaterialDistributeAssetsDto assetsDto: assetsDtoList) {

                BigDecimal distributeNumber = assetsDto.getDistributeNumber();
                sumTotalNumber = sumTotalNumber.add(distributeNumber);
                assetsDto.setDistributeId(distributeDto.getId());
                assetsDto.setDistributeCode(distributeDto.getCode());

                List<MaterialDistributeDetailDto> detailDtoList = assetsDto.getDetailList();
                if(ObjectUtil.isNotEmpty(detailDtoList)) {
                    detailDtoList.forEach(detailDto -> {
//                    detailDto.getAssets
                        detailDto.setDistributeId(distributeDto.getId());
                        detailDto.setDistributeCode(distributeDto.getCode());
                        detailDto.setDistributeAssetsId(assetsDto.getId());
                    });
                    List<MaterialDistributeDetail> detailList = DozerUtils.mapList(dozerMapper, detailDtoList, MaterialDistributeDetail.class);
                    materialDistributeDetailService.saveOrUpdateBatch(detailList);
                }
            }
            List<MaterialDistributeAssets> assetsList = DozerUtils.mapList(dozerMapper,assetsDtoList,MaterialDistributeAssets.class);
            assetsResult = materialDistributeAssetsService.saveOrUpdateBatch(assetsList);
        }
        distributeDto.setSumTotalNumber(sumTotalNumber);
        MaterialDistribute distribute = dozerMapper.map(distributeDto,MaterialDistribute.class);
        result = super.saveOrUpdate(distribute);
        return result && assetsResult && detailResult;
    }

    @Override
    public Page<MaterialDistributeDetailVo> gerDetailByAssetsId(AssetsDetailDto assetsDetailDto) {
        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssetsDetail::getAssetsId,assetsDetailDto.getAssetsId());
        queryWrapper.gt(AssetsDetail::getUsableNumber, BigDecimal.ZERO);
        Page<AssetsDetailVo> page = assetsDetailService.page(assetsDetailDto.getPage(),queryWrapper);
        Page<MaterialDistributeDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDistributeDetailVo.class);
        return pageVo;
    }

    @Override
    public Page getOrders(MaterialDistributeDto distributeDto) {
        Integer distributeType = distributeDto.getDistributeType();

        //领用
        if(distributeType == TaskType.ACCEPT.code){
            LambdaQueryWrapper<MaterialAccept> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MaterialAccept::getAuditStatus, WfProcessEnum.DONE.getType());
            queryWrapper.gt(MaterialAccept::getNotDistributeNumber,BigDecimal.ZERO);

            if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
                queryWrapper.eq(MaterialAccept::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                        .eq(MaterialAccept::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                        .eq(MaterialAccept::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
            }
            queryWrapper.orderByDesc(MaterialAccept::getApplyTime);

            //根据实际业务构建具体的参数做查询
            String  searchCondition= distributeDto.getSearchCondition();
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
            Page<MaterialAccept> page = materialAcceptMapper.selectPage(distributeDto.getPage(),queryWrapper);
            Page<MaterialAcceptVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialAcceptVo.class);
            return pageVo;
        }
        return null;
    }

    @Override
    public List<MaterialDistributeAssetsVo> getAssetsList(MaterialDistributeDto distributeDto) {
        Integer distributeType = distributeDto.getDistributeType();
        //领用
        List<MaterialDistributeAssetsVo> distributeAssetsVoList = new ArrayList<>();
        if(distributeType == TaskType.ACCEPT.code){
            MaterialAccept accept = materialAcceptMapper.selectById(distributeDto.getSourceId());
            List<MaterialAcceptAssetsVo> acceptAssetsVoList = materialAcceptAssetsService.getByAcceptId(distributeDto.getSourceId());
            acceptAssetsVoList.forEach(acceptAssetsVo -> {
                //MaterialDistributeAssetsVo distributeAssetsVo = dozerMapper.map(assetsVo,MaterialDistributeAssetsVo.class);
                //distributeAssetsVo.set
                String itemType = acceptAssetsVo.getItemType();
                AssetsVo assetsVo = assetsService.get(acceptAssetsVo.getAssetsId());

                if(!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.ARTICLES.code) || itemType.equals(ItemTypeEnum.MATERIAL.code))){
                    SuppliesVo suppliesVo = suppliesService.get(acceptAssetsVo.getAssetsId());

                    assetsVo = dozerMapper.map(suppliesVo,AssetsVo.class);
                }

                MaterialDistributeAssetsVo distributeAssetsVo = dozerMapper.map(assetsVo,MaterialDistributeAssetsVo.class);
                distributeAssetsVo.setId(null);
                distributeAssetsVo.setAssetsId(assetsVo.getId());
                distributeAssetsVo.setSourceId(distributeDto.getSourceId());
                distributeAssetsVo.setSourceCode(distributeDto.getSourceCode());
                distributeAssetsVo.setSourceAssetsId(acceptAssetsVo.getId());
                distributeAssetsVo.setAssetsUserId(accept.getApplyUserId());
                distributeAssetsVo.setAssetsUserName(accept.getApplyUserName());
                distributeAssetsVo.setAssetsUserDeptId(accept.getApplyUserDeptId());
                distributeAssetsVo.setAssetsUserDeptName(accept.getApplyUserDeptName());
                distributeAssetsVo.setAssetsUserOrgId(accept.getApplyUserOrgId());
                distributeAssetsVo.setAssetsUserOrgName(accept.getApplyUserOrgName());

                /*distributeAssetsVo.setApplyUserId(acceptVo.getApplyUserId());
                distributeAssetsVo.setApplyUserName(acceptVo.getApplyUserName());
                distributeAssetsVo.setApplyUserDeptId(acceptVo.getApplyUserDeptId());
                distributeAssetsVo.setApplyUserDeptName(acceptVo.getApplyUserDeptName());
                distributeAssetsVo.setApplyUserOrgId(acceptVo.getApplyUserOrgId());
                distributeAssetsVo.setApplyUserOrgName(acceptVo.getApplyUserOrgName());*/
                distributeAssetsVo.setApplyNumber(acceptAssetsVo.getAcceptNumber());
                distributeAssetsVo.setDistributeNumber(acceptAssetsVo.getAcceptNumber());
                //List<AssetsSkuVo> assetsSkuVoList =
                distributeAssetsVoList.add(distributeAssetsVo);
            });
        }
        return distributeAssetsVoList;
    }

    @Override
    public List<MaterialDistributeDetailVo> getDetailByAssets(MaterialDistributeAssetsDto assetsDto) {
        List<MaterialDistributeDetailVo> distributeDetailVoList = new ArrayList<>();
        //派发数量
        BigDecimal distributeNumber = assetsDto.getDistributeNumber();
        if(distributeNumber.compareTo(BigDecimal.ZERO)==1){
            String itemType = assetsDto.getItemType();
            long assetsId = assetsDto.getAssetsId();
            if(ObjectUtil.isNotEmpty(assetsId)) {
//                List<AssetsDetailVo> detailVoList = assetsDetailService.getByAssetsId(assetsId);
                List<AssetsDetail> detailList = assetsDetailService.list(Wrappers.<AssetsDetail>lambdaQuery()
                        .eq(AssetsDetail::getAssetsId,assetsId)
                        .gt(AssetsDetail::getUsableNumber,BigDecimal.ZERO));
                if(!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.ARTICLES.code) || itemType.equals(ItemTypeEnum.MATERIAL.code))){
                    List<SuppliesDetail> suppliesDetailList = suppliesDetailService.list(Wrappers.<SuppliesDetail>lambdaQuery()
                            .eq(SuppliesDetail::getAssetsId,assetsId)
                            .gt(SuppliesDetail::getUsableNumber,BigDecimal.ZERO));
                    detailList = DozerUtils.mapList(dozerMapper,suppliesDetailList,AssetsDetail.class);
                }
                detailList.forEach(detailVo -> {
                    //detailVo.setId(null);
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(detailVo,AssetsDetailDto.class);
                    MaterialDistributeDetailVo distributeDetailVo = dozerMapper.map(assetsDetailDto,MaterialDistributeDetailVo.class);
                    distributeDetailVo.setId(null);
                    distributeDetailVo.setSourceId(assetsDto.getSourceId());
                    distributeDetailVo.setSourceCode(assetsDto.getSourceCode());
                    distributeDetailVo.setSourceAssetsId(assetsDto.getSourceAssetsId());
                    //distributeDetailVo.setSourceDetailId(detailVo.getId());
                    distributeDetailVo.setAssetsDetailId(detailVo.getId());
                    distributeDetailVo.setAssetsUserId(assetsDto.getAssetsUserId());
                    distributeDetailVo.setAssetsUserName(assetsDto.getAssetsUserName());
                    distributeDetailVo.setAssetsUserDeptId(assetsDto.getAssetsUserDeptId());
                    distributeDetailVo.setAssetsUserDeptName(assetsDto.getAssetsUserDeptName());
                    distributeDetailVo.setAssetsUserOrgId(assetsDto.getAssetsUserOrgId());
                    distributeDetailVo.setAssetsUserOrgName(assetsDto.getAssetsUserOrgName());
                    distributeDetailVo.setApplyNumber(assetsDto.getApplyNumber());
                    distributeDetailVo.setDistributeNumber(distributeNumber);
                    distributeDetailVoList.add(distributeDetailVo);
                });
            }
        }
        return distributeDetailVoList;
    }

    @Override
    public List<MaterialDistributeDetailVo> getDetailByAssetsList(List<MaterialDistributeAssetsDto> distributeAssetsDtoList) {

        List<MaterialDistributeDetailVo> distributeDetailVoList = new ArrayList<>();
        distributeAssetsDtoList.forEach(distributeAssetsDto -> {
            //派发数量
            BigDecimal distributeNumber = distributeAssetsDto.getDistributeNumber();
            if(distributeNumber.compareTo(BigDecimal.ZERO)==1){

                long assetsId = distributeAssetsDto.getAssetsId();
                //AssetsVo assetsVo = assetsService.get(assetsId);
                if(ObjectUtil.isNotEmpty(assetsId)){
                    List<AssetsDetailVo> detailVoList = assetsDetailService.getByAssetsId(assetsId);

                    String itemType = distributeAssetsDto.getItemType();
                    if(!ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.ARTICLES.code) || itemType.equals(ItemTypeEnum.MATERIAL.code))){
                        List<SuppliesDetailVo> suppliesDetailVoList = suppliesDetailService.getByAssetsId(distributeAssetsDto.getAssetsId());
                        detailVoList = DozerUtils.mapList(dozerMapper,suppliesDetailVoList,AssetsDetailVo.class);
                    }
                    detailVoList.forEach(detailVo -> {
                        MaterialDistributeDetailVo distributeDetailVo = dozerMapper.map(detailVo,MaterialDistributeDetailVo.class);
                        distributeDetailVo.setId(null);
                        //领用单id、code
                        distributeDetailVo.setSourceId(distributeAssetsDto.getSourceId());
                        distributeDetailVo.setSourceCode(distributeAssetsDto.getSourceCode());
                        //领用明细id
                        distributeDetailVo.setSourceAssetsId(distributeAssetsDto.getSourceAssetsId());
                        //领用详情id
                        //distributeDetailVo.setSourceDetailId();
                        //即将派发的资产明细 的id
                        distributeDetailVo.setAssetsDetailId(detailVo.getId());

                        distributeDetailVo.setApplyUserId(distributeAssetsDto.getAssetsUserId());
                        distributeDetailVo.setApplyUserName(distributeAssetsDto.getAssetsUserName());
                        distributeDetailVo.setApplyUserDeptId(distributeAssetsDto.getAssetsUserDeptId());
                        distributeDetailVo.setApplyUserDeptName(distributeAssetsDto.getAssetsUserDeptName());
                        distributeDetailVo.setApplyUserOrgId(distributeAssetsDto.getAssetsUserOrgId());
                        distributeDetailVo.setApplyUserOrgName(distributeAssetsDto.getAssetsUserOrgName());

                        //物资所属单位
                        distributeDetailVo.setAssetsUserDeptId(detailVo.getAssetDeptId());
                        distributeDetailVo.setAssetsUserDeptName(detailVo.getAssetDeptName());
                        distributeDetailVo.setAssetsUserOrgId(detailVo.getAssetOrgId());
                        distributeDetailVo.setAssetsUserOrgName(detailVo.getAssetOrgName());

                        distributeDetailVo.setApplyNumber(distributeAssetsDto.getApplyNumber());
                        distributeDetailVo.setDistributeNumber(BigDecimal.ZERO);
                        distributeDetailVoList.add(distributeDetailVo);
                    });
                }
            }
        });
        return distributeDetailVoList;
    }

    @Override
    @Transactional
    public Boolean procSaveBefore(String procInstId) {
        boolean result = true;
        MaterialDistributeVo distributeVo = this.getByProc(procInstId);
        Integer distributeType = distributeVo.getDistributeType();


        List<MaterialDistributeAssetsVo> assetsVoList = distributeVo.getAssetsList();
        for (MaterialDistributeAssetsVo assetsVo: assetsVoList) {
            List<MaterialDistributeDetailVo> detailVoList = assetsVo.getDetailList();
            detailVoList.forEach(detailVo ->{
                BigDecimal distributeNumber = detailVo.getDistributeNumber();

                String itemType = detailVo.getItemType();
                if ((itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))){
                    AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo,AssetsDetailDto.class);
                    BigDecimal oldUsableNumber = assetsDetailDto.getUsableNumber();
                    oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
                    BigDecimal newUsableNUmber = oldUsableNumber.add(distributeNumber);
                    assetsDetailDto.setUsableNumber(newUsableNUmber);
                    assetsDetailService.update(assetsDetailDto);
                }else{
                    SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get(detailVo.getAssetsDetailId());
                    SuppliesDetailDto suppliesDetailDto = dozerMapper.map(suppliesDetailVo,SuppliesDetailDto.class);
                    BigDecimal oldUsableNumber = suppliesDetailDto.getUsableNumber();
                    oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
                    BigDecimal newUsableNUmber = oldUsableNumber.add(distributeNumber);
                    suppliesDetailDto.setUsableNumber(newUsableNUmber);
                    suppliesDetailService.update(suppliesDetailDto);
                }
            });
            //领用
            if(distributeType == TaskType.ACCEPT.code){
                BigDecimal distributeNumber = assetsVo.getDistributeNumber();
                MaterialAccept accept = materialAcceptMapper.selectById(distributeVo.getSourceId());
                if(ObjectUtil.isNotEmpty(accept)){
                    BigDecimal oldNotDistributeNumber =  accept.getNotDistributeNumber();
                    oldNotDistributeNumber = ObjectUtils.isEmpty(oldNotDistributeNumber)? BigDecimal.ZERO : oldNotDistributeNumber;
                    BigDecimal newNotDistributeNumber = oldNotDistributeNumber.add(distributeNumber);
                    accept.setNotDistributeNumber(newNotDistributeNumber);
                    result = SqlHelper.retBool(materialAcceptMapper.updateById(accept));
                }

                MaterialAcceptAssetsVo acceptAssetsVo = materialAcceptAssetsService.get(assetsVo.getSourceAssetsId());
                if (ObjectUtil.isNotEmpty(acceptAssetsVo)){
                    MaterialAcceptAssetsDto acceptAssetsDto = dozerMapper.map(acceptAssetsVo,MaterialAcceptAssetsDto.class);
                    BigDecimal oldNotDistributeNumber = acceptAssetsDto.getNotDistributeNumber();
                    oldNotDistributeNumber = ObjectUtil.isEmpty(oldNotDistributeNumber) ? BigDecimal.ZERO : oldNotDistributeNumber;
                    BigDecimal newNotDistributeNumber = oldNotDistributeNumber.add(distributeNumber);
                    acceptAssetsDto.setNotDistributeNumber(newNotDistributeNumber);
                    result = materialAcceptAssetsService.update(acceptAssetsDto);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean procSaveAfter(String procInstId) {
        boolean result = true;
        MaterialDistributeVo distributeVo = this.getByProc(procInstId);
        Integer distributeType = distributeVo.getDistributeType();

        List<MaterialDistributeAssetsVo> assetsVoList = distributeVo.getAssetsList();
        for (MaterialDistributeAssetsVo assetsVo: assetsVoList) {

            List<MaterialDistributeDetailVo> detailVoList = assetsVo.getDetailList();
            detailVoList.forEach(detailVo ->{
                BigDecimal distributeNumber = detailVo.getDistributeNumber();

                String itemType = detailVo.getItemType();
                if ((itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))){
                    AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo,AssetsDetailDto.class);
                    BigDecimal oldUsableNumber = assetsDetailDto.getUsableNumber();
                    oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
                    BigDecimal newUsableNUmber = oldUsableNumber.subtract(distributeNumber);
                    assetsDetailDto.setUsableNumber(newUsableNUmber);
                    assetsDetailService.update(assetsDetailDto);
                }else{
                    SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get(detailVo.getAssetsDetailId());
                    SuppliesDetailDto suppliesDetailDto = dozerMapper.map(suppliesDetailVo,SuppliesDetailDto.class);
                    BigDecimal oldUsableNumber = suppliesDetailDto.getUsableNumber();
                    oldUsableNumber = ObjectUtil.isEmpty(oldUsableNumber) ? BigDecimal.ZERO : oldUsableNumber;
                    BigDecimal newUsableNUmber = oldUsableNumber.subtract(distributeNumber);
                    suppliesDetailDto.setUsableNumber(newUsableNUmber);
                    suppliesDetailService.update(suppliesDetailDto);
                }
            });

            //领用
            if(distributeType == TaskType.ACCEPT.code){
                BigDecimal distributeNumber = assetsVo.getDistributeNumber();
                MaterialAccept accept = materialAcceptMapper.selectById(distributeVo.getSourceId());
                if(ObjectUtil.isNotEmpty(accept)){
                    BigDecimal oldNotDistributeNumber =  accept.getNotDistributeNumber();
                    oldNotDistributeNumber = ObjectUtils.isEmpty(oldNotDistributeNumber)? BigDecimal.ZERO : oldNotDistributeNumber;
                    BigDecimal newNotDistributeNumber = oldNotDistributeNumber.subtract(distributeNumber);
                    accept.setNotDistributeNumber(newNotDistributeNumber);
                    result = SqlHelper.retBool(materialAcceptMapper.updateById(accept));
                }

                MaterialAcceptAssetsVo acceptAssetsVo = materialAcceptAssetsService.get(assetsVo.getSourceAssetsId());
                if (ObjectUtil.isNotEmpty(acceptAssetsVo)){
                    MaterialAcceptAssetsDto acceptAssetsDto = dozerMapper.map(acceptAssetsVo,MaterialAcceptAssetsDto.class);
                    BigDecimal oldNotDistributeNumber = acceptAssetsDto.getNotDistributeNumber();
                    oldNotDistributeNumber =  ObjectUtil.isEmpty(oldNotDistributeNumber) ? BigDecimal.ZERO : oldNotDistributeNumber;
                    BigDecimal newNotDistributeNumber = oldNotDistributeNumber.subtract(distributeNumber);
                    acceptAssetsDto.setNotDistributeNumber(newNotDistributeNumber);
                    result = materialAcceptAssetsService.update(acceptAssetsDto);
                }
            }
        }
        return result;
    }

    /**
     * 流程完成后，回填领用单数量
     * @param procInstId
     * @return
     */
    @Override
    @Transactional
    public Boolean procAuditSuccess(String procInstId) {
        boolean result = true;
        MaterialDistributeVo distributeVo = this.getByProc(procInstId);
        Integer distributeType = distributeVo.getDistributeType();

            //BigDecimal acceptNotDistributeNumber = acceptVo.getNotDistributeNumber();

        List<MaterialDistributeAssetsVo> assetsVoList = distributeVo.getAssetsList();
        for (MaterialDistributeAssetsVo assetsVo: assetsVoList) {

            List<MaterialDistributeDetailVo> detailVoList = assetsVo.getDetailList();
            detailVoList.forEach(detailVo ->{
                BigDecimal distributeNumber = detailVo.getDistributeNumber();
                distributeNumber = ObjectUtil.isEmpty(distributeNumber) ? BigDecimal.ZERO : distributeNumber;
                detailVo.setNotSignNumber(distributeNumber);
                MaterialDistributeDetailDto detailDto = dozerMapper.map(detailVo,MaterialDistributeDetailDto.class);
                materialDistributeDetailService.update(detailDto);
                String itemType = detailVo.getItemType();
                if ((itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))){
                    AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailDto.getAssetsDetailId());
                    AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo,AssetsDetailDto.class);
                    daybookService.log(distributeVo.getId(),distributeVo.getCamundaProcinsId(),assetsDetailDto, DayBookType.DISTRIBUTE);
                }else{
                    SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get(detailDto.getAssetsDetailId());
                    SuppliesDetailDto suppliesDetailDto = dozerMapper.map(suppliesDetailVo,SuppliesDetailDto.class);
                    daybookSuppliesService.log(distributeVo.getId(),distributeVo.getCamundaProcinsId(),suppliesDetailDto, DayBookType.DISTRIBUTE);
                }
            });
            //领用
            if(distributeType == TaskType.ACCEPT.code){
                BigDecimal distributeNumber = assetsVo.getDistributeNumber();

                MaterialAccept accept = materialAcceptMapper.selectById(assetsVo.getSourceId());
                if(ObjectUtil.isNotEmpty(accept)) {
                    BigDecimal oldDistrubuteNumber = accept.getDistributeNumber();
                    oldDistrubuteNumber = ObjectUtil.isEmpty(oldDistrubuteNumber) ? BigDecimal.ZERO : oldDistrubuteNumber;
//                    BigDecimal acceptDistributeNumber = ObjectUtils.isEmpty(acceptDto.getDistributeNumber())? BigDecimal.ZERO : acceptDto.getDistributeNumber();
                    BigDecimal newDistributeNumber = oldDistrubuteNumber.add(distributeNumber);
                    accept.setDistributeNumber(newDistributeNumber);

                    BigDecimal oldNotSignNumber = accept.getNotSignNumber();
                    oldNotSignNumber = oldNotSignNumber == null ? BigDecimal.ZERO : oldNotSignNumber;
                    BigDecimal newNotSignNumber = oldNotSignNumber.add(distributeNumber);
                    accept.setNotSignNumber(newNotSignNumber);
                    result = SqlHelper.retBool(materialAcceptMapper.updateById(accept));
                }
//            List<MaterialDistributeDetailVo> detailVoList = distributeVo.getDetailList();
//            for (MaterialDistributeDetailVo detailVo: detailVoList) {


                MaterialAcceptAssetsVo acceptAssetsVo = materialAcceptAssetsService.get(assetsVo.getSourceAssetsId());
                if (ObjectUtil.isNotEmpty(acceptAssetsVo)){
                    MaterialAcceptAssetsDto acceptAssetsDto = dozerMapper.map(acceptAssetsVo,MaterialAcceptAssetsDto.class);

                    BigDecimal oldDistributeNumber = acceptAssetsDto.getDistributeNumber();
                    oldDistributeNumber = oldDistributeNumber == null ? BigDecimal.ZERO : oldDistributeNumber;
                    BigDecimal newDistributeNumber = oldDistributeNumber.add(distributeNumber);
                    acceptAssetsDto.setDistributeNumber(newDistributeNumber);

                    BigDecimal oldNotSignNumber = acceptAssetsDto.getNotSignNumber();
                    oldNotSignNumber = oldNotSignNumber == null ? BigDecimal.ZERO : oldNotSignNumber;
                    BigDecimal newNotSignNumber = oldNotSignNumber.add(distributeNumber);
                    acceptAssetsDto.setNotSignNumber(newNotSignNumber);
                    materialAcceptAssetsService.update(acceptAssetsDto);
                }
            }
        }
        distributeVo.setStatus(0);
        distributeVo.setNotSignNumber(distributeVo.getSumTotalNumber());
        MaterialDistributeDto distributeDto = dozerMapper.map(distributeVo,MaterialDistributeDto.class);
        return result && this.update(distributeDto);
    }

}
