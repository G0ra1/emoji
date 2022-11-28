package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.entity.StudyLesson;
import com.netwisd.biz.study.entity.StudyLessonComments;
import com.netwisd.biz.study.mapper.StudyLessonCommentsMapper;
import com.netwisd.biz.study.service.StudyLessonCommentsService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyLessonCommentsDto;
import com.netwisd.biz.study.vo.StudyLessonCommentsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description 课程评论表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:18:19
 */
@Service
@Slf4j
public class StudyLessonCommentsServiceImpl extends ServiceImpl<StudyLessonCommentsMapper, StudyLessonComments> implements StudyLessonCommentsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonCommentsMapper studyLessonCommentsMapper;

    //获取子集
    private Integer getKids(StudyLessonCommentsVo lessonCommentsVo,Integer commentSize,Long parentFullId){
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        LambdaQueryWrapper<StudyLessonComments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonComments::getParentId,lessonCommentsVo.getId());
        List<StudyLessonComments> lessonComments = studyLessonCommentsMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(lessonComments)) {
            List<StudyLessonCommentsVo> lessonCommentsVos = DozerUtils.mapList(dozerMapper, lessonComments, StudyLessonCommentsVo.class);
            commentSize = commentSize + lessonCommentsVos.size();
            for (StudyLessonCommentsVo commentsVo : lessonCommentsVos){
                if (commentsVo.getUserId().equals(loginAppUser.getId())) {
                    commentsVo.setIsDelete(YesNo.YES.code);
                }else {
                    commentsVo.setIsDelete(YesNo.NO.code);
                }
                commentsVo.setParentFullId(parentFullId);
                commentSize = this.getKids(commentsVo,commentSize,parentFullId);
            }
            lessonCommentsVo.setKids(lessonCommentsVos);
        }
        return commentSize;
    }

    @Override
    public Map<String,Object> getByLessonId(Long lessonId) {
        if (ObjectUtils.isEmpty(lessonId)) {
            throw new IncloudException("请传课程id");
        }
        Map<String,Object> returnMap = new HashMap<>();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        LambdaQueryWrapper<StudyLessonComments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonComments::getLevel,1);//级别为1的所有评论
        queryWrapper.eq(StudyLessonComments::getLessonId,lessonId);
        List<StudyLessonComments> lessonComments = studyLessonCommentsMapper.selectList(queryWrapper);
        List<StudyLessonCommentsVo> lessonCommentsVos = DozerUtils.mapList(dozerMapper, lessonComments, StudyLessonCommentsVo.class);
        if (CollectionUtils.isNotEmpty(lessonCommentsVos)) {
            Integer commentSize = lessonCommentsVos.size();
            //获取子集
            for (StudyLessonCommentsVo lessonCommentsVo : lessonCommentsVos){
                if (lessonCommentsVo.getUserId().equals(loginAppUser.getId())) {
                    lessonCommentsVo.setIsDelete(YesNo.YES.code);
                }else {
                    lessonCommentsVo.setIsDelete(YesNo.NO.code);
                }
                Long parentFullId = lessonCommentsVo.getId();
                commentSize = this.getKids(lessonCommentsVo, commentSize, parentFullId);
            }
            returnMap.put("commentSize",commentSize);
        }
        returnMap.put("commentList",lessonCommentsVos);
        return returnMap;
    }

    private void checkData(StudyLessonCommentsDto studyLessonCommentsDto){
        if (ObjectUtils.isEmpty(studyLessonCommentsDto.getLessonId())) {
            throw new IncloudException("请传课程id");
        }
        if (StringUtils.isBlank(studyLessonCommentsDto.getLessonName())) {
            throw new IncloudException("请传课程名称");
        }
        if (StringUtils.isBlank(studyLessonCommentsDto.getComment())) {
            throw new IncloudException("请传评论内容");
        }
        if (ObjectUtils.isEmpty(studyLessonCommentsDto.getParentId())) {
            throw new IncloudException("请传上级评论id");
        }
        if (studyLessonCommentsDto.getParentId() == 0) {
            studyLessonCommentsDto.setLevel(1);
        }
        if (studyLessonCommentsDto.getParentId() != 0) {
            LambdaQueryWrapper<StudyLessonComments> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StudyLessonComments::getId,studyLessonCommentsDto.getParentId());
            StudyLessonComments studyLessonComments = studyLessonCommentsMapper.selectOne(queryWrapper);
            studyLessonCommentsDto.setLevel(studyLessonComments.getLevel() + 1);
            studyLessonCommentsDto.setParentName(studyLessonComments.getUserNameCh());
        }
        if (ObjectUtils.isEmpty(studyLessonCommentsDto.getUserId())) {
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            studyLessonCommentsDto.setUserId(loginAppUser.getId());
            studyLessonCommentsDto.setUserName(loginAppUser.getUserName());
            studyLessonCommentsDto.setUserNameCh(loginAppUser.getUserNameCh());
        }
    }

    /**
    * 保存实体
    * @param studyLessonCommentsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyLessonCommentsDto studyLessonCommentsDto) {
        this.checkData(studyLessonCommentsDto);
        StudyLessonComments studyLessonComments = dozerMapper.map(studyLessonCommentsDto,StudyLessonComments.class);
        boolean result = super.save(studyLessonComments);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyLessonCommentsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyLessonCommentsDto studyLessonCommentsDto) {
        this.checkData(studyLessonCommentsDto);
        studyLessonCommentsDto.setUpdateTime(LocalDateTime.now());
        StudyLessonComments studyLessonComments = dozerMapper.map(studyLessonCommentsDto,StudyLessonComments.class);
        boolean result = super.updateById(studyLessonComments);
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
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        StudyLessonComments studyLessonComments = super.getById(id);
        if (studyLessonComments.getUserId().equals(loginAppUser.getId())) {
            boolean result = super.removeById(id);
            if (result){
                List<Long> ids = new ArrayList<>();
                this.getKidsIds(ids, id);
                if (CollectionUtils.isNotEmpty(ids)) {
                    LambdaQueryWrapper<StudyLessonComments> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.in(StudyLessonComments::getId,ids);
                    studyLessonCommentsMapper.delete(queryWrapper);
                }

            }
        }else {
            throw new IncloudException("该评论不是您的评论，无法删除！");
        }
        return true;
    }

    /**
     * 递归获取子集的id
     * @param ids
     * @param id
     */
    private void getKidsIds(List<Long> ids,Long id){
        LambdaQueryWrapper<StudyLessonComments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonComments::getParentId,id);
        List<StudyLessonComments> lessonComments = studyLessonCommentsMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(lessonComments)) {
            for (StudyLessonComments studyLessonComments : lessonComments){
                ids.add(studyLessonComments.getId());
                this.getKidsIds(ids,studyLessonComments.getId());
            }
        }
    }
}
