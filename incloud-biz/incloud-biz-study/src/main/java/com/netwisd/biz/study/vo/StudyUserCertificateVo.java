package com.netwisd.biz.study.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 人员证书
 * @date 2022-04-25 09:39:13
 */
@Data
@ApiModel(value = "人员证书Vo")
public class StudyUserCertificateVo extends IVo {

    /**
     * certificate_template_id
     * 证书模板ID
     */
    @JsonSerialize(using = IdTypeSerializer.class)
    @ApiModelProperty(value = "证书模板ID")
    private Long certificateTemplateId;

    /**
     * certificate_name
     * 证书名称
     */
    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    /**
     * certificate_code
     * 证书编号
     */
    @ApiModelProperty(value = "证书编号")
    private String certificateCode;

    /**
     * type_code
     * 分类编码
     */
    @ApiModelProperty(value = "分类编码")
    private String typeCode;

    /**
     * type_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String typeName;

    /**
     * issuer
     * 发证机构
     */
    @ApiModelProperty(value = "发证机构")
    private String issuer;


    /**
     * issuer
     * 证书描述
     */
    @ApiModelProperty(value = "证书描述")
    private String instructions;

    /**
     * 发证日期
     */
    @ApiModelProperty(value = "issue_date")
    public LocalDate issueDate;


    /**
     * validity
     * 证书有效期 0表示长期
     */
    @ApiModelProperty(value = "证书有效期 0表示长期")
    private Integer validity;

    /**
     * template_file_path
     * 模板文件存放路径
     */
    @ApiModelProperty(value = "模板文件存放路径")
    private String templateFilePath;

    /**
     * special_id
     * 专题ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "专题ID")
    private Long specialId;

    /**
     * special_name
     * 专题名称
     */
    @ApiModelProperty(value = "专题名称")
    private String specialName;

    /**
     * exam_paper_id
     * 试卷ID
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "试卷ID")
    private Long examPaperId;

    /**
     * exam_paper_name
     * 试卷名称
     */
    @ApiModelProperty(value = "试卷名称")
    private String examPaperName;

    /**
     * exam_score
     * 考试得分
     */
    @ApiModelProperty(value = "考试得分")
    private BigDecimal examScore;

    /**
     * exam_level
     * 考试成绩等级
     */
    @ApiModelProperty(value = "考试成绩等级")
    private String examLevel;

    /**
     * user_id
     * 用户主键
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value = "用户主键")
    private Long userId;

    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * idcard
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idcard;
}
