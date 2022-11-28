package com.netwisd.base.wf.service.impl.procdef;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.entity.WfExpreSequDef;
import com.netwisd.base.wf.mapper.WfExpreSequDefMapper;
import com.netwisd.base.wf.service.procdef.WfExpreSequDefService;
import com.netwisd.base.wf.service.procdef.WfExpreSequParamDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfExpreSequDefDto;
import com.netwisd.base.wf.vo.WfExpreSequDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description 流程定义-序列流-表达式 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 18:59:28
 */
@Service
@Slf4j
public class WfExpreSequDefServiceImpl extends BatchServiceImpl<WfExpreSequDefMapper, WfExpreSequDef> implements WfExpreSequDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfExpreSequDefMapper wfExpreSequDefMapper;

    @Autowired
    private WfExpreSequParamDefService wfExpreSequParamDefService;

    /**
    * 单表简单查询操作
    * @param wfExpreSequDefDto
    * @return
    */
    @Override
    public Page list(WfExpreSequDefDto wfExpreSequDefDto) {
        WfExpreSequDef wfExpreSequDef = dozerMapper.map(wfExpreSequDefDto,WfExpreSequDef.class);
        LambdaQueryWrapper<WfExpreSequDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfExpreSequDef);
        Page<WfExpreSequDef> page = wfExpreSequDefMapper.selectPage(wfExpreSequDefDto.getPage(),queryWrapper);
        Page<WfExpreSequDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfExpreSequDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfExpreSequDefDto
    * @return
    */
    @Override
    public Page lists(WfExpreSequDefDto wfExpreSequDefDto) {
        Page<WfExpreSequDefVo> pageVo = wfExpreSequDefMapper.getPageList(wfExpreSequDefDto.getPage(),wfExpreSequDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfExpreSequDefVo get(Long id) {
        WfExpreSequDef wfExpreSequDef = super.getById(id);
        WfExpreSequDefVo wfExpreSequDefVo = null;
        if(wfExpreSequDef !=null){
            wfExpreSequDefVo = dozerMapper.map(wfExpreSequDef,WfExpreSequDefVo.class);
        }
        log.debug("查询成功");
        return wfExpreSequDefVo;
    }

    /**
    * 保存实体
    * @param wfExpreSequDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfExpreSequDefDto wfExpreSequDefDto) {
        WfExpreSequDef wfExpreSequDef = dozerMapper.map(wfExpreSequDefDto,WfExpreSequDef.class);
        boolean result = super.save(wfExpreSequDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfExpreSequDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfExpreSequDefDto wfExpreSequDefDto) {
        WfExpreSequDef wfExpreSequDef = dozerMapper.map(wfExpreSequDefDto,WfExpreSequDef.class);
        boolean result = super.updateById(wfExpreSequDef);
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
    public WfExpreSequDef getExpreByProcDefIdAndSequDefId(String camundaProcdefId, String camundaSequDefId) {
        return null;
    }

    @Override
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 序列流-人员表达式 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfExpreSequDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfExpreSequDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfExpreSequDefMapper.delete(delWrapper);
        //删除人员表达式 参数
        wfExpreSequParamDefService.deleteByCamundaDefKey(camundaDefKey);
        log.debug("根据流程定义Key 删除所有的 序列流-人员表达式 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 序列流-人员表达式 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfExpreSequDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfExpreSequDef::getCamundaProcdefId,camundaDefId);
        int line = wfExpreSequDefMapper.delete(delWrapper);
        wfExpreSequParamDefService.deleteByCamundaDefKey(camundaDefId);
        log.debug("根据流程定义id 删除所有的 序列流-人员表达式 信息(删除某个版本). 影响行数：{}", line);
    }
}
