package com.netwisd.biz.study.service;


import com.netwisd.common.db.data.BatchService;

import java.util.List;

import com.netwisd.biz.study.entity.StudySpecialHis;

import com.netwisd.biz.study.vo.StudySpecialHisVo;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题历史表 功能描述...
 * @date 2022-05-13 14:23:33
 */
public interface StudySpecialHisService extends BatchService<StudySpecialHis> {
    /**
     * 获取专题的历史记录
     *
     * @param specialId
     * @return
     */
    List<StudySpecialHisVo> hisListForSpecial(Long specialId);

    /**
     * 通过id查询专题历史详情
     *
     * @param id
     * @return
     */
    StudySpecialHisVo detail(Long id);
}
