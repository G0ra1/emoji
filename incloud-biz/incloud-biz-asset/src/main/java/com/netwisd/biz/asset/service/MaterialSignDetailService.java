package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialSignDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialSignDetailDto;
import com.netwisd.biz.asset.vo.MaterialSignDetailVo;
/**
 * @Description 签收详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
public interface MaterialSignDetailService extends BatchService<MaterialSignDetail> {
    Page list(MaterialSignDetailDto materialSignDetailDto);
    Page lists(MaterialSignDetailDto materialSignDetailDto);
    MaterialSignDetailVo get(Long id);
    void save(MaterialSignDetailDto materialSignDetailDto);
    Boolean saveList(List<MaterialSignDetailDto> materialSignDetailDtos);
    void update(MaterialSignDetailDto materialSignDetailDto);
    void updateSub(MaterialSignDetailDto materialSignDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialSignDetailVo> getByFkIdVo(Long id);
}
