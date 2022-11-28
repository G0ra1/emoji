package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPartya;
import com.netwisd.biz.mdm.mapper.ContractPartyaMapper;
import com.netwisd.biz.mdm.service.ContractPartyaService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPartyaDto;
import com.netwisd.biz.mdm.vo.ContractPartyaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 甲方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:34:54
 */
@Service
@Slf4j
public class ContractPartyaServiceImpl extends ServiceImpl<ContractPartyaMapper, ContractPartya> implements ContractPartyaService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPartyaMapper contractPartyaMapper;

    /**
    * 单表简单查询操作
    * @param contractPartyaDto
    * @return
    */
    @Override
    public Page list(ContractPartyaDto contractPartyaDto) {
        ContractPartya contractPartya = dozerMapper.map(contractPartyaDto,ContractPartya.class);
        LambdaQueryWrapper<ContractPartya> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPartya> page = contractPartyaMapper.selectPage(contractPartyaDto.getPage(),queryWrapper);
        Page<ContractPartyaVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPartyaVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPartyaDto
    * @return
    */
    @Override
    public Page lists(ContractPartyaDto contractPartyaDto) {
        Page<ContractPartyaVo> pageVo = contractPartyaMapper.getPageList(contractPartyaDto.getPage(),contractPartyaDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPartyaVo get(Long id) {
        ContractPartya contractPartya = super.getById(id);
        ContractPartyaVo contractPartyaVo = null;
        if(contractPartya !=null){
            contractPartyaVo = dozerMapper.map(contractPartya,ContractPartyaVo.class);
        }
        log.debug("查询成功");
        return contractPartyaVo;
    }

    /**
    * 保存实体
    * @param contractPartyaDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPartyaDto contractPartyaDto) {
        ContractPartya contractPartya = dozerMapper.map(contractPartyaDto,ContractPartya.class);
        boolean result = super.save(contractPartya);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPartyaDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPartyaDto contractPartyaDto) {
        ContractPartya contractPartya = dozerMapper.map(contractPartyaDto,ContractPartya.class);
        boolean result = super.updateById(contractPartya);
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
