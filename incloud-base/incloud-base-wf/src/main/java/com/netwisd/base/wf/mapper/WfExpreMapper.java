package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfExpre;
import com.netwisd.base.wf.dto.WfExpreDto;
import com.netwisd.base.wf.vo.WfExpreVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 表达式维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:22:57
 */
@Mapper
public interface WfExpreMapper extends BaseMapper<WfExpre> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfExpreDto
     * @return
     */
    Page<WfExpreVo> getPageList(Page page, @Param("wfExpreDto") WfExpreDto wfExpreDto);
}
