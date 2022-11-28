package com.netwisd.base.common.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RegionDetailsVO implements Serializable {

    private String regionId;

    private String regionName;

    private Integer level;

    private String parentId;

    private String postCode;

    private List<RegionDetailsVO> childList;
}
