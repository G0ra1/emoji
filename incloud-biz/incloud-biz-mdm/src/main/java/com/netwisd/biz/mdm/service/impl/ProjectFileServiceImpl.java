package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ProjectFile;
import com.netwisd.biz.mdm.mapper.ProjectFileMapper;
import com.netwisd.biz.mdm.service.ProjectFileService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ProjectFileDto;
import com.netwisd.biz.mdm.vo.ProjectFileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 项目附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 14:44:47
 */
@Service
@Slf4j
public class ProjectFileServiceImpl extends ServiceImpl<ProjectFileMapper, ProjectFile> implements ProjectFileService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ProjectFileMapper projectFileMapper;

    /**
    * 单表简单查询操作
    * @param projectFileDto
    * @return
    */
    @Override
    public Page list(ProjectFileDto projectFileDto) {
        ProjectFile projectFile = dozerMapper.map(projectFileDto,ProjectFile.class);
        LambdaQueryWrapper<ProjectFile> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ProjectFile> page = projectFileMapper.selectPage(projectFileDto.getPage(),queryWrapper);
        Page<ProjectFileVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ProjectFileVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param projectFileDto
    * @return
    */
    @Override
    public Page lists(ProjectFileDto projectFileDto) {
        Page<ProjectFileVo> pageVo = projectFileMapper.getPageList(projectFileDto.getPage(),projectFileDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ProjectFileVo get(Long id) {
        ProjectFile projectFile = super.getById(id);
        ProjectFileVo projectFileVo = null;
        if(projectFile !=null){
            projectFileVo = dozerMapper.map(projectFile,ProjectFileVo.class);
        }
        log.debug("查询成功");
        return projectFileVo;
    }

    /**
    * 保存实体
    * @param projectFileDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ProjectFileDto projectFileDto) {
        ProjectFile projectFile = dozerMapper.map(projectFileDto,ProjectFile.class);
        boolean result = super.save(projectFile);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param projectFileDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ProjectFileDto projectFileDto) {
        ProjectFile projectFile = dozerMapper.map(projectFileDto,ProjectFile.class);
        boolean result = super.updateById(projectFile);
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
