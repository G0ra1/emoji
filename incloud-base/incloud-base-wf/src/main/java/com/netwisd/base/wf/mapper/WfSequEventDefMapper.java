package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfSequEventDef;
import com.netwisd.base.wf.dto.WfSequEventDefDto;
import com.netwisd.base.wf.vo.WfSequEventDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义-序列流-事件 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:55:36
 */
@Mapper
public interface WfSequEventDefMapper extends BaseMapper<WfSequEventDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfSequEventDefDto
     * @return
     */
    Page<WfSequEventDefVo> getPageList(Page page, @Param("wfSequEventDefDto") WfSequEventDefDto wfSequEventDefDto);
}
