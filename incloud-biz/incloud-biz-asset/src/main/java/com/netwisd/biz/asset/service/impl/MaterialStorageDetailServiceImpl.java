package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialStorageDetailDto;
import com.netwisd.biz.asset.entity.MaterialStorageDetail;
import com.netwisd.biz.asset.mapper.MaterialStorageDetailMapper;
import com.netwisd.biz.asset.service.MaterialStorageDetailService;
import com.netwisd.biz.asset.vo.MaterialStorageDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产入库明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:44:47
 */
@Service
@Slf4j
public class MaterialStorageDetailServiceImpl extends BatchServiceImpl<MaterialStorageDetailMapper, MaterialStorageDetail> implements MaterialStorageDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialStorageDetailMapper materialStorageDetailMapper;

    /**
    * 单表简单查询操作
    * @param materialStorageDetailDto
    * @return
    */
    @Override
    public Page list(MaterialStorageDetailDto materialStorageDetailDto) {
        LambdaQueryWrapper<MaterialStorageDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialStorageDetail> page = materialStorageDetailMapper.selectPage(materialStorageDetailDto.getPage(),queryWrapper);
        Page<MaterialStorageDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialStorageDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialStorageDetailDto
    * @return
    */
    @Override
    public Page lists(MaterialStorageDetailDto materialStorageDetailDto) {
        Page<MaterialStorageDetailVo> pageVo = materialStorageDetailMapper.getPageList(materialStorageDetailDto.getPage(),materialStorageDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialStorageDetailVo get(Long id) {
        MaterialStorageDetail materialStorageDetail = super.getById(id);
        MaterialStorageDetailVo materialStorageDetailVo = null;
        if(materialStorageDetail !=null){
            materialStorageDetailVo = dozerMapper.map(materialStorageDetail,MaterialStorageDetailVo.class);
        }
        return materialStorageDetailVo;
    }

    /**
    * 保存实体
    * @param materialStorageDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialStorageDetailDto materialStorageDetailDto) {
        MaterialStorageDetail materialStorageDetail = dozerMapper.map(materialStorageDetailDto,MaterialStorageDetail.class);
        super.save(materialStorageDetail);
        saveSubList(materialStorageDetailDto);
    }

    /**
     * 保存集合
     * @param materialStorageDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialStorageDetailDto> materialStorageDetailDtos){
        List<MaterialStorageDetail> MaterialStorageDetails = DozerUtils.mapList(dozerMapper, materialStorageDetailDtos, MaterialStorageDetail.class);
        super.saveBatch(MaterialStorageDetails);
        for (MaterialStorageDetailDto materialStorageDetailDto : materialStorageDetailDtos){
            saveSubList(materialStorageDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialStorageDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialStorageDetailDto materialStorageDetailDto){
    }

    /**
     * 修改实体
     * @param materialStorageDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialStorageDetailDto materialStorageDetailDto) {
        materialStorageDetailDto.setUpdateTime(LocalDateTime.now());
        MaterialStorageDetail materialStorageDetail = dozerMapper.map(materialStorageDetailDto,MaterialStorageDetail.class);
        super.updateById(materialStorageDetail);
        updateSub(materialStorageDetailDto);
    }

    /**
    * 修改子类实体
    * @param materialStorageDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialStorageDetailDto materialStorageDetailDto) {
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
        LambdaQueryWrapper<MaterialStorageDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialStorageDetail::getStorageId,id);
        List<MaterialStorageDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialStorageDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<MaterialStorageDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialStorageDetail::getStorageId,id);
        List<MaterialStorageDetail> list = this.list(queryWrapper);
        List<MaterialStorageDetailVo> materialStorageDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialStorageDetailVo.class);
        return materialStorageDetailVos;
    }
}
