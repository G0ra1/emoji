package com.netwisd.base.dict.controller.api;

import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.dto.EncondRuleDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.common.dict.vo.EncondRuleVo;
import com.netwisd.base.dict.service.DictApiService;
import com.netwisd.base.dict.service.EncondRuleValueService;
import com.netwisd.base.dict.vo.DictItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Api(value = "DictApi", tags = "字典内部调用ApiController")
public class DictApiController {

    private final DictApiService dictApiService;
    private final EncondRuleValueService encondRuleValueService;

    @GetMapping("/tree/list")
    public List<DictTreeVo> list(DictTreeDto dictTreeDto) {
        return dictApiService.treeList(dictTreeDto);
    }

    @GetMapping("/tree/detailId/{dictId}")
    public DictTreeVo treeDictIdDetail(@PathVariable(name = "dictId") Long dictId) {
        return dictApiService.treeDetail(dictId, StrUtil.EMPTY);
    }

    @GetMapping("/tree/detailCode/{dictCode}")
    public DictTreeVo treeDictCodeDetail(@PathVariable(name = "dictCode") String dictCode) {
        return dictApiService.treeDetail(null, dictCode);
    }

    @GetMapping("/item/list")
    public List<DictItemVo> itemList(@RequestParam("dictCode") String dictCode) {
        return dictApiService.itemList(dictCode);
    }

    @GetMapping("/item/detailId/{dictId}")
    public DictItemVo itemDictIdDetail(@PathVariable(name = "dictId") Long dictId) {
        return dictApiService.itemDetail(dictId, StrUtil.EMPTY);
    }

    @GetMapping("/item/detailCode/{dictItemCode}")
    public DictItemVo itemDictCodeDetail(@PathVariable(name = "dictItemCode") String dictItemCode) {
        return dictApiService.itemDetail(null, dictItemCode);
    }

    /**
     * 根据表单名称查询该表单关联的规则详情
     * @param formName 表单名称
     * @return Result
     */
    @ApiOperation(value = "根据表单名称查询该表单关联的规则详情", notes = "根据表单名称查询该表单关联的规则详情")
    @GetMapping("/getRuleDetail")
    public EncondRuleVo getRuleDetail(@RequestParam String formName, @RequestParam String ruleType) {
        return encondRuleValueService.getRuleDetail(formName,ruleType);
    }

    /**
     * 创建规则号
     * @param encondRuleDto 创建规则号
     * @return Result
     */
    @ApiOperation(value = "创建规则号", notes = "创建规则号")
    @PostMapping("/createEncondValue")
    public String creatEncondValue(@RequestBody EncondRuleDto encondRuleDto) {
        String value = encondRuleValueService.creatEncondValue(encondRuleDto);
        return value;
    }


    /**
     * 通过规则ID跟规则值删除 规则值记录
     * @param formName 表单名称
     * @param value 规则值
     * @param ruleType 规则类型 1业务表单2工作流3其他
     * @return Result
     */
    @ApiOperation(value = "通过表单名称  规则值 规则类型 删除 规则值记录", notes = "通过表单名称跟规则值删除 规则值记录")
    @DeleteMapping("/deleteEncondValue" )
    public Boolean deleteEncondValue(@RequestParam String formName,@RequestParam String value,@RequestParam String ruleType) {
        Boolean result = encondRuleValueService.deleteEncondValue(formName,value,ruleType);
        return result;
    }


    /**
     * 根据表单名称 业务类型 表单字段生成规则值 --后端调用
     * @param formName 表单名称
     * @param encondField 规则值字段
     * @param entityMap 业务实体字段Map
     * @return Result
     */
    @ApiOperation(value = "根据表单名称 规则值字段 表单字段生成规则值", notes = "根据表单名称 规则值字段 表单字段生成规则值")
    @PostMapping("/createValue")
    public String getRuleValue(@RequestParam String formName, @RequestParam String encondField, @RequestBody Map<String, Object> entityMap ) {
        return encondRuleValueService.getRuleValue(formName,encondField,entityMap);
    }

}
