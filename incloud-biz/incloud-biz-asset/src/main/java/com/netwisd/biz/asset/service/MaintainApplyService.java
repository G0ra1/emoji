package com.netwisd.biz.asset.service;

import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaintainApply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaintainApplyDto;
import com.netwisd.biz.asset.vo.MaintainApplyVo;
/**
 * @Description 维修申请 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-27 10:28:25
 */
public interface MaintainApplyService extends BatchService<MaintainApply> {
    Page list(MaintainApplyDto maintainApplyDto);
    Page lists(MaintainApplyDto maintainApplyDto);
    MaintainApplyVo get(Long id);
    void save(MaintainApplyDto maintainApplyDto);
    void saveList(List<MaintainApplyDto> maintainApplyDtos);
    void update(MaintainApplyDto maintainApplyDto);
    void updateSub(MaintainApplyDto maintainApplyDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaintainApplyVo> getByFkIdVo(Long id);
    /**
     * 根据流程id获取数据
     * @param procInstId
     * @return
     */
    MaintainApplyVo getByProc(String procInstId);

    /**
     * 流程保存前方法
     * @param processInstanceId
     * @return
     */
    Result maintainApplySaveBefore(String processInstanceId);

    /**
     * 流程保存后方法
     * @param processInstanceId
     * @return
     */
    Result maintainApplySaveAfter(String processInstanceId);

    /**
     * 流程完成
     * @param processInstanceId
     * @return
     */
    Result maintainApplyProcSuccess(String processInstanceId);

}
