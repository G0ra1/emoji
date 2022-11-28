package com.netwisd.base.wf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.wf.entity.WfCommLanguage;
import com.netwisd.base.wf.dto.WfCommLanguageDto;
import com.netwisd.base.wf.vo.WfCommLanguageVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 审批时常用语 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-17 14:45:42
 */
@Mapper
public interface WfCommLanguageMapper extends BaseMapper<WfCommLanguage> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param wfCommLanguageDto
     * @return
     */
    Page<WfCommLanguageVo> getPageList(Page page, @Param("wfCommLanguageDto") WfCommLanguageDto wfCommLanguageDto);
}
