package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.DaybookSupplies;
import com.netwisd.biz.asset.dto.DaybookSuppliesDto;
import com.netwisd.biz.asset.vo.DaybookSuppliesVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 耗材流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 17:20:30
 */
@Mapper
public interface DaybookSuppliesMapper extends BaseMapper<DaybookSupplies> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param daybookSuppliesDto
     * @return
     */
    Page<DaybookSuppliesVo> getPageList(Page page, @Param("daybookSuppliesDto") DaybookSuppliesDto daybookSuppliesDto);
}
