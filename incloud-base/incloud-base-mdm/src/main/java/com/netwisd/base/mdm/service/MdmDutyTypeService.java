package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmDutyType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmDutyTypeDto;
import com.netwisd.base.mdm.vo.MdmDutyTypeVo;
/**
 * @Description 职务分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:00:30
 */
public interface MdmDutyTypeService extends IService<MdmDutyType> {
    Page list(MdmDutyTypeDto mdmDutyTypeDto);
    Page lists(MdmDutyTypeDto mdmDutyTypeDto);
    MdmDutyTypeVo get(Long id);
    Boolean save(MdmDutyTypeDto mdmDutyTypeDto);
    Boolean update(MdmDutyTypeDto mdmDutyTypeDto);
    Boolean delete(String id);
}
