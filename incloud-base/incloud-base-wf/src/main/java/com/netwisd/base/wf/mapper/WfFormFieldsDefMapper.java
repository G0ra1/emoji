package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfFormFieldsDef;
import com.netwisd.base.wf.dto.WfFormFieldsDefDto;
import com.netwisd.base.wf.vo.WfFormFieldsDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程表单字段定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:43:44
 */
@Mapper
public interface WfFormFieldsDefMapper extends BaseMapper<WfFormFieldsDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfFormFieldsDefDto
     * @return
     */
    Page<WfFormFieldsDefVo> getPageList(Page page, @Param("wfFormFieldsDefDto") WfFormFieldsDefDto wfFormFieldsDefDto);
}
