package com.netwisd.base.wf.starter.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IValidation;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 流程运行的dto
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/20 1:19 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WfEngineDto implements IValidation {

    @ApiModelProperty(value="任务ID")
    @Valid(nullMsg = "任务ID不能为空！")
    private String camundaTaskId;

    /*@ApiModelProperty(value="变量")
    private Map<String,Object> variables;*/

    @ApiModelProperty(value="业务表单数据")
    @Valid(nullMsg = "业务表单数据不能为空！")
    private List<BizData> bizDataList;

    /**
     * 办理的dto,包括：提交、驳回、撤回、加签等
     */
    @Data
    public static class HandleDto extends WfEngineDto{
        @ApiModelProperty(value="目标节点ID",notes = "可用于驳回或提交时的目标节点")
        @Valid(nullMsg = "提交到目录节点ID不能为空！")
        private String targetActivityId;

        @ApiModelProperty(value="目标节点名称",notes = "可用于加签的节点名称")
        @Valid(nullMsg = "提交到目录节点名称不能为空！")
        private String targetActivityName;

        @ApiModelProperty("提交方式——0，默认；1.提交到原驳回节点; 2.加签，默认不写值为0")
        @Valid(nullMsg = "提交类型不能为空！")
        private Integer submitType = 0;

        @ApiModelProperty(value="候选人")
        private List<String> wfAssignee;

        @ApiModelProperty(value="同意标识")
        @Valid(nullMsg = "是否同意不能为空！")
        private Integer wfLocalIsAgree;

        @ApiModelProperty(value="意见")
        @Valid(nullMsg = "同意意见不能为空！")
        private String wfLocalDescription;

        @ApiModelProperty(value="传阅人")
        private List<String> wfDuplicateList;

        @ApiModelProperty(value="知会人")
        private List<String> wfNotifyList;

        @ApiModelProperty(value="申请原因")
        private String reason;

        @ApiModelProperty(value="优先级")
        private String bizPriority;

        /*@ApiModelProperty(value="业务数据是否改变标识，用户标识工作流是否需要调用业务接口")
        private Boolean isChange;*/
    }

    @Data
    public static class StartDto implements IValidation{
        @ApiModelProperty(value="流程实例ID")
        private String camundaTaskId;

        @ApiModelProperty(value="流程定义key")
        @Valid(nullMsg = "camunda流程定义key不能为空！")
        private String camundaProcdefKey;

        @ApiModelProperty(value="事由")
        @Valid(nullMsg = "事由不能为空！")
        private String reason;

        @ApiModelProperty(value="业务key")
        //@Valid(nullMsg = "camunda流程定义key不能为空！")
        private String bizKey;

        @ApiModelProperty(value="申请时间")
        @Valid(nullMsg = "申请时间不能为空！")
        private LocalDateTime applyTime;

        @ApiModelProperty(value="业务表单数据")
        @Valid(nullMsg = "业务表单数据不能为空！")
        private List<BizData> bizDataList;

        @ApiModelProperty(value="签收时间-回显表单数据使用")
        private LocalDateTime cliamTime;

        @ApiModelProperty(value="优先级")
        private String bizPriority;
        /*@ApiModelProperty(value="业务数据是否改变标识，用户标识工作流是否需要调用业务接口")
        private Boolean isChange;*/
    }

    //转办
    @Data
    public static class ToSbDto implements IValidation{
        @ApiModelProperty(value="任务ID")
        @Valid(nullMsg = "任务ID不能为空！")
        private String taskId;

        @ApiModelProperty(value="转办用户ID不能为空")
        @Valid(nullMsg = "转办用户ID不能为空！")
        private String targetUserId;
    }

    //流程状态相关-挂起、激活、停止、删除
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StateDto implements IValidation{
        @Valid
        private String camundaProcinsId;
        @Valid
        private String camundaProcdefId;
        private String camundaTaskId;
    }

    @Data
    public static class BizData implements IValidation{
        @ApiModelProperty(value="表单名称")
        @Valid(nullMsg = "表单名称不能为空！")
        private String formName;
        @ApiModelProperty(value="表单中文名称")
        @Valid(nullMsg = "表单中文名称不能为空！")
        private String formNameCh;
        @ApiModelProperty(value="表单值是否改变，默认为true")
        private Boolean isChange = true;
        @ApiModelProperty(value="方法类型，不用传值")
        private String methodType;
        @ApiModelProperty(value="表单请求地址")
        private String url;
        /*@ApiModelProperty(value="转办用户ID不能为空")
        private String path;*/
        @Valid
        private String params;
    }

    //待办查询表单DTO
    @Data
    public static class RespFormDto implements IValidation{
        @ApiModelProperty(value="流程实例id")
        private String camundaProcinsId;

        @ApiModelProperty(value="流程定义id")
        private String camundaProcdefId;

        @ApiModelProperty(value="流程taskId")
        private String camundaTaskId;

        @ApiModelProperty(value="签收时间-回显表单数据使用")
        private LocalDateTime cliamTime;

        @ApiModelProperty(value="流程定义key")
        @Valid(nullMsg = "camunda流程定义key不能为空！")
        private String camundaProcdefKey;

        @ApiModelProperty(value="事由")
        @Valid(nullMsg = "事由不能为空！")
        private String reason;

        @ApiModelProperty(value="业务key")
        //@Valid(nullMsg = "camunda流程定义key不能为空！")
        private String bizKey;

        @ApiModelProperty(value="申请时间")
        @Valid(nullMsg = "申请时间不能为空！")
        private LocalDateTime applyTime;

        @ApiModelProperty(value="业务表单数据")
        @Valid(nullMsg = "业务表单数据不能为空！")
        private List<BizData> bizDataList;

        @ApiModelProperty(value="任务状态")
        private Integer state;

        @ApiModelProperty(value="优先级")
        private String bizPriority;


        @ApiModelProperty(value="开始起草人ID")
        @JsonDeserialize(using = IdTypeDeserializer.class)
        @JsonSerialize(using = IdTypeSerializer.class)
        private Long starterId;

        @ApiModelProperty(value="起草人名称")
        private String starterName;

        @ApiModelProperty(value="起草人部门ID")
        @JsonDeserialize(using = IdTypeDeserializer.class)
        @JsonSerialize(using = IdTypeSerializer.class)
        private Long starterDeptId;

        @ApiModelProperty(value="起草人部门名称")
        private String starterDeptName;

        @ApiModelProperty(value="起草人机构ID")
        @JsonDeserialize(using = IdTypeDeserializer.class)
        @JsonSerialize(using = IdTypeSerializer.class)
        private Long starterOrgId;

        @ApiModelProperty(value="起草人机构名称")
        private String starterOrgName;
    }

}
