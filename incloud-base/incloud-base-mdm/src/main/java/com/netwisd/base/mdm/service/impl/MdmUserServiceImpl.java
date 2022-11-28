package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.LoginEnum;
import com.netwisd.base.common.constants.StudyEnum;
import com.netwisd.base.common.constants.UserClassEnum;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.dto.*;
import com.netwisd.base.common.mdm.vo.*;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AesUtil;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.mdm.constants.*;
import com.netwisd.base.mdm.dto.MdmTransferDto;
import com.netwisd.base.mdm.entity.*;
import com.netwisd.base.mdm.event.MdmEvent;
import com.netwisd.base.mdm.event.MdmPublisher;
import com.netwisd.base.mdm.excel.MdmUserExcel;
import com.netwisd.base.mdm.feign.ZuulClient;
import com.netwisd.base.mdm.mapper.MdmOrgMapper;
import com.netwisd.base.mdm.mapper.MdmRoleMapper;
import com.netwisd.base.mdm.mapper.MdmRoleUserMapper;
import com.netwisd.base.mdm.mapper.MdmUserMapper;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.JacksonUtil;
import com.netwisd.common.msg.rocket.service.BinLogProducerService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 云数网讯 XHL@netwisd.com
 * @Description 用户 功能描述...
 * @date 2021-08-25 10:48:50
 */
@Service
@Slf4j
public class MdmUserServiceImpl extends ServiceImpl<MdmUserMapper, MdmUser> implements MdmUserService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmUserMapper mdmUserMapper;

    @Autowired
    private MdmOrgService mdmOrgService;

    @Autowired
    private MdmOrgMapper mdmOrgMapper;

    @Autowired
    private MdmUserDetailService mdmUserDetailService;

    @Autowired
    private MdmPostUserService mdmPostUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Lazy
    private MdmPostService mdmPostService;

    @Autowired
    @Lazy
    private MdmDutyService mdmDutyService;

    @Autowired
    private MdmPublisher mdmPublisher;

    @Autowired
    private MdmRoleUserMapper mdmRoleUserMapper;

    @Autowired
    private MdmRoleUserService mdmRoleUserService;

    @Autowired
    private MdmRolePostService mdmRolePostService;

    @Autowired
    private MdmRoleResourceService mdmRoleResourceService;

    @Autowired
    private MdmDutyUserService mdmDutyUserService;

    @Autowired
    private NeoService neoService;

    @Autowired
    private MdmMqService mdmMqService;

    @Autowired
    @Lazy
    private MdmRoleMapper mdmRoleMapper;

    @Autowired
    private ZuulClient zuulClient;

    @Autowired
    @Lazy
    private MdmRoleService mdmRoleService;

    @Value("${spring.rocketmq.userTopics}")
    private String userTopics;

    @Autowired
    private BinLogProducerService binLogProducerService;

    /**
     * 全路径拼接串名称
     */
    private static String defaultJoinName = ",";

    /**
     * 默认排序号为1
     */
    private static Integer defaultSort = 1;

    /**
     * 默认密码
     */
    private static String defaultPassword = "zyj@951@ad";

    private static String defaultPassWord = "$2a$10$eDPYUOIpahXGFz18gQ1gK.rjZiDjSXFVcRIb7lRIU2jMFf4vVKThi";

    /**
     * 默认第二列全局排序
     */
    private static Integer globalSortSecond = 0;

    /**
     * 验证码登录默认密码
     */
    private final String loginDefaultPassword = "$2a$10$eKsL5twOYgNePPllW5nze.rXHSTu8qFeW2zMQj.yzKqUT81vaoraa";

    /**
     * 超级管理员账号
     */
    private static String admin = "admin";

    /**
     * 单表简单查询操作
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    public Page list(MdmUserDto mdmUserDto) {
        //首先判断传过来的机构类型是组织还是部门
        MdmOrg mdmOrg = mdmOrgService.getById(mdmUserDto.getParentOrgId());
        if (null == mdmOrg) {
            throw new IncloudException("不存在该机构信息。");
        }
        //判断该机构底下是否有正常的子机构
        LambdaQueryWrapper<MdmOrg> orgWrapper = new LambdaQueryWrapper<>();
        orgWrapper.eq(MdmOrg::getParentId, mdmOrg.getId());
        orgWrapper.eq(MdmOrg::getStatus, StatusEnum.RUNNING.code);
        List<MdmOrg> mdmOrgs = mdmOrgMapper.selectList(orgWrapper);
        int hasKids = YesNo.NO.code;
        if (CollectionUtil.isNotEmpty(mdmOrgs)) {
            hasKids = YesNo.YES.code;
        }
        //如果是管理员 查询在职和离职
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (admin.equals(loginAppUser.getUsername())) {
            mdmUserDto.setStatus(null);
        } else {
            mdmUserDto.setStatus(UserStatusEnum.ONJOB.code);
        }
        mdmUserDto.setHasKids(hasKids);
        Page<MdmUserVo> page = mdmUserMapper.getConditionList(mdmUserDto.getPage(), mdmUserDto);
        log.debug("查询条数:" + page.getTotal());
        return page;
    }

    /**
     * 自定义查询操作
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    public List<MdmUserVo> lists(MdmUserDto mdmUserDto) {
        MdmOrg mdmOrg = mdmOrgService.getById(mdmUserDto.getParentOrgId());
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        //在线学习使用 学员角色的人员
        if (ObjectUtils.isNotEmpty(mdmUserDto.getIsEduStudent()) && mdmUserDto.getIsEduStudent().equals(YesNo.YES.code)) {
            LambdaQueryWrapper<MdmRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
            roleUserWrapper.eq(MdmRoleUser::getRoleCode, "STUDY_STUDENT");
            List<MdmRoleUser> roleUserList = mdmRoleUserMapper.selectList(roleUserWrapper);
            if (CollectionUtil.isNotEmpty(roleUserList)) {
                List<Long> userIdList = roleUserList.stream().map(MdmRoleUser::getUserId).collect(Collectors.toList());
                queryWrapper.in(MdmUser::getId, userIdList);
            }
        }
        //父级机构id
        queryWrapper.like(ObjectUtil.isNotNull(mdmUserDto.getParentOrgId()), MdmUser::getOrgFullId, mdmUserDto.getParentOrgId());
        //人员姓名
        queryWrapper.like(ObjectUtil.isNotNull(mdmUserDto.getUserNameCh()), MdmUser::getUserNameCh, mdmUserDto.getUserNameCh());
        //人员部门
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getParentDeptId()), MdmUser::getParentDeptId, mdmUserDto.getParentDeptId());
        //人员状态
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getStatus()), MdmUser::getStatus, mdmUserDto.getStatus());
        //用工类别
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getEmploymentType()), MdmUser::getEmploymentType, mdmUserDto.getEmploymentType());
        //性别
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getSex()), MdmUser::getSex, mdmUserDto.getSex());
        //户口性质
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getBirthNature()), MdmUser::getBirthNature, mdmUserDto.getBirthNature());
        //民族
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getNation()), MdmUser::getNation, mdmUserDto.getNation());
        //政治面貌
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getPoliticsStatus()), MdmUser::getPoliticsStatus, mdmUserDto.getPoliticsStatus());
        //婚姻状况
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getMarriageStatus()), MdmUser::getMarriageStatus, mdmUserDto.getMarriageStatus());
        //健康状况
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getHealthCondition()), MdmUser::getHealthCondition, mdmUserDto.getHealthCondition());
        queryWrapper.between(ObjectUtil.isNotNull(mdmUserDto.getSUpdateTime()) && ObjectUtil.isNotNull(mdmUserDto.getEUpdateTime()), MdmUser::getUpdateTime, mdmUserDto.getSUpdateTime(), mdmUserDto.getEUpdateTime());
        queryWrapper.eq(MdmUser::getUserClass, ObjectUtil.isNull(mdmUserDto.getUserClass()) || 0 == mdmUserDto.getUserClass() ? UserClassEnum.BIZ.code : mdmUserDto.getUserClass());
        if (null != mdmOrg) { //如果不存在子集就是末级  部门末级只按照sort排序
            LambdaQueryWrapper<MdmOrg> mdmOrgWrapper = new LambdaQueryWrapper<>();
            mdmOrgWrapper.eq(MdmOrg::getParentId, mdmOrg.getId());
            mdmOrgWrapper.eq(MdmOrg::getStatus, StatusEnum.RUNNING.code);
            List<MdmOrg> mdmOrgs = mdmOrgMapper.selectList(mdmOrgWrapper);
            if (CollectionUtil.isNotEmpty(mdmOrgs)) {
                queryWrapper.orderByAsc(MdmUser::getGlobalSort, MdmUser::getGlobalSortSecond);
            } else {
                queryWrapper.orderByAsc(MdmUser::getSort);
            }
        }
        List<MdmUser> list = mdmUserMapper.selectList(queryWrapper);
        List<MdmUserVo> mdmUserList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            log.debug("查询条数:" + list.size());
            mdmUserList = DozerUtils.mapList(dozerMapper, list, MdmUserVo.class);
            //查询出所有的用户和岗位关系信息
            getPostsAndDutys(mdmUserList);
        }
        return mdmUserList;
    }

    /**
     * 获取岗位、和职位信息
     *
     * @param mdmUserList
     */
    public void getPostsAndDutys(List<MdmUserVo> mdmUserList) {
        if (CollectionUtil.isNotEmpty(mdmUserList)) {
            //查询出所有的用户和岗位关系信息
            MdmPostUserDto mdmPostUserDto = new MdmPostUserDto();
            List<MdmPostUserVo> mdmPostUserVos = mdmPostUserService.lists(mdmPostUserDto);
            //查出所有职务
            MdmDutyUserDto mdmDutyUserDto = new MdmDutyUserDto();
            List<MdmDutyUserVo> mdmDutyUserVos = mdmDutyUserService.lists(mdmDutyUserDto);
            for (MdmUserVo mdmUserVo : mdmUserList) {
                Map<Long, List<MdmPostUserVo>> postUsersGroup = mdmPostUserVos.stream().collect(Collectors.groupingBy(MdmPostUserVo::getUserId));
                if (null != postUsersGroup && postUsersGroup.size() > 0) {
                    List<MdmPostUserVo> mdmPostUserList = postUsersGroup.get(mdmUserVo.getId());
                    mdmUserVo.setMdmPosts(mdmPostUserList);
                }
                Map<Long, List<MdmDutyUserVo>> dutyUsersGroup = mdmDutyUserVos.stream().collect(Collectors.groupingBy(MdmDutyUserVo::getUserId));
                if (null != dutyUsersGroup && dutyUsersGroup.size() > 0) {
                    List<MdmDutyUserVo> mdmDutyUserList = dutyUsersGroup.get(mdmUserVo.getId());
                    mdmUserVo.setMdmDutys(mdmDutyUserList);
                }
                //提供对外接口密码设置为空
                mdmUserVo.setPassWord(null);
            }
        }
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public MdmUserVo get(Long id) {
        MdmUser mdmUser = super.getById(id);
        MdmUserVo mdmUserVo = null;
        if (mdmUser != null) {
            mdmUserVo = dozerMapper.map(mdmUser, MdmUserVo.class);
        }
        log.debug("查询成功");
        return mdmUserVo;
    }

    @Override
    public List<MdmUserVo> getByIds(String ids) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        List<String> idList = Arrays.asList(ids.split(","));
        queryWrapper.in(MdmUser::getId, idList);
        List<MdmUser> userList = mdmUserMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper, userList, MdmUserVo.class);
    }

    /**
     * 保存实体
     *
     * @param mdmUserDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(MdmUserDto mdmUserDto) {
        List<MdmUserDto> mdmUserDtos = new ArrayList<>();
        mdmUserDtos.add(mdmUserDto);
        boolean boo = this.saveList(mdmUserDtos);
        if (boo) {
            log.debug("单个用户保存成功。");
        }
        return boo;
    }

    public String handlePw(String aesPw) {
        //解前端加密
        String decrypetPasword = AesUtil.aesDecrypt(aesPw);
        log.debug("解析密码为:" + decrypetPasword);
        //重新非对称加密
        return bCryptPasswordEncoder.encode(decrypetPasword);
    }

    public void checkSingleUserInfoUnique(MdmUser mdmUser) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmUser::getUserName, mdmUser.getUserName());
        List<MdmUser> uMdmUserList = mdmUserMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(uMdmUserList)) {
            throw new IncloudException("存在重复的userName：" + uMdmUserList.get(0).getUserName());
        }
        LambdaQueryWrapper<MdmUser> iQueryWrapper = new LambdaQueryWrapper<>();
        iQueryWrapper.in(MdmUser::getIdCard, mdmUser.getIdCard());
        List<MdmUser> iMdmUserList = mdmUserMapper.selectList(iQueryWrapper);
        if (CollectionUtil.isNotEmpty(iMdmUserList)) {
            throw new IncloudException("输入的人员身份证存在重复信息：" + iMdmUserList.get(0).getIdCard());
        }
//        LambdaQueryWrapper<MdmUser> pQueryWrapper = new LambdaQueryWrapper<>();
//        pQueryWrapper.in(MdmUser::getPhoneNum, mdmUser.getPhoneNum());
//        List<MdmUser> pMdmUserList = mdmUserMapper.selectList(pQueryWrapper);
//        if (CollectionUtil.isNotEmpty(pMdmUserList)) {
//            throw new IncloudException("输入的人员手机号存在重复信息：" + pMdmUserList.get(0).getPhoneNum());
//        }
//        LambdaQueryWrapper<MdmUser> eQueryWrapper = new LambdaQueryWrapper<>();
//        eQueryWrapper.in(MdmUser::getEmail, mdmUser.getEmail());
//        List<MdmUser> eMdmUserList = mdmUserMapper.selectList(eQueryWrapper);
//        if (CollectionUtil.isNotEmpty(eMdmUserList)) {
//            throw new IncloudException("输入的人员邮箱存在重复信息：" + eMdmUserList.get(0).getEmail());
//        }
    }

    public void checkUserInfoUnique(List<MdmUserDto> mdmUserDtoList) {
        //userName
        Map<String, List<MdmUserDto>> uMapGroup = mdmUserDtoList.stream().collect(Collectors.groupingBy(MdmUserDto::getUserName));
        List<String> userNames = mdmUserDtoList.stream().map(MdmUserDto::getUserName).collect(Collectors.toList());
        for (String key : uMapGroup.keySet()) {
            List<MdmUserDto> list = (List) uMapGroup.get(key);
            if (list.size() > 1) {
                throw new IncloudException("输入的人员userName存在重复信息：" + list.get(0).getUserName());
            }
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmUser::getUserName, userNames);
        List<MdmUser> uMdmUserList = mdmUserMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(uMdmUserList)) {
            throw new IncloudException("存在重复的userName：" + uMdmUserList.get(0).getUserName());
        }
        //idCard
        Map<String, List<MdmUserDto>> iMapGroup = mdmUserDtoList.stream().collect(Collectors.groupingBy(MdmUserDto::getIdCard));
        List<String> idCards = mdmUserDtoList.stream().map(MdmUserDto::getIdCard).collect(Collectors.toList());
        for (String key : iMapGroup.keySet()) {
            List<MdmUserDto> list = (List) iMapGroup.get(key);
            if (list.size() > 1) {
                throw new IncloudException("输入的人员身份证存在重复信息：" + list.get(0).getIdCard());
            }
        }
        LambdaQueryWrapper<MdmUser> iQueryWrapper = new LambdaQueryWrapper<>();
        iQueryWrapper.in(MdmUser::getIdCard, idCards);
        List<MdmUser> iMdmUserList = mdmUserMapper.selectList(iQueryWrapper);
        if (CollectionUtil.isNotEmpty(iMdmUserList)) {
            throw new IncloudException("输入的人员身份证存在重复信息：" + iMdmUserList.get(0).getIdCard());
        }
        //phoneNum
//        Map<String, List<MdmUserDto>> pMapGroup = mdmUserDtoList.stream().collect(Collectors.groupingBy(MdmUserDto::getPhoneNum));
//        List<String> ps = mdmUserDtoList.stream().map(MdmUserDto::getPhoneNum).collect(Collectors.toList());
//        for (String key : pMapGroup.keySet()) {
//            List<MdmUserDto> list = (List) pMapGroup.get(key);
//            if (list.size() > 1) {
//                throw new IncloudException("输入的人员手机号存在重复信息：" + list.get(0).getPhoneNum());
//            }
//        }
//        LambdaQueryWrapper<MdmUser> pQueryWrapper = new LambdaQueryWrapper<>();
//        pQueryWrapper.in(MdmUser::getPhoneNum, ps);
//        List<MdmUser> pMdmUserList = mdmUserMapper.selectList(pQueryWrapper);
//        if (CollectionUtil.isNotEmpty(pMdmUserList)) {
//            throw new IncloudException("输入的人员手机号存在重复信息：" + pMdmUserList.get(0).getPhoneNum());
//        }

        //邮箱
//        Map<String, List<MdmUserDto>> eMapGroup = mdmUserDtoList.stream().collect(Collectors.groupingBy(MdmUserDto::getEmail));
//        List<String> es = mdmUserDtoList.stream().map(MdmUserDto::getEmail).collect(Collectors.toList());
//        for (String key : eMapGroup.keySet()) {
//            List<MdmUserDto> list = (List) eMapGroup.get(key);
//            if (list.size() > 1) {
//                throw new IncloudException("输入的人员邮箱存在重复信息：" + list.get(0).getEmail());
//            }
//        }
//        LambdaQueryWrapper<MdmUser> eQueryWrapper = new LambdaQueryWrapper<>();
//        eQueryWrapper.in(MdmUser::getEmail, es);
//        List<MdmUser> eMdmUserList = mdmUserMapper.selectList(eQueryWrapper);
//        if (CollectionUtil.isNotEmpty(eMdmUserList)) {
//            throw new IncloudException("输入的人员邮箱存在重复信息：" + eMdmUserList.get(0).getEmail());
//        }
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean saveList(List<MdmUserDto> mdmUserDtoList) {
        if (CollectionUtil.isNotEmpty(mdmUserDtoList)) {
            //先验证 传过的list中存不存在重复数据 再验证数据库中数据是否重复 （验证用户名、身份证、手机号、邮箱唯一）
            checkUserInfoUnique(mdmUserDtoList);
            List<MdmUser> mdmUserList = new ArrayList<>();
            List<MdmUserDetail> mdmUserDetailList = new ArrayList<>();
            List<MdmPostUser> mdmPostUserList = new ArrayList<>();//如果设置了岗位 需要插入岗位人员关系表
            Integer maxGlobalSort = getMaxGlobalSort();
            for (MdmUserDto mdmUserDto : mdmUserDtoList) {
                //处理父级信息
                MdmUser mdmUser = dozerMapper.map(mdmUserDto, MdmUser.class);
                mdmUser.setParentOrgId(mdmUser.getParentOrgId());
                mdmUser.setParentOrgName(mdmUser.getParentOrgName());
                mdmUser.setParentDeptId(mdmUser.getParentDeptId());
                mdmUser.setParentDeptName(mdmUser.getParentDeptName());
                mdmUser.setOrgFullId(mdmUser.getOrgFullId() + defaultJoinName + mdmUser.getParentDeptId());
                mdmUser.setOrgFullName(mdmUser.getOrgFullName() + defaultJoinName + mdmUser.getParentDeptName());
                if (StringUtils.isNotBlank(mdmUser.getParentDeptFullName())) {
                    mdmUser.setParentDeptFullName(mdmUser.getParentDeptFullName() + defaultJoinName + mdmUser.getParentDeptName());
                } else {
                    mdmUser.setParentDeptFullName(mdmUser.getParentDeptName());
                }
                mdmUser.setParentOrgFullName(mdmUser.getParentOrgFullName());
                mdmUser.setPassWord(defaultPassWord);//默认密码 bCryptPasswordEncoder.encode(defaultPassword)
                mdmUser.setStatus(null != mdmUserDto.getStatus() && 0 != mdmUserDto.getStatus() ? mdmUserDto.getStatus() : YesNo.YES.code);//1在职 2离职
                mdmUser.setGlobalSortSecond(YesNo.NO.code);
                mdmUser.setSort(++maxGlobalSort);
                mdmUser.setGlobalSort(maxGlobalSort);
                mdmUser.setGlobalSortSecond(globalSortSecond);
                mdmUser.setUserClass(ObjectUtil.isNull(mdmUserDto.getUserClass()) ? UserClassEnum.BIZ.code : mdmUserDto.getUserClass());//默认如果不设置是中原建用户
                mdmUserList.add(mdmUser);
                //处理详情
                MdmUserDetail mdmUserDetail;
                if (null == mdmUserDto.getMdmUserDetailDto()) {
                    mdmUserDetail = new MdmUserDetail();
                } else {
                    mdmUserDetail = dozerMapper.map(mdmUserDto.getMdmUserDetailDto(), MdmUserDetail.class);
                }
                mdmUserDetail.setUserId(mdmUserDto.getId());
                mdmUserDetailList.add(mdmUserDetail);
                //如果设置岗位
                if (ObjectUtil.isNotNull(mdmUserDto.getMdmPostUserDto())) {
                    MdmPostUser mdmPostUser = new MdmPostUser();
                    mdmPostUser.setId(IdGenerator.getIdGenerator());
                    mdmPostUser.setOrgFullPostId(mdmUserDto.getMdmPostUserDto().getOrgFullPostId());
                    mdmPostUser.setOrgFullPostName(mdmUserDto.getMdmPostUserDto().getOrgFullPostName());
                    mdmPostUser.setPostId(mdmUserDto.getMdmPostUserDto().getPostId());
                    mdmPostUser.setPostCode(mdmUserDto.getMdmPostUserDto().getPostCode());
                    mdmPostUser.setPostName(mdmUserDto.getMdmPostUserDto().getPostName());
                    mdmPostUser.setUserId(mdmUserDto.getId());
                    mdmPostUser.setIsMaster(YesNo.YES.code);
                    mdmPostUser.setUserName(mdmUserDto.getUserName());
                    mdmPostUser.setUserNameCh(mdmUserDto.getUserNameCh());
                    mdmPostUser.setCreateTime(LocalDateTime.now());
                    mdmPostUserList.add(mdmPostUser);
                    mdmPostService.isRef(mdmPostUser.getPostId());
                }
            }
            //保存人员信息
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                boolean result = super.saveBatch(mdmUserList);
                if (result) {
                    //设置默认角色
                    this.setUserDefaultRole(mdmUserList);
                    //保存neo4j 人员信息
                    log.debug("人员基本信息批量保存成功。");
                    //neoService.saveUserNode(mdmUserList);
                }
            }
            //保存人员详情
            if (CollectionUtil.isNotEmpty(mdmUserDetailList)) {
                boolean result = mdmUserDetailService.saveBatch(mdmUserDetailList);
                if (result) {
                    log.debug("人员详情信息批量保存成功。");
                }
            }
            //保存人员岗位关系 以及维护neo4j
            if (CollectionUtil.isNotEmpty(mdmPostUserList)) {
                boolean result = mdmPostUserService.saveBatch(mdmPostUserList);
                ;
                if (result) {
                    for (MdmPostUser mdmPostUser : mdmPostUserList) {
                        MdmPost mdmPost = new MdmPost();
                        mdmPost.setId(mdmPostUser.getPostId());
                        mdmPost.setPostCode(mdmPostUser.getPostCode());
                        mdmPost.setPostName(mdmPostUser.getPostName());
                        MdmUser mdmUser = new MdmUser();
                        mdmUser.setId(mdmPostUser.getUserId());
                        mdmUser.setUserName(mdmPostUser.getUserName());
                        mdmUser.setUserNameCh(mdmPostUser.getUserNameCh());
                        //neoService.mergeUserPostRel(mdmUser,mdmPost,mdmPostUser);
                    }
                    log.debug("人员岗位关系信息批量保存成功。");
                }
            }
            //发送mq
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                for (MdmUser mdmUser : mdmUserList) {
                    log.debug("用户保存成功-发送增量MQ");
                    MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUser.getParentDeptId());
                    String topics[] = userTopics.split(",");
                    MdmUserVo mdmUserVo = this.getUserToMQ(mdmUser.getId());
                    if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
                        mdmUserVo.setPassWord(null);
                        mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                    }
                    if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
                        mdmUserVo.setPassWord(null);
                        mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                    }
                    //通知其他业务系统缓存
//                    mdmMqService.sendAddRocketMqForCache(MqTagEnum.ADD.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
            }
            //Sunzhenxi
            binLogProducerService.sendBinLogMsg("incloud_base_mdm_user", "INSERT", mdmUserList);
            return true;
        } else {
            log.error("用户保存成功-保存的人员信息为空。");
            return false;
        }
    }

    /**
     * 修改实体
     *
     * @param mdmUserDto
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(MdmUserDto mdmUserDto, boolean isSync) {
        mdmUserDto.setUpdateTime(LocalDateTime.now());
        if (!isSync) {
            mdmUserDto.setPassWord(handlePw(mdmUserDto.getPassWord()));
        }
        MdmUser mdmUser = dozerMapper.map(mdmUserDto, MdmUser.class);
        //修改用户详情
        MdmUserDetailDto mdmUserDetailDto = mdmUserDto.getMdmUserDetailDto();
        if (null != mdmUserDetailDto) {
            mdmUserDetailDto.setUserId(mdmUser.getId());
            MdmUserDetail mdmUserDetail = dozerMapper.map(mdmUserDetailDto, MdmUserDetail.class);
            mdmUserDetail.setUpdateTime(LocalDateTime.now());
            mdmUserDetailService.updateById(mdmUserDetail);
        }
        boolean result = super.updateById(mdmUser);
        if (result) {
            log.debug("用户主表修改成功。");
            MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUser.getParentDeptId());
            String topics[] = userTopics.split(",");
            //转换成Vo发送增量mq
            MdmUserVo mdmUserVo = dozerMapper.map(mdmUserDto, MdmUserVo.class);
            mdmUserVo.setPassWord(null);
            if (null != checkLvTypeOrg.getLvType()) {  //设置机构的级别类型的时候 已经推送过新增
                log.debug("用户修改成功-发送增量MQ");
                mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
            }
            if (null != checkLvTypeOrg.getOaLvType()) {  //设置机构的级别类型的时候 已经推送过新增
                log.debug("用户修改成功-发送增量MQ");
                mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
            }
            //通知其他业务系统缓存
//            mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        //修改人员修改岗位人员关系表
        List<MdmUser> mdmUserList = new ArrayList<>();
        mdmUserList.add(mdmUser);
        mdmPublisher.publish(EventConstants.UpdateUserEvent, mdmUserList);
        //如果设置岗位 同时会再发送消息  --修改人员基本信息时 前端这里不会传值 但是同步股份增量数据时存在岗 所以会推两个消息一个修改人员的 一个修改岗位
        if (ObjectUtil.isNotNull(mdmUserDto.getMdmPostUserDto())) {
            MdmPostUser mdmPostUser = new MdmPostUser();
            mdmPostUser.setId(IdGenerator.getIdGenerator());
            mdmPostUser.setOrgFullPostId(mdmUserDto.getMdmPostUserDto().getOrgFullPostId());
            mdmPostUser.setOrgFullPostName(mdmUserDto.getMdmPostUserDto().getOrgFullPostName());
            mdmPostUser.setPostId(mdmUserDto.getMdmPostUserDto().getPostId());
            mdmPostUser.setPostCode(mdmUserDto.getMdmPostUserDto().getPostCode());
            mdmPostUser.setPostName(mdmUserDto.getMdmPostUserDto().getPostName());
            mdmPostUser.setUserId(mdmUserDto.getId());
            mdmPostUser.setIsMaster(YesNo.YES.code);
            mdmPostUser.setUserName(mdmUserDto.getUserName());
            mdmPostUser.setUserNameCh(mdmUserDto.getUserNameCh());
            mdmPostUser.setCreateTime(LocalDateTime.now());
            mdmPostService.isRef(mdmPostUser.getPostId());
            MdmTransferDto mdmTransferDto = new MdmTransferDto();
            mdmTransferDto.setUserId(mdmUserDto.getId());
            mdmTransferDto.setUserName(mdmUserDto.getUserName());
            mdmTransferDto.setUserNameCh(mdmUserDto.getUserNameCh());
            mdmTransferDto.setPostId(mdmPostUser.getPostId());
            mdmTransferDto.setOrgFullPostId(mdmPostUser.getOrgFullPostId());
            mdmTransferDto.setOrgFullPostName(mdmPostUser.getOrgFullPostName());
            mdmTransferDto.setPostCode(mdmPostUser.getPostCode());
            mdmTransferDto.setPostName(mdmPostUser.getPostName());
            this.setMasterPost(mdmTransferDto);
        }
        //Sunzhenxi
        if (result) {
            binLogProducerService.sendBinLogMsg("incloud_base_mdm_user", "UPDATE", mdmUser);
        }
        return result;
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean delete(String id) {
        if (StringUtils.isNotBlank(id)) {
            List<String> streamStr = Stream.of(id.split(",")).collect(Collectors.toList());
            List<MdmUser> removeUserList = super.listByIds(streamStr);
            boolean result = super.removeByIds(streamStr);
            //同时删除详情
            LambdaQueryWrapper<MdmUserDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmUserDetail::getUserId, streamStr);//数据库是long类型，这里可以传String
            mdmUserDetailService.remove(queryWrapper);
            //删除人员岗位关系
            mdmPostUserService.deleteByUserId(streamStr);
            //删除人员角色关系
            mdmRoleUserService.deleteByUserId(streamStr);
            //删除neo4j节点和关系
            //neoService.delNodeByMid(NeoNodeEnum.USER.code,streamStr);
            //转换成Vo发送增量mq
            log.debug("删除用户成功-发送增量MQ");
            mdmMqService.sendRocketMq(userTopics, MqTagEnum.DEL.code, id);
            //通知其他业务系统缓存
//            mdmMqService.sendAddRocketMqForCache(MqTagEnum.DEL.code,id);
            //Sunzhenxi
            binLogProducerService.sendBinLogMsg("incloud_base_mdm_user", "DELETE", removeUserList);
            return result;
        } else {
            throw new IncloudException("删除人员的id不能为空！");
        }
    }

    @Override
    public List<MdmUser> getByIds(List<String> ids) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmUser::getId, ids);
        List<MdmUser> list = mdmUserMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Boolean checkPassword(MdmUserDto mdmUserDto) {
        if (null == mdmUserDto.getId() && 0L == mdmUserDto.getId()) {
            throw new IncloudException("用户id不能为空！");
        }
        if (StringUtils.isBlank(mdmUserDto.getPassWord())) {
            throw new IncloudException("用户id不能为空！");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("改用户不存在！");
        }
        String decrypetPasword = AesUtil.aesDecrypt(mdmUserDto.getPassWord());
        if (bCryptPasswordEncoder.matches(decrypetPasword, mdmUser.getPassWord())) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updatePassword(MdmUserDto mdmUserDto) {
        if (null == mdmUserDto.getId() && 0L == mdmUserDto.getId()) {
            throw new IncloudException("用户id不能为空！");
        }
        if (StringUtils.isBlank(mdmUserDto.getPassWord())) {
            throw new IncloudException("用户id不能为空！");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("改用户不存在！");
        }
        if (StringUtils.isNotBlank(mdmUserDto.getPhoneNum()) && StringUtils.isNotBlank(mdmUser.getPhoneNum()) && !mdmUser.getPhoneNum().trim().equals(mdmUserDto.getPhoneNum().trim())) {
            throw new IncloudException("验真手机号与本人在系统中手机号不一致，请重新填写验真手机号！");
        }
        String newPw = handlePw(mdmUserDto.getPassWord());
        LambdaUpdateWrapper<MdmUser> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(MdmUser::getId, mdmUserDto.getId());
        queryWrapper.set(MdmUser::getPassWord, newPw);
        boolean boo = this.update(queryWrapper);
        if (boo) {
            //清楚token
            zuulClient.sysLogout();
            log.debug(mdmUser.getUserName() + ".密码修改成功！");
        } else {
            log.debug(mdmUser.getUserName() + ".密码修改失败！");
        }
        return boo;
    }

    /**
     * 修改头像
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    @Transactional
    public Boolean updatePhoto(MdmUserDto mdmUserDto) {
        if (null == mdmUserDto.getId() && 0L == mdmUserDto.getId()) {
            throw new IncloudException("用户id不能为空！");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("该用户不存在！");
        }
        //修改头像
        LambdaUpdateWrapper<MdmUser> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(MdmUser::getId, mdmUserDto.getId());
        queryWrapper.set(StringUtils.isNotBlank(mdmUserDto.getPhotoFileId()), MdmUser::getPhotoFileId, mdmUserDto.getPhotoFileId());
        boolean boo = this.update(queryWrapper);
        if (boo) {
            log.debug(mdmUser.getUserName() + ".头像修改成功！");
        } else {
            log.debug(mdmUser.getUserName() + ".头像修改失败！");
        }
        return boo;
    }

    /**
     * 按照实例发送mq
     *
     * @param mdmUser
     */
    @SneakyThrows
    public void sendMqForEntity(MdmUser mdmUser) {
        MdmUserVo mdmUserVo = this.getUserToMQ(mdmUser.getId());
        String topics[] = userTopics.split(",");
        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUserVo.getParentDeptId());
        if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
            mdmUserVo.setPassWord(null);
            mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
            mdmUserVo.setPassWord(null);
            mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        //通知其他业务系统缓存
//        mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
    }

    /**
     * 修改邮箱
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    @SneakyThrows
    @Transactional
    public Boolean updateEmail(MdmUserDto mdmUserDto) {
        if (null == mdmUserDto.getId() && 0L == mdmUserDto.getId()) {
            throw new IncloudException("用户id不能为空！");
        }
        if (StringUtils.isBlank(mdmUserDto.getEmail())) {
            throw new IncloudException("邮箱不能为空！");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("该用户不存在！");
        }
        //修改邮箱
        LambdaUpdateWrapper<MdmUser> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(MdmUser::getId, mdmUserDto.getId());
        queryWrapper.set(MdmUser::getEmail, mdmUserDto.getEmail());
        boolean boo = this.update(queryWrapper);
        if (boo) {
            log.debug(mdmUser.getUserName() + ".邮箱修改成功！");
            mdmUser.setEmail(mdmUserDto.getEmail());
            this.sendMqForEntity(mdmUser);
        } else {
            log.debug(mdmUser.getUserName() + ".邮箱修改失败！");
        }
        return boo;
    }

    /**
     * 修改手机号
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    @SneakyThrows
    @Transactional
    public Boolean updatePhoneNum(MdmUserDto mdmUserDto) {
        if (null == mdmUserDto.getId() && 0L == mdmUserDto.getId()) {
            throw new IncloudException("用户id不能为空！");
        }
        if (StringUtils.isBlank(mdmUserDto.getPhoneNum())) {
            throw new IncloudException("手机号不能为空！");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("该用户不存在！");
        }

        LambdaUpdateWrapper<MdmUser> queryWrapper = new LambdaUpdateWrapper<>();
        //验证手机号是否重复
        queryWrapper.eq(MdmUser::getPhoneNum, mdmUserDto.getPhoneNum());
        queryWrapper.eq(MdmUser::getStatus, StatusEnum.RUNNING.code);
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(queryWrapper);
        if (mdmUsers.size() > 0) {
            throw new IncloudException("手机号已有人使用，请更换手机号再进行修改！");
        }
        //修改手机号
        queryWrapper.clear();
        queryWrapper.eq(MdmUser::getId, mdmUserDto.getId());
        queryWrapper.set(MdmUser::getPhoneNum, mdmUserDto.getPhoneNum());
        boolean boo = this.update(queryWrapper);
        if (boo) {
            //转换成Vo发送增量mq
            mdmUser.setPhoneNum(mdmUserDto.getPhoneNum());
            this.sendMqForEntity(mdmUser);
            log.debug(mdmUser.getUserName() + ".手机号修改成功！");
        } else {
            log.debug(mdmUser.getUserName() + ".手机号修改失败！");
        }
        return boo;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean setMasterPost(MdmTransferDto mdmTransferDto) {
        //如果没有选中岗位则把现在所有的岗位都取消
        if (null == mdmTransferDto.getPostId() || 0L == mdmTransferDto.getPostId()) {
            mdmPostUserService.delByUserIdAndIsMaster(mdmTransferDto.getUserId(), YesNo.YES.code);
        } else {
            //判断是否存在主岗信息
            MdmPostUser mdmPostUser = mdmPostUserService.getMaster(mdmTransferDto.getUserId());
            MdmUser mdmUser = super.getById(mdmTransferDto.getUserId());
            if (null == mdmUser) {
                throw new IncloudException("该用户已经不存在！");
            }
            MdmPost mdmPost = mdmPostService.getById(mdmTransferDto.getPostId());
            if (null == mdmPost) {
                throw new IncloudException("该岗位已经不存在！");
            }
            //如果不存在添加 如果存在修改
            if (ObjectUtil.isNull(mdmPostUser)) {
                MdmPostUser _mdmPostUser = new MdmPostUser();
                _mdmPostUser.setId(IdGenerator.getIdGenerator());
                _mdmPostUser.setOrgFullPostId(mdmTransferDto.getOrgFullPostId());
                _mdmPostUser.setOrgFullPostName(mdmTransferDto.getOrgFullPostName());
                _mdmPostUser.setPostId(mdmTransferDto.getPostId());
                _mdmPostUser.setPostCode(mdmTransferDto.getPostCode());
                _mdmPostUser.setPostName(mdmTransferDto.getPostName());
                _mdmPostUser.setUserId(mdmTransferDto.getUserId());
                _mdmPostUser.setUserName(mdmTransferDto.getUserName());
                _mdmPostUser.setUserNameCh(mdmTransferDto.getUserNameCh());
                _mdmPostUser.setIsMaster(YesNo.YES.code);
                _mdmPostUser.setCreateTime(LocalDateTime.now());
                mdmPostUserService.save(_mdmPostUser);
                mdmPostService.isRef(mdmTransferDto.getPostId());
                //neoService.mergeUserPostRel(mdmUser,mdmPost,_mdmPostUser);//维护neo4j关系
            } else {
                mdmPostUser.setPostCode(mdmTransferDto.getPostCode());
                mdmPostUser.setPostName(mdmTransferDto.getPostName());
                mdmPostUser.setPostId(mdmTransferDto.getPostId());
                mdmPostUser.setOrgFullPostId(mdmTransferDto.getOrgFullPostId());
                mdmPostUser.setUpdateTime(LocalDateTime.now());
                mdmPostUserService.updateById(mdmPostUser);
                mdmPostService.isRef(mdmPostUser.getPostId());
                //neoService.delUserPostRel(mdmUser,YesNo.YES.code);//先删关系
                //neoService.mergeUserPostRel(mdmUser,mdmPost,mdmPostUser);//重新维护关系
            }
        }
//        log.debug("用户调兼岗成功-发送增量MQ");
//        MdmUserVo mdmUserVo = this.getUserToMQ(mdmTransferDto.getUserId());
//        String topics[] = userTopics.split(",");
//        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUserVo.getParentDeptId());
//        if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
//            mdmUserVo.setPassWord(null);
//            mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
//        }
//        if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
//            mdmUserVo.setPassWord(null);
//            mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
//        }
        return true;
    }


    public Boolean transferSetMasterPost(MdmUserDto mdmUserDto) {
        if (ObjectUtil.isNotNull(mdmUserDto.getMdmPostUserDto())) {
            checkTransferPost(mdmUserDto.getMdmPostUserDto());
            MdmPostUserDto mdmPostUserDto = mdmUserDto.getMdmPostUserDto();
            //判断是否存在主岗信息
            MdmPostUser mdmPostUser = mdmPostUserService.getMaster(mdmUserDto.getId());
            MdmPostVo mdmPostVo = mdmPostService.get(mdmPostUserDto.getPostId());
            if (null == mdmPostVo) {
                throw new IncloudException("不存在该岗位信息。");
            }
            //如果不存在添加 如果存在修改
            boolean result;
            if (ObjectUtil.isNull(mdmPostUser)) {
                MdmPostUser _mdmPostUser = new MdmPostUser();
                _mdmPostUser.setOrgFullPostId(mdmPostVo.getOrgFullId());
                _mdmPostUser.setOrgFullPostName(mdmPostVo.getOrgFullName());
                _mdmPostUser.setPostId(mdmPostUserDto.getPostId());
                _mdmPostUser.setPostCode(mdmPostUserDto.getPostCode());
                _mdmPostUser.setPostName(mdmPostUserDto.getPostName());
                _mdmPostUser.setUserId(mdmUserDto.getId());
                _mdmPostUser.setUserName(mdmUserDto.getUserName());
                _mdmPostUser.setUserNameCh(mdmUserDto.getUserNameCh());
                _mdmPostUser.setIsMaster(YesNo.YES.code);
                _mdmPostUser.setCreateTime(LocalDateTime.now());
                result = mdmPostUserService.save(_mdmPostUser);
            } else {
                mdmPostUser.setPostId(mdmPostUserDto.getPostId());
                mdmPostUser.setPostCode(mdmPostUserDto.getPostCode());
                mdmPostUser.setPostName(mdmPostUserDto.getPostName());
                mdmPostUser.setOrgFullPostId(mdmPostUserDto.getOrgFullPostId());
                mdmPostUser.setOrgFullPostName(mdmPostUserDto.getOrgFullPostName());
                mdmPostUser.setUpdateTime(LocalDateTime.now());
                mdmPostUser.setUserId(mdmUserDto.getId());
                mdmPostUser.setUserName(mdmUserDto.getUserName());
                mdmPostUser.setUserNameCh(mdmUserDto.getUserNameCh());
                mdmPostUser.setIsMaster(YesNo.YES.code);
                result = mdmPostUserService.updateById(mdmPostUser);
            }
            return result;
        } else {
            //部门调动 如果没有设置岗 则删除
            mdmPostUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.YES.code);
        }
        return false;
    }

    @SneakyThrows
    @Transactional
    public Boolean transferSetMasterDuty(MdmUserDto mdmUserDto) {
        if (ObjectUtil.isNotNull(mdmUserDto.getMdmDutyUserDto())) {
            checkTransferDuty(mdmUserDto.getMdmDutyUserDto());
            MdmDutyUserDto mdmDutyUserDto = mdmUserDto.getMdmDutyUserDto();
            //判断是否存在主职信息
            MdmDutyUser mdmDutyUser = mdmDutyUserService.getMaster(mdmUserDto.getId());
            MdmDutyVo mdmDutyVo = mdmDutyService.get(mdmDutyUserDto.getDutyId());
            if (null == mdmDutyVo) {
                throw new IncloudException("不存在该职位信息。");
            }
            //如果不存在添加 如果存在修改
            boolean result;
            if (ObjectUtil.isNull(mdmDutyUser)) {
                MdmDutyUser _mdmDutyUser = new MdmDutyUser();
                _mdmDutyUser.setOrgFullDutyId(mdmDutyVo.getOrgFullId());
                _mdmDutyUser.setOrgFullDutyName(mdmDutyVo.getOrgFullName());
                _mdmDutyUser.setDutyId(mdmDutyVo.getId());
                _mdmDutyUser.setDutyCode(mdmDutyVo.getDutyCode());
                _mdmDutyUser.setDutyName(mdmDutyVo.getDutyName());
                _mdmDutyUser.setUserId(mdmUserDto.getId());
                _mdmDutyUser.setUserName(mdmUserDto.getUserName());
                _mdmDutyUser.setUserNameCh(mdmUserDto.getUserNameCh());
                _mdmDutyUser.setIsMaster(YesNo.YES.code);
                _mdmDutyUser.setCreateTime(LocalDateTime.now());
                result = mdmDutyUserService.save(_mdmDutyUser);
            } else {
                mdmDutyUser.setDutyId(mdmDutyVo.getId());
                mdmDutyUser.setDutyCode(mdmDutyVo.getDutyCode());
                mdmDutyUser.setDutyName(mdmDutyVo.getDutyName());
                mdmDutyUser.setOrgFullDutyId(mdmDutyVo.getOrgFullId());
                mdmDutyUser.setOrgFullDutyName(mdmDutyVo.getOrgFullName());
                mdmDutyUser.setUpdateTime(LocalDateTime.now());
                mdmDutyUser.setUserId(mdmUserDto.getId());
                mdmDutyUser.setUserName(mdmUserDto.getUserName());
                mdmDutyUser.setUserNameCh(mdmUserDto.getUserNameCh());
                mdmDutyUser.setIsMaster(YesNo.YES.code);
                result = mdmDutyUserService.updateById(mdmDutyUser);
            }
            return result;
        } else {
            //部门调动 如果没有设置职务 则删除
            mdmDutyUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.YES.code);
        }
        return false;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean transferDetp(MdmUserDto mdmUserDto) {
        MdmUser mdmUser = super.getById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("该用户信息不存在！");
        }
        //根据userId 修改用户表对应的部门信息
        mdmUser.setParentDeptId(mdmUserDto.getParentDeptId());
        mdmUser.setParentDeptName(mdmUserDto.getParentDeptName());
        mdmUser.setOrgFullId(mdmUserDto.getOrgFullId() + defaultJoinName + mdmUserDto.getParentDeptId());
        mdmUser.setOrgFullName(mdmUserDto.getOrgFullName() + defaultJoinName + mdmUserDto.getParentDeptName());
        if (StringUtils.isBlank(mdmUserDto.getParentDeptFullName())) {
            mdmUser.setParentDeptFullName(mdmUserDto.getParentDeptName());
        } else {
            mdmUser.setParentDeptFullName(mdmUserDto.getParentDeptFullName() + defaultJoinName + mdmUserDto.getParentDeptName());
        }
        mdmUser.setParentOrgFullName(mdmUserDto.getParentOrgFullName());
        boolean result = super.updateById(mdmUser);
        if (result) {
            log.debug("调动部门 - 用户主表修改成功。");
        }
        //如果没有选主岗 则 只调整机构部门  如果选了主岗 存在主岗则修改 不存在则添加
        MdmUserDto _mdmUserDto = dozerMapper.map(mdmUser, MdmUserDto.class);
        _mdmUserDto.setMdmPostUserDto(mdmUserDto.getMdmPostUserDto());
        _mdmUserDto.setMdmDutyUserDto(mdmUserDto.getMdmDutyUserDto());
        transferSetMasterPost(_mdmUserDto);
        //如果没有选主职 则 只调整机构部门  如果选了主职 存在主职则修改 不存在则添加
        transferSetMasterDuty(_mdmUserDto);
        //查询出用户详情 推送mq
        log.debug("用户调部门成功-发送增量MQ");
        MdmUserVo mdmUserVo = this.getUserToMQ(mdmUser.getId());
        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUserVo.getParentDeptId());
        String topics[] = userTopics.split(",");
        if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
            mdmUserVo.setPassWord(null);
            mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
            mdmUserVo.setPassWord(null);
            mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        //通知其他业务系统缓存
//        mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        return result;
    }

    public void checkTransferDuty(MdmDutyUserDto mdmDutyUserDto) {
        if (null == mdmDutyUserDto.getDutyId()) {
            throw new IncloudException("岗位ID不能为空！");
        } else if (StringUtils.isBlank(mdmDutyUserDto.getDutyCode())) {
            throw new IncloudException("岗位CODE不能为空！");
        } else if (StringUtils.isBlank(mdmDutyUserDto.getDutyName())) {
            throw new IncloudException("岗位名称不能为空！");
        }
    }

    public void checkTransferPost(MdmPostUserDto mdmPostUserDto) {
        if (null == mdmPostUserDto.getPostId()) {
            throw new IncloudException("岗位ID不能为空！");
        } else if (StringUtils.isBlank(mdmPostUserDto.getPostCode())) {
            throw new IncloudException("岗位CODE不能为空！");
        } else if (StringUtils.isBlank(mdmPostUserDto.getPostName())) {
            throw new IncloudException("岗位名称不能为空！");
        }
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean transferOrg(MdmUserDto mdmUserDto) {
        MdmUser mdmUser = super.getById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("该用户信息不存在！");
        }
        //根据userId 修改用户表对应的组织、部门信息
        mdmUser.setParentOrgId(mdmUserDto.getParentOrgId());
        mdmUser.setParentOrgName(mdmUserDto.getParentOrgName());
        mdmUser.setParentDeptId(mdmUserDto.getParentDeptId());
        mdmUser.setParentDeptName(mdmUserDto.getParentDeptName());
        mdmUser.setOrgFullId(mdmUserDto.getOrgFullId() + defaultJoinName + mdmUserDto.getParentDeptId());
        mdmUser.setOrgFullName(mdmUserDto.getOrgFullName() + defaultJoinName + mdmUserDto.getParentDeptName());
        if (StringUtils.isBlank(mdmUserDto.getParentDeptFullName())) {
            mdmUser.setParentDeptFullName(mdmUserDto.getParentDeptName());
        } else {
            mdmUser.setParentDeptFullName(mdmUserDto.getParentDeptFullName() + defaultJoinName + mdmUserDto.getParentDeptName());
        }
        mdmUser.setParentOrgFullName(mdmUserDto.getParentOrgFullName());
        boolean result = super.updateById(mdmUser);
        if (result) {
            log.debug("调动部门 - 用户主表修改成功。");
        }
        //如果没有选主岗 则 只调整机构部门  如果选了主岗 存在主岗则修改 不存在则添加
        MdmUserDto _mdmUserDto = dozerMapper.map(mdmUser, MdmUserDto.class);
        _mdmUserDto.setMdmPostUserDto(mdmUserDto.getMdmPostUserDto());
        _mdmUserDto.setMdmDutyUserDto(mdmUserDto.getMdmDutyUserDto());
        transferSetMasterPost(_mdmUserDto);
        //如果没有选主职 则只调整机构部门  如果选了主职 存在主职则修改 不存在则添加
        transferSetMasterDuty(_mdmUserDto);
        log.debug("用户调机构成功-发送增量MQ");
        MdmUserVo mdmUserVo = this.getUserToMQ(mdmUser.getId());
        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUserVo.getParentDeptId());
        String topics[] = userTopics.split(",");
        if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
            mdmUserVo.setPassWord(null);
            mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
            mdmUserVo.setPassWord(null);
            mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        //通知其他业务系统缓存
//        mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        return result;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean setAndPost(List<MdmTransferDto> mdmTransferDtoList) {
        //根据userId 删除原先的全部兼岗信息
        mdmPostUserService.delByUserIdAndIsMaster(mdmTransferDtoList.get(0).getUserId(), YesNo.NO.code);
        MdmUser mdmUser = super.getById(mdmTransferDtoList.get(0).getUserId());
        if (null == mdmUser) {
            throw new IncloudException("该用户已经不存在！");
        }
        //先neo4j删关系
        //neoService.delUserPostRel(mdmUser,YesNo.NO.code);
        if (CollectionUtil.isNotEmpty(mdmTransferDtoList)) {
            List<MdmPostUser> resultList = new ArrayList<>();
            for (MdmTransferDto mdmTransferDto : mdmTransferDtoList) {
                MdmPostUser _mdmPostUser = new MdmPostUser();
                _mdmPostUser.setId(IdGenerator.getIdGenerator());
                _mdmPostUser.setOrgFullPostId(mdmTransferDto.getOrgFullPostId());
                _mdmPostUser.setOrgFullPostName(mdmTransferDto.getOrgFullPostName());
                _mdmPostUser.setPostId(mdmTransferDto.getPostId());
                _mdmPostUser.setPostCode(mdmTransferDto.getPostCode());
                _mdmPostUser.setPostName(mdmTransferDto.getPostName());
                _mdmPostUser.setUserId(mdmTransferDto.getUserId());
                _mdmPostUser.setUserName(mdmTransferDto.getUserName());
                _mdmPostUser.setUserNameCh(mdmTransferDto.getUserNameCh());
                _mdmPostUser.setIsMaster(YesNo.NO.code);
                _mdmPostUser.setCreateTime(LocalDateTime.now());
                resultList.add(_mdmPostUser);
                mdmPostService.isRef(_mdmPostUser.getPostId());
                MdmPost mdmPost = mdmPostService.getById(mdmTransferDto.getPostId());
                if (null == mdmPost) {
                    throw new IncloudException("该岗位已经不存在！");
                }
                //neoService.mergeUserPostRel(mdmUser,mdmPost,_mdmPostUser);//重新维护关系
            }
            if (CollectionUtil.isNotEmpty(resultList)) {
                mdmPostUserService.saveBatch(resultList);
                log.debug("用户调主岗成功-发送增量MQ");
                MdmUserVo mdmUserVo = this.getUserToMQ(mdmUser.getId());
                MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUserVo.getParentDeptId());
                String topics[] = userTopics.split(",");
                if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
                    mdmUserVo.setPassWord(null);
                    mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
                if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
                    mdmUserVo.setPassWord(null);
                    mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
            }
            log.debug("设置兼岗 - 兼岗信息修改成功。");
            return true;
        }
        throw new IncloudException("兼岗数据为空！");
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean orgTransfer(List<MdmOrg> mdmOrgList) {
        if (CollectionUtil.isNotEmpty(mdmOrgList)) {
            //分组获取 只要部门信息
            List<MdmOrg> deptOrgList = mdmOrgList.stream().filter(d -> d.getOrgType() == OrgTypeEnum.DEPT.code).collect(Collectors.toList());
            //查询部门下所有人
            if (CollectionUtil.isNotEmpty(deptOrgList)) {
                LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
                List<Long> deptIds = deptOrgList.stream().map(MdmOrg::getId).collect(Collectors.toList());
                queryWrapper.in(MdmUser::getParentDeptId, deptIds);
                List<MdmUser> mdmUserList = mdmUserMapper.selectList(queryWrapper);
                if (CollectionUtil.isNotEmpty(mdmUserList)) {
                    //循环部门 修改所有人员信息
                    Map<Long, List<MdmOrg>> mapGroup = deptOrgList.stream().collect(Collectors.groupingBy(MdmOrg::getId));
                    for (MdmUser mdmUser : mdmUserList) {
                        List<MdmOrg> deptList = mapGroup.get(mdmUser.getParentDeptId());
                        MdmOrg mdmOrg = deptList.get(0);
                        mdmUser.setParentOrgId(mdmOrg.getParentOrgId());
                        mdmUser.setParentOrgName(mdmOrg.getParentOrgName());
                        mdmUser.setParentDeptName(mdmOrg.getOrgName());//deptId 不会变 不设置了
                        if (StringUtils.isNotBlank(mdmUser.getParentDeptFullName())) {
                            mdmUser.setParentDeptFullName(mdmUser.getParentDeptFullName() + defaultJoinName + mdmUser.getParentDeptName());
                        } else {
                            mdmUser.setParentDeptFullName(mdmUser.getParentDeptName());
                        }
                        mdmUser.setParentOrgFullName(mdmOrg.getParentOrgFullName());
                        mdmUser.setOrgFullId(mdmOrg.getOrgFullId() + defaultJoinName + mdmOrg.getId());
                        mdmUser.setOrgFullName(mdmOrg.getOrgFullName() + defaultJoinName + mdmOrg.getOrgName());
                    }
                    if (CollectionUtil.isNotEmpty(mdmUserList)) {
                        super.updateBatchById(mdmUserList);
                        for (MdmUser mdmUser : mdmUserList) {
                            MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUser.getParentDeptId());
                            String topics[] = userTopics.split(",");
                            MdmUserVo mdmUserVo = dozerMapper.map(mdmUser, MdmUserVo.class);
                            mdmUserVo.setPassWord(null);
                            if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
                                mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                            }
                            if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
                                mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                            }
                            //通知其他业务系统缓存
//                            mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                        }
                    }
                }
            }
        }
        return true;
    }

    @EventListener(MdmEvent.class)
    @Async("incloudExecutor")
    @SneakyThrows
    public void onApplicationEvent(MdmEvent mdmEvent) {
        Map<String, List> data = mdmEvent.getListMap();
        Map<String, IDto> dtoData = mdmEvent.getDtoMap();
        //机构划转
        if (data.containsKey(EventConstants.UpdateParentOrgEvent)) {
            List<MdmOrg> mdmOrgList = (List) data.get(EventConstants.UpdateParentOrgEvent);
            log.info("机构划转事件参数：{}", JacksonUtil.toJSONString(mdmOrgList));
            this.orgTransfer(mdmOrgList);
        }
        //组织修改名称
        if (data.containsKey(EventConstants.UpdateNameOrgEvent)) {
            List<MdmOrg> mdmOrgList = (List) data.get(EventConstants.UpdateNameOrgEvent);
            log.info("修改机构名称事件参数：{}", JacksonUtil.toJSONString(mdmOrgList));
            this.orgTransfer(mdmOrgList);
        }
        //组织拆分
        if (dtoData.containsKey(EventConstants.BrokenOrgEvent)) {
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.BrokenOrgEvent);
            List<MdmUserDto> mdmUserDtos = mdmOrgStatusDto.getMdmUserDtos();
            log.info("组织拆分事件参数：{}", JacksonUtil.toJSONString(mdmUserDtos));
            this.orgSplit(mdmUserDtos);
        }

        //组织撤销
        if (dtoData.containsKey(EventConstants.CanceledOrgEvent)) {
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.CanceledOrgEvent);
            List<MdmUserDto> mdmUserDtos = mdmOrgStatusDto.getMdmUserDtos();
            log.info("组织撤销事件参数：{}", JacksonUtil.toJSONString(mdmUserDtos));
            this.orgSplit(mdmUserDtos);
        }

        //人员修改 修改角色人员关系表 和 人员岗位关系表
        if (dtoData.containsKey(EventConstants.UpdateUserEvent)) {
            log.info("人员修改 修改角色人员关系表!");
            List<MdmUser> mdmUserList = (List) dtoData.get(EventConstants.UpdateUserEvent);
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                MdmUser mdmUser = mdmUserList.get(0);
                //修改角色人员
                mdmRoleUserService.updateRoleUserInfoByUserId(mdmUser);
                //修改人员岗位
                mdmPostUserService.updatePostUserInfoByUserId(mdmUser);
            }
        }
        //修改角色人员、角色岗位、角色资源
        if (dtoData.containsKey(EventConstants.UpdateRoleEvent)) {
            log.info("人员修改 修改角色人员关系表!");
            List<MdmRole> mdmRoleList = (List) dtoData.get(EventConstants.UpdateRoleEvent);
            if (CollectionUtil.isNotEmpty(mdmRoleList)) {
                MdmRole mdmRole = mdmRoleList.get(0);
                //角色人员
                mdmRoleUserService.updateRoleUserInfoByRoleId(mdmRole);
                //角色岗位
                mdmRolePostService.updateRolePostInfoByRoleId(mdmRole);
                //角色资源
                mdmRoleResourceService.updateRoleResInfoByRoleId(mdmRole);
            }
        }
        //岗位修改 修改角色岗位关系表
        if (dtoData.containsKey(EventConstants.UpdatePostEvent)) {
            log.info("人员修改 修改角色人员关系表!");
            List<MdmPost> mdmPostList = (List) dtoData.get(EventConstants.UpdatePostEvent);
            if (CollectionUtil.isNotEmpty(mdmPostList)) {
                MdmPost mdmPost = mdmPostList.get(0);
                mdmRolePostService.updateRolePostInfoByPostId(mdmPost);
            }
        }
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean orgSplit(List<MdmUserDto> mdmUserDtoList) {
        if (CollectionUtil.isNotEmpty(mdmUserDtoList)) {
            List<MdmUser> mdmUserList = DozerUtils.mapList(dozerMapper, mdmUserDtoList, MdmUser.class);
            //循环部门 修改所有人员信息
            for (MdmUser mdmUser : mdmUserList) {
                mdmUser.setParentOrgId(mdmUser.getParentOrgId());
                mdmUser.setParentOrgName(mdmUser.getParentOrgName());
                mdmUser.setParentDeptId(mdmUser.getParentDeptId());
                mdmUser.setParentDeptName(mdmUser.getParentDeptName());

                mdmUser.setParentOrgFullName(mdmUser.getParentOrgFullName());
                if (StringUtils.isNotBlank(mdmUser.getParentDeptFullName())) {
                    mdmUser.setParentDeptFullName(mdmUser.getParentDeptFullName() + defaultJoinName + mdmUser.getParentDeptName());
                } else {
                    mdmUser.setParentDeptFullName(mdmUser.getParentDeptName());
                }
                mdmUser.setOrgFullId(mdmUser.getOrgFullId() + defaultJoinName + mdmUser.getParentDeptId());
                mdmUser.setOrgFullName(mdmUser.getOrgFullName() + defaultJoinName + mdmUser.getParentDeptName());
            }
            boolean result = super.updateBatchById(mdmUserList);
            if (result) {
                log.debug("机构拆分/机构撤销 - 用户信息修改成功。");
            }
            //处理岗位、职位
            for (MdmUserDto mdmUserDto : mdmUserDtoList) {
                //主岗
                MdmPostUserDto mdmPostUserDto = mdmUserDto.getMdmPostUserDto();
                if (null != mdmPostUserDto) {
                    MdmTransferDto mdmTransferDto = new MdmTransferDto();
                    mdmTransferDto.setUserId(mdmUserDto.getId());
                    mdmTransferDto.setUserName(mdmUserDto.getUserName());
                    mdmTransferDto.setUserNameCh(mdmUserDto.getUserNameCh());
                    mdmTransferDto.setPostId(mdmPostUserDto.getId());
                    mdmTransferDto.setOrgFullPostId(mdmPostUserDto.getOrgFullPostId());
                    mdmTransferDto.setOrgFullPostName(mdmPostUserDto.getOrgFullPostName());
                    mdmTransferDto.setPostCode(mdmPostUserDto.getPostCode());
                    mdmTransferDto.setPostName(mdmPostUserDto.getPostName());
                    this.setMasterPost(mdmTransferDto);
                } else {
                    //如果设置的为空 要删掉原来的主岗信息
                    mdmPostUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.YES.code);
                }
                //主职
                MdmDutyUserDto mdmDutyUserDto = mdmUserDto.getMdmDutyUserDto();
                if (null != mdmDutyUserDto) {
                    Long dutyId = mdmDutyUserDto.getId();
                    mdmUserDto.setDutyIds(String.valueOf(dutyId));
                    this.setMasterDuty(mdmUserDto);
                } else {
                    //如果设置的为空 要删掉原来的主职信息
                    mdmDutyUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.YES.code);
                }
            }
            //组织拆分 处理完人员和人员岗位后 重新查一遍关系推到mq
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                log.debug("组织拆分-修改用户-发送增量MQ");
                for (MdmUser mdmUser : mdmUserList) {
                    MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUser.getParentDeptId());
                    String topics[] = userTopics.split(",");
                    MdmUserVo mdmUserVo = this.getUserToMQ(mdmUser.getId());
                    mdmUserVo.setPassWord(null);
                    if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
                        mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                    }
                    if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
                        mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                    }
                    //通知其他业务系统缓存
//                    mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
            }
            return result;
        }
        return false;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean sortForDept(Long sourceId, Long targetId, String index) {
        MdmUser source = this.getById(sourceId);
        if (ObjectUtil.isEmpty(source)) {
            throw new IncloudException("根据传入的sourceId查询不到相应的人员信息！");
        }
        List<MdmUser> list = new ArrayList<>();
        if (StrUtil.isNotEmpty(index)) {
            if (index.equals(SortEnum.FIRST.value)) {
                List<MdmUser> otherSortList = getListByParent(source.getParentDeptId(), true, source.getId());
                source.setSort(defaultSort);
                list = sortOtherList(otherSortList, defaultSort);
            } else if (index.equals(SortEnum.LAST.value)) {
                MdmUser lastOne = getOneByParent(source.getParentDeptId(), false, null);
                source.setSort(lastOne.getSort() + 1);
            }
        } else {
            MdmUser target = this.getById(targetId);
            if (ObjectUtil.isEmpty(target)) {
                throw new IncloudException("根据传入的targetId查询不到相应的实体！");
            }
            Integer targetIndex = target.getSort();
            List<MdmUser> otherSortList = getListByParent(source.getParentDeptId(), true, source.getId());
            List<MdmUser> collect = otherSortList.stream().filter(mdmOrg -> mdmOrg.getSort() >= targetIndex).collect(Collectors.toList());
            list = sortOtherList(collect, targetIndex);
            source.setSort(targetIndex);
        }
        //把自己也加进去；
        MdmUser mdmUser = dozerMapper.map(source, MdmUser.class);
        list.add(mdmUser);
        boolean result = updateBatchById(list);
        if (result) {
            this.sendMqForUsers(list);
            log.info("批量排序成功！");
        }
        return true;
    }

    /**
     * 根据用户机构判断是否应该推送mq
     *
     * @param userList
     */
    @SneakyThrows
    public void sendMqForUsers(List<MdmUser> userList) {
        if (CollectionUtil.isNotEmpty(userList)) {
            for (MdmUser _mdmUser : userList) {
                MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(_mdmUser.getParentDeptId());
                String topics[] = userTopics.split(",");
                MdmUserVo mdmUserVo = this.getUserToMQ(_mdmUser.getId());
                mdmUserVo.setPassWord(null);
                if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
                    mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
                if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
                    mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
                //通知其他业务系统缓存
//                mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
            }
        }
    }

    /**
     * 按照上级部门信息查询部门下所有的人员信息
     *
     * @param parentId
     * @param isAsc
     * @param exclusiveId
     * @return
     */
    public List<MdmUser> getListByParent(Long parentId, Boolean isAsc, Long exclusiveId) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getParentDeptId, parentId);
        if (ObjectUtil.isNotEmpty(exclusiveId)) {
            queryWrapper.ne(MdmUser::getId, exclusiveId);
        }
        if (isAsc) {
            queryWrapper.orderByAsc(MdmUser::getSort);
        } else {
            queryWrapper.orderByDesc(MdmUser::getSort);
        }
        List<MdmUser> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 按给定集合和排序index做排序
     *
     * @param otherSortList
     * @param index
     * @return
     */
    List<MdmUser> sortOtherList(List<MdmUser> otherSortList, Integer index) {
        List<MdmUser> list = new ArrayList<>();
        for (MdmUser mdmUser : otherSortList) {
            mdmUser.setSort(index + 1);
            list.add(mdmUser);
            index++;
        }
        return list;
    }

    /**
     * 根据父节点ID，取出其下面排序sort第一条记录
     *
     * @param parentId
     * @param isAsc
     * @param exclusiveId,不包含的记录
     * @return
     */
    @SneakyThrows
    public MdmUser getOneByParent(Long parentId, Boolean isAsc, Long exclusiveId) {
        List<MdmUser> list = getListByParent(parentId, isAsc, exclusiveId);
        if (ObjectUtil.isNotEmpty(list) && !list.isEmpty()) {
            Optional<MdmUser> first = list.stream().findFirst();
            MdmUser mdmUser = first != null ? first.get() : null;
            return mdmUser;
        }
        return null;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean sortForGlobal(Long sourceId, Long targetId, String index) {
        MdmUser source = this.getById(sourceId);
        if (ObjectUtil.isEmpty(source)) {
            throw new IncloudException("根据传入的sourceId查询不到相应的人员信息！");
        }
        List<MdmUser> list = new ArrayList<>();
        if (StrUtil.isNotEmpty(index)) {
            if (index.equals(SortEnum.FIRST.value)) {
                List<MdmUser> otherSortList = getListByGlobalSort(source.getGlobalSort(), true);
                List<MdmUser> collect = otherSortList.stream().filter(mdmOrg -> mdmOrg.getSort() >= defaultSort).collect(Collectors.toList());
                list = sortOtherListForGlobal(collect, defaultSort);
                source.setGlobalSort(defaultSort);
                source.setGlobalSortSecond(defaultSort);
            } else if (index.equals(SortEnum.LAST.value)) {
                MdmUser lastOne = getOneByLastGlobalSort(false);
                source.setSort(lastOne.getSort() + 1);
            }
        } else {
            MdmUser target = this.getById(targetId);
            if (ObjectUtil.isEmpty(target)) {
                throw new IncloudException("根据传入的targetId查询不到相应的实体！");
            }
            Integer targetGlobalSort = target.getGlobalSort();
            Integer targetGlobalSortSecond = target.getGlobalSortSecond();
            List<MdmUser> otherSortList = getListByGlobalSort(targetGlobalSort, true);
            List<MdmUser> collect = otherSortList.stream().filter(mdmOrg -> mdmOrg.getSort() >= targetGlobalSortSecond).collect(Collectors.toList());
            list = sortOtherListForGlobal(collect, targetGlobalSortSecond);
            source.setGlobalSort(targetGlobalSort);
            source.setGlobalSortSecond(targetGlobalSortSecond);
        }
        //把自己也加进去；
        MdmUser mdmUser = dozerMapper.map(source, MdmUser.class);
        list.add(mdmUser);
        boolean result = updateBatchById(list);
        if (result) {
            this.sendMqForUsers(list);
            log.info("批量排序成功！");
        }
        return true;
    }

    /**
     * 根据glabalSort 查询该顺序下所有的人员信息 并且按照globalSortSecond 排序
     *
     * @param GlobalSort
     * @param isAsc
     * @return
     */
    public List<MdmUser> getListByGlobalSort(Integer GlobalSort, Boolean isAsc) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getGlobalSort, GlobalSort);
        if (isAsc) {
            queryWrapper.orderByAsc(MdmUser::getGlobalSortSecond);
        } else {
            queryWrapper.orderByDesc(MdmUser::getGlobalSortSecond);
        }
        List<MdmUser> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 按给定集合和排序index做排序 -全局排序使用
     *
     * @param otherSortList
     * @param index
     * @return
     */
    List<MdmUser> sortOtherListForGlobal(List<MdmUser> otherSortList, Integer index) {
        List<MdmUser> list = new ArrayList<>();
        for (MdmUser mdmUser : otherSortList) {
            mdmUser.setGlobalSortSecond(index + 1);
            list.add(mdmUser);
            index++;
        }
        return list;
    }

    /**
     * 查询最后一条人员信息 取globalSort
     *
     * @param isAsc
     * @return
     */
    @SneakyThrows
    public MdmUser getOneByLastGlobalSort(Boolean isAsc) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        if (isAsc) {
            queryWrapper.orderByAsc(MdmUser::getGlobalSort);
        } else {
            queryWrapper.orderByDesc(MdmUser::getGlobalSort);
        }
        queryWrapper.last("limit 1");
        MdmUser mdmUser = mdmUserMapper.selectOne(queryWrapper);
        return mdmUser;
    }

    @Override
    public LoginAppUser findByUserName(String userName) {
        log.info("登陆:>>>>>>>userName:{}", userName);
        MdmUser mdmUser = null;
        if (userName.contains(LoginEnum.EMAIL.code)) { //邮箱登录
            String emailArr[] = userName.split(LoginEnum.EMAIL.code);
            mdmUser = this.findByEmail(emailArr[1]);
        } else if (userName.contains(LoginEnum.PHONE.code)) {//手机号登录
            String phoneArr[] = userName.split(LoginEnum.PHONE.code);
            mdmUser = this.findByPhone(phoneArr[1]);
        } else if (userName.contains(LoginEnum.IDNUMBER.code)) {//身份证号登录
            String idnumerArr[] = userName.split(LoginEnum.IDNUMBER.code);
            mdmUser = this.findByIdNumber(idnumerArr[1]);
        } else if (userName.contains(LoginEnum.VERIFYCODE.code)) {//验证码登录
            String idnumerArr[] = userName.split(LoginEnum.VERIFYCODE.code);
            mdmUser = this.findByPhone(idnumerArr[1]);
            this.checkUserNotNull(mdmUser);
            //设置默认密码
            mdmUser.setPassWord(loginDefaultPassword);
        } else {
            LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdmUser::getUserName, userName);
            mdmUser = mdmUserMapper.selectOne(queryWrapper);
        }
        this.checkUserNotNull(mdmUser);
        String ob = JSONObject.toJSONString(mdmUser);
        LoginAppUser loginAppUser = (LoginAppUser) JSONObject.parseObject(ob, LoginAppUser.class);
        return loginAppUser;
    }

    public void checkUserNotNull(MdmUser mdmUser) {
        if (null == mdmUser) {
            throw new IncloudException("不存在的用户信息");
        }
    }


    @Override
    public MdmUser findByPhone(String phoneNum) {
        if (StringUtils.isBlank(phoneNum)) {
            throw new IncloudException("手机号不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getPhoneNum, phoneNum);
        MdmUser mdmUser = mdmUserMapper.selectOne(queryWrapper);
        if (null != mdmUser) {
            if (UserStatusEnum.QUIT.code == mdmUser.getStatus()) {
                throw new IncloudException("该人员已离职！");
            }
        }
        return mdmUser;
    }

    @Override
    public MdmUser findByPhoneForMsg(String phoneNum) {
        if (StringUtils.isBlank(phoneNum)) {
            throw new IncloudException("手机号不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getPhoneNum, phoneNum);
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(queryWrapper);
        //如果是一个人 看该人员是否离职
        if (mdmUsers.size() == 1) {
            if (UserStatusEnum.QUIT.code.equals(mdmUsers.get(0).getStatus())) {
                throw new IncloudException("该人员已离职！");
            }
            return mdmUsers.get(0);
        }
        if (mdmUsers.size() > 1) {
            int onJob = 0;
            MdmUser returnUser = new MdmUser();
            for (MdmUser mdmUser : mdmUsers) {
                if (UserStatusEnum.ONJOB.code.equals(mdmUser.getStatus())) {
                    BeanUtils.copyProperties(mdmUser, returnUser);
                    onJob++;
                }
            }
            if (onJob == 1) {
                return returnUser;
            }
            if (onJob > 1) {
                throw new IncloudException("一个手机号关联了多个人，请找管理员重新配置后再进行登录！");
            }
        }
        return null;
    }

    @Override
    public MdmUser findByIdNumber(String idNumber) {
        if (StringUtils.isBlank(idNumber)) {
            throw new IncloudException("身份证号不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getIdCard, idNumber);
        MdmUser mdmUser = mdmUserMapper.selectOne(queryWrapper);
        return mdmUser;
    }

    @Override
    public MdmUser findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IncloudException("邮箱不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getEmail, email);
        MdmUser mdmUser = mdmUserMapper.selectOne(queryWrapper);
        return mdmUser;
    }

    @Override
    public Integer getMaxGlobalSort() {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(MdmUser::getGlobalSort);
        queryWrapper.last("limit 1");
        MdmUser mdmUser = mdmUserMapper.selectOne(queryWrapper);
        if (null == mdmUser) {
            return 0;
        }
        return mdmUser.getGlobalSort();
    }

    @Override
    public List<MdmUserVo> getUserByOrgId(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            throw new IncloudException("机构id不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(MdmUser::getOrgFullId, orgId);
        List<MdmUser> mdmUserList = mdmUserMapper.selectList(queryWrapper);
        List<MdmUserVo> mdmUserVoList = null;
        if (CollectionUtil.isNotEmpty(mdmUserList)) {
            mdmUserVoList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
        }
        return mdmUserVoList;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean setMasterDuty(MdmUserDto mdmUserDto) {
        //check user
        MdmUser mdmUser = super.getById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("该用户已经不存在！");
        }
        if (StringUtils.isBlank(mdmUserDto.getDutyIds())) {
            mdmDutyUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.YES.code);
        } else {
            List<String> streamStr = Stream.of(mdmUserDto.getDutyIds().split(",")).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(streamStr)) {
                List<MdmDuty> dutyList = mdmDutyService.getByIds(streamStr); //设置主职只会有一个
                if (CollectionUtil.isEmpty(dutyList)) {
                    throw new IncloudException("该职位已经不存在！");
                }
                MdmDuty mdmDuty = dutyList.get(0);
                //判断是否存在主岗职信息
                MdmDutyUser mdmDutyUser = mdmDutyUserService.getMaster(mdmUserDto.getId());
                //如果不存在添加 如果存在修改
                if (ObjectUtil.isNull(mdmDutyUser)) {
                    MdmDutyUser _mdmDutyUser = new MdmDutyUser();
                    _mdmDutyUser.setId(IdGenerator.getIdGenerator());
                    _mdmDutyUser.setOrgFullDutyId(mdmDuty.getOrgFullId());
                    _mdmDutyUser.setOrgFullDutyName(mdmDuty.getOrgFullName());
                    _mdmDutyUser.setDutyId(mdmDuty.getId());
                    _mdmDutyUser.setDutyCode(mdmDuty.getDutyCode());
                    _mdmDutyUser.setDutyName(mdmDuty.getDutyName());
                    _mdmDutyUser.setUserId(mdmUser.getId());
                    _mdmDutyUser.setUserName(mdmUser.getUserName());
                    _mdmDutyUser.setUserNameCh(mdmUser.getUserNameCh());
                    _mdmDutyUser.setIsMaster(YesNo.YES.code);
                    _mdmDutyUser.setCreateTime(LocalDateTime.now());
                    mdmDutyUserService.save(_mdmDutyUser);
                    mdmDutyService.isRef(_mdmDutyUser.getDutyId());
                    //neoService.mergeUserPostRel(mdmUser,mdmPost,_mdmPostUser);//维护neo4j关系 职务不维护
                } else {
                    mdmDutyUser.setDutyCode(mdmDuty.getDutyCode());
                    mdmDutyUser.setDutyName(mdmDuty.getDutyName());
                    mdmDutyUser.setDutyId(mdmDuty.getId());
                    mdmDutyUser.setOrgFullDutyId(mdmDuty.getOrgFullId());
                    mdmDutyUser.setUpdateTime(LocalDateTime.now());
                    mdmDutyUserService.updateById(mdmDutyUser);
                    //neoService.delUserPostRel(mdmUser,YesNo.YES.code);//先删关系
                    mdmDutyService.isRef(mdmDutyUser.getDutyId());
                    //neoService.mergeUserPostRel(mdmUser,mdmPost,mdmPostUser);//重新维护关系 职务不维护
                }
            }
        }
        log.debug("用户调职成功-发送增量MQ");
        MdmUserVo mdmUserVo = this.getUserToMQ(mdmUser.getId());
        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUser.getParentDeptId());
        String topics[] = userTopics.split(",");
        if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
            mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
            mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        return true;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean setAndDuty(MdmUserDto mdmUserDto) {
        //根据userId 删除原先的全部兼职信息
        mdmDutyUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.NO.code);
        //check user
        MdmUser mdmUser = super.getById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("该用户已经不存在！");
        }
        if (StringUtils.isBlank(mdmUserDto.getDutyIds())) {
            throw new IncloudException("职位的id信息不能为空！");
        }
        List<String> streamStr = Stream.of(mdmUserDto.getDutyIds().split(",")).collect(Collectors.toList());
        //check duty
        if (CollectionUtil.isNotEmpty(streamStr)) {
            List<MdmDuty> dutyList = mdmDutyService.getByIds(streamStr);
            if (CollectionUtil.isEmpty(dutyList)) {
                throw new IncloudException("该岗位已经不存在！");
            }
            List<MdmDutyUser> resultList = new ArrayList<>();
            for (MdmDuty mdmDuty : dutyList) {
                MdmDutyUser _mdmDutyUser = new MdmDutyUser();
                _mdmDutyUser.setId(IdGenerator.getIdGenerator());
                _mdmDutyUser.setOrgFullDutyId(mdmDuty.getOrgFullId());
                _mdmDutyUser.setOrgFullDutyName(mdmDuty.getOrgFullName());
                _mdmDutyUser.setDutyId(mdmDuty.getId());
                _mdmDutyUser.setDutyCode(mdmDuty.getDutyCode());
                _mdmDutyUser.setDutyName(mdmDuty.getDutyName());
                _mdmDutyUser.setUserId(mdmUser.getId());
                _mdmDutyUser.setUserName(mdmUser.getUserName());
                _mdmDutyUser.setUserNameCh(mdmUser.getUserNameCh());
                _mdmDutyUser.setIsMaster(YesNo.NO.code);
                _mdmDutyUser.setCreateTime(LocalDateTime.now());
                resultList.add(_mdmDutyUser);
                mdmDutyService.isRef(_mdmDutyUser.getDutyId());
                //neoService.mergeUserPostRel(mdmUser,mdmPost,_mdmPostUser);//重新维护关系 职务不维护
            }
            if (CollectionUtil.isNotEmpty(resultList)) {
                mdmDutyUserService.saveBatch(resultList);
                log.debug("设置兼岗 - 兼岗信息修改成功。");
                log.debug("用户设置兼岗成功-发送增量MQ");
                MdmUserVo mdmUserVo = this.getUserToMQ(mdmUser.getId());
                MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUser.getParentDeptId());
                String topics[] = userTopics.split(",");
                if (null != checkLvTypeOrg.getLvType() && 0 != checkLvTypeOrg.getLvType()) {
                    mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
                if (null != checkLvTypeOrg.getOaLvType() && 0 != checkLvTypeOrg.getOaLvType()) {
                    mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
            }
            return true;
        }
        throw new IncloudException("兼岗数据为空！");
    }

    @Override
    public List<MdmUserVo> getUserByDeptId(String deptId) {
        if (StringUtils.isBlank(deptId)) {
            throw new IncloudException("机构id不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getParentDeptId, deptId);
        //如果是管理员 查询在职和离职  -- 安卓调错接口 加了这个条件 -可能影响部门调动-但中原建 现在不存在使用这种功能的情况
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (!admin.equals(loginAppUser.getUsername())) {
            queryWrapper.eq(MdmUser::getStatus, UserStatusEnum.ONJOB.code);
        }
        List<MdmUser> mdmUserList = mdmUserMapper.selectList(queryWrapper);
        List<MdmUserVo> mdmUserVoList = null;
        if (CollectionUtil.isNotEmpty(mdmUserList)) {
            mdmUserVoList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
            getPostsAndDutys(mdmUserVoList);
        }
        return mdmUserVoList;
    }

    @Override
    public MdmUserVo getUserToMQ(Long id) {
        MdmUser mdmUser = super.getById(id);
        MdmUserVo mdmUserVo = null;
        if (mdmUser != null) {
            mdmUserVo = dozerMapper.map(mdmUser, MdmUserVo.class);
            mdmUserVo.setPassWord(null);
            List<MdmPostUserVo> posts = mdmPostUserService.getPostByUserId(String.valueOf(mdmUser.getId()));
            mdmUserVo.setMdmPosts(posts);
            List<MdmDutyUserVo> dutys = mdmDutyUserService.getDutyByUserId(String.valueOf(mdmUser.getId()));
            mdmUserVo.setMdmDutys(dutys);
        }
        log.debug("查询成功");
        return mdmUserVo;
    }

    @Override
    public List<MdmUserVo> getUserByIdCards(String idCards) {
        if (StringUtils.isBlank(idCards)) {
            throw new IncloudException("身份证信息不能为空！");
        }
        List<String> sttList = Stream.of(idCards.split(",")).collect(Collectors.toList());
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmUser::getIdCard, sttList);
        List<MdmUser> mdmUserList = mdmUserMapper.selectList(queryWrapper);
        List<MdmUserVo> mdmUserVoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(mdmUserList)) {
            mdmUserVoList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
        }
        return mdmUserVoList;
    }

    @Override
    @Transactional
    public Boolean setAllUserDefaultRole() {
        MdmUserDto mdmUserDto = new MdmUserDto();
        mdmUserDto.setStatus(UserStatusEnum.ONJOB.code);
        mdmUserDto.setUserClass(UserClassEnum.BIZ.code);
        List<MdmUserVo> list = this.lists(mdmUserDto);
        if (CollectionUtil.isNotEmpty(list)) {
            MdmRoleVo mdmRoleVo = mdmRoleService.getByCode(DefaultRoleEnum.DEFAULT.value);
            if (null == mdmRoleVo) {
                throw new IncloudException("默认角色为空！");
            }
            List<MdmRoleUser> resultRoleUsers = new ArrayList<>();
            for (MdmUserVo mdmUserVo : list) {
                MdmRoleUser mdmRoleUser = new MdmRoleUser();
                mdmRoleUser.setRoleCode(DefaultRoleEnum.DEFAULT.value);
                mdmRoleUser.setRoleId(mdmRoleVo.getId());
                mdmRoleUser.setRoleName(mdmRoleVo.getRoleName());
                mdmRoleUser.setUserId(mdmUserVo.getId());
                mdmRoleUser.setUserName(mdmUserVo.getUserName());
                mdmRoleUser.setUserNameCh(mdmUserVo.getUserNameCh());
                resultRoleUsers.add(mdmRoleUser);
            }
            mdmRoleUserService.saveBatch(resultRoleUsers);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean setUserDefaultRole(List<MdmUser> mdmUserList) {
        MdmRoleVo mdmRoleVo = mdmRoleService.getByCode(DefaultRoleEnum.DEFAULT.value);
        if (null != mdmRoleVo) {
            List<MdmRoleUser> resultRoleUsers = new ArrayList<>();
            for (MdmUser mdmUser : mdmUserList) {
                MdmRoleUser mdmRoleUser = new MdmRoleUser();
                mdmRoleUser.setRoleCode(DefaultRoleEnum.DEFAULT.value);
                mdmRoleUser.setRoleId(mdmRoleVo.getId());
                mdmRoleUser.setRoleName(mdmRoleVo.getRoleName());
                mdmRoleUser.setUserId(mdmUser.getId());
                mdmRoleUser.setUserName(mdmUser.getUserName());
                mdmRoleUser.setUserNameCh(mdmUser.getUserNameCh());
                resultRoleUsers.add(mdmRoleUser);
            }
            mdmRoleUserService.saveBatch(resultRoleUsers);
        }
        return true;
    }

    @Override
    public MdmUserVo getByIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            throw new IncloudException("身份号不能为空！");
        }
        MdmUserVo mdmUserVo = mdmUserMapper.getByIdCard(idCard);
        return mdmUserVo;
    }

    @Transactional
    @Override
    public MdmUserVo saveStudyUser(MdmUserDto mdmUserDto) {
        //校验本地是否重复
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();//校验用户名
        queryWrapper.in(MdmUser::getUserName, mdmUserDto.getUserName());
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(mdmUsers)) {
            setStudyUserRole(mdmUserDto, mdmUsers.get(0));
            return dozerMapper.map(mdmUsers.get(0), MdmUserVo.class);
        }
        LambdaQueryWrapper<MdmUser> iQueryWrapper = new LambdaQueryWrapper<>();//校验证件号
        iQueryWrapper.in(MdmUser::getIdCard, mdmUserDto.getIdCard());
        List<MdmUser> _mdmUsers = mdmUserMapper.selectList(iQueryWrapper);
        if (CollectionUtil.isNotEmpty(_mdmUsers)) {
            setStudyUserRole(mdmUserDto, _mdmUsers.get(0));
            return dozerMapper.map(_mdmUsers.get(0), MdmUserVo.class);
        }
        //没有重复，就新插入人员
        MdmUser mdmUser = dozerMapper.map(mdmUserDto, MdmUser.class);
        super.save(mdmUser);
        //Sunzhenxi
        binLogProducerService.sendBinLogMsg("incloud_base_mdm_user", "INSERT", mdmUser);
        LambdaQueryWrapper<MdmUser> uQueryWrapper = new LambdaQueryWrapper<>();
        uQueryWrapper.in(MdmUser::getIdCard, mdmUserDto.getIdCard());
        List<MdmUser> uMdmUsers = mdmUserMapper.selectList(uQueryWrapper);
        if (CollectionUtil.isNotEmpty(uMdmUsers)) {
            setStudyUserRole(mdmUserDto, uMdmUsers.get(0));
            return dozerMapper.map(uMdmUsers.get(0), MdmUserVo.class);
        }
        return null;
    }


    /**
     * 给在线学习人员设置角色
     *
     * @param mdmUserDto
     * @param mdmUser
     */
    @Transactional
    public void setStudyUserRole(MdmUserDto mdmUserDto, MdmUser mdmUser) {
        //在线学习人员角色类型
        int type = mdmUserDto.getStudyUserRole().intValue();
        LambdaQueryWrapper<MdmRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRole::getRoleCode, StudyEnum.getMessage(type));
        List<MdmRole> mdmRoles = mdmRoleMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(mdmRoles)) {
            throw new IncloudException("学习系统不存在内置角色，请联系管理员");
        }
        MdmRole mdmRole = mdmRoles.get(0);

        //设置人员角色关系
        MdmRoleUser mdmRoleUser = new MdmRoleUser();
        mdmRoleUser.setRoleId(mdmRole.getId());
        mdmRoleUser.setRoleCode(mdmRole.getRoleCode());
        mdmRoleUser.setRoleName(mdmRole.getRoleName());
        mdmRoleUser.setUserId(mdmUser.getId());
        mdmRoleUser.setUserName(mdmUser.getUserName());
        mdmRoleUser.setUserNameCh(mdmUser.getUserNameCh());
        mdmRoleUser.setCreateTime(LocalDateTime.now());
        mdmRoleUser.setUpdateTime(LocalDateTime.now());
        //保存人员角色关系
        mdmRoleUserService.save(mdmRoleUser);
    }

    @Override
    @Transactional
    public Boolean deleteStudyUser(String id) {
        if (StringUtils.isNotBlank(id)) {
            List<String> ids = Stream.of(id.split(",")).collect(Collectors.toList());
            List<MdmUser> mdmUsers = super.listByIds(ids);
            //根据人员类型分类
            Map<Integer, List<MdmUser>> list = mdmUsers.stream().collect(Collectors.groupingBy(MdmUser::getUserClass));
            //获取到edu的人员
            List<MdmUser> studyMdmUsers = list.get(UserClassEnum.EDU.code);
            if (CollectionUtil.isNotEmpty(studyMdmUsers)) {
                //删除的是edu的用户信息（虚拟目录下的人员），非edu用户，不删除
                List<Long> delIds = studyMdmUsers.stream().map(MdmUser::getId).collect(Collectors.toList());
                boolean boo = super.removeByIds(delIds);
            }
            //但是这些用户的edu角色信息肯定都需要删除
            LambdaQueryWrapper<MdmRoleUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmRoleUser::getUserId, ids).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_ADMIN.message)
                    .or()
                    .in(MdmRoleUser::getUserId, ids).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_TEACHER.message)
                    .or()
                    .in(MdmRoleUser::getUserId, ids).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_STUDENT.message);
            boolean result = mdmRoleUserService.remove(queryWrapper);
            if (result) {
                log.debug("删除在线学习人员成功");
                //Sunzhenxi
                binLogProducerService.sendBinLogMsg("incloud_base_mdm_user", "DELETE", mdmUsers);
                return result;
            }

        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateStudyUser(MdmUserDto mdmUserDto) {
        MdmUser mdmUser = super.getById(mdmUserDto.getId());
        mdmUser.setUserNameCh(mdmUserDto.getUserNameCh());
        mdmUser.setPhoneNum(mdmUserDto.getPhoneNum());
        mdmUser.setEmail(mdmUserDto.getEmail());
        mdmUser.setSex(mdmUserDto.getSex());
        mdmUser.setCardType(mdmUserDto.getCardType());
        mdmUser.setIdCard(mdmUserDto.getIdCard());
        mdmUser.setUserName(mdmUserDto.getUserName());
        mdmUser.setPassWord(mdmUserDto.getPassWord());
        mdmUser.setUpdateTime(LocalDateTime.now());
        boolean boo = super.updateById(mdmUser);
        if (boo) {
            //查询要修改成什么角色
            LambdaQueryWrapper<MdmRole> _queryWrapper = new LambdaQueryWrapper<>();
            _queryWrapper.eq(MdmRole::getRoleCode, StudyEnum.getMessage(mdmUserDto.getStudyUserRole()));
            List<MdmRole> mdmRoles = mdmRoleMapper.selectList(_queryWrapper);
            MdmRole mdmRole = mdmRoles.get(0);
            LambdaQueryWrapper<MdmRoleUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MdmRoleUser::getUserId, mdmUser.getId()).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_ADMIN.message)
                    .or()
                    .eq(MdmRoleUser::getUserId, mdmUser.getId()).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_TEACHER.message)
                    .or()
                    .eq(MdmRoleUser::getUserId, mdmUser.getId()).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_STUDENT.message);
            MdmRoleUser mdmRoleUser = mdmRoleUserService.getOne(queryWrapper);
            mdmRoleUser.setRoleId(mdmRole.getId());
            mdmRoleUser.setRoleCode(mdmRole.getRoleCode());
            mdmRoleUser.setRoleName(mdmRole.getRoleName());
            mdmRoleUser.setUserId(mdmUserDto.getId());
            mdmRoleUser.setUserName(mdmUserDto.getUserName());
            mdmRoleUser.setUserNameCh(mdmUserDto.getUserNameCh());
            mdmRoleUser.setUpdateTime(LocalDateTime.now());
            boolean result = mdmRoleUserService.updateById(mdmRoleUser);
            if (result) {
                return result;
            }
            //Sunzhenxi
            binLogProducerService.sendBinLogMsg("incloud_base_mdm_user", "UPDATE", mdmUser);
        }
        return false;
    }

    /**
     * 注册
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    public MdmUserVo regStudyUser(MdmUserDto mdmUserDto) {
        //校验本地是否重复
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();//校验用户名
        queryWrapper.in(MdmUser::getUserName, mdmUserDto.getUserName());
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(mdmUsers)) {
            setStudyUserRole(mdmUserDto, mdmUsers.get(0));
            return dozerMapper.map(mdmUsers.get(0), MdmUserVo.class);
        }
        //校验是否已存在此人，如果存在同步到在线学习，无需审核，并设置角色
        LambdaQueryWrapper<MdmUser> iQueryWrapper = new LambdaQueryWrapper<>();//校验证件号
        iQueryWrapper.in(MdmUser::getIdCard, mdmUserDto.getIdCard());
        List<MdmUser> _mdmUsers = mdmUserMapper.selectList(iQueryWrapper);
        if (CollectionUtil.isNotEmpty(_mdmUsers)) {
            setStudyUserRole(mdmUserDto, _mdmUsers.get(0));
            return dozerMapper.map(_mdmUsers.get(0), MdmUserVo.class);
        }
        //如果不存在此人，返回null
        return null;
    }

    @Override
    public MdmUser getByUserName(String userName) {
        LambdaQueryWrapper<MdmUser> mdmUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mdmUserLambdaQueryWrapper.eq(MdmUser::getUserName, userName);
        return mdmUserMapper.selectOne(mdmUserLambdaQueryWrapper);
    }

    @Override
    public List<MdmUserVo> getUserByDeptIdForPhone(String orgId) {
        if (StringUtils.isBlank(orgId)) {
            throw new IncloudException("机构id不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getParentDeptId, orgId);
        queryWrapper.eq(MdmUser::getStatus, UserStatusEnum.ONJOB.code);
        List<MdmUser> mdmUserList = mdmUserMapper.selectList(queryWrapper);
        List<MdmUserVo> mdmUserVoList = null;
        if (CollectionUtil.isNotEmpty(mdmUserList)) {
            mdmUserVoList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
            getPostsAndDutys(mdmUserVoList);
        }
        return mdmUserVoList;
    }

    @Override
    public List<MdmUserExcel> expertUserInfo() {

        long startTime = System.currentTimeMillis();
        LambdaQueryWrapper<MdmUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(MdmUser::getUserClass, UserClassEnum.BIZ.code);
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(userWrapper);
        List<MdmUserExcel> mdmUserExcels = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(mdmUsers)) {
            List<MdmUserVo> mdmUserVos = DozerUtils.mapList(dozerMapper, mdmUsers, MdmUserVo.class);
            mdmUserVos.forEach(mdmUserVo -> {
                List<String> fullOrgNameList = new ArrayList<>();
                getFullOrgName(mdmUserVo.getParentDeptId(), fullOrgNameList);
                List<String> reverse = CollectionUtil.reverse(fullOrgNameList);
                mdmUserVo.setParentOrgName(reverse.toString());
                //岗位信息
                StringBuilder postName = new StringBuilder();
                StringBuilder orgFullPostName = new StringBuilder();
                StringBuilder postIsMaster = new StringBuilder();
                List<MdmPostUserVo> postUserVos = mdmPostUserService.getPostByUserId(String.valueOf(mdmUserVo.getId()));
                if (CollectionUtil.isNotEmpty(postUserVos)) {
                    postUserVos.forEach(postUserVo -> {
                        postName.append(postUserVo.getPostName());
                        postName.append(";");

                        orgFullPostName.append(postUserVo.getOrgFullPostName());
                        orgFullPostName.append(";");

                        if (postUserVo.getIsMaster().equals(YesNo.YES.code)) {
                            postIsMaster.append("主岗");
                        } else {
                            postIsMaster.append("次岗");
                        }
                        postIsMaster.append(";");
                    });
                }
                //职位信息
                StringBuilder dutyName = new StringBuilder();
                StringBuilder orgFullDutyName = new StringBuilder();
                StringBuilder dutyIsMaster = new StringBuilder();
                List<MdmDutyUserVo> dutyUserVos = mdmDutyUserService.getDutyByUserId(String.valueOf(mdmUserVo.getId()));
                if (CollectionUtil.isNotEmpty(dutyUserVos)) {
                    dutyUserVos.forEach(dutyUserVo -> {
                        dutyName.append(dutyUserVo.getDutyName());
                        dutyName.append(";");

                        orgFullDutyName.append(dutyUserVo.getOrgFullDutyName());
                        orgFullDutyName.append(";");

                        if (dutyUserVo.getIsMaster().equals(YesNo.YES.code)) {
                            dutyIsMaster.append("主职位");
                        } else {
                            dutyIsMaster.append("次职位");
                        }
                        postIsMaster.append(";");
                    });
                }

                //最终信息
                MdmUserExcel mdmUserExcel = new MdmUserExcel();
                dozerMapper.map(mdmUserVo, mdmUserExcel);
                //国籍
                if ("156".equals(mdmUserVo.getNationality())) {
                    mdmUserExcel.setNationality("中国");
                }
                //证件类型
                mdmUserExcel.setCardType(CardTypeEnum.getMessage(mdmUserVo.getCardType()));
                //性别
                mdmUserExcel.setSex(SexEnum.getMessage(mdmUserVo.getSex()));
                //民族
                if ("01".equals(mdmUserVo.getNation())) {
                    mdmUserExcel.setNation("汉族");
                }
                //入职时间
                if (ObjectUtil.isNotNull(mdmUserVo.getBirthday())) {
                    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String date = dtf2.format(mdmUserVo.getBirthday());
                    mdmUserExcel.setBirthday(date);
                }
                //婚姻状况
                if (StringUtils.isNotBlank(mdmUserVo.getMarriageStatus())) {
                    mdmUserExcel.setMarriageStatus(MarriageStatusEnum.getMessage(Integer.valueOf(mdmUserVo.getMarriageStatus())));
                }
                //状态
                if (mdmUserVo.getStatus().equals(YesNo.YES.code)) {
                    mdmUserExcel.setStatus("是");
                } else {
                    mdmUserExcel.setStatus("否");
                }
                //户口性质
                if (ObjectUtil.isNotNull(mdmUserVo.getBirthNature())) {
                    mdmUserExcel.setBirthNature(BirthNatureEnum.getMessage(mdmUserVo.getBirthNature()));
                }
                //岗位信息
                mdmUserExcel.setPostName(postName.toString());
                mdmUserExcel.setOrgFullPostName(orgFullPostName.toString());
                mdmUserExcel.setPostIsMaster(postIsMaster.toString());
                //职位信息
                mdmUserExcel.setDutyName(dutyName.toString());
                mdmUserExcel.setOrgFullDutyName(orgFullDutyName.toString());
                mdmUserExcel.setDutyIsMaster(dutyIsMaster.toString());

                mdmUserExcels.add(mdmUserExcel);
            });
        }
        long endTime = System.currentTimeMillis();
        log.debug("导出全量用户excel信息时间:{}秒--------------------------------------", (endTime - startTime) / 1000);
        return mdmUserExcels;
    }

    @Override
    public List<MdmUserVo> getUserByParentOrgId(String orgIds) {
        if (StringUtils.isBlank(orgIds)) {
            throw new IncloudException("机构id不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(MdmUser::getOrgFullId, orgIds);
        List<MdmUser> mdmUserList = mdmUserMapper.selectList(queryWrapper);
        List<MdmUserVo> mdmUserVoList = null;
        if (CollectionUtil.isNotEmpty(mdmUserList)) {
            mdmUserVoList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
        }
        return mdmUserVoList;
    }

    @Override
    public List<MdmUserVo> getUserByParentDeptId(String deptIds) {
        if (StringUtils.isBlank(deptIds)) {
            throw new IncloudException("部门id不能为空！");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmUser::getParentDeptId, Stream.of(deptIds.split(",")).collect(Collectors.toList()));
        List<MdmUser> mdmUserList = mdmUserMapper.selectList(queryWrapper);
        List<MdmUserVo> mdmUserVoList = null;
        if (CollectionUtil.isNotEmpty(mdmUserList)) {
            mdmUserVoList = DozerUtils.mapList(dozerMapper, mdmUserList, MdmUserVo.class);
        }
        return mdmUserVoList;
    }

    void getFullOrgName(Long parentDeptId, List<String> fullOrgNameList) {
        LambdaQueryWrapper<MdmOrg> orgWrapper = new LambdaQueryWrapper<>();
        orgWrapper.eq(MdmOrg::getId, parentDeptId);
//        orgWrapper.eq(MdmOrg::getStatus,StatusEnum.RUNNING.code);
        MdmOrg mdmOrg = mdmOrgMapper.selectOne(orgWrapper);
        fullOrgNameList.add(mdmOrg.getOrgName());
        if (!mdmOrg.getParentOrgId().equals(0L)) {
            getFullOrgName(mdmOrg.getParentId(), fullOrgNameList);
        }
    }

}