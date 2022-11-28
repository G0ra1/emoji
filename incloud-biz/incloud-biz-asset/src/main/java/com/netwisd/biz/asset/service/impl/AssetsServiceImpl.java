package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.constants.AssetsConditions;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.mapper.SuppliesMapper;
import com.netwisd.biz.asset.service.AssetsSkuService;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.asset.mapper.AssetsMapper;
import com.netwisd.biz.asset.service.AssetsService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.asset.dto.AssetsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.netwisd.common.db.util.EntityListConvert;
import com.netwisd.biz.asset.service.AssetsDetailService;
import com.netwisd.biz.asset.dto.AssetsDetailDto;

/**
 * @Description 资产台账 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-21 14:54:51
 */
@Service
@Slf4j
public class AssetsServiceImpl extends BatchServiceImpl<AssetsMapper, Assets> implements AssetsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private AssetsMapper assetsMapper;

    @Autowired
    private SuppliesMapper suppliesMapper;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private AssetsSkuService assetsSkuService;

    @Autowired
    private ViewerService viewerService;


    /**
    * 单表简单查询操作
    * @param assetsDto
    * @return
    */
    @Override
    public Page list(AssetsDto assetsDto) {
        LambdaQueryWrapper<Assets> queryWrapper = new LambdaQueryWrapper<>();
        //指定的查询字段
        String  searchCondition= assetsDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
                queryWrapper.and(q ->{
                q.like(Assets::getItemName,searchCondition)
                        .or(wrapper -> wrapper.like(Assets::getItemCode,searchCondition))
                        .or(wrapper -> wrapper.like(Assets::getApplyUserName,searchCondition))
                        //.like(Assets::getItemCode,searchCondition)
                        //.or()
                        //q.like(Assets::getApplyUserName,searchCondition)
                        .or(wrapper -> wrapper.like(Assets::getAssetOrgId,searchCondition))
                        //q.like(Assets::getAssetOrgId,searchCondition)
                        .or(wrapper -> wrapper.like(Assets::getAssetDeptId,searchCondition))
                        //q.like(Assets::getAssetDeptId,searchCondition)
                        .or(wrapper -> wrapper.like(Assets::getMaterialQuality,searchCondition))
                        //q.like(Assets::getMaterialQuality,searchCondition)
                        .or(wrapper -> wrapper.like(Assets::getClassifyName,searchCondition))
                        //q.like(Assets::getClassifyName,searchCondition)
                        .or(wrapper -> wrapper.like(Assets::getClassifyCode,searchCondition));
                        //q.like(Assets::getClassifyCode,searchCondition);
            });
        }

        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtil.isNotEmpty(assetsDto.getItemName()), Assets::getItemName,assetsDto.getItemName())
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

        Long appUserId = AppUserUtil.getLoginAppUser().getId();

        if(ObjectUtil.isNotEmpty(appUserId)) {
            //查看人员查看范围
            ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.ASSETS.getCode());

            queryWrapper.and(q -> {
                q.or(CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(Assets::getApplyUserId, viewerVo.getUserList()))
                        .or(CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(Assets::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Assets::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Assets::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        .or(CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Assets::getAssetDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(Assets::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(Assets::getCreateUserParentOrgId, viewerVo.getOrgList()))
                        .or(CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(Assets::getAssetOrgId, viewerVo.getOrgList()))
                ;
            });
//                queryWrapper
//                        //可看人员List
//                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(Assets::getApplyUserId, viewerVo.getUserList()))
//                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(Assets::getCreateUserId, viewerVo.getUserList()))
//                        //可看部门List
//                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Assets::getApplyUserDeptId, viewerVo.getDeptList()))
//                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Assets::getCreateUserParentDeptId, viewerVo.getDeptList()))
//                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Assets::getAssetDeptId, viewerVo.getDeptList()))
//                        //可看机构List
//                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(Assets::getApplyUserOrgId, viewerVo.getOrgList()))
//                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(Assets::getCreateUserParentOrgId, viewerVo.getOrgList()))
//                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Assets::getAssetOrgId, viewerVo.getOrgList()));

        }

        queryWrapper.orderByDesc(Assets::getApplyTime);
        Page<Assets> page = assetsMapper.selectPage(assetsDto.getPage(),queryWrapper);
        Page<AssetsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, AssetsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param assetsDto
    * @return
    */
    @Override
    public Page lists(AssetsDto assetsDto) {
        Page<AssetsVo> pageVo = assetsMapper.getPageList(assetsDto.getPage(),assetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public AssetsVo get(Long id) {
        Assets assets = super.getById(id);
        AssetsVo assetsVo = null;
        if(assets !=null){
            assetsVo = dozerMapper.map(assets,AssetsVo.class);
            //根据id获取assetsDetailVoList
            List<AssetsDetailVo> assetsDetailVoList = assetsDetailService.getByAssetsId(id);
            //设置assetsVo，以便构建其对应的子表数据
            assetsVo.setAssetsDetailList(assetsDetailVoList);
            //根据id获取assetsSkuVoList
            List<AssetsSkuVo> assetsSkuVoList = assetsSkuService.getByFkIdVo(id);
            //设置assetsVo，以便构建其对应的子表数据
            assetsVo.setAssetsSkuList(assetsSkuVoList);
        }
        return assetsVo;
    }

    /**
    * 保存实体
    * @param assetsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(AssetsDto assetsDto) {
        Assets assets = dozerMapper.map(assetsDto,Assets.class);
        return super.save(assets);
    }

    /**
     * 保存集合
     * @param assetsDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<AssetsDto> assetsDtos){
        List<Assets> Assetss = DozerUtils.mapList(dozerMapper, assetsDtos, Assets.class);
        return super.saveBatch(Assetss);
    }


    /**
     * 修改实体
     * @param assetsDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(AssetsDto assetsDto) {
        assetsDto.setUpdateTime(LocalDateTime.now());
        Assets assets = dozerMapper.map(assetsDto,Assets.class);
        return super.updateById(assets);
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
        assetsDetailService.deleteByFkId(id);
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
    public List<AssetsVo> getByFkIdVo(Long id){
        return null;
    }

    private LambdaQueryWrapper<Assets> getQueryWrapperForCondition(AssetsDto assetsDto, AssetsConditions... conditions){
        BigDecimal searchNumber = assetsDto.getAssetsNumber() != null? assetsDto.getAssetsNumber() : BigDecimal.ZERO;
        LambdaQueryWrapper<Assets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(Assets::getStockNumber,0);
        queryWrapper.gt(ObjectUtil.isNotEmpty(assetsDto.getItemId()),Assets::getItemId,assetsDto.getItemId());
        queryWrapper.gt(StringUtils.isNotBlank(assetsDto.getItemCode()),Assets::getItemCode,assetsDto.getItemCode());
        queryWrapper.like(StringUtils.isNotBlank(assetsDto.getItemName()),Assets::getItemName,assetsDto.getItemName());
        queryWrapper.gt(ObjectUtil.isNotEmpty(assetsDto.getAssetOrgId()),Assets::getAssetOrgId,assetsDto.getAssetOrgId());
        queryWrapper.like(StringUtils.isNotBlank(assetsDto.getAssetOrgName()),Assets::getAssetOrgName,assetsDto.getAssetOrgName());
        queryWrapper.gt(ObjectUtil.isNotEmpty(assetsDto.getAssetDeptId()),Assets::getAssetDeptId,assetsDto.getAssetDeptId());
        queryWrapper.like(StringUtils.isNotBlank(assetsDto.getAssetDeptName()),Assets::getAssetDeptName,assetsDto.getAssetDeptName());
        //queryWrapper.last(conditions.conditions+" > " + searchNumber);
        //String lastSql= "";
//        for (int i = 0 ; i < conditions.length ; i ++ ) {
//            lastSql += conditions[i].conditions + " > " + searchNumber;
//        }
//        queryWrapper.last(lastSql);
        return queryWrapper;
    }

    @Override
    public List<AssetsVo> getListByConditions(AssetsDto assetsDto, AssetsConditions... conditions) {
        List<Assets> assets = assetsMapper.selectList(getQueryWrapperForCondition(assetsDto,conditions));
        log.debug("查询条数:"+assets.size());
        return DozerUtils.mapList(dozerMapper,assets,AssetsVo.class);
    }


    @Override
    public Page<AssetsVo> getListByConditionsPage(AssetsDto assetsDto, AssetsConditions... conditions) {
//        LambdaQueryWrapper<Assets> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.gt(Assets::getStockNumber,0);
//        String lastSql= "";
//        for (int i = 0 ; i < conditions.length ; i ++ ) {
//            lastSql += conditions[i].conditions + " > " + searchNumber;
//        }
//        queryWrapper.last(lastSql);
        Page<AssetsVo> pageVo = assetsMapper.selectPage(assetsDto.getPage(),getQueryWrapperForCondition(assetsDto,conditions));
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }


        /**
         * 获取领用物资信息
         *
         * @param assetsDto assetsDto
         * @return
         */
        @Override
        public Page getDetailByAssets(AssetsDto assetsDto) {
            LambdaQueryWrapper<Assets> assetsQueryWrapper = new LambdaQueryWrapper<>();
            if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
                assetsQueryWrapper.eq(Assets::getAssetOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                        .eq(Assets::getAssetDeptId, AppUserUtil.getLoginAppUser().getParentDeptId());
            }
            assetsQueryWrapper.gt(Assets::getUsableNumber,0);
            //指定的查询字段
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
            }else{
                //根据实际业务构建具体的参数做查询
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
            }
            assetsQueryWrapper.orderByDesc(Assets::getApplyTime);
            Page<Assets> assetsPage = assetsMapper.selectPage(assetsDto.getPage(), assetsQueryWrapper);
            Page<AssetsVo> assetsVoPage = DozerUtils.mapPage(dozerMapper,assetsPage,AssetsVo.class);


            LambdaQueryWrapper<Supplies> suppliesQueryWrapper = new LambdaQueryWrapper<>();
            if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
                suppliesQueryWrapper.eq(Supplies::getAssetOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                        .eq(Supplies::getAssetDeptId, AppUserUtil.getLoginAppUser().getParentDeptId());
            }
            suppliesQueryWrapper.gt(Supplies::getUsableNumber,0);
            //全局模糊查询
            if(ObjectUtil.isNotEmpty(searchCondition)){
                suppliesQueryWrapper.like(Supplies::getItemName,searchCondition)
                        .or()
                        .like(Supplies::getItemCode,searchCondition)
                        .or()
                        .like(Supplies::getApplyUserName,searchCondition)
                        .or()
                        .like(Supplies::getAssetOrgId,searchCondition)
                        .or()
                        .like(Supplies::getAssetDeptId,searchCondition)
                        .or()
                        .like(Supplies::getMaterialQuality,searchCondition)
                        .or()
                        .like(Supplies::getClassifyName,searchCondition)
                        .or()
                        .like(Supplies::getClassifyCode,searchCondition);
            }else{
                //根据实际业务构建具体的参数做查询
                suppliesQueryWrapper.eq(ObjectUtil.isNotEmpty(assetsDto.getItemName()), Supplies::getItemName,assetsDto.getItemName())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getAssetOrgId()),Supplies::getAssetOrgId,assetsDto.getAssetOrgId())
                        .or()
                        .likeRight(ObjectUtil.isNotEmpty(assetsDto.getAssetOrgId()),Supplies::getAssetOrgFullId,assetsDto.getAssetOrgId()+",")
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getAssetDeptId()),Supplies::getAssetDeptId,assetsDto.getAssetDeptId())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getAssetOrgName()),Supplies::getAssetOrgName,assetsDto.getAssetOrgName())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getAssetDeptName()),Supplies::getAssetDeptName,assetsDto.getAssetDeptName())
                        .like(ObjectUtil.isNotEmpty(assetsDto.getMaterialQuality()),Supplies::getMaterialQuality,assetsDto.getMaterialQuality())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getClassifyName()),Supplies::getClassifyName,assetsDto.getClassifyName())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getClassifyCode()),Supplies::getClassifyCode,assetsDto.getClassifyCode())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getItemCode()),Supplies::getItemCode,assetsDto.getItemCode())
                        .eq(ObjectUtil.isNotEmpty(assetsDto.getItemType()),Supplies::getItemType,assetsDto.getItemType());
            }

            suppliesQueryWrapper.orderByDesc(Supplies::getApplyTime);
            List<Supplies> suppliesList = suppliesMapper.selectList(suppliesQueryWrapper);
            List<AssetsVo> suppliesVo = DozerUtils.mapList(dozerMapper, suppliesList, AssetsVo.class);

            List<AssetsVo> records = assetsVoPage.getRecords();
            records.addAll(suppliesVo);
            assetsVoPage.setTotal(records.size());
            log.debug("查询成功："+assetsVoPage.getTotal());
            return assetsVoPage;
        }
}

