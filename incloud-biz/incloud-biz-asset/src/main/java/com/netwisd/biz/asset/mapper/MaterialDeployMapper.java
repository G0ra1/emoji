package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialDeploy;
import com.netwisd.biz.asset.dto.MaterialDeployDto;
import com.netwisd.biz.asset.vo.MaterialDeployVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产调配 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-06 10:35:24
 */
@Mapper
public interface MaterialDeployMapper extends BaseMapper<MaterialDeploy> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialDeployDto
     * @return
     */
    //Page<MaterialDeployVo> getPage(Page page, @Param("materialDeployDto") MaterialDeployDto materialDeployDto);
}
