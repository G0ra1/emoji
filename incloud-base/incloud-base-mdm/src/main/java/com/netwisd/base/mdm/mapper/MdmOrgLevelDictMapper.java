package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmOrgLevelDictDto;
import com.netwisd.base.common.mdm.vo.MdmOrgLevelDictVo;
import com.netwisd.base.mdm.entity.MdmOrgLevelDict;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 组织级别类型字典 功能描述...
 * @author 云数网讯 zouliming@netwisd.com@netwisd.com
 * @date 2021-08-26 09:56:26
 */
@Mapper
public interface MdmOrgLevelDictMapper extends BaseMapper<MdmOrgLevelDict> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmOrgLevelDictDto
     * @return
     */
    Page<MdmOrgLevelDictVo> getPageList(Page page, @Param("mdmOrgLevelDictDto") MdmOrgLevelDictDto mdmOrgLevelDictDto);
}
