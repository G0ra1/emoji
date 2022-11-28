package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.constants.isUseEnum;
import com.netwisd.base.portal.entity.PortalTheme;
import com.netwisd.base.portal.mapper.PortalThemeMapper;
import com.netwisd.base.portal.service.PortalThemeService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalThemeDto;
import com.netwisd.base.portal.vo.PortalThemeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @Description 主题管理 功能描述...
 * @date 2021-08-18 23:20:45
 */
@Service
@Slf4j
public class PortalThemeServiceImpl extends ServiceImpl<PortalThemeMapper, PortalTheme> implements PortalThemeService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalThemeMapper portalThemeMapper;

    /**
     * 单表简单查询操作
     *
     * @param portalThemeDto
     * @return
     */
    @Override
    public Page list(PortalThemeDto portalThemeDto) {

        LambdaQueryWrapper<PortalTheme> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.like(StringUtils.isNotBlank(portalThemeDto.getThemeName()), PortalTheme::getThemeName, portalThemeDto.getThemeName());

        Page<PortalTheme> page = portalThemeMapper.selectPage(portalThemeDto.getPage(), queryWrapper);
        Page<PortalThemeVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalThemeVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param portalThemeDto
     * @return
     */
    @Override
    public List<PortalThemeVo> lists(PortalThemeDto portalThemeDto) {

        LambdaQueryWrapper<PortalTheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(portalThemeDto.getThemeName()), PortalTheme::getThemeName, portalThemeDto.getThemeName());

        List<PortalTheme> portalThemes = this.list(queryWrapper);
        List<PortalThemeVo> portalThemeVos = DozerUtils.mapList(dozerMapper, portalThemes, PortalThemeVo.class);
        log.debug("查询条数:" + portalThemeVos.size());
        return portalThemeVos;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public PortalThemeVo get(Long id) {
        PortalTheme portalTheme = super.getById(id);
        PortalThemeVo portalThemeVo = null;
        if (portalTheme != null) {
            portalThemeVo = dozerMapper.map(portalTheme, PortalThemeVo.class);
        }
        log.debug("查询成功");
        return portalThemeVo;
    }

    /**
     * 保存实体
     *
     * @param portalThemeDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PortalThemeDto portalThemeDto) {
        PortalTheme portalTheme = dozerMapper.map(portalThemeDto, PortalTheme.class);
        if (isUseEnum.YES.code == portalTheme.getIsUse()) {// 校验主题如果传值为以应用，那么取消其他以应用
            checkIsUser(portalTheme.getIsUse());
        }
        boolean result = super.save(portalTheme);
        if (result) {
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 修改实体
     *
     * @param portalThemeDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PortalThemeDto portalThemeDto) {
        PortalTheme portalTheme = dozerMapper.map(portalThemeDto, PortalTheme.class);
        if (ObjectUtils.isEmpty(portalTheme.getId()) ) {
            throw new IncloudException("id不可为空");
        }
        if (isUseEnum.YES.code == portalTheme.getIsUse()) { // 校验主题如果传值为以应用，那么取消其他以应用
            checkIsUser(portalTheme.getIsUse());
        }
        portalTheme.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalTheme);
        if (result) {
            log.debug("修改成功");
        }
        return result;
    }

    /**
     * 校验主题
     *
     * @param isUser
     * @return
     */
    public void checkIsUser(int isUser) {

        LambdaQueryWrapper<PortalTheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTheme::getIsUse, isUser);
        List<PortalTheme> portalThemes = portalThemeMapper.selectList(queryWrapper);
        if (portalThemes.size() > 0) {// 如果已经存在应用
            PortalTheme portalTheme = new PortalTheme();
            portalTheme.setId(portalThemes.get(0).getId());
            portalTheme.setIsUse(isUseEnum.NO.code);
            int i = portalThemeMapper.updateById(portalTheme);
            if (i < 0) {
                throw new IncloudException("已存在应用主题，且切换失败");
            } else {
                log.debug("切换主题成功");
            }
        } else {
            log.debug("未存在以应用主题");
        }
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
     * 切换主题
     *
     * @param id
     * @return
     */
    @Override
    public Boolean switchTheme(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new IncloudException("id不可为空");
        }
        checkIsUser(isUseEnum.YES.code);
        PortalTheme portalTheme = new PortalTheme();
        portalTheme.setId(id);
        portalTheme.setIsUse(isUseEnum.YES.code);
        int i = portalThemeMapper.updateById(portalTheme);
        if (i > 0) {
            return true;
        }
        return false;
    }
}
