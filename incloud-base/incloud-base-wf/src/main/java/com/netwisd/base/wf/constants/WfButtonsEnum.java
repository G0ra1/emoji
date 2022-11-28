package com.netwisd.base.wf.constants;

public enum WfButtonsEnum {

    WF_SAVE("wf_save", "保存"),
    WF_HANDLE("wf_handle", "办理"),
    WF_BACK("wf_back", "驳回"),
    WF_REVOKE ("wf_revoke", "撤回"),
    WF_DEL_PROCESS("wf_del_process", "流程删除"),
    WF_ACT_PROCESS("wf_act_process", "流程激活"),
    WF_END_PROCESS("wf_end_process", "流程终止"),
    WF_SUS_PROCESS("wf_sus_process", "流程挂起"),
    WF_TURN_PROCESS("wf_turn_process", "转办"),
    WF_URGE_PROCESS("wf_urge_process", "催办"),
    WF_NOTIFY_PROCESS("wf_notify_process", "知会"),
    WF_SEND_READ_PROCESS("wf_send_read_process", "传阅"),
    WF_COUNTERSIGN ("wf_countersign", "加签"),
    ;

    public String code;
    public String message;

    WfButtonsEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (WfButtonsEnum value : WfButtonsEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
