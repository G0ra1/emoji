package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPartyaContact;
import com.netwisd.biz.mdm.mapper.ContractPartyaContactMapper;
import com.netwisd.biz.mdm.service.ContractPartyaContactService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPartyaContactDto;
import com.netwisd.biz.mdm.vo.ContractPartyaContactVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 甲方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:31:37
 */
@Service
@Slf4j
public class ContractPartyaContactServiceImpl extends ServiceImpl<ContractPartyaContactMapper, ContractPartyaContact> implements ContractPartyaContactService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPartyaContactMapper contractPartyaContactMapper;

    /**
    * 单表简单查询操作
    * @param contractPartyaContactDto
    * @return
    */
    @Override
    public Page list(ContractPartyaContactDto contractPartyaContactDto) {
        LambdaQueryWrapper<ContractPartyaContact> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPartyaContact> page = contractPartyaContactMapper.selectPage(contractPartyaContactDto.getPage(),queryWrapper);
        Page<ContractPartyaContactVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPartyaContactVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPartyaContactDto
    * @return
    */
    @Override
    public Page lists(ContractPartyaContactDto contractPartyaContactDto) {
        Page<ContractPartyaContactVo> pageVo = contractPartyaContactMapper.getPageList(contractPartyaContactDto.getPage(),contractPartyaContactDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPartyaContactVo get(Long id) {
        ContractPartyaContact contractPartyaContact = super.getById(id);
        ContractPartyaContactVo contractPartyaContactVo = null;
        if(contractPartyaContact !=null){
            contractPartyaContactVo = dozerMapper.map(contractPartyaContact,ContractPartyaContactVo.class);
        }
        log.debug("查询成功");
        return contractPartyaContactVo;
    }

    /**
    * 保存实体
    * @param contractPartyaContactDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPartyaContactDto contractPartyaContactDto) {
        ContractPartyaContact contractPartyaContact = dozerMapper.map(contractPartyaContactDto,ContractPartyaContact.class);
        boolean result = super.save(contractPartyaContact);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPartyaContactDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPartyaContactDto contractPartyaContactDto) {
        contractPartyaContactDto.setUpdateTime(LocalDateTime.now());
        ContractPartyaContact contractPartyaContact = dozerMapper.map(contractPartyaContactDto,ContractPartyaContact.class);
        boolean result = super.updateById(contractPartyaContact);
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
