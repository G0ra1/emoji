package com.netwisd.base.wf.feign;

import com.netwisd.base.common.user.dto.ExpressionParamDTO;
import com.netwisd.base.common.user.vo.EmplVO;
import com.netwisd.base.common.user.vo.PostVO;
import com.netwisd.base.common.user.vo.expression.ExpressionVO;
import com.netwisd.base.common.user.vo.expression.UserExpressionVO;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/21 11:12 上午
 */
@FeignClient(value = "incloud-base-user")
public interface UserClient {

    @ApiOperation(value = "执行表达式", notes = "执行表达式")
    @RequestMapping(value = "/userExpression/execute", method = RequestMethod.POST)
    public List<UserExpressionVO> expressionParser(@RequestBody ExpressionParamDTO expressionParam);

    @ApiOperation(value = "执行表达式", notes = "执行表达式")
    @RequestMapping(value = "/customizeExpression/execute", method = RequestMethod.POST)
    public Object customexpressionParser(@RequestBody ExpressionParamDTO expressionParam);

    @ApiOperation(value = "获取指定ID的用户", notes = "获取指定ID的用户")
    @GetMapping(value = "/api/empl/getEmplByUserIds")
    public List<EmplVO> getEmplByUserId(@RequestParam(value = "userIds") String userIds);

    @ApiOperation(value = "通过人员id查找所有岗位/职位", notes = "通过人员id查找所有岗位/职位")
    @GetMapping(value = "/posts/findAllPostByEmplId/{userId}")
    public List<PostVO> findAllPostByEmplId(@PathVariable("userId") String userId);

    @ApiOperation(value = "根据身份证号码获取用户Id", notes = "根据身份证号码获取用户Id")
    @GetMapping(value = "/api/findByIdentityNumber")
    public EmplVO findByIdentityNumber(@RequestParam(value = "identityNumber")String identityNumber);

    @ApiOperation(value = "获取分级中定义表达式信息", notes = "获取分级中定义表达式信息")
    @GetMapping(value = "/expression/list")
    public Result<List<ExpressionVO>> getExpressionList(@RequestParam(value = "groupName") String groupName);
}
