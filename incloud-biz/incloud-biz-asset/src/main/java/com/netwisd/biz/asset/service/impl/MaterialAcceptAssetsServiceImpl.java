package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialAcceptAssetsDto;
import com.netwisd.biz.asset.entity.MaterialAcceptAssets;
import com.netwisd.biz.asset.mapper.MaterialAcceptAssetsMapper;
import com.netwisd.biz.asset.service.MaterialAcceptAssetsService;
import com.netwisd.biz.asset.vo.MaterialAcceptAssetsVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产领用资产明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Service
@Slf4j
public class MaterialAcceptAssetsServiceImpl extends BatchServiceImpl<MaterialAcceptAssetsMapper, MaterialAcceptAssets> implements MaterialAcceptAssetsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialAcceptAssetsMapper materialAcceptAssetsMapper;

    /**
     * 单表简单查询操作
     * @param materialAcceptAssetsDto
     * @return
     */
    @Override
    public Page list(MaterialAcceptAssetsDto materialAcceptAssetsDto) {
        LambdaQueryWrapper<MaterialAcceptAssets> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialAcceptAssets> page = materialAcceptAssetsMapper.selectPage(materialAcceptAssetsDto.getPage(),queryWrapper);
        Page<MaterialAcceptAssetsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialAcceptAssetsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param materialAcceptAssetsDto
     * @return
     */
    @Override
    public Page lists(MaterialAcceptAssetsDto materialAcceptAssetsDto) {
        Page<MaterialAcceptAssetsVo> pageVo = materialAcceptAssetsMapper.getPageList(materialAcceptAssetsDto.getPage(),materialAcceptAssetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public MaterialAcceptAssetsVo get(Long id) {
        MaterialAcceptAssets materialAcceptAssets = super.getById(id);
        MaterialAcceptAssetsVo materialAcceptAssetsVo = null;
        if(materialAcceptAssets !=null){
            materialAcceptAssetsVo = dozerMapper.map(materialAcceptAssets,MaterialAcceptAssetsVo.class);
        }
        return materialAcceptAssetsVo;
    }

    /**
     * 保存实体
     * @param materialAcceptAssetsDto
     * @return
     */
    @Transactional
    @Override
    public void save(MaterialAcceptAssetsDto materialAcceptAssetsDto) {
        MaterialAcceptAssets materialAcceptAssets = dozerMapper.map(materialAcceptAssetsDto,MaterialAcceptAssets.class);
        super.save(materialAcceptAssets);
        saveSubList(materialAcceptAssetsDto);
    }

    /**
     * 保存集合
     * @param materialAcceptAssetsDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialAcceptAssetsDto> materialAcceptAssetsDtos){
        List<MaterialAcceptAssets> MaterialAcceptAssetss = DozerUtils.mapList(dozerMapper, materialAcceptAssetsDtos, MaterialAcceptAssets.class);
        super.saveBatch(MaterialAcceptAssetss);
        for (MaterialAcceptAssetsDto materialAcceptAssetsDto : materialAcceptAssetsDtos){
            saveSubList(materialAcceptAssetsDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialAcceptAssetsDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialAcceptAssetsDto materialAcceptAssetsDto){
    }

    /**
     * 修改实体
     * @param materialAcceptAssetsDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MaterialAcceptAssetsDto materialAcceptAssetsDto) {
        materialAcceptAssetsDto.setUpdateTime(LocalDateTime.now());
        MaterialAcceptAssets materialAcceptAssets = dozerMapper.map(materialAcceptAssetsDto,MaterialAcceptAssets.class);
        return super.updateById(materialAcceptAssets);
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
    public void deleteByFkId(Long id){
        LambdaQueryWrapper<MaterialAcceptAssets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialAcceptAssets::getAcceptId,id);
        List<MaterialAcceptAssets> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialAcceptAssetsVo> getByAcceptId(Long id){
        LambdaQueryWrapper<MaterialAcceptAssets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialAcceptAssets::getAcceptId,id);
        List<MaterialAcceptAssets> list = this.list(queryWrapper);
        List<MaterialAcceptAssetsVo> materialAcceptAssetsVos = DozerUtils.mapList(dozerMapper, list, MaterialAcceptAssetsVo.class);
        return materialAcceptAssetsVos;
    }
    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public Boolean deleteByAcceptId(Long id){
        LambdaQueryWrapper<MaterialAcceptAssets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialAcceptAssets::getAcceptId,id);
        List<MaterialAcceptAssets> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

}
