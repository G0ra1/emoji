package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaintainApply;
import com.netwisd.biz.asset.dto.MaintainApplyDto;
import com.netwisd.biz.asset.vo.MaintainApplyVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 维修申请 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-27 10:28:25
 */
@Mapper
public interface MaintainApplyMapper extends BaseMapper<MaintainApply> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param maintainApplyDto
     * @return
     */
    Page<MaintainApplyVo> getPageList(Page page, @Param("maintainApplyDto") MaintainApplyDto maintainApplyDto);

    /**
     * 获取当天最大编号
     */
    String getMaxCode();
}
