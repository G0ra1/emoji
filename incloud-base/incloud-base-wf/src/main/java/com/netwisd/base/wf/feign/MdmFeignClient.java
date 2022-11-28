package com.netwisd.base.wf.feign;

import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.vo.expression.UserExpressionVO;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description 主数据的feign调用
 * @date 2022/1/18 11:25
 */
@FeignClient(value = "incloud-base-mdm")
public interface MdmFeignClient {
    //表达式相关的通用解析 -----start-----
    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = false)
    @RequestMapping(value = "/userExpression/expressionParser", method = RequestMethod.POST)
    Result<List<UserExpressionVO>> expressionUserParser(@RequestBody ExpressionEntity expressionEntity);
    //表达式相关的通用解析 -----end-----

    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @RequestMapping(value = "/mdmUser/getUsersForCache", method = RequestMethod.POST)
    Map getUsersForCache();

    //查询所有用户
    @GetMapping(value = "/api/user/list")
    List<MdmUserVo> getAllUserList();
}
