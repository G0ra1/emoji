package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.ViewerDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.ViewerDetailDto;
import com.netwisd.biz.asset.vo.ViewerDetailVo;
/**
 * @Description 物资数据权限范围表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
public interface ViewerDetailService extends BatchService<ViewerDetail> {
    Page list(ViewerDetailDto viewerDetailDto);
    Page lists(ViewerDetailDto viewerDetailDto);
    ViewerDetailVo get(Long id);
    Boolean save(ViewerDetailDto viewerDetailDto);
    Boolean saveList(List<ViewerDetailDto> viewerDetailDtos);
    Boolean update(ViewerDetailDto viewerDetailDto);
    Boolean delete(Long id);
    Boolean deleteByViewerId(Long viewerId);
    List<ViewerDetailVo> getByViewerId(Long viewerId);
}
