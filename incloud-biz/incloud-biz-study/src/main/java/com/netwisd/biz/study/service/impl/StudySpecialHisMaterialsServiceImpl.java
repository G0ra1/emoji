package com.netwisd.biz.study.service.impl;


import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudySpecialHisMaterials;
import com.netwisd.biz.study.mapper.StudySpecialHisMaterialsMapper;
import com.netwisd.biz.study.service.StudySpecialHisMaterialsService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题历史与学习资料表 功能描述...
 * @date 2022-05-13 14:23:33
 */
@Service
@Slf4j
public class StudySpecialHisMaterialsServiceImpl extends BatchServiceImpl<StudySpecialHisMaterialsMapper, StudySpecialHisMaterials> implements StudySpecialHisMaterialsService {

}
