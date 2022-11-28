package com.netwisd.base.mdm.service;

import com.alibaba.fastjson.JSONArray;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;

import java.util.List;

public interface QyWechatApiService {
    //新增或者修改企业微信部门
    void createOrUpdateDept(MdmOrgVo mdmOrgVo,String type);
    //删除企业微信部门（只能删除没有人员和子部门的部门）
    void deleteDept(String qyWechatDeptId);
    //删除企业微信部门（只能删除没有人员的部门可以有子部门）
    void batchDeleteDept(String qyWechatDeptId);
    //删除企业微信部门（子部门及人员都进行删除）
    void deleteDeptAndUser(String qyWechatDeptId);

    //新增或者修改人员
    void createOrUpdateUser(MdmUserVo mdmUserVo,String type);
    //批量删除人员(通过企业微信userid)
    void batchDeleteUser(String userIds);
    //批量删除人员(通过主数据人员id)
    void batchDeleteUserById(List<String> ids);

    //全量覆盖企业微信部门
    JSONArray replaceDept();
    //全量覆盖企业微信用户
    JSONArray replaceUser();
    //全量增量修改人员（不删除）
    JSONArray syncuser();

    //获取企业微信异步接口结果
    JSONArray getResult(String jobId);

    //全量同步企业微信部门（定时调用）
    void syncAllDept();

    //全量同步企业微信人员（定时调用）
    void syncAllUser();
}
