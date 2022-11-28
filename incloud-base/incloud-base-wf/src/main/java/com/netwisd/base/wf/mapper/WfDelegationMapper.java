package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfDelegation;
import com.netwisd.base.wf.dto.WfDelegationDto;
import com.netwisd.base.wf.vo.WfDelegationVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 按钮维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 14:45:53
 */
@Mapper
public interface WfDelegationMapper extends BaseMapper<WfDelegation> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfDelegationDto
     * @return
     */
    Page<WfDelegationVo> getPageList(Page page, @Param("wfDelegationDto") WfDelegationDto wfDelegationDto);
}
