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
    import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description $组织级别类型字典 功能描述...
 * @author 云数网讯 zouliming@netwisd.com@netwisd.com
 * @date 2021-08-26 09:56:26
 */
@Data
@Table(value = "incloud_base_mdm_org_level_dict",comment = "组织级别类型字典")
@TableName("incloud_base_mdm_org_level_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "组织级别类型字典 Entity")
public class MdmOrgLevelDict extends IModel<MdmOrgLevelDict> {

    /**
     * org_level
     * 组织级别类型
     */
    @ApiModelProperty(value="组织级别类型")
    @TableField(value="org_level")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "组织级别类型")
    private String orgLevel;
}
