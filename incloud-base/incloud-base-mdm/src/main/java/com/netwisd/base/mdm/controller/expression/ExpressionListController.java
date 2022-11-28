package com.netwisd.base.mdm.controller.expression;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.netwisd.base.common.mdm.vo.ExpressionParameterVO;
import com.netwisd.base.common.mdm.vo.ExpressionVO;
import com.netwisd.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.*;
import springfox.documentation.spring.web.DocumentationCache;

import java.util.Collection;
import java.util.List;

/**
 * @program: incloud
 * @description: 工作流表达式列表
 * @author: sunzhenxi
 * @create: 2020-07-14 15:43
 */
@Slf4j
@RestController
@RequestMapping("/expression")
public class ExpressionListController {

    private static final String SWAGGER_GROUP_NAME = "主数据swagger接口文档";

    @Autowired
    private DocumentationCache documentationCache;

    /**
     * 解析分级对工作流提供的表达式、根据Swagger的注解解析对应的值。
     *
     * @param groupName 是对应的controller缩写。
     *                  比如 UserExpressionController 对应的是 user-expression-controller
     * @return
     */
    @GetMapping(value = "/list")
    public Result<List<ExpressionVO>> expressionList(@RequestParam(value = "groupName") String groupName) {
        List<ExpressionVO> returnList = Lists.newArrayList();
        Documentation documentation = documentationCache.documentationByGroup(SWAGGER_GROUP_NAME);
        Multimap<String, ApiListing> apiListingsMap = documentation.getApiListings();
        Collection<ApiListing> apiListingsList = apiListingsMap.asMap().get(groupName);
        if (CollectionUtil.isNotEmpty(apiListingsList)) {
            for (ApiListing apiListing : apiListingsList) {
                List<ApiDescription> apiList = apiListing.getApis();
                for (ApiDescription api : apiList) {
                    List<Operation> operationsList = api.getOperations();
                    for (Operation operation : operationsList) {
                        //解析方法
                        ModelReference responseModel = operation.getResponseModel();
                        ExpressionVO expressionVO = ExpressionVO.buildExpression(api.getPath(), operation, responseModel.getType());
                        //解析参数
                        List<Parameter> parametersList = operation.getParameters();
                        List<ExpressionParameterVO> parameterVOList = Lists.newArrayListWithCapacity(parametersList.size());
                        //构架第一个参数 默认的方法名
                        String path [] = expressionVO.getPath().split("/");
                        //parameterVOList.add(ExpressionParameterVO.buildDefaultParameter(path[path.length-1]));
                        if (CollectionUtil.isNotEmpty(parametersList)) {
                            for (int i = 0; i < parametersList.size(); i++) {
                                Parameter parameter = parametersList.get(i);
                                parameterVOList.add(ExpressionParameterVO.buildParameter(parameter));
                            }
                        }
                        expressionVO.setParamterList(parameterVOList);
                        returnList.add(expressionVO);
                    }
                }
            }
        }
        return Result.success(returnList);
    }

}
