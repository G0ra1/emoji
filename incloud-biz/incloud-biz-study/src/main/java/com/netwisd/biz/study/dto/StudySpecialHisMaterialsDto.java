package com.netwisd.biz.study.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.constants.Args;
import com.netwisd.base.common.data.IDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.Date;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import com.netwisd.common.db.anntation.Fk;
import com.netwisd.common.db.anntation.Map;
/**
 * @Description 专题历史与学习资料表 功能描述...
 * @author 云数网讯 cr@netwisd.com
 * @date 2022-05-13 14:23:33
 */
@Data
@Map("incloud_biz_study_special_his_materials")
@ApiModel(value = "专题历史与学习资料表 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudySpecialHisMaterialsDto extends IDto{

    public StudySpecialHisMaterialsDto(Args args){
        super(args);
    }
    /**
     * special_id
     * 计划id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @Fk(table = "incloud_biz_study_special_his" ,field = "id")
    @ApiModelProperty(value="计划id")
    private Long specialId;
    /**
     * materials_id
     * 资料id
     */
    @JsonDeserialize(using = IdTypeDeserializer.class)
    @ApiModelProperty(value="资料id")
    private Long materialsId;
    /**
     * materials_name
     * 资料名称
     */
    @ApiModelProperty(value="资料名称")
    private String materialsName;
    /**
     * materials_type_code
     * 资料分类编码
     */
    @ApiModelProperty(value="资料分类编码")
    private String materialsTypeCode;
    /**
     * materials_type_name
     * 资料分类名称
     */
    @ApiModelProperty(value="资料分类名称")
    private String materialsTypeName;
    /**
     * is_download
     * 是否允许下载
     */
    @ApiModelProperty(value="是否允许下载")
    private Integer isDownload;



}
