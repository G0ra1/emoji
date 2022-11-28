package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmDutySequDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmDutySequDictDto;
import com.netwisd.base.mdm.vo.MdmDutySequDictVo;

import java.util.List;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
public interface MdmDutySequDictService extends IService<MdmDutySequDict> {
    Page list(MdmDutySequDictDto mdmDutySequDictDto);
    List<MdmDutySequDictVo> lists();
    MdmDutySequDictVo get(Long id);
    Boolean save(MdmDutySequDictDto mdmDutySequDictDto);
    Boolean update(MdmDutySequDictDto mdmDutySequDictDto);
    Boolean delete(Long id);
}
