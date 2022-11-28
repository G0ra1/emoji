package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.service.runtime.WfEngineCheckService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zouliming@netwisd.com
 * @Description:
 * @date 2021/12/3 11:56
 */
@Service
@Slf4j
public class WfEngineCheckServiceImpl implements WfEngineCheckService {
    @Autowired
    WfProcessService wfProcessService;

    @Override
    public void checkProcessState(String processId) {
        if(StrUtil.isEmpty(processId)){
            throw new IncloudException("流程实例ID为空！");
        }
        WfProcess wfProcess = wfProcessService.get(processId);

        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("通过流程实例ID查找不到流程实例！");
        }
        if(wfProcess.getState().intValue() == WfProcessEnum.SUPENSION.getType().intValue()){
            throw new IncloudException("流程已挂起，请重新激活后继续！");
        }
        if(wfProcess.getState().intValue() == WfProcessEnum.TERMINATION.getType().intValue()){
            throw new IncloudException("流程已终止！");
        }
        if(wfProcess.getState().intValue() == WfProcessEnum.DONE.getType().intValue()){
            throw new IncloudException("流程已结束！");
        }

    }
}
