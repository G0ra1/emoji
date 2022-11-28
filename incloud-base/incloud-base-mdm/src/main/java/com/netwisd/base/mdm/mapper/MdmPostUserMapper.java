package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmPostUserDto;
import com.netwisd.base.common.mdm.vo.MdmPostUserVo;
import com.netwisd.base.mdm.entity.MdmPostUser;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 岗位与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 11:47:13
 */
@Mapper
public interface MdmPostUserMapper extends BaseMapper<MdmPostUser> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmPostUserDto
     * @return
     */
    Page<MdmPostUserVo> getPageList(Page page, @Param("mdmPostUserDto") MdmPostUserDto mdmPostUserDto);

}
