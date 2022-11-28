package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.PurchaseStorageDetailDto;
import com.netwisd.biz.asset.entity.PurchaseStorageDetail;
import com.netwisd.biz.asset.mapper.PurchaseStorageDetailMapper;
import com.netwisd.biz.asset.service.PurchaseStorageDetailService;
import com.netwisd.biz.asset.vo.PurchaseStorageDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 物资验收入库明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-20 18:03:40
 */
@Service
@Slf4j
public class PurchaseStorageDetailServiceImpl extends BatchServiceImpl<PurchaseStorageDetailMapper, PurchaseStorageDetail> implements PurchaseStorageDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseStorageDetailMapper purchaseStorageDetailMapper;

    /**
    * 单表简单查询操作
    * @param purchaseStorageDetailDto
    * @return
    */
    @Override
    public Page list(PurchaseStorageDetailDto purchaseStorageDetailDto) {
        LambdaQueryWrapper<PurchaseStorageDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<PurchaseStorageDetail> page = purchaseStorageDetailMapper.selectPage(purchaseStorageDetailDto.getPage(),queryWrapper);
        Page<PurchaseStorageDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseStorageDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseStorageDetailDto
    * @return
    */
    @Override
    public Page lists(PurchaseStorageDetailDto purchaseStorageDetailDto) {
        Page<PurchaseStorageDetailVo> pageVo = purchaseStorageDetailMapper.getPageList(purchaseStorageDetailDto.getPage(),purchaseStorageDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseStorageDetailVo get(Long id) {
        PurchaseStorageDetail purchaseStorageDetail = super.getById(id);
        PurchaseStorageDetailVo purchaseStorageDetailVo = null;
        if(purchaseStorageDetail !=null){
            purchaseStorageDetailVo = dozerMapper.map(purchaseStorageDetail,PurchaseStorageDetailVo.class);
        }
        return purchaseStorageDetailVo;
    }

    /**
    * 保存实体
    * @param purchaseStorageDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseStorageDetailDto purchaseStorageDetailDto) {
        PurchaseStorageDetail purchaseStorageDetail = dozerMapper.map(purchaseStorageDetailDto,PurchaseStorageDetail.class);
        return super.save(purchaseStorageDetail);
    }

    /**
     * 保存集合
     * @param purchaseStorageDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseStorageDetailDto> purchaseStorageDetailDtos){
        List<PurchaseStorageDetail> PurchaseStorageDetails = DozerUtils.mapList(dozerMapper, purchaseStorageDetailDtos, PurchaseStorageDetail.class);
        return super.saveBatch(PurchaseStorageDetails);
    }

    /**
     * 修改实体
     * @param purchaseStorageDetailDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PurchaseStorageDetailDto purchaseStorageDetailDto) {
        purchaseStorageDetailDto.setUpdateTime(LocalDateTime.now());
        PurchaseStorageDetail purchaseStorageDetail = dozerMapper.map(purchaseStorageDetailDto,PurchaseStorageDetail.class);
        return super.updateById(purchaseStorageDetail);
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
    public Boolean deleteByStorageId(Long id){
        LambdaQueryWrapper<PurchaseStorageDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseStorageDetail::getStorageId,id);
        List<PurchaseStorageDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param storageId
     * @return
     */
    @Override
    public List<PurchaseStorageDetailVo> getByStorageId(Long storageId){
        LambdaQueryWrapper<PurchaseStorageDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(storageId),PurchaseStorageDetail::getStorageId,storageId);
        List<PurchaseStorageDetail> list = this.list(queryWrapper);
        List<PurchaseStorageDetailVo> purchaseStorageDetailVos = DozerUtils.mapList(dozerMapper, list, PurchaseStorageDetailVo.class);
        return purchaseStorageDetailVos;
    }
}
