package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.entity.Assets;
import com.netwisd.biz.asset.entity.AssetsDetail;
import com.netwisd.biz.asset.service.SuppliesSkuService;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.SuppliesSkuVo;
import com.netwisd.biz.asset.vo.ViewerVo;
import com.netwisd.common.core.data.FieldVm;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.asset.entity.Supplies;
import com.netwisd.biz.asset.mapper.SuppliesMapper;
import com.netwisd.biz.asset.service.SuppliesService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.asset.dto.SuppliesDto;
import com.netwisd.biz.asset.vo.SuppliesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.netwisd.common.db.util.EntityListConvert;
import cn.hutool.core.util.ObjectUtil;
import com.netwisd.biz.asset.service.SuppliesDetailService;
import com.netwisd.biz.asset.vo.SuppliesDetailVo;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.entity.SuppliesDetail;

/**
 * @Description 耗材库存台账 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:28:53
 */
@Service
@Slf4j
public class SuppliesServiceImpl extends BatchServiceImpl<SuppliesMapper, Supplies> implements SuppliesService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private SuppliesMapper suppliesMapper;

    @Autowired
    private SuppliesDetailService suppliesDetailService;

    @Autowired
    private SuppliesSkuService suppliesSkuService;

    @Autowired
    private ViewerService viewerService;

    /**
    * 单表简单查询操作
    * @param suppliesDto
    * @return
    */
    @Override
    public Page list(SuppliesDto suppliesDto) {
        LambdaQueryWrapper<Supplies> queryWrapper = new LambdaQueryWrapper<>();

        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(Supplies::getAssetOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(Supplies::getAssetDeptId, AppUserUtil.getLoginAppUser().getParentDeptId());
        }
        //指定的查询字段
        String  searchCondition= suppliesDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(Supplies::getItemName,searchCondition)
                        .or(wrapper -> wrapper.like(Supplies::getItemCode,searchCondition))
                        .or(wrapper -> wrapper.like(Supplies::getDescshort,searchCondition))
                        .or(wrapper -> wrapper.like(Supplies::getAssetOrgName,searchCondition))
                        .or(wrapper -> wrapper.like(Supplies::getAssetDeptName,searchCondition));
            });

        }
        queryWrapper.eq(StringUtils.isNotBlank(suppliesDto.getItemName()),Supplies::getItemName,suppliesDto.getItemName())
                .eq(StringUtils.isNotBlank(suppliesDto.getItemCode()),Supplies::getItemCode,suppliesDto.getItemCode())
                .eq(ObjectUtil.isNotEmpty(suppliesDto.getAssetOrgId()),Supplies::getAssetOrgId,suppliesDto.getAssetOrgId())
                .eq(ObjectUtil.isNotEmpty(suppliesDto.getAssetDeptId()),Supplies::getAssetDeptId,suppliesDto.getAssetDeptId())
                .orderByDesc(Supplies::getApplyTime);


        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        if (ObjectUtil.isNotEmpty(appUserId)) {
                //查看人员查看范围
            ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.ASSETS.getCode());


            if (ObjectUtil.isNotEmpty(viewerVo)) {
                queryWrapper.and(q -> {
                    q
                        //可看人员List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(Supplies::getApplyUserId, viewerVo.getUserList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(Supplies::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Supplies::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Supplies::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Supplies::getAssetDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(Supplies::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(Supplies::getCreateUserParentOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(Supplies::getAssetOrgId, viewerVo.getOrgList()));
                });
            }

        }

        //根据实际业务构建具体的参数做查询
        Page<Supplies> page = suppliesMapper.selectPage(suppliesDto.getPage(),queryWrapper);
        Page<SuppliesVo> pageVo = DozerUtils.mapPage(dozerMapper, page, SuppliesVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param suppliesDto
    * @return
    */
    @Override
    public Page lists(SuppliesDto suppliesDto) {
        Page<SuppliesVo> pageVo = suppliesMapper.getPageList(suppliesDto.getPage(),suppliesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public SuppliesVo get(Long id) {
        Supplies supplies = super.getById(id);
        SuppliesVo suppliesVo = null;
        if(supplies !=null){
            suppliesVo = dozerMapper.map(supplies,SuppliesVo.class);
            //根据id获取suppliesDetailVoList
            List<SuppliesDetailVo> suppliesDetailVoList = suppliesDetailService.getByAssetsId(id);
            //设置suppliesVo，以便构建其对应的子表数据
            suppliesVo.setSuppliesDetailList(suppliesDetailVoList);
            //根据id获取suppliesSkuVoList
            List<SuppliesSkuVo>  suppliesSkuVoList = suppliesSkuService.getByFkIdVo(id);
            //设置suppliesVo，以便构建其对应的子表数据
            suppliesVo.setSuppliesSkuList(suppliesSkuVoList);
        }
        return suppliesVo;
    }

    /**
    * 保存实体
    * @param suppliesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(SuppliesDto suppliesDto) {
        Supplies supplies = dozerMapper.map(suppliesDto,Supplies.class);
        return super.save(supplies) && saveSubList(suppliesDto);
    }

    /**
     * 保存集合
     * @param suppliesDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<SuppliesDto> suppliesDtos){
        Boolean result = true;
        List<Supplies> Suppliess = DozerUtils.mapList(dozerMapper, suppliesDtos, Supplies.class);
        super.saveBatch(Suppliess);
        for (SuppliesDto suppliesDto : suppliesDtos){
            result = saveSubList(suppliesDto);
        }
        return result;
    }

    /**
     * 保存子表集合
     * @param suppliesDto
     * @return
     */
    @Transactional
    Boolean saveSubList(SuppliesDto suppliesDto){
        Boolean result = true;
        //获取其子表集合
        List<SuppliesDetailDto> suppliesDetailDtoList = suppliesDto.getSuppliesDetailList();
        if(suppliesDetailDtoList != null && !suppliesDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            suppliesDetailDtoList.forEach(detailDto -> {
                detailDto.setAssetsId(suppliesDto.getId());
            });
            //调用保存子表的集合方法
            result = suppliesDetailService.saveList(suppliesDetailDtoList);
        }
        return result;
    }

    /**
     * 修改实体
     * @param suppliesDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(SuppliesDto suppliesDto) {
        suppliesDto.setUpdateTime(LocalDateTime.now());
        Supplies supplies = dozerMapper.map(suppliesDto,Supplies.class);
        return super.updateById(supplies) && updateSub(suppliesDto);
    }

    /**
    * 修改子类实体
    * @param suppliesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean updateSub(SuppliesDto suppliesDto) {
        Boolean result = true;
        List<SuppliesDetailDto> suppliesDetailDtoList = suppliesDto.getSuppliesDetailList();
        if(suppliesDetailDtoList != null && !suppliesDetailDtoList.isEmpty()){

            //根据主实体DTO映射其子表的关联键为其赋值
            suppliesDetailDtoList.forEach(detailDto -> {
                detailDto.setAssetsId(suppliesDto.getId());
            });
            List<SuppliesDetail> suppliesDetails = DozerUtils.mapList(dozerMapper, suppliesDetailDtoList, SuppliesDetail.class);

            LambdaQueryWrapper<SuppliesDetail> suppliesDetailListQueryWrapper = new LambdaQueryWrapper<>();
            suppliesDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(suppliesDto.getId()),SuppliesDetail::getAssetsId,suppliesDto.getId());
            //调用更新方法
            result = suppliesDetailService.saveOrUpdateOrDeleteBatch(suppliesDetailListQueryWrapper,suppliesDetails,suppliesDetails.size());
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
        return super.removeById(id) && suppliesDetailService.deleteByAssetsId(id);
    }

}
