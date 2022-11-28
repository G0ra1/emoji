package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfExpreSequDef;
import com.netwisd.base.wf.dto.WfExpreSequDefDto;
import com.netwisd.base.wf.vo.WfExpreSequDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义-序列流-表达式 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 18:59:28
 */
@Mapper
public interface WfExpreSequDefMapper extends BaseMapper<WfExpreSequDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfExpreSequDefDto
     * @return
     */
    Page<WfExpreSequDefVo> getPageList(Page page, @Param("wfExpreSequDefDto") WfExpreSequDefDto wfExpreSequDefDto);
}
