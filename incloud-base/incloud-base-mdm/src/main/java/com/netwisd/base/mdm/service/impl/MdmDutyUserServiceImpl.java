package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.vo.MdmPostUserVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.mdm.entity.MdmDutyUser;
import com.netwisd.base.mdm.entity.MdmDuty;
import com.netwisd.base.mdm.entity.MdmPostUser;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.mapper.MdmDutyUserMapper;
import com.netwisd.base.mdm.mapper.MdmUserMapper;
import com.netwisd.base.mdm.service.MdmDutyUserService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.common.mdm.dto.MdmDutyUserDto;
import com.netwisd.base.common.mdm.vo.MdmDutyUserVo;
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
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Service
@Slf4j
public class MdmDutyUserServiceImpl extends ServiceImpl<MdmDutyUserMapper, MdmDutyUser> implements MdmDutyUserService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmDutyUserMapper mdmDutyUserMapper;

    @Autowired
    private MdmUserMapper mdmUserMapper;

    /**
     * 单表简单查询操作
     * @param mdmDutyUserDto
     * @return
     */
    @Override
    public Page list(MdmDutyUserDto mdmDutyUserDto) {
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmDutyUser> page = mdmDutyUserMapper.selectPage(mdmDutyUserDto.getPage(),queryWrapper);
        Page<MdmDutyUserVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmDutyUserVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     * @param mdmDutyUserDto
     * @return
     */
    @Override
    public List<MdmDutyUserVo> lists(MdmDutyUserDto mdmDutyUserDto) {
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        List<MdmDutyUser> list = mdmDutyUserMapper.selectList(queryWrapper);
        List<MdmDutyUserVo> voList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            voList = DozerUtils.mapList(dozerMapper,list,MdmDutyUserVo.class);
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
    public MdmDutyUserVo get(Long id) {
        MdmDutyUser mdmDutyUser = super.getById(id);
        MdmDutyUserVo mdmDutyUserVo = null;
        if(mdmDutyUser !=null){
            mdmDutyUserVo = dozerMapper.map(mdmDutyUser,MdmDutyUserVo.class);
        }
        log.debug("查询成功");
        return mdmDutyUserVo;
    }

    /**
     * 保存实体
     * @param mdmDutyUserDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(MdmDutyUserDto mdmDutyUserDto) {
        MdmDutyUser mdmDutyUser = dozerMapper.map(mdmDutyUserDto,MdmDutyUser.class);
        boolean result = super.save(mdmDutyUser);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 修改实体
     * @param mdmDutyUserDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MdmDutyUserDto mdmDutyUserDto) {
        mdmDutyUserDto.setUpdateTime(LocalDateTime.now());
        MdmDutyUser mdmDutyUser = dozerMapper.map(mdmDutyUserDto,MdmDutyUser.class);
        boolean result = super.updateById(mdmDutyUser);
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
    public MdmDutyUser getMaster(Long userId) {
        if(null == userId) {
            throw new IncloudException("用户信息不能为空！");
        }
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDutyUser::getIsMaster, YesNo.YES.code);
        queryWrapper.eq(MdmDutyUser::getUserId,userId);
        MdmDutyUser mdmDutyUser = mdmDutyUserMapper.selectOne(queryWrapper);
        return mdmDutyUser;
    }

    @Override
    public void delByUserIdAndIsMaster(Long userId, Integer isMaster) {
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDutyUser::getIsMaster, isMaster);
        queryWrapper.eq(MdmDutyUser::getUserId,userId);
        mdmDutyUserMapper.delete(queryWrapper);
    }

    /**
     * 通过职务id查询人员
     * @param id
     * @return
     */
    @Override
    public List<MdmDutyUserVo> getUserByDutyId(Long id) {
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDutyUser::getDutyId, id);
        List<MdmDutyUser> mdmDutyUser = mdmDutyUserMapper.selectList(queryWrapper);
        List<MdmDutyUserVo> mdmDutyUserVos = DozerUtils.mapList(dozerMapper, mdmDutyUser, MdmDutyUserVo.class);
        return mdmDutyUserVos;
    }

    /**
     * 通过职务id查询主岗人数
     * @param id
     * @return
     */
    public Integer masterNumber(Long id){
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDutyUser::getDutyId,id);
        queryWrapper.eq(MdmDutyUser::getIsMaster,YesNo.YES.code);
        Integer  masterNumber = mdmDutyUserMapper.selectCount(queryWrapper);
        return masterNumber;
    }

    /**
     * 通过职务id查询兼岗人数
     * @param id
     * @return
     */
    public Integer partNumber(Long id){
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDutyUser::getDutyId,id);
        queryWrapper.eq(MdmDutyUser::getIsMaster,YesNo.NO.code);
        Integer  partNumber = mdmDutyUserMapper.selectCount(queryWrapper);
        return partNumber;
    }

    @Override
    public List<MdmDutyUserVo> getDutyByUserId(String id) {
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDutyUser::getUserId, id);
        List<MdmDutyUser> mdmDutyUserList = mdmDutyUserMapper.selectList(queryWrapper);
        List<MdmDutyUserVo> voList = DozerUtils.mapList(dozerMapper,mdmDutyUserList,MdmDutyUserVo.class);
        return voList;
    }

    /**
     * 机构拆分和划转时，职务的所属组织改变，相应的Duty_user关系表也要修改
     * @param mdmDutyList
     * @return
     */
    @Override
    @Transactional
    public Boolean updateByDuty(List<MdmDuty> mdmDutyList) {
        //根据职务id查询Duty——user表中的对应信息
        if (CollectionUtil.isNotEmpty(mdmDutyList)){
            List<Long> DutyIds = mdmDutyList.stream().map(MdmDuty::getId).collect(Collectors.toList());
            LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmDutyUser::getDutyId,DutyIds);
            List<MdmDutyUser> mdmDutyUserList = mdmDutyUserMapper.selectList(queryWrapper);
            //修改对应的org_full_Duty_id和org_full_Duty_name、职务名称
            Map<Long, List<MdmDuty>> groupMap = mdmDutyList.stream().collect(Collectors.groupingBy(MdmDuty::getId));
            for (MdmDutyUser mdmDutyUser : mdmDutyUserList) {
                List<MdmDuty> mdmDutys = groupMap.get(mdmDutyUser.getDutyId());
                MdmDuty mdmDuty = mdmDutys.get(0);
                mdmDutyUser.setDutyCode(mdmDuty.getDutyCode());
                mdmDutyUser.setDutyName(mdmDuty.getDutyName());
                mdmDutyUser.setOrgFullDutyId(mdmDuty.getOrgFullId());
                mdmDutyUser.setOrgFullDutyName(mdmDuty.getOrgFullName());
            }
            this.updateBatchById(mdmDutyUserList);
        }
        return true;
    }
    /**
     * 根据Dutyid删除对应的人员信息
     * @param id
     * @return
     */
    @Override
    public void deleteByDutyId(List id) {
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmDutyUser::getDutyId,id);
        mdmDutyUserMapper.delete(queryWrapper);
    }

    @Override
    public List<MdmUserVo> getUserByDutyId(Integer isMaster, String dutyIds) {
        if(null == isMaster || 0 == isMaster) {
            throw new IncloudException("是否主职,不能为空！");
        }
        if(StringUtils.isBlank(dutyIds)) {
            throw new IncloudException("职位ID,不能为空！");
        }
        List<MdmUserVo> resultList = new ArrayList<>();
        List<String> streamStr = Stream.of(dutyIds.split(",")).collect(Collectors.toList());
        LambdaQueryWrapper<MdmDutyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmDutyUser::getDutyId, streamStr);
        queryWrapper.in(-1!=isMaster,MdmDutyUser::getIsMaster,isMaster);//-1默认查询所有
        List<MdmDutyUser> mdmDutyUserList = mdmDutyUserMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(mdmDutyUserList)) {
            List<String> userIds = mdmDutyUserList.stream().map(d->String.valueOf(d.getUserId())).collect(Collectors.toList());
            List<MdmUser> mdmUserList =  getByIds(userIds);
            resultList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
        }
        return resultList;
    }

    public List<MdmUser> getByIds(List<String> ListId) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmUser::getId, ListId);
        List<MdmUser> list = mdmUserMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Boolean saveList(List<MdmDutyUser> mdmDutyUserList) {
        boolean result = super.saveBatch(mdmDutyUserList);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }
}
