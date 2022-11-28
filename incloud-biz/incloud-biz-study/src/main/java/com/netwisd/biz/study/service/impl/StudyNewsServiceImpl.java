package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.constants.NewsTypeEnum;
import com.netwisd.biz.study.entity.StudyNews;
import com.netwisd.biz.study.mapper.StudyNewsMapper;
import com.netwisd.biz.study.service.StudyNewsService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyNewsDto;
import com.netwisd.biz.study.vo.StudyNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description 在线学习通知公告表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-15 16:37:49
 */
@Service
@Slf4j
public class StudyNewsServiceImpl extends ServiceImpl<StudyNewsMapper, StudyNews> implements StudyNewsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyNewsMapper studyNewsMapper;

    /**
    * 单表简单查询操作
    * @param studyNewsDto
    * @return
    */
    @Override
    public Page list(StudyNewsDto studyNewsDto) {
        LambdaQueryWrapper<StudyNews> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        if(!NewsTypeEnum.QB.getCode().equals(studyNewsDto.getNewsType())){
            queryWrapper.eq(StringUtils.isNotBlank(studyNewsDto.getNewsType()), StudyNews::getNewsType, studyNewsDto.getNewsType());
        }
        queryWrapper.like(ObjectUtils.isNotEmpty(studyNewsDto.getNewsTitle()), StudyNews::getNewsTitle, studyNewsDto.getNewsTitle());
        queryWrapper.orderByDesc(StudyNews::getCreateTime);
        Page<StudyNews> page = studyNewsMapper.selectPage(studyNewsDto.getPage(), queryWrapper);
        Page<StudyNewsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyNewsVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyNewsDto
    * @return
    */
    @Override
    public List<StudyNewsVo> lists(StudyNewsDto studyNewsDto) {
        LambdaQueryWrapper<StudyNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(StudyNews::getCreateTime);
        List<StudyNews> studyNews = studyNewsMapper.selectList(queryWrapper);
        List<StudyNewsVo> studyNewsVos = DozerUtils.mapList(dozerMapper, studyNews, StudyNewsVo.class);
        log.debug("查询条数:"+studyNewsVos.size());
        return studyNewsVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyNewsVo get(Long id) {
        StudyNews studyNews = super.getById(id);
        StudyNewsVo studyNewsVo = null;
        if(studyNews !=null){
            studyNewsVo = dozerMapper.map(studyNews,StudyNewsVo.class);
        }
        log.debug("查询成功");
        return studyNewsVo;
    }

    /**
    * 保存实体
    * @param studyNewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyNewsDto studyNewsDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        studyNewsDto.setCreateUserName(loginAppUser.getUserNameCh());
        StudyNews studyNews = dozerMapper.map(studyNewsDto,StudyNews.class);
        boolean result = super.save(studyNews);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyNewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyNewsDto studyNewsDto) {
        studyNewsDto.setUpdateTime(LocalDateTime.now());
        StudyNews studyNews = dozerMapper.map(studyNewsDto,StudyNews.class);
        boolean result = super.updateById(studyNews);
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
     * 修改浏览量
     * @param id id
     * @return Result
     */
    @Override
    public Boolean upHits(Long id) {
        log.info("首页通知公告浏览量:" +id);
        LambdaQueryWrapper<StudyNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(id),StudyNews::getId,id);
        StudyNews studyNews = studyNewsMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(studyNews)){
            throw new IncloudException("未查询到相关数据");
        }
        Long newsBrowse = studyNews.getNewsBrowse();
        StudyNewsDto studyNewsDto = new StudyNewsDto();
        studyNewsDto.setId(id);
        if (newsBrowse == null){
              studyNewsDto.setNewsBrowse(1L);
        }else{
              studyNewsDto.setNewsBrowse(newsBrowse + 1);
        }
        Boolean update = this.update(studyNewsDto);
        return update;
    }
}
