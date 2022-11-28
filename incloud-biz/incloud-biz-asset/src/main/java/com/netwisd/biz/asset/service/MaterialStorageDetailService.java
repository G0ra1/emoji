package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialStorageDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialStorageDetailDto;
import com.netwisd.biz.asset.vo.MaterialStorageDetailVo;
/**
 * @Description 资产入库明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:44:47
 */
public interface MaterialStorageDetailService extends BatchService<MaterialStorageDetail> {
    Page list(MaterialStorageDetailDto materialStorageDetailDto);
    Page lists(MaterialStorageDetailDto materialStorageDetailDto);
    MaterialStorageDetailVo get(Long id);
    void save(MaterialStorageDetailDto materialStorageDetailDto);
    void saveList(List<MaterialStorageDetailDto> materialStorageDetailDtos);
    void update(MaterialStorageDetailDto materialStorageDetailDto);
    void updateSub(MaterialStorageDetailDto materialStorageDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialStorageDetailVo> getByFkIdVo(Long id);
}
