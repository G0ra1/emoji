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
            log.info("??????????????????????????????{}", JacksonUtil.toJSONString(portalContentNews));
            this.newsToHtmlFileHandle(portalContentNews);
        }
        if (data.containsKey(EventConstants.BannerToHtmlFileHandle)) {
            PortalContentBanners portalContentBanners = (PortalContentBanners)data.get(EventConstants.BannerToHtmlFileHandle);
            log.info("Banner?????????????????????:{}",JacksonUtil.toJSONString(portalContentBanners));
            this.bannerToHtmlFileHandle(portalContentBanners);
        }
        if (data.containsKey(EventConstants.PicnewsToHtmlFileHandle)) {
            PortalContentPicnews portalContentPicnews = (PortalContentPicnews)data.get(EventConstants.PicnewsToHtmlFileHandle);
            log.info("?????????????????????????????????:{}",JacksonUtil.toJSONString(portalContentPicnews));
            this.picnewsToHtmlFileHandle(portalContentPicnews);
        }
        if (data.containsKey(EventConstants.PicturesToHtmlFileHandle)) {
            PortalContentPicturesSon portalContentPicturesSon = (PortalContentPicturesSon)data.get(EventConstants.PicturesToHtmlFileHandle);
            log.info("?????????????????????????????????:{}",JacksonUtil.toJSONString(portalContentPicturesSon));
            this.picturesToHtmlFileHandle(portalContentPicturesSon);
        }
    }

    @Override
    public boolean newsToHtmlFileHandle(PortalContentNews portalContentNews) {
        checkFiltPath();
        //????????????????????????
        String ueditorContent = portalContentNews.getUeditorContent();
        String html = "<div style=\"width: 1200px; margin: 0px auto;\">\n" +
                "    <h2 style=\"text-align: center; margin: 0px;\">"+portalContentNews.getTitle()+"</h2>\n" +
                "    <p style=\"text-align: right; margin: 0px; padding: 0px 30px;font-size: 14px;color: #666;\">??????: "+portalContentNews.getCreateUserName()+" ???????????????"+portalContentNews.getCreateTime()+"</p >\n" +
                ueditorContent
                +"</div>";
        //???????????? ????????????
        String filePath = MakeHtml.buildFilePath(portalContentNews.getTitle(),portalContentNews.getPortalId(),portalContentNews.getPartId());
        log.debug("????????????????????????????????????" + filePath);
        //??????????????????
        log.debug("??????????????????????????????" + rootFilePath + filePath);
        MakeHtml.toHtmlFile(html,rootFilePath + filePath);
        //??????nginx ????????????
        String buildHttpUrl =MakeHtml.buildHttpUrl(portalFileIp,filePath);
        portalContentNews.setContentUrl(buildHttpUrl);
        //??????????????????
        int line = portalContentNewsMapper.updateById(portalContentNews);
        return line > 0;
    }
    public void checkFiltPath() {
        //?????????????????????????????????
        if(StringUtils.isBlank(rootFilePath)) {
            throw new IncloudException("????????????????????????????????????");
        }
        if(StringUtils.isBlank(portalFileIp)) {
            throw new IncloudException("?????????????????????IP?????????");
        }
    }

    @Override
    public boolean bannerToHtmlFileHandle(PortalContentBanners portalContentBanners) {
        checkFiltPath();
        //????????????????????????
        String ueditorContent = portalContentBanners.getUeditorContent();
        String html = "<div style=\"width: 1200px; margin: 0px auto;\">\n" +
                "    <h2 style=\"text-align: center; margin: 0px;\">"+portalContentBanners.getTitle()+"</h2>\n" +
                "    <p style=\"text-align: right; margin: 0px; padding: 0px 30px;font-size: 14px;color: #666;\">??????: "+portalContentBanners.getCreateUserName()+" ???????????????"+portalContentBanners.getCreateTime()+"</p >\n" +
                ueditorContent
                +"</div>";
        //???????????? ????????????
        String filePath = MakeHtml.buildFilePath(portalContentBanners.getTitle(),portalContentBanners.getPortalId(),portalContentBanners.getPartId());
        log.debug("????????????????????????????????????" + filePath);
        //??????????????????
        log.debug("??????????????????????????????" + rootFilePath + filePath);
        MakeHtml.toHtmlFile(html,rootFilePath + filePath);
        //??????nginx ????????????
        String buildHttpUrl =MakeHtml.buildHttpUrl(portalFileIp,filePath);
        portalContentBanners.setContentUrl(buildHttpUrl);
        //??????????????????
        int line = portalContentBannersMapper.updateById(portalContentBanners);
        return line > 0;
    }

    @Override
    public boolean picnewsToHtmlFileHandle(PortalContentPicnews portalContentPicnews) {
        checkFiltPath();
        //????????????????????????
        String ueditorContent = portalContentPicnews.getUeditorContent();
        String html = "<div style=\"width: 1200px; margin: 0px auto;\">\n" +
                "    <h2 style=\"text-align: center; margin: 0px;\">"+portalContentPicnews.getTitle()+"</h2>\n" +
                "    <p style=\"text-align: right; margin: 0px; padding: 0px 30px;font-size: 14px;color: #666;\">??????: "+portalContentPicnews.getCreateUserName()+" ???????????????"+portalContentPicnews.getCreateTime()+"</p >\n" +
                ueditorContent
                +"</div>";
        //???????????? ????????????
        String filePath = MakeHtml.buildFilePath(portalContentPicnews.getTitle(),portalContentPicnews.getPortalId(),portalContentPicnews.getPartId());
        log.debug("????????????????????????????????????" + filePath);
        //??????????????????
        log.debug("??????????????????????????????" + rootFilePath + filePath);
        MakeHtml.toHtmlFile(html,rootFilePath + filePath);
        //??????nginx ????????????
        String buildHttpUrl =MakeHtml.buildHttpUrl(portalFileIp,filePath);
        portalContentPicnews.setContentUrl(buildHttpUrl);
        //??????????????????
        int line = portalContentPicnewsMapper.updateById(portalContentPicnews);
        return line > 0;
    }

    @Override
    public boolean picturesToHtmlFileHandle(PortalContentPicturesSon portalContentPicturesSon) {
        checkFiltPath();
        //????????????????????????
        String ueditorContent = portalContentPicturesSon.getUeditorContent();
        String html = "<div style=\"width: 1200px; margin: 0px auto;\">\n" +
                "    <h2 style=\"text-align: center; margin: 0px;\">"+portalContentPicturesSon.getTitle()+"</h2>\n" +
                "    <p style=\"text-align: right; margin: 0px; padding: 0px 30px;font-size: 14px;color: #666;\">??????: "+portalContentPicturesSon.getCreateUserName()+" ???????????????"+portalContentPicturesSon.getCreateTime()+"</p >\n" +
                ueditorContent
                +"</div>";
        //????????????????????????
        LambdaQueryWrapper<PortalContentPictures> picturesWrapper = new LambdaQueryWrapper<>();
        picturesWrapper.eq(PortalContentPictures::getId,portalContentPicturesSon.getLinkPicturesId());
        PortalContentPictures portalContentPictures = portalContentPicturesMapper.selectOne(picturesWrapper);
        //???????????? ????????????
        String filePath = MakeHtml.buildFilePath(portalContentPicturesSon.getTitle(),portalContentPictures.getPortalId(),portalContentPictures.getPartId());
        log.debug("????????????????????????????????????" + filePath);
        //??????????????????
        log.debug("??????????????????????????????" + rootFilePath + filePath);
        MakeHtml.toHtmlFile(html,rootFilePath + filePath);
        //??????nginx ????????????
        String buildHttpUrl =MakeHtml.buildHttpUrl(portalFileIp,filePath);
        portalContentPicturesSon.setContentUrl(buildHttpUrl);
        //??????????????????
        int line = portalContentPicturesSonMapper.updateById(portalContentPicturesSon);
        return line > 0;
    }
}
