package com.netwisd.base.wf.starter.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.data.DataType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/21 10:03 上午
 */
@Data
public class EngineVo implements Serializable {
    private String camundaProcinsId;
    private String camundaTaskId;
    private String uesrId;
    private String camundaProcdefId;
    private String bizKey;
    private String bizData;
    @ApiModelProperty(value="流程定义key")
    private String camundaProcdefKey;
    @ApiModelProperty(value="流程实例标题")
    private String processName;

    @ApiModelProperty(value="流程定义名称")
    private String procdefName;
    @ApiModelProperty(value="流程版本")
    private Integer procdefVersion;
    @ApiModelProperty(value="流程分类ID")
    private Long procdefTypeId;
    @ApiModelProperty(value="流程分类名称")
    private String procdefTypeName;
}
