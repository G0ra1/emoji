package com.netwisd.base.file.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.file.constants.FileStoreTypeEnum;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.file.dto.FileDsDto;
import com.netwisd.base.file.vo.FileDsVo;
import com.netwisd.base.file.service.FileDsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;
import java.util.List;

/**
 * @Description 文件数据源 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-12-29 11:04:48
 */
@RestController
@AllArgsConstructor
@RequestMapping("/fileDs" )
@Api(value = "fileDs", tags = "文件数据源Controller")
@Slf4j
public class FileDsController {

    private final  FileDsService fileDsService;

    /**
     * 分页查询文件数据源
     * 没有使用参数注解，就是默认从form请求的方式
     * @param fileDsDto 文件数据源
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody FileDsDto fileDsDto) {
        Page pageVo = fileDsService.list(fileDsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 无分页集合
     * @return
     */
    @ApiOperation(value = "无分页集合", notes = "无分页集合")
    @GetMapping("/all" )
    public Result<List> all() {
        List all = fileDsService.all();
        return Result.success(all);
    }

    /**
     * 通过id查询文件数据源
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<FileDsVo> get(@PathVariable("id" ) Long id) {
        FileDsVo fileDsVo = fileDsService.get(id);
        log.debug("查询成功");
        return Result.success(fileDsVo);
    }

    /**
     * 测试数据源连接
     * @param fileDsDto 文件数据源
     * @return Result
     */
    @ApiOperation(value = "连接测试", notes = "连接测试")
    @PostMapping("/connect" )
    public Result connect(@Validation @RequestBody FileDsDto fileDsDto) {
        fileDsService.connect(fileDsDto);
        log.debug("连接成功");
        return Result.success("连接成功");
    }

    /**
     * 新增文件数据源
     * @param fileDsDto 文件数据源
     * @return Result
     */
    @ApiOperation(value = "新增文件数据源", notes = "新增文件数据源")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody FileDsDto fileDsDto) {
        if(fileDsDto.getType().equals(FileStoreTypeEnum.MINIO.name())){
            //检验minio参数必填
            Assert.noNullElements(new Object[]{fileDsDto.getMinioAccessKey(),fileDsDto.getMinioSecretKey(),fileDsDto.getMinioUrl(),fileDsDto.getMinioBucketName()},"minio相关信息不能为空");
        }else if(fileDsDto.getType().equals(FileStoreTypeEnum.LOCAL.name())){
            //检验minio参数必填
            Assert.noNullElements(new Object[]{fileDsDto.getLocalFilePath(),fileDsDto.getLocalFilePrefix()},"本地相关信息不能为空");
        }else {
            throw new IncloudException("type参数:{}有误，请重新填写！",fileDsDto.getType());
        }
        Boolean result = fileDsService.save(fileDsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改文件数据源
     * @param fileDsDto 文件数据源
     * @return Result
     */
    @ApiOperation(value = "修改文件数据源", notes = "修改文件数据源")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody FileDsDto fileDsDto) {
        Boolean result = fileDsService.update(fileDsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 设置是否默认
     * @param isDefault 是否默认
     * @return Result
     */
    @ApiOperation(value = "设置是否默认", notes = "设置是否默认")
    @PutMapping("/setDefault/{id}/{isDefault}")
    public Result<Boolean> setDefault(@Validation @PathVariable("id" )Long id,@Validation @PathVariable("isDefault" ) Integer isDefault) {
        Boolean result = fileDsService.setDefault(id,isDefault);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除文件数据源
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除文件数据源", notes = "通过id删除文件数据源")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = fileDsService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
}