package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmUserDetailDto;
import com.netwisd.base.common.mdm.vo.MdmUserDetailVo;
import com.netwisd.base.mdm.entity.MdmUserDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 用户详情 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 09:09:37
 */
public interface MdmUserDetailService extends IService<MdmUserDetail> {
    Page list(MdmUserDetailDto mdmUserDetailDto);
    Page lists(MdmUserDetailDto mdmUserDetailDto);
    MdmUserDetailVo get(Long id);
    Boolean save(MdmUserDetailDto mdmUserDetailDto);
    Boolean update(MdmUserDetailDto mdmUserDetailDto);
    Boolean delete(Long id);
}
