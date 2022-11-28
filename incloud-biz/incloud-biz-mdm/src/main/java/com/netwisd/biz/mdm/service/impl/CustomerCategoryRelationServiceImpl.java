package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.CustomerCategoryRelation;
import com.netwisd.biz.mdm.mapper.CustomerCategoryRelationMapper;
import com.netwisd.biz.mdm.service.CustomerCategoryRelationService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.CustomerCategoryRelationDto;
import com.netwisd.biz.mdm.vo.CustomerCategoryRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 客户-客户类别关系表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-10-22 10:24:07
 */
@Service
@Slf4j
public class CustomerCategoryRelationServiceImpl extends ServiceImpl<CustomerCategoryRelationMapper, CustomerCategoryRelation> implements CustomerCategoryRelationService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private CustomerCategoryRelationMapper customerCategoryRelationMapper;

    /**
    * 单表简单查询操作
    * @param customerCategoryRelationDto
    * @return
    */
    @Override
    public Page list(CustomerCategoryRelationDto customerCategoryRelationDto) {
        LambdaQueryWrapper<CustomerCategoryRelation> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<CustomerCategoryRelation> page = customerCategoryRelationMapper.selectPage(customerCategoryRelationDto.getPage(),queryWrapper);
        Page<CustomerCategoryRelationVo> pageVo = DozerUtils.mapPage(dozerMapper, page, CustomerCategoryRelationVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param customerCategoryRelationDto
    * @return
    */
    @Override
    public Page lists(CustomerCategoryRelationDto customerCategoryRelationDto) {
        Page<CustomerCategoryRelationVo> pageVo = customerCategoryRelationMapper.getPageList(customerCategoryRelationDto.getPage(),customerCategoryRelationDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public CustomerCategoryRelationVo get(Long id) {
        CustomerCategoryRelation customerCategoryRelation = super.getById(id);
        CustomerCategoryRelationVo customerCategoryRelationVo = null;
        if(customerCategoryRelation !=null){
            customerCategoryRelationVo = dozerMapper.map(customerCategoryRelation,CustomerCategoryRelationVo.class);
        }
        log.debug("查询成功");
        return customerCategoryRelationVo;
    }

    /**
    * 保存实体
    * @param customerCategoryRelationDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(CustomerCategoryRelationDto customerCategoryRelationDto) {
        CustomerCategoryRelation customerCategoryRelation = dozerMapper.map(customerCategoryRelationDto,CustomerCategoryRelation.class);
        boolean result = super.save(customerCategoryRelation);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param customerCategoryRelationDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(CustomerCategoryRelationDto customerCategoryRelationDto) {
        customerCategoryRelationDto.setUpdateTime(LocalDateTime.now());
        CustomerCategoryRelation customerCategoryRelation = dozerMapper.map(customerCategoryRelationDto,CustomerCategoryRelation.class);
        boolean result = super.updateById(customerCategoryRelation);
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
