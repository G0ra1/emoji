package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfExpreUserParamDef;
import com.netwisd.base.wf.dto.WfExpreUserParamDefDto;
import com.netwisd.base.wf.vo.WfExpreUserParamDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:49:34
 */
@Mapper
public interface WfExpreUserParamDefMapper extends BaseMapper<WfExpreUserParamDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfExpreUserParamDefDto
     * @return
     */
    Page<WfExpreUserParamDefVo> getPageList(Page page, @Param("wfExpreUserParamDefDto") WfExpreUserParamDefDto wfExpreUserParamDefDto);
}
