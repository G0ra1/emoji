package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.DisposePlan;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.DisposePlanDto;
import com.netwisd.biz.asset.vo.DisposePlanVo;
/**
 * @Description 处置计划 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
public interface DisposePlanService extends BatchService<DisposePlan> {
    Page list(DisposePlanDto disposePlanDto);
    Page lists(DisposePlanDto disposePlanDto);
    DisposePlanVo get(Long id);
    void save(DisposePlanDto disposePlanDto);
    void saveList(List<DisposePlanDto> disposePlanDtos);
    void update(DisposePlanDto disposePlanDto);
    void updateSub(DisposePlanDto disposePlanDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<DisposePlanVo> getByFkIdVo(Long id);
}
