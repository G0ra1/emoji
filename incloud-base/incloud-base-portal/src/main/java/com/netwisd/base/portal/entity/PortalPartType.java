package com.netwisd.base.portal.entity;

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
 * @Description $栏目类型字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-12 17:20:18
 */
@Data
@Table(value = "incloud_base_portal_part_type",comment = "栏目类型字典")
@TableName("incloud_base_portal_part_type")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "栏目类型字典 Entity")
public class PortalPartType extends IModel<PortalPartType> {

    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    @TableField(value="part_type_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "栏目类型名称")
    private String partTypeName;
    /**
     * part_type_code
     * 栏目类型CODE
     */
    @ApiModelProperty(value="栏目类型CODE")
    @TableField(value="part_type_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "栏目类型CODE")
    private String partTypeCode;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;
}
