package com.netwisd.base.dict.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.constants.EncodFormName;
import com.netwisd.base.common.dict.dto.EncondRuleDetailDto;
import com.netwisd.base.common.dict.dto.EncondRuleDto;
import com.netwisd.base.common.dict.vo.EncondRuleDetailVo;
import com.netwisd.base.common.dict.vo.EncondRuleVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.base.dict.entity.EncondRule;
import com.netwisd.base.dict.mapper.EncondRuleMapper;
import com.netwisd.base.dict.service.EncondRuleService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import com.netwisd.common.db.util.EntityListConvert;
import com.netwisd.base.dict.service.EncondRuleDetailService;
import com.netwisd.base.dict.entity.EncondRuleDetail;

/**
 * @Description 编码规则 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@Service
@Slf4j
public class EncondRuleServiceImpl extends BatchServiceImpl<EncondRuleMapper, EncondRule> implements EncondRuleService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private EncondRuleMapper encondRuleMapper;

    @Autowired
    private EncondRuleDetailService encondRuleDetailService;

    /**
    * 单表简单查询操作
    * @param encondRuleDto
    * @return
    */
    @Override
    public Page list(EncondRuleDto encondRuleDto) {
        LambdaQueryWrapper<EncondRule> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<EncondRule> page = encondRuleMapper.selectPage(encondRuleDto.getPage(),queryWrapper);
        Page<EncondRuleVo> pageVo = DozerUtils.mapPage(dozerMapper, page, EncondRuleVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param encondRuleDto
    * @return
    */
    @Override
    public Page lists(EncondRuleDto encondRuleDto) {
        Page<EncondRuleVo> pageVo = encondRuleMapper.getPageList(encondRuleDto.getPage(),encondRuleDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public EncondRuleVo get(Long id) {
        EncondRule encondRule = super.getById(id);
        EncondRuleVo encondRuleVo = null;
        if(encondRule !=null){
            encondRuleVo = dozerMapper.map(encondRule,EncondRuleVo.class);
            //根据id获取encondRuleDetailVoList
            List<EncondRuleDetailVo> encondRuleDetailVoList = encondRuleDetailService.getByFkIdVo(id);
            //设置encondRuleVo，以便构建其对应的子表数据
            encondRuleVo.setEncondRuleDetailList(encondRuleDetailVoList);
        }
        return encondRuleVo;
    }

    /**
    * 保存实体
    * @param encondRuleDto
    * @return
    */
    @Transactional
    @Override
    public void save(EncondRuleDto encondRuleDto) {
        //查看是否 有重复的
        //1 相同的流程实例的规则只有一条 2 同一个表单只能添加一次
        LambdaQueryWrapper<EncondRule> queryWrapper = new LambdaQueryWrapper<>();
        if(encondRuleDto.getRuleType().equals("1")){
            if(StringUtils.isEmpty(encondRuleDto.getEncondField())){
                throw new IncloudException("未选择业务规则值字段！");
            }
            queryWrapper.eq(ObjectUtil.isNotEmpty(encondRuleDto.getFormName()),EncondRule::getFormName,encondRuleDto.getFormName());
            queryWrapper.eq(ObjectUtil.isNotEmpty(encondRuleDto.getEncondField()),EncondRule::getEncondField,encondRuleDto.getEncondField());
            if(CollectionUtil.isNotEmpty(this.list(queryWrapper))){
                throw new IncloudException("请勿重复添加同一业务表单的相同业务规则字段值规则！");
            }
        }else if(encondRuleDto.getRuleType().equals("2")){
            //encondRuleDto.setFormName(EncodFormName.WF_FORM_NAME.getCode());
            queryWrapper.eq(EncondRule::getRuleType,"2");
            queryWrapper.eq(EncondRule::getCamundaProcdefId,encondRuleDto.getCamundaProcdefId());
            if(CollectionUtil.isNotEmpty(this.list(queryWrapper))){
                throw new IncloudException("请勿重复添加相同的流程实例规则！");
            }
        }else if(encondRuleDto.getRuleType().equals("3")){
            //encondRuleDto.setFormName(EncodFormName.QT_FORM_NAME.getCode());
        }else{
            throw new IncloudException("请选择规则类型！");
        }
        if(ObjectUtil.isNotEmpty(encondRuleDto.getEncondRuleDetailList()) && !encondRuleDto.getEncondRuleDetailList().isEmpty()){
            int sort = 0;
            for (int i = 0; i < encondRuleDto.getEncondRuleDetailList().size(); i++) {
                sort++;
                encondRuleDto.getEncondRuleDetailList().get(i).setSort(sort);
                if(encondRuleDto.getEncondRuleDetailList().get(i).getEncondType().equals("自动计数")&&StringUtils.isEmpty(encondRuleDto.getEncondRuleDetailList().get(i).getResetCycle())){
                    throw new IncloudException("重置周期不能为空！");
                }
            }
        }
        LocalDateTime now = LocalDateTime.now();
        encondRuleDto.setId(IdGenerator.getIdGenerator());
        encondRuleDto.setUpdateTime(now);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        //如果登录用户不为空
        if(ObjectUtil.isNotEmpty(loginAppUser)){
            encondRuleDto.setCreateUserId(loginAppUser.getId());
            encondRuleDto.setCreateUserName(loginAppUser.getUserName());
            encondRuleDto.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
            encondRuleDto.setCreateUserParentOrgName(loginAppUser.getParentOrgName());
            encondRuleDto.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
            encondRuleDto.setCreateUserParentDeptName(loginAppUser.getParentDeptName());
            encondRuleDto.setCreateUserOrgFullId(loginAppUser.getOrgFullId());
        }
        EncondRule encondRule = dozerMapper.map(encondRuleDto,EncondRule.class);
        super.save(encondRule);
        saveSubList(encondRuleDto);

    }

    /**
     * 保存集合
     * @param encondRuleDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<EncondRuleDto> encondRuleDtos){
        List<EncondRule> EncondRules = DozerUtils.mapList(dozerMapper, encondRuleDtos, EncondRule.class);
        super.saveBatch(EncondRules);
        for (EncondRuleDto encondRuleDto : encondRuleDtos){
            saveSubList(encondRuleDto);
        }
    }

    /**
     * 保存子表集合
     * @param encondRuleDto
     * @return
     */
    @Transactional
    void saveSubList(EncondRuleDto encondRuleDto){
        //获取其子表集合
        List<EncondRuleDetailDto> encondRuleDetailDtoList = encondRuleDto.getEncondRuleDetailList();
        if(encondRuleDetailDtoList != null && !encondRuleDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(encondRuleDto,encondRuleDetailDtoList);
            encondRuleDetailDtoList.forEach(EncondRuleDetailDto -> {
                EncondRuleDetailDto.setRuleId(encondRuleDto.id);
            });
            //调用保存子表的集合方法
            encondRuleDetailService.saveList(encondRuleDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param encondRuleDto
     * @return
     */
    @Transactional
    @Override
    public void update(EncondRuleDto encondRuleDto) {
        encondRuleDto.setUpdateTime(LocalDateTime.now());
        EncondRule encondRule = dozerMapper.map(encondRuleDto,EncondRule.class);
        super.updateById(encondRule);
        updateSub(encondRuleDto);
    }

    /**
    * 修改子类实体
    * @param encondRuleDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(EncondRuleDto encondRuleDto) {
        List<EncondRuleDetailDto> encondRuleDetailDtoList = encondRuleDto.getEncondRuleDetailList();
        if(encondRuleDetailDtoList != null && !encondRuleDetailDtoList.isEmpty()){
            LambdaQueryWrapper<EncondRuleDetail> encondRuleDetailListQueryWrapper = new LambdaQueryWrapper<>();
            encondRuleDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(encondRuleDto.getId()),EncondRuleDetail::getRuleId,encondRuleDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            EntityListConvert.convert(encondRuleDto,encondRuleDetailDtoList);
            List<EncondRuleDetail> encondRuleDetails = DozerUtils.mapList(dozerMapper, encondRuleDetailDtoList, EncondRuleDetail.class);
            //调用更新方法
            encondRuleDetailService.saveOrUpdateOrDeleteBatch(encondRuleDetailListQueryWrapper,encondRuleDetails,encondRuleDetails.size());
            //如果子可能还有子，那么遍历子，然后调用其子方法的updateSub
            if(encondRuleDetailDtoList != null && !encondRuleDetailDtoList.isEmpty()){
                for(EncondRuleDetailDto encondRuleDetailDto : encondRuleDetailDtoList){
                    encondRuleDetailService.updateSub(encondRuleDetailDto);
                }
            }
        }
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public void delete(Long id) {
        super.removeById(id);
        encondRuleDetailService.deleteByFkId(id);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public void deleteByFkId(Long id){
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<EncondRuleVo> getByFkIdVo(Long id){
        return null;
    }
}
