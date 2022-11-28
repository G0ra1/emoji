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
 * @Description $  主题管理 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-18 23:20:45
 */
@Data
@Table(value = "incloud_base_portal_theme",comment = "  主题管理")
@TableName("incloud_base_portal_theme")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "  主题管理 Entity")
public class PortalTheme extends IModel<PortalTheme> {

    /**
     * topic_name
     * 主题名称
     */
    @ApiModelProperty(value="主题名称")
    @TableField(value="theme_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "主题名称")
    private String themeName;
    /**
     * is_use
     * 是否应用(0:否；1:是)
     */
    @ApiModelProperty(value="是否应用(0:否；1:是)")
    @TableField(value="is_use")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否应用(0:否；1:是)")
    private Integer isUse;
    /**
     * is_inside
     * 是否内置(0:否；1:是)
     */
    @ApiModelProperty(value="是否内置(0:否；1:是)")
    @TableField(value="is_inside")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否内置(0:否；1:是)")
    private Integer isInside;
    /**
     * topic_data
     * 主题样式数据
     */
    @ApiModelProperty(value="主题样式数据")
    @TableField(value="theme_data")
    @Column(type = DataType.TEXT, length = 0,  isNull = false, comment = "主题样式数据")
    private String themeData;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;
}
