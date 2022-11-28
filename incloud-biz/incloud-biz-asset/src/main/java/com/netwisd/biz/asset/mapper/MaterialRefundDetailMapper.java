package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialRefundDetail;
import com.netwisd.biz.asset.dto.MaterialRefundDetailDto;
import com.netwisd.biz.asset.vo.MaterialRefundDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产退还详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
@Mapper
public interface MaterialRefundDetailMapper extends BaseMapper<MaterialRefundDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialRefundDetailDto
     * @return
     */
    Page<MaterialRefundDetailVo> getPageList(Page page, @Param("materialRefundDetailDto") MaterialRefundDetailDto materialRefundDetailDto);
}
