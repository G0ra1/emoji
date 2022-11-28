package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.constants.CouTypeEnum;
import com.netwisd.biz.study.entity.*;
import com.netwisd.biz.study.mapper.StudyCouMapper;
import com.netwisd.biz.study.mapper.StudyLessonCouMapper;
import com.netwisd.biz.study.mapper.StudySpecialMapper;
import com.netwisd.biz.study.mapper.StudyUserStudyRecordsMapper;
import com.netwisd.biz.study.service.StudyLessonService;
import com.netwisd.biz.study.service.StudyUserStudyRecordsService;
import com.netwisd.biz.study.util.StudyTimeUtil;
import com.netwisd.biz.study.vo.StudySpecialLessonDetailVo;
import com.netwisd.biz.study.vo.StudyUserRecordsVo;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.study.dto.StudyUserStudyRecordsDto;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 用户学习记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-20 11:02:11
 */
@Service
@Slf4j
public class StudyUserStudyRecordsServiceImpl extends ServiceImpl<StudyUserStudyRecordsMapper, StudyUserStudyRecords> implements StudyUserStudyRecordsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyUserStudyRecordsMapper studyUserStudyRecordsMapper;

    @Autowired
    private StudyLessonCouMapper studyLessonCouMapper;

    @Autowired
    private StudyCouMapper studyCouMapper;

    @Autowired
    private StudyLessonService studyLessonService;

    private static long nowStudyTime = 5l;

    /**
     * 保存课程的学习记录
     * @param userStudyRecordsDto
     * @return
     */
    @Override
    @Transactional
    public Map<String,String> saveLessonRecord(StudyUserStudyRecordsDto userStudyRecordsDto) {
        this.checkLessonRecord(userStudyRecordsDto);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //1.查询登录人是否学习过该课程
        LambdaQueryWrapper<StudyUserStudyRecords> userStudyRecordsWrapper = new LambdaQueryWrapper<>();
        userStudyRecordsWrapper.isNull(StudyUserStudyRecords::getSpecialId);
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getUserId,loginAppUser.getId());
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getLessonId,userStudyRecordsDto.getLessonId());
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getCouId,userStudyRecordsDto.getCouId());
        List<StudyUserStudyRecords> studyRecords = studyUserStudyRecordsMapper.selectList(userStudyRecordsWrapper);
        //如果有记录就是学习过 进行修改 如果没有记录就是没有学习过 进行新增
        if (studyRecords.size() > 0) {
            //学习过 进行修改
            StudyUserStudyRecords oldUserStudyRecords = studyRecords.get(0);
            oldUserStudyRecords.setLastStudyTime(LocalDateTime.now());
            oldUserStudyRecords.setCumulativeStudyTime(oldUserStudyRecords.getCumulativeStudyTime() + nowStudyTime);
            oldUserStudyRecords.setLastVideoTime(userStudyRecordsDto.getLastVideoTime());
            studyUserStudyRecordsMapper.updateById(oldUserStudyRecords);
        }else {
            //没有学习过 进行新增
            StudyUserStudyRecords userStudyRecords = dozerMapper.map(userStudyRecordsDto, StudyUserStudyRecords.class);
            userStudyRecords.setUserId(loginAppUser.getId());
            userStudyRecords.setUserNameCh(loginAppUser.getUserNameCh());
            userStudyRecords.setUserOrgFullName(loginAppUser.getOrgFullName());
            userStudyRecords.setCumulativeStudyTime(nowStudyTime);
            userStudyRecords.setLastStudyTime(LocalDateTime.now());
            Long studyBestLessTime = this.getStudyBestLessTime(userStudyRecordsDto.getLessonId(), userStudyRecordsDto.getCouId());
            userStudyRecords.setStudyBestLessTime(studyBestLessTime);
            studyUserStudyRecordsMapper.insert(userStudyRecords);
        }
        StudyUserStudyRecords newStudyRecords = studyUserStudyRecordsMapper.selectOne(userStudyRecordsWrapper);
        Long cumulativeStudyTime = newStudyRecords.getCumulativeStudyTime();
        Long studyBestLessTime = newStudyRecords.getStudyBestLessTime();
        //返回数据
        //如果是音视频类的 取 studyTime （学习时长）
        //如果是文档类的 取 isStudyFinish （是否学习 0否 1是）
        Map<String,String> returnMap = new HashMap<>();
        LambdaQueryWrapper<StudyCou> couWrapper = new LambdaQueryWrapper<>();
        couWrapper.eq(StudyCou::getId,newStudyRecords.getCouId());
        StudyCou studyCou = studyCouMapper.selectOne(couWrapper);
        if (studyCou.getCouType().equals(CouTypeEnum.DOCUMNET.code)) {
            //文档类型
            if (cumulativeStudyTime > studyBestLessTime) {
                returnMap.put("isStudyFinish","1");
            }else {
                returnMap.put("isStudyFinish","0");
            }
        }else {
            //音视频类型
            String studyTime = StudyTimeUtil.timeToStr(cumulativeStudyTime);
            returnMap.put("studyTime",studyTime);
        }
        returnMap.put("lastVideoTime",userStudyRecordsDto.getLastVideoTime().toString());
        return returnMap;
    }

    /**
     * 保存专题的学习记录
     * @param userStudyRecordsDto
     * @return
     */
    @Override
    public Map<String,String> saveSpecialRecord(StudyUserStudyRecordsDto userStudyRecordsDto) {
        this.checkSpecialRecord(userStudyRecordsDto);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //1.查询登录人是否学习过该课程
        LambdaQueryWrapper<StudyUserStudyRecords> userStudyRecordsWrapper = new LambdaQueryWrapper<>();
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getUserId,loginAppUser.getId());
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getSpecialId,userStudyRecordsDto.getSpecialId());
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getLessonId,userStudyRecordsDto.getLessonId());
        userStudyRecordsWrapper.eq(StudyUserStudyRecords::getCouId,userStudyRecordsDto.getCouId());
        List<StudyUserStudyRecords> studyRecords = studyUserStudyRecordsMapper.selectList(userStudyRecordsWrapper);
        //如果有记录就是学习过 进行修改 如果没有记录就是没有学习过 进行新增
        if (studyRecords.size() > 0) {
            //学习过 进行修改
            StudyUserStudyRecords oldUserStudyRecords = studyRecords.get(0);
            Long cumulativeStudyTime = oldUserStudyRecords.getCumulativeStudyTime();
            Long newTime =  nowStudyTime + cumulativeStudyTime;
            oldUserStudyRecords.setCumulativeStudyTime(newTime);
            oldUserStudyRecords.setLastStudyTime(LocalDateTime.now());
            oldUserStudyRecords.setLastVideoTime(userStudyRecordsDto.getLastVideoTime());
            studyUserStudyRecordsMapper.updateById(oldUserStudyRecords);
        }else {
            //没有学习过 进行新增
            StudyUserStudyRecords userStudyRecords = dozerMapper.map(userStudyRecordsDto, StudyUserStudyRecords.class);
            userStudyRecords.setUserId(loginAppUser.getId());
            userStudyRecords.setUserNameCh(loginAppUser.getUserNameCh());
            userStudyRecords.setUserOrgFullName(loginAppUser.getOrgFullName());
            userStudyRecords.setCumulativeStudyTime(nowStudyTime);
            userStudyRecords.setLastStudyTime(LocalDateTime.now());
            Long studyBestLessTime = this.getStudyBestLessTime(userStudyRecordsDto.getLessonId(), userStudyRecordsDto.getCouId());
            userStudyRecords.setStudyBestLessTime(studyBestLessTime);
            studyUserStudyRecordsMapper.insert(userStudyRecords);
        }
        //返回数据
        //finishSize 完成率
        //isStudyFinish 是否以学习完成 0否 1是
        StudyUserStudyRecords newStudyRecords = studyUserStudyRecordsMapper.selectOne(userStudyRecordsWrapper);
        BigDecimal cumulativeStudyTime = new BigDecimal(newStudyRecords.getCumulativeStudyTime());
        BigDecimal studyBestLessTime = new BigDecimal(newStudyRecords.getStudyBestLessTime());
        BigDecimal divide = cumulativeStudyTime.divide(studyBestLessTime,4, RoundingMode.HALF_DOWN);
        BigDecimal one = new BigDecimal(1);
        if (divide.compareTo(one) > -1) {
            divide = one;
        }
        DecimalFormat df = new DecimalFormat("0.00%");
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("finishSize",df.format(divide));
        if (divide.compareTo(one) == 0) {
            returnMap.put("isStudyFinish","1");
        }else {
            returnMap.put("isStudyFinish","0");
        }
        returnMap.put("lastVideoTime",userStudyRecordsDto.getLastVideoTime().toString());
        return returnMap;
    }

    /**
     * 学员端 个人中心 学习记录台账展示
     * @param userStudyRecordsDto
     * @return
     */
    @Override
    public Page<StudyUserRecordsVo> getUserRecords(StudyUserStudyRecordsDto userStudyRecordsDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        userStudyRecordsDto.setUserId(loginAppUser.getId());
        Page<StudyUserRecordsVo> userRecordsVoPage = studyUserStudyRecordsMapper.getUserRecords(userStudyRecordsDto.getPage(), userStudyRecordsDto);
        if (CollectionUtils.isNotEmpty(userRecordsVoPage.getRecords())) {
            List<StudyUserRecordsVo> records = userRecordsVoPage.getRecords();
            for (StudyUserRecordsVo userRecordsVo : records){
                userRecordsVo.setCumulativeStudyTimeSize(StudyTimeUtil.timeToStr(userRecordsVo.getCumulativeStudyTime()));
                BigDecimal bigDecimal = new BigDecimal(userRecordsVo.getCumulativeStudyTime());
                BigDecimal bigDecimal1 = new BigDecimal(userRecordsVo.getStudyBestLessTime());
                Integer studyCouRate = bigDecimal.divide(bigDecimal1, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(0).intValue();
                if (studyCouRate == 0) {//如果比率为0 则返回1
                    studyCouRate = 1;
                }
                if (studyCouRate > 100) {
                    studyCouRate = 100;
                }
                userRecordsVo.setStudyCouRate(studyCouRate);
            }
        }
        return userRecordsVoPage;
    }

    /**
     * 根据人员id获取人员的学习记录相关信息
     * type 1 返回累计学习时长（秒）
     * type 2 返回学习过的课程数量
     * type 3 返回学习过的专题数量
     * @param userId
     * @return
     */
    @Override
    public Long getUserStudyMsg(Long userId,int type) {
        if (ObjectUtils.isEmpty(userId)) {
            userId = AppUserUtil.getLoginAppUser().getId();
        }
        LambdaQueryWrapper<StudyUserStudyRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyUserStudyRecords::getUserId,userId);
        List<StudyUserStudyRecords> studyRecords = studyUserStudyRecordsMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(studyRecords)) {
            if (1 == type)
                return studyRecords.stream().filter(x -> x.getCumulativeStudyTime() != null).mapToLong(StudyUserStudyRecords::getCumulativeStudyTime).sum();
            if (2 == type)
                return (long)studyRecords.stream().filter(x -> ObjectUtils.isNotEmpty(x.getLessonId()) && ObjectUtils.isEmpty(x.getSpecialId())).map(StudyUserStudyRecords::getLessonId).collect(Collectors.toList()).size();
            if (3 == type)
                return (long)studyRecords.stream().filter(x -> ObjectUtils.isNotEmpty(x.getSpecialId())).map(StudyUserStudyRecords::getSpecialId).collect(Collectors.toList()).size();
        }
        return 0l;
    }

    /**
     * 检查课程学习记录字段
     * @param userStudyRecordsDto
     */
    private void checkLessonRecord(StudyUserStudyRecordsDto userStudyRecordsDto){
        if (ObjectUtils.isEmpty(userStudyRecordsDto.getLessonId())) {
            throw new IncloudException("请传课程主键");
        }
        if (StringUtils.isBlank(userStudyRecordsDto.getLessonName())) {
            throw new IncloudException("请传课程名称");
        }
        if (ObjectUtils.isEmpty(userStudyRecordsDto.getCouId())) {
            throw new IncloudException("请传课件主键");
        }
        if (StringUtils.isBlank(userStudyRecordsDto.getCouName())) {
            throw new IncloudException("请传课件名称");
        }
        LambdaQueryWrapper<StudyCou> couWrapper = new LambdaQueryWrapper<>();
        couWrapper.eq(StudyCou::getId,userStudyRecordsDto.getCouId());
        StudyCou studyCou = studyCouMapper.selectOne(couWrapper);
        if (ObjectUtils.isNotEmpty(studyCou)) {
            Integer couType = studyCou.getCouType();
            if (!couType.equals(CouTypeEnum.DOCUMNET.code) && ObjectUtils.isEmpty(userStudyRecordsDto.getLastVideoTime())) {
                throw new IncloudException("请传最后播放音视频时间节点");
            }
        }else {
            throw new IncloudException("通过课件id未查询到课件信息");
        }
    }

    /**
     * 检查专题学习记录字段
     * @param userStudyRecordsDto
     */
    private void checkSpecialRecord(StudyUserStudyRecordsDto userStudyRecordsDto){
        if (ObjectUtils.isEmpty(userStudyRecordsDto.getSpecialId())) {
            throw new IncloudException("请传专题主键");
        }
        if (StringUtils.isBlank(userStudyRecordsDto.getSpecialName())) {
            throw new IncloudException("请传专题名称");
        }
        this.checkLessonRecord(userStudyRecordsDto);
    }

    /**
     * 通过课程id与课件id 查询最低学习时长（秒）
     * @param lessonId
     * @param couId
     * @return
     */
    private Long getStudyBestLessTime(Long lessonId,Long couId){
        LambdaQueryWrapper<StudyLessonCou> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyLessonCou::getLessonId,lessonId);
        queryWrapper.eq(StudyLessonCou::getCouId,couId);
        List<StudyLessonCou> studyLessonCous = studyLessonCouMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(studyLessonCous)) {
            Integer studyBestLessTimeMin = studyLessonCous.get(0).getStudyBestLessTime();
            return (long) (studyBestLessTimeMin * 60);
        }
        throw new IncloudException("通过课程id与课件id未找到最低学习时长配置！");
    }
}
