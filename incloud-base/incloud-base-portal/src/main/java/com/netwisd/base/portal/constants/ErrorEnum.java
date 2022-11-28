package com.netwisd.base.portal.constants;

public enum ErrorEnum implements IError{

    /**
     * 通用
     */
    DATE_ERROR("DATE_ERROR","日期错误"),
    ERROR("ERROR", "内部错误"),
    ID_NULL("ID_NULL", "ID为空"),
    IDS_NULL("IDS_NULL", "IDS为空"),
    DETAIL_NULL("DETAIL_NULL", "详情为空"),


    FEE_TYPE_CODE_NULL("FEE_TYPE_CODE_NULL", "附件类型为空"),
    FEE_TYPE_CODE_NAME_NULL("FEE_TYPE_CODE_NAME_NULL", "附件类型名称为空"),


    /**
     * 文件
     */
    NOT_FOUND_FILE("NOT_FOUND_FILE", "文件未找到"),
    IMG_NULL("IMG_NULL", "图片为空"),
    FILE_READER_ERROR("FILE_READER_ERROR", "文件读取失败"),
    FILE_ERROR("FILE_ERROR", "文件格式不正确"),

    /**
     * 发票
     */
    INVOICETYPE_NULL("INVOICETYPE_NULL", "票据类型为空"),
    INVOICECODE_NULL("INVOICECODE_NULL", "发票代码为空"),
    INVOICENO_NULL("INVOICENO_NULL", "发票号码为空"),
    INVOICEDATE_NULL("INVOICEDATE_NULL", "开票日期为空"),
    INVOICEAMT_NULL("INVOICEAMT_NULL", "不含税金额为空"),
    CHECKCODE_NULL("CHECKCODE_NULL", "校验码为空"),
    VCODE_NULL("VCODE_NULL", "二维码解析串为空"),
    INCHECKCODELENGTH("INCHECKCODELENGTH", "发票号码小于6位"),
    INVOICE_EXIST("INVOICE_EXIST", "发票信息已存在"),
    INVOICE_ERROR("INVOCIE_ERROR","发票识别失败"),

    /**
     * 用户信息
     */
    USERNAME_NULL("USERNAME_NULL", "用户名为空"),
    PASSWORD_NULL("PASSWORD_NULL", "密码为空"),
    USER_LOGIN_ERROR("LOGIN_ERROR", "用户名或密码错误"),
    USER_ERROR("USER_ERROR", "获取用户信息异常"),
    USER_NULL("USER_NULL", "未知的用户"),

    /**
     * 飞机行程单
     */
    FLIGHT_EXIST("FLIGHT_EXIST", "飞机票行程单已存在"),
    TRAINTICKET_EXIST("TRAINTICKET_EXIST", "火车票行程单已存在"),
    TICKETNUMBER_NULL("TICKETNUMBER_NULL", "电子票号为空"),
    FLIGHT_NULL("FLIGHT_NULL", "航班号为空"),
    DEPARTURESTATION_NULL("DEPARTURESTATION_NULL", "始发站为空"),
    DESTINATIONSTATION_NULL("DESTINATIONSTATION_NULL", "终点站为空"),
    DEPARTURETIME_NULL("DEPARTURETIME_NULL", "发车时间为空"),
    CABINCLASS_NULL("CABINCLASS_NULL", "坐席级别为空"),
    PASSENGERNAME_NULL("PASSENGERNAME_NULL", "旅客姓名为空"),
    IDNUMBER_NULL("IDNUMBER_NULL", "身份证件号码为空"),
    FARE_NULL("FARE_NULL", "票价为空"),
    CAACDEVELOPMENTFUND_NULL("CAACDEVELOPMENTFUND_NULL", "发展基金为空"),
    FUELSURCHARGE_NULL("FUELSURCHARGE_NULL", "燃油附加费"),
    OTHERTAXES_NULL("OTHERTAXES_NULL", "其它税费"),
    TOTAL_NULL("TOTAL_NULL", "总价为空"),

    /**
     * Welink
     */
    WELINK_ACCESSTOKEN_NULL("WELINK_ACCESSTOKEN_NULL", "WelinkAccessToken空"),
    WELINK_USERID_NULL("WELINK_USERID_NULL", "WelinkUserId空"),
    WELINK_SILENTLOGIN_ERROR("WELINK_SILENTLOGIN_ERROR", "静默登录失败，请绑定用户"),


    /**
     * 火车票行程单
     */
    DEPARTURE_STATION_NULL("DEPARTURE_STATION_NULL", "始发站为空"),
    DESTINATION_STATION_NULL("DESTINATION_STATION_NULL", "终点站为空"),
    DEPARTURE_TIME_NULL("DEPARTURE_TIME_NULL", "开车时间为空"),
    TICKET_PRICE_NULL("TICKET_PRICE_NULL", "票价为空");

    private String code;

    private String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
