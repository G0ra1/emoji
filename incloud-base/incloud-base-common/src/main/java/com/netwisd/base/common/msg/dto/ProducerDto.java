package com.netwisd.base.common.msg.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "生产者 Dto")
@AllArgsConstructor
@NoArgsConstructor
public class ProducerDto {

    private Long producerId;

    private String tmpParams;

}
