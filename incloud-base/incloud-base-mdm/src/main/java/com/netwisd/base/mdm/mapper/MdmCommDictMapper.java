package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.mdm.entity.MdmCommDict;
import com.netwisd.base.mdm.dto.MdmCommDictDto;
import com.netwisd.base.mdm.vo.MdmCommDictVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description mdm通用字典  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-16 15:58:36
 */
@Mapper
public interface MdmCommDictMapper extends BaseMapper<MdmCommDict> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmCommDictDto
     * @return
     */
    Page<MdmCommDictVo> getPageList(Page page, @Param("mdmCommDictDto") MdmCommDictDto mdmCommDictDto);
}
