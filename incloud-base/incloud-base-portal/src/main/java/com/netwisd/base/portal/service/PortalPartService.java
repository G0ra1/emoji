package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalPart;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalPartDto;
import com.netwisd.base.portal.vo.PortalPartVo;

import java.util.List;

/**
 * @Description 栏目管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 19:27:46
 */
public interface PortalPartService extends IService<PortalPart> {
    Page list(PortalPartDto portalPartDto);
    List<PortalPartVo> lists(PortalPartDto portalPartDto);
    PortalPartVo get(Long id);
    Boolean save(PortalPartDto portalPartDto);
    Boolean update(PortalPartDto portalPartDto);
    Boolean delete(Long id);
    //通过id修改点击量
    Boolean upHits(Long id);
}
