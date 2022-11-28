package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfProcdefType;
import com.netwisd.base.wf.dto.WfProcdefTypeDto;
import com.netwisd.base.wf.vo.WfProcdefTypeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 流程分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-01 14:45:51
 */
@Mapper
public interface WfProcdefTypeMapper extends BaseMapper<WfProcdefType> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfProcdefTypeDto
     * @return
     */
    Page<WfProcdefTypeVo> getPageList(Page page, @Param("wfProcdefTypeDto") WfProcdefTypeDto wfProcdefTypeDto);
}
