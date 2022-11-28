package com.netwisd.base.dict.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.DictTypeEnum;
import com.netwisd.base.dict.dto.DictDto;
import com.netwisd.base.dict.dto.DictItemDto;
import com.netwisd.base.dict.entity.Dict;
import com.netwisd.base.dict.entity.DictItem;
import com.netwisd.base.dict.entity.DictItemVersion;
import com.netwisd.base.dict.mapper.DictItemMapper;
import com.netwisd.base.dict.mapper.DictMapper;
import com.netwisd.base.dict.service.DictItemVersionService;
import com.netwisd.base.dict.service.DictService;
import com.netwisd.base.dict.vo.DictItemVo;
import com.netwisd.base.dict.vo.DictVo;
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
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Autowired
    private DictItemMapper dictItemMapper;

    private final DictItemVersionService dictItemVersionService;

    @Override
    public IPage queryPageList(DictDto dictDto) {
        return page(dictDto.getPage(), Wrappers.<Dict>lambdaQuery()
                .like(StrUtil.isNotEmpty(dictDto.getDictCode()), Dict::getDictCode, dictDto.getDictCode())
                .like(StrUtil.isNotEmpty(dictDto.getDictName()), Dict::getDictCode, dictDto.getDictName())
                .orderByDesc(Dict::getCreateTime));
    }

    @Override
    public boolean addDictType(DictDto dictDto) {
        Optional.ofNullable(count(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, dictDto.getDictCode()))).filter(x -> {
            if (x > 0) {
                throw new IncloudException("字典Code已经存在");
            }
            return true;
        });
        Dict dict = new Dict();
        dict.toDict(dictDto);
        return save(dict);
    }

    @Override
    @Transactional
    public boolean editDictType(DictDto dictDto) {
        Optional.ofNullable(count(Wrappers.<Dict>lambdaQuery().ne(Dict::getId, dictDto.getId()).eq(Dict::getDictCode, dictDto.getDictCode())))
                .filter(x -> {
                    if (x > 0) {
                        throw new IncloudException("字典Code已经存在");
                    }
                    return true;
                });
        Dict dict = Optional.ofNullable(getById(dictDto.getId())).orElseThrow(() -> new IncloudException("未找到要修改的字典信息"));
        dict.toDict(dictDto);
        //存在子项，字典编码更新到子项中
        DictItem dictItem = new DictItem();
        dictItem.setDictCode(dictDto.getDictCode());
        dictItemMapper.update(dictItem, Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictId, dictDto.getId()));
        return updateById(dict);
    }

    @Override
    public DictVo getDictType(Long id, String code) {
        Dict dict = StrUtil.isEmpty(code) ?
                getById(id) :
                getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, code).last("limit 1"));
        return ObjectUtil.isNull(dict) ? null : dict.toDictVo();
    }

    @Override
    @Transactional
    public boolean delDictType(String ids) {
        List<String> collect = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        //系统类字典不可删除
        Integer count = count(Wrappers.<Dict>lambdaQuery().in(Dict::getId, collect)
                .eq(Dict::getDictType, DictTypeEnum.SYSTEM.getCode()));
        if (count > 0) {
            throw new IncloudException("内置字典不可删除");
        }
        //删除字典
        removeByIds(collect);
        //删除字段项对应的版本
        String dictItemIds = dictItemMapper.selectList(Wrappers.<DictItem>lambdaQuery().in(DictItem::getDictId, collect))
                .stream()
                .map(x -> String.valueOf(x.getId()))
                .collect(Collectors.joining(","));
        this.delDictItem(dictItemIds);
        //删除字典项
        return dictItemMapper.delete(Wrappers.<DictItem>lambdaQuery().in(DictItem::getDictId, collect)) > 0;
    }

    @Override
    public IPage dictItemPage(DictItemDto dictItemDto) {
        return dictItemMapper.selectPage(dictItemDto.getPage(), Wrappers.<DictItem>lambdaQuery().eq(dictItemDto.getIsEnable() > 0, DictItem::getIsEnable, dictItemDto.getIsEnable())
                .eq(StrUtil.isNotEmpty(dictItemDto.getDictCode()), DictItem::getDictCode, dictItemDto.getDictCode())
                .eq(StrUtil.isNotEmpty(dictItemDto.getDictItemCode()), DictItem::getDictItemCode, dictItemDto.getDictItemCode())
                .like(StrUtil.isNotEmpty(dictItemDto.getDictItemName()), DictItem::getDictItemName, dictItemDto.getDictItemName())
                .orderByAsc(DictItem::getSort));
    }

    @Override
    public List<DictItemVo> dictItemList(DictItemDto dictItemDto) {
        return dictItemMapper.selectList(Wrappers.<DictItem>lambdaQuery().eq(ObjectUtil.isNotNull(dictItemDto.getDictId()), DictItem::getDictId, dictItemDto.getDictId())
                        .eq(dictItemDto.getIsEnable() > 0, DictItem::getIsEnable, dictItemDto.getIsEnable())
                        .eq(StrUtil.isNotEmpty(dictItemDto.getDictCode()), DictItem::getDictCode, dictItemDto.getDictCode())
                        .like(StrUtil.isNotEmpty(dictItemDto.getDictItemCode()), DictItem::getDictItemCode, dictItemDto.getDictItemCode())
                        .like(StrUtil.isNotEmpty(dictItemDto.getDictItemName()), DictItem::getDictItemName, dictItemDto.getDictItemName())
                        .orderByAsc(DictItem::getSort))
                .stream().map(DictItem::toDictItemVo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean addDictItem(DictItemDto dictItemDto) {
        log.info("增加字典项:{},{}", dictItemDto.getDictCode(), dictItemDto.getDictId());
        Dict dict = ObjectUtils.isNotEmpty(dictItemDto.getDictId())
                ? super.getById(dictItemDto.getDictId())
                : super.getOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, dictItemDto.getDictCode()).last("limit 1"));

        dictItemDto.setDictId(dict.getId());
        Integer count = dictItemMapper.selectCount(Wrappers.<DictItem>lambdaQuery()
                .eq(DictItem::getDictId, dictItemDto.getDictId())
                .eq(DictItem::getDictItemCode, dictItemDto.getDictItemCode()));
        if (count > 0) {
            //throw new IncloudException("字典Code已经存在,全局唯一");
            //2022-06-07 zhaixiaoliang
            throw new IncloudException("字典Code已经存在");
        }

        DictItem dictItem = new DictItem();
        dictItem.toDictItem(dictItemDto);
        dictItem.setDictCode(dict.getDictCode());
        dictItem.setVersion(dictItemVersionService.getMaxVersion(dictItem.getId()) + 1);
        dictItemVersionService.saveDictItemVersion(dictItem);
        return dictItemMapper.insert(dictItem) > 0;
    }

    @Override
    @Transactional
    public boolean editDictItem(DictItemDto dictItemDto) {
        Optional.ofNullable(dictItemDto.getDictId()).orElseThrow(() -> new IncloudException("请选择字典项"));
        Dict dict = Optional.ofNullable(getById(dictItemDto.getDictId())).orElseThrow(() -> new IncloudException("未找到要修改的字典信息"));
        DictItem dictItem = Optional.ofNullable(dictItemMapper.selectById(dictItemDto.getId())).orElseThrow(() -> new IncloudException("未找到要修改的字典信息"));
        Integer count = dictItemMapper.selectCount(Wrappers.<DictItem>lambdaQuery()
                .ne(DictItem::getId, dictItemDto.getId())
                .eq(DictItem::getDictId, dictItemDto.getDictId())
                .eq(DictItem::getDictItemCode, dictItemDto.getDictItemCode()));
        if (count > 0) {
            //throw new IncloudException("字典Code已经存在,全局唯一");
            //2022-06-07 zhaixiaoliang
            throw new IncloudException("字典Code已经存在");
        }
        dictItem.toDictItem(dictItemDto);
        dictItem.setDictCode(dict.getDictCode());
        dictItem.setVersion(dictItemVersionService.getMaxVersion(dictItem.getId()) + 1);
        dictItemVersionService.saveDictItemVersion(dictItem);
        return dictItemMapper.updateById(dictItem) > 0;
    }

    @Override
    public DictItemVo getDictItem(Long id, String code) {
        DictItem dictItem = StrUtil.isEmpty(code) ?
                dictItemMapper.selectById(id) :
                dictItemMapper.selectOne(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictItemCode, code).last("limit 1"));
        return ObjectUtil.isNull(dictItem) ? null : dictItem.toDictItemVo();
    }

    @Override
    @Transactional
    public boolean delDictItem(String ids) {
        List<String> allIds = Arrays.asList(ids.split(","));
        List<DictItem> dictItems = dictItemMapper.selectBatchIds(allIds);
        for (DictItem dictItem : dictItems) {
            dictItemVersionService.remove(Wrappers.<DictItemVersion>lambdaQuery().in(DictItemVersion::getOldDictItemId, dictItem.getId()));
        }
        return dictItemMapper.deleteBatchIds(allIds) > 1;
    }

}
