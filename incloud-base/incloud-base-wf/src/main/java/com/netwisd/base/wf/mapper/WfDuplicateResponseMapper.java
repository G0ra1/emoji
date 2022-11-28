package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfDuplicateResponse;
import com.netwisd.base.wf.dto.WfDuplicateResponseDto;
import com.netwisd.base.wf.vo.WfDuplicateResponseVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 我发起的传阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 22:12:53
 */
@Mapper
public interface WfDuplicateResponseMapper extends BaseMapper<WfDuplicateResponse> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfDuplicateResponseDto
     * @return
     */
    Page<WfDuplicateResponseVo> getPageList(Page page, @Param("wfDuplicateResponseDto") WfDuplicateResponseDto wfDuplicateResponseDto);
}
