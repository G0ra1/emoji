package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisFiles;
import com.netwisd.base.portal.mapper.PortalContentHisFilesMapper;
import com.netwisd.base.portal.service.PortalContentHisFilesService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisFilesDto;
import com.netwisd.base.portal.vo.PortalContentHisFilesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 历史 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:24:56
 */
@Service
@Slf4j
public class PortalContentHisFilesServiceImpl extends ServiceImpl<PortalContentHisFilesMapper, PortalContentHisFiles> implements PortalContentHisFilesService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisFilesMapper portalContentHisFilesMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisFilesDto
    * @return
    */
    @Override
    public Page list(PortalContentHisFilesDto portalContentHisFilesDto) {
        LambdaQueryWrapper<PortalContentHisFiles> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentHisFiles> page = portalContentHisFilesMapper.selectPage(portalContentHisFilesDto.getPage(),queryWrapper);
        Page<PortalContentHisFilesVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisFilesVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisFilesDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisFilesDto portalContentHisFilesDto) {
        Page<PortalContentHisFilesVo> pageVo = portalContentHisFilesMapper.getPageList(portalContentHisFilesDto.getPage(),portalContentHisFilesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisFilesVo get(Long id) {
        PortalContentHisFiles portalContentHisFiles = super.getById(id);
        PortalContentHisFilesVo portalContentHisFilesVo = null;
        if(portalContentHisFiles !=null){
            portalContentHisFilesVo = dozerMapper.map(portalContentHisFiles,PortalContentHisFilesVo.class);
        }
        log.debug("查询成功");
        return portalContentHisFilesVo;
    }

    /**
    * 保存实体
    * @param portalContentHisFilesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisFilesDto portalContentHisFilesDto) {
        PortalContentHisFiles portalContentHisFiles = dozerMapper.map(portalContentHisFilesDto,PortalContentHisFiles.class);
        boolean result = super.save(portalContentHisFiles);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisFilesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisFilesDto portalContentHisFilesDto) {
        portalContentHisFilesDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisFiles portalContentHisFiles = dozerMapper.map(portalContentHisFilesDto,PortalContentHisFiles.class);
        boolean result = super.updateById(portalContentHisFiles);
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
