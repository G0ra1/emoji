package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import com.netwisd.biz.mdm.constants.ContractType;
import com.netwisd.biz.mdm.constants.MdmConstants;
import com.netwisd.biz.mdm.constants.YesNo;
import com.netwisd.biz.mdm.dto.*;
import com.netwisd.biz.mdm.entity.*;
import com.netwisd.biz.mdm.fegin.MdmClient;
import com.netwisd.biz.mdm.mapper.ContractSellMapper;
import com.netwisd.biz.mdm.service.*;
import com.netwisd.biz.mdm.vo.*;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 销售合同 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:07:24
 */
@Service
@Slf4j
public class ContractSellServiceImpl extends ServiceImpl<ContractSellMapper, ContractSell> implements ContractSellService {
    @Autowired
    private Mapper dozerMapper;

    @Value("${spring.rocketmq.sellTopics}")
    private String sellTopics;

    @Autowired
    private ContractSellMapper contractSellMapper;

    @Autowired
    private ContractSellDetailsService contractSellDetailsService;

    @Autowired
    private ContractPartyaService partyaService;

    @Autowired
    private ContractPartybService partybService;
    @Autowired
    private ContractPartycService partycService;

    @Autowired
    MdmMqService mdmMqService;
    @Autowired
    private MdmClient mdmClient;

    /**
    * 单表简单查询操作
    * @param contractSellDto
    * @return
    */
    @Override
    public Page list(ContractSellDto contractSellDto) {
        LambdaQueryWrapper<ContractSell> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(contractSellDto.getContractCode()),ContractSell::getContractCode,contractSellDto.getContractCode())
                .like(ObjectUtils.isNotEmpty(contractSellDto.getContractName()),ContractSell::getContractName,contractSellDto.getContractName())
                .eq(ContractSell::getIsDel, YesNo.NO.getCode());

        Page<ContractSell> page = contractSellMapper.selectPage(contractSellDto.getPage(),queryWrapper);
        Page<ContractSellVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractSellVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractSellDto
    * @return
    */
    @Override
    public List<ContractSellVo> lists(ContractSellDto contractSellDto) {
        LambdaQueryWrapper<ContractSell> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(contractSellDto.getProjectCode()),ContractSell::getProjectCode,contractSellDto.getProjectCode())
                .eq(ObjectUtil.isNotEmpty(contractSellDto.getType()),ContractSell::getType,contractSellDto.getType())
                .eq(ObjectUtil.isNotEmpty(contractSellDto.getState()),ContractSell::getState,contractSellDto.getState())
                .between(ObjectUtil.isNotNull(contractSellDto.getSUpdateTime())&&ObjectUtil.isNotNull(contractSellDto.getEUpdateTime()), ContractSell::getUpdateTime, contractSellDto.getSUpdateTime(),contractSellDto.getEUpdateTime());
        List<ContractSell> list=super.list(queryWrapper);
        List<ContractSellVo> voList=DozerUtils.mapList(dozerMapper,list,ContractSellVo.class);
        for (ContractSellVo vo:voList) {
            this.getOther(vo);
        }
        log.debug("查询条数:"+list.size());
        return voList;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractSellVo get(Long id) {
        ContractSell contractSell = super.getById(id);

        ContractSellVo contractSellVo = null;
        if(contractSell !=null){
            contractSellVo = dozerMapper.map(contractSell,ContractSellVo.class);
        }
        getOther(contractSellVo);
        log.debug("查询成功");
        return contractSellVo;
    }

    /**
    * 保存实体
    * @param contractSellDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractSellDto contractSellDto) {
        ContractSell contractSell = dozerMapper.map(contractSellDto,ContractSell.class);
        boolean result = super.save(contractSell);
        contractSellDto.setId(contractSell.getId());
        saveOther(contractSellDto);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractSellDto
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(ContractSellDto contractSellDto) {
        contractSellDto.setUpdateTime(LocalDateTime.now());
        ContractSell contractSell = dozerMapper.map(contractSellDto,ContractSell.class);
        boolean result = super.updateById(contractSell);
        deleteOther(contractSellDto);
        saveOther(contractSellDto);

        List<ContractSellDto> sellList=new ArrayList<>();
        sellList.add(contractSellDto);
        List<ContractPartya> partyaList=partyaService.list(Wrappers.<ContractPartya>lambdaQuery().eq(ContractPartya::getContractCode,contractSellDto.getContractCode()));
        List<ContractPartyb> partybList=partybService.list(Wrappers.<ContractPartyb>lambdaQuery().eq(ContractPartyb::getContractCode,contractSellDto.getContractCode()));
        List<ContractPartyc> partycList=partycService.list(Wrappers.<ContractPartyc>lambdaQuery().eq(ContractPartyc::getContractCode,contractSellDto.getContractCode()));
        List<ContractSellDetails> detailsList=contractSellDetailsService.list(Wrappers.<ContractSellDetails>lambdaQuery().eq(ContractSellDetails::getContractCode,contractSellDto.getContractCode()));

        contractSellDto.setPartyaList(DozerUtils.mapList(dozerMapper,partyaList,ContractPartyaDto.class));
        contractSellDto.setPartybList(DozerUtils.mapList(dozerMapper,partybList,ContractPartybDto.class));
        contractSellDto.setPartycList(DozerUtils.mapList(dozerMapper,partycList,ContractPartycDto.class));
        contractSellDto.setDetailsList(DozerUtils.mapList(dozerMapper,detailsList,ContractSellDetailsDto.class));
        String json = JSON.toJSONString(sellList);
        mdmMqService.sendRocketMq(sellTopics,MdmConstants.SELL,json);
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
        ContractSell sell=super.getById(id);
        boolean result = super.removeById(id);
        ContractSellDto dto=dozerMapper.map(sell,ContractSellDto.class);
        deleteOther(dto);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    @SneakyThrows
    public Boolean saveList(List<ContractSellDto> dtoList) {
        List<String> dataSourceIdList=dtoList.stream().map(ContractSellDto::getDataSourceId).collect(Collectors.toList());

        LambdaQueryWrapper<ContractSell> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ContractSell::getDataSourceId,dataSourceIdList);
        List<ContractSell> sellList=super.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(sellList)){
            List<Long> idList=sellList.stream().map(ContractSell::getId).collect(Collectors.toList());
            //删除子表数据
            LambdaQueryWrapper<ContractSellDetails> detailsLambdaQueryWrapper=new LambdaQueryWrapper<>();
            detailsLambdaQueryWrapper.in(ContractSellDetails::getContractId,idList);
            contractSellDetailsService.remove(detailsLambdaQueryWrapper);

            LambdaQueryWrapper<ContractPartya> partyaLambdaQueryWrapper=new LambdaQueryWrapper<>();
            partyaLambdaQueryWrapper.in(ContractPartya::getContractId,idList);
            partyaService.remove(partyaLambdaQueryWrapper);

            LambdaQueryWrapper<ContractPartyb> partybLambdaQueryWrapper=new LambdaQueryWrapper<>();
            partybLambdaQueryWrapper.in(ContractPartyb::getContractId,idList);
            partybService.remove(partybLambdaQueryWrapper);

            LambdaQueryWrapper<ContractPartyc> partycLambdaQueryWrapper=new LambdaQueryWrapper<>();
            partycLambdaQueryWrapper.in(ContractPartyc::getContractId,idList);
            partycService.remove(partycLambdaQueryWrapper);
        }

        List<ContractSellDto> contractSellDtos=new ArrayList<>();
        List<ContractPartya> partyaList=new ArrayList<>();
        List<ContractPartyb> partybList=new ArrayList<>();
        List<ContractPartyc> partycList=new ArrayList<>();
        for (ContractSellDto dto:dtoList) {
            ContractSell sell=dozerMapper.map(dto,ContractSell.class);
            ContractSell parentSell=getOne(Wrappers.<ContractSell>lambdaQuery().eq(ContractSell::getDataSourceId, dto.getParentId()));
            if(parentSell!=null){
                sell.setParentId(String.valueOf(parentSell.getId()));
            }
            List<ContractSell> oldSellList=sellList.stream().filter(s -> s.getDataSourceId().equals(dto.getDataSourceId())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(oldSellList)){
                sell.setId(oldSellList.get(0).getId());
                this.updateById(sell);
            }else {
                this.save(sell);
            }
            //saveOrUpdate(sell, Wrappers.<ContractSell>lambdaQuery().eq(ContractSell::getDataSourceId, dto.getDataSourceId()));
            dto.setId(sell.getId());
            List<ContractPartyaDto> partyaDtos=dto.getPartyaList();
            if(CollectionUtils.isNotEmpty(partyaDtos)){
                for (ContractPartyaDto partyaDto:partyaDtos) {
                    ContractPartya partya=dozerMapper.map(partyaDto,ContractPartya.class);
                    partya.setContractId(sell.getId());
                    //查询客户

                    partya.setContractCode(sell.getContractCode());
                    partya.setContractType(ContractType.SALE.getCode());
                    partyaList.add(partya);
                }
            }
            dto.setPartyaList(DozerUtils.mapList(dozerMapper,partyaList,ContractPartyaDto.class));
            List<ContractPartybDto> partybDtos=dto.getPartybList();
            if(CollectionUtils.isNotEmpty(partybDtos)){
                for (ContractPartybDto partybDto:partybDtos) {
                    ContractPartyb partyb=dozerMapper.map(partybDto,ContractPartyb.class);
                    partyb.setContractId(sell.getId());
                    /*//查询组织机构
                    List<MdmOrgVo> orgVos=mdmClient.getOrgByJcOrgIds(partyb.getPartybId());
                    if(CollectionUtils.isNotEmpty(orgVos)){
                        partyb.setPartybId(orgVos.get(0).getId());
                        partyb.setPartybCode(orgVos.get(0).getOrgCode());
                    }else{
                        partyb.setPartybCode(partyb.getPartybId());
                    }*/
                    partyb.setContractCode(sell.getContractCode());
                    partyb.setContractType(ContractType.SALE.getCode());
                    partybList.add(partyb);
                }
            }
            dto.setPartybList(DozerUtils.mapList(dozerMapper,partybList,ContractPartybDto.class));
            List<ContractPartycDto> partycDtos=dto.getPartycList();
            if(CollectionUtils.isNotEmpty(partycDtos)){
                for (ContractPartycDto partycDto:partycDtos) {
                    ContractPartyc partyc=dozerMapper.map(partycDto,ContractPartyc.class);
                    partyc.setContractId(sell.getId());
                    partyc.setContractCode(sell.getContractCode());
                    partyc.setContractType(ContractType.SALE.getCode());
                    partycList.add(partyc);
                }
            }
            dto.setPartycList(DozerUtils.mapList(dozerMapper,partycList,ContractPartycDto.class));

            contractSellDtos.add(dto);
        }
        partyaService.saveBatch(partyaList);
        partybService.saveBatch(partybList);
        partycService.saveBatch(partycList);

        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(contractSellDtos)){
            String json = JSON.toJSONString(contractSellDtos);
            mdmMqService.sendRocketMq(sellTopics,MdmConstants.SELL,json);
        }
        return true;
    }

    @Override
    public Boolean delList(List<ContractSellDto> dtoList) {
        List<String> dataSourceList=dtoList.stream().map(ContractSellDto::getDataSourceId).collect(Collectors.toList());
        ContractSell sell=new ContractSell();
        sell.setIsDel(String.valueOf(YesNo.YES.getCode()));
        Boolean result=update(sell, Wrappers.<ContractSell>lambdaQuery().in(ContractSell::getDataSourceId, dataSourceList));
        if(result){
            List<ContractSell> list=this.list(Wrappers.<ContractSell>lambdaQuery().in(ContractSell::getDataSourceId, dataSourceList));
            List<Long> idList=list.stream().map(ContractSell::getId).collect(Collectors.toList());
            return dels(idList);
        }
        return true;
    }

    public Boolean dels(List<Long> parentIdList){
        ContractSell sell=new ContractSell();
        sell.setIsDel(String.valueOf(YesNo.YES.getCode()));

        Boolean result=update(sell, Wrappers.<ContractSell>lambdaQuery().in(ContractSell::getParentId, parentIdList));
        if(result){
            List<ContractSell> childList=this.list(Wrappers.<ContractSell>lambdaQuery().in(ContractSell::getParentId, parentIdList));
            parentIdList=childList.stream().map(ContractSell::getId).collect(Collectors.toList());
            dels(parentIdList);
        }
        return true;
    }

    public void deleteOther(ContractSellDto contractSellDto){
        //合同详情删除
       LambdaQueryWrapper<ContractSellDetails> queryWrapper=new LambdaQueryWrapper<>();
       queryWrapper.eq(ObjectUtils.isNotEmpty(contractSellDto.getContractCode()),ContractSellDetails::getContractCode,contractSellDto.getContractCode());
       contractSellDetailsService.remove(queryWrapper);
        //甲方删除
        LambdaQueryWrapper<ContractPartya> queryWrapperPa = new LambdaQueryWrapper<>();
        queryWrapperPa.eq(ContractPartya::getContractCode,contractSellDto.getContractCode())
                .eq(ContractPartya::getContractType, ContractType.SALE.getCode());
        partyaService.remove(queryWrapperPa);
        //乙方删除
        LambdaQueryWrapper<ContractPartyb> queryWrapperPb = new LambdaQueryWrapper<>();
        queryWrapperPb.eq(ContractPartyb::getContractCode,contractSellDto.getContractCode())
                .eq(ContractPartyb::getContractType, ContractType.SALE.getCode());
        partybService.remove(queryWrapperPb);
        //丙方删除
        LambdaQueryWrapper<ContractPartyc> queryWrapperPc = new LambdaQueryWrapper<>();
        queryWrapperPc.eq(ContractPartyc::getContractCode,contractSellDto.getContractCode())
                .eq(ContractPartyc::getContractType, ContractType.SALE.getCode());
        partycService.remove(queryWrapperPc);
    }

    public void saveOther(ContractSellDto contractSellDto){
        List<ContractSellDetailsDto> detailsList =contractSellDto.getDetailsList();
        List<ContractSellDetails> details=new ArrayList<>();
        for (ContractSellDetailsDto dto:detailsList
             ) {
            ContractSellDetails detail=dozerMapper.map(dto,ContractSellDetails.class);
            detail.setContractId(contractSellDto.getId());
            detail.setContractCode(contractSellDto.getContractCode());
            detail.setContractName(contractSellDto.getContractName());
            details.add(detail);
        }
        //甲方
        List<ContractPartyaDto> partyaDtos=contractSellDto.getPartyaList();
        List<ContractPartya> partyaList=new ArrayList<>();
        for (ContractPartyaDto dto:partyaDtos) {
            ContractPartya partya=dozerMapper.map(dto,ContractPartya.class);
            partya.setContractId(contractSellDto.getId());
            partya.setContractCode(contractSellDto.getContractCode());
            partya.setContractType(ContractType.SALE.getCode());
            partyaList.add(partya);
        }
        partyaService.saveBatch(partyaList);

        //乙方
        List<ContractPartybDto> partybDtos=contractSellDto.getPartybList();
        List<ContractPartyb> partybList=new ArrayList<>();
        for (ContractPartybDto dto:partybDtos) {
            ContractPartyb partyb=dozerMapper.map(dto,ContractPartyb.class);
            partyb.setContractId(contractSellDto.getId());
            partyb.setContractCode(contractSellDto.getContractCode());
            partyb.setContractType(ContractType.SALE.getCode());
            partybList.add(partyb);
        }
        partybService.saveBatch(partybList);

        //丙方
        List<ContractPartycDto> partycDtos=contractSellDto.getPartycList();
        List<ContractPartyc> partycList=new ArrayList<>();
        for (ContractPartycDto dto:partycDtos) {
            ContractPartyc partyc=dozerMapper.map(dto,ContractPartyc.class);
            partyc.setContractId(contractSellDto.getId());
            partyc.setContractCode(contractSellDto.getContractCode());
            partyc.setContractType(ContractType.SALE.getCode());
            partycList.add(partyc);
        }
        partycService.saveBatch(partycList);

        contractSellDetailsService.saveBatch(details);
    }

    public void getOther(ContractSellVo contractSellVo){
        //合同详情
        LambdaQueryWrapper<ContractSellDetails> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(contractSellVo.getContractCode()),ContractSellDetails::getContractCode,contractSellVo.getContractCode());
        List<ContractSellDetails> details=contractSellDetailsService.list(queryWrapper);
        List<ContractSellDetailsVo> detailsVos=new ArrayList<>();
        for (ContractSellDetails detail:details
             ) {
            ContractSellDetailsVo vo=dozerMapper.map(detail,ContractSellDetailsVo.class);
            detailsVos.add(vo);
        }
        contractSellVo.setDetailsList(detailsVos);

        //甲乙方
        LambdaQueryWrapper<ContractPartya> partyaWrapper=new LambdaQueryWrapper<>();
        partyaWrapper.eq(ContractPartya::getContractCode,contractSellVo.getContractCode())
                .eq(ContractPartya::getContractType, ContractType.SALE.getCode());
        List<ContractPartya> partyaList=partyaService.list(partyaWrapper);
        List<ContractPartyaVo> partyaVoList=new ArrayList<>();
        for (ContractPartya partya:partyaList) {
            ContractPartyaVo partyaVo=dozerMapper.map(partya,ContractPartyaVo.class);
            partyaVoList.add(partyaVo);
        }
        contractSellVo.setPartyaList(partyaVoList);

        LambdaQueryWrapper<ContractPartyb> partybWrapper=new LambdaQueryWrapper<>();
        partybWrapper.eq(ContractPartyb::getContractCode,contractSellVo.getContractCode())
                .eq(ContractPartyb::getContractType, ContractType.SALE.getCode());
        List<ContractPartyb> partybList=partybService.list(partybWrapper);
        List<ContractPartybVo> partybVoList=new ArrayList<>();
        for (ContractPartyb partyb:partybList) {
            ContractPartybVo partybVo=dozerMapper.map(partyb,ContractPartybVo.class);
            partybVoList.add(partybVo);
        }
        contractSellVo.setPartybList(partybVoList);

        LambdaQueryWrapper<ContractPartyc> partycWrapper=new LambdaQueryWrapper<>();
        partycWrapper.eq(ContractPartyc::getContractCode,contractSellVo.getContractCode())
                .eq(ContractPartyc::getContractType, ContractType.SALE.getCode());
        List<ContractPartyc> partycList=partycService.list(partycWrapper);
        List<ContractPartycVo> partycVoList=new ArrayList<>();
        for (ContractPartyc partyc:partycList) {
            ContractPartycVo partycVo=dozerMapper.map(partyc,ContractPartycVo.class);
            partycVoList.add(partycVo);
        }
        contractSellVo.setPartycList(partycVoList);

    }

}
