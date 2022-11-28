package com.netwisd.base.file.vo;

import com.netwisd.base.file.service.FileInfoService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2022/1/10 23:05
 */
@Data
@AllArgsConstructor
public class FileResultVo {
    private String poolName;
    private FileInfoService fileInfoService;
}
