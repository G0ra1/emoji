package com.netwisd.base.wf.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/10 10:22 上午
 */
@Data
public class WfFormTablesVo implements Serializable {
    private String bizTable;
    private String bizTableAlias;
}
