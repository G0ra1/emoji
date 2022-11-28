package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmDeviceBindUserDto;
import com.netwisd.base.common.mdm.dto.MdmUserDeviceDto;
import com.netwisd.base.common.mdm.vo.MdmUserDeviceVo;
import com.netwisd.base.mdm.entity.MdmDeviceBindUser;
import com.netwisd.base.mdm.entity.MdmUserDevice;

import java.util.List;

/**
 * @Description 移动设备 移动设备表...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
public interface MdmUserDeviceService extends IService<MdmUserDevice> {
    Page list(MdmUserDeviceDto mdmUserDeviceDto);
    List<MdmUserDeviceVo> lists(MdmUserDeviceDto mdmUserDeviceDto);
    MdmUserDeviceVo get(Long id);
    Boolean save(MdmUserDeviceDto mdmUserDeviceDto);
    Boolean update(MdmUserDeviceDto mdmUserDeviceDto);
    Boolean delete(String id);
    //根据系统类型和唯一标识查询 设备信息
    List<MdmUserDevice> getByTypeAndUnicode(MdmUserDeviceDto mdmUserDeviceDto);

    //根据用户信息 查询设备
    MdmUserDeviceVo getDeviceByUserId(Long userId);

}
