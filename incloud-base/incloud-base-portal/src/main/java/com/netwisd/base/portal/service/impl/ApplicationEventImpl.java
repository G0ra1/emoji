package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.netwisd.base.portal.config.PortalEvent;
import com.netwisd.base.portal.constants.EventConstants;
import com.netwisd.base.portal.dto.PortalContentBannersDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.*;
import com.netwisd.base.portal.service.ApplicationEvent;
import com.netwisd.base.portal.service.PortalContentNewsService;
import com.netwisd.base.portal.util.MakeHtml;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.JacksonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ApplicationEventImpl implements ApplicationEvent {

    @Value("${portal.filePath}")
    private String rootFilePath;

    @Value("${portal.fileIp}")
    private String portalFileIp;

    @Autowired
    PortalContentNewsMapper portalContentNewsMapper;

    @Autowired
    PortalContentNewsService portalContentNewsService;

    @Autowired
    private PortalContentBannersMapper portalContentBannersMapper;

    @Autowired
    private PortalContentPicnewsMapper portalContentPicnewsMapper;

    @Autowired
    private PortalContentPicturesSonMapper portalContentPicturesSonMapper;

    @Autowired
    private PortalContentPicturesMapper portalContentPicturesMapper;

    @Override
    @EventListener(PortalEvent.class)
    @Async("incloudExecutor")
    @SneakyThrows
    public void onApplicationEvent(PortalEvent portalEvent) {
        Map<String, Object> data = portalEvent.getData();
        if(data.containsKey(EventConstants.NewsToHtmlFileHandle)){
            PortalContentNews portalContentNews = (PortalContentNews)data.get(EventConstants.NewsToHtmlFileHandle);
            log.info("新闻获取静态化参数：{}", JacksonUtil.toJSONString(portalContentNews));
            this.newsToHtmlFileHandle(portalContentNews);
        }
        if (data.containsKey(EventConstants.BannerToHtmlFileHandle)) {
            PortalContentBanners portalContentBanners = (PortalContentBanners)data.get(EventConstants.BannerToHtmlFileHandle);
            log.info("Banner获取静态化参数:{}",JacksonUtil.toJSONString(portalContentBanners));
            this.bannerToHtmlFileHandle(portalContentBanners);
        }
        if (data.containsKey(EventConstants.PicnewsToHtmlFileHandle)) {
            PortalContentPicnews portalContentPicnews = (PortalContentPicnews)data.get(EventConstants.PicnewsToHtmlFileHandle);
            log.info("图片新闻获取静态化参数:{}",JacksonUtil.toJSONString(portalContentPicnews));
            this.picnewsToHtmlFileHandle(portalContentPicnews);
        }
        if (data.containsKey(EventConstants.PicturesToHtmlFileHandle)) {
            PortalContentPicturesSon portalContentPicturesSon = (PortalContentPicturesSon)data.get(EventConstants.PicturesToHtmlFileHandle);
            log.info("图片轮播获取静态化参数:{}",JacksonUtil.toJSONString(portalContentPicturesSon));
            this.picturesToHtmlFileHandle(portalContentPicturesSon);
        }
    }

    @Override
    public boolean newsToHtmlFileHandle(PortalContentNews portalContentNews) {
        checkFiltPath();
        //获取富文本框内容
        String ueditorContent = portalContentNews.getUeditorContent();
        String html = "<div style=\"width: 1200px; margin: 0px auto;\">\n" +
                "    <h2 style=\"text-align: center; margin: 0px;\">"+portalContentNews.getTitle()+"</h2>\n" +
                "    <p style=\"text-align: right; margin: 0px; padding: 0px 30px;font-size: 14px;color: #666;\">作者: "+portalContentNews.getCreateUserName()+" 发布时间："+portalContentNews.getCreateTime()+"</p >\n" +
                ueditorContent
                +"</div>";
        //生成文件 服务路径
        String filePath = MakeHtml.buildFilePath(portalContentNews.getTitle(),portalContentNews.getPortalId(),portalContentNews.getPartId());
        log.debug("生成文件服务器的地址为：" + filePath);
        //写入服务磁盘
        log.debug("写入服务磁盘地址为：" + rootFilePath + filePath);
        MakeHtml.toHtmlFile(html,rootFilePath + filePath);
        //拼接nginx 访问路径
        String buildHttpUrl =MakeHtml.buildHttpUrl(portalFileIp,filePath);
        portalContentNews.setContentUrl(buildHttpUrl);
        //修改这条记录
        int line = portalContentNewsMapper.updateById(portalContentNews);
        return line > 0;
    }
    public void checkFiltPath() {
        //定义生成静态化文件路径
        if(StringUtils.isBlank(rootFilePath)) {
            throw new IncloudException("生成的静态文件路径为空！");
        }
        if(StringUtils.isBlank(portalFileIp)) {
            throw new IncloudException("生成的静态文件IP为空！");
        }
    }

    @Override
    public boolean bannerToHtmlFileHandle(PortalContentBanners portalContentBanners) {
        checkFiltPath();
        //获取富文本框内容
        String ueditorContent = portalContentBanners.getUeditorContent();
        String html = "<div style=\"width: 1200px; margin: 0px auto;\">\n" +
                "    <h2 style=\"text-align: center; margin: 0px;\">"+portalContentBanners.getTitle()+"</h2>\n" +
                "    <p style=\"text-align: right; margin: 0px; padding: 0px 30px;font-size: 14px;color: #666;\">作者: "+portalContentBanners.getCreateUserName()+" 发布时间："+portalContentBanners.getCreateTime()+"</p >\n" +
                ueditorContent
                +"</div>";
        //生成文件 服务路径
        String filePath = MakeHtml.buildFilePath(portalContentBanners.getTitle(),portalContentBanners.getPortalId(),portalContentBanners.getPartId());
        log.debug("生成文件服务器的地址为：" + filePath);
        //写入服务磁盘
        log.debug("写入服务磁盘地址为：" + rootFilePath + filePath);
        MakeHtml.toHtmlFile(html,rootFilePath + filePath);
        //拼接nginx 访问路径
        String buildHttpUrl =MakeHtml.buildHttpUrl(portalFileIp,filePath);
        portalContentBanners.setContentUrl(buildHttpUrl);
        //修改这条记录
        int line = portalContentBannersMapper.updateById(portalContentBanners);
        return line > 0;
    }

    @Override
    public boolean picnewsToHtmlFileHandle(PortalContentPicnews portalContentPicnews) {
        checkFiltPath();
        //获取富文本框内容
        String ueditorContent = portalContentPicnews.getUeditorContent();
        String html = "<div style=\"width: 1200px; margin: 0px auto;\">\n" +
                "    <h2 style=\"text-align: center; margin: 0px;\">"+portalContentPicnews.getTitle()+"</h2>\n" +
                "    <p style=\"text-align: right; margin: 0px; padding: 0px 30px;font-size: 14px;color: #666;\">作者: "+portalContentPicnews.getCreateUserName()+" 发布时间："+portalContentPicnews.getCreateTime()+"</p >\n" +
                ueditorContent
                +"</div>";
        //生成文件 服务路径
        String filePath = MakeHtml.buildFilePath(portalContentPicnews.getTitle(),portalContentPicnews.getPortalId(),portalContentPicnews.getPartId());
        log.debug("生成文件服务器的地址为：" + filePath);
        //写入服务磁盘
        log.debug("写入服务磁盘地址为：" + rootFilePath + filePath);
        MakeHtml.toHtmlFile(html,rootFilePath + filePath);
        //拼接nginx 访问路径
        String buildHttpUrl =MakeHtml.buildHttpUrl(portalFileIp,filePath);
        portalContentPicnews.setContentUrl(buildHttpUrl);
        //修改这条记录
        int line = portalContentPicnewsMapper.updateById(portalContentPicnews);
        return line > 0;
    }

    @Override
    public boolean picturesToHtmlFileHandle(PortalContentPicturesSon portalContentPicturesSon) {
        checkFiltPath();
        //获取富文本框内容
        String ueditorContent = portalContentPicturesSon.getUeditorContent();
        String html = "<div style=\"width: 1200px; margin: 0px auto;\">\n" +
                "    <h2 style=\"text-align: center; margin: 0px;\">"+portalContentPicturesSon.getTitle()+"</h2>\n" +
                "    <p style=\"text-align: right; margin: 0px; padding: 0px 30px;font-size: 14px;color: #666;\">作者: "+portalContentPicturesSon.getCreateUserName()+" 发布时间："+portalContentPicturesSon.getCreateTime()+"</p >\n" +
                ueditorContent
                +"</div>";
        //查询图片轮播主表
        LambdaQueryWrapper<PortalContentPictures> picturesWrapper = new LambdaQueryWrapper<>();
        picturesWrapper.eq(PortalContentPictures::getId,portalContentPicturesSon.getLinkPicturesId());
        PortalContentPictures portalContentPictures = portalContentPicturesMapper.selectOne(picturesWrapper);
        //生成文件 服务路径
        String filePath = MakeHtml.buildFilePath(portalContentPicturesSon.getTitle(),portalContentPictures.getPortalId(),portalContentPictures.getPartId());
        log.debug("生成文件服务器的地址为：" + filePath);
        //写入服务磁盘
        log.debug("写入服务磁盘地址为：" + rootFilePath + filePath);
        MakeHtml.toHtmlFile(html,rootFilePath + filePath);
        //拼接nginx 访问路径
        String buildHttpUrl =MakeHtml.buildHttpUrl(portalFileIp,filePath);
        portalContentPicturesSon.setContentUrl(buildHttpUrl);
        //修改这条记录
        int line = portalContentPicturesSonMapper.updateById(portalContentPicturesSon);
        return line > 0;
    }
}
