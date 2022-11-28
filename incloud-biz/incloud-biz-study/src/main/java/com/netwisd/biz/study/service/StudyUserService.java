package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyUserDto;
import com.netwisd.biz.study.vo.StudyUserVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 在线学习人员表 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-11-22 09:59:58
 */
public interface StudyUserService extends BatchService<StudyUser> {
    Page list(StudyUserDto studyUserDto,Boolean isCheck);
    List<StudyUserVo> lists(StudyUserDto studyUserDto);
    StudyUserVo get(Long id);
    Result<Boolean> save(StudyUserDto studyUserDto);
    Boolean update(StudyUserDto studyUserDto);
    Boolean delete(String id);
    //注册
    Result<Boolean> register(StudyUserDto studyUserDto);

    //人员审核通过
    Boolean checkUpdate(StudyUserDto studyUserDto);

    //查学员
    List<StudyUserVo> studentLists(StudyUserDto studyUserDto);
    //查讲师
    List<StudyUserVo> teacherLists(StudyUserDto studyUserDto);
    //查管理员
    List<StudyUserVo> adminLists(StudyUserDto studyUserDto);

    List<StudyUserVo> getList(String id);

    //通过当前登陆人获取个人信息
    StudyUserVo getStudyUser();
}
