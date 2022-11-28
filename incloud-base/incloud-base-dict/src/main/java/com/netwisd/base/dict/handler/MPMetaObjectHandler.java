package com.netwisd.base.dict.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MPMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....", metaObject);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (ObjectUtil.isNotNull(loginAppUser) && StrUtil.isNotEmpty(loginAppUser.getUserName())) {
            this.strictInsertFill(metaObject, "createUserId", Long.class, loginAppUser.getId());
            this.strictInsertFill(metaObject, "createUserName", String.class, loginAppUser.getUserName());
            this.strictInsertFill(metaObject, "createUserParentOrgId", Long.class, loginAppUser.getParentOrgId());
            this.strictInsertFill(metaObject, "createUserParentOrgName", String.class, loginAppUser.getParentOrgName());
            this.strictInsertFill(metaObject, "createUserParentDeptId", Long.class, loginAppUser.getParentDeptId());
            this.strictInsertFill(metaObject, "createUserParentDeptName", String.class, loginAppUser.getParentDeptName());
            this.strictInsertFill(metaObject, "createUserOrgFullId", String.class, loginAppUser.getOrgFullId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....", metaObject);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
