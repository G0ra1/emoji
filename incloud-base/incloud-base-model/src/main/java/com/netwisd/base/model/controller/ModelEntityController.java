package com.netwisd.base.model.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.model.dto.ModelEntityDto;
import com.netwisd.base.model.entity.ModelEntity;
import com.netwisd.base.model.service.ModelEntityService;
import com.netwisd.base.model.service.ModelFieldService;
import com.netwisd.base.model.service.TableService;
import com.netwisd.base.model.vo.ModelEntityVo;
import com.netwisd.base.model.vo.ModelFieldVo;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.annotation.SysLog;
import com.netwisd.common.log.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Api(value = "ModelEntity", tags = "实体维护Controller")
@Slf4j
@RestController
@AllArgsConstructor
public class ModelEntityController {

    private final ModelEntityService modelEntityService;

    private final TableService tableService;

    private final ModelFieldService modelFieldService;

    @ApiOperation(value = "获取表信息", notes = "获取表信息")
    @PostMapping("/tableInfo/page/{dsId}")
    public Result tableInfoPage(@PathVariable(value = "dsId") String dsId, @RequestBody ModelEntityDto modelEntityDto) {
        return Result.success(tableService.queryTablePage(dsId, modelEntityDto));
    }

    @ApiOperation(value = "获取列信息", notes = "获取列信息")
    @PostMapping("/columnInfo/{dsId}/{tableName}")
    public Result columnInfoList(@PathVariable(value = "dsId") String dsId, @PathVariable(value = "tableName") String tableName) {
        return Result.success(tableService.queryColumnInfoList(dsId, tableName));
    }

    @SysLog(value = "实体分页查询")
    @PostMapping("/entity/page")
    @ApiOperation(value = "分页查询")
    public Result<IPage> modelEntityPage(@RequestBody ModelEntityDto modelEntityDto) {
        return Result.success(modelEntityService.queryModelEntityPage(modelEntityDto));
    }

    @SysLog(value = "字段列表")
    @GetMapping("/entity/field/list/{entityId}")
    @ApiOperation(value = "字段列表")
    public Result fieldList(@PathVariable(value = "entityId", required = false) String entityId) {
        return Result.success(modelEntityService.queryFieldList(entityId));
    }

    @SysLog(value = "实体新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping("/entity")
    @ApiOperation(value = "新增")
    public Result addEntity(@Validation @RequestBody ModelEntityDto modelEntityDto) {
        return Result.success(modelEntityService.saveModelEntity(modelEntityDto));
    }

    @SysLog(value = "实体获取详情")
    @GetMapping("/entity/detailId/{id}")
    @ApiOperation(value = "实体获取详情")
    public Result<ModelEntityVo> getEntityDetail(@PathVariable(name = "id") Long id) {
        return Result.success(modelEntityService.getModelEntity(id));
    }

    @SysLog(value = "实体编辑", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping("/entity")
    @ApiOperation(value = "实体编辑")
    public Result upAndExecSql(@Validation @RequestBody ModelEntityDto modelEntityDto) {
        return Result.success(modelEntityService.upModelEntity(modelEntityDto));
    }

    @SysLog(value = "实体删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/entity/{id}")
    @ApiOperation(value = "实体删除")
    public Result upAndExecSql(@PathVariable("id") Long id) {
        ModelEntityVo modelEntity = modelEntityService.getModelEntity(id);
        modelEntityService.delModelEntity(id);
        try {
            tableService.dropTable(modelEntity.getDbSource(), modelEntity.getTableName());
        } catch (Exception e) {
            //todo 实体删除、顺带着删除表
            log.error("删除实体成功，删除{},{}失败,{}", modelEntity.getDbSource(), modelEntity.getTableName(), e);
        }
        return Result.success();
    }

    @SysLog(value = "sql预览")
    @PostMapping("/entity/getExecSql/{dsId}")
    @ApiOperation(value = "sql预览")
    public Result getExecSql(@PathVariable("dsId") String dsId, @RequestBody ModelEntityDto modelEntityDto) {
        //采用这种办法是因为，getExecSql使用了动态切换数据源。执行fieldService时会报找不到主库的数据表；
        ModelEntity modelEntity = modelEntityService.getById(modelEntityDto.getId());
        modelEntityDto.setOldTableName(ObjectUtil.isNotNull(modelEntity) ? modelEntity.getTableName() : modelEntityDto.getTableName());
        modelEntityDto.setOldTableNameCh(ObjectUtil.isNotNull(modelEntity) ? modelEntity.getTableNameCh() : modelEntityDto.getTableNameCh());
        modelEntityDto.setOldFieldList(modelFieldService.queryModelFieldList(modelEntityDto.getId())
                .stream().map(ModelFieldVo::toModelField).collect(Collectors.toList()));
        return Result.success(tableService.getExecSql(dsId, modelEntityDto));
    }

    @SysLog(value = "sql执行")
    @PostMapping("/entity/execSql/{dsId}")
    @ApiOperation(value = "sql执行")
    public Result execSql(@PathVariable("dsId") String dsId, @RequestBody ModelEntityDto modelEntityDto) {
        try {
            //无异常提示执行成功,是因为走ddl语句，不返回受影响的行数，无法确认是否执行成功，目前从是否有异常进行处理
            tableService.execDDLSql(dsId, modelEntityDto.getSql());
            return Result.success(Boolean.TRUE, "执行成功");
        } catch (Exception e) {
            log.error("sql执行失败,SQl语句:{},异常信息:{}", modelEntityDto.getSql(), e);
            //捕捉到的异常抛出去，是为了异常信息不展现那么多；只展现有用的一部分，但是后台记录到日志文件里面比较全乎。
            throw new IncloudException(e.getCause().getMessage());
        }
    }
}