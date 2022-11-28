package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.mdm.entity.MdmPostTagDict;
import com.netwisd.base.mdm.dto.MdmPostTagDictDto;
import com.netwisd.base.mdm.vo.MdmPostTagDictVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 岗位标识字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:44:20
 */
@Mapper
public interface MdmPostTagDictMapper extends BaseMapper<MdmPostTagDict> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmPostTagDictDto
     * @return
     */
    Page<MdmPostTagDictVo> getPageList(Page page, @Param("mdmPostTagDictDto") MdmPostTagDictDto mdmPostTagDictDto);
}
