package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.constants.ItemTypeEnum;
import com.netwisd.biz.asset.dto.PurchaseRegisterAssetsDto;
import com.netwisd.biz.asset.dto.PurchaseRegisterDetailDto;
import com.netwisd.biz.asset.entity.PurchaseRegisterDetail;
import com.netwisd.biz.asset.mapper.PurchaseRegisterDetailMapper;
import com.netwisd.biz.asset.service.PurchaseRegisterDetailService;
import com.netwisd.biz.asset.vo.PurchaseRegisterDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 采购信息登记详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-09 09:41:26
 */
@Service
@Slf4j
public class PurchaseRegisterDetailServiceImpl extends BatchServiceImpl<PurchaseRegisterDetailMapper, PurchaseRegisterDetail> implements PurchaseRegisterDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseRegisterDetailMapper purchaseRegisterDetailMapper;

    /**
    * 单表简单查询操作
    * @param purchaseRegisterDetailDto
    * @return
    */
    @Override
    public Page list(PurchaseRegisterDetailDto purchaseRegisterDetailDto) {
        LambdaQueryWrapper<PurchaseRegisterDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<PurchaseRegisterDetail> page = purchaseRegisterDetailMapper.selectPage(purchaseRegisterDetailDto.getPage(),queryWrapper);
        Page<PurchaseRegisterDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseRegisterDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseRegisterDetailDto
    * @return
    */
    @Override
    public Page lists(PurchaseRegisterDetailDto purchaseRegisterDetailDto) {
        Page<PurchaseRegisterDetailVo> pageVo = purchaseRegisterDetailMapper.getPageList(purchaseRegisterDetailDto.getPage(),purchaseRegisterDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseRegisterDetailVo get(Long id) {
        PurchaseRegisterDetail purchaseRegisterDetail = super.getById(id);
        PurchaseRegisterDetailVo purchaseRegisterDetailVo = null;
        if(purchaseRegisterDetail !=null){
            purchaseRegisterDetailVo = dozerMapper.map(purchaseRegisterDetail,PurchaseRegisterDetailVo.class);
        }
        return purchaseRegisterDetailVo;
    }

    /**
    * 保存实体
    * @param purchaseRegisterDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseRegisterDetailDto purchaseRegisterDetailDto) {
        PurchaseRegisterDetail purchaseRegisterDetail = dozerMapper.map(purchaseRegisterDetailDto,PurchaseRegisterDetail.class);
        return super.save(purchaseRegisterDetail);
    }

    /**
     * 保存集合
     * @param purchaseRegisterDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseRegisterDetailDto> purchaseRegisterDetailDtos){
        List<PurchaseRegisterDetail> PurchaseRegisterDetails = DozerUtils.mapList(dozerMapper, purchaseRegisterDetailDtos, PurchaseRegisterDetail.class);
        return super.saveBatch(PurchaseRegisterDetails);
    }


    /**
     * 修改实体
     * @param purchaseRegisterDetailDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PurchaseRegisterDetailDto purchaseRegisterDetailDto) {
        purchaseRegisterDetailDto.setUpdateTime(LocalDateTime.now());
        PurchaseRegisterDetail purchaseRegisterDetail = dozerMapper.map(purchaseRegisterDetailDto,PurchaseRegisterDetail.class);
        return super.updateById(purchaseRegisterDetail);
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
        LambdaQueryWrapper<PurchaseRegisterDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseRegisterDetail::getRegisterId,id);
        List<PurchaseRegisterDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteByRegisterAssetsId(Long id){
        LambdaQueryWrapper<PurchaseRegisterDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseRegisterDetail::getRegisterAssetsId,id);
        List<PurchaseRegisterDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<PurchaseRegisterDetailVo> getByRegisterId(Long id){
        LambdaQueryWrapper<PurchaseRegisterDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseRegisterDetail::getRegisterId,id);
        List<PurchaseRegisterDetail> list = this.list(queryWrapper);
        List<PurchaseRegisterDetailVo> purchaseRegisterDetailVos = DozerUtils.mapList(dozerMapper, list, PurchaseRegisterDetailVo.class);
        return purchaseRegisterDetailVos;
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<PurchaseRegisterDetailVo> getByRegisterAssetsId(Long id){
        LambdaQueryWrapper<PurchaseRegisterDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),PurchaseRegisterDetail::getRegisterAssetsId,id);
        List<PurchaseRegisterDetail> list = this.list(queryWrapper);
        List<PurchaseRegisterDetailVo> purchaseRegisterDetailVos = DozerUtils.mapList(dozerMapper, list, PurchaseRegisterDetailVo.class);
        return purchaseRegisterDetailVos;
    }

    @Override
    public List<PurchaseRegisterDetailVo> getByAssetsBuild(PurchaseRegisterAssetsDto purchaseRegisterAssetsDto) {
        List<PurchaseRegisterDetailVo> purchaseRegisterDetailVos = new ArrayList<>();
        if (ObjectUtil.isEmpty(purchaseRegisterAssetsDto)){
            String itemType = purchaseRegisterAssetsDto.getItemType();
            //库存或者资产，目前按台套数管理的
            if(ObjectUtil.isEmpty(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY) || itemType.equals(ItemTypeEnum.ASSET))){
                //获取填写的登记数量，通过登记数量生成详情数
                Integer registerNumber = purchaseRegisterAssetsDto.getRegisterNumber().intValue();
                for(int i = 0 ; i < registerNumber ; i ++){
                    PurchaseRegisterDetailVo purchaseRegisterDetailVo = dozerMapper.map(purchaseRegisterAssetsDto,PurchaseRegisterDetailVo.class);
                    purchaseRegisterDetailVo.setRegisterNumber(BigDecimal.ONE);
                    purchaseRegisterDetailVos.add(purchaseRegisterDetailVo);
                }
            }else{//低值易耗品或原材料
                PurchaseRegisterDetailVo purchaseRegisterDetailVo = dozerMapper.map(purchaseRegisterAssetsDto,PurchaseRegisterDetailVo.class);
                purchaseRegisterDetailVos.add(purchaseRegisterDetailVo);
            }
        }
        return purchaseRegisterDetailVos;
    }
}
