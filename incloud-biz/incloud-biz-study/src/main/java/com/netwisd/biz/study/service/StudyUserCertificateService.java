package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.dto.StudyUserCertificateDto;
import com.netwisd.biz.study.entity.StudyUserCertificate;
import com.netwisd.biz.study.vo.StudyUserCertificateVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 人员证书表 功能描述...
 * @date 2022-04-25 09:39:13
 */
public interface StudyUserCertificateService extends IService<StudyUserCertificate> {
    /**
     * 分页查询人员证书信息
     *
     * @param infoDto
     * @return
     */
    Page pageList(StudyUserCertificateDto infoDto);

    /**
     * 根据ID获取详情
     *
     * @param id
     * @return
     */
    StudyUserCertificateVo detail(Long id);

    /**
     * 生成用户证书
     *
     * @param userId
     * @param specialId
     * @param examScore
     */
    void createUserCertificate(Long userId, Long specialId, BigDecimal examScore);

    /**
     * 获取证书流
     *
     * @param id
     * @param response
     * @param request
     */
    void stream(Long id, HttpServletResponse response, HttpServletRequest request);

    /**
     * 获取人员证书数量
     * @param userId
     * @return
     */
    Integer getUserCertificateNum(Long userId);
}

