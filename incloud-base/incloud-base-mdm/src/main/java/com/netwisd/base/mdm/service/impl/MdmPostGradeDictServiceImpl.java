package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.mdm.entity.MdmPostGradeDict;
import com.netwisd.base.mdm.mapper.MdmPostGradeDictMapper;
import com.netwisd.base.mdm.service.MdmPostGradeDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmPostGradeDictDto;
import com.netwisd.base.mdm.vo.MdmPostGradeDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 岗位职等 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:54:55
 */
@Service
@Slf4j
public class MdmPostGradeDictServiceImpl extends ServiceImpl<MdmPostGradeDictMapper, MdmPostGradeDict> implements MdmPostGradeDictService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmPostGradeDictMapper mdmPostGradeDictMapper;

    /**
    * 单表简单查询操作
    * @param mdmPostGradeDictDto
    * @return
    */
    @Override
    public Page list(MdmPostGradeDictDto mdmPostGradeDictDto) {
        LambdaQueryWrapper<MdmPostGradeDict> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmPostGradeDict> page = mdmPostGradeDictMapper.selectPage(mdmPostGradeDictDto.getPage(),queryWrapper);
        Page<MdmPostGradeDictVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmPostGradeDictVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmPostGradeDictDto
    * @return
    */
    @Override
    public List<MdmPostGradeDictVo> lists(MdmPostGradeDictDto mdmPostGradeDictDto) {
        LambdaQueryWrapper<MdmPostGradeDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostGradeDictDto.getPostSequId()),MdmPostGradeDict::getPostSequId,mdmPostGradeDictDto.getPostSequId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmPostGradeDictDto.getPostSequName()),MdmPostGradeDict::getPostSequName,mdmPostGradeDictDto.getPostSequName());
        List<MdmPostGradeDict> mdmPostGradeDicts = mdmPostGradeDictMapper.selectList(queryWrapper);
        List<MdmPostGradeDictVo> mdmPostGradeDictVos = DozerUtils.mapList(dozerMapper, mdmPostGradeDicts, MdmPostGradeDictVo.class);
        log.debug("查询条数:"+mdmPostGradeDictVos.size());
        return mdmPostGradeDictVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmPostGradeDictVo get(Long id) {
        MdmPostGradeDict mdmPostGradeDict = super.getById(id);
        MdmPostGradeDictVo mdmPostGradeDictVo = null;
        if(mdmPostGradeDict !=null){
            mdmPostGradeDictVo = dozerMapper.map(mdmPostGradeDict,MdmPostGradeDictVo.class);
        }
        log.debug("查询成功");
        return mdmPostGradeDictVo;
    }

    /**
    * 保存实体
    * @param mdmPostGradeDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmPostGradeDictDto mdmPostGradeDictDto) {
        MdmPostGradeDict mdmPostGradeDict = dozerMapper.map(mdmPostGradeDictDto,MdmPostGradeDict.class);
        boolean result = super.save(mdmPostGradeDict);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmPostGradeDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmPostGradeDictDto mdmPostGradeDictDto) {
        mdmPostGradeDictDto.setUpdateTime(LocalDateTime.now());
        MdmPostGradeDict mdmPostGradeDict = dozerMapper.map(mdmPostGradeDictDto,MdmPostGradeDict.class);
        boolean result = super.updateById(mdmPostGradeDict);
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
