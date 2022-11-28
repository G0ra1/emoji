package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.biz.study.constants.RangeTypeEnum;
import com.netwisd.biz.study.constants.StudyStatus;
import com.netwisd.biz.study.constants.UseRangeEnum;
import com.netwisd.biz.study.dto.*;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.fegin.WfClient;
import com.netwisd.biz.study.mapper.*;
import com.netwisd.biz.study.service.*;
import com.netwisd.biz.study.util.StudyTimeUtil;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程调整申请 功能描述...
 * @date 2022-05-12 09:17:24
 */
@Service
@Slf4j
public class StudyLessonAdjServiceImpl extends BatchServiceImpl<StudyLessonAdjMapper, StudyLessonAdj> implements StudyLessonAdjService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonAdjMapper studyLessonAdjMapper;

    @Autowired
    private StudyLessonMapper studyLessonMapper;

    @Autowired
    private StudyLessonHisMapper studyLessonHisMapper;

    @Autowired
    private StudyLessonAdjCouMapper studyLessonAdjCouMapper;

    @Autowired
    private StudyLessonAdjCouService studyLessonAdjCouService;

    @Autowired
    private StudyLessonCouMapper studyLessonCouMapper;

    @Autowired
    private StudyLessonCouService studyLessonCouService;

    @Autowired
    private StudyLessonHisCouService studyLessonHisCouService;

    @Autowired
    private StudyLessonAdjMarterialsMapper studyLessonAdjMarterialsMapper;

    @Autowired
    private StudyLessonAdjMarterialsService studyLessonAdjMarterialsService;

    @Autowired
    private StudyLessonMarterialsMapper studyLessonMarterialsMapper;

    @Autowired
    private StudyLessonMarterialsService studyLessonMarterialsService;

    @Autowired
    private StudyLessonHisMarterialsService studyLessonHisMarterialsService;

    @Autowired
    private StudyLessonAdjCouPermMapper studyLessonAdjCouPermMapper;

    @Autowired
    private StudyLessonAdjCouPermService studyLessonAdjCouPermService;

    @Autowired
    private StudyLessonCouPermMapper studyLessonCouPermMapper;

    @Autowired
    private StudyLessonCouPermService studyLessonCouPermService;

    @Autowired
    private StudyLessonHisCouPermService studyLessonHisCouPermService;

    @Autowired
    private WfClient wfClient;

    @Override
    public List<StudyLessonAdjVo> adjListForLesson(Long lessonId) {
        //按调整发起时间倒叙查询所有
        LambdaQueryWrapper<StudyLessonAdj> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonAdj::getLinkId,lessonId);
        queryWrapper.orderByDesc(StudyLessonAdj::getCreateTime);
        List<StudyLessonAdj> studyLessonAdjs = studyLessonAdjMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper,studyLessonAdjs,StudyLessonAdjVo.class);
    }

    @Override
    public StudyLessonAdjVo detail(Long id) {
        StudyLessonAdj studyLessonAdj = studyLessonAdjMapper.selectById(id);
        StudyLessonAdjVo studyLessonAdjVo = dozerMapper.map(studyLessonAdj, StudyLessonAdjVo.class);
        this.findSons(studyLessonAdjVo);
        return studyLessonAdjVo;
    }

    @Override
    @Transactional
    public StudyLessonAdjVo procSaveOrUpdate(StudyLessonAdjDto lessonAdjDto) {
        this.checkData(lessonAdjDto);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        lessonAdjDto.setCreateUserName(loginAppUser.getUserNameCh());

        StudyLessonAdj lessonAdj = dozerMapper.map(lessonAdjDto, StudyLessonAdj.class);
        super.saveOrUpdate(lessonAdj);
        //课程课件调整表
        if (CollectionUtils.isNotEmpty(lessonAdjDto.getCouList())) {
            //1.先删
            LambdaQueryWrapper<StudyLessonAdjCou> lessonAdjCouWrapper = new LambdaQueryWrapper<>();
            lessonAdjCouWrapper.eq(StudyLessonAdjCou::getLessonAdjId,lessonAdj.getId());
            studyLessonAdjCouMapper.delete(lessonAdjCouWrapper);
            //2.后增
            List<StudyLessonAdjCouDto> lessonAdjCouDtoList = lessonAdjDto.getCouList();
            lessonAdjCouDtoList.forEach(lessonAdjCouDto->{
                lessonAdjCouDto.setLessonAdjId(lessonAdj.getId());
                //如果课件是私有的 设置课程课件权限
                if (UseRangeEnum.SIYOU.code.equals(lessonAdjCouDto.getUseRange())) {
                    this.saveLessonAdjCouPerm(lessonAdjCouDto);
                }
            });
            List<StudyLessonAdjCou> lessonAdjCouList = DozerUtils.mapList(dozerMapper, lessonAdjCouDtoList, StudyLessonAdjCou.class);
            studyLessonAdjCouService.saveBatch(lessonAdjCouList);
        }
        //课程学习资料调整表
        if (CollectionUtils.isNotEmpty(lessonAdjDto.getMarterialsList())) {
            //1.先删
            LambdaQueryWrapper<StudyLessonAdjMarterials> lessonAdjMarterialsWrapper = new LambdaQueryWrapper<>();
            lessonAdjMarterialsWrapper.eq(StudyLessonAdjMarterials::getLessonAdjId,lessonAdj.getId());
            studyLessonAdjMarterialsMapper.delete(lessonAdjMarterialsWrapper);
            //2.后增
            List<StudyLessonAdjMarterialsDto> lessonAdjMarterialsDtoList = lessonAdjDto.getMarterialsList();
            lessonAdjMarterialsDtoList.forEach(lessonAdjMarterialsDto->{
                lessonAdjMarterialsDto.setLessonAdjId(lessonAdj.getId());
            });
            List<StudyLessonAdjMarterials> studyLessonAdjMarterialsList = DozerUtils.mapList(dozerMapper, lessonAdjMarterialsDtoList, StudyLessonAdjMarterials.class);
            studyLessonAdjMarterialsService.saveBatch(studyLessonAdjMarterialsList);
        }
        return this.detail(lessonAdj.getId());
    }

    @Override
    public StudyLessonAdjVo procDetail(String procInstId) {
        StudyLessonAdj lessonAdj = this.getByProcInstId(procInstId);
        StudyLessonAdjVo lessonAdjVo = dozerMapper.map(lessonAdj, StudyLessonAdjVo.class);
        this.findSons(lessonAdjVo);
        return lessonAdjVo;
    }

    @Override
    @Transactional
    public Boolean procAfterSubmit(String procInstId) {
        //1、赋值提交时间
        StudyLessonAdj lessonAdj = this.getByProcInstId(procInstId);
        lessonAdj.setAuditSubmitTime(LocalDateTime.now());
        studyLessonAdjMapper.updateById(lessonAdj);
        //2、修改原数据状态为”调整中“
        StudyLesson lesson = studyLessonMapper.selectById(lessonAdj.getLinkId());
        lesson.setStatus(StudyStatus.IN_UPDATE.code);
        studyLessonMapper.updateById(lesson);
        return true;
    }


    @Override
    @Transactional
    public Boolean procAuditSuccess(String procInstId) {
        //1、赋值审批完成时间
        StudyLessonAdj lessonAdj = this.getByProcInstId(procInstId);
        lessonAdj.setAuditSuccessTime(LocalDateTime.now());
        studyLessonAdjMapper.updateById(lessonAdj);
        //2、查询原数据
        //2.1、课程
        StudyLesson studyLesson = studyLessonMapper.selectById(lessonAdj.getLinkId());
        //2.2、课程课件
        LambdaQueryWrapper<StudyLessonCou> lessonCouWrapper = new LambdaQueryWrapper<>();
        lessonCouWrapper.eq(StudyLessonCou::getLessonId,lessonAdj.getLinkId());
        List<StudyLessonCou> lessonCouList = studyLessonCouMapper.selectList(lessonCouWrapper);
        //2.3、课程课件授权
        LambdaQueryWrapper<StudyLessonCouPerm> lessonCouPermWrapper = new LambdaQueryWrapper<>();
        lessonCouPermWrapper.eq(StudyLessonCouPerm::getLessonId,lessonAdj.getLinkId());
        List<StudyLessonCouPerm> lessonCouPermList = studyLessonCouPermMapper.selectList(lessonCouPermWrapper);
        //2.4、课程学习资料
        LambdaQueryWrapper<StudyLessonMarterials> lessonMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonMarterialsWrapper.eq(StudyLessonMarterials::getLessonId,lessonAdj.getLinkId());
        List<StudyLessonMarterials> lessonMarterialsList = studyLessonMarterialsMapper.selectList(lessonMarterialsWrapper);
        //3、原数据转移至历史
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //3.1、课程
        StudyLessonHis lessonHis = dozerMapper.map(studyLesson, StudyLessonHis.class);
        lessonHis.setId(IdGenerator.getIdGenerator());
        lessonHis.setLinkId(studyLesson.getId());
        lessonHis.setCreateTime(LocalDateTime.now());
        lessonHis.setUpdateTime(LocalDateTime.now());
        lessonHis.setCreateUserId(loginAppUser.getId());
        lessonHis.setCreateUserName(loginAppUser.getUserNameCh());
        lessonHis.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
        lessonHis.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
        lessonHis.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
        lessonHis.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
        lessonHis.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        studyLessonHisMapper.insert(lessonHis);
        //3.2、课程课件历史
        List<StudyLessonHisCou> lessonHisCouList = DozerUtils.mapList(dozerMapper, lessonCouList, StudyLessonHisCou.class);
        lessonHisCouList.forEach(lessonHisCou->{
            lessonHisCou.setId(IdGenerator.getIdGenerator());
            lessonHisCou.setLessonHisId(lessonHis.getId());
            lessonHisCou.setCreateTime(LocalDateTime.now());
            lessonHisCou.setUpdateTime(LocalDateTime.now());
            lessonHisCou.setCreateUserId(loginAppUser.getId());
            lessonHisCou.setCreateUserName(loginAppUser.getUserName());
            lessonHisCou.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
            lessonHisCou.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
            lessonHisCou.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
            lessonHisCou.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
            lessonHisCou.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        });
        studyLessonHisCouService.saveBatch(lessonHisCouList);
        //3.3、课程课件授权历史表
        List<StudyLessonHisCouPerm> lessonHisCouPermList = DozerUtils.mapList(dozerMapper, lessonCouPermList, StudyLessonHisCouPerm.class);
        lessonHisCouPermList.forEach(lessonHisCouPerm->{
            lessonHisCouPerm.setId(IdGenerator.getIdGenerator());
            lessonHisCouPerm.setLessonHisId(lessonHis.getId());
            lessonHisCouPerm.setCreateTime(LocalDateTime.now());
            lessonHisCouPerm.setUpdateTime(LocalDateTime.now());
            lessonHisCouPerm.setCreateUserId(loginAppUser.getId());
            lessonHisCouPerm.setCreateUserName(loginAppUser.getUserName());
            lessonHisCouPerm.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
            lessonHisCouPerm.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
            lessonHisCouPerm.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
            lessonHisCouPerm.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
            lessonHisCouPerm.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        });
        studyLessonHisCouPermService.saveBatch(lessonHisCouPermList);
        //3.4、课程学习资料历史
        List<StudyLessonHisMarterials> lessonHisMarterialsList = DozerUtils.mapList(dozerMapper, lessonMarterialsList, StudyLessonHisMarterials.class);
        lessonHisMarterialsList.forEach(lessonHisMarterials->{
            lessonHisMarterials.setId(IdGenerator.getIdGenerator());
            lessonHisMarterials.setLessonHisId(lessonHis.getId());
            lessonHisMarterials.setCreateTime(LocalDateTime.now());
            lessonHisMarterials.setUpdateTime(LocalDateTime.now());
            lessonHisMarterials.setCreateUserId(loginAppUser.getId());
            lessonHisMarterials.setCreateUserName(loginAppUser.getUserName());
            lessonHisMarterials.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
            lessonHisMarterials.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
            lessonHisMarterials.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
            lessonHisMarterials.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
            lessonHisMarterials.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        });
        studyLessonHisMarterialsService.saveBatch(lessonHisMarterialsList);
        //4、更新变更数据至原数据
        //4.1、课程
        studyLesson.setTypeCode(lessonAdj.getTypeCode());
        studyLesson.setTypeName(lessonAdj.getTypeName());
        studyLesson.setLabelCode(lessonAdj.getLabelCode());
        studyLesson.setLabelName(lessonAdj.getLabelName());
        studyLesson.setLessonName(lessonAdj.getLessonName());
        studyLesson.setDescription(lessonAdj.getDescription());
        studyLesson.setImgId(lessonAdj.getImgId());
        studyLesson.setImgUrl(lessonAdj.getImgUrl());
        studyLesson.setStatus(StudyStatus.TAKE_EFFECT.code);
        studyLesson.setUpdateTime(LocalDateTime.now());
        studyLessonMapper.updateById(studyLesson);
        //4.2、课程课件 先删后增
        studyLessonCouMapper.delete(lessonCouWrapper);
        LambdaQueryWrapper<StudyLessonAdjCou> lessonAdjCouWrapper = new LambdaQueryWrapper<>();
        lessonAdjCouWrapper.eq(StudyLessonAdjCou::getLessonAdjId,lessonAdj.getId());
        List<StudyLessonAdjCou> lessonAdjCouList = studyLessonAdjCouService.list(lessonAdjCouWrapper);
        List<StudyLessonCou> newLessonCouList = DozerUtils.mapList(dozerMapper, lessonAdjCouList, StudyLessonCou.class);
        newLessonCouList.forEach(newLessonCou->{
            newLessonCou.setId(IdGenerator.getIdGenerator());
            newLessonCou.setLessonId(studyLesson.getId());
            newLessonCou.setCreateTime(LocalDateTime.now());
            newLessonCou.setUpdateTime(LocalDateTime.now());
            newLessonCou.setCreateUserId(loginAppUser.getId());
            newLessonCou.setCreateUserName(loginAppUser.getUserName());
            newLessonCou.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
            newLessonCou.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
            newLessonCou.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
            newLessonCou.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
            newLessonCou.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        });
        studyLessonCouService.saveBatch(newLessonCouList);
        //4.3、课程课件授权 先删后增
        studyLessonCouPermMapper.delete(lessonCouPermWrapper);
        LambdaQueryWrapper<StudyLessonAdjCouPerm> lessonAdjCouPermWrapper = new LambdaQueryWrapper<>();
        lessonAdjCouPermWrapper.eq(StudyLessonAdjCouPerm::getLessonAdjId,lessonAdj.getId());
        List<StudyLessonAdjCouPerm> lessonAdjCouPermList = studyLessonAdjCouPermMapper.selectList(lessonAdjCouPermWrapper);
        List<StudyLessonCouPerm> newLessonCouPermList = DozerUtils.mapList(dozerMapper, lessonAdjCouPermList, StudyLessonCouPerm.class);
        newLessonCouPermList.forEach(newLessonCouPerm->{
            newLessonCouPerm.setId(IdGenerator.getIdGenerator());
            newLessonCouPerm.setLessonId(studyLesson.getId());
            newLessonCouPerm.setCreateTime(LocalDateTime.now());
            newLessonCouPerm.setUpdateTime(LocalDateTime.now());
            newLessonCouPerm.setCreateUserId(loginAppUser.getId());
            newLessonCouPerm.setCreateUserName(loginAppUser.getUserName());
            newLessonCouPerm.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
            newLessonCouPerm.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
            newLessonCouPerm.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
            newLessonCouPerm.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
            newLessonCouPerm.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        });
        studyLessonCouPermService.saveBatch(newLessonCouPermList);
        //4.4、课程学习资料 先删后增
        studyLessonMarterialsMapper.delete(lessonMarterialsWrapper);
        LambdaQueryWrapper<StudyLessonAdjMarterials> lessonAdjMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonAdjMarterialsWrapper.eq(StudyLessonAdjMarterials::getLessonAdjId,lessonAdj.getId());
        List<StudyLessonAdjMarterials> lessonAdjMarterialsList = studyLessonAdjMarterialsService.list(lessonAdjMarterialsWrapper);
        List<StudyLessonMarterials> newLessonMarterialsList = DozerUtils.mapList(dozerMapper, lessonAdjMarterialsList, StudyLessonMarterials.class);
        newLessonMarterialsList.forEach(newLessonMarterials->{
            newLessonMarterials.setId(IdGenerator.getIdGenerator());
            newLessonMarterials.setLessonId(studyLesson.getId());
            newLessonMarterials.setCreateTime(LocalDateTime.now());
            newLessonMarterials.setUpdateTime(LocalDateTime.now());
            newLessonMarterials.setCreateUserId(loginAppUser.getId());
            newLessonMarterials.setCreateUserName(loginAppUser.getUserName());
            newLessonMarterials.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
            newLessonMarterials.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
            newLessonMarterials.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
            newLessonMarterials.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
            newLessonMarterials.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        });
        studyLessonMarterialsService.saveBatch(newLessonMarterialsList);
        return true;
    }

    @Override
    @Transactional
    public Boolean procDelete(String procInstId){
        StudyLessonAdj lessonAdj = this.getByProcInstId(procInstId);
        studyLessonAdjMapper.deleteById(lessonAdj.getId());
        //删除课程课件关系表
        LambdaQueryWrapper<StudyLessonAdjCou> lessonAdjCouWrapper = new LambdaQueryWrapper<>();
        lessonAdjCouWrapper.eq(StudyLessonAdjCou::getLessonAdjId,lessonAdj.getId());
        studyLessonAdjCouService.remove(lessonAdjCouWrapper);
        //删除课程课件授权关系表
        LambdaQueryWrapper<StudyLessonAdjCouPerm> lessonAdjCouPermWrapper = new LambdaQueryWrapper<>();
        lessonAdjCouPermWrapper.eq(StudyLessonAdjCouPerm::getLessonAdjId,lessonAdj.getId());
        studyLessonAdjCouPermMapper.delete(lessonAdjCouPermWrapper);
        //删除课程学习资料表
        LambdaQueryWrapper<StudyLessonAdjMarterials> lessonAdjMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonAdjMarterialsWrapper.eq(StudyLessonAdjMarterials::getLessonAdjId,lessonAdj.getId());
        studyLessonAdjMarterialsService.remove(lessonAdjMarterialsWrapper);
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long lessonAdjId) {
        StudyLessonAdj lessonAdj = this.getById(lessonAdjId);
        if (!lessonAdj.getAuditStatus().equals(WfProcessEnum.DRAFT.getType())) {
            throw new IncloudException("仅审核状态为草稿可删除！");
        }
        studyLessonAdjMapper.deleteById(lessonAdj.getId());
        //删除课程课件关系表
        LambdaQueryWrapper<StudyLessonAdjCou> lessonAdjCouWrapper = new LambdaQueryWrapper<>();
        lessonAdjCouWrapper.eq(StudyLessonAdjCou::getLessonAdjId,lessonAdj.getId());
        studyLessonAdjCouService.remove(lessonAdjCouWrapper);
        //删除课程课件授权关系表
        LambdaQueryWrapper<StudyLessonAdjCouPerm> lessonAdjCouPermWrapper = new LambdaQueryWrapper<>();
        lessonAdjCouPermWrapper.eq(StudyLessonAdjCouPerm::getLessonAdjId,lessonAdjId);
        studyLessonAdjCouPermMapper.delete(lessonAdjCouPermWrapper);
        //删除课程学习资料表
        LambdaQueryWrapper<StudyLessonAdjMarterials> lessonAdjMarterialsWrapper = new LambdaQueryWrapper<>();
        lessonAdjMarterialsWrapper.eq(StudyLessonAdjMarterials::getLessonAdjId,lessonAdj.getId());
        studyLessonAdjMarterialsService.remove(lessonAdjMarterialsWrapper);
        //删除流程信息
        WfEngineDto.StateDto stateDto = new WfEngineDto.StateDto();
        stateDto.setCamundaProcdefId(lessonAdj.getCamundaProcinsId());
        stateDto.setCamundaProcinsId(lessonAdj.getCamundaProcinsId());
        wfClient.deleteOnlyProcess(stateDto);
        return true;
    }

    /////////////////////////////////////////当前类提供的方法/////////////////////////////////////////

    /**
     * 检验新增修改数据
     * @param lessonAdjDto
     */
    private void checkData(StudyLessonAdjDto lessonAdjDto){
        if (StringUtils.isBlank(lessonAdjDto.getLabelCode())) {
            throw new IncloudException("请选择标签");
        }
        if (StringUtils.isBlank(lessonAdjDto.getTypeCode())) {
            throw new IncloudException("请选择分类");
        }
        if (StringUtils.isBlank(lessonAdjDto.getLessonName())) {
            throw new IncloudException("请填写课程名称");
        }
        if (StringUtils.isBlank(lessonAdjDto.getImgUrl())) {
            throw new IncloudException("请上传封面图");
        }
        if (CollectionUtils.isEmpty(lessonAdjDto.getCouList())) {
            throw new IncloudException("请选择关联的课件");
        }
        if (CollectionUtils.isNotEmpty(lessonAdjDto.getCouList())) {
            List<StudyLessonAdjCouDto> couDtoList = lessonAdjDto.getCouList();
            for (StudyLessonAdjCouDto couDto : couDtoList){
                if (ObjectUtils.isEmpty(couDto.getStudyBestLessTime())) {
                    throw new IncloudException("课件名称："+couDto.getCouName()+"的最低学习时长未填写！");
                }
                if (ObjectUtils.isNotEmpty(couDto.getUseRange()) && UseRangeEnum.SIYOU.code.equals(couDto.getUseRange())){
                    if (CollectionUtils.isEmpty(couDto.getOrgPermList()) && CollectionUtils.isEmpty(couDto.getUserPermList())) {
                        throw new IncloudException("课件名称:"+couDto.getCouName() + "的使用权限是私有状态，请选择权限组织或者权限人员！");
                    }
                }
            }
        }
    }

    /**
     * 通过procInstId获取详情
     * @param procInstId
     * @return
     */
    private StudyLessonAdj getByProcInstId(String procInstId){
        LambdaQueryWrapper<StudyLessonAdj> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonAdj::getCamundaProcinsId,procInstId);
        return studyLessonAdjMapper.selectOne(queryWrapper);
    }

    /**
     * 通过课程调整表主键获取子表信息
     * @param lessonAdjVo
     */
    private void findSons(StudyLessonAdjVo lessonAdjVo){
        if (ObjectUtils.isNotEmpty(lessonAdjVo)) {
            //课程课件调整表
            LambdaQueryWrapper<StudyLessonAdjCou> lessonAdjCouWrapper = new LambdaQueryWrapper<>();
            lessonAdjCouWrapper.eq(StudyLessonAdjCou::getLessonAdjId,lessonAdjVo.getId());
            List<StudyLessonAdjCou> studyLessonAdjCouList = studyLessonAdjCouService.list(lessonAdjCouWrapper);
            List<StudyLessonAdjCouVo> studyLessonAdjCouVoList = DozerUtils.mapList(dozerMapper, studyLessonAdjCouList, StudyLessonAdjCouVo.class);
            //课程课件调整表放入课件授权信息
            this.setLessonAdjCouPerms(studyLessonAdjCouVoList,lessonAdjVo.getId());
            lessonAdjVo.setCouList(studyLessonAdjCouVoList);
            //课程学习资料调整表
            LambdaQueryWrapper<StudyLessonAdjMarterials> lessonAdjMarterialsWrapper = new LambdaQueryWrapper<>();
            lessonAdjMarterialsWrapper.eq(StudyLessonAdjMarterials::getLessonAdjId,lessonAdjVo.getId());
            List<StudyLessonAdjMarterials> studyLessonAdjMarterialsList = studyLessonAdjMarterialsService.list(lessonAdjMarterialsWrapper);
            List<StudyLessonAdjMarterialsVo> studyLessonAdjMarterialsVoList = DozerUtils.mapList(dozerMapper, studyLessonAdjMarterialsList, StudyLessonAdjMarterialsVo.class);
            lessonAdjVo.setMarterialsList(studyLessonAdjMarterialsVoList);
        }
    }

    /**
     * 往课程课件调整表中放入课件授权信息
     * @param lessonAdjCouVos
     * @param lessonAdjId
     */
    private void setLessonAdjCouPerms(List<StudyLessonAdjCouVo> lessonAdjCouVos,Long lessonAdjId){
        if (CollectionUtils.isNotEmpty(lessonAdjCouVos)) {
            //根据课程id 查出该课程下所有的课件授权信息
            LambdaQueryWrapper<StudyLessonAdjCouPerm> lessonAdjCouPermWrapper = new LambdaQueryWrapper<>();
            lessonAdjCouPermWrapper.eq(StudyLessonAdjCouPerm::getLessonAdjId,lessonAdjId);
            List<StudyLessonAdjCouPerm> lessonAdjCouPermList = studyLessonAdjCouPermMapper.selectList(lessonAdjCouPermWrapper);
            if (CollectionUtils.isNotEmpty(lessonAdjCouPermList)) {
                //如果课件授权信息不为空 根据课件id进行分组
                Map<Long, List<StudyLessonAdjCouPerm>> couIdMap = lessonAdjCouPermList.stream().collect(Collectors.groupingBy(StudyLessonAdjCouPerm::getCouId));
                //循环课程课件信息 把课件授权信息放入课程课件中
                for (StudyLessonAdjCouVo lessonAdjCouVo : lessonAdjCouVos){
                    List<StudyLessonAdjCouPerm> lessonAdjCouPerms = couIdMap.get(lessonAdjCouVo.getCouId());
                    List<StudyLessonAdjCouPermVo> orgPermList = new ArrayList<>();
                    List<StudyLessonAdjCouPermVo> userPermList = new ArrayList<>();
                    if (UseRangeEnum.SIYOU.code.equals(lessonAdjCouVo.getUseRange())) {
                        for (StudyLessonAdjCouPerm lessonAdjCouPerm : lessonAdjCouPerms){
                            StudyLessonAdjCouPermVo lessonAdjCouPermVo = dozerMapper.map(lessonAdjCouPerm, StudyLessonAdjCouPermVo.class);
                            if (lessonAdjCouPerm.getRangeType().equals(RangeTypeEnum.USER.code)) {
                                userPermList.add(lessonAdjCouPermVo);
                            }else {
                                orgPermList.add(lessonAdjCouPermVo);
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(orgPermList)) {
                        lessonAdjCouVo.setOrgPermList(orgPermList);
                    }
                    if (CollectionUtils.isNotEmpty(userPermList)) {
                        lessonAdjCouVo.setUserPermList(userPermList);
                    }
                }
            }
        }
    }

    /**
     * 往课程课件调整授权表中 放入课件授权信息
     * @param lessonAdjCouDto
     */
    private void saveLessonAdjCouPerm(StudyLessonAdjCouDto lessonAdjCouDto){
        //1.先删原来的数据
        LambdaQueryWrapper<StudyLessonAdjCouPerm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonAdjCouPerm::getLessonAdjId,lessonAdjCouDto.getLessonAdjId());
        queryWrapper.eq(StudyLessonAdjCouPerm::getCouId,lessonAdjCouDto.getCouId());
        studyLessonAdjCouPermMapper.delete(queryWrapper);
        //2.再进行新增数据
        List<StudyLessonAdjCouPerm> addLessonAdjCouPermList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(lessonAdjCouDto.getOrgPermList())) {
            List<StudyLessonAdjCouPerm> orgPermList = DozerUtils.mapList(dozerMapper, lessonAdjCouDto.getOrgPermList(), StudyLessonAdjCouPerm.class);
            orgPermList.forEach(perm -> {
                perm.setId(IdGenerator.getIdGenerator());
                perm.setLessonAdjId(lessonAdjCouDto.getLessonAdjId());
                perm.setCouId(lessonAdjCouDto.getCouId());
                addLessonAdjCouPermList.add(perm);
            });
        }
        if (CollectionUtils.isNotEmpty(lessonAdjCouDto.getUserPermList())) {
            List<StudyLessonAdjCouPerm> userPermList = DozerUtils.mapList(dozerMapper, lessonAdjCouDto.getUserPermList(), StudyLessonAdjCouPerm.class);
            userPermList.forEach(perm -> {
                perm.setId(IdGenerator.getIdGenerator());
                perm.setLessonAdjId(lessonAdjCouDto.getLessonAdjId());
                perm.setCouId(lessonAdjCouDto.getCouId());
                perm.setRangeType(RangeTypeEnum.USER.code);
                addLessonAdjCouPermList.add(perm);
            });
        }
        if (CollectionUtils.isNotEmpty(addLessonAdjCouPermList)) {
            studyLessonAdjCouPermService.saveBatch(addLessonAdjCouPermList);
        }
    }
}
