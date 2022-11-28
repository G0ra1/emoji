package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.vo.MdmSysVo;
import com.netwisd.base.mdm.entity.MdmSys;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmSysDto;

import java.util.List;

/**
 * @Description 子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
public interface MdmSysService extends IService<MdmSys> {
    Page list(MdmSysDto mdmSysDto);
    List<MdmSysVo> lists(MdmSysDto mdmSysDto);
    MdmSysVo get(Long id);
    Boolean save(MdmSysDto mdmSysDto);
    Boolean update(MdmSysDto mdmSysDto);
    Boolean delete(String id);
}
