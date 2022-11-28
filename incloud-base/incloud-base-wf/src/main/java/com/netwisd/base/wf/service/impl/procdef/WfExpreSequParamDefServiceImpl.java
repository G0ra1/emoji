package com.netwisd.base.wf.service.impl.procdef;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.entity.WfExpreSequParamDef;
import com.netwisd.base.wf.mapper.WfExpreSequParamDefMapper;
import com.netwisd.base.wf.service.procdef.WfExpreSequParamDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfExpreSequParamDefDto;
import com.netwisd.base.wf.vo.WfExpreSequParamDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 流程定义-序列流-表达式-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 19:00:23
 */
@Service
@Slf4j
public class WfExpreSequParamDefServiceImpl extends BatchServiceImpl<WfExpreSequParamDefMapper, WfExpreSequParamDef> implements WfExpreSequParamDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfExpreSequParamDefMapper wfExpreSequParamDefMapper;

    /**
    * 单表简单查询操作
    * @param wfExpreSequParamDefDto
    * @return
    */
    @Override
    public Page list(WfExpreSequParamDefDto wfExpreSequParamDefDto) {
        WfExpreSequParamDef wfExpreSequParamDef = dozerMapper.map(wfExpreSequParamDefDto,WfExpreSequParamDef.class);
        LambdaQueryWrapper<WfExpreSequParamDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfExpreSequParamDef);
        Page<WfExpreSequParamDef> page = wfExpreSequParamDefMapper.selectPage(wfExpreSequParamDefDto.getPage(),queryWrapper);
        Page<WfExpreSequParamDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfExpreSequParamDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfExpreSequParamDefDto
    * @return
    */
    @Override
    public Page lists(WfExpreSequParamDefDto wfExpreSequParamDefDto) {
        Page<WfExpreSequParamDefVo> pageVo = wfExpreSequParamDefMapper.getPageList(wfExpreSequParamDefDto.getPage(),wfExpreSequParamDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfExpreSequParamDefVo get(Long id) {
        WfExpreSequParamDef wfExpreSequParamDef = super.getById(id);
        WfExpreSequParamDefVo wfExpreSequParamDefVo = null;
        if(wfExpreSequParamDef !=null){
            wfExpreSequParamDefVo = dozerMapper.map(wfExpreSequParamDef,WfExpreSequParamDefVo.class);
        }
        log.debug("查询成功");
        return wfExpreSequParamDefVo;
    }

    /**
    * 保存实体
    * @param wfExpreSequParamDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfExpreSequParamDefDto wfExpreSequParamDefDto) {
        WfExpreSequParamDef wfExpreSequParamDef = dozerMapper.map(wfExpreSequParamDefDto,WfExpreSequParamDef.class);
        boolean result = super.save(wfExpreSequParamDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfExpreSequParamDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfExpreSequParamDefDto wfExpreSequParamDefDto) {
        WfExpreSequParamDef wfExpreSequParamDef = dozerMapper.map(wfExpreSequParamDefDto,WfExpreSequParamDef.class);
        boolean result = super.updateById(wfExpreSequParamDef);
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
        log.debug("根据流程定义Key 删除所有的 序列流-人员表达式-参数  信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfExpreSequParamDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfExpreSequParamDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfExpreSequParamDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 序列流-人员表达式-参数  信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 人员表达式-参数 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfExpreSequParamDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfExpreSequParamDef::getCamundaProcdefId,camundaDefId);
        int line = wfExpreSequParamDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 人员表达式-参数 信息(删除某个版本). 影响行数：{}", line);
    }
}
