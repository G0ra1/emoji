package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalTemplate;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalTemplateDto;
import com.netwisd.base.portal.vo.PortalTemplateVo;

import java.util.List;

/**
 * @Description 模板管理 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-11 00:09:45
 */
public interface PortalTemplateService extends IService<PortalTemplate> {
    Page list(PortalTemplateDto portalTemplateDto);
    List<PortalTemplateVo> lists(PortalTemplateDto portalTemplateDto);
    List<PortalTemplateVo> templateVersions(PortalTemplateDto portalTemplateDto);
    Boolean templateCodeIsOne(PortalTemplateDto portalTemplateDto);
    PortalTemplateVo get(Long id);
    Boolean save(PortalTemplateDto portalTemplateDto);
    Boolean update(PortalTemplateDto portalTemplateDto);
    Boolean updateStartEnable(Long portalId,Integer terminal,String templateCode);
    Boolean updateVersion(Long portalId,Integer terminal,String templateCode,Long templateId);
    Boolean deleteVersion(String ids);
    Boolean deleteTemplate(PortalTemplateDto portalTemplateDto);
}
