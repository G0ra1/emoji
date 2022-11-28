package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.SupplierBank;
import com.netwisd.biz.mdm.mapper.SupplierBankMapper;
import com.netwisd.biz.mdm.service.SupplierBankService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.SupplierBankDto;
import com.netwisd.biz.mdm.vo.SupplierBankVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 供应商银行账号 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:03:18
 */
@Service
@Slf4j
public class SupplierBankServiceImpl extends ServiceImpl<SupplierBankMapper, SupplierBank> implements SupplierBankService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private SupplierBankMapper supplierBankMapper;

    /**
    * 单表简单查询操作
    * @param supplierBankDto
    * @return
    */
    @Override
    public Page list(SupplierBankDto supplierBankDto) {
        SupplierBank supplierBank = dozerMapper.map(supplierBankDto,SupplierBank.class);
        LambdaQueryWrapper<SupplierBank> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<SupplierBank> page = supplierBankMapper.selectPage(supplierBankDto.getPage(),queryWrapper);
        Page<SupplierBankVo> pageVo = DozerUtils.mapPage(dozerMapper, page, SupplierBankVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param supplierBankDto
    * @return
    */
    @Override
    public Page lists(SupplierBankDto supplierBankDto) {
        Page<SupplierBankVo> pageVo = supplierBankMapper.getPageList(supplierBankDto.getPage(),supplierBankDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public SupplierBankVo get(Long id) {
        SupplierBank supplierBank = super.getById(id);
        SupplierBankVo supplierBankVo = null;
        if(supplierBank !=null){
            supplierBankVo = dozerMapper.map(supplierBank,SupplierBankVo.class);
        }
        log.debug("查询成功");
        return supplierBankVo;
    }

    /**
    * 保存实体
    * @param supplierBankDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(SupplierBankDto supplierBankDto) {
        SupplierBank supplierBank = dozerMapper.map(supplierBankDto,SupplierBank.class);
        boolean result = super.save(supplierBank);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param supplierBankDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(SupplierBankDto supplierBankDto) {
        SupplierBank supplierBank = dozerMapper.map(supplierBankDto,SupplierBank.class);
        boolean result = super.updateById(supplierBank);
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
}
