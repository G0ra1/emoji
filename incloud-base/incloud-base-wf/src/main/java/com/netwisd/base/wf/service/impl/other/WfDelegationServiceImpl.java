package com.netwisd.base.wf.service.impl.other;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.entity.WfDelegation;
import com.netwisd.base.wf.entity.WfDoneTask;
import com.netwisd.base.wf.mapper.WfDelegationMapper;
import com.netwisd.base.wf.service.other.WfDelegationService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfDelegationDto;
import com.netwisd.base.wf.vo.WfDelegationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 委办设置 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 14:45:53
 */
@Service
@Slf4j
public class WfDelegationServiceImpl extends ServiceImpl<WfDelegationMapper, WfDelegation> implements WfDelegationService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfDelegationMapper wfDelegationMapper;

    /**
    * 单表简单查询操作
    * @param wfDelegationDto
    * @return
    */
    @Override
    public Page list(WfDelegationDto wfDelegationDto) {
        log.debug("委办 查询参数:"+wfDelegationDto.toString());
        LambdaQueryWrapper<WfDelegation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(wfDelegationDto.getProcdefTypeId()), WfDelegation::getProcdefTypeId, wfDelegationDto.getProcdefTypeId())
                .eq(WfDelegation::getDelegationUserName,getUserID())
                .eq(ObjectUtil.isNotEmpty(wfDelegationDto.getIsActivation()), WfDelegation::getIsActivation, wfDelegationDto.getIsActivation())
                .like(StrUtil.isNotEmpty(wfDelegationDto.getProcdefTypeName()), WfDelegation::getProcdefTypeName, wfDelegationDto.getProcdefTypeName())
                .like(ObjectUtil.isNotEmpty(wfDelegationDto.getDelegationUserNameCh()), WfDelegation::getDelegationUserNameCh, wfDelegationDto.getDelegationUserNameCh())
                .like(ObjectUtil.isNotEmpty(wfDelegationDto.getDesignateUserNameCh()), WfDelegation::getDesignateUserNameCh, wfDelegationDto.getDesignateUserNameCh());
        Page<WfDelegation> page = wfDelegationMapper.selectPage(wfDelegationDto.getPage(),queryWrapper);
        Page<WfDelegationVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDelegationVo.class);
        log.debug("委办 查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page delegationListForAd(WfDelegationDto wfDelegationDto) {
        log.debug("委办 查询参数:"+wfDelegationDto.toString());
        LambdaQueryWrapper<WfDelegation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(wfDelegationDto.getProcdefTypeId()), WfDelegation::getProcdefTypeId, wfDelegationDto.getProcdefTypeId())
                .eq(ObjectUtil.isNotEmpty(wfDelegationDto.getIsActivation()), WfDelegation::getIsActivation, wfDelegationDto.getIsActivation())
                .like(StrUtil.isNotEmpty(wfDelegationDto.getProcdefTypeName()), WfDelegation::getProcdefTypeName, wfDelegationDto.getProcdefTypeName())
                .like(ObjectUtil.isNotEmpty(wfDelegationDto.getDelegationUserNameCh()), WfDelegation::getDelegationUserNameCh, wfDelegationDto.getDelegationUserNameCh())
                .like(ObjectUtil.isNotEmpty(wfDelegationDto.getDesignateUserNameCh()), WfDelegation::getDesignateUserNameCh, wfDelegationDto.getDesignateUserNameCh());
        Page<WfDelegation> page = wfDelegationMapper.selectPage(wfDelegationDto.getPage(),queryWrapper);
        Page<WfDelegationVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDelegationVo.class);
        log.debug("委办 查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfDelegationDto
    * @return
    */
    @Override
    public Page lists(WfDelegationDto wfDelegationDto) {
        Page<WfDelegationVo> pageVo = wfDelegationMapper.getPageList(wfDelegationDto.getPage(),wfDelegationDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfDelegationVo get(Long id) {
        WfDelegation wfDelegation = super.getById(id);
        WfDelegationVo wfDelegationVo = null;
        if(wfDelegation !=null){
            wfDelegationVo = dozerMapper.map(wfDelegation,WfDelegationVo.class);
        }
        log.debug("查询成功");
        return wfDelegationVo;
    }

    /**
    * 保存实体
    * @param wfDelegationDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfDelegationDto wfDelegationDto) {
        WfDelegation wfDelegation = dozerMapper.map(wfDelegationDto,WfDelegation.class);
        checkDateCoincide(wfDelegationDto);
        boolean result = super.save(wfDelegation);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    //验证委托人的时间 是否 和数据库中的时间 重合
    public void checkDateCoincide(WfDelegationDto wfDelegationDto) {
        log.debug("验证委托人的时间 是否 和数据库中的时间 重合 参数：" + wfDelegationDto.toString());
        String designatePersonId = wfDelegationDto.getDesignateUserName();
        if(StringUtils.isNotBlank(designatePersonId)) {
            long s1 = wfDelegationDto.getDelegationStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long e1 = wfDelegationDto.getDelegationEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            //首先验证结束时间 是否小于开始时间
            if(e1 <= s1) {
                throw new IncloudException("结束时间不能，小于等于开始时间！");
            }
            //根据委托人查询出所有 委托人相关的信息
            LambdaQueryWrapper<WfDelegation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ObjectUtil.isNotEmpty(designatePersonId), WfDelegation::getDesignateUserName, wfDelegationDto.getDesignateUserName())
            .eq(ObjectUtil.isNotEmpty(wfDelegationDto.getProcdefTypeId()), WfDelegation::getProcdefTypeId, wfDelegationDto.getProcdefTypeId())
            .ne(WfDelegation::getId,wfDelegationDto.getId());
            List<WfDelegation> wfDelegationList = wfDelegationMapper.selectList(queryWrapper);
            if(CollectionUtil.isNotEmpty(wfDelegationList)) {
                for (WfDelegation delegation : wfDelegationList) {
                    //新添加的开始时间
                    long s2 = delegation.getDelegationStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    long e2 = delegation.getDelegationEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    //其实不重叠就两重情况，s2>e1 or s1 > e2，然后取反就行了
                    if(!(s1 < s2 || s1 > e2)) {
                        throw new IncloudException("委托人："+wfDelegationDto.getDesignateUserNameCh()+" 委托时间存在重合数据！");
                    }
                }
            }
        } else {
            throw new IncloudException("委托人id,不能为空！");
        }
    }

    /**
    * 修改实体
    * @param wfDelegationDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfDelegationDto wfDelegationDto) {
        WfDelegation wfDelegation = dozerMapper.map(wfDelegationDto,WfDelegation.class);
        checkDateCoincide(wfDelegationDto);
        boolean result = super.updateById(wfDelegation);
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
    public Boolean deleteBatchIds(String ids) {
        log.debug("委办的批量删除：参数" + ids);
        boolean result = false;
        if(StringUtils.isNotBlank(ids)) {
            String[] arr = ids.split(",");
            if(arr.length > 0) {
                List<Long> idsL = new ArrayList<>();
                for (String s : arr) {
                    idsL.add(Long.valueOf(s));
                }
                result = super.removeByIds(idsL);
                if(result){
                    log.debug("批量删除成功");
                }
            }
        }
        return result;
    }


    @Override
    public List<WfDelegationVo> selectBatchIds(List<Long> ids) {
        log.debug("按照多个按钮id查询 参数：" + ids.toString());
        List wfDelegationVoList = wfDelegationMapper.selectBatchIds(ids);
        log.debug("按照多个按钮id查询 结果：" + wfDelegationVoList.toString());
        return wfDelegationVoList;
    }

    /**
     * 通过委办人，获取到相应的被委办人
     * @param designateUserName
     * @return
     */
    @Override
    public WfDelegationVo getEntity(String designateUserName) {
        LambdaQueryWrapper<WfDelegation> queryWrapper = new LambdaQueryWrapper<>();
        LocalDateTime dateTime = LocalDateTime.now();
        queryWrapper.eq(StrUtil.isNotEmpty(designateUserName),WfDelegation::getDesignateUserName,designateUserName)
        .le(WfDelegation::getDelegationStartTime,dateTime)
        .ge(WfDelegation::getDelegationEndTime,dateTime);
        WfDelegation wfDelegation = this.getOne(queryWrapper);
        WfDelegationVo wfDelegationVo = null;
        if(ObjectUtil.isNotEmpty(wfDelegation) && ObjectUtil.isNotEmpty(wfDelegation.getDelegationUserName())){
            wfDelegationVo = dozerMapper.map(wfDelegation, WfDelegationVo.class);
            log.info("委办人：{}",wfDelegationVo.getDelegationUserName());
        }
        return wfDelegationVo;
    }

    @Override
    public List<WfDelegationVo> getEntityList(List<String> designatePersonIds) {
        log.info("getEntityList 参数：{}",designatePersonIds.toString());
        LambdaQueryWrapper<WfDelegation> queryWrapper = new LambdaQueryWrapper<>();
        LocalDateTime dateTime = LocalDateTime.now();
        queryWrapper.in(CollectionUtil.isNotEmpty(designatePersonIds),WfDelegation::getDesignateUserName,designatePersonIds)
                .le(WfDelegation::getDelegationStartTime,dateTime)
                .ge(WfDelegation::getDelegationEndTime,dateTime);
        List<WfDelegation> wfDelegationList = this.list(queryWrapper);
        List<WfDelegationVo> wfDelegationVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(wfDelegationList)) {
            for (WfDelegation wfDelegation : wfDelegationList) {
                WfDelegationVo _wfDelegationVo = dozerMapper.map(wfDelegation, WfDelegationVo.class);
                wfDelegationVo.add(_wfDelegationVo);
            }
        }
        log.info("getEntityList 结果为：{}",wfDelegationVo.toString());
        return wfDelegationVo;
    }

    /**
     * 获取用户ID
     * @return
     */
    private String getUserID(){
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtil.isEmpty(loginAppUser)){
            throw new IncloudException("用户未登录！");
        }
        String userId = loginAppUser.getUserName();
        return userId;
    }
}
