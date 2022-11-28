package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.portal.entity.PortalPortal;
import com.netwisd.base.portal.entity.PortalTemplate;
import com.netwisd.base.portal.entity.PortalTheme;
import com.netwisd.base.portal.mapper.PortalPortalMapper;
import com.netwisd.base.portal.mapper.PortalTemplateMapper;
import com.netwisd.base.portal.mapper.PortalThemeMapper;
import com.netwisd.base.portal.service.PortalPortalService;
import com.netwisd.base.portal.vo.PortalEnableDataVo;
import com.netwisd.base.portal.vo.PortalTemplateVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalPortalDto;
import com.netwisd.base.portal.vo.PortalPortalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 云数网讯 limengzheng@netwisd.com
 * @Description 门户维护 功能描述...
 * @date 2021-08-11 09:50:22
 */
@Service
@Slf4j
public class PortalPortalServiceImpl extends ServiceImpl<PortalPortalMapper, PortalPortal> implements PortalPortalService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalPortalMapper portalPortalMapper;

    @Autowired
    private PortalTemplateMapper portalTemplateMapper;

    @Autowired
    private PortalThemeMapper portalThemeMapper;

    /**
     * 单表简单查询操作
     *
     * @param portalPortalDto
     * @return
     */
    @Override
    public Page list(PortalPortalDto portalPortalDto) {
        LambdaQueryWrapper<PortalPortal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(portalPortalDto.getPortalName()), PortalPortal::getPortalName, portalPortalDto.getPortalName());
        Page<PortalPortal> page = portalPortalMapper.selectPage(portalPortalDto.getPage(), queryWrapper);
        Page<PortalPortalVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalPortalVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 不分页集合查询
     *
     * @param
     * @return
     */
    @Override
    public List<PortalPortalVo> lists() {
        LambdaQueryWrapper<PortalPortal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalPortal::getIsEnable, YesNo.YES.code);
        List<PortalPortal> portalPortals = portalPortalMapper.selectList(queryWrapper);
        List<PortalPortalVo> portalPortalVos = DozerUtils.mapList(dozerMapper, portalPortals, PortalPortalVo.class);
        log.debug("查询条数:" + portalPortalVos.size());
        return portalPortalVos;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public PortalPortalVo get(Long id) {
        PortalPortal portalPortal = super.getById(id);
        PortalPortalVo portalPortalVo = null;
        if (portalPortal != null) {
            portalPortalVo = dozerMapper.map(portalPortal, PortalPortalVo.class);
        }
        log.debug("查询成功");
        return portalPortalVo;
    }

    /**
     * 保存实体
     *
     * @param portalPortalDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PortalPortalDto portalPortalDto) {
        PortalPortal portalPortal = dozerMapper.map(portalPortalDto, PortalPortal.class);
        //初始化点击量
        portalPortal.setHits(0L);
        //判断名称是否唯一
        LambdaQueryWrapper<PortalPortal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalPortal::getPortalName, portalPortal.getPortalName());
        List<PortalPortal> portalPortals = portalPortalMapper.selectList(queryWrapper);
        if (portalPortals.size() > 0) {
            throw new IncloudException("该门户名称已存在，请修改名称");
        }
        boolean result = super.save(portalPortal);
        if (result) {
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 修改实体
     *
     * @param portalPortalDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PortalPortalDto portalPortalDto) {
        PortalPortal portalPortal = dozerMapper.map(portalPortalDto, PortalPortal.class);
        //判断名称是否唯一
        LambdaQueryWrapper<PortalPortal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalPortal::getPortalName, portalPortal.getPortalName());
        //除了它自己
        queryWrapper.ne(PortalPortal::getId,portalPortalDto.getId());
        List<PortalPortal> portalPortals = portalPortalMapper.selectList(queryWrapper);
        if (portalPortals.size() > 0) {
            throw new IncloudException("该门户名称已存在，请修改名称");
        }
        //更新修改时间
        portalPortal.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalPortal);
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
        boolean result = super.removeById(id);
        if (result) {
            log.debug("删除成功");
        }
        return result;
    }

    /**
     * 设置首页
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean homePage(Long id) {
        List<PortalPortal> portalPortals = portalPortalMapper.selectList(null);
        ArrayList<PortalPortal> portals = new ArrayList<>();
        for (PortalPortal portalPortal : portalPortals) {
            if (portalPortal.getId().longValue() == id.longValue()) {
                portalPortal.setIsDefault(YesNo.YES.code);
                portals.add(portalPortal);
            } else {
                portalPortal.setIsDefault(YesNo.NO.code);
                portals.add(portalPortal);
            }
        }
        boolean result = super.updateBatchById(portals);
        if (result) {
            log.debug("设置首页成功");
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
        PortalPortal portalPortal = portalPortalMapper.selectById(id);
        //创建修改方法所需要的参数
        PortalPortalDto portalPortalDto = new PortalPortalDto();
        portalPortalDto.setId(id);
        portalPortalDto.setHits((portalPortal.getHits()) + 1);
        Boolean update = this.update(portalPortalDto);
        return update;
    }

    @Override
    public PortalTemplateVo findTemplateByPortalId(Long portalId, Integer terminal) {
        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTemplate::getPortalId,portalId);
        queryWrapper.eq(PortalTemplate::getTerminal,terminal);
        queryWrapper.eq(PortalTemplate::getStartEnable,1);
        PortalTemplate portalTemplate = portalTemplateMapper.selectOne(queryWrapper);
        return dozerMapper.map(portalTemplate,PortalTemplateVo.class);
    }

    @Override
    public PortalPortalVo getDefaultPortal() {
        LambdaQueryWrapper<PortalPortal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalPortal::getIsDefault,YesNo.YES.code);
        PortalPortal portalPortal = portalPortalMapper.selectOne(queryWrapper);
        if(null != portalPortal) {
            return dozerMapper.map(portalPortal,PortalPortalVo.class);
        } else {
            return null;
        }
    }

    @Override
    public PortalEnableDataVo getEnableData() {
        //获取当前生效的门户
        PortalPortalVo defaultPortalVo = this.getDefaultPortal();
        if (ObjectUtils.isEmpty(defaultPortalVo)) {
            throw new IncloudException("未找到生效的门户");
        }
        //通过生效的门户获取当前门户下生效的pc模板
        LambdaQueryWrapper<PortalTemplate> templateWrapper = new LambdaQueryWrapper<>();
        templateWrapper.eq(PortalTemplate::getStartEnable,YesNo.YES.code);
        templateWrapper.eq(PortalTemplate::getTerminal,0);//所属终端 pc端0 移动端1
        templateWrapper.eq(PortalTemplate::getPortalId,defaultPortalVo.getId());
        PortalTemplate portalTemplate = portalTemplateMapper.selectOne(templateWrapper);
        if (ObjectUtils.isEmpty(portalTemplate)) {
            throw new IncloudException("未找到当前门户下的生效pc模板");
        }
        //获取生效的主题
        LambdaQueryWrapper<PortalTheme> themeWrapper = new LambdaQueryWrapper<>();
        themeWrapper.eq(PortalTheme::getIsUse,YesNo.YES.code);
        PortalTheme portalTheme = portalThemeMapper.selectOne(themeWrapper);
        if (ObjectUtils.isEmpty(portalTheme)) {
            throw new IncloudException("未找到当前门户下的生效主题");
        }

        PortalEnableDataVo portalEnableDataVo = new PortalEnableDataVo();
        portalEnableDataVo.setTemplateData(portalTemplate.getTemplateData());
        portalEnableDataVo.setThemeData(portalTheme.getThemeData());

        return portalEnableDataVo;
    }
}
