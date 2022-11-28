package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPartybContact;
import com.netwisd.biz.mdm.mapper.ContractPartybContactMapper;
import com.netwisd.biz.mdm.service.ContractPartybContactService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPartybContactDto;
import com.netwisd.biz.mdm.vo.ContractPartybContactVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 乙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:39:08
 */
@Service
@Slf4j
public class ContractPartybContactServiceImpl extends ServiceImpl<ContractPartybContactMapper, ContractPartybContact> implements ContractPartybContactService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPartybContactMapper contractPartybContactMapper;

    /**
    * 单表简单查询操作
    * @param contractPartybContactDto
    * @return
    */
    @Override
    public Page list(ContractPartybContactDto contractPartybContactDto) {
        LambdaQueryWrapper<ContractPartybContact> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPartybContact> page = contractPartybContactMapper.selectPage(contractPartybContactDto.getPage(),queryWrapper);
        Page<ContractPartybContactVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPartybContactVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPartybContactDto
    * @return
    */
    @Override
    public Page lists(ContractPartybContactDto contractPartybContactDto) {
        Page<ContractPartybContactVo> pageVo = contractPartybContactMapper.getPageList(contractPartybContactDto.getPage(),contractPartybContactDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPartybContactVo get(Long id) {
        ContractPartybContact contractPartybContact = super.getById(id);
        ContractPartybContactVo contractPartybContactVo = null;
        if(contractPartybContact !=null){
            contractPartybContactVo = dozerMapper.map(contractPartybContact,ContractPartybContactVo.class);
        }
        log.debug("查询成功");
        return contractPartybContactVo;
    }

    /**
    * 保存实体
    * @param contractPartybContactDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPartybContactDto contractPartybContactDto) {
        ContractPartybContact contractPartybContact = dozerMapper.map(contractPartybContactDto,ContractPartybContact.class);
        boolean result = super.save(contractPartybContact);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPartybContactDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPartybContactDto contractPartybContactDto) {
        contractPartybContactDto.setUpdateTime(LocalDateTime.now());
        ContractPartybContact contractPartybContact = dozerMapper.map(contractPartybContactDto,ContractPartybContact.class);
        boolean result = super.updateById(contractPartybContact);
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
