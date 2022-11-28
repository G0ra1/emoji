package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisVideos;
import com.netwisd.base.portal.mapper.PortalContentHisVideosMapper;
import com.netwisd.base.portal.service.PortalContentHisVideosService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisVideosDto;
import com.netwisd.base.portal.vo.PortalContentHisVideosVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description  视频类内容发布-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 02:45:42
 */
@Service
@Slf4j
public class PortalContentHisVideosServiceImpl extends ServiceImpl<PortalContentHisVideosMapper, PortalContentHisVideos> implements PortalContentHisVideosService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisVideosMapper portalContentHisVideosMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisVideosDto
    * @return
    */
    @Override
    public Page list(PortalContentHisVideosDto portalContentHisVideosDto) {
        LambdaQueryWrapper<PortalContentHisVideos> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentHisVideos> page = portalContentHisVideosMapper.selectPage(portalContentHisVideosDto.getPage(),queryWrapper);
        Page<PortalContentHisVideosVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisVideosVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisVideosDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisVideosDto portalContentHisVideosDto) {
        Page<PortalContentHisVideosVo> pageVo = portalContentHisVideosMapper.getPageList(portalContentHisVideosDto.getPage(),portalContentHisVideosDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisVideosVo get(Long id) {
        PortalContentHisVideos portalContentHisVideos = super.getById(id);
        PortalContentHisVideosVo portalContentHisVideosVo = null;
        if(portalContentHisVideos !=null){
            portalContentHisVideosVo = dozerMapper.map(portalContentHisVideos,PortalContentHisVideosVo.class);
        }
        log.debug("查询成功");
        return portalContentHisVideosVo;
    }

    /**
    * 保存实体
    * @param portalContentHisVideosDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisVideosDto portalContentHisVideosDto) {
        PortalContentHisVideos portalContentHisVideos = dozerMapper.map(portalContentHisVideosDto,PortalContentHisVideos.class);
        boolean result = super.save(portalContentHisVideos);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisVideosDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisVideosDto portalContentHisVideosDto) {
        portalContentHisVideosDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisVideos portalContentHisVideos = dozerMapper.map(portalContentHisVideosDto,PortalContentHisVideos.class);
        boolean result = super.updateById(portalContentHisVideos);
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
