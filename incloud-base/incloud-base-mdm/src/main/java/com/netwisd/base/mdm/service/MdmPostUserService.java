package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmPostUserDto;
import com.netwisd.base.common.mdm.vo.MdmPostUserVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.mdm.entity.MdmPost;
import com.netwisd.base.mdm.entity.MdmPostUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.entity.MdmUser;

import java.util.List;

/**
 * @Description 岗位与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 11:47:13
 */
public interface MdmPostUserService extends IService<MdmPostUser> {
    Page list(MdmPostUserDto mdmPostUserDto);
    List<MdmPostUserVo> lists(MdmPostUserDto mdmPostUserDto);
    MdmPostUserVo get(Long id);
    Boolean saveList(List<MdmPostUser> mdmPostUserList);
    Boolean save(MdmPostUserDto mdmPostUserDto);
    Boolean update(MdmPostUserDto mdmPostUserDto);
    Boolean delete(Long id);

    /**
     * 获取某用户的主岗
     * @param userId
     * @return
     */
    MdmPostUser getMaster(Long userId);

    /**
     * 删除某个用户的岗位信息
     * @param userId 用户id
     * @param isMaster 主岗还是次岗
     */
    void delByUserIdAndIsMaster(Long userId, Integer isMaster);

    /**
     * 通过岗位id查询岗位人员信息
     * @param id
     * @return
     */
    List<MdmPostUserVo> getUserByPostId(Long id);

    /**
     * 通过岗位id查询主岗人数
     * @param id
     * @return
     */
    public Integer masterNumber(Long id);

    /**
     * 通过岗位id查询兼岗人数
     * @param id
     * @return
     */
    public Integer partNumber(Long id);

    /**
     * 根据用户id 查询用户岗位信息
     * @param id
     * @return
     */
    List<MdmPostUserVo> getPostByUserId(String id);

    /**
     * 机构拆分和划转时，岗位的所属组织改变，相应的post_user关系表也要修改
     * @param mdmPostList
     * @return
     */
    Boolean updateByPost(List<MdmPost> mdmPostList);

    /**
     * 根据postid删除对应的人员信息
     * @param id
     * @return
     */
    void deleteByPostId(List id);

    /**
     * 删除人员时删除对应的 人员岗位关系
     * @param ids
     * @return
     */
    void deleteByUserId(List ids);

    /**
     * 根据人员id修改 相关user 相关的信息
     * @param mdmUser
     */
    void updatePostUserInfoByUserId(MdmUser mdmUser);

    /**
     * 根据用户id和post 查询某人 是否拥有这个岗位
     * @param userId 用户id
     * @param postId 岗位id
     * @param isMaster 是否主岗
     * @return
     */
    List<MdmPostUserVo> getPositionByUserIdAndPostId(Long userId, Long postId, boolean isMaster);

    /**
     * 通过岗位id查询岗位人员信息
     * @param isMaster 是否主岗   -1查询所有
     * @param postIds  岗位ids
     * @return
     */
    List<MdmUserVo> getUserByPostId(Integer isMaster, String postIds);

    /**
     * 根据部门或者机构信息 和 岗位code查询
     * @param orgId 机构 或者 部门信息
     * @param postCode  岗位code
     * @return
     */
    List<MdmUserVo> getByOrgIdAndPostCode(Long orgId, String postCode);
}
