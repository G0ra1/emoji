package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 9:42 上午
 */
@Data
@XmlType(propOrder = {"bpmnextensionElements","bpmnincoming","bpmnoutgoing","bpmnmultiInstanceLoopCharacteristics"})
public class BpmnuserTask implements BpmnFlowNode{
    private String id;
    private String name;
    private String camundacandidateUsers;
    private String camundadueDate;
    private String camundafollowUpDate;
    private String camundapriority;
    private BpmnextensionElements bpmnextensionElements;
    private List<String> bpmnincoming;
    private List<String> bpmnoutgoing;
    private BpmnmultiInstanceLoopCharacteristics bpmnmultiInstanceLoopCharacteristics;
    private String camundadueDay; //到期时间变量
    private String netwisddueDay; //到期时间 例子：0.1天
    //private String camundafollowUpDay; //跟踪时间 例子： 1天

    private String netwisdselectRule;//选人规则
    private String netwisdbatchRule; //批量审批规则
    private String netwisdcancelRule; //是否支持撤回
    private String netwisdreturnRule; //是否支持驳回

    private String netwisddbDetailId; //用户任务详情id
    private String netwisddbId;//用户任务id
    @XmlAttribute
    public String getNetwisddbDetailId() {
        return netwisddbDetailId;
    }

    public void setNetwisddbDetailId(String netwisddbDetailId) {
        this.netwisddbDetailId = netwisddbDetailId;
    }
    @XmlAttribute
    public String getNetwisddbId() {
        return netwisddbId;
    }

    public void setNetwisddbId(String netwisddbId) {
        this.netwisddbId = netwisddbId;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlAttribute
    public String getCamundacandidateUsers() {
        return camundacandidateUsers;
    }

    public void setCamundacandidateUsers(String camundacandidateUsers) {
        this.camundacandidateUsers = camundacandidateUsers;
    }

    @XmlAttribute
    public String getCamundadueDate() {
        return camundadueDate;
    }

    public void setCamundadueDate(String camundadueDate) {
        this.camundadueDate = camundadueDate;
    }
    @XmlAttribute
    public String getCamundafollowUpDate() {
        return camundafollowUpDate;
    }

    public void setCamundafollowUpDate(String camundafollowUpDate) {
        this.camundafollowUpDate = camundafollowUpDate;
    }
    @XmlAttribute
    public String getCamundapriority() {
        return camundapriority;
    }

    public void setCamundapriority(String camundapriority) {
        this.camundapriority = camundapriority;
    }

    @XmlAttribute
    public String getNetwisdselectRule() {
        return netwisdselectRule;
    }

    public void setNetwisdselectRule(String netwisdselectRule) {
        this.netwisdselectRule = netwisdselectRule;
    }
    @XmlAttribute
    public String getNetwisdbatchRule() {
        return netwisdbatchRule;
    }

    public void setNetwisdbatchRule(String netwisdbatchRule) {
        this.netwisdbatchRule = netwisdbatchRule;
    }
    @XmlAttribute
    public String getNetwisdcancelRule() {
        return netwisdcancelRule;
    }

    public void setNetwisdcancelRule(String netwisdcancelRule) {
        this.netwisdcancelRule = netwisdcancelRule;
    }
    @XmlAttribute
    public String getNetwisdreturnRule() {
        return netwisdreturnRule;
    }

    public void setNetwisdreturnRule(String netwisdreturnRule) {
        this.netwisdreturnRule = netwisdreturnRule;
    }
    @XmlAttribute
    public String getCamundadueDay() {
        return camundadueDay;
    }

    public void setCamundadueDay(String camundadueDay) {
        this.camundadueDay = camundadueDay;
    }
    @XmlAttribute
    public String getNetwisddueDay() {
        return netwisddueDay;
    }

    public void setNetwisddueDay(String netwisddueDay) {
        this.netwisddueDay = netwisddueDay;
    }

    //    @XmlAttribute
//    public String getCamundafollowUpDay() {
//        return camundafollowUpDay;
//    }
//
//    public void setCamundafollowUpDay(String camundafollowUpDay) {
//        this.camundafollowUpDay = camundafollowUpDay;
//    }
}
