package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.CustomerCategoryRelation;
import com.netwisd.biz.mdm.dto.CustomerCategoryRelationDto;
import com.netwisd.biz.mdm.vo.CustomerCategoryRelationVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 客户-客户类别关系表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-10-22 10:24:07
 */
@Mapper
public interface CustomerCategoryRelationMapper extends BaseMapper<CustomerCategoryRelation> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param customerCategoryRelationDto
     * @return
     */
    Page<CustomerCategoryRelationVo> getPageList(Page page, @Param("customerCategoryRelationDto") CustomerCategoryRelationDto customerCategoryRelationDto);
}
