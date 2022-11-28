package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialDeployDetailDto;
import com.netwisd.biz.asset.entity.MaterialDeployDetail;
import com.netwisd.biz.asset.mapper.MaterialDeployDetailMapper;
import com.netwisd.biz.asset.service.MaterialDeployDetailService;
import com.netwisd.biz.asset.vo.MaterialDeployDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description 资产调配明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-09-06 10:35:24
 */
@Service
@Slf4j
public class MaterialDeployDetailServiceImpl extends BatchServiceImpl<MaterialDeployDetailMapper, MaterialDeployDetail> implements MaterialDeployDetailService {
    @Autowired
    private Mapper dozerMapper;

    //@Autowired
    //private MaterialDeployDetailMapper materialDeployDetailMapper;

    /**
     * 单表简单查询操作
     * @param materialDeployDetailDto
     * @return
     */
    @Override
    public Page<MaterialDeployDetailVo> page(MaterialDeployDetailDto materialDeployDetailDto) {
        LambdaQueryWrapper<MaterialDeployDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialDeployDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, super.page(materialDeployDetailDto.getPage(),queryWrapper), MaterialDeployDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param materialDeployDetailDto
     * @return
     */
    @Override
    public List<MaterialDeployDetailVo> list(MaterialDeployDetailDto materialDeployDetailDto) {
        LambdaQueryWrapper<MaterialDeployDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        List<MaterialDeployDetailVo> materialDeployDetailVos = DozerUtils.mapList(dozerMapper, super.list(queryWrapper), MaterialDeployDetailVo.class);
        log.debug("查询条数:"+materialDeployDetailVos.size());
        return materialDeployDetailVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDeployDetailVo get(Long id) {
        MaterialDeployDetail materialDeployDetail = Optional.ofNullable(super.getById(id)).orElse(null);
        MaterialDeployDetailVo materialDeployDetailVo = ObjectUtil.isNotEmpty(materialDeployDetail) ? dozerMapper.map(materialDeployDetail,MaterialDeployDetailVo.class) : null;
        return materialDeployDetailVo;
    }

    /**
    * 保存实体
    * @param materialDeployDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialDeployDetailDto materialDeployDetailDto) {
        return super.save(dozerMapper.map(materialDeployDetailDto,MaterialDeployDetail.class));
    }

    /**
     * 保存集合
     * @param materialDeployDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<MaterialDeployDetailDto> materialDeployDetailDtos){
        List<MaterialDeployDetail> MaterialDeployDetails = DozerUtils.mapList(dozerMapper, materialDeployDetailDtos, MaterialDeployDetail.class);
        return super.saveBatch(MaterialDeployDetails);
    }

    /**
     * 修改实体
     * @param materialDeployDetailDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MaterialDeployDetailDto materialDeployDetailDto) {
        materialDeployDetailDto.setUpdateTime(LocalDateTime.now());
        return super.updateById(dozerMapper.map(materialDeployDetailDto,MaterialDeployDetail.class));
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        return super.removeById(id);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public Boolean deleteByDeployId(Long id){
        LambdaQueryWrapper<MaterialDeployDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialDeployDetail::getDeployId,id);
        List<MaterialDeployDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialDeployDetailVo> getByDeployId(Long id){
        LambdaQueryWrapper<MaterialDeployDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialDeployDetail::getDeployId,id);
        List<MaterialDeployDetail> list = this.list(queryWrapper);
        List<MaterialDeployDetailVo> materialDeployDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialDeployDetailVo.class);
        return materialDeployDetailVos;
    }
}
