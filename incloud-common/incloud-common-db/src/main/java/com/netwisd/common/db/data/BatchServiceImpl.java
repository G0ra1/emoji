package com.netwisd.common.db.data;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.constants.IdTypeEnum;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * @Description: 批处理service实现
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/8 10:28 上午
 */
public class BatchServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BatchService<T>{

    private static final String sign = "idSign";

    /**
     * @param queryWrapper
     * @param entityList
     * @param batchSize
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateOrDeleteBatch(LambdaQueryWrapper<T> queryWrapper,Collection<T> entityList, int batchSize) {
        dataHandler(queryWrapper,entityList,batchSize);
        return true;
    }

    private Map<String, List<Serializable>> dataHandler(LambdaQueryWrapper<T> queryWrapper,Collection<T> entityList, int batchSize){
        List<T> insertList = new ArrayList<>();
        List<T> updateList = new ArrayList<>();
        Map<Serializable, T> deleteMap = new HashMap<>();

        Map<String, List<Serializable>> dataMap = new HashMap<>();
        List<Serializable> insertIds = new ArrayList<>();
        List<Serializable> updateIds = new ArrayList<>();
        List<Serializable> deleteIds = new ArrayList<>();
        dataMap.put(IdTypeEnum.DEL.getName(),deleteIds);
        dataMap.put(IdTypeEnum.ADD.getName(),insertIds);
        dataMap.put(IdTypeEnum.EDIT.getName(),updateIds);
        List<T> list = super.list(queryWrapper);
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "错误，当前实体查不到数据库中对应的表");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "当前实体查不到对应主键");
        if(list != null){
            for(T t: list){
                Object idVal = ReflectionKit.getFieldValue(t, keyProperty);
                if (!StringUtils.checkValNull(idVal)) {
                    deleteMap.put((Serializable) idVal,t);
                }
            }
        }

        if(entityList != null){
            for(T t: entityList){
                Object idVal = ReflectionKit.getFieldValue(t, keyProperty);
                Object identifierSign = ReflectionKit.getFieldValue(t, sign);
                if(ObjectUtil.isNotEmpty(identifierSign) && (Boolean)identifierSign){
                    //新增的
                    insertList.add(t);
                    insertIds.add((Serializable) idVal);
                }else {
                    if(ObjectUtil.isNotEmpty(deleteMap.get(idVal))){
                        deleteMap.remove(idVal);
                        updateList.add(t);
                        updateIds.add((Serializable) idVal);
                    }
                }
            }
        }
        deleteIds.addAll(deleteMap.keySet());

        if(deleteMap.size() > 0){
            this.removeByIds(deleteMap.keySet());
        }
        if(insertList.size() > 0){
            this.saveBatch(insertList,batchSize);
        }
        if(updateList.size() > 0){
            this.updateBatchById(updateList,batchSize);
        }
        return dataMap;
    }

    @Override
    public boolean saveOrUpdateOrDeleteBatch(LambdaQueryWrapper<T> queryWrapper, Collection<T> entityList) {
        return saveOrUpdateOrDeleteBatch(queryWrapper,entityList,entityList == null ? 0 : entityList.size());
    }

    @Override
    public Map<String, List<Serializable>> saveOrUpdateOrDeleteResultBatch(LambdaQueryWrapper<T> queryWrapper, Collection<T> entityList, int batchSize) {
        Map<String, List<Serializable>> map = dataHandler(queryWrapper, entityList, batchSize);
        return map;
    }

    @Override
    public Map<String, List<Serializable>> saveOrUpdateOrDeleteResultBatch(LambdaQueryWrapper<T> queryWrapper, Collection<T> entityList) {
        return saveOrUpdateOrDeleteResultBatch(queryWrapper,entityList,entityList == null ? 0 : entityList.size());
    }

}
