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
 * @Description $集成友报账-待办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:57:18
 */
@Data
@Table(value = "incloud_base_portal_ybz_todo_tasks",comment = "集成友报账-待办")
@TableName("incloud_base_portal_ybz_todo_tasks")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "集成友报账-待办 Entity")
public class PortalYbzTodoTasks extends IModel<PortalYbzTodoTasks> {

    /**
    * id_card
    * 身份证号
    */
    @ApiModelProperty(value="身份证号")
    @TableField(value="id_card")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = true, comment = "身份证号")
    private String idCard;

    /**
    * phone_num
    * 手机号
    */
    @ApiModelProperty(value="手机号")
    @TableField(value="phone_num")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = true, comment = "手机号")
    private String phoneNum;

    /**
    * user_name_ch
    * 用户名称
    */
    @ApiModelProperty(value="用户名称")
    @TableField(value="user_name_ch")
    @Column(type = DataType.VARCHAR, length = 20,  isNull = true, comment = "用户名称")
    private String userNameCh;

    /**
    * title
    * 标题
    */
    @ApiModelProperty(value="标题")
    @TableField(value="title")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "标题")
    private String title;

    /**
    * content
    * 内容
    */
    @ApiModelProperty(value="内容")
    @TableField(value="content")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "内容")
    private String content;

    /**
    * content_url
    * 内容路径
    */
    @ApiModelProperty(value="内容路径")
    @TableField(value="content_url")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "内容路径")
    private String contentUrl;

    /**
    * msg_id
    * 消息id
    */
    @ApiModelProperty(value="消息id")
    @TableField(value="msg_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "消息id")
    private Long msgId;

    /**
     * ybz_id
     * 友报账唯一标识
     */
    @ApiModelProperty(value="友报账唯一标识")
    @TableField(value="ybz_id")
    @Column(type = DataType.VARCHAR, length = 30,  isNull = true, comment = "友报账唯一标识")
    private String ybzId;

}
