package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialAcceptDetailDto;
import com.netwisd.biz.asset.entity.MaterialAcceptDetail;
import com.netwisd.biz.asset.mapper.MaterialAcceptDetailMapper;
import com.netwisd.biz.asset.service.MaterialAcceptDetailService;
import com.netwisd.biz.asset.vo.MaterialAcceptDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产领用明细详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
@Service
@Slf4j
public class MaterialAcceptDetailServiceImpl extends BatchServiceImpl<MaterialAcceptDetailMapper, MaterialAcceptDetail> implements MaterialAcceptDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialAcceptDetailMapper materialAcceptDetailMapper;

    /**
     * 单表简单查询操作
     * @param materialAcceptDetailDto
     * @return
     */
    @Override
    public Page list(MaterialAcceptDetailDto materialAcceptDetailDto) {
        LambdaQueryWrapper<MaterialAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialAcceptDetail> page = materialAcceptDetailMapper.selectPage(materialAcceptDetailDto.getPage(),queryWrapper);
        Page<MaterialAcceptDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialAcceptDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param materialAcceptDetailDto
     * @return
     */
    @Override
    public Page lists(MaterialAcceptDetailDto materialAcceptDetailDto) {
        Page<MaterialAcceptDetailVo> pageVo = materialAcceptDetailMapper.getPageList(materialAcceptDetailDto.getPage(),materialAcceptDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public MaterialAcceptDetailVo get(Long id) {
        MaterialAcceptDetail materialAcceptDetail = super.getById(id);
        MaterialAcceptDetailVo materialAcceptDetailVo = null;
        if(materialAcceptDetail !=null){
            materialAcceptDetailVo = dozerMapper.map(materialAcceptDetail,MaterialAcceptDetailVo.class);
        }
        return materialAcceptDetailVo;
    }

    /**
     * 保存实体
     * @param materialAcceptDetailDto
     * @return
     */
    @Transactional
    @Override
    public void save(MaterialAcceptDetailDto materialAcceptDetailDto) {
        MaterialAcceptDetail materialAcceptDetail = dozerMapper.map(materialAcceptDetailDto,MaterialAcceptDetail.class);
        super.save(materialAcceptDetail);
        saveSubList(materialAcceptDetailDto);
    }

    /**
     * 保存集合
     * @param materialAcceptDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialAcceptDetailDto> materialAcceptDetailDtos){
        List<MaterialAcceptDetail> MaterialAcceptDetails = DozerUtils.mapList(dozerMapper, materialAcceptDetailDtos, MaterialAcceptDetail.class);
        super.saveBatch(MaterialAcceptDetails);
        for (MaterialAcceptDetailDto materialAcceptDetailDto : materialAcceptDetailDtos){
            saveSubList(materialAcceptDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialAcceptDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialAcceptDetailDto materialAcceptDetailDto){
    }

    /**
     * 修改实体
     * @param materialAcceptDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialAcceptDetailDto materialAcceptDetailDto) {
        materialAcceptDetailDto.setUpdateTime(LocalDateTime.now());
        MaterialAcceptDetail materialAcceptDetail = dozerMapper.map(materialAcceptDetailDto,MaterialAcceptDetail.class);
        super.updateById(materialAcceptDetail);
        updateSub(materialAcceptDetailDto);
    }

    /**
     * 修改子类实体
     * @param materialAcceptDetailDto
     * @return
     */
    @Transactional
    @Override
    public void updateSub(MaterialAcceptDetailDto materialAcceptDetailDto) {
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
     * 通过acceptId删除
     * @param acceptId
     * @return
     */
    @Override
    public void deleteByAcceptId(Long acceptId){
        LambdaQueryWrapper<MaterialAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(acceptId),MaterialAcceptDetail::getAcceptId,acceptId);
        List<MaterialAcceptDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过acceptId删除
     * @param acceptAssetsId
     * @return
     */
    @Override
    public void deleteByAcceptAssetsId(Long acceptAssetsId) {
        LambdaQueryWrapper<MaterialAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(acceptAssetsId),MaterialAcceptDetail::getAcceptAssetsId,acceptAssetsId);
        List<MaterialAcceptDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过acceptId获取
     * @param acceptId
     * @return
     */
    @Override
    public List<MaterialAcceptDetailVo> getByAcceptId(Long acceptId){
        LambdaQueryWrapper<MaterialAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(acceptId),MaterialAcceptDetail::getAcceptId,acceptId);
        List<MaterialAcceptDetail> list = this.list(queryWrapper);
        List<MaterialAcceptDetailVo> materialAcceptDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialAcceptDetailVo.class);
        return materialAcceptDetailVos;
    }

    /**
     * 通过acceptAssetsId获取
     * @param acceptAssetsId
     * @return
     */
    @Override
    public List<MaterialAcceptDetailVo> getByAcceptAssetsId(Long acceptAssetsId){
        LambdaQueryWrapper<MaterialAcceptDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(acceptAssetsId),MaterialAcceptDetail::getAcceptAssetsId,acceptAssetsId);
        List<MaterialAcceptDetail> list = this.list(queryWrapper);
        List<MaterialAcceptDetailVo> materialAcceptDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialAcceptDetailVo.class);
        return materialAcceptDetailVos;
    }
}
