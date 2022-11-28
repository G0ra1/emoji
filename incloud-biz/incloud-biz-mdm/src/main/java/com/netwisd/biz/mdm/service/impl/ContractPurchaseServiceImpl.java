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
import com.netwisd.biz.mdm.mapper.*;
import com.netwisd.biz.mdm.service.*;
import com.netwisd.biz.mdm.vo.*;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

/*
 * @Description 采购合同 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
@Service
@Slf4j
public class ContractPurchaseServiceImpl extends ServiceImpl<ContractPurchaseMapper, ContractPurchase> implements ContractPurchaseService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ContractPurchaseMapper contractPurchaseMapper;

    @Autowired
    private ContractPurchaseExecutionService contractPurchaseExecutionService;

    @Autowired
    private ContractPartyaService partyaService;

    @Autowired
    private ContractPartybService partybService;

    @Autowired
    private ContractPartycService partycService;

    @Autowired
    private ContractPartyaContactService contractPartyaContactService;

    @Autowired
    private ContractPartybContactService contractPartybContactService;

    @Autowired
    private ContractPartycContactService contractPartycContactService;

    @Autowired
    private ContractPurchaseDetailsService detailsService;

    @Autowired
    private ProjectService projectService;

    @Value("${spring.rocketmq.purchaseTopics}")
    private String purchaseTopics;

    @Autowired
    MdmMqService mdmMqService;

    @Autowired
    private ContractPurchaseFileService contractPurchaseFileService;
    @Autowired
    private MdmClient mdmClient;
    @Autowired
    private SupplierService supplierService;

    /**
    * 单表简单查询操作
    * @param contractPurchaseDto
    * @return
    */
    @Override
    public Page list(ContractPurchaseDto contractPurchaseDto) {
        ContractPurchase contractPurchase = dozerMapper.map(contractPurchaseDto,ContractPurchase.class);
        LambdaQueryWrapper<ContractPurchase> queryWrapper = new LambdaQueryWrapper<>();
        List<String> ContractCategorylist=new ArrayList<>();
        ContractCategorylist.add("4");
        ContractCategorylist.add("5");
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtil.isNotEmpty(contractPurchase.getContractCode()),ContractPurchase::getContractCode, contractPurchase.getContractCode())
                .eq(StringUtils.isNotEmpty(contractPurchase.getContractCategory())&&!contractPurchase.getContractCategory().equals("-1"),ContractPurchase::getContractCategory, contractPurchase.getContractCategory())
                .ne(StringUtils.isNotEmpty(contractPurchase.getContractCategory())&&contractPurchase.getContractCategory().equals("-1"),ContractPurchase::getContractCategory,"2")
                .notIn(ContractPurchase::getContractCategory,ContractCategorylist)
                .like(ObjectUtil.isNotEmpty(contractPurchase.getContractName()),ContractPurchase::getContractName,contractPurchase.getContractName());

        Page<ContractPurchase> page = contractPurchaseMapper.selectPage(contractPurchaseDto.getPage(),queryWrapper);
        Page<ContractPurchaseVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ContractPurchaseVo.class);
        if(StringUtils.isNotEmpty(contractPurchase.getContractCategory())&&contractPurchase.getContractCategory().equals("2")){//框架协议查询项下合同/订单
            List<ContractPurchaseVo> list=pageVo.getRecords();
            List<String> idList=list.stream().map(ContractPurchaseVo::getDataSourceId).collect(Collectors.toList());
            List<ContractPurchase> childList = this.list( Wrappers.<ContractPurchase>lambdaQuery().in(ContractPurchase::getFramecontractId,idList));
            for (ContractPurchaseVo vo:list) {
                List<ContractPurchase> thisChildList =childList.stream().filter(con -> con.getFramecontractId().equals(vo.getDataSourceId())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(thisChildList)){
                    vo.setChildList(DozerUtils.mapList(dozerMapper,thisChildList,ContractPurchaseVo.class));
                    vo.setHasKids(true);
                }else{
                    vo.setHasKids(false);
                }
            }
        }
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param contractPurchaseDto
    * @return
    */
    @Override
    public List<ContractPurchaseVo> lists(ContractPurchaseDto contractPurchaseDto) {
        LambdaQueryWrapper<ContractPurchase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(contractPurchaseDto.getProjectCode()),ContractPurchase::getProjectCode,contractPurchaseDto.getProjectCode())
                .eq(ObjectUtil.isNotEmpty(contractPurchaseDto.getType()),ContractPurchase::getType,contractPurchaseDto.getType())
                .eq(ObjectUtil.isNotEmpty(contractPurchaseDto.getState()),ContractPurchase::getState,contractPurchaseDto.getState())
                .between(ObjectUtil.isNotNull(contractPurchaseDto.getSUpdateTime())&&ObjectUtil.isNotNull(contractPurchaseDto.getEUpdateTime()), ContractPurchase::getUpdateTime, contractPurchaseDto.getSUpdateTime(),contractPurchaseDto.getEUpdateTime());

        List<ContractPurchase> list=super.list(queryWrapper);
        List<ContractPurchaseVo> voList=DozerUtils.mapList(dozerMapper,list,ContractPurchaseVo.class);

        for (ContractPurchaseVo vo:voList) {
            getOther(vo);
        }
        log.debug("查询条数:"+voList.size());
        return voList;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ContractPurchaseVo get(Long id) {
        ContractPurchase contractPurchase = super.getById(id);
        ContractPurchaseVo contractPurchaseVo =new ContractPurchaseVo();
        if(contractPurchase !=null){
            contractPurchaseVo = dozerMapper.map(contractPurchase,ContractPurchaseVo.class);
            getOther(contractPurchaseVo);
        }
        log.debug("查询成功");
        return contractPurchaseVo;
    }


    /**
    * 保存实体
    * @param contractPurchaseDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ContractPurchaseDto contractPurchaseDto) {
        ContractPurchase contractPurchase = dozerMapper.map(contractPurchaseDto,ContractPurchase.class);
        //先查询是否code重复
        LambdaQueryWrapper<ContractPurchase> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ContractPurchase::getContractCode,contractPurchase.getContractCode());
        List<ContractPurchase> list=super.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            log.error("已存在此合同");
            return false;
        }
        boolean result = super.save(contractPurchase);
        contractPurchaseDto.setId(contractPurchase.getId());
        if(result){
            saveOther(contractPurchaseDto);
        }
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param contractPurchaseDto
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(ContractPurchaseDto contractPurchaseDto) {
        ContractPurchase contractPurchase = dozerMapper.map(contractPurchaseDto,ContractPurchase.class);
        boolean result = super.updateById(contractPurchase);
        if(result){
            deleteOther(contractPurchaseDto);
            saveOther(contractPurchaseDto);
        }

        List<ContractPurchaseDto> dtoList=new ArrayList<>();
        ContractPurchaseVo vo =dozerMapper.map(contractPurchaseDto,ContractPurchaseVo.class);
        getOther(vo);
        dtoList.add(dozerMapper.map(vo,ContractPurchaseDto.class));
        mdmMqService.sendRocketMq(purchaseTopics,MdmConstants.CUSTOMER,JSON.toJSONString(dtoList));

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
        ContractPurchase purchase=super.getById(id);
        ContractPurchaseDto dto=dozerMapper.map(purchase,ContractPurchaseDto.class);
        deleteOther(dto);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public Boolean saveTransmitAll(List<ContractPurchaseDto> list) {
        List<String> projectIdList=list.stream().map(ContractPurchaseDto::getProjectId).collect(Collectors.toList());
        //查询项目信息
        LambdaQueryWrapper<Project> projectLambdaQueryWrapper=new LambdaQueryWrapper<>();
        projectLambdaQueryWrapper.in(Project::getDataSourceId,projectIdList);
        List<Project> projects=projectService.list(projectLambdaQueryWrapper);
        List<ContractPurchaseDto> purchaseDtoList=new ArrayList<>();
        for (ContractPurchaseDto dto:list) {
            ContractPurchaseDto purchaseDto = dozerMapper.map(dto,ContractPurchaseDto.class);
            //判断项目是否存在
            List<Project> projectList=projects.stream().filter(pro -> pro.getDataSourceId().equals(dto.getProjectId())).collect(Collectors.toList());Collectors.toList();
            if (CollectionUtils.isNotEmpty(projectList)){
                Project project=projects.get(0);
                purchaseDto.setProjectCode(project.getProjectCode());
                purchaseDto.setProjectId(project.getId().toString());
                purchaseDto.setProjectName(project.getProjectName());
            }
            //判断必填项
            if(StringUtils.isBlank(dto.getDataSourceId())||StringUtils.isBlank(dto.getContractCode())||
                    StringUtils.isBlank(dto.getContractName())){
                continue;
            }
            //执行范围
            if(StringUtils.isNotEmpty(dto.getExecutionscopecodes())){
                List<ContractPurchaseExecution> executions=new ArrayList<>();
                String executionscopecodes=dto.getExecutionscopecodes();
                String executionscopeIds=dto.getExecutionscopeids();
                String executionscopeNames=dto.getExecutionscopenames();
                String[] exeCodes=executionscopecodes.split(",");
                String[] exeIds=executionscopeIds.split(",");
                String[] exeNames=executionscopeNames.split(",");
                for(int i =0;i<exeIds.length;i++){
                    ContractPurchaseExecution purchaseExecution=new ContractPurchaseExecution();
                    purchaseExecution.setContractCode(dto.getContractCode());
                    purchaseExecution.setExecutionScopeCode(dto.getExecutionscopecodes());
                    purchaseExecution.setExecutionScopeId(exeIds[i]);
                    if(i<exeCodes.length){
                        purchaseExecution.setExecutionScopeCode(exeCodes[i]);
                    }if(i<exeNames.length){
                        purchaseExecution.setExecutionScopeName(exeNames[i]);
                    }
                    purchaseDto.setExecutionList(executions);
                }
            }
            purchaseDtoList.add(purchaseDto);
        }
        saveList(purchaseDtoList);
        return true;
    }

    @Override
    public Page getList(ContractPurchaseDto contractPurchaseDto) {
        Result<MdmOrgVo> result=mdmClient.get(Long.valueOf(contractPurchaseDto.getPartyaId()));
        MdmOrgVo mdmOrgVo=result.getData();
        contractPurchaseDto.setPartyaCode(mdmOrgVo.getGepsJcOrgId());
        Page<ContractPurchaseVo> pageVo = contractPurchaseMapper.getPageList(contractPurchaseDto.getPage(),contractPurchaseDto);
                log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 数据保存
     * @param list
     * @return
     */
    @SneakyThrows
    public Boolean saveList(List<ContractPurchaseDto> list) {
        if(CollectionUtils.isNotEmpty(list)){
            //直接删除已有的数据：合同、执行范围、甲乙方、联系人、合同详情
            List<String> dataSourceIdList=list.stream().map(ContractPurchaseDto::getDataSourceId).collect(Collectors.toList());
            LambdaQueryWrapper<ContractPurchase> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(ContractPurchase::getDataSourceId,dataSourceIdList);
            List<ContractPurchase> purchases=super.list(queryWrapper);
            //super.remove(queryWrapper);
            if(CollectionUtils.isNotEmpty(purchases)){
                List<Long> idList=purchases.stream().map(ContractPurchase::getId).collect(Collectors.toList());
                //执行范围删除
                LambdaQueryWrapper<ContractPurchaseExecution> queryWrapperEx = new LambdaQueryWrapper<>();
                queryWrapperEx.in(ContractPurchaseExecution::getContractId,idList);
                contractPurchaseExecutionService.remove(queryWrapperEx);
                //甲方删除
                LambdaQueryWrapper<ContractPartya> queryWrapperPa = new LambdaQueryWrapper<>();
                queryWrapperPa.in(ContractPartya::getContractId,idList);
                partyaService.remove(queryWrapperPa);
                //乙方删除
                LambdaQueryWrapper<ContractPartyb> queryWrapperPb = new LambdaQueryWrapper<>();
                queryWrapperPb.in(ContractPartyb::getContractId,idList);
                partybService.remove(queryWrapperPb);
                //合同详情删除
                LambdaQueryWrapper<ContractPurchaseDetails> queryWrapperDe = new LambdaQueryWrapper<>();
                queryWrapperDe.in(ContractPurchaseDetails::getContractId,idList);
                detailsService.remove(queryWrapperDe);
                //附件删除
                LambdaQueryWrapper<ContractPurchaseFile> queryWrapperfile=new LambdaQueryWrapper<>();
                queryWrapperfile.in(ContractPurchaseFile::getContractId,idList);
                contractPurchaseFileService.remove(queryWrapperfile);

            }

            for (ContractPurchaseDto purchaseDto :list) {
                ContractPurchase purchaseThis = dozerMapper.map(purchaseDto,ContractPurchase.class);
                List<ContractPurchase> purchases1=purchases.stream().filter(pur->pur.getContractCode().equals(purchaseDto.getContractCode())).collect(Collectors.toList());

                if(CollectionUtils.isEmpty(purchases1)){
                    super.save(purchaseThis);
                }else{
                    purchaseThis.setId(purchases1.get(0).getId());
                    super.updateById(purchaseThis);
                }

                List<ContractPartyaDto> partyaList=new ArrayList<>();
                //子表数据填充--甲乙方
                if(StringUtils.isNotEmpty(purchaseDto.getPartybId())){
                    ContractPartyaDto partya=new ContractPartyaDto();
                    partya.setContractCode(purchaseThis.getContractCode());
                    partya.setContractType(ContractType.PURCHASE.getCode());
                    partya.setContractId(purchaseThis.getId());
                    //查询组织机构
                    List<MdmOrgVo> orgVos=mdmClient.getOrgByJcOrgIds(purchaseDto.getPartyaId()).getData();
                    if(CollectionUtils.isNotEmpty(orgVos)){
                        partya.setPartyaId(orgVos.get(0).getId());
                        partya.setPartyaCode(orgVos.get(0).getOrgCode());
                    }else{
                        partya.setPartyaCode(purchaseDto.getPartyaId());
                    }
                    partya.setPartyaName(purchaseDto.getPartyaName());
                    partyaList.add(partya);
                    purchaseDto.setPartyaList(partyaList);
                }

                if(StringUtils.isNotEmpty(purchaseDto.getPartybId())){
                    List<ContractPartybDto> partybList=new ArrayList<>();
                    ContractPartybDto partyb=new ContractPartybDto();
                    partyb.setContractCode(purchaseThis.getContractCode());
                    partyb.setContractType(ContractType.PURCHASE.getCode());
                    partyb.setContractId(purchaseThis.getId());
                    //查询供应商
                    List<Supplier> supplierList=supplierService.list(Wrappers.<Supplier>lambdaQuery().eq(Supplier::getDataSourceId,purchaseDto.getPartybId()));
                    if(CollectionUtils.isNotEmpty(supplierList)){
                        partyb.setPartybId(supplierList.get(0).getId());
                        partyb.setPartybCode(supplierList.get(0).getSupplierCode());
                    }else{
                        partyb.setPartybCode(purchaseDto.getPartybId());
                    }
                    partyb.setPartybName(purchaseDto.getPartybName());
                    partybList.add(partyb);
                    purchaseDto.setPartybList(partybList);
                }

                if(StringUtils.isNotEmpty(purchaseDto.getPartycId())){
                    List<ContractPartycDto> partycList=new ArrayList<>();
                    ContractPartycDto partyc=new ContractPartycDto();
                    partyc.setContractCode(purchaseThis.getContractCode());
                    partyc.setContractType(ContractType.PURCHASE.getCode());
                    partyc.setContractId(purchaseThis.getId());
                    partyc.setPartycCode(purchaseDto.getPartycId());
                    partyc.setPartycName(purchaseDto.getPartycName());
                    partycList.add(partyc);
                    purchaseDto.setPartycList(partycList);
                }

                List<ContractPurchaseExecution>  executions =purchaseDto.getExecutionList();
                if(CollectionUtils.isNotEmpty(executions)){
                    for (ContractPurchaseExecution execution :executions) {
                        execution.setContractId(String.valueOf(purchaseThis.getId()));
                        execution.setCreateTime(LocalDateTime.now());
                        execution.setUpdateTime(LocalDateTime.now());
                    }
                }
                List<ContractPurchaseDetails> details=purchaseDto.getDetailsList();
                if(CollectionUtils.isNotEmpty(details)){
                    for (ContractPurchaseDetails purchaseDetails:details) {
                        purchaseDetails.setContractId(String.valueOf(purchaseThis.getId()));
                        purchaseDetails.setContractCode(purchaseThis.getContractCode());
                        purchaseDetails.setContractName(purchaseThis.getContractName());
                        purchaseDetails.setCreateTime(LocalDateTime.now());
                        purchaseDetails.setUpdateTime(LocalDateTime.now());
                    }
                }
                purchaseDto.setDetailsList(details);

                //附件
                List<ContractPurchaseFileDto> files=purchaseDto.getFileList();
                if(CollectionUtils.isNotEmpty(files)){
                    for (ContractPurchaseFileDto fileDto:files
                    ) {
                        fileDto.setContractId(purchaseThis.getId());
                        fileDto.setContractCode(purchaseThis.getContractCode());
                        fileDto.setContractName(purchaseThis.getContractName());
                        fileDto.setCreateTime(LocalDateTime.now());
                        fileDto.setUpdateTime(LocalDateTime.now());
                    }
                    purchaseDto.setFileList(files);
                }

                //新增子表数据
                saveOther(purchaseDto);
            }
            //mdmMqService.sendRocketMq(purchaseTopics,MdmConstants.CUSTOMER,JSON.toJSONString(list));
        }
        return true;
    }

    public void getOther(ContractPurchaseVo purchaseVo){
        //获取相关数据
        LambdaQueryWrapper<ContractPurchaseExecution> queryWrapperEx = new LambdaQueryWrapper<>();
        queryWrapperEx.eq(ContractPurchaseExecution::getContractCode,purchaseVo.getContractCode());
        List<ContractPurchaseExecution> executions=contractPurchaseExecutionService.list(queryWrapperEx);
        List<ContractPurchaseExecutionVo> executionVos=new ArrayList<>();
        for (ContractPurchaseExecution execution:executions
             ) {
            ContractPurchaseExecutionVo vo=dozerMapper.map(execution,ContractPurchaseExecutionVo.class);
            executionVos.add(vo);
        }
        //清单
        LambdaQueryWrapper<ContractPurchaseDetails> detailsQueryWrapper = new LambdaQueryWrapper<>();
        detailsQueryWrapper.eq(ContractPurchaseDetails::getContractCode,purchaseVo.getContractCode());
        List<ContractPurchaseDetails> details= detailsService.list(detailsQueryWrapper);
        List<ContractPurchaseDetailsVo> detailVoList=new ArrayList<>();
        for (ContractPurchaseDetails detail:details) {
            ContractPurchaseDetailsVo detailsVo=dozerMapper.map(detail,ContractPurchaseDetailsVo.class);
            detailVoList.add(detailsVo);
        }
        //甲乙方
        LambdaQueryWrapper<ContractPartya> partyaWrapper=new LambdaQueryWrapper<>();
        partyaWrapper.eq(ContractPartya::getContractCode,purchaseVo.getContractCode());
        List<ContractPartya> partyaList=partyaService.list(partyaWrapper);
        List<ContractPartyaVo> partyaVoList=new ArrayList<>();

        List<Long> partyaIds=partyaList.stream().map(ContractPartya::getId).collect(Collectors.toList());
        LambdaQueryWrapper<ContractPartyaContact> partyaContactWrapper=new LambdaQueryWrapper<>();
        partyaContactWrapper.in(ContractPartyaContact::getPartyaId,partyaIds);
        List<ContractPartyaContact> partyaContacts=contractPartyaContactService.list(partyaContactWrapper);

        for (ContractPartya partya:partyaList) {
            List<ContractPartyaContact> contacts=partyaContacts.stream().filter(con -> con.getPartyaId().equals(partya.getId())).collect(Collectors.toList());
            ContractPartyaVo partyaVo=dozerMapper.map(partya,ContractPartyaVo.class);
            partyaVo.setPartyaContactList(DozerUtils.mapList(dozerMapper,contacts,ContractPartyaContactVo.class));
            partyaVoList.add(partyaVo);
        }

        LambdaQueryWrapper<ContractPartyb> partybWrapper=new LambdaQueryWrapper<>();
        partybWrapper.eq(ContractPartyb::getContractCode,purchaseVo.getContractCode());
        List<ContractPartyb> partybList=partybService.list(partybWrapper);
        List<ContractPartybVo> partybVoList=new ArrayList<>();

        List<Long> partybIds=partybList.stream().map(ContractPartyb::getId).collect(Collectors.toList());
        LambdaQueryWrapper<ContractPartybContact> partybContactWrapper=new LambdaQueryWrapper<>();
        partybContactWrapper.in(ContractPartybContact::getPartybId,partybIds);
        List<ContractPartybContact> partybContacts=contractPartybContactService.list(partybContactWrapper);

        for (ContractPartyb partyb:partybList) {
            List<ContractPartybContact> contacts=partybContacts.stream().filter(con -> con.getPartybId().equals(partyb.getId())).collect(Collectors.toList());
            ContractPartybVo partybVo=dozerMapper.map(partyb,ContractPartybVo.class);
            partybVo.setPartybContactList(DozerUtils.mapList(dozerMapper,contacts,ContractPartybContactVo.class));
            partybVoList.add(partybVo);
        }
        LambdaQueryWrapper<ContractPartyc> partycWrapper=new LambdaQueryWrapper<>();
        partycWrapper.eq(ContractPartyc::getContractCode,purchaseVo.getContractCode())
                .eq(ContractPartyc::getContractType, ContractType.PURCHASE.getCode());
        List<ContractPartyc> partycList=partycService.list(partycWrapper);
        List<ContractPartycVo> partycVoList=new ArrayList<>();

        List<Long> partycIds=partycList.stream().map(ContractPartyc::getId).collect(Collectors.toList());
        List<ContractPartycContact> partycContacts=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(partycIds)){
            LambdaQueryWrapper<ContractPartycContact> partycContactWrapper=new LambdaQueryWrapper<>();
            partycContactWrapper.in(ContractPartycContact::getPartycId,partycIds);
            partycContacts=contractPartycContactService.list(partycContactWrapper);
        }

        for (ContractPartyc partyc:partycList) {
            List<ContractPartycContact> contacts=partycContacts.stream().filter(con -> con.getPartycId().equals(partyc.getId())).collect(Collectors.toList());
            ContractPartycVo partycVo=dozerMapper.map(partyc,ContractPartycVo.class);
            partycVo.setPartycContactList(DozerUtils.mapList(dozerMapper,contacts,ContractPartycContactVo.class));
            partycVoList.add(partycVo);
        }
        purchaseVo.setPartycList(partycVoList);
        purchaseVo.setExecutionList(executionVos);
        purchaseVo.setDetailsList(detailVoList);
        purchaseVo.setPartyaList(partyaVoList);
        purchaseVo.setPartybList(partybVoList);
    }

    public void deleteOther(ContractPurchaseDto purchaseDto){
        //执行范围删除
        LambdaQueryWrapper<ContractPurchaseExecution> queryWrapperEx = new LambdaQueryWrapper<>();
        queryWrapperEx.eq(ContractPurchaseExecution::getContractCode,purchaseDto.getContractCode());
        contractPurchaseExecutionService.remove(queryWrapperEx);
        //清单删除
        LambdaQueryWrapper<ContractPurchaseDetails> detailsLambdaQueryWrapper=new LambdaQueryWrapper<>();
        detailsLambdaQueryWrapper.eq(ContractPurchaseDetails::getContractCode,purchaseDto.getContractCode());
        detailsService.remove(detailsLambdaQueryWrapper);
        //甲方删除
        LambdaQueryWrapper<ContractPartya> queryWrapperPa = new LambdaQueryWrapper<>();
        queryWrapperPa.eq(ContractPartya::getContractCode,purchaseDto.getContractCode())
                .eq(ContractPartya::getContractType, ContractType.PURCHASE.getCode());
        List<ContractPartya> partyaList=partyaService.list(queryWrapperPa);
        if(CollectionUtils.isNotEmpty(partyaList)){
            List<Long> partyaIds=partyaList.stream().map(ContractPartya::getId).collect(Collectors.toList());
            LambdaQueryWrapper<ContractPartyaContact> queryWrapperPaCon=new LambdaQueryWrapper<>();
            queryWrapperPaCon.in(ContractPartyaContact::getPartyaId,partyaIds);
            contractPartyaContactService.remove(queryWrapperPaCon);
            partyaService.remove(queryWrapperPa);
        }
        //乙方删除
        LambdaQueryWrapper<ContractPartyb> queryWrapperPb = new LambdaQueryWrapper<>();
        queryWrapperPb.eq(ContractPartyb::getContractCode,purchaseDto.getContractCode())
                .eq(ContractPartyb::getContractType, ContractType.PURCHASE.getCode());
        List<ContractPartyb> partybList=partybService.list(queryWrapperPb);
        if(CollectionUtils.isNotEmpty(partybList)){
            List<Long> partybIds=partybList.stream().map(ContractPartyb::getId).collect(Collectors.toList());
            LambdaQueryWrapper<ContractPartybContact> queryWrapperPbCon=new LambdaQueryWrapper<>();
            queryWrapperPbCon.in(ContractPartybContact::getPartybId,partybIds);
            contractPartybContactService.remove(queryWrapperPbCon);
            partybService.remove(queryWrapperPb);
        }
        //丙方删除
        LambdaQueryWrapper<ContractPartyc> queryWrapperPc = new LambdaQueryWrapper<>();
        queryWrapperPc.eq(ContractPartyc::getContractCode,purchaseDto.getContractCode())
                .eq(ContractPartyc::getContractType, ContractType.PURCHASE.getCode());
        List<ContractPartyc> partycList=partycService.list(queryWrapperPc);
        if(CollectionUtils.isNotEmpty(partycList)){
            List<Long> partycIds=partycList.stream().map(ContractPartyc::getId).collect(Collectors.toList());
            LambdaQueryWrapper<ContractPartycContact> queryWrapperPcCon=new LambdaQueryWrapper<>();
            queryWrapperPcCon.in(ContractPartycContact::getPartycId,partycIds);
            contractPartycContactService.remove(queryWrapperPcCon);
            partycService.remove(queryWrapperPc);
        }
    }

    public void saveOther(ContractPurchaseDto purchaseDto){
        //执行范围
        List<ContractPurchaseExecution> executions=purchaseDto.getExecutionList();
        if(CollectionUtils.isNotEmpty(executions)){
            contractPurchaseExecutionService.saveBatch(executions);
        }

        //清单
        List<ContractPurchaseDetails> details=purchaseDto.getDetailsList();
        if(CollectionUtils.isNotEmpty(details)){
            detailsService.saveBatch(details);
        }

        //甲方
        List<ContractPartyaDto> partyaList=purchaseDto.getPartyaList();
        if(CollectionUtils.isNotEmpty(partyaList)){
            for (ContractPartyaDto dto:partyaList) {
                ContractPartya partya=dozerMapper.map(dto,ContractPartya.class);
                partyaService.save(partya);
                List<ContractPartyaContactDto> contactDtos=dto.getPartyaContactList();
                if(CollectionUtils.isNotEmpty(contactDtos)){
                    for (ContractPartyaContactDto contactDto:contactDtos
                    ) {
                        contactDto.setPartyaId(partya.getId());
                    }
                    List<ContractPartyaContact> partyaContacts=DozerUtils.mapList(dozerMapper,contactDtos,ContractPartyaContact.class);
                    contractPartyaContactService.saveBatch(partyaContacts);
                }
            }
        }

        //乙方
        List<ContractPartybDto> partybList=purchaseDto.getPartybList();
        if(CollectionUtils.isNotEmpty(partybList)){
            for (ContractPartybDto dto:partybList) {
                ContractPartyb partyb=dozerMapper.map(dto,ContractPartyb.class);
                partybService.save(partyb);
                List<ContractPartybContactDto> contactDtos=dto.getPartybContactList();
                if(CollectionUtils.isNotEmpty(contactDtos)){
                    for (ContractPartybContactDto contactDto:contactDtos) {
                        contactDto.setPartybId(partyb.getId());
                    }
                    List<ContractPartybContact> partybContacts=DozerUtils.mapList(dozerMapper,contactDtos,ContractPartybContact.class);
                    contractPartybContactService.saveBatch(partybContacts);
                }
            }
        }

        //丙方
        List<ContractPartycDto> partycDtos=purchaseDto.getPartycList();
        if(CollectionUtils.isNotEmpty(partycDtos)){
            for (ContractPartycDto dto:partycDtos) {
                ContractPartyc partyc=dozerMapper.map(dto,ContractPartyc.class);
                partycService.save(partyc);
                List<ContractPartycContactDto> contactDtos=dto.getPartycContactList();
                if(CollectionUtils.isNotEmpty(contactDtos)){
                    for (ContractPartycContactDto contactDto:contactDtos) {
                        contactDto.setPartycId(partyc.getId());
                    }
                    List<ContractPartycContact> partycContacts=DozerUtils.mapList(dozerMapper,contactDtos,ContractPartycContact.class);
                    contractPartycContactService.saveBatch(partycContacts);
                }
            }
            List<ContractPartyc> partycList=DozerUtils.mapList(dozerMapper,partycDtos,ContractPartyc.class);
            partycService.saveBatch(partycList);
        }

        List<ContractPurchaseFileDto> fileDtos=purchaseDto.getFileList();
        if(CollectionUtils.isNotEmpty(fileDtos)){
            List<ContractPurchaseFile> files=DozerUtils.mapList(dozerMapper,fileDtos,ContractPurchaseFile.class);
            contractPurchaseFileService.saveBatch(files);
        }
    }
}
