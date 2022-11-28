package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmPostTagDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmPostTagDictDto;
import com.netwisd.base.mdm.vo.MdmPostTagDictVo;

import java.util.List;

/**
 * @Description 岗位标识字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:44:20
 */
public interface MdmPostTagDictService extends IService<MdmPostTagDict> {
    Page list(MdmPostTagDictDto mdmPostTagDictDto);
    List<MdmPostTagDictVo> lists();
    MdmPostTagDictVo get(Long id);
    Boolean save(MdmPostTagDictDto mdmPostTagDictDto);
    Boolean update(MdmPostTagDictDto mdmPostTagDictDto);
    Boolean delete(Long id);
}
