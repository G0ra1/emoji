package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfExpre;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfExpreDto;
import com.netwisd.base.wf.vo.WfExpreVo;
import com.netwisd.base.wf.vo.WfExpresVo;

import java.util.List;

/**
 * @Description 表达式维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:22:57
 */
public interface WfExpreService extends IService<WfExpre> {
    Page list(WfExpreDto wfExpreDto);
    Page lists(WfExpreDto wfExpreDto);
    WfExpreVo get(Long id);
    Boolean save(WfExpreDto wfExpreDto);
    Boolean update(WfExpreDto wfExpreDto);
    Boolean delete(String ids);
    WfExpresVo getWfExpresVo(Long id);
    List<WfExpreVo> selectBatchIds(List<Long> ids);
    //Result getExpressionList();
}
