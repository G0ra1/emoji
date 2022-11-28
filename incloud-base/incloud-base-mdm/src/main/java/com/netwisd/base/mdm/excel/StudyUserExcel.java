package com.netwisd.base.mdm.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class StudyUserExcel {

    @Excel(name = "机构名称*", needMerge = true, orderNum = "0", width = 30)
    private String parentOrgName;

    @Excel(name = "部门名称*", needMerge = true, orderNum = "1", width = 30)
    private String parentDeptName;

    @Excel(name = "人员姓名*", needMerge = true, orderNum = "2", width = 12)
    private String userNameCh;

    @Excel(name = "密码*", needMerge = true, orderNum = "3", width = 20)
    private String passWord;

    @Excel(name = "手机号*", needMerge = true, orderNum = "4", width = 20)
    private Integer phoneNum;

    @Excel(name = "身份证号*", needMerge = true, orderNum = "5", width = 30)
    private String idCard;

    @Excel(name = "邮箱", needMerge = true, orderNum = "6", width = 30)
    private String email;

    @Excel(name = "用户类型（学员、讲师、管理员）*", needMerge = true, orderNum = "7", width = 35)
    private String userType;

    @Excel(name = "性别（男、女）*", needMerge = true, orderNum = "8", width = 20)
    private String sex;
}
