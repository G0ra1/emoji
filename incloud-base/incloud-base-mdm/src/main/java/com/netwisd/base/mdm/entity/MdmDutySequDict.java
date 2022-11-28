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
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Data
@Table(value = "incloud_base_mdm_duty_sequ_dict",comment = "职务序列字典")
@TableName("incloud_base_mdm_duty_sequ_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "职务序列字典 Entity")
public class MdmDutySequDict extends IModel<MdmDutySequDict> {

    /**
     * duty_sequ_name
     * 职务序列
     */
    @ApiModelProperty(value="职务序列")
    @TableField(value="duty_sequ_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = false, comment = "职务序列")
    private String dutySequName;
}
