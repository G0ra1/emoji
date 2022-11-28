package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.mdm.entity.MdmOrg;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 组织 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-08-12 10:27:19
 */
@Mapper
public interface MdmOrgMapper extends BaseMapper<MdmOrg> {

    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param id
     * @param orgCodeSearch
     * @param orgNameSearch
     * @param levelSearch
     * @return
     */
    Page<MdmOrgAllVo> listAll(Page page, @Param("id") Long id, @Param("orgCodeSearch") String orgCodeSearch,
                              @Param("orgNameSearch") String orgNameSearch, @Param("levelSearch") Integer levelSearch);
}
