package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.entity.MaterialDistributeTask;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.MaterialDistributeTaskDto;
import com.netwisd.biz.asset.vo.MaterialDistributeTaskVo;
/**
 * @Description 资产派发任务表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:55:47
 */
public interface MaterialDistributeTaskService extends IService<MaterialDistributeTask> {
    Page list(MaterialDistributeTaskDto materialDistributeTaskDto);
    Page lists(MaterialDistributeTaskDto materialDistributeTaskDto);
    MaterialDistributeTaskVo get(Long id);
    Boolean save(MaterialDistributeTaskDto materialDistributeTaskDto);
    Boolean update(MaterialDistributeTaskDto materialDistributeTaskDto);
    Boolean delete(Long id);
}
