package com.netwisd.base.wf.starter.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.service.WfProcService;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/2/25 11:22
 */
public abstract class WfProcServiceImpl <M extends BaseMapper<T>, T ,D extends WfDto, V extends WfVo> extends ServiceImpl<M, T> implements WfProcService<T ,D, V> {

    @Autowired
    private WfService wfService;

    @Override
    @SneakyThrows
    public Result procSubmit(D d) {
        /*wfService.submitBefore(d.getHandleDto().getCamundaTaskId());*/
        procBizSubmit(d);
        return wfService.submitEngine(d);
    }

    @Override
    public abstract void procBizSubmit(D d);

    @Override
    public abstract Result procSave(D d);

    @Override
    public abstract V procView(ProcViewDto procViewDto);
}
