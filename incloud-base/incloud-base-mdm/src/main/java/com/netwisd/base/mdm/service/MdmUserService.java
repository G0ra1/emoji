package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.mdm.dto.MdmTransferDto;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.netwisd.base.mdm.entity.MdmUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.excel.MdmUserExcel;

import java.util.List;

/**
 * @Description 用户 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 10:48:50
 */
public interface MdmUserService extends IService<MdmUser> {
    Page list(MdmUserDto mdmUserDto);
    List<MdmUserVo> lists(MdmUserDto mdmUserDto);
    MdmUserVo get(Long id);
    List<MdmUserVo> getByIds(String ids);
    Boolean save(MdmUserDto mdmUserDto);
    Boolean saveList(List<MdmUserDto> mdmUserDtoList);
    Boolean update(MdmUserDto mdmUserDto, boolean isSync);
    Boolean delete(String id);
    List<MdmUser> getByIds(List<String> ids);

    //检查密码是否正确
    Boolean checkPassword(MdmUserDto mdmUserDto);

    //修改密码
    Boolean updatePassword(MdmUserDto mdmUserDto);

    //修改头像
    Boolean updatePhoto(MdmUserDto mdmUserDto);

    //修改邮箱
    Boolean updateEmail(MdmUserDto mdmUserDto);

    //修改手机号
    Boolean updatePhoneNum(MdmUserDto mdmUserDto);

    //设置主岗
    Boolean setMasterPost(MdmTransferDto mdmTransferDto);

    //调动部门
    Boolean transferDetp(MdmUserDto mdmUserDto);

    //调动单位
    Boolean transferOrg(MdmUserDto mdmUserDto);

    //设置兼岗 支持多个
    Boolean setAndPost(List<MdmTransferDto> mdmTransferDto);

    //机构划转
    Boolean orgTransfer(List<MdmOrg> mdmOrgList);
    
    //机构拆分/机构撤销（功能一致  前端拼装好直接改人员数据就行）
    Boolean orgSplit(List<MdmUserDto> mdmUserDtoList);

    //部门级排序
    Boolean sortForDept(Long sourceId,Long targetId,String index);

    //全局级排序
    Boolean sortForGlobal(Long sourceId,Long targetId,String index);

    /**
     * 根据userName获取当前人信息
     * @param userName
     * @return
     */
    LoginAppUser findByUserName(String userName);

    /**
     * 根据 手机号 获取当前人信息
     * @param phoneNum 手机号
     * @return
     */
    MdmUser findByPhone(String phoneNum);

    /**
     * 通过手机号查人用于手机号登录（1个手机号多个人报错）
     * @param phoneNum
     * @return
     */
    MdmUser findByPhoneForMsg(String phoneNum);

    /**
     * 根据 身份证号 获取当前人信息
     * @param idNumber 身份证号
     * @return
     */
    MdmUser findByIdNumber(String idNumber);

    /**
     * 根据 邮箱 获取当前人信息
     * @param email 邮箱
     * @return
     */
    MdmUser findByEmail(String email);

    //查询最大的 globalSort
    Integer getMaxGlobalSort();

    //根据OrgId查询出下面所有层级的人员
    List<MdmUserVo> getUserByOrgId(String orgId);

    //设置主职位
    Boolean setMasterDuty(MdmUserDto mdmUserDto);

    //设置兼职 支持多个
    Boolean setAndDuty(MdmUserDto mdmUserDto);

    //根据部门id 精确查询下级所有用户
    List<MdmUserVo> getUserByDeptId(String orgId);

    //MQ推送全新的 用户信息
    MdmUserVo getUserToMQ(Long id);

    //根据身份证号 查询
    MdmUserVo getByIdCard(String idCard);
    List<MdmUserVo> getUserByIdCards(String idCards);

    //给所有可用状态的用户 设置默认角色
    Boolean setAllUserDefaultRole();

    //设置默认角色
    Boolean setUserDefaultRole(List<MdmUser> mdmUserList);

    /**
     * 同步主数据人员
     * @param mdmUserDto
     * @return
     */
    MdmUserVo saveStudyUser(MdmUserDto mdmUserDto);

    /**
     * 删除在线学习人员
     * @param id
     * @return
     */
    Boolean deleteStudyUser(String id);


    /**
     * 修改在线学习主数据人员
     * @param mdmUserDto
     * @return
     */
    Boolean updateStudyUser(MdmUserDto mdmUserDto);

    MdmUserVo regStudyUser(MdmUserDto mdmUserDto);

    /**
     * 根据username获取人信息 企业微信调用
     * @param userName
     * @return
     */
    MdmUser getByUserName(String userName);


    //根据部门id 精确查询下级所有用户 ---单独提供手机端查询
    List<MdmUserVo> getUserByDeptIdForPhone(String orgId);

    List<MdmUserExcel> expertUserInfo();

    //根据parentOrgId查询出下面所有层级的人员  --前端固定展示机构职能选一个
    List<MdmUserVo> getUserByParentOrgId(String orgIds);

    //根据parentDeptId查询出下面所有层级的人员
    List<MdmUserVo> getUserByParentDeptId(String deptIds);
}
