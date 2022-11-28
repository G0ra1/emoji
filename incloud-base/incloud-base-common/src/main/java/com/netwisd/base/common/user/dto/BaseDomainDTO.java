package com.netwisd.base.common.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDomainDTO implements Serializable {

    private static final long serialVersionUID = 2885638467157056218L;

    private Integer current=0;

    private Integer pageSize=0;

    private String id;
}
