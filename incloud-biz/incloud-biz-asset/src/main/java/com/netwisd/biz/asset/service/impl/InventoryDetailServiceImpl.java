package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.InventoryDetailDto;
import com.netwisd.biz.asset.entity.InventoryDetail;
import com.netwisd.biz.asset.mapper.InventoryDetailMapper;
import com.netwisd.biz.asset.service.InventoryDetailService;
import com.netwisd.biz.asset.vo.InventoryDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 盘点详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-21 09:55:49
 */
@Service
@Slf4j
public class InventoryDetailServiceImpl extends BatchServiceImpl<InventoryDetailMapper, InventoryDetail> implements InventoryDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private InventoryDetailMapper inventoryDetailMapper;

    /**
    * 单表简单查询操作
    * @param inventoryDetailDto
    * @return
    */
    @Override
    public Page list(InventoryDetailDto inventoryDetailDto) {
        LambdaQueryWrapper<InventoryDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<InventoryDetail> page = inventoryDetailMapper.selectPage(inventoryDetailDto.getPage(),queryWrapper);
        Page<InventoryDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, InventoryDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param inventoryDetailDto
    * @return
    */
    @Override
    public Page lists(InventoryDetailDto inventoryDetailDto) {
        Page<InventoryDetailVo> pageVo = inventoryDetailMapper.getPageList(inventoryDetailDto.getPage(),inventoryDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public InventoryDetailVo get(Long id) {
        InventoryDetail inventoryDetail = super.getById(id);
        InventoryDetailVo inventoryDetailVo = null;
        if(inventoryDetail !=null){
            inventoryDetailVo = dozerMapper.map(inventoryDetail,InventoryDetailVo.class);
        }
        return inventoryDetailVo;
    }

    /**
    * 保存实体
    * @param inventoryDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(InventoryDetailDto inventoryDetailDto) {
        InventoryDetail inventoryDetail = dozerMapper.map(inventoryDetailDto,InventoryDetail.class);
        super.save(inventoryDetail);
        saveSubList(inventoryDetailDto);
    }

    /**
     * 保存集合
     * @param inventoryDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<InventoryDetailDto> inventoryDetailDtos){
        List<InventoryDetail> InventoryDetails = DozerUtils.mapList(dozerMapper, inventoryDetailDtos, InventoryDetail.class);
        super.saveBatch(InventoryDetails);
        for (InventoryDetailDto inventoryDetailDto : inventoryDetailDtos){
            saveSubList(inventoryDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param inventoryDetailDto
     * @return
     */
    @Transactional
    void saveSubList(InventoryDetailDto inventoryDetailDto){
    }

    /**
     * 修改实体
     * @param inventoryDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(InventoryDetailDto inventoryDetailDto) {
        inventoryDetailDto.setUpdateTime(LocalDateTime.now());
        InventoryDetail inventoryDetail = dozerMapper.map(inventoryDetailDto,InventoryDetail.class);
        super.updateById(inventoryDetail);
        updateSub(inventoryDetailDto);
    }

    /**
    * 修改子类实体
    * @param inventoryDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(InventoryDetailDto inventoryDetailDto) {
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
        LambdaQueryWrapper<InventoryDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),InventoryDetail::getInventoryId,id);
        List<InventoryDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<InventoryDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<InventoryDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),InventoryDetail::getInventoryId,id);
        List<InventoryDetail> list = this.list(queryWrapper);
        List<InventoryDetailVo> inventoryDetailVos = DozerUtils.mapList(dozerMapper, list, InventoryDetailVo.class);
        return inventoryDetailVos;
    }
}
