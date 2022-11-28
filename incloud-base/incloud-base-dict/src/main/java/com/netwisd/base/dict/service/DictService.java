package com.netwisd.base.dict.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.dict.dto.DictDto;
import com.netwisd.base.dict.dto.DictItemDto;
import com.netwisd.base.dict.entity.Dict;
import com.netwisd.base.dict.vo.DictItemVo;
import com.netwisd.base.dict.vo.DictVo;

import java.util.List;

public interface DictService extends IService<Dict> {

    /**
     * 分页查询
     *
     * @param dictDto
     * @return
     */
    IPage queryPageList(DictDto dictDto);

    /**
     * 新增
     *
     * @param dictDto
     * @return
     */
    boolean addDictType(DictDto dictDto);

    /**
     * 修改
     *
     * @param dictDto
     * @return
     */
    boolean editDictType(DictDto dictDto);

    /**
     * 获取详情
     *
     * @param id
     * @param code
     * @return
     */
    DictVo getDictType(Long id, String code);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    boolean delDictType(String ids);

    /**
     * 字典项目List
     *
     * @param dictItemDto
     * @return
     */
    IPage dictItemPage(DictItemDto dictItemDto);

    /**
     * 字典项目List
     *
     * @param dictItemDto
     * @return
     */
    List<DictItemVo> dictItemList(DictItemDto dictItemDto);

    /**
     * 新增字典项
     *
     * @param dictItemDto
     * @return
     */
    boolean addDictItem(DictItemDto dictItemDto);

    /**
     * 编辑字典项
     *
     * @param dictItemDto
     * @return
     */
    boolean editDictItem(DictItemDto dictItemDto);

    /**
     * 获取字典项详情
     *
     * @param id
     * @param code
     * @return
     */
    DictItemVo getDictItem(Long id, String code);

    /**
     * 删除字典项
     *
     * @param ids
     * @return
     */
    boolean delDictItem(String ids);
}
