package com.netwisd.base.mdm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Description $职务分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:00:30
 */
@Data
@Table(value = "incloud_base_mdm_duty_type",comment = "职务分类")
@TableName("incloud_base_mdm_duty_type")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "职务分类 Entity")
public class MdmDutyType extends IModel<MdmDutyType> {

    /**
     * duty_type_name
     * 职务分类名称
     */
    @ApiModelProperty(value="职务分类名称")
    @TableField(value="duty_type_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "职务分类名称")
    private String dutyTypeName;
    /**
     * duty_type_code
     * 职务分类Code
     */
    @ApiModelProperty(value="职务分类Code")
    @TableField(value="duty_type_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "职务分类Code")
    private String dutyTypeCode;
    /**
     * sort
     * 排序
     */
    @ApiModelProperty(value="排序")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "排序")
    private Integer sort;
}
