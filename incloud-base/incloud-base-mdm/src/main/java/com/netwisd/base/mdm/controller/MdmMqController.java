package com.netwisd.base.mdm.controller;

import com.netwisd.base.common.mdm.dto.MdmDutyDto;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.*;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.anntation.IncludeAnntation;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description $对外提供的同步全量MQ接口 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-10-18 10:45:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mq" )
@Api(value = "mq", tags = "对外提供的同步全量MQ接口")
@Slf4j
public class MdmMqController {

    private final MdmMqService mdmMqService;


    /**
     * 同步到mq所有组织
     * @param mdmOrgDto 组织
     * @return
     */
    @ApiOperation(value = "同步到mq所有组织", notes = "同步到mq所有组织")
    @PostMapping("/syncOrgsToMq" )
    public Result<List<MdmOrgAllVo>> syncOrgsToMq(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL),
            include = @IncludeAnntation(vars = {"isDefault"}))
                                          @RequestBody MdmOrgDto mdmOrgDto) {
        return Result.success(mdmMqService.syncMqForOrgs(mdmOrgDto));
    }

    /**
     * 同步到mq所有岗位
     * @param
     * @return
     */
    @ApiOperation(value = "同步到mq所有岗位", notes = "同步到mq所有岗位")
    @PostMapping("/syncPostsToMq" )
    public Result<List<MdmPostVo>> syncPostsToMq(@RequestBody MdmPostDto mdmPostDto) {
        return Result.success(mdmMqService.syncMqForPosts(mdmPostDto));
    }

    /**
     * 同步到mq所有职务
     * @param
     * @return
     */
    @ApiOperation(value = "同步到mq所有职务", notes = "同步到mq所有职务")
    @PostMapping("/syncDutysToMq" )
    public Result<List<MdmDutyVo>> syncDutysToMq(@RequestBody MdmDutyDto mdmDutyDto) {
        return Result.success(mdmMqService.syncMqForDutys(mdmDutyDto));
    }

    /**
     * 同步到mq所有用户
     * @param mdmUserDto 查询用户
     * @return
     */
    @ApiOperation(value = "同步到mq所有用户", notes = "同步到mq所有用户")
    @PostMapping("/syncUsersToMq" )
    public Result syncUsersToMq(@RequestBody MdmUserDto mdmUserDto) {
        return Result.success(mdmMqService.syncMqForUsers(mdmUserDto));
    }
}
