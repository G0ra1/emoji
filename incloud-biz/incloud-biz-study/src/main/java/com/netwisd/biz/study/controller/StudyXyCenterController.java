package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.study.dto.*;
import com.netwisd.biz.study.entity.StudyUserCertificate;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 学员端个人中心Controller
 * @date 2022-03-15 16:37:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/xueyuan/center")
@Api(value = "/xueyuan/center", tags = "学员端个人中心Controller")
@Slf4j
public class StudyXyCenterController {
    @Autowired
    private StudyCollectionService studyCollectionService;

    @Autowired
    private StudyBrowseService studyBrowseService;

    @Autowired
    private StudyUserCertificateService studyUserCertificateService;

    @Autowired
    private StudyUserLearnApplyService studyUserLearnApplyService;

    @Autowired
    private StudyUserExamService studyUserExamService;

    @Autowired
    private StudyUserStudyRecordsService studyUserStudyRecordsService;

    @Autowired
    private MdmClient mdmClient;

    /**
     * 个人中心-收藏/取消收藏（存在：取消收藏；不存在：收藏）
     *
     * @param studyCollectionDto 收藏/取消收藏（存在：取消收藏；不存在：收藏）
     * @return
     */
    @ApiOperation(value = "收藏/取消收藏（存在：取消收藏；不存在：收藏）", notes = "收藏/取消收藏（存在：取消收藏；不存在：收藏）")
    @PostMapping("/saveCollection")
    public Result<Boolean> saveCollection(@RequestBody StudyCollectionDto studyCollectionDto) {
        Boolean save = studyCollectionService.save(studyCollectionDto);
        return Result.success(save);
    }

    /**
     * 个人中心-专题收藏
     *
     * @param studyCollectionDto 个人中心-专题收藏
     * @return
     */
    @ApiOperation(value = "个人中心-专题收藏", notes = "个人中心-专题收藏")
    @PostMapping("/getSpecialPage")
    public Result<Page<StudySpecialVo>> getSpecialPage(@RequestBody StudyCollectionDto studyCollectionDto) {
        Page<StudySpecialVo> pageVo = studyCollectionService.getSpecialPage(studyCollectionDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 个人中心-取消收藏
     *
     * @param id 个人中心-取消收藏
     * @return
     */
    @ApiOperation(value = "个人中心-取消收藏", notes = "个人中心-取消收藏")
    @DeleteMapping("/delCollection/{id}")
    public Result<Boolean> delCollection(@PathVariable("id") Long id) {
        Boolean remove = studyCollectionService.remove(id);
        log.debug("取消收藏成功:" + remove);
        return Result.success(remove);
    }

    /**
     * 个人中心-课程收藏
     *
     * @param studyCollectionDto 个人中心-课程收藏
     * @return
     */
    @ApiOperation(value = "个人中心-课程收藏", notes = "个人中心-课程收藏")
    @PostMapping("/getLessonPage")
    public Result<Page<StudyLessonForShowVo>> getLessonPage(@RequestBody StudyCollectionDto studyCollectionDto) {
        Page<StudyLessonForShowVo> pageVo = studyCollectionService.getLessonPage(studyCollectionDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }


    /**
     * 个人中心-我的试卷
     *
     * @param studyUserExamDto 人员考试 Dto
     * @return
     */
    @ApiOperation(value = "个人中心-我的试卷", notes = "个人中心-我的试卷")
    @PostMapping("/getUserExamPage")
    public Result<Page<StudyUserExamVo>> getUserExamPage(@RequestBody StudyUserExamDto studyUserExamDto) {
        Page<StudyUserExamVo> pageVo = studyUserExamService.pageList(studyUserExamDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 个人中心-我的试卷-详情id
     *
     * @param id 人员考试
     * @return
     */
    @ApiOperation(value = "个人中心-我的试卷-详情", notes = "个人中心-我的试卷-详情")
    @GetMapping("/getUserExamDetail/{id}")
    public Result<StudyUserExamVo> getUserExamDetail(@PathVariable Long id) {
        StudyUserExamVo studyUserExamVo = studyUserExamService.getUserExamDetail(id);
        log.debug("查询条数:" + studyUserExamVo);
        return Result.success(studyUserExamVo);
    }

    /**
     * 个人中心-分页获取浏览过的专题
     *
     * @param studyBrowseDto 个人中心-分页获取浏览过的专题
     * @return
     */
    @ApiOperation(value = "个人中心 分页获取浏览过的专题", notes = "个人中心 分页获取浏览过的专题")
    @PostMapping("/browse/getSpecials")
    public Result<Page<StudySpecialVo>> getPageSpecials(@RequestBody StudyBrowseDto studyBrowseDto) {
        Page<StudySpecialVo> pageVo = studyBrowseService.getSpecials(studyBrowseDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 个人中心-分页获取浏览过的课程
     *
     * @param studyBrowseDto 个人中心-分页获取浏览过的课程
     * @return
     */
    @ApiOperation(value = "个人中心- 分页获取浏览过的课程", notes = "个人中心- 分页获取浏览过的课程")
    @PostMapping("/browse/getLessons")
    public Result<Page<StudyLessonForShowVo>> getPageLessons(@RequestBody StudyBrowseDto studyBrowseDto) {
        Page<StudyLessonForShowVo> pageVo = studyBrowseService.getLessons(studyBrowseDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询人员证书
     *
     * @param infoDto
     * @return
     */
    @ApiOperation(value = "分页查询人员证书", notes = "分页查询人员证书")
    @PostMapping("/certificate/pageList")
    public Result<Page> certificatePageList(@RequestBody StudyUserCertificateDto infoDto) {
        //设置人员ID
        infoDto.setUserId(AppUserUtil.getLoginAppUser().getId());
        Page pageVo = studyUserCertificateService.pageList(infoDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 获取证书流
     *
     * @param id
     * @param response
     */
    @ApiOperation(value = "获取证书流", notes = "获取证书流")
    @GetMapping("/certificate/stream/{id}")
    public void certificateStream(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) {
        studyUserCertificateService.stream(id, response, request);
    }

    /**
     * 分页查询我的申请
     *
     * @param infoDto
     * @return
     */
    @ApiOperation(value = "分页查询我的申请", notes = "分页查询我的申请")
    @PostMapping("/learnApply/pageList")
    public Result<Page<StudyUserLearnApplyVo>> learnApplyPageList(@RequestBody StudyUserLearnApplyDto infoDto) {
        infoDto.setUserId(AppUserUtil.getLoginAppUser().getId());
        Page<StudyUserLearnApplyVo> pageVo = studyUserLearnApplyService.pageList(infoDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页查询我的学习记录
     *
     * @param userStudyRecordsDto
     * @return
     */
    @ApiOperation(value = "分页查询我的学习记录", notes = "分页查询我的学习记录")
    @PostMapping("/getUserRecords")
    public Result<Page<StudyUserRecordsVo>> getUserRecords(@RequestBody StudyUserStudyRecordsDto userStudyRecordsDto) {
        userStudyRecordsDto.setUserId(AppUserUtil.getLoginAppUser().getId());
        Page<StudyUserRecordsVo> pageVo = studyUserStudyRecordsService.getUserRecords(userStudyRecordsDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 个人中心-抬头信息
     *
     * @return
     */
    @ApiOperation(value = "个人中心-抬头信息", notes = "个人中心-抬头信息")
    @PostMapping("/getUserMsg")
    public Result<Map<String,Object>> getUserMsg(){
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String userName = loginAppUser.getUserNameCh();//姓名
        Integer sex = loginAppUser.getSex();//性别
        String phoneNum = loginAppUser.getPhoneNum();//手机号
        String postName = loginAppUser.getPostName();//岗位名称
        String email = loginAppUser.getEmail();//邮箱
        if (StringUtils.isBlank(postName)) {
            postName = "无";
        }
        String parentDeptName = loginAppUser.getParentDeptName();//部门名称
        if (StringUtils.isBlank(parentDeptName)) {
            parentDeptName = "无";
        }
        //在线学习身份
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        String userType = StudyUserTypeEnum.getMessage(studyUserRole);
        //个人学习时长
        Long userStudyTime = studyUserStudyRecordsService.getUserStudyMsg(loginAppUser.getId(),1);
        BigDecimal studyTime = new BigDecimal(userStudyTime);
        BigDecimal oneHour = new BigDecimal(3600);
        BigDecimal cumulativeStudyTime = studyTime.divide(oneHour, 2, RoundingMode.HALF_UP);
        //个人学习课程数量
        Long studyLessonNum = studyUserStudyRecordsService.getUserStudyMsg(loginAppUser.getId(), 2);
        //个人学习专题数量
        Long studySpecialNum = studyUserStudyRecordsService.getUserStudyMsg(loginAppUser.getId(),3);
        //个人证书数量
        Integer userCertificateNum = studyUserCertificateService.getUserCertificateNum(loginAppUser.getId());
        //返回数据拼接
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("userName",userName);
        returnMap.put("sex",sex);
        returnMap.put("phoneNum",phoneNum);
        returnMap.put("email",email);
        returnMap.put("postName",postName);
        returnMap.put("parentDeptName",parentDeptName);
        returnMap.put("userType",userType);
        returnMap.put("cumulativeStudyTime",cumulativeStudyTime);
        returnMap.put("studyLessonNum",studyLessonNum);
        returnMap.put("studySpecialNum",studySpecialNum);
        returnMap.put("userCertificateNum",userCertificateNum);
        return Result.success(returnMap);
    }

}
