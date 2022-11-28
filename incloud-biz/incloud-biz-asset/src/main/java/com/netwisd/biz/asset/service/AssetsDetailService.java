package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.constants.AssetsConditions;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDto;
import com.netwisd.common.db.data.BatchService;

import java.math.BigDecimal;
import java.util.List;
import com.netwisd.biz.asset.entity.AssetsDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
/**
 * @Description 资产明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-21 14:54:51
 */
public interface AssetsDetailService extends BatchService<AssetsDetail> {
    Page list(AssetsDetailDto assetsDetailDto);
    Page lists(AssetsDetailDto assetsDetailDto);
    AssetsDetailVo get(Long id);
    Boolean save(AssetsDetailDto assetsDetailDto);
    Boolean saveList(List<AssetsDetailDto> assetsDetailDtos);
    Boolean update(AssetsDetailDto assetsDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<AssetsDetailVo> getByAssetsId(Long id);
    List<AssetsDetailVo> getByAssetsSkuId(Long id);

    /**
     * 根据机构id或者部门id或者人员id
     * @param assetsDetailDto
     * @return
     */
    Page getAssetsDetail(AssetsDetailDto assetsDetailDto);

    /**
     * 获取大于指定数量的库存物资
     * @param assetsDetailDto
     * @param conditions
     * @return
     */
    List<AssetsDetailVo> getListByConditions(AssetsDetailDto assetsDetailDto,AssetsConditions... conditions);

    /**
     * 获取大于指定数量的库存物资
     * @param assetsDetailDto
     * @param conditions
     * @return
     */
    Page<AssetsDetailVo> getListByConditionsPage(AssetsDetailDto assetsDetailDto,AssetsConditions... conditions);

    /**
     * 获取大于指定数量的库存物资
     * @param assetsIds
     * @return
     */
    Page<AssetsDetailVo> getListByAssetsIdPage(Long... assetsIds);

    /**
     * 获取大于指定数量的库存物资
     * @param assetsDetailDto
     * @return
     */
    Page<AssetsDetailVo> getScrapDetail(AssetsDetailDto assetsDetailDto);
}
