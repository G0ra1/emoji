package com.netwisd.base.wf.service.impl.other;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.netwisd.base.wf.service.other.AssignmentHandlerService;
import com.netwisd.common.db.anntation.DsId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/21 11:09 上午
 */
@Service("assignmentHandlerService")
@Slf4j
public class AssignmentHandlerServiceImpl implements AssignmentHandlerService {

    /*@Autowired
    UserServiceClient userServiceClient;*/

    @Override
    public String getAssignment(String starter) {
        log.info("Service处理---用户名：{}",starter);
        //模拟个取用户的逻辑
        log.info("Service处理---模拟个取用户的逻辑userName:{}",starter);
        return starter;
    }

    @Override
    public String getAssignmentByDeptID(Long deptid) {
        log.info("Service处理---模拟一个给定的部门ID:{}",deptid);
        if(deptid == 111l){
            log.info("Service处理---返回主管lisi");
            return "lisi";
        }
        return deptid.toString();
    }

    @DS("#param")
    @Override
    public String test(@DsId String dsName){
        return "";
    }

    @Override
    public String getCandidateGroupByDeptID(String deptid) {
        log.info("Service处理---模拟一个给定的部门String 类型的ID:{}",deptid);
        if(deptid.equals("111")){
            log.info("Service处理---返回主管zhangsan");
            return "zhangsan";
        }
        return deptid.toString();
    }

    /**
     * 根据流程定义ID+节点ID去获取实际定义的表达式和参数；
     * 参数又分为：参数的变量名和参数值，参数值是在流程定义的时候赋值的，比如：根据岗位ID获取用户
     * xxx.getGangWeiByID(String ID),岗位ID会在流程定义时选择一个具体岗位，然后拿到对应的ID
     * 运行时根据这个ID去调对应的方法传值过去，通过反射调用取到运行结果，得到岗位对应的人
     * @param expressionId
     * @return
     */
    @Override
    public Map<String, Map<String,Object>> getFlowNodeExpressionAndArgValue(String expressionId){
        String key = "${assignmentHandlerService.getUserTest(java.lang.String someId)}";
        String test = StrUtil.subAfter(expressionId,"-",true);
        Map<String,Map<String,Object>> map = new HashMap();
        Map<String,Object> variabls = new HashMap();
        //只是模拟，实际这些值是维护时动态存到数据库的，这里为了完事模拟一个整个流程做的测试数据；
        /*switch (test){
            case "node1" -> variabls.put("someId","testid1");
            case "node2" -> variabls.put("someId","testid2");
            case "node3" -> variabls.put("someId","testid3");
            case "node4" -> variabls.put("someId","testid4");
            case "node5" -> variabls.put("someId","testid5");
            case "node6" -> variabls.put("someId","testid6");
            case "node7" -> variabls.put("someId","testid7");
            case "node8" -> variabls.put("someId","testid8");
            case "subnode1" -> variabls.put("someId","subid1");
            case "subnode2" -> variabls.put("someId","subid2");
            default -> throw new IllegalStateException("Unexpected value: " + test);
        };*/
        map.put(key,variabls);
        return map;
    }

    @Override
    public Integer test1(String amount) {
        log.info("amount= {}",amount);
        return Integer.valueOf(amount);
    }

    @Override
    public Integer test2(Object... params) {
        log.info("params= {}",params);
        return 50001;
    }

    //这只是一个模拟，不太恰当，实际上可能每个节点的表达式并不同
    @Override
    public String[] getUserTest(String someId) {
        String[] users ={};
        /*users = switch (someId){
            case "testid1" -> new String[]{"test"};
            case "testid2" -> new String[]{"zhangsan"};
            case "testid3" -> new String[]{"lisi"};
            case "testid4" -> new String[]{"wangwu"};
            //模拟一个多人会签
            case "testid5" -> new String[]{"zhaoliu","zhouer","zhaosan","zhaosi"};
            case "testid6" -> new String[]{"xiaoming"};
            case "testid7" -> new String[]{"laowang"};
            case "testid8" -> new String[]{"zouliming"};
            case "subid1" -> new String[]{"subuser1","subuser2"};
            case "subid2" -> new String[]{"suba","subb"};
            default -> throw new IllegalStateException("Unexpected value: " + someId);
        };*/
        return users;
    }

    @Override
    public String[] test() {
        return new String[]{"zouliming"};
    }
}
