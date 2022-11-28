package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialRefund;
import com.netwisd.biz.asset.dto.MaterialRefundDto;
import com.netwisd.biz.asset.vo.MaterialRefundVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产退还 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
@Mapper
public interface MaterialRefundMapper extends BaseMapper<MaterialRefund> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialRefundDto
     * @return
     */
    Page<MaterialRefundVo> getPageList(Page page, @Param("materialRefundDto") MaterialRefundDto materialRefundDto);

    /**
     * 查询当日最大申请编号
     * @return
     */
    String getMaxCode();
}
