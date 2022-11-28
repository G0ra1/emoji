package com.netwisd.base.wf.service.impl.other;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.entity.WfButtonDef;
import com.netwisd.base.wf.mapper.WfButtonDefMapper;
import com.netwisd.base.wf.service.other.WfButtonDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfButtonDefDto;
import com.netwisd.base.wf.vo.WfButtonDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流程定义-按钮 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 22:26:53
 */
@Service
@Slf4j
public class WfButtonDefServiceImpl extends BatchServiceImpl<WfButtonDefMapper, WfButtonDef> implements WfButtonDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfButtonDefMapper wfButtonDefMapper;

    /**
    * 单表简单查询操作
    * @param wfButtonDefDto
    * @return
    */
    @Override
    public Page list(WfButtonDefDto wfButtonDefDto) {
        WfButtonDef wfButtonDef = dozerMapper.map(wfButtonDefDto,WfButtonDef.class);
        LambdaQueryWrapper<WfButtonDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfButtonDef);
        Page<WfButtonDef> page = wfButtonDefMapper.selectPage(wfButtonDefDto.getPage(),queryWrapper);
        Page<WfButtonDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfButtonDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfButtonDefDto
    * @return
    */
    @Override
    public Page lists(WfButtonDefDto wfButtonDefDto) {
        Page<WfButtonDefVo> pageVo = wfButtonDefMapper.getPageList(wfButtonDefDto.getPage(),wfButtonDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfButtonDefVo get(Long id) {
        WfButtonDef wfButtonDef = super.getById(id);
        WfButtonDefVo wfButtonDefVo = null;
        if(wfButtonDef !=null){
            wfButtonDefVo = dozerMapper.map(wfButtonDef,WfButtonDefVo.class);
        }
        log.debug("查询成功");
        return wfButtonDefVo;
    }

    /**
    * 保存实体
    * @param wfButtonDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfButtonDefDto wfButtonDefDto) {
        WfButtonDef wfButtonDef = dozerMapper.map(wfButtonDefDto,WfButtonDef.class);
        boolean result = super.save(wfButtonDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfButtonDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfButtonDefDto wfButtonDefDto) {
        WfButtonDef wfButtonDef = dozerMapper.map(wfButtonDefDto,WfButtonDef.class);
        boolean result = super.updateById(wfButtonDef);
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
        log.debug("根据流程定义Key 删除所有的按钮信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfButtonDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfButtonDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfButtonDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的按钮信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的按钮信息(删除大版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfButtonDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfButtonDef::getCamundaProcdefId,camundaDefId);
        int line = wfButtonDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的按钮信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public List<WfButtonDefVo> queryDefButtons(String camundaDefId, String camundaNodeDefId) {
        log.debug("根据表单id、流程定义id 和节点定义id 查询出所有的按钮信息.camundaDefId:{}.camundaNodeDefId:{}",camundaDefId,camundaNodeDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfButtonDef> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WfButtonDef::getCamundaProcdefId,camundaDefId)
                .eq(WfButtonDef::getCamundaNodeDefId,camundaNodeDefId);
        List<WfButtonDef> wfButtonDefList = wfButtonDefMapper.selectList(wrapper);
        log.debug("根据表单id、流程定义id 和节点定义id 查询出所有的按钮信息. 返回：{}", wfButtonDefList);
        List<WfButtonDefVo> listVo = DozerUtils.mapList(dozerMapper, wfButtonDefList, WfButtonDefVo.class);
        return listVo;
    }

    @Override
    @Transactional
    public void delButtonsByNodeId(Long nodeDefId) {
        LambdaQueryWrapper<WfButtonDef> delButtonDefWrapper = new LambdaQueryWrapper<>();
        delButtonDefWrapper.eq(WfButtonDef::getNodeDefId,nodeDefId);
        this.remove(delButtonDefWrapper);
    }

    @Override
    @Transactional
    public void saveXml2WfButtonsDef(List<WfButtonDefDto> wfButtonDefDtoList) {
        List<WfButtonDef> tempButtonDefList = new ArrayList<>();
        //节点变量
        if(CollectionUtil.isNotEmpty(wfButtonDefDtoList)) {
            log.debug("saveNodeInfo-按钮信息：{}", wfButtonDefDtoList.toString());
            for (WfButtonDefDto wfButtonDefDto : wfButtonDefDtoList) {
                WfButtonDef wfButtonDef = dozerMapper.map(wfButtonDefDto,WfButtonDef.class);
                tempButtonDefList.add(wfButtonDef);
            }
        }
        if(CollectionUtil.isNotEmpty(tempButtonDefList)) {
            boolean saveButtonDefBoo = this.saveBatch(tempButtonDefList);
            log.debug("saveNodeInfo-按钮信息：{}", saveButtonDefBoo);
        }
    }

    @Override
    @Transactional
    public void delButtonsByCmdNodeIdAndCmdProcdefId(String camundaNodeId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(camundaNodeId)) {
            throw new IncloudException("camunda节点定义id 不能为空！");
        }
        if(ObjectUtil.isEmpty(camundaProcdefId)) {
            throw new IncloudException("camunda流程定义id 不能为空！");
        }
        LambdaQueryWrapper<WfButtonDef> delButtonDefWrapper = new LambdaQueryWrapper<>();
        delButtonDefWrapper.eq(WfButtonDef::getCamundaNodeDefId,camundaNodeId);
        delButtonDefWrapper.eq(WfButtonDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delButtonDefWrapper);
    }
}
