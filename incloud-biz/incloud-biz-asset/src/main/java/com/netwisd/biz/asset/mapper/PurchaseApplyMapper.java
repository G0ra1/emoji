package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseApply;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.netwisd.biz.asset.vo.PurchaseApplyVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 购置申请 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 10:10:21
 */
@Mapper
public interface PurchaseApplyMapper extends BaseMapper<PurchaseApply> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseApplyDto
     * @return
     */
    Page<PurchaseApplyVo> getPageList(Page page, @Param("purchaseApplyDto") PurchaseApplyDto purchaseApplyDto);

}
