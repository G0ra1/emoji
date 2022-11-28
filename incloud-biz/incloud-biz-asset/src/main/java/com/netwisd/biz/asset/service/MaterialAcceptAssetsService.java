package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.MaterialAcceptAssetsDto;
import com.netwisd.biz.asset.vo.MaterialAcceptAssetsVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialAcceptAssets;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 资产领用资产明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-26 17:25:03
 */
public interface MaterialAcceptAssetsService extends BatchService<MaterialAcceptAssets> {
    Page list(MaterialAcceptAssetsDto materialAcceptAssetsDto);
    Page lists(MaterialAcceptAssetsDto materialAcceptAssetsDto);
    MaterialAcceptAssetsVo get(Long id);
    void save(MaterialAcceptAssetsDto materialAcceptAssetsDto);
    void saveList(List<MaterialAcceptAssetsDto> materialAcceptAssetsDtos);
    Boolean update(MaterialAcceptAssetsDto materialAcceptAssetsDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialAcceptAssetsVo> getByAcceptId(Long id);

    Boolean deleteByAcceptId(Long id);
}
