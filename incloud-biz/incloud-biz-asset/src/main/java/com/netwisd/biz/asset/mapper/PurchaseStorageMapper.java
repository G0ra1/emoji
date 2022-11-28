package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseStorage;
import com.netwisd.biz.asset.dto.PurchaseStorageDto;
import com.netwisd.biz.asset.vo.PurchaseStorageVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资验收入库 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-05-20 18:03:40
 */
@Mapper
public interface PurchaseStorageMapper extends BaseMapper<PurchaseStorage> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseStorageDto
     * @return
     */
    Page<PurchaseStorageVo> getPageList(Page page, @Param("purchaseStorageDto") PurchaseStorageDto purchaseStorageDto);

}
