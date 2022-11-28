package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseAccept;
import com.netwisd.biz.asset.dto.PurchaseAcceptDto;
import com.netwisd.biz.asset.vo.PurchaseAcceptVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资购置验收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
@Mapper
public interface PurchaseAcceptMapper extends BaseMapper<PurchaseAccept> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseAcceptDto
     * @return
     */
    Page<PurchaseAcceptVo> getPageList(Page page, @Param("purchaseAcceptDto") PurchaseAcceptDto purchaseAcceptDto);

}
