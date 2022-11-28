package com.netwisd.base.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.portal.dto.FileInfoDto;
import com.netwisd.base.portal.entity.FileInfo;
import com.netwisd.base.portal.vo.FileInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Description 文件存储  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-01-14 09:51:13
 */
public interface FileInfoService extends IService<FileInfo> {
    Page list(FileInfoDto fileInfoDto);
    Page lists(FileInfoDto fileInfoDto);
    FileInfoVo get(Long id);
    Boolean save(FileInfoDto fileInfoDto);
    Boolean update(FileInfoDto fileInfoDto);
    Boolean delete(Long id);

    //根据业务id 删除附件信息
    void delByBizId(Long bizId);

    //根据业务id 获取附件信息
    List<FileInfoVo> getByBizId(Long bizId);
}
