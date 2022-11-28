package com.netwisd.biz.mdm.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.mdm.entity.Projectjc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ProjectjcDto;
import com.netwisd.biz.mdm.vo.ProjectjcVo;
/**
 * @Description 集采项目 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-19 14:17:49
 */
public interface ProjectjcService extends BatchService<Projectjc> {
    Page list(ProjectjcDto projectjcDto);
    Page lists(ProjectjcDto projectjcDto);
    ProjectjcVo get(Long id);
    void save(ProjectjcDto projectjcDto);
    void saveList(List<ProjectjcDto> projectjcDtos);
    void update(ProjectjcDto projectjcDto);
    void updateSub(ProjectjcDto projectjcDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<ProjectjcVo> getByFkIdVo(Long id);
}
