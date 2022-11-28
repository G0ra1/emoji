package com.netwisd.common.db.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.db.anntation.Column;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 基础model类，用于定义公共的实体属性
 * @Author: zouliming@netwisd.com
 * @Date: 2020/2/27 2:50 下午
 */
@Data
public class IModelExOrg<T extends IModelExOrg<?>> extends Model<T> {
    /**
     * id
     * 主键
     * 主键目前使用序列化处理为String的方式，MP使用的开源算法基于雪花算法，他的实现方式还有一种microuuid的方式
     * 就是使用精度为15位的方式，当然需要修改MP的代码，我们现在使用这种处理方式解决前端处理Number精度问题
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value="主键")
    @TableId(value="id",type= IdType.ASSIGN_ID )
    @Column(type = DataType.BIGINT, isKey=true ,length = 20, isNull = false, comment = "主键")
    public Long id;

    /**
     * id自动赋值标识
     */
    @ApiModelProperty(value="idSign")
    @TableField(exist = false)
    public Boolean idSign;

    /**
     * ----------
     * 这两个做为系统内置的自动加上的两个字段，当然业务model可以覆盖此属性，在codegen中已经对这两个字段的生成做了特殊处理；
     * 同时，在@cloumn注解中也在对此两个字段做了处理；
     * 如果子类中使用了这两个方法的话，那么在自动建表时全使用子类中的；相反通过表生成代码时，默认不会生成在子类中；
     */
    /**
     * 创建日期
     */
    @ApiModelProperty( value="create_time" )
    @TableField(value="create_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "创建日期")
    public LocalDateTime createTime;

    /**
     * 修改日期
     */
    @ApiModelProperty( value="update_time" )
    @TableField(value="update_time")
    @Column(type = DataType.DATETIME, length = 0, isNull = true, comment = "修改日期")
    public LocalDateTime updateTime;
}
