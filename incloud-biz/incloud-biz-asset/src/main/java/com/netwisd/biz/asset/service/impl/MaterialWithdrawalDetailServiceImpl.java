package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDetailDto;
import com.netwisd.biz.asset.entity.MaterialWithdrawalDetail;
import com.netwisd.biz.asset.mapper.MaterialWithdrawalDetailMapper;
import com.netwisd.biz.asset.service.MaterialWithdrawalDetailService;
import com.netwisd.biz.asset.vo.MaterialWithdrawalDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产退库详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
@Service
@Slf4j
public class MaterialWithdrawalDetailServiceImpl extends BatchServiceImpl<MaterialWithdrawalDetailMapper, MaterialWithdrawalDetail> implements MaterialWithdrawalDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialWithdrawalDetailMapper materialWithdrawalDetailMapper;

    /**
    * 单表简单查询操作
    * @param materialWithdrawalDetailDto
    * @return
    */
    @Override
    public Page list(MaterialWithdrawalDetailDto materialWithdrawalDetailDto) {
        LambdaQueryWrapper<MaterialWithdrawalDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaterialWithdrawalDetail> page = materialWithdrawalDetailMapper.selectPage(materialWithdrawalDetailDto.getPage(),queryWrapper);
        Page<MaterialWithdrawalDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialWithdrawalDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialWithdrawalDetailDto
    * @return
    */
    @Override
    public Page lists(MaterialWithdrawalDetailDto materialWithdrawalDetailDto) {
        Page<MaterialWithdrawalDetailVo> pageVo = materialWithdrawalDetailMapper.getPageList(materialWithdrawalDetailDto.getPage(),materialWithdrawalDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialWithdrawalDetailVo get(Long id) {
        MaterialWithdrawalDetail materialWithdrawalDetail = super.getById(id);
        MaterialWithdrawalDetailVo materialWithdrawalDetailVo = null;
        if(materialWithdrawalDetail !=null){
            materialWithdrawalDetailVo = dozerMapper.map(materialWithdrawalDetail,MaterialWithdrawalDetailVo.class);
        }
        return materialWithdrawalDetailVo;
    }

    /**
    * 保存实体
    * @param materialWithdrawalDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaterialWithdrawalDetailDto materialWithdrawalDetailDto) {
        MaterialWithdrawalDetail materialWithdrawalDetail = dozerMapper.map(materialWithdrawalDetailDto,MaterialWithdrawalDetail.class);
        super.save(materialWithdrawalDetail);
        saveSubList(materialWithdrawalDetailDto);
    }

    /**
     * 保存集合
     * @param materialWithdrawalDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialWithdrawalDetailDto> materialWithdrawalDetailDtos){
        List<MaterialWithdrawalDetail> MaterialWithdrawalDetails = DozerUtils.mapList(dozerMapper, materialWithdrawalDetailDtos, MaterialWithdrawalDetail.class);
        super.saveBatch(MaterialWithdrawalDetails);
        for (MaterialWithdrawalDetailDto materialWithdrawalDetailDto : materialWithdrawalDetailDtos){
            saveSubList(materialWithdrawalDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param materialWithdrawalDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialWithdrawalDetailDto materialWithdrawalDetailDto){
    }

    /**
     * 修改实体
     * @param materialWithdrawalDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialWithdrawalDetailDto materialWithdrawalDetailDto) {
        materialWithdrawalDetailDto.setUpdateTime(LocalDateTime.now());
        MaterialWithdrawalDetail materialWithdrawalDetail = dozerMapper.map(materialWithdrawalDetailDto,MaterialWithdrawalDetail.class);
        super.updateById(materialWithdrawalDetail);
        updateSub(materialWithdrawalDetailDto);
    }

    /**
    * 修改子类实体
    * @param materialWithdrawalDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaterialWithdrawalDetailDto materialWithdrawalDetailDto) {
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
        LambdaQueryWrapper<MaterialWithdrawalDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialWithdrawalDetail::getWithdrawalId,id);
        List<MaterialWithdrawalDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaterialWithdrawalDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<MaterialWithdrawalDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaterialWithdrawalDetail::getWithdrawalId,id);
        List<MaterialWithdrawalDetail> list = this.list(queryWrapper);
        List<MaterialWithdrawalDetailVo> materialWithdrawalDetailVos = DozerUtils.mapList(dozerMapper, list, MaterialWithdrawalDetailVo.class);
        return materialWithdrawalDetailVos;
    }
}
