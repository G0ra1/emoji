package com.netwisd.base.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.mdm.entity.MdmPostSequDict;
import com.netwisd.base.mdm.mapper.MdmPostSequDictMapper;
import com.netwisd.base.mdm.service.MdmPostSequDictService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmPostSequDictDto;
import com.netwisd.base.mdm.vo.MdmPostSequDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 岗位序列 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 11:01:12
 */
@Service
@Slf4j
public class MdmPostSequDictServiceImpl extends ServiceImpl<MdmPostSequDictMapper, MdmPostSequDict> implements MdmPostSequDictService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmPostSequDictMapper mdmPostSequDictMapper;

    /**
    * 单表简单查询操作
    * @param mdmPostSequDictDto
    * @return
    */
    @Override
    public Page list(MdmPostSequDictDto mdmPostSequDictDto) {
        LambdaQueryWrapper<MdmPostSequDict> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmPostSequDict> page = mdmPostSequDictMapper.selectPage(mdmPostSequDictDto.getPage(),queryWrapper);
        Page<MdmPostSequDictVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmPostSequDictVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param
    * @return
    */
    @Override
    public List<MdmPostSequDictVo> lists() {
        List<MdmPostSequDict> mdmPostSequDicts = mdmPostSequDictMapper.selectList(null);
        List<MdmPostSequDictVo> mdmPostSequDictVos = DozerUtils.mapList(dozerMapper, mdmPostSequDicts, MdmPostSequDictVo.class);
        log.debug("查询条数:"+mdmPostSequDictVos.size());
        return mdmPostSequDictVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmPostSequDictVo get(Long id) {
        MdmPostSequDict mdmPostSequDict = super.getById(id);
        MdmPostSequDictVo mdmPostSequDictVo = null;
        if(mdmPostSequDict !=null){
            mdmPostSequDictVo = dozerMapper.map(mdmPostSequDict,MdmPostSequDictVo.class);
        }
        log.debug("查询成功");
        return mdmPostSequDictVo;
    }

    /**
    * 保存实体
    * @param mdmPostSequDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmPostSequDictDto mdmPostSequDictDto) {
        MdmPostSequDict mdmPostSequDict = dozerMapper.map(mdmPostSequDictDto,MdmPostSequDict.class);
        boolean result = super.save(mdmPostSequDict);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmPostSequDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmPostSequDictDto mdmPostSequDictDto) {
        mdmPostSequDictDto.setUpdateTime(LocalDateTime.now());
        MdmPostSequDict mdmPostSequDict = dozerMapper.map(mdmPostSequDictDto,MdmPostSequDict.class);
        boolean result = super.updateById(mdmPostSequDict);
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
