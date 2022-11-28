package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.study.entity.StudyCouPerm;
import com.netwisd.biz.study.mapper.StudyCouPermMapper;
import com.netwisd.biz.study.service.StudyCouPermService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyCouPermDto;
import com.netwisd.biz.study.vo.StudyCouPermVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 课件授权表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:13:08
 */
@Service
@Slf4j
public class StudyCouPermServiceImpl extends ServiceImpl<StudyCouPermMapper, StudyCouPerm> implements StudyCouPermService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyCouPermMapper studyCouPermMapper;

    /**
    * 单表简单查询操作
    * @param studyCouPermDto
    * @return
    */
    @Override
    public Page list(StudyCouPermDto studyCouPermDto) {
        LambdaQueryWrapper<StudyCouPerm> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<StudyCouPerm> page = studyCouPermMapper.selectPage(studyCouPermDto.getPage(),queryWrapper);
        Page<StudyCouPermVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyCouPermVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyCouPermDto
    * @return
    */
    @Override
    public Page lists(StudyCouPermDto studyCouPermDto) {
        Page<StudyCouPermVo> pageVo = studyCouPermMapper.getPageList(studyCouPermDto.getPage(),studyCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyCouPermVo get(Long id) {
        StudyCouPerm studyCouPerm = super.getById(id);
        StudyCouPermVo studyCouPermVo = null;
        if(studyCouPerm !=null){
            studyCouPermVo = dozerMapper.map(studyCouPerm,StudyCouPermVo.class);
        }
        log.debug("查询成功");
        return studyCouPermVo;
    }

    /**
    * 保存实体
    * @param studyCouPermDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyCouPermDto studyCouPermDto) {
        StudyCouPerm studyCouPerm = dozerMapper.map(studyCouPermDto,StudyCouPerm.class);
        boolean result = super.save(studyCouPerm);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyCouPermDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyCouPermDto studyCouPermDto) {
        studyCouPermDto.setUpdateTime(LocalDateTime.now());
        StudyCouPerm studyCouPerm = dozerMapper.map(studyCouPermDto,StudyCouPerm.class);
        boolean result = super.updateById(studyCouPerm);
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
