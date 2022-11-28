package com.netwisd.base.model.entity;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 云数网讯 sunzhenxi@netwisd.com
 * @Description $数据建模测试表 功能描述...
 * @date 2021-12-21 10:21:27
 */
@Data
@Table(value = "incloud_base_model_test", comment = "数据建模测试表")
@TableName("incloud_base_model_test")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "数据建模测试表 Entity")
public class ModelTest extends IModel<ModelTest> {

    /**
     * name
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @TableField(value = "name")
    @Column(type = DataType.VARCHAR, length = 50, isNull = true, comment = "姓名")
    private String name;

    /**
     * age
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    @TableField(value = "age")
    @Column(type = DataType.INT, length = 10, isNull = true, comment = "年龄")
    private Integer age;

    /**
     * sex
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @TableField(value = "sex")
    @Column(type = DataType.INT, length = 1, isNull = true, comment = "性别")
    private Integer sex;

    /**
     * salary
     * 工资
     */
    @ApiModelProperty(value = "工资")
    @TableField(value = "salary")
    @Column(type = DataType.DECIMAL, length = 10, precision = 2, isNull = true, comment = "工资")
    private BigDecimal salary;

    /**
     * dept_name
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @TableField(value = "dept_name")
    @Column(type = DataType.VARCHAR, length = 50, isNull = true, comment = "部门名称")
    private String deptName;

    /**
     * org_name
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称")
    @TableField(value = "org_name")
    @Column(type = DataType.VARCHAR, length = 50, isNull = true, comment = "机构名称")
    private String orgName;

}
