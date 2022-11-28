package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseRegisterResult;
import com.netwisd.biz.asset.dto.PurchaseRegisterResultDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 采购登记结果表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:54:08
 */
@Mapper
public interface PurchaseRegisterResultMapper extends BaseMapper<PurchaseRegisterResult> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseRegisterResultDto
     * @return
     */
    Page<PurchaseRegisterResultVo> getPageList(Page page, @Param("purchaseRegisterResultDto") PurchaseRegisterResultDto purchaseRegisterResultDto);
}
