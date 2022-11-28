package com.netwisd.base.wf.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.netwisd.base.common.user.vo.expression.UserExpressionVO;
import com.netwisd.base.wf.constants.ExpreParamSourceEnum;
import com.netwisd.base.wf.constants.FlowNodeTypeEnum;
import com.netwisd.base.wf.starter.expression.HandleExpressionParam;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.JacksonUtil;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import de.odysseus.el.util.SimpleResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.dmn.feel.impl.FeelEngine;
import org.camunda.bpm.dmn.feel.impl.FeelEngineFactory;
import org.camunda.bpm.dmn.feel.impl.juel.FeelEngineFactoryImpl;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import java.util.*;

/**
 * @Description: 流程节点相关工具
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/22 11:17 上午
 */
@Slf4j
public class FlowUtils {
    private static final String wfArgs = "wfArgs, '";
    
    public static Collection<FlowNode> getFlowNodes(BpmnModelInstance bpmnModelInstance){
        Collection<FlowNode> flowNodes = bpmnModelInstance.getModelElementsByType(FlowNode.class);
        return flowNodes;
    }

    /**
     * 获取下一步选人的userTask
     * @param bpmnModelInstance
     * @param elementId
     * @param map
     * @param flowNodeType
     * @return
     */
    public static List<FlowNode> getNextUserTasks(BpmnModelInstance bpmnModelInstance, String elementId, Map<String, Object> map,String flowNodeType) {
        List<FlowNode> data = new ArrayList<>();
        Collection<FlowNode> flowNodes = getFlowNodes(bpmnModelInstance);
        ModelElementInstance instance = bpmnModelInstance.getModelElementById(elementId);
        FlowNode flowNode = (FlowNode)instance;
        log.info("elementId：{}",elementId);
        next(flowNodes,flowNode,map,data, flowNodeType);
        return data;
    }

    /**
     * 获取当前流程节点后面的所有出线，包括后面网后的出线，直到出现其他节点
     * @param bpmnModelInstance
     * @param task
     * @return
     */
    public static Collection<BpmnModelElementInstance> getOutgoings(BpmnModelInstance bpmnModelInstance, Task task){
        FlowNode flowNode = getFlowNode(bpmnModelInstance, task);
        Collection<BpmnModelElementInstance> results = new ArrayList<>();
        Collection<SequenceFlow> outgoings = flowNode.getOutgoing();
        //得到的是当前节点后面所有符合条件的出线和相应的用户任务
        getOutgoings(outgoings,results);
        return results;
    }

    /**
     * 一个递归方法，递归找到所有符合条件的出线和用户任务集合
     * 注意：不是当前节点后面整个流程定义中的所有出线和任务节点
     * @param outgoings
     * @return
     */
    private static void getOutgoings(Collection<SequenceFlow> outgoings,Collection<BpmnModelElementInstance> results){
        if(ObjectUtil.isNotEmpty(outgoings) && !outgoings.isEmpty()){
            results.addAll(outgoings);
            for(SequenceFlow sequenceFlow: outgoings){
                FlowNode nextFlowNode = sequenceFlow.getTarget();
                //如果出线的目标节点是网关，那么继续往下找
                if(nextFlowNode instanceof Gateway){
                    String gateWayId = nextFlowNode.getId();
                    log.info("有网关，网关id为：{}",gateWayId);
                    Collection<SequenceFlow> outgoing = nextFlowNode.getOutgoing();
                    getOutgoings(outgoing,results);
                }
                //如果是用户任务，那就不往下找了，而是把当前用户任务加入到结果中
                if(nextFlowNode instanceof UserTask){
                    results.add(nextFlowNode);
                }
                //如果是call activity，也就是外部子流程，需要找到相应的相应的子流程定义再进行遍历
                if(nextFlowNode instanceof CallActivity){
                    //目前来讲：CallActivity中也实现了人员表达式，先返回；
                    results.add(nextFlowNode);
                }
                //如果是subprocess，内嵌子流程
                if(nextFlowNode instanceof SubProcess){
                    results.add(nextFlowNode);
                }
                //如果是end，正常情况下就不管了，但要看当前节点是否是一个子流程，如果是子流程，就找到他对应的父流程后面的节点是什么；
                if(nextFlowNode instanceof EndEvent){
                    //目前的情况，实现的是如果是子流程的结束节点，提交就是当前子流程结束，如果后面有需求说要提交时选父流程的人员，这里再加上逻辑；
                    continue;
                }
            }
        }
    }


    private static Collection<FlowNode> getFlowNodes(BpmnModelInstance bpmnModelInstance, Task task){
        Collection<FlowNode> flowNodes = getFlowNodes(bpmnModelInstance);
        return flowNodes;
    }

    private static FlowNode getFlowNode(BpmnModelInstance bpmnModelInstance, Task task){
        ModelElementInstance instance = bpmnModelInstance.getModelElementById(task.getTaskDefinitionKey());
        FlowNode flowNode = (FlowNode)instance;
        log.info("任务id：{} ,任务名称：{}，任务流程id:{}",task.getId(),task.getName(),task.getProcessInstanceId());
        return flowNode;
    }

    public static Collection<UserTask> nextAll(BpmnModelInstance bpmnModelInstance, Task task,String flowNodeType){
        Collection<FlowNode> flowNodes = getFlowNodes(bpmnModelInstance, task);
        FlowNode flowNode = getFlowNode(bpmnModelInstance, task);
        Collection<SequenceFlow> outgoings = flowNode.getOutgoing();
        List<FlowNode> data = new ArrayList<>();
        //Map <String,UserTask> userTaskMap = new HashMap<>();
        if (outgoings != null && outgoings.size() > 0) {
            //遍历所有的出线--找到可以正确执行的那一条
            for (SequenceFlow sequenceFlow : outgoings) {
                handleFlowNode(flowNodes,outgoings,null,data,false,flowNodeType);
            }
        }
        return null;
    }

    @SneakyThrows
    public static void next(Collection<FlowNode> flowNodes, FlowNode flowNode, Map<String, Object> map, List<FlowNode> data,String flowNodeType) {
        //Map <String,UserTask> userTaskMap = new HashMap<>();
        log.info("当节节点ID：{}",flowNode.getId());
        //如果是结束节点
        if (flowNode instanceof EndEvent) {
            if(flowNodeType.equals(FlowNodeTypeEnum.ONLY_NEXT_NODE.getType())){
                data.add(flowNode);
                return;
            }
            //判断当前节点是否是子流程的结束节点，如果是返回子流程本身，然后再根据子流程本身找他下一级任务节点
            FlowNode flowNodetemp = checkCurrectNodeIsSubProcess(flowNodes, flowNode);
            if (flowNodetemp != null) {
                flowNode = flowNodetemp;
            }else{
                //代表不是子流程中的结束节点，而是流程的结束节点，那么就返回
                data.add(flowNode);
                return;
            }
        }
        //获取Task的出线信息--可以拥有多个
        Collection<SequenceFlow> outgoings = flowNode.getOutgoing();
        if (outgoings != null && outgoings.size() > 0) {
            handleFlowNode(flowNodes,outgoings,map,data,true,flowNodeType);
        }
    }

    /**
     *
     * @param expression
     * @param map
     */
    public static void handleExpressionParam(String expression,Map<String, Object> map) {
        //判断是否存在 wfArgs 这个变量
        if(StrUtil.contains(expression,wfArgs)) {
            //截取最后一个参数的 json 数组对象
            int lastIndex = expression.indexOf("]'")+1;
            String jsonString = expression.substring(expression.indexOf(wfArgs)+9,lastIndex);
            List<HandleExpressionParam>  handleExpressionParamDtoList = JSONObject.parseArray(jsonString, HandleExpressionParam.class);
            if(CollectionUtil.isNotEmpty(handleExpressionParamDtoList)) {
                //循环参数判断是否是变量，如果是变量需要替换变量中的值
                for (HandleExpressionParam handleExpressionParamDto : handleExpressionParamDtoList) {
                    if(ExpreParamSourceEnum.FORM_FIELD.getType().equals(handleExpressionParamDto.getParamType())) {
                        Object paramVal = map.get(handleExpressionParamDto.getParamValue());//现在paramValue 是变量转换成变量中的值
                        if(null != paramVal) {
                            handleExpressionParamDto.setParamValue(paramVal);
                        } else {
                            handleExpressionParamDto.setParamValue(null);
                        }
                    }
                }
                String jsonStr = JSON.toJSONString(handleExpressionParamDtoList);
                map.put("wfArgs",jsonStr);
                log.debug("wfArgs的值为：{}",jsonStr);
            } else {
                map.put("wfArgs","");
            }
        }

    }

    public static String setFlowNodeConditionExpression(Collection<SequenceFlow> outgoings,Map<String, Object> map,String flowNodeType) {
        //遍历所有的出线--找到可以正确执行的那一条
        for (SequenceFlow sequenceFlow : outgoings) {
            ConditionExpression conditionExpression = sequenceFlow.getConditionExpression();
            String expression = conditionExpression != null ?conditionExpression.getTextContent():null;
            //如果只是找下一个出线对应的节点的话
            if(flowNodeType.equals(FlowNodeTypeEnum.ONLY_NEXT_NODE.getType())){
                return expression;
            }
            if(StringUtils.isBlank(expression)) {
                continue;
            }
            //要么有表达式，且为true，要么为空
            //用标识判断，如果为true时，需要检验表达式，否则就是找全部的出线后续节点
            if (checkFelResult(map, expression.substring(expression.indexOf("{") + 1, expression.lastIndexOf("}")))) {
                handleExpressionParam(expression,map);
                return expression;
            }
        }
        return null;
    }

    private static void handleFlowNode(Collection<FlowNode> flowNodes,Collection<SequenceFlow> outgoings,Map<String, Object> map,
                                       List<FlowNode> data,Boolean isCheckExpression,String flowNodeType){
        //遍历所有的出线--找到可以正确执行的那一条
        for (SequenceFlow sequenceFlow : outgoings) {
            //如果只是找下一个出线对应的节点的话
            if(flowNodeType.equals(FlowNodeTypeEnum.ONLY_NEXT_NODE.getType())){
                FlowNode targetFlowNode = sequenceFlow.getTarget();
                data.add(targetFlowNode);
                continue;
            }
            ConditionExpression conditionExpression = sequenceFlow.getConditionExpression();
            String expression = conditionExpression != null ?conditionExpression.getTextContent():null;
            System.out.println("expression" + expression);
            handleExpressionParam(expression,map);
            //要么有表达式，且为true，要么为空
            //用标识判断，如果为true时，需要检验表达式，否则就是找全部的出线后续节点
            if (expression == null || (isCheckExpression ? checkFelResult(map, expression.substring(expression.indexOf("{") + 1, expression.lastIndexOf("}"))): false) ) {
                //出线的下一节点
                String nextFlowElementID = sequenceFlow.getTarget().getId();
                /*if (checkSubProcess(nextFlowElementID, flowNodes, data)) {
                    continue;
                }*/


                //查询下一节点的信息
                FlowNode nextFlowNode = getFlowNodeById(nextFlowElementID, flowNodes);
                //调用流程
                if (nextFlowNode instanceof CallActivity) {
                    CallActivity callActivity = (CallActivity) nextFlowNode;
                    //这玩意是bpmn定义的东西，camunda使用了自己的扩展实现collection和element Varable的方式
                    //ModelElementInstance multiInstanceLoopCharacteristics = ca.getUniqueChildElementByType(MultiInstanceLoopCharacteristics.class);
                    /*if (ca.getLoopCharacteristics() != null) {
                        //if (multiInstanceLoopCharacteristics != null) {
                        UserTask userTask = (UserTask)ca;
                        //userTaskMap.put(TaskTypeEnum.MULTI_TASK.getType(),userTask);
                        data.add(userTask);
                    }
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);*/
                    log.info("callActivity的ID：{}",callActivity.getId());
                    data.add(callActivity);
                }
                //内嵌子流程
                else if (nextFlowNode instanceof SubProcess) {
                    SubProcess subProcess = (SubProcess) nextFlowNode;
                    log.info("subProcess的ID：{}",subProcess.getId());
                    data.add(subProcess);
                }
                //用户任务
                else if (nextFlowNode instanceof UserTask) {
                    UserTask userTask = (UserTask) nextFlowNode;
                    log.info("用户任务的ID：{}",userTask.getId());
                    data.add(userTask);
                }
                //排他网关
                else if (nextFlowNode instanceof ExclusiveGateway) {
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);
                }
                //并行网关
                else if (nextFlowNode instanceof ParallelGateway) {
                    //这种我们暂时不支持
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);
                }
                //接收任务
                else if (nextFlowNode instanceof ReceiveTask) {
                    //这种我们暂时不支持
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);
                }
                //服务任务
                else if (nextFlowNode instanceof ServiceTask) {
                    //这种我们暂时不支持
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);
                }
                //自定义规则任务
                else if (nextFlowNode instanceof BusinessRuleTask) {
                    BusinessRuleTask businessRuleTask = (BusinessRuleTask)nextFlowNode;
                    //需要处理自定义规则引擎的表达式解析，如DMN规则，expression规则，像外部JAVA应用这种无法解析，不做处理
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);
                }
                //子任务的起点
                else if (nextFlowNode instanceof StartEvent) {
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);
                }
                //结束节点
                else if (nextFlowNode instanceof EndEvent) {
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);
                }else{
                    next(flowNodes, nextFlowNode, map, data,flowNodeType);
                }
            }
        }
    }

    /**
     * 判断是否是多实例子流程并且需要设置集合类型变量
     */
    public static boolean checkSubProcess(String Id, Collection<FlowNode> flowNodes, List<FlowNode> data) {
        for (FlowNode flowNode : flowNodes) {
            if (flowNode instanceof SubProcess && flowNode.getId().equals(Id)) {
                SubProcess subProcess = (SubProcess) flowNode;
                if (subProcess.getLoopCharacteristics() != null) {
                    /*UserTask userTask = (UserTask)subProcess;
                    data.add(userTask);*/
                    data.add(subProcess);
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 是否当前节点是否是子流程中的结点，如果是就返回子流程；
     * 主要作用是判断当前节点是子流程的结束节点时，返回子流程当前节点，然后继续去找他下一个任务节点
     * @param flowNodes
     * @param flowNode
     * @return
     */
    public static FlowNode checkCurrectNodeIsSubProcess(Collection<FlowNode> flowNodes, FlowNode flowNode) {
        for (FlowNode flowNodeInner : flowNodes) {
            if (flowNodeInner instanceof SubProcess) {
                for (FlowElement flowElement : ((SubProcess) flowNodeInner).getFlowElements()) {
                    //排除掉其他元素，只取节点元素
                    if(flowElement instanceof FlowNode){
                        FlowNode flowNodeInnerSub = (FlowNode)flowElement;
                        if (flowNode.equals(flowNodeInnerSub)) {
                            return flowNodeInner;
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * 根据ID查询流程节点对象, 如果是子任务，则返回子任务的开始节点
     * @param id 节点ID
     * @param flowNodes 流程节点集合
     * @return
     */
    public static FlowNode getFlowNodeById(String id, Collection<FlowNode> flowNodes) {
        for (FlowNode flowNode : flowNodes) {
            if (flowNode.getId().equals(id)) {
                //如果是子任务，则查询出子任务的开始节点
                if (flowNode instanceof SubProcess) {
                    Collection<FlowElement> flowElements = ((SubProcess) flowNode).getFlowElements();
                    return getStartFlowNode(((SubProcess) flowNode).getFlowElements());
                }
                return flowNode;
            }
            if (flowNode instanceof SubProcess) {
                Collection<FlowElement> flowElements = ((SubProcess) flowNode).getFlowElements();
                FlowNode flowNodeSub = getFlowNodeById(id, getFlowNodes(flowElements));
                if (flowNodeSub != null) {
                    return flowNodeSub;
                }
            }
        }
        return null;
    }

    private static Collection<FlowNode> getFlowNodes(Collection<FlowElement> flowElements){
        Collection<FlowNode> flowNodes = CollectionUtil.newArrayList();
        for (FlowElement flowElement:flowElements) {
            //排除掉其他元素，只取节点元素
            if(flowElement instanceof FlowNode){
                FlowNode flowNode = (FlowNode)flowElement;
                flowNodes.add(flowNode);
            }
        }
        return flowNodes;
    }

    /**
     * 返回流程的开始节点
     * @param flowElements 节点集合
     * @description:
     */
    public static FlowNode getStartFlowNode(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {

            if (flowElement instanceof StartEvent) {
                return (FlowNode)flowElement;
            }
        }
        return null;
    }

    /**
     * 校验Fel表达式的结果 该方法给条件表达式使用
     * @param map
     * @param expression
     * @return
     */
    public static Boolean checkFelResult(Map<String, Object> map, String expression) {
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        //因为表达式的结果是布尔类型的，我们做强转
        Boolean flag = false;
        try {
            SpecialSign specialSign = handSpecialSign(map, expression);
            String expressions = specialSign.getExpression();
            Map<String, Object> variables = specialSign.getMap();
            map.putAll(variables);
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                ctx.set(entry.getKey(), entry.getValue());
            }
            String vars  = JSONUtil.toJsonStr(map);
            log.info("============表达式解析参数为===========：{}",vars);
            Object result = fel.eval(expressions);
            flag = Boolean.valueOf(String.valueOf(result));
        }catch (Exception e){
            log.error("fel校验结果强转失败,表达式为：{} 结果为：{}",expression,flag);
            throw new IncloudException("fel校验结果强转失败,表达式为："+expression);
        }
        log.info("fel校验表达式：{} 结果为：{}",expression,flag);

        return flag;
    }

    /**
     * 获取表达式结果  该方法给人员表达式使用
     * @param map
     * @param expression
     * @return
     */
    public static List<UserExpressionVO> getResult(Map<String, Object> map, String expression){
        FelEngine fel = new FelEngineImpl();
        FelContext ctx = fel.getContext();
        List<UserExpressionVO> list = new ArrayList<>();
        Object result = null;
        try {
            SpecialSign specialSign = handSpecialSign(map, expression);
            String expressions = specialSign.getExpression();
            Map<String, Object> variables = specialSign.getMap();
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                ctx.set(entry.getKey(), entry.getValue());
            }
            map.putAll(variables);
            result = fel.eval(expressions);
            if(ObjectUtil.isNull(result)){
                log.error("fel校验结果强转失败：{}",result);
                return list;
            }
            log.debug("获取表达式结果：返回值：" + result);
            list = (List<UserExpressionVO>)result;
        }catch (Exception e){
            log.error("fel校验结果强转失败：{}",result);
        }
        return list;

    }

    /*public static List<UserExpressionVO> getResult(Map<String, Object> map, String expression){
        org.camunda.feel.FeelEngine engine = new org.camunda.feel.FeelEngine.Builder()
                .valueMapper(SpiServiceLoader.loadValueMapper())
                .functionProvider(SpiServiceLoader.loadFunctionProvider())
                .enableExternalFunctions(true)
                .build();
        List<UserExpressionVO> list = null;
        try {
            SpecialSign specialSign = handSpecialSign(map, expression);
            String expressions = specialSign.getExpression();
            Map<String, Object> variables = specialSign.getMap();
            Either<org.camunda.feel.FeelEngine.Failure, Object> result = engine.evalExpression(expressions,  variables);
            if (result.isRight()) {
                Object value = result.right().get();
                if(ObjectUtil.isNotEmpty(value)){
                    if(value instanceof List){
                        list = (List<UserExpressionVO>) value;
                    }
                }
                log.info("result is " + list);
            } else {
                final org.camunda.feel.FeelEngine.Failure failure = result.left().get();
                throw new RuntimeException(failure.message());
            }
        }catch (Exception e){
            log.error("转换失败 -> {}",e.getMessage());
            throw new IncloudException("fel校验结果强转失败,表达式为："+expression);
        }
        return list;

    }*/

    /*public static Boolean feel(Map<String, Object> variables, String expression) {
        Boolean results = false;
        org.camunda.feel.FeelEngine engine = new org.camunda.feel.FeelEngine.Builder()
                .valueMapper(SpiServiceLoader.loadValueMapper())
                .functionProvider(SpiServiceLoader.loadFunctionProvider())
                .enableExternalFunctions(true)
                .build();
        try {
            SpecialSign specialSign = handSpecialSign(variables, expression);
            String expressions = specialSign.getExpression();
            Map<String, Object> map = specialSign.getMap();
            Either<org.camunda.feel.FeelEngine.Failure, Object> result = engine.evalExpression(expressions,  map);
            if (result.isRight()) {
                Object value = result.right().get();
                if(ObjectUtil.isNotEmpty(value)){
                    if(value instanceof Boolean){
                        results = Boolean.valueOf(String.valueOf(value));
                    }
                }
                log.info("result is " + results);
            } else {
                final org.camunda.feel.FeelEngine.Failure failure = result.left().get();
                throw new RuntimeException(failure.message());
            }
        }catch (Exception e){
            log.error("转换失败 -> {}",e.getMessage());
            throw new IncloudException("fel校验结果强转失败,表达式为："+expression);
        }
        return results;
    }*/

    @Data
    @AllArgsConstructor
    static
    public class SpecialSign{
        private Map<String, Object> map;
        private String expression;
    }

    private final static String prefix_before = "camunda_";
    private final static String prefix_after = "_camunda";
    private final static String link = "_";

    public static SpecialSign handSpecialSign(Map<String, Object> variables, String expression){
        Map<String, Object> map = new HashMap<>();
        map.putAll(variables);
        List<String> oldKeys = new ArrayList<>();
        //遍历出旧变量
        variables.forEach((k,v)->{
            if(StrUtil.contains(k,".")){
                String linkKey = StrUtil.replace(k, ".", link);
                oldKeys.add(linkKey);
            }
        });
        oldKeys.forEach(d-> {variables.remove(d);});
        for (Map.Entry entry:variables.entrySet()){
            String key = entry.getKey().toString();
            String prefixKey= key;
            if(StrUtil.containsAny(key,"[", "]")){
                prefixKey = StrUtil.replace(key, "[", prefix_before);
                prefixKey = StrUtil.replace(prefixKey, "]", prefix_after);
                expression = StrUtil.replace(expression,key,prefixKey);
            }
            String linkKey = prefixKey;
            if(StrUtil.contains(prefixKey,".")){
                linkKey = StrUtil.replace(prefixKey, ".", link);
                expression = StrUtil.replace(expression,prefixKey,linkKey);
            }
            map.put(linkKey,entry.getValue());
        }
        handExpressionVar(expression, map);
        SpecialSign specialSign = new SpecialSign(map,expression);
        return specialSign;
    }

    /**
     * 处理表达式中动态参数，转为变量的形式
     * 表达式例子：${wfUserExpression.notify(taskId ,'getUserByDeptIdAndPostCode','[{"paramId":"testlist.deptId","paramValue":"1465530385621323791","paramType":0},{"paramId":"testlist.postCode","paramValue":null,"paramType":1}]',wfArgVarSqu1)}
     * @param expression
     * @param variable
     * @return
     */
    static void handExpressionVar(String expression,Map<String, Object> variable){
        if(StrUtil.containsAny(expression,"[", "]")){
            List<HandleExpressionParam> result = new ArrayList<>();
            //获取一下对应的sequVar表达式；
            String[] vars = StrUtil.subBetweenAll(expression, ",", ")");
            if(ObjectUtil.isNull(vars) || vars.length <1){
                throw new IncloudException("表达式中获取var参数出错，请检查表达式！");
            }
            String key = vars[vars.length - 1];
            String strings = StrUtil.subBetween(expression, "[", "]");
            String paramExpression = "[" + strings + "]";
            try {
                List<HandleExpressionParam> handleExpressionParamDtoList = JSONObject.parseArray(paramExpression, HandleExpressionParam.class);
                for (HandleExpressionParam handleExpressionParamDto : handleExpressionParamDtoList){
                    String paramType = handleExpressionParamDto.getParamType();
                    //只要是非手工填写的，都要处理
                    if(!paramType.equals(ExpreParamSourceEnum.HAND_FIELD.getType())){
                        /*Object o = variable.get(handleExpressionParamDto.getParamId());*/
                        //这里取的是value，value值不是一个真实的值，而是一个变量值
                        Object o = variable.get(handleExpressionParamDto.getParamValue());
                        if(ObjectUtil.isNull(o)){
                            throw new IncloudException("变量：{} 未找到相应的值，请检查表达式！",handleExpressionParamDto.getParamId());
                        }
                        handleExpressionParamDto.setParamValue(o);
                        result.add(handleExpressionParamDto);
                    }else {
                        result.add(handleExpressionParamDto);
                    }
                }
                String newParamExpression = JacksonUtil.toJSONString(result);
                variable.put(key,newParamExpression);
            }catch (Exception e){
                throw new IncloudException("表达式转换失败！");
            }
        }
        //todo  XHL 注释 由于可能表达式不存在[]  而是直接 1==3 这种表达式
//        else {
//            throw new IncloudException("表达式参数错误！");
//        }
    }

    /**
     * 获取表达式结果
     * @param map
     * @param expression
     * @return
     */
    public static List<UserExpressionVO> getResults(Map<String, Object> map, String expression){
        ExpressionFactory factory = new de.odysseus.el.ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        for (String key : map.keySet()) {
            if (ObjectUtil.isNotEmpty(map.get(key))) {
                context.setVariable(key, factory.createValueExpression(map.get(key), map.get(key).getClass()));
            }
        }
        Class<?> expectedType = factory.createValueExpression(context, expression, Object.class).getExpectedType();
        MethodExpression methodExpression = factory.createMethodExpression(context, expression, Object.class, new Class[]{});
        List<Object> ll = new ArrayList<>();
        ll.add("1111");
        ll.add(1);
        ll.add("asdfadf");
        Object invoke = methodExpression.invoke(context, ll.toArray());
        ValueExpression valueExpression = factory.createValueExpression(context, expression, String.class);
        Object result = valueExpression.getValue(context);
        List<UserExpressionVO> list = new ArrayList<>();
        try {
            list = (List<UserExpressionVO>)result;
        }catch (Exception e){
            log.error("juel结果强转失败：{}",result);
        }
        return list;

    }

    /**
     * 校验Fel表达式的结果
     * @param map
     * @param inputName
     * @return
     */
    public static Boolean checkFeelExpression(Map<String, Object> map, String inputName) {
        FeelEngineFactory feelEngineFactory = new FeelEngineFactoryImpl();
        FeelEngine instance = feelEngineFactory.createInstance();


        boolean result = instance.evaluateSimpleUnaryTests("",inputName,null);
        return result;
    }

    /**
     * 这段来自于camunda官方论坛的一个人的方案，但太简单，而且是自已处理的一元的表达式，并没有考虑网关等其他节点因素；
     * @param node
     * @param vars
     * @return
     */
    private List<UserTask> getOutgoingTask(FlowNode node, Map<String, Object> vars) {
        List<UserTask> possibleTasks = new ArrayList<>();
        for(SequenceFlow sf: node.getOutgoing()) {
            if (sf.getName() != null) {
                log.info("Entering flow node {}", sf.getName());
            }
            boolean next = true;
            if (sf.getConditionExpression() != null) {
                ExpressionFactory factory = new ExpressionFactoryImpl();
                SimpleContext context = new SimpleContext(new SimpleResolver());

                log.info("Generating map vars {}", vars.toString());
                for (Map.Entry<String, Object> v : vars.entrySet()) {
                    if(v.getValue() instanceof Boolean) {
                        factory.createValueExpression(context, "${"+ v.getKey() +"}", Boolean.class).setValue(context, v.getValue());
                    }else if(v.getValue() instanceof Date) {
                        factory.createValueExpression(context, "${"+ v.getKey() +"}", Date.class).setValue(context, v.getValue());
                    }else {
                        factory.createValueExpression(context, "${"+ v.getKey() +"}", String.class).setValue(context, v.getValue());
                    }
                }
                ValueExpression expr1 = factory.createValueExpression(context, sf.getConditionExpression().getTextContent(), boolean.class);

                next = (Boolean)expr1.getValue(context);
                log.info("Evaluating expression {} to result {}", sf.getConditionExpression().getTextContent(), expr1.getValue(context));

            }

            if (next && sf.getTarget() != null) {
                if (sf.getTarget() instanceof UserTask) {
                    log.info("Found next user task {}", sf.getTarget().getName());
                    possibleTasks.add((UserTask)sf.getTarget());
                    break;
                }

                possibleTasks.addAll(getOutgoingTask(sf.getTarget(), vars));
            }

        }
        return possibleTasks;
    }
}
