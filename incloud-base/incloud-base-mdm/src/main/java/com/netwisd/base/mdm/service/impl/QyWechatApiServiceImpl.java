package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.csv.CsvWriter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.netwisd.base.mdm.entity.MdmPostUser;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.entity.qywechat.Dept;
import com.netwisd.base.mdm.mapper.MdmOrgMapper;
import com.netwisd.base.mdm.mapper.MdmUserMapper;
import com.netwisd.base.mdm.service.MdmOrgService;
import com.netwisd.base.mdm.service.MdmPostUserService;
import com.netwisd.base.mdm.service.QyWechatApiService;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QyWechatApiServiceImpl implements QyWechatApiService {

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private RedisQyWeChatCodeServices redisQyWeChatCodeServices;

    @Autowired
    private MdmOrgMapper mdmOrgMapper;

    @Autowired
    private MdmOrgService mdmOrgService;

    @Autowired
    private MdmUserMapper mdmUserMapper;

    @Autowired
    private MdmPostUserService mdmPostUserService;

    /////////////////////企业微信相关开始/////////////////////
    @Value("${qywechat.listdepturl}")
    private String listDeptUrl;//获取部门列表

    @Value("${qywechat.deptUserUrl}")
    private String deptUserUrl;

    @Value("${qywechat.createdepturl}")
    private String createDeptUrl;//创建部门

    @Value("${qywechat.updatedepturl}")
    private String updateDeptUrl;//更新部门

    @Value("${qywechat.deletedepturl}")
    private String deleteDeptUrl;//删除部门

    @Value("${qywechat.createuserurl}")
    private String createUserUrl;//创建成员

    @Value("${qywechat.updateuserurl}")
    private String updateUserUrl;//更新成员

    @Value("${qywechat.deleteuserurl}")
    private String deleteUserUrl;//删除成员

    @Value("${qywechat.batchdeleteuserurl}")
    private String batchDeleteUserUrl;//批量删除成员

    @Value("${qywechat.uploadurl}")
    private String uploadUrl;//上传临时素材

    @Value("${qywechat.replacedept}")
    private String replaceDept;//全量覆盖部门

    @Value("${qywechat.replaceuser}")
    private String replaceUser;//全量覆盖成员

    @Value("${qywechat.syncuser}")
    private String syncUser;//增量更新成员

    @Value("${qywechat.getresulturl}")
    private String getResultUrl;//获取异步任务结果

    @Value("${qywechat.csvdeptfileurl}")
    private String csvdeptfileurl;//批量新增更新（覆盖）部门文件路径

    @Value("${qywechat.csvuserfileurl}")
    private String csvuserfileurl;//批量新增更新（覆盖）人员文件路径

    @Value("${qywechat.isSync}")
    private Boolean isSync;

    @Value("${qywechat.deptUserDetailUrl}")
    private String deptUserDetailUrl;

    /////////////////////企业微信相关结束/////////////////////

    /////////////////////接口代码/////////////////////
    /**
     * 获取调用企业微信接口的token
     * @return
     */
    private String getAccessToken() throws Exception{
       return redisQyWeChatCodeServices.getAccessToken();
    }

    /**
     * 创建或修改企业微信部门
     * @param mdmOrgVo
     * @param type
     */
    @Override
    public void createOrUpdateDept(MdmOrgVo mdmOrgVo, String type) {
        if (isSync) {
            try {
                //请求路径
                String url = "";
                if ("add".equals(type)) {
                    url = createDeptUrl + "?access_token=" + getAccessToken();
                }
                if ("update".equals(type)){
                    url = updateDeptUrl + "?access_token=" + getAccessToken();
                }

                String parentid = "";
                MdmOrgVo parentMdmOrgVo = mdmOrgService.get(mdmOrgVo.getParentId());
                if ("root".equals(parentMdmOrgVo.getParentOrgName())) {
                    parentid = "1";
                }else {
                    parentid = parentMdmOrgVo.getQyWeChatDeptId().toString();
                }

                //请求参数
                Map<String,Object> map = new HashMap<>();
                map.put("id",mdmOrgVo.getQyWeChatDeptId());//id
                map.put("name",mdmOrgVo.getOrgName());//部门名称
                map.put("parentid",parentid);//父部门id
                String jsonString = JSONObject.toJSONString(map);

                //post请求企业微信修改部门接口
                String result = doPost(url, jsonString);
                if (StringUtils.isNotEmpty(result)) {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    Integer errcode = jsonObject.getInteger("errcode");
                    if (0 == errcode) {
                        if ("add".equals(type)) {
                            log.info("同步企业微信，成功创建机构，机构id：{}，机构名称：{}",mdmOrgVo.getQyWeChatDeptId(),mdmOrgVo.getOrgName());
                        }
                        if ("update".equals(type)) {
                            log.info("同步企业微信，成功修改机构，机构id：{}，机构名称：{}",mdmOrgVo.getQyWeChatDeptId(),mdmOrgVo.getOrgName());
                        }
                    }else {
                        String errmsg = jsonObject.getString("errmsg");
                        if ("add".equals(type)) {
                            log.error("同步企业微信，创建机构报错，报错机构id：{}，报错机构名称：{}，报错内容：{}",mdmOrgVo.getQyWeChatDeptId(),mdmOrgVo.getOrgName(),errmsg);
                        }
                        if ("update".equals(type)) {
                            log.error("同步企业微信，修改机构报错，报错机构id：{}，报错机构名称：{}，报错内容：{}",mdmOrgVo.getQyWeChatDeptId(),mdmOrgVo.getOrgName(),errmsg);
                        }
                    }
                }
            }catch (Exception e){
                if ("add".equals(type)) {
                    log.error("同步企业微信，创建机构报错，报错机构id：{}，报错机构名称：{}，报错内容：{}",mdmOrgVo.getQyWeChatDeptId(),mdmOrgVo.getOrgName(),e.toString());
                }
                if ("update".equals(type)) {
                    log.error("同步企业微信，修改机构报错，报错机构id：{}，报错机构名称：{}，报错内容：{}",mdmOrgVo.getQyWeChatDeptId(),mdmOrgVo.getOrgName(),e.toString());
                }
            }
        }
    }

    /**
     * 删除部门（只能删除没有子部门并且没有人员的部门）
     * @param qyWechatDeptId
     */
    @Override
    public void deleteDept(String qyWechatDeptId) {
        if (isSync) {
            try {
                String url = deleteDeptUrl + "?access_token=" + getAccessToken() + "&id=" + qyWechatDeptId;
                String result = doGet(url);
                if (StringUtils.isNotEmpty(result)) {
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    Integer errcode = jsonObject.getInteger("errcode");
                    if (0 == errcode) {
                        log.info("同步企业微信，成功删除机构，机构id：{}", qyWechatDeptId);
                    }else {
                        String errmsg = jsonObject.getString("errmsg");
                        log.error("同步企业微信，删除机构报错，报错机构id：{}，报错内容：{}", qyWechatDeptId,errmsg);
                    }
                }
            }catch (Exception e){
                log.error("同步企业微信，删除机构报错，报错机构id：{}，报错内容：{}", qyWechatDeptId,e.toString());
            }
        }
    }

    /**
     * 删除当前部门下的所有部门（部门下不能有人员）
     * @param qyWechatDeptId
     */
    @Override
    public void batchDeleteDept(String qyWechatDeptId){
        if (isSync) {
            try {
                String url = listDeptUrl + "?access_token=" + getAccessToken() + "&id=" + qyWechatDeptId;
                String deptListResult = doGet(url);
                if (null == deptListResult) {
                    return;
                }
                JSONObject objJsonObject = (JSONObject) JSONObject.parse(deptListResult);
                Integer errcode = (Integer)objJsonObject.get("errcode");
                if (0 == errcode) {
                    JSONArray department = objJsonObject.getJSONArray("department");
                    if (CollectionUtil.isNotEmpty(department)) {
                        List<Dept> depts = JSONObject.parseArray(department.toJSONString(), Dept.class);
                        for (Dept dept : depts){
                            if (!dept.getId().equals(qyWechatDeptId)) {
                                batchDeleteDept(dept.getId());
                            }
                        }
                    }
                    deleteDept(qyWechatDeptId);
                }
            }catch (Exception e){
                log.error("递归删除机构/部门出错，报错机构/部门id:{},报错信息：{}",qyWechatDeptId,e.toString());
            }
        }
    }

    /**
     * 删除部门和人员（删除当前部门底下所有的人员和部门）
     * @param qyWechatDeptId
     */
    @Override
    public void deleteDeptAndUser(String qyWechatDeptId) {
        if (isSync) {
            StringBuilder userIdList = new StringBuilder();
            try {
                //获取企业微信token
                String accessToken = getAccessToken();
                //1.先查询该部门下面所有的人员包括子部门的
                String deptUserGetUrl = deptUserUrl + "?" + "access_token=" + accessToken + "&department_id=" + qyWechatDeptId + "&fetch_child=1";
                String deptUserResult = doGet(deptUserGetUrl);
                if (null == deptUserResult) {
                    return;
                }
                JSONObject objJsonObject = (JSONObject) JSONObject.parse(deptUserResult);
                Integer errcode = objJsonObject.getInteger("errcode");
                if(0 == errcode){
                    JSONArray userJsonArray = objJsonObject.getJSONArray("userlist");
                    for (int i = 0 ; i < userJsonArray.size() ; i ++){
                        JSONObject obj = userJsonArray.getJSONObject(i);
                        String userid = obj.getString("userid");
                        userIdList.append(userid);
                        userIdList.append(",");
                    }
                }else {
                    String errmsg = objJsonObject.getString("errmsg");
                    log.error("删除部门和人员中的查询部门报错，查询的部门id：{}，报错信息：{}",qyWechatDeptId,errmsg);
                    return;
                }

                //2.如果部门下面有人，先对人员进行删除
                batchDeleteUser(userIdList.toString());

                //3.递归删除该部门下面所有的部门
                batchDeleteDept(qyWechatDeptId);
            }catch (Exception e){
                log.error("删除机构/部门及子机构/部门及人员报错，报错机构/部门id：{},报错信息：{}",qyWechatDeptId,e.toString());
            }
        }
    }

    /**
     * 创建或者修改企业微信人员
     * @param mdmUserVo
     * @param type
     */
    @Override
    public void createOrUpdateUser(MdmUserVo mdmUserVo, String type) {
        if (isSync) {
            try {
                String url = "";
                String accessToken = getAccessToken();
                if ("add".equals(type)) {
                    url = createUserUrl + "?access_token=" + accessToken;
                }
                if ("update".equals(type)) {
                    url = updateUserUrl + "?access_token=" + accessToken;
                }

                Long parentDeptId = mdmUserVo.getParentDeptId();
                Integer parentQyWechatDeptId = mdmOrgService.get(parentDeptId).getQyWeChatDeptId();
                Integer[] parentDeptIdNum = new Integer[1];
                parentDeptIdNum[0] = parentQyWechatDeptId;

                Map<String,Object> map = new HashMap<>();
                map.put("userid",mdmUserVo.getUserName());
                map.put("name",mdmUserVo.getUserNameCh());
                map.put("mobile",mdmUserVo.getPhoneNum());
                map.put("email",mdmUserVo.getEmail());
                map.put("department",parentDeptIdNum);
                map.put("enable",mdmUserVo.getStatus());
                String jsonString = JSONObject.toJSONString(map);
                String result = doPost(url, jsonString);
                if (null == result) {
                    return;
                }
                JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                Integer errcode = jsonObject.getInteger("errcode");
                if (0 == errcode) {
                    if ("add".equals(type)) {
                        log.info("同步企业微信，成功创建人员，人员userid：{}，人员名称：{}",mdmUserVo.getUserName(),mdmUserVo.getUserNameCh());
                    }
                    if ("update".equals(type)) {
                        log.info("同步企业微信，成功修改人员，人员userid：{}，人员名称：{}",mdmUserVo.getUserName(),mdmUserVo.getUserNameCh());
                    }
                }else {
                    String errmsg = jsonObject.getString("errmsg");
                    if ("add".equals(type)) {
                        log.error("同步企业微信，创建人员报错，报错人员userid：{}，报错人员名称：{}，报错内容：{}",mdmUserVo.getUserName(),mdmUserVo.getUserNameCh(),errmsg);
                    }
                    if ("update".equals(type)) {
                        log.error("同步企业微信，修改人员报错，报错人员userid：{}，报错人员名称：{}，报错内容：{}",mdmUserVo.getUserName(),mdmUserVo.getUserNameCh(),errmsg);
                    }
                }
            }catch (Exception e){
                if ("add".equals(type)) {
                    log.error("同步企业微信，创建人员报错，报错人员userid：{}，报错人员名称：{}，报错内容：{}",mdmUserVo.getUserName(),mdmUserVo.getUserNameCh(),e.toString());
                }
                if ("update".equals(type)) {
                    log.error("同步企业微信，修改人员报错，报错人员userid：{}，报错人员名称：{}，报错内容：{}",mdmUserVo.getUserName(),mdmUserVo.getUserNameCh(),e.toString());
                }
            }
        }
    }

    /**
     * 批量删除人员
     * @param userIds
     */
    @Override
    public void batchDeleteUser(String userIds) {
        if (isSync) {
            try {
                String url = batchDeleteUserUrl + "?access_token=" + getAccessToken();
                String[] userIdList = userIds.split(",");
                Map<String,Object> map = new HashMap<>();
                map.put("useridlist",userIdList);
                String jsonString = JSONObject.toJSONString(map);
                String result = doPost(url, jsonString);
                if (null == result) {
                    return;
                }
                JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
                Integer errcode = jsonObject.getInteger("errcode");
                if (0 == errcode) {
                    log.info("批量删除人员成功，删除的人员有：{}",userIds);
                }else {
                    String errmsg = jsonObject.getString("errmsg");
                    log.error("批量删除人员失败，删除的人员有：{}，错误信息是：{}",userIds,errmsg);
                }
            }catch (Exception e){
                log.error("批量删除人员失败，删除的人员有：{}，错误信息是：{}",userIds,e.toString());
            }
        }
    }

    @Override
    public void batchDeleteUserById(List<String> ids) {
        if (isSync) {
            try {
                StringBuilder userIds = new StringBuilder();
                ids.forEach(id->{
                    LambdaQueryWrapper<MdmUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    userLambdaQueryWrapper.eq(MdmUser::getId,id);
                    MdmUser mdmUser = mdmUserMapper.selectOne(userLambdaQueryWrapper);
                    userIds.append(mdmUser.getUserName());
                    userIds.append(",");
                });
                batchDeleteUser(userIds.toString());
            }catch (Exception e){
                log.error("通过ids删除企业微信用户报错！报错信息："+e);
            }
        }
    }

    /**
     * 全量覆盖企业微信部门
     * @return
     */
    @Override
    public JSONArray replaceDept() {
        try {
            String accessToken = getAccessToken();
            //1.生成本地文件（企业微信的上传文件）
            writeDeptCsv();

            //2.上传生成的文件
            File file = new File(csvdeptfileurl);
            String mediaId = qyWechatUpload(accessToken, file);
            //3.让企业微信根据刚才上传的文件执行全量覆盖部门操作
            String getJobIdUrl = replaceDept + "?access_token=" + accessToken;
            Map<String,Object> map = new HashMap<>();
            map.put("media_id",mediaId);
            String jsonString = JSONObject.toJSONString(map);
            String jobIdResult = doPost(getJobIdUrl, jsonString);
            JSONObject jsonObject = JSONObject.parseObject(jobIdResult);
            Integer errcode = jsonObject.getInteger("errcode");
            if (0 == errcode) {
                String jobId = jsonObject.getString("jobid");
                //4.根据jobId获取全量覆盖部门的结果
                return getResult(jobId);
            }
            throw new IncloudException("根据上传的文件执行去量覆盖部门报错",jsonObject.getString("errmsg"));
        }catch (Exception e){
            throw new IncloudException("全量覆盖企业微信部门报错，错误信息："+e);
        }
    }

    /**
     * 全量覆盖企业微信用户
     * @return
     */
    @Override
    public JSONArray replaceUser() {
        try {
            String accessToken = getAccessToken();
            //1.生成文件
            Boolean aBoolean = writeUserCsv();
            if (aBoolean) {
                File file = new File(csvuserfileurl);
                //1.上传文件，获取mediaId
                String mediaId = qyWechatUpload(accessToken,file);
                //2.通过mediaid，执行全量覆盖成员，返回jobid
                String getJobIdUrl = replaceUser + "?access_token=" + accessToken;
                Map<String,Object> map = new HashMap<>();
                map.put("media_id",mediaId);
                map.put("to_invite",false);
                String jsonString = JSONObject.toJSONString(map);
                String jobIdResult = doPost(getJobIdUrl, jsonString);

                JSONObject jsonObject = JSONObject.parseObject(jobIdResult);
                Integer errcode = jsonObject.getInteger("errcode");
                if (0 == errcode) {
                    String jobId = jsonObject.getString("jobid");
                    //4.根据jobId获取全量覆盖部门的结果
                    return getResult(jobId);
                }
                throw new IncloudException("通过mediaid执行全量覆盖成员报错，错误信息："+errcode+","+jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            throw new IncloudException("全量覆盖企业微信用户报错，错误信息："+e);
        }
        return null;
    }

    /**
     * 全量增量修改人员（不删除）
     * @return
     */
    @Override
    public JSONArray syncuser() {
        try {
            String accessToken = getAccessToken();
            //1.生成本地需要上传到企业微信的excel表
            Boolean aBoolean = writeUserCsv();
            if (aBoolean) {
                File file = new File(csvuserfileurl);
                //2.上传文件，获取mediaId
                String mediaId = qyWechatUpload(accessToken,file);
                //3.通过mediaid，执行全量覆盖成员，返回jobid
                String getJobIdUrl = syncUser + "?access_token=" + accessToken;
                String jsonString = JSONObject.toJSONString(mediaId);
                String jobIdResult = doPost(getJobIdUrl, jsonString);

                JSONObject jsonObject = JSONObject.parseObject(jobIdResult);
                Integer errcode = jsonObject.getInteger("errcode");
                if (0 == errcode) {
                    //4.通过jobid获取结果
                    String jobId = jsonObject.getString("jobid");
                    return getResult(jobId);
                }
                throw new IncloudException("通过mediaid执行全量覆盖成员，返回jobId错误，错误信息："+errcode+","+jsonObject.getString("errmsg"));
            }
        }catch (Exception e){
            throw new IncloudException("全量增量修改人员（不删除）报错，错误信息："+e);
        }
        return null;
    }

    /**
     * 获取企业微信异步结果
     * @param jobId
     * @return
     */
    @Override
    public JSONArray getResult(String jobId){
        try {
            String url = getResultUrl + "?access_token=" + getAccessToken() + "&jobid=" + jobId;
            String result = doGet(url);
            if (null == result) {
                return null;
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer errcode = jsonObject.getInteger("errcode");
            if (0 == errcode) {
                int status = jsonObject.getIntValue("status");
                if (3 != status) {
                    return getResult(jobId);
                }
                JSONArray array = jsonObject.getJSONArray("result");
                if (ObjectUtils.isNotEmpty(array)) {
                    return array;
                }
            }else {
                throw new IncloudException(jsonObject.toString());
            }
        }catch (Exception e){
            log.debug("通过jobId获取返回结果错误，错误信息：{}",e.toString());
        }
        return null;
    }

    /**
     * 全量同步企业微信部门（定时调用）
     */
    @Override
    public void syncAllDept() {
        long startTime = System.currentTimeMillis();
        //获取主数据全量部门
        MdmOrgDto mdmOrgDto = new MdmOrgDto();
        mdmOrgDto.setStatus(YesNo.YES.code);
        List<MdmOrgAllVo> orgAllVos = mdmOrgService.list(mdmOrgDto);
        List<MdmOrgAllVo> orgAllKids = orgAllVos.get(0).getKids();
        //获取企业微信全量机构
        Map<String, List<Dept>> qyWechatMap;
        try {
            String getAllOrgUrl = listDeptUrl + "?access_token=" + getAccessToken();
            String getAllOrgResult = this.doGet(getAllOrgUrl);
            JSONObject jsonObject = JSONObject.parseObject(getAllOrgResult);
            Integer errcode = jsonObject.getInteger("errcode");
            if (0 == errcode) {
                //将企业微信部门按照企业微信id分组
                JSONArray department = jsonObject.getJSONArray("department");
                List<Dept> depts = JSONObject.parseArray(department.toJSONString(), Dept.class);
                qyWechatMap = depts.stream().collect(Collectors.groupingBy(Dept::getId));
            }else {
                throw new IncloudException(jsonObject.toString());
            }
        }catch (Exception e){
            log.debug("获取企业微信全量部门失败：{}",e.toString());
            return;
        }
        //循环除了顶级机构其他全部机构组织
        if (CollectionUtil.isNotEmpty(orgAllKids) && ObjectUtils.isNotEmpty(qyWechatMap)) {
            List<Integer> addList = new ArrayList<>();//需要添加的企业微信部门id集合
            List<Integer> updateList = new ArrayList<>();//需要修改的企业微信部门id集合
            //递归对比主数据与企业微信部门，获得需要添加的部门和修改的部门
            this.crudOrg(orgAllKids,qyWechatMap,addList,updateList);
            //查询出全部部门，控制台输出使用
            LambdaQueryWrapper<MdmOrg> orgLambdaQueryWrapper = new LambdaQueryWrapper<>();
            List<MdmOrg> mdmOrgs = mdmOrgMapper.selectList(orgLambdaQueryWrapper);
            Map<Integer, List<MdmOrg>> mdmOrgMap = mdmOrgs.stream().filter(mdm->ObjectUtils.isNotEmpty(mdm.getQyWeChatDeptId())).collect(Collectors.groupingBy(MdmOrg::getQyWeChatDeptId));
            //循环添加集合，依次调用企业微信添加部门接口
            for (Integer add:addList){
                MdmOrgVo orgVo = dozerMapper.map(mdmOrgMap.get(add).get(0), MdmOrgVo.class);
                this.createOrUpdateDept(orgVo,"add");
                log.debug("企业微信添加的部门id：{},名称：{}",add,mdmOrgMap.get(add).get(0).getOrgName());
            }
            //循环修改集合，依次调用企业微信修改部门接口
            for (Integer update:updateList){
                MdmOrgVo orgVo = dozerMapper.map(mdmOrgMap.get(update).get(0), MdmOrgVo.class);
                this.createOrUpdateDept(orgVo,"update");
                log.debug("企业微信修改的部门id:{},原名称:{},新名称:{}",update,
                        qyWechatMap.get(update.toString()).get(0).getName(), mdmOrgMap.get(update).get(0).getOrgName());
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("同步完企业微信部门，消耗时间："+(endTime-startTime)/1000+"s");
    }

    //递归对比主数据与企业微信部门，获得需要添加的部门和修改的部门
    private void crudOrg(List<MdmOrgAllVo> orgAllKids,Map<String, List<Dept>> qyWechatMap,List<Integer> addList,List<Integer> updateList){
        for (MdmOrgAllVo orgVo : orgAllKids){
            //如果主数据的部门在企业微信中，判断企业微信的部门名称是否与主数据中的一样，一样不更新，不一样进行更新
            if (CollectionUtil.isNotEmpty(qyWechatMap.get(orgVo.getQyWeChatDeptId().toString()))) {
                Dept dept = qyWechatMap.get(orgVo.getQyWeChatDeptId().toString()).get(0);
                if (!dept.getName().equals(orgVo.getOrgName())) {
                    updateList.add(orgVo.getQyWeChatDeptId());
                }
            }else {
                //企业微信中没有主数据的部门，则企业微信中进行新增
                addList.add(orgVo.getQyWeChatDeptId());
            }
            //如果主数据的部门有子集，进行递归
            if (CollectionUtil.isNotEmpty(orgVo.getKids())) {
                this.crudOrg(orgVo.getKids(),qyWechatMap,addList,updateList);
            }
        }
    }

    /**
     * 全量同步企业微信人员（定时调用）
     */
    @Override
    public void syncAllUser() {
        long startTime = System.currentTimeMillis();
        //1.递归获取主数据全量部门
        MdmOrgDto mdmOrgDto = new MdmOrgDto();
        mdmOrgDto.setStatus(YesNo.YES.code);
        List<MdmOrgAllVo> orgAllVos = mdmOrgService.list(mdmOrgDto);
        List<MdmOrgAllVo> orgAllKids = orgAllVos.get(0).getKids();
        List<MdmUser> addList = new ArrayList<>();
        List<MdmUser> updateList = new ArrayList<>();
        this.crudUser(orgAllKids,addList,updateList);
        for (MdmUser mdmUser : addList){
            System.out.println("新增的人证件号："+mdmUser.getUserName());
            System.out.println("机构："+mdmUser.getParentOrgName());
            System.out.println("部门:"+ mdmUser.getParentDeptName());
            System.out.println("名称："+mdmUser.getUserNameCh());
        }
        for (MdmUser mdmUser : updateList){
            System.out.println("修改的人证件号："+mdmUser.getUserName());
            System.out.println("机构："+mdmUser.getParentOrgName());
            System.out.println("部门:"+ mdmUser.getParentDeptName());
            System.out.println("名称："+mdmUser.getUserNameCh());
        }
        System.out.println("新增的人员数量："+addList.size());
        System.out.println("修改的人员数量："+updateList.size());
        long endTime = System.currentTimeMillis();
        System.out.println("同步企业微信人员完成，耗时："+(endTime-startTime)/1000+"s");
    }

    //递归循环每个部门，看每个部门的人员与企业微信的人员是否相同
    private void crudUser(List<MdmOrgAllVo> orgAllKids,List<MdmUser> addList ,List<MdmUser> updateList){
        for (MdmOrgAllVo orgVo : orgAllKids){
            //获取主数据的人员
            LambdaQueryWrapper<MdmUser> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.eq(MdmUser::getParentDeptId,orgVo.getId());
            List<MdmUser> mdmUserList = mdmUserMapper.selectList(userWrapper);
            List<MdmUser> qyWechatUserList = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                //获取企业微信当前部门的成员
                try {
                    String deptUserGetUrl = deptUserDetailUrl + "?" + getAccessToken() +"&department_id="+ orgVo.getQyWeChatDeptId();
                    String qyWechatUserString = this.doGet(deptUserGetUrl);
                    JSONObject jsonObject = JSONObject.parseObject(qyWechatUserString);
                    Integer errcode = jsonObject.getInteger("errcode");
                    if (0 == errcode) {
                        //将企业微信部门按照企业微信id分组
                        JSONArray qyWechatUserArray = jsonObject.getJSONArray("userlist");
                        for (int i = 0 ; i < qyWechatUserArray.size() ; i++){
                            JSONObject qyWechatUserObject = (JSONObject)qyWechatUserArray.get(i);
                            String name = qyWechatUserObject.getString("name");
                            String userid = qyWechatUserObject.getString("userid");
                            String mobile = qyWechatUserObject.getString("mobile");
                            String email = qyWechatUserObject.getString("email");
                            MdmUser mdmUser = new MdmUser();
                            mdmUser.setUserNameCh(name);
                            mdmUser.setUserName(userid);
                            mdmUser.setPhoneNum(mobile);
                            mdmUser.setEmail(email);
                            qyWechatUserList.add(mdmUser);
                        }
                    }else {
                        throw new IncloudException(jsonObject.toString());
                    }
                }catch (Exception e){
                    log.debug("获取企业微信全量部门失败：{}",e.toString());
                    return;
                }
            }
            //对比主数据的人员和企业微信部门的成员
            for (MdmUser mdmUser : mdmUserList){
                int i = 0;
                for (MdmUser qyWechatUser : qyWechatUserList){
                    if (mdmUser.getUserName().equals(qyWechatUser.getUserName())) {
                        i++;
                        if (!mdmUser.getUserNameCh().equals(qyWechatUser.getUserNameCh())
                                || !mdmUser.getPhoneNum().equals(qyWechatUser.getPhoneNum()) || !mdmUser.getEmail().equals(qyWechatUser.getEmail())) {
                            MdmUserVo mdmUserVo = dozerMapper.map(mdmUser, MdmUserVo.class);
                            this.createOrUpdateUser(mdmUserVo,"update");
                            updateList.add(mdmUser);
                        }
                    }
                }
                if (i == 0) {
                    MdmUserVo mdmUserVo = dozerMapper.map(mdmUser, MdmUserVo.class);
                    this.createOrUpdateUser(mdmUserVo,"add");
                    addList.add(mdmUser);
                }
            }
            if (CollectionUtil.isNotEmpty(orgVo.getKids())) {
                this.crudUser(orgVo.getKids(),addList,updateList);
            }
        }
    }
    //////////////////////接口代码结束///////////////////////

    /////////////////////生成本地文件工具/////////////////////
    public void writeDeptCsv() {
        MdmOrgDto mdmOrgDto = new MdmOrgDto();
        mdmOrgDto.setIsDefault(0);
        mdmOrgDto.setStatus(1);
        List<MdmOrgAllVo> mdmOrgAllVos = mdmOrgService.list(mdmOrgDto);

        File file = new File(csvdeptfileurl);
        CsvWriter csvWriter = new CsvWriter(file, Charset.forName("GBK"));
        List<String> line = new ArrayList<>();
        line.add("部门名称,部门ID,父部门ID,排序");
        addDeptLine(line,mdmOrgAllVos,0);
        csvWriter.write(line);

        csvWriter.flush();
        csvWriter.close();

        log.info("--------------------------全部部门覆盖文档完成---------------------------");
    }

    public Boolean writeUserCsv(){
        //查询所有启用，属于中原建设的用户
        LambdaQueryWrapper<MdmUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(MdmUser::getStatus,1);
        userLambdaQueryWrapper.eq(MdmUser::getUserClass,2);
        List<MdmUser> userList = mdmUserMapper.selectList(userLambdaQueryWrapper);

        File file = new File(csvuserfileurl);
        CsvWriter csvWriter = new CsvWriter(file,Charset.forName("GBK"));
        List<String> line = new ArrayList<>();
        line.add("姓名,帐号,手机号,邮箱,所在部门,职位,性别,是否部门内领导,排序,别名,地址,座机,禁用,禁用项说明：(0-启用;1-禁用)");
        for (MdmUser mdmUser : userList){
            StringBuilder sb = new StringBuilder();
            sb.append(mdmUser.getUserNameCh());//姓名
            sb.append(",");
            sb.append(mdmUser.getUserName());//账号
            sb.append(",");
            if (org.apache.commons.lang3.StringUtils.isNotBlank(mdmUser.getPhoneNum())) {
                sb.append(mdmUser.getPhoneNum());//手机号
            }
            sb.append(",");
            if (org.apache.commons.lang3.StringUtils.isNotBlank(mdmUser.getEmail())) {
                sb.append(mdmUser.getEmail());//邮箱
            }
            sb.append(",");
            //所在部门
            MdmOrgVo mdmOrgVo = mdmOrgService.get(mdmUser.getParentDeptId());
            if (ObjectUtils.isNotEmpty(mdmOrgVo)) {
                sb.append(mdmOrgVo.getQyWeChatDeptId());
            }
            sb.append(",");
            //职位
            MdmPostUser master = mdmPostUserService.getMaster(mdmUser.getId());
            if (ObjectUtils.isNotEmpty(master)) {
                sb.append(master.getPostName());
            }
            sb.append(",");
            //性别
            if (mdmUser.getSex() != null) {
                if (mdmUser.getSex() == 0) {
                    sb.append("男");
                }else {
                    sb.append("女");
                }
            }
            sb.append(",");
            //是否部门内领导
            sb.append(",");
            sb.append(mdmUser.getSort());//排序
            sb.append(",");
            //别名
            sb.append(",");
            sb.append(mdmUser.getAddr());//地址
            sb.append(",");
            //座机
            sb.append(",");
            sb.append(0);//禁用 0-启用;1-禁用
            line.add(sb.toString());
            System.out.println(sb);
        }
        csvWriter.write(line);

        csvWriter.flush();
        csvWriter.close();

        System.out.println("--------------------------全部人员数量："+userList.size());
        return true;
    }

    //递归 部门集合
    public static void addDeptLine(List<String> line,List<MdmOrgAllVo> mdmOrgAllVos,Integer parentId){
        if (CollectionUtil.isNotEmpty(mdmOrgAllVos)) {
            StringBuilder repeatData = new StringBuilder();//重复数据
            for (MdmOrgAllVo mdmOrgAllVo : mdmOrgAllVos){
                //部门名称
                StringBuilder sb = new StringBuilder();
                int count = getCount(repeatData.toString(), mdmOrgAllVo.getOrgName());
                sb.append(mdmOrgAllVo.getOrgName());
                if (count > 0) {
                    sb.append(count+1);
                }
                repeatData.append(mdmOrgAllVo.getOrgName());
                repeatData.append(",");
                sb.append(",");
                //部门id
                if ("root".equals(mdmOrgAllVo.getParentName())) {
                    sb.append(1);
                }else {
                    sb.append(mdmOrgAllVo.getQyWeChatDeptId());
                }
                sb.append(",");
                //父级部门id
                sb.append(parentId);
                line.add(sb.toString());
                if (CollectionUtil.isNotEmpty(mdmOrgAllVo.getKids())) {
                    if ("root".equals(mdmOrgAllVo.getParentName())) {
                        addDeptLine(line,mdmOrgAllVo.getKids(),1);
                    }else {
                        addDeptLine(line,mdmOrgAllVo.getKids(),mdmOrgAllVo.getQyWeChatDeptId());
                    }
                }
            }
        }
    }

    //获取重复字符串的数量
    public static int getCount(String str,String c){
        int index = 0;
        int count = 0;
        while((index = str.indexOf(c)) != -1){
            count++;
            str = str.substring(index + 1);
        }
        return count;
    }

    /////////////////////生成本地文件工具结束/////////////////////

    //////////////////////远程调用工具类方法/////////////////////
    /**
     * get远程调用
     * @param url
     * @return
     */
    private String doGet(String url){
        try {
            HttpClient client =  HttpClientBuilder.create().build();
            HttpGet httpGet= new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            return EntityUtils.toString(response.getEntity());
        }catch (Exception e){
            log.error("企业微信get调用失败，请求失败url:{}",url);
        }
        return null;
    }

    /**
     * post远程调用
     * @param url
     * @param jsonString
     * @return
     */
    private String doPost(String url,String jsonString){
        try {
            // 创建Httpclient对象
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            // 创建请求内容
            StringEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");

            httpClient.close();
            httpPost.clone();
            response.close();

            return result;
        }catch (Exception e) {
            log.error("企业微信post调用失败，请求失败url：{}，请求失败的传参：{}", url, jsonString);
        }
        return null;
    }

    //企业微信，上传附件
    public String qyWechatUpload(String accessToken,File file) {
        StringBuffer buffer = new StringBuffer();
        try{
            //1.建立连接
            URL url = new URL(uploadUrl+"?access_token="+accessToken+"&type=file");
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  //打开链接

            //1.1输入输出设置
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false); // post方式不能使用缓存
            //1.2设置请求头信息
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            //1.3设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);

            // 请求正文信息
            // 第一部分：
            //2.将文件头输出到微信服务器
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length()
                    + "\";filename=\""+ file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
            // 将表头写入输出流中：输出表头
            outputStream.write(head);

            //3.将文件正文部分输出到微信服务器
            // 把文件以流文件的方式 写入到微信服务器中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
            in.close();
            //4.将结尾部分输出到微信服务器
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();

            //5.将微信服务器返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;

            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            throw new IncloudException("企业微信上传附件错误！错误信息："+e);
        }
        JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
        if (!jsonObject.getString("errcode").equals("0")) {
            throw new IncloudException("企业微信上传附件错误！错误信息:"+jsonObject.toJSONString());
        }
        return jsonObject.getString("media_id");
    }
    /////////////////////远程调用工具类方法结束///////////////////


}
