package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.dto.StudySpecialAdjDto;
import com.netwisd.biz.study.entity.StudySpecialAdj;

import com.netwisd.biz.study.vo.StudySpecialAdjVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 专题调整申请表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 11:27:37
 */
@Mapper
public interface StudySpecialAdjMapper extends BaseMapper<StudySpecialAdj> {

}
