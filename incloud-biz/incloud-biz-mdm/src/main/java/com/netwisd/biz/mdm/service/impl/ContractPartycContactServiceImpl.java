package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPartycContact;
import com.netwisd.biz.mdm.mapper.ContractPartycContactMapper;
import com.netwisd.biz.mdm.service.ContractPartycContactService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPartycContactDto;
import com.netwisd.biz.mdm.vo.ContractPartycContactVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 丙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:40:00
 */
@Service
@Slf4j
public class ContractPartycContactServiceImpl extends ServiceImpl<ContractPartycContactMapper, ContractPartycContact> implements ContractPartycContactService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPartycContactMapper contractPartycContactMapper;

    /**
    * 单表简单查询操作
    * @param contractPartycContactDto
    * @return
    */
    @Override
    public Page list(ContractPartycContactDto contractPartycContactDto) {
        LambdaQueryWrapper<ContractPartycContact> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPartycContact> page = contractPartycContactMapper.selectPage(contractPartycContactDto.getPage(),queryWrapper);
        Page<ContractPartycContactVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPartycContactVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPartycContactDto
    * @return
    */
    @Override
    public Page lists(ContractPartycContactDto contractPartycContactDto) {
        Page<ContractPartycContactVo> pageVo = contractPartycContactMapper.getPageList(contractPartycContactDto.getPage(),contractPartycContactDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPartycContactVo get(Long id) {
        ContractPartycContact contractPartycContact = super.getById(id);
        ContractPartycContactVo contractPartycContactVo = null;
        if(contractPartycContact !=null){
            contractPartycContactVo = dozerMapper.map(contractPartycContact,ContractPartycContactVo.class);
        }
        log.debug("查询成功");
        return contractPartycContactVo;
    }

    /**
    * 保存实体
    * @param contractPartycContactDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPartycContactDto contractPartycContactDto) {
        ContractPartycContact contractPartycContact = dozerMapper.map(contractPartycContactDto,ContractPartycContact.class);
        boolean result = super.save(contractPartycContact);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPartycContactDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPartycContactDto contractPartycContactDto) {
        contractPartycContactDto.setUpdateTime(LocalDateTime.now());
        ContractPartycContact contractPartycContact = dozerMapper.map(contractPartycContactDto,ContractPartycContact.class);
        boolean result = super.updateById(contractPartycContact);
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
