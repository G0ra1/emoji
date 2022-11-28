package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.ContractPurchase;
import com.netwisd.biz.mdm.entity.ContractPurchaseDetails;
import com.netwisd.biz.mdm.mapper.ContractPurchaseDetailsMapper;
import com.netwisd.biz.mdm.mapper.ContractPurchaseMapper;
import com.netwisd.biz.mdm.service.ContractPurchaseDetailsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ContractPurchaseDetailsDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 采购合同清单 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:49:25
 */
@Service
@Slf4j
public class ContractPurchaseDetailsServiceImpl extends ServiceImpl<ContractPurchaseDetailsMapper, ContractPurchaseDetails> implements ContractPurchaseDetailsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPurchaseDetailsMapper contractPurchaseDetailsMapper;

    @Autowired
    private ContractPurchaseMapper purchaseMapper;

    /**
    * 单表简单查询操作
    * @param contractPurchaseDetailsDto
    * @return
    */
    @Override
    public Page list(ContractPurchaseDetailsDto contractPurchaseDetailsDto) {
        ContractPurchaseDetails contractPurchaseDetails = dozerMapper.map(contractPurchaseDetailsDto,ContractPurchaseDetails.class);
        LambdaQueryWrapper<ContractPurchaseDetails> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ContractPurchaseDetails> page = contractPurchaseDetailsMapper.selectPage(contractPurchaseDetailsDto.getPage(),queryWrapper);
        Page<ContractPurchaseDetailsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPurchaseDetailsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param dto
    * @return
    */
    @Override
    public List<ContractPurchaseDetailsVo> lists(ContractPurchaseDetailsDto dto) {
        LambdaQueryWrapper<ContractPurchaseDetails> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(dto.getContractCode()),ContractPurchaseDetails::getContractCode,dto.getContractCode());
        List<ContractPurchaseDetails> list=super.list(queryWrapper);
        log.debug("查询条数:"+list.size());
        return DozerUtils.mapList(dozerMapper,list,ContractPurchaseDetailsVo.class);
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPurchaseDetailsVo get(Long id) {
        ContractPurchaseDetails contractPurchaseDetails = super.getById(id);
        ContractPurchaseDetailsVo contractPurchaseDetailsVo = null;
        if(contractPurchaseDetails !=null){
            contractPurchaseDetailsVo = dozerMapper.map(contractPurchaseDetails,ContractPurchaseDetailsVo.class);
        }
        log.debug("查询成功");
        return contractPurchaseDetailsVo;
    }

    /**
    * 保存实体
    * @param contractPurchaseDetailsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPurchaseDetailsDto contractPurchaseDetailsDto) {
        ContractPurchaseDetails contractPurchaseDetails = dozerMapper.map(contractPurchaseDetailsDto,ContractPurchaseDetails.class);
        boolean result = super.save(contractPurchaseDetails);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPurchaseDetailsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ContractPurchaseDetailsDto contractPurchaseDetailsDto) {
        ContractPurchaseDetails contractPurchaseDetails = dozerMapper.map(contractPurchaseDetailsDto,ContractPurchaseDetails.class);
        boolean result = super.updateById(contractPurchaseDetails);
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

    /**
     * 数据同步
     * @param list
     */
    @Transactional
    @Override
    public void saveTransmitAll(List<ContractPurchaseDetailsDto> list){
        //查询所有合同
        List<String> contractSourceIdList=list.stream().map(ContractPurchaseDetailsDto::getContractId).collect(Collectors.toList());
        LambdaQueryWrapper<ContractPurchase> queryWrapperCon=new LambdaQueryWrapper<>();
        queryWrapperCon.in(ContractPurchase::getDataSourceId,contractSourceIdList);
        List<ContractPurchase> purchaseList=purchaseMapper.selectList(queryWrapperCon);

        //删除原有的合同明细，查询合同，然后根据合同id删除
        List<Long> contractIdList=purchaseList.stream().map(ContractPurchase::getId).collect(Collectors.toList());
        LambdaQueryWrapper<ContractPurchaseDetails> queryWrapperDet=new LambdaQueryWrapper<>();
        queryWrapperDet.in(ContractPurchaseDetails::getContractId,contractIdList);
        super.remove(queryWrapperDet);

        for (ContractPurchaseDetailsDto dto:list) {
            ContractPurchaseDetails details=dozerMapper.map(dto,ContractPurchaseDetails.class);
            details.setDataSourceId(dto.getId().toString());
            List<ContractPurchase> purchases=purchaseList.stream().filter(pur-> pur.getDataSourceId().equals(details.getContractId().toString())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(purchases)){
                ContractPurchase purchase=purchases.get(0);
                details.setContractId(purchase.getId().toString());
                details.setContractName(purchase.getContractName());
                details.setContractCode(purchase.getContractCode());
            }else {
                continue;
            }
            super.save(details);
        }


    }

}
