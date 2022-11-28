package com.netwisd.biz.mdm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import com.netwisd.biz.mdm.dto.*;
import com.netwisd.biz.mdm.entity.*;
import com.netwisd.biz.mdm.fegin.MdmClient;
import com.netwisd.biz.mdm.service.*;
import com.netwisd.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("syncGldService")
public class SyncGldServiceImpl implements SyncGldService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierContactsService supplierContactsService;
    @Autowired
    private ContractPurchaseService contractPurchaseService;

    @Autowired
    private ProjectjcService projectjcService;
    @Autowired
    private MdmClient mdmClient;

    @Value("${spring.gldInfo.url.supplierUrl}")
    private String supplierUrl;

    @Value("${spring.gldInfo.url.contractUrl}")
    private String contractUrl;

    @Value("${spring.gldInfo.url.conMaterialUrl}")
    private String conMaterialUrl;

    @Value("${spring.gldInfo.url.orgnizationsUrl}")
    private String orgnizationsUrl;

    @Value("${spring.gldInfo.url.redirectUri}")
    private String redirectUri;

    @Value("${spring.gldInfo.url.projectUrl}")
    private String projectUrl;
    @Autowired
    private ProjectService projectService;
    private static String token=null;


    @Override
    public void syncGldSupplier() throws java.io.IOException, ParseException{
        token=MdmApiServiceImpl.token;
        if(StringUtils.isEmpty(token)) {
            getAuthorize();
        }
        token=MdmApiServiceImpl.token;
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1,0,0,0);
        long timestamp =calendar.getTime().getTime()/1000;

        String url = supplierUrl+token+"&timestamp="+timestamp;
        HttpClient client =  HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        HashMap<String,Object> map = JSON.parseObject(result, HashMap.class);
        String body=String.valueOf(map.get("body"));
        HashMap<String,Object> bodyMap=JSON.parseObject(body, HashMap.class);
        String suppliers=String.valueOf(bodyMap.get("suppliers"));
        //System.out.println(suppliers);
        if(StringUtils.isNotEmpty(suppliers)){
            List<SupplierTransmit> supplierTransmitList=JSON.parseArray(suppliers,SupplierTransmit.class);
            for (SupplierTransmit transmit:supplierTransmitList) {
                Supplier supplier=transmit.toSupplier();
                SupplierContacts contacts=transmit.toSupplierContacts();
                supplierService.saveOrUpdate(supplier, Wrappers.<Supplier>lambdaQuery().eq(Supplier::getDataSourceId,transmit.getSupplierid()));
                contacts.setSupplierId(supplier.getId());
                LambdaQueryWrapper<SupplierContacts> lambdaQueryWrapper=new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(SupplierContacts::getSupplierCode,supplier.getSupplierCode());
                supplierContactsService.remove(lambdaQueryWrapper);
                supplierContactsService.save(contacts);
            }
        }
    }


    @Async
    public void syncGldPurchase() throws IOException , java.text.ParseException{

        log.debug("=============组织-------采购合同数据开始执行===========");
        String token=MdmApiServiceImpl.token;
        if(StringUtils.isEmpty(token)) {
            getAuthorize();
        }
        token=MdmApiServiceImpl.token;
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-5,0,0,0);
        long timestamp =calendar.getTime().getTime()/1000;

        String orgUrl=orgnizationsUrl +token+"&orgid=8382818622c6423491c3ebd2c60b118a&timestamp="+timestamp;
        HttpClient orgClient =  HttpClientBuilder.create().build();
        HttpGet orghttpGet=new HttpGet(orgUrl);
        HttpResponse orgResponse = orgClient.execute(orghttpGet);
        String orgResult = EntityUtils.toString(orgResponse.getEntity());
        HashMap<String,Object> orgMap = JSON.parseObject(orgResult, HashMap.class);
        String orgBody=String.valueOf(orgMap.get("body"));
        HashMap<String,Object> orgBodyMap=JSON.parseObject(orgBody, HashMap.class);
        String orgnizations=String.valueOf(orgBodyMap.get("orgnizations"));
        List<OrgTransmit> orgTransmits=JSON.parseArray(orgnizations,OrgTransmit.class);
        if(StringUtils.isNotEmpty(orgnizations)){
            List<String> orgTransmitIds=orgTransmits.stream().map(OrgTransmit::getOrgnizationid).collect(Collectors.toList());
            String ids = String.join(",", orgTransmitIds);
            Result<List<MdmOrgVo>> result=mdmClient.getOrgByJcOrgIds(ids);
            List<MdmOrgVo> orgVos=result.getData();
            for (OrgTransmit org:orgTransmits) {
                List<MdmOrgVo> mdmOrgVos= orgVos.stream().filter(o -> o.getGepsJcOrgId().equals(org.getOrgnizationid())).collect(Collectors.toList());
                MdmOrgVo mdmOrgVo=new MdmOrgVo();
                if(CollectionUtils.isNotEmpty(mdmOrgVos)){
                    mdmOrgVo=mdmOrgVos.get(0);
                }
                getPurchaseList(org.getOrgnizationid(),mdmOrgVo);
            }

        }

    }

    @Override
    public void syncGldProject() throws IOException, ParseException {
        String token=MdmApiServiceImpl.token;
        if(StringUtils.isEmpty(token)) {
            getAuthorize();
        }
        token=MdmApiServiceImpl.token;
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-5,0,0,0);
        long timestamp =calendar.getTime().getTime()/1000;
        String url = projectUrl + token + "&orgid==8382818622c6423491c3ebd2c60b118a&timestamp="+timestamp;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        HashMap<String, Object> map = JSON.parseObject(result, HashMap.class);
        String body = String.valueOf(map.get("body"));
        HashMap<String, Object> bodyMap = JSON.parseObject(body, HashMap.class);
        String projects=String.valueOf(bodyMap.get("projects"));
        if(StringUtils.isNotEmpty(projects)){
            List<ProjectTransmit> transmitList=JSON.parseArray(projects,ProjectTransmit.class);
            List<Projectjc> projectjcs=new ArrayList<>();
            for (ProjectTransmit transmit:transmitList) {
                Projectjc projectjc=transmit.toProjectjc();
                projectjcs.add(projectjc);
            }
            projectjcService.saveBatch(projectjcs);
        }
    }

    public void getPurchaseList(String orgid,MdmOrgVo mdmOrgVo) throws IOException , java.text.ParseException{

        log.debug("=============采购合同数据定时器开始执行===========");
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1,0,0,0);
        long timestamp =calendar.getTime().getTime()/1000;

        String url = contractUrl+token+"&orgid="+orgid +"&timestamp="+timestamp;
        HttpClient client =  HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        HashMap<String,Object> map = JSON.parseObject(result, HashMap.class);
        String body=String.valueOf(map.get("body"));
        HashMap<String,Object> bodyMap=JSON.parseObject(body, HashMap.class);
        String contracts=String.valueOf(bodyMap.get("contracts"));
        if(StringUtils.isNotEmpty(contracts)){
            List<ContractPurchaseDto> list= new ArrayList<>();
            List<ContractPurchaseTransmit> contractTransmitList=JSON.parseArray(contracts,ContractPurchaseTransmit.class);
            List<Project> projectList= projectService.list();
            List<Projectjc> projectjcs=projectjcService.list();
            for (ContractPurchaseTransmit transmit:contractTransmitList) {
                ContractPurchaseDto dto=transmit.toContractPurchaseDto();
                if(ObjectUtils.isNotEmpty(mdmOrgVo.getId())){
                    dto.setOrgId(mdmOrgVo.getId().toString());
                    dto.setOrgName(mdmOrgVo.getOrgName());
                    dto.setOrgCode(mdmOrgVo.getOrgCode());
                }//查找项目编码
                if(StringUtils.isNotEmpty(transmit.getProjectid())){
                    Optional<Projectjc> projectjcResult = projectjcs.stream().filter(pro -> pro.getProjectId().equals(transmit.getProjectid())).findFirst();
                    if(projectjcResult != null && !projectjcResult.isEmpty()){
                        Projectjc thisProjectjc=projectjcResult.get();
                        dto.setProjectCode(thisProjectjc.getProjectCode());
                        Optional<Project> projectResult = projectList.stream().filter(pro -> pro.getProjectCode().equals(thisProjectjc.getProjectCode())).findFirst();
                        if(projectResult != null && !projectResult.isEmpty()){
                            Project project= projectResult.get();
                            dto.setProjectId(project.getId().toString());
                        }
                    }
                }
                getPurchaseDetailList(dto);
                String attachments=transmit.getAttachments();
                if(StringUtils.isNotEmpty(attachments)){
                    List<ContractPurchaseTransmit> fileList= JSON.parseArray(attachments,ContractPurchaseTransmit.class);
                    if(CollectionUtils.isNotEmpty(fileList)){
                        List<ContractPurchaseFileDto> fileDtos=new ArrayList<>();
                        for (ContractPurchaseTransmit fileTransmit:fileList) {
                            ContractPurchaseFileDto fileDto=new ContractPurchaseFileDto();
                            fileDto.setFileUrl(fileTransmit.getFileurl());
                            fileDto.setFileName(fileTransmit.getFilename());
                            fileDtos.add(fileDto);
                        }
                        dto.setFileList(fileDtos);
                    }
                }
                list.add(dto);
            }
            contractPurchaseService.saveTransmitAll(list);

        }
    }

    public List<ContractPurchaseDetails> getPurchaseDetailList(ContractPurchaseDto dto) throws IOException, ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1,0,0,0);
        long timestamp =calendar.getTime().getTime()/1000;
        String url = conMaterialUrl+token+"&contractid="+dto.getDataSourceId()+"&timestamp="+timestamp;
        HttpClient client =  HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        HashMap<String,Object> map = JSON.parseObject(result, HashMap.class);
        String body=String.valueOf(map.get("body"));
        HashMap<String,Object> bodyMap=JSON.parseObject(body, HashMap.class);
        String materials=String.valueOf(bodyMap.get("materials"));
        List<ContractPurchaseDetails> details=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(dto.getDetailsList())){
            details=dto.getDetailsList();
        }
        if(StringUtils.isNotEmpty(materials)){
            List<ContractPurchaseDetailTransmit> list=JSON.parseArray(materials,ContractPurchaseDetailTransmit.class);
            for (ContractPurchaseDetailTransmit transmit:list) {
                ContractPurchaseDetailsDto detailsDto=transmit.toPurchaseDetailDto();
                detailsDto.setContractCode(dto.getContractCode());
                details.add(dozerMapper.map(detailsDto, ContractPurchaseDetails.class));
            }
            dto.setDetailsList(details);
        }
        return details;
    }

    public void getAuthorize()throws java.io.IOException {
        OutputStreamWriter out = null;
        String url ="http://inter.cnecc.com/api/oauth2/authorize?client_id=100004&client_secret=1ea85063355fbfad3de73ab038261d62" +
                "&response_type=code&redirect_uri="+redirectUri;
        URL restUrl=new URL(url);
        HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();
        conn.setRequestMethod("GET");
        //设置通用的请求属性
        conn.setRequestProperty("test-header","get-header-value");
        //获取内容
        InputStream inputStream = conn.getInputStream();
    }

}
