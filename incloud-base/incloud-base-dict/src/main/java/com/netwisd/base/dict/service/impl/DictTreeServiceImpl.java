package com.netwisd.base.dict.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.dict.dto.DictReceiveDto;
import com.netwisd.base.dict.entity.DictTree;
import com.netwisd.base.dict.entity.DictTreeVersion;
import com.netwisd.base.dict.mapper.DictTreeMapper;
import com.netwisd.base.dict.service.DictTreeService;
import com.netwisd.base.dict.service.DictTreeVersionService;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DictTreeServiceImpl extends ServiceImpl<DictTreeMapper, DictTree> implements DictTreeService {

    private final DictTreeVersionService dictTreeVersionService;

    @Override
    public IPage queryPageList(DictTreeDto dictTreeDto) {
        //根据parentCode获取下parentId
        if (StrUtil.isNotEmpty(dictTreeDto.getParentCode())) {
            DictTreeVo dictTree = this.getDictTree(0L, dictTreeDto.getParentCode());
            dictTreeDto.setParentId(dictTree.getId());
        }
        List<DictTreeVo> list = baseMapper.getDictTreeVo(dictTreeDto.getPage(), dictTreeDto.getParentId());
        IPage<DictTreeVo> result = new Page<>(dictTreeDto.getPage().getCurrent(), dictTreeDto.getPage().getSize(), list.size());
        result.setRecords(handleTreeDict(list));
        return result;
    }

    //根据父级code查出父级id 在线学习使用
    private void addParentId(DictTreeDto dictTreeDto){
        if (ObjectUtils.isNull(dictTreeDto.getParentId()) && StringUtils.isNotBlank(dictTreeDto.getParentCode())) {
            LambdaQueryWrapper<DictTree> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DictTree::getDictCode,dictTreeDto.getParentCode());
            DictTree parentDictTree = baseMapper.selectOne(queryWrapper);
            dictTreeDto.setParentId(parentDictTree.getId());
        }
    }

    @Override
    public List<DictTreeVo> list(DictTreeDto dictTreeDto) {
        this.addParentId(dictTreeDto);
        List<DictTreeVo> collect = list(Wrappers.<DictTree>lambdaQuery().eq(ObjectUtil.isNotNull(dictTreeDto.getParentId()), DictTree::getParentId, dictTreeDto.getParentId())
                .like(StrUtil.isNotEmpty(dictTreeDto.getDictCode()), DictTree::getDictCode, dictTreeDto.getDictCode())
                .like(StrUtil.isNotEmpty(dictTreeDto.getDictName()), DictTree::getDictName, dictTreeDto.getDictName())
                .orderByAsc(DictTree::getLevel)).stream().map(DictTree::toDictTreeVo).collect(Collectors.toList());
        return handleTreeDict(collect);
    }

    @Override
    @Transactional
    public boolean add(DictTreeDto dictTreeDto) {
        Optional.ofNullable(count(Wrappers.<DictTree>lambdaQuery().eq(DictTree::getDictCode, dictTreeDto.getDictCode()))).filter(x -> {
            if (x > 0) {
                throw new IncloudException("字典Code已经存在");
            }
            return true;
        });
        //根据parentCode获取下paretnId 注：是因为页面单独另出来维护会导致新增二级，选择一级时候获取不到parentId
        if (StrUtil.isNotEmpty(dictTreeDto.getParentCode())) {
            DictTreeVo dictTree = this.getDictTree(0L, dictTreeDto.getParentCode());
            dictTreeDto.setParentId(dictTree.getId());
        }
        DictTree dictTree = new DictTree();
        dictTree.toDictTree(dictTreeDto);
        dictTree.setLevel(returnParentLevel(dictTreeDto));
        dictTree.setVersion(dictTreeVersionService.getMaxVersion(dictTree.getId()) + 1);
        dictTreeVersionService.saveDictTreeVersion(dictTree);
        return save(dictTree);
    }

    @Override
    @Transactional
    public boolean edit(DictTreeDto dictTreeDto) {
        Optional.ofNullable(count(Wrappers.<DictTree>lambdaQuery().ne(DictTree::getId, dictTreeDto.getId())
                .eq(DictTree::getDictCode, dictTreeDto.getDictCode()))
        ).filter(x -> {
            if (x > 0) {
                throw new IncloudException("字典Code已经存在");
            }
            return true;
        });
        DictTree dictTree = Optional.ofNullable(getById(dictTreeDto.getId())).orElseThrow(() -> new IncloudException("未找到要修改的字典信息"));
        if (dictTreeDto.getId().equals(dictTreeDto.getParentId())) {
            throw new IncloudException("父级节点不能是自己");
        }
        dictTree.toDictTree(dictTreeDto);
        dictTree.setLevel(returnParentLevel(dictTreeDto));
        dictTree.setVersion(dictTreeVersionService.getMaxVersion(dictTree.getId()) + 1);
        dictTreeVersionService.saveDictTreeVersion(dictTree);
        return updateById(dictTree);
    }

    @Override
    public DictTreeVo getDictTree(Long id, String code) {
        DictTree dictTree = StrUtil.isEmpty(code) ?
                getById(id) :
                getOne(Wrappers.<DictTree>lambdaQuery().eq(DictTree::getDictCode, code).last("limit 1"));
        return ObjectUtil.isNull(dictTree) ? null : dictTree.toDictTreeVo();

    }

    @Override
    @Transactional
    public boolean del(String ids) {
        //删除子集一并删除
        List<DictTree> allDictTreeList = this.queryTreeChildIds(ids);
        List<Long> allIds = allDictTreeList.stream().map(DictTree::getId).collect(Collectors.toList());
        for (DictTree dictTree : allDictTreeList) {
            //删除字典版本信息
            dictTreeVersionService.remove(Wrappers.<DictTreeVersion>lambdaQuery().in(DictTreeVersion::getOldDictTreeId, dictTree.getId()));
        }
        return removeByIds(allIds);
    }

    @Override
    public List<DictTreeVo> childListByParentCode(String parentCode) {
        Optional<DictTree> dictTree = Optional.ofNullable(getOne(Wrappers.<DictTree>lambdaQuery().eq(DictTree::getDictCode, parentCode)
                .last("limit 1"))
        ).filter(x -> {
            if (ObjectUtil.isNull(x) || StrUtil.isEmpty(x.getDictCode())) {
                throw new IncloudException("未获取对应的信息,请检查{}是否在字典中配置", parentCode);
            }
            return true;
        });
        return handleTreeDict(list(Wrappers.<DictTree>lambdaQuery().eq(DictTree::getParentId, dictTree.get().getId()).orderByAsc(DictTree::getLevel))
                .stream()
                .map(DictTree::toDictTreeVo)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public boolean receiveTree(List<DictReceiveDto> receiveDtoList) {
        log.info("接受树形数据:{}", receiveDtoList.size());
        //1、先接受数据
        for (DictReceiveDto dictReceiveDto : receiveDtoList) {
            if (ObjectUtil.isNull(dictReceiveDto) || StrUtil.isEmpty(dictReceiveDto.getId()))
                continue;
            DictTree dictTree = new DictTree();
            dictTree.toDictTree(dictReceiveDto);
            this.saveOrUpdate(dictTree, Wrappers.<DictTree>lambdaQuery().eq(DictTree::getExtId, dictReceiveDto.getId()));
        }
        //2、处理数据
        dealWithReceiveDictTree();
        return true;
    }

    @Override
    public boolean delReceiveTree(List<DictReceiveDto> dictReceiveDtoList) {
        List<String> collect = dictReceiveDtoList.stream().map(DictReceiveDto::getFullCode).filter(x -> StrUtil.isNotEmpty(x)).collect(Collectors.toList());
        log.info("数据删除:{}", collect);
        return CollUtil.isEmpty(collect) ? false : remove(Wrappers.<DictTree>lambdaQuery().in(DictTree::getExtFullCode, collect));
    }

    //处理树形接受到的数据
    private void dealWithReceiveDictTree() {
        List<DictTree> dictTreeList = list(Wrappers.<DictTree>lambdaQuery().last("where ext_id is not null"));
        log.info("处理树形接受到的数据:{}", dictTreeList.size());
        Map<String, DictTree> groupMap = dictTreeList.stream().collect(Collectors.toMap(DictTree::getExtId, Function.identity(), (key1, key2) -> key2));
        for (DictTree dictTree : dictTreeList) {
            DictTree parentDictTree = groupMap.get(dictTree.getExtPid());
            dictTree.setParentId(ObjectUtil.isNull(parentDictTree) ? 0L : parentDictTree.getId());
        }
        updateBatchById(dictTreeList);
    }

    //构建子集数据
    private List<DictTreeVo> handleTreeDict(List<DictTreeVo> list) {
        return list.stream().map(x -> {
            //子集数据
            List<DictTreeVo> kids = Lists.newArrayList();
            List<DictTree> childDictTreeList = Lists.newArrayList();
            getChildTree(x.getId(), childDictTreeList);
            List<DictTreeVo> childDictTreeListVo = childDictTreeList.stream().sorted(Comparator.comparing(DictTree::getLevel).reversed()).map(DictTree::toDictTreeVo).collect(Collectors.toList());
            Map<Long, DictTreeVo> groupMap = childDictTreeListVo.stream().collect(Collectors.toMap(DictTreeVo::getId, Function.identity(), (key1, key2) -> key2));
            for (DictTreeVo dictTreeVo : childDictTreeListVo) {
                Long parentId = dictTreeVo.getParentId();
                DictTreeVo parentObj = groupMap.get(parentId);
                //如果从map中找到其父级，那么就把父级的list里加上自己
                if (ObjectUtil.isNotEmpty(parentObj)) {
                    //加上自己，因为是引用，所以getKids再add后，引用数据就加入进去了；
                    parentObj.getKids().add(dictTreeVo);
                    //从map里把自己干掉，下次不再处理自己——因为列表排序是按level倒序排的，前面的处理的都是最末级层的
                    groupMap.remove(dictTreeVo.getId());
                }
            }
            //最后整合完的map里，就是我们要的结果
            for (Map.Entry<Long, DictTreeVo> entry : groupMap.entrySet()) {
                kids.add(entry.getValue());
            }
            x.setHasKids(CollUtil.isEmpty(kids) ? YesNo.NO.getCode() : YesNo.YES.getCode());
            x.setKids(kids);
            return x;
        }).collect(Collectors.toList());
    }

    //更新父级数据斌并返回级别
    private Integer returnParentLevel(DictTreeDto dictTreeDto) {
        int level = 1;
        if (ObjectUtil.isNotNull(dictTreeDto.getParentId()) && 0L != dictTreeDto.getParentId()) {
            DictTree parentDictTree = Optional.ofNullable(getById(dictTreeDto.getParentId())).orElseThrow(() -> new IncloudException("未找到对于的父级字典"));
            level = parentDictTree.getLevel() + 1;
        }
        return level;
    }

    //获取子集数据并包含自己
    private List<DictTree> queryTreeChildIds(String ids) {
        List<DictTree> dictTreeList = list(Wrappers.<DictTree>lambdaQuery().in(DictTree::getId, ids.split(",")));
        for (String id : ids.split(",")) {
            getChildTree(Long.valueOf(id), dictTreeList);
        }
        return dictTreeList;
    }

    private List<DictTree> getChildTree(Long id, List<DictTree> dictTreeList) {
        List<DictTree> dataList = list(Wrappers.<DictTree>lambdaQuery().eq(DictTree::getParentId, id));
        if (CollUtil.isNotEmpty(dataList)) {
            for (DictTree dictTree : dataList) {
                dictTreeList.add(dictTree);
                if (ObjectUtil.isNotNull(dictTree.getParentId()) && 0L != dictTree.getParentId()) {
                    getChildTree(dictTree.getId(), dictTreeList);
                }
            }
        }
        return dictTreeList;
    }
}
