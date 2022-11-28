package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.study.constants.CouTypeEnum;
import com.netwisd.biz.study.constants.RangeTypeEnum;
import com.netwisd.biz.study.constants.UseRangeEnum;
import com.netwisd.biz.study.dto.StudyCouDto;
import com.netwisd.biz.study.entity.StudyCou;
import com.netwisd.biz.study.entity.StudyCouPerm;
import com.netwisd.biz.study.entity.StudyLessonCou;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.StudyCouMapper;
import com.netwisd.biz.study.mapper.StudyCouPermMapper;
import com.netwisd.biz.study.mapper.StudyLessonCouMapper;
import com.netwisd.biz.study.service.StudyCouPermService;
import com.netwisd.biz.study.service.StudyCouService;
import com.netwisd.biz.study.vo.StudyCouPermVo;
import com.netwisd.biz.study.vo.StudyCouVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课件表 功能描述...
 * @date 2022-04-19 18:23:02
 */
@Service
@Slf4j
public class StudyCouServiceImpl extends ServiceImpl<StudyCouMapper, StudyCou> implements StudyCouService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyCouMapper studyCouMapper;

    @Autowired
    private StudyCouPermMapper studyCouPermMapper;

    @Autowired
    private StudyCouPermService studyCouPermService;

    @Autowired
    private StudyLessonCouMapper studyLessonCouMapper;

    @Autowired
    private MdmClient mdmClient;

    /**
     * 单表简单查询操作
     *
     * @param couDto
     * @return
     */
    @Override
    public Page pageList(StudyCouDto couDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyCou> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(couDto.getCouType()), StudyCou::getCouType, couDto.getCouType());
        queryWrapper.eq(StringUtils.isNotBlank(couDto.getLabelCode()), StudyCou::getLabelCode, couDto.getLabelCode());
        queryWrapper.like(StringUtils.isNotBlank(couDto.getCouName()), StudyCou::getCouName, couDto.getCouName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(couDto.getUseRange()), StudyCou::getUseRange, couDto.getUseRange());
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //如果是讲师，只看到自己申请的
            queryWrapper.eq(StudyCou::getCreateUserId, loginAppUser.getId());
        }
        queryWrapper.orderByDesc(StudyCou::getCreateTime);
        Page<StudyCou> page = studyCouMapper.selectPage(couDto.getPage(), queryWrapper);
        Page<StudyCouVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyCouVo.class);
        List<StudyCouVo> records = pageVo.getRecords();
        for (StudyCouVo cou : records){
            //分钟
            long minute = cou.getStudyTime() / 1000 / 60;
            String timeSize = Long.toString(minute);
            cou.setStudyTimeSize(timeSize);
        }
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param couDto
     * @return
     */
    @Override
    public List<StudyCouVo> lists(StudyCouDto couDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyCou> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(couDto.getLabelCode()), StudyCou::getLabelCode, couDto.getLabelCode());
        queryWrapper.eq(ObjectUtils.isNotEmpty(couDto.getCouType()), StudyCou::getCouType, couDto.getCouType());
        queryWrapper.like(StringUtils.isNotBlank(couDto.getCouName()), StudyCou::getCouName, couDto.getCouName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(couDto.getUseRange()), StudyCou::getUseRange, couDto.getUseRange());
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //如果是讲师，只看到自己申请的
            queryWrapper.eq(StudyCou::getCreateUserId, loginAppUser.getId());
        }
        queryWrapper.orderByDesc(StudyCou::getCreateTime);
        List<StudyCou> studyCouList = studyCouMapper.selectList(queryWrapper);
        List<StudyCouVo> couVoList = DozerUtils.mapList(dozerMapper, studyCouList, StudyCouVo.class);
        if (CollectionUtils.isNotEmpty(couVoList)) {
            //课件授权设置课件分钟数
            for (StudyCouVo couVo : couVoList) {
                long minute = couVo.getStudyTime() / 1000 / 60;
                String timeSize = Long.toString(minute);
                couVo.setStudyTimeSize(timeSize);
            }
            //通过课件集合分出集合id
            List<Long> couIdList = couVoList.stream().map(StudyCouVo::getId).collect(Collectors.toList());
            //通过集合id查出所有的课件授权
            LambdaQueryWrapper<StudyCouPerm> couPermWrapper = new LambdaQueryWrapper<>();
            couPermWrapper.in(StudyCouPerm::getCouId, couIdList);
            List<StudyCouPerm> couPermList = studyCouPermMapper.selectList(couPermWrapper);
            List<StudyCouPermVo> couPermVos = DozerUtils.mapList(dozerMapper, couPermList, StudyCouPermVo.class);
            if (CollectionUtils.isNotEmpty(couPermVos)) {
                //如果有课件授权 按照课件id进行分组
                Map<Long, List<StudyCouPermVo>> couIdMap = couPermVos.stream().collect(Collectors.groupingBy(StudyCouPermVo::getCouId));
                for (StudyCouVo couVo : couVoList) {
                    //如果课件是私有的 将课件授权放入课件信息中返回
                    if (UseRangeEnum.SIYOU.code.equals(couVo.getUseRange())) {
                        List<StudyCouPermVo> couPermVoList = couIdMap.get(couVo.getId());
                        List<StudyCouPermVo> orgPermList = new ArrayList<>();
                        List<StudyCouPermVo> userPermList = new ArrayList<>();
                        for (StudyCouPermVo couPermVo : couPermVoList) {
                            if (couPermVo.getRangeType().equals(RangeTypeEnum.USER.code)) {
                                userPermList.add(couPermVo);
                            } else {
                                orgPermList.add(couPermVo);
                            }
                        }
                        couVo.setUserPermList(userPermList);
                        couVo.setOrgPermList(orgPermList);

                    }
                }
            }
        }
        return couVoList;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public StudyCouVo get(Long id) {
        StudyCou studyCou = super.getById(id);
        StudyCouVo studyCouVo = Optional.ofNullable(studyCou).map(x -> dozerMapper.map(studyCou, StudyCouVo.class)).orElseGet(() -> new StudyCouVo());
       /* if (studyCou != null) {
            studyCouVo = dozerMapper.map(studyCou, StudyCouVo.class);
            Integer studyTime = studyCou.getStudyTime();
            Integer min = studyTime / 60;
            Integer s = studyTime % 60;
            studyCouVo.setStudyTime(min + "." + s);
        }*/
        if (studyCouVo.getUseRange().equals(UseRangeEnum.SIYOU.code)) {
            LambdaQueryWrapper<StudyCouPerm> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StudyCouPerm::getCouId, id);
            List<StudyCouPerm> studyCouPerms = studyCouPermMapper.selectList(queryWrapper);
            List<StudyCouPermVo> orgPermList = new ArrayList<>();
            List<StudyCouPermVo> userPermList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(studyCouPerms)) {
                for (StudyCouPerm studyCouPerm : studyCouPerms) {
                    if (RangeTypeEnum.USER.code.equals(studyCouPerm.getRangeType())) {
                        userPermList.add(dozerMapper.map(studyCouPerm, StudyCouPermVo.class));
                    } else {
                        orgPermList.add(dozerMapper.map(studyCouPerm, StudyCouPermVo.class));
                    }
                }
            }
            studyCouVo.setOrgPermList(orgPermList);
            studyCouVo.setUserPermList(userPermList);
        }
        log.debug("查询成功");
        return studyCouVo;
    }

    /**
     * 检查填写字段
     *
     * @param couDto
     */
    private void checkFiled(StudyCouDto couDto) {
        if (StringUtils.isEmpty(couDto.getLabelCode())) {
            throw new IncloudException("请选择课件分类");
        }
        if (ObjectUtils.isEmpty(couDto.getCouName())) {
            throw new IncloudException("请填写课件标题");
        }
        if (ObjectUtils.isEmpty(couDto.getCouType())) {
            throw new IncloudException("请选择课件类型");
        }
        if (StringUtils.isBlank(couDto.getFileName()) || StringUtils.isBlank(couDto.getFileUrl())) {
            throw new IncloudException("请上传课件文件");
        }

        if (couDto.getFileSize() == null) {
            throw new IncloudException("请传递文件大小");
        }
        couDto.setFileSizeView(readableFileSize(couDto.getFileSize()));

        if ((CouTypeEnum.AUDIO.code == couDto.getCouType() || CouTypeEnum.VIDEO.code == couDto.getCouType()) && couDto.getStudyTime() == null) {
            throw new IncloudException("请填写媒体文件时长");
        }
        //默认为公开
        couDto.setUseRange(couDto.getUseRange() == null ? UseRangeEnum.GONGKAI.code : couDto.getUseRange());
        /*String[] studyTime = couDto.getStudyTime().split(":");
        Integer min = Integer.valueOf(studyTime[0]) * 60;
        Integer s = Integer.valueOf(studyTime[1]);
        Integer allS = min + s;
        couDto.setStudyTime(allS.toString());*/
    }

    /**
     * 获取文件大小展示
     *
     * @param size
     * @return
     */
    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + units[digitGroups];
    }

    /**
     * 保存课件授权信息
     *
     * @param couDto
     */
    private void saveCouPermBatch(StudyCouDto couDto) {
        List<StudyCouPerm> addCouPermList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(couDto.getOrgPermList())) {
            List<StudyCouPerm> orgPermList = DozerUtils.mapList(dozerMapper, couDto.getOrgPermList(), StudyCouPerm.class);
            orgPermList.forEach(perm -> {
                perm.setCouId(couDto.getId());
                addCouPermList.add(perm);
            });
        }
        if (CollectionUtils.isNotEmpty(couDto.getUserPermList())) {
            List<StudyCouPerm> userPermList = DozerUtils.mapList(dozerMapper, couDto.getUserPermList(), StudyCouPerm.class);
            userPermList.forEach(perm -> {
                perm.setCouId(couDto.getId());
                perm.setRangeType(RangeTypeEnum.USER.code);
                addCouPermList.add(perm);
            });
        }
        if (CollectionUtils.isNotEmpty(addCouPermList)) {
            studyCouPermService.saveBatch(addCouPermList);
        }
    }

    /**
     * 保存实体
     *
     * @param couDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(StudyCouDto couDto) {
        checkFiled(couDto);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        couDto.setCreateUserName(loginAppUser.getUserNameCh());
        StudyCou studyCou = dozerMapper.map(couDto, StudyCou.class);
        //studyCou.setStudyTime(Integer.valueOf(couDto.getStudyTime()));
        super.save(studyCou);
        if (couDto.getUseRange().equals(UseRangeEnum.SIYOU.code)) {
            saveCouPermBatch(couDto);
        }
        return true;
    }

    /**
     * 修改实体
     *
     * @param couDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(StudyCouDto couDto) {
        checkFiled(couDto);
        couDto.setUpdateTime(LocalDateTime.now());
        StudyCou studyCou = dozerMapper.map(couDto, StudyCou.class);
        super.updateById(studyCou);
        //先删除权限课件权限表
        LambdaQueryWrapper<StudyCouPerm> couPermWrapper = new LambdaQueryWrapper<>();
        couPermWrapper.eq(StudyCouPerm::getCouId, studyCou.getId());
        studyCouPermMapper.delete(couPermWrapper);
        if (couDto.getUseRange().equals(UseRangeEnum.SIYOU.code)) {
            //如果课件状态是私有，新增课件权限表
            this.saveCouPermBatch(couDto);
        }
        return true;
    }

    /**
     * 通过ID删除
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        Optional.ofNullable(ids).orElseThrow(() -> new IncloudException("请选择要删除的记录"));
        List<Long> idsList = Arrays.stream(ids.split(",")).map(x -> Long.valueOf(x)).collect(Collectors.toList());
        //判断该课件是否被引用
        if (studyLessonCouMapper.selectCount(Wrappers.<StudyLessonCou>lambdaQuery().in(StudyLessonCou::getCouId, idsList)) > 0) {
            throw new IncloudException("被课程引用的课件无法删除");
        }
        //删除课件
        studyCouMapper.delete(Wrappers.<StudyCou>lambdaQuery().in(StudyCou::getId, idsList));
        //删除课件权限
        studyCouPermMapper.delete(Wrappers.<StudyCouPerm>lambdaQuery().in(StudyCouPerm::getCouId, idsList));
        return true;
    }
}
