package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.dto.WfProcDefDto;
import com.netwisd.base.wf.vo.WfProcDefVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-01 16:18:04
 */
@Mapper
public interface WfProcdefMapper extends BaseMapper<WfProcDef> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfProcdefDto
     * @return
     */
    Page<WfProcDefVo> getPageList(Page page, @Param("wfProcdefDto") WfProcDefDto wfProcdefDto);

    int updateXmlByDeploymentId(String deploymentId, String xml);
}
