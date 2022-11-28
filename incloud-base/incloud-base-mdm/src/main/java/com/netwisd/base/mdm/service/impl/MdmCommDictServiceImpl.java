package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.mdm.entity.MdmCommDict;
import com.netwisd.base.mdm.mapper.MdmCommDictMapper;
import com.netwisd.base.mdm.service.MdmCommDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmCommDictDto;
import com.netwisd.base.mdm.vo.MdmCommDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description mdm通用字典  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-16 15:58:36
 */
@Service
@Slf4j
public class MdmCommDictServiceImpl extends ServiceImpl<MdmCommDictMapper, MdmCommDict> implements MdmCommDictService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmCommDictMapper mdmCommDictMapper;

    /**
    * 单表简单查询操作
    * @param mdmCommDictDto
    * @return
    */
    @Override
    public Page list(MdmCommDictDto mdmCommDictDto) {
        LambdaQueryWrapper<MdmCommDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(mdmCommDictDto.getDictTypeId()),MdmCommDict::getDictTypeId,mdmCommDictDto.getDictTypeId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmCommDictDto.getDictCode()),MdmCommDict::getDictCode,mdmCommDictDto.getDictCode());
        queryWrapper.eq(StringUtils.isNotBlank(mdmCommDictDto.getDictName()),MdmCommDict::getDictName,mdmCommDictDto.getDictName());
        Page<MdmCommDict> page = mdmCommDictMapper.selectPage(mdmCommDictDto.getPage(),queryWrapper);
        Page<MdmCommDictVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmCommDictVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmCommDictDto
    * @return
    */
    @Override
    public List<MdmCommDictVo> lists(MdmCommDictDto mdmCommDictDto) {
        LambdaQueryWrapper<MdmCommDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(mdmCommDictDto.getDictTypeId()),MdmCommDict::getDictTypeId,mdmCommDictDto.getDictTypeId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmCommDictDto.getDictCode()),MdmCommDict::getDictCode,mdmCommDictDto.getDictCode());
        queryWrapper.eq(StringUtils.isNotBlank(mdmCommDictDto.getDictName()),MdmCommDict::getDictName,mdmCommDictDto.getDictName());
        List<MdmCommDict> list = mdmCommDictMapper.selectList(queryWrapper);
        List<MdmCommDictVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmCommDictVo.class);
        }
        return listVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmCommDictVo get(Long id) {
        MdmCommDict mdmCommDict = super.getById(id);
        MdmCommDictVo mdmCommDictVo = null;
        if(mdmCommDict !=null){
            mdmCommDictVo = dozerMapper.map(mdmCommDict,MdmCommDictVo.class);
        }
        log.debug("查询成功");
        return mdmCommDictVo;
    }

    /**
    * 保存实体
    * @param mdmCommDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmCommDictDto mdmCommDictDto) {
        MdmCommDict mdmCommDict = dozerMapper.map(mdmCommDictDto,MdmCommDict.class);
        boolean result = super.save(mdmCommDict);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmCommDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmCommDictDto mdmCommDictDto) {
        mdmCommDictDto.setUpdateTime(LocalDateTime.now());
        MdmCommDict mdmCommDict = dozerMapper.map(mdmCommDictDto,MdmCommDict.class);
        boolean result = super.updateById(mdmCommDict);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
