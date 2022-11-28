package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.vo.MdmPostVo;
import com.netwisd.base.mdm.entity.MdmPost;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 岗位 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-25 15:15:37
 */
@Mapper
public interface MdmPostMapper extends BaseMapper<MdmPost> {

    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmPostDto
     * @return
     */
    Page<MdmPostVo> getPageList(Page page, @Param("mdmPostDto") MdmPostDto mdmPostDto);

    /**
     * 分页查
     * @param page
     * @param mdmPostDto
     * @return
     */
    Page<MdmPostVo> getList(Page page, @Param("mdmPostDto") MdmPostDto mdmPostDto);
}
