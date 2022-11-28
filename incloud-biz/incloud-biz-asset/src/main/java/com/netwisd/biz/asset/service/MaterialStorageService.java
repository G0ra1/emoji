package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialStorage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialStorageDto;
import com.netwisd.biz.asset.vo.MaterialStorageVo;
/**
 * @Description 资产入库管理 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:44:47
 */
public interface MaterialStorageService extends BatchService<MaterialStorage> {
    Page list(MaterialStorageDto materialStorageDto);
    Page lists(MaterialStorageDto materialStorageDto);
    MaterialStorageVo get(Long id);
    void save(MaterialStorageDto materialStorageDto);
    void saveList(List<MaterialStorageDto> materialStorageDtos);
    void update(MaterialStorageDto materialStorageDto);
    void updateSub(MaterialStorageDto materialStorageDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialStorageVo> getByFkIdVo(Long id);

    /**
     * 流程删除
     */
    void deleteByProc(String procInstId);

    /**
     * 根据流程实例id获取数据
     * @param procInstId
     * @return
     */
    MaterialStorageVo getByProc(String procInstId);

    /**
     * 出库流程保存前方法
     * @param processInstanceId
     * @return
     */
    Result storageSaveBefore(String processInstanceId);

    /**
     * 出库流程保存后方法
     * @param processInstanceId
     * @return
     */
    Result storageSaveAfter(String processInstanceId);

    /**
     * 流程完成
     * @param processInstanceId
     * @return
     */
    Result storageAuditSuccess(String processInstanceId);

    /**
     * 获取待入账资产明细
     * @param assetsDetailDto
     * @return
     */
    Page<AssetsDetailVo> getAssetsDetails(AssetsDetailDto assetsDetailDto);
}
