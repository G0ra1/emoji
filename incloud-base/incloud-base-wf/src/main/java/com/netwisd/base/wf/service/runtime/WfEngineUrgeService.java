package com.netwisd.base.wf.service.runtime;


/** 流程c催办相关
 * @author XHL
 * @description
 * @date 2022/03/08 16:41
 */
public interface WfEngineUrgeService {

    /**
     * 根据流程实例id催办
     * @param camundaProcInsId
     * @return
     */
    boolean handleUrge(String camundaProcInsId);

}
