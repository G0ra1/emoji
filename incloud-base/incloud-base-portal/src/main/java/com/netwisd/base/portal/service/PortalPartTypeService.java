package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalPartType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalPartTypeDto;
import com.netwisd.base.portal.vo.PortalPartTypeVo;

import java.util.List;

/**
 * @Description 栏目类型字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-12 17:20:18
 */
public interface PortalPartTypeService extends IService<PortalPartType> {
    Page list(PortalPartTypeDto portalPartTypeDto);
    List<PortalPartTypeVo> lists();
    PortalPartTypeVo get(Long id);
    Boolean save(PortalPartTypeDto portalPartTypeDto);
    Boolean update(PortalPartTypeDto portalPartTypeDto);
    Boolean delete(Long id);
}
