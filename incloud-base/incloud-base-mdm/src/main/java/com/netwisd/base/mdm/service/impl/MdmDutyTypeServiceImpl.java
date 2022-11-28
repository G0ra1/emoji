package com.netwisd.base.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.mdm.entity.MdmDutyType;
import com.netwisd.base.mdm.mapper.MdmDutyTypeMapper;
import com.netwisd.base.mdm.service.MdmDutyTypeService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmDutyTypeDto;
import com.netwisd.base.mdm.vo.MdmDutyTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 职务分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:00:30
 */
@Service
@Slf4j
public class MdmDutyTypeServiceImpl extends ServiceImpl<MdmDutyTypeMapper, MdmDutyType> implements MdmDutyTypeService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmDutyTypeMapper mdmDutyTypeMapper;

    /**
    * 单表简单查询操作
    * @param mdmDutyTypeDto
    * @return
    */
    @Override
    public Page list(MdmDutyTypeDto mdmDutyTypeDto) {
        LambdaQueryWrapper<MdmDutyType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(mdmDutyTypeDto.getDutyTypeName()), MdmDutyType::getDutyTypeName,mdmDutyTypeDto.getDutyTypeName());
        queryWrapper.orderByDesc(MdmDutyType::getSort);
        Page<MdmDutyType> page = mdmDutyTypeMapper.selectPage(mdmDutyTypeDto.getPage(),queryWrapper);
        Page<MdmDutyTypeVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmDutyTypeVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmDutyTypeDto
    * @return
    */
    @Override
    public Page lists(MdmDutyTypeDto mdmDutyTypeDto) {
        Page<MdmDutyTypeVo> pageVo = mdmDutyTypeMapper.getPageList(mdmDutyTypeDto.getPage(),mdmDutyTypeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmDutyTypeVo get(Long id) {
        MdmDutyType mdmDutyType = super.getById(id);
        MdmDutyTypeVo mdmDutyTypeVo = null;
        if(mdmDutyType !=null){
            mdmDutyTypeVo = dozerMapper.map(mdmDutyType,MdmDutyTypeVo.class);
        }
        log.debug("查询成功");
        return mdmDutyTypeVo;
    }

    /**
    * 保存实体
    * @param mdmDutyTypeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmDutyTypeDto mdmDutyTypeDto) {
        MdmDutyType mdmDutyType = dozerMapper.map(mdmDutyTypeDto,MdmDutyType.class);
        boolean result = super.save(mdmDutyType);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmDutyTypeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmDutyTypeDto mdmDutyTypeDto) {
        mdmDutyTypeDto.setUpdateTime(LocalDateTime.now());
        MdmDutyType mdmDutyType = dozerMapper.map(mdmDutyTypeDto,MdmDutyType.class);
        boolean result = super.updateById(mdmDutyType);
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
            throw new IncloudException("删除职务类型的id不能为空！");
        }
    }
}
