package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfExpreSequParamDef;
import com.netwisd.base.wf.dto.WfExpreSequParamDefDto;
import com.netwisd.base.wf.vo.WfExpreSequParamDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义-序列流-表达式-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 19:00:23
 */
@Mapper
public interface WfExpreSequParamDefMapper extends BaseMapper<WfExpreSequParamDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfExpreSequParamDefDto
     * @return
     */
    Page<WfExpreSequParamDefVo> getPageList(Page page, @Param("wfExpreSequParamDefDto") WfExpreSequParamDefDto wfExpreSequParamDefDto);
}
