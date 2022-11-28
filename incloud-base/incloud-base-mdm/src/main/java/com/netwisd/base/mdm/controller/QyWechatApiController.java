package com.netwisd.base.mdm.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.service.MdmOrgService;
import com.netwisd.base.mdm.service.MdmUserService;
import com.netwisd.base.mdm.service.QyWechatApiService;
import com.netwisd.base.mdm.utils.EasyExcelUtils;
import com.netwisd.base.mdm.vo.qywechat.SyncDeptExcelVo;
import com.netwisd.base.mdm.vo.qywechat.SyncUserExcelVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/qyWechatApi")
@Api(value = "qyWechatApi", tags = "企业微信apiController")
@Slf4j
public class QyWechatApiController {

    @Autowired
    private QyWechatApiService qyWechatApiService;

    @Autowired
    private MdmOrgService mdmOrgService;

    @Autowired
    private MdmUserService mdmUserService;

    /**
     * 创建部门
     *
     * @return
     */
    @ApiOperation(value = "创建部门", notes = "创建部门")
    @PostMapping("/createDept")
    public Result<Boolean> createDept(@RequestBody MdmOrgVo mdmOrgVo) {
        qyWechatApiService.createOrUpdateDept(mdmOrgVo,"add");
        return Result.success();
    }

    /**
     * 修改部门
     *
     * @return
     */
    @ApiOperation(value = "修改部门", notes = "修改部门")
    @PostMapping("/updateDept")
    public Result<Boolean> updateDept(@RequestBody MdmOrgVo mdmOrgVo) {
        qyWechatApiService.createOrUpdateDept(mdmOrgVo,"update");
        return Result.success();
    }

    /**
     * 删除部门
     *
     * @return
     */
    @ApiOperation(value = "删除部门", notes = "删除部门")
    @GetMapping("/deleteDept")
    public Result<Boolean> deleteDept(@RequestParam(value = "qyWechatDeptId") String qyWechatDeptId) {
        qyWechatApiService.deleteDept(qyWechatDeptId);
        return Result.success();
    }

    /**
     * 删除部门
     *
     * @return
     */
    @ApiOperation(value = "删除部门", notes = "删除部门")
    @GetMapping("/batchDeleteDept")
    public Result<Boolean> batchDeleteDept(@RequestParam(value = "qyWechatDeptId") String qyWechatDeptId) {
        qyWechatApiService.batchDeleteDept(qyWechatDeptId);
        return Result.success();
    }

    /**
     * 删除部门
     *
     * @return
     */
    @ApiOperation(value = "删除部门", notes = "删除部门")
    @GetMapping("/deleteDeptAndUser")
    public Result<Boolean> deleteDeptAndUser(@RequestParam(value = "qyWechatDeptId") String qyWechatDeptId) {
        qyWechatApiService.deleteDeptAndUser(qyWechatDeptId);
        return Result.success();
    }

    /**
     * 创建人员
     *
     * @return
     */
    @ApiOperation(value = "创建人员", notes = "创建人员")
    @PostMapping("/createUser")
    public Result<Boolean> createUser(@RequestBody MdmUserVo mdmUserVo) {
        qyWechatApiService.createOrUpdateUser(mdmUserVo,"add");
        return Result.success();
    }

    /**
     * 修改人员
     *
     * @return
     */
    @ApiOperation(value = "修改人员", notes = "修改人员")
    @PostMapping("/updateUser")
    public Result<Boolean> updateUser(@RequestBody MdmUserVo mdmUserVo) {
        qyWechatApiService.createOrUpdateUser(mdmUserVo,"update");
        return Result.success();
    }

    /**
     * 批量删除人员
     *
     * @return
     */
    @ApiOperation(value = "批量删除人员", notes = "批量删除人员")
    @GetMapping("/batchDeleteUser")
    public Result<Boolean> batchDeleteUser(@RequestParam(value = "userIds") String userIds) {
        qyWechatApiService.batchDeleteUser(userIds);
        return Result.success();
    }

    /**
     * 全量覆盖企业微信部门
     *
     * @return
     */
    @ApiOperation(value = "全量覆盖企业微信部门", notes = "全量覆盖企业微信部门")
    @GetMapping("/replaceDept")
    public Result<Boolean> replaceDept(HttpServletResponse response) throws Exception{
        JSONArray replaceDeptArray = qyWechatApiService.replaceDept();
        List<SyncDeptExcelVo> syncDeptExcelVos = JSONObject.parseArray(replaceDeptArray.toJSONString(), SyncDeptExcelVo.class);
        if (CollectionUtil.isNotEmpty(syncDeptExcelVos)) {
            List<SyncDeptExcelVo> successList = new ArrayList<>();
            List<SyncDeptExcelVo> faildList = new ArrayList<>();
            for (SyncDeptExcelVo deptExcelVo : syncDeptExcelVos) {
                MdmOrg mdmOrg = mdmOrgService.getByQyWeChatId(Integer.valueOf(deptExcelVo.getPartyid()));
                deptExcelVo.setOrgName(mdmOrg.getOrgName());
                if (deptExcelVo.getErrcode().equals("0")) {
                    successList.add(deptExcelVo);
                } else {
                    faildList.add(deptExcelVo);
                }
            }

            ExportParams exportParams = new ExportParams();
            exportParams.setType(ExcelType.HSSF);
            if (CollectionUtil.isNotEmpty(successList)) {
                EasyExcelUtils.exportExcel(successList, SyncDeptExcelVo.class, "全量覆盖部门-成功文档", exportParams, response);
            }
            if (CollectionUtil.isNotEmpty(faildList)) {
                EasyExcelUtils.exportExcel(faildList, SyncDeptExcelVo.class, "全量覆盖部门-失败文档", exportParams, response);
            }
        }
        return Result.success();
    }

    /**
     * 全量覆盖企业微信用户
     *
     * @return
     */
    @ApiOperation(value = "全量覆盖企业微信用户", notes = "全量覆盖企业微信用户")
    @GetMapping("/replaceUser")
    public Result<Boolean> replaceUser(HttpServletResponse response) throws Exception{
        JSONArray replaceUserArray = qyWechatApiService.replaceUser();
        List<SyncUserExcelVo> syncUserExcelVos = JSONObject.parseArray(replaceUserArray.toJSONString(), SyncUserExcelVo.class);
        if (CollectionUtil.isNotEmpty(syncUserExcelVos)) {
            List<SyncUserExcelVo> successList = new ArrayList<>();
            List<SyncUserExcelVo> faildList = new ArrayList<>();
            for (SyncUserExcelVo userExcelVo : syncUserExcelVos) {
                MdmUser mdmUser = mdmUserService.getByUserName(userExcelVo.getUserId());
                userExcelVo.setUserName(mdmUser.getUserNameCh());
                if (userExcelVo.getErrcode().equals("0")) {
                    successList.add(userExcelVo);
                } else {
                    faildList.add(userExcelVo);
                }
            }

            ExportParams exportParams = new ExportParams();
            exportParams.setType(ExcelType.HSSF);
            if (CollectionUtil.isNotEmpty(successList)) {
                EasyExcelUtils.exportExcel(successList, SyncUserExcelVo.class, "全量覆盖人员-成功文档", exportParams, response);
            }
            if (CollectionUtil.isNotEmpty(faildList)) {
                EasyExcelUtils.exportExcel(faildList, SyncUserExcelVo.class, "全量覆盖人员-失败文档", exportParams, response);
            }
        }
        return Result.success();
    }

    /**
     * 全量增量修改人员（不删除）
     *
     * @return
     */
    @ApiOperation(value = "全量增量修改人员（不删除）", notes = "全量增量修改人员（不删除）")
    @GetMapping("/syncuser")
    public Result<Boolean> syncuser(HttpServletResponse response) throws Exception{
        JSONArray syncuserArray = qyWechatApiService.syncuser();
        List<SyncUserExcelVo> syncUserExcelVos = JSONObject.parseArray(syncuserArray.toJSONString(), SyncUserExcelVo.class);
        if (CollectionUtil.isNotEmpty(syncUserExcelVos)) {
            List<SyncUserExcelVo> successList = new ArrayList<>();
            List<SyncUserExcelVo> faildList = new ArrayList<>();
            for (SyncUserExcelVo userExcelVo : syncUserExcelVos) {
                MdmUser mdmUser = mdmUserService.getByUserName(userExcelVo.getUserId());
                userExcelVo.setUserName(mdmUser.getUserNameCh());
                if (userExcelVo.getErrcode().equals("0")) {
                    successList.add(userExcelVo);
                } else {
                    faildList.add(userExcelVo);
                }
            }

            ExportParams exportParams = new ExportParams();
            exportParams.setType(ExcelType.HSSF);
            if (CollectionUtil.isNotEmpty(successList)) {
                EasyExcelUtils.exportExcel(successList, SyncUserExcelVo.class, "全量覆盖人员-成功文档", exportParams, response);
            }
            if (CollectionUtil.isNotEmpty(faildList)) {
                EasyExcelUtils.exportExcel(faildList, SyncUserExcelVo.class, "全量覆盖人员-失败文档", exportParams, response);
            }
        }
        return Result.success();
    }

    @GetMapping("/syncAllDept")
    public void syncAllDept(){
        qyWechatApiService.syncAllDept();
    }

    @GetMapping("/syncAllUser")
    public void syncAllUser(){
        qyWechatApiService.syncAllUser();
    }
}
