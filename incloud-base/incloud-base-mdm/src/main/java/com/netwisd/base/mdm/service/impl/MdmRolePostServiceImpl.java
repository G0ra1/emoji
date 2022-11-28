package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.dto.MdmRolePostDto;
import com.netwisd.base.common.mdm.vo.MdmRolePostVo;
import com.netwisd.base.mdm.entity.*;
import com.netwisd.base.mdm.mapper.MdmRolePostMapper;
import com.netwisd.base.mdm.service.MdmRolePostService;
import org.apache.commons.lang3.StringUtils;
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
 * @Description 角色与岗位关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:19:56
 */
@Service
@Slf4j
public class MdmRolePostServiceImpl extends ServiceImpl<MdmRolePostMapper, MdmRolePost> implements MdmRolePostService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmRolePostMapper mdmRolePostMapper;

    /**
    * 单表简单查询操作
    * @param mdmRolePostDto
    * @return
    */
    @Override
    public Page list(MdmRolePostDto mdmRolePostDto) {
        LambdaQueryWrapper<MdmRolePost> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmRolePost> page = mdmRolePostMapper.selectPage(mdmRolePostDto.getPage(),queryWrapper);
        Page<MdmRolePostVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmRolePostVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmRolePostDto
    * @return
    */
    @Override
    public Page lists(MdmRolePostDto mdmRolePostDto) {
        Page<MdmRolePostVo> pageVo = mdmRolePostMapper.getPageList(mdmRolePostDto.getPage(),mdmRolePostDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmRolePostVo get(Long id) {
        MdmRolePost mdmRolePost = super.getById(id);
        MdmRolePostVo mdmRolePostVo = null;
        if(mdmRolePost !=null){
            mdmRolePostVo = dozerMapper.map(mdmRolePost,MdmRolePostVo.class);
        }
        log.debug("查询成功");
        return mdmRolePostVo;
    }

    /**
    * 保存实体
    * @param mdmRolePostDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmRolePostDto mdmRolePostDto) {
        MdmRolePost mdmRolePost = dozerMapper.map(mdmRolePostDto,MdmRolePost.class);
        boolean result = super.save(mdmRolePost);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmRolePostDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmRolePostDto mdmRolePostDto) {
        mdmRolePostDto.setUpdateTime(LocalDateTime.now());
        MdmRolePost mdmRolePost = dozerMapper.map(mdmRolePostDto,MdmRolePost.class);
        boolean result = super.updateById(mdmRolePost);
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
    @Transactional
    public void updateRolePostInfoByPostId(MdmPost mdmPost) {
        LambdaUpdateWrapper<MdmRolePost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(MdmRolePost::getPostCode, mdmPost.getPostCode());
        updateWrapper.set(MdmRolePost::getPostName, mdmPost.getPostName());
        updateWrapper.eq(MdmRolePost::getPostId,mdmPost.getId());
        super.update(updateWrapper);
    }

    @Override
    @Transactional
    public void updateRolePostInfoByRoleId(MdmRole mdmRole) {
        LambdaUpdateWrapper<MdmRolePost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(MdmRolePost::getRoleCode, mdmRole.getRoleCode());
        updateWrapper.set(MdmRolePost::getRoleName, mdmRole.getRoleName());
        updateWrapper.eq(MdmRolePost::getRoleId,mdmRole.getId());
        super.update(updateWrapper);
    }

    @Override
    public List<MdmRolePostVo> getRolePostByRoleId(Long roleId) {
        LambdaQueryWrapper<MdmRolePost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRolePost::getRoleId,roleId);
        List<MdmRolePost> list = mdmRolePostMapper.selectList(queryWrapper);
        List<MdmRolePostVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmRolePostVo.class);
        }
        return listVo;
    }

    @Override
    public Boolean deleteByRoleId(Long roleId) {
        LambdaUpdateWrapper<MdmRolePost> delWrapper = new LambdaUpdateWrapper<>();
        delWrapper.eq(MdmRolePost::getRoleId,roleId);
        int line = mdmRolePostMapper.delete(delWrapper);
        return line > 0 ? true : false;
    }

    /**
     * 根据postid删除对应的角色信息
     * @param id
     * @return
     */
    @Override
    public void deleteByPostId(List id) {
        LambdaQueryWrapper<MdmRolePost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmRolePost::getPostId,id);
        mdmRolePostMapper.delete(queryWrapper);
    }

    /**
     * 机构拆分和划转时，岗位的所属组织改变，相应的role_post关系表也要修改
     * @param mdmPostList
     * @return
     */
    @Override
    @Transactional
    public void updateByPost(List<MdmPost> mdmPostList) {
        //根据岗位id查询post——user表中的对应信息
        if (CollectionUtil.isNotEmpty(mdmPostList)){
            List<Long> postIds = mdmPostList.stream().map(MdmPost::getId).collect(Collectors.toList());
            LambdaQueryWrapper<MdmRolePost> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmRolePost::getPostId,postIds);
            List<MdmRolePost> mdmRolePostList = mdmRolePostMapper.selectList(queryWrapper);
            if(CollectionUtil.isNotEmpty(mdmRolePostList)) {
                //修改对应的岗位名称、code
                Map<Long, List<MdmPost>> groupMap = mdmPostList.stream().collect(Collectors.groupingBy(MdmPost::getId));
                for (MdmRolePost mdmRolePost : mdmRolePostList) {
                    List<MdmPost> mdmPosts = groupMap.get(mdmRolePost.getPostId());
                    MdmPost mdmPost = mdmPosts.get(0);
                    mdmRolePost.setPostCode(mdmPost.getPostCode());
                    mdmRolePost.setPostName(mdmPost.getPostName());
                }
                this.updateBatchById(mdmRolePostList);
            }
        }

    }

    @Override
    public List<MdmRolePostVo> getRolePostByPostId(Long postId) {
        LambdaQueryWrapper<MdmRolePost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRolePost::getPostId,postId);
        List<MdmRolePost> list = mdmRolePostMapper.selectList(queryWrapper);
        List<MdmRolePostVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmRolePostVo.class);
        }
        return listVo;
    }

    @Override
    public List<MdmRolePostVo> getRolePostByPostIds(List<Long> ids) {
        List<MdmRolePostVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(ids)) {
            LambdaQueryWrapper<MdmRolePost> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmRolePost::getPostId,ids);
            List<MdmRolePost> list = mdmRolePostMapper.selectList(queryWrapper);
            if(CollectionUtil.isNotEmpty(list)) {
                listVo = DozerUtils.mapList(dozerMapper, list, MdmRolePostVo.class);
            }
        }
        return listVo;
    }
}
