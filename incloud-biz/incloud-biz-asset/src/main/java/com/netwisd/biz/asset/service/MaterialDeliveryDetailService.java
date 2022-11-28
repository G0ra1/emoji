package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.MaterialDeliveryDetailDto;
import com.netwisd.biz.asset.vo.MaterialDeliveryDetailVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialDeliveryDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 资产出库明细详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-24 14:37:41
 */
public interface MaterialDeliveryDetailService extends BatchService<MaterialDeliveryDetail> {
    Page list(MaterialDeliveryDetailDto materialDeliveryDetailDto);
    Page lists(MaterialDeliveryDetailDto materialDeliveryDetailDto);
    MaterialDeliveryDetailVo get(Long id);
    void save(MaterialDeliveryDetailDto materialDeliveryDetailDto);
    void saveList(List<MaterialDeliveryDetailDto> materialDeliveryDetailDtos);
    void update(MaterialDeliveryDetailDto materialDeliveryDetailDto);
    void updateSub(MaterialDeliveryDetailDto materialDeliveryDetailDto);
    void delete(Long id);
    void deleteByDeliveryId(Long id);

    List<MaterialDeliveryDetailVo> getByDeliveryId(Long id);
}
