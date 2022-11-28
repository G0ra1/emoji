package com.netwisd.base.common.model.dto;

import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@ApiModel(value = "消息数据源 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DbDsDto extends IDto {

    private String dbDsId;

    private String dbds;

    private String sql;
}
