package com.netwisd.base.portal.service;

import com.netwisd.base.portal.dto.PortalStatisticsDto;

import java.util.List;
import java.util.Map;

public interface PortalStatisticsService {
    /**
     * 门户访问量
     */
   List<PortalStatisticsDto> portalPortalHis();

    /**
     * 新闻数量
     *
     * @return
     */
    List<PortalStatisticsDto> portalNewsNumber();
    /**
     * 栏目点击量
     *
     * @return
     */
    List<PortalStatisticsDto> portalPartHis();


}
