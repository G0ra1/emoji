package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.dto.SuppliesSkuDto;
import com.netwisd.biz.asset.entity.SuppliesSku;
import com.netwisd.biz.asset.mapper.SuppliesSkuMapper;
import com.netwisd.biz.asset.service.SuppliesSkuService;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.SuppliesSkuVo;
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
 * @Description 耗材库存sku信息 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-27 16:52:43
 */
@Service
@Slf4j
public class SuppliesSkuServiceImpl extends BatchServiceImpl<SuppliesSkuMapper, SuppliesSku> implements SuppliesSkuService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private SuppliesSkuMapper suppliesSkuMapper;

    @Autowired
    private ViewerService viewerService;

    /**
    * 单表简单查询操作
    * @param suppliesSkuDto
    * @return
    */
    @Override
    public Page list(SuppliesSkuDto suppliesSkuDto) {
        LambdaQueryWrapper<SuppliesSku> queryWrapper = new LambdaQueryWrapper<>();

        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        if(ObjectUtil.isNotEmpty(appUserId)) {
            //查看人员查看范围
            ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.ASSETS.getCode());
            if (ObjectUtil.isNotEmpty(viewerVo)) {
                queryWrapper
                        //可看人员List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(SuppliesSku::getApplyUserId, viewerVo.getUserList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(SuppliesSku::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(SuppliesSku::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(SuppliesSku::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(SuppliesSku::getAssetDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(SuppliesSku::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(SuppliesSku::getCreateUserParentOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(SuppliesSku::getAssetOrgId, viewerVo.getOrgList()));
            }
        }
        //根据实际业务构建具体的参数做查询
        Page<SuppliesSku> page = suppliesSkuMapper.selectPage(suppliesSkuDto.getPage(),queryWrapper);
        Page<SuppliesSkuVo> pageVo = DozerUtils.mapPage(dozerMapper, page, SuppliesSkuVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param suppliesSkuDto
    * @return
    */
    @Override
    public Page lists(SuppliesSkuDto suppliesSkuDto) {
        Page<SuppliesSkuVo> pageVo = suppliesSkuMapper.getPageList(suppliesSkuDto.getPage(),suppliesSkuDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public SuppliesSkuVo get(Long id) {
        SuppliesSku suppliesSku = super.getById(id);
        SuppliesSkuVo suppliesSkuVo = null;
        if(suppliesSku !=null){
            suppliesSkuVo = dozerMapper.map(suppliesSku,SuppliesSkuVo.class);
        }
        return suppliesSkuVo;
    }

    /**
    * 保存实体
    * @param suppliesSkuDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(SuppliesSkuDto suppliesSkuDto) {
        SuppliesSku suppliesSku = dozerMapper.map(suppliesSkuDto,SuppliesSku.class);
        return super.save(suppliesSku) ;
    }

    /**
     * 保存集合
     * @param suppliesSkuDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<SuppliesSkuDto> suppliesSkuDtos){
        List<SuppliesSku> SuppliesSkus = DozerUtils.mapList(dozerMapper, suppliesSkuDtos, SuppliesSku.class);
        return super.saveBatch(SuppliesSkus);
    }


    /**
     * 修改实体
     * @param suppliesSkuDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(SuppliesSkuDto suppliesSkuDto) {
        suppliesSkuDto.setUpdateTime(LocalDateTime.now());
        SuppliesSku suppliesSku = dozerMapper.map(suppliesSkuDto,SuppliesSku.class);
        return super.updateById(suppliesSku);
    }


    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        return super.removeById(id);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public Boolean deleteByFkId(Long id){
        LambdaQueryWrapper<SuppliesSku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),SuppliesSku::getAssetsId,id);
        List<SuppliesSku> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<SuppliesSkuVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<SuppliesSku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),SuppliesSku::getAssetsId,id);
        List<SuppliesSku> list = this.list(queryWrapper);
        List<SuppliesSkuVo> suppliesSkuVos = DozerUtils.mapList(dozerMapper, list, SuppliesSkuVo.class);
        return suppliesSkuVos;
    }
}
