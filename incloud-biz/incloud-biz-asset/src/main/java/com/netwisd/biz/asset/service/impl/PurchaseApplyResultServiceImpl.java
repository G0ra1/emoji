package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.PurchaseApplyResultDto;
import com.netwisd.biz.asset.entity.PurchaseApplyResult;
import com.netwisd.biz.asset.mapper.PurchaseApplyResultMapper;
import com.netwisd.biz.asset.service.PurchaseApplyResultService;
import com.netwisd.biz.asset.vo.PurchaseApplyResultVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 购置申请结果表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-21 11:18:24
 */
@Service
@Slf4j
public class PurchaseApplyResultServiceImpl extends ServiceImpl<PurchaseApplyResultMapper, PurchaseApplyResult> implements PurchaseApplyResultService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseApplyResultMapper purchaseApplyResultMapper;

    /**
    * 单表简单查询操作
    * @param purchaseApplyResultDto
    * @return
    */
    @Override
    public Page list(PurchaseApplyResultDto purchaseApplyResultDto) {
        LambdaQueryWrapper<PurchaseApplyResult> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PurchaseApplyResult> page = purchaseApplyResultMapper.selectPage(purchaseApplyResultDto.getPage(),queryWrapper);
        Page<PurchaseApplyResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseApplyResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseApplyResultDto
    * @return
    */
    @Override
    public Page lists(PurchaseApplyResultDto purchaseApplyResultDto) {
        Page<PurchaseApplyResultVo> pageVo = purchaseApplyResultMapper.getPageList(purchaseApplyResultDto.getPage(),purchaseApplyResultDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseApplyResultVo get(Long id) {
        PurchaseApplyResult purchaseApplyResult = super.getById(id);
        PurchaseApplyResultVo purchaseApplyResultVo = null;
        if(purchaseApplyResult !=null){
            purchaseApplyResultVo = dozerMapper.map(purchaseApplyResult,PurchaseApplyResultVo.class);
        }
        log.debug("查询成功");
        return purchaseApplyResultVo;
    }

    /**
    * 保存实体
    * @param purchaseApplyResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseApplyResultDto purchaseApplyResultDto) {
        PurchaseApplyResult purchaseApplyResult = dozerMapper.map(purchaseApplyResultDto,PurchaseApplyResult.class);
        boolean result = super.save(purchaseApplyResult);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param purchaseApplyResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PurchaseApplyResultDto purchaseApplyResultDto) {
        purchaseApplyResultDto.setUpdateTime(LocalDateTime.now());
        PurchaseApplyResult purchaseApplyResult = dozerMapper.map(purchaseApplyResultDto,PurchaseApplyResult.class);
        boolean result = super.updateById(purchaseApplyResult);
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
     * 保存集合
     * @param purchaseApplyResultDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseApplyResultDto> purchaseApplyResultDtos){
        List<PurchaseApplyResult> purchaseApplyResults = DozerUtils.mapList(dozerMapper, purchaseApplyResultDtos, PurchaseApplyResult.class);
        return super.saveBatch(purchaseApplyResults);
    }
}
