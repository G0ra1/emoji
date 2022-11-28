package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.dto.MdmOrgStatusDto;
import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Description 组织 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-08-12 10:27:19
 */
public interface MdmOrgService extends IService<MdmOrg> {
    List<MdmOrgAllVo> list(MdmOrgDto mdmOrgDto);
    List<MdmOrgAllVo> lists(MdmOrgDto mdmOrgDto);
    List<MdmOrgAllVo> vos(MdmOrgDto mdmOrgDto);
    Page listAll(MdmOrgDto mdmOrgDto);
    List<MdmOrgVo> kids(Long id);
    Boolean sort(Long sourceId,Long targetId,String index);
    List<MdmOrgAllVo> treeList(Integer orgType);
    MdmOrg getParentByParentId(Long parentId);
    MdmOrgVo get(Long id);
    MdmOrg getByCode(String orgCode);
    Boolean save(List<MdmOrgDto> mdmOrgDtos,boolean isSendMq,boolean isSync);
    Boolean update(MdmOrgDto mdmOrgDto,boolean isSync);
    Boolean justUpdateOrg(MdmOrgDto mdmOrgDto,boolean isSync);
    Boolean updateParent(MdmOrgDto mdmOrgDto);
    Boolean updateOrgName(MdmOrgDto mdmOrgDto);
    Boolean updateStatus(MdmOrgStatusDto mdmOrgStatusDto);
    Boolean delete(Long id);

    //根据GEPS 集采机构ID 查询所有机构信息
    List<MdmOrgVo> getOrgByJcOrgIds(String ids);

    //根据机构ID查询 查询该机构下的所有部门 以及子部门
    List<MdmOrgAllVo> getDeptByOrgId(Long id);

    //还原撤销的机构
    Boolean backoutOrg(Long id);

    //通过企业微信id获取机构信息 （企业微信调用）
    MdmOrg getByQyWeChatId(Integer qyWechatId);
}
