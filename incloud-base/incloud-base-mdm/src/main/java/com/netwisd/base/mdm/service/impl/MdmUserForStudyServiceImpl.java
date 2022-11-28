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
    private final String studyAdmin = "STUDY_ADMIN";//管理员角色编码
    private final String studyTeacher = "STUDY_TEACHER";//讲师角色编码
    private final String studyStudent = "STUDY_STUDENT";//学员角色编码

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
     * 分页查询在线学习人员
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
        log.debug("分页查询在线学习人员，查询数量：{}", userVoPage.getTotal());
        return userVoPage;
    }

    /**
     * 集合查询在线学习人员
     *
     * @param studyUserDto
     * @return
     */
    @Override
    public List<StudyUserVo> findByList(StudyUserDto studyUserDto) {
        LambdaQueryWrapper<MdmUser> userWrapper = findStudyUser(studyUserDto);
        List<MdmUser> studyUsers = userMapper.selectList(userWrapper);
        List<StudyUserVo> mdmUserVos = relationPost(studyUsers);
        log.debug("集合查询在线学习人员，查询数量：{}", mdmUserVos.size());
        return mdmUserVos;
    }

    /**
     * 条件查询人员数据
     *
     * @param studyUserDto
     * @return
     */
    private LambdaQueryWrapper<MdmUser> findStudyUser(StudyUserDto studyUserDto) {
        //查在线学习的角色与人的关系表 查出人员的id集合
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
        //根据人员id等其他基本信息查人员数据
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
     * 关联人员的角色
     *
     * @param userList
     * @return
     */
    private List<StudyUserVo> relationPost(List<MdmUser> userList) {
        List<StudyUserVo> studyUserVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userList)) {
            for (MdmUser mdmUser : userList) {
                //查出当前人的所有在线学习角色
                LambdaQueryWrapper<MdmRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
                List<String> roleList = new ArrayList<>();
                roleList.add(studyAdmin);
                roleList.add(studyTeacher);
                roleList.add(studyStudent);
                roleUserWrapper.in(MdmRoleUser::getRoleCode, roleList);
                roleUserWrapper.eq(MdmRoleUser::getUserId, mdmUser.getId());
                List<MdmRoleUser> roleUsers = roleUserMapper.selectList(roleUserWrapper);
                //人员返回信息
                StudyUserVo studyUserVo = new StudyUserVo();
                dozerMapper.map(mdmUser, studyUserVo);
                //将在线学习角色放到返回数据中 管理员>教师>学员
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
        log.debug("通过id查询在线学习人员，查询id：{}", id);
        return null;
    }

    /**
     * 批量在主数据新增在线学习人员
     *
     * @param studyUserDtoList
     * @return
     */
    @Override
    @Transactional
    public Boolean saveStudyUserBatch(List<StudyUserDto> studyUserDtoList) {
        if (CollectionUtil.isNotEmpty(studyUserDtoList)) {
            for (StudyUserDto studyUserDto : studyUserDtoList) {
                //校验身份证号
                IdCartUtils.IDCardValidate(studyUserDto.getIdCard());
                //校验手机号
                PhoneUtil.isMobileNO(studyUserDto.getPhoneNum());
                //1.先判断当前人员是否已经在主数据中
                LambdaQueryWrapper<MdmUser> userWrapper = new LambdaQueryWrapper<>();
                userWrapper.eq(MdmUser::getIdCard, studyUserDto.getIdCard());
                MdmUser mdmUser = userMapper.selectOne(userWrapper);
                if (ObjectUtils.isNotEmpty(mdmUser)) {
                    //2.如果存在，只关联人员角色关系
                    if (mdmUser.getUserClass().equals(UserClassEnum.BIZ.code)) {
                        //2.1如果人员是中原建的并且是离职状态，则报错
                        if (mdmUser.getStatus().equals(UserStatusEnum.QUIT.code)) {
                            throw new IncloudException(mdmUser.getUserNameCh() + "已经在中原建中离职，请重新选择需要增加的人员！");
                        }
                        //2.2如果人员是中原建的并且已经在在线学习中，则报错
                        StudyUserDto findDto = new StudyUserDto();
                        findDto.setIdCard(mdmUser.getIdCard());
                        List<StudyUserVo> studyUserVoList = findByList(findDto);
                        if (studyUserVoList.size() > 0) {
                            throw new IncloudException(mdmUser.getUserNameCh() + "已经在在线学习人员中，请重新选择需要增加的人员！");
                        }
                    }
                } else {
                    //3.如果不存在，先添加人（类型是在线学习的人员）
                    //3.1查询在线学习机构信息
                    LambdaQueryWrapper<MdmOrg> orgWrapper = new LambdaQueryWrapper<>();
                    orgWrapper.eq(MdmOrg::getId, studyUserDto.getParentDeptId());
                    orgWrapper.eq(MdmOrg::getStatus, StatusEnum.RUNNING.code);
                    MdmOrg studyOrg = orgMapper.selectOne(orgWrapper);
                    if (ObjectUtils.isEmpty(studyOrg)) {
                        throw new IncloudException("未查询到相关部门信息，请联系管理员！");
                    }
                    if (StringUtils.isBlank(studyUserDto.getUserType())) {
                        throw new IncloudException(studyUserDto.getUserNameCh() + "未选择人员类型！");
                    }
                    //3.2增加在线学习人员信息
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
                //关联角色
                updateUserRole(mdmUser, studyUserDto.getUserType());
            }

        }
        return true;
    }

    @Override
    public Boolean updateStudyUser(StudyUserDto studyUserDto) {
        //校验身份证号
        IdCartUtils.IDCardValidate(studyUserDto.getIdCard());
        //校验手机号
        PhoneUtil.isMobileNO(studyUserDto.getPhoneNum());
        //1.判断是在线学习的人员还是主数据的人员
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getIdCard, studyUserDto.getIdCard());
        MdmUser mdmUser = userMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(mdmUser)) {
            throw new IncloudException("未找到人员的相关信息！");
        }
        //2.如果是在线学习的人员，可以修改基本信息和角色关系
        if (mdmUser.getUserClass().equals(UserClassEnum.EDU.code)) {
            dozerMapper.map(studyUserDto, mdmUser);
            userMapper.updateById(mdmUser);
        }
        //3.如果是主数据人员，只能修改角色关系
        updateUserRole(mdmUser, studyUserDto.getUserType());
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteStudyUser(String ids,String userType) {
        if (StringUtils.isEmpty(ids)) {
            throw new IncloudException("请选择需要删除的人员！");
        }
        for (String id : ids.split(",")) {
            LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdmUser::getId, id);
            MdmUser mdmUser = userMapper.selectOne(queryWrapper);
            if (ObjectUtils.isNotEmpty(mdmUser)) {
                if (mdmUser.getUserClass().equals(UserClassEnum.EDU.code)) {
                    userMapper.deleteById(id);
                }
                //删除人员角色关系
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
     * 关联在线学习角色
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
                //2.新增在线学习的角色
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
                    throw new IncloudException("未找到在线学习默认角色：{}，请联系管理员！", userType);
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
     * 根据用户id返回在线学习角色
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
        //如果一个人包含多个在线学习角色，那么返回顺序 管理员 > 讲师 > 学员
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
