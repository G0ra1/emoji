package com.netwisd.base.wf.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/2 6:11 下午
 */
@Data
@XmlRootElement(name="bpmndefinitions")
@XmlType(propOrder = {"xmlnsbpmn","xmlnsbpmndi","xmlnsdc","xmlnsxsi","xmlnscamunda","xmlnsdi","xmlnsnetwisd","id","targetNamespace","exporter","exporterVersion","bpmnprocess","bpmndiBPMNDiagram"})
public class Bpmndefinitions {
    private Bpmnprocess bpmnprocess;
    private BpmndiBPMNDiagram bpmndiBPMNDiagram;
    private String xmlnsbpmn;
    private String xmlnsbpmndi;
    private String xmlnsdc;
    private String xmlnsxsi;
    private String xmlnscamunda;
    private String xmlnsdi;
    private String xmlnsnetwisd;
    private String id;
    private String targetNamespace;
    private String exporter;
    private String exporterVersion;

    @XmlAttribute
    public String getXmlnsbpmn() {
        return xmlnsbpmn;
    }

    public void setXmlnsbpmn(String xmlnsbpmn) {
        this.xmlnsbpmn = xmlnsbpmn;
    }
    @XmlAttribute
    public String getXmlnsbpmndi() {
        return xmlnsbpmndi;
    }

    public void setXmlnsbpmndi(String xmlnsbpmndi) {
        this.xmlnsbpmndi = xmlnsbpmndi;
    }
    @XmlAttribute
    public String getXmlnsdc() {
        return xmlnsdc;
    }

    public void setXmlnsdc(String xmlnsdc) {
        this.xmlnsdc = xmlnsdc;
    }
    @XmlAttribute
    public String getXmlnsxsi() {
        return xmlnsxsi;
    }

    public void setXmlnsxsi(String xmlnsxsi) {
        this.xmlnsxsi = xmlnsxsi;
    }
    @XmlAttribute
    public String getXmlnscamunda() {
        return xmlnscamunda;
    }

    public void setXmlnscamunda(String xmlnscamunda) {
        this.xmlnscamunda = xmlnscamunda;
    }
    @XmlAttribute
    public String getXmlnsdi() {
        return xmlnsdi;
    }

    public void setXmlnsdi(String xmlnsdi) {
        this.xmlnsdi = xmlnsdi;
    }
    @XmlAttribute
    public String getXmlnsnetwisd() {
        return xmlnsnetwisd;
    }

    public void setXmlnsnetwisd(String xmlnsnetwisd) {
        this.xmlnsnetwisd = xmlnsnetwisd;
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public String getTargetNamespace() {
        return targetNamespace;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }
    @XmlAttribute
    public String getExporter() {
        return exporter;
    }

    public void setExporter(String exporter) {
        this.exporter = exporter;
    }
    @XmlAttribute
    public String getExporterVersion() {
        return exporterVersion;
    }

    public void setExporterVersion(String exporterVersion) {
        this.exporterVersion = exporterVersion;
    }
}
