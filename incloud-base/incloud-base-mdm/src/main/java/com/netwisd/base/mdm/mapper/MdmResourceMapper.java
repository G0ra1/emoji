package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmResourceDto;
import com.netwisd.base.common.mdm.vo.MdmResourceVo;
import com.netwisd.base.mdm.entity.MdmResource;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资源管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-09 10:39:04
 */
@Mapper
public interface MdmResourceMapper extends BaseMapper<MdmResource> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmResourceDto
     * @return
     */
    Page<MdmResourceVo> getPageList(Page page, @Param("mdmResourceDto") MdmResourceDto mdmResourceDto);
}
