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
 * @Description ?????? ????????????...
 * @author ???????????? XHL@netwisd.com
 * @date 2021-08-25 10:48:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmUser" )
@Api(value = "mdmUser", tags = "??????Controller")
@Slf4j
public class MdmUserController {

    private final  MdmUserService mdmUserService;

    @Autowired
    private Mapper dozerMapper;

    /**
     * ??????????????????
     * ??????????????????????????????????????????form???????????????
     * @param mdmUserDto ??????
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/list" )
    public Result<Page> list(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL),
                                include = @IncludeAnntation(vars = {"orgId"}))
                             @RequestBody MdmUserDto mdmUserDto) {
        Page pageVo = mdmUserService.list(mdmUserDto);
        log.debug("????????????:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * ???????????????????????????
     * @param mdmUserDto ??????
     * @return
     */
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    @PostMapping("/lists" )
    public Result lists(@RequestBody MdmUserDto mdmUserDto) {
        List<MdmUserVo> list = mdmUserService.lists(mdmUserDto);
        return Result.success(list);
    }

    /**
     * ??????id????????????
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "??????id??????", notes = "??????id??????")
    @GetMapping("/{id}" )
    public Result<MdmUserVo> get(@PathVariable("id" ) Long id) {
        MdmUserVo mdmUserVo = mdmUserService.get(id);
        log.debug("????????????");
        return Result.success(mdmUserVo);
    }

    /**
     * ????????????id??????
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "????????????id??????", notes = "????????????id??????")
    @GetMapping("/getByIds/{ids}" )
    public Result<List<MdmUserVo>> getByIds(@PathVariable("ids" ) String ids) {
        List<MdmUserVo> userVoList = mdmUserService.getByIds(ids);
        log.debug("????????????");
        return Result.success(userVoList);
    }


    /**
     * ??????????????????
     * @param mdmUserDtoList ??????
     * @return Result
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @PostMapping("/saveList")
    public Result<Boolean> saveList(@Validation(exclude =@ExcludeAnntation
            (vars = {"orgType","nationality","status","birthAddr","birthNature","birthPlace"})) @RequestBody List<MdmUserDto> mdmUserDtoList) {
        Boolean result = mdmUserService.saveList(mdmUserDtoList);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmUserDto ??????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping
    public Result<Boolean> save(@Validation(exclude =@ExcludeAnntation
            (vars = {"orgType","nationality","status","birthAddr","birthNature","birthPlace"})) @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.save(mdmUserDto);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmUserDto ??????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.update(mdmUserDto,false);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ??????id????????????
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "??????id????????????", notes = "??????id????????????")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = mdmUserService.delete(id);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmUserDto ????????????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/transferDetp")
    public Result<Boolean> transferDetp(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.transferDetp(mdmUserDto);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmUserDto ????????????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/transferOrg")
    public Result<Boolean> transferOrg(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.transferOrg(mdmUserDto);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmTransferDto ????????????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/setAndPost")
    public Result<Boolean> setAndPost(@Validation(exclude = @ExcludeAnntation(vars = {"parentOrgFullName","parentDeptFullName","orgFullPostId","orgFullPostName","orgFullId","orgFullName"}))
                                          @RequestBody List<MdmTransferDto> mdmTransferDto) {
        Boolean result = mdmUserService.setAndPost(mdmTransferDto);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ???/?????? ??????
     * @param mdmTransferDto ???/?????? ??????
     * @return Result
     */
    @ApiOperation(value = "???/?????? ??????", notes = "???/?????? ??????")
    @PostMapping("/setMasterPost")
    public Result<Boolean> setMasterPost(@Validation(exclude = @ExcludeAnntation(vars = {"orgFullId","postId","postCode","postName"})) @RequestBody MdmTransferDto mdmTransferDto) {
        Boolean result = mdmUserService.setMasterPost(mdmTransferDto);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ?????????????????????
     * @param sortDto ??????
     * @return
     */
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    @PostMapping("/sort" )
    public Result<Boolean> sortForDept(@Validation @RequestBody MdmSortDto sortDto) {
        Long sourceId = sortDto.getSourceId();
        Long targetId = sortDto.getTargetId();

        String index = sortDto.getIndex();
        if(ObjectUtil.isEmpty(targetId)){
            if(StrUtil.isEmpty(index)){
                throw new IncloudException("?????????????????????????????????????????????????????????????????????????????????");
            }
        }else {
            if(StrUtil.isNotEmpty(index)){
                log.error("???????????????????????????source???target????????????????????????Index???????????????????????????index??????null???");
                index = null;
            }
        }
        return Result.success(mdmUserService.sortForDept(sourceId,targetId,index));
    }

    /**
     * ??????????????????
     * @param sortDto ??????
     * @return
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @PostMapping("/globalsort" )
    public Result<Boolean> sortForGlobal(@Validation @RequestBody MdmSortDto sortDto) {
        Long sourceId = sortDto.getSourceId();
        Long targetId = sortDto.getTargetId();

        String index = sortDto.getIndex();
        if(ObjectUtil.isEmpty(targetId)){
            if(StrUtil.isEmpty(index)){
                throw new IncloudException("?????????????????????????????????????????????????????????????????????????????????");
            }
        }else {
            if(StrUtil.isNotEmpty(index)){
                log.error("???????????????????????????source???target????????????????????????Index???????????????????????????index??????null???");
                index = null;
            }
        }
        return Result.success(mdmUserService.sortForGlobal(sourceId,targetId,index));
    }

    //??????
    @GetMapping(value = "/anon-internal/findByUsername", params = "username")
    public LoginAppUser findByUsername(@RequestParam("username") @Validation String userName) {
        log.info("??????:{}", userName);
        LoginAppUser loginUser = mdmUserService.findByUserName(userName);
        return loginUser;
    }

    /**
     * ??????OrgId????????????????????????????????????
     * @param id ??????id
     * @return
     */
    @ApiOperation(value = "??????OrgId????????????????????????????????????", notes = "??????OrgId????????????????????????????????????")
    @GetMapping("/getUserByOrgId/{id}" )
    public Result getUserByOrgId(@PathVariable String id) {
        List<MdmUserVo> list = mdmUserService.getUserByOrgId(id);
        return Result.success(list);
    }

    /**
     * ??????deptId ??????????????????????????????
     * @param deptId ??????id
     * @return
     */
    @ApiOperation(value = "??????deptId ??????????????????????????????", notes = "??????deptId ??????????????????????????????")
    @GetMapping("/getUserByDeptId/{deptId}" )
    public Result getUserByDeptId(@PathVariable String deptId) {
        List<MdmUserVo> list = mdmUserService.getUserByDeptId(deptId);
        return Result.success(list);
    }

    /**
     * ???????????????neo4j
     * @return
     */
    @ApiOperation(value = "???????????????neo4j", notes = "???????????????neo4j")
    @GetMapping("/testNeo4j" )
    public Result testNeo4j() {
        DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(Paths.get("/root/docker_store/neo4j")).setConfig(GraphDatabaseSettings.read_only_database_default, true).build();//D:\neo4j_databases\neo4j
        GraphDatabaseService graphDb = managementService.database("neo4j");
        Transaction tx = graphDb.beginTx();
        //???????????????
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
            managementService.shutdown();    //???????????????
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
     * ???/?????? ??????
     * @param mdmUserDto ?????????????????? ?????????id ??????
     * @return Result
     */
    @ApiOperation(value = "???/?????? ??????", notes = "???/?????? ??????")
    @PostMapping("/setMasterDuty")
    public Result<Boolean> setMasterDuty(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.setMasterDuty(mdmUserDto);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmUserDto ????????????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/setAndDuty")
    public Result<Boolean> setAndDuty(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.setAndDuty(mdmUserDto);
        log.debug("????????????");
        return Result.success(result);
    }

    /**
     * ??????????????????
     * @param mdmUserDto ????????????
     * @return Result
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @PostMapping("/checkPassword")
    public Result<Boolean> checkPassword(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.checkPassword(mdmUserDto);
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmUserDto ????????????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/updatePassword")
    public Result<Boolean> updatePassword(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updatePassword(mdmUserDto);
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmUserDto ????????????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/updatePhoto")
    public Result<Boolean> updatePhoto(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updatePhoto(mdmUserDto);
        return Result.success(result);
    }

    /**
     * ????????????
     * @param mdmUserDto ????????????
     * @return Result
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/updateEmail")
    public Result<Boolean> updateEmail(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updateEmail(mdmUserDto);
        return Result.success(result);
    }

    /**
     * ???????????????
     * @param mdmUserDto ????????????
     * @return Result
     */
    @ApiOperation(value = "???????????????", notes = "???????????????")
    @PostMapping("/updatePhoneNum")
    public Result<Boolean> updatePhoneNum(@RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updatePhoneNum(mdmUserDto);
        return Result.success(result);
    }

    /**
     * ????????????????????????????????????
     * @param idCards ????????????
     * @return Result
     */
    @ApiOperation(value = "????????????????????????????????????", notes = "????????????????????????????????????")
    @GetMapping("/getUserByIdCards")
    public List<MdmUserVo> getUserByIdCards(String idCards) {
        List<MdmUserVo> list = mdmUserService.getUserByIdCards(idCards);
        return list;
    }

    @Autowired
    DefaultMQProducer defaultMQProducer;

    /**
     * ???????????????MQ??????
     * @param msg
     * @return
     */
    @GetMapping("/send")
    public Result send(String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        if (StringUtils.isEmpty(msg)) {
            return  Result.success();
        }
        log.info("??????MQ???????????????" + msg);
        Message sendMsg = new Message("XHL", "test-x", msg.getBytes());
        // ??????3?????????
        SendResult sendResult = defaultMQProducer.send(sendMsg,new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                //Long id = Long.valueOf((String)arg);
                //long index = id % mqs.size();
                return mqs.get(0);
            }
        }, msg);
        log.info("?????????????????????" + sendResult.toString());
        return new Result().success(sendResult);
    }
    /**
     * ????????????????????????
     * @param mdmUserDto ??????
     * @return Result
     */
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @PostMapping("/saveStudyUser")
    public MdmUserVo saveStudyUser(@Validation(exclude =@ExcludeAnntation
            (vars = {"orgType","nationality","status","birthAddr","birthNature","birthPlace"})) @RequestBody MdmUserDto mdmUserDto) {
        MdmUserVo mdmUserVo = mdmUserService.saveStudyUser(mdmUserDto);
        log.debug("????????????");
        return mdmUserVo;
    }

    /**
     * ??????id????????????????????????
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "??????id????????????????????????", notes = "??????id????????????????????????")
    @DeleteMapping("/deleteStudyUser/{id}" )
    public Boolean deleteStudyUser(@PathVariable String id) {
        return mdmUserService.deleteStudyUser(id);
    }

    /**
     * ???????????????????????? --????????????
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping("/getUsersForCache" )
    public Map getUsersForCache() {
        log.info("????????????????????????????????? --????????????");
        MdmUserDto mdmUserDto = new MdmUserDto();
        List<MdmUserVo> listVo = mdmUserService.lists(mdmUserDto);
        if(CollectionUtils.isNotEmpty(listVo)) {
            Map<Long, MdmUserVo> map = listVo.stream().collect(Collectors.toMap(MdmUserVo::getId, Function.identity(),(key1 , key2)-> key2 ));
            return map;
        }
        return null;
    }

    /**
     * ?????????????????????????????? ??????????????????
     * @return Result
     */
    @ApiOperation(value = "?????????????????????????????? ??????????????????", notes = "?????????????????????????????? ??????????????????")
    @PostMapping("/setAllUserDefaultRole")
    public Result<Boolean> setAllUserDefaultRole() {
        return Result.success(mdmUserService.setAllUserDefaultRole());
    }

    /**
     * ????????????????????????
     * @param mdmUserDto ????????????????????????
     * @return Result
     */
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @PutMapping("/updateStudyUser")
    public Boolean updateStudyUser(@Validation @RequestBody MdmUserDto mdmUserDto) {
        Boolean result = mdmUserService.updateStudyUser(mdmUserDto);
        log.debug("????????????");
        return result;
    }

    /**
     * ??????
     * @param mdmUserDto ??????
     * @return Result
     */
    @ApiOperation(value = "??????", notes = "??????")
    @PostMapping("/regStudyUser")
    public MdmUserVo registerStudyUser(@Validation(exclude =@ExcludeAnntation
            (vars = {"orgType","nationality","status","birthAddr","birthNature","birthPlace"})) @RequestBody MdmUserDto mdmUserDto) {
        MdmUserVo mdmUserVo = mdmUserService.regStudyUser(mdmUserDto);
        log.debug("????????????");
        return mdmUserVo;
    }
    /**
     * ??????deptId ??????????????????????????????----?????????????????????
     * @param deptId ??????id
     * @return
     */
    @ApiOperation(value = "??????deptId ??????????????????????????????", notes = "??????deptId ??????????????????????????????")
    @GetMapping("/getUserByDeptIdForPhone/{deptId}" )
    public Result getUserByDeptIdForPhone(@PathVariable String deptId) {
        List<MdmUserVo> list = mdmUserService.getUserByDeptIdForPhone(deptId);
        return Result.success(list);
    }

    @GetMapping("/export")
    @ApiOperation(value = "??????????????????")
    public void mdmUserExport(HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams();
        exportParams.setType(ExcelType.HSSF);
        log.debug("??????????????????excel????????????--------------------------------------");
        List<MdmUserExcel> mdmUserExcels = mdmUserService.expertUserInfo();
        EasyExcelUtils.exportExcel(mdmUserExcels, MdmUserExcel.class, "??????????????????", exportParams, response);
    }
}
