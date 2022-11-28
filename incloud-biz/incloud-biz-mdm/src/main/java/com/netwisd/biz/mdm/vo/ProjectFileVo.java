package com.netwisd.biz.mdm.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netwisd.common.core.util.IdTypeDeserializer;
import com.netwisd.common.core.util.IdTypeSerializer;
import com.netwisd.common.core.data.IVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description 项目附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 14:44:47
 */
@Data
@ApiModel(value = "项目附件 Vo")
public class ProjectFileVo extends IVo{

    /**
     * project_id
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Long projectId;
    /**
     * project_code
     * 项目编码
     */
    @ApiModelProperty(value="项目编码")
    private String projectCode;
    /**
     * file_name
     * 附件名称
     */
    @ApiModelProperty(value="附件名称")
    private String fileName;
    /**
     * file_add
     * 附件地址
     */
    @ApiModelProperty(value="附件地址")
    private String fileUrl;
}
