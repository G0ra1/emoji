package com.netwisd.biz.study.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudyMySpecialVo {
    private Integer todoSpecialNum;//待学习数量
    private List<StudySpecialVo> mySpecialList;
}
