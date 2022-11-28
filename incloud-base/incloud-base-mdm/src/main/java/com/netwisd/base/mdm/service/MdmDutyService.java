package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmDutyDto;
import com.netwisd.base.mdm.entity.MdmDuty;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.netwisd.base.common.mdm.vo.MdmDutyVo;

import java.util.List;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
public interface MdmDutyService extends IService<MdmDuty> {
    Page list(MdmDutyDto mdmDutyDto);
    List<MdmDutyVo> lists(MdmDutyDto mdmDutyDto);
    MdmDutyVo get(Long id);
    Boolean save(List<MdmDutyDto> mdmDutyDtos);
    Boolean saveOne(MdmDutyDto mdmDutyDtos);
    Boolean update(MdmDutyDto mdmDutyDto);
    Boolean delete(String id);
    List<MdmDuty> getByIds(List<String> ids);

    /**
     * 复制职务到另外一个部门
     * @param mdmDutyDto
     * @param
     * @return
     */
    Boolean copyToOrg(MdmDutyDto mdmDutyDto);

    /**
     * 通过部门id查询已启用的职务
     * @param id
     * @return
     */
    List<MdmDutyVo> getDuty(Long id);

    /**
     * 通过组织id查询所有的职务
     * @param
     * @return
     */
    List<MdmDutyVo> getAllDuty(MdmDutyDto mdmDutyDto);
    /**
     * 部门内职务排序
     * @param sourceId
     * @param targetId
     * @param index
     * @return
     */
    Boolean sortForDept(Long sourceId,Long targetId,String index);

    /**
     * 根据职务id，修改是否被引用字段为1(表示已被引用)
     * @param id
     * @return
     */
    Boolean isRef(Long id);

    /**
     * 机构拆分/划转/撤销
     * @param mdmOrgList
     * @return
     */
    Boolean orgUpdate(List<MdmOrg> mdmOrgList);

    /**
     * 职务划转
     * @param mdmDutyDtos
     * @return
     */
    Boolean dutyUpdate(List<MdmDutyDto> mdmDutyDtos);

    /**
     * 查询同级职务
     * @param mdmDutyDto
     * @return
     */
    List<MdmDutyVo> getSameLevel(MdmDutyDto mdmDutyDto);



}
