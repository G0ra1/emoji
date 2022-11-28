package com.netwisd.base.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.dict.dto.EncondRuleValueDto;
import com.netwisd.base.common.dict.vo.EncondRuleValueVo;
import com.netwisd.base.dict.entity.EncondRuleValue;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 编码规则值 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:29
 */
@Mapper
public interface EncondRuleValueMapper extends BaseMapper<EncondRuleValue> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param encondRuleValueDto
     * @return
     */
    Page<EncondRuleValueVo> getPageList(Page page, @Param("encondRuleValueDto") EncondRuleValueDto encondRuleValueDto);
}
