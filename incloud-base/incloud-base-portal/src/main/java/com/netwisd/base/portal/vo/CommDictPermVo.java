package com.netwisd.base.portal.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 字典授权  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-01-16 11:38:29
 */
@Data
@ApiModel(value = "字典授权  Vo")
public class CommDictPermVo extends IVo{

    /**
     * dict_id
     * 字典ID
     */
    @ApiModelProperty(value="字典ID")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    private Long dictId;
    /**
     * dict_type
     * 字典类型
     */
    @ApiModelProperty(value="字典类型")
    private String dictType;
    /**
     * dictTypeName
     * 字典类型名称
     */
    @ApiModelProperty(value="字典类型名称")
    private String dictTypeName;
    /**
     * organ_id
     * 授权的部门所在机构id
     */
    @ApiModelProperty(value="授权的部门所在机构id")
    private String organId;
    /**
     * organ_name
     * 授权的部门所在机构Name
     */
    @ApiModelProperty(value="授权的部门所在机构Name")
    private String organName;
    /**
     * dept_id
     * 授权的部门id
     */
    @ApiModelProperty(value="授权的部门id")
    private String deptId;
    /**
     * dept_name
     * 授权的部门Name
     */
    @ApiModelProperty(value="授权的部门Name")
    private String deptName;
}
