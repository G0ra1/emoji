package com.netwisd.base.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.dict.dto.DictItemDto;
import com.netwisd.base.dict.entity.DictItem;
import com.netwisd.base.dict.vo.DictItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {

}
