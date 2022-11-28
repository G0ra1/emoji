package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmUserDetailDto;
import com.netwisd.base.common.mdm.vo.MdmUserDetailVo;
import com.netwisd.base.mdm.entity.MdmUserDetail;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 用户详情 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 09:09:37
 */
@Mapper
public interface MdmUserDetailMapper extends BaseMapper<MdmUserDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmUserDetailDto
     * @return
     */
    Page<MdmUserDetailVo> getPageList(Page page, @Param("mdmUserDetailDto") MdmUserDetailDto mdmUserDetailDto);
}
