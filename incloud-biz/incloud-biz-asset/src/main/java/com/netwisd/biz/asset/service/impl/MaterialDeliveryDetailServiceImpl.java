package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialDeliveryDetailDto;
import com.netwisd.biz.asset.entity.MaterialDeliveryDetail;
import com.netwisd.biz.asset.mapper.MaterialDeliveryDetailMapper;
import com.netwisd.biz.asset.service.MaterialDeliveryDetailService;
import com.netwisd.biz.asset.vo.MaterialDeliveryDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产出库明细详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-24 14:37:41
 */
@Service
@Slf4j
public class MaterialDeliveryDetailServiceImpl extends BatchServiceImpl<MaterialDeliveryDetailMapper, MaterialDeliveryDetail> implements MaterialDeliveryDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialDeliveryDetailMapper materialDeliveryDetailMapper;

    /**
    * 单表简单查询操作
    * @param materialDeliveryDetailDto
    * @return
    */
    @Override
    public Page list(MaterialDeliveryDetailDto materialDeliveryDetailDto) {
        LambdaQueryWrapper<MaterialDeliveryDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialDeliveryDetail> page = materialDeliveryDetailMapper.selectPage(materialDeliveryDetailDto.getPage(),queryWrapper);
        Page<MaterialDeliveryDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDeliveryDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialDeliveryDetailDto
    * @return
    */
    @Override
    public Page lists(MaterialDeliveryDetailDto materialDeliveryDetailDto) {
        Page<MaterialDeliveryDetailVo> pageVo = materialDeliveryDetailMapper.getPageList(materialDeliveryDetailDto.getPage(),materialDeliveryDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDeliveryDetailVo get(Long id) {
        MaterialDeliveryDetail materialDeliveryDetail = super.getById(id);
        MaterialDeliveryDetailVo materialDeliveryDetailVo = null;
        if(materialDeliveryDetail !=null){
            materialDeliveryDetailVo = dozerMapper.map(materialDeliveryDetail,MaterialDeliveryDetailVo.class);
        }
        return materialDeliveryDetailVo;
    }

    /**
    * 保存实体
    * @param materialDeliveryDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialDeliveryDetailDto materialDeliveryDetailDto) {
        MaterialDeliveryDetail materialDeliveryDetail = dozerMapper.map(materialDeliveryDetailDto,MaterialDeliveryDetail.class);
        super.save(materialDeliveryDetail);
        saveSubList(materialDeliveryDetailDto);
    }

    /**
     * 保存集合
     * @param materialDeliveryDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialDeliveryDetailDto> materialDeliveryDetailDtos){
        List<MaterialDeliveryDetail> MaterialDeliveryDetails = DozerUtils.mapList(dozerMapper, materialDeliveryDetailDtos, MaterialDeliveryDetail.class);
        super.saveBatch(MaterialDeliveryDetails);
//        for (MaterialDeliveryDetailDto materialDeliveryDetailDto : materialDeliveryDetailDtos){
//            saveSubList(materialDeliveryDetailDto);
//        }
    }

    /**
     * 保存子表集合
     * @param materialDeliveryDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialDeliveryDetailDto materialDeliveryDetailDto){
    }

    /**
     * 修改实体
     * @param materialDeliveryDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialDeliveryDetailDto materialDeliveryDetailDto) {
        materialDeliveryDetailDto.setUpdateTime(LocalDateTime.now());
        MaterialDeliveryDetail materialDeliveryDetail = dozerMapper.map(materialDeliveryDetailDto,MaterialDeliveryDetail.class);
        super.updateById(materialDeliveryDetail);
        updateSub(materialDeliveryDetailDto);
    }

    /**
    * 修改子类实体
    * @param materialDeliveryDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialDeliveryDetailDto materialDeliveryDetailDto) {
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
    public void deleteByDeliveryId(Long id){
        LambdaQueryWrapper<MaterialDeliveryDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialDeliveryDetail::getDeliveryId,id);
        List<MaterialDeliveryDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过出库id（deliveryId）获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialDeliveryDetailVo> getByDeliveryId(Long id){
        LambdaQueryWrapper<MaterialDeliveryDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialDeliveryDetail::getDeliveryId,id);
        List<MaterialDeliveryDetail> list = this.list(queryWrapper);
        List<MaterialDeliveryDetailVo> materialDeliveryDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialDeliveryDetailVo.class);
        return materialDeliveryDetailVos;

    }
}
