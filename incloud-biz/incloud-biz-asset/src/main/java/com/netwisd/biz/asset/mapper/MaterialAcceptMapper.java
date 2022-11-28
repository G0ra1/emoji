package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.dto.MaterialAcceptDto;
import com.netwisd.biz.asset.entity.MaterialAccept;
import com.netwisd.biz.asset.vo.MaterialAcceptVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产领用 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Mapper
public interface MaterialAcceptMapper extends BaseMapper<MaterialAccept> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialAcceptDto
     * @return
     */
    Page<MaterialAcceptVo> getPageList(Page page, @Param("materialAcceptDto") MaterialAcceptDto materialAcceptDto);

    /**
     * 查询当日最大申请编号
     * @return
     */
    String getMaxCode();
}
