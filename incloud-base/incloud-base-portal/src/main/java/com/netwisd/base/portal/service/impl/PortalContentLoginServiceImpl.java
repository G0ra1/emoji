package com.netwisd.base.portal.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.portal.constants.isDefault;
import com.netwisd.base.portal.constants.isEnable;
import com.netwisd.base.portal.entity.PortalContentLogin;
import com.netwisd.base.portal.entity.PortalContentLoginBackgroundSon;
import com.netwisd.base.portal.entity.PortalContentLoginCorporateSon;
import com.netwisd.base.portal.mapper.PortalContentLoginMapper;
import com.netwisd.base.portal.service.PortalContentLoginBackgroundSonService;
import com.netwisd.base.portal.service.PortalContentLoginCorporateSonService;
import com.netwisd.base.portal.service.PortalContentLoginService;
import com.netwisd.base.portal.vo.PortalContentLoginBackgroundSonVo;
import com.netwisd.base.portal.vo.PortalContentLoginCorporateSonVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentLoginDto;
import com.netwisd.base.portal.vo.PortalContentLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 云数网讯 cuiran@netwisd.com
 * @Description 登录页设置表 功能描述...
 * @date 2021-12-27 16:36:19
 */
@Service
@Slf4j
public class PortalContentLoginServiceImpl extends ServiceImpl<PortalContentLoginMapper, PortalContentLogin> implements PortalContentLoginService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentLoginMapper portalContentLoginMapper;

    @Autowired
    private PortalContentLoginBackgroundSonService portalContentLoginBackgroundSonService;

    @Autowired
    private PortalContentLoginCorporateSonService portalContentLoginCorporateSonService;

    /**
     * 单表简单查询操作
     *
     * @param portalContentLoginDto
     * @return
     */
    @Override
    public Page list(PortalContentLoginDto portalContentLoginDto) {
        LambdaQueryWrapper<PortalContentLogin> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtil.isNotEmpty(portalContentLoginDto.getIsDefault()), PortalContentLogin::getIsDefault, portalContentLoginDto.getIsDefault());
        queryWrapper.eq(ObjectUtil.isNotEmpty(portalContentLoginDto.getIsEnable()), PortalContentLogin::getIsEnable, portalContentLoginDto.getIsEnable());
        queryWrapper.like(StringUtils.isNotBlank(portalContentLoginDto.getSubjectName()), PortalContentLogin::getSubjectName, portalContentLoginDto.getSubjectName());
        Page<PortalContentLogin> page = portalContentLoginMapper.selectPage(portalContentLoginDto.getPage(), queryWrapper);
        Page<PortalContentLoginVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentLoginVo.class);


        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param portalContentLoginDto
     * @return
     */
    @Override
    public Page lists(PortalContentLoginDto portalContentLoginDto) {
        Page<PortalContentLoginVo> pageVo = portalContentLoginMapper.getPageList(portalContentLoginDto.getPage(), portalContentLoginDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public PortalContentLoginVo get(Long id) {
        log.debug("登录页设置详情参数：" + id);
        PortalContentLogin portalContentLogin = super.getById(id);
        PortalContentLoginVo portalContentLoginVo = null;
        if (portalContentLogin != null) {
            portalContentLoginVo = dozerMapper.map(portalContentLogin, PortalContentLoginVo.class);
            List<PortalContentLoginBackgroundSonVo> portalContentLoginBackgroundSonVoList = portalContentLoginBackgroundSonService.getListOrLogId(id);
            if (portalContentLoginBackgroundSonVoList.size() > 0) {
                portalContentLoginVo.setPortalContentLoginBackgroundSons(portalContentLoginBackgroundSonVoList);
            }
            List<PortalContentLoginCorporateSonVo> portalContentLoginCorporateSonVoList = portalContentLoginCorporateSonService.getListOrLogId(id);
            if (portalContentLoginCorporateSonVoList.size() > 0) {
                portalContentLoginVo.setPortalContentLoginCorporateSons(portalContentLoginCorporateSonVoList);
            }
        }
        log.debug("查询成功");
        return portalContentLoginVo;
    }

    /**
     * 保存实体
     *
     * @param portalContentLoginDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PortalContentLoginDto portalContentLoginDto) {
        if (ObjectUtil.isEmpty(portalContentLoginDto)) {
            throw new IncloudException("登录页设置参数不可为空！");
        }
        log.debug("登录页设置新增参数：" + portalContentLoginDto.toString());
        PortalContentLogin portalContentLogin = dozerMapper.map(portalContentLoginDto, PortalContentLogin.class);
        //系只能操作下 主题仅仅只为自定义的，不可设置为默认
        portalContentLogin.setIsDefault(isDefault.NO.code);
        boolean result = super.save(portalContentLogin);
        if (result) {
            log.debug("保存成功");
            if (CollectionUtils.isNotEmpty(portalContentLoginDto.getPortalContentLoginCorporateSons())) {
                List<PortalContentLoginCorporateSon> portalContentLoginCorporateSons = DozerUtils.mapList(dozerMapper, portalContentLoginDto.getPortalContentLoginCorporateSons(), PortalContentLoginCorporateSon.class);
                for (PortalContentLoginCorporateSon p : portalContentLoginCorporateSons) {
                    p.setLoginId(portalContentLogin.getId());
                    log.debug("登录页设置新增企业文化轮播图参数：" + p.toString());
                }
                boolean b = portalContentLoginCorporateSonService.saveBatch(portalContentLoginCorporateSons);
                if (!b) {
                    throw new IncloudException("登录页企业文化图保存失败！");
                }
            }
            if (CollectionUtils.isNotEmpty(portalContentLoginDto.getPortalContentLoginBackgroundSons())) {
                List<PortalContentLoginBackgroundSon> portalContentLoginBackgroundSons = DozerUtils.mapList(dozerMapper, portalContentLoginDto.getPortalContentLoginBackgroundSons(), PortalContentLoginBackgroundSon.class);
                for (PortalContentLoginBackgroundSon p : portalContentLoginBackgroundSons) {
                    p.setLoginId(portalContentLogin.getId());
                    log.debug("登录页设置新增背景轮播图参数：" + p.toString());
                }
                boolean b = portalContentLoginBackgroundSonService.saveBatch(portalContentLoginBackgroundSons);
                if (!b) {
                    throw new IncloudException("登录页背景图保存失败！");
                }
            }
        } else {
            throw new IncloudException("登录页元素设置保存失败！");
        }

        return result;
    }

    /**
     * 修改实体
     *
     * @param portalContentLoginDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PortalContentLoginDto portalContentLoginDto) {
        if (ObjectUtil.isEmpty(portalContentLoginDto)) {
            throw new IncloudException("登录页设置参数不可为空！");
        }
        log.debug("登录页设置修改参数：" + portalContentLoginDto.toString());
        //校验要操作的主题是否时默认的
        isDefault(portalContentLoginDto.getId());
        portalContentLoginDto.setUpdateTime(LocalDateTime.now());
        PortalContentLogin portalContentLogin = dozerMapper.map(portalContentLoginDto, PortalContentLogin.class);

        boolean result = super.updateById(portalContentLogin);
        if (result) {
            log.debug("登录页-主表修改成功");
            if (CollectionUtils.isNotEmpty(portalContentLoginDto.getPortalContentLoginCorporateSons())) {
                List<PortalContentLoginCorporateSon> portalContentLoginCorporateSons = DozerUtils.mapList(dozerMapper, portalContentLoginDto.getPortalContentLoginCorporateSons(), PortalContentLoginCorporateSon.class);
                for (PortalContentLoginCorporateSon p : portalContentLoginCorporateSons) {
                    p.setLoginId(portalContentLogin.getId());
                    log.debug("登录页设置修改企业文化轮播图参数：" + p.toString());
                }
                boolean b = portalContentLoginCorporateSonService.updateBatchById(portalContentLoginCorporateSons);
                if (!b) {
                    throw new IncloudException("登录页企业文化图修改失败！");
                }
            }
            if (CollectionUtils.isNotEmpty(portalContentLoginDto.getPortalContentLoginBackgroundSons())) {
                List<PortalContentLoginBackgroundSon> portalContentLoginBackgroundSons = DozerUtils.mapList(dozerMapper, portalContentLoginDto.getPortalContentLoginBackgroundSons(), PortalContentLoginBackgroundSon.class);
                for (PortalContentLoginBackgroundSon p : portalContentLoginBackgroundSons) {
                    p.setLoginId(portalContentLogin.getId());
                    log.debug("登录页设置修改背景轮播图参数：" + p.toString());
                }
                boolean b = portalContentLoginBackgroundSonService.updateBatchById(portalContentLoginBackgroundSons);
                if (!b) {
                    throw new IncloudException("登录页背景图修改失败！");
                }
            }
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
        log.debug("登录页设置新增背景轮播图参数：" + id);
        //校验要操作的主题是否时默认的
        isDefault(id);
        boolean result = super.removeById(id);
        if (result) {
            log.debug("删除成功");
            Boolean aBoolean = portalContentLoginBackgroundSonService.deleteOrLogId(id);
            if (!aBoolean) {
                throw new IncloudException("通过关联id删除背景轮播图失败！");
            }
            Boolean aBoolean1 = portalContentLoginCorporateSonService.deleteOrLogId(id);
            if (!aBoolean1) {
                throw new IncloudException("通过关联id删除企业文化轮播图失败！");
            }
        }
        return result;
    }

    /**
     * 登录时调用
     *
     * @return
     */
    @Override
    public PortalContentLoginVo getLog() {
        log.debug("登录使用");
        LambdaQueryWrapper<PortalContentLogin> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(PortalContentLogin::getIsEnable, isEnable.YES.code);//启用
        PortalContentLogin portalContentLogin = super.getOne(lambdaQueryWrapper);
       if (ObjectUtil.isNotEmpty(portalContentLogin)){
           PortalContentLoginVo portalContentLoginVo = dozerMapper.map(portalContentLogin, PortalContentLoginVo.class);
           //企业文化轮播图
           List<PortalContentLoginCorporateSonVo> listOrLogId = portalContentLoginCorporateSonService.getListOrLogId(portalContentLogin.getId());
           portalContentLoginVo.setPortalContentLoginCorporateSons(listOrLogId);
           //背景轮播图
           List<PortalContentLoginBackgroundSonVo> listOrLogId1 = portalContentLoginBackgroundSonService.getListOrLogId(portalContentLogin.getId());
           portalContentLoginVo.setPortalContentLoginBackgroundSons(listOrLogId1);
           return portalContentLoginVo;
       }
     return new PortalContentLoginVo();
    }

    /**
     * 设置为启用
     *
     * @param id
     * @return
     */
    @Override
    public Boolean isEnable(Long id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new IncloudException("参数不可为空！");
        }
        Boolean aBoolean = notEnable();
        Boolean update = false;
        if (aBoolean) {
            LambdaUpdateWrapper<PortalContentLogin> lambdaUpdateWrapper = new LambdaUpdateWrapper();
            lambdaUpdateWrapper.eq(PortalContentLogin::getId, id);
            lambdaUpdateWrapper.set(PortalContentLogin::getIsEnable, isEnable.YES.code);
            update = super.update(lambdaUpdateWrapper);
            if (update) {
                log.debug("设置《" + id + "》启用成功！");
            }
        }
        return update;
    }

    //将已经启用的设置为禁用
    public Boolean notEnable() {
        LambdaQueryWrapper<PortalContentLogin> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(PortalContentLogin::getIsEnable, isEnable.YES.code);

        PortalContentLogin one = portalContentLoginMapper.selectOne(lambdaQueryWrapper);
        if (ObjectUtil.isEmpty(one)) {
            log.debug("数据存在异常！需调整");//todo 暂无控制权限，所以写死（如果数据中没有启用的主题，就直接将当前的主题设置为启用 ）
            return true;
        }
        LambdaUpdateWrapper<PortalContentLogin> lambdaUpdateWrapper1 = new LambdaUpdateWrapper();
        lambdaUpdateWrapper1.eq(PortalContentLogin::getId, one.getId());
        lambdaUpdateWrapper1.set(PortalContentLogin::getIsEnable, isEnable.NO.code);
        boolean update = super.update(lambdaUpdateWrapper1);
        if (update) {
            log.debug("将启用的《" + one.getId() + "》设置为禁用成功！");
        } else {
            throw new IncloudException("启用失败,请稍后再试");
        }
        return update;
    }

    //判断时否是默认主题
    public void isDefault(Long id) {
        log.debug("判断《" + id + "》是否是默认主题");
        PortalContentLoginVo portalContentLoginVo = this.get(id);
        if (ObjectUtil.isNotEmpty(portalContentLoginVo)) {
            if (portalContentLoginVo.getIsDefault().equals(isDefault.YES.code)) {
                throw new IncloudException("改主题不可操作");
            }
        } else {
            throw new IncloudException("该主题不存在,刷新后在操作！");
        }

    }
}
