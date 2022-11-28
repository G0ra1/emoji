package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.dto.MdmDeviceBindUserDto;
import com.netwisd.base.common.mdm.dto.MdmUserDeviceDto;
import com.netwisd.base.common.mdm.vo.MdmUserDeviceVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.mdm.entity.MdmDeviceBindUser;
import com.netwisd.base.mdm.entity.MdmUserDevice;
import com.netwisd.base.mdm.mapper.MdmUserDeviceMapper;
import com.netwisd.base.mdm.service.MdmDeviceBindUserService;
import com.netwisd.base.mdm.service.MdmUserDeviceService;
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
 * @Description 移动设备 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Service
@Slf4j
public class MdmUserDeviceServiceImpl extends ServiceImpl<MdmUserDeviceMapper, MdmUserDevice> implements MdmUserDeviceService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmUserDeviceMapper mdmUserDeviceMapper;

    @Autowired
    private MdmDeviceBindUserService mdmDeviceBindUserService;

    /**
     * 单表简单查询操作
     * @param mdmUserDeviceDto
     * @return
     */
    @Override
    public Page list(MdmUserDeviceDto mdmUserDeviceDto) {
        LambdaQueryWrapper<MdmUserDevice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(mdmUserDeviceDto.getDeviceName()),MdmUserDevice::getDeviceName,mdmUserDeviceDto.getDeviceName());
        queryWrapper.eq(StringUtils.isNotBlank(mdmUserDeviceDto.getDeviceModel()),MdmUserDevice::getDeviceModel,mdmUserDeviceDto.getDeviceModel());
        Page<MdmUserDevice> page = mdmUserDeviceMapper.selectPage(mdmUserDeviceDto.getPage(),queryWrapper);
        Page<MdmUserDeviceVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmUserDeviceVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param mdmUserDeviceDto
     * @return
     */
    @Override
    public List<MdmUserDeviceVo> lists(MdmUserDeviceDto mdmUserDeviceDto) {
        LambdaQueryWrapper<MdmUserDevice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(mdmUserDeviceDto.getDeviceName()),MdmUserDevice::getDeviceName,mdmUserDeviceDto.getDeviceName());
        queryWrapper.eq(StringUtils.isNotBlank(mdmUserDeviceDto.getDeviceModel()),MdmUserDevice::getDeviceModel,mdmUserDeviceDto.getDeviceModel());
        List<MdmUserDevice> list = mdmUserDeviceMapper.selectList(queryWrapper);
        List<MdmUserDeviceVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmUserDeviceVo.class);
        }
        return listVo;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public MdmUserDeviceVo get(Long id) {
        MdmUserDevice mdmUserDevice = super.getById(id);
        MdmUserDeviceVo mdmUserDeviceVo = null;
        if(mdmUserDevice !=null){
            mdmUserDeviceVo = dozerMapper.map(mdmUserDevice,MdmUserDeviceVo.class);
        }
        log.debug("查询成功");
        return mdmUserDeviceVo;
    }

    /**
     * 保存实体
     * @param mdmUserDeviceDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(MdmUserDeviceDto mdmUserDeviceDto) {
        MdmUserDevice mdmUserDevice = null;
        //先按照 系统 和 设备唯一id 查询是否存在该设备
        List<MdmUserDevice> mdmUserDevices = getByTypeAndUnicode(mdmUserDeviceDto);
        //如果存在则更新  不存在则添加
        if(CollectionUtil.isNotEmpty(mdmUserDevices)) {
            mdmUserDevice =  mdmUserDevices.get(0);
            mdmUserDeviceDto.setId(mdmUserDevice.getId());
            mdmUserDeviceDto.setLastLoginTime(LocalDateTime.now());
            boolean result = update(mdmUserDeviceDto); //修改关系则不处理
        } else {
            mdmUserDevice = dozerMapper.map(mdmUserDeviceDto,MdmUserDevice.class);
            mdmUserDevice.setLastLoginTime(LocalDateTime.now());
            boolean result = super.save(mdmUserDevice);
            if(result){
                log.debug("用户设备信息保存成功");
            }
        }
        //根据user ID 删除一下关系
        mdmDeviceBindUserService.deleteByUserId(mdmUserDeviceDto.getUserId());
        //然后删除用户和设备的关系  并且重新维护
        MdmDeviceBindUserDto mdmDeviceBindUserDto = new MdmDeviceBindUserDto();
        mdmDeviceBindUserDto.setUserId(mdmUserDeviceDto.getUserId());
        mdmDeviceBindUserDto.setDeviceId(mdmUserDevice.getId());
        mdmDeviceBindUserDto.setBindTime(LocalDateTime.now());
        boolean result = mdmDeviceBindUserService.save(mdmDeviceBindUserDto);
        return result;
    }

    /**
     * 修改实体
     * @param mdmUserDeviceDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MdmUserDeviceDto mdmUserDeviceDto) {
        mdmUserDeviceDto.setUpdateTime(LocalDateTime.now());
        MdmUserDevice mdmUserDevice = dozerMapper.map(mdmUserDeviceDto,MdmUserDevice.class);
        boolean result = super.updateById(mdmUserDevice);
        if(result){
            log.debug("用户设备信息修改成功");
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
    public List<MdmUserDevice> getByTypeAndUnicode(MdmUserDeviceDto mdmUserDeviceDto) {
        LambdaQueryWrapper<MdmUserDevice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUserDevice::getDeviceType,mdmUserDeviceDto.getDeviceType());
        queryWrapper.eq(MdmUserDevice::getDeviceFlag,mdmUserDeviceDto.getDeviceFlag());
        List<MdmUserDevice> list = mdmUserDeviceMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public MdmUserDeviceVo getDeviceByUserId(Long userId) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //先查关系
        MdmDeviceBindUser mdmDeviceBindUser = mdmDeviceBindUserService.getByUserId(loginAppUser.getId());
        MdmUserDeviceVo mdmUserDevicevo = null;
        if(null != mdmDeviceBindUser) {
            MdmUserDevice mdmUserDevice =  mdmUserDeviceMapper.selectById(mdmDeviceBindUser.getDeviceId());
            mdmUserDevicevo = dozerMapper.map(mdmUserDevice,MdmUserDeviceVo.class);
        }
        return mdmUserDevicevo;
    }
}
