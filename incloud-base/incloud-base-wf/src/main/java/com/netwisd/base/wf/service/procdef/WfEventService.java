package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfEvent;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfEventDto;
import com.netwisd.base.wf.vo.WfEventVo;

import java.util.List;

/**
 * @Description 按钮维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 11:37:15
 */
public interface WfEventService extends IService<WfEvent> {
    Page list(WfEventDto wfEventDto);
    List<WfEventVo> lists(WfEventDto wfEventDto);
    List<WfEvent> getDefaultEventList(WfEventDto wfEventDto);
    WfEventVo get(Long id);
    Boolean save(WfEventDto wfEventDto);
    Boolean update(WfEventDto wfEventDto);
    Boolean delete(String ids);
    List<WfEventVo> selectBatchIds(List<Long> ids);
}
