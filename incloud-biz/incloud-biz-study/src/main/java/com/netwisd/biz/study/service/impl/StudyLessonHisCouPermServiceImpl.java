package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyLessonHisCouPerm;
import com.netwisd.biz.study.mapper.StudyLessonHisCouPermMapper;
import com.netwisd.biz.study.service.StudyLessonHisCouPermService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyLessonHisCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonHisCouPermVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 课程课件授权历史表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:53:32
 */
@Service
@Slf4j
public class StudyLessonHisCouPermServiceImpl extends ServiceImpl<StudyLessonHisCouPermMapper, StudyLessonHisCouPerm> implements StudyLessonHisCouPermService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonHisCouPermMapper studyLessonHisCouPermMapper;

    /**
    * 单表简单查询操作
    * @param studyLessonHisCouPermDto
    * @return
    */
    @Override
    public Page list(StudyLessonHisCouPermDto studyLessonHisCouPermDto) {
        LambdaQueryWrapper<StudyLessonHisCouPerm> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyLessonHisCouPerm> page = studyLessonHisCouPermMapper.selectPage(studyLessonHisCouPermDto.getPage(),queryWrapper);
        Page<StudyLessonHisCouPermVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyLessonHisCouPermVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyLessonHisCouPermDto
    * @return
    */
    @Override
    public Page lists(StudyLessonHisCouPermDto studyLessonHisCouPermDto) {
        Page<StudyLessonHisCouPermVo> pageVo = studyLessonHisCouPermMapper.getPageList(studyLessonHisCouPermDto.getPage(),studyLessonHisCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyLessonHisCouPermVo get(Long id) {
        StudyLessonHisCouPerm studyLessonHisCouPerm = super.getById(id);
        StudyLessonHisCouPermVo studyLessonHisCouPermVo = null;
        if(studyLessonHisCouPerm !=null){
            studyLessonHisCouPermVo = dozerMapper.map(studyLessonHisCouPerm,StudyLessonHisCouPermVo.class);
        }
        log.debug("查询成功");
        return studyLessonHisCouPermVo;
    }

    /**
    * 保存实体
    * @param studyLessonHisCouPermDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyLessonHisCouPermDto studyLessonHisCouPermDto) {
        StudyLessonHisCouPerm studyLessonHisCouPerm = dozerMapper.map(studyLessonHisCouPermDto,StudyLessonHisCouPerm.class);
        boolean result = super.save(studyLessonHisCouPerm);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyLessonHisCouPermDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyLessonHisCouPermDto studyLessonHisCouPermDto) {
        studyLessonHisCouPermDto.setUpdateTime(LocalDateTime.now());
        StudyLessonHisCouPerm studyLessonHisCouPerm = dozerMapper.map(studyLessonHisCouPermDto,StudyLessonHisCouPerm.class);
        boolean result = super.updateById(studyLessonHisCouPerm);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
