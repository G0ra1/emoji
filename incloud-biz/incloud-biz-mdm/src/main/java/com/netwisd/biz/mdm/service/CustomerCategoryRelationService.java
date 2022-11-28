package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.CustomerCategoryRelation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.CustomerCategoryRelationDto;
import com.netwisd.biz.mdm.vo.CustomerCategoryRelationVo;
/**
 * @Description 客户-客户类别关系表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-10-22 10:24:07
 */
public interface CustomerCategoryRelationService extends IService<CustomerCategoryRelation> {
    Page list(CustomerCategoryRelationDto customerCategoryRelationDto);
    Page lists(CustomerCategoryRelationDto customerCategoryRelationDto);
    CustomerCategoryRelationVo get(Long id);
    Boolean save(CustomerCategoryRelationDto customerCategoryRelationDto);
    Boolean update(CustomerCategoryRelationDto customerCategoryRelationDto);
    Boolean delete(Long id);
}
