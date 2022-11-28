package com.netwisd.biz.study.service.impl;


import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudySpecialHisRange;
import com.netwisd.biz.study.mapper.StudySpecialHisRangeMapper;
import com.netwisd.biz.study.service.StudySpecialHisRangeService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题历史与对象表 功能描述...
 * @date 2022-05-13 14:23:33
 */
@Service
@Slf4j
public class StudySpecialHisRangeServiceImpl extends BatchServiceImpl<StudySpecialHisRangeMapper, StudySpecialHisRange> implements StudySpecialHisRangeService {

}
