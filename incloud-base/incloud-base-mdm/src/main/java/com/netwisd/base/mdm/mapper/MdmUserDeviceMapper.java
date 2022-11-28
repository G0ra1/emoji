package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.vo.MdmSysVo;
import com.netwisd.base.mdm.dto.MdmSysDto;
import com.netwisd.base.mdm.entity.MdmSys;
import com.netwisd.base.mdm.entity.MdmUserDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 移动设备 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Mapper
public interface MdmUserDeviceMapper extends BaseMapper<MdmUserDevice> {


}
