package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.wf.entity.WfNodeDef;
import com.netwisd.base.wf.entity.WfProcDefRel;
import com.netwisd.base.wf.mapper.WfProcDefRelMapper;
import com.netwisd.base.wf.service.procdef.WfProcDefRelService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.wf.dto.WfProcDefRelDto;
import com.netwisd.base.wf.vo.WfProcDefRelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 流程定义和子流程定义关系表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-10-21 11:22:02
 */
@Service
@Slf4j
public class WfProcDefRelServiceImpl extends ServiceImpl<WfProcDefRelMapper, WfProcDefRel> implements WfProcDefRelService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfProcDefRelMapper wfProcDefRelMapper;

    /**
    * 单表简单查询操作
    * @param wfProcDefRelDto
    * @return
    */
    @Override
    public Page list(WfProcDefRelDto wfProcDefRelDto) {
        WfProcDefRel wfProcDefRel = dozerMapper.map(wfProcDefRelDto,WfProcDefRel.class);
        LambdaQueryWrapper<WfProcDefRel> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfProcDefRel);
        Page<WfProcDefRel> page = wfProcDefRelMapper.selectPage(wfProcDefRelDto.getPage(),queryWrapper);
        Page<WfProcDefRelVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfProcDefRelVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfProcDefRelDto
    * @return
    */
    @Override
    public Page lists(WfProcDefRelDto wfProcDefRelDto) {
        Page<WfProcDefRelVo> page = wfProcDefRelMapper.getPageList(wfProcDefRelDto.getPage(),wfProcDefRelDto);
        log.debug("查询条数:"+page.getTotal());
        return page;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfProcDefRelVo get(Long id) {
        WfProcDefRel wfProcDefRel = super.getById(id);
        WfProcDefRelVo wfProcDefRelVo = null;
        if(wfProcDefRel !=null){
            wfProcDefRelVo = dozerMapper.map(wfProcDefRel,WfProcDefRelVo.class);
        }
        log.debug("查询成功");
        return wfProcDefRelVo;
    }

    /**
    * 保存实体
    * @param wfProcDefRelDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfProcDefRelDto wfProcDefRelDto) {
        WfProcDefRel wfProcDefRel = dozerMapper.map(wfProcDefRelDto,WfProcDefRel.class);
        boolean result = super.save(wfProcDefRel);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfProcDefRelDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfProcDefRelDto wfProcDefRelDto) {
        WfProcDefRel wfProcDefRel = dozerMapper.map(wfProcDefRelDto,WfProcDefRel.class);
        boolean result = super.updateById(wfProcDefRel);
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
    public void deleteByNodeDefId(List<Serializable> delIds) {
        log.debug("根据子流程数据id 删除表单信息（子流程使用）. camundaNodeDefId：{}", delIds);
        if(CollectionUtil.isEmpty(delIds)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfProcDefRel> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfProcDefRel::getMainNodeDefId,delIds);
        int line = wfProcDefRelMapper.deleteBatchIds(delIds);
        log.debug("根据子流程数据id 删除表单信息（子流程使用）. 影响行数：{}", line);
    }

    @Override
    public WfProcDefRel getCalActRelByCmdProcIdAndCmdNodeId(String camundaProcdefId, String camundaNodeId) {
        LambdaQueryWrapper<WfProcDefRel> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(WfProcDefRel::getMainCamundaProcdefId, camundaProcdefId);
        queryWrapper.eq(WfProcDefRel::getMainCamundaNodeDefId,camundaNodeId);
        return wfProcDefRelMapper.selectOne(queryWrapper);
    }
}
