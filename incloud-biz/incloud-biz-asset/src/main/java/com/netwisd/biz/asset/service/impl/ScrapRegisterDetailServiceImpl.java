package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.ScrapRegisterDetailDto;
import com.netwisd.biz.asset.entity.ScrapRegisterDetail;
import com.netwisd.biz.asset.mapper.ScrapRegisterDetailMapper;
import com.netwisd.biz.asset.service.ScrapRegisterDetailService;
import com.netwisd.biz.asset.vo.ScrapRegisterDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 报废登记详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-05 10:14:01
 */
@Service
@Slf4j
public class ScrapRegisterDetailServiceImpl extends BatchServiceImpl<ScrapRegisterDetailMapper, ScrapRegisterDetail> implements ScrapRegisterDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ScrapRegisterDetailMapper scrapRegisterDetailMapper;

    /**
    * 单表简单查询操作
    * @param scrapRegisterDetailDto
    * @return
    */
    @Override
    public Page list(ScrapRegisterDetailDto scrapRegisterDetailDto) {
        LambdaQueryWrapper<ScrapRegisterDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<ScrapRegisterDetail> page = scrapRegisterDetailMapper.selectPage(scrapRegisterDetailDto.getPage(),queryWrapper);
        Page<ScrapRegisterDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ScrapRegisterDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param scrapRegisterDetailDto
    * @return
    */
    @Override
    public Page lists(ScrapRegisterDetailDto scrapRegisterDetailDto) {
        Page<ScrapRegisterDetailVo> pageVo = scrapRegisterDetailMapper.getPageList(scrapRegisterDetailDto.getPage(),scrapRegisterDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ScrapRegisterDetailVo get(Long id) {
        ScrapRegisterDetail scrapRegisterDetail = super.getById(id);
        ScrapRegisterDetailVo scrapRegisterDetailVo = null;
        if(scrapRegisterDetail !=null){
            scrapRegisterDetailVo = dozerMapper.map(scrapRegisterDetail,ScrapRegisterDetailVo.class);
        }
        return scrapRegisterDetailVo;
    }

    /**
    * 保存实体
    * @param scrapRegisterDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(ScrapRegisterDetailDto scrapRegisterDetailDto) {
        ScrapRegisterDetail scrapRegisterDetail = dozerMapper.map(scrapRegisterDetailDto,ScrapRegisterDetail.class);
        super.save(scrapRegisterDetail);
        saveSubList(scrapRegisterDetailDto);
    }

    /**
     * 保存集合
     * @param scrapRegisterDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<ScrapRegisterDetailDto> scrapRegisterDetailDtos){
        List<ScrapRegisterDetail> ScrapRegisterDetails = DozerUtils.mapList(dozerMapper, scrapRegisterDetailDtos, ScrapRegisterDetail.class);
        super.saveBatch(ScrapRegisterDetails);
        for (ScrapRegisterDetailDto scrapRegisterDetailDto : scrapRegisterDetailDtos){
            saveSubList(scrapRegisterDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param scrapRegisterDetailDto
     * @return
     */
    @Transactional
    void saveSubList(ScrapRegisterDetailDto scrapRegisterDetailDto){
    }

    /**
     * 修改实体
     * @param scrapRegisterDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(ScrapRegisterDetailDto scrapRegisterDetailDto) {
        scrapRegisterDetailDto.setUpdateTime(LocalDateTime.now());
        ScrapRegisterDetail scrapRegisterDetail = dozerMapper.map(scrapRegisterDetailDto,ScrapRegisterDetail.class);
        super.updateById(scrapRegisterDetail);
        updateSub(scrapRegisterDetailDto);
    }

    /**
    * 修改子类实体
    * @param scrapRegisterDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(ScrapRegisterDetailDto scrapRegisterDetailDto) {
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
        LambdaQueryWrapper<ScrapRegisterDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),ScrapRegisterDetail::getRegisterId,id);
        List<ScrapRegisterDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<ScrapRegisterDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<ScrapRegisterDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),ScrapRegisterDetail::getRegisterId,id);
        List<ScrapRegisterDetail> list = this.list(queryWrapper);
        List<ScrapRegisterDetailVo> scrapRegisterDetailVos = DozerUtils.mapList(dozerMapper, list, ScrapRegisterDetailVo.class);
        return scrapRegisterDetailVos;
    }
}
