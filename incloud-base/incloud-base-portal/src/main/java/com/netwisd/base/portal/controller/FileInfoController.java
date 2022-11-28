package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.FileInfoDto;
import com.netwisd.base.portal.vo.FileInfoVo;
import com.netwisd.base.portal.service.FileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 文件存储  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-01-14 09:51:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/fileinfo" )
@Api(value = "fileinfo", tags = "文件存储 Controller")
@Slf4j
public class FileInfoController {

    private final  FileInfoService fileInfoService;

    /**
     * 分页查询文件存储 
     * 没有使用参数注解，就是默认从form请求的方式
     * @param fileInfoDto 文件存储 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody FileInfoDto fileInfoDto) {
        Page pageVo = fileInfoService.list(fileInfoDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询文件存储 
     * @param fileInfoDto 文件存储 
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody FileInfoDto fileInfoDto) {
        Page pageVo = fileInfoService.lists(fileInfoDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询文件存储 
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<FileInfoVo> get(@PathVariable("id" ) Long id) {
        FileInfoVo fileInfoVo = fileInfoService.get(id);
        log.debug("查询成功");
        return Result.success(fileInfoVo);
    }

    /**
     * 新增文件存储 
     * @param fileInfoDto 文件存储 
     * @return Result
     */
    @ApiOperation(value = "新增文件存储 ", notes = "新增文件存储 ")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody FileInfoDto fileInfoDto) {
        Boolean result = fileInfoService.save(fileInfoDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改文件存储 
     * @param fileInfoDto 文件存储 
     * @return Result
     */
    @ApiOperation(value = "修改文件存储 ", notes = "修改文件存储 ")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody FileInfoDto fileInfoDto) {
        Boolean result = fileInfoService.update(fileInfoDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除文件存储 
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除文件存储 ", notes = "通过id删除文件存储 ")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = fileInfoService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
