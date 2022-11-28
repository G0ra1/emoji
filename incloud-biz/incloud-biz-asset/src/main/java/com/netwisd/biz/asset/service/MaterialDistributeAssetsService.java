package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialDistributeAssets;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDistributeAssetsDto;
import com.netwisd.biz.asset.vo.MaterialDistributeAssetsVo;
/**
 * @Description 资产派发明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:46:22
 */
public interface MaterialDistributeAssetsService extends BatchService<MaterialDistributeAssets> {
    Page list(MaterialDistributeAssetsDto materialDistributeAssetsDto);
    Page lists(MaterialDistributeAssetsDto materialDistributeAssetsDto);
    MaterialDistributeAssetsVo get(Long id);
    void save(MaterialDistributeAssetsDto materialDistributeAssetsDto);
    void saveList(List<MaterialDistributeAssetsDto> materialDistributeAssetsDtos);
    void update(MaterialDistributeAssetsDto materialDistributeAssetsDto);
    void updateSub(MaterialDistributeAssetsDto materialDistributeAssetsDto);
    void delete(Long id);
    Boolean deleteByDistributeId(Long id);
    List<MaterialDistributeAssetsVo> getByDistributeId(Long id);
}
