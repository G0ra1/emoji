package com.netwisd.base.common.user.vo.expression;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @author: sunzhenxi
 * @Date: 2020/7/15 14:05
 * @Description: 人员表达式返回对象
 */
@Data
public class UserExpressionVO implements Serializable {
    /**
     * id
     * 主键
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="主键"  )
    public Long id;
    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String userName;
    /**
     * user_name_ch
     * 用户中文姓名
     */
    @ApiModelProperty(value="用户中文姓名")
    private String userNameCh;

    /**
     * parent_org_id
     * 父级机构ID
     */
    @ApiModelProperty(value="父级机构ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentOrgId;
    /**
     * parent_org_name
     * 父级机构名称
     */
    @ApiModelProperty(value="父级机构名称")
    private String parentOrgName;
    /**
     * parent_dept_id
     * 父级部门ID
     */
    @ApiModelProperty(value="父级部门ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long parentDeptId;
    /**
     * parent_dept_name
     * 父级部门名称
     */
    @ApiModelProperty(value="父级部门名称")
    private String parentDeptName;
}