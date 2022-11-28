package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfExpreParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfExpreParamDto;
import com.netwisd.base.wf.vo.WfExpreParamVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 表达式参数维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:23:36
 */
public interface WfExpreParamService extends BatchService<WfExpreParam> {
    Page list(WfExpreParamDto wfExpreParamDto);
    Page lists(WfExpreParamDto wfExpreParamDto);
    WfExpreParamVo get(Long id);
    Boolean save(WfExpreParamDto wfExpreParamDto);
    Boolean update(WfExpreParamDto wfExpreParamDto);
    Boolean delete(Long id);
    Boolean removeByExpreId(Long id);
    List<WfExpreParam> getByExpreId(Long id);
}
