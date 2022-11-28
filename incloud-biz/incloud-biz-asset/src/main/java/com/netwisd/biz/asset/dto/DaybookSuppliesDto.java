package com.netwisd.biz.asset.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @Description 耗材流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 17:20:30
 */
@Data
@ApiModel(value = "耗材流水表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DaybookSuppliesDto extends IDto{

    public DaybookSuppliesDto(Args args){
        super(args);
    }
    /**
     * form_id
     * 表单id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="表单id")
    private Long formId;
    /**
     * camunda_procins_id
     * 流程实例id
     */
    @ApiModelProperty("流程实例id")
    private String camundaProcinsId;

    /**
     * assets_id
     * 资产台账id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产台账id")
    private Long assetsId;

    /**
     * assets_detail_id
     * 资产明细表id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资产明细表id")
    private Long assetsDetailId;

    /**
     * item_id
     * 物资Id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="物资Id")
    private Long itemId;

    /**
     * item_code
     * 物资编码;物资编码
     */
    @ApiModelProperty(value="物资编码;物资编码")
    private String itemCode;

    /**
     * item_name
     * 物资名称;物资名称
     */
    @ApiModelProperty(value="物资名称;物资名称")
    private String itemName;

    /**
     * type
     * 业务类型;验收/领用等等
     */
    @ApiModelProperty(value="业务类型;验收/领用等等")
    private String type;

    /**
     * apply_time
     * 申请时间
     */
    @ApiModelProperty(value="申请时间")
    private LocalDateTime applyTime;

    /**
     * apply_user_id
     * 申请人ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人ID")
    private Long applyUserId;

    /**
     * apply_user_name
     * 申请人名称
     */
    @ApiModelProperty(value="申请人名称")
    private String applyUserName;

    /**
     * apply_user_org_id
     * 申请人机构ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人机构ID")
    private Long applyUserOrgId;

    /**
     * apply_user_org_name
     * 申请人机构名称
     */
    @ApiModelProperty(value="申请人机构名称")
    private String applyUserOrgName;

    /**
     * apply_user_dept_id
     * 申请人部门ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="申请人部门ID")
    private Long applyUserDeptId;

    /**
     * apply_user_dept_name
     * 申请人部门名称
     */
    @ApiModelProperty(value="申请人部门名称")
    private String applyUserDeptName;

}
