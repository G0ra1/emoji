package com.netwisd.base.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.file.entity.FileDs;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.file.dto.FileDsDto;
import com.netwisd.base.file.vo.FileDsVo;

import java.util.List;

/**
 * @Description 文件数据源 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-12-29 11:04:48
 */
public interface FileDsService extends IService<FileDs> {
    Page list(FileDsDto fileDsDto);
    List all();
    FileDsVo get(Long id);
    FileDs getDefaultDs();
    Boolean save(FileDsDto fileDsDto);
    void connect(FileDsDto fileDsDto);
    Boolean update(FileDsDto fileDsDto);
    Boolean setDefault(Long id,Integer isDefault);
    Boolean delete(Long id);
}
