package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyLessonMarterials;
import com.netwisd.biz.study.mapper.StudyLessonMarterialsMapper;
import com.netwisd.biz.study.service.StudyLessonMarterialsService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyLessonMarterialsDto;
import com.netwisd.biz.study.vo.StudyLessonMarterialsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 课程学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:21:47
 */
@Service
@Slf4j
public class StudyLessonMarterialsServiceImpl extends ServiceImpl<StudyLessonMarterialsMapper, StudyLessonMarterials> implements StudyLessonMarterialsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonMarterialsMapper studyLessonMarterialsMapper;

    /**
    * 单表简单查询操作
    * @param studyLessonMarterialsDto
    * @return
    */
    @Override
    public Page list(StudyLessonMarterialsDto studyLessonMarterialsDto) {
        LambdaQueryWrapper<StudyLessonMarterials> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyLessonMarterials> page = studyLessonMarterialsMapper.selectPage(studyLessonMarterialsDto.getPage(),queryWrapper);
        Page<StudyLessonMarterialsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyLessonMarterialsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyLessonMarterialsDto
    * @return
    */
    @Override
    public Page lists(StudyLessonMarterialsDto studyLessonMarterialsDto) {
        Page<StudyLessonMarterialsVo> pageVo = studyLessonMarterialsMapper.getPageList(studyLessonMarterialsDto.getPage(),studyLessonMarterialsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyLessonMarterialsVo get(Long id) {
        StudyLessonMarterials studyLessonMarterials = super.getById(id);
        StudyLessonMarterialsVo studyLessonMarterialsVo = null;
        if(studyLessonMarterials !=null){
            studyLessonMarterialsVo = dozerMapper.map(studyLessonMarterials,StudyLessonMarterialsVo.class);
        }
        log.debug("查询成功");
        return studyLessonMarterialsVo;
    }

    /**
    * 保存实体
    * @param studyLessonMarterialsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyLessonMarterialsDto studyLessonMarterialsDto) {
        StudyLessonMarterials studyLessonMarterials = dozerMapper.map(studyLessonMarterialsDto,StudyLessonMarterials.class);
        boolean result = super.save(studyLessonMarterials);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyLessonMarterialsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyLessonMarterialsDto studyLessonMarterialsDto) {
        studyLessonMarterialsDto.setUpdateTime(LocalDateTime.now());
        StudyLessonMarterials studyLessonMarterials = dozerMapper.map(studyLessonMarterialsDto,StudyLessonMarterials.class);
        boolean result = super.updateById(studyLessonMarterials);
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
