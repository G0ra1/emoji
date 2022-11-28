package com.netwisd.biz.asset.service;

import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.dto.MaterialDistributeAssetsDto;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.data.IVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialDistribute;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDistributeDto;

/**
 * @Description 资产派发 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:46:22
 */
public interface MaterialDistributeService extends BatchService<MaterialDistribute> {
    Page list(MaterialDistributeDto materialDistributeDto);
    Page lists(MaterialDistributeDto materialDistributeDto);
    MaterialDistributeVo get(Long id);
    Boolean save(MaterialDistributeDto materialDistributeDto);
    Boolean saveList(List<MaterialDistributeDto> materialDistributeDtos);
    Boolean update(MaterialDistributeDto materialDistributeDto);
    Boolean updateSub(MaterialDistributeDto materialDistributeDto);
    Boolean delete(Long id);

    List<MaterialDistributeVo> getBySourceId(Long sourceId);

    Boolean deleteByProc(String procInstId);

    MaterialDistributeVo getByProc(String procInstId);

    Boolean procSaveOrUpdate(MaterialDistributeDto distributeDto);

    Page<MaterialDistributeDetailVo> gerDetailByAssetsId(AssetsDetailDto assetsDetailDto);

    /**
     * 获取来源单据
     * @param distributeDto
     * @return
     */
    Page getOrders(MaterialDistributeDto distributeDto);

    List<MaterialDistributeAssetsVo> getAssetsList(MaterialDistributeDto distributeDto);

    List<MaterialDistributeDetailVo> getDetailByAssets(MaterialDistributeAssetsDto assetsDto);

//    List<AssetsDetailVo> getAssetsDetailByAssets(MaterialDistributeAssetsDto assetsDto);

    List<MaterialDistributeDetailVo> getDetailByAssetsList(List<MaterialDistributeAssetsDto> distributeAssetsDtoList);

    /**
     * 资产派发保存前
     */
    Boolean procSaveBefore(String procInstId);

    /**
     * 资产派发保存后
     */
    Boolean procSaveAfter(String procInstId);

    /**
     * 流程完成后，填写资产台账及明细验收数量
     */
    Boolean procAuditSuccess(String procInstId);

}
