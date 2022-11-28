package com.netwisd.biz.study.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.UserClassEnum;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.util.AesUtil;
import com.netwisd.base.common.util.IdCartUtils;
import com.netwisd.base.common.util.PhoneUtil;
import com.netwisd.biz.study.constants.ApplyStatus;
import com.netwisd.biz.study.constants.UserType;
import com.netwisd.biz.study.constants.YesNo;
import com.netwisd.biz.study.entity.StudyUser;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.StudyUserMapper;
import com.netwisd.biz.study.service.StudyUserService;
import com.netwisd.biz.study.util.StudyUserUtil;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyUserDto;
import com.netwisd.biz.study.vo.StudyUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 在线学习人员表 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-11-22 09:59:58
 */
@Service
@Slf4j
public class StudyUserServiceImpl extends BatchServiceImpl<StudyUserMapper, StudyUser> implements StudyUserService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyUserMapper studyUserMapper;
    @Autowired
    private StudyUserUtil studyUserUtil;
    @Autowired
    private MdmClient mdmClient;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //默认组织路径
    private static Long defaultOrgId = 0L;

    private static String defaultOrgName = "root";

    private static Integer defaultSort = 1;


    /**
    * 单表简单查询操作
    * @param studyUserDto
    * @return
    */
    @Override
    public Page list(StudyUserDto studyUserDto,Boolean isCheck) {
        LambdaQueryWrapper<StudyUser> queryWrapper = new LambdaQueryWrapper<>();
        //中文姓名模糊查询
        queryWrapper.like(StringUtils.isNotBlank(studyUserDto.getUserNameCh()),StudyUser::getUserNameCh,studyUserDto.getUserNameCh());
        //账号模糊查询
        queryWrapper.like(StringUtils.isNotBlank(studyUserDto.getUserName()),StudyUser::getUserName,studyUserDto.getUserName());
        //是否是审核列表
        if(isCheck){
            queryWrapper.eq(StudyUser::getUserStatus,YesNo.NO.code.intValue());
        }else {
            queryWrapper.eq(StudyUser::getUserStatus,YesNo.YES.code.intValue());
        }
        Page<StudyUser> page = studyUserMapper.selectPage(studyUserDto.getPage(),queryWrapper);
        Page<StudyUserVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyUserVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyUserDto
    * @return
    */
    @Override
    public List<StudyUserVo> lists(StudyUserDto studyUserDto) {
        LambdaQueryWrapper<StudyUser> queryWrapper = new LambdaQueryWrapper<>();
        //审核通过人员
        queryWrapper.eq(StudyUser::getUserStatus,YesNo.YES.code.intValue());
        //人员类型
        queryWrapper.eq(ObjectUtil.isNotEmpty(studyUserDto.getUserType()),StudyUser::getUserType,studyUserDto.getUserType());
        queryWrapper.like(StringUtils.isNotBlank(studyUserDto.getUserName()),StudyUser::getUserName,studyUserDto.getUserName());
        queryWrapper.like(StringUtils.isNotBlank(studyUserDto.getUserNameCh()),StudyUser::getUserNameCh,studyUserDto.getUserNameCh());
        List<StudyUser> studyUsers = studyUserMapper.selectList(queryWrapper);
        List<StudyUserVo> studyUserVos = DozerUtils.mapList(dozerMapper, studyUsers, StudyUserVo.class);
        log.debug("查询条数:"+studyUsers.size());
        return studyUserVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyUserVo get(Long id) {
        StudyUser studyUser = super.getById(id);
        StudyUserVo studyUserVo = null;
        if(studyUser !=null){
            studyUserVo = dozerMapper.map(studyUser,StudyUserVo.class);
        }
        log.debug("查询成功");
        return studyUserVo;
    }

    /**
    * 保存实体
    * @param studyUserDto
    * @return
    */
    @Transactional
    @Override
    public Result<Boolean> save(StudyUserDto studyUserDto) {
        //校验身份证号
        IdCartUtils.IDCardValidate(studyUserDto.getIdCard());
        //校验手机号
        PhoneUtil.isMobileNO(studyUserDto.getPhoneNum());
        //校验用户名和证件号是否唯一
        checkUserUnique(studyUserDto);
        //默认人员启用
        studyUserDto.setStatus(YesNo.YES.code);
        //默认已审核
        studyUserDto.setUserStatus(YesNo.YES.code);
        //设置密码加密
        studyUserDto.setPassWord(handlePw(studyUserDto.getPassWord()));
        //同步至主数据人员表,
        MdmUserVo mdmUserVo = syncMdmUser(studyUserDto,false);
        //保存本地人员表
        StudyUser studyUser = dozerMapper.map(mdmUserVo, StudyUser.class);
        studyUser.setDescription(studyUserDto.getDescription());
        studyUser.setUserStatus(studyUserDto.getUserStatus());
        studyUser.setStatus(studyUserDto.getStatus());
        studyUser.setUserType(studyUserDto.getUserType());
        studyUser.setMasterDataId(mdmUserVo.getId());
        studyUser.setCreateTime(LocalDateTime.now());
        studyUser.setUpdateTime(LocalDateTime.now());
        studyUser.setCreateUserId(studyUserDto.getCreateUserId());
        studyUser.setCreateUserName(studyUserDto.getCreateUserName());
        studyUser.setCreateUserParentOrgId(studyUserDto.getCreateUserParentOrgId());
        studyUser.setCreateUserParentOrgName(studyUserDto.getCreateUserParentOrgName());
        studyUser.setCreateUserParentDeptId(studyUserDto.getCreateUserParentDeptId());
        studyUser.setCreateUserParentDeptName(studyUserDto.getCreateUserParentDeptName());
        studyUser.setCreateUserOrgFullId(studyUserDto.getCreateUserOrgFullId());
        boolean result = this.save(studyUser);
        if (UserClassEnum.EDU.code.intValue() != mdmUserVo.getUserClass().intValue()){
            //throw new IncloudException("此用户已存在："+ studyUser.getUserName());
            return Result.failed("此用户已存在："+ studyUser.getUserName());
        }
        if(result){
            log.debug("保存成功");
        }
        return Result.success(result);
    }
    //处理密码
    public String handlePw(String aesPw) {
        //解前端加密
        String decrypetPasword = AesUtil.aesDecrypt(aesPw);
        log.debug("解析密码为:" + decrypetPasword);
        //重新非对称加密
        return bCryptPasswordEncoder.encode(decrypetPasword);
    }
    /**
     * 同步主数据人员
     * @param studyUserDto
     */
    private MdmUserVo syncMdmUser(StudyUserDto studyUserDto,Boolean isRegister) {
        MdmUserDto mdmUserDto = dozerMapper.map(studyUserDto, MdmUserDto.class);
        mdmUserDto.setParentOrgId(defaultOrgId);
        mdmUserDto.setParentOrgName(defaultOrgName);
        mdmUserDto.setParentDeptId(defaultOrgId);
        mdmUserDto.setParentDeptName(defaultOrgName);
        mdmUserDto.setOrgFullId(defaultOrgId.toString());
        mdmUserDto.setOrgFullName(defaultOrgName);
        //排序字段，默认值
        mdmUserDto.setSort(defaultSort);
        mdmUserDto.setGlobalSort(defaultSort);
        mdmUserDto.setGlobalSortSecond(defaultSort);
        //人员类型
        mdmUserDto.setUserClass(UserClassEnum.EDU.code);
        mdmUserDto.setStudyUserRole(studyUserDto.getUserType());
        MdmUserVo mdmUserVo = null;
        if (isRegister){
            mdmUserVo = mdmClient.regStudyUser(mdmUserDto);
        }else {
            mdmUserVo = mdmClient.saveStudyUser(mdmUserDto);
        }

        return mdmUserVo;
    }

    /**
     * 校验用户名和证件号是否唯一
     * @param studyUserDto
     */
    private void checkUserUnique(StudyUserDto studyUserDto) {
         //校验用户名唯一
         LambdaQueryWrapper<StudyUser> uQueryWrapper = new LambdaQueryWrapper();
         uQueryWrapper.in(StudyUser::getUserName,studyUserDto.getUserName());
         List<StudyUser> uStudyUsers = studyUserMapper.selectList(uQueryWrapper);
         if(CollectionUtil.isNotEmpty(uStudyUsers)){
             throw new IncloudException("存在重复的用户名："+studyUserDto.getUserName());
         }

        //校验证件号唯一
        LambdaQueryWrapper<StudyUser> iQueryWrapper = new LambdaQueryWrapper();
        iQueryWrapper.in(StudyUser::getIdCard,studyUserDto.getIdCard());
        List<StudyUser> iStudyUsers = studyUserMapper.selectList(iQueryWrapper);
        if(CollectionUtil.isNotEmpty(iStudyUsers)){
            throw new IncloudException("存在重复的证件号："+studyUserDto.getIdCard());
        }

    }

    /**
    * 修改实体
    * @param studyUserDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyUserDto studyUserDto) {
        if (null == studyUserDto.getId()){
            throw new IncloudException("用户id不能为空");
        }
        //默认人员启用，mdmUser需要用的字段
        studyUserDto.setStatus(YesNo.YES.code);
        studyUserDto.setUpdateTime(LocalDateTime.now());
        studyUserDto.setPassWord(handlePw(studyUserDto.getPassWord()));
        MdmUserDto mdmUserDto = dozerMapper.map(studyUserDto, MdmUserDto.class);
        mdmUserDto.setId(studyUserDto.getMasterDataId());
        mdmUserDto.setParentOrgId(defaultOrgId);
        mdmUserDto.setParentOrgName(defaultOrgName);
        mdmUserDto.setParentDeptId(defaultOrgId);
        mdmUserDto.setParentDeptName(defaultOrgName);
        mdmUserDto.setOrgFullId(defaultOrgId.toString());
        mdmUserDto.setOrgFullName(defaultOrgName);
        //排序字段，默认值
        mdmUserDto.setSort(defaultSort);
        mdmUserDto.setGlobalSort(defaultSort);
        mdmUserDto.setGlobalSortSecond(defaultSort);

        //人员类型
        mdmUserDto.setStudyUserRole(studyUserDto.getUserType());
        Boolean boo = mdmClient.updateStudyUser(mdmUserDto);
        if (boo){
            StudyUser studyUser = dozerMapper.map(studyUserDto,StudyUser.class);
            boolean result = super.updateById(studyUser);
            if(result){
                log.debug("修改成功");
                return result;
            }
        }
        return false;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String id) {
        List<String> ids = Stream.of(id.split(",")).collect(Collectors.toList());
        List<StudyUser> studyUsers = this.listByIds(ids);
        if (CollectionUtil.isEmpty(studyUsers)){
            throw new IncloudException("不存在该用户");
        }
        List<Long> masterDataIds = studyUsers.stream().map(StudyUser::getMasterDataId).collect(Collectors.toList());
        String mids = "";
        for (Long masterDataId : masterDataIds) {
            mids = mids + masterDataId +",";
        }
        String _mids = mids.substring(0, mids.length() - 1);
        //删除主数据相关人员
        Boolean boo = mdmClient.deleteStudyUser(_mids);
        if (boo){
            //true后删除本地
            boolean result = super.removeByIds(ids);
            if(result){
                log.debug("删除成功");
            }
            return result;
        }
        return false;
    }

    /**
     * 注册
     * @param studyUserDto
     * @return
     */
    @Transactional
    @Override
    public Result<Boolean> register(StudyUserDto studyUserDto) {
        //校验用户名和证件号是否唯一
        checkUserUnique(studyUserDto);
        //默认人员启用
        studyUserDto.setStatus(YesNo.YES.code);
        //默认未审核
        studyUserDto.setUserStatus(YesNo.NO.code);
        //设置密码加密
        studyUserDto.setPassWord(handlePw(studyUserDto.getPassWord()));
        StudyUser _studyUser = dozerMapper.map(studyUserDto, StudyUser.class);
        //同步至主数据人员表
        MdmUserVo mdmUserVo = syncMdmUser(studyUserDto,true);
        boolean result = false;
        //如果不为空，说明主数据已存在此人员，该人员无需注册，
        if (null != mdmUserVo){
            //保存本地人员表
            StudyUser studyUser = dozerMapper.map(mdmUserVo, StudyUser.class);
            //审核状态改为已审核
            studyUser.setUserStatus(YesNo.YES.code);
            studyUser.setDescription(studyUserDto.getDescription());
            studyUser.setStatus(studyUserDto.getStatus());
            studyUser.setUserType(studyUserDto.getUserType());
            studyUser.setMasterDataId(mdmUserVo.getId());
            studyUser.setCreateTime(LocalDateTime.now());
            studyUser.setUpdateTime(LocalDateTime.now());
            studyUser.setCreateUserId(studyUserDto.getCreateUserId());
            studyUser.setCreateUserName(studyUserDto.getCreateUserName());
            studyUser.setCreateUserParentOrgId(studyUserDto.getCreateUserParentOrgId());
            studyUser.setCreateUserParentOrgName(studyUserDto.getCreateUserParentOrgName());
            studyUser.setCreateUserParentDeptId(studyUserDto.getCreateUserParentDeptId());
            studyUser.setCreateUserParentDeptName(studyUserDto.getCreateUserParentDeptName());
            studyUser.setCreateUserOrgFullId(studyUserDto.getCreateUserOrgFullId());
            result = this.save(studyUser);
            if (UserClassEnum.EDU.code.intValue() != mdmUserVo.getUserClass().intValue()){
                //throw new IncloudException("此用户已存在："+ studyUser.getUserName());
                return Result.failed("此用户已存在");
            }

        }else {
            //如果是null,说明主数据人员不存在，仅保存至当前在线学习人员表,待审核
            result = this.save(_studyUser);
        }
        if(result){
            log.debug("保存成功");
        }
        return Result.success(result);
    }

    /**
     * 审核通过
     * @param studyUserDto
     * @return
     */
    @Override
    public Boolean checkUpdate(StudyUserDto studyUserDto) {
        boolean result = false;
        //审核通过
        if (studyUserDto.getUserStatus().intValue() == YesNo.YES.code.intValue()) {
            //校验用户名和证件号是否唯一
            checkUserUnique(studyUserDto);
            //修改状态
            studyUserDto.setUserStatus(YesNo.YES.code);
            MdmUserVo mdmUserVo = syncMdmUser(studyUserDto, false);
            studyUserDto.setMasterDataId(mdmUserVo.getId());
            StudyUser studyUser = dozerMapper.map(studyUserDto, StudyUser.class);
            result = this.updateById(studyUser);
            if (result) {
                log.debug("审核成功");
            }
        }else {
            //审核不通过，删除此人信息即可
            result = super.removeById(studyUserDto.getId());
            if (result){
                log.debug("审核成功");
            }
        }
        return result;
    }

    /**
     * 查学员
     * @param studyUserDto
     * @return
     */
    @Override
    public List<StudyUserVo> studentLists(StudyUserDto studyUserDto) {
        LambdaQueryWrapper<StudyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyUser::getUserType, UserType.STUDENT.code);
        queryWrapper.eq(StudyUser::getStatus,YesNo.YES.code);
        queryWrapper.eq(StudyUser::getUserStatus, ApplyStatus.APPLY_PASS.code);
        List<StudyUser> studyUsers = super.list(queryWrapper);
        List<StudyUserVo> studyUserVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(studyUsers)){
             studyUserVos = DozerUtils.mapList(dozerMapper, studyUsers, StudyUserVo.class);
        }
        return studyUserVos;
    }

    /**
     * 查学员
     * @param studyUserDto
     * @return
     */
    @Override
    public List<StudyUserVo> teacherLists(StudyUserDto studyUserDto) {
        LambdaQueryWrapper<StudyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyUser::getUserType, UserType.TEACHER.code);
        queryWrapper.eq(StudyUser::getStatus,YesNo.YES.code);
        queryWrapper.eq(StudyUser::getUserStatus, ApplyStatus.APPLY_PASS.code);
        List<StudyUser> studyUsers = super.list(queryWrapper);
        List<StudyUserVo> studyUserVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(studyUsers)){
            studyUserVos = DozerUtils.mapList(dozerMapper, studyUsers, StudyUserVo.class);
        }
        return studyUserVos;
    }

    /**
     * 查学员
     * @param studyUserDto
     * @return
     */
    @Override
    public List<StudyUserVo> adminLists(StudyUserDto studyUserDto) {
        LambdaQueryWrapper<StudyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudyUser::getUserType, UserType.ADMIN.code);
        queryWrapper.eq(StudyUser::getStatus,YesNo.YES.code);
        queryWrapper.eq(StudyUser::getUserStatus, ApplyStatus.APPLY_PASS.code);
        List<StudyUser> studyUsers = super.list(queryWrapper);
        List<StudyUserVo> studyUserVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(studyUsers)){
            studyUserVos = DozerUtils.mapList(dozerMapper, studyUsers, StudyUserVo.class);
        }
        return studyUserVos;
    }

    @Override
    public List<StudyUserVo> getList(String id) {
        List<String> ids = Stream.of(id.split(",")).collect(Collectors.toList());
        List<StudyUser> studyUsers = this.listByIds(ids);
        List<StudyUserVo> studyUserVos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(studyUsers)){
            studyUserVos.addAll(DozerUtils.mapList(dozerMapper,studyUsers,StudyUserVo.class));
        }
        return studyUserVos;
    }

    @Override
    public StudyUserVo getStudyUser() {
        StudyUser currentUser = studyUserUtil.getCurrentUser();
        StudyUserVo studyUserVo = dozerMapper.map(currentUser, StudyUserVo.class);
        if (ObjectUtil.isEmpty(studyUserVo)){
            throw new IncloudException("您并没有维护个人信息");
        }
        log.debug("通过当前登陆人获取个人信息成功！如：（"+studyUserVo.toString()+"）");
        return studyUserVo;
    }
}
