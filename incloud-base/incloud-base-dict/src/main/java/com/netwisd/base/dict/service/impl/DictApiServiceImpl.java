package com.netwisd.base.dict.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.dict.entity.DictItem;
import com.netwisd.base.dict.entity.DictTree;
import com.netwisd.base.dict.mapper.DictItemMapper;
import com.netwisd.base.dict.mapper.DictTreeMapper;
import com.netwisd.base.dict.service.DictApiService;
import com.netwisd.base.dict.vo.DictItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DictApiServiceImpl extends ServiceImpl<DictTreeMapper, DictTree> implements DictApiService {

    @Autowired
    private DictItemMapper dictItemMapper;

    @Override
    public List<DictTreeVo> treeList(DictTreeDto dictTreeDto) {
        return list(Wrappers.<DictTree>lambdaQuery().in(StrUtil.isNotEmpty(dictTreeDto.getExtIds()), DictTree::getExtId, Arrays.asList(dictTreeDto.getExtIds().split(",")))
                .eq(StrUtil.isNotEmpty(dictTreeDto.getExtId()), DictTree::getExtId, dictTreeDto.getExtId())
                .eq(StrUtil.isNotEmpty(dictTreeDto.getExtPid()), DictTree::getExtPid, dictTreeDto.getExtPid())
                .eq(ObjectUtil.isNotNull(dictTreeDto.getParentId()), DictTree::getParentId, dictTreeDto.getParentId())
                .like(StrUtil.isNotEmpty(dictTreeDto.getDictCode()), DictTree::getDictCode, dictTreeDto.getDictCode())
                .like(StrUtil.isNotEmpty(dictTreeDto.getDictName()), DictTree::getDictName, dictTreeDto.getDictName())
                .orderByAsc(DictTree::getLevel)).stream().map(DictTree::toDictTreeVo).collect(Collectors.toList());
    }

    @Override
    public DictTreeVo treeDetail(Long dictId, String dictCode) {
        DictTree dictTree = StrUtil.isEmpty(dictCode) ?
                getById(dictId) :
                getOne(Wrappers.<DictTree>lambdaQuery().eq(DictTree::getDictCode, dictCode).last("limit 1"));
        return ObjectUtil.isNull(dictTree) ? null : dictTree.toDictTreeVo();
    }

    @Override
    public DictItemVo itemDetail(Long dictId, String dictItemCode) {
        DictItem dictItem = StrUtil.isEmpty(dictItemCode) ?
                dictItemMapper.selectById(dictId) :
                dictItemMapper.selectOne(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictItemCode, dictItemCode).last("limit 1"));
        return ObjectUtil.isNull(dictItem) ? null : dictItem.toDictItemVo();
    }

    /**
     * 字典项列表
     *
     * @param dictCode
     * @return
     */
    @Override
    public List<DictItemVo> itemList(String dictCode) {
        return dictItemMapper.selectList(Wrappers.<DictItem>lambdaQuery().eq(StrUtil.isNotEmpty(dictCode), DictItem::getDictCode, dictCode)
        ).stream().map(DictItem::toDictItemVo).collect(Collectors.toList());
    }
}
