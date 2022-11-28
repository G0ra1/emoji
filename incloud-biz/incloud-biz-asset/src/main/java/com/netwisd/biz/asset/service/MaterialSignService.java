package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.biz.asset.vo.MaterialDistributeDetailVo;
import com.netwisd.biz.asset.vo.MaterialSignDetailVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialSign;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialSignDto;
import com.netwisd.biz.asset.vo.MaterialSignVo;
/**
 * @Description 签收 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
public interface MaterialSignService extends BatchService<MaterialSign> {
    Page list(MaterialSignDto materialSignDto);
    Page lists(MaterialSignDto materialSignDto);
    MaterialSignVo get(Long id);
    Boolean save(MaterialSignDto materialSignDto);
    void saveList(List<MaterialSignDto> materialSignDtos);
    void update(MaterialSignDto materialSignDto);
    void updateSub(MaterialSignDto materialSignDto);
    void delete(Long id);

    /*
    * 流程终新增或修改
    * */
    MaterialSignVo procSaveOrUpdate(MaterialSignDto materialSignDto);

    MaterialSignVo getByProc(String procInstId);

    /*
     * 通过流程实例id删除
     * */
    void deleteByProc(String procInstId);

    /**
     * 资产签收保存前
     */
    Boolean procSaveBefore(String procInstId);

    /**
     * 资产签收保存后
     */
    Boolean procSaveAfter(String procInstId);

    /*
     * 签收流程结束
     * */
    Boolean procAuditSuccess(String procInstId);

    /*
    * 获取待签收领用单编号
    * */
    Page getAcceptList(MaterialSignDto materialSignDto);

    /*
     * 获取待签收物资详情
     * */
    List<AssetsDetailVo> getSignDetailList(MaterialSignDto materialSignDto);

    List<MaterialSignDetailVo> getDetailList(MaterialSignDto materialSignDto);

    Page getOrders(MaterialSignDto signDto);
}
