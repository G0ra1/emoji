package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.ViewerDetailDto;
import com.netwisd.biz.asset.entity.ViewerDetail;
import com.netwisd.biz.asset.mapper.ViewerDetailMapper;
import com.netwisd.biz.asset.service.ViewerDetailService;
import com.netwisd.biz.asset.vo.ViewerDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 物资数据权限范围表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@Service
@Slf4j
public class ViewerDetailServiceImpl extends BatchServiceImpl<ViewerDetailMapper, ViewerDetail> implements ViewerDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ViewerDetailMapper viewerDetailMapper;

    /**
    * 单表简单查询操作
    * @param viewerDetailDto
    * @return
    */
    @Override
    public Page list(ViewerDetailDto viewerDetailDto) {
        LambdaQueryWrapper<ViewerDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<ViewerDetail> page = viewerDetailMapper.selectPage(viewerDetailDto.getPage(),queryWrapper);
        Page<ViewerDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ViewerDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param viewerDetailDto
    * @return
    */
    @Override
    public Page lists(ViewerDetailDto viewerDetailDto) {
        Page<ViewerDetailVo> pageVo = viewerDetailMapper.getPageList(viewerDetailDto.getPage(),viewerDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ViewerDetailVo get(Long id) {
        ViewerDetail viewerDetail = super.getById(id);
        ViewerDetailVo viewerDetailVo = null;
        if(viewerDetail !=null){
            viewerDetailVo = dozerMapper.map(viewerDetail,ViewerDetailVo.class);
        }
        return viewerDetailVo;
    }

    /**
    * 保存实体
    * @param viewerDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ViewerDetailDto viewerDetailDto) {
        ViewerDetail viewerDetail = dozerMapper.map(viewerDetailDto,ViewerDetail.class);
        return super.save(viewerDetail);
    }

    /**
     * 保存集合
     * @param viewerDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<ViewerDetailDto> viewerDetailDtos){
        List<ViewerDetail> ViewerDetails = DozerUtils.mapList(dozerMapper, viewerDetailDtos, ViewerDetail.class);
        return super.saveBatch(ViewerDetails);
    }


    /**
     * 修改实体
     * @param viewerDetailDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(ViewerDetailDto viewerDetailDto) {
        viewerDetailDto.setUpdateTime(LocalDateTime.now());
        ViewerDetail viewerDetail = dozerMapper.map(viewerDetailDto,ViewerDetail.class);
        return super.updateById(viewerDetail);
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
     * @param viewerId
     * @return
     */
    @Override
    public Boolean deleteByViewerId(Long viewerId){
        LambdaQueryWrapper<ViewerDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(viewerId),ViewerDetail::getViewerId,viewerId);
        List<ViewerDetail> list = this.list(queryWrapper);
        return remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param viewerId
     * @return
     */
    @Override
    public List<ViewerDetailVo> getByViewerId(Long viewerId){
        LambdaQueryWrapper<ViewerDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(viewerId),ViewerDetail::getViewerId,viewerId);
        List<ViewerDetail> list = this.list(queryWrapper);
        List<ViewerDetailVo> viewerDetailVos = DozerUtils.mapList(dozerMapper, list, ViewerDetailVo.class);
        return viewerDetailVos;
    }
}
