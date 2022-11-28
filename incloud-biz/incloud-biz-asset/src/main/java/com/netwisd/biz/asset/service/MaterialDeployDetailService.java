package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialDeployDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDeployDetailDto;
import com.netwisd.biz.asset.vo.MaterialDeployDetailVo;
import java.util.List;

/**
 * @Description 资产调配明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-06 10:35:24
 */
public interface MaterialDeployDetailService extends BatchService<MaterialDeployDetail> {
    Page<MaterialDeployDetailVo> page(MaterialDeployDetailDto materialDeployDetailDto);
    List<MaterialDeployDetailVo> list(MaterialDeployDetailDto materialDeployDetailDto);
    MaterialDeployDetailVo get(Long id);
    Boolean save(MaterialDeployDetailDto materialDeployDetailDto);
    Boolean saveList(List<MaterialDeployDetailDto> materialDeployDetailDtos);
    Boolean update(MaterialDeployDetailDto materialDeployDetailDto);
    Boolean delete(Long id);
    Boolean deleteByDeployId(Long id);
    List<MaterialDeployDetailVo> getByDeployId(Long id);
}