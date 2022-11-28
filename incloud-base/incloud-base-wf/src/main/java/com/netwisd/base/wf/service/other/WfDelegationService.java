package com.netwisd.base.wf.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfDelegation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfDelegationDto;
import com.netwisd.base.wf.vo.WfDelegationVo;

import java.util.List;

/**
 * @Description 委办设置 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 14:45:53
 */
public interface WfDelegationService extends IService<WfDelegation> {
    Page list(WfDelegationDto wfDelegationDto);
    Page lists(WfDelegationDto wfDelegationDto);
    WfDelegationVo get(Long id);
    Boolean save(WfDelegationDto wfDelegationDto);
    Boolean update(WfDelegationDto wfDelegationDto);
    Boolean delete(Long id);
    Boolean deleteBatchIds(String ids);
    List<WfDelegationVo> selectBatchIds(List<Long> ids);
    WfDelegationVo getEntity(String designateUserName);
    List<WfDelegationVo> getEntityList(List<String> designateUserNames);

    //管理员查询接口
    Page delegationListForAd(WfDelegationDto wfDelegationDto);
}
