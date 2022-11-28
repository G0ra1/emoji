package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.data.FieldVm;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.biz.mdm.entity.Projectjc;
import com.netwisd.biz.mdm.mapper.ProjectjcMapper;
import com.netwisd.biz.mdm.service.ProjectjcService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ProjectjcDto;
import com.netwisd.biz.mdm.vo.ProjectjcVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.netwisd.common.db.util.EntityListConvert;
import cn.hutool.core.util.ObjectUtil;

/**
 * @Description 集采项目 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-19 14:17:49
 */
@Service
@Slf4j
public class ProjectjcServiceImpl extends BatchServiceImpl<ProjectjcMapper, Projectjc> implements ProjectjcService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ProjectjcMapper projectjcMapper;

    /**
    * 单表简单查询操作
    * @param projectjcDto
    * @return
    */
    @Override
    public Page list(ProjectjcDto projectjcDto) {
        LambdaQueryWrapper<Projectjc> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<Projectjc> page = projectjcMapper.selectPage(projectjcDto.getPage(),queryWrapper);
        Page<ProjectjcVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ProjectjcVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param projectjcDto
    * @return
    */
    @Override
    public Page lists(ProjectjcDto projectjcDto) {
        Page<ProjectjcVo> pageVo = projectjcMapper.getPageList(projectjcDto.getPage(),projectjcDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ProjectjcVo get(Long id) {
        Projectjc projectjc = super.getById(id);
        ProjectjcVo projectjcVo = null;
        if(projectjc !=null){
            projectjcVo = dozerMapper.map(projectjc,ProjectjcVo.class);
        }
        return projectjcVo;
    }

    /**
    * 保存实体
    * @param projectjcDto
    * @return
    */
    @Transactional
    @Override
    public void save(ProjectjcDto projectjcDto) {
        Projectjc projectjc = dozerMapper.map(projectjcDto,Projectjc.class);
        super.save(projectjc);
        saveSubList(projectjcDto);
    }

    /**
     * 保存集合
     * @param projectjcDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<ProjectjcDto> projectjcDtos){
        List<Projectjc> Projectjcs = DozerUtils.mapList(dozerMapper, projectjcDtos, Projectjc.class);
        super.saveBatch(Projectjcs);
        for (ProjectjcDto projectjcDto : projectjcDtos){
            saveSubList(projectjcDto);
        }
    }

    /**
     * 保存子表集合
     * @param projectjcDto
     * @return
     */
    @Transactional
    void saveSubList(ProjectjcDto projectjcDto){
    }

    /**
     * 修改实体
     * @param projectjcDto
     * @return
     */
    @Transactional
    @Override
    public void update(ProjectjcDto projectjcDto) {
        projectjcDto.setUpdateTime(LocalDateTime.now());
        Projectjc projectjc = dozerMapper.map(projectjcDto,Projectjc.class);
        super.updateById(projectjc);
        updateSub(projectjcDto);
    }

    /**
    * 修改子类实体
    * @param projectjcDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(ProjectjcDto projectjcDto) {
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public void delete(Long id) {
        super.removeById(id);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public void deleteByFkId(Long id){
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<ProjectjcVo> getByFkIdVo(Long id){
        return null;
    }
}
