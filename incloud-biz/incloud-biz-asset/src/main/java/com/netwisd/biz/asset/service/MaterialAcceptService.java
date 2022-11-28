package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.dto.MaterialAcceptAssetsDto;
import com.netwisd.biz.asset.dto.MaterialAcceptDto;
import com.netwisd.biz.asset.vo.MaterialAcceptAssetsVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.MaterialAccept;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.vo.MaterialAcceptVo;
/**
 * @Description 资产领用 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:59
 */
public interface MaterialAcceptService extends BatchService<MaterialAccept> {
    Page list(MaterialAcceptDto materialAcceptDto);
    Page lists(MaterialAcceptDto materialAcceptDto);
    MaterialAcceptVo get(Long id);
    Boolean save(MaterialAcceptDto materialAcceptDto);
    void saveList(List<MaterialAcceptDto> materialAcceptDtos);
    Boolean update(MaterialAcceptDto materialAcceptDto);
    void updateSub(MaterialAcceptDto materialAcceptDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<MaterialAcceptVo> getByFkIdVo(Long id);

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    void deleteByProc(String procInstId);

    /**
     * 根据流程实例id获取数据
     * @param procInstId
     * @return
     */
    MaterialAcceptVo getByProc(String procInstId);

    /**
     * 领用流程保存前方法
     * @param processInstanceId
     * @return
     */
    Boolean acceptSaveBefore(String processInstanceId);

    /**
     * 领用流程保存后方法
     * @param processInstanceId
     * @return
     */
    Result acceptSaveAfter(String processInstanceId);

    /**
     * 领用流程完成后
     * @param processInstanceId
     * @return
     */
    Boolean acceptAuditSuccess(String processInstanceId);

    MaterialAcceptVo procSaveOrUpdate(MaterialAcceptDto materialAcceptDto);

    /**
     * 任务发送
     * @param procInstId
     * @return
     */
    Result sendTask(String procInstId);

    /**
     * 任务发送
     * @param procInstId
     * @return
     */
    Boolean processTask(String procInstId);
    /**
     * 获取待签收物资信息
     * @param materialAcceptDto
     * @return
     */
    Page getAcceptList(MaterialAcceptDto materialAcceptDto);

    Boolean sendDistriButeTask(String processInstanceId);

    /**
     * 个人终止领用
     * @param procInstId
     * @return
     */
    Boolean stopAccept(String procInstId);
}
