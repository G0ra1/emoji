package com.netwisd.base.wf;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description 数据源 功能描述... 测试构建
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-25 14:34:24
 */
@Data
@ApiModel(value = "数据源 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TestDto extends IDto{

    public TestDto(Args args){
        super(args);
    }

    /**
     * pool_name
     * 数据源
     */
    @ApiModelProperty(value="数据源")
    @Valid(length = 50) 
    private String poolName;

    /**
     * type
     * 类型1：mysql 2：oracle
     */
    @ApiModelProperty(value="类型1：mysql 2：oracle")
    private Integer type;

    /**
     * username
     * 用户名
     */
    @ApiModelProperty(value="用户名")
    private String username;

    /**
     * password
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * url
     * url
     */
    @ApiModelProperty(value="url")
    private String url;

    /**
     * is_enable
     * 是否启用
     */
    @ApiModelProperty(value="是否启用")
    @Valid(length = 1) 
    private Integer isEnable;

    /**
     * description
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;

}
