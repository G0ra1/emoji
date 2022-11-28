package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.constants.AssetsConditions;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDto;
import com.netwisd.biz.asset.entity.Assets;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.ViewerVo;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.asset.entity.AssetsDetail;
import com.netwisd.biz.asset.mapper.AssetsDetailMapper;
import com.netwisd.biz.asset.service.AssetsDetailService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.netwisd.common.db.util.EntityListConvert;
import cn.hutool.core.util.ObjectUtil;

/**
 * @Description 资产明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-21 14:54:51
 */
@Service
@Slf4j
public class AssetsDetailServiceImpl extends BatchServiceImpl<AssetsDetailMapper, AssetsDetail> implements AssetsDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private AssetsDetailMapper assetsDetailMapper;

    @Autowired
    private ViewerService viewerService;

    /**
    * 单表简单查询操作
    * @param assetsDetailDto
    * @return
    */
    @Override
    public Page list(AssetsDetailDto assetsDetailDto) {
        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(AssetsDetail::getAssetOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                        .eq(AssetsDetail::getAssetDeptId, AppUserUtil.getLoginAppUser().getParentDeptId());
        }
//        queryWrapper.eq(StringUtils.isNotBlank(assetsDetailDto.getItemName()),AssetsDetail::getItemName,assetsDetailDto.getItemName())
//                    .eq(StringUtils.isNotBlank(assetsDetailDto.getItemCode()),AssetsDetail::getItemCode,assetsDetailDto.getItemCode());
        String searchCondition = assetsDetailDto.getSearchCondition();
        if (ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
               q.like(AssetsDetail::getItemName,searchCondition)
               .or(wrapper -> wrapper.like(AssetsDetail::getItemCode,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getAssetOrgName,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getDescshort,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getAssetDeptName,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getClassifyCode,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getClassifyName,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getUnitName,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getMaterialQuality,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getStandard,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getSpecs,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getManufacturer,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getProductionDate,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getBatchNumber,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getFactoryCode,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getServiceLife,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getWarehouseName,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getShelfName,searchCondition))
               .or(wrapper -> wrapper.like(AssetsDetail::getStatus,searchCondition));
            });
        }
        queryWrapper
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetOrgId()),AssetsDetail::getAssetOrgId,assetsDetailDto.getAssetOrgId())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetDeptId()),AssetsDetail::getAssetDeptId,assetsDetailDto.getAssetDeptId())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetUserId()),AssetsDetail::getAssetUserId,assetsDetailDto.getAssetUserId())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getItemName()), AssetsDetail::getItemName,assetsDetailDto.getItemName())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetOrgId()),AssetsDetail::getAssetOrgId,assetsDetailDto.getAssetOrgId())
                .or()
                .likeRight(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetOrgId()),AssetsDetail::getAssetOrgFullId,assetsDetailDto.getAssetOrgId()+",")
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetDeptId()),AssetsDetail::getAssetDeptId,assetsDetailDto.getAssetDeptId())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetOrgName()),AssetsDetail::getAssetOrgName,assetsDetailDto.getAssetOrgName())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetDeptName()),AssetsDetail::getAssetDeptName,assetsDetailDto.getAssetDeptName())
                .like(ObjectUtil.isNotEmpty(assetsDetailDto.getMaterialQuality()),AssetsDetail::getMaterialQuality,assetsDetailDto.getMaterialQuality())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getClassifyName()),AssetsDetail::getClassifyName,assetsDetailDto.getClassifyName())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getClassifyCode()),AssetsDetail::getClassifyCode,assetsDetailDto.getClassifyCode())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getItemType()),AssetsDetail::getItemType,assetsDetailDto.getItemType())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetSource()),AssetsDetail::getAssetSource,assetsDetailDto.getAssetSource())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getSeriesNumber()),AssetsDetail::getSeriesNumber,assetsDetailDto.getSeriesNumber())
                .eq(ObjectUtil.isNotEmpty(assetsDetailDto.getItemCode()),AssetsDetail::getItemCode,assetsDetailDto.getItemCode());

        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        if (ObjectUtil.isNotEmpty(appUserId)) {
            //查看人员查看范围
            ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.ASSETS.getCode());

//            Long orgId = assetsDetailDto.getAssetOrgId();
//            if(ObjectUtil.isEmpty(orgId)){
//                List<Long> orgList = new ArrayList();
//                if(viewerVo.getOrgList().contains(orgId)){
//                    orgList.add(orgId);
//                }
//                viewerVo.setOrgList(orgList);
//            }
//            Long deptId = assetsDetailDto.getAssetDeptId();
//            if(ObjectUtil.isEmpty(deptId)){
//                List<Long> deptList = new ArrayList();
//                if(viewerVo.getDeptList().contains(deptId)){
//                    deptList.add(deptId);
//                }
//                viewerVo.setDeptList(deptList);
//            }

            if (ObjectUtil.isNotEmpty(viewerVo)) {
                queryWrapper.and(q -> {
                    q
                            //可看人员List
                            .or(CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(AssetsDetail::getApplyUserId, viewerVo.getUserList()))
                            .or(CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(AssetsDetail::getCreateUserId, viewerVo.getUserList()))
                            .or(CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(AssetsDetail::getAssetUserId, viewerVo.getUserList()))
                            //可看部门List
                            .or(CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(AssetsDetail::getApplyUserDeptId, viewerVo.getDeptList()))
                            .or(CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(AssetsDetail::getCreateUserParentDeptId, viewerVo.getDeptList()))
                            .or(CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(AssetsDetail::getAssetDeptId, viewerVo.getDeptList()))
                            //可看机构List
                            .or(CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(AssetsDetail::getApplyUserOrgId, viewerVo.getOrgList()))
                            .or(CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(AssetsDetail::getCreateUserParentOrgId, viewerVo.getOrgList()))
                            .or(CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(AssetsDetail::getAssetOrgId, viewerVo.getOrgList()));
                });
            }
        }

        queryWrapper.orderByDesc(AssetsDetail::getApplyTime);
        Page<AssetsDetail> page = assetsDetailMapper.selectPage(assetsDetailDto.getPage(),queryWrapper);
        Page<AssetsDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, AssetsDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param assetsDetailDto
    * @return
    */
    @Override
    public Page lists(AssetsDetailDto assetsDetailDto) {
        Page<AssetsDetailVo> pageVo = assetsDetailMapper.getPageList(assetsDetailDto.getPage(),assetsDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public AssetsDetailVo get(Long id) {
        AssetsDetail assetsDetail = super.getById(id);
        AssetsDetailVo assetsDetailVo = null;
        if(assetsDetail !=null){
            assetsDetailVo = dozerMapper.map(assetsDetail,AssetsDetailVo.class);
        }
        return assetsDetailVo;
    }

    /**
    * 保存实体
    * @param assetsDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(AssetsDetailDto assetsDetailDto) {
        AssetsDetail assetsDetail = dozerMapper.map(assetsDetailDto,AssetsDetail.class);
        return super.save(assetsDetail);
    }

    /**
     * 保存集合
     * @param assetsDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<AssetsDetailDto> assetsDetailDtos){
        List<AssetsDetail> AssetsDetails = DozerUtils.mapList(dozerMapper, assetsDetailDtos, AssetsDetail.class);
        return super.saveBatch(AssetsDetails);
    }

    /**
     * 修改实体
     * @param assetsDetailDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(AssetsDetailDto assetsDetailDto) {
        assetsDetailDto.setUpdateTime(LocalDateTime.now());
        AssetsDetail assetsDetail = dozerMapper.map(assetsDetailDto,AssetsDetail.class);
        return super.updateById(assetsDetail);
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
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public void deleteByFkId(Long id){
        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),AssetsDetail::getAssetsId,id);
        List<AssetsDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<AssetsDetailVo> getByAssetsId(Long id){
        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),AssetsDetail::getAssetsId,id);
        queryWrapper.orderByDesc(AssetsDetail::getApplyTime);
        List<AssetsDetail> list = this.list(queryWrapper);
        List<AssetsDetailVo> assetsDetailVos = DozerUtils.mapList(dozerMapper, list, AssetsDetailVo.class);
        return assetsDetailVos;
    }
    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<AssetsDetailVo> getByAssetsSkuId(Long id){
        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),AssetsDetail::getAssetsSkuId,id);
        queryWrapper.orderByDesc(AssetsDetail::getApplyTime);
        List<AssetsDetail> list = this.list(queryWrapper);
        List<AssetsDetailVo> assetsDetailVos = DozerUtils.mapList(dozerMapper, list, AssetsDetailVo.class);
        return assetsDetailVos;
    }

    /**
     * 根据机构id或者部门id或者人员id
     * @param assetsDetailDto
     * @return
     */
    @Override
    public Page getAssetsDetail(AssetsDetailDto assetsDetailDto) {
        Page<AssetsDetailVo> pageVo = assetsDetailMapper.getAssetsDetail(assetsDetailDto.getPage(),assetsDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public List<AssetsDetailVo> getListByConditions(AssetsDetailDto assetsDetailDto, AssetsConditions... conditions) {
        List<AssetsDetail> assetsDetails = assetsDetailMapper.selectList(getQueryWrapperForCondition(assetsDetailDto,conditions));
        log.debug("查询条数:"+assetsDetails.size());
        return DozerUtils.mapList(dozerMapper,assetsDetails,AssetsDetailVo.class);
    }

    @Override
    public Page getListByConditionsPage(AssetsDetailDto assetsDetailDto, AssetsConditions... conditions) {
        Page<AssetsDetailVo> pageVo = assetsDetailMapper.selectPage(assetsDetailDto.getPage(),getQueryWrapperForCondition(assetsDetailDto,conditions));
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page<AssetsDetailVo> getListByAssetsIdPage(Long... assetsIds) {
        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(assetsIds.length > 0,AssetsDetail::getAssetsId,assetsIds);
        Page<AssetsDetailVo> pageVo = assetsDetailMapper.selectPage(new AssetsDetailDto().getPage(),queryWrapper);
        return pageVo;
    }
    /**
    *获取待报废物资资产明细信息
    * @param assetsDetailDto
     * @return
    */
    @Override
    public Page<AssetsDetailVo> getScrapDetail(AssetsDetailDto assetsDetailDto) {
        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(AssetsDetail::getAssetOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(AssetsDetail::getAssetDeptId, AppUserUtil.getLoginAppUser().getParentDeptId());
        }
        queryWrapper.gt(AssetsDetail::getUsableNumber, YesNo.NO.getCode())
                .gt(AssetsDetail::getStockNumber,YesNo.NO.getCode())
                .gt(AssetsDetail::getStorageNumber,YesNo.NO.getCode())
                .orderByDesc(AssetsDetail::getApplyTime);
        String searchCondition = assetsDetailDto.getSearchCondition();
        if (ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(AssetsDetail::getItemName,searchCondition)
                        .or(wrapper -> wrapper.like(AssetsDetail::getItemCode,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getAssetOrgName,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getDescshort,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getAssetDeptName,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getClassifyCode,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getClassifyName,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getUnitName,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getMaterialQuality,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getStandard,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getSpecs,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getManufacturer,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getProductionDate,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getBatchNumber,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getFactoryCode,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getServiceLife,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getWarehouseName,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getShelfName,searchCondition))
                        .or(wrapper -> wrapper.like(AssetsDetail::getStatus,searchCondition));
            });
        }
        Page<AssetsDetailVo> page = assetsDetailMapper.selectPage(assetsDetailDto.getPage(),queryWrapper);
        Page<AssetsDetailVo> pageVo = DozerUtils.mapPage(dozerMapper,page,AssetsDetailVo.class);
        return pageVo;
    }

    private LambdaQueryWrapper<AssetsDetail> getQueryWrapperForCondition(AssetsDetailDto assetsDetailDto, AssetsConditions... conditions){
        BigDecimal searchNumber = assetsDetailDto.getAssetsNumber() != null? assetsDetailDto.getAssetsNumber() : BigDecimal.ZERO;
        LambdaQueryWrapper<AssetsDetail> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.gt(AssetsDetail::getStockNumber,0);
//        queryWrapper.gt(StringUtils.isNotBlank(assetsDetailDto.getItemCode()),AssetsDetail::getItemCode,assetsDetailDto.getItemCode());
//        queryWrapper.like(StringUtils.isNotBlank(assetsDetailDto.getItemName()),AssetsDetail::getItemName,assetsDetailDto.getItemName());
//        queryWrapper.gt(StringUtils.isNotBlank(assetsDetailDto.getAssetUserOrgId()),AssetsDetail::getAssetOrgId,assetsDetailDto.getAssetOrgId());
//        queryWrapper.like(StringUtils.isNotBlank(assetsDetailDto.getAssetOrgName()),AssetsDetail::getAssetOrgName,assetsDetailDto.getAssetOrgName());
//        queryWrapper.gt(ObjectUtil.isNotEmpty(assetsDetailDto.getAssetUserDeptId()),AssetsDetail::getAssetDeptId,assetsDetailDto.getAssetDeptId());
//        queryWrapper.like(StringUtils.isNotBlank(assetsDetailDto.getAssetDeptName()),AssetsDetail::getAssetDeptName,assetsDetailDto.getAssetDeptName());
//        //queryWrapper.last(conditions.conditions+" > " + searchNumber);
//        String lastSql= "";
//        for (int i = 0 ; i < conditions.length ; i ++ ) {
//            lastSql += conditions[i].conditions + " > " + searchNumber;
//        }
//        queryWrapper.last(lastSql);
        return queryWrapper;
    }
}
