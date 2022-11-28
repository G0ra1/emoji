package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.mdm.entity.MdmPostGradeDict;
import com.netwisd.base.mdm.dto.MdmPostGradeDictDto;
import com.netwisd.base.mdm.vo.MdmPostGradeDictVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 岗位职等 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:54:55
 */
@Mapper
public interface MdmPostGradeDictMapper extends BaseMapper<MdmPostGradeDict> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmPostGradeDictDto
     * @return
     */
    Page<MdmPostGradeDictVo> getPageList(Page page, @Param("mdmPostGradeDictDto") MdmPostGradeDictDto mdmPostGradeDictDto);
}
