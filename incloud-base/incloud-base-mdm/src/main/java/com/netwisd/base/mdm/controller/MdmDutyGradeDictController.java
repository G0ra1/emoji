package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmDutyGradeDictDto;
import com.netwisd.base.mdm.vo.MdmDutyGradeDictVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmDutyGradeDictService;
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
@RequestMapping("/mdmDutyGradeDict" )
@Api(value = "mdmDutyGradeDict", tags = "职务职等字典Controller")
@Slf4j
public class MdmDutyGradeDictController {

    private final  MdmDutyGradeDictService mdmDutyGradeDictService;

    /**
     * 分页查询职务职等
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmDutyGradeDictDto 职务职等
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmDutyGradeDictDto mdmDutyGradeDictDto) {
        Page pageVo = mdmDutyGradeDictService.list(mdmDutyGradeDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页自定义查询职务职等
     * @param mdmDutyGradeDictDto 职务职等
     * @return
     */
    @ApiOperation(value = "根据职务序列id查询职等集合", notes = "根据职务序列id查询职等集合")
    @PostMapping("/lists" )
    public Result<List<MdmDutyGradeDictVo>> lists(@RequestBody MdmDutyGradeDictDto mdmDutyGradeDictDto) {
        List<MdmDutyGradeDictVo> mdmDutyGradeDictVos = mdmDutyGradeDictService.lists(mdmDutyGradeDictDto);
        log.debug("查询条数:"+mdmDutyGradeDictVos.size());
        return Result.success(mdmDutyGradeDictVos);
    }

    /**
     * 通过id查询职务职等
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmDutyGradeDictVo> get(@PathVariable("id" ) Long id) {
        MdmDutyGradeDictVo mdmDutyGradeDictVo = mdmDutyGradeDictService.get(id);
        log.debug("查询成功");
        return Result.success(mdmDutyGradeDictVo);
    }

    /**
     * 新增职务职等
     * @param mdmDutyGradeDictDto 职务职等
     * @return Result
     */
    @ApiOperation(value = "新增职务职等", notes = "新增职务职等")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmDutyGradeDictDto mdmDutyGradeDictDto) {
        Boolean result = mdmDutyGradeDictService.save(mdmDutyGradeDictDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改职务职等
     * @param mdmDutyGradeDictDto 职务职等
     * @return Result
     */
    @ApiOperation(value = "修改职务职等", notes = "修改职务职等")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmDutyGradeDictDto mdmDutyGradeDictDto) {
        Boolean result = mdmDutyGradeDictService.update(mdmDutyGradeDictDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除职务职等
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除职务职等", notes = "通过id删除职务职等")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmDutyGradeDictService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
