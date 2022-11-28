package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.dto.FileInfoDto;
import com.netwisd.base.portal.entity.FileInfo;
import com.netwisd.base.portal.vo.FileInfoVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 文件存储  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-01-14 09:51:13
 */
@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param fileInfoDto
     * @return
     */
    Page<FileInfoVo> getPageList(Page page, @Param("fileInfoDto") FileInfoDto fileInfoDto);
}
