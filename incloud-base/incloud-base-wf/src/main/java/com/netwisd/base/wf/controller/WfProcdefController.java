package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.constants.YesNo;
import com.netwisd.base.wf.dto.DeploymentBpmnDto;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.util.DomUtils;
import com.netwisd.base.wf.util.XmlUtils;
import com.netwisd.base.wf.xml.Bpmn;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfProcDefDto;
import com.netwisd.base.wf.vo.WfProcDefVo;
import com.netwisd.base.wf.service.WfProcdefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import static com.netwisd.base.wf.service.impl.WfProcdefServiceImpl.getStringStream;

/**
 * @Description 流程定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-01 16:18:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfprocdef" )
@Api(value = "wfprocdef", tags = "流程定义Controller")
@Slf4j
public class WfProcdefController {

    private final  WfProcdefService wfProcdefService;

    private final WfProcessService wfProcessService;

    /**
     * 分页查询流程定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfProcdefDto 流程定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfProcDefDto wfProcdefDto) {
        Page pageVo = wfProcdefService.list(wfProcdefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 业务中心展示流程定义
     * @param wfProcdefDto 流程定义
     * @return
     */
    @ApiOperation(value = "业务中心展示流程定义", notes = "业务中心展示流程定义")
    @PostMapping("/lists" )
    public Result lists(@RequestBody WfProcDefDto wfProcdefDto) {
        List<WfProcDefVo> vos = wfProcdefService.lists(wfProcdefDto);
        return Result.success(vos);
    }

    /**
     * 通过id查询流程定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfProcDefVo> get(@PathVariable("id" ) Long id) {
        WfProcDefVo wfProcdefVo = wfProcdefService.get(id);
        log.debug("查询成功");
        return Result.success(wfProcdefVo);
    }

    @SneakyThrows
    private String convertXml(MultipartFile data){
        InputStream inputStream = data.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String xml = new String(bytes);
        return xml;
    }

    /**
     * 通过id删除流程定义(删除所有的对应的流程信息-包含所有版本)
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义(删除所有的对应的流程信息-包含所有版本)", notes = "通过id删除流程定义(删除所有的对应的流程信息-包含所有版本)")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfProcdefService.checkDelete(id,true);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义(删除某版本的流程定义)
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过camundaProcdefId删除流程定义(删除某版本的流程定义)", notes = "通过camundaProcdefId删除流程定义(删除某版本的流程定义)")
    @DeleteMapping("/delVerByCamundaProcdefId/{id}" )
    public Result<Boolean> delVerByCamundaProcdefId(@PathVariable String id) {
        Boolean result = wfProcdefService.delVerByCamundaDefId(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过camundaid查询xml
     * @param camundaDefId camundaDefId
     * @return Result
     */
    @ApiOperation(value = "通过camundaid查询xml", notes = "通过camundaid查询xml")
    @GetMapping("/getXmlByCamundaId")
    public void getXmlByCamundaId(HttpServletResponse response, HttpServletRequest request,@RequestParam("camundaDefId")String camundaDefId) {
        InputStream inputStream = wfProcdefService.getXmlByCamundaId(camundaDefId);
        response.setCharacterEncoding("GBK");
        try {
            //写出
            OutputStream out = response.getOutputStream();
            //定义读取缓冲区
            byte buffer[] = new byte[1024];
            //定义读取长度
            int len = 1024;
            //循环读取
            while((len = inputStream.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            inputStream.close();
            out.flush();
            out.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据deploymentId 修改xml 文件并且调用重新发布
     * @param deploymentId deploymentId
     * @return Result
     */
    @ApiOperation(value = "根据deploymentId 修改xml 文件并且调用重新发布", notes = "根据deploymentId 修改xml 文件并且调用重新发布")
    @PostMapping("/updateXmlByDeploymentId")
    public void updateXmlByDeploymentId(@RequestParam("deploymentId")String deploymentId,@RequestParam("data") MultipartFile data) {
        String xml = convertXml(data);
        wfProcdefService.updateXmlByDeploymentId(deploymentId,xml);
    }

    /**
     * 根据流程定义 查询出当前生效的版本 并且返回 生效版本的xml文件
     * @param camundaProcdefId camundaDefId
     * @return Result
     */
    @ApiOperation(value = "根据流程定义 查询出当前生效的版本 并且返回 生效版本的xml文件", notes = "根据流程定义 查询出当前生效的版本 并且返回 生效版本的xml文件")
    @PostMapping("/createNewVersionByCamundaProcdefId")
    public Result createNewVersionByCamundaProcdefId(@RequestParam("camundaProcdefId")String camundaProcdefId) {
        return Result.success(wfProcdefService.createNewVersionByCamundaId(camundaProcdefId));
    }

    /**
     * 根据流程定义Key 查询出 未生效版本信息
     * @param camundaProcdefKey camundaProcdefKey
     * @return Result
     */
    @ApiOperation(value = "根据流程定义Key 未生效版本信息", notes = "根据流程定义Key 未生效版本信息")
    @PostMapping("/queryVerListByCamundaProcdefKey")
    public Result<List<WfProcDefVo>>queryVerListByCamundaProcdefKey(@RequestParam("camundaProcdefKey")String camundaProcdefKey) {
        List<WfProcDefVo> list = wfProcdefService.queryVerListByCamundaKey(camundaProcdefKey,YesNo.NO.code);
        return Result.success(list);
    }

    /**
     * 根据流程定义Key 查询出所有对应的版本信息
     * @param camundaProcdefKey camundaProcdefKey
     * @return Result
     */
    @ApiOperation(value = "根据流程定义Key 查询出所有对应的版本信息", notes = "根据流程定义Key 查询出所有对应的版本信息")
    @PostMapping("/queryVerAllListByCamundaProcdefKey")
    public Result<List<WfProcDefVo>>queryVerAllListByCamundaProcdefKey(@RequestParam("camundaProcdefKey")String camundaProcdefKey) {
        List<WfProcDefVo> list = wfProcdefService.queryVerListByCamundaKey(camundaProcdefKey,null);
        return Result.success(list);
    }

    /**
     * 根据camundaDefId设置 生效版本
     * @param camundaProcdefId camundaProcdefId
     * @return Result
     */
    @ApiOperation(value = "根据camundaDefId设置 生效版本", notes = "根据camundaDefId设置 生效版本")
    @PostMapping("/setCurrentVerByCamundaProcdefId")
    public Result setCurrentVerByCamundaProcdefId(@RequestParam("camundaProcdefId")String camundaProcdefId) {
        return Result.success(wfProcdefService.setCurrentVerByCamundaId(camundaProcdefId));
    }

    /**
     * 新建表单页面时 根据流程定义key 获取对应的流程定义信息 以及firtNode 表单权限信息
     * @param camundaProcdefKey camundaProcdefKey
     * @return Result
     */
    @ApiOperation(value = "新建表单页面时 根据流程定义key 获取对应的流程定义信息 以及firtNode 表单权限信息", notes = "新建表单页面时 根据流程定义key 获取对应的流程定义信息 以及firtNode 表单权限信息")
    @GetMapping("/createFormByCamundaDefKey")
    public Result<WfProcDefVo>createFormByCamundaDefKey(@RequestParam("camundaProcdefKey")String camundaProcdefKey) {
        WfProcDefVo wfProcDefVo = wfProcdefService.createFormByCamundaDefKey(camundaProcdefKey);
        return Result.success(wfProcDefVo);
    }

    /**
     * 根据流程定义和 nodeKey 获取表单信息 以及表单权限信息
     * @param camundaProcdefId camunda流程定义id
     * @Param nodeKey  camunda 节点定义id
     * @return Result
     */
    @ApiOperation(value = "根据流程定义和 nodeKey 获取表单信息 以及表单权限信息", notes = "根据流程定义和 nodeKey 获取表单信息 以及表单权限信息")
    @GetMapping("/getProcDefInfoByCamundaProcdefId")
    public Result<WfProcDefVo>getProcDefInfoByCamundaProcdefId(@RequestParam("camundaProcdefId")String camundaProcdefId,@RequestParam("nodeKey")String nodeKey) {
        WfProcDefVo wfProcDefVo = wfProcdefService.getProcDefInfoByCamundaProcdefId(camundaProcdefId, nodeKey);
        return Result.success(wfProcDefVo);
    }

    /**
     * 编辑流程定义时 返回前端是否存在正在跑得流程实例
     * @param camundaProcdefId camundaProcdefId
     * @return Result
     */
    @ApiOperation(value = "编辑流程定义时 返回前端是否存在正在跑得流程实例", notes = "编辑流程定义时 返回前端是否存在正在跑得流程实例")
    @GetMapping("/getIsExistInstProcByCamundaProcdefId/{camundaProcdefId}")
    public Result getIsExistInstProcByCamundaProcdefId(@PathVariable("camundaProcdefId") String camundaProcdefId) {
        return Result.success(wfProcessService.getIsExistInstProcByCamundaProcdefId(camundaProcdefId));
    }

    /**
     * 流程复制
     * @param data 流程定义
     * @return Result
     */
    @ApiOperation(value = "流程复制", notes = "流程复制")
    @PostMapping("/saveProcCopy")
    public Result<Boolean> saveProcCopy(@RequestParam("data") MultipartFile data) throws IOException {
        String xml = convertXml(data);
        //保存 生成 当前生效版本
        wfProcdefService.saveOrUpdateBpmnModel(false,null,xml,true,true);
        log.debug("保存成功");
        return Result.success();
    }

    //------------------------------4.0---------------------
    /**
     * 创建新的流程定义
     * @param deploymentBpmnDto 流程定义信息
     * @return Result
     */
    @ApiOperation(value = "创建新的流程定义", notes = "创建新的流程定义")
    @PostMapping("/deployment")
    public Result saveDeployment(@Validation @RequestBody DeploymentBpmnDto deploymentBpmnDto) throws IOException {
        wfProcdefService.deployment(deploymentBpmnDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 根据camundaProcdefKey设置 启用 禁用
     * @param wfProcdefDto
     * @return Result
     */
    @ApiOperation(value = "根据camundaProcdefKey设置 启用 禁用", notes = "根据camundaProcdefKey设置 启用 禁用")
    @PostMapping("/setStartableByCamundaProcdefKey")
        public Result setStartableByCamundaProcdefKey(@RequestBody WfProcDefDto wfProcdefDto) {
        return Result.success(wfProcdefService.setStartableByCamundaProcdefKey(wfProcdefDto));
    }

    /**
     * 根据camundaProcdefKey设置 是否处理中心显示
     * @param wfProcdefDto
     * @return Result
     */
    @ApiOperation(value = "根据camundaProcdefKey设置 是否处理中心显示", notes = "根据camundaProcdefKey设置 是否处理中心显示")
    @PostMapping("/setBizCenterByCamundaProcdefKey")
    public Result setBizCenterByCamundaProcdefKey(@RequestBody WfProcDefDto wfProcdefDto) {
        return Result.success(wfProcdefService.setBizCenterByCamundaProcdefKey(wfProcdefDto));
    }

}
