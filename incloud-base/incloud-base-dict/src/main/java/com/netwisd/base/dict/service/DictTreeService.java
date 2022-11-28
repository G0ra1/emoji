package com.netwisd.base.dict.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.dict.dto.DictReceiveDto;
import com.netwisd.base.dict.entity.DictTree;

import java.util.List;

public interface DictTreeService extends IService<DictTree> {

    /**
     * 分页查询
     *
     * @param dictTreeDto
     * @return
     */
    IPage queryPageList(DictTreeDto dictTreeDto);

    /**
     * 列表查询
     *
     * @param dictTreeDto
     * @return
     */
    List<DictTreeVo> list(DictTreeDto dictTreeDto);

    /**
     * 新增
     *
     * @param dictTreeDto
     * @return
     */
    boolean add(DictTreeDto dictTreeDto);

    /**
     * 修改
     *
     * @param dictTreeDto
     * @return
     */
    boolean edit(DictTreeDto dictTreeDto);

    /**
     * 获取详情
     *
     * @param id
     * @param code
     * @return
     */
    DictTreeVo getDictTree(Long id, String code);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    boolean del(String ids);

    /**
     * 根据父级Id获取子集
     *
     * @param parentCode
     * @return
     */
    List<DictTreeVo> childListByParentCode(String parentCode);

    boolean receiveTree(List<DictReceiveDto> receiveDtoList);

    boolean delReceiveTree(List<DictReceiveDto> dictReceiveDtoList);
}
