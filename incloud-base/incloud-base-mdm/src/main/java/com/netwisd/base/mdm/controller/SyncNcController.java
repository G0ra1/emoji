package com.netwisd.base.mdm.controller;

import com.netwisd.base.mdm.service.SyncNcService;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 同步用友数据控制层 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-06 15:54:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/syncNc" )
@Api(value = "syncNc", tags = "同步用友数据控制层")
@Slf4j
public class SyncNcController {

    @Autowired
    private SyncNcService syncNcService;

    /**
     * 获取用友组织信息
     * @return Result
     */
    @ApiOperation(value = "获取用友组织信息", notes = "获取用友组织信息")
    @PostMapping("/getNcOrgDatas")
    public Result getNcOrgDatas() {

        return Result.success(syncNcService.getNcOrgDatas());
    }

    /**
     * 获取用友部门信息
     * @return Result
     */
    @ApiOperation(value = "获取用友部门信息", notes = "获取用友部门信息")
    @PostMapping("/getNcDeptDatas")
    public Result getNcDeptDatas() {

        return Result.success(syncNcService.getNcDeptDatas());
    }

    /**
     * 获取用友人员信息
     * @return Result
     */
    @ApiOperation(value = "获取用友人员信息", notes = "获取用友人员信息")
    @PostMapping("/getNcPsnDatas")
    public Result getNcPsnDatas() {

        return Result.success(syncNcService.getNcPsnDatas());
    }



}
