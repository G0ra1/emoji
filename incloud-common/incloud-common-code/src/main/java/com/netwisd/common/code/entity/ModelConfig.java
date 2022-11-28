package com.netwisd.common.code.entity;

import com.netwisd.common.code.constants.ModelPropertyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zouliming@netwisd.com
 * @Description:
 * @date 2021/11/9 16:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelConfig {
    private ModelPropertyEnum modelPropertyEnum;
    private EntityConfig entityConfig;
    private String templatePacket;
}
