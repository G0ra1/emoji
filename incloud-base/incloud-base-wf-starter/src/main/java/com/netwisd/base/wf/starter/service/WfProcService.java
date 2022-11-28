package com.netwisd.base.wf.starter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/2/25 11:17
 */
public interface WfProcService <T ,D extends WfDto, V extends WfVo> extends IService<T> {
    Result procSubmit(D d);
    Result procSave(D d);
    V procView(ProcViewDto procViewDto);
    void procBizSubmit(D d);
}
