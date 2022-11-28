package com.netwisd.biz.study.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudySpecialDto;
import com.netwisd.biz.study.entity.StudySpecial;
import com.netwisd.biz.study.vo.StudyMySpecialVo;
import com.netwisd.biz.study.vo.StudySpecialAdjVo;
import com.netwisd.biz.study.vo.StudySpecialVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题定义表 功能描述...
 * @date 2022-05-13 10:59:05
 */
public interface StudySpecialService extends BatchService<StudySpecial> {
    /**
     * 专题台账
     *
     * @param studySpecialDto
     * @return
     */
    Page<StudySpecialVo> list(StudySpecialDto studySpecialDto);

    /**
     * 专题详情（非流程）
     *
     * @param id
     * @return
     */
    StudySpecialVo detail(Long id);

    /**
     * 启用停用
     *
     * @param id
     * @return
     */
    Boolean isEnable(Long id);

    /**
     * 是否首页展示
     *
     * @param id
     * @return
     */
    Boolean showIndex(Long id);

    /**
     * 流程新增
     *
     * @param studySpecialDto
     * @return
     */
    StudySpecialVo procSave(StudySpecialDto studySpecialDto);

    /**
     * 流程修改方法
     *
     * @param studySpecialDto
     * @return
     */
    StudySpecialVo procUpdate(StudySpecialDto studySpecialDto);

    /**
     * 流程回显获取详情
     *
     * @param procInstId
     * @return
     */
    StudySpecialVo procDetail(String procInstId);

    /**
     * 通过id组装调整信息
     *
     * @param id
     * @return
     */
    StudySpecialAdjVo detailForAdj(Long id);

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
     * @param processInstanceId
     * @return
     */
    Boolean procDelete(String processInstanceId);

    /**
     * 专题删除
     *
     * @param specialId
     * @return
     */
    Boolean delete(Long specialId);

    /**
     * 设置专题是否首页展示或启用
     *
     * @param studySpecialDtos
     * @return
     */
    Boolean updateIsIndexOrEnable(List<StudySpecialDto> studySpecialDtos);

    /**
     * 学员端专题分页查询
     *
     * @param specialDto
     * @return
     */
    Page xueyuanPageList(StudySpecialDto specialDto);

    /**
     * 学员端专题详情
     *
     * @param specialId
     * @return
     */
    StudySpecialVo xueyuanDetail(Long specialId);

    /**
     * 通过专题ID 删除专题子表信息（专题对象、专题课程、专题课程课件、专题资料、专题结业设置）调整审核完成使用
     *
     * @param specialId
     */
    void deleteSpecialChildren(Long specialId);

    /**
     * 判断学员是否可以开始考试
     *
     * @param id
     * @return
     */
    Boolean isCanExam(Long id);

    /**
     * 我的专题展示列表
     * @param specialDto
     * @return
     */
    StudyMySpecialVo findMySpecial(StudySpecialDto specialDto);
}
