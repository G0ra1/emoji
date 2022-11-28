package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.PurchaseRegisterResultDto;
import com.netwisd.biz.asset.entity.PurchaseRegisterResult;
import com.netwisd.biz.asset.mapper.PurchaseRegisterResultMapper;
import com.netwisd.biz.asset.service.PurchaseRegisterResultService;
import com.netwisd.biz.asset.vo.PurchaseRegisterResultVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description 采购登记结果表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:54:08
 */
@Service
@Slf4j
public class PurchaseRegisterResultServiceImpl extends ServiceImpl<PurchaseRegisterResultMapper, PurchaseRegisterResult> implements PurchaseRegisterResultService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseRegisterResultMapper purchaseRegisterResultMapper;

    /**
    * 单表简单查询操作
    * @param purchaseRegisterResultDto
    * @return
    */
    @Override
    public Page list(PurchaseRegisterResultDto purchaseRegisterResultDto) {
        LambdaQueryWrapper<PurchaseRegisterResult> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PurchaseRegisterResult> page = purchaseRegisterResultMapper.selectPage(purchaseRegisterResultDto.getPage(),queryWrapper);
        Page<PurchaseRegisterResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseRegisterResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseRegisterResultDto
    * @return
    */
    @Override
    public Page lists(PurchaseRegisterResultDto purchaseRegisterResultDto) {
        Page<PurchaseRegisterResultVo> pageVo = purchaseRegisterResultMapper.getPageList(purchaseRegisterResultDto.getPage(),purchaseRegisterResultDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseRegisterResultVo get(Long id) {
        PurchaseRegisterResult purchaseRegisterResult = super.getById(id);
        PurchaseRegisterResultVo purchaseRegisterResultVo = null;
        if(purchaseRegisterResult !=null){
            purchaseRegisterResultVo = dozerMapper.map(purchaseRegisterResult,PurchaseRegisterResultVo.class);
        }
        log.debug("查询成功");
        return purchaseRegisterResultVo;
    }

    /**
    * 保存实体
    * @param purchaseRegisterResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseRegisterResultDto purchaseRegisterResultDto) {
        PurchaseRegisterResult purchaseRegisterResult = dozerMapper.map(purchaseRegisterResultDto,PurchaseRegisterResult.class);
        boolean result = super.save(purchaseRegisterResult);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param purchaseRegisterResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PurchaseRegisterResultDto purchaseRegisterResultDto) {
        purchaseRegisterResultDto.setUpdateTime(LocalDateTime.now());
        PurchaseRegisterResult purchaseRegisterResult = dozerMapper.map(purchaseRegisterResultDto,PurchaseRegisterResult.class);
        boolean result = super.updateById(purchaseRegisterResult);
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

    @Override
    public Page getAcceptList(PurchaseRegisterResultDto purchaseRegisterResultDto) {
        LambdaQueryWrapper<PurchaseRegisterResult> queryWrapper = new LambdaQueryWrapper<>();
//        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//        if(!ObjectUtil.isEmpty(loginAppUser)){
//            queryWrapper.eq((!ObjectUtil.isEmpty(loginAppUser.getParentOrgId())),PurchaseRegisterResult::getRegisterNumber , BigDecimal.ZERO);
//        }
        queryWrapper.gt(PurchaseRegisterResult::getRegisterNumber , BigDecimal.ZERO);
        queryWrapper.gt(PurchaseRegisterResult::getNotAcceptanceNumber , BigDecimal.ZERO);
        Page<PurchaseRegisterResult> page = purchaseRegisterResultMapper.selectPage(purchaseRegisterResultDto.getPage(),queryWrapper);
        Page<PurchaseRegisterResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseRegisterResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page getRegResultByIds(String registerIds) {
        String[] ids = registerIds.split(",");
        Page<PurchaseRegisterResult> page = super.page(new PurchaseRegisterResultDto().page,Wrappers.<PurchaseRegisterResult>lambdaQuery().in(PurchaseRegisterResult::getRegisterId, ids));
        Page<PurchaseRegisterResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseRegisterResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page getRegResultList(PurchaseRegisterResultDto registerResultDto) {
        LambdaQueryWrapper<PurchaseRegisterResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .gt(PurchaseRegisterResult::getNotAcceptanceNumber,BigDecimal.ZERO)
                .eq(PurchaseRegisterResult::getRegisterId,registerResultDto.getRegisterId());

        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(registerResultDto.getApplyUserOrgName()),PurchaseRegisterResult::getApplyUserOrgName,registerResultDto.getApplyUserOrgName())
                .eq(ObjectUtils.isNotEmpty(registerResultDto.getApplyUserDeptName()),PurchaseRegisterResult::getApplyUserDeptName,registerResultDto.getApplyUserDeptName())
                .eq(ObjectUtils.isNotEmpty(registerResultDto.getApplyUserName()),PurchaseRegisterResult::getApplyUserName,registerResultDto.getApplyUserName());

        //指定的查询字段
        String  searchCondition= registerResultDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtils.isNotEmpty(searchCondition)){
            queryWrapper
                    .like(PurchaseRegisterResult::getApplyUserName,searchCondition)
                    .or()
                    .like(PurchaseRegisterResult::getApplyUserDeptName,searchCondition)
                    .or()
                    .like(PurchaseRegisterResult::getApplyUserOrgName,searchCondition)
                    .or()
                    .like(PurchaseRegisterResult::getApplyTime,searchCondition);
        }
        Page<PurchaseRegisterResult> page = purchaseRegisterResultMapper.selectPage(registerResultDto.page,queryWrapper);
        Page<PurchaseRegisterResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseRegisterResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }
}
