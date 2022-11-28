package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaintainPlanDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaintainPlanDetailDto;
import com.netwisd.biz.asset.vo.MaintainPlanDetailVo;
/**
 * @Description 维修计划明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-25 14:07:05
 */
public interface MaintainPlanDetailService extends BatchService<MaintainPlanDetail> {
    Page list(MaintainPlanDetailDto maintainPlanDetailDto);
    Page lists(MaintainPlanDetailDto maintainPlanDetailDto);
    MaintainPlanDetailVo get(Long id);
    void save(MaintainPlanDetailDto maintainPlanDetailDto);
    void saveList(List<MaintainPlanDetailDto> maintainPlanDetailDtos);
    void update(MaintainPlanDetailDto maintainPlanDetailDto);
    void updateSub(MaintainPlanDetailDto maintainPlanDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaintainPlanDetailVo> getByFkIdVo(Long id);
}
