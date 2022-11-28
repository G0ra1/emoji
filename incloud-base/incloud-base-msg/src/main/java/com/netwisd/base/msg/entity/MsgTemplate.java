package com.netwisd.base.msg.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.msg.dto.MsgTemplateDto;
import com.netwisd.base.msg.vo.MsgTemplateVo;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Table(value = "incloud_base_msg_template", comment = "消息模板")
@ApiModel(value = "消息模板 Entity")
@TableName("incloud_base_msg_template")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MsgTemplate extends IModel<MsgTemplate> {

    @ApiModelProperty(value = "模板Code")
    @TableField(value = "tmp_code")
    @Column(length = 100, isNull = false, comment = "模板Code")
    private String tmpCode;

    @ApiModelProperty(value = "模板名称")
    @TableField(value = "tmp_name")
    @Column(length = 100, isNull = false, comment = "模板名称")
    private String tmpName;

    @ApiModelProperty(value = "模板标题")
    @TableField(value = "tmp_title")
    @Column(length = 100, isNull = false, comment = "模板标题")
    private String tmpTitle;

    @ApiModelProperty(value = "模板内容")
    @TableField(value = "tmp_content")
    @Column(length = 100, isNull = false, comment = "模板内容")
    private String tmpContent;

    public void toMsgTemplate(MsgTemplateDto msgTemplateDto) {
        this.setTmpCode(msgTemplateDto.getTmpCode());
        this.setTmpName(msgTemplateDto.getTmpName());
        this.setTmpContent(msgTemplateDto.getTmpContent());
        this.setTmpTitle(msgTemplateDto.getTmpTitle());
    }

    public MsgTemplateVo toMsgTemplateVo() {
        MsgTemplateVo msgTemplateVo = new MsgTemplateVo();
        msgTemplateVo.setId(this.id);
        msgTemplateVo.setTmpCode(this.tmpCode);
        msgTemplateVo.setTmpName(this.tmpName);
        msgTemplateVo.setTmpContent(this.tmpContent);
        msgTemplateVo.setTmpTitle(this.tmpTitle);
        return msgTemplateVo;
    }
}
