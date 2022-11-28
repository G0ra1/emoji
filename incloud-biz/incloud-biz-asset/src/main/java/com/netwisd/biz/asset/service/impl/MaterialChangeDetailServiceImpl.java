package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialChangeDetailDto;
import com.netwisd.biz.asset.entity.MaterialChangeDetail;
import com.netwisd.biz.asset.mapper.MaterialChangeDetailMapper;
import com.netwisd.biz.asset.service.MaterialChangeDetailService;
import com.netwisd.biz.asset.vo.MaterialChangeDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产变更详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:11:54
 */
@Service
@Slf4j
public class MaterialChangeDetailServiceImpl extends BatchServiceImpl<MaterialChangeDetailMapper, MaterialChangeDetail> implements MaterialChangeDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialChangeDetailMapper materialChangeDetailMapper;

    /**
    * 单表简单查询操作
    * @param materialChangeDetailDto
    * @return
    */
    @Override
    public Page list(MaterialChangeDetailDto materialChangeDetailDto) {
        LambdaQueryWrapper<MaterialChangeDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialChangeDetail> page = materialChangeDetailMapper.selectPage(materialChangeDetailDto.getPage(),queryWrapper);
        Page<MaterialChangeDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialChangeDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialChangeDetailDto
    * @return
    */
    @Override
    public Page lists(MaterialChangeDetailDto materialChangeDetailDto) {
        Page<MaterialChangeDetailVo> pageVo = materialChangeDetailMapper.getPageList(materialChangeDetailDto.getPage(),materialChangeDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialChangeDetailVo get(Long id) {
        MaterialChangeDetail materialChangeDetail = super.getById(id);
        MaterialChangeDetailVo materialChangeDetailVo = null;
        if(materialChangeDetail !=null){
            materialChangeDetailVo = dozerMapper.map(materialChangeDetail,MaterialChangeDetailVo.class);
        }
        return materialChangeDetailVo;
    }

    /**
    * 保存实体
    * @param materialChangeDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialChangeDetailDto materialChangeDetailDto) {
        MaterialChangeDetail materialChangeDetail = dozerMapper.map(materialChangeDetailDto,MaterialChangeDetail.class);
        return super.save(materialChangeDetail);
    }

    /**
     * 保存集合
     * @param materialChangeDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<MaterialChangeDetailDto> materialChangeDetailDtos){
        List<MaterialChangeDetail> MaterialChangeDetails = DozerUtils.mapList(dozerMapper, materialChangeDetailDtos, MaterialChangeDetail.class);
        return super.saveBatch(MaterialChangeDetails);
    }

    /**
     * 修改实体
     * @param materialChangeDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialChangeDetailDto materialChangeDetailDto) {
        materialChangeDetailDto.setUpdateTime(LocalDateTime.now());
        MaterialChangeDetail materialChangeDetail = dozerMapper.map(materialChangeDetailDto,MaterialChangeDetail.class);
        super.updateById(materialChangeDetail);
        updateSub(materialChangeDetailDto);
    }

    /**
    * 修改子类实体
    * @param materialChangeDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialChangeDetailDto materialChangeDetailDto) {
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public void delete(Long id) {
        super.removeById(id);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public Boolean deleteByChangeId(Long id){
        LambdaQueryWrapper<MaterialChangeDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialChangeDetail::getChangeId,id);
        List<MaterialChangeDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialChangeDetailVo> getByChangeId(Long id){
        LambdaQueryWrapper<MaterialChangeDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialChangeDetail::getChangeId,id);
        List<MaterialChangeDetail> list = this.list(queryWrapper);
        List<MaterialChangeDetailVo> materialChangeDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialChangeDetailVo.class);
        return materialChangeDetailVos;
    }
}
