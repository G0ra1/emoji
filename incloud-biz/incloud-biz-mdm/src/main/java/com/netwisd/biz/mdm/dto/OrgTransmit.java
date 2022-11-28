package com.netwisd.biz.mdm.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 集采组织机构数据传送 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
@Data
@ApiModel(value = "集采组织机构数据传送 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrgTransmit {

    private String orgnizationid;
    private String parentid;
    private String orgname;
    private String orgcode;
    private String orgtype;
    private String isuse;
    private String orgdescription;
    private String companyid;
    private String shortname;

}
