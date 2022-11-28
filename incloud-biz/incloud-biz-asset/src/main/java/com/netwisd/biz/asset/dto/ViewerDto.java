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
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
import com.netwisd.biz.asset.dto.ViewerDetailDto;
/**
 * @Description 物资数据权限人员表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@Data
@Map("incloud_biz_asset_viewer")
@ApiModel(value = "物资数据权限人员表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ViewerDto extends IDto{

    public ViewerDto(Args args){
        super(args);
    }
    /**
     * user_id
     * 人员id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="人员id")
    private Long userId;
    /**
     * user_name
     * 人员名称
     */
    @ApiModelProperty(value="人员名称")
    private String userName;
    /**
     * user_name_ch
     * 人员名称
     */
    @ApiModelProperty(value="人员名称")
    private String userNameCh;
    /**
     * viewer_type
     * 查看这类型;1人员，2角色
     */
    @ApiModelProperty(value="查看这类型;1人员，2角色")
    private Integer viewerType;
    /**
     * business_type
     * 查看业务类型（字典）
     */
    @ApiModelProperty(value="查看业务类型（字典）")
    private String businessType;
    /**
     * business_type_name
     * 查看业务类型（字典）
     */
    @ApiModelProperty(value="查看业务类型（字典）")
    private String businessTypeName;

    @ApiModelProperty(value="数据范围")
    private List<ViewerDetailDto> detailList;

    /**
     * user_id
     * 人员ID
     */
    @ApiModelProperty(value="人员ID")
    private String userIds;
    /**
     * user_name
     * 人员名称
     */
    @ApiModelProperty(value="人员名称")
    private String userNames;
    /**
     * user_name_ch
     * 人员名称
     */
    @ApiModelProperty(value="人员名称")
    private String userNameChs;

    /**
     * 指定字段模糊查询
     */
    @ApiModelProperty(value="查询条件")
    private String searchCondition;

}
