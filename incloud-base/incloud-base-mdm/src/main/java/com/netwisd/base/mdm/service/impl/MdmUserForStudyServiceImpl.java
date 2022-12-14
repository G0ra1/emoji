package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.constants.UserClassEnum;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.MdmRoleUserVo;
import com.netwisd.base.common.study.dto.StudyUserDto;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.study.vo.StudyUserVo;
import com.netwisd.base.common.user.constants.UserType;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.mdm.constants.StatusEnum;
import com.netwisd.base.mdm.constants.UserStatusEnum;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.netwisd.base.mdm.entity.MdmRole;
import com.netwisd.base.mdm.entity.MdmRoleUser;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.mapper.MdmOrgMapper;
import com.netwisd.base.mdm.mapper.MdmRoleMapper;
import com.netwisd.base.mdm.mapper.MdmRoleUserMapper;
import com.netwisd.base.mdm.mapper.MdmUserMapper;
import com.netwisd.base.mdm.service.MdmRoleUserService;
import com.netwisd.base.mdm.service.MdmUserForStudyService;
import com.netwisd.base.common.util.IdCartUtils;
import com.netwisd.base.common.util.PhoneUtil;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MdmUserForStudyServiceImpl implements MdmUserForStudyService {
    private final String studyAdmin = "STUDY_ADMIN";//?????????????????????
    private final String studyTeacher = "STUDY_TEACHER";//??????????????????
    private final String studyStudent = "STUDY_STUDENT";//??????????????????

    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private MdmRoleMapper roleMapper;
    @Autowired
    private MdmRoleUserMapper roleUserMapper;
    @Autowired
    private MdmRoleUserService roleUserService;
    @Autowired
    private MdmUserMapper userMapper;
    @Autowired
    private MdmOrgMapper orgMapper;

    /**
     * ??????????????????????????????
     *
     * @param studyUserDto
     * @return
     */
    @Override
    public Page<StudyUserVo> findByPage(StudyUserDto studyUserDto) {
        LambdaQueryWrapper<MdmUser> userWrapper = findStudyUser(studyUserDto);
        Page<MdmUser> page = userMapper.selectPage(studyUserDto.getPage(), userWrapper);
        Page<StudyUserVo> userVoPage = DozerUtils.mapPage(dozerMapper, page, StudyUserVo.class);
        List<StudyUserVo> studyUserVos = relationPost(DozerUtils.mapList(dozerMapper, userVoPage.getRecords(), MdmUser.class));
        userVoPage.setRecords(studyUserVos);
        log.debug("????????????????????????????????????????????????{}", userVoPage.getTotal());
        return userVoPage;
    }

    /**
     * ??????????????????????????????
     *
     * @param studyUserDto
     * @return
     */
    @Override
    public List<StudyUserVo> findByList(StudyUserDto studyUserDto) {
        LambdaQueryWrapper<MdmUser> userWrapper = findStudyUser(studyUserDto);
        List<MdmUser> studyUsers = userMapper.selectList(userWrapper);
        List<StudyUserVo> mdmUserVos = relationPost(studyUsers);
        log.debug("????????????????????????????????????????????????{}", mdmUserVos.size());
        return mdmUserVos;
    }

    /**
     * ????????????????????????
     *
     * @param studyUserDto
     * @return
     */
    private LambdaQueryWrapper<MdmUser> findStudyUser(StudyUserDto studyUserDto) {
        //?????????????????????????????????????????? ???????????????id??????
        LambdaQueryWrapper<MdmRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
        List<String> roleList = new ArrayList<>();
        roleList.add(studyAdmin);
        roleList.add(studyTeacher);
        roleList.add(studyStudent);
        if (StringUtils.isNotEmpty(studyUserDto.getUserType())) {
            roleList.clear();
            String[] userTypes = studyUserDto.getUserType().split(",");
            for (String userType : userTypes) {
                if (Integer.valueOf(userType).equals(StudyUserTypeEnum.ADMIN.code)) {
                    roleList.add(studyAdmin);
                }
                if (Integer.valueOf(userType).equals(StudyUserTypeEnum.TEACHER.code)) {
                    roleList.add(studyTeacher);
                }
                if (Integer.valueOf(userType).equals(StudyUserTypeEnum.STUDENT.code)) {
                    roleList.add(studyStudent);
                }
            }
        }
        roleUserWrapper.in(MdmRoleUser::getRoleCode, roleList);
        List<MdmRoleUser> mdmRoleUsers = roleUserMapper.selectList(roleUserWrapper);
        List<Long> userIds = mdmRoleUsers.stream().map(MdmRoleUser::getUserId).collect(Collectors.toList());
        //????????????id????????????????????????????????????
        LambdaQueryWrapper<MdmUser> userWrapper = new LambdaQueryWrapper<>();
        if (CollectionUtil.isEmpty(userIds)) {
            userWrapper.in(MdmUser::getId, "123456");
        } else {
            userWrapper.in(MdmUser::getId, userIds);
        }
        userWrapper.like(StringUtils.isNotEmpty(studyUserDto.getUserNameCh()), MdmUser::getUserNameCh, studyUserDto.getUserNameCh());
        userWrapper.like(StringUtils.isNotEmpty(studyUserDto.getIdCard()), MdmUser::getIdCard, studyUserDto.getIdCard());
        userWrapper.like(StringUtils.isNotEmpty(studyUserDto.getPhoneNum()), MdmUser::getPhoneNum, studyUserDto.getPhoneNum());
        userWrapper.like(StringUtils.isNotEmpty(studyUserDto.getEmail()), MdmUser::getEmail, studyUserDto.getEmail());
        userWrapper.eq(ObjectUtils.isNotEmpty(studyUserDto.getUserClass()), MdmUser::getUserClass, studyUserDto.getUserClass());
        return userWrapper;
    }

    /**
     * ?????????????????????
     *
     * @param userList
     * @return
     */
    private List<StudyUserVo> relationPost(List<MdmUser> userList) {
        List<StudyUserVo> studyUserVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userList)) {
            for (MdmUser mdmUser : userList) {
                //??????????????????????????????????????????
                LambdaQueryWrapper<MdmRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
                List<String> roleList = new ArrayList<>();
                roleList.add(studyAdmin);
                roleList.add(studyTeacher);
                roleList.add(studyStudent);
                roleUserWrapper.in(MdmRoleUser::getRoleCode, roleList);
                roleUserWrapper.eq(MdmRoleUser::getUserId, mdmUser.getId());
                List<MdmRoleUser> roleUsers = roleUserMapper.selectList(roleUserWrapper);
                //??????????????????
                StudyUserVo studyUserVo = new StudyUserVo();
                dozerMapper.map(mdmUser, studyUserVo);
                //?????????????????????????????????????????? ?????????>??????>??????
                if (ObjectUtils.isNotEmpty(roleUsers)) {
                    Integer userType = StudyUserTypeEnum.STUDENT.code;
                    for (MdmRoleUser roleUser : roleUsers){
                        if (roleUser.getRoleCode().equals(studyAdmin)) {
                            userType = StudyUserTypeEnum.ADMIN.code;
                            break;
                        }
                        if (roleUser.getRoleCode().equals(studyTeacher)) {
                            userType = StudyUserTypeEnum.TEACHER.code;
                        }
                    }
                    studyUserVo.setUserType(userType);
                }
                studyUserVos.add(studyUserVo);
            }
        }
        return studyUserVos;
    }

    @Override
    public StudyUserVo get(Long id) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getId, id);
        MdmUser mdmUser = userMapper.selectOne(queryWrapper);
        List<MdmUser> userList = new ArrayList<>();
        userList.add(mdmUser);
        List<StudyUserVo> studyUserVos = relationPost(userList);
        if (CollectionUtil.isNotEmpty(studyUserVos)) {
            return studyUserVos.get(0);
        }
        log.debug("??????id?????????????????????????????????id???{}", id);
        return null;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param studyUserDtoList
     * @return
     */
    @Override
    @Transactional
    public Boolean saveStudyUserBatch(List<StudyUserDto> studyUserDtoList) {
        if (CollectionUtil.isNotEmpty(studyUserDtoList)) {
            for (StudyUserDto studyUserDto : studyUserDtoList) {
                //??????????????????
                IdCartUtils.IDCardValidate(studyUserDto.getIdCard());
                //???????????????
                PhoneUtil.isMobileNO(studyUserDto.getPhoneNum());
                //1.????????????????????????????????????????????????
                LambdaQueryWrapper<MdmUser> userWrapper = new LambdaQueryWrapper<>();
                userWrapper.eq(MdmUser::getIdCard, studyUserDto.getIdCard());
                MdmUser mdmUser = userMapper.selectOne(userWrapper);
                if (ObjectUtils.isNotEmpty(mdmUser)) {
                    //2.??????????????????????????????????????????
                    if (mdmUser.getUserClass().equals(UserClassEnum.BIZ.code)) {
                        //2.1????????????????????????????????????????????????????????????
                        if (mdmUser.getStatus().equals(UserStatusEnum.QUIT.code)) {
                            throw new IncloudException(mdmUser.getUserNameCh() + "?????????????????????????????????????????????????????????????????????");
                        }
                        //2.2?????????????????????????????????????????????????????????????????????
                        StudyUserDto findDto = new StudyUserDto();
                        findDto.setIdCard(mdmUser.getIdCard());
                        List<StudyUserVo> studyUserVoList = findByList(findDto);
                        if (studyUserVoList.size() > 0) {
                            throw new IncloudException(mdmUser.getUserNameCh() + "????????????????????????????????????????????????????????????????????????");
                        }
                    }
                } else {
                    //3.??????????????????????????????????????????????????????????????????
                    //3.1??????????????????????????????
                    LambdaQueryWrapper<MdmOrg> orgWrapper = new LambdaQueryWrapper<>();
                    orgWrapper.eq(MdmOrg::getId, studyUserDto.getParentDeptId());
                    orgWrapper.eq(MdmOrg::getStatus, StatusEnum.RUNNING.code);
                    MdmOrg studyOrg = orgMapper.selectOne(orgWrapper);
                    if (ObjectUtils.isEmpty(studyOrg)) {
                        throw new IncloudException("??????????????????????????????????????????????????????");
                    }
                    if (StringUtils.isBlank(studyUserDto.getUserType())) {
                        throw new IncloudException(studyUserDto.getUserNameCh() + "????????????????????????");
                    }
                    //3.2??????????????????????????????
                    mdmUser = new MdmUser();
                    dozerMapper.map(studyUserDto, mdmUser);
                    userWrapper.clear();
                    userWrapper.eq(MdmUser::getUserClass, UserClassEnum.EDU.code);
                    Integer studyUserSize = userMapper.selectCount(userWrapper);
                    mdmUser.setSort(studyUserSize + 1);
                    mdmUser.setGlobalSort(mdmUser.getSort());
                    mdmUser.setGlobalSortSecond(0);
                    mdmUser.setUserName("u" + studyUserDto.getIdCard());
                    mdmUser.setCreateTime(LocalDateTime.now());
                    mdmUser.setUpdateTime(LocalDateTime.now());
                    mdmUser.setUserClass(studyUserDto.getUserClass());
                    mdmUser.setStatus(studyUserDto.getStatus());
                    userMapper.insert(mdmUser);
                }
                //????????????
                updateUserRole(mdmUser, studyUserDto.getUserType());
            }

        }
        return true;
    }

    @Override
    public Boolean updateStudyUser(StudyUserDto studyUserDto) {
        //??????????????????
        IdCartUtils.IDCardValidate(studyUserDto.getIdCard());
        //???????????????
        PhoneUtil.isMobileNO(studyUserDto.getPhoneNum());
        //1.??????????????????????????????????????????????????????
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getIdCard, studyUserDto.getIdCard());
        MdmUser mdmUser = userMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(mdmUser)) {
            throw new IncloudException("?????????????????????????????????");
        }
        //2.????????????????????????????????????????????????????????????????????????
        if (mdmUser.getUserClass().equals(UserClassEnum.EDU.code)) {
            dozerMapper.map(studyUserDto, mdmUser);
            userMapper.updateById(mdmUser);
        }
        //3.???????????????????????????????????????????????????
        updateUserRole(mdmUser, studyUserDto.getUserType());
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteStudyUser(String ids,String userType) {
        if (StringUtils.isEmpty(ids)) {
            throw new IncloudException("?????????????????????????????????");
        }
        for (String id : ids.split(",")) {
            LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdmUser::getId, id);
            MdmUser mdmUser = userMapper.selectOne(queryWrapper);
            if (ObjectUtils.isNotEmpty(mdmUser)) {
                if (mdmUser.getUserClass().equals(UserClassEnum.EDU.code)) {
                    userMapper.deleteById(id);
                }
                //????????????????????????
                LambdaQueryWrapper<MdmRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
                roleUserWrapper.eq(MdmRoleUser::getUserId,id);
                if (Integer.valueOf(userType).equals(StudyUserTypeEnum.ADMIN.code)) {
                    roleUserWrapper.eq(MdmRoleUser::getRoleCode,studyAdmin);
                }
                if (Integer.valueOf(userType).equals(StudyUserTypeEnum.TEACHER.code)) {
                    roleUserWrapper.eq(MdmRoleUser::getRoleCode,studyTeacher);
                }
                if (Integer.valueOf(userType).equals(StudyUserTypeEnum.STUDENT.code)) {
                    roleUserWrapper.eq(MdmRoleUser::getRoleCode,studyStudent);
                }
                roleUserMapper.delete(roleUserWrapper);
            }
        }
        return true;
    }

    /**
     * ????????????????????????
     *
     * @param user
     * @param userTypes
     * @return
     */
    @Override
    public Boolean updateUserRole(MdmUser user, String userTypes) {
        if (StringUtils.isNotEmpty(userTypes)) {
            String[] userTypeList = userTypes.split(",");
            for (String userType : userTypeList) {
                //2.???????????????????????????
                LambdaQueryWrapper<MdmRole> roleWrapper = new LambdaQueryWrapper<>();
                if (userType.equals(StudyUserTypeEnum.ADMIN.code.toString())) {
                    roleWrapper.eq(MdmRole::getRoleCode, studyAdmin);
                }
                if (userType.equals(StudyUserTypeEnum.TEACHER.code.toString())) {
                    roleWrapper.eq(MdmRole::getRoleCode, studyTeacher);
                }
                if (userType.equals(StudyUserTypeEnum.STUDENT.code.toString())) {
                    roleWrapper.eq(MdmRole::getRoleCode, studyStudent);
                }
                MdmRole role = roleMapper.selectOne(roleWrapper);
                if (ObjectUtils.isEmpty(role)) {
                    throw new IncloudException("????????????????????????????????????{}????????????????????????", userType);
                }
                MdmRoleUser roleUser = new MdmRoleUser();
                roleUser.setRoleId(role.getId());
                roleUser.setRoleCode(role.getRoleCode());
                roleUser.setRoleName(role.getRoleName());
                roleUser.setUserId(user.getId());
                roleUser.setUserName(user.getUserName());
                roleUser.setUserNameCh(user.getUserNameCh());
                roleUserMapper.insert(roleUser);
            }
            return true;
        }
        return null;
    }

    /**
     * ????????????id????????????????????????
     *
     * @param id
     * @return
     */
    @Override
    public Integer getStudyRoleByUserId(Long id) {
        List<MdmRoleUserVo> roleUserByUserId = roleUserService.getRoleUserByUserId(id);
        StringBuilder roleBuilder = new StringBuilder();
        for (MdmRoleUserVo roleUser : roleUserByUserId) {
            if (roleUser.getRoleCode().equals(studyAdmin)) {
                roleBuilder.append(StudyUserTypeEnum.ADMIN.code);
                roleBuilder.append(",");
            }
            if (roleUser.getRoleCode().equals(studyTeacher)) {
                roleBuilder.append(StudyUserTypeEnum.TEACHER.code);
                roleBuilder.append(",");
            }
            if (roleUser.getRoleCode().equals(studyStudent)) {
                roleBuilder.append(StudyUserTypeEnum.STUDENT.code);
                roleBuilder.append(",");
            }
        }
        //?????????????????????????????????????????????????????????????????? ????????? > ?????? > ??????
        if (StringUtils.isNotBlank(roleBuilder)) {
            String roleString = roleBuilder.toString();
            if (roleString.contains(StudyUserTypeEnum.ADMIN.code.toString())) {
                return StudyUserTypeEnum.ADMIN.code;
            }
            if (roleString.contains(StudyUserTypeEnum.TEACHER.code.toString())) {
                return StudyUserTypeEnum.TEACHER.code;
            }
            if (roleString.contains(StudyUserTypeEnum.STUDENT.code.toString())) {
                return StudyUserTypeEnum.STUDENT.code;
            }
        }
        return null;
    }
}
