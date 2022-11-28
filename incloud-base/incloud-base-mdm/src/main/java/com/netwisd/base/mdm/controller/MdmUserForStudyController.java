package com.netwisd.base.mdm.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.study.dto.StudyUserDto;
import com.netwisd.base.common.study.vo.StudyUserVo;
import com.netwisd.base.mdm.excel.StudyUserExcel;
import com.netwisd.base.mdm.service.MdmUserForStudyService;
import com.netwisd.base.mdm.utils.EasyExcelUtils;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/mdmUserForStudy")
@Api(value = "mdmUserForStudy", tags = "在线学习人员Controller")
@Slf4j
public class MdmUserForStudyController {

    private final MdmUserForStudyService mdmUserForStudyService;

    /**
     * 分页查询在线学习人员
     *
     * @param studyUserDto 在线学习人员
     * @return
     */
    @ApiOperation(value = "分页查询在线学习人员", notes = "分页查询在线学习人员")
    @PostMapping("/findByPage")
    public Result<Page> findByPage(@RequestBody StudyUserDto studyUserDto) {
        Page<StudyUserVo> userByPageVo = mdmUserForStudyService.findByPage(studyUserDto);
        return Result.success(userByPageVo);
    }

    /**
     * 集合查询在线学习人员
     *
     * @param studyUserDto 在线学习人员
     * @return
     */
    @ApiOperation(value = "集合查询在线学习人员", notes = "集合查询在线学习人员")
    @PostMapping("/findByList")
    public Result<List<StudyUserVo>> findByList(@RequestBody StudyUserDto studyUserDto) {
        List<StudyUserVo> userByListVo = mdmUserForStudyService.findByList(studyUserDto);
        return Result.success(userByListVo);
    }

    /**
     * 通过id查询在线学习人员
     *
     * @param id 在线学习人员
     * @return
     */
    @ApiOperation(value = "通过id查询在线学习人员", notes = "通过id查询在线学习人员")
    @GetMapping("/get/{id}")
    public Result<StudyUserVo> getById(@PathVariable("id") Long id) {
        StudyUserVo studyUserVo = mdmUserForStudyService.get(id);
        return Result.success(studyUserVo);
    }

    /**
     * 批量新增在线学习人员
     *
     * @param studyUserDtoList 在线学习人员集合
     * @return
     */
    @ApiOperation(value = "批量新增在线学习人员", notes = "批量新增在线学习人员")
    @PostMapping("/saveStudyUserBatch")
    public Result<Boolean> saveStudyUserBatch(@RequestBody List<StudyUserDto> studyUserDtoList) {
        Boolean saveState = mdmUserForStudyService.saveStudyUserBatch(studyUserDtoList);
        return Result.success(saveState);
    }

    /**
     * 修改在线学习人员
     *
     * @param studyUserDto 在线学习人员
     * @return
     */
    @ApiOperation(value = "修改在线学习人员", notes = "修改在线学习人员")
    @PutMapping("/updateStudyUser")
    public Result<Boolean> updateStudyUser(@RequestBody StudyUserDto studyUserDto) {
        Boolean updateState = mdmUserForStudyService.updateStudyUser(studyUserDto);
        return Result.success(updateState);
    }

    /**
     * 批量删除在线学习人员
     *
     * @param ids ids
     * @return
     */
    @ApiOperation(value = "批量删除在线学习人员", notes = "批量删除在线学习人员")
    @DeleteMapping("/deleteStudyUser")
    public Result<Boolean> deleteStudyUser(@RequestParam(value = "ids") String ids,
                                           @RequestParam(value = "userType") String userType) {
        Boolean deleteState = mdmUserForStudyService.deleteStudyUser(ids,userType);
        return Result.success(deleteState);
    }

    /**
     * 通过人员id返回在线学习角色
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "通过人员id返回在线学习角色", notes = "通过人员id返回在线学习角色")
    @GetMapping("/getStudyUserRole/{id}")
    public Integer getStudyUserRole(@PathVariable(value = "id") Long id) {
        return mdmUserForStudyService.getStudyRoleByUserId(id);
    }

    /**
     * 用户导入模板导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportTemplate")
    @ApiOperation(value = "用户导入模板导出")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.HSSF);
        log.debug("在线学习人员导入模板excel导出开始--------------------------------------");
        List<StudyUserExcel> studyUserExcels = new ArrayList<>();
        StudyUserExcel studyUserExcel = new StudyUserExcel();
        studyUserExcel.setParentOrgName("中原建设机构");
        studyUserExcel.setParentDeptName("中原建设部门");
        studyUserExcel.setUserNameCh("人员姓名");
        studyUserExcel.setPhoneNum(1234567890);
        studyUserExcel.setPassWord("123456");
        studyUserExcel.setIdCard("12345678901234567X");
        studyUserExcel.setEmail("xxx@163.com");
        studyUserExcel.setUserType("学员");
        studyUserExcel.setSex("男");
        studyUserExcels.add(studyUserExcel);
        EasyExcelUtils.exportExcel(studyUserExcels, StudyUserExcel.class, "在线学习人员导入模板", exportParams, response);
        log.debug("在线学习人员导入模板excel导出结束--------------------------------------");
    }
}
