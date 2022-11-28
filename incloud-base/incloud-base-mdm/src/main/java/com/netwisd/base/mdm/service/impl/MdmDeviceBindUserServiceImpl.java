package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.dto.MdmDeviceBindUserDto;
import com.netwisd.base.common.mdm.dto.MdmUserDeviceDto;
import com.netwisd.base.common.mdm.vo.MdmDeviceBindUserVo;
import com.netwisd.base.common.mdm.vo.MdmUserDeviceVo;
import com.netwisd.base.mdm.entity.MdmDeviceBindUser;
import com.netwisd.base.mdm.entity.MdmUserDevice;
import com.netwisd.base.mdm.mapper.MdmDeviceBindUserMapper;
import com.netwisd.base.mdm.mapper.MdmUserDeviceMapper;
import com.netwisd.base.mdm.service.MdmDeviceBindUserService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 设备绑定人员 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Service
@Slf4j
public class MdmDeviceBindUserServiceImpl extends ServiceImpl<MdmDeviceBindUserMapper, MdmDeviceBindUser> implements MdmDeviceBindUserService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmDeviceBindUserMapper mdmDeviceBindUserMapper;

    /**
     * 单表简单查询操作
     * @param mdmDeviceBindUserDto
     * @return
     */
    @Override
    public Page list(MdmDeviceBindUserDto mdmDeviceBindUserDto) {
        LambdaQueryWrapper<MdmDeviceBindUser> queryWrapper = new LambdaQueryWrapper<>();
        Page<MdmDeviceBindUser> page = mdmDeviceBindUserMapper.selectPage(mdmDeviceBindUserDto.getPage(),queryWrapper);
        Page<MdmDeviceBindUserVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmDeviceBindUserVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param mdmDeviceBindUserDto
     * @return
     */
    @Override
    public List<MdmDeviceBindUserVo> lists(MdmDeviceBindUserDto mdmDeviceBindUserDto) {
        LambdaQueryWrapper<MdmDeviceBindUser> queryWrapper = new LambdaQueryWrapper<>();
        List<MdmDeviceBindUser> list = mdmDeviceBindUserMapper.selectList(queryWrapper);
        List<MdmDeviceBindUserVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmDeviceBindUserVo.class);
        }
        return listVo;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public MdmDeviceBindUserVo get(Long id) {
        MdmDeviceBindUser mdmDeviceBindUser = super.getById(id);
        MdmDeviceBindUserVo mdmDeviceBindUserVo = null;
        if(mdmDeviceBindUser !=null){
            mdmDeviceBindUserVo = dozerMapper.map(mdmDeviceBindUser,MdmDeviceBindUserVo.class);
        }
        log.debug("查询成功");
        return mdmDeviceBindUserVo;
    }

    /**
     * 保存实体
     * @param mdmDeviceBindUserDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(MdmDeviceBindUserDto mdmDeviceBindUserDto) {
        MdmDeviceBindUser mdmDeviceBindUser = dozerMapper.map(mdmDeviceBindUserDto,MdmDeviceBindUser.class);
        boolean result = super.save(mdmDeviceBindUser);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 修改实体
     * @param mdmDeviceBindUserDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MdmDeviceBindUserDto mdmDeviceBindUserDto) {
        mdmDeviceBindUserDto.setUpdateTime(LocalDateTime.now());
        MdmDeviceBindUser mdmDeviceBindUser = dozerMapper.map(mdmDeviceBindUserDto,MdmDeviceBindUser.class);
        boolean result = super.updateById(mdmDeviceBindUser);
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
    public Boolean delete(String id) {
        if(StringUtils.isNotBlank(id)) {
            List<String> streamStr = Stream.of(id.split(",")).collect(Collectors.toList());
            boolean result = super.removeByIds(streamStr);
            if(result){
                log.debug("删除成功");
            }
            return result;
        } else {
            throw new IncloudException("删除子系统的id不能为空！");
        }
    }

    @Override
    public Boolean deleteByUserId(Long userId) {
        if(null != userId) {
            LambdaQueryWrapper<MdmDeviceBindUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdmDeviceBindUser::getUserId,userId);
            int line = mdmDeviceBindUserMapper.delete(queryWrapper);
            if(line > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MdmDeviceBindUser getByUserId(Long userId) {
        LambdaQueryWrapper<MdmDeviceBindUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDeviceBindUser::getUserId,userId);
        MdmDeviceBindUser mdmDeviceBindUser = mdmDeviceBindUserMapper.selectOne(queryWrapper);
        return mdmDeviceBindUser;
    }
}
