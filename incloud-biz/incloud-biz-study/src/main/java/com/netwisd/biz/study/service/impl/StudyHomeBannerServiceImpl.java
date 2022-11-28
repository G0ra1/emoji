package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.entity.StudyHomeBanner;
import com.netwisd.biz.study.mapper.StudyHomeBannerMapper;
import com.netwisd.biz.study.service.StudyHomeBannerService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyHomeBannerDto;
import com.netwisd.biz.study.vo.StudyHomeBannerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description 在线学习轮播图表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-17 14:17:50
 */
@Service
@Slf4j
public class StudyHomeBannerServiceImpl extends ServiceImpl<StudyHomeBannerMapper, StudyHomeBanner> implements StudyHomeBannerService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyHomeBannerMapper studyHomeBannerMapper;

    /**
    * 单表简单查询操作
    * @param studyHomeBannerDto
    * @return
    */
    @Override
    public Page list(StudyHomeBannerDto studyHomeBannerDto) {
        LambdaQueryWrapper<StudyHomeBanner> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.like(ObjectUtils.isNotEmpty(studyHomeBannerDto.getHomeBannerName()),StudyHomeBanner::getHomeBannerName,studyHomeBannerDto.getHomeBannerName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyHomeBannerDto.getHomeBannerStartUsing()),StudyHomeBanner::getHomeBannerStartUsing,studyHomeBannerDto.getHomeBannerStartUsing());
        Page<StudyHomeBanner> page = studyHomeBannerMapper.selectPage(studyHomeBannerDto.getPage(),queryWrapper);
        Page<StudyHomeBannerVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyHomeBannerVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyHomeBannerDto
    * @return
    */
    @Override
    public List<StudyHomeBannerVo> lists(StudyHomeBannerDto studyHomeBannerDto) {
        LambdaQueryWrapper<StudyHomeBanner> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyHomeBanner::getHomeBannerStartUsing,1);
        List<StudyHomeBanner> studyHomeBanner = studyHomeBannerMapper.selectList(queryWrapper);
        List<StudyHomeBannerVo> StudyHomeBannerVos = DozerUtils.mapList(dozerMapper,studyHomeBanner,StudyHomeBannerVo.class);

        log.debug("查询条数:"+StudyHomeBannerVos.size());
        return StudyHomeBannerVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyHomeBannerVo get(Long id) {
        StudyHomeBanner studyHomeBanner = super.getById(id);
        StudyHomeBannerVo studyHomeBannerVo = null;
        if(studyHomeBanner !=null){
            studyHomeBannerVo = dozerMapper.map(studyHomeBanner,StudyHomeBannerVo.class);
        }
        log.debug("查询成功");
        return studyHomeBannerVo;
    }

    /**
    * 保存实体
    * @param studyHomeBannerDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyHomeBannerDto studyHomeBannerDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        studyHomeBannerDto.setCreateUserName(loginAppUser.getUserNameCh());
        StudyHomeBanner studyHomeBanner = dozerMapper.map(studyHomeBannerDto,StudyHomeBanner.class);
        boolean result = super.save(studyHomeBanner);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyHomeBannerDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyHomeBannerDto studyHomeBannerDto) {
        studyHomeBannerDto.setUpdateTime(LocalDateTime.now());
        StudyHomeBanner studyHomeBanner = dozerMapper.map(studyHomeBannerDto,StudyHomeBanner.class);
        boolean result = super.updateById(studyHomeBanner);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param ids
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            LambdaQueryWrapper<StudyHomeBanner> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(StudyHomeBanner::getId,idList);
            int delete = studyHomeBannerMapper.delete(queryWrapper);
            if (delete > 0) {
                return true;
            }
        }
        return false;
    }
}
