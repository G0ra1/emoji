package com.netwisd.base.portal.controller;

import com.netwisd.base.portal.dto.PortalStatisticsDto;
import com.netwisd.base.portal.service.PortalStatisticsService;
import com.netwisd.base.portal.vo.PortalPortalVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/PortalStatistics" )
@Api(value = "PortalStatistics", tags = "统计信息Controller")
@Slf4j
public class PortalStatisticsController {

    @Autowired
    private PortalStatisticsService portalStatisticsService;
    /**
     * 门户访问量
     * @param
     * @return
     */
    @ApiOperation(value = "门户访问量", notes = "门户访问量")
    @PostMapping("/portalPortalHis" )
    public Result<List<PortalStatisticsDto>> portalPortalHis() {
        List<PortalStatisticsDto> portalStatisticsDtoList = portalStatisticsService.portalPortalHis();
        log.debug("查询条数:"+portalStatisticsDtoList.size());
        return Result.success(portalStatisticsDtoList);
    }
    /**
     * 新闻数量
     *
     * @return
     */
    @ApiOperation(value = "新闻数量", notes = "新闻数量")
    @PostMapping("/portalNewsNumber" )
    public Result<List<PortalStatisticsDto>> portalNewsNumber() {
        List<PortalStatisticsDto> portalStatisticsDtoList = portalStatisticsService.portalNewsNumber();
        log.debug("查询条数:"+portalStatisticsDtoList.size());
        return Result.success(portalStatisticsDtoList);
    }
    /**
     * 栏目点击量
     * @param
     * @return
     */
    @ApiOperation(value = "栏目点击量", notes = "栏目点击量")
    @PostMapping("/portalPartHis" )
    public Result<List<PortalStatisticsDto>> portalPartHis() {
        List<PortalStatisticsDto> portalStatisticsDtos = portalStatisticsService.portalPartHis();
        log.debug("查询条数:"+portalStatisticsDtos.size());
        return Result.success(portalStatisticsDtos);
    }
}
