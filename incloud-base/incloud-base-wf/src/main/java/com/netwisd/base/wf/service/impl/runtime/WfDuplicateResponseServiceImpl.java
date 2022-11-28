package com.netwisd.base.wf.service.impl.runtime;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.wf.entity.WfDuplicateResponse;
import com.netwisd.base.wf.mapper.WfDuplicateResponseMapper;
import com.netwisd.base.wf.service.runtime.WfDuplicateResponseService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.wf.dto.WfDuplicateResponseDto;
import com.netwisd.base.wf.vo.WfDuplicateResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 传阅回复 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 22:12:53
 */
@Service
@Slf4j
public class WfDuplicateResponseServiceImpl extends ServiceImpl<WfDuplicateResponseMapper, WfDuplicateResponse> implements WfDuplicateResponseService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfDuplicateResponseMapper wfDuplicateResponseMapper;

    /**
    * 单表简单查询操作
    * @param wfDuplicateResponseDto
    * @return
    */
    @Override
    public Page list(WfDuplicateResponseDto wfDuplicateResponseDto) {
        LambdaQueryWrapper<WfDuplicateResponse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfDuplicateResponse::getInDuplicateTaskId,wfDuplicateResponseDto.getInDuplicateTaskId())
            .eq(WfDuplicateResponse::getOutDuplicateTaskId,wfDuplicateResponseDto.getOutDuplicateTaskId());
        queryWrapper.orderByDesc(WfDuplicateResponse::getCreateTime);
        Page<WfDuplicateResponse> page = wfDuplicateResponseMapper.selectPage(wfDuplicateResponseDto.getPage(),queryWrapper);
        Page<WfDuplicateResponseVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDuplicateResponseVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfDuplicateResponseDto
    * @return
    */
    @Override
    public Page lists(WfDuplicateResponseDto wfDuplicateResponseDto) {
        Page<WfDuplicateResponseVo> page = wfDuplicateResponseMapper.getPageList(wfDuplicateResponseDto.getPage(),wfDuplicateResponseDto);
        Page<WfDuplicateResponseVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDuplicateResponseVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfDuplicateResponseVo get(Long id) {
        WfDuplicateResponse wfDuplicateResponse = super.getById(id);
        WfDuplicateResponseVo wfDuplicateResponseVo = null;
        if(wfDuplicateResponse !=null){
            wfDuplicateResponseVo = dozerMapper.map(wfDuplicateResponse,WfDuplicateResponseVo.class);
        }
        log.debug("查询成功");
        return wfDuplicateResponseVo;
    }

    /**
    * 保存实体
    * @param wfDuplicateResponseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfDuplicateResponseDto wfDuplicateResponseDto) {
        WfDuplicateResponse wfDuplicateResponse = dozerMapper.map(wfDuplicateResponseDto,WfDuplicateResponse.class);
        boolean result = super.save(wfDuplicateResponse);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfDuplicateResponseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfDuplicateResponseDto wfDuplicateResponseDto) {
        WfDuplicateResponse wfDuplicateResponse = dozerMapper.map(wfDuplicateResponseDto,WfDuplicateResponse.class);
        boolean result = super.updateById(wfDuplicateResponse);
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
