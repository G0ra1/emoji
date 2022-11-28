package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.entity.MaterialDeliveryResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDeliveryResultDto;
import com.netwisd.biz.asset.vo.MaterialDeliveryResultVo;

import java.util.List;

/**
 * @Description 资产出库详情表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:56
 */
public interface MaterialDeliveryResultService extends IService<MaterialDeliveryResult> {
    Page list(MaterialDeliveryResultDto materialDeliveryResultDto);
    Page lists(MaterialDeliveryResultDto materialDeliveryResultDto);
    MaterialDeliveryResultVo get(Long id);
    Boolean save(MaterialDeliveryResultDto materialDeliveryResultDto);
    Boolean saveList(List<MaterialDeliveryResultDto> maintDeliveryResultDtos);
    Boolean update(MaterialDeliveryResultDto materialDeliveryResultDto);
    Boolean delete(Long id);

    /**
     * 获取可以退库的 出库详情数据
     * @param materialDeliveryResultDto
     * @return
     */
    Page<MaterialDeliveryResultVo> getDeliveryReuslt(MaterialDeliveryResultDto materialDeliveryResultDto);

}
