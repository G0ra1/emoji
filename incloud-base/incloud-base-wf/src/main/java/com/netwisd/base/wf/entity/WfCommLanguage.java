package com.netwisd.base.wf.entity;

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
 * @Description $审批时常用语 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-17 14:45:42
 */
@Data
@Table(value = "incloud_base_wf_comm_language",comment = "审批时常用语")
@TableName("incloud_base_wf_comm_language")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "审批时常用语 Entity")
public class WfCommLanguage extends IModel<WfCommLanguage> {

    /**
     * use_user_id
     * 使用人id
     */
    @ApiModelProperty(value="使用人id")
    @TableField(value="use_user_id")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "使用人id")
    private String useUserId;
    /**
     * use_user_name
     * 使用人名称
     */
    @ApiModelProperty(value="使用人名称")
    @TableField(value="use_user_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "使用人名称")
    private String useUserName;
    /**
     * content
     * 常用语内容
     */
    @ApiModelProperty(value="常用语内容")
    @TableField(value="content")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "常用语内容")
    private String content;
    /**
     * is_general
     * 是否是通用 常用语
     */
    @ApiModelProperty(value="是否是通用 常用语")
    @TableField(value="is_general")
    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否是通用 常用语")
    private Integer isGeneral;
}
