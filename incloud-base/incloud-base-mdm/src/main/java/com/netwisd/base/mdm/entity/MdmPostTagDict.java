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
 * @Description $岗位标识字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:44:20
 */
@Data
@Table(value = "incloud_base_mdm_post_tag_dict",comment = "岗位标识字典")
@TableName("incloud_base_mdm_post_tag_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "岗位标识字典 Entity")
public class MdmPostTagDict extends IModel<MdmPostTagDict> {

    /**
     * post_tag_name
     * 岗位标识
     */
    @ApiModelProperty(value="岗位标识")
    @TableField(value="post_tag_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "岗位标识")
    private String postTagName;
}
