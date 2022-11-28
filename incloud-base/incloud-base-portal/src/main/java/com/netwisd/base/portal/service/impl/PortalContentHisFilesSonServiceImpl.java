package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisFilesSon;
import com.netwisd.base.portal.mapper.PortalContentHisFilesSonMapper;
import com.netwisd.base.portal.service.PortalContentHisFilesSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentHisFilesSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 历史 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:26:04
 */
@Service
@Slf4j
public class PortalContentHisFilesSonServiceImpl extends ServiceImpl<PortalContentHisFilesSonMapper, PortalContentHisFilesSon> implements PortalContentHisFilesSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisFilesSonMapper portalContentHisFilesSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisFilesSonDto
    * @return
    */
    @Override
    public Page list(PortalContentHisFilesSonDto portalContentHisFilesSonDto) {
        LambdaQueryWrapper<PortalContentHisFilesSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentHisFilesSon> page = portalContentHisFilesSonMapper.selectPage(portalContentHisFilesSonDto.getPage(),queryWrapper);
        Page<PortalContentHisFilesSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisFilesSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisFilesSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisFilesSonDto portalContentHisFilesSonDto) {
        Page<PortalContentHisFilesSonVo> pageVo = portalContentHisFilesSonMapper.getPageList(portalContentHisFilesSonDto.getPage(),portalContentHisFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisFilesSonVo get(Long id) {
        PortalContentHisFilesSon portalContentHisFilesSon = super.getById(id);
        PortalContentHisFilesSonVo portalContentHisFilesSonVo = null;
        if(portalContentHisFilesSon !=null){
            portalContentHisFilesSonVo = dozerMapper.map(portalContentHisFilesSon,PortalContentHisFilesSonVo.class);
        }
        log.debug("查询成功");
        return portalContentHisFilesSonVo;
    }

    /**
    * 保存实体
    * @param portalContentHisFilesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisFilesSonDto portalContentHisFilesSonDto) {
        PortalContentHisFilesSon portalContentHisFilesSon = dozerMapper.map(portalContentHisFilesSonDto,PortalContentHisFilesSon.class);
        boolean result = super.save(portalContentHisFilesSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisFilesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisFilesSonDto portalContentHisFilesSonDto) {
        portalContentHisFilesSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisFilesSon portalContentHisFilesSon = dozerMapper.map(portalContentHisFilesSonDto,PortalContentHisFilesSon.class);
        boolean result = super.updateById(portalContentHisFilesSon);
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
