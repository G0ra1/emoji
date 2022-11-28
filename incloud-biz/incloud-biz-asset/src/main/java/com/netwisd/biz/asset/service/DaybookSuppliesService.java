package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.entity.DaybookSupplies;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.DaybookSuppliesDto;
import com.netwisd.biz.asset.vo.DaybookSuppliesVo;
/**
 * @Description 耗材流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 17:20:30
 */
public interface DaybookSuppliesService extends IService<DaybookSupplies> {
    Page list(DaybookSuppliesDto daybookSuppliesDto);
    Page lists(DaybookSuppliesDto daybookSuppliesDto);
    DaybookSuppliesVo get(Long id);
    Boolean save(DaybookSuppliesDto daybookSuppliesDto);
    Boolean update(DaybookSuppliesDto daybookSuppliesDto);
    Boolean delete(Long id);
    Boolean log(Long formId, String procId, SuppliesDetailDto suppliesDetailDto, DayBookType operation);

    Page getByAssets(AssetsDetailDto assetsDetailDto);
}
