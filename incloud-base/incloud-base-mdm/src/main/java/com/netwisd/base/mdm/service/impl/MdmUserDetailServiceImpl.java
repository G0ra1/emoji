package com.netwisd.base.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.dto.MdmUserDetailDto;
import com.netwisd.base.common.mdm.vo.MdmUserDetailVo;
import com.netwisd.base.mdm.entity.MdmUserDetail;
import com.netwisd.base.mdm.mapper.MdmUserDetailMapper;
import com.netwisd.base.mdm.service.MdmUserDetailService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 用户详情 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 09:09:37
 */
@Service
@Slf4j
public class MdmUserDetailServiceImpl extends ServiceImpl<MdmUserDetailMapper, MdmUserDetail> implements MdmUserDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmUserDetailMapper mdmUserDetailMapper;

    /**
    * 单表简单查询操作
    * @param mdmUserDetailDto
    * @return
    */
    @Override
    public Page list(MdmUserDetailDto mdmUserDetailDto) {
        LambdaQueryWrapper<MdmUserDetail> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmUserDetail> page = mdmUserDetailMapper.selectPage(mdmUserDetailDto.getPage(),queryWrapper);
        Page<MdmUserDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmUserDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmUserDetailDto
    * @return
    */
    @Override
    public Page lists(MdmUserDetailDto mdmUserDetailDto) {
        Page<MdmUserDetailVo> pageVo = mdmUserDetailMapper.getPageList(mdmUserDetailDto.getPage(),mdmUserDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmUserDetailVo get(Long id) {
        MdmUserDetail mdmUserDetail = super.getById(id);
        MdmUserDetailVo mdmUserDetailVo = null;
        if(mdmUserDetail !=null){
            mdmUserDetailVo = dozerMapper.map(mdmUserDetail,MdmUserDetailVo.class);
        }
        log.debug("查询成功");
        return mdmUserDetailVo;
    }

    /**
    * 保存实体
    * @param mdmUserDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmUserDetailDto mdmUserDetailDto) {
        MdmUserDetail mdmUserDetail = dozerMapper.map(mdmUserDetailDto,MdmUserDetail.class);
        boolean result = super.save(mdmUserDetail);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmUserDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmUserDetailDto mdmUserDetailDto) {
        mdmUserDetailDto.setUpdateTime(LocalDateTime.now());
        MdmUserDetail mdmUserDetail = dozerMapper.map(mdmUserDetailDto,MdmUserDetail.class);
        boolean result = super.updateById(mdmUserDetail);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
