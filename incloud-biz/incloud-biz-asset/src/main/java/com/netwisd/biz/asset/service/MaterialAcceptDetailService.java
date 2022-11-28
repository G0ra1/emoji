package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.MaterialAcceptDetailDto;
import com.netwisd.biz.asset.vo.MaterialAcceptDetailVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialAcceptDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 资产领用明细详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-26 17:25:03
 */
public interface MaterialAcceptDetailService extends BatchService<MaterialAcceptDetail> {
    Page list(MaterialAcceptDetailDto materialAcceptDetailDto);
    Page lists(MaterialAcceptDetailDto materialAcceptDetailDto);
    MaterialAcceptDetailVo get(Long id);
    void save(MaterialAcceptDetailDto materialAcceptDetailDto);
    void saveList(List<MaterialAcceptDetailDto> materialAcceptDetailDtos);
    void update(MaterialAcceptDetailDto materialAcceptDetailDto);
    void updateSub(MaterialAcceptDetailDto materialAcceptDetailDto);
    void delete(Long id);
    void deleteByAcceptId(Long acceptId);
    void deleteByAcceptAssetsId(Long acceptAssetsId);

    List<MaterialAcceptDetailVo> getByAcceptId(Long acceptId);
    List<MaterialAcceptDetailVo> getByAcceptAssetsId(Long acceptAssetsId);
}
