package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.biz.asset.vo.MaterialAcceptResultVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialWithdrawal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDto;
import com.netwisd.biz.asset.vo.MaterialWithdrawalVo;
/**
 * @Description 资产退库 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
public interface MaterialWithdrawalService extends BatchService<MaterialWithdrawal> {
    Page list(MaterialWithdrawalDto materialWithdrawalDto);
    Page lists(MaterialWithdrawalDto materialWithdrawalDto);
    MaterialWithdrawalVo get(Long id);
    void save(MaterialWithdrawalDto materialWithdrawalDto);
    void saveList(List<MaterialWithdrawalDto> materialWithdrawalDtos);
    void update(MaterialWithdrawalDto materialWithdrawalDto);
    void updateSub(MaterialWithdrawalDto materialWithdrawalDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialWithdrawalVo> getByFkIdVo(Long id);

    /**
     * 流程中新增或修改
     * @param materialWithdrawalDto
     * @return Result
     */
    MaterialWithdrawalVo procSaveOrUpdate(MaterialWithdrawalDto materialWithdrawalDto);
    /**
     * 通过流程实例id查询
     * @param procInstId
     * @return
     */
    MaterialWithdrawalVo getByProc(String procInstId);

    /**
     * 通过流程实例id删除
     * @param procInstId
     * @return Result
     */
    void deleteByProc(String procInstId);

    /**
     * 退库流程结束
     * @param procInstId
     * @return Result
     */
    Boolean procAuditSuccess(String procInstId);

    /**
     * 退库流程保存前
     * @param procInstId
     * @return Result
     */
    Boolean procSaveBefore(String procInstId);

    /**
     * 退库流程保存后
     * @param procInstId
     * @return Result
     */
    Boolean procSaveAfter(String procInstId);

    /**
     * 获取待入库物资信息
     */
    Page<MaterialAcceptResultVo> getWithdrawalDetail(MaterialWithdrawalDto materialWithdrawalDto);
}
