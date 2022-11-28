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
 * @Description $岗位序列 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 11:01:12
 */
@Data
@Table(value = "incloud_base_mdm_post_sequ_dict",comment = "岗位序列")
@TableName("incloud_base_mdm_post_sequ_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "岗位序列 Entity")
public class MdmPostSequDict extends IModel<MdmPostSequDict> {

    /**
     * post_sequ_name
     * 岗位序列
     */
    @ApiModelProperty(value="岗位序列")
    @TableField(value="post_sequ_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "岗位序列")
    private String postSequName;
}
