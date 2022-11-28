package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfDuplicateResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfDuplicateResponseDto;
import com.netwisd.base.wf.vo.WfDuplicateResponseVo;
/**
 * @Description 传阅回复 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 22:12:53
 */
public interface WfDuplicateResponseService extends IService<WfDuplicateResponse> {
    Page list(WfDuplicateResponseDto wfDuplicateResponseDto);
    Page lists(WfDuplicateResponseDto wfDuplicateResponseDto);
    WfDuplicateResponseVo get(Long id);
    Boolean save(WfDuplicateResponseDto wfDuplicateResponseDto);
    Boolean update(WfDuplicateResponseDto wfDuplicateResponseDto);
    Boolean delete(Long id);
}
