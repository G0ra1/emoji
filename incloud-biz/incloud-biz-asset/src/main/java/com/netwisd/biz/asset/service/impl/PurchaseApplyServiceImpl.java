package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.constants.ViewerTypeEnum;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.PurchaseApplyDetailMapper;
import com.netwisd.biz.asset.mapper.PurchaseApplyMapper;
import com.netwisd.biz.asset.mapper.PurchaseApplyResultMapper;
import com.netwisd.biz.asset.service.PurchaseApplyDetailService;
import com.netwisd.biz.asset.service.PurchaseApplyResultService;
import com.netwisd.biz.asset.service.PurchaseApplyService;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.PurchaseApplyDetailVo;
import com.netwisd.biz.asset.vo.ViewerVo;
import com.netwisd.common.core.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.asset.vo.PurchaseApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 购置申请 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 10:10:21
 */
@Service
@Slf4j
public class PurchaseApplyServiceImpl extends ServiceImpl<PurchaseApplyMapper, PurchaseApply> implements PurchaseApplyService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseApplyMapper purchaseApplyMapper;

    @Autowired
    private PurchaseApplyDetailService detailService;

    @Autowired
    private PurchaseApplyResultService resultService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private ViewerService viewerService;

    /**
    * 单表简单查询操作
    * @param purchaseApplyDto
    * @return
    */
    @Override
    public Page list(PurchaseApplyDto purchaseApplyDto) {

        LambdaQueryWrapper<PurchaseApply> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(PurchaseApply::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                        .eq(PurchaseApply::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                        .eq(PurchaseApply::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        queryWrapper.orderByDesc(PurchaseApply::getApplyTime);
        //指定的查询字段
        String  searchCondition= purchaseApplyDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(PurchaseApply::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getPlanYear,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyTime,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getItemTypeName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getExplanation,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getSumTotalAmount,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getSumTotalNumber,searchCondition));
            });
        }
        queryWrapper.eq(ObjectUtil.isNotEmpty(purchaseApplyDto.getApplyUserOrgId()),PurchaseApply::getApplyUserOrgName,purchaseApplyDto.getApplyUserOrgName())
            .eq(ObjectUtil.isNotEmpty(purchaseApplyDto.getApplyUserDeptId()),PurchaseApply::getApplyUserDeptName,purchaseApplyDto.getApplyUserDeptName())
            .eq(ObjectUtil.isNotEmpty(purchaseApplyDto.getApplyUserName()),PurchaseApply::getApplyUserName,purchaseApplyDto.getApplyUserName())
            .eq(ObjectUtil.isNotEmpty(purchaseApplyDto.getCode()),PurchaseApply::getCode,purchaseApplyDto.getCode())
            .eq(ObjectUtil.isNotEmpty(purchaseApplyDto.getPlanYear()),PurchaseApply::getPlanYear,purchaseApplyDto.getPlanYear())
            .eq(ObjectUtil.isNotEmpty(purchaseApplyDto.getItemTypeName()),PurchaseApply::getItemTypeName,purchaseApplyDto.getItemTypeName());

        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        //查看人员查看范围
        ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.LIST.getCode());

        if (ObjectUtil.isNotEmpty(viewerVo)) {
            queryWrapper.and(q -> {
                q
                        //可看人员List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseApply::getApplyUserId, viewerVo.getUserList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseApply::getCreateUserId, viewerVo.getUserList()))
                        //可看部门List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseApply::getApplyUserDeptId, viewerVo.getDeptList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseApply::getCreateUserParentDeptId, viewerVo.getDeptList()))
                        //可看机构List
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseApply::getApplyUserOrgId, viewerVo.getOrgList()))
                        .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseApply::getCreateUserParentOrgId, viewerVo.getOrgList()));

            });

        }


        Page<PurchaseApply> page = purchaseApplyMapper.selectPage(purchaseApplyDto.getPage(),queryWrapper);
        Page<PurchaseApplyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseApplyVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseApplyDto
    * @return
    */
    @Override
    public Page lists(PurchaseApplyDto purchaseApplyDto) {
        Page<PurchaseApplyVo> pageVo = purchaseApplyMapper.getPageList(purchaseApplyDto.getPage(),purchaseApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseApplyVo get(Long id) {
        PurchaseApply purchaseApply = super.getById(id);
        PurchaseApplyVo purchaseApplyVo = null;
        if(purchaseApply !=null){
            purchaseApplyVo = dozerMapper.map(purchaseApply,PurchaseApplyVo.class);
        }
        List<PurchaseApplyDetailVo> detailVos=detailService.getByApplyId(id);
        purchaseApplyVo.setDetailList(detailVos);
        log.debug("查询成功");
        return purchaseApplyVo;
    }

    /**
     * 通过ids查询实体列表
     * @param ids
     * @return
     */
    @Override
    public List<PurchaseApplyVo> getByIds(String ids) {
        List<PurchaseApply> purchaseApplyList = super.list(Wrappers.<PurchaseApply>lambdaQuery().in(PurchaseApply::getId, ids));
        return DozerUtils.mapList(dozerMapper,purchaseApplyList,PurchaseApplyVo.class);
    }

    /**
    * 保存实体
    * @param purchaseApplyDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseApplyDto purchaseApplyDto) {
        //获取申请编号
        if(StringUtils.isBlank(purchaseApplyDto.getCode())){
            getMaxCode(purchaseApplyDto);
        }
        purchaseApplyDto.setStatus("0");//未采购
        PurchaseApply purchaseApply = dozerMapper.map(purchaseApplyDto,PurchaseApply.class);
        boolean result = super.save(purchaseApply);
        boolean resultDetail = saveSubList(purchaseApplyDto);
        if(result && resultDetail){
            log.debug("保存成功");
        }
        return result && resultDetail;
    }

    /**
     * 保存集合
     * @param purchaseApplyDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseApplyDto> purchaseApplyDtos){
        List<PurchaseApply> purchaseApplys = DozerUtils.mapList(dozerMapper, purchaseApplyDtos, PurchaseApply.class);
        for (PurchaseApplyDto purchaseApplyDto : purchaseApplyDtos){
            //获取申请编号
           if(StringUtils.isBlank(purchaseApplyDto.getCode())){
                getMaxCode(purchaseApplyDto);
            }
            purchaseApplyDto.setStatus("0");//未采购
            saveSubList(purchaseApplyDto);
        }
        return super.saveBatch(purchaseApplys);
    }

    /**
     * 保存子表集合
     * @param purchaseApplyDto
     * @return
     */
    @Transactional
    Boolean saveSubList(PurchaseApplyDto purchaseApplyDto){
        String itemCodes = purchaseApplyDto.getItemType();
        //获取其子表集合
        List<PurchaseApplyDetailDto> purchaseApplyDetailDtos = purchaseApplyDto.getDetailList();

        List<PurchaseApplyDetail> purchaseApplyDetailList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(purchaseApplyDetailDtos)){
            purchaseApplyDetailDtos.forEach(purchaseApplyDetailDto -> {
                /*
                根据主表所选的类型，保存对应的子表数据
                （前端删数据不方便）
                 */
                String itemCode = purchaseApplyDetailDto.getItemCode();
                if(itemCodes.contains(itemCode)){

                    PurchaseApplyDetail purchaseApplyDetail = dozerMapper.map(purchaseApplyDetailDto,PurchaseApplyDetail.class);
                    BigDecimal applyNumber = purchaseApplyDetail.getApplyNumber();

                    purchaseApplyDetail.setRegisterNumber(BigDecimal.ZERO);
                    purchaseApplyDetail.setNotRegisterNumber(applyNumber);
                    purchaseApplyDetail.setAcceptanceNumber(BigDecimal.ZERO);
                    purchaseApplyDetail.setStorageNumber(BigDecimal.ZERO);
                    purchaseApplyDetail.setApplyId(purchaseApplyDto.getId());
                    purchaseApplyDetail.setApplyCode(purchaseApplyDto.getCode());
                    purchaseApplyDetail.setApplyUserId(purchaseApplyDto.getApplyUserId());
                    purchaseApplyDetail.setApplyUserName(purchaseApplyDto.getApplyUserName());
                    purchaseApplyDetail.setApplyUserOrgId(purchaseApplyDto.getApplyUserOrgId());
                    purchaseApplyDetail.setApplyUserOrgName(purchaseApplyDto.getApplyUserOrgName());
                    purchaseApplyDetail.setApplyUserDeptId(purchaseApplyDto.getApplyUserDeptId());
                    purchaseApplyDetail.setApplyUserDeptName(purchaseApplyDto.getApplyUserDeptName());
                    purchaseApplyDetail.setApplyTime(purchaseApplyDto.getApplyTime());
                    purchaseApplyDetail.setPlanYear(purchaseApplyDto.getPlanYear());
                    purchaseApplyDetailList.add(purchaseApplyDetail);
                }
            });
        }
        //调用保存子表的集合方法
        return detailService.saveOrUpdateBatch(purchaseApplyDetailList);
    }


    /**
    * 修改实体
    * @param purchaseApplyDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PurchaseApplyDto purchaseApplyDto) {
        purchaseApplyDto.setUpdateTime(LocalDateTime.now());
        PurchaseApply purchaseApply = dozerMapper.map(purchaseApplyDto,PurchaseApply.class);
        boolean result = super.updateById(purchaseApply);
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
        //删除原子表
        detailService.remove(Wrappers.<PurchaseApplyDetail>lambdaQuery().eq(PurchaseApplyDetail::getApplyId, id));
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public PurchaseApplyVo getByProcId(String procInstId) {
        List<PurchaseApply> purchaseApplyList = super.list(Wrappers.<PurchaseApply>lambdaQuery().eq(PurchaseApply::getCamundaProcinsId, procInstId));
        PurchaseApplyVo purchaseApplyVo = null;
        if(purchaseApplyList !=null && purchaseApplyList.size()>0){
            purchaseApplyVo = dozerMapper.map(purchaseApplyList.get(0),PurchaseApplyVo.class);
            List<PurchaseApplyDetailVo> detailVos = detailService.getByApplyId(purchaseApplyVo.getId());
            purchaseApplyVo.setDetailList(detailVos);
        }
        log.debug("查询成功");
        return purchaseApplyVo;
    }

    @Override
    @Transactional
    public void deleteByProc(String procInstId) {
        PurchaseApplyVo purchaseApplyVo = this.getByProcId(procInstId);
        this.delete(purchaseApplyVo.getId());
        detailService.remove(Wrappers.<PurchaseApplyDetail>lambdaQuery().eq(PurchaseApplyDetail::getApplyId,purchaseApplyVo.getId()));
    }

    /**
     * 获取申请编号
     * @return
     */
    private void getMaxCode(PurchaseApplyDto purchaseApplyDto){
        //获取申请编号
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(purchaseApplyDto,HashMap.class);
        String code = dictClient.getRuleValue("purchase_apply","code",entityMap);
        log.debug("规则值:{}",code);
        if(StringUtils.isNotBlank(code)){
            purchaseApplyDto.setCode(code);
        }
    }

    @Override
    @Transactional
    public PurchaseApplyVo procSaveOrUpdate(PurchaseApplyDto purchaseApplyDto) {
        String itemTypes = purchaseApplyDto.getItemType();
        //获取申请编号
        if(StringUtils.isBlank(purchaseApplyDto.getCode())){
            getMaxCode(purchaseApplyDto);
        }
        if(StringUtils.isBlank(purchaseApplyDto.getStatus())){
            purchaseApplyDto.setStatus("0");//未采购
        }

        PurchaseApply purchaseApply = dozerMapper.map(purchaseApplyDto,PurchaseApply.class);
        if(ObjectUtil.isNotEmpty(purchaseApplyDto.getId())){
            detailService.deleteByApplyId(purchaseApplyDto.getId());
        }
        List<PurchaseApplyDetailDto> purchaseApplyDetailDtos = purchaseApplyDto.getDetailList();

        List<PurchaseApplyDetail> purchaseApplyDetailList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(purchaseApplyDetailDtos)){
            purchaseApplyDetailDtos.forEach(purchaseApplyDetailDto -> {
                /*
                根据主表所选的类型，保存对应的子表数据
                （前端删数据不方便）
                 */
                String itemType = purchaseApplyDetailDto.getItemType();
                if(itemTypes.contains(itemType)){
                    PurchaseApplyDetail purchaseApplyDetail = dozerMapper.map(purchaseApplyDetailDto,PurchaseApplyDetail.class);

//                    BigDecimal applyNumber = purchaseApplyDetail.getApplyNumber();
//                    applyNumber = ObjectUtil.isEmpty(applyNumber) ? BigDecimal.ZERO : applyNumber;
//
//                    BigDecimal applySumAmount = purchaseApplyDetail.getApplySumAmount();
//                    applySumAmount = ObjectUtil.isEmpty(applySumAmount) ? BigDecimal.ZERO : applySumAmount;

//                    if(ObjectUtil.isEmpty(purchaseApplyDetail.getRegisterNumber())){
//                        purchaseApplyDetail.setRegisterNumber(BigDecimal.ZERO);
//                    }
//                    if(ObjectUtil.isEmpty(purchaseApplyDetail.getNotRegisterNumber())){
//                        purchaseApplyDetail.setNotRegisterNumber(applyNumber);
//                    }
//                    if(ObjectUtil.isEmpty(purchaseApplyDetail.getAcceptanceNumber())){
//                        purchaseApplyDetail.setAcceptanceNumber(BigDecimal.ZERO);
//                    }
//                    if(ObjectUtil.isEmpty(purchaseApplyDetail.getStorageNumber())){
//                        purchaseApplyDetail.setStorageNumber(BigDecimal.ZERO);
//                    }
//                    if(ObjectUtil.isEmpty(purchaseApplyDetail.getRegisterSumAmount())){
//                        purchaseApplyDetail.setRegisterSumAmount(BigDecimal.ZERO);
//                    }
//                    if(ObjectUtil.isEmpty(purchaseApplyDetail.getNotRegisterSumAmount())){
//                        purchaseApplyDetail.setNotRegisterSumAmount(applySumAmount);
//                    }

                    if(ObjectUtil.isEmpty(purchaseApplyDetail.getStatus())){
                        purchaseApplyDetail.setStatus(YesNo.NO.code);
                    }
                    purchaseApplyDetail.setApplyId(purchaseApplyDto.getId());
                    purchaseApplyDetail.setApplyCode(purchaseApplyDto.getCode());
                    purchaseApplyDetail.setApplyUserId(purchaseApplyDto.getApplyUserId());
                    purchaseApplyDetail.setApplyUserName(purchaseApplyDto.getApplyUserName());
                    purchaseApplyDetail.setApplyUserOrgId(purchaseApplyDto.getApplyUserOrgId());
                    purchaseApplyDetail.setApplyUserOrgName(purchaseApplyDto.getApplyUserOrgName());
                    purchaseApplyDetail.setApplyUserDeptId(purchaseApplyDto.getApplyUserDeptId());
                    purchaseApplyDetail.setApplyUserDeptName(purchaseApplyDto.getApplyUserDeptName());
                    purchaseApplyDetail.setApplyTime(purchaseApplyDto.getApplyTime());
                    purchaseApplyDetail.setPlanYear(purchaseApplyDto.getPlanYear());
                    purchaseApplyDetailList.add(purchaseApplyDetail);
                }
            });
        }
        super.saveOrUpdate(purchaseApply);
        detailService.saveOrUpdateBatch(purchaseApplyDetailList);
        return this.getByProcId(purchaseApply.getCamundaProcinsId());

    }

    @Override
    @Transactional
    public Boolean purApplyAuditSuccess(String procInstId) {
        PurchaseApplyVo applyVo = this.getByProcId(procInstId);
        PurchaseApplyDto applyDto = dozerMapper.map(applyVo,PurchaseApplyDto.class);

        applyDto.setRegisterNumber(BigDecimal.ZERO);
        applyDto.setNotRegisterNumber(applyDto.getSumTotalNumber());
        applyDto.setNotRegisterAmount(applyDto.getSumTotalAmount());
        //        BigDecimal sumApplyNUmber = BigDecimal.ZERO;
//        BigDecimal sumApplyAmount = BigDecimal.ZERO;
        List<PurchaseApplyDetailVo> detailVos =applyVo.getDetailList();
        List<PurchaseApplyDetail> details = DozerUtils.mapList(dozerMapper,detailVos,PurchaseApplyDetail.class);
        for (PurchaseApplyDetail detail: details) {
            BigDecimal applyNumber = detail.getApplyNumber();
            BigDecimal applySumAmount = detail.getApplySumAmount();


            detail.setStatus(YesNo.YES.code);
            detail.setRegisterNumber(BigDecimal.ZERO);

            // detail.setNotRegisterNumber(applyNumber);
            detail.setAcceptanceNumber(BigDecimal.ZERO);
            detail.setStorageNumber(BigDecimal.ZERO);
            detail.setRegisterSumAmount(BigDecimal.ZERO);
            detail.setNotRegisterSumAmount(applySumAmount);
            //设置未登记数量
            BigDecimal oldNotRegisterNumber = detail.getNotRegisterNumber();
            if(oldNotRegisterNumber == null) oldNotRegisterNumber = BigDecimal.ZERO;
            BigDecimal newNotRegisterNumber = oldNotRegisterNumber.add(applyNumber);
            detail.setNotRegisterNumber(newNotRegisterNumber);
            detail.setAcceptanceNumber(BigDecimal.ZERO);
            detail.setStorageNumber(BigDecimal.ZERO);
        }
        return this.update(applyDto) && detailService.updateBatchById(details);
    }

    /**
     * 单表简单查询操作
     * @param purchaseApplyDto
     * @return
     */
    @Override
    public Page getRegisterList(PurchaseApplyDto purchaseApplyDto) {
        LambdaQueryWrapper<PurchaseApply> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(PurchaseApply::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(PurchaseApply::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(PurchaseApply::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }
        //状态筛选字段
        queryWrapper.eq(PurchaseApply::getAuditStatus, WfProcessEnum.DONE.getType());
        queryWrapper.ne(PurchaseApply::getStatus,"1");
        queryWrapper.orderByDesc(PurchaseApply::getApplyTime);

        //根据实际业务构建具体的参数做查询
        //指定的查询字段
        String searchCondition= purchaseApplyDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(PurchaseApply::getCode,searchCondition)
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyUserDeptName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyUserOrgName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getPlanYear,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyTime,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getItemTypeName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getExplanation,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getApplyUserName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getSumTotalAmount,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApply::getSumTotalNumber,searchCondition));
            });
        }
        Page<PurchaseApply> page = purchaseApplyMapper.selectPage(purchaseApplyDto.getPage(),queryWrapper);
        Page<PurchaseApplyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseApplyVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }
}
