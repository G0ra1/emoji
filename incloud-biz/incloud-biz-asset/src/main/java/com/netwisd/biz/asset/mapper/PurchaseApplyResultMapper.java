package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseApplyResult;
import com.netwisd.biz.asset.dto.PurchaseApplyResultDto;
import com.netwisd.biz.asset.vo.PurchaseApplyResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 购置申请结果表 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-21 11:18:24
 */
@Mapper
public interface PurchaseApplyResultMapper extends BaseMapper<PurchaseApplyResult> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseApplyResultDto
     * @return
     */
    Page<PurchaseApplyResultVo> getPageList(Page page, @Param("purchaseApplyResultDto") PurchaseApplyResultDto purchaseApplyResultDto);
}
