package com.netwisd.base.portal.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.base.portal.dto.PortalPartDsDto;
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
 * @Description $栏目管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 19:27:46
 */
@Data
@Table(value = "incloud_base_portal_part",comment = "栏目管理")
@TableName("incloud_base_portal_part")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "栏目管理 Entity")
public class PortalPart extends IModel<PortalPart> {

    /**
     * portal_id
     * 所属门户
     */
    @ApiModelProperty(value="所属门户")
    @TableField(value="portal_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "所属门户")
    private Long portalId;
    /**
     * portal_name
     * 门户名称
     */
    @ApiModelProperty(value="门户名称")
    @TableField(value="portal_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "门户名称")
    private String portalName;
    /**
     * part_name
     * 栏目名称
     */
    @ApiModelProperty(value="栏目名称")
    @TableField(value="part_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "栏目名称")
    private String partName;
    /**
     * part_type_id
     * 栏目类型ID
     */
    @ApiModelProperty(value="栏目类型ID")
    @TableField(value="part_type_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "栏目类型ID")
    private Long partTypeId;
    /**
     * part_type_code
     * 栏目类型code
     */
    @ApiModelProperty(value="栏目类型code")
    @TableField(value="part_type_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "栏目类型code")
    private String partTypeCode;
    /**
     * part_type_name
     * 栏目类型名称
     */
    @ApiModelProperty(value="栏目类型名称")
    @TableField(value="part_type_name")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "栏目类型名称")
    private String partTypeName;
    /**
     * portal_code
     * 栏目CODE 栏目CODE;1用来确定版本2静态化可能用这个code生成文件夹
     */
    @ApiModelProperty(value="栏目CODE 栏目CODE;1用来确定版本2静态化可能用这个code生成文件夹")
    @TableField(value="part_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "栏目CODE 栏目CODE;1用来确定版本2静态化可能用这个code生成文件夹")
    private String partCode;
    /**
     * is_enable
     * 是否启用 0否1是;是否启用
     */
    @ApiModelProperty(value="是否启用 0否1是;是否启用")
    @TableField(value="is_enable")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "是否启用 0否1是;是否启用")
    private Integer isEnable;
    /**
     * is_ds
     * 是否使用数据源
     */
    @ApiModelProperty(value="是否使用数据源")
    @TableField(value="is_ds")
    @Column(type = DataType.INT, length = 11,  isNull = false, comment = "是否使用数据源")
    private Integer isDs;
    /**
     * front_data
     * 前端保留字段
     */
    @ApiModelProperty(value="前端保留字段")
    @TableField(value="front_data")
    @Column(type = DataType.TEXT, length = 0,  isNull = true, comment = "前端保留字段")
    private String frontData;
    /**
     * remark
     * 备注
     */
    @ApiModelProperty(value="备注")
    @TableField(value="remark")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "备注")
    private String remark;
    /**
     * hits
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    @TableField(value = "hits")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "点击量")
    private Long hits;

}
