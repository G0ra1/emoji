package com.netwisd.base.wf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $流程分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-01 14:45:51
 */
@Data
@Table(value = "incloud_base_wf_procdef_type",comment = "流程分类")
@TableName("incloud_base_wf_procdef_type")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程分类 Entity")
public class WfProcdefType extends IModel<WfProcdefType> {
    /**
     * procdef_type_name
     * 流程分类名称
     */
    @ApiModelProperty(value="流程分类名称")
    @TableField(value="procdef_type_name")
    @Column(type = DataType.VARCHAR, length = 64,  isNull = true, comment = "流程分类名称")
    private String procdefTypeName;
    /**
     * procdef_type_code
     * 流程分类code
     */
//    @ApiModelProperty(value="流程分类code")
//    @TableField(value="procdef_type_code")
//    @Column(type = DataType.VARCHAR, length = 12,  isNull = true, comment = "流程分类code")
//    private String procdefTypeCode;
    /**
     * is_enable
     * 是否启用
     */
    @ApiModelProperty(value="是否启用")
    @TableField(value="is_enable")
    @Column(type = DataType.INT, length = 2,  isNull = true, comment = "是否启用")
    private Integer isEnable;

    /**
     * parentId
     * 上级节点id
     */
    @ApiModelProperty(value="上级节点id")
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @TableField(value="parent_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "上级节点id")
    private Long parentId;
}
