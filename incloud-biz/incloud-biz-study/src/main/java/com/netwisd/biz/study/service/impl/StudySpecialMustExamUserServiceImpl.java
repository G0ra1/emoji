package com.netwisd.biz.study.service.impl;


import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudySpecialMustExamUser;
import com.netwisd.biz.study.mapper.StudySpecialMustExamUserMapper;
import com.netwisd.biz.study.service.StudySpecialMustExamUserService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题必考人员 功能描述...
 * @date 2022-06-21 14:29:47
 */
@Service
@Slf4j
public class StudySpecialMustExamUserServiceImpl extends BatchServiceImpl<StudySpecialMustExamUserMapper, StudySpecialMustExamUser> implements StudySpecialMustExamUserService {

}
