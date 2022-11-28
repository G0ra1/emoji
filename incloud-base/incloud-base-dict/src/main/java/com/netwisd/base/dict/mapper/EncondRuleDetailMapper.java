package com.netwisd.base.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.dict.dto.EncondRuleDetailDto;
import com.netwisd.base.common.dict.vo.EncondRuleDetailVo;
import com.netwisd.base.dict.entity.EncondRuleDetail;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 编码规则详情 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Mapper
public interface EncondRuleDetailMapper extends BaseMapper<EncondRuleDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param encondRuleDetailDto
     * @return
     */
    Page<EncondRuleDetailVo> getPageList(Page page, @Param("encondRuleDetailDto") EncondRuleDetailDto encondRuleDetailDto);
}
