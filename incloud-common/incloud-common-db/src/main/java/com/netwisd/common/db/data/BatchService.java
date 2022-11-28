package com.netwisd.common.db.data;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 批处理service
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/8 10:26 上午
 */
public interface BatchService<T> extends IService<T> {
    boolean saveOrUpdateOrDeleteBatch(LambdaQueryWrapper<T> queryWrapper,Collection<T> entityList, int batchSize);
    boolean saveOrUpdateOrDeleteBatch(LambdaQueryWrapper<T> queryWrapper,Collection<T> entityList);

    Map<String, List<Serializable>> saveOrUpdateOrDeleteResultBatch(LambdaQueryWrapper<T> queryWrapper, Collection<T> entityList, int batchSize);
    Map<String, List<Serializable>> saveOrUpdateOrDeleteResultBatch(LambdaQueryWrapper<T> queryWrapper,Collection<T> entityList);
}
