package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.dto.MaterialAcceptDetailDto;
import com.netwisd.biz.asset.entity.MaterialAcceptDetail;
import com.netwisd.biz.asset.vo.MaterialAcceptDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产领用明细详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Mapper
public interface MaterialAcceptDetailMapper extends BaseMapper<MaterialAcceptDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialAcceptDetailDto
     * @return
     */
    Page<MaterialAcceptDetailVo> getPageList(Page page, @Param("materialAcceptDetailDto") MaterialAcceptDetailDto materialAcceptDetailDto);
}
