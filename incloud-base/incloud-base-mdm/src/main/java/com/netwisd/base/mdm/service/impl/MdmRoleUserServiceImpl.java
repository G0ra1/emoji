package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.dto.MdmRoleUserDto;
import com.netwisd.base.common.mdm.vo.MdmRoleUserVo;
import com.netwisd.base.mdm.entity.MdmRole;
import com.netwisd.base.mdm.entity.MdmRoleUser;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.mapper.MdmRoleUserMapper;
import com.netwisd.base.mdm.mapper.MdmUserMapper;
import com.netwisd.base.mdm.service.MdmRoleUserService;
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

/**
 * @Description 角色与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:44:05
 */
@Service
@Slf4j
public class MdmRoleUserServiceImpl extends ServiceImpl<MdmRoleUserMapper, MdmRoleUser> implements MdmRoleUserService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmRoleUserMapper mdmRoleUserMapper;

    @Autowired
    private MdmUserMapper mdmUserMapper;

    /**
    * 单表简单查询操作
    * @param mdmRoleUserDto
    * @return
    */
    @Override
    public Page list(MdmRoleUserDto mdmRoleUserDto) {
        LambdaQueryWrapper<MdmRoleUser> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmRoleUser> page = mdmRoleUserMapper.selectPage(mdmRoleUserDto.getPage(),queryWrapper);
        Page<MdmRoleUserVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmRoleUserVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmRoleUserDto
    * @return
    */
    @Override
    public Page lists(MdmRoleUserDto mdmRoleUserDto) {
        Page<MdmRoleUserVo> pageVo = mdmRoleUserMapper.getPageList(mdmRoleUserDto.getPage(),mdmRoleUserDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmRoleUserVo get(Long id) {
        MdmRoleUser mdmRoleUser = super.getById(id);
        MdmRoleUserVo mdmRoleUserVo = null;
        if(mdmRoleUser !=null){
            mdmRoleUserVo = dozerMapper.map(mdmRoleUser,MdmRoleUserVo.class);
        }
        log.debug("查询成功");
        return mdmRoleUserVo;
    }

    /**
    * 保存实体
    * @param mdmRoleUserDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmRoleUserDto mdmRoleUserDto) {
        MdmRoleUser mdmRoleUser = dozerMapper.map(mdmRoleUserDto,MdmRoleUser.class);
        boolean result = super.save(mdmRoleUser);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmRoleUserDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmRoleUserDto mdmRoleUserDto) {
        mdmRoleUserDto.setUpdateTime(LocalDateTime.now());
        MdmRoleUser mdmRoleUser = dozerMapper.map(mdmRoleUserDto,MdmRoleUser.class);
        boolean result = super.updateById(mdmRoleUser);
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
    public void updateRoleUserInfoByUserId(MdmUser mdmUser) {
        LambdaUpdateWrapper<MdmRoleUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(MdmRoleUser::getUserName, mdmUser.getUserName());
        updateWrapper.set(MdmRoleUser::getUserNameCh, mdmUser.getUserNameCh());
        updateWrapper.eq(MdmRoleUser::getUserId,mdmUser.getId());
        super.update(updateWrapper);
    }

    @Override
    @Transactional
    public void updateRoleUserInfoByRoleId(MdmRole mdmRole) {
        LambdaUpdateWrapper<MdmRoleUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(MdmRoleUser::getRoleCode, mdmRole.getRoleCode());
        updateWrapper.set(MdmRoleUser::getRoleName, mdmRole.getRoleName());
        updateWrapper.eq(MdmRoleUser::getRoleId,mdmRole.getId());
        super.update(updateWrapper);
    }

    @Override
    public List<MdmRoleUserVo> getRoleUserByRoleId(Long roleId) {
        LambdaQueryWrapper<MdmRoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRoleUser::getRoleId,roleId);
        List<MdmRoleUser> list = mdmRoleUserMapper.selectList(queryWrapper);
        List<MdmRoleUserVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmRoleUserVo.class);
            //增加返回人员的父级部门id
            //获取到所有人员id
            List<Long> userIdList = listVo.stream().map(MdmRoleUserVo::getUserId).collect(Collectors.toList());
            //通过人员id获取人员信息
            LambdaQueryWrapper<MdmUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.in(MdmUser::getId,userIdList);
            List<MdmUser> userList = mdmUserMapper.selectList(userLambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(userList)) {
                //通过人员id匹配返回的人员 然后将人员上级部门信息 放到返回信息中
                Map<Long, List<MdmUser>> userIdMap = userList.stream().collect(Collectors.groupingBy(MdmUser::getId));
                for (MdmRoleUserVo roleUserVo : listVo){
                    List<MdmUser> users = userIdMap.get(roleUserVo.getUserId());
                    roleUserVo.setOrgId(users.get(0).getParentDeptId());
                }
            }
        }
        return listVo;
    }

    @Override
    public List<MdmRoleUserVo> getRoleUserByRoleIds(List<String> roleIds) {
        if(CollectionUtil.isEmpty(roleIds)) {
            throw new IncloudException("角色id不能为空！");
        }
        LambdaQueryWrapper<MdmRoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmRoleUser::getRoleId,roleIds);
        List<MdmRoleUser> list = mdmRoleUserMapper.selectList(queryWrapper);
        List<MdmRoleUserVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmRoleUserVo.class);
        }
        return listVo;
    }

    @Override
    @Transactional
    public Boolean deleteByRoleId(Long id) {
        LambdaUpdateWrapper<MdmRoleUser> delWrapper = new LambdaUpdateWrapper<>();
        delWrapper.eq(MdmRoleUser::getRoleId,id);
        int line = mdmRoleUserMapper.delete(delWrapper);
        return line > 0 ? true : false;
    }

    @Override
    @Transactional
    public Boolean deleteByUserId(List<String> ids) {
        LambdaUpdateWrapper<MdmRoleUser> delWrapper = new LambdaUpdateWrapper<>();
        delWrapper.in(MdmRoleUser::getUserId,ids);
        int line = mdmRoleUserMapper.delete(delWrapper);
        return line > 0 ? true : false;
    }

    @Override
    public List<MdmRoleUserVo> getRoleUserByUserId(Long userId) {
        LambdaQueryWrapper<MdmRoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRoleUser::getUserId,userId);
        List<MdmRoleUser> list = mdmRoleUserMapper.selectList(queryWrapper);
        List<MdmRoleUserVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmRoleUserVo.class);
        }
        return listVo;
    }
}
