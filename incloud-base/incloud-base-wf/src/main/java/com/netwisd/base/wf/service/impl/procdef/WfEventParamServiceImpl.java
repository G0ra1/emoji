package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.wf.entity.WfEventParam;
import com.netwisd.base.wf.mapper.WfEventParamMapper;
import com.netwisd.base.wf.service.procdef.WfEventParamService;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfEventParamDto;
import com.netwisd.base.wf.vo.WfEventParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 事件运行参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:10:12
 */
@Service
@Slf4j
public class WfEventParamServiceImpl extends ServiceImpl<WfEventParamMapper, WfEventParam> implements WfEventParamService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfEventParamMapper wfEventParamMapper;

    /**
    * 单表简单查询操作
    * @param wfEventParamDto
    * @return
    */
    @Override
    public Page list(WfEventParamDto wfEventParamDto) {
        WfEventParam wfEventParam = dozerMapper.map(wfEventParamDto,WfEventParam.class);
        LambdaQueryWrapper<WfEventParam> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfEventParam);
        Page<WfEventParam> page = wfEventParamMapper.selectPage(wfEventParamDto.getPage(),queryWrapper);
        Page<WfEventParamVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfEventParamVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfEventParamDto
    * @return
    */
    @Override
    public Page lists(WfEventParamDto wfEventParamDto) {
        Page<WfEventParamVo> pageVo = wfEventParamMapper.getPageList(wfEventParamDto.getPage(),wfEventParamDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfEventParamVo get(Long id) {
        WfEventParam wfEventParam = super.getById(id);
        WfEventParamVo wfEventParamVo = null;
        if(wfEventParam !=null){
            wfEventParamVo = dozerMapper.map(wfEventParam,WfEventParamVo.class);
        }
        log.debug("查询成功");
        return wfEventParamVo;
    }

    /**
    * 保存实体
    * @param wfEventParamDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfEventParamDto wfEventParamDto) {
        WfEventParam wfEventParam = dozerMapper.map(wfEventParamDto,WfEventParam.class);
        boolean result = super.save(wfEventParam);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfEventParamDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfEventParamDto wfEventParamDto) {
        WfEventParam wfEventParam = dozerMapper.map(wfEventParamDto,WfEventParam.class);
        boolean result = super.updateById(wfEventParam);
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
    public Boolean deleteByEventId(Long id) {
        log.debug("删除参数eventId：" + id);
        LambdaQueryWrapper<WfEventParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),WfEventParam::getEventId,id);
        boolean result = super.remove(queryWrapper);
        return result;
    }
}
