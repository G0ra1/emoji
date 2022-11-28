package com.netwisd.biz.study.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
/**
 * @Description 专题历史必考人员 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-06-21 14:50:51
 */
@Data
@ApiModel(value = "专题历史必考人员 Vo")
public class StudySpecialHisMustExamUserVo extends IVo{

    /**
     * create_user_id
     * 创建人ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="创建人ID")
    private Long createUserId;
    /**
     * create_user_name
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    private String createUserName;
    /**
     * create_user_parent_org_id
     * 父级机构ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="父级机构ID")
    private Long createUserParentOrgId;
    /**
     * create_user_parent_org_name
     * 父级机构名称
     */
    @ApiModelProperty(value="父级机构名称")
    private String createUserParentOrgName;
    /**
     * create_user_parent_dept_id
     * 父级部门ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="父级部门ID")
    private Long createUserParentDeptId;
    /**
     * create_user_parent_dept_name
     * 父级部门名称
     */
    @ApiModelProperty(value="父级部门名称")
    private String createUserParentDeptName;
    /**
     * create_user_org_full_id
     * 父级组织全路径ID
     */
    @ApiModelProperty(value="父级组织全路径ID")
    private String createUserOrgFullId;
    /**
     * special_id
     * 专题id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="专题id")
    private Long specialId;
    /**
     * special_paper_id
     * 专题试卷id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="专题试卷id")
    private Long specialPaperId;
    /**
     * special_must_exam_user_id
     * 专题必考人员id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="专题必考人员id")
    private Long specialMustExamUserId;


}
