package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfVarDef;
import com.netwisd.base.wf.dto.WfVarDefDto;
import com.netwisd.base.wf.vo.WfVarDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义-变量 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 12:56:28
 */
@Mapper
public interface WfVarDefMapper extends BaseMapper<WfVarDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfVarDefDto
     * @return
     */
    Page<WfVarDefVo> getPageList(Page page, @Param("wfVarDefDto") WfVarDefDto wfVarDefDto);
}
