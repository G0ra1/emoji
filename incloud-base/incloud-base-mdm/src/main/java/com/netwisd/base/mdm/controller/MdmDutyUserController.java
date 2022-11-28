package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.entity.MdmDutyUser;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.common.mdm.dto.MdmDutyUserDto;
import com.netwisd.base.common.mdm.vo.MdmDutyUserVo;
import com.netwisd.base.mdm.service.MdmDutyUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmDutyUser" )
@Api(value = "mdmDutyUser", tags = "职务与用户关系Controller")
@Slf4j
public class MdmDutyUserController {

    private final  MdmDutyUserService mdmDutyUserService;

    /**
     * 分页查询职务与用户关系
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmDutyUserDto 职务与用户关系
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmDutyUserDto mdmDutyUserDto) {
        Page pageVo = mdmDutyUserService.list(mdmDutyUserDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询职务与用户关系
     * @param mdmDutyUserDto 职务与用户关系
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody MdmDutyUserDto mdmDutyUserDto) {
        List<MdmDutyUserVo> list = mdmDutyUserService.lists(mdmDutyUserDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * 通过id查询职务与用户关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmDutyUserVo> get(@PathVariable("id" ) Long id) {
        MdmDutyUserVo mdmDutyUserVo = mdmDutyUserService.get(id);
        log.debug("查询成功");
        return Result.success(mdmDutyUserVo);
    }

    /**
     * 新增职务与用户关系
     * @param mdmDutyUserDto 职务与用户关系
     * @return Result
     */
    @ApiOperation(value = "新增职务与用户关系", notes = "新增职务与用户关系")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmDutyUserDto mdmDutyUserDto) {
        Boolean result = mdmDutyUserService.save(mdmDutyUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改职务与用户关系
     * @param mdmDutyUserDto 职务与用户关系
     * @return Result
     */
    @ApiOperation(value = "修改职务与用户关系", notes = "修改职务与用户关系")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmDutyUserDto mdmDutyUserDto) {
        Boolean result = mdmDutyUserService.update(mdmDutyUserDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除职务与用户关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除职务与用户关系", notes = "通过id删除职务与用户关系")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmDutyUserService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过id查询职务与用户关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据职务id查询职务人员信息", notes = "根据职务id查询职务人员信息")
    @GetMapping("/user/getUserByDutyId/{id}")
    public Result<List<MdmDutyUserVo>> getUser(@PathVariable("id" ) Long id) {
        List<MdmDutyUserVo> mdmDutyUserVo = mdmDutyUserService.getUserByDutyId(id);
        log.debug("查询成功");
        return Result.success(mdmDutyUserVo);
    }

    /**
     * 根据用户id 查询用户职务信息
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据用户id 查询用户职务信息", notes = "根据用户id 查询用户职务信息")
    @GetMapping("/duty/getDutyByUserId/{id}")
    public Result getDutyByUserId(@PathVariable("id" ) String id) {
        List<MdmDutyUserVo> list = mdmDutyUserService.getDutyByUserId(id);
        log.debug("查询成功");
        return Result.success(list);
    }

}
