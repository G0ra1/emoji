package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.dto.MaterialAcceptResultDto;
import com.netwisd.biz.asset.entity.MaterialAcceptResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.vo.MaterialAcceptResultVo;

import java.util.List;

/**
 * @Description 资产领用详情结果 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
public interface MaterialAcceptResultService extends IService<MaterialAcceptResult> {
    Page list(MaterialAcceptResultDto materialAcceptResultDto);
    Page lists(MaterialAcceptResultDto materialAcceptResultDto);
    MaterialAcceptResultVo get(Long id);
    Boolean save(MaterialAcceptResultDto materialAcceptResultDto);
    Boolean saveList(List<MaterialAcceptResultDto> materialAcceptResultDtos);
    Boolean update(MaterialAcceptResultDto materialAcceptResultDto);
    Boolean delete(Long id);


}
