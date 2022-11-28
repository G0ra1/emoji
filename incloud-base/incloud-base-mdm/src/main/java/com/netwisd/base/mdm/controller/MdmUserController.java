package com.netwisd.base.mdm.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.dto.MdmPostUserDto;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.mdm.dto.MdmSortDto;
import com.netwisd.base.mdm.dto.MdmTransferDto;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.excel.MdmUserExcel;
import com.netwisd.base.mdm.utils.EasyExcelUtils;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.anntation.IncludeAnntation;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description 用户 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 10:48:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmUser" )
@Api(value = "mdmUser", tags = "用户Controller")
@Slf4j
public class MdmUserController {

    private final  MdmUserService mdmUserService;

    @Autowired
    private Mapper dozerMapper;

    /**
     * 分页查询用户
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmUserDto 用户
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL),
                                include = @IncludeAnntation(vars = {"orgId"}))
                             @RequestBody MdmUserDto mdmUserDto) {
        Page pageVo = mdmUserService.list(mdmUserDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询用户
     * @param mdmUserDto 用户
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody MdmUserDto mdmUserDto) {
        List<MdmUserVo> list = mdmUserService.lists(mdmUserDto);
        return Result.success(list);
    }

    /**
     * 通过id查询用户
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmUserVo> get(@PathVariable("id" ) Long id) {
        MdmUserVo mdmUserVo = mdmUserService.get(id);
        log.debug("查询成功");
        return Result.success(mdmUserVo);
    }

    /**
     * 批量通过id查询
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "批量通过id查询", notes = "批量通过id查询")
    @GetMapping("/getByIds/{ids}" )
    public Result<List<MdmUserVo>> getByIds(@PathVariable("ids" ) String ids) {
        List<MdmUserVo> userVoList = mdmUserService.getByIds(ids);
        log.debug("查询成功");
        return Result.success(userVoList);
    }


    /**
     * 批量新增用户
     * @param mdmUserDtoList 用户
     * @return Result
     */
    @ApiOperation(value = "批量新增用户", notes = "批量新增用户")
    @PostMapping("/saveList")
    public Result<Boolean> saveList(@Validation(exclude =@ExcludeAnntation
            (vars = {"orgType","nationality","status","birthAddr","birthNature","birthPlace"})) @RequestBody List<MdmUserDto> mdmUserDtoList) {
        Boolean result = mdmUserService.saveList(mdmUserDtoList);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 新增用户
     * @param mdmUserDto 用户
     * @return Result
     */
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping
    public Result<Boolean> save(@Validation(exclude =@ExcludeAnntation
            (vars = {"orgType","nationality","status","birthAddr","birthNature","birthPlace"})) @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.save(mdmUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改用户
     * @param mdmUserDto 用户
     * @return Result
     */
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.update(mdmUserDto,false);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除用户
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除用户", notes = "通过id删除用户")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = mdmUserService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 调动部门
     * @param mdmUserDto 调动部门
     * @return Result
     */
    @ApiOperation(value = "调动部门", notes = "调动部门")
    @PostMapping("/transferDetp")
    public Result<Boolean> transferDetp(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.transferDetp(mdmUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 调动单位
     * @param mdmUserDto 调动单位
     * @return Result
     */
    @ApiOperation(value = "调动单位", notes = "调动单位")
    @PostMapping("/transferOrg")
    public Result<Boolean> transferOrg(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.transferOrg(mdmUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 设置兼岗
     * @param mdmTransferDto 设置兼岗
     * @return Result
     */
    @ApiOperation(value = "设置兼岗", notes = "设置兼岗")
    @PostMapping("/setAndPost")
    public Result<Boolean> setAndPost(@Validation(exclude = @ExcludeAnntation(vars = {"parentOrgFullName","parentDeptFullName","orgFullPostId","orgFullPostName","orgFullId","orgFullName"}))
                                          @RequestBody List<MdmTransferDto> mdmTransferDto) {
        Boolean result = mdmUserService.setAndPost(mdmTransferDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 调/设置 主岗
     * @param mdmTransferDto 调/设置 主岗
     * @return Result
     */
    @ApiOperation(value = "调/设置 主岗", notes = "调/设置 主岗")
    @PostMapping("/setMasterPost")
    public Result<Boolean> setMasterPost(@Validation(exclude = @ExcludeAnntation(vars = {"orgFullId","postId","postCode","postName"})) @RequestBody MdmTransferDto mdmTransferDto) {
        Boolean result = mdmUserService.setMasterPost(mdmTransferDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 部门内人员排序
     * @param sortDto 排序
     * @return
     */
    @ApiOperation(value = "部门内人员排序", notes = "部门内人员排序")
    @PostMapping("/sort" )
    public Result<Boolean> sortForDept(@Validation @RequestBody MdmSortDto sortDto) {
        Long sourceId = sortDto.getSourceId();
        Long targetId = sortDto.getTargetId();

        String index = sortDto.getIndex();
        if(ObjectUtil.isEmpty(targetId)){
            if(StrUtil.isEmpty(index)){
                throw new IncloudException("置顶、置底、要排序的目标组织、三者至少有一个不能为空！");
            }
        }else {
            if(StrUtil.isNotEmpty(index)){
                log.error("前端传值有误，如果source、target都有值的情况下，Index不能有值，后台强制index置为null！");
                index = null;
            }
        }
        return Result.success(mdmUserService.sortForDept(sourceId,targetId,index));
    }

    /**
     * 全局人员排序
     * @param sortDto 排序
     * @return
     */
    @ApiOperation(value = "全局人员排序", notes = "全局人员排序")
    @PostMapping("/globalsort" )
    public Result<Boolean> sortForGlobal(@Validation @RequestBody MdmSortDto sortDto) {
        Long sourceId = sortDto.getSourceId();
        Long targetId = sortDto.getTargetId();

        String index = sortDto.getIndex();
        if(ObjectUtil.isEmpty(targetId)){
            if(StrUtil.isEmpty(index)){
                throw new IncloudException("置顶、置底、要排序的目标组织、三者至少有一个不能为空！");
            }
        }else {
            if(StrUtil.isNotEmpty(index)){
                log.error("前端传值有误，如果source、target都有值的情况下，Index不能有值，后台强制index置为null！");
                index = null;
            }
        }
        return Result.success(mdmUserService.sortForGlobal(sourceId,targetId,index));
    }

    //登陆
    @GetMapping(value = "/anon-internal/findByUsername", params = "username")
    public LoginAppUser findByUsername(@RequestParam("username") @Validation String userName) {
        log.info("登陆:{}", userName);
        LoginAppUser loginUser = mdmUserService.findByUserName(userName);
        return loginUser;
    }

    /**
     * 根据OrgId查询出下面所有层级的人员
     * @param id 机构id
     * @return
     */
    @ApiOperation(value = "根据OrgId查询出下面所有层级的人员", notes = "根据OrgId查询出下面所有层级的人员")
    @GetMapping("/getUserByOrgId/{id}" )
    public Result getUserByOrgId(@PathVariable String id) {
        List<MdmUserVo> list = mdmUserService.getUserByOrgId(id);
        return Result.success(list);
    }

    /**
     * 根据deptId 精确查询下面所有人员
     * @param deptId 机构id
     * @return
     */
    @ApiOperation(value = "根据deptId 精确查询下面所有人员", notes = "根据deptId 精确查询下面所有人员")
    @GetMapping("/getUserByDeptId/{deptId}" )
    public Result getUserByDeptId(@PathVariable String deptId) {
        List<MdmUserVo> list = mdmUserService.getUserByDeptId(deptId);
        return Result.success(list);
    }

    /**
     * 测试嵌入式neo4j
     * @return
     */
    @ApiOperation(value = "测试嵌入式neo4j", notes = "测试嵌入式neo4j")
    @GetMapping("/testNeo4j" )
    public Result testNeo4j() {
        DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(Paths.get("/root/docker_store/neo4j")).setConfig(GraphDatabaseSettings.read_only_database_default, true).build();//D:\neo4j_databases\neo4j
        GraphDatabaseService graphDb = managementService.database("neo4j");
        Transaction tx = graphDb.beginTx();
        //查询数据库
        String query ="match (n) return n.name";
        log.debug("query:" + query);
        Map<String, Object > parameters = new HashMap<String, Object>();
        try ( org.neo4j.graphdb.Result result = tx.execute( query, parameters ) ) {
            log.debug("result:" +  result.columns());
            log.debug("resultNext:" + result.hasNext());
            while ( result.hasNext() ) {
                Map<String, Object> row = result.next();
                log.debug("row:" +  row.size());
                for ( String key : result.columns() ) {
                    log.debug("key:" +  key + row.get( key ));
                    System.out.printf( "%s = %s%n", key, row.get( key ) );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            managementService.shutdown();    //关闭数据库
        }
        registerShutdownHook(managementService);
        return Result.success(true);
    }

    private static void registerShutdownHook( final DatabaseManagementService managementService )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                managementService.shutdown();
            }
        } );
    }


    /**
     * 调/设置 主职
     * @param mdmUserDto 用户信息包含 职位的id 信息
     * @return Result
     */
    @ApiOperation(value = "调/设置 主职", notes = "调/设置 主职")
    @PostMapping("/setMasterDuty")
    public Result<Boolean> setMasterDuty(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.setMasterDuty(mdmUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 设置兼职
     * @param mdmUserDto 设置兼职
     * @return Result
     */
    @ApiOperation(value = "设置兼职", notes = "设置兼职")
    @PostMapping("/setAndDuty")
    public Result<Boolean> setAndDuty(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.setAndDuty(mdmUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 效验初始密码
     * @param mdmUserDto 用户信息
     * @return Result
     */
    @ApiOperation(value = "效验初始密码", notes = "效验初始密码")
    @PostMapping("/checkPassword")
    public Result<Boolean> checkPassword(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.checkPassword(mdmUserDto);
        return Result.success(result);
    }

    /**
     * 修改密码
     * @param mdmUserDto 用户信息
     * @return Result
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping("/updatePassword")
    public Result<Boolean> updatePassword(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updatePassword(mdmUserDto);
        return Result.success(result);
    }

    /**
     * 修改头像
     * @param mdmUserDto 用户信息
     * @return Result
     */
    @ApiOperation(value = "修改头像", notes = "修改头像")
    @PostMapping("/updatePhoto")
    public Result<Boolean> updatePhoto(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updatePhoto(mdmUserDto);
        return Result.success(result);
    }

    /**
     * 修改邮箱
     * @param mdmUserDto 用户信息
     * @return Result
     */
    @ApiOperation(value = "修改邮箱", notes = "修改邮箱")
    @PostMapping("/updateEmail")
    public Result<Boolean> updateEmail(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updateEmail(mdmUserDto);
        return Result.success(result);
    }

    /**
     * 修改手机号
     * @param mdmUserDto 用户信息
     * @return Result
     */
    @ApiOperation(value = "修改手机号", notes = "修改手机号")
    @PostMapping("/updatePhoneNum")
    public Result<Boolean> updatePhoneNum(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updatePhoneNum(mdmUserDto);
        return Result.success(result);
    }

    /**
     * 根据身份证号获取用户信息
     * @param idCards 身份证号
     * @return Result
     */
    @ApiOperation(value = "根据身份证号获取用户信息", notes = "根据身份证号获取用户信息")
    @GetMapping("/getUserByIdCards")
    public List<MdmUserVo> getUserByIdCards(String idCards) {
        List<MdmUserVo> list = mdmUserService.getUserByIdCards(idCards);
        return list;
    }

    @Autowired
    DefaultMQProducer defaultMQProducer;

    /**
     * 发送简单的MQ消息
     * @param msg
     * @return
     */
    @GetMapping("/send")
    public Result send(String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        if (StringUtils.isEmpty(msg)) {
            return  Result.success();
        }
        log.info("发送MQ消息内容：" + msg);
        Message sendMsg = new Message("XHL", "test-x", msg.getBytes());
        // 默认3秒超时
        SendResult sendResult = defaultMQProducer.send(sendMsg,new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                //Long id = Long.valueOf((String)arg);
                //long index = id % mqs.size();
                return mqs.get(0);
            }
        }, msg);
        log.info("消息发送响应：" + sendResult.toString());
        return new Result().success(sendResult);
    }
    /**
     * 同步在线学习人员
     * @param mdmUserDto 用户
     * @return Result
     */
    @ApiOperation(value = "同步在线学习人员", notes = "同步在线学习人员")
    @PostMapping("/saveStudyUser")
    public MdmUserVo saveStudyUser(@Validation(exclude =@ExcludeAnntation
            (vars = {"orgType","nationality","status","birthAddr","birthNature","birthPlace"})) @RequestBody MdmUserDto mdmUserDto) {
        MdmUserVo mdmUserVo = mdmUserService.saveStudyUser(mdmUserDto);
        log.debug("保存成功");
        return mdmUserVo;
    }

    /**
     * 通过id删除在线学习用户
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除在线学习用户", notes = "通过id删除在线学习用户")
    @DeleteMapping("/deleteStudyUser/{id}" )
    public Boolean deleteStudyUser(@PathVariable String id) {
        return mdmUserService.deleteStudyUser(id);
    }

    /**
     * 查询所有人员信息 --用于缓存
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/getUsersForCache" )
    public Map getUsersForCache() {
        log.info("查询主数据所有人员信息 --用于缓存");
        MdmUserDto mdmUserDto = new MdmUserDto();
        List<MdmUserVo> listVo = mdmUserService.lists(mdmUserDto);
        if(CollectionUtils.isNotEmpty(listVo)) {
            Map<Long, MdmUserVo> map = listVo.stream().collect(Collectors.toMap(MdmUserVo::getId, Function.identity(),(key1 , key2)-> key2 ));
            return map;
        }
        return null;
    }

    /**
     * 给所有可用状态的用户 设置默认角色
     * @return Result
     */
    @ApiOperation(value = "给所有可用状态的用户 设置默认角色", notes = "给所有可用状态的用户 设置默认角色")
    @PostMapping("/setAllUserDefaultRole")
    public Result<Boolean> setAllUserDefaultRole() {
        return Result.success(mdmUserService.setAllUserDefaultRole());
    }

    /**
     * 修改在线学习用户
     * @param mdmUserDto 修改在线学习用户
     * @return Result
     */
    @ApiOperation(value = "修改在线学习用户", notes = "修改在线学习用户")
    @PutMapping("/updateStudyUser")
    public Boolean updateStudyUser(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updateStudyUser(mdmUserDto);
        log.debug("更新成功");
        return result;
    }

    /**
     * 注册
     * @param mdmUserDto 用户
     * @return Result
     */
    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping("/regStudyUser")
    public MdmUserVo registerStudyUser(@Validation(exclude =@ExcludeAnntation
            (vars = {"orgType","nationality","status","birthAddr","birthNature","birthPlace"})) @RequestBody MdmUserDto mdmUserDto) {
        MdmUserVo mdmUserVo = mdmUserService.regStudyUser(mdmUserDto);
        log.debug("保存成功");
        return mdmUserVo;
    }
    /**
     * 根据deptId 精确查询下面所有人员----手机端专用接口
     * @param deptId 机构id
     * @return
     */
    @ApiOperation(value = "根据deptId 精确查询下面所有人员", notes = "根据deptId 精确查询下面所有人员")
    @GetMapping("/getUserByDeptIdForPhone/{deptId}" )
    public Result getUserByDeptIdForPhone(@PathVariable String deptId) {
        List<MdmUserVo> list = mdmUserService.getUserByDeptIdForPhone(deptId);
        return Result.success(list);
    }

    @GetMapping("/export")
    @ApiOperation(value = "用户全量导出")
    public void mdmUserExport(HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.HSSF);
        log.debug("导出全量用户excel信息开始--------------------------------------");
        List<MdmUserExcel> mdmUserExcels = mdmUserService.expertUserInfo();
        EasyExcelUtils.exportExcel(mdmUserExcels, MdmUserExcel.class, "用户全量信息", exportParams, response);
    }
}
