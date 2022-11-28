package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisVideosSon;
import com.netwisd.base.portal.mapper.PortalContentHisVideosSonMapper;
import com.netwisd.base.portal.service.PortalContentHisVideosSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentHisVideosSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description  视频类型内容发布-历史表子表  功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 14:08:44
 */
@Service
@Slf4j
public class PortalContentHisVideosSonServiceImpl extends ServiceImpl<PortalContentHisVideosSonMapper, PortalContentHisVideosSon> implements PortalContentHisVideosSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisVideosSonMapper portalContentHisVideosSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisVideosSonDto
    * @return
    */
    @Override
    public Page list(PortalContentHisVideosSonDto portalContentHisVideosSonDto) {
        LambdaQueryWrapper<PortalContentHisVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentHisVideosSon> page = portalContentHisVideosSonMapper.selectPage(portalContentHisVideosSonDto.getPage(),queryWrapper);
        Page<PortalContentHisVideosSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisVideosSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisVideosSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisVideosSonDto portalContentHisVideosSonDto) {
        Page<PortalContentHisVideosSonVo> pageVo = portalContentHisVideosSonMapper.getPageList(portalContentHisVideosSonDto.getPage(),portalContentHisVideosSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisVideosSonVo get(Long id) {
        PortalContentHisVideosSon portalContentHisVideosSon = super.getById(id);
        PortalContentHisVideosSonVo portalContentHisVideosSonVo = null;
        if(portalContentHisVideosSon !=null){
            portalContentHisVideosSonVo = dozerMapper.map(portalContentHisVideosSon,PortalContentHisVideosSonVo.class);
        }
        log.debug("查询成功");
        return portalContentHisVideosSonVo;
    }

    /**
    * 保存实体
    * @param portalContentHisVideosSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisVideosSonDto portalContentHisVideosSonDto) {
        PortalContentHisVideosSon portalContentHisVideosSon = dozerMapper.map(portalContentHisVideosSonDto,PortalContentHisVideosSon.class);
        boolean result = super.save(portalContentHisVideosSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisVideosSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisVideosSonDto portalContentHisVideosSonDto) {
        portalContentHisVideosSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisVideosSon portalContentHisVideosSon = dozerMapper.map(portalContentHisVideosSonDto,PortalContentHisVideosSon.class);
        boolean result = super.updateById(portalContentHisVideosSon);
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
