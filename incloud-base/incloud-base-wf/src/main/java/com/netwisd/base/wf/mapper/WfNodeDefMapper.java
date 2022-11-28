package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfNodeDef;
import com.netwisd.base.wf.dto.WfNodeDefDto;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义-节点定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 10:39:52
 */
@Mapper
public interface WfNodeDefMapper extends BaseMapper<WfNodeDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfNodeDefDto
     * @return
     */
    Page<WfNodeDefVo> getPageList(Page page, @Param("wfNodeDefDto") WfNodeDefDto wfNodeDefDto);
}
