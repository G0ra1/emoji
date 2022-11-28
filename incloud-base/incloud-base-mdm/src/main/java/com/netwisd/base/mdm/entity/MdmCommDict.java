package com.netwisd.base.mdm.entity;

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

/**
 * @Description $通用字典 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:00:30
 */
@Data
@Table(value = "incloud_base_mdm_comm_dict",comment = "$通用字典")
@TableName("incloud_base_mdm_comm_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "$通用字典 Entity")
public class MdmCommDict extends IModel<MdmCommDict> {

    /**
     * dict_type_id
     * 字典分类id
     */
    @ApiModelProperty(value="字典分类id")
    @TableField(value="dict_type_id")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "字典分类id")
    private String dictTypeId;
    /**
     * dict_code
     * 字典code
     */
    @ApiModelProperty(value="字典code")
    @TableField(value="dict_code")
    @Column(type = DataType.VARCHAR, length = 32,  isNull = false, comment = "字典code")
    private String dictCode;
    /**
     * dict_name
     * 字典名称
     */
    @ApiModelProperty(value="字典名称")
    @TableField(value="dict_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "字典名称")
    private String dictName;
}
