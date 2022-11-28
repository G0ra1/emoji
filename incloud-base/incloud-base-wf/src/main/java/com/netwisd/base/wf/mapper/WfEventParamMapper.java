package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfEventParam;
import com.netwisd.base.wf.dto.WfEventParamDto;
import com.netwisd.base.wf.vo.WfEventParamVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 事件运行参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:10:12
 */
@Mapper
public interface WfEventParamMapper extends BaseMapper<WfEventParam> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfEventParamDto
     * @return
     */
    Page<WfEventParamVo> getPageList(Page page, @Param("wfEventParamDto") WfEventParamDto wfEventParamDto);
}
