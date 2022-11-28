package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.mdm.dto.MdmDutyGradeDictDto;
import com.netwisd.base.mdm.entity.MdmDutyGradeDict;
import com.netwisd.base.mdm.entity.MdmDutyGradeDict;
import com.netwisd.base.mdm.mapper.MdmDutyGradeDictMapper;
import com.netwisd.base.mdm.service.MdmDutyGradeDictService;
import com.netwisd.base.mdm.vo.MdmDutyGradeDictVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmDutyGradeDictDto;
import com.netwisd.base.mdm.vo.MdmDutyGradeDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Service
@Slf4j
public class MdmDutyGradeDictServiceImpl extends ServiceImpl<MdmDutyGradeDictMapper, MdmDutyGradeDict> implements MdmDutyGradeDictService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmDutyGradeDictMapper mdmDutyGradeDictMapper;

    /**
     * 单表简单查询操作
     * @param mdmDutyGradeDictDto
     * @return
     */
    @Override
    public Page list(MdmDutyGradeDictDto mdmDutyGradeDictDto) {
        LambdaQueryWrapper<MdmDutyGradeDict> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmDutyGradeDict> page = mdmDutyGradeDictMapper.selectPage(mdmDutyGradeDictDto.getPage(),queryWrapper);
        Page<MdmDutyGradeDictVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmDutyGradeDictVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param mdmDutyGradeDictDto
     * @return
     */
    @Override
    public List<MdmDutyGradeDictVo> lists(MdmDutyGradeDictDto mdmDutyGradeDictDto) {
        LambdaQueryWrapper<MdmDutyGradeDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(mdmDutyGradeDictDto.getDutySequId()),MdmDutyGradeDict::getDutySequId,mdmDutyGradeDictDto.getDutySequId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmDutyGradeDictDto.getDutySequName()),MdmDutyGradeDict::getDutySequName,mdmDutyGradeDictDto.getDutySequName());
        List<MdmDutyGradeDict> mdmDutyGradeDicts = mdmDutyGradeDictMapper.selectList(queryWrapper);
        List<MdmDutyGradeDictVo> mdmDutyGradeDictVos = DozerUtils.mapList(dozerMapper, mdmDutyGradeDicts, MdmDutyGradeDictVo.class);
        log.debug("查询条数:"+mdmDutyGradeDictVos.size());
        return mdmDutyGradeDictVos;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public MdmDutyGradeDictVo get(Long id) {
        MdmDutyGradeDict mdmDutyGradeDict = super.getById(id);
        MdmDutyGradeDictVo mdmDutyGradeDictVo = null;
        if(mdmDutyGradeDict !=null){
            mdmDutyGradeDictVo = dozerMapper.map(mdmDutyGradeDict,MdmDutyGradeDictVo.class);
        }
        log.debug("查询成功");
        return mdmDutyGradeDictVo;
    }

    /**
     * 保存实体
     * @param mdmDutyGradeDictDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(MdmDutyGradeDictDto mdmDutyGradeDictDto) {
        MdmDutyGradeDict mdmDutyGradeDict = dozerMapper.map(mdmDutyGradeDictDto,MdmDutyGradeDict.class);
        boolean result = super.save(mdmDutyGradeDict);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 修改实体
     * @param mdmDutyGradeDictDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MdmDutyGradeDictDto mdmDutyGradeDictDto) {
        mdmDutyGradeDictDto.setUpdateTime(LocalDateTime.now());
        MdmDutyGradeDict mdmDutyGradeDict = dozerMapper.map(mdmDutyGradeDictDto,MdmDutyGradeDict.class);
        boolean result = super.updateById(mdmDutyGradeDict);
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
