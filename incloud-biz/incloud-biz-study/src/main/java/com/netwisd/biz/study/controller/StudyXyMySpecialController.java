package com.netwisd.biz.study.controller;

import com.netwisd.biz.study.dto.StudySpecialDto;
import com.netwisd.biz.study.service.StudySpecialService;
import com.netwisd.biz.study.vo.StudyMySpecialVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 学员端课程Controller
 * @date 2022-03-15 16:37:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/xueyuan/mySpecial")
@Api(value = "/xueyuan/mySpecial", tags = "学员端我的专题Controller")
@Slf4j
public class StudyXyMySpecialController {

    @Autowired
    private StudySpecialService studySpecialService;

    /**
     * 我的专题-列表展示
     * @param specialDto 专题
     * @return
     */
    @ApiOperation(value = "我的专题-列表展示", notes = "我的专题-列表展示")
    @PostMapping("/list" )
    public Result<StudyMySpecialVo> list(@RequestBody StudySpecialDto specialDto) {
        StudyMySpecialVo mySpecial = studySpecialService.findMySpecial(specialDto);
        return Result.success(mySpecial);
    }
}


