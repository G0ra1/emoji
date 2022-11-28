package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaintainPlanDetailDto;
import com.netwisd.biz.asset.entity.MaintainPlanDetail;
import com.netwisd.biz.asset.mapper.MaintainPlanDetailMapper;
import com.netwisd.biz.asset.service.MaintainPlanDetailService;
import com.netwisd.biz.asset.vo.MaintainPlanDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 维修计划明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-25 14:07:05
 */
@Service
@Slf4j
public class MaintainPlanDetailServiceImpl extends BatchServiceImpl<MaintainPlanDetailMapper, MaintainPlanDetail> implements MaintainPlanDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaintainPlanDetailMapper maintainPlanDetailMapper;

    /**
    * 单表简单查询操作
    * @param maintainPlanDetailDto
    * @return
    */
    @Override
    public Page list(MaintainPlanDetailDto maintainPlanDetailDto) {
        LambdaQueryWrapper<MaintainPlanDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaintainPlanDetail> page = maintainPlanDetailMapper.selectPage(maintainPlanDetailDto.getPage(),queryWrapper);
        Page<MaintainPlanDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaintainPlanDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param maintainPlanDetailDto
    * @return
    */
    @Override
    public Page lists(MaintainPlanDetailDto maintainPlanDetailDto) {
        Page<MaintainPlanDetailVo> pageVo = maintainPlanDetailMapper.getPageList(maintainPlanDetailDto.getPage(),maintainPlanDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaintainPlanDetailVo get(Long id) {
        MaintainPlanDetail maintainPlanDetail = super.getById(id);
        MaintainPlanDetailVo maintainPlanDetailVo = null;
        if(maintainPlanDetail !=null){
            maintainPlanDetailVo = dozerMapper.map(maintainPlanDetail,MaintainPlanDetailVo.class);
        }
        return maintainPlanDetailVo;
    }

    /**
    * 保存实体
    * @param maintainPlanDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaintainPlanDetailDto maintainPlanDetailDto) {
        MaintainPlanDetail maintainPlanDetail = dozerMapper.map(maintainPlanDetailDto,MaintainPlanDetail.class);
        super.save(maintainPlanDetail);
        saveSubList(maintainPlanDetailDto);
    }

    /**
     * 保存集合
     * @param maintainPlanDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaintainPlanDetailDto> maintainPlanDetailDtos){
        List<MaintainPlanDetail> MaintainPlanDetails = DozerUtils.mapList(dozerMapper, maintainPlanDetailDtos, MaintainPlanDetail.class);
        super.saveBatch(MaintainPlanDetails);
        for (MaintainPlanDetailDto maintainPlanDetailDto : maintainPlanDetailDtos){
            saveSubList(maintainPlanDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param maintainPlanDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaintainPlanDetailDto maintainPlanDetailDto){
    }

    /**
     * 修改实体
     * @param maintainPlanDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaintainPlanDetailDto maintainPlanDetailDto) {
        maintainPlanDetailDto.setUpdateTime(LocalDateTime.now());
        MaintainPlanDetail maintainPlanDetail = dozerMapper.map(maintainPlanDetailDto,MaintainPlanDetail.class);
        super.updateById(maintainPlanDetail);
        updateSub(maintainPlanDetailDto);
    }

    /**
    * 修改子类实体
    * @param maintainPlanDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaintainPlanDetailDto maintainPlanDetailDto) {
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
        LambdaQueryWrapper<MaintainPlanDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaintainPlanDetail::getMaintainPlanId,id);
        List<MaintainPlanDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaintainPlanDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<MaintainPlanDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaintainPlanDetail::getMaintainPlanId,id);
        List<MaintainPlanDetail> list = this.list(queryWrapper);
        List<MaintainPlanDetailVo> maintainPlanDetailVos = DozerUtils.mapList(dozerMapper, list, MaintainPlanDetailVo.class);
        return maintainPlanDetailVos;
    }
}
