package com.netwisd.base.dict.service;

import com.netwisd.base.common.dict.dto.EncondRuleDto;
import com.netwisd.base.common.dict.vo.EncondRuleVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.base.dict.entity.EncondRule;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 编码规则 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
public interface EncondRuleService extends BatchService<EncondRule> {
    Page list(EncondRuleDto encondRuleDto);
    Page lists(EncondRuleDto encondRuleDto);
    EncondRuleVo get(Long id);
    void save(EncondRuleDto encondRuleDto);
    void saveList(List<EncondRuleDto> encondRuleDtos);
    void update(EncondRuleDto encondRuleDto);
    void updateSub(EncondRuleDto encondRuleDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<EncondRuleVo> getByFkIdVo(Long id);
}
