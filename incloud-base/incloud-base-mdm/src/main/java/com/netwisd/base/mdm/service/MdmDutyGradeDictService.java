package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmDutyGradeDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmDutyGradeDictDto;
import com.netwisd.base.mdm.vo.MdmDutyGradeDictVo;
import com.netwisd.base.mdm.vo.MdmPostGradeDictVo;

import java.util.List;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
public interface MdmDutyGradeDictService extends IService<MdmDutyGradeDict> {
    Page list(MdmDutyGradeDictDto mdmDutyGradeDictDto);
    List<MdmDutyGradeDictVo> lists(MdmDutyGradeDictDto mdmDutyGradeDictDto);
    MdmDutyGradeDictVo get(Long id);
    Boolean save(MdmDutyGradeDictDto mdmDutyGradeDictDto);
    Boolean update(MdmDutyGradeDictDto mdmDutyGradeDictDto);
    Boolean delete(Long id);
}
