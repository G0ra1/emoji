package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentLoginBackgroundSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentLoginBackgroundSonDto;
import com.netwisd.base.portal.vo.PortalContentLoginBackgroundSonVo;

import java.util.List;

/**
 * @Description 登录页-子表（背景轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:03:27
 */
public interface PortalContentLoginBackgroundSonService extends IService<PortalContentLoginBackgroundSon> {
    Page getList(PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto);
    Page lists(PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto);
    PortalContentLoginBackgroundSonVo get(Long id);
    Boolean save(PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto);
    Boolean update(PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto);
    Boolean delete(Long id);
    //通过关联id查背景轮播图所有信息
    List<PortalContentLoginBackgroundSonVo> getListOrLogId(Long logId);
    //通过关联id删除所有当前参数的所有背景轮播图
    Boolean deleteOrLogId(Long logId);

}
