package com.netwisd.base.wf.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfCommLanguage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfCommLanguageDto;
import com.netwisd.base.wf.vo.WfCommLanguageVo;
/**
 * @Description 审批时常用语 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-17 14:45:42
 */
public interface WfCommLanguageService extends IService<WfCommLanguage> {
    Page list(WfCommLanguageDto wfCommLanguageDto);
    Page lists(WfCommLanguageDto wfCommLanguageDto);
    WfCommLanguageVo get(Long id);
    Boolean save(WfCommLanguageDto wfCommLanguageDto);
    Boolean update(WfCommLanguageDto wfCommLanguageDto);
    Boolean delete(Long id);
}
