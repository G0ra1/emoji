package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.PurchaseRegisterAssetsDto;
import com.netwisd.biz.asset.entity.PurchaseRegisterAssets;
import com.netwisd.biz.asset.mapper.PurchaseRegisterAssetsMapper;
import com.netwisd.biz.asset.service.PurchaseRegisterAssetsService;
import com.netwisd.biz.asset.service.PurchaseRegisterDetailService;
import com.netwisd.biz.asset.vo.PurchaseRegisterAssetsVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 采购信息登记明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-27 18:21:48
 */
@Service
@Slf4j
public class PurchaseRegisterAssetsServiceImpl extends BatchServiceImpl<PurchaseRegisterAssetsMapper, PurchaseRegisterAssets> implements PurchaseRegisterAssetsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseRegisterAssetsMapper purchaseRegisterAssetsMapper;

    @Autowired
    private PurchaseRegisterDetailService purchaseRegisterDetailService;

    /**
    * 单表简单查询操作
    * @param purchaseRegisterAssetsDto
    * @return
    */
    @Override
    public Page list(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto) {
        LambdaQueryWrapper<PurchaseRegisterAssets> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<PurchaseRegisterAssets> page = purchaseRegisterAssetsMapper.selectPage(purchaseRegisterAssetsDto.getPage(),queryWrapper);
        Page<PurchaseRegisterAssetsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseRegisterAssetsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseRegisterAssetsDto
    * @return
    */
    @Override
    public Page lists(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto) {
        Page<PurchaseRegisterAssetsVo> pageVo = purchaseRegisterAssetsMapper.getPageList(purchaseRegisterAssetsDto.getPage(),purchaseRegisterAssetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseRegisterAssetsVo get(Long id) {
        PurchaseRegisterAssets purchaseRegisterAssets = super.getById(id);
        PurchaseRegisterAssetsVo purchaseRegisterAssetsVo = null;
        if(purchaseRegisterAssets !=null){
            purchaseRegisterAssetsVo = dozerMapper.map(purchaseRegisterAssets,PurchaseRegisterAssetsVo.class);
        }
        return purchaseRegisterAssetsVo;
    }

    /**
    * 保存实体
    * @param purchaseRegisterAssetsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto) {
        PurchaseRegisterAssets purchaseRegisterAssets = dozerMapper.map(purchaseRegisterAssetsDto,PurchaseRegisterAssets.class);
        return super.save(purchaseRegisterAssets);
    }

    /**
     * 保存集合
     * @param purchaseRegisterAssetsDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseRegisterAssetsDto> purchaseRegisterAssetsDtos){
        List<PurchaseRegisterAssets> PurchaseRegisterAssetss = DozerUtils.mapList(dozerMapper, purchaseRegisterAssetsDtos, PurchaseRegisterAssets.class);
        return super.saveBatch(PurchaseRegisterAssetss);
    }

    /**
     * 修改实体
     * @param purchaseRegisterAssetsDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto) {
        purchaseRegisterAssetsDto.setUpdateTime(LocalDateTime.now());
        PurchaseRegisterAssets purchaseRegisterAssets = dozerMapper.map(purchaseRegisterAssetsDto,PurchaseRegisterAssets.class);
        return super.updateById(purchaseRegisterAssets);
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
    @Transactional
    public Boolean deleteByRegisterId(Long id){
        LambdaQueryWrapper<PurchaseRegisterAssets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseRegisterAssets::getRegisterId,id);
        List<PurchaseRegisterAssets> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<PurchaseRegisterAssetsVo> getByRegisterId(Long id){
        LambdaQueryWrapper<PurchaseRegisterAssets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseRegisterAssets::getRegisterId,id);
        List<PurchaseRegisterAssets> list = this.list(queryWrapper);
        List<PurchaseRegisterAssetsVo> purchaseRegisterAssetsVos = DozerUtils.mapList(dozerMapper, list, PurchaseRegisterAssetsVo.class);
        return purchaseRegisterAssetsVos;
    }

    @Override
    public List<PurchaseRegisterAssetsVo> getRegAssetsByIds(String registerIds) {
        String[] ids = registerIds.split(",");
        LambdaQueryWrapper<PurchaseRegisterAssets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(registerIds),PurchaseRegisterAssets::getRegisterId,ids);

        List<PurchaseRegisterAssets> assetsList = this.list(queryWrapper);
        List<PurchaseRegisterAssetsVo> assetsVoList = DozerUtils.mapList(dozerMapper, assetsList, PurchaseRegisterAssetsVo.class);
//        assetsVoList.forEach( assetsVo -> {
//            List<PurchaseRegisterDetailVo> detailVoList = purchaseRegisterDetailService.getByRegisterAssetsId(assetsVo.getId());
//            assetsVo.setDetailList(detailVoList);
//        });
        return assetsVoList;
    }
}
