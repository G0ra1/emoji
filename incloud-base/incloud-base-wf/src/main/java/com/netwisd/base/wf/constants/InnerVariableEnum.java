package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 内置变量
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/26 11:11 上午
 */
@AllArgsConstructor
@Getter
public enum InnerVariableEnum {
    PREFIX_LOCAL("wfLocal"),
    PREFIX("wf"),
    STARTER(InnerVariableEnum.PREFIX_LOCAL.name+"Starter"),
    ASSIGNEE(InnerVariableEnum.PREFIX.name+"Assignee"),
    ASSIGNEELOCAL(InnerVariableEnum.PREFIX_LOCAL.name+"Assignee"),
    /*ASSIGNEE_LIST(InnerVariableEnum.PREFIX.name+"AssigneeList"),*/
    DESCRIPTION(InnerVariableEnum.PREFIX_LOCAL.name+"Description"),
    DECISION(InnerVariableEnum.PREFIX_LOCAL.name+"Decision"),
    DUEDATE(InnerVariableEnum.PREFIX_LOCAL.name+"DueDate"),
    DECISION_INSTANCES(InnerVariableEnum.PREFIX.name+"DecisionInstances"),
    UNDECISION_INSTANCES(InnerVariableEnum.PREFIX.name+"UnDecisionInstances"),
    CONDITIONEXPRESSION(InnerVariableEnum.PREFIX.name+"ConditionExpression"),
    USEREXPRESSION(InnerVariableEnum.PREFIX.name+"UserExpression"),
    CAMUNDA_PROCESS_ID(InnerVariableEnum.PREFIX.name+"CamundaProcessId"),
    PROCESS_ID(InnerVariableEnum.PREFIX.name+"ProcessId"),
    CALLACTIVITY_DEF_ID(InnerVariableEnum.PREFIX.name+"CallActivityDefId"),
    ACT_INS_ID(InnerVariableEnum.PREFIX.name+"ActInsId"),
    REASON(InnerVariableEnum.PREFIX_LOCAL.name+"Reason"),
    BIZPRIORITY(InnerVariableEnum.PREFIX_LOCAL.name+"bizPriority"),//优先级
    DRAFT(InnerVariableEnum.PREFIX_LOCAL.name+"Draft"),
    TASK_ID("taskId"),
    //DECISION_INSTANCES_ACTIVED(InnerVariableEnum.PREFIX.name+"DecisionInstancesActived")
    ;
    String name;
}
