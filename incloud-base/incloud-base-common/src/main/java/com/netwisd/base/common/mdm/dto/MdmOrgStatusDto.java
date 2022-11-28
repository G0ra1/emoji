package com.netwisd.base.common.mdm.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 云数网讯 zouliming@netwisd.com
 * @Description 组织拆分和撤消
 * @date 2021/9/7 9:52
 */
@Data
@ApiModel(value = "拼装dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MdmOrgStatusDto extends IDto {
    @Valid
    private MdmOrgDto mdmOrgDto;
    private List<MdmPostDto> mdmPostDtos;
    private List<MdmUserDto> mdmUserDtos;
    private List<MdmDutyDto> mdmDutyDtos;
}
