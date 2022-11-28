package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyUserCollect;
import com.netwisd.biz.study.mapper.StudyUserCollectMapper;
import com.netwisd.biz.study.service.StudyUserCollectService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyUserCollectDto;
import com.netwisd.biz.study.vo.StudyUserCollectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 人员收藏表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-27 11:16:09
 */
@Service
@Slf4j
public class StudyUserCollectServiceImpl extends ServiceImpl<StudyUserCollectMapper, StudyUserCollect> implements StudyUserCollectService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyUserCollectMapper studyUserCollectMapper;

    /**
    * 单表简单查询操作
    * @param studyUserCollectDto
    * @return
    */
    @Override
    public Page list(StudyUserCollectDto studyUserCollectDto) {
        LambdaQueryWrapper<StudyUserCollect> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyUserCollect> page = studyUserCollectMapper.selectPage(studyUserCollectDto.getPage(),queryWrapper);
        Page<StudyUserCollectVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyUserCollectVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyUserCollectDto
    * @return
    */
    @Override
    public Page lists(StudyUserCollectDto studyUserCollectDto) {
        Page<StudyUserCollectVo> pageVo = studyUserCollectMapper.getPageList(studyUserCollectDto.getPage(),studyUserCollectDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyUserCollectVo get(Long id) {
        StudyUserCollect studyUserCollect = super.getById(id);
        StudyUserCollectVo studyUserCollectVo = null;
        if(studyUserCollect !=null){
            studyUserCollectVo = dozerMapper.map(studyUserCollect,StudyUserCollectVo.class);
        }
        log.debug("查询成功");
        return studyUserCollectVo;
    }

    /**
    * 保存实体
    * @param studyUserCollectDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyUserCollectDto studyUserCollectDto) {
        StudyUserCollect studyUserCollect = dozerMapper.map(studyUserCollectDto,StudyUserCollect.class);
        boolean result = super.save(studyUserCollect);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyUserCollectDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyUserCollectDto studyUserCollectDto) {
        studyUserCollectDto.setUpdateTime(LocalDateTime.now());
        StudyUserCollect studyUserCollect = dozerMapper.map(studyUserCollectDto,StudyUserCollect.class);
        boolean result = super.updateById(studyUserCollect);
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
