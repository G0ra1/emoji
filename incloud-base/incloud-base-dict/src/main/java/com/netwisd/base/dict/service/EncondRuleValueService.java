package com.netwisd.base.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.dict.dto.EncondRuleDto;
import com.netwisd.base.common.dict.dto.EncondRuleValueDto;
import com.netwisd.base.common.dict.vo.EncondRuleValueVo;
import com.netwisd.base.common.dict.vo.EncondRuleVo;
import com.netwisd.base.dict.entity.EncondRuleValue;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * @Description 编码规则值 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:29
 */
public interface EncondRuleValueService extends IService<EncondRuleValue> {
    Page list(EncondRuleValueDto encondRuleValueDto);
    Page lists(EncondRuleValueDto encondRuleValueDto);
    EncondRuleValueVo get(Long id);
    Boolean save(EncondRuleValueDto encondRuleValueDto);
    Boolean update(EncondRuleValueDto encondRuleValueDto);
    Boolean delete(String ids);

    //1 业务表单需要传递 规则ID 表单Map字段值 2工作流 只传递ruleType=2 　其他规则传递ruleType=３
    String creatEncondValue(EncondRuleDto encondRuleDto);
    //通过规则ID跟规则值删除 规则值记录
    Boolean deleteEncondValue(String formName,String value,String ruleType);
    //根据表单名称查询该表单关联的规则详情
    EncondRuleVo getRuleDetail(String formName, String ruleType);

    //根据表单名称 业务规则类型 业务实体创建规则号
    String  getRuleValue(String formName, String encondField, Map<String, Object> entityMap);

    //根据表单名称 业务字段 创建规则号 --前端调用
    String getRuleValueForqd(String formName, String encondField);

    //根据流程实例Id生成规则值 --前端调用
    String createWfValue(String camundaProcdefId);
}
