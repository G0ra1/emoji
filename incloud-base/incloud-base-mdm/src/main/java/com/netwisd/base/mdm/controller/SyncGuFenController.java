package com.netwisd.base.mdm.controller;

import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.mdm.service.SyncGuFenService;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 同步股份数据控制层 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-06 15:54:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/syncGuFen" )
@Api(value = "syncGuFen", tags = "同步股份数据Controller")
@Slf4j
public class SyncGuFenController {

    @Autowired
    private SyncGuFenService syncGuFenService;

    /**
     * 同步组织以及岗位
     * @return Result
     */
    //todo 非增量 全部更新暂时注释掉 防止调用错误
//    @ApiOperation(value = "同步组织以及岗位", notes = "同步组织以及岗位")
//    @PostMapping("/syncGuFenOrgAndPost")
//    public Result<Boolean> syncGuFenOrgAndPost() {
//        Boolean result = syncGuFenService.syncGuFenOrgAndPost();
//        log.debug("保存成功");
//        return Result.success(result);
//    }

    /**
     * 同步组织以及岗位 -增量
     * @return Result
     */
    @ApiOperation(value = "同步组织以及岗位 -增量", notes = "同步组织以及岗位 -增量")
    @GetMapping("/syncGuFenOrgAndPostIncrement")
    public Result<Boolean> syncGuFenOrgAndPostIncrement(String curStr) {
        if(StringUtils.isBlank(curStr)) {
            curStr = DateUtil.getDate(); //获取当天
        }
        Boolean result = syncGuFenService.syncGuFenOrgAndPostIncrement(curStr);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 同步股份人员信息以及人员得主岗位信息
     * @return Result
     */
    //todo 非增量 全部更新暂时注释掉 防止调用错误
//    @ApiOperation(value = "同步股份人员信息以及人员得主岗位信息", notes = "同步股份人员信息以及人员得主岗位信息")
//    @PostMapping("/syncGuFenUserAndMasterPost")
//    public Result<Boolean> syncGuFenUserAndMasterPost() {
//        Boolean result = syncGuFenService.syncGuFenUserAndMasterPost();
//        log.debug("保存成功");
//        return Result.success(result);
//    }

    /**
     * 同步股份人员信息以及人员得主岗位信息 --增量
     * @return Result
     */
    @ApiOperation(value = "同步股份人员信息以及人员得主岗位信息  --增量", notes = "同步股份人员信息以及人员得主岗位信息  --增量")
    @GetMapping("/syncGuFenUserAndMasterPostIncrement")
    public Result<Boolean> syncGuFenUserAndMasterPostIncrement(String curStr) {
        if(StringUtils.isBlank(curStr)) {
            curStr = DateUtil.getDate(); //获取当天
        }
        Boolean result = syncGuFenService.syncGuFenUserAndMasterPostIncrement(curStr);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 同步股份兼岗信息
     * @return Result
     */
    @ApiOperation(value = "同步股份兼岗信息", notes = "同步股份兼岗信息")
    @PostMapping("/syncGuFenPosition")
    public Result<Boolean> syncGuFenPosition() {
        Boolean result = syncGuFenService.syncGuFenPosition();
        log.debug("保存成功");
        return Result.success(result);
    }


    /**
     * 同步股份字典信息
     * @return Result
     */
    @ApiOperation(value = "同步股份字典信息", notes = "同步股份字典信息")
    @PostMapping("/syncGuFenDict")
    public Result<Boolean> syncGuFenDict() {
        Boolean result = syncGuFenService.syncGuFenDict();
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 处理过期的机构信息
     * @return Result
     */
    @ApiOperation(value = "处理过期的机构信息", notes = "处理过期的机构信息")
    @GetMapping("/handleOrgPastDue")
    public Result<Boolean> handleOrgPastDue() {
        syncGuFenService.handleOrgPastDue();
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 处理过期的岗位信息
     * @return Result
     */
    @ApiOperation(value = "处理过期的岗位信息", notes = "处理过期的岗位信息")
    @GetMapping("/handlePostPastDue")
    public Result<Boolean> handlePostPastDue() {
        syncGuFenService.handlePostPastDue();
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 同步股份全部信息（机构，人员，兼岗）  --增量
     * @return Result
     */
    @ApiOperation(value = "同步股份全部信息（机构，人员，兼岗）  --增量", notes = "同步股份全部信息（机构，人员，兼岗）  --增量")
    @GetMapping("/syncGuFenAllIncrement")
    public Result<Boolean> syncGuFenAllIncrement(){
        String curStr = DateUtil.getDate();
        Boolean org = syncGuFenService.syncGuFenOrgAndPostIncrement(curStr);
        Boolean user = syncGuFenService.syncGuFenUserAndMasterPostIncrement(curStr);
        Boolean post = syncGuFenService.syncGuFenPosition();
        if (org && user && post) {
            return Result.success(true);
        }
        return Result.failed(false);
    }

}
