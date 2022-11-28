package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.DisposePlanDetailDto;
import com.netwisd.biz.asset.entity.DisposePlanDetail;
import com.netwisd.biz.asset.mapper.DisposePlanDetailMapper;
import com.netwisd.biz.asset.service.DisposePlanDetailService;
import com.netwisd.biz.asset.vo.DisposePlanDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 处置计划明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
@Service
@Slf4j
public class DisposePlanDetailServiceImpl extends BatchServiceImpl<DisposePlanDetailMapper, DisposePlanDetail> implements DisposePlanDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private DisposePlanDetailMapper disposePlanDetailMapper;

    /**
    * 单表简单查询操作
    * @param disposePlanDetailDto
    * @return
    */
    @Override
    public Page list(DisposePlanDetailDto disposePlanDetailDto) {
        LambdaQueryWrapper<DisposePlanDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<DisposePlanDetail> page = disposePlanDetailMapper.selectPage(disposePlanDetailDto.getPage(),queryWrapper);
        Page<DisposePlanDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, DisposePlanDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param disposePlanDetailDto
    * @return
    */
    @Override
    public Page lists(DisposePlanDetailDto disposePlanDetailDto) {
        Page<DisposePlanDetailVo> pageVo = disposePlanDetailMapper.getPageList(disposePlanDetailDto.getPage(),disposePlanDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public DisposePlanDetailVo get(Long id) {
        DisposePlanDetail disposePlanDetail = super.getById(id);
        DisposePlanDetailVo disposePlanDetailVo = null;
        if(disposePlanDetail !=null){
            disposePlanDetailVo = dozerMapper.map(disposePlanDetail,DisposePlanDetailVo.class);
        }
        return disposePlanDetailVo;
    }

    /**
    * 保存实体
    * @param disposePlanDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(DisposePlanDetailDto disposePlanDetailDto) {
        DisposePlanDetail disposePlanDetail = dozerMapper.map(disposePlanDetailDto,DisposePlanDetail.class);
        super.save(disposePlanDetail);
        saveSubList(disposePlanDetailDto);
    }

    /**
     * 保存集合
     * @param disposePlanDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<DisposePlanDetailDto> disposePlanDetailDtos){
        List<DisposePlanDetail> DisposePlanDetails = DozerUtils.mapList(dozerMapper, disposePlanDetailDtos, DisposePlanDetail.class);
        super.saveBatch(DisposePlanDetails);
        for (DisposePlanDetailDto disposePlanDetailDto : disposePlanDetailDtos){
            saveSubList(disposePlanDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param disposePlanDetailDto
     * @return
     */
    @Transactional
    void saveSubList(DisposePlanDetailDto disposePlanDetailDto){
    }

    /**
     * 修改实体
     * @param disposePlanDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(DisposePlanDetailDto disposePlanDetailDto) {
        disposePlanDetailDto.setUpdateTime(LocalDateTime.now());
        DisposePlanDetail disposePlanDetail = dozerMapper.map(disposePlanDetailDto,DisposePlanDetail.class);
        super.updateById(disposePlanDetail);
        updateSub(disposePlanDetailDto);
    }

    /**
    * 修改子类实体
    * @param disposePlanDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(DisposePlanDetailDto disposePlanDetailDto) {
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
        LambdaQueryWrapper<DisposePlanDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),DisposePlanDetail::getDisposePlanId,id);
        List<DisposePlanDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<DisposePlanDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<DisposePlanDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),DisposePlanDetail::getDisposePlanId,id);
        List<DisposePlanDetail> list = this.list(queryWrapper);
        List<DisposePlanDetailVo> disposePlanDetailVos = DozerUtils.mapList(dozerMapper, list, DisposePlanDetailVo.class);
        return disposePlanDetailVos;
    }
}
