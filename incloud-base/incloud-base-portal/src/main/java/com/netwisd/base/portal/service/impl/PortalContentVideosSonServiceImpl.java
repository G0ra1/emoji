package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.dto.PortalContentVideosDto;
import com.netwisd.base.portal.entity.PortalContentVideos;
import com.netwisd.base.portal.entity.PortalContentVideosSon;
import com.netwisd.base.portal.mapper.PortalContentVideosSonMapper;
import com.netwisd.base.portal.service.PortalContentVideosSonService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentVideosSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description  视频类型内容发布子表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-05 21:48:10
 */
@Service
@Slf4j
public class PortalContentVideosSonServiceImpl extends ServiceImpl<PortalContentVideosSonMapper, PortalContentVideosSon> implements PortalContentVideosSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentVideosSonMapper portalContentVideosSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentVideosSonDto
    * @return
    */
    @Override
    public Page list(PortalContentVideosSonDto portalContentVideosSonDto) {
        LambdaQueryWrapper<PortalContentVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentVideosSon> page = portalContentVideosSonMapper.selectPage(portalContentVideosSonDto.getPage(),queryWrapper);
        Page<PortalContentVideosSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentVideosSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentVideosSonDto
    * @return
    */
    @Override
    public List<PortalContentVideosSonVo> lists(PortalContentVideosSonDto portalContentVideosSonDto) {
        LambdaQueryWrapper<PortalContentVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        List<PortalContentVideosSon> portalContentVideosSons = portalContentVideosSonMapper.selectList(queryWrapper);
        List<PortalContentVideosSonVo> portalContentVideosSonVos = DozerUtils.mapList(dozerMapper, portalContentVideosSons, PortalContentVideosSonVo.class);
        log.debug("查询条数:"+portalContentVideosSonVos.size());
        return portalContentVideosSonVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentVideosSonVo get(Long id) {
        PortalContentVideosSon portalContentVideosSon = super.getById(id);
        PortalContentVideosSonVo portalContentVideosSonVo = null;
        if(portalContentVideosSon !=null){
            portalContentVideosSonVo = dozerMapper.map(portalContentVideosSon,PortalContentVideosSonVo.class);
        }
        log.debug("查询成功");
        return portalContentVideosSonVo;
    }

    /**
    * 保存实体
    * @param portalContentVideosSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentVideosSonDto portalContentVideosSonDto) {
        PortalContentVideosSon portalContentVideosSon = dozerMapper.map(portalContentVideosSonDto,PortalContentVideosSon.class);
        boolean result = super.save(portalContentVideosSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentVideosSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentVideosSonDto portalContentVideosSonDto) {
        portalContentVideosSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentVideosSon portalContentVideosSon = dozerMapper.map(portalContentVideosSonDto,PortalContentVideosSon.class);
        boolean result = super.updateById(portalContentVideosSon);
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
    /**
     * 通过id修改点击量
     *
     * @param id
     * @return
     */
    public Boolean upHits(Long id) {
        LambdaQueryWrapper<PortalContentVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), PortalContentVideosSon::getLinkVideosId, id);
        PortalContentVideosSon portalContentVideosSon = portalContentVideosSonMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(portalContentVideosSon)) {
            throw new IncloudException("为查询到相关数据");
        }
        Long hits = portalContentVideosSon.getHits();
        PortalContentVideosSonDto portalContentVideosSonDto = new PortalContentVideosSonDto();
        portalContentVideosSonDto.setId(id);
        portalContentVideosSonDto.setHits(hits + 1);
        Boolean update = this.update(portalContentVideosSonDto);
        return update;
    }
}
