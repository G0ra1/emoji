package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmUserDeviceDto;
import com.netwisd.base.common.mdm.vo.MdmSysVo;
import com.netwisd.base.common.mdm.vo.MdmUserDeviceVo;
import com.netwisd.base.mdm.dto.MdmSysDto;
import com.netwisd.base.mdm.entity.MdmUserDevice;
import com.netwisd.base.mdm.service.MdmSysService;
import com.netwisd.base.mdm.service.MdmUserDeviceService;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 移动设备 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userDevice" )
@Api(value = "userDevice", tags = "移动设备Controller")
@Slf4j
public class MdmUserDeviceController {

    private final MdmUserDeviceService mdmUserDeviceService;

    /**
     * 保存用户信息
     * @param mdmUserDeviceDto
     * @return
     */
    @ApiOperation(value = "保存用户信息", notes = "保存用户信息")
    @PostMapping("/save" )
    public Boolean list(@RequestBody MdmUserDeviceDto mdmUserDeviceDto) {
        Boolean bool = mdmUserDeviceService.save(mdmUserDeviceDto);
        return bool;
    }

    /**
     * 通过用户id 查询设备信息
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过用户id 查询设备信息", notes = "通过用户id 查询设备信息")
    @GetMapping("/{id}" )
    public MdmUserDeviceVo get(@PathVariable("id") Long id) {
        MdmUserDeviceVo mdmUserDeviceVo = mdmUserDeviceService.getDeviceByUserId(id);
        log.debug("查询成功");
        return mdmUserDeviceVo;
    }

}
