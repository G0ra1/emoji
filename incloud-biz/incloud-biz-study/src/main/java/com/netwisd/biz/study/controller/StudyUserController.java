package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.*;
import com.netwisd.biz.study.service.*;
import com.netwisd.common.core.anntation.IncludeAnntation;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.vo.StudyUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 在线学习人员表 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-11-22 09:59:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyUser" )
@Api(value = "studyUser", tags = "在线学习人员表Controller")
@Slf4j
public class StudyUserController {

    private final  StudyUserService studyUserService;

    /**
     * 分页查询在线学习人员表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyUserDto 在线学习人员表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyUserDto studyUserDto) {
        Page pageVo = studyUserService.list(studyUserDto,false);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询在线学习人员表
     * @param studyUserDto 在线学习人员表
     * @return
     */
    @ApiOperation(value = "不分页的审核通过人员", notes = "不分页的审核通过人员")
    @PostMapping("/lists" )
    public Result<List<StudyUserVo>> lists(@RequestBody StudyUserDto studyUserDto) {
        List<StudyUserVo> studyUserVos = studyUserService.lists(studyUserDto);
        log.debug("查询条数:"+studyUserVos.size());
        return Result.success(studyUserVos);
    }

    /**
     * 通过id查询在线学习人员表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyUserVo> get(@PathVariable("id" ) Long id) {
        StudyUserVo studyUserVo = studyUserService.get(id);
        log.debug("查询成功");
        return Result.success(studyUserVo);
    }

    /**
     * 通过id查询在线学习人员表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过多个id查询多个人员", notes = "通过多个id查询多个人员")
    @GetMapping("/list/{id}" )
    public Result<List<StudyUserVo>> getList(@PathVariable("id" ) String id) {
        List<StudyUserVo> studyUserVos = studyUserService.getList(id);
        log.debug("查询成功");
        return Result.success(studyUserVos);
    }

    /**
     * 新增在线学习人员表
     * @param studyUserDto 在线学习人员表
     * @return Result
     */
    @ApiOperation(value = "新增在线学习人员表", notes = "新增在线学习人员表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyUserDto studyUserDto) {
        Result<Boolean> result = studyUserService.save(studyUserDto);
        log.debug("保存成功");
        return result;
    }

    /**
     * 修改在线学习人员表
     * @param studyUserDto 在线学习人员表
     * @return Result
     */
    @ApiOperation(value = "修改在线学习人员表", notes = "修改在线学习人员表")
    @PutMapping
    public Result<Boolean> update(@Validation (include = @IncludeAnntation(vars = {"masterDataId"}))
                                      @RequestBody StudyUserDto studyUserDto) {
        Boolean result = studyUserService.update(studyUserDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除在线学习人员表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除在线学习人员表", notes = "通过id删除在线学习人员表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = studyUserService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }


    /**
     * 人员注册
     * @param studyUserDto 人员注册
     * @return Result
     */
    @ApiOperation(value = "人员注册", notes = "人员注册")
    @PostMapping("/register")
    public Result<Boolean> register(@Validation @RequestBody StudyUserDto studyUserDto) {
        Result<Boolean> result = studyUserService.register(studyUserDto);
        log.debug("保存成功");
        return result;
    }

    /**
     * 人员审核列表
     * @param studyUserDto
     * @return
     */
    @ApiOperation(value = "人员未审核列表", notes = "人员未审核列表")
    @PostMapping("/check/list" )
    public Result<Page> checkList(@RequestBody StudyUserDto studyUserDto) {
        Page pageVo = studyUserService.list(studyUserDto,true);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 人员审核通过
     * @param studyUserDto 在线学习人员表
     * @return Result
     */
    @ApiOperation(value = "人员审核通过", notes = "人员审核通过")
    @PutMapping("check/updateYes")
    public Result<Boolean> checkUpdateYes(@Validation (include = @IncludeAnntation(vars = {"userStatus"}))
                                              @RequestBody StudyUserDto studyUserDto) {
        Boolean result = studyUserService.checkUpdate(studyUserDto);
        log.debug("审核成功");
        return Result.success(result);
    }

    /**
     * 分页自定义查询在线学习人员表
     * @param studyUserDto 在线学习人员表
     * @return
     */
    @ApiOperation(value = "查学员", notes = "查学员")
    @PostMapping("/student/lists" )
    public Result<List<StudyUserVo>> studentLists(@RequestBody StudyUserDto studyUserDto) {
        List<StudyUserVo> studyUserVos = studyUserService.studentLists(studyUserDto);
        log.debug("查询条数:"+studyUserVos.size());
        return Result.success(studyUserVos);
    }
    /**
     * 分页自定义查询在线学习人员表
     * @param studyUserDto 在线学习人员表
     * @return
     */
    @ApiOperation(value = "查讲师", notes = "查讲师")
    @PostMapping("/teacher/lists" )
    public Result<List<StudyUserVo>> teacherLists(@RequestBody StudyUserDto studyUserDto) {
        List<StudyUserVo> studyUserVos = studyUserService.teacherLists(studyUserDto);
        log.debug("查询条数:"+studyUserVos.size());
        return Result.success(studyUserVos);
    }
    /**
     * 分页自定义查询在线学习人员表
     * @param studyUserDto 在线学习人员表
     * @return
     */
    @ApiOperation(value = "查管理员", notes = "查管理员")
    @PostMapping("/admin/lists" )
    public Result<List<StudyUserVo>> adminLists(@RequestBody StudyUserDto studyUserDto) {
        List<StudyUserVo> studyUserVos = studyUserService.adminLists(studyUserDto);
        log.debug("查询条数:"+studyUserVos.size());
        return Result.success(studyUserVos);
    }

    /**
     * 通过当前登陆人获取登陆人信息
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过当前登陆人获取登陆人信息", notes = "通过当前登陆人获取登陆人信息")
    @GetMapping("/getStudyUser" )
    public Result<StudyUserVo> getStudyUser() {
        StudyUserVo studyUserVos = studyUserService.getStudyUser();
        log.debug("查询成功");
        return Result.success(studyUserVos);
    }

}
