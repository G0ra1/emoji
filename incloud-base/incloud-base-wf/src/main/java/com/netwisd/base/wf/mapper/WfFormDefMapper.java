package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfFormDef;
import com.netwisd.base.wf.dto.WfFormDefDto;
import com.netwisd.base.wf.vo.WfFormDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程表单定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:31:54
 */
@Mapper
public interface WfFormDefMapper extends BaseMapper<WfFormDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfFormDefDto
     * @return
     */
    Page<WfFormDefVo> getPageList(Page page, @Param("wfFormDefDto") WfFormDefDto wfFormDefDto);
}
