package com.netwisd.base.portal.constants;

public enum BizTagEnum {
//    会议费事项提交前("HYSQ-BEFORESUBMIT","会议费事项提交前"),
//    会议费事项提交后("HYSQ-AFTERSUBMIT","会议费事项提交后"),
//    会议费事项撤回("HYSQ-REVOKE","会议费事项撤回"),
//    会议费事项被驳回("HYSQ-BACKTODRAFT","会议费事项被驳回"),
//    会议费事项审批完成("HYSQ-AUDITSUCCEED","会议费事项审批完成"),
//    会议费事项流程终止("HYSQ-STOP","会议费事项流程终止"),
//    会议费事项流程删除("HYSQ-DEL","会议费事项流程删除"),

    新闻类内容完成("NEWS-AUDITSUCCEED","新闻类内容审批完成"),
    新闻类内容流程终止("NEWS-STOP","新闻类内容流程终止"),
    新闻类内容流程删除("NEWS-DEL","新闻类内容流程删除"),

    调整新闻类内容完成("ADJ-NEWS-AUDITSUCCEED","调整新闻类内容审批完成"),
    调整新闻类内容流程终止("ADJ-NEWS-STOP","调整新闻类内容流程终止"),
    调整新闻类内容流程删除("ADJ-NEWS-DEL","调整新闻类内容流程删除"),

    banner类内容类完成("BANNER-AUDITSUCCEED","banner类内容类完成"),
    banner类内容流程终止("BANNER-STOP","banner类内容流程终止"),
    banner类内容流程删除("BANNER-DEL","banner类内容流程删除"),

    调整表banner类内容类完成("ADJ-BANNER-AUDITSUCCEED","调整表banner类内容类完成"),
    调整表banner类内容流程终止("ADJ-BANNER-STOP","调整表banner类内容流程终止"),
    调整表banner类内容流程删除("ADJ-BANNER-DEL","调整表banner类内容流程删除"),

    文件下载类型内容完成("FILES-AUDITSUCCEED","文件下载类型内容完成"),
    文件下载类型内容流程终止("FILES-STOP","文件下载类型内容流程终止"),
    文件下载类型内容流程删除("FILES-DEL","文件下载类型内容流程删除"),

    调整文件下载类型内容完成("ADJ-FILES-AUDITSUCCEED","调整文件下载类型内容完成"),
    调整文件下载类型内容流程终止("ADJ-FILES-STOP","调整文件下载类型内容流程终止"),
    调整文件下载类型内容流程删除("ADJ-FILES-DEL","调整文件下载类型内容流程删除"),

    图片新闻类内容完成("PICNEWS-AUDITSUCCEED","图片新闻类内容审批完成"),
    图片新闻类内容流程终止("PICNEWS-STOP","图片新闻类内容流程终止"),
    图片新闻类内容流程删除("PICNEWS-DEL","图片新闻类内容流程删除"),

    调整图片新闻类内容完成("ADJ-PICNEWS-AUDITSUCCEED","调整图片新闻类内容审批完成"),
    调整图片新闻类内容流程终止("ADJ-PICNEWS-STOP","调整图片新闻类内容流程终止"),
    调整图片新闻类内容流程删除("ADJ-PICNEWS-DEL","调整图片新闻类内容流程删除"),

    图片轮播类内容完成("PICTURES-AUDITSUCCEED","图片轮播类内容审批完成"),
    图片轮播类内容流程终止("PICTURES-STOP","图片轮播类内容流程终止"),
    图片轮播类内容流程删除("PICTURES-DEL","图片轮播类内容流程删除"),

    系统集成类内容完成("SYSJOINTS-AUDITSUCCEED","系统集成类内容审批完成"),
    系统集成类内容流程终止("SYSJOINTS-STOP","系统集成类内容流程终止"),
    系统集成类内容流程删除("SYSJOINTS-DEL","系统集成类内容流程删除"),

    视频类内容完成("VIDEOS-AUDITSUCCEED","视频类内容审批完成"),
    视频类内容流程终止("VIDEOS-STOP","视频类内容流程终止"),
    视频类内容流程删除("VIDEOS-DEL","视频类内容流程删除"),

    调整表视频类内容完成("ADJ-VIDEOS-AUDITSUCCEED","调整表视频类内容审批完成"),
    调整表视频类内容流程终止("ADJ-VIDEOS-STOP","调整表视频类内容流程终止"),
    调整表视频类内容流程删除("ADJ-VIDEOS-DEL","调整表视频类内容流程删除");

    public String code;
    public String message;

    BizTagEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public static String getMessage(String code) {
        if (code != null) {
            for (BizTagEnum value : BizTagEnum.values()) {
                if (value.code .equals( code)) return value.message;
            }
        }
        return null;
    }
}
