package com.netwisd.biz.asset.entity;

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
 * @Description $资产派发任务表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:55:47
 */
@Data
@Table(value = "incloud_biz_asset_material_distribute_task",comment = "资产派发任务表")
@TableName("incloud_biz_asset_material_distribute_task")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "资产派发任务表 Entity")
public class MaterialDistributeTask extends IModel<MaterialDistributeTask> {

    /**
    * type
    * 任务类型
    */
    @ApiModelProperty(value="任务类型")
    @TableField(value="type")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "任务类型")
    private Integer type;

    /**
    * source_id
    * 关联id
    */
    @ApiModelProperty(value="关联id")
    @TableField(value="source_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "关联id")
    private Long sourceId;

    /**
    * code
    * 申请单号
    */
    @ApiModelProperty(value="申请单号")
    @TableField(value="code")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "申请单号")
    private Long code;

    /**
    * apply_time
    * 申请日期
    */
    @ApiModelProperty(value="申请日期")
    @TableField(value="apply_time")
    @Column(type = DataType.DATETIME, length = 0,  isNull = true, comment = "申请日期")
    private LocalDateTime applyTime;

    /**
    * manage_user_id
    * 处理人id
    */
    @ApiModelProperty(value="处理人id")
    @TableField(value="manage_user_id")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "处理人id")
    private Long manageUserId;

    /**
    * manage_user_name
    * 处理人名称
    */
    @ApiModelProperty(value="处理人名称")
    @TableField(value="manage_user_name")
    @Column(type = DataType.BIGINT, length = 19,  isNull = true, comment = "处理人名称")
    private Long manageUserName;

    /**
    * reason
    * 事由
    */
    @ApiModelProperty(value="事由")
    @TableField(value="reason")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "事由")
    private String reason;

    /**
    * form_url
    * 表单url
    */
    @ApiModelProperty(value="表单url")
    @TableField(value="form_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "表单url")
    private String formUrl;

    /**
    * status
    * 状态
    */
    @ApiModelProperty(value="状态")
    @TableField(value="status")
    @Column(type = DataType.INT, length = 10,  isNull = true, comment = "状态")
    private Integer status;

}
