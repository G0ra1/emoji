package com.netwisd.biz.study.controller;

import com.netwisd.biz.study.dto.*;
import com.netwisd.biz.study.service.StudyHomeBannerService;
import com.netwisd.biz.study.service.StudyNewsService;
import com.netwisd.biz.study.service.StudyXyIndexService;
import com.netwisd.biz.study.vo.*;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 学员端首页Controller
 * @date 2022-03-15 16:37:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/xueyuan/index")
@Api(value = "/xueyuan/index", tags = "学员端首页Controller")
@Slf4j
public class StudyXyIndexController {

    @Autowired
    private StudyHomeBannerService studyHomeBannerService;

    @Autowired
    private StudyNewsService studyNewsService;

    @Autowired
    private StudyXyIndexService studyXyIndexService;

    /**
     * 首页轮播图
     * @param studyHomeBannerDto 在线学习轮播图表
     * @return
     */
    @ApiOperation(value = "首页轮播图", notes = "首页轮播图")
    @PostMapping("/getHomeBanners" )
    public Result<List<StudyHomeBannerVo>> getHomeBanners(@RequestBody StudyHomeBannerDto studyHomeBannerDto) {
        List<StudyHomeBannerVo> pageVo = studyHomeBannerService.lists(studyHomeBannerDto);
        log.debug("查询条数:"+pageVo.size());
        return Result.success(pageVo);
    }

    /**
     * 首页通知公告
     * @param studyNewsDto 在线学习通知公告表
     * @return
     */
    @ApiOperation(value = "首页通知公告", notes = "首页通知公告")
    @PostMapping("/getNews" )
    public Result<List<StudyNewsVo>> getNews(@RequestBody StudyNewsDto studyNewsDto) {
        List<StudyNewsVo> pageVo = studyNewsService.lists(studyNewsDto);
        log.debug("查询条数:"+pageVo.size());
        return Result.success(pageVo);
    }

    /**
     * 首页热门课程
     * @return
     */
    @ApiOperation(value = "首页热门课程", notes = "首页热门课程")
    @GetMapping("/getLessons" )
    public Result<List<StudyLessonVo>> getLessons(@RequestParam(value = "current", required = false) Integer current,
                                                  @RequestParam(value = "size", required = false) Integer size,
                                                  @RequestParam(value = "lessonName", required = false) String lessonName) {
        List<StudyLessonVo> lessonVos = studyXyIndexService.getLessons(current,size,lessonName);
        return Result.success(lessonVos);
    }

    /**
     * 首页热门专题
     * @return
     */
    @ApiOperation(value = "首页热门专题", notes = "首页热门专题")
    @GetMapping("/getSpecials" )
    public Result<List<StudySpecialVo>> getSpecials(@RequestParam(value = "current", required = false) Integer current,
                                                    @RequestParam(value = "size", required = false) Integer size,
                                                    @RequestParam(value = "specialName", required = false) String specialName) {
        List<StudySpecialVo> specialVos = studyXyIndexService.getSpecials(current,size,specialName);
        return Result.success(specialVos);
    }

    /**
     * 首页课程排行榜
     * @return
     */
    @ApiOperation(value = "首页课程排行榜", notes = "首页课程排行榜")
    @GetMapping("/getLessonRanking" )
    public Result<Map<String,Object>> getLessonRanking(@RequestParam Integer size) {
        Map<String,Object> lessonRankingVos = studyXyIndexService.getLessonOrSpecialRanking(size,"lesson");
        return Result.success(lessonRankingVos);
    }

    /**
     * 首页专题排行榜
     * @return
     */
    @ApiOperation(value = "首页专题排行榜", notes = "首页专题排行榜")
    @GetMapping("/getSpecialRanking" )
    public Result<Map<String,Object>> getSpecialRanking(@RequestParam Integer size) {
        Map<String,Object> lessonRankingVos = studyXyIndexService.getLessonOrSpecialRanking(size,"special");
        return Result.success(lessonRankingVos);
    }

    /**
     * 首页学时排行榜
     * @return
     */
    @ApiOperation(value = "首页学时排行榜", notes = "首页学时排行榜")
    @GetMapping("/getStudyTimeRanking" )
    public Result<Map<String,Object>> getStudyTimeRanking(@RequestParam Integer size) {
        Map<String,Object> studyTimeRankingVos = studyXyIndexService.getStudyTimeRanking(size);
        return Result.success(studyTimeRankingVos);
    }

}
