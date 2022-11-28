package com.netwisd.base.mdm.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.AesUtil;
import com.netwisd.base.mdm.dto.*;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SyncNcServiceImpl implements SyncNcService {

    @Autowired
    HttpClient httpClient;

    @Autowired
    MdmOrgService mdmOrgService;

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmPostService mdmPostService;

    @Autowired
    private MdmUserService mdmUserService;

    @Autowired
    private MdmPostUserService mdmPostUserService;

    @Autowired
    private MdmCommDictService mdmCommDictService;

    //nc 机构
    @Value("${ncInfo.url.orgUrl}")
    private String ncOrgUrl;
    //nc 部门
    @Value("${ncInfo.url.deptUrl}")
    private String ncDeptUrl;
    //nc 人员
    @Value("${ncInfo.url.psnUrl}")
    private String ncPsnUrl;

    //股份 用户
    @Value("${ncInfo.headers.clientid}")
    private String ncClientid;
    //股份 密码
    @Value("${ncInfo.headers.clientsecret}")
    private String ncClientsecret;

    @Override
    public boolean getNcOrgDatas() {
        log.info("ncOrgUrl:{}",ncOrgUrl);
        if(StringUtils.isBlank(ncOrgUrl))  {
            throw new IncloudException("没有获取到NC机构URL.");
        }
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(ncOrgUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //拼接参数
            NcDto ncDto = new NcDto();
            ncDto.setClientid(ncClientid);
            ncDto.setOrgcode("109011501");
            ncDto.setTime(System.currentTimeMillis());
            ncDto.setName("");
            ncDto.setYear("2021");
            String allStr = ncClientid + ncDto.getOrgcode()+ncDto.getYear()+ncDto.getTime()+ncClientsecret;
            ncDto.setSign(AesUtil.getMD5String(allStr));
            JSONObject json = (JSONObject)JSONObject.toJSON(ncDto);
            log.debug("请求nc组织信息,参数为：" + json.toJSONString());
            StringEntity entity = new StringEntity(json.toJSONString(), "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                System.out.println(resString);
                log.info("result！" + resString);
                log.info("同步NC组织数据完成！");
            }else {
                log.info("同步NC组织数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步NC机构数据失败！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步NC组织数据失败!");
        }finally {

        }
    }

    @Override
    public boolean getNcDeptDatas() {
        log.info("ncDeptUrl:{}",ncDeptUrl);
        if(StringUtils.isBlank(ncDeptUrl))  {
            throw new IncloudException("没有获取到NC部门URL.");
        }
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(ncDeptUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //拼接参数
            NcDto ncDto = new NcDto();
            ncDto.setClientid(ncClientid);
            ncDto.setOrgcode("109011501");
            ncDto.setTime(System.currentTimeMillis());
            ncDto.setName("");
            ncDto.setYear("2021");
            String allStr = ncClientid + ncDto.getOrgcode()+ncDto.getYear()+ncDto.getTime()+ncClientsecret;
            ncDto.setSign(AesUtil.getMD5String(allStr));
            JSONObject json = (JSONObject)JSONObject.toJSON(ncDto);
            log.debug("请求nc部门信息,参数为：" + json.toJSONString());
            StringEntity entity = new StringEntity(json.toJSONString(), "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                System.out.println(resString);
                log.info("result！" + resString);
                log.info("同步NC部门数据完成！");
            }else {
                log.info("同步NC部门数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步NC部门数据失败！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步NC部门数据失败!");
        }finally {

        }
    }

    @Override
    public boolean getNcPsnDatas() {
        log.info("ncPsnUrl:{}",ncPsnUrl);
        if(StringUtils.isBlank(ncPsnUrl))  {
            throw new IncloudException("没有获取到NC人员URL.");
        }
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(ncPsnUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //拼接参数
            NcDto ncDto = new NcDto();
            ncDto.setClientid(ncClientid);
            ncDto.setOrgcode("109011501");
            ncDto.setTime(System.currentTimeMillis());
            ncDto.setName("");
            ncDto.setYear("2021");
            String [] pks = {"1004A5100000000150UX","1004A5100000000150VC"};
            ncDto.setPks(pks);
            String allStr = ncClientid + ncDto.getOrgcode()+ncDto.getYear()+ncDto.getTime()+ncClientsecret;
            ncDto.setSign(AesUtil.getMD5String(allStr));
            JSONObject json = (JSONObject)JSONObject.toJSON(ncDto);
            log.debug("请求nc人员信息,参数为：" + json.toJSONString());
            StringEntity entity = new StringEntity(json.toJSONString(), "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                System.out.println(resString);
                log.info("result！" + resString);
                log.info("同步NC人员数据完成！");
            }else {
                log.info("同步NC人员数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步NC人员数据失败！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步NC人员数据失败!");
        }finally {

        }
    }
}
