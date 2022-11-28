package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialDeployResult;
import com.netwisd.biz.asset.dto.MaterialDeployResultDto;
import com.netwisd.biz.asset.vo.MaterialDeployResultVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产调配结果数据 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-08 11:05:25
 */
@Mapper
public interface MaterialDeployResultMapper extends BaseMapper<MaterialDeployResult> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialDeployResultDto
     * @return
     */
    //Page<MaterialDeployResultVo> getPage(Page page, @Param("materialDeployResultDto") MaterialDeployResultDto materialDeployResultDto);
}
