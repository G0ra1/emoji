package com.netwisd.base.wf.service.impl.procdef;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.entity.WfExpreUserParamDef;
import com.netwisd.base.wf.mapper.WfExpreUserParamDefMapper;
import com.netwisd.base.wf.service.procdef.WfExpreUserParamDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfExpreUserParamDefDto;
import com.netwisd.base.wf.vo.WfExpreUserParamDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:49:34
 */
@Service
@Slf4j
public class WfExpreUserParamDefServiceImpl extends BatchServiceImpl<WfExpreUserParamDefMapper, WfExpreUserParamDef> implements WfExpreUserParamDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfExpreUserParamDefMapper wfExpreUserParamDefMapper;

    /**
    * 单表简单查询操作
    * @param wfExpreUserParamDefDto
    * @return
    */
    @Override
    public Page list(WfExpreUserParamDefDto wfExpreUserParamDefDto) {
        WfExpreUserParamDef wfExpreUserParamDef = dozerMapper.map(wfExpreUserParamDefDto,WfExpreUserParamDef.class);
        LambdaQueryWrapper<WfExpreUserParamDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfExpreUserParamDef);
        Page<WfExpreUserParamDef> page = wfExpreUserParamDefMapper.selectPage(wfExpreUserParamDefDto.getPage(),queryWrapper);
        Page<WfExpreUserParamDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfExpreUserParamDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfExpreUserParamDefDto
    * @return
    */
    @Override
    public Page lists(WfExpreUserParamDefDto wfExpreUserParamDefDto) {
        Page<WfExpreUserParamDefVo> pageVo = wfExpreUserParamDefMapper.getPageList(wfExpreUserParamDefDto.getPage(),wfExpreUserParamDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfExpreUserParamDefVo get(Long id) {
        WfExpreUserParamDef wfExpreUserParamDef = super.getById(id);
        WfExpreUserParamDefVo wfExpreUserParamDefVo = null;
        if(wfExpreUserParamDef !=null){
            wfExpreUserParamDefVo = dozerMapper.map(wfExpreUserParamDef,WfExpreUserParamDefVo.class);
        }
        log.debug("查询成功");
        return wfExpreUserParamDefVo;
    }

    /**
    * 保存实体
    * @param wfExpreUserParamDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfExpreUserParamDefDto wfExpreUserParamDefDto) {
        WfExpreUserParamDef wfExpreUserParamDef = dozerMapper.map(wfExpreUserParamDefDto,WfExpreUserParamDef.class);
        boolean result = super.save(wfExpreUserParamDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfExpreUserParamDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfExpreUserParamDefDto wfExpreUserParamDefDto) {
        WfExpreUserParamDef wfExpreUserParamDef = dozerMapper.map(wfExpreUserParamDefDto,WfExpreUserParamDef.class);
        boolean result = super.updateById(wfExpreUserParamDef);
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
        log.debug("根据流程定义Key 删除所有的 人员表达式-参数 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfExpreUserParamDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfExpreUserParamDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfExpreUserParamDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 人员表达式-参数 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 人员表达式-参数 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfExpreUserParamDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfExpreUserParamDef::getCamundaProcdefId,camundaDefId);
        int line = wfExpreUserParamDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 人员表达式-参数 信息(删除某个版本). 影响行数：{}", line);
    }
}
