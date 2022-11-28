package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.biz.asset.dto.MaterialDeployResultDto;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.netwisd.biz.asset.vo.PurchaseApplyVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialDeploy;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDeployDto;
import com.netwisd.biz.asset.vo.MaterialDeployVo;
import java.util.List;

/**
 * @Description 资产调配 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-06 10:35:24
 */
public interface MaterialDeployService extends BatchService<MaterialDeploy> {
    Page<MaterialDeployVo> page(MaterialDeployDto materialDeployDto);
    List<MaterialDeployVo> list(MaterialDeployDto materialDeployDto);
    MaterialDeployVo get(Long id);
    Boolean save(MaterialDeployDto materialDeployDto);
    Boolean saveList(List<MaterialDeployDto> materialDeployDtos);
    Boolean update(MaterialDeployDto materialDeployDto);
    Boolean updateSub(MaterialDeployDto materialDeployDto);
    Boolean delete(Long id);

    /**
     * 根据流程id获取详情数据
     * @param procInstId
     * @return
     */
    MaterialDeployVo getByProcId(String procInstId);

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     * @return Result
     */
    Boolean deleteByProc(String procInstId);

    /**
     * 流程保存更新
     * @param materialDeployDto
     * @return
     */
    MaterialDeployVo procSaveOrUpdate(MaterialDeployDto materialDeployDto);

    /**
     * 流程保存前方法
     * @param procInstId
     * @return
     */
    Boolean procSaveBefore(String procInstId);

    /**
     * 流程保存后方法
     * @param procInstId
     * @return
     */
    Boolean procSaveAfter(String procInstId);

    /**
     * 流程完成
     * @param procInstId
     * @return
     */
    Boolean procAuditSuccess(String procInstId);

    Page listForDelivery(MaterialDeployDto materialDeployDto);

    Page getResultByDeploy(MaterialDeployResultDto materialDeployResultDto);

    Page getDetailByAssets(AssetsDto assetsDto);
}