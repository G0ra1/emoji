package com.netwisd.base.mdm.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class MdmUserExcel {
    /**
     * 基本信息
     */
    @Excel(name = "机构部门名称", needMerge = true, orderNum = "0", width = 120)
    private String parentOrgName;

//    @Excel(name = "部门名称", needMerge = true, orderNum = "1", width = 25)
//    private String parentDeptName;

    @Excel(name = "姓名", needMerge = true, orderNum = "2", width = 25)
    private String userNameCh;

    @Excel(name = "国籍", needMerge = true, orderNum = "3", width = 25)
    private String nationality;

    @Excel(name = "证件类型", needMerge = true, orderNum = "4", width = 25)
    private String cardType;

    @Excel(name = "性别", needMerge = true, orderNum = "5", width = 25)
    private String sex;

//    @Excel(name = "学历", needMerge = true, orderNum = "6", width = 25)
//    private String education;

    @Excel(name = "证件号", needMerge = true, orderNum = "7", width = 25)
    private String idCard;

    @Excel(name = "籍贯", needMerge = true, orderNum = "8", width = 25)
    private String nativePlace;

    @Excel(name = "民族", needMerge = true, orderNum = "9", width = 25)
    private String nation;

    @Excel(name = "是否启用", needMerge = true, orderNum = "10", width = 25)
    private String status;

    @Excel(name = "手机号", needMerge = true, orderNum = "11", width = 25)
    private String phoneNum;

    @Excel(name = "邮箱", needMerge = true, orderNum = "12", width = 25)
    private String email;

    @Excel(name = "家庭住址", needMerge = true, orderNum = "13", width = 25)
    private String addr;

    @Excel(name = "婚姻状况", needMerge = true, orderNum = "14", width = 25)
    private String marriageStatus;

    @Excel(name = "入职时间", needMerge = true, orderNum = "15", width = 25)
    private String gfEntryTime;

    @Excel(name = "离职时间", needMerge = true, orderNum = "16", width = 25)
    private String gfQuitTime;

    @Excel(name = "户口性质", needMerge = true, orderNum = "17", width = 25)
    private String birthNature;

    @Excel(name = "户口所在地", needMerge = true, orderNum = "18", width = 25)
    private String birthAddr;

    @Excel(name = "出生地", needMerge = true, orderNum = "19", width = 25)
    private String birthPlace;

    @Excel(name = "所属派出所", needMerge = true, orderNum = "20", width = 25)
    private String policeStation;

    @Excel(name = "曾用名", needMerge = true, orderNum = "21", width = 25)
    private String formerName;

    @Excel(name = "身高;cm", needMerge = true, orderNum = "22", width = 25)
    private String height;

    @Excel(name = "体重;kg", needMerge = true, orderNum = "23", width = 25)
    private String bodyWeight;

    @Excel(name = "血型", needMerge = true, orderNum = "24", width = 25)
    private Integer bloodType;

    @Excel(name = "出生日期", needMerge = true, orderNum = "25", width = 25)
    private String birthday;

    @Excel(name = "政治面貌", needMerge = true, orderNum = "26", width = 25)
    private Integer politicsStatus;

    @Excel(name = "生育情况", needMerge = true, orderNum = "27", width = 25)
    private Integer isFertility;

    @Excel(name = "宗教信仰", needMerge = true, orderNum = "28", width = 25)
    private Integer religion;

    @Excel(name = "健康状况", needMerge = true, orderNum = "29", width = 25)
    private Integer healthCondition;

    @Excel(name = "个人专长", needMerge = true, orderNum = "30", width = 25)
    private String personalExpertise;

    @Excel(name = "兴趣爱好", needMerge = true, orderNum = "31", width = 25)
    private String hobbies;

    @Excel(name = "办公电话", needMerge = true, orderNum = "32", width = 25)
    private String officePhone;

    @Excel(name = "QQ", needMerge = true, orderNum = "33", width = 25)
    private String qq;

    @Excel(name = "微信", needMerge = true, orderNum = "34", width = 25)
    private String wechat;

    @Excel(name = "现居地址", needMerge = true, orderNum = "35", width = 25)
    private String nowAddr;

    @Excel(name = "现居地邮编", needMerge = true, orderNum = "36", width = 25)
    private String nowAddrZipcode;

    /**
     * 岗位信息
     */
    @Excel(name = "岗位名称", needMerge = true, orderNum = "37", width = 25)
    private String postName;

    @Excel(name = "岗位所属机构", needMerge = true, orderNum = "38", width = 120)
    private String orgFullPostName;

    @Excel(name = "岗位性质", needMerge = true, orderNum = "39", width = 25)
    private String postIsMaster;

    /**
     * 职位信息
     */
    @Excel(name = "职位名称", needMerge = true, orderNum = "40", width = 25)
    private String dutyName;

    @Excel(name = "职位所属机构", needMerge = true, orderNum = "41", width = 120)
    private String orgFullDutyName;

    @Excel(name = "职位性质", needMerge = true, orderNum = "42", width = 25)
    private String dutyIsMaster;
}
