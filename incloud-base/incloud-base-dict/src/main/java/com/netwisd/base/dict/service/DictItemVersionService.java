package com.netwisd.base.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.dict.entity.DictItem;
import com.netwisd.base.dict.entity.DictItemVersion;
import com.netwisd.base.dict.vo.DictItemVo;

import java.util.List;

public interface DictItemVersionService extends IService<DictItemVersion> {

    List<DictItemVo> list(Long oldDictTreeId);

    int getMaxVersion(Long oldDictTreeId);

    void delDictItemVersion(String ids);

    void settingMainVersion(String id);

    void saveDictItemVersion(DictItem dictItem);

}