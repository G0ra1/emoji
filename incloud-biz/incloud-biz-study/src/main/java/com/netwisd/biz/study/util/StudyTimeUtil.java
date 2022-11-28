package com.netwisd.biz.study.util;

public class StudyTimeUtil {

    /**
     * 秒转时分秒
     *
     * @param time
     * @return
     */
    public static String timeToStr(long time) {
        String returnTime = "";
        long mi = 60;
        long hh = mi * 60;
        long hour = time / hh;
        long min = (time % hh) / mi;
        long second = time - hour * 60 * 60 - min * 60;
        if (0 != hour) {
            returnTime = hour + "小时";
        }
        if (0 != min) {
            returnTime = returnTime + min + "分";
        }
        if (0 != second) {
            returnTime = returnTime + second + "秒";
        }
        return returnTime;
    }

    /**
     * 用毫秒秒转化显示学时信息
     *
     * @param studyTime
     * @return
     */
    public static String buildStudyTimeStr(Long studyTime) {
        if (studyTime == null || studyTime.equals(0L) || studyTime < 0) return "未知";
        long hh = 0, mm = 0, ss = 0;
        hh = studyTime / (1000 * 60 * 60);
        mm = studyTime % (1000 * 60 * 60) / (1000 * 60);
        ss = (studyTime - (hh * 60 * 60 + mm * 60) * 1000)/1000;
        StringBuffer resultBuffer = new StringBuffer();
        if (hh != 0l) {
            resultBuffer.append(hh).append("小时");
        }
        if (mm != 0l) {
            resultBuffer.append(mm).append("分");
        }
        //如果小时和分钟都有值就不再显示秒
        if (hh != 0l && mm != 0l) {
            return resultBuffer.toString();
        }
        if (ss != 0l) {
            resultBuffer.append(ss).append("秒");
        }
        return resultBuffer.toString();
    }
}
