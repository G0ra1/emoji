package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentLogin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentLoginDto;
import com.netwisd.base.portal.vo.PortalContentLoginVo;

import java.util.List;

/**
 * @Description 登录页设置表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 16:36:19
 */
public interface PortalContentLoginService extends IService<PortalContentLogin> {
    Page list(PortalContentLoginDto portalContentLoginDto);
    Page lists(PortalContentLoginDto portalContentLoginDto);
    PortalContentLoginVo get(Long id);
    Boolean save(PortalContentLoginDto portalContentLoginDto);
    Boolean update(PortalContentLoginDto portalContentLoginDto);
    Boolean delete(Long id);
    //进入登录页时使用
    PortalContentLoginVo getLog();
    //设置为启用
    Boolean isEnable(Long id);

}
