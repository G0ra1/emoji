package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.netwisd.base.portal.dto.PortalStatisticsDto;
import com.netwisd.base.portal.entity.PortalPart;
import com.netwisd.base.portal.entity.PortalPortal;
import com.netwisd.base.portal.service.*;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PortalStatisticsServiceImpl implements PortalStatisticsService {
    //门户
    @Autowired
    private PortalPortalService portalPortalService;
    //栏目
    @Autowired
    private PortalPartService portalPartService;
    //系统集成类内容
    @Autowired
    private PortalContentSysjointsSonService portalContentSysjointsSonService;
    //新闻公告类内容
    @Autowired
    private PortalContentNewsService portalContentNewsService;
    //视频类内容
    @Autowired
    private PortalContentVideosSonService portalContentVideosSonService;
    //图片轮播内容
    @Autowired
    private PortalContentPicturesSonService portalContentPicturesSonService;
    //文件下载内容
    @Autowired
    private PortalContentFilesSonServiceImpl portalContentFilesSonService;
    //图片新闻类内容
    @Autowired
    private PortalContentPicnewsService portalContentPicnewsService;
    //banner类内容
    @Autowired
    private PortalContentBannersService portalContentBannersService;

    /**
     * 门户访问量
     *
     * @return
     */
    @Override
    public List<PortalStatisticsDto> portalPortalHis() {
        //定义 一个  返回的门户名称 + 点击量 的list集合
        List<PortalStatisticsDto> portalStatisticsDtos=new ArrayList<>();

        //首先通过点击量排序获取前五条数据
        LambdaQueryWrapper<PortalPortal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(PortalPortal::getHits);
        queryWrapper.last("limit 5");
        List<PortalPortal> list = portalPortalService.list(queryWrapper);
        if (!list.isEmpty()){
            //将其存如List集合
            for (PortalPortal p : list) {
                PortalStatisticsDto portalStatisticsDto = new PortalStatisticsDto();
                portalStatisticsDto.setName(p.getPortalName());
                portalStatisticsDto.setNumber(p.getHits());
                portalStatisticsDtos.add(portalStatisticsDto);
            }
        }
        if (portalStatisticsDtos.isEmpty()) {
            throw new IncloudException("暂无门户点击量信息");
        }
        return portalStatisticsDtos;
    }

    /**
     * 新闻数量
     *
     * @return
     */
    @Override
    public List<PortalStatisticsDto> portalNewsNumber() {

        List<PortalStatisticsDto> portalStatisticsDtos=new ArrayList<>();

        //文件下载类内容数量
        int filesCount = portalContentFilesSonService.count();
        portalStatisticsDtos.add(new PortalStatisticsDto("文件下载类内容数量",(long)filesCount));
        //图片新闻类内容数量
        int picNewsCount = portalContentPicnewsService.count();
        portalStatisticsDtos.add(new PortalStatisticsDto("图片新闻类内容数量",(long)picNewsCount));
        //图片轮播内容数量
        int picturesCount = portalContentPicturesSonService.count();
        portalStatisticsDtos.add(new PortalStatisticsDto("图片轮播内容数量",(long)picturesCount));
        //系统集成类内容数量
        int sysjointsCount = portalContentSysjointsSonService.count();
        portalStatisticsDtos.add(new PortalStatisticsDto("系统集成类内容数量",(long)sysjointsCount));
        //视频类内容数量
        int videosCount = portalContentVideosSonService.count();
        portalStatisticsDtos.add(new PortalStatisticsDto("视频类内容数量",(long)videosCount));
        //banner类内容数量
        int bannerCount = portalContentBannersService.count();
        portalStatisticsDtos.add(new PortalStatisticsDto("banner类内容数量",(long)bannerCount));
        //新闻公告类内容
        int newsCount = portalContentNewsService.count();
        portalStatisticsDtos.add(new PortalStatisticsDto("新闻公告类内容",(long)newsCount));

        if (portalStatisticsDtos.isEmpty()){
            throw new IncloudException("暂无新闻数据");
        }
        return portalStatisticsDtos;
    }
    /**
     * 栏目点击量
     *
     * @return
     */
    @Override
    public List<PortalStatisticsDto> portalPartHis() {

        List<PortalStatisticsDto> portalStatisticsDtos=new ArrayList<>();

        //首先通过点击量排序获取前五条数据
        LambdaQueryWrapper<PortalPart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(PortalPart::getHits);
        queryWrapper.last("limit 5");
        List<PortalPart> list = portalPartService.list(queryWrapper);
        if(!list.isEmpty()){
            for (PortalPart p : list) {
                PortalStatisticsDto portalStatisticsDto = new PortalStatisticsDto();
                portalStatisticsDto.setName(p.getPartName());
                portalStatisticsDto.setNumber(p.getHits());
                portalStatisticsDtos.add(portalStatisticsDto);
            }
        }
        if (portalStatisticsDtos.isEmpty()) {
            throw new IncloudException("暂无栏目点击量信息");
        }
        return portalStatisticsDtos;
    }

}
