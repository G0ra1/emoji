package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaintainRegDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaintainRegDetailDto;
import com.netwisd.biz.asset.vo.MaintainRegDetailVo;
/**
 * @Description 维修登记详情表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-28 17:12:23
 */
public interface MaintainRegDetailService extends BatchService<MaintainRegDetail> {
    Page list(MaintainRegDetailDto maintainRegDetailDto);
    Page lists(MaintainRegDetailDto maintainRegDetailDto);
    MaintainRegDetailVo get(Long id);
    void save(MaintainRegDetailDto maintainRegDetailDto);
    void saveList(List<MaintainRegDetailDto> maintainRegDetailDtos);
    void update(MaintainRegDetailDto maintainRegDetailDto);
    void updateSub(MaintainRegDetailDto maintainRegDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaintainRegDetailVo> getByFkIdVo(Long id);
}
