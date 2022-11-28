package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentHisVideos;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentHisVideosDto;
import com.netwisd.base.portal.vo.PortalContentHisVideosVo;
/**
 * @Description  视频类内容发布-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 02:45:42
 */
public interface PortalContentHisVideosService extends IService<PortalContentHisVideos> {
    Page list(PortalContentHisVideosDto portalContentHisVideosDto);
    Page lists(PortalContentHisVideosDto portalContentHisVideosDto);
    PortalContentHisVideosVo get(Long id);
    Boolean save(PortalContentHisVideosDto portalContentHisVideosDto);
    Boolean update(PortalContentHisVideosDto portalContentHisVideosDto);
    Boolean delete(Long id);
}
