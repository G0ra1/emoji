package com.netwisd.base.portal.service;

import com.netwisd.base.portal.config.PortalEvent;
import com.netwisd.base.portal.dto.PortalContentBannersDto;
import com.netwisd.base.portal.dto.PortalContentPicnewsDto;
import com.netwisd.base.portal.entity.*;

public interface ApplicationEvent {

    public void onApplicationEvent(PortalEvent portalEvent);

    boolean newsToHtmlFileHandle(PortalContentNews portalContentNews);

    boolean bannerToHtmlFileHandle(PortalContentBanners portalContentBanners);

    boolean picnewsToHtmlFileHandle(PortalContentPicnews portalContentPicnews);

    boolean picturesToHtmlFileHandle(PortalContentPicturesSon portalContentPicturesSon);
}
