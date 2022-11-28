package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyLessonCouPerm;
import com.netwisd.biz.study.mapper.StudyLessonCouPermMapper;
import com.netwisd.biz.study.service.StudyLessonCouPermService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyLessonCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonCouPermVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 课程课件授权表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 11:13:20
 */
@Service
@Slf4j
public class StudyLessonCouPermServiceImpl extends ServiceImpl<StudyLessonCouPermMapper, StudyLessonCouPerm> implements StudyLessonCouPermService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonCouPermMapper studyLessonCouPermMapper;

    /**
    * 单表简单查询操作
    * @param studyLessonCouPermDto
    * @return
    */
    @Override
    public Page list(StudyLessonCouPermDto studyLessonCouPermDto) {
        LambdaQueryWrapper<StudyLessonCouPerm> queryWrapper = new LambdaQueryWrapper<>();
        Page<StudyLessonCouPerm> page = studyLessonCouPermMapper.selectPage(studyLessonCouPermDto.getPage(),queryWrapper);
        Page<StudyLessonCouPermVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyLessonCouPermVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyLessonCouPermDto
    * @return
    */
    @Override
    public Page lists(StudyLessonCouPermDto studyLessonCouPermDto) {
        Page<StudyLessonCouPermVo> pageVo = studyLessonCouPermMapper.getPageList(studyLessonCouPermDto.getPage(),studyLessonCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyLessonCouPermVo get(Long id) {
        StudyLessonCouPerm studyLessonCouPerm = super.getById(id);
        StudyLessonCouPermVo studyLessonCouPermVo = null;
        if(studyLessonCouPerm !=null){
            studyLessonCouPermVo = dozerMapper.map(studyLessonCouPerm,StudyLessonCouPermVo.class);
        }
        log.debug("查询成功");
        return studyLessonCouPermVo;
    }

    /**
    * 保存实体
    * @param studyLessonCouPermDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyLessonCouPermDto studyLessonCouPermDto) {
        StudyLessonCouPerm studyLessonCouPerm = dozerMapper.map(studyLessonCouPermDto,StudyLessonCouPerm.class);
        boolean result = super.save(studyLessonCouPerm);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyLessonCouPermDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyLessonCouPermDto studyLessonCouPermDto) {
        studyLessonCouPermDto.setUpdateTime(LocalDateTime.now());
        StudyLessonCouPerm studyLessonCouPerm = dozerMapper.map(studyLessonCouPermDto,StudyLessonCouPerm.class);
        boolean result = super.updateById(studyLessonCouPerm);
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
