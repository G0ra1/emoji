package com.netwisd.base.wf.service.other;

import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/21 11:06 上午
 */
public interface AssignmentHandlerService {

    public String test(String dsName);

    public String getAssignment(String userName);

    public String getAssignmentByDeptID(Long deptid);

    public String getCandidateGroupByDeptID(String deptid);

    public String[] getUserTest(String someId);

    public String[] test();

    public Map<String, Map<String,Object>> getFlowNodeExpressionAndArgValue(String expressionId);

    public Integer test1(String wfname);

    public Integer test2(Object... params);
}
