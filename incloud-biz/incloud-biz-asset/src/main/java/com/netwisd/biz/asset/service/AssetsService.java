package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.constants.AssetsConditions;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.common.db.data.BatchService;

import java.math.BigDecimal;
import java.util.List;
import com.netwisd.biz.asset.entity.Assets;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.AssetsDto;
import com.netwisd.biz.asset.vo.AssetsVo;
import org.apache.poi.hpsf.Decimal;

/**
 * @Description 资产台账 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-21 14:54:51
 */
public interface AssetsService extends BatchService<Assets> {
    Page list(AssetsDto assetsDto);
    Page lists(AssetsDto assetsDto);
    AssetsVo get(Long id);
    Boolean save(AssetsDto assetsDto);
    Boolean saveList(List<AssetsDto> assetsDtos);
    Boolean update(AssetsDto assetsDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<AssetsVo> getByFkIdVo(Long id);

    /**
     * 获取大于指定数量的库存物资
     * @param assetsDto
     * @param conditions
     * @return
     */
    List<AssetsVo> getListByConditions(AssetsDto assetsDto,AssetsConditions... conditions);

    /**
     * 获取大于指定数量的库存物资
     * @param assetsDto
     * @param conditions
     * @return
     */
    Page<AssetsVo> getListByConditionsPage(AssetsDto assetsDto, AssetsConditions... conditions);

    Page getDetailByAssets(AssetsDto assetsDto);
}
