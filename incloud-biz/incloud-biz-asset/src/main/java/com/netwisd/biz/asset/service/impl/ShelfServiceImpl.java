package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.ShelfDto;
import com.netwisd.biz.asset.entity.Shelf;
import com.netwisd.biz.asset.mapper.ShelfMapper;
import com.netwisd.biz.asset.service.ShelfService;
import com.netwisd.biz.asset.vo.ShelfVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 货架号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-18 10:17:57
 */
@Service
@Slf4j
public class ShelfServiceImpl extends ServiceImpl<ShelfMapper, Shelf> implements ShelfService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ShelfMapper shelfMapper;

    /**
    * 单表简单查询操作
    * @param shelfDto
    * @return
    */
    @Override
    public Page list(ShelfDto shelfDto) {
        LambdaQueryWrapper<Shelf> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(shelfDto.getWarehouseId()), Shelf::getWarehouseId,shelfDto.getWarehouseId());

        Page<Shelf> page = shelfMapper.selectPage(shelfDto.getPage(),queryWrapper);
        Page<ShelfVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ShelfVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public List<ShelfVo> lists(ShelfDto shelfDto) {
        LambdaQueryWrapper<Shelf> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(shelfDto.getWarehouseId()), Shelf::getWarehouseId,shelfDto.getWarehouseId())
        .like(ObjectUtils.isNotEmpty(shelfDto.getWarehouseName()), Shelf::getWarehouseName,shelfDto.getWarehouseName())
        .like(ObjectUtils.isNotEmpty(shelfDto.getShelfName()),Shelf::getShelfName,shelfDto.getShelfName());
        List<Shelf> list=super.list(queryWrapper);
        return DozerUtils.mapList(dozerMapper,list,ShelfVo.class);
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ShelfVo get(Long id) {
        Shelf shelf = super.getById(id);
        ShelfVo shelfVo = null;
        if(shelf !=null){
            shelfVo = dozerMapper.map(shelf,ShelfVo.class);
        }
        log.debug("查询成功");
        return shelfVo;
    }

    /**
    * 保存实体
    * @param shelfDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ShelfDto shelfDto) {
        Shelf shelf = dozerMapper.map(shelfDto,Shelf.class);
        boolean result = super.save(shelf);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param shelfDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ShelfDto shelfDto) {
        shelfDto.setUpdateTime(LocalDateTime.now());
        Shelf shelf = dozerMapper.map(shelfDto,Shelf.class);
        boolean result = super.updateById(shelf);
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
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
    /**
     * 批量新增，删除原有的
     * @param shelfDtoList
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<ShelfDto> shelfDtoList){
        boolean result = true;
        if(CollectionUtils.isNotEmpty(shelfDtoList)){
            //super.remove(Wrappers.<Shelf>lambdaQuery().eq(Shelf::getWarehouseId,shelfDtoList.get(0).getWarehouseId()));
            List<Shelf> list=DozerUtils.mapList(dozerMapper,shelfDtoList,Shelf.class);
            for (Shelf shelf:list) {
                if(shelf.getId()!=null){
                    super.save(shelf);
                }else {
                    super.updateById(shelf);
                }
            }
            //result = this.saveBatch(list);
            if(result){
                log.debug("保存成功");
            }
        }
        return result;
    }

    @Override
    public Boolean updateList(List<ShelfDto> shelfDtoList) {
        boolean result = true;
        if(CollectionUtils.isNotEmpty(shelfDtoList)){
            super.remove(Wrappers.<Shelf>lambdaQuery().eq(Shelf::getWarehouseId,shelfDtoList.get(0).getWarehouseId()));
            List<Shelf> list=DozerUtils.mapList(dozerMapper,shelfDtoList,Shelf.class);
            result = this.saveBatch(list);
            if(result){
                log.debug("保存成功");
            }
        }
        return result;
    }
}
