package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.biz.asset.dto.InventoryDetailDto;
import com.netwisd.biz.asset.dto.InventoryDto;
import com.netwisd.biz.asset.entity.Inventory;
import com.netwisd.biz.asset.entity.InventoryDetail;
import com.netwisd.biz.asset.mapper.InventoryDetailMapper;
import com.netwisd.biz.asset.mapper.InventoryMapper;
import com.netwisd.biz.asset.service.InventoryDetailService;
import com.netwisd.biz.asset.service.InventoryService;
import com.netwisd.biz.asset.vo.InventoryDetailVo;
import com.netwisd.biz.asset.vo.InventoryVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 云数网讯 hansongpeng@netwisd.com
 * @Description 物资盘点 功能描述...
 * @date 2022-04-21 09:55:49
 */
@Service
@Slf4j
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private InventoryDetailMapper inventoryDetailMapper;

    @Autowired
    private InventoryDetailService inventoryDetailService;

    /**
     * 单表简单查询操作
     *
     * @param inventoryDto
     * @return
     */
    @Override
    public Page list(InventoryDto inventoryDto) {
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<Inventory> page = inventoryMapper.selectPage(inventoryDto.getPage(), queryWrapper);
        Page<InventoryVo> pageVo = DozerUtils.mapPage(dozerMapper, page, InventoryVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param inventoryDto
     * @return
     */
    @Override
    public Page lists(InventoryDto inventoryDto) {
        Page<InventoryVo> pageVo = inventoryMapper.getPageList(inventoryDto.getPage(), inventoryDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public InventoryVo get(Long id) {
        Inventory inventory = super.getById(id);
        InventoryVo inventoryVo = null;
        if (inventory != null) {
            inventoryVo = dozerMapper.map(inventory, InventoryVo.class);
        }
        List<InventoryDetail> details = inventoryDetailMapper.selectList(Wrappers.<InventoryDetail>lambdaQuery().eq(InventoryDetail::getInventoryId, id));
        List<InventoryDetailVo> detailVos = DozerUtils.mapList(dozerMapper, details, InventoryDetailVo.class);
        inventoryVo.setInventoryDetailList(detailVos);
        log.debug("查询成功");
        return inventoryVo;
    }

    /**
     * 保存实体
     *
     * @param inventoryDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(InventoryDto inventoryDto) {
        Inventory inventory = dozerMapper.map(inventoryDto, Inventory.class);
        List<InventoryDetailDto> detailDtos = inventoryDto.getInventoryDetailList();
        List<InventoryDetail> details = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(detailDtos)) {
            for (InventoryDetailDto inventoryDetailDto : detailDtos) {
                inventoryDetailDto.setInventoryId(inventory.getId());
                InventoryDetail inventoryDetail = dozerMapper.map(inventoryDetailDto, InventoryDetail.class);
                details.add(inventoryDetail);
            }
        }
        //获取盘点申请编号
        if (StringUtils.isBlank(inventory.getCode())){
            inventory.setCode(getMaxCode());
        }
        boolean result = super.save(inventory);
        if (result) {
            log.debug("保存成功");
            if (CollectionUtils.isNotEmpty(details)){
                boolean b = inventoryDetailService.saveBatch(details);
            }
        }
        return result;
    }
    /**
     * 获取盘点编号
     * @return
     */
    private String getMaxCode(){
        String maxCode = inventoryMapper.getMaxCode();
        String applyCode = "PDSQ-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"-";
        if(org.apache.commons.lang3.StringUtils.isBlank(maxCode)){
            applyCode += "0001";
        }else {
            DecimalFormat df = new DecimalFormat("0000");
            applyCode += df.format(Long.valueOf(maxCode)+1);
        }
        log.info("盘点申请编号===>{}",applyCode);
        return applyCode;
    }


    /**
     * 修改实体
     *
     * @param inventoryDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(InventoryDto inventoryDto) {
        inventoryDto.setUpdateTime(LocalDateTime.now());
        Inventory inventory = dozerMapper.map(inventoryDto, Inventory.class);
        //删除原子表
        inventoryDetailMapper.delete(Wrappers.<InventoryDetail>lambdaQuery().eq(InventoryDetail::getInventoryId, inventory.getId()));
        List<InventoryDetailDto> detailDtoList = inventoryDto.getInventoryDetailList();
        List<InventoryDetail> details = new ArrayList<>();
        //判断子表数据是否为空
        if (CollectionUtils.isNotEmpty(detailDtoList)){
            //新增子表
            for (InventoryDetailDto inventoryDetailDto : detailDtoList) {
                inventoryDetailDto.setInventoryId(inventory.getId());
                InventoryDetail inventoryDetail = dozerMapper.map(inventoryDetailDto,InventoryDetail.class);
                details.add(inventoryDetail);
            }
        }
        inventoryDetailService.saveBatch(details);
        boolean result = super.updateById(inventory);
        if (result) {
            log.debug("修改成功");
         }
        return result;
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        //删除原子表
        inventoryDetailMapper.delete(Wrappers.<InventoryDetail>lambdaQuery().eq(InventoryDetail::getInventoryId, id));
        boolean result = super.removeById(id);
        if (result) {
            log.debug("删除成功");
        }
        return result;
    }



    /**
     * 通过外建获取
     *
     * @param id
     * @return
     */
    @Override
    public List<InventoryVo> getByFkIdVo(Long id) {
        return null;
    }

    /**
     * 通过procInstId查
     *
     * @param procInstId
     * @return
     */
    @Override
    public InventoryVo procDetail(String procInstId) {
        LambdaQueryWrapper<Inventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inventory::getCamundaProcinsId,procInstId);
        Inventory inventory = inventoryMapper.selectOne(queryWrapper);
        InventoryVo inventoryVo = null;
        if(inventory != null){
            inventoryVo = dozerMapper.map(inventory,InventoryVo.class);
            //根据id获取InventoryDetailList
            List<InventoryDetailVo> InventoryDetailList = inventoryDetailService.getByFkIdVo(inventoryVo.getId());
            //设置inventoryVo,构建对应的子表
            inventoryVo.setInventoryDetailList(InventoryDetailList);
        }
        return inventoryVo;
    }

    /**
     * 流程新增或修改
     *
     * @param inventoryDto
     * @return
     */
    @Override
    public InventoryVo procSaveOrUpdate(InventoryDto inventoryDto) {
        Inventory inventory = dozerMapper.map(inventoryDto, Inventory.class);
        super.saveOrUpdate(inventory);
        if (CollectionUtils.isNotEmpty(inventoryDto.getInventoryDetailList())) {
            List<InventoryDetailDto> detailDtoList = inventoryDto.getInventoryDetailList();
            //替换盘点子表信息
            detailDtoList.forEach(detailDto -> {
                detailDto.setInventoryId(inventory.getId());
            });
            List<InventoryDetail> detailList = DozerUtils.mapList(dozerMapper, detailDtoList, InventoryDetail.class);
            inventoryDetailService.saveOrUpdateBatch(detailList);
        }
        return this.get(inventory.getId());
    }


}
