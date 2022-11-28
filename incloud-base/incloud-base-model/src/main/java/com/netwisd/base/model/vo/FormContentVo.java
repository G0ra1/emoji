package com.netwisd.base.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormContentVo {

    private String version;

    private List<FormFileDirVo> fileDir;

}
