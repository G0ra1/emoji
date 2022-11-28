package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.dto.MaterialAcceptResultDto;
import com.netwisd.biz.asset.entity.MaterialAcceptResult;
import com.netwisd.biz.asset.vo.MaterialAcceptResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产领用详情结果 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Mapper
public interface MaterialAcceptResultMapper extends BaseMapper<MaterialAcceptResult> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialAcceptResultDto
     * @return
     */
    Page<MaterialAcceptResultVo> getPageList(Page page, @Param("materialAcceptResultDto") MaterialAcceptResultDto materialAcceptResultDto);
}
