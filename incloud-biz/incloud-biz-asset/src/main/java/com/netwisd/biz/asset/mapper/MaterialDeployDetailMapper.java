package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialDeployDetail;
import com.netwisd.biz.asset.dto.MaterialDeployDetailDto;
import com.netwisd.biz.asset.vo.MaterialDeployDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产调配明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-06 10:35:24
 */
@Mapper
public interface MaterialDeployDetailMapper extends BaseMapper<MaterialDeployDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialDeployDetailDto
     * @return
     */
    //Page<MaterialDeployDetailVo> getPage(Page page, @Param("materialDeployDetailDto") MaterialDeployDetailDto materialDeployDetailDto);
}
