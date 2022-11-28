package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.vo.wf.WfProcessVo;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.dto.WfProcessDto;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程实例 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 13:49:51
 */
@Mapper
public interface WfProcessMapper extends BaseMapper<WfProcess> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfProcessDto
     * @return
     */
    Page<WfProcessVo> getPageList(Page page, @Param("wfProcessDto") WfProcessDto wfProcessDto);
}
