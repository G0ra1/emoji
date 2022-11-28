package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmPostGradeDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmPostGradeDictDto;
import com.netwisd.base.mdm.vo.MdmPostGradeDictVo;

import java.util.List;

/**
 * @Description 岗位职等 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:54:55
 */
public interface MdmPostGradeDictService extends IService<MdmPostGradeDict> {
    Page list(MdmPostGradeDictDto mdmPostGradeDictDto);
    List<MdmPostGradeDictVo> lists(MdmPostGradeDictDto mdmPostGradeDictDto);
    MdmPostGradeDictVo get(Long id);
    Boolean save(MdmPostGradeDictDto mdmPostGradeDictDto);
    Boolean update(MdmPostGradeDictDto mdmPostGradeDictDto);
    Boolean delete(Long id);
}
