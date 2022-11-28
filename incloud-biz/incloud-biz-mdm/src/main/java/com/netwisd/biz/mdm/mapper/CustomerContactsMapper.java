package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.CustomerContacts;
import com.netwisd.biz.mdm.dto.CustomerContactsDto;
import com.netwisd.biz.mdm.vo.CustomerContactsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 客户联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:07:37
 */
@Mapper
public interface CustomerContactsMapper extends BaseMapper<CustomerContacts> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param customerContactsDto
     * @return
     */
    Page<CustomerContactsVo> getPageList(Page page, @Param("customerContactsDto") CustomerContactsDto customerContactsDto);
}
