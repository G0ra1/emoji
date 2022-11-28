package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.dto.MdmOrgLevelDictDto;
import com.netwisd.base.common.mdm.vo.MdmOrgLevelDictVo;
import com.netwisd.base.mdm.entity.MdmOrgLevelDict;
import com.netwisd.base.mdm.mapper.MdmOrgLevelDictMapper;
import com.netwisd.base.mdm.service.MdmOrgLevelDictService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 组织级别类型字典 功能描述...
 * @author 云数网讯 zouliming@netwisd.com@netwisd.com
 * @date 2021-08-26 09:56:26
 */
@Service
@Slf4j
public class MdmOrgLevelDictServiceImpl extends ServiceImpl<MdmOrgLevelDictMapper, MdmOrgLevelDict> implements MdmOrgLevelDictService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmOrgLevelDictMapper mdmOrgLevelDictMapper;

    /**
    * 单表简单查询操作
    * @param mdmOrgLevelDictDto
    * @return
    */
    @Override
    public Page list(MdmOrgLevelDictDto mdmOrgLevelDictDto) {
        LambdaQueryWrapper<MdmOrgLevelDict> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.like(StrUtil.isNotEmpty(mdmOrgLevelDictDto.getOrgLevel()),MdmOrgLevelDict::getOrgLevel,mdmOrgLevelDictDto.getOrgLevel());
        Page<MdmOrgLevelDict> page = mdmOrgLevelDictMapper.selectPage(mdmOrgLevelDictDto.getPage(),queryWrapper);
        Page<MdmOrgLevelDictVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmOrgLevelDictVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmOrgLevelDictDto
    * @return
    */
    @Override
    public Page lists(MdmOrgLevelDictDto mdmOrgLevelDictDto) {
        Page<MdmOrgLevelDictVo> pageVo = mdmOrgLevelDictMapper.getPageList(mdmOrgLevelDictDto.getPage(),mdmOrgLevelDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmOrgLevelDictVo get(Long id) {
        MdmOrgLevelDict mdmOrgLevelDict = super.getById(id);
        MdmOrgLevelDictVo mdmOrgLevelDictVo = null;
        if(mdmOrgLevelDict !=null){
            mdmOrgLevelDictVo = dozerMapper.map(mdmOrgLevelDict,MdmOrgLevelDictVo.class);
        }
        log.debug("查询成功");
        return mdmOrgLevelDictVo;
    }

    /**
    * 保存实体
    * @param mdmOrgLevelDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmOrgLevelDictDto mdmOrgLevelDictDto) {
        LambdaQueryWrapper<MdmOrgLevelDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(mdmOrgLevelDictDto.getOrgLevel()),MdmOrgLevelDict::getOrgLevel,mdmOrgLevelDictDto.getOrgLevel());
        List list = this.list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list)){
            throw new IncloudException("组织级别类型不能重复!");
        }
        MdmOrgLevelDict mdmOrgLevelDict = dozerMapper.map(mdmOrgLevelDictDto,MdmOrgLevelDict.class);
        boolean result = super.save(mdmOrgLevelDict);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmOrgLevelDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmOrgLevelDictDto mdmOrgLevelDictDto) {
        LambdaQueryWrapper<MdmOrgLevelDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(mdmOrgLevelDictDto.getOrgLevel()),MdmOrgLevelDict::getOrgLevel,mdmOrgLevelDictDto.getOrgLevel())
                    .ne(MdmOrgLevelDict::getId,mdmOrgLevelDictDto.getId());
        List list = this.list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list)){
            throw new IncloudException("组织级别类型不能重复!");
        }
        mdmOrgLevelDictDto.setUpdateTime(LocalDateTime.now());
        MdmOrgLevelDict mdmOrgLevelDict = dozerMapper.map(mdmOrgLevelDictDto,MdmOrgLevelDict.class);
        boolean result = super.updateById(mdmOrgLevelDict);
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
