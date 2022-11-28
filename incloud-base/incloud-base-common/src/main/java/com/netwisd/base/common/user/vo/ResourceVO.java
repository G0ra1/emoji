package com.netwisd.base.common.user.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
public class ResourceVO implements Serializable {

    private String id;

    private String parentId;

    private String permission;

    private String resCode;

    private String resIcon;

    private String resName;

    private String resType;

    private String resUrl;

    private String blackSelect;

    private String whiteSelect;

    public static ResourceVO budilResource(ResourceDetailsVO resource) {
        ResourceVO resourceVO = new ResourceVO();
        BeanUtils.copyProperties(resource, resourceVO);
        return resourceVO;
    }
}
