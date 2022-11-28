package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.entity.StudyLessonScore;
import com.netwisd.biz.study.mapper.StudyLessonScoreMapper;
import com.netwisd.biz.study.service.StudyLessonScoreService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.study.dto.StudyLessonScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 课程评分表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 17:27:45
 */
@Service
@Slf4j
public class StudyLessonScoreServiceImpl extends ServiceImpl<StudyLessonScoreMapper, StudyLessonScore> implements StudyLessonScoreService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyLessonScoreMapper studyLessonScoreMapper;

    /**
    * 保存实体
    * @param studyLessonScoreDto
    * @return
    */
    @Transactional
    @Override
    public BigDecimal save(StudyLessonScoreDto studyLessonScoreDto) {
        this.checkData(studyLessonScoreDto);
        StudyLessonScore studyLessonScore = dozerMapper.map(studyLessonScoreDto,StudyLessonScore.class);
        boolean result = super.save(studyLessonScore);
        if(result){
            log.debug("保存成功");
        }
        //查出来当前课程所有的评分 然后所有评分除以评分数量 算出平均分返回
        return this.getLessonScore(studyLessonScoreDto.getLessonId());
    }

    /**
     * 获取课程平均分
     * @param lessonId
     * @return
     */
    @Override
    public BigDecimal getLessonScore(Long lessonId) {
        LambdaQueryWrapper<StudyLessonScore> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonScore::getLessonId,lessonId);
        List<StudyLessonScore> studyLessonScores = studyLessonScoreMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(studyLessonScores)) {
            return new BigDecimal(5);
        }
        BigDecimal allScore = new BigDecimal(0);
        for (StudyLessonScore lessonScore : studyLessonScores){
            allScore = allScore.add(lessonScore.getScore());
        }
        BigDecimal num = new BigDecimal(studyLessonScores.size());
        return allScore.divide(num, 1, RoundingMode.HALF_DOWN);
    }

    /**
     * 判断当前登录人是否评过分 0否1是
     * @param lessonId
     * @return
     */
    @Override
    public Integer getIsScore(Long lessonId) {
        int isScore = 0;
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        LambdaQueryWrapper<StudyLessonScore> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonScore::getLessonId,lessonId);
        queryWrapper.eq(StudyLessonScore::getCreateUserId,loginAppUser.getId());
        Integer scoreNum = studyLessonScoreMapper.selectCount(queryWrapper);
        if (scoreNum > 0) {
            isScore = 1;
        }
        return isScore;
    }

    /**
     * 检查传参
     * @param studyLessonScoreDto
     */
    private void checkData(StudyLessonScoreDto studyLessonScoreDto){
        if (ObjectUtils.isEmpty(studyLessonScoreDto.getLessonId())) {
            throw new IncloudException("请传课程主键");
        }
        if (ObjectUtils.isEmpty(studyLessonScoreDto.getScore())) {
            throw new IncloudException("请传课程分数");
        }
        BigDecimal maxScore = new BigDecimal(5);
        BigDecimal minScore = new BigDecimal(0.5);
        if (studyLessonScoreDto.getScore().compareTo(maxScore) > 0) {
            throw new IncloudException("评分超过最大分值！");
        }
        if (minScore.compareTo(studyLessonScoreDto.getScore()) > 0) {
            throw new IncloudException("评分低于最小分值！");
        }
    }
}
