package com.netwisd.base.mdm.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.dto.MdmOrgStatusDto;
import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import com.netwisd.base.mdm.dto.MdmSortDto;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.anntation.IncludeAnntation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 组织 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-08-12 10:27:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmOrg" )
@Api(value = "mdmOrg", tags = "组织Controller")
@Slf4j
public class MdmOrgController {

    private final  MdmOrgService mdmOrgService;

    /**
     * 分页查询组织
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmOrgDto 组织
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<List<MdmOrgAllVo>> list(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL),
                                                      include = @IncludeAnntation(vars = {"isDefault"}))
                                          @RequestBody MdmOrgDto mdmOrgDto) {
        List<MdmOrgAllVo> list = mdmOrgService.list(mdmOrgDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * 用于在线学习注册的时候使用
     * @param mdmOrgDto 组织
     * @return
     */
    @ApiOperation(value = "用于在线学习注册的时候使用", notes = "用于在线学习注册的时候使用")
    @PostMapping("/listForStudy" )
    public Result<List<MdmOrgAllVo>> listForStudy(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL),
            include = @IncludeAnntation(vars = {"isDefault"}))
                                          @RequestBody MdmOrgDto mdmOrgDto) {
        List<MdmOrgAllVo> list = mdmOrgService.list(mdmOrgDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * 查出给定组织下的一级子组织
     * @param id
     * @return
     */
    @ApiOperation(value = "查出给定组织下的一级子组织", notes = "查出给定组织下的一级子组织")
    @GetMapping("/kids/{id}" )
    public Result<List> kids(@PathVariable Long id) {
        List<MdmOrgVo> mdmOrgVos = mdmOrgService.kids(id);
        log.debug("kids查询条数:"+mdmOrgVos.size());
        return Result.success(mdmOrgVos);
    }

    /**
     * 组织树
     * @return
     */
    /*@ApiOperation(value = "组织树", notes = "组织树")
    @GetMapping("/trees/{orgType}" )
    public Result<List> trees(@PathVariable Integer orgType) {
        List list = mdmOrgService.treeList(orgType);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }*/


    /**
     * 排序
     * @param sortDto 组织
     * @return
     */
    @ApiOperation(value = "排序", notes = "排序")
    @PostMapping("/sort" )
    public Result<Boolean> sort(@Validation @RequestBody MdmSortDto sortDto) {
        Long sourceId = sortDto.getSourceId();
        Long targetId = sortDto.getTargetId();

        String index = sortDto.getIndex();
        if(ObjectUtil.isEmpty(targetId)){
            if(StrUtil.isEmpty(index)){
                throw new IncloudException("置顶、置底、要排序的目标组织、三者至少有一个不能为空！");
            }
        }else {
            if(StrUtil.isNotEmpty(index)){
                log.error("前端传值有误，如果source、target都有值的情况下，Index不能有值，后台强制index置为null！");
                index = null;
            }
        }

        return Result.success(mdmOrgService.sort(sourceId,targetId,index));
    }

    /**
     * 通过id查询组织
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmOrgVo> get(@PathVariable("id" ) Long id) {
        MdmOrgVo mdmOrgVo = mdmOrgService.get(id);
        log.debug("查询成功");
        return Result.success(mdmOrgVo);
    }

    /**
     * 新增组织
     * @param mdmOrgDtos 组织
     * @return Result
     */
    @ApiOperation(value = "新增组织", notes = "新增组织")
    @PostMapping
    public Result<Boolean> save(@Validation(exclude =
                                    @ExcludeAnntation(vars = {"level","sort","hasKids","status","orgFullId","orgFullName"}))
                                    @RequestBody List<MdmOrgDto> mdmOrgDtos) {
        Boolean result = mdmOrgService.save(mdmOrgDtos,true,false);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改组织
     * @param mdmOrgDto 组织
     * @return Result
     */
    @ApiOperation(value = "修改组织", notes = "修改组织")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmOrgDto mdmOrgDto) {
        Boolean result = mdmOrgService.update(mdmOrgDto,false);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 修改组织父级
     * @param mdmOrgDto 组织
     * @return Result
     */
    @ApiOperation(value = "修改组织父级", notes = "修改组织父级")
    @PutMapping("/updateParent" )
    public Result<Boolean> updateParent(@Validation @RequestBody MdmOrgDto mdmOrgDto) {
        Boolean result = mdmOrgService.updateParent(mdmOrgDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 修改组织名称
     * @param mdmOrgDto 组织
     * @return Result
     */
    @ApiOperation(value = "修改组织名称", notes = "修改组织名称")
    @PutMapping("/updateOrgName" )
    public Result<Boolean> updateOrgName(@Validation @RequestBody MdmOrgDto mdmOrgDto) {
        Boolean result = mdmOrgService.updateOrgName(mdmOrgDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 修改组织名称
     * @param mdmOrgStatusDto 组织
     * @return Result
     */
    @ApiOperation(value = "修改组织状态-如拆分、撤消等", notes = "修改组织状态-如拆分、撤消等")
    @PutMapping("/updateStatus" )
    public Result<Boolean> updateStatus(@Validation @RequestBody MdmOrgStatusDto mdmOrgStatusDto) {
        Boolean result = mdmOrgService.updateStatus(mdmOrgStatusDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除组织
     * @param id
     * @return Result
     */
    @ApiOperation(value = "通过id删除组织", notes = "通过id删除组织")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmOrgService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 根据GEPS 集采机构ID 查询所有机构信息
     * @param ids 集采的机构id
     * @return
     */
    @ApiOperation(value = "根据GEPS 集采机构ID 查询所有机构信息", notes = "根据GEPS 集采机构ID 查询所有机构信息")
    @PostMapping("/getOrgByJcOrgIds" )
    public List<MdmOrgVo> getOrgByJcOrgIds(String ids) {
        List<MdmOrgVo> list = mdmOrgService.getOrgByJcOrgIds(ids);
        log.debug("查询条数:"+list.size());
        return list;
    }

    /**
     * 根据机构ID查询 查询该机构下的所有部门 以及子部门
     * @param id
     * @return
     */
    @ApiOperation(value = "根据机构ID查询 查询该机构下的所有部门 以及子部门", notes = "根据机构ID查询 查询该机构下的所有部门 以及子部门")
    @GetMapping("/getDeptByOrgId/{id}" )
    public Result getDeptByOrgId(@PathVariable Long id) {
        List<MdmOrgAllVo> mdmOrgVos = mdmOrgService.getDeptByOrgId(id);
        return Result.success(mdmOrgVos);
    }

    /**
     * 还原撤销的机构
     * @param id
     * @return
     */
    @ApiOperation(value = "还原撤销的机构", notes = "还原撤销的机构")
    @GetMapping("/backoutOrg/{id}" )
    public Result backoutOrg(@PathVariable Long id) {
        return Result.success(mdmOrgService.backoutOrg(id));
    }

}
