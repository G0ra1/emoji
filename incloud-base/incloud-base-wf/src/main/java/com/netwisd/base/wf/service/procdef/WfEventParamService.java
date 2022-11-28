package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfEventParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfEventParamDto;
import com.netwisd.base.wf.vo.WfEventParamVo;
/**
 * @Description 事件运行参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:10:12
 */
public interface WfEventParamService extends IService<WfEventParam> {
    Page list(WfEventParamDto wfEventParamDto);
    Page lists(WfEventParamDto wfEventParamDto);
    WfEventParamVo get(Long id);
    Boolean save(WfEventParamDto wfEventParamDto);
    Boolean update(WfEventParamDto wfEventParamDto);
    Boolean delete(Long id);
    Boolean deleteByEventId(Long id);
}
