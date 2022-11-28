package com.netwisd.base.file.vo;

import lombok.Data;

@Data
public class FileInfoVoForVideo extends FileInfoVo {
    //时长展示
    private String durationText;
    //时长毫秒
    private Long millisecond;
    //时长秒
    private Long second;
}
