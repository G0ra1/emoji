package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.MaterialDeployResultDto;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialDelivery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDeliveryDto;
import com.netwisd.biz.asset.vo.MaterialDeliveryVo;
/**
 * @Description 资产出库管理 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:38
 */
public interface MaterialDeliveryService extends BatchService<MaterialDelivery> {
    Page list(MaterialDeliveryDto materialDeliveryDto);
    Page lists(MaterialDeliveryDto materialDeliveryDto);
    MaterialDeliveryVo get(Long id);
    void save(MaterialDeliveryDto materialDeliveryDto);
    void saveList(List<MaterialDeliveryDto> materialDeliveryDtos);
    void update(MaterialDeliveryDto materialDeliveryDto);
    void updateSub(MaterialDeliveryDto materialDeliveryDto);
    void delete(Long id);

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    void deleteByProc(String procInstId);

    /**
     * 根据流程实例id获取数据
     * @param procInstId
     * @return
     */
    MaterialDeliveryVo getByProc(String procInstId);

    /**
     * 出库流程保存前方法
     * @param processInstanceId
     * @return
     */
    Result deliverySaveBefore(String processInstanceId);

    /**
     * 出库流程保存后方法
     * @param processInstanceId
     * @return
     */
    Result deliverySaveAfter(String processInstanceId);

    /**
     * 出库流程完成后
     * @param processInstanceId
     * @return
     */
    Result deliveryAuditSuccess(String processInstanceId);

}
