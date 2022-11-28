package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.dto.PurchaseApplyDetailDto;
import com.netwisd.biz.asset.entity.PurchaseApplyDetail;
import com.netwisd.biz.asset.vo.PurchaseApplyDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 购置申请详情 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 17:26:16
 */
@Mapper
public interface PurchaseApplyDetailMapper extends BaseMapper<PurchaseApplyDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseApplyDetailDto
     * @return
     */
    Page<PurchaseApplyDetailVo> getPageList(Page page, @Param("purchaseApplyDetailDto") PurchaseApplyDetailDto purchaseApplyDetailDto);
}
