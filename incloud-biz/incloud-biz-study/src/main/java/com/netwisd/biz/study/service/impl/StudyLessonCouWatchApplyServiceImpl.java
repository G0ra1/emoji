package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.constants.ApplyStatus;
import com.netwisd.biz.study.entity.StudyLessonCouWatchApply;
import com.netwisd.biz.study.mapper.StudyLessonCouWatchApplyMapper;
import com.netwisd.biz.study.service.StudyLessonCouWatchApplyService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyLessonCouWatchApplyDto;
import com.netwisd.biz.study.vo.StudyLessonCouWatchApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 课程课件观看申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-29 14:22:04
 */
@Service
@Slf4j
public class StudyLessonCouWatchApplyServiceImpl extends ServiceImpl<StudyLessonCouWatchApplyMapper, StudyLessonCouWatchApply> implements StudyLessonCouWatchApplyService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonCouWatchApplyMapper studyLessonCouWatchApplyMapper;

    /**
    * 单表简单查询操作
    * @param studyLessonCouWatchApplyDto
    * @return
    */
    @Override
    public Page list(StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto) {
        LambdaQueryWrapper<StudyLessonCouWatchApply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonCouWatchApply::getApplyStatus, ApplyStatus.NO_APPLY.code);
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyLessonCouWatchApplyDto.getUserId()),StudyLessonCouWatchApply::getUserId,studyLessonCouWatchApplyDto.getUserId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyLessonCouWatchApplyDto.getRangeId()),StudyLessonCouWatchApply::getRangeId,studyLessonCouWatchApplyDto.getRangeId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyLessonCouWatchApplyDto.getRangeName()),StudyLessonCouWatchApply::getRangeName,studyLessonCouWatchApplyDto.getRangeName());
        Page<StudyLessonCouWatchApply> page = studyLessonCouWatchApplyMapper.selectPage(studyLessonCouWatchApplyDto.getPage(),queryWrapper);
        Page<StudyLessonCouWatchApplyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyLessonCouWatchApplyVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyLessonCouWatchApplyVo get(Long id) {
        StudyLessonCouWatchApply studyLessonCouWatchApply = super.getById(id);
        StudyLessonCouWatchApplyVo studyLessonCouWatchApplyVo = null;
        if(studyLessonCouWatchApply !=null){
            studyLessonCouWatchApplyVo = dozerMapper.map(studyLessonCouWatchApply,StudyLessonCouWatchApplyVo.class);
        }
        log.debug("查询成功");
        return studyLessonCouWatchApplyVo;
    }

    /**
    * 保存实体
    * @param studyLessonCouWatchApplyDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto) {
        this.checkData(studyLessonCouWatchApplyDto);
        if (ObjectUtils.isEmpty(studyLessonCouWatchApplyDto.getApplyStatus())) {
            studyLessonCouWatchApplyDto.setApplyStatus(ApplyStatus.NO_APPLY.code);
        }
        StudyLessonCouWatchApply studyLessonCouWatchApply = dozerMapper.map(studyLessonCouWatchApplyDto,StudyLessonCouWatchApply.class);
        boolean result = super.save(studyLessonCouWatchApply);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyLessonCouWatchApplyDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto) {
        this.checkData(studyLessonCouWatchApplyDto);
        if (ObjectUtils.isEmpty(studyLessonCouWatchApplyDto.getApplyStatus())) {
            throw new IncloudException("请选择申请状态 1 审核通过 2审核失败");
        }
        studyLessonCouWatchApplyDto.setUpdateTime(LocalDateTime.now());
        StudyLessonCouWatchApply studyLessonCouWatchApply = dozerMapper.map(studyLessonCouWatchApplyDto,StudyLessonCouWatchApply.class);
        boolean result = super.updateById(studyLessonCouWatchApply);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    @Override
    public Boolean updateStatusBatch(List<StudyLessonCouWatchApplyDto> studyLessonCouWatchApplyDtos) {
        if (CollectionUtil.isNotEmpty(studyLessonCouWatchApplyDtos)) {
            studyLessonCouWatchApplyDtos.forEach(this::update);
        }
        return true;
    }

    /**
     * 检验必填字段
     * @param studyLessonCouWatchApplyDto
     */
    private void checkData(StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto){
        if (ObjectUtils.isEmpty(studyLessonCouWatchApplyDto.getUserId())) {
            throw new IncloudException("请填写当前登陆人主键");
        }
        if (StringUtils.isBlank(studyLessonCouWatchApplyDto.getUserName())) {
            throw new IncloudException("请填写当前登陆人用户名");
        }
        if (StringUtils.isBlank(studyLessonCouWatchApplyDto.getUserNameCh())) {
            throw new IncloudException("请填写当前登陆人中文名称");
        }
        if (ObjectUtils.isEmpty(studyLessonCouWatchApplyDto.getRangeType())) {
            throw new IncloudException("请填写申请对象类型 1 课程 2 课件");
        }
        if (ObjectUtils.isEmpty(studyLessonCouWatchApplyDto.getRangeId())) {
            throw new IncloudException("请填写申请对象主键");
        }
        if (ObjectUtils.isEmpty(studyLessonCouWatchApplyDto.getRangeName())) {
            throw new IncloudException("请填写申请对象名称");
        }
        if (StringUtils.isBlank(studyLessonCouWatchApplyDto.getApplyMessage())) {
            throw new IncloudException("请填写申请信息");
        }
    }
}
