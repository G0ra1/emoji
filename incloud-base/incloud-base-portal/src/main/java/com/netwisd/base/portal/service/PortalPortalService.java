package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalPortal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalPortalDto;
import com.netwisd.base.portal.vo.PortalEnableDataVo;
import com.netwisd.base.portal.vo.PortalPortalVo;
import com.netwisd.base.portal.vo.PortalTemplateVo;

import java.util.List;

/**
 * @Description 门户维护 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-11 09:50:22
 */
public interface PortalPortalService extends IService<PortalPortal> {
    Page list(PortalPortalDto portalPortalDto);
    List<PortalPortalVo> lists();
    PortalPortalVo get(Long id);
    Boolean save(PortalPortalDto portalPortalDto);
    Boolean update(PortalPortalDto portalPortalDto);
    Boolean delete(Long id);
    Boolean homePage(Long id );
    //通过id修改点击量
    Boolean upHits(Long id);
    //通过门户id和所属终端查询当前门户下启用的模板内容
    PortalTemplateVo findTemplateByPortalId(Long portalId,Integer terminal);
    //获取默认门户首页
    PortalPortalVo getDefaultPortal();

    //通过生效门户 获取当前门户下模板；获取当前生效主题
    PortalEnableDataVo getEnableData();
}
