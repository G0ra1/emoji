package com.netwisd.base.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.mdm.dto.MdmDutyTagDictDto;
import com.netwisd.base.mdm.entity.MdmDutyTagDict;
import com.netwisd.base.mdm.mapper.MdmDutyTagDictMapper;
import com.netwisd.base.mdm.service.MdmDutyTagDictService;
import com.netwisd.base.mdm.vo.MdmDutyTagDictVo;
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
public class MdmDutyTagDictServiceImpl extends ServiceImpl<MdmDutyTagDictMapper, MdmDutyTagDict> implements MdmDutyTagDictService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmDutyTagDictMapper mdmDutyTagDictMapper;

    /**
     * 单表简单查询操作
     * @param mdmDutyTagDictDto
     * @return
     */
    @Override
    public Page list(MdmDutyTagDictDto mdmDutyTagDictDto) {
        LambdaQueryWrapper<MdmDutyTagDict> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmDutyTagDict> page = mdmDutyTagDictMapper.selectPage(mdmDutyTagDictDto.getPage(),queryWrapper);
        Page<MdmDutyTagDictVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmDutyTagDictVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param
     * @return
     */
    @Override
    public List<MdmDutyTagDictVo> lists() {
        List<MdmDutyTagDict> mdmDutyTagDicts = mdmDutyTagDictMapper.selectList(null);
        List<MdmDutyTagDictVo> mdmDutyTagDictVos = DozerUtils.mapList(dozerMapper, mdmDutyTagDicts, MdmDutyTagDictVo.class);
        log.debug("查询条数:"+mdmDutyTagDictVos.size());
        return mdmDutyTagDictVos;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public MdmDutyTagDictVo get(Long id) {
        MdmDutyTagDict mdmDutyTagDict = super.getById(id);
        MdmDutyTagDictVo mdmDutyTagDictVo = null;
        if(mdmDutyTagDict !=null){
            mdmDutyTagDictVo = dozerMapper.map(mdmDutyTagDict,MdmDutyTagDictVo.class);
        }
        log.debug("查询成功");
        return mdmDutyTagDictVo;
    }

    /**
     * 保存实体
     * @param mdmDutyTagDictDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(MdmDutyTagDictDto mdmDutyTagDictDto) {
        MdmDutyTagDict mdmDutyTagDict = dozerMapper.map(mdmDutyTagDictDto,MdmDutyTagDict.class);
        boolean result = super.save(mdmDutyTagDict);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 修改实体
     * @param mdmDutyTagDictDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MdmDutyTagDictDto mdmDutyTagDictDto) {
        mdmDutyTagDictDto.setUpdateTime(LocalDateTime.now());
        MdmDutyTagDict mdmDutyTagDict = dozerMapper.map(mdmDutyTagDictDto,MdmDutyTagDict.class);
        boolean result = super.updateById(mdmDutyTagDict);
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
