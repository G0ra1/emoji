package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialDistributeAssetsDto;
import com.netwisd.biz.asset.entity.MaterialDistributeAssets;
import com.netwisd.biz.asset.mapper.MaterialDistributeAssetsMapper;
import com.netwisd.biz.asset.service.MaterialDistributeAssetsService;
import com.netwisd.biz.asset.vo.MaterialDistributeAssetsVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产派发明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:46:22
 */
@Service
@Slf4j
public class MaterialDistributeAssetsServiceImpl extends BatchServiceImpl<MaterialDistributeAssetsMapper, MaterialDistributeAssets> implements MaterialDistributeAssetsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialDistributeAssetsMapper materialDistributeAssetsMapper;

    /**
    * 单表简单查询操作
    * @param materialDistributeAssetsDto
    * @return
    */
    @Override
    public Page list(MaterialDistributeAssetsDto materialDistributeAssetsDto) {
        LambdaQueryWrapper<MaterialDistributeAssets> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialDistributeAssets> page = materialDistributeAssetsMapper.selectPage(materialDistributeAssetsDto.getPage(),queryWrapper);
        Page<MaterialDistributeAssetsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDistributeAssetsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialDistributeAssetsDto
    * @return
    */
    @Override
    public Page lists(MaterialDistributeAssetsDto materialDistributeAssetsDto) {
        Page<MaterialDistributeAssetsVo> pageVo = materialDistributeAssetsMapper.getPageList(materialDistributeAssetsDto.getPage(),materialDistributeAssetsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDistributeAssetsVo get(Long id) {
        MaterialDistributeAssets materialDistributeAssets = super.getById(id);
        MaterialDistributeAssetsVo materialDistributeAssetsVo = null;
        if(materialDistributeAssets !=null){
            materialDistributeAssetsVo = dozerMapper.map(materialDistributeAssets,MaterialDistributeAssetsVo.class);
        }
        return materialDistributeAssetsVo;
    }

    /**
    * 保存实体
    * @param materialDistributeAssetsDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialDistributeAssetsDto materialDistributeAssetsDto) {
        MaterialDistributeAssets materialDistributeAssets = dozerMapper.map(materialDistributeAssetsDto,MaterialDistributeAssets.class);
        super.save(materialDistributeAssets);
        saveSubList(materialDistributeAssetsDto);
    }

    /**
     * 保存集合
     * @param materialDistributeAssetsDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialDistributeAssetsDto> materialDistributeAssetsDtos){
        List<MaterialDistributeAssets> MaterialDistributeAssetss = DozerUtils.mapList(dozerMapper, materialDistributeAssetsDtos, MaterialDistributeAssets.class);
        super.saveBatch(MaterialDistributeAssetss);
        for (MaterialDistributeAssetsDto materialDistributeAssetsDto : materialDistributeAssetsDtos){
            saveSubList(materialDistributeAssetsDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialDistributeAssetsDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialDistributeAssetsDto materialDistributeAssetsDto){
    }

    /**
     * 修改实体
     * @param materialDistributeAssetsDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialDistributeAssetsDto materialDistributeAssetsDto) {
        materialDistributeAssetsDto.setUpdateTime(LocalDateTime.now());
        MaterialDistributeAssets materialDistributeAssets = dozerMapper.map(materialDistributeAssetsDto,MaterialDistributeAssets.class);
        super.updateById(materialDistributeAssets);
        updateSub(materialDistributeAssetsDto);
    }

    /**
    * 修改子类实体
    * @param materialDistributeAssetsDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialDistributeAssetsDto materialDistributeAssetsDto) {
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
    public Boolean deleteByDistributeId(Long id){
        LambdaQueryWrapper<MaterialDistributeAssets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialDistributeAssets::getDistributeId,id);
        List<MaterialDistributeAssets> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialDistributeAssetsVo> getByDistributeId(Long id){
        LambdaQueryWrapper<MaterialDistributeAssets> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialDistributeAssets::getDistributeId,id);
        List<MaterialDistributeAssets> list = this.list(queryWrapper);
        List<MaterialDistributeAssetsVo> materialDistributeAssetsVos = DozerUtils.mapList(dozerMapper, list, MaterialDistributeAssetsVo.class);
        return materialDistributeAssetsVos;
    }
}
