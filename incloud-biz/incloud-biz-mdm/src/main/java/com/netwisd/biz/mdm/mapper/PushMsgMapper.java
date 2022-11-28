package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.PushMsgDto;
import com.netwisd.biz.mdm.entity.PushMsg;
import com.netwisd.biz.mdm.vo.PushMsgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 推送消息 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-10-16 11:54:52
 */
@Mapper
public interface PushMsgMapper extends BaseMapper<PushMsg> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param pushMsgDto
     * @return
     */
    Page<PushMsgVo> getPageList(Page page, @Param("pushMsgDto") PushMsgDto pushMsgDto);
}
