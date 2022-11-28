package com.netwisd.base.mdm.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.dto.*;
import com.netwisd.base.common.mdm.vo.*;
import com.netwisd.base.mdm.dto.MdmSortDto;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.service.*;
import com.netwisd.base.mdm.service.impl.JGSmsServices;
import com.netwisd.base.mdm.service.impl.RedisQyWeChatCodeServices;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.anntation.IncludeAnntation;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 云数网讯 limengzheng@netwisd.com
 * @Description $对外提供的restFull接口 功能描述...
 * @date 2021-10-18 10:45:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Api(value = "api", tags = "对外提供的restFull接口")
@Slf4j
public class MdmApiController {

    private final MdmOrgService mdmOrgService;

    private final MdmUserService mdmUserService;

    private final MdmPostService mdmPostService;

    private final MdmDutyService mdmDutyService;

    private final MdmPostUserService mdmPostUserService;

    private final MdmDutyUserService mdmDutyUserService;

    private final NeoService neoService;

    private final MdmResourceService mdmResourceService;

    private final RedisQyWeChatCodeServices redisQyWeChatCodeServices;

    private final JGSmsServices jGSmsServices;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MdmUserDeviceService mdmUserDeviceService;

    private final MdmDeviceBindUserService mdmDeviceBindUserService;

    @Autowired
    private Mapper dozerMapper;

    /**
     * 查询组织
     *
     * @param mdmOrgDto 组织
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/queryOrgs")
    public Result<List<MdmOrgAllVo>> queryOrgs(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL),
            include = @IncludeAnntation(vars = {"isDefault"}))
                                               @RequestBody MdmOrgDto mdmOrgDto) {
        List<MdmOrgAllVo> list = mdmOrgService.list(mdmOrgDto);
        log.debug("查询条数:" + list.size());
        return Result.success(list);
    }

    /**
     * 查询岗位
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询岗位", notes = "查询岗位")
    @PostMapping("/queryPosts")
    public Result<List<MdmPostVo>> queryPosts(@RequestBody MdmPostDto mdmPostDto) {
        List<MdmPostVo> mdmPostVos = mdmPostService.lists(mdmPostDto);
        log.debug("查询条数:" + mdmPostVos.size());
        return Result.success(mdmPostVos);
    }

    /**
     * 查询职务
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询职务", notes = "查询职务")
    @PostMapping("/queryDutys")
    public Result<List<MdmDutyVo>> queryDutys(@RequestBody MdmDutyDto mdmDutyDto) {
        List<MdmDutyVo> mdmDutyVos = mdmDutyService.lists(mdmDutyDto);
        log.debug("查询条数:" + mdmDutyVos.size());
        return Result.success(mdmDutyVos);
    }

    /**
     * 查询用户
     *
     * @param mdmUserDto 查询用户
     * @return
     */
    @ApiOperation(value = "查询用户", notes = "查询用户")
    @PostMapping("/queryUsers")
    public Result queryUsers(@RequestBody MdmUserDto mdmUserDto) {
        List<MdmUserVo> list = mdmUserService.lists(mdmUserDto);
        return Result.success(list);
    }

    /**
     * 根据用户id 查询用户岗位信息
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据用户id 查询用户岗位信息", notes = "根据用户id 查询用户岗位信息")
    @GetMapping("/queryPostByUserId/{id}")
    public Result getPostByUserId(@PathVariable("id") String id) {
        List<MdmPostUserVo> list = mdmPostUserService.getPostByUserId(id);
        log.debug("查询成功");
        return Result.success(list);
    }

    /**
     * 根据用户id 查询用户职务信息
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据用户id 查询用户职务信息", notes = "根据用户id 查询用户职务信息")
    @GetMapping("/queryDutyByUserId/{id}")
    public Result queryDutyByUserId(@PathVariable("id") String id) {
        List<MdmDutyUserVo> list = mdmDutyUserService.getDutyByUserId(id);
        log.debug("查询成功");
        return Result.success(list);
    }

    /**
     * 发送模板短信-验证码
     *
     * @return Result
     */
    @ApiOperation(value = "发送模板短信-验证码", notes = "发送模板短信-验证码")
    @GetMapping("/sendSMSCode")
    public Result sendSMSCode(String phoneNumber) throws Exception {
        return Result.success(jGSmsServices.sendSMSCode(phoneNumber));
    }

    /**
     * 发送模板短信-验证码
     *
     * @return Result
     */
    @ApiOperation(value = "发送模板短信-验证码", notes = "发送模板短信-验证码")
    @GetMapping("/verificationCode")
    public Result verificationCode(String phoneNumber, String code) throws Exception {
        return Result.success(jGSmsServices.verificationCode(phoneNumber, code));
    }


    /**
     * Test
     *
     * @return Result
     */
    @ApiOperation(value = "Test", notes = "Test")
    @GetMapping("/test")
    public Result test() throws Exception {
//        String aa = bCryptPasswordEncoder.encode("Netwisd");
//        log.debug(">>>>>>>>>>>>>>:" + aa);
//        log.debug("===:" + bCryptPasswordEncoder.matches("Netwisd","$2a$10$eKsL5twOYgNePPllW5nze.rXHSTu8qFeW2zMQj.yzKqUT81vaoraa"));
//        Result.success(redisQyWeChatCodeServices.getAccessToken());
        MdmUserDeviceDto mdmUserDeviceDto = new MdmUserDeviceDto();
        mdmUserDeviceDto.setDeviceName("hhhh");
        mdmUserDeviceDto.setDeviceModel("tt");
        mdmUserDeviceDto.setDeviceType("IOS");
        mdmUserDeviceDto.setLastLoginTime(LocalDateTime.now());
        mdmUserDeviceDto.setDeviceFlag("1234567");
        mdmUserDeviceService.save(mdmUserDeviceDto);
        return Result.success();
    }

    //根据手机号获取用户信息
    @GetMapping(value = "/findByPhone", params = "phone")
    public MdmUserVo findByPhone(@RequestParam("phone") String phone) {
        log.info("根据手机号获取用户信息:{}", phone);
        MdmUser mdmUser = mdmUserService.findByPhone(phone);
        MdmUserVo mdmUserVo = null;
        if (null != mdmUser) {
            mdmUserVo = dozerMapper.map(mdmUser, MdmUserVo.class);
        }
        return mdmUserVo;
    }

    //通过手机号查人用于手机号登录（1个手机号多个人报错）
    @GetMapping(value = "/findByPhoneForMsg", params = "phone")
    public MdmUserVo findByPhoneForMsg(@RequestParam("phone") String phone) {
        log.info("通过手机号查人用于手机号登录:{}", phone);
        MdmUser mdmUser = mdmUserService.findByPhoneForMsg(phone);
        MdmUserVo mdmUserVo = null;
        if (null != mdmUser) {
            mdmUserVo = dozerMapper.map(mdmUser, MdmUserVo.class);
        }
        return mdmUserVo;
    }

    //获取所有用户信息（szx）
    @GetMapping(value = "/user/list")
    public List<MdmUserVo> userLists() {
        return mdmUserService.list()
                .stream()
                .map(x -> dozerMapper.map(x, MdmUserVo.class))
                .collect(Collectors.toList());
    }
}
