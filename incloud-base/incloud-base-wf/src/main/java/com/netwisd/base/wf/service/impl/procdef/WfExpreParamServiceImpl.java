package com.netwisd.base.wf.service.impl.procdef;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.entity.WfExpreParam;
import com.netwisd.base.wf.mapper.WfExpreParamMapper;
import com.netwisd.base.wf.service.procdef.WfExpreParamService;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfExpreParamDto;
import com.netwisd.base.wf.vo.WfExpreParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description 表达式参数维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:23:36
 */
@Service
@Slf4j
public class WfExpreParamServiceImpl extends BatchServiceImpl<WfExpreParamMapper, WfExpreParam> implements WfExpreParamService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfExpreParamMapper wfExpreParamMapper;

    /**
    * 单表简单查询操作
    * @param wfExpreParamDto
    * @return
    */
    @Override
    public Page list(WfExpreParamDto wfExpreParamDto) {
        WfExpreParam wfExpreParam = dozerMapper.map(wfExpreParamDto,WfExpreParam.class);
        QueryWrapper<WfExpreParam> queryWrapper = new QueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfExpreParam);
        Page<WfExpreParam> page = wfExpreParamMapper.selectPage(wfExpreParamDto.getPage(),queryWrapper);
        Page<WfExpreParamVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfExpreParamVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfExpreParamDto
    * @return
    */
    @Override
    public Page lists(WfExpreParamDto wfExpreParamDto) {
        Page<WfExpreParamVo> pageVo = wfExpreParamMapper.getPageList(wfExpreParamDto.getPage(),wfExpreParamDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfExpreParamVo get(Long id) {
        WfExpreParam wfExpreParam = super.getById(id);
        WfExpreParamVo wfExpreParamVo = null;
        if(wfExpreParam !=null){
            wfExpreParamVo = dozerMapper.map(wfExpreParam,WfExpreParamVo.class);
        }
        log.debug("查询成功");
        return wfExpreParamVo;
    }

    /**
    * 保存实体
    * @param wfExpreParamDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfExpreParamDto wfExpreParamDto) {
        WfExpreParam wfExpreParam = dozerMapper.map(wfExpreParamDto,WfExpreParam.class);
        boolean result = super.save(wfExpreParam);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfExpreParamDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfExpreParamDto wfExpreParamDto) {
        WfExpreParam wfExpreParam = dozerMapper.map(wfExpreParamDto,WfExpreParam.class);
        boolean result = super.updateById(wfExpreParam);
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

    private LambdaQueryWrapper<WfExpreParam> byExpreId(Long id){
        LambdaQueryWrapper<WfExpreParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfExpreParam::getExpreId,id);
        return queryWrapper;
    }

    @Override
    public Boolean removeByExpreId(Long id) {
        LambdaQueryWrapper<WfExpreParam> queryWrapper = byExpreId(id);
        int delete = wfExpreParamMapper.delete(queryWrapper);
        log.info("成功删除:{} 条",delete);
        return true;
    }

    @Override
    public List<WfExpreParam> getByExpreId(Long id) {
        LambdaQueryWrapper<WfExpreParam> queryWrapper = byExpreId(id);
        List<WfExpreParam> wfExpreParams = wfExpreParamMapper.selectList(queryWrapper);
        log.info("查询条数:{}",wfExpreParams.size());
        return wfExpreParams;
    }
}
