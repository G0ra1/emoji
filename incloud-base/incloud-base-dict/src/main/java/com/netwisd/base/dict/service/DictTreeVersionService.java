package com.netwisd.base.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.dict.entity.DictTree;
import com.netwisd.base.dict.entity.DictTreeVersion;

import java.util.List;

public interface DictTreeVersionService extends IService<DictTreeVersion> {

    List<DictTreeVo> list(Long oldDictTreeId);

    int getMaxVersion(Long oldDictTreeId);

    void delDictVersion(String ids);

    void settingMainVersion(String id);

    void saveDictTreeVersion(DictTree dictTree);

}
