package com.netwisd.biz.asset.service;

import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialRefund;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialRefundDto;
import com.netwisd.biz.asset.vo.MaterialRefundVo;
/**
 * @Description 资产退还 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
public interface MaterialRefundService extends BatchService<MaterialRefund> {
    Page list(MaterialRefundDto materialRefundDto);
    Page lists(MaterialRefundDto materialRefundDto);
    MaterialRefundVo get(Long id);
    void save(MaterialRefundDto materialRefundDto);
    void saveList(List<MaterialRefundDto> materialRefundDtos);
    void update(MaterialRefundDto materialRefundDto);
    void updateSub(MaterialRefundDto materialRefundDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialRefundVo> getByFkIdVo(Long id);

    /**
     * 根据流程实例id删除业务数据
     * @param procInstId
     */
    void deleteByProc(String procInstId);

    /**
     * 根据流程实例id获取数据
     * @param procInstId
     */
    MaterialRefundVo getByProc(String procInstId);

    /**
     * 退库流程保存前方法
     * @param processInstanceId
     * @return
     */
    Result refundSaveBefore(String processInstanceId);

    /**
     * 退库流程保存后方法
     * @param processInstanceId
     * @return
     */
    Result refundSaveAfter(String processInstanceId);

    /**
     * 退库流程完成后
     * @param processInstanceId
     * @return
     */
    Result refundAuditSuccess(String processInstanceId);
}
