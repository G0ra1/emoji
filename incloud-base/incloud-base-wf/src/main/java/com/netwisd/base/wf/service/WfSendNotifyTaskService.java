package com.netwisd.base.wf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfSendNotifyTask;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfSendNotifyTaskDto;
import com.netwisd.base.wf.vo.WfSendNotifyTaskVo;
/**
 * @Description 我发出的知会 功能描述...
 * @author 云数网讯 XHK@netwisd.com
 * @date 2022-03-09 10:06:02
 */
public interface WfSendNotifyTaskService extends IService<WfSendNotifyTask> {
    Page list(WfSendNotifyTaskDto wfSendNotifyTaskDto);
    Page lists(WfSendNotifyTaskDto wfSendNotifyTaskDto);
    WfSendNotifyTaskVo get(Long id);
    Boolean save(WfSendNotifyTaskDto wfSendNotifyTaskDto);
    Boolean update(WfSendNotifyTaskDto wfSendNotifyTaskDto);
    Boolean delete(Long id);

    //管理员查询页面
    Page sendNotifyListForAd(WfSendNotifyTaskDto wfSendNotifyTaskDto);
}
