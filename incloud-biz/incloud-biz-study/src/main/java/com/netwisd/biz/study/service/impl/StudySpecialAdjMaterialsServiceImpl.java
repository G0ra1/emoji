package com.netwisd.biz.study.service.impl;


import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudySpecialAdjMaterials;
import com.netwisd.biz.study.mapper.StudySpecialAdjMaterialsMapper;
import com.netwisd.biz.study.service.StudySpecialAdjMaterialsService;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题调整与学习资料表 功能描述...
 * @date 2022-05-13 11:27:37
 */
@Service
@Slf4j
public class StudySpecialAdjMaterialsServiceImpl extends BatchServiceImpl<StudySpecialAdjMaterialsMapper, StudySpecialAdjMaterials> implements StudySpecialAdjMaterialsService {

}
