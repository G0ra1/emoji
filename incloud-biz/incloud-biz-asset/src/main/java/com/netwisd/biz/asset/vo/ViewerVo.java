package com.netwisd.biz.asset.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import com.netwisd.biz.asset.vo.ViewerDetailVo;
/**
 * @Description 物资数据权限人员表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@Data
@ApiModel(value = "物资数据权限人员表 Vo")
public class ViewerVo extends IVo{

    /**
     * user_id
     * 人员id
     */
    @JsonSerialize(using = IdTypeSerializer.class)
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
    private Integer businessType;
    /**
     * business_type_name
     * 查看业务类型（字典）
     */
    @ApiModelProperty(value="查看业务类型（字典）")
    private String businessTypeName;

    @ApiModelProperty(value="数据范围")
    private List<ViewerDetailVo> detailList;

    @ApiModelProperty(value="可查看机构集合")
    private List<Long> orgList;

    @ApiModelProperty(value="可查看部门集合")
    private List<Long> deptList;

    @ApiModelProperty(value="可查看人员集合")
    private List<Long> userList;


}
