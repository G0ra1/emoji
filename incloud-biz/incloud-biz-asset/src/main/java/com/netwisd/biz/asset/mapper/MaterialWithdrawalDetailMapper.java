package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialWithdrawalDetail;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDetailDto;
import com.netwisd.biz.asset.vo.MaterialWithdrawalDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产退库详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
@Mapper
public interface MaterialWithdrawalDetailMapper extends BaseMapper<MaterialWithdrawalDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialWithdrawalDetailDto
     * @return
     */
    Page<MaterialWithdrawalDetailVo> getPageList(Page page, @Param("materialWithdrawalDetailDto") MaterialWithdrawalDetailDto materialWithdrawalDetailDto);
}
