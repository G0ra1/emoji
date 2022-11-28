package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialDistributeDetailDto;
import com.netwisd.biz.asset.entity.MaterialDistributeDetail;
import com.netwisd.biz.asset.mapper.MaterialDistributeDetailMapper;
import com.netwisd.biz.asset.service.MaterialDistributeDetailService;
import com.netwisd.biz.asset.vo.MaterialDistributeDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产派发资产详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:46:22
 */
@Service
@Slf4j
public class MaterialDistributeDetailServiceImpl extends BatchServiceImpl<MaterialDistributeDetailMapper, MaterialDistributeDetail> implements MaterialDistributeDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialDistributeDetailMapper materialDistributeDetailMapper;

    /**
    * 单表简单查询操作
    * @param materialDistributeDetailDto
    * @return
    */
    @Override
    public Page list(MaterialDistributeDetailDto materialDistributeDetailDto) {
        LambdaQueryWrapper<MaterialDistributeDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialDistributeDetail> page = materialDistributeDetailMapper.selectPage(materialDistributeDetailDto.getPage(),queryWrapper);
        Page<MaterialDistributeDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDistributeDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialDistributeDetailDto
    * @return
    */
    @Override
    public Page lists(MaterialDistributeDetailDto materialDistributeDetailDto) {
        Page<MaterialDistributeDetailVo> pageVo = materialDistributeDetailMapper.getPageList(materialDistributeDetailDto.getPage(),materialDistributeDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDistributeDetailVo get(Long id) {
        MaterialDistributeDetail materialDistributeDetail = super.getById(id);
        MaterialDistributeDetailVo materialDistributeDetailVo = null;
        if(materialDistributeDetail !=null){
            materialDistributeDetailVo = dozerMapper.map(materialDistributeDetail,MaterialDistributeDetailVo.class);
        }
        return materialDistributeDetailVo;
    }

    /**
    * 保存实体
    * @param materialDistributeDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialDistributeDetailDto materialDistributeDetailDto) {
        MaterialDistributeDetail materialDistributeDetail = dozerMapper.map(materialDistributeDetailDto,MaterialDistributeDetail.class);
        super.save(materialDistributeDetail);
        saveSubList(materialDistributeDetailDto);
    }

    /**
     * 保存集合
     * @param materialDistributeDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialDistributeDetailDto> materialDistributeDetailDtos){
        List<MaterialDistributeDetail> MaterialDistributeDetails = DozerUtils.mapList(dozerMapper, materialDistributeDetailDtos, MaterialDistributeDetail.class);
        super.saveBatch(MaterialDistributeDetails);
        for (MaterialDistributeDetailDto materialDistributeDetailDto : materialDistributeDetailDtos){
            saveSubList(materialDistributeDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialDistributeDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialDistributeDetailDto materialDistributeDetailDto){
    }

    /**
     * 修改实体
     * @param materialDistributeDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialDistributeDetailDto materialDistributeDetailDto) {
        materialDistributeDetailDto.setUpdateTime(LocalDateTime.now());
        MaterialDistributeDetail materialDistributeDetail = dozerMapper.map(materialDistributeDetailDto,MaterialDistributeDetail.class);
        super.updateById(materialDistributeDetail);
        updateSub(materialDistributeDetailDto);
    }

    /**
    * 修改子类实体
    * @param materialDistributeDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialDistributeDetailDto materialDistributeDetailDto) {
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
        LambdaQueryWrapper<MaterialDistributeDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialDistributeDetail::getDistributeId,id);
        List<MaterialDistributeDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialDistributeDetailVo> getByDistributeId(Long id){
        LambdaQueryWrapper<MaterialDistributeDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialDistributeDetail::getDistributeId,id);
        List<MaterialDistributeDetail> list = this.list(queryWrapper);
        List<MaterialDistributeDetailVo> materialDistributeDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialDistributeDetailVo.class);
        return materialDistributeDetailVos;
    }

    /**
     * 通过外建获取
     * @param getByDistributeAssetsId
     * @return
     */
    @Override
    public List<MaterialDistributeDetailVo> getByDistributeAssetsId(Long getByDistributeAssetsId) {
        LambdaQueryWrapper<MaterialDistributeDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(getByDistributeAssetsId), MaterialDistributeDetail::getDistributeAssetsId, getByDistributeAssetsId);
        List<MaterialDistributeDetail> list = this.list(queryWrapper);
        List<MaterialDistributeDetailVo> materialDistributeDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialDistributeDetailVo.class);
        return materialDistributeDetailVos;
    }

}
