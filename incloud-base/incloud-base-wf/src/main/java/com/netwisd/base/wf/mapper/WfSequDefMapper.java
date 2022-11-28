package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfSequDef;
import com.netwisd.base.wf.dto.WfSequDefDto;
import com.netwisd.base.wf.vo.WfSequDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义-序列流 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 16:21:33
 */
@Mapper
public interface WfSequDefMapper extends BaseMapper<WfSequDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfSequDefDto
     * @return
     */
    Page<WfSequDefVo> getPageList(Page page, @Param("wfSequDefDto") WfSequDefDto wfSequDefDto);
}
