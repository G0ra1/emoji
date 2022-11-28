package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.Viewer;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.ViewerDto;
import com.netwisd.biz.asset.vo.ViewerVo;
/**
 * @Description 物资数据权限人员表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
public interface ViewerService extends BatchService<Viewer> {
    Page list(ViewerDto viewerDto);
    Page lists(ViewerDto viewerDto);
    ViewerVo get(Long id);
    Boolean save(ViewerDto viewerDto);
    void saveList(List<ViewerDto> viewerDtos);
    Boolean update(ViewerDto viewerDto);
    Boolean updateSub(ViewerDto viewerDto);
    Boolean delete(Long id);

    /**
     * 根据人员ID 查看业务类型 查看该人员可看的范围
     * @param userId
     * @param businessType 1工时填报2工时日志
     * @return
     */
    ViewerVo getUserRange(Long userId,Integer businessType);
}
