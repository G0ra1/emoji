package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.Daybook;
import com.netwisd.biz.asset.dto.DaybookDto;
import com.netwisd.biz.asset.vo.DaybookVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产流水表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:19:59
 */
@Mapper
public interface DaybookMapper extends BaseMapper<Daybook> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param daybookDto
     * @return
     */
    Page<DaybookVo> getPageList(Page page, @Param("daybookDto") DaybookDto daybookDto);
}
