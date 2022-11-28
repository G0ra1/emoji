package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.vo.MdmPostVo;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.netwisd.base.mdm.entity.MdmPost;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Description 岗位 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-25 15:15:37
 */
public interface MdmPostService extends IService<MdmPost> {
    Page list(MdmPostDto mdmPostDto);
    List<MdmPostVo> lists(MdmPostDto mdmPostDto);
    MdmPostVo get(Long id);
    Boolean save(List<MdmPostDto> mdmPostDtos);
    Boolean saveOne(MdmPostDto mdmPostDtos);
    Boolean update(MdmPostDto mdmPostDto);
    Boolean delete(String id);
    List<MdmPost> getByIds(List<String> ids);
    MdmPost getByPostCode(String postCode);

    /**
     * 复制岗位到另外一个部门
     * @param mdmPostDto
     * @param
     * @return
     */
    Boolean copyToOrg(MdmPostDto mdmPostDto);

    /**
     * 通过部门id查询已启用的岗位
     * @param id
     * @return
     */
    List<MdmPostVo> getPost(Long id);

    /**
     * 通过组织id查询所有的岗位
     * @param
     * @return
     */
    List<MdmPostVo> getAllPost(MdmPostDto mdmPostDto);
    /**
     * 部门内岗位排序
     * @param sourceId
     * @param targetId
     * @param index
     * @return
     */
    Boolean sortForDept(Long sourceId,Long targetId,String index);

    /**
     * 根据岗位id，修改是否被引用字段为1(表示已被引用)
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
     * 岗位划转
     * @param mdmPostDtos
     * @return
     */
    Boolean postUpdate(List<MdmPostDto> mdmPostDtos);

    /**
     * 查询同级岗位
     * @param mdmPostDto
     * @return
     */
    List<MdmPostVo> getSameLevel(MdmPostDto mdmPostDto);
}
