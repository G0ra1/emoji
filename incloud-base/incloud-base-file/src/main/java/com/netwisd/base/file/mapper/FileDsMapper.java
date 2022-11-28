package com.netwisd.base.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.file.entity.FileDs;
import com.netwisd.base.file.dto.FileDsDto;
import com.netwisd.base.file.vo.FileDsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 文件数据源 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-12-29 11:04:48
 */
@Mapper
public interface FileDsMapper extends BaseMapper<FileDs> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param fileDsDto
     * @return
     */
    Page<FileDsVo> getPageList(Page page, @Param("fileDsDto") FileDsDto fileDsDto);
}
