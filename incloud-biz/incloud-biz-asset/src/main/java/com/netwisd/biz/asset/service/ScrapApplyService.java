package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.ScrapApply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.ScrapApplyDto;
import com.netwisd.biz.asset.vo.ScrapApplyVo;
/**
 * @Description 报废申请 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-03 14:54:19
 */
public interface ScrapApplyService extends BatchService<ScrapApply> {
    Page list(ScrapApplyDto scrapApplyDto);
    Page lists(ScrapApplyDto scrapApplyDto);
    ScrapApplyVo get(Long id);
    void save(ScrapApplyDto scrapApplyDto);
    void saveList(List<ScrapApplyDto> scrapApplyDtos);
    void update(ScrapApplyDto scrapApplyDto);
    void updateSub(ScrapApplyDto scrapApplyDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<ScrapApplyVo> getByFkIdVo(Long id);

    /**
     * 流程中新增或修改
     * @param scrapApplyDto
     * @return Result
     */
    ScrapApplyVo procSaveOrUpdate(ScrapApplyDto scrapApplyDto);


    /**
     * 通过流程实例id进行查询
     * @param procInstId
     * @return scrapApplyVo
     */
    ScrapApplyVo getByProc(String procInstId);

    /**
     * 通过流程实例id进行删除
     * @param procInstId
     * @return scrapApplyVo
     */
    void deleteByProc(String procInstId);

    /**
     * 报废申请流程保存前
     * @param procInstId procInstId
     * @return Result
     */
    Boolean procSaveBefore(String procInstId);

    /**
     * 报废申请流程保存后
     * @param procInstId procInstId
     * @return Result
     */
    Boolean procSaveAfter(String procInstId);

    /**
     * 报废申请流程结束
     * @param procInstId procInstId
     * @return Result
     */
    Boolean procAuditSuccess(String procInstId);

    Page getRegisterList(ScrapApplyDto scrapApplyDto);
}
