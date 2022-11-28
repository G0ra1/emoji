package com.netwisd.base.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.model.entity.ModelEntity;
import com.netwisd.base.model.vo.ModelEntityVo;
import com.netwisd.base.model.vo.ModelFieldVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ModelEntityMapper extends BaseMapper<ModelEntity> {

}

