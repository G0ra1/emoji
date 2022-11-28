package com.netwisd.base.portal.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.portal.dto.PortalPartDsDto;
import com.netwisd.base.portal.dto.PortalPortalDto;
import com.netwisd.base.portal.entity.PortalPart;
import com.netwisd.base.portal.entity.PortalPartDs;
import com.netwisd.base.portal.entity.PortalPortal;
import com.netwisd.base.portal.mapper.PortalPartDsMapper;
import com.netwisd.base.portal.mapper.PortalPartMapper;
import com.netwisd.base.portal.service.PortalPartDsService;
import com.netwisd.base.portal.service.PortalPartService;
import com.netwisd.base.portal.vo.PortalPartDsVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalPartDto;
import com.netwisd.base.portal.vo.PortalPartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 云数网讯 limengzheng@netwisd.com
 * @Description 栏目管理 功能描述...
 * @date 2021-08-13 19:27:46
 */
@Service
@Slf4j
public class PortalPartServiceImpl extends ServiceImpl<PortalPartMapper, PortalPart> implements PortalPartService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalPartMapper portalPartMapper;

    @Autowired
    private  PortalPartDsService portalPartDsService;

    @Autowired
    private PortalPartDsMapper portalPartDsMapper;
    /**
     * 单表简单查询操作
     *
     * @param portalPartDto
     * @return
     */
    @Override
    public Page list(PortalPartDto portalPartDto) {
        LambdaQueryWrapper<PortalPart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(portalPartDto.getPartName()), PortalPart::getPartName, portalPartDto.getPartName());
        queryWrapper.like(StringUtils.isNotBlank(portalPartDto.getPartTypeCode()),PortalPart::getPartTypeCode,portalPartDto.getPartTypeCode());
        queryWrapper.like(StringUtils.isNotBlank(portalPartDto.getPartTypeName()), PortalPart::getPartTypeName, portalPartDto.getPartTypeName());
        queryWrapper.like(StringUtils.isNotBlank(portalPartDto.getPortalName()), PortalPart::getPortalName, portalPartDto.getPortalName());
        Page<PortalPart> page = portalPartMapper.selectPage(portalPartDto.getPage(), queryWrapper);
        Page<PortalPartVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalPartVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 不分页集合查询(根据门户id查栏目,包括其对应的数据源信息)
     *
     * @param portalPartDto
     * @return
     */
    @Override
    public List<PortalPartVo> lists(PortalPartDto portalPartDto) {
        LambdaQueryWrapper<PortalPart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalPart::getIsEnable,YesNo.YES.code);
        queryWrapper.eq(StringUtils.isNotBlank(portalPartDto.getPartTypeCode()),PortalPart::getPartTypeCode,portalPartDto.getPartTypeCode());
        queryWrapper.eq(portalPartDto.getPortalId() != null,PortalPart::getPortalId,portalPartDto.getPortalId());
        queryWrapper.eq(portalPartDto.getPartTypeId() != null,PortalPart::getPartTypeId,portalPartDto.getPartTypeId());
        List<PortalPart> portalParts = portalPartMapper.selectList(queryWrapper);
        List<PortalPartVo> portalPartVos = DozerUtils.mapList(dozerMapper, portalParts, PortalPartVo.class);
        //查出所有的栏目对应的数据源信息，根据栏目id匹配，减少数据库查询次数，在内存中进行匹配
        List<PortalPartDs> portalPartDs = portalPartDsMapper.selectList(null);
        List<PortalPartDsVo> portalPartDsVos = DozerUtils.mapList(dozerMapper, portalPartDs, PortalPartDsVo.class);
        //根据栏目id查对应的栏目数据源信息
        for (PortalPartVo portalPartVo : portalPartVos) {
            Long id = portalPartVo.getId();//栏目id
            for (PortalPartDsVo portalPartDsVo : portalPartDsVos) {
                if (id.longValue() == portalPartDsVo.getPartId().longValue()){
                    portalPartVo.setPortalPartDsVo(portalPartDsVo);
                }
            }
        }
        log.debug("查询条数:" + portalPartVos.size());
        return portalPartVos;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public PortalPartVo get(Long id) {
        PortalPart portalPart = super.getById(id);
        //查询出对应的数据源
        LambdaQueryWrapper<PortalPartDs> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalPartDs::getPartId,id);
        PortalPartDs portalPartDs = portalPartDsMapper.selectOne(queryWrapper);
        PortalPartDsVo portalPartDsVo =null;
        if (portalPartDs != null){
            portalPartDsVo = dozerMapper.map(portalPartDs, PortalPartDsVo.class);
        }
        PortalPartVo portalPartVo = null;
        if (portalPart != null) {
            portalPartVo = dozerMapper.map(portalPart, PortalPartVo.class);
            portalPartVo.setPortalPartDsVo(portalPartDsVo);
        }
        log.debug("查询成功");
        return portalPartVo;
    }

    /**
     * 保存实体
     *
     * @param portalPartDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PortalPartDto portalPartDto) {
        PortalPart portalPart = dozerMapper.map(portalPartDto, PortalPart.class);
        //初始化点击量
        portalPart.setHits(0L);
        //同一门户下，栏目的code不能重复,查询出同一门户的栏目进行判断
        LambdaQueryWrapper<PortalPart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalPart::getPortalId,portalPartDto.getPortalId());
        List<PortalPart> portalParts = portalPartMapper.selectList(queryWrapper);
        for (PortalPart part : portalParts) {
            if (portalPartDto.getPartCode().equals(part.getPartCode())){
                throw new IncloudException("同一门户下编码重复，请修改编码");
            }
        }
        boolean result = super.save(portalPart);
        //判断是否连接数据源
        if (portalPart.getIsDs() == YesNo.YES.code){
            PortalPartDsDto portalPartDsDto = portalPartDto.getPortalPartDsDto();
            portalPartDsDto.setPartId(portalPart.getId());
            portalPartDsDto.setPartName(portalPart.getPartName());
            portalPartDsService.save(portalPartDsDto);
        }
        if (result) {
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 修改实体
     *
     * @param portalPartDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PortalPartDto portalPartDto) {
        PortalPart portalPart = dozerMapper.map(portalPartDto, PortalPart.class);
        //同一门户下，栏目的code不能重复,查询出同一门户的栏目进行判断
        LambdaQueryWrapper<PortalPart> queryWrapperPart = new LambdaQueryWrapper<>();
        queryWrapperPart.eq(PortalPart::getPortalId,portalPartDto.getPortalId());
        queryWrapperPart.eq(PortalPart::getPartCode,portalPart.getPartCode());
        //除了它自己
        queryWrapperPart.ne(PortalPart::getId,portalPartDto.getId());
        Integer count = portalPartMapper.selectCount(queryWrapperPart);
        if (count.intValue() > 0 ){
            throw new IncloudException("同一门户下编码重复，请修改编码");
        }

        //更新修改时间
        portalPart.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalPart);
        //判断是否连接数据源
        if (portalPart.getIsDs() == YesNo.YES.code){
            PortalPartDsDto portalPartDsDto = portalPartDto.getPortalPartDsDto();
            portalPartDsDto.setPartId(portalPart.getId());
            portalPartDsDto.setPartName(portalPart.getPartName());
            PortalPartDs portalPartDs = dozerMapper.map(portalPartDsDto, PortalPartDs.class);
            portalPartDsService.saveOrUpdate(portalPartDs);
        }else {
            //如果修改为不连接数据源，但原本有连接的数据源，需要把对应的数据源信息删掉
            LambdaQueryWrapper<PortalPartDs> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PortalPartDs::getPartId,portalPart.getId());
            PortalPartDs portalPartDs = portalPartDsMapper.selectOne(queryWrapper);
            if (portalPartDs != null){
                portalPartDsMapper.delete(queryWrapper);
            }
        }
        if (result) {
            log.debug("修改成功");
        }
        return result;
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        PortalPart portalPart = super.getById(id);
        //判断是否连接数据源
        if (portalPart.getIsDs() == YesNo.YES.code ){
            LambdaQueryWrapper<PortalPartDs> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PortalPartDs::getPartId,id);
            portalPartDsMapper.delete(queryWrapper);
        }
        boolean result = super.removeById(id);
        if (result) {
            log.debug("删除成功");
        }
        return result;
    }

    /**
     * 修改点击量
     *
     * @param id
     * @return Boolean
     */
    @Override
    public Boolean upHits(Long id) {
        //先通过id获取到当前点击量
        PortalPart portalPart = portalPartMapper.selectById(id);
        //创建修改方法所需要的参数
        PortalPartDto portalPartDto = new PortalPartDto();
        portalPartDto.setId(id);
        portalPartDto.setHits((portalPart.getHits()) + 1);
        Boolean update = this.update(portalPartDto);
        return update;
    }
}
