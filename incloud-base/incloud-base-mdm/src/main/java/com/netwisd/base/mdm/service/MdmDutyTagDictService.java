package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmDutyTagDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmDutyTagDictDto;
import com.netwisd.base.mdm.vo.MdmDutyTagDictVo;

import java.util.List;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
public interface MdmDutyTagDictService extends IService<MdmDutyTagDict> {
    Page list(MdmDutyTagDictDto mdmDutyTagDictDto);
    List<MdmDutyTagDictVo> lists();
    MdmDutyTagDictVo get(Long id);
    Boolean save(MdmDutyTagDictDto mdmDutyTagDictDto);
    Boolean update(MdmDutyTagDictDto mdmDutyTagDictDto);
    Boolean delete(Long id);
}
