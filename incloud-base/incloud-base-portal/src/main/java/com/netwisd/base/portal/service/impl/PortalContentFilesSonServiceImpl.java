package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentFilesSon;
import com.netwisd.base.portal.entity.PortalContentNews;
import com.netwisd.base.portal.mapper.PortalContentFilesSonMapper;
import com.netwisd.base.portal.service.PortalContentFilesSonService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentFilesSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 10:20:31
 */
@Service
@Slf4j
public class PortalContentFilesSonServiceImpl extends ServiceImpl<PortalContentFilesSonMapper, PortalContentFilesSon> implements PortalContentFilesSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentFilesSonMapper portalContentFilesSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentFilesSonDto
    * @return
    */
    @Override
    public Page list(PortalContentFilesSonDto portalContentFilesSonDto) {
        LambdaQueryWrapper<PortalContentFilesSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentFilesSon> page = portalContentFilesSonMapper.selectPage(portalContentFilesSonDto.getPage(),queryWrapper);
        Page<PortalContentFilesSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentFilesSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentFilesSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentFilesSonDto portalContentFilesSonDto) {
        Page<PortalContentFilesSonVo> pageVo = portalContentFilesSonMapper.getPageList(portalContentFilesSonDto.getPage(),portalContentFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentFilesSonVo get(Long id) {
        PortalContentFilesSon portalContentFilesSon = super.getById(id);
        PortalContentFilesSonVo portalContentFilesSonVo = null;
        if(portalContentFilesSon !=null){
            portalContentFilesSonVo = dozerMapper.map(portalContentFilesSon,PortalContentFilesSonVo.class);
        }
        log.debug("查询成功");
        return portalContentFilesSonVo;
    }

    /**
    * 保存实体
    * @param portalContentFilesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentFilesSonDto portalContentFilesSonDto) {
        PortalContentFilesSon portalContentFilesSon = dozerMapper.map(portalContentFilesSonDto,PortalContentFilesSon.class);
        boolean result = super.save(portalContentFilesSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentFilesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentFilesSonDto portalContentFilesSonDto) {
        portalContentFilesSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentFilesSon portalContentFilesSon = dozerMapper.map(portalContentFilesSonDto,PortalContentFilesSon.class);
        boolean result = super.updateById(portalContentFilesSon);
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

    @Override
    public Boolean addHits(Long id) {
        LambdaQueryWrapper<PortalContentFilesSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentFilesSon::getId,id);
        PortalContentFilesSon portalContentFilesSon = portalContentFilesSonMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(portalContentFilesSon)) {
            throw new IncloudException("未找到相关信息,点击量无法增加");
        }
        portalContentFilesSon.setHits(portalContentFilesSon.getHits()+1);
        portalContentFilesSonMapper.updateById(portalContentFilesSon);
        return true;
    }
}
