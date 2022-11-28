package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.UserClassEnum;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.study.dto.StudyUserDto;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.study.constants.ApplyStatus;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.entity.StudyUserApply;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.StudyUserApplyMapper;
import com.netwisd.biz.study.service.StudyUserApplyService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyUserApplyDto;
import com.netwisd.biz.study.vo.StudyUserApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 在线学习人员申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-03-23 16:06:26
 */
@Service
@Slf4j
public class StudyUserApplyServiceImpl extends ServiceImpl<StudyUserApplyMapper, StudyUserApply> implements StudyUserApplyService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyUserApplyMapper studyUserApplyMapper;

    @Autowired
    private MdmClient mdmClient;

    /**
    * 单表简单查询操作
    * @param studyUserApplyDto
    * @return
    */
    @Override
    public Page list(StudyUserApplyDto studyUserApplyDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        LambdaQueryWrapper<StudyUserApply> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(StringUtils.isNotBlank(studyUserApplyDto.getUserNameCh()),StudyUserApply::getUserNameCh,studyUserApplyDto.getUserNameCh());
        queryWrapper.eq(StringUtils.isNotBlank(studyUserApplyDto.getPhoneNum()),StudyUserApply::getPhoneNum,studyUserApplyDto.getPhoneNum());
        queryWrapper.eq(StringUtils.isNotBlank(studyUserApplyDto.getIdCard()),StudyUserApply::getIdCard,studyUserApplyDto.getIdCard());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyUserApplyDto.getUserType()),StudyUserApply::getUserType,studyUserApplyDto.getUserType());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyUserApplyDto.getSex()),StudyUserApply::getSex,studyUserApplyDto.getSex());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyUserApplyDto.getUserStatus()),StudyUserApply::getUserStatus,studyUserApplyDto.getUserStatus());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyUserApplyDto.getIsStatus())
                && ObjectUtils.isEmpty(studyUserApplyDto.getUserStatus()) && studyUserApplyDto.getIsStatus() == 0,StudyUserApply::getUserStatus,ApplyStatus.NO_APPLY.code);
        queryWrapper.ne(ObjectUtils.isNotEmpty(studyUserApplyDto.getIsStatus())
                && ObjectUtils.isEmpty(studyUserApplyDto.getUserStatus()) && studyUserApplyDto.getIsStatus() == 1,StudyUserApply::getUserStatus,ApplyStatus.NO_APPLY.code);
        if (!"10000".equals(loginAppUser.getIdCard())) {//平台管理员看所有，审核人员只能看到本机构的
            queryWrapper.eq(StudyUserApply::getParentOrgId,loginAppUser.getParentOrgId());
        }
        Page<StudyUserApply> page = studyUserApplyMapper.selectPage(studyUserApplyDto.getPage(),queryWrapper);
        Page<StudyUserApplyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyUserApplyVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyUserApplyDto
    * @return
    */
    @Override
    public List<StudyUserApplyVo> lists(StudyUserApplyDto studyUserApplyDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        LambdaQueryWrapper<StudyUserApply> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(StringUtils.isNotBlank(studyUserApplyDto.getUserNameCh()),StudyUserApply::getUserNameCh,studyUserApplyDto.getUserNameCh());
        queryWrapper.eq(StringUtils.isNotBlank(studyUserApplyDto.getPhoneNum()),StudyUserApply::getPhoneNum,studyUserApplyDto.getPhoneNum());
        queryWrapper.eq(StringUtils.isNotBlank(studyUserApplyDto.getIdCard()),StudyUserApply::getIdCard,studyUserApplyDto.getIdCard());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyUserApplyDto.getUserType()),StudyUserApply::getUserType,studyUserApplyDto.getUserType());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyUserApplyDto.getSex()),StudyUserApply::getSex,studyUserApplyDto.getSex());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyUserApplyDto.getUserStatus()),StudyUserApply::getUserStatus,studyUserApplyDto.getUserStatus());
        queryWrapper.eq(ObjectUtils.isNotEmpty(studyUserApplyDto.getIsStatus())
                && ObjectUtils.isEmpty(studyUserApplyDto.getUserStatus()) && studyUserApplyDto.getIsStatus() == 0,StudyUserApply::getUserStatus,ApplyStatus.NO_APPLY.code);
        queryWrapper.ne(ObjectUtils.isNotEmpty(studyUserApplyDto.getIsStatus())
                && ObjectUtils.isEmpty(studyUserApplyDto.getUserStatus()) && studyUserApplyDto.getIsStatus() == 1,StudyUserApply::getUserStatus,ApplyStatus.NO_APPLY.code);
        if (!"10000".equals(loginAppUser.getIdCard())) {//平台管理员看所有，审核人员只能看到本机构的
            queryWrapper.eq(StudyUserApply::getParentOrgId,loginAppUser.getParentOrgId());
        }
        List<StudyUserApply> studyUserApplies = studyUserApplyMapper.selectList(queryWrapper);
        List<StudyUserApplyVo> studyUserApplyVos = DozerUtils.mapList(dozerMapper, studyUserApplies, StudyUserApplyVo.class);
        log.debug("查询条数:"+studyUserApplyVos.size());
        return studyUserApplyVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyUserApplyVo get(Long id) {
        StudyUserApply studyUserApply = super.getById(id);
        StudyUserApplyVo studyUserApplyVo = null;
        if(studyUserApply !=null){
            studyUserApplyVo = dozerMapper.map(studyUserApply,StudyUserApplyVo.class);
        }
        log.debug("查询成功");
        return studyUserApplyVo;
    }

    /**
     * 验证字段
     */
    private void checkData(StudyUserApplyDto userApplyDto){
        if (StringUtils.isBlank(userApplyDto.getUserNameCh())) {
            throw new IncloudException("请填写姓名");
        }
        if (ObjectUtils.isEmpty(userApplyDto.getSex())) {
            throw new IncloudException("请选择性别");
        }
        if (StringUtils.isBlank(userApplyDto.getPhoneNum())) {
            throw new IncloudException("请填写电话");
        }
        if (StringUtils.isBlank(userApplyDto.getIdCard())) {
            throw new IncloudException("请填写身份证号");
        }
        if (StringUtils.isBlank(userApplyDto.getUnitName())) {
            throw new IncloudException("请填写单位名称");
        }
        if (StringUtils.isBlank(userApplyDto.getUserConditionCode())) {
            throw new IncloudException("请选择人员情况");
        }
        if (ObjectUtils.isEmpty(userApplyDto.getUserType())) {
            throw new IncloudException("请选择人员角色");
        }
        if (ObjectUtils.isEmpty(userApplyDto.getParentOrgId())) {
            throw new IncloudException("请选择服务单位");
        }
        if (StringUtils.isBlank(userApplyDto.getVerificationCode())) {
            throw new IncloudException("请填写验证码");
        }
        try {
            Result result = mdmClient.verificationCode(userApplyDto.getPhoneNum(), userApplyDto.getVerificationCode());
            if (!result.getData().equals("true")) {
                throw new IncloudException("验证码错误，请重新填写");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new IncloudException("验证码验证失败，请联系管理员");
        }
        //如果人员已经申请并且是审核通过或者审核中，不让重复申请
        List<Integer> statusList = new ArrayList<>();
        statusList.add(ApplyStatus.NO_APPLY.code);
        statusList.add(ApplyStatus.APPLY_PASS.code);
        LambdaQueryWrapper<StudyUserApply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyUserApply::getIdCard,userApplyDto.getIdCard());
        queryWrapper.in(StudyUserApply::getUserStatus,statusList);
        Integer oldNum = studyUserApplyMapper.selectCount(queryWrapper);
        if (oldNum > 0) {
            throw new IncloudException("该人员已经申请，请勿重复申请");
        }
    }

    /**
    * 保存实体
    * @param studyUserApplyDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyUserApplyDto studyUserApplyDto) {
        this.checkData(studyUserApplyDto);
        studyUserApplyDto.setId(IdGenerator.getIdGenerator());
        studyUserApplyDto.setUserStatus(ApplyStatus.NO_APPLY.code);
        studyUserApplyDto.setCreateTime(LocalDateTime.now());
        StudyUserApply studyUserApply = dozerMapper.map(studyUserApplyDto,StudyUserApply.class);
        boolean result = super.save(studyUserApply);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyUserApplyDtos
    * @return
    */
    @Transactional
    @Override
    public Boolean update(List<StudyUserApplyDto> studyUserApplyDtos) {
        if (CollectionUtil.isEmpty(studyUserApplyDtos)) {
            throw new IncloudException("请传需要审批的人员！");
        }
        for (StudyUserApplyDto studyUserApplyDto : studyUserApplyDtos){
            studyUserApplyDto.setUpdateTime(LocalDateTime.now());
            StudyUserApply studyUserApply = dozerMapper.map(studyUserApplyDto,StudyUserApply.class);
            boolean result = super.updateById(studyUserApply);
            if(result){
                if (studyUserApply.getUserStatus().equals(ApplyStatus.APPLY_PASS.code)) {
                    //审核通过
                    //增加人员
                    List<StudyUserDto> studyUserDtoList = new ArrayList<>();
                    StudyUserDto studyUserDto = new StudyUserDto();
                    dozerMapper.map(studyUserApplyDto,studyUserDto);
                    studyUserDto.setStatus(YesNo.YES.code);
                    studyUserDto.setUserClass(UserClassEnum.EDU.code);
                    studyUserDtoList.add(studyUserDto);
                    Result<Boolean> addStudyResult = mdmClient.saveStudyUserBatch(studyUserDtoList);
                    if (addStudyResult.getData()) {
                        //短信通知用户结果

                    }
                }
                if (studyUserApply.getUserStatus().equals(ApplyStatus.APPLY_NOTPASS.code)) {
                    //审核不通过
                    //手机通知用户结果
                }
            }
        }
        return true;
    }

    /**
    * 通过ID删除
    * @param ids
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        String[] idList = ids.split(",");
        for (String id : idList){
            super.removeById(id);
        }
        return true;
    }
}
