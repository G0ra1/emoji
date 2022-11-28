package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPurchaseFile;
import com.netwisd.biz.mdm.mapper.ContractPurchaseFileMapper;
import com.netwisd.biz.mdm.service.ContractPurchaseFileService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPurchaseFileDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseFileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 采购合同附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-08 10:22:57
 */
@Service
@Slf4j
public class ContractPurchaseFileServiceImpl extends ServiceImpl<ContractPurchaseFileMapper, ContractPurchaseFile> implements ContractPurchaseFileService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPurchaseFileMapper contractPurchaseFileMapper;

    /**
    * 单表简单查询操作
    * @param contractPurchaseFileDto
    * @return
    */
    @Override
    public Page list(ContractPurchaseFileDto contractPurchaseFileDto) {
        LambdaQueryWrapper<ContractPurchaseFile> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPurchaseFile> page = contractPurchaseFileMapper.selectPage(contractPurchaseFileDto.getPage(),queryWrapper);
        Page<ContractPurchaseFileVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPurchaseFileVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPurchaseFileDto
    * @return
    */
    @Override
    public Page lists(ContractPurchaseFileDto contractPurchaseFileDto) {
        Page<ContractPurchaseFileVo> pageVo = contractPurchaseFileMapper.getPageList(contractPurchaseFileDto.getPage(),contractPurchaseFileDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPurchaseFileVo get(Long id) {
        ContractPurchaseFile contractPurchaseFile = super.getById(id);
        ContractPurchaseFileVo contractPurchaseFileVo = null;
        if(contractPurchaseFile !=null){
            contractPurchaseFileVo = dozerMapper.map(contractPurchaseFile,ContractPurchaseFileVo.class);
        }
        log.debug("查询成功");
        return contractPurchaseFileVo;
    }

    /**
    * 保存实体
    * @param contractPurchaseFileDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPurchaseFileDto contractPurchaseFileDto) {
        ContractPurchaseFile contractPurchaseFile = dozerMapper.map(contractPurchaseFileDto,ContractPurchaseFile.class);
        boolean result = super.save(contractPurchaseFile);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPurchaseFileDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPurchaseFileDto contractPurchaseFileDto) {
        contractPurchaseFileDto.setUpdateTime(LocalDateTime.now());
        ContractPurchaseFile contractPurchaseFile = dozerMapper.map(contractPurchaseFileDto,ContractPurchaseFile.class);
        boolean result = super.updateById(contractPurchaseFile);
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
