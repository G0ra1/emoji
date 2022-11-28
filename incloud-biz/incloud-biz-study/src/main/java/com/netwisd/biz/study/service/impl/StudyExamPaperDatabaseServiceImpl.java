package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyExamPaperDatabase;
import com.netwisd.biz.study.mapper.StudyExamPaperDatabaseMapper;
import com.netwisd.biz.study.service.StudyExamPaperDatabaseService;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyExamPaperDatabaseDto;
import com.netwisd.biz.study.vo.StudyExamPaperDatabaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 试卷申请题库结果表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 17:36:55
 */
@Service
@Slf4j
public class StudyExamPaperDatabaseServiceImpl extends BatchServiceImpl<StudyExamPaperDatabaseMapper, StudyExamPaperDatabase> implements StudyExamPaperDatabaseService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyExamPaperDatabaseMapper studyExamPaperDatabaseMapper;

    /**
    * 单表简单查询操作
    * @param studyExamPaperDatabaseDto
    * @return
    */
    @Override
    public Page list(StudyExamPaperDatabaseDto studyExamPaperDatabaseDto) {
        LambdaQueryWrapper<StudyExamPaperDatabase> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyExamPaperDatabase> page = studyExamPaperDatabaseMapper.selectPage(studyExamPaperDatabaseDto.getPage(),queryWrapper);
        Page<StudyExamPaperDatabaseVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyExamPaperDatabaseVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyExamPaperDatabaseDto
    * @return
    */
    @Override
    public Page lists(StudyExamPaperDatabaseDto studyExamPaperDatabaseDto) {
        Page<StudyExamPaperDatabaseVo> pageVo = studyExamPaperDatabaseMapper.getPageList(studyExamPaperDatabaseDto.getPage(),studyExamPaperDatabaseDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyExamPaperDatabaseVo get(Long id) {
        StudyExamPaperDatabase studyExamPaperDatabase = super.getById(id);
        StudyExamPaperDatabaseVo studyExamPaperDatabaseVo = null;
        if(studyExamPaperDatabase !=null){
            studyExamPaperDatabaseVo = dozerMapper.map(studyExamPaperDatabase,StudyExamPaperDatabaseVo.class);
        }
        log.debug("查询成功");
        return studyExamPaperDatabaseVo;
    }

    /**
    * 保存实体
    * @param studyExamPaperDatabaseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyExamPaperDatabaseDto studyExamPaperDatabaseDto) {
        StudyExamPaperDatabase studyExamPaperDatabase = dozerMapper.map(studyExamPaperDatabaseDto,StudyExamPaperDatabase.class);
        boolean result = super.save(studyExamPaperDatabase);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyExamPaperDatabaseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyExamPaperDatabaseDto studyExamPaperDatabaseDto) {
        studyExamPaperDatabaseDto.setUpdateTime(LocalDateTime.now());
        StudyExamPaperDatabase studyExamPaperDatabase = dozerMapper.map(studyExamPaperDatabaseDto,StudyExamPaperDatabase.class);
        boolean result = super.updateById(studyExamPaperDatabase);
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
