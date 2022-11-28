package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.dto.AssetsSkuDto;
import com.netwisd.biz.asset.entity.AssetsSku;
import com.netwisd.biz.asset.mapper.AssetsSkuMapper;
import com.netwisd.biz.asset.service.AssetsSkuService;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.AssetsSkuVo;
import com.netwisd.biz.asset.vo.ViewerVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产sku信息 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-27 13:57:50
 */
@Service
@Slf4j
public class AssetsSkuServiceImpl extends BatchServiceImpl<AssetsSkuMapper, AssetsSku> implements AssetsSkuService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private AssetsSkuMapper assetsSkuMapper;

    @Autowired
    private ViewerService viewerService;
    
    /**
    * 单表简单查询操作
    * @param assetsSkuDto
    * @return
    */
    @Override
    public Page list(AssetsSkuDto assetsSkuDto) {
        LambdaQueryWrapper<AssetsSku> queryWrapper = new LambdaQueryWrapper<>();

        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        if(ObjectUtil.isNotEmpty(appUserId)) {
            //查看人员查看范围
            ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.ASSETS.getCode());
            if (ObjectUtil.isNotEmpty(viewerVo)) {
                queryWrapper
                        //可看人员List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(AssetsSku::getApplyUserId, viewerVo.getUserList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(AssetsSku::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(AssetsSku::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(AssetsSku::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(AssetsSku::getAssetDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(AssetsSku::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(AssetsSku::getCreateUserParentOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(AssetsSku::getAssetOrgId, viewerVo.getOrgList()));
            }
        }
        //根据实际业务构建具体的参数做查询
        Page<AssetsSku> page = assetsSkuMapper.selectPage(assetsSkuDto.getPage(),queryWrapper);
        Page<AssetsSkuVo> pageVo = DozerUtils.mapPage(dozerMapper, page, AssetsSkuVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param assetsSkuDto
    * @return
    */
    @Override
    public Page lists(AssetsSkuDto assetsSkuDto) {
        Page<AssetsSkuVo> pageVo = assetsSkuMapper.getPageList(assetsSkuDto.getPage(),assetsSkuDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public AssetsSkuVo get(Long id) {
        AssetsSku assetsSku = super.getById(id);
        AssetsSkuVo assetsSkuVo = null;
        if(assetsSku !=null){
            assetsSkuVo = dozerMapper.map(assetsSku,AssetsSkuVo.class);
        }
        return assetsSkuVo;
    }

    /**
    * 保存实体
    * @param assetsSkuDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(AssetsSkuDto assetsSkuDto) {
        AssetsSku assetsSku = dozerMapper.map(assetsSkuDto,AssetsSku.class);
        return super.save(assetsSku);
    }

    /**
     * 保存集合
     * @param assetsSkuDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<AssetsSkuDto> assetsSkuDtos){
        List<AssetsSku> AssetsSkus = DozerUtils.mapList(dozerMapper, assetsSkuDtos, AssetsSku.class);
        return super.saveBatch(AssetsSkus);
    }


    /**
     * 修改实体
     * @param assetsSkuDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(AssetsSkuDto assetsSkuDto) {
        assetsSkuDto.setUpdateTime(LocalDateTime.now());
        AssetsSku assetsSku = dozerMapper.map(assetsSkuDto,AssetsSku.class);
        return super.updateById(assetsSku);
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
        LambdaQueryWrapper<AssetsSku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),AssetsSku::getAssetsId,id);
        List<AssetsSku> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<AssetsSkuVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<AssetsSku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),AssetsSku::getAssetsId,id);
        List<AssetsSku> list = this.list(queryWrapper);
        List<AssetsSkuVo> assetsSkuVos = DozerUtils.mapList(dozerMapper, list, AssetsSkuVo.class);
        return assetsSkuVos;
    }
}
