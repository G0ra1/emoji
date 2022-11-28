package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyCouPerm;
import com.netwisd.biz.study.dto.StudyCouPermDto;
import com.netwisd.biz.study.vo.StudyCouPermVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课件授权表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:13:08
 */
@Mapper
public interface StudyCouPermMapper extends BaseMapper<StudyCouPerm> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyCouPermDto
     * @return
     */
    Page<StudyCouPermVo> getPageList(Page page, @Param("studyCouPermDto") StudyCouPermDto studyCouPermDto);
}
