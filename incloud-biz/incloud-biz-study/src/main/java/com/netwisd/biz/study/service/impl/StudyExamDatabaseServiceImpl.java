package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.*;
import com.netwisd.biz.study.service.StudyExamDatabaseService;
import com.netwisd.biz.study.vo.StudyExamQuestionAnswerVo;
import com.netwisd.biz.study.vo.StudyExamQuestionVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyExamDatabaseDto;
import com.netwisd.biz.study.vo.StudyExamDatabaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 题库定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:53:35
 */
@Service
@Slf4j
public class StudyExamDatabaseServiceImpl extends ServiceImpl<StudyExamDatabaseMapper, StudyExamDatabase> implements StudyExamDatabaseService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamDatabaseMapper studyExamDatabaseMapper;

    @Autowired
    private StudyExamQuestionMapper studyExamQuestionMapper;

    @Autowired
    private StudyExamQuestionAnswerMapper studyExamQuestionAnswerMapper;

    @Autowired
    private StudyExamPaperDatabaseMapper studyExamPaperDatabaseMapper;

    @Autowired
    private MdmClient mdmClient;
    /**
    * 单表简单查询操作
    * @param studyExamDatabaseDto
    * @return
    */
    @Override
    public Page list(StudyExamDatabaseDto studyExamDatabaseDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyExamDatabase> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.like(StringUtils.isNotBlank(studyExamDatabaseDto.getDatabaseName()), StudyExamDatabase::getDatabaseName, studyExamDatabaseDto.getDatabaseName());
        queryWrapper.like(ObjectUtils.isNotEmpty(studyExamDatabaseDto.getDescription()),StudyExamDatabase::getDescription,studyExamDatabaseDto.getDescription());
        queryWrapper.like(ObjectUtils.isNotEmpty(studyExamDatabaseDto.getCreateUserName()),StudyExamDatabase::getCreateUserName,studyExamDatabaseDto.getCreateUserName());
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //如果是讲师，只看到自己申请的
            queryWrapper.eq(StudyExamDatabase::getCreateUserId, loginAppUser.getId());
        }
        Page<StudyExamDatabase> page = studyExamDatabaseMapper.selectPage(studyExamDatabaseDto.getPage(),queryWrapper);
        Page<StudyExamDatabaseVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyExamDatabaseVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyExamDatabaseDto
    * @return
    */
    @Override
    public List<StudyExamDatabaseVo> lists(StudyExamDatabaseDto studyExamDatabaseDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper <StudyExamDatabase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtils.isNotEmpty(studyExamDatabaseDto.getDatabaseName()),StudyExamDatabase::getDatabaseName,studyExamDatabaseDto.getDatabaseName());
        queryWrapper.like(ObjectUtils.isNotEmpty(studyExamDatabaseDto.getDescription()),StudyExamDatabase::getDescription,studyExamDatabaseDto.getDescription());
        queryWrapper.like(ObjectUtils.isNotEmpty(studyExamDatabaseDto.getCreateUserName()),StudyExamDatabase::getCreateUserName,studyExamDatabaseDto.getCreateUserName());
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //如果是讲师，只看到自己申请的
            queryWrapper.eq(StudyExamDatabase::getCreateUserId, loginAppUser.getId());
        }
        List<StudyExamDatabase> page = studyExamDatabaseMapper.selectList(queryWrapper);
        List <StudyExamDatabaseVo> studyExamDatabaseVoPage = DozerUtils.mapList(dozerMapper, page, StudyExamDatabaseVo.class);
        log.debug("查询条数:"+studyExamDatabaseVoPage.size());
        return studyExamDatabaseVoPage;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyExamDatabaseVo get(Long id) {
        StudyExamDatabase studyExamDatabaseDef = super.getById(id);
        StudyExamDatabaseVo studyExamDatabaseDefVo = null;
        if (studyExamDatabaseDef != null) {
            studyExamDatabaseDefVo = dozerMapper.map(studyExamDatabaseDef, StudyExamDatabaseVo.class);
        }
        LambdaQueryWrapper<StudyExamQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamQuestion::getDatabaseId, id);
        List<StudyExamQuestion> studyExamQuestionDefs = studyExamQuestionMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(studyExamQuestionDefs)) {
            List<StudyExamQuestionVo> studyExamQuestionDefVos = DozerUtils.mapList(dozerMapper, studyExamQuestionDefs, StudyExamQuestionVo.class);
            List<Long> collect = studyExamQuestionDefVos.stream().map(StudyExamQuestionVo::getId).collect(Collectors.toList());
            LambdaQueryWrapper<StudyExamQuestionAnswer> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.in(StudyExamQuestionAnswer::getQuestionId, collect);
            List<StudyExamQuestionAnswer> studyExamQuestionAnswers = studyExamQuestionAnswerMapper.selectList(queryWrapper1);
            List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos = DozerUtils.mapList(dozerMapper, studyExamQuestionAnswers, StudyExamQuestionAnswerVo.class);
            Map<Long, List<StudyExamQuestionAnswerVo>> collect1 = studyExamQuestionAnswerVos.stream().collect(Collectors.groupingBy(StudyExamQuestionAnswerVo::getQuestionId));
            for (StudyExamQuestionVo studyExamQuestionDefVo : studyExamQuestionDefVos) {
                List<StudyExamQuestionAnswerVo> studyExamQuestionAnswerVos1 = collect1.get(studyExamQuestionDefVo.getId());
                studyExamQuestionDefVo.setAnswers(studyExamQuestionAnswerVos1);
            }
            if (studyExamDatabaseDefVo != null) {
                studyExamDatabaseDefVo.setStudyExamQuestionDefList(studyExamQuestionDefVos);
            }
        }
        return studyExamDatabaseDefVo;
    }

    /**
    * 保存实体
    * @param studyExamDatabaseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyExamDatabaseDto studyExamDatabaseDto) {
        if (studyExamDatabaseDto.getDatabaseName() == null) {
            throw new IncloudException("请输入题库名称");
        }
        StudyExamDatabase studyExamDatabaseDef = dozerMapper.map(studyExamDatabaseDto, StudyExamDatabase.class);
        return super.save(studyExamDatabaseDef);
    }

    /**
     * 查看多个题库详情
     * @param id id
     * @return List<StudyExamDatabaseDefVo>
     */
    @Override
    public List<StudyExamDatabaseVo> listByIds(String id) {
        List<String> ids = Stream.of(id.split(",")).collect(Collectors.toList());
        List<StudyExamDatabase> studyExamDatabaseDefList = this.listByIds(ids);
        List<StudyExamDatabaseVo> studyExamDatabaseDefVoList = DozerUtils.mapList(dozerMapper, studyExamDatabaseDefList, StudyExamDatabaseVo.class);
        for (StudyExamDatabaseVo studyExamDatabaseDefVo : studyExamDatabaseDefVoList) {
            StudyExamDatabaseVo examDatabaseDefVo = this.get(studyExamDatabaseDefVo.getId());
            studyExamDatabaseDefVo.setStudyExamQuestionDefList(examDatabaseDefVo.getStudyExamQuestionDefList());
        }
        return studyExamDatabaseDefVoList;
    }
    /**
    * 修改实体
    * @param studyExamDatabaseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyExamDatabaseDto studyExamDatabaseDto) {
        studyExamDatabaseDto.setUpdateTime(LocalDateTime.now());
        StudyExamDatabase studyExamDatabaseDef = dozerMapper.map(studyExamDatabaseDto, StudyExamDatabase.class);
        return super.updateById(studyExamDatabaseDef);
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        LambdaQueryWrapper<StudyExamQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyExamQuestion::getDatabaseId,id);
        List<StudyExamQuestion> studyQuestionList = studyExamQuestionMapper.selectList(queryWrapper);
         if (CollectionUtil.isNotEmpty(studyQuestionList)){
            throw new IncloudException("题库内有题目,不能删除");
        }
         LambdaQueryWrapper <StudyExamPaperDatabase> databaseWrapper = new LambdaQueryWrapper<>();
         databaseWrapper.eq(StudyExamPaperDatabase::getDatabaseId,id);
        List<StudyExamPaperDatabase> studyExamPaperDatabases = studyExamPaperDatabaseMapper.selectList(databaseWrapper);
        //题库被引用,不能删除
       if(CollectionUtil.isNotEmpty(studyExamPaperDatabases)) {
           throw new IncloudException("题库被选做随机试卷抽题题库,不能删除");
        }
        return super.removeById(id);
    }

}
