package com.netwisd.biz.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.study.dto.StudyCouDto;
import com.netwisd.biz.study.entity.StudyCou;
import com.netwisd.biz.study.vo.StudyCouVo;

import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课件表 功能描述...
 * @date 2022-04-19 18:23:02
 */
public interface StudyCouService extends IService<StudyCou> {
    /**
     * 分页查询
     *
     * @param studyCouDto
     * @return
     */
    Page pageList(StudyCouDto studyCouDto);

    List<StudyCouVo> lists(StudyCouDto studyCouDto);

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    StudyCouVo get(Long id);

    /**
     * 保存
     *
     * @param studyCouDto
     * @return
     */
    Boolean save(StudyCouDto studyCouDto);

    /**
     * 修改
     *
     * @param studyCouDto
     * @return
     */
    Boolean update(StudyCouDto studyCouDto);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    Boolean delete(String ids);
}
