package com.netwisd.base.wf;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.netwisd.base.wf.util.FlowUtils;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.util.JacksonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/30 11:44 上午
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class MainTest {
    @Test
    public void test(){
        /*TestDto testDto = new TestDto();
        testDto.setId(1l);
        testDto.setCreateTime(null);
        String s = JacksonUtil.toJSONString(testDto);
        TestDto dto = JacksonUtil.parseObject(s, TestDto.class);
        log.info("id:{},createTime:{},updateTime:{},page:{}", dto.getId(),dto.getCreateTime(),dto.getUpdateTime(),dto.getPage());

        TestDto dto1 = new TestDto();
        log.info("id:{},createTime:{},updateTime:{},page:{}", dto1.getId(),dto1.getCreateTime(),dto1.getUpdateTime(),dto1.getPage());

        TestDto dto2 = new TestDto(Args.NULL_ID);
        log.info("id:{},createTime:{},updateTime:{},page:{}", dto2.getId(),dto2.getCreateTime(),dto2.getUpdateTime(),dto2.getPage());*/

        /*String str = "${wfUserExpression.notify(taskId ,'getUserByDeptIdAndPostCode','[{\"paramId\":\"testlist.deptId\",\"paramValue\":\"1465530385621323791\",\"paramType\":0},{\"paramId\":\"testlist.postCode\",\"paramValue\":null,\"paramType\":1}]',wfArgVarSqu1)}";
        Map<String,Object> variable = new HashMap<>();
        variable.put("taskId","111");
        variable.put("testlist.postCode","111");

        String s = StrUtil.subAfter(str, "${", false);
        String s1 = StrUtil.subBefore(s, "}", true);

        *//*FlowUtils.SpecialSign specialSign = FlowUtils.handSpecialSign(variable, str);*//*
        log.info("strings:{}",s1);*/

    }

    @Test
    public void jsonTest(){
        String json = ResourceUtil.readUtf8Str("expression.json");
        List<Expression> expressions = JacksonUtil.parseObject(json, new TypeReference<List<Expression>>() {});
        Map<String,Object> variables = new HashMap<>();
        variables.put("userId","zhangsan");
        Map<String,Object> args = new HashMap<>();
        for (Expression expression : expressions){
            String paramId = expression.getParamId();
            Object value = expression.getParamValue();
            if(expression.getParamType() == 2){
                if(variables.containsKey(paramId)){
                    value = variables.get(paramId);
                }
            }
            args.put(paramId,value);
        }
        log.info("args:{}",args);
    }
}

@Data
class Expression implements Serializable {
    private String paramId;
    private Object paramValue;
    private Integer paramType;

    @Override
    public String toString() {
        return "Expression{" +
                "paramId='" + paramId + '\'' +
                ", paramValue=" + paramValue +
                ", paramType=" + paramType +
                '}';
    }
}
