package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.entity.MaintainAssetsResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaintainAssetsResultDto;
import com.netwisd.biz.asset.vo.MaintainAssetsResultVo;

import java.util.List;

/**
 * @Description 维修资产明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-26 14:08:43
 */
public interface MaintainAssetsResultService extends IService<MaintainAssetsResult> {
    Page list(MaintainAssetsResultDto maintainAssetsResultDto);
    Page lists(MaintainAssetsResultDto maintainAssetsResultDto);
    MaintainAssetsResultVo get(Long id);
    Boolean save(MaintainAssetsResultDto maintainAssetsResultDto);
    Boolean update(MaintainAssetsResultDto maintainAssetsResultDto);
    Boolean delete(Long id);

    Boolean saveList(List<MaintainAssetsResultDto> maintainAssetsResultDtos);
}
