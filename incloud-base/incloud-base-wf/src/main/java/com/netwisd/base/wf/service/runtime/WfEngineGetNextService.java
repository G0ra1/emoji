package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.vo.WfNextUserVo;
import java.util.List;

/**
 * @author zouliming@netwisd.com
 * @description 获取下一步的相关操作
 * @date 2021/12/13 9:47
 */
public interface WfEngineGetNextService {
    List<WfNextUserVo> getNextUser(WfEngineDto wfEngineDto);
}
