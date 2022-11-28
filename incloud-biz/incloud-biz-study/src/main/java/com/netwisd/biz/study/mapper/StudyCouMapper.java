package com.netwisd.biz.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.study.entity.StudyCou;
import com.netwisd.biz.study.dto.StudyCouDto;
import com.netwisd.biz.study.vo.StudyCouVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 课件表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 18:23:02
 */
@Mapper
public interface StudyCouMapper extends BaseMapper<StudyCou> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param studyCouDto
     * @return
     */
    Page<StudyCouVo> getPageList(Page page, @Param("studyCouDto") StudyCouDto studyCouDto);
}
