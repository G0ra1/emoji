package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.PurchaseAcceptAssetDto;
import com.netwisd.biz.asset.entity.PurchaseAcceptAsset;
import com.netwisd.biz.asset.mapper.PurchaseAcceptAssetMapper;
import com.netwisd.biz.asset.service.PurchaseAcceptAssetService;
import com.netwisd.biz.asset.vo.PurchaseAcceptAssetVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 验收明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
@Service
@Slf4j
public class PurchaseAcceptAssetServiceImpl extends BatchServiceImpl<PurchaseAcceptAssetMapper, PurchaseAcceptAsset> implements PurchaseAcceptAssetService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseAcceptAssetMapper purchaseAcceptAssetMapper;

    /**
    * 单表简单查询操作
    * @param purchaseAcceptAssetDto
    * @return
    */
    @Override
    public Page list(PurchaseAcceptAssetDto purchaseAcceptAssetDto) {
        LambdaQueryWrapper<PurchaseAcceptAsset> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<PurchaseAcceptAsset> page = purchaseAcceptAssetMapper.selectPage(purchaseAcceptAssetDto.getPage(),queryWrapper);
        Page<PurchaseAcceptAssetVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseAcceptAssetVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseAcceptAssetDto
    * @return
    */
    @Override
    public Page lists(PurchaseAcceptAssetDto purchaseAcceptAssetDto) {
        Page<PurchaseAcceptAssetVo> pageVo = purchaseAcceptAssetMapper.getPageList(purchaseAcceptAssetDto.getPage(),purchaseAcceptAssetDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseAcceptAssetVo get(Long id) {
        PurchaseAcceptAsset purchaseAcceptAsset = super.getById(id);
        PurchaseAcceptAssetVo purchaseAcceptAssetVo = null;
        if(purchaseAcceptAsset !=null){
            purchaseAcceptAssetVo = dozerMapper.map(purchaseAcceptAsset,PurchaseAcceptAssetVo.class);
        }
        return purchaseAcceptAssetVo;
    }

    /**
    * 保存实体
    * @param purchaseAcceptAssetDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseAcceptAssetDto purchaseAcceptAssetDto) {
        PurchaseAcceptAsset purchaseAcceptAsset = dozerMapper.map(purchaseAcceptAssetDto,PurchaseAcceptAsset.class);
        //saveSubList(purchaseAcceptAssetDto);
        return super.save(purchaseAcceptAsset);
    }

    /**
     * 保存集合
     * @param purchaseAcceptAssetDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseAcceptAssetDto> purchaseAcceptAssetDtos){
        List<PurchaseAcceptAsset> PurchaseAcceptAssets = DozerUtils.mapList(dozerMapper, purchaseAcceptAssetDtos, PurchaseAcceptAsset.class);
//        for (PurchaseAcceptAssetDto purchaseAcceptAssetDto : purchaseAcceptAssetDtos){
//            saveSubList(purchaseAcceptAssetDto);
//        }
        return super.saveBatch(PurchaseAcceptAssets);
    }

    /**
     * 保存子表集合
     * @param purchaseAcceptAssetDto
     * @return
     */
    @Transactional
    void saveSubList(PurchaseAcceptAssetDto purchaseAcceptAssetDto){
    }

    /**
     * 修改实体
     * @param purchaseAcceptAssetDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PurchaseAcceptAssetDto purchaseAcceptAssetDto) {
        purchaseAcceptAssetDto.setUpdateTime(LocalDateTime.now());
        PurchaseAcceptAsset purchaseAcceptAsset = dozerMapper.map(purchaseAcceptAssetDto,PurchaseAcceptAsset.class);
        return super.updateById(purchaseAcceptAsset);
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
    public Boolean deleteByAcceptanceId(Long id){
        LambdaQueryWrapper<PurchaseAcceptAsset> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseAcceptAsset::getAcceptanceId,id);
        List<PurchaseAcceptAsset> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<PurchaseAcceptAssetVo> getByAcceptanceId(Long id){
        LambdaQueryWrapper<PurchaseAcceptAsset> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseAcceptAsset::getAcceptanceId,id);
        List<PurchaseAcceptAsset> list = this.list(queryWrapper);
        List<PurchaseAcceptAssetVo> purchaseAcceptAssetVos = DozerUtils.mapList(dozerMapper, list, PurchaseAcceptAssetVo.class);
        return purchaseAcceptAssetVos;
    }
}
