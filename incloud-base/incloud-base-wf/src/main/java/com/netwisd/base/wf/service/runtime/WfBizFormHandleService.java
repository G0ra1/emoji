package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.vo.WfProcDefVo;

/**
 * @Description 业务表单统一操作
 * @author 云数网讯 XHL
 * @date 2022-02-28 11:20:23
 */
public interface WfBizFormHandleService {

    WfProcDefVo getFormInfo(String taskType,String id);

}
