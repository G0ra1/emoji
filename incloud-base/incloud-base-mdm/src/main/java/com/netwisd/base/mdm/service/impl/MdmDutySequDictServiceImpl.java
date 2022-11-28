package com.netwisd.base.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.mdm.dto.MdmDutySequDictDto;
import com.netwisd.base.mdm.entity.MdmDutySequDict;
import com.netwisd.base.mdm.mapper.MdmDutySequDictMapper;
import com.netwisd.base.mdm.service.MdmDutySequDictService;
import com.netwisd.base.mdm.vo.MdmDutySequDictVo;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
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
public class MdmDutySequDictServiceImpl extends ServiceImpl<MdmDutySequDictMapper, MdmDutySequDict> implements MdmDutySequDictService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmDutySequDictMapper mdmDutySequDictMapper;

    /**
     * 单表简单查询操作
     * @param mdmDutySequDictDto
     * @return
     */
    @Override
    public Page list(MdmDutySequDictDto mdmDutySequDictDto) {
        LambdaQueryWrapper<MdmDutySequDict> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmDutySequDict> page = mdmDutySequDictMapper.selectPage(mdmDutySequDictDto.getPage(),queryWrapper);
        Page<MdmDutySequDictVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmDutySequDictVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param
     * @return
     */
    @Override
    public List<MdmDutySequDictVo> lists() {
        List<MdmDutySequDict> mdmDutySequDicts = mdmDutySequDictMapper.selectList(null);
        List<MdmDutySequDictVo> mdmDutySequDictVos = DozerUtils.mapList(dozerMapper, mdmDutySequDicts, MdmDutySequDictVo.class);
        log.debug("查询条数:"+mdmDutySequDictVos.size());
        return mdmDutySequDictVos;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public MdmDutySequDictVo get(Long id) {
        MdmDutySequDict mdmDutySequDict = super.getById(id);
        MdmDutySequDictVo mdmDutySequDictVo = null;
        if(mdmDutySequDict !=null){
            mdmDutySequDictVo = dozerMapper.map(mdmDutySequDict,MdmDutySequDictVo.class);
        }
        log.debug("查询成功");
        return mdmDutySequDictVo;
    }

    /**
     * 保存实体
     * @param mdmDutySequDictDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(MdmDutySequDictDto mdmDutySequDictDto) {
        MdmDutySequDict mdmDutySequDict = dozerMapper.map(mdmDutySequDictDto,MdmDutySequDict.class);
        boolean result = super.save(mdmDutySequDict);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 修改实体
     * @param mdmDutySequDictDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MdmDutySequDictDto mdmDutySequDictDto) {
        mdmDutySequDictDto.setUpdateTime(LocalDateTime.now());
        MdmDutySequDict mdmDutySequDict = dozerMapper.map(mdmDutySequDictDto,MdmDutySequDict.class);
        boolean result = super.updateById(mdmDutySequDict);
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
