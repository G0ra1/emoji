package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.dto.MaintainAssetsResultDto;
import com.netwisd.biz.asset.dto.MaintainPlanDetailDto;
import com.netwisd.biz.asset.dto.MaintainPlanDto;
import com.netwisd.biz.asset.entity.MaintainPlan;
import com.netwisd.biz.asset.entity.MaintainPlanDetail;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaintainPlanMapper;
import com.netwisd.biz.asset.service.MaintainAssetsResultService;
import com.netwisd.biz.asset.service.MaintainPlanDetailService;
import com.netwisd.biz.asset.service.MaintainPlanService;
import com.netwisd.biz.asset.vo.MaintainPlanDetailVo;
import com.netwisd.biz.asset.vo.MaintainPlanVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 维修计划 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-25 14:07:05
 */
@Service
@Slf4j
public class MaintainPlanServiceImpl extends BatchServiceImpl<MaintainPlanMapper, MaintainPlan> implements MaintainPlanService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaintainPlanMapper maintainPlanMapper;

    @Autowired
    private MaintainPlanDetailService maintainPlanDetailService;

    @Autowired
    private MaintainAssetsResultService maintainAssetsResultService;

    @Autowired
    private DictClient dictClient;
    /**
    * 单表简单查询操作
    * @param maintainPlanDto
    * @return
    */
    @Override
    public Page list(MaintainPlanDto maintainPlanDto) {
        LambdaQueryWrapper<MaintainPlan> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaintainPlan> page = maintainPlanMapper.selectPage(maintainPlanDto.getPage(),queryWrapper);
        Page<MaintainPlanVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaintainPlanVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param maintainPlanDto
    * @return
    */
    @Override
    public Page lists(MaintainPlanDto maintainPlanDto) {
        Page<MaintainPlanVo> pageVo = maintainPlanMapper.getPageList(maintainPlanDto.getPage(),maintainPlanDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaintainPlanVo get(Long id) {
        MaintainPlan maintainPlan = super.getById(id);
        MaintainPlanVo maintainPlanVo = null;
        if(maintainPlan !=null){
            maintainPlanVo = dozerMapper.map(maintainPlan,MaintainPlanVo.class);
            //根据id获取maintainPlanDetailVoList
            List<MaintainPlanDetailVo> maintainPlanDetailVoList = maintainPlanDetailService.getByFkIdVo(id);
            //设置maintainPlanVo，以便构建其对应的子表数据
            maintainPlanVo.setMaintainPlanDetailList(maintainPlanDetailVoList);
        }
        return maintainPlanVo;
    }

    /**
    * 保存实体
    * @param maintainPlanDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaintainPlanDto maintainPlanDto) {
        //设置维修计划编号
        if (StringUtils.isBlank(maintainPlanDto.getCode())){
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap entityMap = objectMapper.convertValue(maintainPlanDto, HashMap.class);
            String code = dictClient.getRuleValue("maintain_plan","code",entityMap);
            log.debug("规则值:{}",code);
            if (StringUtils.isNotBlank(code)){
                maintainPlanDto.setCode(code);
            }
        }
        MaintainPlan maintainPlan = dozerMapper.map(maintainPlanDto,MaintainPlan.class);
        super.save(maintainPlan);
        saveSubList(maintainPlanDto);
    }
    /**
     * 获取维修计划编号
     * @param
     * @return
     */
    private String getMaxCode(){
        String maxCode = maintainPlanMapper.getMaxCode();
        String planCode = "WXJH-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"-";
        if (StringUtils.isBlank(maxCode)){
            planCode += "0001";
        }else{
            DecimalFormat decimalFormat = new DecimalFormat("0000");
            planCode += decimalFormat.format(Long.valueOf(maxCode)+1);
        }
        log.debug("出库申请编号===>{}",planCode);
        return planCode;
    }

    /**
     * 保存集合
     * @param maintainPlanDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaintainPlanDto> maintainPlanDtos){
        List<MaintainPlan> MaintainPlans = DozerUtils.mapList(dozerMapper, maintainPlanDtos, MaintainPlan.class);
        super.saveBatch(MaintainPlans);
        for (MaintainPlanDto maintainPlanDto : maintainPlanDtos){
            saveSubList(maintainPlanDto);
        }
    }

    /**
     * 保存子表集合
     * @param maintainPlanDto
     * @return
     */
    @Transactional
    void saveSubList(MaintainPlanDto maintainPlanDto){
        //获取其子表集合
        List<MaintainPlanDetailDto> maintainPlanDetailDtoList = maintainPlanDto.getMaintainPlanDetailList();
        if(maintainPlanDetailDtoList != null && !maintainPlanDetailDtoList.isEmpty()){
            //给子表关联建赋值
            maintainPlanDetailDtoList.forEach(maintainPlanDetailDto -> {
                maintainPlanDetailDto.setMaintainPlanId(maintainPlanDto.getId());
            });
            //调用保存子表的集合方法
            maintainPlanDetailService.saveList(maintainPlanDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param maintainPlanDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaintainPlanDto maintainPlanDto) {
        maintainPlanDto.setUpdateTime(LocalDateTime.now());
        MaintainPlan maintainPlan = dozerMapper.map(maintainPlanDto,MaintainPlan.class);
        super.updateById(maintainPlan);
        updateSub(maintainPlanDto);
    }

    /**
    * 修改子类实体
    * @param maintainPlanDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaintainPlanDto maintainPlanDto) {
        List<MaintainPlanDetailDto> maintainPlanDetailDtoList = maintainPlanDto.getMaintainPlanDetailList();
        if(maintainPlanDetailDtoList != null && !maintainPlanDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaintainPlanDetail> maintainPlanDetailListQueryWrapper = new LambdaQueryWrapper<>();
            maintainPlanDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(maintainPlanDto.getId()),MaintainPlanDetail::getMaintainPlanId,maintainPlanDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(maintainPlanDto,maintainPlanDetailDtoList);
            //给子表的关联建赋值
            maintainPlanDetailDtoList.forEach(maintainPlanDetailDto -> {
                maintainPlanDetailDto.setMaintainPlanId(maintainPlanDto.getId());
            });
            List<MaintainPlanDetail> maintainPlanDetails = DozerUtils.mapList(dozerMapper, maintainPlanDetailDtoList, MaintainPlanDetail.class);
            //调用更新方法
            maintainPlanDetailService.saveOrUpdateOrDeleteBatch(maintainPlanDetailListQueryWrapper,maintainPlanDetails,maintainPlanDetails.size());
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
        maintainPlanDetailService.deleteByFkId(id);
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
    public List<MaintainPlanVo> getByFkIdVo(Long id){
        return null;
    }
    /**
     * 通过流程id获取表单信息
     * @param procInstId
     * @return
     */
    @Override
    public MaintainPlanVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaintainPlan> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MaintainPlan::getCamundaProcinsId,procInstId);
        MaintainPlan maintainPlan = maintainPlanMapper.selectOne(lambdaQueryWrapper);
        MaintainPlanVo maintainPlanVo = null;
        if (maintainPlan != null){
            maintainPlanVo = dozerMapper.map(maintainPlan,MaintainPlanVo.class);
            //根据id获取子表信息
            List<MaintainPlanDetailVo> maintainPlanDetailVoList = maintainPlanDetailService.getByFkIdVo(maintainPlanVo.getId());
            //设置maintainPlanVo，便于构建其对应的子表数据
            maintainPlanVo.setMaintainPlanDetailList(maintainPlanDetailVoList);
        }
        return maintainPlanVo;
    }
    /**
     * 维修计划流程审批结束后生成结果表
     * @param processInstanceId
     * @return
     */
    @Override
    public Result maintainProcSuccess(String processInstanceId) {
        MaintainPlanVo maintainPlanVo = this.getByProc(processInstanceId);
        //通过维修计划详情生成结果表
        List<MaintainAssetsResultDto> maintainAssetsResultDtos = DozerUtils.mapList(dozerMapper,maintainPlanVo.getMaintainPlanDetailList(),MaintainAssetsResultDto.class);
        maintainAssetsResultDtos.forEach(maintainAssetsResultDto->{
            maintainAssetsResultDto.setMaintainPlanCode(maintainPlanVo.getCode());
            maintainAssetsResultDto.setPlanYear(maintainPlanVo.getPlanYear());
            maintainAssetsResultDto.setMaintainPlanDetailId(maintainAssetsResultDto.getId());
            maintainAssetsResultDto.setNotMaintainNumber(maintainAssetsResultDto.getMaintainPlanNumber());
            maintainAssetsResultDto.setPlanType(0);
            maintainAssetsResultDto.setId(null);
           });
        maintainAssetsResultService.saveList(maintainAssetsResultDtos);
        return Result.success();
    }
}
