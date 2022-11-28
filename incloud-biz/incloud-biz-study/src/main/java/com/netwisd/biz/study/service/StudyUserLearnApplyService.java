package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.dto.StudyUserLearnApplyDto;
import com.netwisd.biz.study.entity.StudyUserLearnApply;
import com.netwisd.biz.study.vo.StudyUserLearnApplyVo;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 人员学习申请
 * @date 2022-04-25 09:39:13
 */
public interface StudyUserLearnApplyService extends IService<StudyUserLearnApply> {
    /**
     * 分页查询人员学习申请
     *
     * @param infoDto
     * @return
     */
    Page pageList(StudyUserLearnApplyDto infoDto);

    /**
     * 根据ID获取详情
     *
     * @param id
     * @return
     */
    StudyUserLearnApplyVo detail(Long id);

    /**
     * 处理申请
     *
     * @param infoDto
     * @return
     */
    void dealApply(StudyUserLearnApplyDto infoDto);

    /**
     * 专题学习申请
     *
     * @param infoDto
     * @return
     */
    Boolean specialLearnApply(StudyUserLearnApplyDto infoDto);

    /**
     * 课件学习申请
     *
     * @param infoDto
     * @return
     */
    Boolean couLearnApply(StudyUserLearnApplyDto infoDto);
}

