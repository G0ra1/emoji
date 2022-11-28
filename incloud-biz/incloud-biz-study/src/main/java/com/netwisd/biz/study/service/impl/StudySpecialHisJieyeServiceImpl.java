package com.netwisd.biz.study.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.data.FieldVm;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.study.entity.StudySpecialHisJieye;
import com.netwisd.biz.study.mapper.StudySpecialHisJieyeMapper;
import com.netwisd.biz.study.service.StudySpecialHisJieyeService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudySpecialHisJieyeDto;
import com.netwisd.biz.study.vo.StudySpecialHisJieyeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.netwisd.common.db.util.EntityListConvert;
import cn.hutool.core.util.ObjectUtil;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题历史与结业设置（子表） 功能描述...
 * @date 2022-05-13 14:23:33
 */
@Service
@Slf4j
public class StudySpecialHisJieyeServiceImpl extends BatchServiceImpl<StudySpecialHisJieyeMapper, StudySpecialHisJieye> implements StudySpecialHisJieyeService {

}
