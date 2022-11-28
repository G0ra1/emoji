package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.portal.entity.PortalPortal;
import com.netwisd.base.portal.entity.PortalTemplate;
import com.netwisd.base.portal.mapper.PortalPortalMapper;
import com.netwisd.base.portal.mapper.PortalTemplateMapper;
import com.netwisd.base.portal.service.PortalTemplateService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalTemplateDto;
import com.netwisd.base.portal.vo.PortalTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 模板管理 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-11 00:09:45
 */
@Service
@Slf4j
public class PortalTemplateServiceImpl extends ServiceImpl<PortalTemplateMapper, PortalTemplate> implements PortalTemplateService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalTemplateMapper portalTemplateMapper;

    @Autowired
    private PortalPortalMapper portalPortalMapper;

    /**
    * 单表简单查询操作
    * @param portalTemplateDto
    * @return
    */
    @Override
    public Page list(PortalTemplateDto portalTemplateDto) {
        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTemplate::getCrrVersion,1);
        queryWrapper.like(StringUtils.isNotBlank(portalTemplateDto.getTemplateCode()),PortalTemplate::getTemplateCode,portalTemplateDto.getTemplateCode());
        queryWrapper.like(StringUtils.isNotBlank(portalTemplateDto.getTemplateName()),PortalTemplate::getTemplateName,portalTemplateDto.getTemplateName());
        queryWrapper.eq(portalTemplateDto.getPortalId() != null,PortalTemplate::getPortalId,portalTemplateDto.getPortalId());
        queryWrapper.like(StringUtils.isNotBlank(portalTemplateDto.getPortalName()),PortalTemplate::getPortalName,portalTemplateDto.getPortalName());
        queryWrapper.eq(portalTemplateDto.getTerminal() != null,PortalTemplate::getTerminal,portalTemplateDto.getTerminal());
        queryWrapper.eq(portalTemplateDto.getStartEnable() != null,PortalTemplate::getStartEnable,portalTemplateDto.getStartEnable());
        Page<PortalTemplate> page = portalTemplateMapper.selectPage(portalTemplateDto.getPage(),queryWrapper);
        Page<PortalTemplateVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalTemplateVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 不分页集合查询
    * @param portalTemplateDto
    * @return
    */
    @Override
    public List<PortalTemplateVo> lists(PortalTemplateDto portalTemplateDto) {
        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTemplate::getCrrVersion,1);
        queryWrapper.like(StringUtils.isNotBlank(portalTemplateDto.getTemplateCode()),PortalTemplate::getTemplateCode,portalTemplateDto.getTemplateCode());
        queryWrapper.like(StringUtils.isNotBlank(portalTemplateDto.getTemplateName()),PortalTemplate::getTemplateName,portalTemplateDto.getTemplateName());
        queryWrapper.eq(portalTemplateDto.getPortalId() != null,PortalTemplate::getPortalId,portalTemplateDto.getPortalId());
        queryWrapper.like(StringUtils.isNotBlank(portalTemplateDto.getPortalName()),PortalTemplate::getPortalName,portalTemplateDto.getPortalName());
        queryWrapper.eq(portalTemplateDto.getTerminal() != null,PortalTemplate::getTerminal,portalTemplateDto.getTerminal());
        queryWrapper.eq(portalTemplateDto.getStartEnable() != null,PortalTemplate::getStartEnable,portalTemplateDto.getStartEnable());
        List<PortalTemplate> portalTemplates = portalTemplateMapper.selectList(queryWrapper);
        List<PortalTemplateVo> portalTemplateVos = DozerUtils.mapList(dozerMapper, portalTemplates, PortalTemplateVo.class);
        log.debug("查询条数:"+portalTemplateVos.size());
        return portalTemplateVos;
    }

    @Override
    public List<PortalTemplateVo> templateVersions(PortalTemplateDto portalTemplateDto) {
        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTemplate::getPortalId,portalTemplateDto.getPortalId());
        queryWrapper.eq(PortalTemplate::getTemplateCode,portalTemplateDto.getTemplateCode());
        queryWrapper.eq(PortalTemplate::getTerminal,portalTemplateDto.getTerminal());
        List<PortalTemplate> portalTemplates = portalTemplateMapper.selectList(queryWrapper);
        List<PortalTemplateVo> portalTemplateVos = DozerUtils.mapList(dozerMapper, portalTemplates, PortalTemplateVo.class);
        log.debug("查询条数:"+portalTemplateVos.size());
        return portalTemplateVos;
    }

    @Override
    public Boolean templateCodeIsOne(PortalTemplateDto portalTemplateDto) {
        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTemplate::getPortalId,portalTemplateDto.getPortalId());
        queryWrapper.eq(PortalTemplate::getTemplateCode,portalTemplateDto.getTemplateCode());
        queryWrapper.eq(PortalTemplate::getTerminal,portalTemplateDto.getTerminal());
        List<PortalTemplate> portalTemplates = portalTemplateMapper.selectList(queryWrapper);
        if (portalTemplates.size() > 0) {
            throw new IncloudException("该模板编码已经存在，请修改编码！");
        }
        return true;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalTemplateVo get(Long id) {
        PortalTemplate portalTemplate = super.getById(id);
        PortalTemplateVo portalTemplateVo = null;
        if(portalTemplate !=null){
            portalTemplateVo = dozerMapper.map(portalTemplate,PortalTemplateVo.class);
        }
        log.debug("查询成功");
        return portalTemplateVo;
    }

    /**
    * 保存实体
    * @param portalTemplateDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalTemplateDto portalTemplateDto) {
        PortalTemplate portalTemplate = dozerMapper.map(portalTemplateDto,PortalTemplate.class);
        //查询当前模板code是否已有版本，如果有版本，则是新增版本，否则第一次新增该模板code下的版本
        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTemplate::getPortalId,portalTemplate.getPortalId());
        queryWrapper.eq(PortalTemplate::getTemplateCode,portalTemplateDto.getTemplateCode());
        queryWrapper.eq(PortalTemplate::getTerminal,portalTemplateDto.getTerminal());
        queryWrapper.orderByDesc(PortalTemplate::getCreateTime);
        List<PortalTemplate> oldTemplateVersions = portalTemplateMapper.selectList(queryWrapper);
        if (oldTemplateVersions.size() > 0) {
            //已有版本,版本号数字递增
            portalTemplate.setTemplateVersion(oldTemplateVersions.get(0).getTemplateVersion()+1);
            //是否生效处理
            if (portalTemplate.getStartEnable().equals(YesNo.YES.code)) {
                //如果新增版本，并且直接生效，把原来的所有版本都改成不生效，当前模板code下的当前版本设置为0，并把当前模板code下的当前版本设置成1
                queryWrapper.clear();
                queryWrapper.eq(PortalTemplate::getPortalId,portalTemplate.getPortalId());
                List<PortalTemplate> allTemplateByPortalId = portalTemplateMapper.selectList(queryWrapper);
                allTemplateByPortalId.forEach(d->{
                    d.setUpdateTime(LocalDateTime.now());
                    d.setStartEnable(YesNo.NO.code);
                    if (d.getTemplateCode().equals(portalTemplate.getTemplateCode())) {
                        d.setCrrVersion(YesNo.NO.code);
                    }
                    portalTemplateMapper.updateById(d);
                });
                portalTemplate.setCrrVersion(YesNo.YES.code);
            }else {
                //新增版本，不生效，当前版本和是否生效 都为0
                portalTemplate.setCrrVersion(YesNo.NO.code);
                portalTemplate.setStartEnable(YesNo.NO.code);
            }
        }else {
            //第一次新增第一个版本
            portalTemplate.setCrrVersion(YesNo.YES.code);
            portalTemplate.setTemplateVersion(1);
            queryWrapper.clear();
            //查看当前门户下，有没有其他版本存在
            queryWrapper.eq(PortalTemplate::getPortalId,portalTemplate.getPortalId());
            queryWrapper.eq(PortalTemplate::getTerminal,portalTemplate.getTerminal());
            queryWrapper.eq(PortalTemplate::getStartEnable,YesNo.YES.code);
            List<PortalTemplate> allTemplateByPortalId = portalTemplateMapper.selectList(queryWrapper);
            if (allTemplateByPortalId.size() > 0) {
                //当前门户下，已经有启用版本
                if (portalTemplate.getStartEnable().equals(YesNo.YES.code)) {
                    //启用新增版本,修改原来的所有版本为不启用
                    allTemplateByPortalId.forEach(d->{
                        if (d.getStartEnable().equals(YesNo.YES.code)) {
                            d.setStartEnable(YesNo.NO.code);
                            portalTemplateMapper.updateById(d);
                        }
                    });
                    portalTemplate.setStartEnable(YesNo.YES.code);
                }
            }else {
                portalTemplate.setStartEnable(YesNo.YES.code);
            }
        }
        boolean result = super.save(portalTemplate);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalTemplateDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalTemplateDto portalTemplateDto) {
        PortalTemplate portalTemplate = dozerMapper.map(portalTemplateDto,PortalTemplate.class);
        if (portalTemplate.getStartEnable().equals(YesNo.YES.code)) {
            portalTemplate.setCrrVersion(YesNo.YES.code);

            LambdaQueryWrapper<PortalTemplate> templateWrapper = new LambdaQueryWrapper<>();
            templateWrapper.eq(PortalTemplate::getPortalId,portalTemplate.getPortalId());
            templateWrapper.eq(PortalTemplate::getTerminal,portalTemplate.getTerminal());
            List<PortalTemplate> templateList = portalTemplateMapper.selectList(templateWrapper);
            if (CollectionUtils.isNotEmpty(templateList)) {
                templateList.forEach(template->{
                    template.setStartEnable(YesNo.NO.code);
                    if (template.getTemplateCode().equals(portalTemplate.getTemplateCode()) && !template.getTemplateVersion().equals(portalTemplate.getTemplateVersion())) {
                        template.setCrrVersion(YesNo.NO.code);
                    }
                });
                super.updateBatchById(templateList);
            }
        }
        portalTemplate.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalTemplate);
        if(result){
            log.debug("修改成功");
        }

        return result;
    }

    /**
     * 修改启用版本
     * @param portalId
     * @param terminal
     * @param templateCode
     * @return
     */
    @Override
    @Transactional
    public Boolean updateStartEnable(Long portalId, Integer terminal, String templateCode) {
        //查询出原来的生效版本，并改成不启用
        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTemplate::getPortalId,portalId);
        queryWrapper.eq(PortalTemplate::getTerminal,terminal);
        queryWrapper.eq(PortalTemplate::getStartEnable,YesNo.YES.code);
        PortalTemplate oldEnableTemplate = portalTemplateMapper.selectOne(queryWrapper);
        oldEnableTemplate.setStartEnable(YesNo.NO.code);
        portalTemplateMapper.updateById(oldEnableTemplate);

        //生效新模板
        queryWrapper.clear();
        queryWrapper.eq(PortalTemplate::getPortalId,portalId);
        queryWrapper.eq(PortalTemplate::getTerminal,terminal);
        queryWrapper.eq(PortalTemplate::getTemplateCode,templateCode);
        queryWrapper.eq(PortalTemplate::getCrrVersion,YesNo.YES.code);
        PortalTemplate newEnableTemplate = portalTemplateMapper.selectOne(queryWrapper);
        newEnableTemplate.setStartEnable(YesNo.YES.code);
        portalTemplateMapper.updateById(newEnableTemplate);
        return true;
    }

    /**
     * 修改生效版本
     *
     * @param portalId
     * @param terminal
     * @param templateCode
     * @param templateId
     * @return
     */
    @Override
    @Transactional
    public Boolean updateVersion(Long portalId, Integer terminal, String templateCode, Long templateId) {
        //修改原来的版本为不生效
        LambdaQueryWrapper<PortalTemplate> templateWrapper = new LambdaQueryWrapper<>();
        templateWrapper.eq(PortalTemplate::getPortalId,portalId);
        templateWrapper.eq(PortalTemplate::getTerminal,terminal);
        templateWrapper.eq(PortalTemplate::getTemplateCode,templateCode);
        templateWrapper.eq(PortalTemplate::getCrrVersion,YesNo.YES.code);
        PortalTemplate oldCrrVersionTemplate = portalTemplateMapper.selectOne(templateWrapper);
        oldCrrVersionTemplate.setCrrVersion(YesNo.NO.code);
        portalTemplateMapper.updateById(oldCrrVersionTemplate);
        //根据传的id 修改生效版本
        templateWrapper.clear();
        templateWrapper.eq(PortalTemplate::getId,templateId);
        PortalTemplate newVersionTemplate = portalTemplateMapper.selectOne(templateWrapper);
        newVersionTemplate.setCrrVersion(YesNo.YES.code);
        portalTemplateMapper.updateById(newVersionTemplate);
        //如果修改的生效版本是当前启用的模板code下，那启用版本也换成当前生效版本
        templateWrapper.clear();
        templateWrapper.eq(PortalTemplate::getPortalId,portalId);
        templateWrapper.eq(PortalTemplate::getTerminal,terminal);
        templateWrapper.eq(PortalTemplate::getStartEnable,YesNo.YES.code);
        PortalTemplate oldEnableTemplate = portalTemplateMapper.selectOne(templateWrapper);
        if (oldEnableTemplate.getTemplateCode().equals(newVersionTemplate.getTemplateCode())) {
            oldEnableTemplate.setStartEnable(YesNo.NO.code);
            portalTemplateMapper.updateById(oldEnableTemplate);

            newVersionTemplate.setStartEnable(YesNo.YES.code);
            portalTemplateMapper.updateById(newVersionTemplate);
        }
        return true;
    }

    /**
    * 删除某个版本
    * @param ids
    * @return
    */
    @Transactional
    @Override
    public Boolean deleteVersion(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PortalTemplate::getId,idList);
        List<PortalTemplate> portalTemplates = portalTemplateMapper.selectList(queryWrapper);
        if (portalTemplates.size() > 0) {
            LambdaQueryWrapper<PortalPortal> portalWrapper = new LambdaQueryWrapper<>();
            portalWrapper.eq(PortalPortal::getId,portalTemplates.get(0).getPortalId());
            PortalPortal portalPortal = portalPortalMapper.selectOne(portalWrapper);

            //判断删除的版本中，有没有生效版本，如果有，提示出来
            portalTemplates.forEach(d->{
                if (d.getStartEnable().equals(YesNo.YES.code) && YesNo.YES.code.equals(portalPortal.getIsDefault()) && d.getTerminal() == 1) {
                    throw new IncloudException("删除的版本中，有默认门户下的启用版本，启用版本的门户名称是："+d.getPortalName()+",所属终端是移动,所属模板code："+d.getTemplateCode()+",所属模板版本是:"+d.getTemplateVersion());
                }
                if (d.getStartEnable().equals(YesNo.YES.code) && YesNo.YES.code.equals(portalPortal.getIsDefault()) && d.getTerminal() == 0) {
                    throw new IncloudException("删除的版本中，有默认门户下的启用版本，启用版本的门户名称是："+d.getPortalName()+",所属终端是pc,所属模板code："+d.getTemplateCode()+",所属模板版本是:"+d.getTemplateVersion());
                }
            });
        }
        int delete = portalTemplateMapper.delete(queryWrapper);
        if(delete > 0){
            log.debug("删除成功");
        }
        return true;
    }

    /**
     * 删除一整个模板
     *
     * @param portalTemplateDto@return
     */
    @Override
    public Boolean deleteTemplate(PortalTemplateDto portalTemplateDto) {
        LambdaQueryWrapper<PortalPortal> portalWrapper = new LambdaQueryWrapper<>();
        portalWrapper.eq(PortalPortal::getId,portalTemplateDto.getPortalId());
        PortalPortal portalPortal = portalPortalMapper.selectOne(portalWrapper);

        LambdaQueryWrapper<PortalTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalTemplate::getPortalId,portalTemplateDto.getPortalId());
        queryWrapper.eq(PortalTemplate::getTemplateCode,portalTemplateDto.getTemplateCode());
        queryWrapper.eq(PortalTemplate::getTerminal,portalTemplateDto.getTerminal());
        List<PortalTemplate> portalTemplates = portalTemplateMapper.selectList(queryWrapper);
        portalTemplates.forEach(d->{
            if (d.getStartEnable().equals(YesNo.YES.code) && YesNo.YES.code.equals(portalPortal.getIsDefault())) {
                throw new IncloudException("当前门户是默认首页，并且是启用模板，不能删除，请先更换启用模板，再进行删除！");
            }
        });
        int delete = portalTemplateMapper.delete(queryWrapper);
        if (delete > 0) {
            return true;
        }
        return false;
    }
}
