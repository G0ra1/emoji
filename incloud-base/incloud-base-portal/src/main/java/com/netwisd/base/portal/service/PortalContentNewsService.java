package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.entity.PortalContentNews;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalContentNewsDto;
import com.netwisd.base.portal.vo.PortalContentNewsVo;

import java.util.List;

/**
 * @Description 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-16 17:58:47
 */
public interface PortalContentNewsService extends IService<PortalContentNews> {
    Page list(PortalContentNewsDto portalContentNewsDto);
    List<PortalContentNewsVo> lists(PortalContentNewsDto portalContentNewsDto);
    Page<PortalContentNewsVo> findForMobile(PortalContentNewsDto portalContentNewsDto);
    PortalContentNewsVo get(Long id);
    Boolean save(PortalContentNewsDto portalContentNewsDto);
    Boolean update(PortalContentNewsDto portalContentNewsDto);
    Boolean delete(String ids);

    //增加点击量
    Boolean addHits(Long id);

    Boolean saveNews(PortalContentNewsDto newsDto);
    Boolean updateNews(PortalContentNewsDto newsDto);
    Boolean deleteNews(String oaId,String partCode);

    void updateCreateUserMsg();
//    //流程删除
//    Result procDel(String camundaProcinsId);
//    //流程中止
//    Result procStop(String camundaProcinsId);
//    //流程审批完后
//    Result auditSucceed(String camundaProcinsId);

}
