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
 * @Description $子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Data
@Table(value = "incloud_base_mdm_sys",comment = "子系统")
@TableName("incloud_base_mdm_sys")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "子系统 Entity")
public class MdmSys extends IModel<MdmSys> {

    /**
     * sys_code
     * 系统code
     */
    @ApiModelProperty(value="系统code")
    @TableField(value="sys_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "系统code")
    private String sysCode;
    /**
     * sys_name
     * 系统名称
     */
    @ApiModelProperty(value="系统名称")
    @TableField(value="sys_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "系统名称")
    private String sysName;
    /**
     * sys_icon
     * 图标
     */
    @ApiModelProperty(value="图标")
    @TableField(value="sys_icon")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "图标")
    private String sysIcon;
    /**
     * sort
     * 排序字段
     */
    @ApiModelProperty(value="排序字段")
    @TableField(value="sort")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "排序字段")
    private Integer sort;
    /**
     * status
     * 状态标识
     */
    @ApiModelProperty(value="状态标识")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 2,  isNull = false, comment = "状态标识")
    private Integer status;
}
