package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.dto.MdmPostUserDto;
import com.netwisd.base.common.mdm.vo.MdmPostUserVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.mdm.entity.MdmPost;
import com.netwisd.base.mdm.entity.MdmPostUser;
import com.netwisd.base.mdm.entity.MdmRoleUser;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.mapper.MdmPostUserMapper;
import com.netwisd.base.mdm.mapper.MdmUserMapper;
import com.netwisd.base.mdm.service.MdmPostUserService;
import com.netwisd.base.mdm.service.MdmUserService;
import com.netwisd.base.mdm.service.NeoService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 岗位与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 11:47:13
 */
@Service
@Slf4j
public class MdmPostUserServiceImpl extends ServiceImpl<MdmPostUserMapper, MdmPostUser> implements MdmPostUserService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmPostUserMapper mdmPostUserMapper;

    @Autowired
    private NeoService neoService;

    @Autowired
    private MdmUserMapper mdmUserMapper;

    /**
    * 单表简单查询操作
    * @param mdmPostUserDto
    * @return
    */
    @Override
    public Page list(MdmPostUserDto mdmPostUserDto) {
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmPostUser> page = mdmPostUserMapper.selectPage(mdmPostUserDto.getPage(),queryWrapper);
        Page<MdmPostUserVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmPostUserVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmPostUserDto
    * @return
    */
    @Override
    public List<MdmPostUserVo> lists(MdmPostUserDto mdmPostUserDto) {
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        List<MdmPostUser> list = mdmPostUserMapper.selectList(queryWrapper);
        List<MdmPostUserVo> voList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            voList = DozerUtils.mapList(dozerMapper,list,MdmPostUserVo.class);
        }
        log.debug("查询条数:"+ voList.size());
        return voList;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmPostUserVo get(Long id) {
        MdmPostUser mdmPostUser = super.getById(id);
        MdmPostUserVo mdmPostUserVo = null;
        if(mdmPostUser !=null){
            mdmPostUserVo = dozerMapper.map(mdmPostUser,MdmPostUserVo.class);
        }
        log.debug("查询成功");
        return mdmPostUserVo;
    }

    /**
    * 保存实体
    * @param mdmPostUserDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmPostUserDto mdmPostUserDto) {
        MdmPostUser mdmPostUser = dozerMapper.map(mdmPostUserDto,MdmPostUser.class);
        boolean result = super.save(mdmPostUser);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmPostUserDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmPostUserDto mdmPostUserDto) {
        mdmPostUserDto.setUpdateTime(LocalDateTime.now());
        MdmPostUser mdmPostUser = dozerMapper.map(mdmPostUserDto,MdmPostUser.class);
        boolean result = super.updateById(mdmPostUser);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public MdmPostUser getMaster(Long userId) {
        if(null == userId) {
            throw new IncloudException("用户信息不能为空！");
        }
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPostUser::getIsMaster, YesNo.YES.code);
        queryWrapper.eq(MdmPostUser::getUserId,userId);
        MdmPostUser mdmPostUser = mdmPostUserMapper.selectOne(queryWrapper);
        return mdmPostUser;
    }

    @Override
    public void delByUserIdAndIsMaster(Long userId, Integer isMaster) {
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPostUser::getIsMaster, isMaster);
        queryWrapper.eq(MdmPostUser::getUserId,userId);
        mdmPostUserMapper.delete(queryWrapper);
    }

    /**
     * 通过岗位id查询人员
     * @param id
     * @return
     */
    @Override
    public List<MdmPostUserVo> getUserByPostId(Long id) {
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPostUser::getPostId, id);
        List<MdmPostUser> mdmPostUser = mdmPostUserMapper.selectList(queryWrapper);
        List<MdmPostUserVo> mdmPostUserVos = DozerUtils.mapList(dozerMapper, mdmPostUser, MdmPostUserVo.class);
        return mdmPostUserVos;
    }

    /**
     * 通过岗位id查询主岗人数
     * @param id
     * @return
     */
    public Integer masterNumber(Long id){
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPostUser::getPostId,id);
        queryWrapper.eq(MdmPostUser::getIsMaster,YesNo.YES.code);
        Integer  masterNumber = mdmPostUserMapper.selectCount(queryWrapper);
        return masterNumber;
    }

    /**
     * 通过岗位id查询兼岗人数
     * @param id
     * @return
     */
    public Integer partNumber(Long id){
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPostUser::getPostId,id);
        queryWrapper.eq(MdmPostUser::getIsMaster,YesNo.NO.code);
        Integer  partNumber = mdmPostUserMapper.selectCount(queryWrapper);
        return partNumber;
    }

    @Override
    public List<MdmPostUserVo> getPostByUserId(String id) {
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPostUser::getUserId, id);
        List<MdmPostUser> mdmPostUserList = mdmPostUserMapper.selectList(queryWrapper);
        List<MdmPostUserVo> voList = DozerUtils.mapList(dozerMapper,mdmPostUserList,MdmPostUserVo.class);
        return voList;
    }

    /**
     * 机构拆分和划转时，岗位的所属组织改变，相应的post_user关系表也要修改
     * @param mdmPostList
     * @return
     */
    @Override
    @Transactional
    public Boolean updateByPost(List<MdmPost> mdmPostList) {
        //根据岗位id查询post——user表中的对应信息
        if (CollectionUtil.isNotEmpty(mdmPostList)){
            List<Long> postIds = mdmPostList.stream().map(MdmPost::getId).collect(Collectors.toList());
            LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmPostUser::getPostId,postIds);
            List<MdmPostUser> mdmPostUserList = mdmPostUserMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(mdmPostUserList)) {
                //修改对应的org_full_post_id和org_full_post_name、岗位名称
                Map<Long, List<MdmPost>> groupMap = mdmPostList.stream().collect(Collectors.groupingBy(MdmPost::getId));
                for (MdmPostUser mdmPostUser : mdmPostUserList) {
                    List<MdmPost> mdmPosts = groupMap.get(mdmPostUser.getPostId());
                    MdmPost mdmPost = mdmPosts.get(0);
                    mdmPostUser.setPostCode(mdmPost.getPostCode());
                    mdmPostUser.setPostName(mdmPost.getPostName());
                    mdmPostUser.setOrgFullPostId(mdmPost.getOrgFullId());
                    mdmPostUser.setOrgFullPostName(mdmPost.getOrgFullName());
                }
                this.updateBatchById(mdmPostUserList);
            }
        }
        return true;
    }
    /**
     * 根据postid删除对应的人员信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public void deleteByPostId(List id) {
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmPostUser::getPostId,id);
        mdmPostUserMapper.delete(queryWrapper);
    }

    @Override
    @Transactional
    public void deleteByUserId(List ids) {
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmPostUser::getUserId,ids);
        mdmPostUserMapper.delete(queryWrapper);
    }

    @Override
    @Transactional
    public Boolean saveList(List<MdmPostUser> mdmPostUserList) {
        boolean result = false;
        if(CollectionUtil.isNotEmpty(mdmPostUserList)) {
            result = super.saveBatch(mdmPostUserList);
            if(result){
                //维护neo4j 关系
                for (MdmPostUser mdmPostUser : mdmPostUserList) {
                    MdmUser _mdmUser = new MdmUser();
                    _mdmUser.setId(mdmPostUser.getUserId());
                    _mdmUser.setUserName(mdmPostUser.getUserName());
                    _mdmUser.setUserNameCh(mdmPostUser.getUserNameCh());
                    MdmPost mdmPost = new MdmPost();
                    mdmPost.setId(mdmPostUser.getPostId());
                    mdmPost.setPostCode(mdmPostUser.getPostCode());
                    mdmPost.setPostName(mdmPostUser.getPostName());
                    //neoService.mergeUserPostRel(_mdmUser,mdmPost,mdmPostUser);
                }
                log.debug("保存成功");
            }
        }
        return result;
    }

    @Override
    @Transactional
    public void updatePostUserInfoByUserId(MdmUser mdmUser) {
        LambdaUpdateWrapper<MdmPostUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(MdmPostUser::getUserName, mdmUser.getUserName());
        updateWrapper.set(MdmPostUser::getUserNameCh, mdmUser.getUserNameCh());
        updateWrapper.eq(MdmPostUser::getUserId,mdmUser.getId());
        super.update(updateWrapper);
    }

    @Override
    public List<MdmPostUserVo> getPositionByUserIdAndPostId(Long userId, Long postId, boolean isMaster) {
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPostUser::getUserId, userId);
        queryWrapper.eq(MdmPostUser::getPostId, postId);
        queryWrapper.eq(MdmPostUser::getIsMaster, isMaster==true?YesNo.YES.code:YesNo.NO.code);
        List<MdmPostUser> mdmPostUserList = mdmPostUserMapper.selectList(queryWrapper);
        List<MdmPostUserVo> voList = DozerUtils.mapList(dozerMapper,mdmPostUserList,MdmPostUserVo.class);
        return voList;
    }
    @Override
    public List<MdmUserVo> getUserByPostId(Integer isMaster, String postIds) {
        if(null == isMaster || 0 == isMaster) {
            throw new IncloudException("是否主岗,不能为空！");
        }
        if(StringUtils.isBlank(postIds)) {
            throw new IncloudException("岗位ID,不能为空！");
        }
        List<MdmUserVo> resultList = new ArrayList<>();
        List<String> streamStr = Stream.of(postIds.split(",")).collect(Collectors.toList());
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmPostUser::getPostId, streamStr);
        queryWrapper.in(-1!=isMaster,MdmPostUser::getIsMaster,isMaster);//-1默认查询所有
        List<MdmPostUser> mdmPostUserList = mdmPostUserMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(mdmPostUserList)) {
            List<String> userIds = mdmPostUserList.stream().map(d->String.valueOf(d.getUserId())).collect(Collectors.toList());
            LambdaQueryWrapper<MdmUser> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.in(MdmUser::getId, userIds);
            List<MdmUser> mdmUserList = mdmUserMapper.selectList(userWrapper);
            resultList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
        }
        return resultList;
    }

    @Override
    public List<MdmUserVo> getByOrgIdAndPostCode(Long orgId, String postCode) {
        if(null == orgId || 0L == orgId) {
            throw new IncloudException("组织id,不能为空！");
        }
        if(StringUtils.isBlank(postCode)) {
            throw new IncloudException("岗位code,不能为空！");
        }
        List<MdmUserVo> resultList = new ArrayList<>();
        LambdaQueryWrapper<MdmPostUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(MdmPostUser::getOrgFullPostId, orgId);
        queryWrapper.eq(MdmPostUser::getPostCode, postCode);
        List<MdmPostUser> mdmPostUserList = mdmPostUserMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(mdmPostUserList)) {
            List<String> userIds = mdmPostUserList.stream().map(d->String.valueOf(d.getUserId())).collect(Collectors.toList());
            LambdaQueryWrapper<MdmUser> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.in(MdmUser::getId, userIds);
            List<MdmUser> mdmUserList = mdmUserMapper.selectList(userWrapper);
            resultList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
        }
        return resultList;
    }
}