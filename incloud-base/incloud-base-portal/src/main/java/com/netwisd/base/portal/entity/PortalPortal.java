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
 * @Description 门户维护 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-11 09:50:22
 */
@Data
@Table(value = "incloud_base_portal_portal",comment = "门户维护")
@TableName("incloud_base_portal_portal")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "门户维护 Entity")
public class PortalPortal extends IModel<PortalPortal> {

    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    @TableField(value="portal_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "门户名称")
    private String portalName;
    /**
     * is_default
     * 默认首页 0否1是;
     */
    @ApiModelProperty(value="默认首页 0否1是;")
    @TableField(value="is_default")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "默认首页 0否1是;")
    private Integer isDefault;
    /**
     * is_login
     * 是否登录 0否1是;
     */
    @ApiModelProperty(value="是否登录 0否1是;")
    @TableField(value="is_login")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否登录 0否1是;")
    private Integer isLogin;
    /**
     * is_enable
     * 是否启用 0否1是;
     */
    @ApiModelProperty(value="是否启用 0否1是;")
    @TableField(value="is_enable")
    @Column(type = DataType.INT, length = 1,  isNull = false, comment = "是否启用 0否1是;")
    private Integer isEnable;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;

    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    @TableField(value = "hits")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "点击量")
    private Long hits;
}
