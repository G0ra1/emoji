package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.SupplierContacts;
import com.netwisd.biz.mdm.mapper.SupplierContactsMapper;
import com.netwisd.biz.mdm.service.SupplierContactsService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.SupplierContactsDto;
import com.netwisd.biz.mdm.vo.SupplierContactsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 供应商联系人 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:05:59
 */
@Service
@Slf4j
public class SupplierContactsServiceImpl extends ServiceImpl<SupplierContactsMapper, SupplierContacts> implements SupplierContactsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private SupplierContactsMapper supplierContactsMapper;

    /**
    * 单表简单查询操作
    * @param supplierContactsDto
    * @return
    */
    @Override
    public Page list(SupplierContactsDto supplierContactsDto) {
        SupplierContacts supplierContacts = dozerMapper.map(supplierContactsDto,SupplierContacts.class);
        LambdaQueryWrapper<SupplierContacts> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<SupplierContacts> page = supplierContactsMapper.selectPage(supplierContactsDto.getPage(),queryWrapper);
        Page<SupplierContactsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, SupplierContactsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param supplierContactsDto
    * @return
    */
    @Override
    public Page lists(SupplierContactsDto supplierContactsDto) {
        Page<SupplierContactsVo> pageVo = supplierContactsMapper.getPageList(supplierContactsDto.getPage(),supplierContactsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public SupplierContactsVo get(Long id) {
        SupplierContacts supplierContacts = super.getById(id);
        SupplierContactsVo supplierContactsVo = null;
        if(supplierContacts !=null){
            supplierContactsVo = dozerMapper.map(supplierContacts,SupplierContactsVo.class);
        }
        log.debug("查询成功");
        return supplierContactsVo;
    }

    /**
    * 保存实体
    * @param supplierContactsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(SupplierContactsDto supplierContactsDto) {
        SupplierContacts supplierContacts = dozerMapper.map(supplierContactsDto,SupplierContacts.class);
        boolean result = super.save(supplierContacts);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param supplierContactsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(SupplierContactsDto supplierContactsDto) {
        SupplierContacts supplierContacts = dozerMapper.map(supplierContactsDto,SupplierContacts.class);
        boolean result = super.updateById(supplierContacts);
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
