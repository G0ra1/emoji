package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialSignDetailDto;
import com.netwisd.biz.asset.entity.MaterialSignDetail;
import com.netwisd.biz.asset.mapper.MaterialSignDetailMapper;
import com.netwisd.biz.asset.service.MaterialSignDetailService;
import com.netwisd.biz.asset.vo.MaterialSignDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 签收详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
@Service
@Slf4j
public class MaterialSignDetailServiceImpl extends BatchServiceImpl<MaterialSignDetailMapper, MaterialSignDetail> implements MaterialSignDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialSignDetailMapper materialSignDetailMapper;

    /**
    * 单表简单查询操作
    * @param materialSignDetailDto
    * @return
    */
    @Override
    public Page list(MaterialSignDetailDto materialSignDetailDto) {
        LambdaQueryWrapper<MaterialSignDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialSignDetail> page = materialSignDetailMapper.selectPage(materialSignDetailDto.getPage(),queryWrapper);
        Page<MaterialSignDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialSignDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialSignDetailDto
    * @return
    */
    @Override
    public Page lists(MaterialSignDetailDto materialSignDetailDto) {
        Page<MaterialSignDetailVo> pageVo = materialSignDetailMapper.getPageList(materialSignDetailDto.getPage(),materialSignDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialSignDetailVo get(Long id) {
        MaterialSignDetail materialSignDetail = super.getById(id);
        MaterialSignDetailVo materialSignDetailVo = null;
        if(materialSignDetail !=null){
            materialSignDetailVo = dozerMapper.map(materialSignDetail,MaterialSignDetailVo.class);
        }
        return materialSignDetailVo;
    }

    /**
    * 保存实体
    * @param materialSignDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialSignDetailDto materialSignDetailDto) {
        MaterialSignDetail materialSignDetail = dozerMapper.map(materialSignDetailDto,MaterialSignDetail.class);
        super.save(materialSignDetail);
        saveSubList(materialSignDetailDto);
    }

    /**
     * 保存集合
     * @param materialSignDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<MaterialSignDetailDto> materialSignDetailDtos){
        Boolean result = true;
        List<MaterialSignDetail> MaterialSignDetails = DozerUtils.mapList(dozerMapper, materialSignDetailDtos, MaterialSignDetail.class);
        super.saveBatch(MaterialSignDetails);
        for (MaterialSignDetailDto materialSignDetailDto : materialSignDetailDtos){
            saveSubList(materialSignDetailDto);
        }
        return result;
    }

    /**
     * 保存子表集合
     * @param materialSignDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialSignDetailDto materialSignDetailDto){
    }

    /**
     * 修改实体
     * @param materialSignDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialSignDetailDto materialSignDetailDto) {
        materialSignDetailDto.setUpdateTime(LocalDateTime.now());
        MaterialSignDetail materialSignDetail = dozerMapper.map(materialSignDetailDto,MaterialSignDetail.class);
        super.updateById(materialSignDetail);
        updateSub(materialSignDetailDto);
    }

    /**
    * 修改子类实体
    * @param materialSignDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialSignDetailDto materialSignDetailDto) {
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
        LambdaQueryWrapper<MaterialSignDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialSignDetail::getSignId,id);
        List<MaterialSignDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialSignDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<MaterialSignDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialSignDetail::getSignId,id);
        List<MaterialSignDetail> list = this.list(queryWrapper);
        List<MaterialSignDetailVo> materialSignDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialSignDetailVo.class);
        return materialSignDetailVos;
    }
}
