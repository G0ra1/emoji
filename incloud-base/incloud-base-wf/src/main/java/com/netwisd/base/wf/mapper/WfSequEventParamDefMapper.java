package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfSequEventParamDef;
import com.netwisd.base.wf.dto.WfSequEventParamDefDto;
import com.netwisd.base.wf.vo.WfSequEventParamDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义-序列流-事件-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:56:16
 */
@Mapper
public interface WfSequEventParamDefMapper extends BaseMapper<WfSequEventParamDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfSequEventParamDefDto
     * @return
     */
    Page<WfSequEventParamDefVo> getPageList(Page page, @Param("wfSequEventParamDefDto") WfSequEventParamDefDto wfSequEventParamDefDto);
}
