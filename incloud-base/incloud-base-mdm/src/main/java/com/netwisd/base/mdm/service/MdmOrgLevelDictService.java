package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmOrgLevelDictDto;
import com.netwisd.base.common.mdm.vo.MdmOrgLevelDictVo;
import com.netwisd.base.mdm.entity.MdmOrgLevelDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 组织级别类型字典 功能描述...
 * @author 云数网讯 zouliming@netwisd.com@netwisd.com
 * @date 2021-08-26 09:56:26
 */
public interface MdmOrgLevelDictService extends IService<MdmOrgLevelDict> {
    Page list(MdmOrgLevelDictDto mdmOrgLevelDictDto);
    Page lists(MdmOrgLevelDictDto mdmOrgLevelDictDto);
    MdmOrgLevelDictVo get(Long id);
    Boolean save(MdmOrgLevelDictDto mdmOrgLevelDictDto);
    Boolean update(MdmOrgLevelDictDto mdmOrgLevelDictDto);
    Boolean delete(Long id);
}
