package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialRefundDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialRefundDetailDto;
import com.netwisd.biz.asset.vo.MaterialRefundDetailVo;
/**
 * @Description 资产退还详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
public interface MaterialRefundDetailService extends BatchService<MaterialRefundDetail> {
    Page list(MaterialRefundDetailDto materialRefundDetailDto);
    Page lists(MaterialRefundDetailDto materialRefundDetailDto);
    MaterialRefundDetailVo get(Long id);
    void save(MaterialRefundDetailDto materialRefundDetailDto);
    void saveList(List<MaterialRefundDetailDto> materialRefundDetailDtos);
    void update(MaterialRefundDetailDto materialRefundDetailDto);
    void updateSub(MaterialRefundDetailDto materialRefundDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialRefundDetailVo> getByFkIdVo(Long id);
}
