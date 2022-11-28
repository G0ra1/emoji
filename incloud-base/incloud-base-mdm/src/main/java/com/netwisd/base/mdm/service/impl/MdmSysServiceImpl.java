package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.common.mdm.vo.MdmSysVo;
import com.netwisd.base.mdm.entity.MdmSys;
import com.netwisd.base.mdm.mapper.MdmSysMapper;
import com.netwisd.base.mdm.service.MdmSysService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmSysDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Service
@Slf4j
public class MdmSysServiceImpl extends ServiceImpl<MdmSysMapper, MdmSys> implements MdmSysService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmSysMapper mdmSysMapper;

    /**
    * 单表简单查询操作
    * @param mdmSysDto
    * @return
    */
    @Override
    public Page list(MdmSysDto mdmSysDto) {
        LambdaQueryWrapper<MdmSys> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(mdmSysDto.getSysName()),MdmSys::getSysName,mdmSysDto.getSysName());
        queryWrapper.eq(StringUtils.isNotBlank(mdmSysDto.getSysCode()),MdmSys::getSysCode,mdmSysDto.getSysCode());
        Page<MdmSys> page = mdmSysMapper.selectPage(mdmSysDto.getPage(),queryWrapper);
        Page<MdmSysVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmSysVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmSysDto
    * @return
    */
    @Override
    public List<MdmSysVo> lists(MdmSysDto mdmSysDto) {
        LambdaQueryWrapper<MdmSys> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(mdmSysDto.getSysName()),MdmSys::getSysName,mdmSysDto.getSysName());
        queryWrapper.eq(StringUtils.isNotBlank(mdmSysDto.getSysCode()),MdmSys::getSysCode,mdmSysDto.getSysCode());
        List<MdmSys> list = mdmSysMapper.selectList(queryWrapper);
        List<MdmSysVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmSysVo.class);
        }
        return listVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmSysVo get(Long id) {
        MdmSys mdmSys = super.getById(id);
        MdmSysVo mdmSysVo = null;
        if(mdmSys !=null){
            mdmSysVo = dozerMapper.map(mdmSys,MdmSysVo.class);
        }
        log.debug("查询成功");
        return mdmSysVo;
    }

    /**
    * 保存实体
    * @param mdmSysDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmSysDto mdmSysDto) {
        MdmSys mdmSys = dozerMapper.map(mdmSysDto,MdmSys.class);
        LambdaQueryWrapper<MdmSys> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmSys::getSysCode,mdmSysDto.getSysCode());
        List<MdmSys> list = mdmSysMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)) {
            throw new IncloudException("系统CODE重复。");
        }
        mdmSys.setSort(getMaxSort());
        boolean result = super.save(mdmSys);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmSysDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmSysDto mdmSysDto) {
        LambdaQueryWrapper<MdmSys> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmSys::getSysCode,mdmSysDto.getSysCode());
        List<MdmSys> list = mdmSysMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(list) && list.size() > 1) {
            throw new IncloudException("系统CODE重复。");
        }
        mdmSysDto.setUpdateTime(LocalDateTime.now());
        MdmSys mdmSys = dozerMapper.map(mdmSysDto,MdmSys.class);
        boolean result = super.updateById(mdmSys);
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
    public Boolean delete(String id) {
        if(StringUtils.isNotBlank(id)) {
            List<String> streamStr = Stream.of(id.split(",")).collect(Collectors.toList());
            boolean result = super.removeByIds(streamStr);
            if(result){
                log.debug("删除成功");
            }
            return result;
        } else {
            throw new IncloudException("删除子系统的id不能为空！");
        }
    }

    public Integer getMaxSort() {
        LambdaQueryWrapper<MdmSys> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(MdmSys::getSort);
        queryWrapper.last("limit 1");
        MdmSys mdmSys = mdmSysMapper.selectOne(queryWrapper);
        if(null  == mdmSys) {
            return 0;
        }
        return mdmSys.getSort();
    }
}
