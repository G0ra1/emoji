package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseRegisterAssets;
import com.netwisd.biz.asset.dto.PurchaseRegisterAssetsDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterAssetsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 采购信息登记明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-27 18:21:48
 */
@Mapper
public interface PurchaseRegisterAssetsMapper extends BaseMapper<PurchaseRegisterAssets> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseRegisterAssetsDto
     * @return
     */
    Page<PurchaseRegisterAssetsVo> getPageList(Page page, @Param("purchaseRegisterAssetsDto") PurchaseRegisterAssetsDto purchaseRegisterAssetsDto);
}
