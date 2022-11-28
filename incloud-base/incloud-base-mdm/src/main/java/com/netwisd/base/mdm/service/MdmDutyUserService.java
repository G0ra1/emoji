package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.mdm.entity.MdmDutyUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmDutyUserDto;
import com.netwisd.base.mdm.entity.MdmDuty;
import com.netwisd.base.common.mdm.vo.MdmDutyUserVo;

import java.util.List;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
public interface MdmDutyUserService extends IService<MdmDutyUser> {
    Page list(MdmDutyUserDto mdmDutyUserDto);
    List<MdmDutyUserVo> lists(MdmDutyUserDto mdmDutyUserDto);
    MdmDutyUserVo get(Long id);
    Boolean saveList(List<MdmDutyUser> mdmDutyUserList);
    Boolean save(MdmDutyUserDto mdmDutyUserDto);
    Boolean update(MdmDutyUserDto mdmDutyUserDto);
    Boolean delete(Long id);

    /**
     * 获取某用户的主岗
     * @param userId
     * @return
     */
    MdmDutyUser getMaster(Long userId);

    /**
     * 删除某个用户的职务信息
     * @param userId 用户id
     * @param isMaster 主岗还是次岗
     */
    void delByUserIdAndIsMaster(Long userId, Integer isMaster);

    /**
     * 通过职务id查询职务人员信息
     * @param id
     * @return
     */
    List<MdmDutyUserVo> getUserByDutyId(Long id);

    /**
     * 通过职务id查询主岗人数
     * @param id
     * @return
     */
    public Integer masterNumber(Long id);

    /**
     * 通过职务id查询兼岗人数
     * @param id
     * @return
     */
    public Integer partNumber(Long id);

    /**
     * 根据用户id 查询用户职务信息
     * @param id
     * @return
     */
    List<MdmDutyUserVo> getDutyByUserId(String id);

    /**
     * 机构拆分和划转时，职务的所属组织改变，相应的Duty_user关系表也要修改
     * @param mdmDutyList
     * @return
     */
    Boolean updateByDuty(List<MdmDuty> mdmDutyList);

    /**
     * 根据Dutyid删除对应的人员信息
     * @param id
     * @return
     */
    void deleteByDutyId(List id);

    /**
     * 通过职位id查询职位人员信息
     * @param isMaster 是否主职   -1查询所有
     * @param dutyIds  职位ids
     * @return
     */
    List<MdmUserVo> getUserByDutyId(Integer isMaster, String dutyIds);
}
