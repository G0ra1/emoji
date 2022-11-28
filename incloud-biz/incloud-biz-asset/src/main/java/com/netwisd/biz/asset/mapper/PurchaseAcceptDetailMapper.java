package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseAcceptDetail;
import com.netwisd.biz.asset.dto.PurchaseAcceptDetailDto;
import com.netwisd.biz.asset.vo.PurchaseAcceptDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 验收资产明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
@Mapper
public interface PurchaseAcceptDetailMapper extends BaseMapper<PurchaseAcceptDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseAcceptDetailDto
     * @return
     */
    Page<PurchaseAcceptDetailVo> getPageList(Page page, @Param("purchaseAcceptDetailDto") PurchaseAcceptDetailDto purchaseAcceptDetailDto);
}
