package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialChangeDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialChangeDetailDto;
import com.netwisd.biz.asset.vo.MaterialChangeDetailVo;
/**
 * @Description 资产变更详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:11:54
 */
public interface MaterialChangeDetailService extends BatchService<MaterialChangeDetail> {
    Page list(MaterialChangeDetailDto materialChangeDetailDto);
    Page lists(MaterialChangeDetailDto materialChangeDetailDto);
    MaterialChangeDetailVo get(Long id);
    Boolean save(MaterialChangeDetailDto materialChangeDetailDto);
    Boolean saveList(List<MaterialChangeDetailDto> materialChangeDetailDtos);
    void update(MaterialChangeDetailDto materialChangeDetailDto);
    void updateSub(MaterialChangeDetailDto materialChangeDetailDto);
    void delete(Long id);
    Boolean deleteByChangeId(Long id);
    List<MaterialChangeDetailVo> getByChangeId(Long id);
}
