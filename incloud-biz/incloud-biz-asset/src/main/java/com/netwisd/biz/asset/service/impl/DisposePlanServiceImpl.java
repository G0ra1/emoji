package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.DisposePlanDetailDto;
import com.netwisd.biz.asset.dto.DisposePlanDto;
import com.netwisd.biz.asset.entity.DisposePlan;
import com.netwisd.biz.asset.entity.DisposePlanDetail;
import com.netwisd.biz.asset.mapper.DisposePlanMapper;
import com.netwisd.biz.asset.service.DisposePlanDetailService;
import com.netwisd.biz.asset.service.DisposePlanService;
import com.netwisd.biz.asset.vo.DisposePlanDetailVo;
import com.netwisd.biz.asset.vo.DisposePlanVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.common.db.util.EntityListConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 处置计划 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-07 17:50:50
 */
@Service
@Slf4j
public class DisposePlanServiceImpl extends BatchServiceImpl<DisposePlanMapper, DisposePlan> implements DisposePlanService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private DisposePlanMapper disposePlanMapper;

    @Autowired
    private DisposePlanDetailService disposePlanDetailService;

    /**
    * 单表简单查询操作
    * @param disposePlanDto
    * @return
    */
    @Override
    public Page list(DisposePlanDto disposePlanDto) {
        LambdaQueryWrapper<DisposePlan> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<DisposePlan> page = disposePlanMapper.selectPage(disposePlanDto.getPage(),queryWrapper);
        Page<DisposePlanVo> pageVo = DozerUtils.mapPage(dozerMapper, page, DisposePlanVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param disposePlanDto
    * @return
    */
    @Override
    public Page lists(DisposePlanDto disposePlanDto) {
        Page<DisposePlanVo> pageVo = disposePlanMapper.getPageList(disposePlanDto.getPage(),disposePlanDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public DisposePlanVo get(Long id) {
        DisposePlan disposePlan = super.getById(id);
        DisposePlanVo disposePlanVo = null;
        if(disposePlan !=null){
            disposePlanVo = dozerMapper.map(disposePlan,DisposePlanVo.class);
            //根据id获取disposePlanDetailVoList
            List<DisposePlanDetailVo> disposePlanDetailVoList = disposePlanDetailService.getByFkIdVo(id);
            //设置disposePlanVo，以便构建其对应的子表数据
            disposePlanVo.setDisposePlanDetailList(disposePlanDetailVoList);
        }
        return disposePlanVo;
    }

    /**
    * 保存实体
    * @param disposePlanDto
    * @return
    */
    @Transactional
    @Override
    public void save(DisposePlanDto disposePlanDto) {
        DisposePlan disposePlan = dozerMapper.map(disposePlanDto,DisposePlan.class);
        super.save(disposePlan);
        saveSubList(disposePlanDto);
    }

    /**
     * 保存集合
     * @param disposePlanDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<DisposePlanDto> disposePlanDtos){
        List<DisposePlan> DisposePlans = DozerUtils.mapList(dozerMapper, disposePlanDtos, DisposePlan.class);
        super.saveBatch(DisposePlans);
        for (DisposePlanDto disposePlanDto : disposePlanDtos){
            saveSubList(disposePlanDto);
        }
    }

    /**
     * 保存子表集合
     * @param disposePlanDto
     * @return
     */
    @Transactional
    void saveSubList(DisposePlanDto disposePlanDto){
        //获取其子表集合
        List<DisposePlanDetailDto> disposePlanDetailDtoList = disposePlanDto.getDisposePlanDetailList();
        if(disposePlanDetailDtoList != null && !disposePlanDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            EntityListConvert.convert(disposePlanDto,disposePlanDetailDtoList);
            //调用保存子表的集合方法
            disposePlanDetailService.saveList(disposePlanDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param disposePlanDto
     * @return
     */
    @Transactional
    @Override
    public void update(DisposePlanDto disposePlanDto) {
        disposePlanDto.setUpdateTime(LocalDateTime.now());
        DisposePlan disposePlan = dozerMapper.map(disposePlanDto,DisposePlan.class);
        super.updateById(disposePlan);
        updateSub(disposePlanDto);
    }

    /**
    * 修改子类实体
    * @param disposePlanDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(DisposePlanDto disposePlanDto) {
        List<DisposePlanDetailDto> disposePlanDetailDtoList = disposePlanDto.getDisposePlanDetailList();
        if(disposePlanDetailDtoList != null && !disposePlanDetailDtoList.isEmpty()){
            LambdaQueryWrapper<DisposePlanDetail> disposePlanDetailListQueryWrapper = new LambdaQueryWrapper<>();
            disposePlanDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(disposePlanDto.getId()),DisposePlanDetail::getDisposePlanId,disposePlanDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            EntityListConvert.convert(disposePlanDto,disposePlanDetailDtoList);
            List<DisposePlanDetail> disposePlanDetails = DozerUtils.mapList(dozerMapper, disposePlanDetailDtoList, DisposePlanDetail.class);
            //调用更新方法
            disposePlanDetailService.saveOrUpdateOrDeleteBatch(disposePlanDetailListQueryWrapper,disposePlanDetails,disposePlanDetails.size());
            //如果子可能还有子，那么遍历子，然后调用其子方法的updateSub
            if(disposePlanDetailDtoList != null && !disposePlanDetailDtoList.isEmpty()){
                for(DisposePlanDetailDto disposePlanDetailDto : disposePlanDetailDtoList){
                    disposePlanDetailService.updateSub(disposePlanDetailDto);
                }
            }
        }
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
        disposePlanDetailService.deleteByFkId(id);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public void deleteByFkId(Long id){
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<DisposePlanVo> getByFkIdVo(Long id){
        return null;
    }
}
