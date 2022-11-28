package com.netwisd.biz.asset.controller.common;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 获取登录人信息 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-06-07 16:12:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/assetLoginUser" )
@Api(value = "assetLoginUser", tags = "获取登录人信息Controller")
@Slf4j
public class LoginUserController {

    /**
     * 获取登录人信息
     * @return
     */
    @ApiOperation(value = "获取登录人信息", notes = "获取登录人信息")
    @PostMapping("/getUser" )
    public Result getUser() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        return Result.success(loginAppUser);
    }
}
