package com.netwisd.biz.asset.service;

import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaintainPlan;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaintainPlanDto;
import com.netwisd.biz.asset.vo.MaintainPlanVo;
/**
 * @Description 维修计划 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-25 14:07:05
 */
public interface MaintainPlanService extends BatchService<MaintainPlan> {
    Page list(MaintainPlanDto maintainPlanDto);
    Page lists(MaintainPlanDto maintainPlanDto);
    MaintainPlanVo get(Long id);
    void save(MaintainPlanDto maintainPlanDto);
    void saveList(List<MaintainPlanDto> maintainPlanDtos);
    void update(MaintainPlanDto maintainPlanDto);
    void updateSub(MaintainPlanDto maintainPlanDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaintainPlanVo> getByFkIdVo(Long id);
    /**
    * 根据流程实例id获取数据
     * @param procInstId
     * @return
    */
    MaintainPlanVo getByProc(String procInstId);

    /**
     * 流程审批结束后执行
     * @param processInstanceId
     * @return
     */
    Result maintainProcSuccess(String processInstanceId);
}
