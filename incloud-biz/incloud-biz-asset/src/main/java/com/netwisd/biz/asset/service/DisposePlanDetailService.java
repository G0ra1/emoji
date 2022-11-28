package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.DisposePlanDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.DisposePlanDetailDto;
import com.netwisd.biz.asset.vo.DisposePlanDetailVo;
/**
 * @Description 处置计划明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
public interface DisposePlanDetailService extends BatchService<DisposePlanDetail> {
    Page list(DisposePlanDetailDto disposePlanDetailDto);
    Page lists(DisposePlanDetailDto disposePlanDetailDto);
    DisposePlanDetailVo get(Long id);
    void save(DisposePlanDetailDto disposePlanDetailDto);
    void saveList(List<DisposePlanDetailDto> disposePlanDetailDtos);
    void update(DisposePlanDetailDto disposePlanDetailDto);
    void updateSub(DisposePlanDetailDto disposePlanDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<DisposePlanDetailVo> getByFkIdVo(Long id);
}
