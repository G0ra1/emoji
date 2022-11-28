package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClaimStatusEnum {
    UNCLAIMED(0,"未签收"),
    CLAIMED(1,"已签收");

    private Integer type;
    private String name;
}
