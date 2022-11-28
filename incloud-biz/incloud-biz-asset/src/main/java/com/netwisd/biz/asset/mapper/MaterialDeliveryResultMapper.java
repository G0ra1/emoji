package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialDeliveryResult;
import com.netwisd.biz.asset.dto.MaterialDeliveryResultDto;
import com.netwisd.biz.asset.vo.MaterialDeliveryResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产出库详情表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:56
 */
@Mapper
public interface MaterialDeliveryResultMapper extends BaseMapper<MaterialDeliveryResult> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialDeliveryResultDto
     * @return
     */
    Page<MaterialDeliveryResultVo> getPageList(Page page, @Param("materialDeliveryResultDto") MaterialDeliveryResultDto materialDeliveryResultDto);
}
