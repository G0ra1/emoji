package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmDeviceBindUserDto;
import com.netwisd.base.common.mdm.dto.MdmUserDeviceDto;
import com.netwisd.base.common.mdm.vo.MdmDeviceBindUserVo;
import com.netwisd.base.common.mdm.vo.MdmUserDeviceVo;
import com.netwisd.base.mdm.entity.MdmDeviceBindUser;
import com.netwisd.base.mdm.entity.MdmUserDevice;

import java.util.List;

/**
 * @Description 设备绑定人员 移动设备表...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
public interface MdmDeviceBindUserService extends IService<MdmDeviceBindUser> {
    Page list(MdmDeviceBindUserDto mdmDeviceBindUserDto);
    List<MdmDeviceBindUserVo> lists(MdmDeviceBindUserDto mdmDeviceBindUserDto);
    MdmDeviceBindUserVo get(Long id);
    Boolean save(MdmDeviceBindUserDto mdmDeviceBindUserDto);
    Boolean update(MdmDeviceBindUserDto mdmDeviceBindUserDto);
    Boolean delete(String id);
    //根据用户id 删除 关系
    Boolean deleteByUserId(Long userId);
    //根据用户id 查询 关系
    MdmDeviceBindUser getByUserId(Long userId);


}
