package com.netwisd.base.wf.service.impl.procdef;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.entity.WfSequEventParamDef;
import com.netwisd.base.wf.mapper.WfSequEventParamDefMapper;
import com.netwisd.base.wf.service.procdef.WfSequEventParamDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfSequEventParamDefDto;
import com.netwisd.base.wf.vo.WfSequEventParamDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 流程定义-序列流-事件-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:56:16
 */
@Service
@Slf4j
public class WfSequEventParamDefServiceImpl extends BatchServiceImpl<WfSequEventParamDefMapper, WfSequEventParamDef> implements WfSequEventParamDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfSequEventParamDefMapper wfSequEventParamDefMapper;

    /**
    * 单表简单查询操作
    * @param wfSequEventParamDefDto
    * @return
    */
    @Override
    public Page list(WfSequEventParamDefDto wfSequEventParamDefDto) {
        WfSequEventParamDef wfSequEventParamDef = dozerMapper.map(wfSequEventParamDefDto,WfSequEventParamDef.class);
        LambdaQueryWrapper<WfSequEventParamDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfSequEventParamDef);
        Page<WfSequEventParamDef> page = wfSequEventParamDefMapper.selectPage(wfSequEventParamDefDto.getPage(),queryWrapper);
        Page<WfSequEventParamDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfSequEventParamDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfSequEventParamDefDto
    * @return
    */
    @Override
    public Page lists(WfSequEventParamDefDto wfSequEventParamDefDto) {
        Page<WfSequEventParamDefVo> pageVo = wfSequEventParamDefMapper.getPageList(wfSequEventParamDefDto.getPage(),wfSequEventParamDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfSequEventParamDefVo get(Long id) {
        WfSequEventParamDef wfSequEventParamDef = super.getById(id);
        WfSequEventParamDefVo wfSequEventParamDefVo = null;
        if(wfSequEventParamDef !=null){
            wfSequEventParamDefVo = dozerMapper.map(wfSequEventParamDef,WfSequEventParamDefVo.class);
        }
        log.debug("查询成功");
        return wfSequEventParamDefVo;
    }

    /**
    * 保存实体
    * @param wfSequEventParamDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfSequEventParamDefDto wfSequEventParamDefDto) {
        WfSequEventParamDef wfSequEventParamDef = dozerMapper.map(wfSequEventParamDefDto,WfSequEventParamDef.class);
        boolean result = super.save(wfSequEventParamDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfSequEventParamDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfSequEventParamDefDto wfSequEventParamDefDto) {
        WfSequEventParamDef wfSequEventParamDef = dozerMapper.map(wfSequEventParamDefDto,WfSequEventParamDef.class);
        boolean result = super.updateById(wfSequEventParamDef);
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
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 序列流-事件-参数 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfSequEventParamDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfSequEventParamDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfSequEventParamDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 序列流-事件-参数 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 序列流-事件-参数 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfSequEventParamDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfSequEventParamDef::getCamundaProcdefId,camundaDefId);
        int line = wfSequEventParamDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 序列流-事件-参数 信息(删除某个版本). 影响行数：{}", line);
    }
}
