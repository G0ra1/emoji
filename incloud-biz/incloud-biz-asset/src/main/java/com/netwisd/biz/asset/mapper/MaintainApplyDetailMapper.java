package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaintainApplyDetail;
import com.netwisd.biz.asset.dto.MaintainApplyDetailDto;
import com.netwisd.biz.asset.vo.MaintainApplyDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 维修申请详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-27 10:28:25
 */
@Mapper
public interface MaintainApplyDetailMapper extends BaseMapper<MaintainApplyDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param maintainApplyDetailDto
     * @return
     */
    Page<MaintainApplyDetailVo> getPageList(Page page, @Param("maintainApplyDetailDto") MaintainApplyDetailDto maintainApplyDetailDto);
}
