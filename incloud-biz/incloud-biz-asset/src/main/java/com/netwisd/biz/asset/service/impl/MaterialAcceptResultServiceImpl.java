package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialAcceptResultDto;
import com.netwisd.biz.asset.entity.MaterialAcceptResult;
import com.netwisd.biz.asset.mapper.MaterialAcceptResultMapper;
import com.netwisd.biz.asset.service.MaterialAcceptResultService;
import com.netwisd.biz.asset.vo.MaterialAcceptResultVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产领用详情结果 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Service
@Slf4j
public class MaterialAcceptResultServiceImpl extends ServiceImpl<MaterialAcceptResultMapper, MaterialAcceptResult> implements MaterialAcceptResultService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialAcceptResultMapper materialAcceptResultMapper;

    /**
    * 单表简单查询操作
    * @param materialAcceptResultDto
    * @return
    */
    @Override
    public Page list(MaterialAcceptResultDto materialAcceptResultDto) {
        LambdaQueryWrapper<MaterialAcceptResult> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MaterialAcceptResult> page = materialAcceptResultMapper.selectPage(materialAcceptResultDto.getPage(),queryWrapper);
        Page<MaterialAcceptResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialAcceptResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialAcceptResultDto
    * @return
    */
    @Override
    public Page lists(MaterialAcceptResultDto materialAcceptResultDto) {
        Page<MaterialAcceptResultVo> pageVo = materialAcceptResultMapper.getPageList(materialAcceptResultDto.getPage(),materialAcceptResultDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialAcceptResultVo get(Long id) {
        MaterialAcceptResult materialAcceptResult = super.getById(id);
        MaterialAcceptResultVo materialAcceptResultVo = null;
        if(materialAcceptResult !=null){
            materialAcceptResultVo = dozerMapper.map(materialAcceptResult,MaterialAcceptResultVo.class);
        }
        log.debug("查询成功");
        return materialAcceptResultVo;
    }

    /**
    * 保存实体
    * @param materialAcceptResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialAcceptResultDto materialAcceptResultDto) {
        MaterialAcceptResult materialAcceptResult = dozerMapper.map(materialAcceptResultDto,MaterialAcceptResult.class);
        boolean result = super.save(materialAcceptResult);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 保存集合
     * @param materialAcceptResultDtos
     * @return
     */
    @Override
    public Boolean saveList(List<MaterialAcceptResultDto> materialAcceptResultDtos) {
        List<MaterialAcceptResult> materialAcceptResults = DozerUtils.mapList(dozerMapper, materialAcceptResultDtos, MaterialAcceptResult.class);
        return super.saveBatch(materialAcceptResults);
    }

    /**
    * 修改实体
    * @param materialAcceptResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MaterialAcceptResultDto materialAcceptResultDto) {
        materialAcceptResultDto.setUpdateTime(LocalDateTime.now());
        MaterialAcceptResult materialAcceptResult = dozerMapper.map(materialAcceptResultDto,MaterialAcceptResult.class);
        boolean result = super.updateById(materialAcceptResult);
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
