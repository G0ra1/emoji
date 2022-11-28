package com.netwisd.base.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.mdm.entity.MdmRoleSys;
import com.netwisd.base.mdm.mapper.MdmRoleSysMapper;
import com.netwisd.base.mdm.service.MdmRoleSysService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmRoleSysDto;
import com.netwisd.base.mdm.vo.MdmRoleSysVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 角色对应的功能权限子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-23 19:12:14
 */
@Service
@Slf4j
public class MdmRoleSysServiceImpl extends ServiceImpl<MdmRoleSysMapper, MdmRoleSys> implements MdmRoleSysService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmRoleSysMapper mdmRoleSysMapper;

    /**
    * 单表简单查询操作
    * @param mdmRoleSysDto
    * @return
    */
    @Override
    public Page list(MdmRoleSysDto mdmRoleSysDto) {
        LambdaQueryWrapper<MdmRoleSys> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmRoleSys> page = mdmRoleSysMapper.selectPage(mdmRoleSysDto.getPage(),queryWrapper);
        Page<MdmRoleSysVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmRoleSysVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmRoleSysDto
    * @return
    */
    @Override
    public Page lists(MdmRoleSysDto mdmRoleSysDto) {
        Page<MdmRoleSysVo> pageVo = mdmRoleSysMapper.getPageList(mdmRoleSysDto.getPage(),mdmRoleSysDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmRoleSysVo get(Long id) {
        MdmRoleSys mdmRoleSys = super.getById(id);
        MdmRoleSysVo mdmRoleSysVo = null;
        if(mdmRoleSys !=null){
            mdmRoleSysVo = dozerMapper.map(mdmRoleSys,MdmRoleSysVo.class);
        }
        log.debug("查询成功");
        return mdmRoleSysVo;
    }

    /**
    * 保存实体
    * @param mdmRoleSysDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmRoleSysDto mdmRoleSysDto) {
        MdmRoleSys mdmRoleSys = dozerMapper.map(mdmRoleSysDto,MdmRoleSys.class);
        boolean result = super.save(mdmRoleSys);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmRoleSysDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmRoleSysDto mdmRoleSysDto) {
        mdmRoleSysDto.setUpdateTime(LocalDateTime.now());
        MdmRoleSys mdmRoleSys = dozerMapper.map(mdmRoleSysDto,MdmRoleSys.class);
        boolean result = super.updateById(mdmRoleSys);
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

    @Override
    public int deleteByRoleId(Long id) {
        LambdaQueryWrapper<MdmRoleSys> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(MdmRoleSys::getRoleId,id);
        int line = mdmRoleSysMapper.delete(delWrapper);
        return line;
    }
}
