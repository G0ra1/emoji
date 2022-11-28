package com.netwisd.biz.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.StudyUserTypeEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.study.entity.StudyCou;
import com.netwisd.biz.study.entity.StudyMarterials;
import com.netwisd.biz.study.feign.MdmClient;
import com.netwisd.biz.study.mapper.StudyMarterialsMapper;
import com.netwisd.biz.study.service.StudyMarterialsService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.study.dto.StudyMarterialsDto;
import com.netwisd.biz.study.vo.StudyMarterialsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description 学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 20:04:23
 */
@Service
@Slf4j
public class StudyMarterialsServiceImpl extends ServiceImpl<StudyMarterialsMapper, StudyMarterials> implements StudyMarterialsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private StudyMarterialsMapper studyMarterialsMapper;

    @Autowired
    private MdmClient mdmClient;

    /**
    * 单表简单查询操作
    * @param studyMarterialsDto
    * @return
    */
    @Override
    public Page list(StudyMarterialsDto studyMarterialsDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyMarterials> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(studyMarterialsDto.getLabelCode())) {
            String[] labelCodeList = studyMarterialsDto.getLabelCode().split(",");
            for (String labelCode : labelCodeList){
                queryWrapper.or().like(StudyMarterials::getLabelCode,labelCode);
            }
        }
        queryWrapper.like(StringUtils.isNotBlank(studyMarterialsDto.getMarterialsName()),StudyMarterials::getMarterialsName,studyMarterialsDto.getMarterialsName());
        queryWrapper.orderByDesc(StudyMarterials::getCreateTime);
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //如果是讲师，只看到自己申请的
            queryWrapper.eq(StudyMarterials::getCreateUserId, loginAppUser.getId());
        }
        Page<StudyMarterials> page = studyMarterialsMapper.selectPage(studyMarterialsDto.getPage(),queryWrapper);
        Page<StudyMarterialsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, StudyMarterialsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param studyMarterialsDto
    * @return
    */
    @Override
    public List<StudyMarterialsVo> lists(StudyMarterialsDto studyMarterialsDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Integer studyUserRole = mdmClient.getStudyUserRole(loginAppUser.getId());
        LambdaQueryWrapper<StudyMarterials> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(studyMarterialsDto.getLabelCode())) {
            String[] labelCodeList = studyMarterialsDto.getLabelCode().split(",");
            for (String labelCode : labelCodeList){
                queryWrapper.or().like(StudyMarterials::getLabelCode,labelCode);
            }
        }
        queryWrapper.like(StringUtils.isNotBlank(studyMarterialsDto.getMarterialsName()),StudyMarterials::getMarterialsName,studyMarterialsDto.getMarterialsName());
        queryWrapper.orderByDesc(StudyMarterials::getCreateTime);
        if (StudyUserTypeEnum.TEACHER.code.equals(studyUserRole)) {
            //如果是讲师，只看到自己申请的
            queryWrapper.eq(StudyMarterials::getCreateUserId, loginAppUser.getId());
        }
        List<StudyMarterials> studyMarterials = studyMarterialsMapper.selectList(queryWrapper);
        List<StudyMarterialsVo> marterialsVos = DozerUtils.mapList(dozerMapper, studyMarterials, StudyMarterialsVo.class);
        log.debug("查询条数:"+marterialsVos.size());
        return marterialsVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public StudyMarterialsVo get(Long id) {
        StudyMarterials studyMarterials = super.getById(id);
        StudyMarterialsVo studyMarterialsVo = null;
        if(studyMarterials !=null){
            studyMarterialsVo = dozerMapper.map(studyMarterials,StudyMarterialsVo.class);
        }
        log.debug("查询成功");
        return studyMarterialsVo;
    }

    /**
    * 保存实体
    * @param studyMarterialsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(StudyMarterialsDto studyMarterialsDto) {
        this.checkData(studyMarterialsDto);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Optional.ofNullable(loginAppUser).orElseThrow(() -> new IncloudException("登录信息失效"));
        studyMarterialsDto.setCreateUserName(loginAppUser.getUserNameCh());
        StudyMarterials studyMarterials = dozerMapper.map(studyMarterialsDto,StudyMarterials.class);
        boolean result = super.save(studyMarterials);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param studyMarterialsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(StudyMarterialsDto studyMarterialsDto) {
        this.checkData(studyMarterialsDto);
        studyMarterialsDto.setUpdateTime(LocalDateTime.now());
        StudyMarterials studyMarterials = dozerMapper.map(studyMarterialsDto,StudyMarterials.class);
        boolean result = super.updateById(studyMarterials);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param ids
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        boolean result = super.removeByIds(Arrays.asList(ids.split(",")));
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    /**
     * 校验必填项
     * @param studyMarterialsDto
     */
    private void checkData(StudyMarterialsDto studyMarterialsDto){
        if (StringUtils.isBlank(studyMarterialsDto.getLabelCode())) {
            throw new IncloudException("请选择标签！");
        }
        if (StringUtils.isBlank(studyMarterialsDto.getMarterialsName())) {
            throw new IncloudException("请填写学习资料名称！");
        }
        if (ObjectUtils.isEmpty(studyMarterialsDto.getIsDownload())) {
            throw new IncloudException("请选择是否可以下载");
        }
        if (StringUtils.isBlank(studyMarterialsDto.getFileUrl())) {
            throw new IncloudException("请上传文件！");
        }
    }
}
