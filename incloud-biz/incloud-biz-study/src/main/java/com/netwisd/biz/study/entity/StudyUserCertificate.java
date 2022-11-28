package com.netwisd.biz.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.netwisd.common.db.anntation.Column;
import com.netwisd.common.db.anntation.Table;
import com.netwisd.common.db.data.DataType;
import com.netwisd.common.db.data.IModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description $人员证书
 * @date 2022-04-25 09:39:13
 */
@Data
@Table(value = "incloud_biz_study_user_certificate", comment = "人员证书")
@TableName("incloud_biz_study_user_certificate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "人员证书Entity")
public class StudyUserCertificate extends IModel<StudyUserCertificate> {

    /**
     * certificate_template_id
     * 证书模板ID
     */
    @ApiModelProperty(value = "证书模板ID")
    @TableField(value = "certificate_template_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "证书模板ID")
    private Long certificateTemplateId;

    /**
     * certificate_name
     * 证书名称
     */
    @ApiModelProperty(value = "证书名称")
    @TableField(value = "certificate_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "证书名称")
    private String certificateName;

    /**
     * certificate_code
     * 证书编号
     */
    @ApiModelProperty(value = "证书编号")
    @TableField(value = "certificate_code")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "证书编号")
    private String certificateCode;

    /**
     * type_code
     * 分类编码
     */
    @ApiModelProperty(value = "分类编码")
    @TableField(value = "type_code")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "分类编码")
    private String typeCode;

    /**
     * type_name
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @TableField(value = "type_name")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "分类名称")
    private String typeName;

    /**
     * issuer
     * 发证机构
     */
    @ApiModelProperty(value = "发证机构")
    @TableField(value = "issuer")
    @Column(type = DataType.VARCHAR, length = 100, isNull = true, comment = "发证机构")
    private String issuer;


    /**
     * issuer
     * 证书描述
     */
    @ApiModelProperty(value = "证书描述")
    @TableField(value = "instructions")
    @Column(type = DataType.VARCHAR, length = 1000, isNull = true, comment = "证书描述")
    private String instructions;

    /**
     * 发证日期
     */
    @ApiModelProperty(value = "issue_date")
    @TableField(value = "issue_date")
    @Column(type = DataType.DATE, length = 0, isNull = true, comment = "发证日期")
    public LocalDate issueDate;


    /**
     * validity
     * 证书有效期 0表示长期
     */
    @ApiModelProperty(value = "证书有效期 0表示长期")
    @TableField(value = "validity")
    @Column(type = DataType.INT, length = 3, isNull = true, comment = "证书有效期 0表示长期")
    private Integer validity;

    /**
     * template_file_path
     * 模板文件存放路径
     */
    @ApiModelProperty(value = "模板文件存放路径")
    @TableField(value = "template_file_path")
    @Column(type = DataType.VARCHAR, length = 255, isNull = true, comment = "模板文件存放路径")
    private String templateFilePath;


    /**
     * special_id
     * 专题ID
     */
    @ApiModelProperty(value = "专题ID")
    @TableField(value = "special_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "专题ID")
    private Long specialId;

    /**
     * special_name
     * 专题名称
     */
    @ApiModelProperty(value = "专题名称")
    @TableField(value = "special_name")
    @Column(type = DataType.VARCHAR, length = 64, isNull = true, comment = "专题名称")
    private String specialName;

    /**
     * exam_paper_id
     * 试卷ID
     */
    @ApiModelProperty(value = "试卷ID")
    @TableField(value = "exam_paper_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "试卷ID")
    private Long examPaperId;

    /**
     * exam_paper_name
     * 试卷名称
     */
    @ApiModelProperty(value = "试卷名称")
    @TableField(value = "exam_paper_name")
    @Column(type = DataType.VARCHAR, length = 64, isNull = true, comment = "试卷名称")
    private String examPaperName;

    /**
     * exam_score
     * 考试得分
     */
    @ApiModelProperty(value = "考试得分")
    @TableField(value = "exam_score")
    @Column(type = DataType.DECIMAL, length = 4, precision = 1, isNull = true, comment = "考试得分")
    private BigDecimal examScore;

    /**
     * exam_level
     * 考试成绩等级
     */
    @ApiModelProperty(value = "考试成绩等级")
    @TableField(value = "exam_level")
    @Column(type = DataType.VARCHAR, length = 64, isNull = true, comment = "考试成绩等级")
    private String examLevel;

    /**
     * user_id
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键")
    @TableField(value = "user_id")
    @Column(type = DataType.BIGINT, length = 20, isNull = true, comment = "用户主键")
    private Long userId;

    /**
     * user_name
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField(value = "user_name")
    @Column(type = DataType.VARCHAR, length = 64, isNull = true, comment = "用户名")
    private String userName;

    /**
     * idcard
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    @TableField(value = "idcard")
    @Column(type = DataType.VARCHAR, length = 50, isNull = true, comment = "身份证号")
    private String idcard;
}
