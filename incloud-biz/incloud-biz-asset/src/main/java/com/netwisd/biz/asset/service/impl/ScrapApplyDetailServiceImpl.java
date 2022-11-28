package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.biz.asset.dto.ScrapApplyDetailDto;
import com.netwisd.biz.asset.entity.ScrapApplyDetail;
import com.netwisd.biz.asset.mapper.ScrapApplyDetailMapper;
import com.netwisd.biz.asset.service.ScrapApplyDetailService;
import com.netwisd.biz.asset.vo.ScrapApplyDetailVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 报废申请详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 14:54:19
 */
@Service
@Slf4j
public class ScrapApplyDetailServiceImpl extends BatchServiceImpl<ScrapApplyDetailMapper, ScrapApplyDetail> implements ScrapApplyDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ScrapApplyDetailMapper scrapApplyDetailMapper;

    /**
    * 单表简单查询操作
    * @param scrapApplyDetailDto
    * @return
    */
    @Override
    public Page list(ScrapApplyDetailDto scrapApplyDetailDto) {
        LambdaQueryWrapper<ScrapApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<ScrapApplyDetail> page = scrapApplyDetailMapper.selectPage(scrapApplyDetailDto.getPage(),queryWrapper);
        Page<ScrapApplyDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ScrapApplyDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param scrapApplyDetailDto
    * @return
    */
    @Override
    public Page lists(ScrapApplyDetailDto scrapApplyDetailDto) {
        Page<ScrapApplyDetailVo> pageVo = scrapApplyDetailMapper.getPageList(scrapApplyDetailDto.getPage(),scrapApplyDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ScrapApplyDetailVo get(Long id) {
        ScrapApplyDetail scrapApplyDetail = super.getById(id);
        ScrapApplyDetailVo scrapApplyDetailVo = null;
        if(scrapApplyDetail !=null){
            scrapApplyDetailVo = dozerMapper.map(scrapApplyDetail,ScrapApplyDetailVo.class);
        }
        return scrapApplyDetailVo;
    }

    /**
    * 保存实体
    * @param scrapApplyDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(ScrapApplyDetailDto scrapApplyDetailDto) {
        ScrapApplyDetail scrapApplyDetail = dozerMapper.map(scrapApplyDetailDto,ScrapApplyDetail.class);
        super.save(scrapApplyDetail);
        saveSubList(scrapApplyDetailDto);
    }

    /**
     * 保存集合
     * @param scrapApplyDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<ScrapApplyDetailDto> scrapApplyDetailDtos){
        List<ScrapApplyDetail> ScrapApplyDetails = DozerUtils.mapList(dozerMapper, scrapApplyDetailDtos, ScrapApplyDetail.class);
        super.saveBatch(ScrapApplyDetails);
        for (ScrapApplyDetailDto scrapApplyDetailDto : scrapApplyDetailDtos){
            saveSubList(scrapApplyDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param scrapApplyDetailDto
     * @return
     */
    @Transactional
    void saveSubList(ScrapApplyDetailDto scrapApplyDetailDto){
    }

    /**
     * 修改实体
     * @param scrapApplyDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(ScrapApplyDetailDto scrapApplyDetailDto) {
        scrapApplyDetailDto.setUpdateTime(LocalDateTime.now());
        ScrapApplyDetail scrapApplyDetail = dozerMapper.map(scrapApplyDetailDto,ScrapApplyDetail.class);
        super.updateById(scrapApplyDetail);
        updateSub(scrapApplyDetailDto);
    }

    /**
    * 修改子类实体
    * @param scrapApplyDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(ScrapApplyDetailDto scrapApplyDetailDto) {
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
        LambdaQueryWrapper<ScrapApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),ScrapApplyDetail::getApplyId,id);
        List<ScrapApplyDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<ScrapApplyDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<ScrapApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),ScrapApplyDetail::getApplyId,id);
        List<ScrapApplyDetail> list = this.list(queryWrapper);
        List<ScrapApplyDetailVo> scrapApplyDetailVos = DozerUtils.mapList(dozerMapper, list, ScrapApplyDetailVo.class);
        return scrapApplyDetailVos;
    }

    @Override
    public List<ScrapApplyDetailVo> getByApplyIds(String applyIds) {
        String[] ids = applyIds.split(",");
        LambdaQueryWrapper<ScrapApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(applyIds),ScrapApplyDetail::getApplyId,ids);
        queryWrapper.gt(ScrapApplyDetail::getNotScrapRegisterNumber, YesNo.NO.getCode());
        queryWrapper.orderByDesc(ScrapApplyDetail::getCreateTime);
        //ScrapApplyDetailDto scrapApplyDetailDto = new ScrapApplyDetailDto();
        //Page<ScrapApplyDetail> page = scrapApplyDetailMapper.selectPage(scrapApplyDetailDto.getPage(), queryWrapper);
        List<ScrapApplyDetail> list = this.list(queryWrapper);
        //Page<ScrapApplyDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ScrapApplyDetailVo.class);
        List<ScrapApplyDetailVo> scrapApplyDetailVos = DozerUtils.mapList(dozerMapper, list, ScrapApplyDetailVo.class);
        return scrapApplyDetailVos;
    }
}
