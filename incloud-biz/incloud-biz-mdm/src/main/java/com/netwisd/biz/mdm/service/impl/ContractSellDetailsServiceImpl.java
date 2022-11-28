package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractSellDetails;
import com.netwisd.biz.mdm.mapper.ContractSellDetailsMapper;
import com.netwisd.biz.mdm.service.ContractSellDetailsService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractSellDetailsDto;
import com.netwisd.biz.mdm.vo.ContractSellDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 销售合同清单 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:09:22
 */
@Service
@Slf4j
public class ContractSellDetailsServiceImpl extends ServiceImpl<ContractSellDetailsMapper, ContractSellDetails> implements ContractSellDetailsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractSellDetailsMapper contractSellDetailsMapper;

    /**
    * 单表简单查询操作
    * @param contractSellDetailsDto
    * @return
    */
    @Override
    public Page list(ContractSellDetailsDto contractSellDetailsDto) {
        LambdaQueryWrapper<ContractSellDetails> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractSellDetails> page = contractSellDetailsMapper.selectPage(contractSellDetailsDto.getPage(),queryWrapper);
        Page<ContractSellDetailsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractSellDetailsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractSellDetailsDto
    * @return
    */
    @Override
    public Page lists(ContractSellDetailsDto contractSellDetailsDto) {
        Page<ContractSellDetailsVo> pageVo = contractSellDetailsMapper.getPageList(contractSellDetailsDto.getPage(),contractSellDetailsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractSellDetailsVo get(Long id) {
        ContractSellDetails contractSellDetails = super.getById(id);
        ContractSellDetailsVo contractSellDetailsVo = null;
        if(contractSellDetails !=null){
            contractSellDetailsVo = dozerMapper.map(contractSellDetails,ContractSellDetailsVo.class);
        }
        log.debug("查询成功");
        return contractSellDetailsVo;
    }

    /**
    * 保存实体
    * @param contractSellDetailsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractSellDetailsDto contractSellDetailsDto) {
        ContractSellDetails contractSellDetails = dozerMapper.map(contractSellDetailsDto,ContractSellDetails.class);
        boolean result = super.save(contractSellDetails);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractSellDetailsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractSellDetailsDto contractSellDetailsDto) {
        contractSellDetailsDto.setUpdateTime(LocalDateTime.now());
        ContractSellDetails contractSellDetails = dozerMapper.map(contractSellDetailsDto,ContractSellDetails.class);
        boolean result = super.updateById(contractSellDetails);
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
