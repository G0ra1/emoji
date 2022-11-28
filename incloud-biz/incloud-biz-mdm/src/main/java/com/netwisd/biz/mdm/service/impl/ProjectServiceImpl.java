package com.netwisd.biz.mdm.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.constants.MdmConstants;
import com.netwisd.biz.mdm.constants.YesNo;
import com.netwisd.biz.mdm.dto.CustomerDto;
import com.netwisd.biz.mdm.dto.ProjectFileDto;
import com.netwisd.biz.mdm.entity.Customer;
import com.netwisd.biz.mdm.entity.Item;
import com.netwisd.biz.mdm.entity.Project;
import com.netwisd.biz.mdm.entity.ProjectFile;
import com.netwisd.biz.mdm.mapper.ProjectMapper;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.biz.mdm.service.ProjectFileService;
import com.netwisd.biz.mdm.service.ProjectService;
import com.netwisd.biz.mdm.vo.ProjectFileVo;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ProjectDto;
import com.netwisd.biz.mdm.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 14:30:06
 */
@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private Mapper dozerMapper;


    @Value("${spring.rocketmq.projectTopics}")
    private String projectTopics;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectFileService projectFileService;

    @Autowired
    MdmMqService mdmMqService;

    /**
    * 单表简单查询操作
    * @param projectDto
    * @return
    */
    @Override
    public Page list(ProjectDto projectDto) {
        Project project = dozerMapper.map(projectDto,Project.class);
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(projectDto.getProjectCode()),Project::getProjectCode,projectDto.getProjectCode())
                .like(ObjectUtils.isNotEmpty(projectDto.getProjectName()),Project::getProjectName,projectDto.getProjectName())
                .eq(Project::getIsDel, YesNo.NO.getCode());

        Page<Project> page = projectMapper.selectPage(projectDto.getPage(),queryWrapper);
        Page<ProjectVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ProjectVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param projectDto
    * @return
    */
    @Override
    public List<ProjectVo> lists(ProjectDto projectDto) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(projectDto.getProjectCode()),Project::getProjectCode,projectDto.getProjectCode())
                .like(ObjectUtils.isNotEmpty(projectDto.getProjectName()),Project::getProjectName,projectDto.getProjectName())
                .eq(Project::getIsDel,YesNo.NO.getCode());
        List<Project> projectList=super.list(queryWrapper);
        List<ProjectVo> projectVos=DozerUtils.mapList(dozerMapper,projectList,ProjectVo.class);
        for (ProjectVo vo: projectVos) {
            getOther(vo);
        }
        log.debug("查询条数:"+projectVos.size());
        return projectVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ProjectVo get(Long id) {
        Project project = super.getById(id);
        ProjectVo projectVo = null;
        if(project !=null){
            projectVo = dozerMapper.map(project,ProjectVo.class);
            getOther(projectVo);
        }
        log.debug("查询成功");
        return projectVo;
    }

    /**
    * 保存实体
    * @param projectDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ProjectDto projectDto) {
        LambdaQueryWrapper<Project> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(projectDto.getProjectCode()),Project::getProjectCode,projectDto.getProjectCode());
        List<Project> list=super.list(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            Project project = dozerMapper.map(projectDto,Project.class);
            boolean result = super.save(project);
            projectDto.setId(project.getId());
            deleteOther(projectDto);
            if(CollectionUtils.isNotEmpty(projectDto.getFiles())){
                saveOther(projectDto);
            }
            if(result){
                log.debug("保存成功");
            }
            return result;
        }
        log.error("此项目已存在");
        return false;

    }

    /**
    * 修改实体
    * @param projectDto
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(ProjectDto projectDto) {
        Project project = dozerMapper.map(projectDto,Project.class);
        boolean result = super.updateById(project);
        deleteOther(projectDto);
        saveOther(projectDto);
        List<Project> projectList=new ArrayList<>();
        projectList.add(super.getById(projectDto.getId()));
        String json = JSON.toJSONString(projectList);
        mdmMqService.sendRocketMq(projectTopics,MdmConstants.PROJECT,json);
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
        Project project=super.getById(id);
        ProjectDto projectDto=dozerMapper.map(project,ProjectDto.class);
        deleteOther(projectDto);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    /**
     * 同步数据
     * @param projectDtos
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public void saveList(List<ProjectDto> projectDtos){
        List<Project> projectList=new ArrayList<>();
        for (ProjectDto dto:projectDtos
             ) {
            Project project=dozerMapper.map(dto,Project.class);
            saveOrUpdate(project, Wrappers.<Project>lambdaQuery().eq(Project::getDataSourceId, dto.getDataSourceId()));
            projectList.add(project);
        }
        String json = JSON.toJSONString(projectList);
        mdmMqService.sendRocketMq(projectTopics,MdmConstants.PROJECT,json);
    }

    @Override
    public Boolean delList(List<ProjectDto> projectDtos) {
        List<String> dataSourceList=projectDtos.stream().map(ProjectDto::getDataSourceId).collect(Collectors.toList());
        Project project=new Project();
        project.setIsDel(String.valueOf(YesNo.YES.getCode()));
        Boolean result=update(project, Wrappers.<Project>lambdaQuery().in(Project::getDataSourceId, dataSourceList));
        if(result){
            List<Project> projectList=this.list(Wrappers.<Project>lambdaQuery().in(Project::getDataSourceId, dataSourceList));
            List<Long> parentIdList=projectList.stream().map(Project::getId).collect(Collectors.toList());
            return dels(parentIdList);
        }
        return true;
    }

    public Boolean dels(List<Long> parentIdList){
        Project project=new Project();
        project.setIsDel(String.valueOf(YesNo.YES.getCode()));

        Boolean result=update(project, Wrappers.<Project>lambdaQuery().in(Project::getParentId, parentIdList));
        if(result){
            List<Project> projectChildList=this.list(Wrappers.<Project>lambdaQuery().in(Project::getParentId, parentIdList));
            parentIdList=projectChildList.stream().map(Project::getId).collect(Collectors.toList());
            dels(parentIdList);
        }
        return true;
    }

    public void deleteOther(ProjectDto projectDto){
        LambdaQueryWrapper<ProjectFile> fileLambdaQueryWrapper=new LambdaQueryWrapper<>();
        fileLambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(projectDto.getProjectCode()),ProjectFile::getProjectCode,projectDto.getProjectCode());
        projectFileService.remove(fileLambdaQueryWrapper);

    }
    public void getOther(ProjectVo projectVo){
        LambdaQueryWrapper<ProjectFile> fileLambdaQueryWrapper=new LambdaQueryWrapper<>();
        fileLambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(projectVo.getProjectCode()),ProjectFile::getProjectCode,projectVo.getProjectCode());
        List<ProjectFile> files=projectFileService.list(fileLambdaQueryWrapper);
        List<ProjectFileVo> fileVos=new ArrayList<>();
        for (ProjectFile file:files
        ) {
            ProjectFileVo fileVo=dozerMapper.map(file,ProjectFileVo.class);
            fileVos.add(fileVo);
        }
        projectVo.setFiles(fileVos);
    }
    public void saveOther(ProjectDto projectDto){
        List<ProjectFileDto> fileDtos=projectDto.getFiles();
        List<ProjectFile> files=new ArrayList<>();
        for (ProjectFileDto fileDto:fileDtos) {
            ProjectFile file=dozerMapper.map(fileDto,ProjectFile.class);
            file.setProjectId(projectDto.getId());
            file.setProjectCode(projectDto.getProjectCode());
            files.add(file);
        }
        projectFileService.saveBatch(files);
    }


}
