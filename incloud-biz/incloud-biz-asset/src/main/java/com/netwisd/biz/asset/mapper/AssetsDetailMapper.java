package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.AssetsDetail;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 资产明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-21 14:54:51
 */
@Mapper
public interface AssetsDetailMapper extends BaseMapper<AssetsDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param assetsDetailDto
     * @return
     */
    Page<AssetsDetailVo> getPageList(Page page, @Param("assetsDetailDto") AssetsDetailDto assetsDetailDto);

    /**
     * 根据机构id或者部门id或者人员id
     * @param assetsDetailDto
     * @return
     */
    Page<AssetsDetailVo> getAssetsDetail(Page page, AssetsDetailDto assetsDetailDto);

    Page<AssetsDetailVo> getDetailByAssets(Page page, AssetsDetailDto assetsDetailDto);


    /**
     * 获取验收数量大于入库数量的数据
     */




}
