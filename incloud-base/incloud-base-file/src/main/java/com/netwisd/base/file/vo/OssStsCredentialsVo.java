package com.netwisd.base.file.vo;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 阿里云OSS存储STS授权凭证
 * @date 2022-06-27 12:27:14
 */
@Data
@ApiModel(value = "阿里云OSS存储STS授权凭证Vo")
public class OssStsCredentialsVo implements Serializable {

    /**
     * accessKeyId
     * 临时accessKeyId
     */
    @ApiModelProperty(value = "临时accessKeyId")
    private String accessKeyId;

    /**
     * accessKeySecret
     * 临时accessKeySecret
     */
    @ApiModelProperty(value = "临时accessKeySecret")
    private String accessKeySecret;

    /**
     * securityToken
     * 临时token
     */
    @ApiModelProperty(value = "临时token")
    private String securityToken;

    /**
     * expiration
     * token有效期
     */
    @ApiModelProperty(value = "token有效期")
    private String expiration;

    /**
     * bucketName
     * 存储bucket
     */
    @ApiModelProperty(value = "存储bucket")
    private String bucketName;

    public static OssStsCredentialsVo buildVo(AssumeRoleResponse.Credentials credentials, String bucketName) {
        OssStsCredentialsVo result = new OssStsCredentialsVo();
        result.setAccessKeyId(credentials.getAccessKeyId());
        result.setAccessKeySecret(credentials.getAccessKeySecret());
        result.setSecurityToken(credentials.getSecurityToken());
        result.setExpiration(credentials.getExpiration());
        result.setBucketName(bucketName);
        return result;
    }

}
