package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmResourceDto;
import com.netwisd.base.common.mdm.vo.MdmResourceVo;
import com.netwisd.base.mdm.entity.MdmResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import java.util.List;

/**
 * @Description 资源管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-09 10:39:04
 */
public interface MdmResourceService extends IService<MdmResource> {
    List list(MdmResourceDto mdmResourceDto);
    List<MdmResourceVo> lists(MdmResourceDto mdmResourceDto);
    MdmResourceVo get(Long id);
    List<MdmResourceVo> getKids(Long id);
    Boolean save(MdmResourceDto mdmResourceDto);
    Boolean update(MdmResourceDto mdmResourceDto);
    Boolean delete(Long id);

    /**
     * 根据资源ids获取所有的资源信息
     * @param ids
     * @return
     */
    List<MdmResourceVo> getResByIds(List<Long> ids,Long parentId);


    /**
     * 根据当前人获取 所有资源信息 pid -1为什么所有
     * @return
     */
    public List<MdmResourceVo> getResByPid(Long pid);

    /**
     * 效验当前按钮 是否存在该资源权限
     * @return
     */
    public MdmResourceVo checkResByResId(Long resId);



}
