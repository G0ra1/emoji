package com.netwisd.base.common.mdm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationVo implements Serializable {
    private Boolean authenticated;
    private MdmUserVo mdmUserVo;
}
