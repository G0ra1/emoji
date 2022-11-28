package com.netwisd.base.mdm.service;

/**
 * @Description 同步用友 功能描述...
 * @author 云数网讯 真*XHL@netwisd.com
 * @date 2021-09-24 15:54:49
 */
public interface SyncNcService {

    /**
     * 获取用友组织信息
     * @return
     */
    public boolean getNcOrgDatas();

    /**
     * 获取用友部门信息
     * @return
     */
    public boolean getNcDeptDatas();

    /**
     * 获取用友人员信息
     * @return
     */
    public boolean getNcPsnDatas();
}
