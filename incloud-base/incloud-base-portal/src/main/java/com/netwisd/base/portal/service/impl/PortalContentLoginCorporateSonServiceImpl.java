package com.netwisd.base.portal.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentLoginBackgroundSon;
import com.netwisd.base.portal.entity.PortalContentLoginCorporateSon;
import com.netwisd.base.portal.mapper.PortalContentLoginCorporateSonMapper;
import com.netwisd.base.portal.service.PortalContentLoginCorporateSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentLoginCorporateSonDto;
import com.netwisd.base.portal.vo.PortalContentLoginCorporateSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 登录页-子表（企业文化轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:22:50
 */
@Service
@Slf4j
public class PortalContentLoginCorporateSonServiceImpl extends ServiceImpl<PortalContentLoginCorporateSonMapper, PortalContentLoginCorporateSon> implements PortalContentLoginCorporateSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentLoginCorporateSonMapper portalContentLoginCorporateSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentLoginCorporateSonDto
    * @return
    */
    @Override
    public Page list(PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto) {
        LambdaQueryWrapper<PortalContentLoginCorporateSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentLoginCorporateSon> page = portalContentLoginCorporateSonMapper.selectPage(portalContentLoginCorporateSonDto.getPage(),queryWrapper);
        Page<PortalContentLoginCorporateSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentLoginCorporateSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentLoginCorporateSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto) {
        Page<PortalContentLoginCorporateSonVo> pageVo = portalContentLoginCorporateSonMapper.getPageList(portalContentLoginCorporateSonDto.getPage(),portalContentLoginCorporateSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentLoginCorporateSonVo get(Long id) {
        PortalContentLoginCorporateSon portalContentLoginCorporateSon = super.getById(id);
        PortalContentLoginCorporateSonVo portalContentLoginCorporateSonVo = null;
        if(portalContentLoginCorporateSon !=null){
            portalContentLoginCorporateSonVo = dozerMapper.map(portalContentLoginCorporateSon,PortalContentLoginCorporateSonVo.class);
        }
        log.debug("查询成功");
        return portalContentLoginCorporateSonVo;
    }

    /**
    * 保存实体
    * @param portalContentLoginCorporateSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto) {
        PortalContentLoginCorporateSon portalContentLoginCorporateSon = dozerMapper.map(portalContentLoginCorporateSonDto,PortalContentLoginCorporateSon.class);
        boolean result = super.save(portalContentLoginCorporateSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentLoginCorporateSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto) {
        portalContentLoginCorporateSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentLoginCorporateSon portalContentLoginCorporateSon = dozerMapper.map(portalContentLoginCorporateSonDto,PortalContentLoginCorporateSon.class);
        boolean result = super.updateById(portalContentLoginCorporateSon);
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
     * 通过关联ID查询当前参数下的所有企业文化轮播图
     * @param logId
     * @return
     */
    @Override
    public List<PortalContentLoginCorporateSonVo> getListOrLogId(Long logId) {
        LambdaQueryWrapper<PortalContentLoginCorporateSon> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ObjectUtil.isNotEmpty(logId),PortalContentLoginCorporateSon::getLoginId,logId);
        List<PortalContentLoginCorporateSon> list = super.list(lambdaQueryWrapper);
        List<PortalContentLoginCorporateSonVo> portalContentLoginCorporateSonVos = DozerUtils.mapList(dozerMapper, list, PortalContentLoginCorporateSonVo.class);
        return portalContentLoginCorporateSonVos;
    }
    /**
     * 通过管ID删除当前参数下的所有企业文化轮播图
     * @param logId
     * @return
     */
    @Override
    public Boolean deleteOrLogId(Long logId) {
        log.debug("通过关联id删除当前参数下的所有轮播图参数："+logId);
        LambdaQueryWrapper<PortalContentLoginCorporateSon> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ObjectUtil.isNotEmpty(logId),PortalContentLoginCorporateSon::getLoginId,logId);
        boolean remove = super.remove(lambdaQueryWrapper);
        if (remove){
            log.debug("通过关联id删除成功");
        }
        return remove;
    }
}
