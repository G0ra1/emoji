package com.netwisd.base.dict.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.dict.entity.DictItem;
import com.netwisd.base.dict.entity.DictItemVersion;
import com.netwisd.base.dict.entity.DictTree;
import com.netwisd.base.dict.entity.DictTreeVersion;
import com.netwisd.base.dict.mapper.DictItemMapper;
import com.netwisd.base.dict.mapper.DictItemVersionMapper;
import com.netwisd.base.dict.service.DictItemVersionService;
import com.netwisd.base.dict.vo.DictItemVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DictItemVersionServiceImpl extends ServiceImpl<DictItemVersionMapper, DictItemVersion> implements DictItemVersionService {

    @Autowired
    private Mapper dozerMapper;

    private final DictItemMapper dictItemMapper;

    @Override
    public List<DictItemVo> list(Long oldDictTreeId) {
        return list(Wrappers.<DictItemVersion>lambdaQuery().eq(DictItemVersion::getOldDictItemId, oldDictTreeId)
                .orderByDesc(DictItemVersion::getVersion).orderByDesc(DictItemVersion::getIsMainVersion))
                .stream()
                .map(x -> dozerMapper.map(x, DictItemVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getMaxVersion(Long oldDictTreeId) {
        return Optional.ofNullable(getOne(Wrappers.<DictItemVersion>lambdaQuery().eq(DictItemVersion::getOldDictItemId, oldDictTreeId)
                .orderByDesc(DictItemVersion::getVersion).last(" limit 1"))).orElseGet(() -> new DictItemVersion(0)).getVersion();
    }

    @Override
    @Transactional
    public void delDictItemVersion(String ids) {
        removeByIds(Arrays.asList(ids.split(",")));
    }

    @Override
    @Transactional
    public void settingMainVersion(String id) {
        DictItemVersion dictItemVersion = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("未获取到对应的版本信息"));
        //设置全部为次要版本
        update(Wrappers.<DictItemVersion>lambdaUpdate().eq(DictItemVersion::getOldDictItemId, dictItemVersion.getOldDictItemId()).set(DictItemVersion::getIsMainVersion, YesNo.NO.code));
        //设置到当前版本为主版本
        update(Wrappers.<DictItemVersion>lambdaUpdate().eq(DictItemVersion::getId, id).set(DictItemVersion::getIsMainVersion, YesNo.YES.code));
        //把当前数据更新到主表
        DictItem dictItem = dozerMapper.map(dictItemVersion, DictItem.class);
        dictItem.setId(dictItemVersion.getOldDictItemId());
        dictItemMapper.updateById(dictItem);
    }

    @Override
    @Transactional
    public void saveDictItemVersion(DictItem dictItem) {
        DictItemVersion dictItemVersion = dozerMapper.map(dictItem, DictItemVersion.class);
        dictItemVersion.setId(IdGenerator.getIdGenerator());
        dictItemVersion.setOldDictItemId(dictItem.getId());
        update(Wrappers.<DictItemVersion>lambdaUpdate().eq(DictItemVersion::getOldDictItemId, dictItemVersion.getOldDictItemId()).set(DictItemVersion::getIsMainVersion, YesNo.NO.code));
        save(dictItemVersion);
    }


}
