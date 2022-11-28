package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.OrgClassEnum;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.dto.MdmPostUserDto;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.*;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.mdm.constants.*;
import com.netwisd.base.mdm.dto.GuFenDictDto;
import com.netwisd.base.mdm.dto.GuFenOrgDto;
import com.netwisd.base.mdm.dto.GuFenPositionDto;
import com.netwisd.base.mdm.dto.GuFenUserDto;
import com.netwisd.base.mdm.entity.*;
import com.netwisd.base.mdm.mapper.MdmRoleUserMapper;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service("syncGuFenService")
public class SyncGuFenServiceImpl implements SyncGuFenService {

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

    @Autowired
    private NeoService neoService;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Autowired
    private MdmMqService mdmMqService;

    @Autowired
    private MdmRoleUserMapper roleUserMapper;

    @Autowired
    private MdmRoleService roleService;

    /**
     * 全路径拼接串名称
     */
    private static String defaultJoinName = ",";

    //股份 机构岗位
    @Value("${mdInfo.url.orgUrl}")
    private String orgUrl;
    //股份 人员
    @Value("${mdInfo.url.empoyeeUrl}")
    private String empoyeeUrl;
    //股份 兼岗
    @Value("${mdInfo.url.empPositionUrl}")
    private String empPositionUrl;
    //股份 字典
    @Value("${mdInfo.url.dictUrl}")
    private String mdDictUrl;
    //股份 用户
    @Value("${mdInfo.headers.userName}")
    private String mdUserName;
    //股份 密码
    @Value("${mdInfo.headers.passWord}")
    private String mdPassWord;

    @Value("${spring.rocketmq.orgTopics}")
    private String orgTopics;

    @Value("${spring.rocketmq.postTopics}")
    private String postTopics;

    @Value("${spring.rocketmq.userTopics}")
    private String userTopics;

    private static String df = "yyyy-MM-dd";

    /**
     * 默认请求参数-获取所有
     */
    private static String defaultReqData = "{\"pageIndex\":-1,\"pageSize\":20}";

    /**
     * 默认请求参数-获取增量
     */
    private static String defaultReqDataIncrement = "{\"pageIndex\": -1,\"pageSize\": 20,\"criteria\": {\"_expr[1]/_property\": \"lastupdatetime\",\"_expr[1]/_value\": \""+getYesterDay()+"\",\"_expr[1]/_op\": \"=\"}}";


    @Override
    @Transactional
    public boolean syncGuFenOrgAndPost() {
        log.info("orgUrl:{}",orgUrl);
        if(StringUtils.isBlank(orgUrl))  {
            throw new IncloudException("没有获取到股份机构URL.");
        }
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(orgUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("userName",mdUserName);
            httpPost.setHeader("passWord",mdPassWord);
            StringEntity entity = new StringEntity(defaultReqData, "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) {
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                log.info("result！" + resString);
                //String str = readJsonFile("C:\\Users\\x1c\\Desktop\\股份json数据\\org.json");
                JSONObject obj = JSON.parseObject(resString);
                JSONArray datas = obj.getJSONArray("datas");
                List<GuFenOrgDto> list = JSONObject.parseArray(datas.toJSONString(), GuFenOrgDto.class);
                Map<String, List<GuFenOrgDto>> groupMap = list.stream().collect(Collectors.groupingBy(GuFenOrgDto::getOrgtype));
                List<GuFenOrgDto> orgList = groupMap.get("UN");//机构
                List<GuFenOrgDto> deptList = groupMap.get("UM");//部门
                List<GuFenOrgDto> postList = groupMap.get("@K");//岗位
                //岗位按照 上级code 分组
                Map<String, List<GuFenOrgDto>> postMap = postList.stream().collect(Collectors.groupingBy(GuFenOrgDto::getAsparorgcode));
                // 处理机构  finish
                orgList.addAll(deptList);
                Map<String, List<GuFenOrgDto>> orgMap = orgList.stream().collect(Collectors.groupingBy(GuFenOrgDto::getAsparorgcode));
                MdmOrgDto mdmOrgDto = new MdmOrgDto();
                mdmOrgDto.id = 0L;
                mdmOrgDto.setOrgCode("root");
                mdmOrgDto.setOrgName("root");
                mdmOrgDto.setParentId(-1L);
                mdmOrgDto.setParentName("root");
                mdmOrgDto.setOrgType(1);
                loopOrgInfo(mdmOrgDto,orgMap,"01");
                saveOrg(mdmOrgDto.getMdmOrgDtoList()); //保存数据前org表中要内置一条root
                log.debug("同步机构信息完成！");
                MdmOrgDto getAllMdmOrg = new MdmOrgDto();
                getAllMdmOrg.setIsDefault(YesNo.NO.code);
                //getAllMdmOrg.setStatus(StatusEnum.RUNNING.code);
                getAllMdmOrg.setOrgClass(OrgClassEnum.BIZ.code);
                List<MdmOrgAllVo> mdmOrgAllVoList = mdmOrgService.vos(getAllMdmOrg);
                //处理所有机构下的岗位
                handlePost(mdmOrgAllVoList,postMap);
                this.handleOrgPastDue();//处理过期时间
                log.debug("同步岗位信息完成！");
                //同步完成推送一次 默认建设公司的MQ
                MdmOrgDto tempOrgDto = new MdmOrgDto();
                tempOrgDto.setOrgName("中国核工业中原建设有限公司");
                tempOrgDto.setLvType(LvTypeEnum.CONSTRUCTION_COMPANY.code);
                tempOrgDto.setOaLvType(OaLvTypeEnum.ORG.code);
                List<MdmOrgAllVo> orgs = mdmOrgService.lists(tempOrgDto);
                if(CollectionUtil.isNotEmpty(orgs)) {//有且只有一条
                    mdmMqService.sendRocketMq(orgTopics, MqTagEnum.ADD.code, JSON.toJSONString(orgs.get(0), SerializerFeature.WriteMapNullValue));
                }
                return true;
            }else {
                log.info("同步股份数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步股份机构、岗位数据失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步股份数据失败!" +e.getMessage());
        }finally {

        }
    }

    @Override
    @Transactional
    public boolean syncGuFenOrgAndPostIncrement(String curStr) { //查询的当天时间
        log.info("增量同步组织、岗位。orgUrl:{}",orgUrl);
        if(StringUtils.isBlank(orgUrl))  {
            throw new IncloudException("没有获取到股份机构URL.");
        }
        HttpResponse execute = null;
        try {
            //根据当天时间查询 后面一天时间  >curStr < curStr+1
            String tomorrowStr = getTomorrowDay(curStr);//获取明天
//            String queryStr = "{'pageIndex': -1,'pageSize': 20,'criteria': {'_expr[1]/_property':'lastupdatetime','_expr[1]/_value':'"+curStr+"','_expr[1]/_op':'>=','_expr[2]/_property':'lastupdatetime','_expr[2]/_value':'"+tomorrowStr+"','_expr[2]/_op':'<'}}";
            //todo 改成全量新增
            String queryStr = "{'pageIndex': -1,'pageSize': 20}";
            HttpPost httpPost = new HttpPost(orgUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("userName",mdUserName);
            httpPost.setHeader("passWord",mdPassWord);
            log.info("增量同步组织、岗位。参数:{}",queryStr);
            StringEntity entity = new StringEntity(queryStr, "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) { //true
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                //String resString = readJsonFile("C:\\Users\\x1c\\Desktop\\股份json数据\\org_add.json");
                log.info("增量 org result！" + resString);
                JSONObject obj = JSON.parseObject(resString);
                JSONArray datas = obj.getJSONArray("datas");
                List<GuFenOrgDto> list = JSONObject.parseArray(datas.toJSONString(), GuFenOrgDto.class);
                Map<String, List<GuFenOrgDto>> groupMap = list.stream().collect(Collectors.groupingBy(GuFenOrgDto::getOrgtype));
                List<GuFenOrgDto> orgs = new ArrayList<>();
                List<GuFenOrgDto> orgList = groupMap.get("UN");//机构
                if(CollectionUtil.isNotEmpty(orgList)) {
                    orgs.addAll(orgList);
                }
                List<GuFenOrgDto> deptList = groupMap.get("UM");//部门
                if(CollectionUtil.isNotEmpty(deptList)) {
                    orgs.addAll(deptList);
                }
                List<GuFenOrgDto> postList = groupMap.get("@K");//岗位
                //如果是增量的机构、部门
                if(CollectionUtil.isNotEmpty(orgs)) {
                    for (GuFenOrgDto guFenOrgDto : orgs) {
                        if(StringUtils.isBlank(guFenOrgDto.getAsparorgcode())) {
                            log.error("同步增量组织数据异常，该数据没有上级机构code." + guFenOrgDto.toString());
                            continue;
                        }
                        //根据机构id 查询出机构的上级
                        MdmOrg mdmOrg = mdmOrgService.getByCode(guFenOrgDto.getAsparorgcode());
                        if(null == mdmOrg) {
                            log.error("同步增量组织数据异常，该机构数据在本系统中不存在." + guFenOrgDto.toString());
                            continue;
                        }
                        //查询数据库中是否已经存在
                        MdmOrg dbMdmOrg = mdmOrgService.getByCode(guFenOrgDto.getOrgcode());
                        if(null != dbMdmOrg) {
                            MdmOrgDto mdmOrgDto = dozerMapper.map(dbMdmOrg, MdmOrgDto.class);
                            mdmOrgDto.setOrgCode(guFenOrgDto.getOrgcode());
                            mdmOrgDto.setOrgName(guFenOrgDto.getOrgname());
                            mdmOrgDto.setParentId(mdmOrg.id);
                            mdmOrgDto.setParentName(mdmOrg.getOrgName());
                            mdmOrgDto.setOrgType(GuFenOrgTypeEnum.UN.code.equals(guFenOrgDto.getOrgtype()) ? OrgTypeEnum.ORG.code:OrgTypeEnum.DEPT.code);
                            if(StringUtils.isNotBlank(guFenOrgDto.getStarttime())) {
                                mdmOrgDto.setValidStartTime(DateUtil.toLocalDateTime(guFenOrgDto.getStarttime(),df));
                            }
                            if(StringUtils.isNotBlank(guFenOrgDto.getEnd_date())) {
                                mdmOrgDto.setValidEndTime(DateUtil.toLocalDateTime(guFenOrgDto.getEnd_date(),df));
                            }
                            LocalDateTime now = LocalDateTime.now();
                            if(now.compareTo(mdmOrgDto.getValidStartTime()) > 0 && now.compareTo(mdmOrgDto.getValidEndTime()) < 0) {
                                mdmOrgDto.setStatus(StatusEnum.RUNNING.code);
                            } else {
                                mdmOrgDto.setStatus(StatusEnum.CANCELED.code);
                            }
                            mdmOrgDto.setGfOrgcode(guFenOrgDto.getOrgcode());
                            mdmOrgDto.setGfOrgname(guFenOrgDto.getOrgname());
                            mdmOrgDto.setGfOrgtype(guFenOrgDto.getOrgtype());
                            mdmOrgDto.setGfAsparorgcode(guFenOrgDto.getAsparorgcode());
                            mdmOrgDto.setGfStarttime(guFenOrgDto.getStarttime());
                            mdmOrgDto.setGfEndDate(guFenOrgDto.getEnd_date());
                            mdmOrgDto.setGfLastupdatetime(guFenOrgDto.getLastupdatetime());
                            mdmOrgDto.setGfInstitutional(guFenOrgDto.getInstitutional());
                            mdmOrgDto.setGfNorder(guFenOrgDto.getNorder());
//                            mdmOrgDto.setSort(guFenOrgDto.getNorder());
                            List<MdmOrgDto> mdmOrgDtoList = new ArrayList<>();
                            mdmOrgDtoList.add(mdmOrgDto);
                            //同时调用机构的三个修改方法
                            mdmOrgService.justUpdateOrg(mdmOrgDto,true);
                            //todo 改成全量同步 暂时注释掉
//                            mdmOrgService.update(mdmOrgDto,true);
//                            mdmOrgService.updateOrgName(mdmOrgDto);
//                            mdmOrgService.updateParent(mdmOrgDto);
//                            //发送 增量 mq
//                            log.info("修改机构--发送MQ消息内容：" + mdmOrgDto.toString());
//                            this.sendRocketMq(orgTopics,MqTagEnum.EDIT.code,JSON.toJSONString(mdmOrgDto));
                        } else {
                            MdmOrgDto _mdmOrgDto = new MdmOrgDto();
                            _mdmOrgDto.setOrgCode(guFenOrgDto.getOrgcode());
                            _mdmOrgDto.setOrgName(guFenOrgDto.getOrgname());
                            _mdmOrgDto.setParentId(mdmOrg.id);
                            _mdmOrgDto.setParentName(mdmOrg.getOrgName());
                            _mdmOrgDto.setOrgType(GuFenOrgTypeEnum.UN.code.equals(guFenOrgDto.getOrgtype()) ? OrgTypeEnum.ORG.code:OrgTypeEnum.DEPT.code);
                            if(StringUtils.isNotBlank(guFenOrgDto.getStarttime())) {
                                _mdmOrgDto.setValidStartTime(DateUtil.toLocalDateTime(guFenOrgDto.getStarttime(),df));
                            }
                            if(StringUtils.isNotBlank(guFenOrgDto.getEnd_date())) {
                                _mdmOrgDto.setValidEndTime(DateUtil.toLocalDateTime(guFenOrgDto.getEnd_date(),df));
                            }
                            LocalDateTime now = LocalDateTime.now();
                            if(now.compareTo(_mdmOrgDto.getValidStartTime()) > 0 && now.compareTo(_mdmOrgDto.getValidEndTime()) < 0) {
                                _mdmOrgDto.setStatus(StatusEnum.RUNNING.code);
                            } else {
                                _mdmOrgDto.setStatus(StatusEnum.CANCELED.code);
                            }
                            _mdmOrgDto.setGfOrgcode(guFenOrgDto.getOrgcode());
                            _mdmOrgDto.setGfOrgname(guFenOrgDto.getOrgname());
                            _mdmOrgDto.setGfOrgtype(guFenOrgDto.getOrgtype());
                            _mdmOrgDto.setGfAsparorgcode(guFenOrgDto.getAsparorgcode());
                            _mdmOrgDto.setGfStarttime(guFenOrgDto.getStarttime());
                            _mdmOrgDto.setGfEndDate(guFenOrgDto.getEnd_date());
                            _mdmOrgDto.setGfLastupdatetime(guFenOrgDto.getLastupdatetime());
                            _mdmOrgDto.setGfInstitutional(guFenOrgDto.getInstitutional());
                            _mdmOrgDto.setGfNorder(guFenOrgDto.getNorder());
                            _mdmOrgDto.setSort(guFenOrgDto.getNorder());
                            //增量机构信息 geps 默认设置都是部门 OA一级设置为机构 其他设置为部门
                            _mdmOrgDto.setLvType(LvTypeEnum.FUNCTIONAL_DEPARTMENT.code);
                            //判断 OA是否是一级机构  0115固定是中国核工业中原建设有限公司
                            if("0115".equals(mdmOrg.getGfOrgcode())) {
                                _mdmOrgDto.setOaLvType(OaLvTypeEnum.ORG.code);
                            } else {
                                _mdmOrgDto.setOaLvType(OaLvTypeEnum.DEPT.code);
                            }
                            List<MdmOrgDto> mdmOrgDtoList = new ArrayList<>();
                            mdmOrgDtoList.add(_mdmOrgDto);
                            //机构会引用处理DTO 所以重新cp 一个  因为是增量数据 所以循环保存
                            List<MdmOrgDto> tempOrgVos = DozerUtils.mapList(dozerMapper, mdmOrgDtoList, MdmOrgDto.class);
                            mdmOrgService.save(tempOrgVos,true,true);
//                            //发送 增量 mq
//                            log.info("增量机构--发送MQ消息内容：" + tempOrgVos.toString());
//                            this.sendRocketMq(orgTopics,MqTagEnum.ADD.code,JSON.toJSONString(_mdmOrgDto));
                        }
                    }
                }
                //岗位按照 上级code 分组
                if(CollectionUtil.isNotEmpty(postList)) {
                    Map<String, List<GuFenOrgDto>> postMap = postList.stream().collect(Collectors.groupingBy(GuFenOrgDto::getAsparorgcode));
                    log.info("同步增量机构信息完成！");
                    MdmOrgDto getAllMdmOrg = new MdmOrgDto();
                    getAllMdmOrg.setIsDefault(YesNo.NO.code);
                    //getAllMdmOrg.setStatus(StatusEnum.RUNNING.code);
                    getAllMdmOrg.setOrgClass(OrgClassEnum.BIZ.code);
                    List<MdmOrgAllVo> mdmOrgAllVoList = mdmOrgService.vos(getAllMdmOrg);
                    //处理所有机构下的岗位
                    handlePostIncrement(mdmOrgAllVoList,postMap);
                    log.debug("同步增量岗位信息完成！");
                }
            } else {
                log.info("同步股份数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步股份机构、岗位数据失败！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步股份数据失败!"+e.getMessage());
        }finally {

        }
    }

    @Override
    @Transactional
    public boolean syncGuFenUserAndMasterPost() {
        log.info("同步人员、主岗。userUrl:{}",empoyeeUrl);
        if(StringUtils.isBlank(empoyeeUrl))  {
            throw new IncloudException("没有获取到股份人员URL.");
        }
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(empoyeeUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("userName",mdUserName);
            httpPost.setHeader("passWord",mdPassWord);
            StringEntity entity = new StringEntity(defaultReqData, "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) { //true
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                //String strUser = readJsonFile("C:\\Users\\x1c\\Desktop\\股份json数据\\user.json");
                JSONObject objUser = JSON.parseObject(resString);
                JSONArray datasUser = objUser.getJSONArray("datas");
                List<GuFenUserDto> listUser = JSONObject.parseArray(datasUser.toJSONString(), GuFenUserDto.class);
                //查询出所有岗位
                MdmPostDto mdmPostDto = new MdmPostDto();
                List<MdmPostVo> allPost = mdmPostService.lists(mdmPostDto);
                //查询所有机构
                MdmOrgDto getAllMdmOrg = new MdmOrgDto();
                getAllMdmOrg.setIsDefault(YesNo.NO.code);
                //getAllMdmOrg.setStatus(StatusEnum.RUNNING.code);
                getAllMdmOrg.setOrgClass(OrgClassEnum.BIZ.code);
                List<MdmOrgAllVo> mdmOrgAllVoList = mdmOrgService.vos(getAllMdmOrg);
                //获取入库后的所有部门信息
                Map<Integer, List<MdmOrgAllVo>> mdmOrgAllTypeGrop = mdmOrgAllVoList.stream().collect(Collectors.groupingBy(MdmOrgAllVo::getOrgType));
                List<MdmOrgAllVo> allDeptList = mdmOrgAllTypeGrop.get(OrgTypeEnum.DEPT.code);
                //获取部门为空的某些用户
                Map<String, List<GuFenUserDto>> _groupDeptUserMap = listUser.stream().filter(d-> null != d.getOwn_deptcode()).collect(Collectors.groupingBy(GuFenUserDto::getOwn_deptcode));
                //处理人员 以及主岗信息
                handleUser(allDeptList,_groupDeptUserMap,allPost);
                log.debug("同步人员、以及人员主岗信息完成！");
            } else {
                log.info("同步股份数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步股份机构、岗位数据失败！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步股份人员数据失败!"+e.getMessage());
        }finally {

        }
    }

    @Override
    @Transactional
    public boolean syncGuFenUserAndMasterPostIncrement(String curStr) {
        log.info("增量同步人员、主岗。userUrl:{}",empoyeeUrl);
        if(StringUtils.isBlank(empoyeeUrl))  {
            throw new IncloudException("没有获取到股份人员URL.");
        }
        HttpResponse execute = null;
        try {
            //根据当天时间查询 后面一天时间  >curStr < curStr+1
            String tomorrowStr = getTomorrowDay(curStr);//获取明天
//            String queryStr = "{'pageIndex': -1,'pageSize': 20,'criteria': {'_expr[1]/_property':'lastupdatetime','_expr[1]/_value':'"+curStr+"','_expr[1]/_op':'>=','_expr[2]/_property':'lastupdatetime','_expr[2]/_value':'"+tomorrowStr+"','_expr[2]/_op':'<'}}";
            //todo 改成全量新增
            String queryStr = "{'pageIndex': -1,'pageSize': 20}";
            HttpPost httpPost = new HttpPost(empoyeeUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("userName",mdUserName);
            httpPost.setHeader("passWord",mdPassWord);
            StringEntity entity = new StringEntity(queryStr, "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) { // true
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                //log.info("增量 user result！" + resString);
                //处理人员
                //String resString = readJsonFile("C:\\Users\\x1c\\Desktop\\股份json数据\\user.json");
                JSONObject objUser = JSON.parseObject(resString);
                JSONArray datasUser = objUser.getJSONArray("datas");
                List<GuFenUserDto> listUser = JSONObject.parseArray(datasUser.toJSONString(), GuFenUserDto.class);
                //身份证号小写改大写
                listUser.forEach(d->d.setId_card_number(d.getId_card_number().toUpperCase()));
                if(CollectionUtil.isNotEmpty(listUser)) {
                    //过滤人员信息
                    filterUser(listUser);
                    //查询出所有岗位
                    MdmPostDto mdmPostDto = new MdmPostDto();
                    List<MdmPostVo> allPost = mdmPostService.lists(mdmPostDto);

                    //查询所有机构
                    MdmOrgDto getAllMdmOrg = new MdmOrgDto();
                    getAllMdmOrg.setIsDefault(YesNo.NO.code);
                    getAllMdmOrg.setOrgClass(OrgClassEnum.BIZ.code);
                    //getAllMdmOrg.setStatus(StatusEnum.RUNNING.code);
                    List<MdmOrgAllVo> mdmOrgAllVoList = mdmOrgService.vos(getAllMdmOrg);

                    //获取入库后的所有部门信息
                    Map<Integer, List<MdmOrgAllVo>> mdmOrgAllTypeGrop = mdmOrgAllVoList.stream().collect(Collectors.groupingBy(MdmOrgAllVo::getOrgType));
                    //List<MdmOrgAllVo> allDeptList = mdmOrgAllVoList.stream().filter(d->d.getOrgType() == OrgTypeEnum.DEPT.code || d.getOrgType() == OrgTypeEnum.ORG.code).collect(Collectors.toList());
                    List<MdmOrgAllVo> allDeptList = mdmOrgAllTypeGrop.get(OrgTypeEnum.DEPT.code);
                    //获取部门不为空的某些用户
                    Map<String, List<GuFenUserDto>> _groupDeptUserMap = listUser.stream().filter(d-> null != d.getOwn_deptcode()).collect(Collectors.groupingBy(GuFenUserDto::getOwn_deptcode));
                    //处理人员 以及主岗信息
                    handleUserIncrement(allDeptList,_groupDeptUserMap,allPost);
                    log.debug("同步人员、以及人员主岗信息完成--增量！");
                }
            } else {
                log.info("同步股份数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步股份机构、岗位数据失败--增量！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步股份人员数据失败!--增量"+e.getMessage());
        }finally {

        }
    }

    /**
     * 过滤人员信息
     * @param listUser
     */
    private void filterUser(List<GuFenUserDto> listUser){
        //根据身份证号分组
        Map<String, List<GuFenUserDto>> groupIdCardUserMap = listUser.stream().filter(d-> null != d.getId_card_number()).collect(Collectors.groupingBy(GuFenUserDto::getId_card_number));
        List<GuFenUserDto> removeList = new ArrayList<>();
        for (String idCard : groupIdCardUserMap.keySet()){
            List<GuFenUserDto> guFenUserDtos = groupIdCardUserMap.get(idCard);
            //如果一个身份证号 有多条数据 只留lastupdatetime 最后一条
            if (guFenUserDtos.size() > 1) {
                listSort(guFenUserDtos);
                for (int i = 1; i < guFenUserDtos.size(); i++) {//跳过第一条
                    removeList.add(guFenUserDtos.get(i));
                }
            }
        }
        listUser.removeAll(removeList);
    }

    public void handlePositon(List<GuFenPositionDto> listAndPost, List<MdmUserVo> allUsre, List<MdmPostVo> allPost) {
        if(CollectionUtil.isNotEmpty(listAndPost)) {
            List<MdmPostUser> resultList = new ArrayList<>();
            //分组获取user  admin 用户的getGfHrCode 是空
            Map<String, List<MdmUserVo>> _groupUserMap = allUsre.stream().filter(d->StringUtils.isNotBlank(d.getGfHrCode())).collect(Collectors.groupingBy(MdmUserVo::getGfHrCode));
            //分组获取岗位
            Map<String, List<MdmPostVo>> _groupPostMap = allPost.stream().collect(Collectors.groupingBy(MdmPostVo::getPostCode));
            for (GuFenPositionDto guFenPositionDto : listAndPost) {
                MdmPostUser mdmPostUser = new MdmPostUser();
                //获取现有数据库的人员信息
                List<MdmUserVo> mdmUserList = _groupUserMap.get(guFenPositionDto.getHr_code());
                MdmUserVo mdmUserVo = null;
                if(CollectionUtil.isNotEmpty(mdmUserList)) {
                    mdmUserVo = mdmUserList.get(0);
                }
                //获取现有数据的岗位信息
                MdmPostVo mdmPostVo = null;
                List<MdmPostVo> mdmUserVoList = _groupPostMap.get(guFenPositionDto.getPostcode());
                if(CollectionUtil.isNotEmpty(mdmUserVoList)) {
                    mdmPostVo = mdmUserVoList.get(0);
                }
                if(null != mdmUserVo && mdmPostVo != null) {
                    mdmPostUser.setId(IdGenerator.getIdGenerator());
                    mdmPostUser.setOrgFullPostId(mdmPostVo.getOrgFullId());
                    mdmPostUser.setOrgFullPostName(mdmPostVo.getOrgFullName());
                    mdmPostUser.setPostId(mdmPostVo.getId());
                    mdmPostUser.setPostCode(mdmPostVo.getPostCode());
                    mdmPostUser.setPostName(mdmPostVo.getPostName());
                    mdmPostUser.setUserId(mdmUserVo.getId());
                    mdmPostUser.setUserName(mdmUserVo.getUserName());
                    mdmPostUser.setUserNameCh(mdmUserVo.getUserNameCh());
                    mdmPostUser.setIsMaster(YesNo.NO.code);
                    mdmPostUser.setCreateTime(LocalDateTime.now());

                    //判断数据库中是否存在这个兼岗 如果存在删除重来 如果不存在则直接添加
                    List<MdmPostUserVo> mdmPostUserVos = mdmPostUserService.getPositionByUserIdAndPostId(mdmPostUser.getUserId(),mdmPostUser.getPostId(),false);
                    if(CollectionUtil.isNotEmpty(mdmPostUserVos)) {
                        MdmPostUserVo mdmPostUserVo = mdmPostUserVos.get(0);//有且只有一条
                        //删除neo4j关系
                        //neoService.delUserPostRelById(mdmPostUserVo.getId());
                        //删除mysql关系
                        mdmPostUserService.delete(mdmPostUserVo.getId());
                    }
//                // neo4j关联关系
//                MdmUser _mdmUser = new MdmUser();
//                _mdmUser.setId(mdmUserVo.getId());
//                _mdmUser.setUserName(mdmUserVo.getUserName());
//                _mdmUser.setUserNameCh(mdmUserVo.getUserNameCh());
//                _mdmUser.setIdCard(mdmUserVo.getIdCard());
//                MdmPost mdmPost = new MdmPost();
//                mdmPost.setId(mdmPostUser.getPostId());
//                mdmPost.setPostCode(mdmPostUser.getPostCode());
//                mdmPost.setPostName(mdmPostUser.getPostName());
//                neoService.mergeUserPostRel(_mdmUser,mdmPost,mdmPostUser);
                    resultList.add(mdmPostUser);
                }
            }
            if(CollectionUtil.isNotEmpty(resultList)) {
                mdmPostUserService.saveList(resultList);
            }
        }
    }

    /**
     * 处理人员 同时处理主岗信息
     * @param deptList
     * @param _groupDeptUserMap
     */
    public void handleUser(List<MdmOrgAllVo> deptList, Map<String, List<GuFenUserDto>> _groupDeptUserMap,List<MdmPostVo> allPost) {
        List<MdmUserDto> resultList = new ArrayList<>();
        List<MdmPostUserDto> resultPostUserList = new ArrayList<>();
        Map<String, List<MdmPostVo>> groupPostMap = new HashMap<>();
        //入库的所有方位分组
        if(CollectionUtil.isNotEmpty(allPost)) {
            groupPostMap = allPost.stream().collect(Collectors.groupingBy(MdmPostVo::getPostCode));
        }
        for (MdmOrgAllVo mdmOrgAllVo : deptList) {
            List<GuFenUserDto> listUser =  _groupDeptUserMap.get(mdmOrgAllVo.getOrgCode());
            if(CollectionUtil.isNotEmpty(listUser)) {
                for (GuFenUserDto guFenUserDto : listUser) {
                    //组装mdm User
                    MdmUserDto mdmUser = new MdmUserDto();
                    mdmUser.setParentOrgId(mdmOrgAllVo.getParentOrgId());
                    mdmUser.setParentOrgName(mdmOrgAllVo.getParentOrgName());
                    mdmUser.setParentDeptId(mdmOrgAllVo.getId());
                    mdmUser.setParentDeptName(mdmOrgAllVo.getOrgName());
                    mdmUser.setOrgFullId(mdmOrgAllVo.getOrgFullId());
                    mdmUser.setOrgFullName(mdmOrgAllVo.getOrgFullName());
                    mdmUser.setParentOrgFullName(mdmOrgAllVo.getParentOrgFullName());
                    mdmUser.setParentDeptFullName(mdmOrgAllVo.getParentDeptFullName());
                    mdmUser.setUserName("u" + guFenUserDto.getId_card_number()); //同步宏景登录 固定添加小写u
                    mdmUser.setUserNameCh(guFenUserDto.getEmployee_name());
                    mdmUser.setCardType(0);//默认身份证号
                    mdmUser.setSex(StringUtils.isNotBlank(guFenUserDto.getGender())?Integer.valueOf(guFenUserDto.getGender()):null);
                    mdmUser.setIdCard(guFenUserDto.getId_card_number());
                    mdmUser.setNativePlace(guFenUserDto.getNative_place());
                    mdmUser.setNation(guFenUserDto.getEthnic_group());
                    mdmUser.setMarriageStatus(guFenUserDto.getMarital_status());//todo 对应字典不知道是那个
                    mdmUser.setPhoneNum(guFenUserDto.getContact_phone());
                    mdmUser.setOfficePhone(guFenUserDto.getPhone());
                    mdmUser.setEmail(guFenUserDto.getEmail());
                    mdmUser.setAddr(guFenUserDto.getRegistered_residence());
                    mdmUser.setBirthNature(StringUtils.isNotBlank(guFenUserDto.getRegistered_residence_type())?Integer.valueOf(guFenUserDto.getRegistered_residence_type()):null);
                    mdmUser.setEducation(guFenUserDto.getDegree_education());
                    mdmUser.setEmploymentType(EmploymentTypeEnum.CONTRACT_USER.code);
                    mdmUser.setGfHrCode(guFenUserDto.getHr_code());
                    mdmUser.setGfPost(guFenUserDto.getPost());
                    mdmUser.setGfEntryTime(guFenUserDto.getEntry_time());
                    mdmUser.setGfAge(Integer.valueOf(guFenUserDto.getAge()));
                    mdmUser.setGfTitle(guFenUserDto.getTitle());
                    mdmUser.setGfTitleLevel(guFenUserDto.getTitle_level());
                    mdmUser.setGfEmployeeType(guFenUserDto.getEmployee_type());
                    mdmUser.setGfEmployeeDict(guFenUserDto.getEmployee_dict());
                    mdmUser.setGfEmployeeStatus(guFenUserDto.getEmployee_status());
                    mdmUser.setGfPoliticalOutlook(guFenUserDto.getPolitical_outlook());
                    mdmUser.setGfGraduationTime(guFenUserDto.getGraduation_time());
                    mdmUser.setGfDegreeEducation(guFenUserDto.getDegree_education());
                    mdmUser.setGfJobTime(guFenUserDto.getJob_time());
                    mdmUser.setGfJobclassification(guFenUserDto.getJobclassification());
                    mdmUser.setGfTalentcategory(guFenUserDto.getTalentcategory());
                    mdmUser.setGfProvince(guFenUserDto.getProvince());
                    mdmUser.setGfQuitTime(guFenUserDto.getQuit_time());
                    mdmUser.setGfAdministrativeOrg(guFenUserDto.getAdministrative_org());
                    mdmUser.setGfNorder(Integer.valueOf(guFenUserDto.getNorder()));
                    mdmUser.setNationality("156");
                    mdmUser.setStatus(StringUtils.isNotBlank(guFenUserDto.getEmployee_status()) ? Integer.valueOf(guFenUserDto.getEmployee_status()):2); //股份 1在职 2离职；本系统： 1启用 2停用
                    //处理岗位
                    if(CollectionUtil.isNotEmpty(groupPostMap)) {
                        List<MdmPostVo> mdmPostVoList = groupPostMap.get(guFenUserDto.getPost());
                        if(CollectionUtil.isNotEmpty(mdmPostVoList)) {
                            MdmPostVo mdmPostVo = mdmPostVoList.get(0);
                            MdmPostUserDto mdmPostUserDto = new MdmPostUserDto();
                            mdmPostUserDto.setId(IdGenerator.getIdGenerator());
                            mdmPostUserDto.setOrgFullPostId(mdmPostVo.getOrgFullId());
                            mdmPostUserDto.setOrgFullPostName(mdmPostVo.getOrgFullName());
                            mdmPostUserDto.setPostId(mdmPostVo.getId());
                            mdmPostUserDto.setPostCode(mdmPostVo.getPostCode());
                            mdmPostUserDto.setPostName(mdmPostVo.getPostName());
                            mdmPostUserDto.setUserId(mdmUser.getId());
                            mdmPostUserDto.setUserName(mdmUser.getUserName());
                            mdmPostUserDto.setUserNameCh(mdmUser.getUserNameCh());
                            mdmPostUserDto.setIsMaster(YesNo.YES.code);
                            mdmPostUserDto.setCreateTime(LocalDateTime.now());
                            resultPostUserList.add(mdmPostUserDto);
                            mdmUser.setMdmPostUserDto(mdmPostUserDto);
                        }
                    }
                    resultList.add(mdmUser);
                }
            }
        }
        if(CollectionUtil.isNotEmpty(resultList)) {
            mdmUserService.saveList(resultList);
        }
    }

    /**
     * 人员关联在线学习学员角色
     * @param studyRole
     * @param user
     */
    private void addStudyRole(MdmRoleVo studyRole , MdmUserDto user){
        //查询该人员是否拥有在线学习角色
        List<String> roleCodeList = new ArrayList<>();
        roleCodeList.add("STUDY_ADMIN");
        roleCodeList.add("STUDY_TEACHER");
        roleCodeList.add("STUDY_STUDENT");
        LambdaQueryWrapper<MdmRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
        roleUserWrapper.in(MdmRoleUser::getRoleCode,roleCodeList);
        roleUserWrapper.eq(MdmRoleUser::getUserId,user.getId());
        Integer roleUserSize = roleUserMapper.selectCount(roleUserWrapper);
        if (roleUserSize == 0) {
            if (ObjectUtils.isNotEmpty(user.getId()) && ObjectUtils.isNotEmpty(studyRole.getId())) {
                //如果没有添加在线学习学员角色
                MdmRoleUser roleUser = new MdmRoleUser();
                roleUser.setId(IdGenerator.getIdGenerator());
                roleUser.setRoleId(studyRole.getId());
                roleUser.setRoleCode(studyRole.getRoleCode());
                roleUser.setRoleName(studyRole.getRoleName());
                roleUser.setUserId(user.getId());
                roleUser.setUserName(user.getUserName());
                roleUser.setUserNameCh(user.getUserNameCh());
                int insert = roleUserMapper.insert(roleUser);
                if (insert > 0) {
                    log.debug("添加在线学习默认学员角色成功，用户：{}",user.getUserName());
                }
            }else {
                log.debug("添加在线学习默认学员角色失败，用户：{}",user.getUserName());
            }
        }
    }

    /**
     * 处理人员 同时处理主岗信息
     * @param deptList
     * @param _groupDeptUserMap
     */
    public void handleUserIncrement(List<MdmOrgAllVo> deptList, Map<String, List<GuFenUserDto>> _groupDeptUserMap,List<MdmPostVo> allPost) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Map<String, List<MdmPostVo>> groupPostMap = new HashMap<>();
        //入库的所有岗位分组
        if(CollectionUtil.isNotEmpty(allPost)) {
            groupPostMap = allPost.stream().collect(Collectors.groupingBy(MdmPostVo::getPostCode));
        }
        //查询所有数据库中用户信息
        List<MdmUserVo> dataUserList =  mdmUserService.lists(new MdmUserDto());
        Map<String, List<MdmUserVo>> mdmOrgAllTypeGrop = dataUserList.stream().collect(Collectors.groupingBy(MdmUserVo::getIdCard));
        for (MdmOrgAllVo mdmOrgAllVo : deptList) {
            List<GuFenUserDto> listUser =  _groupDeptUserMap.get(mdmOrgAllVo.getOrgCode());
            if(CollectionUtil.isNotEmpty(listUser)) {
                //获取在线学习学员角色信息 下面添加在线学习学员角色使用 lhy
                MdmRoleVo studyStudentRoleVo = roleService.getByCode("STUDY_STUDENT");
                for (GuFenUserDto guFenUserDto : listUser) {
                    //如果是数据库中存在则修改 不存在则保存
                    //MdmUserVo _mdmUser = mdmUserService.getByIdCard();
                    List<MdmUserVo> _mdmUserList = mdmOrgAllTypeGrop.get(guFenUserDto.getId_card_number());
                    if(CollectionUtil.isNotEmpty(_mdmUserList)) {
                        MdmUserVo _mdmUser=_mdmUserList.get(0);
                        //组装mdm User
                        _mdmUser.setParentOrgId(mdmOrgAllVo.getParentOrgId());
                        _mdmUser.setParentOrgName(mdmOrgAllVo.getParentOrgName());
                        _mdmUser.setParentDeptId(mdmOrgAllVo.getId());
                        _mdmUser.setParentDeptName(mdmOrgAllVo.getOrgName());
                        _mdmUser.setOrgFullId(mdmOrgAllVo.getOrgFullId() + defaultJoinName + _mdmUser.getParentDeptId());
                        _mdmUser.setOrgFullName(mdmOrgAllVo.getOrgFullName() + defaultJoinName + _mdmUser.getParentDeptName());
                        _mdmUser.setParentOrgFullName(mdmOrgAllVo.getParentOrgFullName());
                        if(StringUtils.isNotBlank(mdmOrgAllVo.getParentDeptFullName())) { //如果父级为空 则是直接父级 父级不为空则拼上自己
                            _mdmUser.setParentDeptFullName(mdmOrgAllVo.getParentDeptFullName() + defaultJoinName + mdmOrgAllVo.getOrgName());
                        } else {
                            _mdmUser.setParentDeptFullName(mdmOrgAllVo.getParentDeptFullName());
                        }
                        //_mdmUser.setParentDeptFullName(mdmOrgAllVo.getParentDeptFullName());
                        _mdmUser.setUserName("u" + guFenUserDto.getId_card_number());
                        _mdmUser.setUserNameCh(guFenUserDto.getEmployee_name());
                        _mdmUser.setCardType(null);
                        _mdmUser.setSex(StringUtils.isNotBlank(guFenUserDto.getGender())?Integer.valueOf(guFenUserDto.getGender()):null);
                        _mdmUser.setIdCard(guFenUserDto.getId_card_number());
                        _mdmUser.setNativePlace(guFenUserDto.getNative_place());
                        _mdmUser.setNation(guFenUserDto.getEthnic_group());
                        _mdmUser.setMarriageStatus(guFenUserDto.getMarital_status());//
                        //_mdmUser.setPhoneNum(guFenUserDto.getContact_phone()); //todo 上线后则不同步增量手机号 联系电话不同步修改
                        _mdmUser.setOfficePhone(guFenUserDto.getPhone());
                        _mdmUser.setEmail(guFenUserDto.getEmail());
                        _mdmUser.setAddr(guFenUserDto.getRegistered_residence());
                        _mdmUser.setBirthNature(StringUtils.isNotBlank(guFenUserDto.getRegistered_residence_type())?Integer.valueOf(guFenUserDto.getRegistered_residence_type()):null);
                        _mdmUser.setEducation(guFenUserDto.getDegree_education());
                        _mdmUser.setEmploymentType(EmploymentTypeEnum.CONTRACT_USER.code);
                        _mdmUser.setGfHrCode(guFenUserDto.getHr_code());
                        _mdmUser.setGfPost(guFenUserDto.getPost());
                        _mdmUser.setGfEntryTime(guFenUserDto.getEntry_time());
                        _mdmUser.setGfAge(Integer.valueOf(guFenUserDto.getAge()));
                        _mdmUser.setGfTitle(guFenUserDto.getTitle());
                        _mdmUser.setGfTitleLevel(guFenUserDto.getTitle_level());
                        _mdmUser.setGfEmployeeType(guFenUserDto.getEmployee_type());
                        _mdmUser.setGfEmployeeDict(guFenUserDto.getEmployee_dict());
                        _mdmUser.setGfEmployeeStatus(guFenUserDto.getEmployee_status());
                        _mdmUser.setGfPoliticalOutlook(guFenUserDto.getPolitical_outlook());
                        _mdmUser.setGfGraduationTime(guFenUserDto.getGraduation_time());
                        _mdmUser.setGfDegreeEducation(guFenUserDto.getDegree_education());
                        _mdmUser.setGfJobTime(guFenUserDto.getJob_time());
                        _mdmUser.setGfJobclassification(guFenUserDto.getJobclassification());
                        _mdmUser.setGfTalentcategory(guFenUserDto.getTalentcategory());
                        _mdmUser.setGfProvince(guFenUserDto.getProvince());
                        _mdmUser.setGfQuitTime(guFenUserDto.getQuit_time());
                        _mdmUser.setGfAdministrativeOrg(guFenUserDto.getAdministrative_org());
                        _mdmUser.setGfNorder(Integer.valueOf(guFenUserDto.getNorder()));
                        _mdmUser.setNationality("156");
                        _mdmUser.setStatus(StringUtils.isNotBlank(guFenUserDto.getEmployee_status()) ? Integer.valueOf(guFenUserDto.getEmployee_status()):2);
                        MdmUserDto mdmUserDto = dozerMapper.map(_mdmUser, MdmUserDto.class);
                        //处理岗位
                        if(CollectionUtil.isNotEmpty(groupPostMap)) {
                            List<MdmPostVo> mdmPostVoList = groupPostMap.get(guFenUserDto.getPost());
                            if(CollectionUtil.isNotEmpty(mdmPostVoList)) {
                                MdmPostVo mdmPostVo = mdmPostVoList.get(0);
                                MdmPostUserDto mdmPostUserDto = new MdmPostUserDto();
                                mdmPostUserDto.setId(IdGenerator.getIdGenerator());
                                mdmPostUserDto.setOrgFullPostId(mdmPostVo.getOrgFullId());
                                mdmPostUserDto.setOrgFullPostName(mdmPostVo.getOrgFullName());
                                mdmPostUserDto.setPostId(mdmPostVo.getId());
                                mdmPostUserDto.setPostCode(mdmPostVo.getPostCode());
                                mdmPostUserDto.setPostName(mdmPostVo.getPostName());
                                mdmPostUserDto.setUserId(_mdmUser.getId());
                                mdmPostUserDto.setUserName(_mdmUser.getUserName());
                                mdmPostUserDto.setUserNameCh(_mdmUser.getUserNameCh());
                                mdmPostUserDto.setIsMaster(YesNo.YES.code);
                                mdmPostUserDto.setCreateTime(LocalDateTime.now());
                                mdmUserDto.setMdmPostUserDto(mdmPostUserDto);
                            }
                        }
                        mdmUserService.update(mdmUserDto,true);
                        //添加在线学习学员角色
                        addStudyRole(studyStudentRoleVo,mdmUserDto);
                    } else {
                        //组装mdm User
                        MdmUserDto mdmUser = new MdmUserDto();
                        mdmUser.setParentOrgId(mdmOrgAllVo.getParentOrgId());
                        mdmUser.setParentOrgName(mdmOrgAllVo.getParentOrgName());
                        mdmUser.setParentDeptId(mdmOrgAllVo.getId());
                        mdmUser.setParentDeptName(mdmOrgAllVo.getOrgName());
                        mdmUser.setOrgFullId(mdmOrgAllVo.getOrgFullId());
                        mdmUser.setOrgFullName(mdmOrgAllVo.getOrgFullName());
                        mdmUser.setParentOrgFullName(mdmOrgAllVo.getParentOrgFullName());
                        mdmUser.setParentDeptFullName(mdmOrgAllVo.getParentDeptFullName());
                        mdmUser.setUserName("u" + guFenUserDto.getId_card_number());
                        mdmUser.setUserNameCh(guFenUserDto.getEmployee_name());
                        mdmUser.setCardType(null);
                        mdmUser.setSex(StringUtils.isNotBlank(guFenUserDto.getGender())?Integer.valueOf(guFenUserDto.getGender()):null);
                        mdmUser.setIdCard(guFenUserDto.getId_card_number());
                        mdmUser.setNativePlace(guFenUserDto.getNative_place());
                        mdmUser.setNation(guFenUserDto.getEthnic_group());
                        mdmUser.setMarriageStatus(guFenUserDto.getMarital_status());//todo 对应字典不知道是那个
                        mdmUser.setPhoneNum(guFenUserDto.getContact_phone());
                        mdmUser.setOfficePhone(guFenUserDto.getPhone());
                        mdmUser.setEmail(guFenUserDto.getEmail());
                        mdmUser.setAddr(guFenUserDto.getRegistered_residence());
                        mdmUser.setBirthNature(StringUtils.isNotBlank(guFenUserDto.getRegistered_residence_type())?Integer.valueOf(guFenUserDto.getRegistered_residence_type()):null);
                        mdmUser.setEducation(guFenUserDto.getDegree_education());
                        mdmUser.setEmploymentType(EmploymentTypeEnum.CONTRACT_USER.code);
                        mdmUser.setGfHrCode(guFenUserDto.getHr_code());
                        mdmUser.setGfPost(guFenUserDto.getPost());
                        mdmUser.setGfEntryTime(guFenUserDto.getEntry_time());
                        mdmUser.setGfAge(Integer.valueOf(guFenUserDto.getAge()));
                        mdmUser.setGfTitle(guFenUserDto.getTitle());
                        mdmUser.setGfTitleLevel(guFenUserDto.getTitle_level());
                        mdmUser.setGfEmployeeType(guFenUserDto.getEmployee_type());
                        mdmUser.setGfEmployeeDict(guFenUserDto.getEmployee_dict());
                        mdmUser.setGfEmployeeStatus(guFenUserDto.getEmployee_status());
                        mdmUser.setGfPoliticalOutlook(guFenUserDto.getPolitical_outlook());
                        mdmUser.setGfGraduationTime(guFenUserDto.getGraduation_time());
                        mdmUser.setGfDegreeEducation(guFenUserDto.getDegree_education());
                        mdmUser.setGfJobTime(guFenUserDto.getJob_time());
                        mdmUser.setGfJobclassification(guFenUserDto.getJobclassification());
                        mdmUser.setGfTalentcategory(guFenUserDto.getTalentcategory());
                        mdmUser.setGfProvince(guFenUserDto.getProvince());
                        mdmUser.setGfQuitTime(guFenUserDto.getQuit_time());
                        mdmUser.setGfAdministrativeOrg(guFenUserDto.getAdministrative_org());
                        mdmUser.setGfNorder(Integer.valueOf(guFenUserDto.getNorder()));
                        mdmUser.setNationality("156");
                        mdmUser.setStatus(StringUtils.isNotBlank(guFenUserDto.getEmployee_status()) ? Integer.valueOf(guFenUserDto.getEmployee_status()):2);
                        MdmUserDto mdmUserDto = dozerMapper.map(mdmUser, MdmUserDto.class);
                        //处理岗位
                        if(CollectionUtil.isNotEmpty(groupPostMap)) {
                            List<MdmPostVo> mdmPostVoList = groupPostMap.get(guFenUserDto.getPost());
                            if(CollectionUtil.isNotEmpty(mdmPostVoList)) {
                                MdmPostVo mdmPostVo = mdmPostVoList.get(0);
                                MdmPostUserDto mdmPostUserDto = new MdmPostUserDto();
                                mdmPostUserDto.setId(IdGenerator.getIdGenerator());
                                mdmPostUserDto.setOrgFullPostId(mdmPostVo.getOrgFullId());
                                mdmPostUserDto.setOrgFullPostName(mdmPostVo.getOrgFullName());
                                mdmPostUserDto.setPostId(mdmPostVo.getId());
                                mdmPostUserDto.setPostCode(mdmPostVo.getPostCode());
                                mdmPostUserDto.setPostName(mdmPostVo.getPostName());
                                mdmPostUserDto.setUserId(mdmUserDto.getId());
                                mdmPostUserDto.setUserName(mdmUserDto.getUserName());
                                mdmPostUserDto.setUserNameCh(mdmUserDto.getUserNameCh());
                                mdmPostUserDto.setIsMaster(YesNo.YES.code);
                                mdmPostUserDto.setCreateTime(LocalDateTime.now());
                                mdmUserDto.setMdmPostUserDto(mdmPostUserDto);
                            }
                        }
                        mdmUserService.save(mdmUserDto);
                        //添加在线学习学员角色
                        addStudyRole(studyStudentRoleVo,mdmUserDto);
                    }
                }
            }
        }
    }

    /**
     * 处理岗位
     * @param mdmOrgAllVoList 数据库中的所有组织信息
     * @param postMap 同步过来的所有岗位信息
     */
    public void handlePost(List<MdmOrgAllVo> mdmOrgAllVoList,Map<String, List<GuFenOrgDto>> postMap) {
        List<MdmPostDto> mdmPostDtoList = new ArrayList<>();
        for (MdmOrgAllVo mdmOrgAllVo : mdmOrgAllVoList) {
            //获取该机构下的所有岗
            List<GuFenOrgDto> postList = postMap.get(mdmOrgAllVo.getOrgCode());
            if(CollectionUtil.isNotEmpty(postList)) {
                for (GuFenOrgDto guFenOrgDto : postList) {
                    MdmPostDto mdmPostDto = new MdmPostDto();
                    mdmPostDto.setParentDeptId(mdmOrgAllVo.getId());
                    mdmPostDto.setParentDeptName(mdmOrgAllVo.getOrgName());
                    mdmPostDto.setParentOrgId(mdmOrgAllVo.getStatus() == OrgTypeEnum.ORG.code?mdmOrgAllVo.getId():mdmOrgAllVo.getParentOrgId());
                    mdmPostDto.setParentOrgName(mdmOrgAllVo.getStatus() == OrgTypeEnum.ORG.code?mdmOrgAllVo.getOrgName():mdmOrgAllVo.getParentOrgName());
                    mdmPostDto.setParentOrgFullName(mdmOrgAllVo.getParentOrgFullName());
                    mdmPostDto.setParentDeptFullName(mdmOrgAllVo.getParentDeptFullName());
                    mdmPostDto.setOrgFullId(mdmOrgAllVo.getOrgFullId());
                    mdmPostDto.setOrgFullName(mdmOrgAllVo.getOrgFullName());
                    mdmPostDto.setPostCode(guFenOrgDto.getOrgcode());
                    mdmPostDto.setPostName(guFenOrgDto.getOrgname());
                    mdmPostDto.setStatus(YesNo.YES.code);
                    mdmPostDto.setUpdateTime(LocalDateTime.now());
                    mdmPostDto.setSort(guFenOrgDto.getNorder());
                    if(StringUtils.isNotBlank(guFenOrgDto.getStarttime())) {
                        mdmPostDto.setValidStartTime(DateUtil.toLocalDateTime(guFenOrgDto.getStarttime(),df));
                    }
                    if(StringUtils.isNotBlank(guFenOrgDto.getEnd_date())) {
                        mdmPostDto.setValidEndTime(DateUtil.toLocalDateTime(guFenOrgDto.getEnd_date(),df));
                    }
                    LocalDateTime now = LocalDateTime.now();
                    if(now.compareTo(mdmPostDto.getValidStartTime()) > 0 && now.compareTo(mdmPostDto.getValidEndTime()) < 0) {
                        mdmPostDto.setStatus(YesNo.YES.code);
                    } else {
                        mdmPostDto.setStatus(YesNo.NO.code);
                    }
                    mdmPostDtoList.add(mdmPostDto);
                }
            }
        }
        if(CollectionUtil.isNotEmpty(mdmPostDtoList)) {
            mdmPostService.save(mdmPostDtoList);
        }
    }

    /**
     * 处理岗位 -- 增量
     * @param mdmOrgAllVoList 数据库中的所有组织信息
     * @param postMap 同步过来的所有岗位信息
     */
    public void handlePostIncrement(List<MdmOrgAllVo> mdmOrgAllVoList,Map<String, List<GuFenOrgDto>> postMap) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        List<MdmPostDto> mdmPostDtoList = new ArrayList<>();
        for (MdmOrgAllVo mdmOrgAllVo : mdmOrgAllVoList) {
            //获取该机构下的所有岗
            List<GuFenOrgDto> postList = postMap.get(mdmOrgAllVo.getOrgCode());
            if(CollectionUtil.isNotEmpty(postList)) {
                for (GuFenOrgDto guFenOrgDto : postList) {
                    //判断该岗位在db中是否存在 如果存在则修改
                    MdmPost mdmPost = mdmPostService.getByPostCode(guFenOrgDto.getOrgcode());
                    if(null != mdmPost) {
                        MdmPostDto mdmPostDto = dozerMapper.map(mdmPost, MdmPostDto.class);
                        mdmPostDto.setParentDeptId(mdmOrgAllVo.getId());
                        mdmPostDto.setParentDeptName(mdmOrgAllVo.getOrgName());
                        mdmPostDto.setParentOrgId(mdmOrgAllVo.getStatus() == OrgTypeEnum.ORG.code?mdmOrgAllVo.getId():mdmOrgAllVo.getParentOrgId());
                        mdmPostDto.setParentOrgName(mdmOrgAllVo.getStatus() == OrgTypeEnum.ORG.code?mdmOrgAllVo.getOrgName():mdmOrgAllVo.getParentOrgName());
                        mdmPostDto.setParentOrgFullName(mdmOrgAllVo.getParentOrgFullName());
                        mdmPostDto.setParentDeptFullName(mdmOrgAllVo.getParentDeptFullName());
                        mdmPostDto.setOrgFullId(mdmOrgAllVo.getOrgFullId());
                        mdmPostDto.setOrgFullName(mdmOrgAllVo.getOrgFullName());
                        mdmPostDto.setPostCode(guFenOrgDto.getOrgcode());
                        mdmPostDto.setPostName(guFenOrgDto.getOrgname());
                        mdmPostDto.setSort(guFenOrgDto.getNorder());
                        mdmPostDto.setStatus(YesNo.YES.code);
                        if(StringUtils.isNotBlank(guFenOrgDto.getStarttime())) {
                            mdmPostDto.setValidStartTime(DateUtil.toLocalDateTime(guFenOrgDto.getStarttime(),df));
                        }
                        if(StringUtils.isNotBlank(guFenOrgDto.getEnd_date())) {
                            mdmPostDto.setValidEndTime(DateUtil.toLocalDateTime(guFenOrgDto.getEnd_date(),df));
                        }
                        LocalDateTime now = LocalDateTime.now();
                        if(now.compareTo(mdmPostDto.getValidStartTime()) > 0 && now.compareTo(mdmPostDto.getValidEndTime()) < 0) {
                            mdmPostDto.setStatus(YesNo.YES.code);
                        } else {
                            mdmPostDto.setStatus(YesNo.NO.code);
                        }
                        mdmPostService.update(mdmPostDto);
//                        //发送 增量 mq
//                        log.info("修改岗位--发送MQ消息内容：" + mdmPostDto.toString());
//                        this.sendRocketMq(postTopics,MqTagEnum.EDIT.code,JSON.toJSONString(mdmPostDto));
                    } else {
                        MdmPostDto mdmPostDto = new MdmPostDto();
                        mdmPostDto.setParentDeptId(mdmOrgAllVo.getId());
                        mdmPostDto.setParentDeptName(mdmOrgAllVo.getOrgName());
                        mdmPostDto.setParentOrgId(mdmOrgAllVo.getStatus() == OrgTypeEnum.ORG.code?mdmOrgAllVo.getId():mdmOrgAllVo.getParentOrgId());
                        mdmPostDto.setParentOrgName(mdmOrgAllVo.getStatus() == OrgTypeEnum.ORG.code?mdmOrgAllVo.getOrgName():mdmOrgAllVo.getParentOrgName());
                        mdmPostDto.setParentOrgFullName(mdmOrgAllVo.getParentOrgFullName());
                        mdmPostDto.setParentDeptFullName(mdmOrgAllVo.getParentDeptFullName());
                        mdmPostDto.setOrgFullId(mdmOrgAllVo.getOrgFullId());
                        mdmPostDto.setOrgFullName(mdmOrgAllVo.getOrgFullName());
                        mdmPostDto.setPostCode(guFenOrgDto.getOrgcode());
                        mdmPostDto.setPostName(guFenOrgDto.getOrgname());
                        mdmPostDto.setSort(guFenOrgDto.getNorder());
                        if(StringUtils.isNotBlank(guFenOrgDto.getStarttime())) {
                            mdmPostDto.setValidStartTime(DateUtil.toLocalDateTime(guFenOrgDto.getStarttime(),df));
                        }
                        if(StringUtils.isNotBlank(guFenOrgDto.getEnd_date())) {
                            mdmPostDto.setValidEndTime(DateUtil.toLocalDateTime(guFenOrgDto.getEnd_date(),df));
                        }
                        LocalDateTime now = LocalDateTime.now();
                        if(now.compareTo(mdmPostDto.getValidStartTime()) > 0 && now.compareTo(mdmPostDto.getValidEndTime()) < 0) {
                            mdmPostDto.setStatus(YesNo.YES.code);
                        } else {
                            mdmPostDto.setStatus(YesNo.NO.code);
                        }
                        mdmPostDtoList.add(mdmPostDto);
//                        //发送 增量 mq
//                        log.info("增量岗位--发送MQ消息内容：" + mdmPostDto.toString());
//                        this.sendRocketMq(postTopics,MqTagEnum.ADD.code,JSON.toJSONString(mdmPostDto));
                    }
                }
            }
        }
        if(CollectionUtil.isNotEmpty(mdmPostDtoList)) {
            mdmPostService.save(mdmPostDtoList);
        }
    }

    //按照层级 递归 保存机构信息
    public void saveOrg(List<MdmOrgDto> mdmOrgDtoList) {
        if(CollectionUtil.isNotEmpty(mdmOrgDtoList)) {
            //机构会引用处理DTO 所以重新cp 一个
            List<MdmOrgDto> tempOrgVos = DozerUtils.mapList(dozerMapper, mdmOrgDtoList, MdmOrgDto.class);
            //级别类型 默认添加 中原建设的级别信息 建设公司
            mdmOrgService.save(tempOrgVos,false,true);
            for (MdmOrgDto mdmOrgDto : mdmOrgDtoList) {
                if(CollectionUtil.isNotEmpty(mdmOrgDto.getMdmOrgDtoList())) {
                    saveOrg(mdmOrgDto.getMdmOrgDtoList());
                }
            }
        }
    }

    public static void main1(String[] args) {
        String str = readJsonFile("C:\\Users\\x1c\\Desktop\\股份json数据\\org.json");
        JSONObject obj = JSON.parseObject(str);
        JSONArray datas = obj.getJSONArray("datas");
        List<GuFenOrgDto> list = JSONObject.parseArray(datas.toJSONString(), GuFenOrgDto.class);
        //List<String> map = list.stream().map(GuFenOrgDto::getOrgcode).collect(Collectors.toList());
        Map<String, List<GuFenOrgDto>> groupMap = list.stream().collect(Collectors.groupingBy(GuFenOrgDto::getOrgtype));
        List<GuFenOrgDto> orgList = groupMap.get("UN");//机构
        List<GuFenOrgDto> deptList = groupMap.get("UM");//部门
        List<GuFenOrgDto> postList = groupMap.get("@K");//岗位
        //机构 上级code  分组
        Map<String, List<GuFenOrgDto>> orgMap = orgList.stream().collect(Collectors.groupingBy(GuFenOrgDto::getAsparorgcode));
        int a = 0;
        for (Map.Entry<String, List<GuFenOrgDto>> entry : orgMap.entrySet()) {
            List<GuFenOrgDto>  _entry = entry.getValue();
            a= a+_entry.size();
            //System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
        System.out.println("=====" + a);
        //岗位按照 上级code 分组
//        Map<String, List<GuFenOrgDto>> postMap = postList.stream().collect(Collectors.groupingBy(GuFenOrgDto::getAsparorgcode));
//        MdmOrgDto mdmOrgDto = new MdmOrgDto();
//        mdmOrgDto.id = 0L;
//        mdmOrgDto.setOrgCode("root");
//        mdmOrgDto.setOrgName("root");
//        mdmOrgDto.setParentId(-1L);
//        mdmOrgDto.setParentName("root");
//        mdmOrgDto.setOrgType(1);
//        loopOrgInfo2(mdmOrgDto,orgMap,"01",tempList);
//        List<GuFenOrgDto> tempOrgVos = new ArrayList<>();
//        for (int i = 0; i < orgList.size(); i++) {
//            GuFenOrgDto GuFenOrgDto = orgList.get(i);
//            List<GuFenOrgDto> GuFenOrgDtoList = tempList.stream().filter(d->d.getOrgcode().equals(GuFenOrgDto.getOrgcode())).collect(Collectors.toList());
//            if(CollectionUtil.isNotEmpty(GuFenOrgDtoList)) {
//
//            } else {
//                tempOrgVos.add(GuFenOrgDto);
//            }
//        }
//        System.out.println(tempList);
//        System.out.println(orgList);
//        System.out.println(tempOrgVos);

//        for (GuFenOrgDto guFenOrgDto : orgList) {
//            List<GuFenOrgDto> _list =  postMap.get(guFenOrgDto.getOrgcode());
//            if(CollectionUtil.isNotEmpty(_list)) {
//                System.out.println(guFenOrgDto.getOrgname()+"   下存在岗位CODE！" + _list.get(0));
//            }
//        }
        //人员
        String strUser = readJsonFile("C:\\Users\\x1c\\Desktop\\股份json数据\\user.json");
        JSONObject objUser = JSON.parseObject(strUser);
        JSONArray datasUser = objUser.getJSONArray("datas");
        List<GuFenUserDto> listUser = JSONObject.parseArray(datasUser.toJSONString(), GuFenUserDto.class);

        //人员根据部门分组
        Map<String, List<GuFenUserDto>> groupDeptUserMap = listUser.stream().collect(Collectors.groupingBy(GuFenUserDto::getOwn_deptcode));
        //获取部门为空的某些用户
        List<GuFenUserDto> _notDeptNullListUser =  listUser.stream().filter(d-> null == d.getOwn_deptcode() || "".equals(d.getOwn_deptcode()) ).collect(Collectors.toList());

        //post
        List<GuFenUserDto> notPostNullListUser =  listUser.stream().filter(d-> d.getPost() != null).collect(Collectors.toList());
        Map<String, List<GuFenUserDto>> groupUserMap = notPostNullListUser.stream().collect(Collectors.groupingBy(GuFenUserDto::getPost));
        //机构code
        Map<String, List<GuFenUserDto>> groupUnUserMap = notPostNullListUser.stream().collect(Collectors.groupingBy(GuFenUserDto::getOwn_unitcode));

        //根据机构下的岗 找对应的人
//        for (GuFenOrgDto guFenOrgDto : orgList) {
//            //取机构下存在的岗
//            List<GuFenOrgDto> _list =  postMap.get(guFenOrgDto.getOrgcode());
//            if(CollectionUtil.isNotEmpty(_list)) {
//                for (GuFenOrgDto fenOrgDto : _list) {
//                    //根据岗位找对应人
//                    List<GuFenUserDto> __list = groupUserMap.get(fenOrgDto.getOrgcode());
//                    if(CollectionUtil.isNotEmpty(__list)) {
//                        System.out.println("机构：" + guFenOrgDto.getOrgname()+"("+guFenOrgDto.getOrgcode()+");岗位:"+fenOrgDto.getOrgname()+"("+fenOrgDto.getOrgcode()+"). 人员：" + __list .get(0).getEmployee_name() +"("+__list .get(0).getHr_code()+")");
//                    }
//                }
//            }
//        }
        //根据部门 找对应的人员
        for (GuFenOrgDto guFenOrgDto : deptList) {
            List<GuFenUserDto> _list =  groupDeptUserMap.get(guFenOrgDto.getOrgcode());
            if(CollectionUtil.isNotEmpty(_list)) {
                for (GuFenUserDto guFenUserDto : _list) {
                    System.out.println("部门：" + guFenOrgDto.getOrgname()+".人员：" +guFenUserDto.getEmployee_name());
                }

            }
        }
        //兼岗信息
//        String strPosition = readJsonFile("C:\\Users\\x1c\\Desktop\\empposition.json");
//        JSONObject objPosition = JSON.parseObject(strPosition);
//        JSONArray datasPosition = objPosition.getJSONArray("datas");
//        List<GuFenPositionDto> listPosition = JSONObject.parseArray(datasPosition.toJSONString(), GuFenPositionDto.class);

    }

    //循环把机构转成咱们的机构对象
    public static void loopOrgInfo2(MdmOrgDto mdmOrgDto, Map<String, List<GuFenOrgDto>> orgMap, String asparorgcode,List<GuFenOrgDto> _tempList) {
        if(StringUtils.isNotBlank(asparorgcode)) {
            List<GuFenOrgDto> tempList = orgMap.get(asparorgcode);
            if(CollectionUtil.isNotEmpty(tempList)) {
                _tempList.addAll(tempList);
                List<MdmOrgDto> mdmOrgDtoList = new ArrayList<>();
                for (GuFenOrgDto fenOrgDto : tempList) {
                    MdmOrgDto _mdmOrgDto = new MdmOrgDto();
                    _mdmOrgDto.setOrgCode(fenOrgDto.getOrgcode());
                    _mdmOrgDto.setOrgName(fenOrgDto.getOrgname());
                    _mdmOrgDto.setParentId(mdmOrgDto.id);
                    _mdmOrgDto.setParentName(mdmOrgDto.getOrgName());
                    _mdmOrgDto.setOrgType(GuFenOrgTypeEnum.UN.code.equals(fenOrgDto.getOrgtype()) ? OrgTypeEnum.ORG.code:OrgTypeEnum.DEPT.code);

                    _mdmOrgDto.setGfOrgcode(fenOrgDto.getOrgcode());
                    _mdmOrgDto.setGfOrgname(fenOrgDto.getOrgname());
                    _mdmOrgDto.setGfOrgtype(fenOrgDto.getOrgtype());
                    _mdmOrgDto.setGfAsparorgcode(fenOrgDto.getAsparorgcode());
                    _mdmOrgDto.setGfStarttime(fenOrgDto.getStarttime());
                    _mdmOrgDto.setGfEndDate(fenOrgDto.getEnd_date());
                    _mdmOrgDto.setGfLastupdatetime(fenOrgDto.getLastupdatetime());
                    _mdmOrgDto.setGfInstitutional(fenOrgDto.getInstitutional());
                    _mdmOrgDto.setGfNorder(fenOrgDto.getNorder());
                    mdmOrgDtoList.add(_mdmOrgDto);
                    loopOrgInfo2(_mdmOrgDto,orgMap,fenOrgDto.getOrgcode(),_tempList);
                }
                mdmOrgDto.setMdmOrgDtoList(mdmOrgDtoList);
            }
        }
    }

    //递归封装机构部门
    public static void loopOrgInfo(MdmOrgDto mdmOrgDto, Map<String, List<GuFenOrgDto>> orgMap, String asparorgcode) {
        if(StringUtils.isNotBlank(asparorgcode)) {
            List<GuFenOrgDto> tempList = orgMap.get(asparorgcode);
            if(CollectionUtil.isNotEmpty(tempList)) {
                List<MdmOrgDto> mdmOrgDtoList = new ArrayList<>();
                for (GuFenOrgDto fenOrgDto : tempList) {
                    MdmOrgDto _mdmOrgDto = new MdmOrgDto();
                    _mdmOrgDto.setOrgCode(fenOrgDto.getOrgcode());
                    _mdmOrgDto.setOrgName(fenOrgDto.getOrgname());
                    _mdmOrgDto.setParentId(mdmOrgDto.id);
                    _mdmOrgDto.setParentName(mdmOrgDto.getOrgName());
                    _mdmOrgDto.setOrgType(GuFenOrgTypeEnum.UN.code.equals(fenOrgDto.getOrgtype()) ? OrgTypeEnum.ORG.code:OrgTypeEnum.DEPT.code);
                    if(StringUtils.isNotBlank(fenOrgDto.getStarttime())) {
                        _mdmOrgDto.setValidStartTime(DateUtil.toLocalDateTime(fenOrgDto.getStarttime(),df));
                    }
                    if(StringUtils.isNotBlank(fenOrgDto.getEnd_date())) {
                        _mdmOrgDto.setValidEndTime(DateUtil.toLocalDateTime(fenOrgDto.getEnd_date(),df));
                    }
//                    LocalDateTime now = LocalDateTime.now();
//                    if(now.compareTo(_mdmOrgDto.getValidStartTime()) > 0 && now.compareTo(_mdmOrgDto.getValidEndTime()) < 0) {
//                        _mdmOrgDto.setStatus(StatusEnum.RUNNING.code);
//                    } else {
//                        _mdmOrgDto.setStatus(StatusEnum.CANCELED.code);
//                    }
                    if("中国核工业中原建设有限公司".equals(_mdmOrgDto.getOrgName())) {
                        _mdmOrgDto.setLvType(LvTypeEnum.CONSTRUCTION_COMPANY.code);
                        _mdmOrgDto.setOaLvType(LvTypeEnum.CONSTRUCTION_COMPANY.code);
                    }
                    _mdmOrgDto.setGfOrgcode(fenOrgDto.getOrgcode());
                    _mdmOrgDto.setGfOrgname(fenOrgDto.getOrgname());
                    _mdmOrgDto.setGfOrgtype(fenOrgDto.getOrgtype());
                    _mdmOrgDto.setGfAsparorgcode(fenOrgDto.getAsparorgcode());
                    _mdmOrgDto.setGfStarttime(fenOrgDto.getStarttime());
                    _mdmOrgDto.setGfEndDate(fenOrgDto.getEnd_date());
                    _mdmOrgDto.setGfLastupdatetime(fenOrgDto.getLastupdatetime());
                    _mdmOrgDto.setGfInstitutional(fenOrgDto.getInstitutional());
                    _mdmOrgDto.setGfNorder(fenOrgDto.getNorder());
                    _mdmOrgDto.setSort(fenOrgDto.getNorder());
                    mdmOrgDtoList.add(_mdmOrgDto);
                    loopOrgInfo(_mdmOrgDto,orgMap,fenOrgDto.getOrgcode());
                }
                mdmOrgDto.setMdmOrgDtoList(mdmOrgDtoList);
            }
        }
    }

    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public boolean syncGuFenPosition() {
        log.info("同步股份兼岗信息。userUrl:{}",empPositionUrl);
        if(StringUtils.isBlank(empPositionUrl))  {
            throw new IncloudException("没有获取到同步股份兼岗信息URL.");
        }
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(empPositionUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("userName",mdUserName);
            httpPost.setHeader("passWord",mdPassWord);
            StringEntity entity = new StringEntity(defaultReqData, "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) { // true
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                //处理兼岗
                //String resString = readJsonFile("C:\\Users\\x1c\\Desktop\\股份json数据\\position.json");
                JSONObject objAndPost = JSON.parseObject(resString);
                JSONArray datasAndPost = objAndPost.getJSONArray("datas");
                List<GuFenPositionDto> listAndPost = JSONObject.parseArray(datasAndPost.toJSONString(), GuFenPositionDto.class);
                //查询所有人员
                List<MdmUserVo> allUsre = mdmUserService.lists(new MdmUserDto());
                //查询出所有岗位
                MdmPostDto mdmPostDto = new MdmPostDto();
                List<MdmPostVo> allPost = mdmPostService.lists(mdmPostDto);
                handlePositon(listAndPost,allUsre,allPost);
                log.debug("同步兼岗信息完成！");
            } else {
                log.info("同步股份兼岗信息数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步股份兼岗信息数据失败！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步股份兼岗信息数据失败!");
        }finally {

        }
    }

    @Override
    @Transactional
    public boolean syncGuFenDict() {
        log.info("同步股份字典信息。userUrl:{}",mdDictUrl);
        if(StringUtils.isBlank(mdDictUrl))  {
            throw new IncloudException("没有获取到同步股份字典信息URL.");
        }
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(mdDictUrl);
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("userName",mdUserName);
            httpPost.setHeader("passWord",mdPassWord);
            StringEntity entity = new StringEntity(defaultReqData, "UTF-8");
            httpPost.setEntity(entity);
            execute = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) { // true
                HttpEntity result = execute.getEntity();
                String resString = EntityUtils.toString(result);
                //处理兼岗
                //String resString = readJsonFile("C:\\Users\\x1c\\Desktop\\股份json数据\\dict.json");
                JSONObject obj = JSON.parseObject(resString);
                JSONArray datas = obj.getJSONArray("datas");
                List<GuFenDictDto> list = JSONObject.parseArray(datas.toJSONString(), GuFenDictDto.class);
                //查询所有人员
                if(CollectionUtil.isNotEmpty(list)) {
                    List<MdmCommDict> resultList = new ArrayList<>();
                    for (GuFenDictDto guFenDictDto : list) {
                        MdmCommDict mdmCommDict = new MdmCommDict();
                        mdmCommDict.setDictTypeId(guFenDictDto.getDicttypeid());
                        mdmCommDict.setDictCode(guFenDictDto.getDictcode());
                        mdmCommDict.setDictName(guFenDictDto.getDictname());
                        resultList.add(mdmCommDict);
                    }
                    if(CollectionUtil.isNotEmpty(resultList)) {
                        mdmCommDictService.saveBatch(resultList);
                    }
                    log.debug("同步股份字典信息完成！");
                }
            } else {
                log.info("同步股份字典信息数据失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new IncloudException("同步股份字典信息数据失败！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncloudException("同步股份字典信息数据失败!");
        }finally {

        }
    }

    public static String getYesterDay() {
        Date today = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = simpleDateFormat.format(today);//获取昨天日期
        return yesterday;
    }

    /**
     * 获取传入时间的 下一天时间
     * @param str
     * @return
     */
    public static String getTomorrowDay(String str) {
        Date date = DateUtil.parseDate(str);
        long now2 = date.getTime();
        Date today = new Date(now2 + 1000 * 60 * 60 * 24);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = simpleDateFormat.format(today);//获取明天日期
        return yesterday;
    }

    public Result sendRocketMq(String topic,String tag,String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        if (StringUtils.isEmpty(msg)) {
            log.error("发送rocketMq msg为空。");
            return  Result.success();
        }
        if(StringUtils.isEmpty(topic)) {
            log.error("发送rocketMq topic为空。");
            return  Result.success();
        }
        if(StringUtils.isEmpty(tag)) {
            log.error("发送rocketMq tag。");
            return  Result.success();
        }
        log.info("发送rocketMq消息内容：" + msg);
        List<String> streamStr = Stream.of(topic.split(",")).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(streamStr)) {
            for (String tp : streamStr) {
                Message sendMsg = new Message(tp, tag, msg.getBytes());
                // 默认3秒超时
                SendResult sendResult = defaultMQProducer.send(sendMsg,new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        return mqs.get(0); //数据量不大 直接指定一个队列
                    }
                }, msg);
                log.info("消息发送响应：" + sendResult.toString());
            }
        } else {
            log.error("发送rocketMq topic为空。");
            return  Result.success();
        }
        return  Result.success();
    }

    @Override
    @Transactional
    @SneakyThrows
    public void handleOrgPastDue() {
        log.debug("处理机构过期状态。=============================start");
        //查询所有的机构信息
        List<MdmOrg> upList = new ArrayList<>();
        MdmOrgDto mdmOrgDto = new MdmOrgDto();
        List<MdmOrgAllVo> MdmOrgAllVos = mdmOrgService.lists(mdmOrgDto);
        //判断如果 开始时间和结束时间都不为空 并且不在事件范围内则都是撤销
        if(CollectionUtil.isNotEmpty(MdmOrgAllVos)) {
            for (MdmOrgAllVo mdmOrgAllVo : MdmOrgAllVos) {
                if(null!=mdmOrgAllVo.getValidStartTime()&&null!=mdmOrgAllVo.getValidEndTime()) {
                    //当前时间 是否在范围内
                    // 此时此刻
                    LocalDateTime now = LocalDateTime.now();
                    //如果当前时间大于开始时间 并且 小于 结束时间说明是 运行中
                    if(now.compareTo(mdmOrgAllVo.getValidStartTime()) > 0 && now.compareTo(mdmOrgAllVo.getValidEndTime()) < 0) {
                        //如果数据库中已经是撤销就不修改了
                        if(StatusEnum.RUNNING.code == mdmOrgAllVo.getStatus()) {

                        } else { //如果与数据库状态不一致则修改
                            mdmOrgAllVo.setStatus(StatusEnum.RUNNING.code);
                            MdmOrg mdmOrg = dozerMapper.map(mdmOrgAllVo,MdmOrg.class);
                            upList.add(mdmOrg);
                        }
                    } else { //过期的就是撤销
                        if(StatusEnum.CANCELED.code == mdmOrgAllVo.getStatus()) {

                        } else { //如果与数据库状态不一致则修改
                            mdmOrgAllVo.setStatus(StatusEnum.CANCELED.code);
                            MdmOrg mdmOrg = dozerMapper.map(mdmOrgAllVo,MdmOrg.class);
                            upList.add(mdmOrg);
                        }
                    }
                }
            }
            if(CollectionUtil.isNotEmpty(upList)) {
                mdmOrgService.updateBatchById(upList);
                //转换成Vo发送增量mq
                List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, upList, MdmOrgVo.class);
                log.debug("updateParent 机构修改成功-发送增量MQ");
                for (MdmOrgVo mdmOrgVo : mdmOrgVos) {
                    String topics[] = orgTopics.split(",");
                    //判断geps
                    if(null != mdmOrgVo.getLvType() && 0!=mdmOrgVo.getLvType()) {
                        mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                    //判断oa
                    if(null != mdmOrgVo.getOaLvType() && 0!=mdmOrgVo.getOaLvType()) {
                        mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                }
            }
        }
        log.debug("处理机构过期状态。=============================end");
    }

    @Override
    @Transactional
    public void handlePostPastDue() {
        log.debug("处理岗位过期状态。=============================start");
        //查询所有的机构信息
        List<MdmPost> upList = new ArrayList<>();
        MdmPostDto mdmPostDto = new MdmPostDto();
        List<MdmPostVo> mdmPostVos = mdmPostService.lists(mdmPostDto);
        //判断如果 开始时间和结束时间都不为空 并且不在事件范围内则都是撤销
        if(CollectionUtil.isNotEmpty(mdmPostVos)) {
            for (MdmPostVo mdmPostVo : mdmPostVos) {
                if(null!=mdmPostVo.getValidStartTime()&&null!=mdmPostVo.getValidEndTime()) {
                    //当前时间 是否在范围内
                    // 此时此刻
                    LocalDateTime now = LocalDateTime.now();
                    //如果当前时间大于开始时间 并且 小于 结束时间说明是 运行中
                    if(now.compareTo(mdmPostVo.getValidStartTime()) > 0 && now.compareTo(mdmPostVo.getValidEndTime()) < 0) {
                        //如果数据库中已经是撤销就不修改了
                        if(YesNoEnum.YES.code == mdmPostVo.getStatus()) {

                        } else { //如果与数据库状态不一致则修改
                            mdmPostVo.setStatus(YesNoEnum.YES.code);
                            MdmPost mdmPost = dozerMapper.map(mdmPostVo,MdmPost.class);
                            upList.add(mdmPost);
                        }
                    } else { //过期的就是撤销
                        if(YesNoEnum.NO.code == mdmPostVo.getStatus()) {

                        } else { //如果与数据库状态不一致则修改
                            mdmPostVo.setStatus(YesNoEnum.NO.code);
                            MdmPost mdmPost = dozerMapper.map(mdmPostVo,MdmPost.class);
                            upList.add(mdmPost);
                        }
                    }
                }
            }
            if(CollectionUtil.isNotEmpty(upList)) {
                mdmPostService.updateBatchById(upList);
            }
        }
        log.debug("处理岗位过期状态。=============================end");
    }

    private static void listSort(List<GuFenUserDto> list) {
        Collections.sort(list, new Comparator<GuFenUserDto>() {
            @Override
            //定义一个比较器
            public int compare(GuFenUserDto o1, GuFenUserDto o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Long dt1 = format.parse(o1.getLastupdatetime()).getTime();
                    Long dt2 = format.parse(o2.getLastupdatetime()).getTime();
                    int i = dt2.compareTo(dt1);
                    if (i != 0) {
                        return i;
                    } else {
                        //如果日期相同 则再按照在职1 离职2排序
                        int d1 = Integer.valueOf(o1.getEmployee_status());
                        int d2 = Integer.valueOf(o2.getEmployee_status());
                        if (d1 != d2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
