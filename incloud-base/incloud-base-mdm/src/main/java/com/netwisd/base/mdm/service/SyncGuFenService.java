package com.netwisd.base.mdm.service;

/**
 * @Description 同步股份 功能描述...
 * @author 云数网讯 真*XHL@netwisd.com
 * @date 2021-08-27 15:54:49
 */
public interface SyncGuFenService {

    /**
     * 同步股份组织信息以及岗位信息
     * @return
     */
    public boolean syncGuFenOrgAndPost();

    /**
     * 同步股份组织信息以及岗位信息---增量
     * @return
     */
    public boolean syncGuFenOrgAndPostIncrement(String dateStr);



    /**
     * 同步股份人员信息以及人员得主岗位信息
     * @return
     */
    public boolean syncGuFenUserAndMasterPost();

    /**
     * 同步股份人员信息以及人员得主岗位信息---增量
     * @return
     */
    public boolean syncGuFenUserAndMasterPostIncrement(String curStr);

    /**
     * 同步股份兼岗信息
     * @return
     */
    public boolean syncGuFenPosition();

    /**
     * 同步股份字典信息
     * @return
     */
    public boolean syncGuFenDict();

    //处理过期的机构信息
    void handleOrgPastDue();

    //处理过期的岗位信息
    void handlePostPastDue();
}
