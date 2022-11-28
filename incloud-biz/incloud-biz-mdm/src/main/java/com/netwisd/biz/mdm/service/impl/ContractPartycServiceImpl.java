package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPartyc;
import com.netwisd.biz.mdm.mapper.ContractPartycMapper;
import com.netwisd.biz.mdm.service.ContractPartycService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPartycDto;
import com.netwisd.biz.mdm.vo.ContractPartycVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 丙方 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-14 15:34:03
 */
@Service
@Slf4j
public class ContractPartycServiceImpl extends ServiceImpl<ContractPartycMapper, ContractPartyc> implements ContractPartycService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPartycMapper contractPartycMapper;

    /**
    * 单表简单查询操作
    * @param contractPartycDto
    * @return
    */
    @Override
    public Page list(ContractPartycDto contractPartycDto) {
        LambdaQueryWrapper<ContractPartyc> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPartyc> page = contractPartycMapper.selectPage(contractPartycDto.getPage(),queryWrapper);
        Page<ContractPartycVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPartycVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPartycDto
    * @return
    */
    @Override
    public Page lists(ContractPartycDto contractPartycDto) {
        Page<ContractPartycVo> pageVo = contractPartycMapper.getPageList(contractPartycDto.getPage(),contractPartycDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPartycVo get(Long id) {
        ContractPartyc contractPartyc = super.getById(id);
        ContractPartycVo contractPartycVo = null;
        if(contractPartyc !=null){
            contractPartycVo = dozerMapper.map(contractPartyc,ContractPartycVo.class);
        }
        log.debug("查询成功");
        return contractPartycVo;
    }

    /**
    * 保存实体
    * @param contractPartycDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPartycDto contractPartycDto) {
        ContractPartyc contractPartyc = dozerMapper.map(contractPartycDto,ContractPartyc.class);
        boolean result = super.save(contractPartyc);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPartycDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPartycDto contractPartycDto) {
        contractPartycDto.setUpdateTime(LocalDateTime.now());
        ContractPartyc contractPartyc = dozerMapper.map(contractPartycDto,ContractPartyc.class);
        boolean result = super.updateById(contractPartyc);
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
