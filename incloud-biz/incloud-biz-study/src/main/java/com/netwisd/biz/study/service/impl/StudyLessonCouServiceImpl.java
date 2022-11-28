package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyLessonCou;
import com.netwisd.biz.study.mapper.StudyLessonCouMapper;
import com.netwisd.biz.study.service.StudyLessonCouService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyLessonCouDto;
import com.netwisd.biz.study.vo.StudyLessonCouVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 课程课件表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:20:24
 */
@Service
@Slf4j
public class StudyLessonCouServiceImpl extends ServiceImpl<StudyLessonCouMapper, StudyLessonCou> implements StudyLessonCouService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonCouMapper studyLessonCouMapper;

    /**
    * 单表简单查询操作
    * @param studyLessonCouDto
    * @return
    */
    @Override
    public Page list(StudyLessonCouDto studyLessonCouDto) {
        LambdaQueryWrapper<StudyLessonCou> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyLessonCou> page = studyLessonCouMapper.selectPage(studyLessonCouDto.getPage(),queryWrapper);
        Page<StudyLessonCouVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyLessonCouVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyLessonCouDto
    * @return
    */
    @Override
    public Page lists(StudyLessonCouDto studyLessonCouDto) {
        Page<StudyLessonCouVo> pageVo = studyLessonCouMapper.getPageList(studyLessonCouDto.getPage(),studyLessonCouDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyLessonCouVo get(Long id) {
        StudyLessonCou studyLessonCou = super.getById(id);
        StudyLessonCouVo studyLessonCouVo = null;
        if(studyLessonCou !=null){
            studyLessonCouVo = dozerMapper.map(studyLessonCou,StudyLessonCouVo.class);
        }
        log.debug("查询成功");
        return studyLessonCouVo;
    }

    /**
    * 保存实体
    * @param studyLessonCouDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyLessonCouDto studyLessonCouDto) {
        StudyLessonCou studyLessonCou = dozerMapper.map(studyLessonCouDto,StudyLessonCou.class);
        boolean result = super.save(studyLessonCou);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyLessonCouDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyLessonCouDto studyLessonCouDto) {
        studyLessonCouDto.setUpdateTime(LocalDateTime.now());
        StudyLessonCou studyLessonCou = dozerMapper.map(studyLessonCouDto,StudyLessonCou.class);
        boolean result = super.updateById(studyLessonCou);
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
