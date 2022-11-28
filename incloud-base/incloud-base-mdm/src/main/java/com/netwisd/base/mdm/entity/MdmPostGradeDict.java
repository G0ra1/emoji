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
 * @Description $岗位职等 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:54:55
 */
@Data
@Table(value = "incloud_base_mdm_post_grade_dict",comment = "岗位职等")
@TableName("incloud_base_mdm_post_grade_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "岗位职等 Entity")
public class MdmPostGradeDict extends IModel<MdmPostGradeDict> {

    /**
     * post_grade_name
     * 职等
     */
    @ApiModelProperty(value="职等")
    @TableField(value="post_grade_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "职等")
    private String postGradeName;
    /**
     * post_sequ_id
     * 所属序列id
     */
    @ApiModelProperty(value="所属序列id")
    @TableField(value="post_sequ_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = false, comment = "所属序列id")
    private Long postSequId;
    /**
     * post_sequ_name
     * 所属序列名称
     */
    @ApiModelProperty(value="所属序列名称")
    @TableField(value="post_sequ_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "所属序列名称")
    private String postSequName;
}
