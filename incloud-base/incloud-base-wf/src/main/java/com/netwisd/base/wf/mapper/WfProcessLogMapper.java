package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfProcessLog;
import com.netwisd.base.wf.dto.WfProcessLogDto;
import com.netwisd.base.wf.vo.WfProcessLogVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程日志 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-14 17:42:43
 */
@Mapper
public interface WfProcessLogMapper extends BaseMapper<WfProcessLog> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfProcessLogDto
     * @return
     */
    Page<WfProcessLogVo> getPageList(Page page, @Param("wfProcessLogDto") WfProcessLogDto wfProcessLogDto);
}
