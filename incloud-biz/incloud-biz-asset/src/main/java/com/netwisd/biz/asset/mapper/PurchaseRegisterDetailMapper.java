package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseRegisterDetail;
import com.netwisd.biz.asset.dto.PurchaseRegisterDetailDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 采购信息登记详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-09 09:41:26
 */
@Mapper
public interface PurchaseRegisterDetailMapper extends BaseMapper<PurchaseRegisterDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseRegisterDetailDto
     * @return
     */
    Page<PurchaseRegisterDetailVo> getPageList(Page page, @Param("purchaseRegisterDetailDto") PurchaseRegisterDetailDto purchaseRegisterDetailDto);
}
