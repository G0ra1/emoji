package com.netwisd.biz.study.service;

import com.netwisd.biz.study.dto.StudySpecialAdjDto;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

import com.netwisd.biz.study.entity.StudySpecialAdj;
import com.netwisd.biz.study.vo.StudySpecialAdjVo;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题调整申请表 功能描述...
 * @date 2022-05-13 11:27:37
 */
public interface StudySpecialAdjService extends BatchService<StudySpecialAdj> {
    /**
     * 获取专题的调整记录
     *
     * @param specialId
     * @return
     */
    List<StudySpecialAdjVo> adjListForSpecial(Long specialId);

    /**
     * 通过id查询详情
     *
     * @param id
     * @return
     */
    StudySpecialAdjVo detail(Long id);

    /**
     * 流程新增
     *
     * @param studySpecialAdjDto
     * @return
     */
    StudySpecialAdjVo procSave(StudySpecialAdjDto studySpecialAdjDto);

    /**
     * 流程修改
     *
     * @param studySpecialAdjDto
     * @return
     */
    StudySpecialAdjVo procUpdate(StudySpecialAdjDto studySpecialAdjDto);

    /**
     * 流程回显获取详情
     *
     * @param procInstId
     * @return
     */
    StudySpecialAdjVo procDetail(String procInstId);

    /**
     * 流程提交触发操作
     *
     * @param processInstanceId
     * @return
     */
    Boolean procAfterSubmit(String processInstanceId);

    /**
     * 审批完成触发操作
     *
     * @param processInstanceId
     * @return
     */
    Boolean procAuditSuccess(String processInstanceId);

    /**
     * 流程删除触发操作
     *
     * @param specialAdjId
     * @return
     */
    Boolean delete(Long specialAdjId);
    /**
     * 流程删除触发操作
     *
     * @param processInstanceId
     * @return
     */
    Boolean procDelete(String processInstanceId);
}
