package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.mdm.entity.MdmPostSequDict;
import com.netwisd.base.mdm.dto.MdmPostSequDictDto;
import com.netwisd.base.mdm.vo.MdmPostSequDictVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 岗位序列 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 11:01:12
 */
@Mapper
public interface MdmPostSequDictMapper extends BaseMapper<MdmPostSequDict> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmPostSequDictDto
     * @return
     */
    Page<MdmPostSequDictVo> getPageList(Page page, @Param("mdmPostSequDictDto") MdmPostSequDictDto mdmPostSequDictDto);
}
