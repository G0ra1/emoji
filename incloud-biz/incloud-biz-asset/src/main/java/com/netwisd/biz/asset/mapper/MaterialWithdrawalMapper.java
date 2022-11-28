package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialWithdrawal;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDto;
import com.netwisd.biz.asset.vo.MaterialWithdrawalVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产退库 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
@Mapper
public interface MaterialWithdrawalMapper extends BaseMapper<MaterialWithdrawal> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialWithdrawalDto
     * @return
     */
    Page<MaterialWithdrawalVo> getPageList(Page page, @Param("materialWithdrawalDto") MaterialWithdrawalDto materialWithdrawalDto);
}
