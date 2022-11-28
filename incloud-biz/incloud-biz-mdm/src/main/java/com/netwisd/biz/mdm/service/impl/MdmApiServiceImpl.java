package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
import springfox.documentation.spring.web.json.Json;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Wrapper;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:54:49
 */
@Service
@Slf4j
public class MdmApiServiceImpl implements MdmApiService {
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
    private ProjectService projectService;

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

    public static String token=null;

    @Override
    public void getToken(String code, String state, String accessToken, String expireIn)throws java.io.IOException  {

        if(StringUtils.isNotEmpty(code)){
            OutputStreamWriter out = null;
            String url ="http://inter.cnecc.com/api/oauth2/access_token";

            JSONObject obj = new JSONObject();
            obj.put("grant_type", "authorization_code");
            obj.put("client_id", "100004");
            obj.put("client_secret", "1ea85063355fbfad3de73ab038261d62");
            obj.put("redirect_uri", redirectUri);
            obj.put("code", code);

            URL restUrl=new URL(url);
            HttpURLConnection conn = (HttpURLConnection) restUrl.openConnection();
            conn.setRequestMethod("POST");
            //conn.setRequestMethod("GET");

            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

            //发送请求参数即数据
            out.write(obj.toString());
            //flush输出流的缓冲
            out.flush();
            conn.connect();
            BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while((line = reader.readLine()) !=null){
                buffer.append(line);
            }
            reader.close();
            conn.disconnect();
            HashMap<String,Object> map = JSON.parseObject(buffer.toString(), HashMap.class);
            String body=String.valueOf(map.get("body"));
            HashMap<String,String> bodyMap = JSON.parseObject(map.get("body").toString(), HashMap.class);
            token=bodyMap.get("access_token");
        }
        System.out.println("token==========================="+token);
    }

    public void getAuthorize()throws java.io.IOException {
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

    public void getProjectList(int pagenum,int pagesize,String orgid) throws IOException {
        if (StringUtils.isEmpty(token)) {
            getAuthorize();
        }
        String url = projectUrl + token + "&pagenum=" + pagenum + "&pagesize=" + pagesize+"&orgid="+orgid;
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
            if(transmitList.size()>=pagesize){
                pagenum++;
                getProjectList(pagenum, pagesize,orgid);
            }
        }
    }


    @Override
    public void getSupplierList(int pagenum,int pagesize) throws IOException, ParseException {
        if(StringUtils.isEmpty(token)){
            getAuthorize();
        }
        String url = supplierUrl+token+"&pagenum="+pagenum+"&pagesize="+pagesize;
        HttpClient client =  HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        HashMap<String,Object> map = JSON.parseObject(result, HashMap.class);
        String body=String.valueOf(map.get("body"));
        HashMap<String,Object> bodyMap=JSON.parseObject(body, HashMap.class);
        String suppliers=String.valueOf(bodyMap.get("suppliers"));
        if(StringUtils.isNotEmpty(suppliers)){
            List<SupplierTransmit> supplierTransmitList=JSON.parseArray(suppliers,SupplierTransmit.class);
            for (SupplierTransmit transmit:supplierTransmitList) {
                Supplier supplier=transmit.toSupplier();
                SupplierContacts contacts=transmit.toSupplierContacts();
                List<Supplier> list=supplierService.list(Wrappers.<Supplier>lambdaQuery().eq(Supplier::getDataSourceId,transmit.getSupplierid()));
                if(CollectionUtils.isNotEmpty(list)){
                    Supplier old = list.get(0);
                    supplier.setId(old.getId());
                    supplierService.updateById(supplier);
                }else
                    supplierService.save(supplier);
                //supplierService.saveOrUpdate(supplier, Wrappers.<Supplier>lambdaQuery().eq(Supplier::getDataSourceId,transmit.getSupplierid()));
                contacts.setSupplierId(supplier.getId());
                LambdaQueryWrapper<SupplierContacts> lambdaQueryWrapper=new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(SupplierContacts::getSupplierCode,supplier.getSupplierCode());
                supplierContactsService.remove(lambdaQueryWrapper);
                supplierContactsService.save(contacts);
            }
            if(supplierTransmitList.size()>=pagesize){
                pagenum++;
                getSupplierList(pagenum, pagesize);
            }
        }
    }

    @Async
    public void getPurchaseListByOrgid(int pagenum,int pagesize,String orgid) throws IOException , java.text.ParseException{
        if(StringUtils.isEmpty(token)){
            getAuthorize();
        }
        String orgUrl=orgnizationsUrl +token+"&orgid="+orgid+"&pagenum="+pagenum+"&pagesize="+pagesize;
        HttpClient orgClient =  HttpClientBuilder.create().build();
        HttpGet orghttpGet=new HttpGet(orgUrl);
        HttpResponse orgResponse = orgClient.execute(orghttpGet);
        String orgResult = EntityUtils.toString(orgResponse.getEntity());
        HashMap<String,Object> orgMap = JSON.parseObject(orgResult, HashMap.class);
        String orgBody=String.valueOf(orgMap.get("body"));
        HashMap<String,Object> orgBodyMap=JSON.parseObject(orgBody, HashMap.class);
        String orgnizations=String.valueOf(orgBodyMap.get("orgnizations"));
        if(StringUtils.isNotEmpty(orgnizations)){
            List<OrgTransmit> orgTransmits=JSON.parseArray(orgnizations,OrgTransmit.class);
            List<String> orgTransmitIds=orgTransmits.stream().map(OrgTransmit::getOrgnizationid).collect(Collectors.toList());
            String ids = String.join(",", orgTransmitIds);
            Result<List<MdmOrgVo>> result=mdmClient.getOrgByJcOrgIds(ids);
            List<MdmOrgVo> orgVos=result.getData();
            int contractPagenum=1;
            for (OrgTransmit org:orgTransmits) {
                List<MdmOrgVo> mdmOrgVos= orgVos.stream().filter(o -> o.getGepsJcOrgId().equals(org.getOrgnizationid())).collect(Collectors.toList());
                MdmOrgVo mdmOrgVo=new MdmOrgVo();
                if(CollectionUtils.isNotEmpty(mdmOrgVos)){
                    mdmOrgVo=mdmOrgVos.get(0);
                }
                getPurchaseList( contractPagenum, pagesize, org.getOrgnizationid(),mdmOrgVo);
                contractPagenum++;
            }
            if(orgTransmits.size()>=pagesize){
                pagenum++;
                getPurchaseListByOrgid( pagenum, pagesize, orgid);
            }
        }

    }

    public void getPurchaseList(int pagenum,int pagesize,String orgid,MdmOrgVo mdmOrgVo) throws IOException , java.text.ParseException{
        String url = contractUrl+token+"&orgid="+orgid +"&pagenum="+pagenum+"&pagesize="+pagesize;
        HttpClient client =  HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        HashMap<String,Object> map = JSON.parseObject(result, HashMap.class);
        String body=String.valueOf(map.get("body"));
        HashMap<String,Object> bodyMap=JSON.parseObject(body, HashMap.class);
        String contracts=String.valueOf(bodyMap.get("contracts"));
        List<ContractPurchaseDto> list= new ArrayList<>();
        if(StringUtils.isNotEmpty(contracts)){
            List<ContractPurchaseTransmit> contractTransmitList=JSON.parseArray(contracts,ContractPurchaseTransmit.class);
            List<Project> projectList= projectService.list();
            List<Projectjc> projectjcs=projectjcService.list();
            int detailPagenum=1;
            for (ContractPurchaseTransmit transmit:contractTransmitList) {
                ContractPurchaseDto dto=transmit.toContractPurchaseDto();
                if(ObjectUtils.isNotEmpty(mdmOrgVo)){
                    dto.setOrgId(mdmOrgVo.getId().toString());
                    dto.setOrgName(mdmOrgVo.getOrgName());
                    dto.setOrgCode(mdmOrgVo.getOrgCode());
                }
                //查找项目编码
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
                getPurchaseDetailList(detailPagenum,pagesize,dto,projectList,projectjcs);
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
            if(contractTransmitList.size()>=pagesize){
                pagenum++;
                getPurchaseList( pagenum, pagesize, orgid,mdmOrgVo);
            }
        }
    }

    public List<ContractPurchaseDetails> getPurchaseDetailList(int pagenum,int pagesize,ContractPurchaseDto dto,List<Project> projectList,List<Projectjc> projectjcs) throws IOException, ParseException {
        String url = conMaterialUrl+token+"&contractid="+dto.getDataSourceId()+"&pagenum="+pagenum+"&pagesize="+pagesize;
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
            List<String> orgTransmitIds=list.stream().map(ContractPurchaseDetailTransmit::getOrgnizationid).collect(Collectors.toList());
            String ids = String.join(",", orgTransmitIds);
            //List<MdmOrgVo> orgVos=mdmClient.getOrgByJcOrgIds(ids);
            for (ContractPurchaseDetailTransmit transmit:list) {
                ContractPurchaseDetailsDto detailsDto=transmit.toPurchaseDetailDto();
                detailsDto.setContractCode(dto.getContractCode());

                //组织
               /* List<MdmOrgVo> mdmOrgVos= orgVos.stream().filter(o -> o.getGepsJcOrgId().equals(detailsDto.getSourceOrgId())).collect(Collectors.toList());
                MdmOrgVo mdmOrgVo= new MdmOrgVo();
                if(CollectionUtils.isNotEmpty(mdmOrgVos)){
                    mdmOrgVo=mdmOrgVos.get(0);
                    dto.setOrgCode(mdmOrgVo.getOrgCode());
                    dto.setOrgId(String.valueOf(mdmOrgVo.getId()));
                }*/

                //项目
                if(StringUtils.isNotEmpty(detailsDto.getSourceProjectId())){
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
                details.add(dozerMapper.map(detailsDto, ContractPurchaseDetails.class));
            }
            dto.setDetailsList(details);
            if(list.size()>=pagesize){
                pagenum++;
                getPurchaseDetailList( pagenum, pagesize, dto,projectList,projectjcs);
            }
        }

        return details;
    }

}
