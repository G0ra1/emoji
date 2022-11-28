package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaintainApplyDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaintainApplyDetailDto;
import com.netwisd.biz.asset.vo.MaintainApplyDetailVo;
/**
 * @Description 维修申请详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-27 10:28:25
 */
public interface MaintainApplyDetailService extends BatchService<MaintainApplyDetail> {
    Page list(MaintainApplyDetailDto maintainApplyDetailDto);
    Page lists(MaintainApplyDetailDto maintainApplyDetailDto);
    MaintainApplyDetailVo get(Long id);
    void save(MaintainApplyDetailDto maintainApplyDetailDto);
    void saveList(List<MaintainApplyDetailDto> maintainApplyDetailDtos);
    void update(MaintainApplyDetailDto maintainApplyDetailDto);
    void updateSub(MaintainApplyDetailDto maintainApplyDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaintainApplyDetailVo> getByFkIdVo(Long id);
}
