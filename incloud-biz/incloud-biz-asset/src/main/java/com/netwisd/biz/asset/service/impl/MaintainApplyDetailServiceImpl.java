package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaintainApplyDetailDto;
import com.netwisd.biz.asset.entity.MaintainApplyDetail;
import com.netwisd.biz.asset.mapper.MaintainApplyDetailMapper;
import com.netwisd.biz.asset.service.MaintainApplyDetailService;
import com.netwisd.biz.asset.vo.MaintainApplyDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 维修申请详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-27 10:28:25
 */
@Service
@Slf4j
public class MaintainApplyDetailServiceImpl extends BatchServiceImpl<MaintainApplyDetailMapper, MaintainApplyDetail> implements MaintainApplyDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaintainApplyDetailMapper maintainApplyDetailMapper;

    /**
    * 单表简单查询操作
    * @param maintainApplyDetailDto
    * @return
    */
    @Override
    public Page list(MaintainApplyDetailDto maintainApplyDetailDto) {
        LambdaQueryWrapper<MaintainApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaintainApplyDetail> page = maintainApplyDetailMapper.selectPage(maintainApplyDetailDto.getPage(),queryWrapper);
        Page<MaintainApplyDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaintainApplyDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param maintainApplyDetailDto
    * @return
    */
    @Override
    public Page lists(MaintainApplyDetailDto maintainApplyDetailDto) {
        Page<MaintainApplyDetailVo> pageVo = maintainApplyDetailMapper.getPageList(maintainApplyDetailDto.getPage(),maintainApplyDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaintainApplyDetailVo get(Long id) {
        MaintainApplyDetail maintainApplyDetail = super.getById(id);
        MaintainApplyDetailVo maintainApplyDetailVo = null;
        if(maintainApplyDetail !=null){
            maintainApplyDetailVo = dozerMapper.map(maintainApplyDetail,MaintainApplyDetailVo.class);
        }
        return maintainApplyDetailVo;
    }

    /**
    * 保存实体
    * @param maintainApplyDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaintainApplyDetailDto maintainApplyDetailDto) {
        MaintainApplyDetail maintainApplyDetail = dozerMapper.map(maintainApplyDetailDto,MaintainApplyDetail.class);
        super.save(maintainApplyDetail);
        saveSubList(maintainApplyDetailDto);
    }

    /**
     * 保存集合
     * @param maintainApplyDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaintainApplyDetailDto> maintainApplyDetailDtos){
        List<MaintainApplyDetail> MaintainApplyDetails = DozerUtils.mapList(dozerMapper, maintainApplyDetailDtos, MaintainApplyDetail.class);
        super.saveBatch(MaintainApplyDetails);
        for (MaintainApplyDetailDto maintainApplyDetailDto : maintainApplyDetailDtos){
            saveSubList(maintainApplyDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param maintainApplyDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaintainApplyDetailDto maintainApplyDetailDto){
    }

    /**
     * 修改实体
     * @param maintainApplyDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaintainApplyDetailDto maintainApplyDetailDto) {
        maintainApplyDetailDto.setUpdateTime(LocalDateTime.now());
        MaintainApplyDetail maintainApplyDetail = dozerMapper.map(maintainApplyDetailDto,MaintainApplyDetail.class);
        super.updateById(maintainApplyDetail);
        updateSub(maintainApplyDetailDto);
    }

    /**
    * 修改子类实体
    * @param maintainApplyDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaintainApplyDetailDto maintainApplyDetailDto) {
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
        LambdaQueryWrapper<MaintainApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaintainApplyDetail::getMaintainApplyId,id);
        List<MaintainApplyDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaintainApplyDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<MaintainApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaintainApplyDetail::getMaintainApplyId,id);
        List<MaintainApplyDetail> list = this.list(queryWrapper);
        List<MaintainApplyDetailVo> maintainApplyDetailVos = DozerUtils.mapList(dozerMapper, list, MaintainApplyDetailVo.class);
        return maintainApplyDetailVos;
    }
}
