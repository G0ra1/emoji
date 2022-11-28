package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialRefundDetailDto;
import com.netwisd.biz.asset.entity.MaterialRefundDetail;
import com.netwisd.biz.asset.mapper.MaterialRefundDetailMapper;
import com.netwisd.biz.asset.service.MaterialRefundDetailService;
import com.netwisd.biz.asset.vo.MaterialRefundDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产退还详情 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:46:00
 */
@Service
@Slf4j
public class MaterialRefundDetailServiceImpl extends BatchServiceImpl<MaterialRefundDetailMapper, MaterialRefundDetail> implements MaterialRefundDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialRefundDetailMapper materialRefundDetailMapper;

    /**
    * 单表简单查询操作
    * @param materialRefundDetailDto
    * @return
    */
    @Override
    public Page list(MaterialRefundDetailDto materialRefundDetailDto) {
        LambdaQueryWrapper<MaterialRefundDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialRefundDetail> page = materialRefundDetailMapper.selectPage(materialRefundDetailDto.getPage(),queryWrapper);
        Page<MaterialRefundDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialRefundDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialRefundDetailDto
    * @return
    */
    @Override
    public Page lists(MaterialRefundDetailDto materialRefundDetailDto) {
        Page<MaterialRefundDetailVo> pageVo = materialRefundDetailMapper.getPageList(materialRefundDetailDto.getPage(),materialRefundDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialRefundDetailVo get(Long id) {
        MaterialRefundDetail materialRefundDetail = super.getById(id);
        MaterialRefundDetailVo materialRefundDetailVo = null;
        if(materialRefundDetail !=null){
            materialRefundDetailVo = dozerMapper.map(materialRefundDetail,MaterialRefundDetailVo.class);
        }
        return materialRefundDetailVo;
    }

    /**
    * 保存实体
    * @param materialRefundDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialRefundDetailDto materialRefundDetailDto) {
        MaterialRefundDetail materialRefundDetail = dozerMapper.map(materialRefundDetailDto,MaterialRefundDetail.class);
        super.save(materialRefundDetail);
        saveSubList(materialRefundDetailDto);
    }

    /**
     * 保存集合
     * @param materialRefundDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialRefundDetailDto> materialRefundDetailDtos){
        List<MaterialRefundDetail> MaterialRefundDetails = DozerUtils.mapList(dozerMapper, materialRefundDetailDtos, MaterialRefundDetail.class);
        super.saveBatch(MaterialRefundDetails);
        for (MaterialRefundDetailDto materialRefundDetailDto : materialRefundDetailDtos){
            saveSubList(materialRefundDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialRefundDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialRefundDetailDto materialRefundDetailDto){
    }

    /**
     * 修改实体
     * @param materialRefundDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialRefundDetailDto materialRefundDetailDto) {
        materialRefundDetailDto.setUpdateTime(LocalDateTime.now());
        MaterialRefundDetail materialRefundDetail = dozerMapper.map(materialRefundDetailDto,MaterialRefundDetail.class);
        super.updateById(materialRefundDetail);
        updateSub(materialRefundDetailDto);
    }

    /**
    * 修改子类实体
    * @param materialRefundDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialRefundDetailDto materialRefundDetailDto) {
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
        LambdaQueryWrapper<MaterialRefundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialRefundDetail::getRefundId,id);
        List<MaterialRefundDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialRefundDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<MaterialRefundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialRefundDetail::getRefundId,id);
        List<MaterialRefundDetail> list = this.list(queryWrapper);
        List<MaterialRefundDetailVo> materialRefundDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialRefundDetailVo.class);
        return materialRefundDetailVos;
    }
}
