package com.netwisd.base.wf.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.event.WfDelegateTaskDto;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.constants.InnerVariableEnum;
import com.netwisd.base.wf.constants.TaskTypeEnum;
import com.netwisd.base.wf.dto.CamundaDto;
import com.netwisd.base.wf.dto.VariableDto;
import com.netwisd.base.wf.service.other.AssignmentHandlerService;
import com.netwisd.base.wf.util.FlowUtils;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.LoopCharacteristics;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Description: camunda的功能测试验证，待转正...
 * 为了测试方便，所有方法void，后台看日志数据测试，所有任务都有遍历，处理任务时一股会用一条数据测试，不接收输入用户ID太麻烦
 * 不做异常处理，写主逻辑验证
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/12 4:54 下午
 */
@Slf4j
@RestController
@RequestMapping("/engine")
@RefreshScope
@Deprecated
public class EngineController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private AssignmentHandlerService assignmentHandlerService;

    @Autowired
    HttpClient httpClient;

    @Autowired
    Mapper dozerMapper;

    @GetMapping("/processList")
    public void getDepeloyProcessList(){
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
        processDefinitions.forEach(processDefinition -> {
            log.info("流程定义 id:{},key:{},name:{}",processDefinition.getId(),processDefinition.getKey(),processDefinition.getName());
        });
    }

    @GetMapping("/startProcess")
    public void startProcess(@RequestParam String prockey){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(prockey)
//                .versionTag("1")
                .latestVersion()
                .singleResult();
        //以不同方式创建三个流程，业务key不同
        Map<String, Object> variables = new HashMap<String,Object>();
/*        List list = new ArrayList();
        list.add("a1");
        list.add("a2");*/
        variables.put("amount", 1);
        //variables.put("flag", true);
        /*variables.put("assigneeList", list);*/
        variables.put("wfStarter", "zouliming");
        variables.put("wfAssignee", "zouliming");
        variables.put("wfLocalDueDate", new Date());
        /*variables.put("wfname", "incloud");*/
        String businessKey = prockey+"_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        ProcessInstance instance1 = runtimeService.startProcessInstanceByKey(processDefinition.getKey(),businessKey,variables);
        log.info("instance1实例id:{}，instance1实例key:{} ",instance1.getId(),instance1.getBusinessKey());
        /*ProcessInstance instance2 = runtimeService.createProcessInstanceByKey(processDefinition.getKey())
                .setVariable("amount",1200)
                .setVariable("item","incloud")
                .setVariable("starter","test")
                .setVariable("approved",false)
                .businessKey("payment-002")
                .execute();
        log.info("instance2实例id:{}，instance2实例key:{} ",instance2.getId(),instance2.getBusinessKey());*/
        /*ProcessInstanceWithVariables instance3 = runtimeService.createProcessInstanceByKey("payment")
                .setVariable("amount",1200)
                .setVariable("item","incloud1")
                .setVariable("starter","test")
                .businessKey("payment-003")
                .executeWithVariablesInReturn();
        log.info("instance3实例id:{}，instance3实例key:{} ",instance3.getId(),instance3.getBusinessKey());*/
    }

    /**
     * 查询所有默认一级流程
     */
    @GetMapping("/queryAllProcess")
    public void queryAllProcess(){
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().processDefinitionKey("test001").orderByBusinessKey().asc().list();
        processInstances.forEach(processInstance -> {
            log.info("查询所有流程ID:{} ,是否结束：{}",processInstance.getId(),processInstance.isEnded());
            this.getActivityAndExecutionInstanceInfo(processInstance.getId());
        });
    }

    /**
     *
     * @param processInstanceId
     * @return
     */
    public List<ProcessInstance> getActivityAndExecutionInstanceInfo(@PathVariable String processInstanceId){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        List<ProcessInstance> processInstances  = new ArrayList<>();
        log.info("当前流程ID:{} 业务key：{},是否结束：{}",processInstance.getId(),processInstance.getBusinessKey(),processInstance.isEnded());
        List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(processInstance.getProcessInstanceId()).list();

        ActivityInstance activityInstance = runtimeService.getActivityInstance(processInstance.getProcessInstanceId());
        ActivityInstance[] childActivityInstances = activityInstance.getChildActivityInstances();
        for(ActivityInstance activityInstanceChlid:childActivityInstances){
            log.info("查询所有流程的子活动实例名称：{} ",activityInstanceChlid.getActivityName());
            String[] executionIds = activityInstanceChlid.getExecutionIds();
            for(String executionId : executionIds){
                log.info("executionId:{}",executionId);
                Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
                if(execution instanceof ProcessInstance){
                    ProcessInstance subProcessInstance = (ProcessInstance)execution;
                    log.info("查询所有子话动ID:{} ,是否结束：{}",subProcessInstance.getId(),subProcessInstance.isEnded());
                    processInstances.add(subProcessInstance);
                }
            }
        }
        return processInstances;
    }

    /**
     * 流程驳回
     * 注：会签节点不能够驳回，需要在页面上限定；目前根据已知其他平台没有会签驳回一说；
     * 另：多分支（也就是由并行网关产生的multiInstance,每个分支对应自己的exection)情况下，
     * 驳回支持驳回到当前exection分支路线中已经走过的，并且是并行网关之后的节点；
     * 否则，需要让整个流程中的其他分支也cancel掉，这个目前暂不实现；
     * @param camundaDto
     */
    @PostMapping("/reject")
    public void reject(@RequestBody CamundaDto camundaDto){
        Task task = taskService.createTaskQuery().taskId(camundaDto.getTaskId()).singleResult();
        String targetActivityId = camundaDto.getTargetActivityId();
        String targetAssignee = camundaDto.getTargetAssignee();
        String processInstanceId = task.getProcessInstanceId();
        log.info("---reject---当前任务的流程实例ID",processInstanceId);
        log.info("---reject---驳回的目标活动ID：{}，目标受理人：{}",targetActivityId,targetAssignee);

        ActivityInstance activityInstance = runtimeService.getActivityInstance(task.getProcessInstanceId());
        log.info("---reject---当前任务所对应的流程定义ID:{},executionID:{}",activityInstance.getActivityId(),activityInstance.getId());

        String id = this.getInstanceIdForActivity(activityInstance,task.getTaskDefinitionKey());
        log.info("---reject---获取当前activity实例ID：{}",id);

        //设置目标节点对应的过程变量
        Map<String, Object> taskVariable = new HashMap<>();
        /**
         * todo 这里还需要判断目标节点是否是会签节点，如果是会签节点，应该设置相应的collection属性对应的变量名，可以从流程定义flownode中获取
         */
        taskVariable.put(InnerVariableEnum.ASSIGNEE.getName(), targetAssignee);
        taskService.createComment(task.getId(), processInstanceId, "驳回原因:123");
        runtimeService.createProcessInstanceModification(processInstanceId)
                //只需要cancel掉当前活动的实例，之前的不用，因为之前的已经完成——，这个前提是没有并行execution，我们暂不考虑并行网关这种；
                .cancelActivityInstance(id)//关闭相关任务
                .setAnnotation("进行了驳回到第一个任务节点操作")
                .startBeforeActivity(targetActivityId)//启动目标活动节点
                .setVariables(taskVariable)//流程的可变参数赋值
                .execute();
        log.info("---reject---驳回成功");
    }

    /**
     * 撤回功能
     * @param camundaDto
     */
    @PostMapping("/revoke")
    public void revoke(@RequestBody CamundaDto camundaDto){
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(camundaDto.getTaskId()).singleResult();
        String processInstanceId = historicTaskInstance.getProcessInstanceId();
        String currentActivityId = historicTaskInstance.getTaskDefinitionKey();
        /*String currentActivityInstanceId = historicTaskInstance.getActivityInstanceId();
        String currentActivityId = historyService.createHistoricActivityInstanceQuery().activityInstanceId(currentActivityInstanceId).singleResult().getActivityId();*/
        //设置目标节点对应的过程变量
        Map<String, Object> taskVariable = new HashMap<>();
        /**
         * todo 这里还需要判断目标节点是否是会签节点，如果是会签节点，应该设置相应的collection属性对应的变量名，可以从流程定义flownode中获取
         * todo 包括目标节点所需要的其他过程变量，如：assignee
         */
        taskVariable.put(InnerVariableEnum.ASSIGNEE.getName(), camundaDto.getUserName());
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).active().orderByDueDate().asc().list();
        Collection<String> singleTaskList = getSingleTaskList(list);
        for(String targetActivityId:singleTaskList){
            log.info("目标撤回要取消的节点ID：{}",targetActivityId);
            runtimeService.createProcessInstanceModification(processInstanceId)
                    .cancelAllForActivity(targetActivityId)
                    .setAnnotation("撤回原因：456")
                    .startBeforeActivity(currentActivityId)
                    .setVariable("assignee",camundaDto.getUserName())
                    .execute();
            log.info("---revoke---撤回成功");
        }
    }

    /**
     * 清洗同一个activityId并且在同一个exection（同一个exectionId）中的Task
     * 目标list保证同一个exection中的activity只有一个Task
     * @param sourceList
     * @return
     */
    private Collection<String> getSingleTaskList(List<Task> sourceList){
        Map<String,String> targetMap = new HashMap();
        for(Task task:sourceList) {
            log.info("任务id:{},任务名称:{},任务受理人:{}", task.getId(), task.getName(), task.getAssignee());
            //String key = task.getExecutionId()+task.getTaskDefinitionKey();
            String key = task.getTaskDefinitionKey();
            targetMap.put(key,task.getTaskDefinitionKey());
        }
        return targetMap.values();
    }

    /**
     * 流程处理日志
     * @param processInstanceId
     */
    @GetMapping("/historyActivity/{processInstanceId}")
    public void historyActivity(@PathVariable String processInstanceId){
        log.info("---historyActivity---当前任务的流程实例ID:{}", processInstanceId);
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityType("userTask")
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        for(HistoricActivityInstance historicActivityInstance : historicActivityInstances){
            String targetActivityId = historicActivityInstance.getActivityId();
            String activityName = historicActivityInstance.getActivityName();
            String targetAssignee = historicActivityInstance.getAssignee();
            log.info("---historyActivity---流程日志--节点ID：{}，节点名称：{}，受理人：{}",targetActivityId,activityName,targetAssignee);
        }
    }

    /**
     * 获取当前人的待办列表
     * @param userName
     */
    @GetMapping("/taskList/{userName}")
    public void taskList(@PathVariable String userName){
        List<Task> tasks = getUserTask(userName);
        for (Task task :tasks) {
            String currentActivityType = getCurrentActivityType(task);
            log.info("当前用户任务类型");
            if(ObjectUtil.isNotEmpty(task))
            log.info("任务id：{} ,任务名称：{}，流程实例id:{},任务key:{}"
                    ,task.getId(),task.getName(),task.getProcessInstanceId(),task.getTaskDefinitionKey());
            ActivityInstance activityInstance = runtimeService.getActivityInstance(task.getProcessInstanceId());
            log.info("当前任务所对应的流程定义ID:{},当前分支的executionID:{},当前任务的executionID:{}"
                    ,activityInstance.getActivityId(),activityInstance.getId(),task.getExecutionId());

            String id = this.getInstanceIdForActivity(activityInstance,task.getTaskDefinitionKey());
            log.info("获取activity实例ID：{}",id);
            Map<String, Object> processVariables = runtimeService.getVariables(task.getExecutionId());
            log.info("当前过程变量为：{}",processVariables);
        }
        /*tasks.forEach(task -> {
            *//*BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
            Collection<FlowNode> flowNodes = bpmnModelInstance.getModelElementsByType(FlowNode.class);
            for(FlowNode flowNode:flowNodes){
                log.info("遍历一下节点：名称：{},ID:{}",flowNode.getName(),flowNode.getId());
            }
            ModelElementInstance instance = bpmnModelInstance.getModelElementById(task.getTaskDefinitionKey());
            UserTask userTask = (UserTask)instance;
            List<FlowNode> flowNodeList = userTask.getSucceedingNodes().list();
            for(FlowNode possibleTask:flowNodeList){
                log.info("可能的任务的名称：{}",possibleTask.getName());
            }*//*
            //BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
            //DMN的玩法---start
            *//*DmnModelInstance dmnModelInstance = repositoryService.getDmnModelInstance(task.getProcessDefinitionId());
            Collection<DmnElement> dmnElements = dmnModelInstance.getModelElementsByType(DmnElement.class);
            for (DmnElement dmnElement:dmnElements) {
                if(dmnElement instanceof Input){
                    Input input = (Input)dmnElement;
                    String inputVariable = input.getInputExpression().getText().getTextContent();
                }else if(dmnElement instanceof Input){
                    Output output = (Output)dmnElement;
                    String outputVariable = output.getName();
                }else if(dmnElement instanceof Rule){
                    Rule rule = (Rule)dmnElement;
                    Collection<InputEntry> inputEntries = rule.getInputEntries();
                }
            }*//*
            //DMN的玩法---end
            *//*Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
            log.info("过程变量为：{}",variables);
            List<UserTask> nextUserTasks = FlowUtils.getNextUserTasks(bpmnModelInstance, task, variables);
            for(UserTask userTask:nextUserTasks){
                log.info("---下一个任务名称：{}",userTask.getName());
            }*//*
            //log.info("任务id：{} ,任务名称：{}，任务流程id:{}",task.getId(),task.getName(),task.getProcessInstanceId());
        });*/
    }

    /**
     * 通过活动ID获取当前的instanceID
     * @param activityInstance
     * @param activityId
     * @return
     */
    private String getInstanceIdForActivity(ActivityInstance activityInstance, String activityId) {
        ActivityInstance instance = getChildInstanceForActivity(activityInstance, activityId);
        if (instance != null) {
            return instance.getId();
        }
        return null;
    }

    /**
     * 递归获取其最终子实例ID
     * @param activityInstance
     * @param activityId
     * @return
     */
    private ActivityInstance getChildInstanceForActivity(ActivityInstance activityInstance, String activityId) {
        if (activityId.equals(activityInstance.getActivityId())) {
            return activityInstance;
        }
        for (ActivityInstance childInstance : activityInstance.getChildActivityInstances()) {
            ActivityInstance instance = getChildInstanceForActivity(childInstance, activityId);
            if (instance != null) {
                return instance;
            }
        }
        return null;
    }

    /**
     * 获取用户任务
     * @param userName
     * @return
     */
    private List<Task> getUserTask(String userName){
        List<Task> assigneeTasks = taskService.createTaskQuery().taskAssignee(userName).orderByTaskId().desc().list();
        List<Task> candidateTasks = taskService.createTaskQuery().taskCandidateUser(userName).orderByDueDate().desc().list();
        //List<Task> candidateGroupTasks = taskService.createTaskQuery().taskCandidateGroup(userName).orderByDueDate().desc().list();
        List<Task> tasks = CollectionUtil.newArrayList();
        CollectionUtil.addAll(tasks,candidateTasks).addAll(assigneeTasks);
        return tasks;
    }

    /**
     * 根据任务获取一个bpmn实例
     * @param task
     * @return
     */
    private BpmnModelInstance getBpmnModelInstance(Task task){
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
        return bpmnModelInstance;
    }



    /**
     * 获取下一个节点的审批人；
     * 注：如果是会签节点，不能够选人；根据其他业务平台处理方式：1.会签完后回到上一个节点，由上一节点选下一步；2.提前定义好会签后下一节点的具体人，会签直接提交
     * @param userName
     * @param variables
     */
    /*@GetMapping("/nextuser/{userName}")
    public void getNextUser(@PathVariable String userName, @RequestBody Map<String, Object> variables){
        List<Task> tasks = getUserTask(userName);
        for (Task task : tasks) {
            taskService.claim(task.getId(),userName);
            Map<String, Object> processVariables = runtimeService.getVariables(task.getExecutionId());
            log.info("原过程变量为：{}", processVariables);
            processVariables.putAll(variables);
            log.info("用户操作后过程变量为：{}", processVariables);
            log.info("用户：{}，任务id：{} ,任务名称：{}，任务流程id:{}", userName, task.getId(), task.getName(), task.getProcessInstanceId());
            BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task);
            List<UserTask> nextUserTasks = FlowUtils.getNextUserTasks(bpmnModelInstance, task, processVariables);

                //这儿注意，直接在candidateUser和assginee中同时放值，会有问题，实际情况下，我们还是自己单独存，存取的表达式可以是集合和更复杂的数据结构，不放在bnmn20.xml文件中了
                //List<String> camundaCandidateUsersList = userTask.getCamundaCandidateUsersList();
                //流程定义ID，流程节点定义ID，代表一个唯一的
                //实际上只有一层循环
                for (UserTask userTask:nextUserTasks){
                    StringBuffer sb = new StringBuffer();
                    String expressionId = sb.append(task.getProcessDefinitionId()).append("-").append(userTask.getId()).toString();
                    List<String> users = getCamundaCandidatesList(expressionId, processVariables);
                    log.info("---下一个任务类型：{}，下一个任务名称：{},下一个任务的待选用户：{}"
                            , userTask.getName(), users);
                }

        }
    }*/

    /**
     * 模拟根据流程定义ID+节点定义ID，去取流程定义时的获取人员的表达式
     * 这个实际会跟分级对接，这里做个模拟
     * @param expressionId
     * @return
     */
    private List<String> getCamundaCandidatesList(String expressionId,Map<String, Object> processVariables){
        log.info("=====模拟获取分级的人员表达式===：{}",expressionId);
        List<String> camundaCandidatesExpressionList = new ArrayList<>();
        /**
         * 我暂时把表达式分成三种样子，一种是spring bean的方式，一种是普通变量形式，还有一种直接是值；当然这三种形式都可以以集合的形式体现
         * 这个变量名我们内置，后面就用这个变量名来设置节点的人员表达式标识，这个expressionId就相当于模拟获取到我们定义时存的表达式，如下：
         * 表达式只是具个例子，通过someId去获取人，这个someId可能是业务中产生的什么问题东西，比如当前人部门ID，当前职位ID等任何东西，通过过程变量传递过来；
         * 注：这个表达式是在维护定义流程时，动态写入数据库的
         */
        Map<String, Map<String, Object>> flowNodeExpressionAndArgValue = assignmentHandlerService.getFlowNodeExpressionAndArgValue(expressionId);
        for (Map.Entry<String, Map<String, Object>> mapEntry:flowNodeExpressionAndArgValue.entrySet()){
            camundaCandidatesExpressionList.add(mapEntry.getKey());
            processVariables.putAll(mapEntry.getValue());
        }
        /*camundaCandidatesList.add("${assignmentHandlerService.getUserTest(java.lang.String someId)}");*/
        List<String> users = inVokeExpressionMethod(camundaCandidatesExpressionList, processVariables);
        return users;
    }

    /**
     * 暂处理单个表达式，暂不支持同时设置多个表达式
     * @param camundaCandidatesExpressionList
     * @param processVariables
     * @return
     */
    private List<String> inVokeExpressionMethod(List<String> camundaCandidatesExpressionList,Map<String, Object> processVariables){
        List userList = new ArrayList();
        for (String uesrs : camundaCandidatesExpressionList) {
            if(StrUtil.isNotEmpty(uesrs)){
                //代表是表达式
                if (uesrs.startsWith("${")) {
                    //获取用户表达式中的bean，method，参数等
                    String expression = StrUtil.subBetween(uesrs,"{","}");
                    Object objRe = null;
                    if(uesrs.contains(".") && uesrs.contains("(") && uesrs.contains(")")){
                        String bean = StrUtil.subBefore(expression,".",false);
                        String method = StrUtil.subAfter(expression,".",false);
                        String methodName = StrUtil.subBefore(method,"(",false);
                        String parameters = StrUtil.subBetween(method,"(",")");
                        //参数的类型和变量Map结构
                        Map<String,String> parametersMap = new HashMap<>();
                        if(StrUtil.contains(parameters,",")){
                            String[] splits = parameters.split(",");
                            for(String parameter:splits){
                                String[] split = parameters.split(" ");
                                String parameterType = split[0];
                                String parameterVariable = split[1];
                                parametersMap.put(parameterType,parameterVariable);
                            }
                        }else if(StrUtil.contains(parameters," ")){
                            String[] split = parameters.split(" ");
                            String parameterType = split[0];
                            String parameterVariable = split[1];
                            parametersMap.put(parameterType,parameterVariable);
                        }else {//无参的
                            //不处理
                        }
                        List<Object> targetParameters = new ArrayList();
                        if(StrUtil.isNotEmpty(parameters) && ObjectUtil.isNotEmpty(parametersMap)){
                            //String[] splits = StrUtil.split(parameters, ",");
                            for(Map.Entry<String,String> parMap:parametersMap.entrySet()){
                                //表示当前过程变量中有与参数名一样的，那么参数执行时，就取当前过程变量的值
                                //不是key，vaule是变量名
                                if(processVariables.containsKey(parMap.getValue())){
                                    targetParameters.add(processVariables.get(parMap.getValue()));
                                }
                            }
                        }

                        //通过获取用户表达式中相关内容，做java反射，调用表达式获取结果，因为是人员表达式，bpmn规范中我们只有有两种玩法，要么是string数组（或集合），要么是string，反正就不能有别的
                        Object targetBean = SpringContextUtils.getBean(bean);
                        Method targetMethod = null;
                        if(parametersMap.size()>0){
                            Collection<String> values = parametersMap.keySet();
                            Class<?>[] classes = new Class<?>[values.size()];
                            int index = 0;
                            for (String paramType:values){
                                try {
                                    Class<?> targetClassType = Class.forName(paramType);
                                    classes[index] = targetClassType;
                                    index++;
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            targetMethod = ReflectionUtils.findMethod(targetBean.getClass(), methodName,classes);
                        }else {
                            targetMethod = ReflectionUtils.findMethod(targetBean.getClass(), methodName);
                        }
                        objRe = ReflectionUtils.invokeMethod(targetMethod, targetBean, targetParameters.toArray());
                    }else {
                        //表示当前过程变量中有与参数名一样的，那么参数执行时，就取当前过程变量的值
                        if(processVariables.containsKey(expression)){
                            objRe = processVariables.get(expression);
                        }
                    }
                    if(objRe instanceof String[]){
                        userList.addAll(Arrays.asList((String[])objRe));
                    }else if(objRe instanceof List){
                        userList.addAll((List)objRe);
                    }else if(objRe instanceof String){
                        userList.add((String)objRe);
                    }else {
                        log.error("其他错误类型的人员表达式返回值，暂不支持");
                    }
                } else {//说明直接是具体的值，我们就可以直接取了；不用处理表达式那么麻烦了
                    String[] split = uesrs.split(",");
                    userList.addAll(Arrays.asList((String[])split));
                }
            }
        }
        return userList;
    }

    /**
     * 根据当前任务获取任务的类型是多实例，还是标准实例，这个最好只用一次，然后存起来；
     * 操作model层面的API相当麻烦且费时；
     * @param task
     * @return
     */
    public String getCurrentActivityType(Task task){
        String executionId = task.getExecutionId();
        Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
        if(execution instanceof ExecutionEntity){
            ExecutionEntity executionEntity = (ExecutionEntity) execution;
            String activityId = executionEntity.getActivityId();
            BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task);
            Collection<FlowNode> flowNodes = FlowUtils.getFlowNodes(bpmnModelInstance);
            FlowNode flowNode = FlowUtils.getFlowNodeById(activityId, flowNodes);
            String taskType = checkActivityType(flowNode);
            return taskType;
        }
        return null;
    }

    /**
     * 检查当前活动节点的类型
     * @param flowNode
     * @return
     */
    public String checkActivityType(FlowNode flowNode){
        if (flowNode instanceof UserTask) {
            UserTask userTask = (UserTask) flowNode;
            log.info("xxxx"+userTask.getId());
            LoopCharacteristics loopCharacteristics = userTask.getLoopCharacteristics();
            if(loopCharacteristics != null){
                return TaskTypeEnum.MULTI_TASK.getType();
            }else {
                return TaskTypeEnum.STANDARD_TASK.getType();
            }
        }
        return null;
    }

    /**
     * 任务的委派
     * @param variableDto
     */
    @Transactional
    @PostMapping("/delegate")
    public void delegate(@RequestBody VariableDto variableDto){
        String taskId = variableDto.getTaskId();
        String userId = variableDto.getUserId();
        String delegateUserId = variableDto.getDelegateUserId();
        if(StrUtil.isEmpty(taskId) || StrUtil.isEmpty(userId) || StrUtil.isEmpty(delegateUserId)){
            throw new IncloudException("参数错误~");
        }
        taskService.setOwner(taskId,userId);
        taskService.delegateTask(taskId,delegateUserId);
    }

    private void checkParm(VariableDto variableDto){
        Map<String, Object> variables;
        String taskId,userId,decision,description;
        try{
            variables = variableDto.getInnerVariable();
            taskId = variableDto.getTaskId();
            userId = variableDto.getUserId();
            //模拟取到表单这两个值，同意和备注
            variables.get(InnerVariableEnum.DECISION.getName());
            decision = (String) variables.get(InnerVariableEnum.DECISION.getName());
            description = (String) variables.get(InnerVariableEnum.DESCRIPTION.getName());
            if(ObjectUtil.isEmpty(variables) || variables.keySet().size() == 0
                    || StrUtil.isEmpty(taskId) || StrUtil.isEmpty(userId) || StrUtil.isEmpty(decision)){
                throw new IncloudException("参数错误~");
            }
        }catch (Exception e){
            throw new IncloudException("参数错误~");
        }
    }

    /**
     * 任务保存提交
     * @param variableDto
     */
    @Transactional
    @PostMapping("/handle")
    public void handle(@RequestBody VariableDto variableDto){
        Map<String, Object> variables =  variableDto.getInnerVariable();
        List<String> ll = new ArrayList<>();
        ll.add("zhangsan");
        ll.add("lisi");
        variables.put("wfAssignee",ll);
        taskService.complete(variableDto.getTaskId(),variables);

        /*checkParm(variableDto);
        Map<String, Object> variables =  variableDto.getInnerVariable();
        String taskId = variableDto.getTaskId();
        String userId = variableDto.getUserId();
        String decision = (String) variables.get(InnerVariableEnum.DECISION.getName());
        String description = (String) variables.get(InnerVariableEnum.DESCRIPTION.getName());

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        task.setAssignee(userId);
        //taskService.claim(taskId,(String)variables.get(InnerVariableEnum.ASSIGNEE.getName()));

        String ownner = task.getOwner();
        if(StrUtil.isEmpty(ownner)){
            task.setOwner(userId);
        }else {//说明这是由别给委派的任务

        }

        task.setDescription(decision+"/"+description);
        taskService.saveTask(task);

        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        log.info("原过程变量为：{}",processVariables);
        processVariables.putAll(variables);
        clearWfLocalVariable(processVariables);
        taskService.setVariables(task.getId(),processVariables);
        log.info("提交合并后操作后过程变量为：{}",processVariables);

        //模拟表单提交一个流程类型
        String currentActivityType = getCurrentActivityType(task);
        //多实例——目前只支持会签和子流程多实例
        if(Boolean.valueOf(decision)){
            if(currentActivityType.equals(TaskTypeEnum.MULTI_TASK.getType())){
                Object decisionInstanceValue = processVariables.get(InnerVariableEnum.DECISION_INSTANCES.getName());
                Integer decisionInstances =  ObjectUtil.isEmpty(decisionInstanceValue) ? 0 : (Integer)decisionInstanceValue;
                processVariables.put(InnerVariableEnum.DECISION_INSTANCES.getName(),++decisionInstances);
                taskService.setVariables(task.getId(),processVariables);
                log.info("用户操作后过程变量为：{}",processVariables);
            }
            //根据是否回滚和提交前提交后，在handle方法中调用这个，如果需要回滚，则handle抛出异常
            customListenerInvoke(task,false);
            complete(task,variables);
        }else {
            if(!currentActivityType.equals(TaskTypeEnum.MULTI_TASK.getType())){
                log.info("走驳回了....");
                //reject(null);
            }
        }*/
    }

    /**
     * 根据是否回滚和提交前提交后，在handle方法中调用这个，如果需要回滚，则handle抛出异常
     * @param task
     * @param isRollBack
     * @return
     */
    public Boolean customListenerInvoke(Task task,Boolean isRollBack){
        //模拟从数据库获取到维护的url
        String url = "http://localhost:8007/test/handle";
        Boolean rollBackFlag = false;
        log.info("url:{}",url);
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            String accessToken = AppUserUtil.getAccessToken();
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("Authorization",accessToken);

            WfDelegateTaskDto wfDelegateTask = dozerMapper.map(task, WfDelegateTaskDto.class);

            String jsonString = JSONUtil.toJsonStr(wfDelegateTask);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);

            execute = httpClient.execute(httpPost);

            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) {
                log.info("自定义任务执行完成！");
            }else {
                log.info("自定义任务执行失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                if(isRollBack){
                    rollBackFlag = true;
                }
                throw new BpmnError("errorooooooooooooo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return rollBackFlag;
    }


    /**
     * 清除掉本地变量
     * @param variables
     */
    public void clearWfLocalVariable(Map<String, Object> variables){
        List<Object> fieldValues = EnumUtil.getFieldValues(InnerVariableEnum.class,"name");
        for (Object name:fieldValues){
            String filedValue = String.valueOf(name);
            if(ObjectUtil.isNotEmpty(variables.get(filedValue)) && filedValue.startsWith(InnerVariableEnum.PREFIX_LOCAL.getName())){
                variables.remove(filedValue);
            }
        }
    }

    /**
     * 任务提交
     * @param task
     * @param variables
     */
    @Transactional
    public void complete(Task task, @RequestBody Map<String, Object> variables){
        log.info("用户：{}，任务id：{} ,任务名称：{}，任务流程id:{}",task.getAssignee(),task.getId(),task.getName(),task.getProcessInstanceId());
        taskService.complete(task.getId());
        log.info("模拟任务处理完成：id {} name {}",task.getId(),task.getName());
    }
    @GetMapping("/complete/{taskId}")
    public void complete(@PathVariable String taskId){
        taskService.complete(taskId);
        taskService.deleteTask(taskId);
    }

    /**
     * 历史查询任务
     * @param userName
     */
    @GetMapping("/historyList/{userName}")
    public void historyList(@PathVariable String userName){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userName)
                //.finished()
                .list();
        list.forEach(historicTaskInstance -> {
            log.info("已办任务id：{} ,已办任务名称：{} 任务流程key：{}",historicTaskInstance.getId(),historicTaskInstance.getName(),historicTaskInstance.getProcessInstanceId());
        });
    }
}