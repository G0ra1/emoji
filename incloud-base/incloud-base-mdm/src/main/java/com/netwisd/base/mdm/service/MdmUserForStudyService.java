package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.study.dto.StudyUserDto;
import com.netwisd.base.common.study.vo.StudyUserVo;
import com.netwisd.base.mdm.entity.MdmUser;

import java.util.List;

public interface MdmUserForStudyService {
    Page<StudyUserVo> findByPage(StudyUserDto studyUserDto);

    List<StudyUserVo> findByList(StudyUserDto studyUserDto);

    StudyUserVo get(Long id);

    Boolean saveStudyUserBatch(List<StudyUserDto> studyUserDtoList);

    Boolean updateStudyUser(StudyUserDto studyUserDto);

    Boolean deleteStudyUser(String ids,String userType);

    Boolean updateUserRole(MdmUser user, String userType);

    Integer getStudyRoleByUserId(Long id);
}
