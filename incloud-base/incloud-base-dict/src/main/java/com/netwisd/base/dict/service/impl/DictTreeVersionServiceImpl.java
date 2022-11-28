package com.netwisd.base.dict.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.dict.entity.DictTree;
import com.netwisd.base.dict.entity.DictTreeVersion;
import com.netwisd.base.dict.mapper.DictTreeMapper;
import com.netwisd.base.dict.mapper.DictTreeVersionMapper;
import com.netwisd.base.dict.service.DictTreeVersionService;
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
public class DictTreeVersionServiceImpl extends ServiceImpl<DictTreeVersionMapper, DictTreeVersion> implements DictTreeVersionService {

    @Autowired
    private Mapper dozerMapper;

    private final DictTreeMapper dictTreeMapper;

    @Override
    public List<DictTreeVo> list(Long oldDictTreeId) {
        return list(Wrappers.<DictTreeVersion>lambdaQuery().eq(DictTreeVersion::getOldDictTreeId, oldDictTreeId)
                .orderByDesc(DictTreeVersion::getVersion).orderByDesc(DictTreeVersion::getIsMainVersion))
                .stream()
                .map(x -> dozerMapper.map(x, DictTreeVo.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取最大版本号
     *
     * @param oldDictTreeId
     * @return
     */
    @Override
    public int getMaxVersion(Long oldDictTreeId) {
        return Optional.ofNullable(getOne(Wrappers.<DictTreeVersion>lambdaQuery().eq(DictTreeVersion::getOldDictTreeId, oldDictTreeId)
                .orderByDesc(DictTreeVersion::getVersion).last(" limit 1"))).orElseGet(() -> new DictTreeVersion(0)).getVersion();
    }

    @Override
    @Transactional
    public void delDictVersion(String ids) {
        removeByIds(Arrays.asList(ids.split(",")));
    }

    @Override
    @Transactional
    public void settingMainVersion(String id) {
        //要设置为生效版本
        DictTreeVersion dictTreeVersion = Optional.ofNullable(getById(id)).orElseThrow(() -> new IncloudException("未获取到对应的版本信息"));
        //设置全部为次要版本
        update(Wrappers.<DictTreeVersion>lambdaUpdate().eq(DictTreeVersion::getOldDictTreeId, dictTreeVersion.getOldDictTreeId()).set(DictTreeVersion::getIsMainVersion, YesNo.NO.code));
        //设置到当前版本为主版本
        update(Wrappers.<DictTreeVersion>lambdaUpdate().eq(DictTreeVersion::getId, id).set(DictTreeVersion::getIsMainVersion, YesNo.YES.code));
        //把当前数据更新到主表
        DictTree dictTree = dozerMapper.map(dictTreeVersion, DictTree.class);
        dictTree.setId(dictTreeVersion.getOldDictTreeId());
        dictTreeMapper.updateById(dictTree);
    }

    @Override
    @Transactional
    public void saveDictTreeVersion(DictTree dictTree) {
        DictTreeVersion dictTreeVersion = dozerMapper.map(dictTree, DictTreeVersion.class);
        dictTreeVersion.setId(IdGenerator.getIdGenerator());
        dictTreeVersion.setOldDictTreeId(dictTree.getId());
        update(Wrappers.<DictTreeVersion>lambdaUpdate().eq(DictTreeVersion::getOldDictTreeId, dictTreeVersion.getOldDictTreeId()).set(DictTreeVersion::getIsMainVersion, YesNo.NO.code));
        save(dictTreeVersion);
    }
}
