package com.netwisd.base.portal.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentLoginBackgroundSon;
import com.netwisd.base.portal.mapper.PortalContentLoginBackgroundSonMapper;
import com.netwisd.base.portal.service.PortalContentLoginBackgroundSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentLoginBackgroundSonDto;
import com.netwisd.base.portal.vo.PortalContentLoginBackgroundSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 登录页-子表（背景轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:03:27
 */
@Service
@Slf4j
public class PortalContentLoginBackgroundSonServiceImpl extends ServiceImpl<PortalContentLoginBackgroundSonMapper, PortalContentLoginBackgroundSon> implements PortalContentLoginBackgroundSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentLoginBackgroundSonMapper portalContentLoginBackgroundSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentLoginBackgroundSonDto
    * @return
    */
    @Override
    public Page getList(PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto) {
        LambdaQueryWrapper<PortalContentLoginBackgroundSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentLoginBackgroundSon> page = portalContentLoginBackgroundSonMapper.selectPage(portalContentLoginBackgroundSonDto.getPage(),queryWrapper);
        Page<PortalContentLoginBackgroundSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentLoginBackgroundSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentLoginBackgroundSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto) {
        Page<PortalContentLoginBackgroundSonVo> pageVo = portalContentLoginBackgroundSonMapper.getPageList(portalContentLoginBackgroundSonDto.getPage(),portalContentLoginBackgroundSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentLoginBackgroundSonVo get(Long id) {
        PortalContentLoginBackgroundSon portalContentLoginBackgroundSon = super.getById(id);
        PortalContentLoginBackgroundSonVo portalContentLoginBackgroundSonVo = null;
        if(portalContentLoginBackgroundSon !=null){
            portalContentLoginBackgroundSonVo = dozerMapper.map(portalContentLoginBackgroundSon,PortalContentLoginBackgroundSonVo.class);
        }
        log.debug("查询成功");
        return portalContentLoginBackgroundSonVo;
    }

    /**
    * 保存实体
    * @param portalContentLoginBackgroundSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto) {
        PortalContentLoginBackgroundSon portalContentLoginBackgroundSon = dozerMapper.map(portalContentLoginBackgroundSonDto,PortalContentLoginBackgroundSon.class);
        boolean result = super.save(portalContentLoginBackgroundSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentLoginBackgroundSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto) {
        portalContentLoginBackgroundSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentLoginBackgroundSon portalContentLoginBackgroundSon = dozerMapper.map(portalContentLoginBackgroundSonDto,PortalContentLoginBackgroundSon.class);
        boolean result = super.updateById(portalContentLoginBackgroundSon);
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
     * 通过关联ID查询当前参数下的所有背景轮播图
     * @param logId
     * @return
     */
    @Override
    public List<PortalContentLoginBackgroundSonVo> getListOrLogId(Long logId) {
        LambdaQueryWrapper<PortalContentLoginBackgroundSon> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ObjectUtil.isNotEmpty(logId),PortalContentLoginBackgroundSon::getLoginId,logId);
        List<PortalContentLoginBackgroundSon> list = super.list(lambdaQueryWrapper);
        List<PortalContentLoginBackgroundSonVo> portalContentLoginBackgroundSonVoList = DozerUtils.mapList(dozerMapper, list, PortalContentLoginBackgroundSonVo.class);
        return portalContentLoginBackgroundSonVoList;
    }
    /**
     * 通过管ID删除当前参数下的所有背景轮播图
     * @param logId
     * @return
     */
    @Override
    public Boolean deleteOrLogId(Long logId) {
        log.debug("通过关联id删除背景轮播图参数："+logId);
        LambdaQueryWrapper<PortalContentLoginBackgroundSon> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ObjectUtil.isNotEmpty(logId),PortalContentLoginBackgroundSon::getLoginId,logId);
        boolean remove = super.remove(lambdaQueryWrapper);
        if (remove){
            log.debug("通过关联id删除成功");
        }
        return remove;
    }
}
