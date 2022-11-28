package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialDeliveryDetail;
import com.netwisd.biz.asset.dto.MaterialDeliveryDetailDto;
import com.netwisd.biz.asset.vo.MaterialDeliveryDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产出库明细详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:38
 */
@Mapper
public interface MaterialDeliveryDetailMapper extends BaseMapper<MaterialDeliveryDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialDeliveryDetailDto
     * @return
     */
    Page<MaterialDeliveryDetailVo> getPageList(Page page, @Param("materialDeliveryDetailDto") MaterialDeliveryDetailDto materialDeliveryDetailDto);
}
