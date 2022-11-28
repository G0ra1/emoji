package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.entity.Supplies;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.ViewerVo;
import com.netwisd.common.core.data.FieldVm;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.asset.entity.SuppliesDetail;
import com.netwisd.biz.asset.mapper.SuppliesDetailMapper;
import com.netwisd.biz.asset.service.SuppliesDetailService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.vo.SuppliesDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.netwisd.common.db.util.EntityListConvert;
import cn.hutool.core.util.ObjectUtil;

/**
 * @Description 耗材库存明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:28:53
 */
@Service
@Slf4j
public class SuppliesDetailServiceImpl extends BatchServiceImpl<SuppliesDetailMapper, SuppliesDetail> implements SuppliesDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private SuppliesDetailMapper suppliesDetailMapper;

    @Autowired
    private ViewerService viewerService;

    /**
    * 单表简单查询操作
    * @param suppliesDetailDto
    * @return
    */
    @Override
    public Page list(SuppliesDetailDto suppliesDetailDto) {
        LambdaQueryWrapper<SuppliesDetail> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(SuppliesDetail::getAssetOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(SuppliesDetail::getAssetDeptId, AppUserUtil.getLoginAppUser().getParentDeptId());
        }
        queryWrapper.orderByDesc(SuppliesDetail::getApplyTime);
        String  searchCondition= suppliesDetailDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)) {
            queryWrapper.and(q -> {
                q.like(SuppliesDetail::getItemName, searchCondition)
                        .or(wrapper -> wrapper.like(SuppliesDetail::getItemCode, searchCondition))
                        .or(wrapper -> wrapper.like(SuppliesDetail::getDescshort, searchCondition))
                        .or(wrapper -> wrapper.like(SuppliesDetail::getAssetOrgName, searchCondition))
                        .or(wrapper -> wrapper.like(SuppliesDetail::getAssetDeptName, searchCondition))
                        .or(wrapper -> wrapper.like(SuppliesDetail::getBatchNumber, searchCondition))
                        .or(wrapper -> wrapper.like(SuppliesDetail::getAcceptanceDate, searchCondition))
                        .or(wrapper -> wrapper.like(SuppliesDetail::getItemType, searchCondition));
            });
        }
            Long appUserId = AppUserUtil.getLoginAppUser().getId();
            if (ObjectUtil.isNotEmpty(appUserId)) {
                //查看人员查看范围
                ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.ASSETS.getCode());

                if (ObjectUtil.isNotEmpty(viewerVo)) {
                    queryWrapper.and(q -> {
                        q
                            //可看人员List
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(SuppliesDetail::getApplyUserId, viewerVo.getUserList()))
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(SuppliesDetail::getCreateUserId, viewerVo.getUserList()))
                            //可看部门List
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(SuppliesDetail::getApplyUserDeptId, viewerVo.getDeptList()))
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(SuppliesDetail::getCreateUserParentDeptId, viewerVo.getDeptList()))
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(SuppliesDetail::getAssetDeptId, viewerVo.getDeptList()))
                            //可看机构List
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(SuppliesDetail::getApplyUserOrgId, viewerVo.getOrgList()))
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(SuppliesDetail::getCreateUserParentOrgId, viewerVo.getOrgList()))
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(SuppliesDetail::getAssetOrgId, viewerVo.getOrgList()));
                });
            }

        }

        //根据实际业务构建具体的参数做查询
        Page<SuppliesDetail> page = suppliesDetailMapper.selectPage(suppliesDetailDto.getPage(),queryWrapper);
        Page<SuppliesDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, SuppliesDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param suppliesDetailDto
    * @return
    */
    @Override
    public Page lists(SuppliesDetailDto suppliesDetailDto) {
        Page<SuppliesDetailVo> pageVo = suppliesDetailMapper.getPageList(suppliesDetailDto.getPage(),suppliesDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public SuppliesDetailVo get(Long id) {
        SuppliesDetail suppliesDetail = super.getById(id);
        SuppliesDetailVo suppliesDetailVo = null;
        if(suppliesDetail !=null){
            suppliesDetailVo = dozerMapper.map(suppliesDetail,SuppliesDetailVo.class);
        }
        return suppliesDetailVo;
    }

    /**
    * 保存实体
    * @param suppliesDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(SuppliesDetailDto suppliesDetailDto) {
        SuppliesDetail suppliesDetail = dozerMapper.map(suppliesDetailDto,SuppliesDetail.class);
        return super.save(suppliesDetail);
    }

    /**
     * 保存集合
     * @param suppliesDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<SuppliesDetailDto> suppliesDetailDtos){
        List<SuppliesDetail> SuppliesDetails = DozerUtils.mapList(dozerMapper, suppliesDetailDtos, SuppliesDetail.class);
        return super.saveBatch(SuppliesDetails);
    }


    /**
     * 修改实体
     * @param suppliesDetailDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(SuppliesDetailDto suppliesDetailDto) {
        suppliesDetailDto.setUpdateTime(LocalDateTime.now());
        SuppliesDetail suppliesDetail = dozerMapper.map(suppliesDetailDto,SuppliesDetail.class);
        return super.updateById(suppliesDetail);
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
    public Boolean deleteByAssetsId(Long id){
        LambdaQueryWrapper<SuppliesDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),SuppliesDetail::getAssetsId,id);
        List<SuppliesDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<SuppliesDetailVo> getByAssetsId(Long id){
        LambdaQueryWrapper<SuppliesDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),SuppliesDetail::getAssetsId,id);
        List<SuppliesDetail> list = this.list(queryWrapper);
        List<SuppliesDetailVo> suppliesDetailVos = DozerUtils.mapList(dozerMapper, list, SuppliesDetailVo.class);
        return suppliesDetailVos;
    }
    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<SuppliesDetailVo> getByAssetsSkuId(Long id){
        LambdaQueryWrapper<SuppliesDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),SuppliesDetail::getAssetsSkuId,id);
        List<SuppliesDetail> list = this.list(queryWrapper);
        List<SuppliesDetailVo> suppliesDetailVos = DozerUtils.mapList(dozerMapper, list, SuppliesDetailVo.class);
        return suppliesDetailVos;
    }
}
