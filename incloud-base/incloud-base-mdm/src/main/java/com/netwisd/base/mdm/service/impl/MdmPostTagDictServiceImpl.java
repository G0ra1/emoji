package com.netwisd.base.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.mdm.entity.MdmPostTagDict;
import com.netwisd.base.mdm.mapper.MdmPostTagDictMapper;
import com.netwisd.base.mdm.service.MdmPostTagDictService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmPostTagDictDto;
import com.netwisd.base.mdm.vo.MdmPostTagDictVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 岗位标识字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:44:20
 */
@Service
@Slf4j
public class MdmPostTagDictServiceImpl extends ServiceImpl<MdmPostTagDictMapper, MdmPostTagDict> implements MdmPostTagDictService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmPostTagDictMapper mdmPostTagDictMapper;

    /**
    * 单表简单查询操作
    * @param mdmPostTagDictDto
    * @return
    */
    @Override
    public Page list(MdmPostTagDictDto mdmPostTagDictDto) {
        LambdaQueryWrapper<MdmPostTagDict> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmPostTagDict> page = mdmPostTagDictMapper.selectPage(mdmPostTagDictDto.getPage(),queryWrapper);
        Page<MdmPostTagDictVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmPostTagDictVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param
    * @return
    */
    @Override
    public List<MdmPostTagDictVo> lists() {
        List<MdmPostTagDict> mdmPostTagDicts = mdmPostTagDictMapper.selectList(null);
        List<MdmPostTagDictVo> mdmPostTagDictVos = DozerUtils.mapList(dozerMapper, mdmPostTagDicts, MdmPostTagDictVo.class);
        log.debug("查询条数:"+mdmPostTagDictVos.size());
        return mdmPostTagDictVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmPostTagDictVo get(Long id) {
        MdmPostTagDict mdmPostTagDict = super.getById(id);
        MdmPostTagDictVo mdmPostTagDictVo = null;
        if(mdmPostTagDict !=null){
            mdmPostTagDictVo = dozerMapper.map(mdmPostTagDict,MdmPostTagDictVo.class);
        }
        log.debug("查询成功");
        return mdmPostTagDictVo;
    }

    /**
    * 保存实体
    * @param mdmPostTagDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmPostTagDictDto mdmPostTagDictDto) {
        MdmPostTagDict mdmPostTagDict = dozerMapper.map(mdmPostTagDictDto,MdmPostTagDict.class);
        boolean result = super.save(mdmPostTagDict);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmPostTagDictDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmPostTagDictDto mdmPostTagDictDto) {
        mdmPostTagDictDto.setUpdateTime(LocalDateTime.now());
        MdmPostTagDict mdmPostTagDict = dozerMapper.map(mdmPostTagDictDto,MdmPostTagDict.class);
        boolean result = super.updateById(mdmPostTagDict);
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
