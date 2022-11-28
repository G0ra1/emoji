package com.netwisd.base.wf.service.impl.other;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.wf.entity.WfCommLanguage;
import com.netwisd.base.wf.mapper.WfCommLanguageMapper;
import com.netwisd.base.wf.service.other.WfCommLanguageService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.wf.dto.WfCommLanguageDto;
import com.netwisd.base.wf.vo.WfCommLanguageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 审批时常用语 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-17 14:45:42
 */
@Service
@Slf4j
public class WfCommLanguageServiceImpl extends ServiceImpl<WfCommLanguageMapper, WfCommLanguage> implements WfCommLanguageService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfCommLanguageMapper wfCommLanguageMapper;

    /**
    * 单表简单查询操作
    * @param wfCommLanguageDto
    * @return
    */
    @Override
    public Page list(WfCommLanguageDto wfCommLanguageDto) {
        LambdaQueryWrapper<WfCommLanguage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(wfCommLanguageDto.getUseUserName()), WfCommLanguage::getUseUserName, wfCommLanguageDto.getUseUserName())
                .like(StrUtil.isNotEmpty(wfCommLanguageDto.getCreateUserName()), WfCommLanguage::getCreateUserName, wfCommLanguageDto.getCreateUserName())
                .like(StrUtil.isNotEmpty(wfCommLanguageDto.getContent()), WfCommLanguage::getContent, wfCommLanguageDto.getContent())
                .eq(ObjectUtil.isNotEmpty(wfCommLanguageDto.getIsGeneral()), WfCommLanguage::getIsGeneral, wfCommLanguageDto.getIsGeneral());
        Page<WfCommLanguage> page = wfCommLanguageMapper.selectPage(wfCommLanguageDto.getPage(),queryWrapper);
        Page<WfCommLanguageVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfCommLanguageVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfCommLanguageDto
    * @return
    */
    @Override
    public Page lists(WfCommLanguageDto wfCommLanguageDto) {
        Page<WfCommLanguageVo> page = wfCommLanguageMapper.getPageList(wfCommLanguageDto.getPage(),wfCommLanguageDto);
        Page<WfCommLanguageVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfCommLanguageVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfCommLanguageVo get(Long id) {
        WfCommLanguage wfCommLanguage = super.getById(id);
        WfCommLanguageVo wfCommLanguageVo = null;
        if(wfCommLanguage !=null){
            wfCommLanguageVo = dozerMapper.map(wfCommLanguage,WfCommLanguageVo.class);
        }
        log.debug("查询成功");
        return wfCommLanguageVo;
    }

    /**
    * 保存实体
    * @param wfCommLanguageDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfCommLanguageDto wfCommLanguageDto) {
        WfCommLanguage wfCommLanguage = dozerMapper.map(wfCommLanguageDto,WfCommLanguage.class);
        boolean result = super.save(wfCommLanguage);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfCommLanguageDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfCommLanguageDto wfCommLanguageDto) {
        WfCommLanguage wfCommLanguage = dozerMapper.map(wfCommLanguageDto,WfCommLanguage.class);
        boolean result = super.updateById(wfCommLanguage);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
