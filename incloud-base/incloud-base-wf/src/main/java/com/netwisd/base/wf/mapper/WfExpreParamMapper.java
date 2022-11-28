package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfExpreParam;
import com.netwisd.base.wf.dto.WfExpreParamDto;
import com.netwisd.base.wf.vo.WfExpreParamVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 表达式参数维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:23:36
 */
@Mapper
public interface WfExpreParamMapper extends BaseMapper<WfExpreParam> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfExpreParamDto
     * @return
     */
    Page<WfExpreParamVo> getPageList(Page page, @Param("wfExpreParamDto") WfExpreParamDto wfExpreParamDto);
}
