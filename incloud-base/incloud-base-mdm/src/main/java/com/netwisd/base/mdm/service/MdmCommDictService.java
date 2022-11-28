package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmCommDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmCommDictDto;
import com.netwisd.base.mdm.vo.MdmCommDictVo;

import java.util.List;

/**
 * @Description mdm通用字典  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-16 15:58:36
 */
public interface MdmCommDictService extends IService<MdmCommDict> {
    Page list(MdmCommDictDto mdmCommDictDto);
    List<MdmCommDictVo> lists(MdmCommDictDto mdmCommDictDto);
    MdmCommDictVo get(Long id);
    Boolean save(MdmCommDictDto mdmCommDictDto);
    Boolean update(MdmCommDictDto mdmCommDictDto);
    Boolean delete(Long id);
}
