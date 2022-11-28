package com.netwisd.base.wf.service.impl.other;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfFormFieldsDefDto;
import com.netwisd.base.wf.entity.WfFormFieldsDef;
import com.netwisd.base.wf.mapper.WfFormFieldsDefMapper;
import com.netwisd.base.wf.service.other.WfFormFieldsDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.vo.WfFormFieldsDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description 流程表单字段定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:43:44
 */
@Service
@Slf4j
public class WfFormFieldsDefServiceImpl extends BatchServiceImpl<WfFormFieldsDefMapper, WfFormFieldsDef> implements WfFormFieldsDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfFormFieldsDefMapper wfFormFieldsDefMapper;

    /**
    * 单表简单查询操作
    * @param wfFormFieldsDefDto
    * @return
    */
    @Override
    public Page list(WfFormFieldsDefDto wfFormFieldsDefDto) {
        WfFormFieldsDef wfFormFieldsDef = dozerMapper.map(wfFormFieldsDefDto, WfFormFieldsDef.class);
        LambdaQueryWrapper<WfFormFieldsDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfFormFieldsDef);
        Page<WfFormFieldsDef> page = wfFormFieldsDefMapper.selectPage(wfFormFieldsDefDto.getPage(),queryWrapper);
        Page<WfFormFieldsDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfFormFieldsDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfFormFieldsDefDto
    * @return
    */
    @Override
    public Page lists(WfFormFieldsDefDto wfFormFieldsDefDto) {
        Page<WfFormFieldsDefVo> pageVo = wfFormFieldsDefMapper.getPageList(wfFormFieldsDefDto.getPage(), wfFormFieldsDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfFormFieldsDefVo get(Long id) {
        WfFormFieldsDef wfFormFieldsDef = super.getById(id);
        WfFormFieldsDefVo wfFormFieldsDefVo = null;
        if(wfFormFieldsDef !=null){
            wfFormFieldsDefVo = dozerMapper.map(wfFormFieldsDef, WfFormFieldsDefVo.class);
        }
        log.debug("查询成功");
        return wfFormFieldsDefVo;
    }

    /**
    * 保存实体
    * @param wfFormFieldsDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfFormFieldsDefDto wfFormFieldsDefDto) {
        WfFormFieldsDef wfFormFieldsDef = dozerMapper.map(wfFormFieldsDefDto, WfFormFieldsDef.class);
        boolean result = super.save(wfFormFieldsDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfFormFieldsDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfFormFieldsDefDto wfFormFieldsDefDto) {
        WfFormFieldsDef wfFormFieldsDef = dozerMapper.map(wfFormFieldsDefDto, WfFormFieldsDef.class);
        boolean result = super.updateById(wfFormFieldsDef);
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
        log.debug("根据流程定义Key 删除所有的 表单字段 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfFormFieldsDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfFormFieldsDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfFormFieldsDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 表单字段 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 表单字段 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfFormFieldsDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfFormFieldsDef::getCamundaProcdefId,camundaDefId);
        int line = wfFormFieldsDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 表单字段 信息(删除某个版本). 影响行数：{}", line);
    }

    @Override
    public List<WfFormFieldsDefVo> getFormFields(String camundaDefId, String camundaNodeDefId) {
        log.debug("流程定义id 和节点定义id 查询出所有的表单字段信息. camundaDefId:{}.camundaNodeDefId:{}", camundaDefId,camundaNodeDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfFormFieldsDef> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WfFormFieldsDef::getCamundaProcdefId,camundaDefId)
                .eq(WfFormFieldsDef::getCamundaNodeDefId,camundaNodeDefId);
        List<WfFormFieldsDef> wfFormFieldsDefList = wfFormFieldsDefMapper.selectList(wrapper);
        log.debug("根据表单id、流程定义id 和节点定义id 查询出所有的表单字段信息. 返回：{}", wfFormFieldsDefList);
        List<WfFormFieldsDefVo> listVo = DozerUtils.mapList(dozerMapper, wfFormFieldsDefList, WfFormFieldsDefVo.class);
        return listVo;
    }

    @Override
    @Transactional
    public void delFormFieldsByNodeIdAndFormId(Long nodeDefId, List formIdList) {
        LambdaQueryWrapper<WfFormFieldsDef> delFormFieldsWrapper = new LambdaQueryWrapper<>();
        delFormFieldsWrapper.eq(WfFormFieldsDef::getNodeDefId,nodeDefId);
        delFormFieldsWrapper.in(CollectionUtil.isNotEmpty(formIdList), WfFormFieldsDef::getFormId,formIdList);
        this.remove(delFormFieldsWrapper);
    }

    @Override
    @Transactional
    public void delFieldsByCmdNodeIdAndCmdProcdefId(String camundaNodeId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(camundaNodeId)) {
            throw new IncloudException("camunda节点定义id 不能为空！");
        }
        if(ObjectUtil.isEmpty(camundaProcdefId)) {
            throw new IncloudException("camunda流程定义id 不能为空！");
        }
        LambdaQueryWrapper<WfFormFieldsDef> delFormFieldsWrapper = new LambdaQueryWrapper<>();
        delFormFieldsWrapper.eq(WfFormFieldsDef::getCamundaNodeDefId,camundaNodeId);
        delFormFieldsWrapper.eq(WfFormFieldsDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delFormFieldsWrapper);
    }
}
