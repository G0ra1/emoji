package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPurchaseExecution;
import com.netwisd.biz.mdm.mapper.ContractPurchaseExecutionMapper;
import com.netwisd.biz.mdm.service.ContractPurchaseExecutionService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPurchaseExecutionDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseExecutionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 执行范围 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-02 14:38:36
 */
@Service
@Slf4j
public class ContractPurchaseExecutionServiceImpl extends ServiceImpl<ContractPurchaseExecutionMapper, ContractPurchaseExecution> implements ContractPurchaseExecutionService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPurchaseExecutionMapper contractPurchaseExecutionMapper;

    /**
    * 单表简单查询操作
    * @param contractPurchaseExecutionDto
    * @return
    */
    @Override
    public Page list(ContractPurchaseExecutionDto contractPurchaseExecutionDto) {
        ContractPurchaseExecution contractPurchaseExecution = dozerMapper.map(contractPurchaseExecutionDto,ContractPurchaseExecution.class);
        LambdaQueryWrapper<ContractPurchaseExecution> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPurchaseExecution> page = contractPurchaseExecutionMapper.selectPage(contractPurchaseExecutionDto.getPage(),queryWrapper);
        Page<ContractPurchaseExecutionVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPurchaseExecutionVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPurchaseExecutionDto
    * @return
    */
    @Override
    public Page lists(ContractPurchaseExecutionDto contractPurchaseExecutionDto) {
        Page<ContractPurchaseExecutionVo> pageVo = contractPurchaseExecutionMapper.getPageList(contractPurchaseExecutionDto.getPage(),contractPurchaseExecutionDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPurchaseExecutionVo get(Long id) {
        ContractPurchaseExecution contractPurchaseExecution = super.getById(id);
        ContractPurchaseExecutionVo contractPurchaseExecutionVo = null;
        if(contractPurchaseExecution !=null){
            contractPurchaseExecutionVo = dozerMapper.map(contractPurchaseExecution,ContractPurchaseExecutionVo.class);
        }
        log.debug("查询成功");
        return contractPurchaseExecutionVo;
    }

    /**
    * 保存实体
    * @param contractPurchaseExecutionDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPurchaseExecutionDto contractPurchaseExecutionDto) {
        ContractPurchaseExecution contractPurchaseExecution = dozerMapper.map(contractPurchaseExecutionDto,ContractPurchaseExecution.class);
        boolean result = super.save(contractPurchaseExecution);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPurchaseExecutionDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPurchaseExecutionDto contractPurchaseExecutionDto) {
        ContractPurchaseExecution contractPurchaseExecution = dozerMapper.map(contractPurchaseExecutionDto,ContractPurchaseExecution.class);
        boolean result = super.updateById(contractPurchaseExecution);
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
