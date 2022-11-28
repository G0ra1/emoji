package com.netwisd.base.dict.service;

import com.netwisd.base.common.dict.dto.EncondRuleDetailDto;
import com.netwisd.base.common.dict.vo.EncondRuleDetailVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.base.dict.entity.EncondRuleDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 编码规则详情 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
public interface EncondRuleDetailService extends BatchService<EncondRuleDetail> {
    Page list(EncondRuleDetailDto encondRuleDetailDto);
    Page lists(EncondRuleDetailDto encondRuleDetailDto);
    EncondRuleDetailVo get(Long id);
    void save(EncondRuleDetailDto encondRuleDetailDto);
    void saveList(List<EncondRuleDetailDto> encondRuleDetailDtos);
    void update(EncondRuleDetailDto encondRuleDetailDto);
    void updateSub(EncondRuleDetailDto encondRuleDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<EncondRuleDetailVo> getByFkIdVo(Long id);
}
