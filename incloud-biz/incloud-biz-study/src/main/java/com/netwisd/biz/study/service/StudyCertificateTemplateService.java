package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.dto.StudyCertificateTemplateDto;
import com.netwisd.biz.study.entity.StudyCertificateTemplate;
import com.netwisd.biz.study.vo.StudyCertificateTemplateVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 证书模板
 * @date 2022-04-19 19:31:09
 */
public interface StudyCertificateTemplateService extends IService<StudyCertificateTemplate> {
    /**
     * 分页查询
     *
     * @param studyCertificateDto
     * @return
     */
    Page pageList(StudyCertificateTemplateDto studyCertificateDto);

    /**
     * 根据ID获取详情
     *
     * @param id
     * @return
     */
    StudyCertificateTemplateVo get(Long id);

    /**
     * 新增
     *
     * @param studyCertificateTemplateDto
     * @return
     */
    Boolean save(StudyCertificateTemplateDto studyCertificateTemplateDto);

    /**
     * 修改
     *
     * @param studyCertificateTemplateDto
     * @return
     */
    Boolean update(StudyCertificateTemplateDto studyCertificateTemplateDto);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    Boolean delete(String ids);


    /**
     * 获取预览证书模板流
     *
     * @param id
     * @param response
     * @param request
     */
    void previewTemplate(Long id, HttpServletResponse response, HttpServletRequest request);
}
