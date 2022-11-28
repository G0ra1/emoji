package com.netwisd.biz.study.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.biz.study.entity.StudyUser;
import com.netwisd.biz.study.service.StudyUserService;
import com.netwisd.biz.study.service.impl.StudyUserServiceImpl;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class StudyUserUtil {

    @Autowired
    private  StudyUserService studyUserService;

    /**
     * 获取当前登录人
     * @param
     */
    public  StudyUser getCurrentUser() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (ObjectUtils.isEmpty(loginAppUser)){
            throw new IncloudException("请重新登录！");
        }
        Long masterDateId = loginAppUser.getId();
        LambdaQueryWrapper<StudyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(StudyUser::getMasterDataId,masterDateId);
//        StudyUserServiceImpl studyUserService = new StudyUserServiceImpl();
        StudyUser studyUser = studyUserService.getOne(queryWrapper);
        if (null == studyUser){
            throw new IncloudException("当前登录人不存在于该学习系统");
        }
        return studyUser;
    }
}
