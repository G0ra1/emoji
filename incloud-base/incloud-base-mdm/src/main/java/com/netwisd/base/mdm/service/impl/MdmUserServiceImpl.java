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
 * @author ???????????? XHL@netwisd.com
 * @Description ?????? ????????????...
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
     * ????????????????????????
     */
    private static String defaultJoinName = ",";

    /**
     * ??????????????????1
     */
    private static Integer defaultSort = 1;

    /**
     * ????????????
     */
    private static String defaultPassword = "zyj@951@ad";

    private static String defaultPassWord = "$2a$10$eDPYUOIpahXGFz18gQ1gK.rjZiDjSXFVcRIb7lRIU2jMFf4vVKThi";

    /**
     * ???????????????????????????
     */
    private static Integer globalSortSecond = 0;

    /**
     * ???????????????????????????
     */
    private final String loginDefaultPassword = "$2a$10$eKsL5twOYgNePPllW5nze.rXHSTu8qFeW2zMQj.yzKqUT81vaoraa";

    /**
     * ?????????????????????
     */
    private static String admin = "admin";

    /**
     * ????????????????????????
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    public Page list(MdmUserDto mdmUserDto) {
        //?????????????????????????????????????????????????????????
        MdmOrg mdmOrg = mdmOrgService.getById(mdmUserDto.getParentOrgId());
        if (null == mdmOrg) {
            throw new IncloudException("???????????????????????????");
        }
        //????????????????????????????????????????????????
        LambdaQueryWrapper<MdmOrg> orgWrapper = new LambdaQueryWrapper<>();
        orgWrapper.eq(MdmOrg::getParentId, mdmOrg.getId());
        orgWrapper.eq(MdmOrg::getStatus, StatusEnum.RUNNING.code);
        List<MdmOrg> mdmOrgs = mdmOrgMapper.selectList(orgWrapper);
        int hasKids = YesNo.NO.code;
        if (CollectionUtil.isNotEmpty(mdmOrgs)) {
            hasKids = YesNo.YES.code;
        }
        //?????????????????? ?????????????????????
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (admin.equals(loginAppUser.getUsername())) {
            mdmUserDto.setStatus(null);
        } else {
            mdmUserDto.setStatus(UserStatusEnum.ONJOB.code);
        }
        mdmUserDto.setHasKids(hasKids);
        Page<MdmUserVo> page = mdmUserMapper.getConditionList(mdmUserDto.getPage(), mdmUserDto);
        log.debug("????????????:" + page.getTotal());
        return page;
    }

    /**
     * ?????????????????????
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    public List<MdmUserVo> lists(MdmUserDto mdmUserDto) {
        MdmOrg mdmOrg = mdmOrgService.getById(mdmUserDto.getParentOrgId());
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        //?????????????????? ?????????????????????
        if (ObjectUtils.isNotEmpty(mdmUserDto.getIsEduStudent()) && mdmUserDto.getIsEduStudent().equals(YesNo.YES.code)) {
            LambdaQueryWrapper<MdmRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
            roleUserWrapper.eq(MdmRoleUser::getRoleCode, "STUDY_STUDENT");
            List<MdmRoleUser> roleUserList = mdmRoleUserMapper.selectList(roleUserWrapper);
            if (CollectionUtil.isNotEmpty(roleUserList)) {
                List<Long> userIdList = roleUserList.stream().map(MdmRoleUser::getUserId).collect(Collectors.toList());
                queryWrapper.in(MdmUser::getId, userIdList);
            }
        }
        //????????????id
        queryWrapper.like(ObjectUtil.isNotNull(mdmUserDto.getParentOrgId()), MdmUser::getOrgFullId, mdmUserDto.getParentOrgId());
        //????????????
        queryWrapper.like(ObjectUtil.isNotNull(mdmUserDto.getUserNameCh()), MdmUser::getUserNameCh, mdmUserDto.getUserNameCh());
        //????????????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getParentDeptId()), MdmUser::getParentDeptId, mdmUserDto.getParentDeptId());
        //????????????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getStatus()), MdmUser::getStatus, mdmUserDto.getStatus());
        //????????????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getEmploymentType()), MdmUser::getEmploymentType, mdmUserDto.getEmploymentType());
        //??????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getSex()), MdmUser::getSex, mdmUserDto.getSex());
        //????????????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getBirthNature()), MdmUser::getBirthNature, mdmUserDto.getBirthNature());
        //??????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getNation()), MdmUser::getNation, mdmUserDto.getNation());
        //????????????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getPoliticsStatus()), MdmUser::getPoliticsStatus, mdmUserDto.getPoliticsStatus());
        //????????????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getMarriageStatus()), MdmUser::getMarriageStatus, mdmUserDto.getMarriageStatus());
        //????????????
        queryWrapper.eq(ObjectUtil.isNotNull(mdmUserDto.getHealthCondition()), MdmUser::getHealthCondition, mdmUserDto.getHealthCondition());
        queryWrapper.between(ObjectUtil.isNotNull(mdmUserDto.getSUpdateTime()) && ObjectUtil.isNotNull(mdmUserDto.getEUpdateTime()), MdmUser::getUpdateTime, mdmUserDto.getSUpdateTime(), mdmUserDto.getEUpdateTime());
        queryWrapper.eq(MdmUser::getUserClass, ObjectUtil.isNull(mdmUserDto.getUserClass()) || 0 == mdmUserDto.getUserClass() ? UserClassEnum.BIZ.code : mdmUserDto.getUserClass());
        if (null != mdmOrg) { //?????????????????????????????????  ?????????????????????sort??????
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
            log.debug("????????????:" + list.size());
            mdmUserList = DozerUtils.mapList(dozerMapper, list, MdmUserVo.class);
            //?????????????????????????????????????????????
            getPostsAndDutys(mdmUserList);
        }
        return mdmUserList;
    }

    /**
     * ??????????????????????????????
     *
     * @param mdmUserList
     */
    public void getPostsAndDutys(List<MdmUserVo> mdmUserList) {
        if (CollectionUtil.isNotEmpty(mdmUserList)) {
            //?????????????????????????????????????????????
            MdmPostUserDto mdmPostUserDto = new MdmPostUserDto();
            List<MdmPostUserVo> mdmPostUserVos = mdmPostUserService.lists(mdmPostUserDto);
            //??????????????????
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
                //????????????????????????????????????
                mdmUserVo.setPassWord(null);
            }
        }
    }

    /**
     * ??????ID????????????
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
        log.debug("????????????");
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
     * ????????????
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
            log.debug("???????????????????????????");
        }
        return boo;
    }

    public String handlePw(String aesPw) {
        //???????????????
        String decrypetPasword = AesUtil.aesDecrypt(aesPw);
        log.debug("???????????????:" + decrypetPasword);
        //?????????????????????
        return bCryptPasswordEncoder.encode(decrypetPasword);
    }

    public void checkSingleUserInfoUnique(MdmUser mdmUser) {
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmUser::getUserName, mdmUser.getUserName());
        List<MdmUser> uMdmUserList = mdmUserMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(uMdmUserList)) {
            throw new IncloudException("???????????????userName???" + uMdmUserList.get(0).getUserName());
        }
        LambdaQueryWrapper<MdmUser> iQueryWrapper = new LambdaQueryWrapper<>();
        iQueryWrapper.in(MdmUser::getIdCard, mdmUser.getIdCard());
        List<MdmUser> iMdmUserList = mdmUserMapper.selectList(iQueryWrapper);
        if (CollectionUtil.isNotEmpty(iMdmUserList)) {
            throw new IncloudException("?????????????????????????????????????????????" + iMdmUserList.get(0).getIdCard());
        }
//        LambdaQueryWrapper<MdmUser> pQueryWrapper = new LambdaQueryWrapper<>();
//        pQueryWrapper.in(MdmUser::getPhoneNum, mdmUser.getPhoneNum());
//        List<MdmUser> pMdmUserList = mdmUserMapper.selectList(pQueryWrapper);
//        if (CollectionUtil.isNotEmpty(pMdmUserList)) {
//            throw new IncloudException("?????????????????????????????????????????????" + pMdmUserList.get(0).getPhoneNum());
//        }
//        LambdaQueryWrapper<MdmUser> eQueryWrapper = new LambdaQueryWrapper<>();
//        eQueryWrapper.in(MdmUser::getEmail, mdmUser.getEmail());
//        List<MdmUser> eMdmUserList = mdmUserMapper.selectList(eQueryWrapper);
//        if (CollectionUtil.isNotEmpty(eMdmUserList)) {
//            throw new IncloudException("??????????????????????????????????????????" + eMdmUserList.get(0).getEmail());
//        }
    }

    public void checkUserInfoUnique(List<MdmUserDto> mdmUserDtoList) {
        //userName
        Map<String, List<MdmUserDto>> uMapGroup = mdmUserDtoList.stream().collect(Collectors.groupingBy(MdmUserDto::getUserName));
        List<String> userNames = mdmUserDtoList.stream().map(MdmUserDto::getUserName).collect(Collectors.toList());
        for (String key : uMapGroup.keySet()) {
            List<MdmUserDto> list = (List) uMapGroup.get(key);
            if (list.size() > 1) {
                throw new IncloudException("???????????????userName?????????????????????" + list.get(0).getUserName());
            }
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmUser::getUserName, userNames);
        List<MdmUser> uMdmUserList = mdmUserMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(uMdmUserList)) {
            throw new IncloudException("???????????????userName???" + uMdmUserList.get(0).getUserName());
        }
        //idCard
        Map<String, List<MdmUserDto>> iMapGroup = mdmUserDtoList.stream().collect(Collectors.groupingBy(MdmUserDto::getIdCard));
        List<String> idCards = mdmUserDtoList.stream().map(MdmUserDto::getIdCard).collect(Collectors.toList());
        for (String key : iMapGroup.keySet()) {
            List<MdmUserDto> list = (List) iMapGroup.get(key);
            if (list.size() > 1) {
                throw new IncloudException("?????????????????????????????????????????????" + list.get(0).getIdCard());
            }
        }
        LambdaQueryWrapper<MdmUser> iQueryWrapper = new LambdaQueryWrapper<>();
        iQueryWrapper.in(MdmUser::getIdCard, idCards);
        List<MdmUser> iMdmUserList = mdmUserMapper.selectList(iQueryWrapper);
        if (CollectionUtil.isNotEmpty(iMdmUserList)) {
            throw new IncloudException("?????????????????????????????????????????????" + iMdmUserList.get(0).getIdCard());
        }
        //phoneNum
//        Map<String, List<MdmUserDto>> pMapGroup = mdmUserDtoList.stream().collect(Collectors.groupingBy(MdmUserDto::getPhoneNum));
//        List<String> ps = mdmUserDtoList.stream().map(MdmUserDto::getPhoneNum).collect(Collectors.toList());
//        for (String key : pMapGroup.keySet()) {
//            List<MdmUserDto> list = (List) pMapGroup.get(key);
//            if (list.size() > 1) {
//                throw new IncloudException("?????????????????????????????????????????????" + list.get(0).getPhoneNum());
//            }
//        }
//        LambdaQueryWrapper<MdmUser> pQueryWrapper = new LambdaQueryWrapper<>();
//        pQueryWrapper.in(MdmUser::getPhoneNum, ps);
//        List<MdmUser> pMdmUserList = mdmUserMapper.selectList(pQueryWrapper);
//        if (CollectionUtil.isNotEmpty(pMdmUserList)) {
//            throw new IncloudException("?????????????????????????????????????????????" + pMdmUserList.get(0).getPhoneNum());
//        }

        //??????
//        Map<String, List<MdmUserDto>> eMapGroup = mdmUserDtoList.stream().collect(Collectors.groupingBy(MdmUserDto::getEmail));
//        List<String> es = mdmUserDtoList.stream().map(MdmUserDto::getEmail).collect(Collectors.toList());
//        for (String key : eMapGroup.keySet()) {
//            List<MdmUserDto> list = (List) eMapGroup.get(key);
//            if (list.size() > 1) {
//                throw new IncloudException("??????????????????????????????????????????" + list.get(0).getEmail());
//            }
//        }
//        LambdaQueryWrapper<MdmUser> eQueryWrapper = new LambdaQueryWrapper<>();
//        eQueryWrapper.in(MdmUser::getEmail, es);
//        List<MdmUser> eMdmUserList = mdmUserMapper.selectList(eQueryWrapper);
//        if (CollectionUtil.isNotEmpty(eMdmUserList)) {
//            throw new IncloudException("??????????????????????????????????????????" + eMdmUserList.get(0).getEmail());
//        }
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean saveList(List<MdmUserDto> mdmUserDtoList) {
        if (CollectionUtil.isNotEmpty(mdmUserDtoList)) {
            //????????? ?????????list??????????????????????????? ??????????????????????????????????????? ????????????????????????????????????????????????????????????
            checkUserInfoUnique(mdmUserDtoList);
            List<MdmUser> mdmUserList = new ArrayList<>();
            List<MdmUserDetail> mdmUserDetailList = new ArrayList<>();
            List<MdmPostUser> mdmPostUserList = new ArrayList<>();//????????????????????? ?????????????????????????????????
            Integer maxGlobalSort = getMaxGlobalSort();
            for (MdmUserDto mdmUserDto : mdmUserDtoList) {
                //??????????????????
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
                mdmUser.setPassWord(defaultPassWord);//???????????? bCryptPasswordEncoder.encode(defaultPassword)
                mdmUser.setStatus(null != mdmUserDto.getStatus() && 0 != mdmUserDto.getStatus() ? mdmUserDto.getStatus() : YesNo.YES.code);//1?????? 2??????
                mdmUser.setGlobalSortSecond(YesNo.NO.code);
                mdmUser.setSort(++maxGlobalSort);
                mdmUser.setGlobalSort(maxGlobalSort);
                mdmUser.setGlobalSortSecond(globalSortSecond);
                mdmUser.setUserClass(ObjectUtil.isNull(mdmUserDto.getUserClass()) ? UserClassEnum.BIZ.code : mdmUserDto.getUserClass());//???????????????????????????????????????
                mdmUserList.add(mdmUser);
                //????????????
                MdmUserDetail mdmUserDetail;
                if (null == mdmUserDto.getMdmUserDetailDto()) {
                    mdmUserDetail = new MdmUserDetail();
                } else {
                    mdmUserDetail = dozerMapper.map(mdmUserDto.getMdmUserDetailDto(), MdmUserDetail.class);
                }
                mdmUserDetail.setUserId(mdmUserDto.getId());
                mdmUserDetailList.add(mdmUserDetail);
                //??????????????????
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
            //??????????????????
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                boolean result = super.saveBatch(mdmUserList);
                if (result) {
                    //??????????????????
                    this.setUserDefaultRole(mdmUserList);
                    //??????neo4j ????????????
                    log.debug("???????????????????????????????????????");
                    //neoService.saveUserNode(mdmUserList);
                }
            }
            //??????????????????
            if (CollectionUtil.isNotEmpty(mdmUserDetailList)) {
                boolean result = mdmUserDetailService.saveBatch(mdmUserDetailList);
                if (result) {
                    log.debug("???????????????????????????????????????");
                }
            }
            //???????????????????????? ????????????neo4j
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
                    log.debug("?????????????????????????????????????????????");
                }
            }
            //??????mq
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                for (MdmUser mdmUser : mdmUserList) {
                    log.debug("??????????????????-????????????MQ");
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
                    //??????????????????????????????
//                    mdmMqService.sendAddRocketMqForCache(MqTagEnum.ADD.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                }
            }
            //Sunzhenxi
            binLogProducerService.sendBinLogMsg("incloud_base_mdm_user", "INSERT", mdmUserList);
            return true;
        } else {
            log.error("??????????????????-??????????????????????????????");
            return false;
        }
    }

    /**
     * ????????????
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
        //??????????????????
        MdmUserDetailDto mdmUserDetailDto = mdmUserDto.getMdmUserDetailDto();
        if (null != mdmUserDetailDto) {
            mdmUserDetailDto.setUserId(mdmUser.getId());
            MdmUserDetail mdmUserDetail = dozerMapper.map(mdmUserDetailDto, MdmUserDetail.class);
            mdmUserDetail.setUpdateTime(LocalDateTime.now());
            mdmUserDetailService.updateById(mdmUserDetail);
        }
        boolean result = super.updateById(mdmUser);
        if (result) {
            log.debug("???????????????????????????");
            MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUser.getParentDeptId());
            String topics[] = userTopics.split(",");
            //?????????Vo????????????mq
            MdmUserVo mdmUserVo = dozerMapper.map(mdmUserDto, MdmUserVo.class);
            mdmUserVo.setPassWord(null);
            if (null != checkLvTypeOrg.getLvType()) {  //???????????????????????????????????? ?????????????????????
                log.debug("??????????????????-????????????MQ");
                mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
            }
            if (null != checkLvTypeOrg.getOaLvType()) {  //???????????????????????????????????? ?????????????????????
                log.debug("??????????????????-????????????MQ");
                mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
            }
            //??????????????????????????????
//            mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        }
        //???????????????????????????????????????
        List<MdmUser> mdmUserList = new ArrayList<>();
        mdmUserList.add(mdmUser);
        mdmPublisher.publish(EventConstants.UpdateUserEvent, mdmUserList);
        //?????????????????? ????????????????????????  --??????????????????????????? ???????????????????????? ?????????????????????????????????????????? ????????????????????????????????????????????? ??????????????????
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
     * ??????ID??????
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
            //??????????????????
            LambdaQueryWrapper<MdmUserDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmUserDetail::getUserId, streamStr);//????????????long????????????????????????String
            mdmUserDetailService.remove(queryWrapper);
            //????????????????????????
            mdmPostUserService.deleteByUserId(streamStr);
            //????????????????????????
            mdmRoleUserService.deleteByUserId(streamStr);
            //??????neo4j???????????????
            //neoService.delNodeByMid(NeoNodeEnum.USER.code,streamStr);
            //?????????Vo????????????mq
            log.debug("??????????????????-????????????MQ");
            mdmMqService.sendRocketMq(userTopics, MqTagEnum.DEL.code, id);
            //??????????????????????????????
//            mdmMqService.sendAddRocketMqForCache(MqTagEnum.DEL.code,id);
            //Sunzhenxi
            binLogProducerService.sendBinLogMsg("incloud_base_mdm_user", "DELETE", removeUserList);
            return result;
        } else {
            throw new IncloudException("???????????????id???????????????");
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
            throw new IncloudException("??????id???????????????");
        }
        if (StringUtils.isBlank(mdmUserDto.getPassWord())) {
            throw new IncloudException("??????id???????????????");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("?????????????????????");
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
            throw new IncloudException("??????id???????????????");
        }
        if (StringUtils.isBlank(mdmUserDto.getPassWord())) {
            throw new IncloudException("??????id???????????????");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("?????????????????????");
        }
        if (StringUtils.isNotBlank(mdmUserDto.getPhoneNum()) && StringUtils.isNotBlank(mdmUser.getPhoneNum()) && !mdmUser.getPhoneNum().trim().equals(mdmUserDto.getPhoneNum().trim())) {
            throw new IncloudException("??????????????????????????????????????????????????????????????????????????????????????????");
        }
        String newPw = handlePw(mdmUserDto.getPassWord());
        LambdaUpdateWrapper<MdmUser> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(MdmUser::getId, mdmUserDto.getId());
        queryWrapper.set(MdmUser::getPassWord, newPw);
        boolean boo = this.update(queryWrapper);
        if (boo) {
            //??????token
            zuulClient.sysLogout();
            log.debug(mdmUser.getUserName() + ".?????????????????????");
        } else {
            log.debug(mdmUser.getUserName() + ".?????????????????????");
        }
        return boo;
    }

    /**
     * ????????????
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    @Transactional
    public Boolean updatePhoto(MdmUserDto mdmUserDto) {
        if (null == mdmUserDto.getId() && 0L == mdmUserDto.getId()) {
            throw new IncloudException("??????id???????????????");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("?????????????????????");
        }
        //????????????
        LambdaUpdateWrapper<MdmUser> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(MdmUser::getId, mdmUserDto.getId());
        queryWrapper.set(StringUtils.isNotBlank(mdmUserDto.getPhotoFileId()), MdmUser::getPhotoFileId, mdmUserDto.getPhotoFileId());
        boolean boo = this.update(queryWrapper);
        if (boo) {
            log.debug(mdmUser.getUserName() + ".?????????????????????");
        } else {
            log.debug(mdmUser.getUserName() + ".?????????????????????");
        }
        return boo;
    }

    /**
     * ??????????????????mq
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
        //??????????????????????????????
//        mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
    }

    /**
     * ????????????
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    @SneakyThrows
    @Transactional
    public Boolean updateEmail(MdmUserDto mdmUserDto) {
        if (null == mdmUserDto.getId() && 0L == mdmUserDto.getId()) {
            throw new IncloudException("??????id???????????????");
        }
        if (StringUtils.isBlank(mdmUserDto.getEmail())) {
            throw new IncloudException("?????????????????????");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("?????????????????????");
        }
        //????????????
        LambdaUpdateWrapper<MdmUser> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(MdmUser::getId, mdmUserDto.getId());
        queryWrapper.set(MdmUser::getEmail, mdmUserDto.getEmail());
        boolean boo = this.update(queryWrapper);
        if (boo) {
            log.debug(mdmUser.getUserName() + ".?????????????????????");
            mdmUser.setEmail(mdmUserDto.getEmail());
            this.sendMqForEntity(mdmUser);
        } else {
            log.debug(mdmUser.getUserName() + ".?????????????????????");
        }
        return boo;
    }

    /**
     * ???????????????
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    @SneakyThrows
    @Transactional
    public Boolean updatePhoneNum(MdmUserDto mdmUserDto) {
        if (null == mdmUserDto.getId() && 0L == mdmUserDto.getId()) {
            throw new IncloudException("??????id???????????????");
        }
        if (StringUtils.isBlank(mdmUserDto.getPhoneNum())) {
            throw new IncloudException("????????????????????????");
        }
        MdmUser mdmUser = mdmUserMapper.selectById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("?????????????????????");
        }

        LambdaUpdateWrapper<MdmUser> queryWrapper = new LambdaUpdateWrapper<>();
        //???????????????????????????
        queryWrapper.eq(MdmUser::getPhoneNum, mdmUserDto.getPhoneNum());
        queryWrapper.eq(MdmUser::getStatus, StatusEnum.RUNNING.code);
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(queryWrapper);
        if (mdmUsers.size() > 0) {
            throw new IncloudException("???????????????????????????????????????????????????????????????");
        }
        //???????????????
        queryWrapper.clear();
        queryWrapper.eq(MdmUser::getId, mdmUserDto.getId());
        queryWrapper.set(MdmUser::getPhoneNum, mdmUserDto.getPhoneNum());
        boolean boo = this.update(queryWrapper);
        if (boo) {
            //?????????Vo????????????mq
            mdmUser.setPhoneNum(mdmUserDto.getPhoneNum());
            this.sendMqForEntity(mdmUser);
            log.debug(mdmUser.getUserName() + ".????????????????????????");
        } else {
            log.debug(mdmUser.getUserName() + ".????????????????????????");
        }
        return boo;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean setMasterPost(MdmTransferDto mdmTransferDto) {
        //????????????????????????????????????????????????????????????
        if (null == mdmTransferDto.getPostId() || 0L == mdmTransferDto.getPostId()) {
            mdmPostUserService.delByUserIdAndIsMaster(mdmTransferDto.getUserId(), YesNo.YES.code);
        } else {
            //??????????????????????????????
            MdmPostUser mdmPostUser = mdmPostUserService.getMaster(mdmTransferDto.getUserId());
            MdmUser mdmUser = super.getById(mdmTransferDto.getUserId());
            if (null == mdmUser) {
                throw new IncloudException("???????????????????????????");
            }
            MdmPost mdmPost = mdmPostService.getById(mdmTransferDto.getPostId());
            if (null == mdmPost) {
                throw new IncloudException("???????????????????????????");
            }
            //????????????????????? ??????????????????
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
                //neoService.mergeUserPostRel(mdmUser,mdmPost,_mdmPostUser);//??????neo4j??????
            } else {
                mdmPostUser.setPostCode(mdmTransferDto.getPostCode());
                mdmPostUser.setPostName(mdmTransferDto.getPostName());
                mdmPostUser.setPostId(mdmTransferDto.getPostId());
                mdmPostUser.setOrgFullPostId(mdmTransferDto.getOrgFullPostId());
                mdmPostUser.setUpdateTime(LocalDateTime.now());
                mdmPostUserService.updateById(mdmPostUser);
                mdmPostService.isRef(mdmPostUser.getPostId());
                //neoService.delUserPostRel(mdmUser,YesNo.YES.code);//????????????
                //neoService.mergeUserPostRel(mdmUser,mdmPost,mdmPostUser);//??????????????????
            }
        }
//        log.debug("?????????????????????-????????????MQ");
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
            //??????????????????????????????
            MdmPostUser mdmPostUser = mdmPostUserService.getMaster(mdmUserDto.getId());
            MdmPostVo mdmPostVo = mdmPostService.get(mdmPostUserDto.getPostId());
            if (null == mdmPostVo) {
                throw new IncloudException("???????????????????????????");
            }
            //????????????????????? ??????????????????
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
            //???????????? ????????????????????? ?????????
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
            //??????????????????????????????
            MdmDutyUser mdmDutyUser = mdmDutyUserService.getMaster(mdmUserDto.getId());
            MdmDutyVo mdmDutyVo = mdmDutyService.get(mdmDutyUserDto.getDutyId());
            if (null == mdmDutyVo) {
                throw new IncloudException("???????????????????????????");
            }
            //????????????????????? ??????????????????
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
            //???????????? ???????????????????????? ?????????
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
            throw new IncloudException("???????????????????????????");
        }
        //??????userId ????????????????????????????????????
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
            log.debug("???????????? - ???????????????????????????");
        }
        //????????????????????? ??? ?????????????????????  ?????????????????? ????????????????????? ??????????????????
        MdmUserDto _mdmUserDto = dozerMapper.map(mdmUser, MdmUserDto.class);
        _mdmUserDto.setMdmPostUserDto(mdmUserDto.getMdmPostUserDto());
        _mdmUserDto.setMdmDutyUserDto(mdmUserDto.getMdmDutyUserDto());
        transferSetMasterPost(_mdmUserDto);
        //????????????????????? ??? ?????????????????????  ?????????????????? ????????????????????? ??????????????????
        transferSetMasterDuty(_mdmUserDto);
        //????????????????????? ??????mq
        log.debug("?????????????????????-????????????MQ");
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
        //??????????????????????????????
//        mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        return result;
    }

    public void checkTransferDuty(MdmDutyUserDto mdmDutyUserDto) {
        if (null == mdmDutyUserDto.getDutyId()) {
            throw new IncloudException("??????ID???????????????");
        } else if (StringUtils.isBlank(mdmDutyUserDto.getDutyCode())) {
            throw new IncloudException("??????CODE???????????????");
        } else if (StringUtils.isBlank(mdmDutyUserDto.getDutyName())) {
            throw new IncloudException("???????????????????????????");
        }
    }

    public void checkTransferPost(MdmPostUserDto mdmPostUserDto) {
        if (null == mdmPostUserDto.getPostId()) {
            throw new IncloudException("??????ID???????????????");
        } else if (StringUtils.isBlank(mdmPostUserDto.getPostCode())) {
            throw new IncloudException("??????CODE???????????????");
        } else if (StringUtils.isBlank(mdmPostUserDto.getPostName())) {
            throw new IncloudException("???????????????????????????");
        }
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean transferOrg(MdmUserDto mdmUserDto) {
        MdmUser mdmUser = super.getById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("???????????????????????????");
        }
        //??????userId ?????????????????????????????????????????????
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
            log.debug("???????????? - ???????????????????????????");
        }
        //????????????????????? ??? ?????????????????????  ?????????????????? ????????????????????? ??????????????????
        MdmUserDto _mdmUserDto = dozerMapper.map(mdmUser, MdmUserDto.class);
        _mdmUserDto.setMdmPostUserDto(mdmUserDto.getMdmPostUserDto());
        _mdmUserDto.setMdmDutyUserDto(mdmUserDto.getMdmDutyUserDto());
        transferSetMasterPost(_mdmUserDto);
        //????????????????????? ????????????????????????  ?????????????????? ????????????????????? ??????????????????
        transferSetMasterDuty(_mdmUserDto);
        log.debug("?????????????????????-????????????MQ");
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
        //??????????????????????????????
//        mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
        return result;
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean setAndPost(List<MdmTransferDto> mdmTransferDtoList) {
        //??????userId ?????????????????????????????????
        mdmPostUserService.delByUserIdAndIsMaster(mdmTransferDtoList.get(0).getUserId(), YesNo.NO.code);
        MdmUser mdmUser = super.getById(mdmTransferDtoList.get(0).getUserId());
        if (null == mdmUser) {
            throw new IncloudException("???????????????????????????");
        }
        //???neo4j?????????
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
                    throw new IncloudException("???????????????????????????");
                }
                //neoService.mergeUserPostRel(mdmUser,mdmPost,_mdmPostUser);//??????????????????
            }
            if (CollectionUtil.isNotEmpty(resultList)) {
                mdmPostUserService.saveBatch(resultList);
                log.debug("?????????????????????-????????????MQ");
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
            log.debug("???????????? - ???????????????????????????");
            return true;
        }
        throw new IncloudException("?????????????????????");
    }

    @Override
    @Transactional
    @SneakyThrows
    public Boolean orgTransfer(List<MdmOrg> mdmOrgList) {
        if (CollectionUtil.isNotEmpty(mdmOrgList)) {
            //???????????? ??????????????????
            List<MdmOrg> deptOrgList = mdmOrgList.stream().filter(d -> d.getOrgType() == OrgTypeEnum.DEPT.code).collect(Collectors.toList());
            //????????????????????????
            if (CollectionUtil.isNotEmpty(deptOrgList)) {
                LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
                List<Long> deptIds = deptOrgList.stream().map(MdmOrg::getId).collect(Collectors.toList());
                queryWrapper.in(MdmUser::getParentDeptId, deptIds);
                List<MdmUser> mdmUserList = mdmUserMapper.selectList(queryWrapper);
                if (CollectionUtil.isNotEmpty(mdmUserList)) {
                    //???????????? ????????????????????????
                    Map<Long, List<MdmOrg>> mapGroup = deptOrgList.stream().collect(Collectors.groupingBy(MdmOrg::getId));
                    for (MdmUser mdmUser : mdmUserList) {
                        List<MdmOrg> deptList = mapGroup.get(mdmUser.getParentDeptId());
                        MdmOrg mdmOrg = deptList.get(0);
                        mdmUser.setParentOrgId(mdmOrg.getParentOrgId());
                        mdmUser.setParentOrgName(mdmOrg.getParentOrgName());
                        mdmUser.setParentDeptName(mdmOrg.getOrgName());//deptId ????????? ????????????
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
                            //??????????????????????????????
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
        //????????????
        if (data.containsKey(EventConstants.UpdateParentOrgEvent)) {
            List<MdmOrg> mdmOrgList = (List) data.get(EventConstants.UpdateParentOrgEvent);
            log.info("???????????????????????????{}", JacksonUtil.toJSONString(mdmOrgList));
            this.orgTransfer(mdmOrgList);
        }
        //??????????????????
        if (data.containsKey(EventConstants.UpdateNameOrgEvent)) {
            List<MdmOrg> mdmOrgList = (List) data.get(EventConstants.UpdateNameOrgEvent);
            log.info("?????????????????????????????????{}", JacksonUtil.toJSONString(mdmOrgList));
            this.orgTransfer(mdmOrgList);
        }
        //????????????
        if (dtoData.containsKey(EventConstants.BrokenOrgEvent)) {
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.BrokenOrgEvent);
            List<MdmUserDto> mdmUserDtos = mdmOrgStatusDto.getMdmUserDtos();
            log.info("???????????????????????????{}", JacksonUtil.toJSONString(mdmUserDtos));
            this.orgSplit(mdmUserDtos);
        }

        //????????????
        if (dtoData.containsKey(EventConstants.CanceledOrgEvent)) {
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.CanceledOrgEvent);
            List<MdmUserDto> mdmUserDtos = mdmOrgStatusDto.getMdmUserDtos();
            log.info("???????????????????????????{}", JacksonUtil.toJSONString(mdmUserDtos));
            this.orgSplit(mdmUserDtos);
        }

        //???????????? ??????????????????????????? ??? ?????????????????????
        if (dtoData.containsKey(EventConstants.UpdateUserEvent)) {
            log.info("???????????? ???????????????????????????!");
            List<MdmUser> mdmUserList = (List) dtoData.get(EventConstants.UpdateUserEvent);
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                MdmUser mdmUser = mdmUserList.get(0);
                //??????????????????
                mdmRoleUserService.updateRoleUserInfoByUserId(mdmUser);
                //??????????????????
                mdmPostUserService.updatePostUserInfoByUserId(mdmUser);
            }
        }
        //????????????????????????????????????????????????
        if (dtoData.containsKey(EventConstants.UpdateRoleEvent)) {
            log.info("???????????? ???????????????????????????!");
            List<MdmRole> mdmRoleList = (List) dtoData.get(EventConstants.UpdateRoleEvent);
            if (CollectionUtil.isNotEmpty(mdmRoleList)) {
                MdmRole mdmRole = mdmRoleList.get(0);
                //????????????
                mdmRoleUserService.updateRoleUserInfoByRoleId(mdmRole);
                //????????????
                mdmRolePostService.updateRolePostInfoByRoleId(mdmRole);
                //????????????
                mdmRoleResourceService.updateRoleResInfoByRoleId(mdmRole);
            }
        }
        //???????????? ???????????????????????????
        if (dtoData.containsKey(EventConstants.UpdatePostEvent)) {
            log.info("???????????? ???????????????????????????!");
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
            //???????????? ????????????????????????
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
                log.debug("????????????/???????????? - ???????????????????????????");
            }
            //?????????????????????
            for (MdmUserDto mdmUserDto : mdmUserDtoList) {
                //??????
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
                    //????????????????????? ??????????????????????????????
                    mdmPostUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.YES.code);
                }
                //??????
                MdmDutyUserDto mdmDutyUserDto = mdmUserDto.getMdmDutyUserDto();
                if (null != mdmDutyUserDto) {
                    Long dutyId = mdmDutyUserDto.getId();
                    mdmUserDto.setDutyIds(String.valueOf(dutyId));
                    this.setMasterDuty(mdmUserDto);
                } else {
                    //????????????????????? ??????????????????????????????
                    mdmDutyUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.YES.code);
                }
            }
            //???????????? ????????????????????????????????? ???????????????????????????mq
            if (CollectionUtil.isNotEmpty(mdmUserList)) {
                log.debug("????????????-????????????-????????????MQ");
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
                    //??????????????????????????????
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
            throw new IncloudException("???????????????sourceId????????????????????????????????????");
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
                throw new IncloudException("???????????????targetId??????????????????????????????");
            }
            Integer targetIndex = target.getSort();
            List<MdmUser> otherSortList = getListByParent(source.getParentDeptId(), true, source.getId());
            List<MdmUser> collect = otherSortList.stream().filter(mdmOrg -> mdmOrg.getSort() >= targetIndex).collect(Collectors.toList());
            list = sortOtherList(collect, targetIndex);
            source.setSort(targetIndex);
        }
        //????????????????????????
        MdmUser mdmUser = dozerMapper.map(source, MdmUser.class);
        list.add(mdmUser);
        boolean result = updateBatchById(list);
        if (result) {
            this.sendMqForUsers(list);
            log.info("?????????????????????");
        }
        return true;
    }

    /**
     * ??????????????????????????????????????????mq
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
                //??????????????????????????????
//                mdmMqService.sendAddRocketMqForCache(MqTagEnum.EDIT.code,JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
            }
        }
    }

    /**
     * ????????????????????????????????????????????????????????????
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
     * ????????????????????????index?????????
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
     * ???????????????ID????????????????????????sort???????????????
     *
     * @param parentId
     * @param isAsc
     * @param exclusiveId,??????????????????
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
            throw new IncloudException("???????????????sourceId????????????????????????????????????");
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
                throw new IncloudException("???????????????targetId??????????????????????????????");
            }
            Integer targetGlobalSort = target.getGlobalSort();
            Integer targetGlobalSortSecond = target.getGlobalSortSecond();
            List<MdmUser> otherSortList = getListByGlobalSort(targetGlobalSort, true);
            List<MdmUser> collect = otherSortList.stream().filter(mdmOrg -> mdmOrg.getSort() >= targetGlobalSortSecond).collect(Collectors.toList());
            list = sortOtherListForGlobal(collect, targetGlobalSortSecond);
            source.setGlobalSort(targetGlobalSort);
            source.setGlobalSortSecond(targetGlobalSortSecond);
        }
        //????????????????????????
        MdmUser mdmUser = dozerMapper.map(source, MdmUser.class);
        list.add(mdmUser);
        boolean result = updateBatchById(list);
        if (result) {
            this.sendMqForUsers(list);
            log.info("?????????????????????");
        }
        return true;
    }

    /**
     * ??????glabalSort ??????????????????????????????????????? ????????????globalSortSecond ??????
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
     * ????????????????????????index????????? -??????????????????
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
     * ?????????????????????????????? ???globalSort
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
        log.info("??????:>>>>>>>userName:{}", userName);
        MdmUser mdmUser = null;
        if (userName.contains(LoginEnum.EMAIL.code)) { //????????????
            String emailArr[] = userName.split(LoginEnum.EMAIL.code);
            mdmUser = this.findByEmail(emailArr[1]);
        } else if (userName.contains(LoginEnum.PHONE.code)) {//???????????????
            String phoneArr[] = userName.split(LoginEnum.PHONE.code);
            mdmUser = this.findByPhone(phoneArr[1]);
        } else if (userName.contains(LoginEnum.IDNUMBER.code)) {//??????????????????
            String idnumerArr[] = userName.split(LoginEnum.IDNUMBER.code);
            mdmUser = this.findByIdNumber(idnumerArr[1]);
        } else if (userName.contains(LoginEnum.VERIFYCODE.code)) {//???????????????
            String idnumerArr[] = userName.split(LoginEnum.VERIFYCODE.code);
            mdmUser = this.findByPhone(idnumerArr[1]);
            this.checkUserNotNull(mdmUser);
            //??????????????????
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
            throw new IncloudException("????????????????????????");
        }
    }


    @Override
    public MdmUser findByPhone(String phoneNum) {
        if (StringUtils.isBlank(phoneNum)) {
            throw new IncloudException("????????????????????????");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getPhoneNum, phoneNum);
        MdmUser mdmUser = mdmUserMapper.selectOne(queryWrapper);
        if (null != mdmUser) {
            if (UserStatusEnum.QUIT.code == mdmUser.getStatus()) {
                throw new IncloudException("?????????????????????");
            }
        }
        return mdmUser;
    }

    @Override
    public MdmUser findByPhoneForMsg(String phoneNum) {
        if (StringUtils.isBlank(phoneNum)) {
            throw new IncloudException("????????????????????????");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getPhoneNum, phoneNum);
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(queryWrapper);
        //?????????????????? ????????????????????????
        if (mdmUsers.size() == 1) {
            if (UserStatusEnum.QUIT.code.equals(mdmUsers.get(0).getStatus())) {
                throw new IncloudException("?????????????????????");
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
                throw new IncloudException("????????????????????????????????????????????????????????????????????????????????????");
            }
        }
        return null;
    }

    @Override
    public MdmUser findByIdNumber(String idNumber) {
        if (StringUtils.isBlank(idNumber)) {
            throw new IncloudException("???????????????????????????");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getIdCard, idNumber);
        MdmUser mdmUser = mdmUserMapper.selectOne(queryWrapper);
        return mdmUser;
    }

    @Override
    public MdmUser findByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IncloudException("?????????????????????");
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
            throw new IncloudException("??????id???????????????");
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
            throw new IncloudException("???????????????????????????");
        }
        if (StringUtils.isBlank(mdmUserDto.getDutyIds())) {
            mdmDutyUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.YES.code);
        } else {
            List<String> streamStr = Stream.of(mdmUserDto.getDutyIds().split(",")).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(streamStr)) {
                List<MdmDuty> dutyList = mdmDutyService.getByIds(streamStr); //???????????????????????????
                if (CollectionUtil.isEmpty(dutyList)) {
                    throw new IncloudException("???????????????????????????");
                }
                MdmDuty mdmDuty = dutyList.get(0);
                //?????????????????????????????????
                MdmDutyUser mdmDutyUser = mdmDutyUserService.getMaster(mdmUserDto.getId());
                //????????????????????? ??????????????????
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
                    //neoService.mergeUserPostRel(mdmUser,mdmPost,_mdmPostUser);//??????neo4j?????? ???????????????
                } else {
                    mdmDutyUser.setDutyCode(mdmDuty.getDutyCode());
                    mdmDutyUser.setDutyName(mdmDuty.getDutyName());
                    mdmDutyUser.setDutyId(mdmDuty.getId());
                    mdmDutyUser.setOrgFullDutyId(mdmDuty.getOrgFullId());
                    mdmDutyUser.setUpdateTime(LocalDateTime.now());
                    mdmDutyUserService.updateById(mdmDutyUser);
                    //neoService.delUserPostRel(mdmUser,YesNo.YES.code);//????????????
                    mdmDutyService.isRef(mdmDutyUser.getDutyId());
                    //neoService.mergeUserPostRel(mdmUser,mdmPost,mdmPostUser);//?????????????????? ???????????????
                }
            }
        }
        log.debug("??????????????????-????????????MQ");
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
        //??????userId ?????????????????????????????????
        mdmDutyUserService.delByUserIdAndIsMaster(mdmUserDto.getId(), YesNo.NO.code);
        //check user
        MdmUser mdmUser = super.getById(mdmUserDto.getId());
        if (null == mdmUser) {
            throw new IncloudException("???????????????????????????");
        }
        if (StringUtils.isBlank(mdmUserDto.getDutyIds())) {
            throw new IncloudException("?????????id?????????????????????");
        }
        List<String> streamStr = Stream.of(mdmUserDto.getDutyIds().split(",")).collect(Collectors.toList());
        //check duty
        if (CollectionUtil.isNotEmpty(streamStr)) {
            List<MdmDuty> dutyList = mdmDutyService.getByIds(streamStr);
            if (CollectionUtil.isEmpty(dutyList)) {
                throw new IncloudException("???????????????????????????");
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
                //neoService.mergeUserPostRel(mdmUser,mdmPost,_mdmPostUser);//?????????????????? ???????????????
            }
            if (CollectionUtil.isNotEmpty(resultList)) {
                mdmDutyUserService.saveBatch(resultList);
                log.debug("???????????? - ???????????????????????????");
                log.debug("????????????????????????-????????????MQ");
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
        throw new IncloudException("?????????????????????");
    }

    @Override
    public List<MdmUserVo> getUserByDeptId(String deptId) {
        if (StringUtils.isBlank(deptId)) {
            throw new IncloudException("??????id???????????????");
        }
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmUser::getParentDeptId, deptId);
        //?????????????????? ?????????????????????  -- ?????????????????? ?????????????????? -????????????????????????-???????????? ??????????????????????????????????????????
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
        log.debug("????????????");
        return mdmUserVo;
    }

    @Override
    public List<MdmUserVo> getUserByIdCards(String idCards) {
        if (StringUtils.isBlank(idCards)) {
            throw new IncloudException("??????????????????????????????");
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
                throw new IncloudException("?????????????????????");
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
            throw new IncloudException("????????????????????????");
        }
        MdmUserVo mdmUserVo = mdmUserMapper.getByIdCard(idCard);
        return mdmUserVo;
    }

    @Transactional
    @Override
    public MdmUserVo saveStudyUser(MdmUserDto mdmUserDto) {
        //????????????????????????
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();//???????????????
        queryWrapper.in(MdmUser::getUserName, mdmUserDto.getUserName());
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(mdmUsers)) {
            setStudyUserRole(mdmUserDto, mdmUsers.get(0));
            return dozerMapper.map(mdmUsers.get(0), MdmUserVo.class);
        }
        LambdaQueryWrapper<MdmUser> iQueryWrapper = new LambdaQueryWrapper<>();//???????????????
        iQueryWrapper.in(MdmUser::getIdCard, mdmUserDto.getIdCard());
        List<MdmUser> _mdmUsers = mdmUserMapper.selectList(iQueryWrapper);
        if (CollectionUtil.isNotEmpty(_mdmUsers)) {
            setStudyUserRole(mdmUserDto, _mdmUsers.get(0));
            return dozerMapper.map(_mdmUsers.get(0), MdmUserVo.class);
        }
        //?????????????????????????????????
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
     * ?????????????????????????????????
     *
     * @param mdmUserDto
     * @param mdmUser
     */
    @Transactional
    public void setStudyUserRole(MdmUserDto mdmUserDto, MdmUser mdmUser) {
        //??????????????????????????????
        int type = mdmUserDto.getStudyUserRole().intValue();
        LambdaQueryWrapper<MdmRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRole::getRoleCode, StudyEnum.getMessage(type));
        List<MdmRole> mdmRoles = mdmRoleMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(mdmRoles)) {
            throw new IncloudException("??????????????????????????????????????????????????????");
        }
        MdmRole mdmRole = mdmRoles.get(0);

        //????????????????????????
        MdmRoleUser mdmRoleUser = new MdmRoleUser();
        mdmRoleUser.setRoleId(mdmRole.getId());
        mdmRoleUser.setRoleCode(mdmRole.getRoleCode());
        mdmRoleUser.setRoleName(mdmRole.getRoleName());
        mdmRoleUser.setUserId(mdmUser.getId());
        mdmRoleUser.setUserName(mdmUser.getUserName());
        mdmRoleUser.setUserNameCh(mdmUser.getUserNameCh());
        mdmRoleUser.setCreateTime(LocalDateTime.now());
        mdmRoleUser.setUpdateTime(LocalDateTime.now());
        //????????????????????????
        mdmRoleUserService.save(mdmRoleUser);
    }

    @Override
    @Transactional
    public Boolean deleteStudyUser(String id) {
        if (StringUtils.isNotBlank(id)) {
            List<String> ids = Stream.of(id.split(",")).collect(Collectors.toList());
            List<MdmUser> mdmUsers = super.listByIds(ids);
            //????????????????????????
            Map<Integer, List<MdmUser>> list = mdmUsers.stream().collect(Collectors.groupingBy(MdmUser::getUserClass));
            //?????????edu?????????
            List<MdmUser> studyMdmUsers = list.get(UserClassEnum.EDU.code);
            if (CollectionUtil.isNotEmpty(studyMdmUsers)) {
                //????????????edu???????????????????????????????????????????????????edu??????????????????
                List<Long> delIds = studyMdmUsers.stream().map(MdmUser::getId).collect(Collectors.toList());
                boolean boo = super.removeByIds(delIds);
            }
            //?????????????????????edu?????????????????????????????????
            LambdaQueryWrapper<MdmRoleUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmRoleUser::getUserId, ids).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_ADMIN.message)
                    .or()
                    .in(MdmRoleUser::getUserId, ids).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_TEACHER.message)
                    .or()
                    .in(MdmRoleUser::getUserId, ids).eq(MdmRoleUser::getRoleCode, StudyEnum.STUDY_STUDENT.message);
            boolean result = mdmRoleUserService.remove(queryWrapper);
            if (result) {
                log.debug("??????????????????????????????");
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
            //??????????????????????????????
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
     * ??????
     *
     * @param mdmUserDto
     * @return
     */
    @Override
    public MdmUserVo regStudyUser(MdmUserDto mdmUserDto) {
        //????????????????????????
        LambdaQueryWrapper<MdmUser> queryWrapper = new LambdaQueryWrapper<>();//???????????????
        queryWrapper.in(MdmUser::getUserName, mdmUserDto.getUserName());
        List<MdmUser> mdmUsers = mdmUserMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(mdmUsers)) {
            setStudyUserRole(mdmUserDto, mdmUsers.get(0));
            return dozerMapper.map(mdmUsers.get(0), MdmUserVo.class);
        }
        //????????????????????????????????????????????????????????????????????????????????????????????????
        LambdaQueryWrapper<MdmUser> iQueryWrapper = new LambdaQueryWrapper<>();//???????????????
        iQueryWrapper.in(MdmUser::getIdCard, mdmUserDto.getIdCard());
        List<MdmUser> _mdmUsers = mdmUserMapper.selectList(iQueryWrapper);
        if (CollectionUtil.isNotEmpty(_mdmUsers)) {
            setStudyUserRole(mdmUserDto, _mdmUsers.get(0));
            return dozerMapper.map(_mdmUsers.get(0), MdmUserVo.class);
        }
        //??????????????????????????????null
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
            throw new IncloudException("??????id???????????????");
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
                //????????????
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
                            postIsMaster.append("??????");
                        } else {
                            postIsMaster.append("??????");
                        }
                        postIsMaster.append(";");
                    });
                }
                //????????????
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
                            dutyIsMaster.append("?????????");
                        } else {
                            dutyIsMaster.append("?????????");
                        }
                        postIsMaster.append(";");
                    });
                }

                //????????????
                MdmUserExcel mdmUserExcel = new MdmUserExcel();
                dozerMapper.map(mdmUserVo, mdmUserExcel);
                //??????
                if ("156".equals(mdmUserVo.getNationality())) {
                    mdmUserExcel.setNationality("??????");
                }
                //????????????
                mdmUserExcel.setCardType(CardTypeEnum.getMessage(mdmUserVo.getCardType()));
                //??????
                mdmUserExcel.setSex(SexEnum.getMessage(mdmUserVo.getSex()));
                //??????
                if ("01".equals(mdmUserVo.getNation())) {
                    mdmUserExcel.setNation("??????");
                }
                //????????????
                if (ObjectUtil.isNotNull(mdmUserVo.getBirthday())) {
                    DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String date = dtf2.format(mdmUserVo.getBirthday());
                    mdmUserExcel.setBirthday(date);
                }
                //????????????
                if (StringUtils.isNotBlank(mdmUserVo.getMarriageStatus())) {
                    mdmUserExcel.setMarriageStatus(MarriageStatusEnum.getMessage(Integer.valueOf(mdmUserVo.getMarriageStatus())));
                }
                //??????
                if (mdmUserVo.getStatus().equals(YesNo.YES.code)) {
                    mdmUserExcel.setStatus("???");
                } else {
                    mdmUserExcel.setStatus("???");
                }
                //????????????
                if (ObjectUtil.isNotNull(mdmUserVo.getBirthNature())) {
                    mdmUserExcel.setBirthNature(BirthNatureEnum.getMessage(mdmUserVo.getBirthNature()));
                }
                //????????????
                mdmUserExcel.setPostName(postName.toString());
                mdmUserExcel.setOrgFullPostName(orgFullPostName.toString());
                mdmUserExcel.setPostIsMaster(postIsMaster.toString());
                //????????????
                mdmUserExcel.setDutyName(dutyName.toString());
                mdmUserExcel.setOrgFullDutyName(orgFullDutyName.toString());
                mdmUserExcel.setDutyIsMaster(dutyIsMaster.toString());

                mdmUserExcels.add(mdmUserExcel);
            });
        }
        long endTime = System.currentTimeMillis();
        log.debug("??????????????????excel????????????:{}???--------------------------------------", (endTime - startTime) / 1000);
        return mdmUserExcels;
    }

    @Override
    public List<MdmUserVo> getUserByParentOrgId(String orgIds) {
        if (StringUtils.isBlank(orgIds)) {
            throw new IncloudException("??????id???????????????");
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
            throw new IncloudException("??????id???????????????");
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