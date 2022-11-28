package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaintainAssetsResultDto;
import com.netwisd.biz.asset.entity.MaintainAssetsResult;
import com.netwisd.biz.asset.mapper.MaintainAssetsResultMapper;
import com.netwisd.biz.asset.service.MaintainAssetsResultService;
import com.netwisd.biz.asset.vo.MaintainAssetsResultVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 维修资产明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-26 14:08:43
 */
@Service
@Slf4j
public class MaintainAssetsResultServiceImpl extends ServiceImpl<MaintainAssetsResultMapper, MaintainAssetsResult> implements MaintainAssetsResultService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaintainAssetsResultMapper maintainAssetsResultMapper;

    /**
    * 单表简单查询操作
    * @param maintainAssetsResultDto
    * @return
    */
    @Override
    public Page list(MaintainAssetsResultDto maintainAssetsResultDto) {
        LambdaQueryWrapper<MaintainAssetsResult> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MaintainAssetsResult> page = maintainAssetsResultMapper.selectPage(maintainAssetsResultDto.getPage(),queryWrapper);
        Page<MaintainAssetsResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaintainAssetsResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param maintainAssetsResultDto
    * @return
    */
    @Override
    public Page lists(MaintainAssetsResultDto maintainAssetsResultDto) {
        Page<MaintainAssetsResultVo> pageVo = maintainAssetsResultMapper.getPageList(maintainAssetsResultDto.getPage(),maintainAssetsResultDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaintainAssetsResultVo get(Long id) {
        MaintainAssetsResult maintainAssetsResult = super.getById(id);
        MaintainAssetsResultVo maintainAssetsResultVo = null;
        if(maintainAssetsResult !=null){
            maintainAssetsResultVo = dozerMapper.map(maintainAssetsResult,MaintainAssetsResultVo.class);
        }
        log.debug("查询成功");
        return maintainAssetsResultVo;
    }

    /**
    * 保存实体
    * @param maintainAssetsResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaintainAssetsResultDto maintainAssetsResultDto) {
        MaintainAssetsResult maintainAssetsResult = dozerMapper.map(maintainAssetsResultDto,MaintainAssetsResult.class);
        boolean result = super.save(maintainAssetsResult);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param maintainAssetsResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MaintainAssetsResultDto maintainAssetsResultDto) {
        maintainAssetsResultDto.setUpdateTime(LocalDateTime.now());
        MaintainAssetsResult maintainAssetsResult = dozerMapper.map(maintainAssetsResultDto,MaintainAssetsResult.class);
        boolean result = super.updateById(maintainAssetsResult);
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
    public Boolean saveList(List<MaintainAssetsResultDto> maintainAssetsResultDtos) {
        List<MaintainAssetsResult> maintainAssetsResults = DozerUtils.mapList(dozerMapper, maintainAssetsResultDtos, MaintainAssetsResult.class);
        return super.saveBatch(maintainAssetsResults);
    }
}
