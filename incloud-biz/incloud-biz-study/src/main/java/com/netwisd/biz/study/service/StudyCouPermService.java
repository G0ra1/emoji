package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.entity.StudyCouPerm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyCouPermDto;
import com.netwisd.biz.study.vo.StudyCouPermVo;
/**
 * @Description 课件授权表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:13:08
 */
public interface StudyCouPermService extends IService<StudyCouPerm> {
    Page list(StudyCouPermDto studyCouPermDto);
    Page lists(StudyCouPermDto studyCouPermDto);
    StudyCouPermVo get(Long id);
    Boolean save(StudyCouPermDto studyCouPermDto);
    Boolean update(StudyCouPermDto studyCouPermDto);
    Boolean delete(Long id);
}
