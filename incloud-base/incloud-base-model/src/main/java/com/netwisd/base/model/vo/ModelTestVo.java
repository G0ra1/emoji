package com.netwisd.base.model.vo;

import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Description 数据建模测试表 功能描述...
 * @author 云数网讯 sunzhenxi@netwisd.com
 * @date 2021-12-21 10:21:27
 */
@Data
@ApiModel(value = "数据建模测试表 Vo")
public class ModelTestVo extends IVo{

    /**
     * name
     * 姓名
     */
    
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * age
     * 年龄
     */
    
    @ApiModelProperty(value="年龄")
    private Integer age;
    /**
     * sex
     * 性别
     */
    
    @ApiModelProperty(value="性别")
    private Integer sex;
    /**
     * salary
     * 工资
     */
    
    @ApiModelProperty(value="工资")
    private BigDecimal salary;
    /**
     * dept_name
     * 部门名称
     */
    
    @ApiModelProperty(value="部门名称")
    private String deptName;
    /**
     * org_name
     * 机构名称
     */
    
    @ApiModelProperty(value="机构名称")
    private String orgName;
}
