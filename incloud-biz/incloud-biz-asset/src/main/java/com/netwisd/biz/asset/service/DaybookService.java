package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.constants.DayBookType;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.entity.AssetsDetail;
import com.netwisd.biz.asset.entity.Daybook;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.DaybookDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.biz.asset.vo.DaybookVo;
/**
 * @Description 资产流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:19:59
 */
public interface DaybookService extends IService<Daybook> {
    Page list(DaybookDto daybookDto);
    Page lists(DaybookDto daybookDto);
    DaybookVo get(Long id);
    Boolean save(DaybookDto daybookDto);
    Boolean update(DaybookDto daybookDto);
    Boolean delete(Long id);
    Boolean log(Long formId, String procId, AssetsDetailDto assetsDetailDto, DayBookType operation);

    Page getByAssets(AssetsDetailDto assetsDetailDto);
}
