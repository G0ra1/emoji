package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.Customer;
import com.netwisd.biz.mdm.dto.CustomerDto;
import com.netwisd.biz.mdm.vo.CustomerVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 客户 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 16:45:51
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param customerDto
     * @return
     */
    Page<CustomerVo> getPageList(Page page, @Param("customerDto") CustomerDto customerDto);
    List<CustomerVo> outList();
}
