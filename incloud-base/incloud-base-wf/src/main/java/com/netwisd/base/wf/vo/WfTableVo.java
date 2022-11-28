package com.netwisd.base.wf.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 1:13 下午
 */
@Data
public class WfTableVo implements Serializable {
    private String tableName;
    private String tableComment;
    private String engine;
    private String tableCollation;
    private LocalDateTime createTime;
}
