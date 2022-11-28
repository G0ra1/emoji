package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialDistributeDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDistributeDetailDto;
import com.netwisd.biz.asset.vo.MaterialDistributeDetailVo;
/**
 * @Description 资产派发资产详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:46:22
 */
public interface MaterialDistributeDetailService extends BatchService<MaterialDistributeDetail> {
    Page list(MaterialDistributeDetailDto materialDistributeDetailDto);
    Page lists(MaterialDistributeDetailDto materialDistributeDetailDto);
    MaterialDistributeDetailVo get(Long id);
    void save(MaterialDistributeDetailDto materialDistributeDetailDto);
    void saveList(List<MaterialDistributeDetailDto> materialDistributeDetailDtos);
    void update(MaterialDistributeDetailDto materialDistributeDetailDto);
    void updateSub(MaterialDistributeDetailDto materialDistributeDetailDto);
    void delete(Long id);
    Boolean deleteByDistributeId(Long id);
    List<MaterialDistributeDetailVo> getByDistributeId(Long id);
    List<MaterialDistributeDetailVo> getByDistributeAssetsId(Long getByDistributeAssetsId);
}
