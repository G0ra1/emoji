package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseRegister;
import com.netwisd.biz.asset.dto.PurchaseRegisterDto;
import com.netwisd.biz.asset.vo.PurchaseRegisterVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 采购信息登记 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-09 09:41:26
 */
@Mapper
public interface PurchaseRegisterMapper extends BaseMapper<PurchaseRegister> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseRegisterDto
     * @return
     */
    Page<PurchaseRegisterVo> getPageList(Page page, @Param("purchaseRegisterDto") PurchaseRegisterDto purchaseRegisterDto);

}
