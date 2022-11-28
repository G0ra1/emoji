package com.netwisd.base.mdm.vo.qywechat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SyncUserExcelVo {
    /**
     * 用户id
     */
    @Excel(name = "用户id", needMerge = true, orderNum = "0", width = 25)
    private String userId;

    /**
     * 用户名称
     */
    @Excel(name = "用户名称", needMerge = true, orderNum = "1", width = 25)
    private String userName;

    /**
     * 错误编码
     */
    @Excel(name = "错误编码", needMerge = true, orderNum = "2", width = 25)
    private String errcode;

    /**
     * 错误信息
     */
    @Excel(name = "错误信息", needMerge = true, orderNum = "3", width = 25)
    private String errmsg;
}
