package com.netwisd.base.dict.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.dict.dto.EncondRuleDetailDto;
import com.netwisd.base.common.dict.vo.EncondRuleDetailVo;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.base.dict.entity.EncondRuleDetail;
import com.netwisd.base.dict.mapper.EncondRuleDetailMapper;
import com.netwisd.base.dict.service.EncondRuleDetailService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 编码规则详情 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Service
@Slf4j
public class EncondRuleDetailServiceImpl extends BatchServiceImpl<EncondRuleDetailMapper, EncondRuleDetail> implements EncondRuleDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private EncondRuleDetailMapper encondRuleDetailMapper;

    /**
    * 单表简单查询操作
    * @param encondRuleDetailDto
    * @return
    */
    @Override
    public Page list(EncondRuleDetailDto encondRuleDetailDto) {
        LambdaQueryWrapper<EncondRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<EncondRuleDetail> page = encondRuleDetailMapper.selectPage(encondRuleDetailDto.getPage(),queryWrapper);
        Page<EncondRuleDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, EncondRuleDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param encondRuleDetailDto
    * @return
    */
    @Override
    public Page lists(EncondRuleDetailDto encondRuleDetailDto) {
        Page<EncondRuleDetailVo> pageVo = encondRuleDetailMapper.getPageList(encondRuleDetailDto.getPage(),encondRuleDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public EncondRuleDetailVo get(Long id) {
        EncondRuleDetail encondRuleDetail = super.getById(id);
        EncondRuleDetailVo encondRuleDetailVo = null;
        if(encondRuleDetail !=null){
            encondRuleDetailVo = dozerMapper.map(encondRuleDetail,EncondRuleDetailVo.class);
        }
        return encondRuleDetailVo;
    }

    /**
    * 保存实体
    * @param encondRuleDetailDto
    * @return
    */
    @Transactional
    @Override
    public void save(EncondRuleDetailDto encondRuleDetailDto) {
        EncondRuleDetail encondRuleDetail = dozerMapper.map(encondRuleDetailDto,EncondRuleDetail.class);
        super.save(encondRuleDetail);
        saveSubList(encondRuleDetailDto);
    }

    /**
     * 保存集合
     * @param encondRuleDetailDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<EncondRuleDetailDto> encondRuleDetailDtos){
        List<EncondRuleDetail> EncondRuleDetails = DozerUtils.mapList(dozerMapper, encondRuleDetailDtos, EncondRuleDetail.class);
        super.saveBatch(EncondRuleDetails);
        for (EncondRuleDetailDto encondRuleDetailDto : encondRuleDetailDtos){
            saveSubList(encondRuleDetailDto);
        }
    }

    /**
     * 保存子表集合
     * @param encondRuleDetailDto
     * @return
     */
    @Transactional
    void saveSubList(EncondRuleDetailDto encondRuleDetailDto){
    }

    /**
     * 修改实体
     * @param encondRuleDetailDto
     * @return
     */
    @Transactional
    @Override
    public void update(EncondRuleDetailDto encondRuleDetailDto) {
        encondRuleDetailDto.setUpdateTime(LocalDateTime.now());
        EncondRuleDetail encondRuleDetail = dozerMapper.map(encondRuleDetailDto,EncondRuleDetail.class);
        super.updateById(encondRuleDetail);
        updateSub(encondRuleDetailDto);
    }

    /**
    * 修改子类实体
    * @param encondRuleDetailDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(EncondRuleDetailDto encondRuleDetailDto) {
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
        LambdaQueryWrapper<EncondRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),EncondRuleDetail::getRuleId,id);
        List<EncondRuleDetail> list = this.list(queryWrapper);
        remove(queryWrapper);
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<EncondRuleDetailVo> getByFkIdVo(Long id){
        LambdaQueryWrapper<EncondRuleDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id),EncondRuleDetail::getRuleId,id);
        queryWrapper.orderByAsc(EncondRuleDetail::getSort);
        List<EncondRuleDetail> list = this.list(queryWrapper);
        List<EncondRuleDetailVo> encondRuleDetailVos = DozerUtils.mapList(dozerMapper, list, EncondRuleDetailVo.class);
        return encondRuleDetailVos;
    }
}
