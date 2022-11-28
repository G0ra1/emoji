package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfButtonDef;
import com.netwisd.base.wf.dto.WfButtonDefDto;
import com.netwisd.base.wf.vo.WfButtonDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义-按钮 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 22:26:53
 */
@Mapper
public interface WfButtonDefMapper extends BaseMapper<WfButtonDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfButtonDefDto
     * @return
     */
    Page<WfButtonDefVo> getPageList(Page page, @Param("wfButtonDefDto") WfButtonDefDto wfButtonDefDto);
}
