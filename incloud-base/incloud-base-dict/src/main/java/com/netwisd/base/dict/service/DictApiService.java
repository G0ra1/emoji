package com.netwisd.base.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.dict.entity.DictTree;
import com.netwisd.base.dict.vo.DictItemVo;

import java.util.List;

public interface DictApiService extends IService<DictTree> {

    /**
     * 列表查询
     *
     * @param dictTreeDto
     * @return
     */
    List<DictTreeVo> treeList(DictTreeDto dictTreeDto);

    /**
     * 字典树详情
     *
     * @param dictId
     * @param dictCode
     * @return
     */
    DictTreeVo treeDetail(Long dictId, String dictCode);

    /**
     * 字典项详情
     *
     * @param dictId
     * @param dictItemCode
     * @return
     */
    DictItemVo itemDetail(Long dictId, String dictItemCode);

    /**
     * 字典项列表
     *
     * @param dictCode
     * @return
     */
    List<DictItemVo> itemList(String dictCode);
}
