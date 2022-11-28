package com.netwisd.biz.mdm.entity;

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
 * @Description $乙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:39:08
 */
@Data
@Table(value = "incloud_biz_mdm_contract_partyb_contact",comment = "乙方联系人")
@TableName("incloud_biz_mdm_contract_partyb_contact")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "乙方联系人 Entity")
public class ContractPartybContact extends IModel<ContractPartybContact> {

    /**
     * partyb_id
     * 
     */
    @ApiModelProperty(value="partyb_id")
    @TableField(value="partyb_id")
    @Column(type = DataType.BIGINT, length = 20,  isNull = true, comment = "")
    private Long partybId;
    /**
     * partyb_code
     * 
     */
    @ApiModelProperty(value="partyb_code")
    @TableField(value="partyb_code")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "")
    private String partybCode;
    /**
     * contacts_id
     * 联系人ID
     */
    @ApiModelProperty(value="联系人ID")
    @TableField(value="contacts_id")
    @Column(type = DataType.INT, length = 11,  isNull = true, comment = "联系人ID")
    private Integer contactsId;
    /**
     * contacts_name
     * 联系人姓名
     */
    @ApiModelProperty(value="联系人姓名")
    @TableField(value="contacts_name")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "联系人姓名")
    private String contactsName;
    /**
     * contacts_phone
     * 联系人电话
     */
    @ApiModelProperty(value="联系人电话")
    @TableField(value="contacts_phone")
    @Column(type = DataType.VARCHAR, length = 255,  isNull = true, comment = "联系人电话")
    private String contactsPhone;
}
