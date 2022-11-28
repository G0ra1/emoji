package com.netwisd.biz.mdm.dto;

import com.netwisd.biz.mdm.entity.Projectjc;
import com.netwisd.biz.mdm.entity.Supplier;
import com.netwisd.biz.mdm.entity.SupplierContacts;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @Description 集采项目数据传送 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Data
@ApiModel(value = "集采项目数据传送 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectTransmit {
    private String projectid;//项目ID
    private String projectcode;//项目编码
    private String projectname;//项目名称
    private String orgnizationid;//所属组织机构ID
    private String orgcode;//所属组织机构编码
    private String orgname;//所属组织机构名称
    private String suborgids;//二级单位ID
    private String suborgnames;//二级单位名称
    private String islive;//是否在建（0：非在建，1：在建）
    private String createdate;//创建时间（时间戳）
    private String updatedate;//更新时间（时间戳）


    public Projectjc toProjectjc(){
        Projectjc projectjc=new Projectjc();
        projectjc.setProjectId(this.getProjectid());
        projectjc.setProjectCode(this.getProjectcode());
        projectjc.setProjectName(this.getProjectname());
        projectjc.setOrgId(this.getOrgnizationid());
        projectjc.setOrgCode(this.getOrgcode());
        projectjc.setOrgName(this.getOrgname());
        projectjc.setSuborgIds(this.getSuborgids());
        projectjc.setSuborgNames(this.getSuborgnames());
        projectjc.setIsLive(this.getIslive());
        return projectjc;
    }


}
