package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.biz.asset.dto.WarehouseDto;
import com.netwisd.biz.asset.entity.Shelf;
import com.netwisd.biz.asset.entity.Warehouse;
import com.netwisd.biz.asset.mapper.ShelfMapper;
import com.netwisd.biz.asset.mapper.WarehouseMapper;
import com.netwisd.biz.asset.service.WarehouseService;
import com.netwisd.biz.asset.vo.ShelfVo;
import com.netwisd.biz.asset.vo.WarehouseVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 仓库 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-22 10:19:44
 */
@Service
@Slf4j
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private ShelfMapper shelfMapper;

    /**
    * 单表简单查询操作
    * @param warehouseDto
    * @return
    */
    @Override
    public Page list(WarehouseDto warehouseDto) {
        LambdaQueryWrapper<Warehouse> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(warehouseDto.getDeptId()),Warehouse::getDeptId,warehouseDto.getDeptId())
                .and(wrapper -> wrapper.eq(Warehouse::getDelFlag,YesNo.NO.getCode().toString()).or().isNull(Warehouse::getDelFlag))
//                    .eq(Warehouse::getDelFlag,YesNo.NO.getCode().toString()).or(wrapper -> wrapper.isNull(Warehouse::getDelFlag))
                .or()
                .likeRight(ObjectUtils.isNotEmpty(warehouseDto.getOrgId()),Warehouse::getOrgFullId,warehouseDto.getOrgId())
                .eq(ObjectUtils.isNotEmpty(warehouseDto.getWarehouseName()),Warehouse::getWarehouseName,warehouseDto.getWarehouseName())
                .eq(ObjectUtils.isNotEmpty(warehouseDto.getRespondUserId()),Warehouse::getRespondUserId,warehouseDto.getRespondUserId())
                .eq(ObjectUtils.isNotEmpty(warehouseDto.getHouseType()),Warehouse::getHouseType,warehouseDto.getHouseType())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getAddress()),Warehouse::getAddress,warehouseDto.getAddress())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getRemark()),Warehouse::getRemark,warehouseDto.getRemark())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getRespondUserName()),Warehouse::getRespondUserName,warehouseDto.getRespondUserName())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getDeptName()),Warehouse::getDeptName,warehouseDto.getDeptName())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getOrgName()),Warehouse::getOrgName,warehouseDto.getOrgName())
                .orderByDesc(Warehouse::getCreateTime);
        Page<Warehouse> page = warehouseMapper.selectPage(warehouseDto.getPage(),queryWrapper);
        Page<WarehouseVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WarehouseVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param warehouseDto
    * @return
    */
    @Override
    public List<WarehouseVo> lists(WarehouseDto warehouseDto) {
        LambdaQueryWrapper<Warehouse> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(warehouseDto.getDeptId()),Warehouse::getDeptId,warehouseDto.getDeptId())
                .or()
                .likeRight(ObjectUtils.isNotEmpty(warehouseDto.getOrgId()),Warehouse::getOrgFullId,warehouseDto.getOrgId())
                .eq(ObjectUtils.isNotEmpty(warehouseDto.getWarehouseName()),Warehouse::getWarehouseName,warehouseDto.getWarehouseName())
                .eq(ObjectUtils.isNotEmpty(warehouseDto.getRespondUserId()),Warehouse::getRespondUserId,warehouseDto.getRespondUserId())
                .eq(ObjectUtils.isNotEmpty(warehouseDto.getHouseType()),Warehouse::getHouseType,warehouseDto.getHouseType())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getAddress()),Warehouse::getAddress,warehouseDto.getAddress())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getRemark()),Warehouse::getRemark,warehouseDto.getRemark())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getRespondUserName()),Warehouse::getRespondUserName,warehouseDto.getRespondUserName())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getDeptName()),Warehouse::getDeptName,warehouseDto.getDeptName())
                .like(ObjectUtils.isNotEmpty(warehouseDto.getOrgName()),Warehouse::getOrgName,warehouseDto.getOrgName())
                .orderByDesc(Warehouse::getCreateTime);
        List<Warehouse> list = super.list(queryWrapper);
        log.debug("查询条数:"+list.size());
        return DozerUtils.mapList(dozerMapper,list,WarehouseVo.class);
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WarehouseVo get(Long id) {
        Warehouse warehouse = super.getById(id);
        WarehouseVo warehouseVo = null;
        if(warehouse !=null){
            warehouseVo = dozerMapper.map(warehouse,WarehouseVo.class);
        }
        List<Shelf> shelfList=shelfMapper.selectList(Wrappers.<Shelf>lambdaQuery().eq(Shelf::getWarehouseId,id));
        warehouseVo.setShelfList(DozerUtils.mapList(dozerMapper,shelfList, ShelfVo.class));
        log.debug("查询成功");
        return warehouseVo;
    }

    /**
    * 保存实体
    * @param warehouseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WarehouseDto warehouseDto) {
        Warehouse warehouse = dozerMapper.map(warehouseDto,Warehouse.class);
        boolean result = super.save(warehouse);
        List<Shelf> shelfList=DozerUtils.mapList(dozerMapper,warehouseDto.getShelfList(),Shelf.class);
        for (Shelf shelf:shelfList) {
            shelf.setWarehouseId(warehouse.getId());
            shelf.setWarehouseName(warehouse.getWarehouseName());
            shelfMapper.insert(shelf);
        }
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param warehouseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WarehouseDto warehouseDto) {
        warehouseDto.setUpdateTime(LocalDateTime.now());
        Warehouse warehouse = dozerMapper.map(warehouseDto,Warehouse.class);
        shelfMapper.delete(Wrappers.<Shelf>lambdaQuery().eq(Shelf::getWarehouseId,warehouseDto.getId()));
        boolean result = super.updateById(warehouse);
        List<Shelf> shelfList=DozerUtils.mapList(dozerMapper,warehouseDto.getShelfList(),Shelf.class);
        for (Shelf shelf:shelfList) {
            shelf.setWarehouseId(warehouse.getId());
            shelf.setWarehouseName(warehouse.getWarehouseName());
            shelfMapper.insert(shelf);
        }
        if(result){
            log.debug("修改成功");
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
        Warehouse warehouse = new Warehouse();
        warehouse.setId(id);
        warehouse.setDelFlag(YesNo.YES.code.toString());
        boolean result = super.updateById(warehouse);
        shelfMapper.delete(Wrappers.<Shelf>lambdaQuery().eq(Shelf::getWarehouseId,id));
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
