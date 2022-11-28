package com.netwisd.base.wf.vo;

import com.netwisd.base.common.user.vo.expression.UserExpressionVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/21 10:03 上午
 */
@Data
public class WfNextUserVo implements Serializable {
/*    private String currentCamundaNodeId;
    private String currentCamundaNodeName;
    private Integer currentcamundaNodeType;*/
    private String nextCamundaNodeId;
    private String nextCamundaNodeName;
    private Integer nextcamundaNodeType;
    private String camundaTaskId;
    /*private Integer selectRule;;*/
    @ApiModelProperty("提交方式——0，正常提交；1.提交到原驳回节点")
    private Integer submitType;
    private List<UserExpressionVO> userList;
}
