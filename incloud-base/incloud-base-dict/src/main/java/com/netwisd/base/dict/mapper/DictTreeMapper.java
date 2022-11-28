package com.netwisd.base.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.dict.entity.DictTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictTreeMapper extends BaseMapper<DictTree> {

    List<DictTreeVo> getDictTreeVo(IPage page, @Param("parentId") Long parentId);

}
