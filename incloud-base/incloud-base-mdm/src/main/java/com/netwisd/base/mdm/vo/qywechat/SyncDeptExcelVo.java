package com.netwisd.base.mdm.vo.qywechat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SyncDeptExcelVo {

    /**
     * 操作类型
     */
    @Excel(name = "操作类型 1 新建部门 ，2 更改部门名称， 4 移动部门， 8 修改部门排序", needMerge = true, orderNum = "0", width = 25)
    private String action;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID", needMerge = true, orderNum = "1", width = 25)
    private String partyid;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称", needMerge = true, orderNum = "2", width = 25)
    private String orgName;

    /**
     * 错误编码
     */
    @Excel(name = "错误编码", needMerge = true, orderNum = "3", width = 25)
    private String errcode;

    /**
     * 错误信息
     */
    @Excel(name = "错误信息", needMerge = true, orderNum = "4", width = 25)
    private String errmsg;
}
