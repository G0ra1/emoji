package com.netwisd.base.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.mapper.PortalContentTodoTasksMapper;
import com.netwisd.base.portal.service.PortalApiService;
import com.netwisd.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PortalApiServiceImpl implements PortalApiService {

    @Value("${portal.gpesTaskCountUrl}")
    private String getGpesTaskCountUrl;

    @Autowired
    private PortalContentTodoTasksMapper todoTasksMapper;

    /**
     * 获取geps待办数量
     * @return
     */
    @Override
    public Map<String, Integer> getGepsCount() {
        Map<String, Integer> returnMap = new HashMap<>();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        System.out.println("当前人员的idCard:" + loginAppUser.getIdCard());

        try {
            log.info("--------调用webservice接口begin-------");
            // 创建动态客户端
            String s = doPostSoap1_1(getGpesTaskCountUrl+"?op=GetUserTaskCountByIdCard", "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <GetUserTaskCountByIdCard xmlns=\"http://tempuri.org/\">\n" +
                    "      <userIdCardNumber>" + loginAppUser.getIdCard() + "</userIdCardNumber>\n" +
                    "    </GetUserTaskCountByIdCard>\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>", "");

            Map<String, String> map = new HashMap<>();
            map = parseXml2Map(s, map);
            String msg = map.get("Body.GetUserTaskCountByIdCardResponse.GetUserTaskCountByIdCardResult");
            System.out.println("--------解析返回数据xml，获得的内容：" + msg);
            JSONObject jsonObject = JSONObject.parseObject(msg);
            int todoTaskCount = (int) jsonObject.get("todoTaskCount");
            System.out.println("--------todoTaskCount:" + todoTaskCount);
            int todoReadTaskCount = (int) jsonObject.get("todoReadTaskCount");
            System.out.println("--------todoReadTaskCount" + todoReadTaskCount);

            returnMap.put("taskCount", todoTaskCount);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("服务器断开连接，请稍后再试");
            returnMap.put("taskCount", 0);
            return returnMap;
        }
        log.info("--------调用webservice接口end-------");
        return returnMap;
    }

    /**
     * 获取oa待办数量
     * @return
     */
    @Override
    public Map<String, Integer> getOaCount() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        System.out.println("当前人员的idCard:" + loginAppUser.getIdCard());
        LambdaQueryWrapper<PortalContentTodoTasks> todoTasksWrapper = new LambdaQueryWrapper<>();
        todoTasksWrapper.eq(PortalContentTodoTasks::getSysBizCode,"OA");
        todoTasksWrapper.eq(PortalContentTodoTasks::getAssigneeIdCard,loginAppUser.getIdCard());
        List<PortalContentTodoTasks> todoTasksList = todoTasksMapper.selectList(todoTasksWrapper);
        Map<String,Integer> map = new HashMap<>();
        map.put("taskCount",todoTasksList.size());
        return map;
    }

    public String doPostSoap1_1(String postUrl, String soapXml,
                                String soapAction) {
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        //  设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().build();
        httpPost.setConfig(requestConfig);
        try {
            //设置请求头部信息
            httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
//            httpPost.setHeader("X-Powered-By", "ASP.NET");

            StringEntity data = new StringEntity(soapXml, Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
                log.info("response:" + retStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retStr;
    }

    private Map<String, String> parseXml2Map(String xml, Map<String, String> map) {
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new StringReader(xml));
            Element root = doc.getRootElement();
            String path = "";
            if (map.containsKey(root.getName().trim())) {
                path = map.get(root.getName().trim());
                map.remove(root.getName().trim());
            }
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                if (element.isTextOnly()) {
                    if (path.length() > 0) {
                        map.put(path + element.getName().trim(), element.getTextTrim());
                    } else {
                        map.put(element.getName().trim(), element.getTextTrim());
                    }
                } else {
                    map.put(element.getName().trim(), path + element.getName().trim() + ".");
                    parseXml2Map(element.asXML(), map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
