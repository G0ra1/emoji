package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentLoginCorporateSon;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentLoginCorporateSonDto;
import com.netwisd.base.portal.vo.PortalContentLoginCorporateSonVo;

import java.util.List;

/**
 * @Description 登录页-子表（企业文化轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:22:50
 */
public interface PortalContentLoginCorporateSonService extends IService<PortalContentLoginCorporateSon> {
    Page list(PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto);
    Page lists(PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto);
    PortalContentLoginCorporateSonVo get(Long id);
    Boolean save(PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto);
    Boolean update(PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto);
    Boolean delete(Long id);
    //通过关联id查所有企业文化轮播图集合
    List<PortalContentLoginCorporateSonVo> getListOrLogId(Long logId);
    //通过关联id删除所有当前参数的所有企业文化轮播图
    Boolean deleteOrLogId(Long logId);
}
