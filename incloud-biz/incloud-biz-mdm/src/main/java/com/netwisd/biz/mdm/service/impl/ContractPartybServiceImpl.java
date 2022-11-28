package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPartyb;
import com.netwisd.biz.mdm.mapper.ContractPartybMapper;
import com.netwisd.biz.mdm.service.ContractPartybService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPartybDto;
import com.netwisd.biz.mdm.vo.ContractPartybVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 合同乙方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-30 11:09:06
 */
@Service
@Slf4j
public class ContractPartybServiceImpl extends ServiceImpl<ContractPartybMapper, ContractPartyb> implements ContractPartybService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPartybMapper contractPartybMapper;

    /**
    * 单表简单查询操作
    * @param contractPartybDto
    * @return
    */
    @Override
    public Page list(ContractPartybDto contractPartybDto) {
        ContractPartyb contractPartyb = dozerMapper.map(contractPartybDto,ContractPartyb.class);
        LambdaQueryWrapper<ContractPartyb> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPartyb> page = contractPartybMapper.selectPage(contractPartybDto.getPage(),queryWrapper);
        Page<ContractPartybVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPartybVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPartybDto
    * @return
    */
    @Override
    public Page lists(ContractPartybDto contractPartybDto) {
        Page<ContractPartybVo> pageVo = contractPartybMapper.getPageList(contractPartybDto.getPage(),contractPartybDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPartybVo get(Long id) {
        ContractPartyb contractPartyb = super.getById(id);
        ContractPartybVo contractPartybVo = null;
        if(contractPartyb !=null){
            contractPartybVo = dozerMapper.map(contractPartyb,ContractPartybVo.class);
        }
        log.debug("查询成功");
        return contractPartybVo;
    }

    /**
    * 保存实体
    * @param contractPartybDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPartybDto contractPartybDto) {
        ContractPartyb contractPartyb = dozerMapper.map(contractPartybDto,ContractPartyb.class);
        boolean result = super.save(contractPartyb);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPartybDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPartybDto contractPartybDto) {
        ContractPartyb contractPartyb = dozerMapper.map(contractPartybDto,ContractPartyb.class);
        boolean result = super.updateById(contractPartyb);
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
