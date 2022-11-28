package com.netwisd.biz.mdm.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.biz.mdm.constants.YesNo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;

/**
 * @Description 物资数据传送 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Data
@ApiModel(value = "物资数据传送 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemTransmit extends IDto{

    private List<ItemTransmitDetailDto> data;
}
