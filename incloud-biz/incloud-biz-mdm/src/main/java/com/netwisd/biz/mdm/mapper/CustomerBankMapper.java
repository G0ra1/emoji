package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.CustomerBank;
import com.netwisd.biz.mdm.dto.CustomerBankDto;
import com.netwisd.biz.mdm.vo.CustomerBankVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 客户银行账号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:06:29
 */
@Mapper
public interface CustomerBankMapper extends BaseMapper<CustomerBank> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param customerBankDto
     * @return
     */
    Page<CustomerBankVo> getPageList(Page page, @Param("customerBankDto") CustomerBankDto customerBankDto);
}
