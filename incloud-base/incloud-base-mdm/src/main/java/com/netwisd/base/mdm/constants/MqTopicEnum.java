package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: rock topic
 * @Author: XHL@netwisd.com
 * @Date: 2021/10/11 11:52 上午
 */
@AllArgsConstructor
@Getter
public enum MqTopicEnum {
    ZY_MDM_ORG_TOPIC("zy_mdm_org_topic", "组织"),
    ZY_MDM_POST_TOPIC("zy_mdm_post_topic", "岗位"),
    ZY_MDM_USER_TOPIC("zy_mdm_user_topic", "人员");

    public String code;
    public String message;

    public static String getMessage(String code){
        for (MqTopicEnum value : MqTopicEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
