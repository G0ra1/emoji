package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaintainRegDetailDto;
import com.netwisd.biz.asset.entity.MaintainRegDetail;
import com.netwisd.biz.asset.mapper.MaintainRegDetailMapper;
import com.netwisd.biz.asset.service.MaintainRegDetailService;
import com.netwisd.biz.asset.vo.MaintainRegDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 维修登记详情表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-28 17:12:23
 */
@Service
@Slf4j
public class MaintainRegDetailServiceImpl extends BatchServiceImpl<MaintainRegDetailMapper, MaintainRegDetail> implements MaintainRegDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaintainRegDetailMapper maintainRegDetailMapper;

    /**
    * 单表简单查询操作
    * @param maintainRegDetailDto
    * @return
    */
    @Override
    public Page list(MaintainRegDetailDto maintainRegDetailDto) {
        LambdaQueryWrapper<MaintainRegDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaintainRegDetail> page = maintainRegDetailMapper.selectPage(maintainRegDetailDto.getPage(),queryWrapper);
        Page<MaintainRegDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaintainRegDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param maintainRegDetailDto
    * @return
    */
    @Override
    public Page lists(MaintainRegDetailDto maintainRegDetailDto) {
        Page<MaintainRegDetailVo> pageVo = maintainRegDetailMapper.getPageList(maintainRegDetailDto.getPage(),maintainRegDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaintainRegDetailVo get(Long id) {
        MaintainRegDetail maintainRegDetail = super.getById(id);
        MaintainRegDetailVo maintainRegDetailVo = null;
        if(maintainRegDetail !=null){
            maintainRegDetailVo = dozerMapper.map(maintainRegDetail,MaintainRegDetailVo.class);
        }
        return maintainRegDetailVo;
    }

    /**
    * 保存实体
    * @param maintainRegDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaintainRegDetailDto maintainRegDetailDto) {
        MaintainRegDetail maintainRegDetail = dozerMapper.map(maintainRegDetailDto,MaintainRegDetail.class);
        super.save(maintainRegDetail);
        saveSubList(maintainRegDetailDto);
    }

    /**
     * 保存集合
     * @param maintainRegDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaintainRegDetailDto> maintainRegDetailDtos){
        List<MaintainRegDetail> MaintainRegDetails = DozerUtils.mapList(dozerMapper, maintainRegDetailDtos, MaintainRegDetail.class);
        super.saveBatch(MaintainRegDetails);
        for (MaintainRegDetailDto maintainRegDetailDto : maintainRegDetailDtos){
            saveSubList(maintainRegDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param maintainRegDetailDto
     * @return
     */
    @Transactional
    void saveSubList(MaintainRegDetailDto maintainRegDetailDto){
    }

    /**
     * 修改实体
     * @param maintainRegDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaintainRegDetailDto maintainRegDetailDto) {
        maintainRegDetailDto.setUpdateTime(LocalDateTime.now());
        MaintainRegDetail maintainRegDetail = dozerMapper.map(maintainRegDetailDto,MaintainRegDetail.class);
        super.updateById(maintainRegDetail);
        updateSub(maintainRegDetailDto);
    }

    /**
    * 修改子类实体
    * @param maintainRegDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaintainRegDetailDto maintainRegDetailDto) {
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
        LambdaQueryWrapper<MaintainRegDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaintainRegDetail::getMaintainRegId,id);
        List<MaintainRegDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaintainRegDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<MaintainRegDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),MaintainRegDetail::getMaintainRegId,id);
        List<MaintainRegDetail> list = this.list(queryWrapper);
        List<MaintainRegDetailVo> maintainRegDetailVos = DozerUtils.mapList(dozerMapper, list, MaintainRegDetailVo.class);
        return maintainRegDetailVos;
    }
}
