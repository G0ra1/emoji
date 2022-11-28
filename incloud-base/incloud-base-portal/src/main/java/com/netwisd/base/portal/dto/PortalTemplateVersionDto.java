package com.netwisd.base.portal.dto;

import lombok.Data;

@Data
public class PortalTemplateVersionDto {
    private Long portalId;
    private Integer terminal;
    private String templateCode;
    private Long templateId;
}
