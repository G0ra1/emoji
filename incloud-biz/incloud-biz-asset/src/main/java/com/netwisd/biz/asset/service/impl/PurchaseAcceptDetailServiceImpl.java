package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.PurchaseAcceptDetailDto;
import com.netwisd.biz.asset.entity.PurchaseAcceptDetail;
import com.netwisd.biz.asset.mapper.PurchaseAcceptDetailMapper;
import com.netwisd.biz.asset.service.PurchaseAcceptDetailService;
import com.netwisd.biz.asset.vo.PurchaseAcceptDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 验收资产明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
@Service
@Slf4j
public class PurchaseAcceptDetailServiceImpl extends BatchServiceImpl<PurchaseAcceptDetailMapper, PurchaseAcceptDetail> implements PurchaseAcceptDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseAcceptDetailMapper purchaseAcceptDetailMapper;

    /**
    * 单表简单查询操作
    * @param purchaseAcceptDetailDto
    * @return
    */
    @Override
    public Page list(PurchaseAcceptDetailDto purchaseAcceptDetailDto) {
        LambdaQueryWrapper<PurchaseAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<PurchaseAcceptDetail> page = purchaseAcceptDetailMapper.selectPage(purchaseAcceptDetailDto.getPage(),queryWrapper);
        Page<PurchaseAcceptDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseAcceptDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseAcceptDetailDto
    * @return
    */
    @Override
    public Page lists(PurchaseAcceptDetailDto purchaseAcceptDetailDto) {
        Page<PurchaseAcceptDetailVo> pageVo = purchaseAcceptDetailMapper.getPageList(purchaseAcceptDetailDto.getPage(),purchaseAcceptDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseAcceptDetailVo get(Long id) {
        PurchaseAcceptDetail purchaseAcceptDetail = super.getById(id);
        PurchaseAcceptDetailVo purchaseAcceptDetailVo = null;
        if(purchaseAcceptDetail !=null){
            purchaseAcceptDetailVo = dozerMapper.map(purchaseAcceptDetail,PurchaseAcceptDetailVo.class);
        }
        return purchaseAcceptDetailVo;
    }

    /**
    * 保存实体
    * @param purchaseAcceptDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseAcceptDetailDto purchaseAcceptDetailDto) {
        PurchaseAcceptDetail purchaseAcceptDetail = dozerMapper.map(purchaseAcceptDetailDto,PurchaseAcceptDetail.class);
        return super.save(purchaseAcceptDetail);
    }

    /**
     * 保存集合
     * @param purchaseAcceptDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseAcceptDetailDto> purchaseAcceptDetailDtos){
        List<PurchaseAcceptDetail> PurchaseAcceptDetails = DozerUtils.mapList(dozerMapper, purchaseAcceptDetailDtos, PurchaseAcceptDetail.class);
        return super.saveBatch(PurchaseAcceptDetails);
    }


    /**
     * 修改实体
     * @param purchaseAcceptDetailDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PurchaseAcceptDetailDto purchaseAcceptDetailDto) {
        purchaseAcceptDetailDto.setUpdateTime(LocalDateTime.now());
        PurchaseAcceptDetail purchaseAcceptDetail = dozerMapper.map(purchaseAcceptDetailDto,PurchaseAcceptDetail.class);
        return super.updateById(purchaseAcceptDetail);
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
        LambdaQueryWrapper<PurchaseAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseAcceptDetail::getAcceptanceId,id);
        List<PurchaseAcceptDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<PurchaseAcceptDetailVo> getByAcceptanceId(Long id){
        LambdaQueryWrapper<PurchaseAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseAcceptDetail::getAcceptanceId,id);
        List<PurchaseAcceptDetail> list = this.list(queryWrapper);
        List<PurchaseAcceptDetailVo> purchaseAcceptDetailVos = DozerUtils.mapList(dozerMapper, list, PurchaseAcceptDetailVo.class);
        return purchaseAcceptDetailVos;
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public Boolean deleteByAcceptanceAssetsId(Long id){
        LambdaQueryWrapper<PurchaseAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseAcceptDetail::getAcceptanceAssetsId,id);
        List<PurchaseAcceptDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<PurchaseAcceptDetailVo> getByAcceptanceAssetsId(Long id){
        LambdaQueryWrapper<PurchaseAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseAcceptDetail::getAcceptanceAssetsId,id);
        List<PurchaseAcceptDetail> list = this.list(queryWrapper);
        List<PurchaseAcceptDetailVo> purchaseAcceptDetailVos = DozerUtils.mapList(dozerMapper, list, PurchaseAcceptDetailVo.class);
        return purchaseAcceptDetailVos;
    }
}
