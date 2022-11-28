package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.MaterialChangeDetailDto;
import com.netwisd.biz.asset.vo.MaterialChangeDetailVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialChange;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialChangeDto;
import com.netwisd.biz.asset.vo.MaterialChangeVo;
/**
 * @Description 资产信息变更 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 17:11:54
 */
public interface MaterialChangeService extends BatchService<MaterialChange> {
    Page list(MaterialChangeDto materialChangeDto);
    Page lists(MaterialChangeDto materialChangeDto);
    MaterialChangeVo get(Long id);
    Boolean save(MaterialChangeDto materialChangeDto);
    Boolean saveList(List<MaterialChangeDto> materialChangeDtos);
    Boolean update(MaterialChangeDto materialChangeDto);
    void updateSub(MaterialChangeDto materialChangeDto);
    Boolean delete(Long id);

    /**
     * 根据流程id删除
     * @param procInstId
     */
    Boolean deleteByProc(String procInstId);

    /**
     * 根据流程id查询业务数据
     * @param procInstId
     * @return
     */
    MaterialChangeVo getByProcId(String procInstId);

    MaterialChangeVo procSaveOrUpdate(MaterialChangeDto materialChangeDto);

    Boolean procSaveBefore(String procInstId);

    Boolean procSaveAfter(String procInstId);

    Boolean procAuditSuccess(String procInstId);

    List<MaterialChangeDetailVo> getDetailList(MaterialChangeDetailDto materialChangeDetailDto);
}
