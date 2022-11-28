package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyLessonAdjCouPerm;
import com.netwisd.biz.study.mapper.StudyLessonAdjCouPermMapper;
import com.netwisd.biz.study.service.StudyLessonAdjCouPermService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyLessonAdjCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonAdjCouPermVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 课程课件授权调整表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:54:14
 */
@Service
@Slf4j
public class StudyLessonAdjCouPermServiceImpl extends ServiceImpl<StudyLessonAdjCouPermMapper, StudyLessonAdjCouPerm> implements StudyLessonAdjCouPermService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonAdjCouPermMapper studyLessonAdjCouPermMapper;

    /**
    * 单表简单查询操作
    * @param studyLessonAdjCouPermDto
    * @return
    */
    @Override
    public Page list(StudyLessonAdjCouPermDto studyLessonAdjCouPermDto) {
        LambdaQueryWrapper<StudyLessonAdjCouPerm> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyLessonAdjCouPerm> page = studyLessonAdjCouPermMapper.selectPage(studyLessonAdjCouPermDto.getPage(),queryWrapper);
        Page<StudyLessonAdjCouPermVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyLessonAdjCouPermVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyLessonAdjCouPermDto
    * @return
    */
    @Override
    public Page lists(StudyLessonAdjCouPermDto studyLessonAdjCouPermDto) {
        Page<StudyLessonAdjCouPermVo> pageVo = studyLessonAdjCouPermMapper.getPageList(studyLessonAdjCouPermDto.getPage(),studyLessonAdjCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyLessonAdjCouPermVo get(Long id) {
        StudyLessonAdjCouPerm studyLessonAdjCouPerm = super.getById(id);
        StudyLessonAdjCouPermVo studyLessonAdjCouPermVo = null;
        if(studyLessonAdjCouPerm !=null){
            studyLessonAdjCouPermVo = dozerMapper.map(studyLessonAdjCouPerm,StudyLessonAdjCouPermVo.class);
        }
        log.debug("查询成功");
        return studyLessonAdjCouPermVo;
    }

    /**
    * 保存实体
    * @param studyLessonAdjCouPermDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyLessonAdjCouPermDto studyLessonAdjCouPermDto) {
        StudyLessonAdjCouPerm studyLessonAdjCouPerm = dozerMapper.map(studyLessonAdjCouPermDto,StudyLessonAdjCouPerm.class);
        boolean result = super.save(studyLessonAdjCouPerm);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyLessonAdjCouPermDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyLessonAdjCouPermDto studyLessonAdjCouPermDto) {
        studyLessonAdjCouPermDto.setUpdateTime(LocalDateTime.now());
        StudyLessonAdjCouPerm studyLessonAdjCouPerm = dozerMapper.map(studyLessonAdjCouPermDto,StudyLessonAdjCouPerm.class);
        boolean result = super.updateById(studyLessonAdjCouPerm);
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
