package com.netwisd.base.mdm.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.vo.expression.UserExpressionVO;
import com.netwisd.base.mdm.service.UserExpressionService;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.base.wf.starter.expression.HandleExpressionParam;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 提供工作流表达式
 * @author 云数网讯 XHL
 * @date 2022-01-18 10:45:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userExpression" )
@Api(value = "userExpression", tags = "$提供工作流表达式")
@Slf4j
public class UserExpressionController {

    private static MethodAccess access = MethodAccess.get(UserExpressionController.class);

    @Autowired
    UserExpressionService userExpressionService;

    //根据职务获取人
    @GetMapping(value = "/getUserByDutyIds")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByDutyIds')", tags = "userExpression", response = MdmUserVo.class, notes = "根据职务获取人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dutyIds", value = "职位Id", example = "标准内置", paramType = "duty", required = true),
            @ApiImplicitParam(name = "isMaster", value = "是否主职位", example = "自定义", paramType = "integer", required = true )
    })
    public List<MdmUserVo> getUserByDutyIds(@RequestParam(value = "dutyIds") String dutyIds,
                                            @RequestParam(value = "isMaster", defaultValue = "-1") String isMaster){
        return userExpressionService.getUserByDutyIds(Integer.valueOf(isMaster), dutyIds);
    }

    //根据岗位获取人
    @GetMapping(value = "/getUserByPostIds")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByPostIds')", tags = "userExpression", response = MdmUserVo.class, notes = "根据岗位获取人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isMaster", value = "是否主岗位", example = "自定义", paramType = "integer", required = true),
            @ApiImplicitParam(name = "postIds", value = "岗位Id", example = "标准内置", paramType = "post", required = true)
    })
    public List<MdmUserVo> getUserByPostIds(@RequestParam(value = "isMaster", defaultValue = "-1") String isMaster,
                                            @RequestParam(value = "postIds") String postIds) {
        return userExpressionService.getUserByPostIds(isMaster, postIds);
    }

    //根据机构获取人
    @GetMapping(value = "/getUserByOrgIds")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByOrgIds')", tags = "userExpression", response = MdmUserVo.class, notes = "根据机构获取人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgIds", value = "机构Id", example = "标准内置", paramType = "mechanism", required = true)
    })
    public List<MdmUserVo> getUserByOrgIds(@RequestParam(value = "orgIds") String orgIds) {
        return userExpressionService.getUserByOrgIds(orgIds);
    }

    //根据部门获取人
    @GetMapping(value = "/getUserByDeptIds")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByDeptIds')", tags = "userExpression", response = MdmUserVo.class, notes = "根据部门获取人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptIds", value = "部门Id", example = "标准内置", paramType = "dept", required = true)
    })
    public List<MdmUserVo> getUserByDeptIds(@RequestParam(value = "deptIds") String deptIds) {
        return userExpressionService.getUserByDeptIds(deptIds);
    }


    //根据用户ID获取人
    @GetMapping(value = "/getUserByUserIds")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByUserIds')", tags = "userExpression", response = MdmUserVo.class, notes = "根据用户ID获取人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "人员Ids", example = "标准内置", paramType = "user", required = true)
    })
    public List<MdmUserVo> getUserByUserIds(@RequestParam(value = "userIds") String userIds) {
        return userExpressionService.getUserByUserIds(userIds);
    }

    //根据用户ID获取人 -- 取表单或者手动输入
    @GetMapping(value = "/getUserByUserIdsForCustom")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByUserIdsForCustom')", tags = "userExpression", response = MdmUserVo.class, notes = "根据用户ID获取人From表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "人员Ids", example = "标准内置", paramType = "string", required = true)
    })
    public List<MdmUserVo> getUserByUserIdsForCustom(@RequestParam(value = "userIds") String userIds) {
        return userExpressionService.getUserByUserIds(userIds);
    }

    //根据角色获取人
    @GetMapping(value = "/user/getUserByRoleIds")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByRoleIds')", tags = "userExpression", response = MdmUserVo.class, notes = "根据角色获取人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleIds", value = "角色Id", example = "标准内置", paramType = "role", required = true)
    })
    public List<MdmUserVo> getUserByRoleIds(@RequestParam(value = "roleIds") String roleIds) {
        return userExpressionService.getUserByRoleIds(roleIds);
    }

    //根据部门id和岗位编码获取人
    @GetMapping(value = "/user/getUserByDeptIdAndPostCode")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByDeptIdAndPostCode')", tags = "userExpression", response = MdmUserVo.class, notes = "根据部门id和岗位编码获取人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id", example = "自定义", paramType = "dept", required = true),
            @ApiImplicitParam(name = "postCode", value = "岗位code", example = "自定义", paramType = "post", required = true)
    })
    public List<MdmUserVo> getUserByDeptIdAndPostCode(@RequestParam(value = "deptId") String deptId,
                                                      @RequestParam(value = "postCode") String postCode) {
        return userExpressionService.getUserByDeptIdAndPostCode(deptId,postCode);
    }


    //根据流程申请人 获取申请人所在部门领导（岗位）
    @GetMapping(value = "/getDeptUserByStarterIdAndPostCode")
    @ApiOperation(value = "wfUserExpression.notify(taskId,'getUserByUserIds')", tags = "userExpression", response = MdmUserVo.class, notes = "根据流程申请人 获取申请人所在部门领导（岗位）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postCode", value = "岗位code", example = "post",required = true)
    })
    public List<MdmUserVo> getDeptUserByStarterIdAndPostCode(@RequestParam(value = "postCode") String postCode, @ApiIgnore ExpressionEntity expressionEntity) {
        return userExpressionService.getDeptUserByStarterIdAndPostCode(expressionEntity.getStarterId(),postCode);
    }

    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/expressionParser", method = RequestMethod.POST)
    public Result<List<UserExpressionVO>> expressionUserParser(@RequestBody ExpressionEntity expressionEntity) {
        log.info("UserExpressionController expressionEntity:{}", expressionEntity);
        String methodName = expressionEntity.getBizTag();
        List<HandleExpressionParam> argList = expressionEntity.getArgList();
        List<Object> list = new ArrayList<>();
        for (HandleExpressionParam handleExpressionParam : argList){
            list.add(handleExpressionParam.getParamValue());
        }
        int methodIndex = access.getIndex(methodName);
        return Result.success( (List<UserExpressionVO>) access.invoke(this, methodIndex, list.toArray()));
    }
}
