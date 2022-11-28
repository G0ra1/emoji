package com.netwisd.biz.mdm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.*;
import com.netwisd.biz.mdm.service.*;
import com.netwisd.biz.mdm.vo.*;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * @Description Api功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api" )
@Api(value = "Api", tags = "Api")
@Slf4j
public class MdmApiController {

    private final  ItemService itemService;
    private final ContractSellService contractSellService;
    private final ItemClassifyService itemClassifyService;
    private final CustomerService customerService;
    private final ContractPurchaseService contractPurchaseService;
    private final ContractPurchaseDetailsService contractPurchaseDetailsService;
    private final ProjectService projectService;
    private final SupplierService supplierService;
    private final MdmApiService mdmApiService;
    private final SyncGldService syncGldService;
    private static int size=200;
    private static int current=1;
    private static String orgid="8382818622c6423491c3ebd2c60b118a";

    /**
     * rest方式获取销售合同数据
     * @param contractSellDto 销售合同
     * @return
     */
    @ApiOperation(value = "rest方式获取销售合同数据", notes = "rest方式获取销售合同数据")
    @PostMapping("/queerySellList" )
    public Result<List<ContractSellVo>> queerySellList(@RequestBody ContractSellDto contractSellDto) {
        List<ContractSellVo> list = contractSellService.lists(contractSellDto);
        return Result.success(list);
    }
    /**
     * rest方式获取物资数据
     * @param itemDto 物资
     * @return
     */
    @ApiOperation(value = "rest方式获取物资数据", notes = "rest方式获取物资数据")
    @PostMapping("/queryItemList" )
    public Result<List<ItemVo>> queryItemList(@RequestBody ItemDto itemDto) {
        List<ItemVo> list= itemService.lists(itemDto);
        return Result.success(list);
    }

    /**
     * rest方式获取物资分类数据
     * @param itemClassifyDto 物资分类
     * @return
     */
    @ApiOperation(value = "rest方式获取物资分类数据", notes = "rest方式获取物资分类数据")
    @PostMapping("/queryItemClassifyList" )
    public Result<List<ItemClassifyAllVo>> queryItemClassifyList(@RequestBody ItemClassifyDto itemClassifyDto) {
        List<ItemClassifyAllVo> list = itemClassifyService.lists(itemClassifyDto);
        return Result.success(list);
    }

    /**
     * rest方式获取客户数据
     * @param customerDto 客户
     * @return
     */
    @ApiOperation(value = "rest方式获取客户数据", notes = "rest方式获取客户数据")
    @PostMapping("/queryCustomerList" )
    public Result<List<CustomerVo>> queryCustomerList(@RequestBody CustomerDto customerDto) {
        List<CustomerVo> list= customerService.lists(customerDto);
        return Result.success(list);
    }

    /**
     * rest方式获取采购合同数据
     * @param contractPurchaseDto 采购合同
     * @return
     */
    @ApiOperation(value = "rest方式获取采购合同数据", notes = "rest方式获取采购合同数据")
    @PostMapping("/queryPurchaseList" )
    public Result<List<ContractPurchaseVo>> queryPurchaseList(@RequestBody ContractPurchaseDto contractPurchaseDto) {
        List<ContractPurchaseVo> list = contractPurchaseService.lists(contractPurchaseDto);
        return Result.success(list);
    }

    /**
     * rest方式获取采购合同清单数据
     * @param contractPurchaseDetailsDto 采购合同清单
     * @return
     */
    @ApiOperation(value = "rest方式获取采购合同清单数据", notes = "rest方式获取采购合同清单数据")
    @PostMapping("/queryPurchaseDetailList" )
    public Result<List<ContractPurchaseDetailsVo>> queryPurchaseDetailList(@RequestBody ContractPurchaseDetailsDto contractPurchaseDetailsDto) {
        List<ContractPurchaseDetailsVo> list = contractPurchaseDetailsService.lists(contractPurchaseDetailsDto);
        return Result.success(list);
    }

    /**
     * rest方式获取项目数据
     * @param projectDto 项目
     * @return
     */
    @ApiOperation(value = "rest方式获取项目数据", notes = "rest方式获取项目数据")
    @PostMapping("/queryProjectList" )
    public Result<List<ProjectVo>> queryProjectList(@RequestBody ProjectDto projectDto) {
        List<ProjectVo> list = projectService.lists(projectDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * rest方式获取供应商数据
     * @param supplierDto 供应商
     * @return
     */
    @ApiOperation(value = "rest方式获取供应商数据", notes = "rest方式获取供应商数据")
    @PostMapping("/querySupplierList" )
    public Result<List<SupplierVo>> querySupplierList(@RequestBody SupplierDto supplierDto) {
        List<SupplierVo> voList = supplierService.lists(supplierDto);
        return Result.success(voList);
    }

    /**
     * 获取集采供应商数据
     * @param
     * @return
     */
    @ApiOperation(value = "获取集采供应商数据", notes = "获取集采供应商数据")
    @PostMapping("/getSupplierList" )
    public Result<String> getSupplierList() throws IOException, ParseException {
        mdmApiService.getSupplierList(current,size);
        return Result.success("获取集采供应商数据成功");
    }
    /**
     * 获取集采采购合同数据
     * @param
     * @return
     */
    @ApiOperation(value = "获取集采采购合同数据", notes = "获取集采采购合同数据")
    @PostMapping("/getContractPxurchaseList" )
    public Result<String> getContractPurchaseList() throws IOException, ParseException {
        mdmApiService.getPurchaseListByOrgid(current,size, orgid);
        return Result.success("获取集采采购合同数据成功");
    }
    /**
     * 获取集采项目数据
     * @param
     * @return
     */
    @ApiOperation(value = "获取集采项目数据", notes = "获取集采项目数据")
    @PostMapping("/getProjectList" )
    public Result<String> getProjectList() throws IOException, ParseException {
        mdmApiService.getProjectList(current,size, orgid);
        return Result.success("获取集采项目数据成功");
    }

    @GetMapping("/getToken" )
    public void test(@RequestParam(value = "code",required = false) String code,@RequestParam(value = "state",required = false) String state
            ,@RequestParam(value = "accessToken",required = false) String accessToken,@RequestParam(value = "expireIn",required = false) String expireIn) throws MalformedURLException ,java.io.IOException {
        mdmApiService.getToken(code,state,accessToken,expireIn);
    }
    @GetMapping("/getAuthorize" )
    public void getAuthorize() throws MalformedURLException ,java.io.IOException {
        mdmApiService.getAuthorize();
    }
}
