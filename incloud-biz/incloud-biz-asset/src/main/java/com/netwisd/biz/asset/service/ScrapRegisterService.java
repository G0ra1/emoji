package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.MaterialSignDto;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.ScrapRegister;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.ScrapRegisterDto;
import com.netwisd.biz.asset.vo.ScrapRegisterVo;
/**
 * @Description 报废登记 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-05 10:14:01
 */
public interface ScrapRegisterService extends BatchService<ScrapRegister> {
    Page list(ScrapRegisterDto scrapRegisterDto);
    Page lists(ScrapRegisterDto scrapRegisterDto);
    ScrapRegisterVo get(Long id);
    void save(ScrapRegisterDto scrapRegisterDto);
    void saveList(List<ScrapRegisterDto> scrapRegisterDtos);
    void update(ScrapRegisterDto scrapRegisterDto);
    void updateSub(ScrapRegisterDto scrapRegisterDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<ScrapRegisterVo> getByFkIdVo(Long id);
    /**
     * 流程中新增或修改
     * @param
     * @return Result
     */
    ScrapRegisterVo procSaveOrUpdate(ScrapRegisterDto scrapRegisterDto);
    /**
     * 通过流程实例Id查询
     * @param
     * @return Result
     */
    ScrapRegisterVo getByProc(String procInstId);

    /**
     * 通过流程实例Id删除
     * @param procInstId
     * @return
     */
    void deleteByProc(String procInstId);
    /**
     * 报废登记流程结束
     * @param procInstId procInstId
     * @return Result
     */
    Boolean procAuditSuccess(String procInstId);
    /**
     * 报废登记流程保存前
     * @param procInstId procInstId
     * @return Result
     */
    Boolean procSaveBefore(String procInstId);

    Boolean procSaveAfter(String procInstId);
}
