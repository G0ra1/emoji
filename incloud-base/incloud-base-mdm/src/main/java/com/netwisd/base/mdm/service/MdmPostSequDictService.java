package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmPostSequDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmPostSequDictDto;
import com.netwisd.base.mdm.vo.MdmPostSequDictVo;

import java.util.List;

/**
 * @Description 岗位序列 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 11:01:12
 */
public interface MdmPostSequDictService extends IService<MdmPostSequDict> {
    Page list(MdmPostSequDictDto mdmPostSequDictDto);
    List<MdmPostSequDictVo> lists();
    MdmPostSequDictVo get(Long id);
    Boolean save(MdmPostSequDictDto mdmPostSequDictDto);
    Boolean update(MdmPostSequDictDto mdmPostSequDictDto);
    Boolean delete(Long id);
}
