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
 * @Description $事件运行参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:10:12
 */
@Data
@Table(value = "incloud_base_wf_event_param",comment = "事件运行参数维护")
@TableName("incloud_base_wf_event_param")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "事件运行参数维护 Entity")
public class WfEventParam extends IModel<WfEventParam> {
    /**
     * event_id
     * 事件ID
     */
    @ApiModelProperty(value="事件ID")
    @TableField(value="event_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "事件ID")
    private Long eventId;
    /**
     * param_type
     * 参数类型
     */
    @ApiModelProperty(value="参数类型")
    @TableField(value="param_type")
    @Column(type = DataType.VARCHAR, length = 50,  isNull = false, comment = "参数类型")
    private String paramType;

    /**
     * var_name
     * 变量名称
     */
    @ApiModelProperty(value="变量名称")
    @TableField(value="param_name")
    @Column(type = DataType.VARCHAR, length = 100,  isNull = false, comment = "变量名称")
    private String paramName;
    /**
     * var_defalut_value
     * 默认值
     */
    @ApiModelProperty(value="默认值")
    @TableField(value="param_defalut_value")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "默认值")
    private String paramDefalutValue;

    /**
     * param_id
     * 参数id
     */
    @ApiModelProperty(value="参数id")
    @TableField(value="param_id")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = false, comment = "参数id")
    private String paramId;

//    /**
//     * is_pull_down
//     * 是否列表选值
//     */
//    @ApiModelProperty(value="是否列表选值")
//    @TableField(value="is_pull_down")
//    @Column(type = DataType.INT, length = 1,  isNull = true, comment = "是否列表选值")
//    private Integer isPullDown ;
}
