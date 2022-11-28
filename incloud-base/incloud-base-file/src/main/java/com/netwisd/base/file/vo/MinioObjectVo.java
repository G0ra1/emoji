package com.netwisd.base.file.vo;

import io.minio.ObjectStat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * minio返回的对象类，copy自minio的返回值
 */

@Data
@AllArgsConstructor
public class MinioObjectVo {
    private String bucketName;
    private String name;
    private Date createdTime;
    private Long length;
    private String etag;
    private String contentType;
    private Map<String, List<String>> httpHeaders;

    public MinioObjectVo(ObjectStat os) {
        this.bucketName = os.bucketName();
        this.name = os.name();
        this.createdTime = os.createdTime();
        this.length = os.length();
        this.etag = os.etag();
        this.contentType = os.contentType();
        this.httpHeaders = os.httpHeaders();
    }
}
