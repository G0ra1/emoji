package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.biz.asset.constants.ItemTypeEnum;
import com.netwisd.biz.asset.dto.*;
import com.netwisd.biz.asset.entity.MaterialAcceptResult;
import com.netwisd.biz.asset.entity.MaterialWithdrawal;
import com.netwisd.biz.asset.entity.MaterialWithdrawalDetail;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaterialAcceptResultMapper;
import com.netwisd.biz.asset.mapper.MaterialWithdrawalMapper;
import com.netwisd.biz.asset.service.*;
import com.netwisd.biz.asset.vo.*;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.common.db.util.EntityListConvert;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 资产退库 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
@Service
@Slf4j
public class MaterialWithdrawalServiceImpl extends BatchServiceImpl<MaterialWithdrawalMapper, MaterialWithdrawal> implements MaterialWithdrawalService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialWithdrawalMapper materialWithdrawalMapper;

    @Autowired
    private MaterialWithdrawalDetailService materialWithdrawalDetailService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private MaterialAcceptResultService materialAcceptResultService;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetsDetailService assetsDetailService;

    @Autowired
    private SuppliesService suppliesService;

    @Autowired
    private SuppliesDetailService suppliesDetailService;

    @Autowired
    private MaterialAcceptResultMapper materialAcceptResultMapper;



    /**
     * 单表简单查询操作
     *
     * @param materialWithdrawalDto
     * @return
     */
    @Override
    public Page list(MaterialWithdrawalDto materialWithdrawalDto) {
        LambdaQueryWrapper<MaterialWithdrawal> queryWrapper = new LambdaQueryWrapper<>();
        //根据申请人增加过滤条件
        if (ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())) {
            queryWrapper.eq(MaterialWithdrawal::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(MaterialWithdrawal::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(MaterialWithdrawal::getApplyUserId, AppUserUtil.getLoginAppUser().getId())
                    .orderByDesc(MaterialWithdrawal::getApplyTime);
        }
        //指定的全局模糊查询字段
        String searchCondition = materialWithdrawalDto.getSearchCondition();
        if (ObjectUtil.isNotEmpty(searchCondition)) {
            queryWrapper.and(q -> {
                q.like(MaterialWithdrawal::getCode, searchCondition)
                        .or(wrapper -> wrapper.like(MaterialWithdrawal::getSumTotalAmount, searchCondition))
                        .or(wrapper -> wrapper.like(MaterialWithdrawal::getApplyUserName, searchCondition))
                        .or(wrapper -> wrapper.like(MaterialWithdrawal::getApplyUserDeptName, searchCondition))
                        .or(wrapper -> wrapper.like(MaterialWithdrawal::getApplyUserOrgName, searchCondition));
            });
        }
        //根据实际业务构建具体的参数做查询
        Page<MaterialWithdrawal> page = materialWithdrawalMapper.selectPage(materialWithdrawalDto.getPage(), queryWrapper);
        Page<MaterialWithdrawalVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialWithdrawalVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param materialWithdrawalDto
     * @return
     */
    @Override
    public Page lists(MaterialWithdrawalDto materialWithdrawalDto) {
        Page<MaterialWithdrawalVo> pageVo = materialWithdrawalMapper.getPageList(materialWithdrawalDto.getPage(), materialWithdrawalDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public MaterialWithdrawalVo get(Long id) {
        MaterialWithdrawal materialWithdrawal = super.getById(id);
        MaterialWithdrawalVo materialWithdrawalVo = null;
        if (materialWithdrawal != null) {
            materialWithdrawalVo = dozerMapper.map(materialWithdrawal, MaterialWithdrawalVo.class);
            //根据id获取materialWithdrawalDetailVoList
            List<MaterialWithdrawalDetailVo> materialWithdrawalDetailVoList = materialWithdrawalDetailService.getByFkIdVo(id);
            //设置materialWithdrawalVo，以便构建其对应的子表数据
            materialWithdrawalVo.setMaterialWithdrawalDetailList(materialWithdrawalDetailVoList);
        }
        return materialWithdrawalVo;
    }

    /**
     * 保存实体
     *
     * @param materialWithdrawalDto
     * @return
     */
    @Transactional
    @Override
    public void save(MaterialWithdrawalDto materialWithdrawalDto) {
        if (StringUtils.isBlank(materialWithdrawalDto.getCode())) {
            getMaxcode(materialWithdrawalDto);
        }
        MaterialWithdrawal materialWithdrawal = dozerMapper.map(materialWithdrawalDto, MaterialWithdrawal.class);
        super.save(materialWithdrawal);
        saveSubList(materialWithdrawalDto);
    }

    /**
     * 获取申请编号
     *
     * @return
     */
    private void getMaxcode(MaterialWithdrawalDto materialWithdrawalDto) {
        //获取申请编号
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap entityMap = objectMapper.convertValue(materialWithdrawalDto, HashMap.class);
        String code = dictClient.getRuleValue("material_withdrawal", "code", entityMap);
        log.debug("规则值：{}", code);
        if (StringUtils.isNotBlank(code)) {
            materialWithdrawalDto.setCode(code);
        }
    }

    /**
     * 保存集合
     *
     * @param materialWithdrawalDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaterialWithdrawalDto> materialWithdrawalDtos) {
        List<MaterialWithdrawal> MaterialWithdrawals = DozerUtils.mapList(dozerMapper, materialWithdrawalDtos, MaterialWithdrawal.class);
        super.saveBatch(MaterialWithdrawals);
        for (MaterialWithdrawalDto materialWithdrawalDto : materialWithdrawalDtos) {
            saveSubList(materialWithdrawalDto);
        }
    }

    /**
     * 保存子表集合
     *
     * @param materialWithdrawalDto
     * @return
     */
    @Transactional
    void saveSubList(MaterialWithdrawalDto materialWithdrawalDto) {
        //获取其子表集合
        List<MaterialWithdrawalDetailDto> materialWithdrawalDetailDtoList = materialWithdrawalDto.getMaterialWithdrawalDetailList();
        if (materialWithdrawalDetailDtoList != null && !materialWithdrawalDetailDtoList.isEmpty()) {
            //给子表的关联id赋值
            for (MaterialWithdrawalDetailDto materialWithdrawalDetailDto : materialWithdrawalDetailDtoList) {
                materialWithdrawalDetailDto.setWithdrawalId(materialWithdrawalDto.getId());
            }
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(materialWithdrawalDto,materialWithdrawalDetailDtoList);
            //调用保存子表的集合方法
            materialWithdrawalDetailService.saveList(materialWithdrawalDetailDtoList);
        }
    }

    /**
     * 修改实体
     *
     * @param materialWithdrawalDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaterialWithdrawalDto materialWithdrawalDto) {
        materialWithdrawalDto.setUpdateTime(LocalDateTime.now());
        MaterialWithdrawal materialWithdrawal = dozerMapper.map(materialWithdrawalDto, MaterialWithdrawal.class);
        super.updateById(materialWithdrawal);
        updateSub(materialWithdrawalDto);
    }

    /**
     * 修改子类实体
     *
     * @param materialWithdrawalDto
     * @return
     */
    @Transactional
    @Override
    public void updateSub(MaterialWithdrawalDto materialWithdrawalDto) {
        List<MaterialWithdrawalDetailDto> materialWithdrawalDetailDtoList = materialWithdrawalDto.getMaterialWithdrawalDetailList();
        if (materialWithdrawalDetailDtoList != null && !materialWithdrawalDetailDtoList.isEmpty()) {
            LambdaQueryWrapper<MaterialWithdrawalDetail> materialWithdrawalDetailListQueryWrapper = new LambdaQueryWrapper<>();
            materialWithdrawalDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(materialWithdrawalDto.getId()), MaterialWithdrawalDetail::getWithdrawalId, materialWithdrawalDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            EntityListConvert.convert(materialWithdrawalDto, materialWithdrawalDetailDtoList);
            List<MaterialWithdrawalDetail> materialWithdrawalDetails = DozerUtils.mapList(dozerMapper, materialWithdrawalDetailDtoList, MaterialWithdrawalDetail.class);
            //调用更新方法
            materialWithdrawalDetailService.saveOrUpdateOrDeleteBatch(materialWithdrawalDetailListQueryWrapper, materialWithdrawalDetails, materialWithdrawalDetails.size());
            //如果子可能还有子，那么遍历子，然后调用其子方法的updateSub
            if (materialWithdrawalDetailDtoList != null && !materialWithdrawalDetailDtoList.isEmpty()) {
                for (MaterialWithdrawalDetailDto materialWithdrawalDetailDto : materialWithdrawalDetailDtoList) {
                    materialWithdrawalDetailService.updateSub(materialWithdrawalDetailDto);
                }
            }
        }
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public void delete(Long id) {
        super.removeById(id);
        materialWithdrawalDetailService.deleteByFkId(id);
    }

    /**
     * 通过外建删除
     *
     * @param id
     * @return
     */
    @Override
    public void deleteByFkId(Long id) {
    }

    /**
     * 通过外建获取
     *
     * @param id
     * @return
     */
    @Override
    public List<MaterialWithdrawalVo> getByFkIdVo(Long id) {
        return null;
    }

    /**
     * 流程中新增或修改
     *
     * @param materialWithdrawalDto
     * @return Result
     */
    @Transactional
    @Override
    public MaterialWithdrawalVo procSaveOrUpdate(MaterialWithdrawalDto materialWithdrawalDto) {
        log.debug("procSaveOrUpdate ---> 资产：{},流程中新增或修改");

        MaterialWithdrawalVo materialWithdrawalVo = this.get(materialWithdrawalDto.getId());
        //判断是否未编辑、是否是起草
        //若不是起草(流程中保存)，手动调用保存前方法
        if (ObjectUtil.isNotEmpty(materialWithdrawalVo)
                && ObjectUtil.isNotEmpty(materialWithdrawalVo.getAuditStatus())
                && materialWithdrawalVo.getAuditStatus() != WfProcessEnum.DRAFT.getType()){
            this.procSaveBefore(materialWithdrawalVo.getCamundaProcinsId());
        }
        //获取编码规则
        if (StringUtils.isBlank(materialWithdrawalDto.getCode())) {
            getMaxcode(materialWithdrawalDto);
        }

        MaterialWithdrawal withdrawal = dozerMapper.map(materialWithdrawalDto, MaterialWithdrawal.class);
        //通过外键删除子表
        materialWithdrawalDetailService.deleteByFkId(materialWithdrawalDto.getId());
        List<MaterialWithdrawalDetailDto> detailList = materialWithdrawalDto.getMaterialWithdrawalDetailList();
        if (CollectionUtils.isNotEmpty(detailList)) {
            List<MaterialWithdrawalDetail> materialWithdrawalDetails = DozerUtils.mapList(dozerMapper, detailList, MaterialWithdrawalDetail.class);
            //给子表的关联Id赋值
            for (MaterialWithdrawalDetail detail : materialWithdrawalDetails) {
                detail.setWithdrawalId(withdrawal.getId());
            }
            materialWithdrawalDetailService.saveOrUpdateBatch(materialWithdrawalDetails);
        }
        super.saveOrUpdate(withdrawal);

        return this.get(withdrawal.getId());
    }

    /**
     * 通过流程实例id进行查询
     *
     * @param procInstId
     * @return
     */
    @Override
    public MaterialWithdrawalVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaterialWithdrawal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialWithdrawal::getCamundaProcinsId, procInstId);
        List<MaterialWithdrawal> materialWithdrawalList = super.list(queryWrapper);
        MaterialWithdrawalVo materialWithdrawalVo = null;
        if (materialWithdrawalList != null && materialWithdrawalList.size() > 0) {
            materialWithdrawalVo = dozerMapper.map(materialWithdrawalList.get(0), MaterialWithdrawalVo.class);
            //根据id获取子表信息
            List<MaterialWithdrawalDetailVo> detailVoList = materialWithdrawalDetailService.getByFkIdVo(materialWithdrawalVo.getId());
            //构建对应的子表数据
            materialWithdrawalVo.setMaterialWithdrawalDetailList(detailVoList);
        }
        return materialWithdrawalVo;
    }

    /**
     * 通过流程实例id进行删除
     *
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public void deleteByProc(String procInstId) {
        MaterialWithdrawalVo materialWithdrawalVo = this.getByProc(procInstId);
        if (materialWithdrawalVo != null) {
            this.delete(materialWithdrawalVo.getId());
        }

    }

    /**
     * 退库流程结束执行的方法
     *
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public Boolean procAuditSuccess(String procInstId) {
        MaterialWithdrawalVo withdrawalVo = this.getByProc(procInstId);
        List<MaterialWithdrawalDetailVo> detailList = withdrawalVo.getMaterialWithdrawalDetailList();
        if (detailList.size()>0){
            for (MaterialWithdrawalDetailVo detailVo : detailList){
                //获取本次退库数量
                BigDecimal withdrawalNumber = detailVo.getWithdrawalNumber();
                //获取物资类型
                String itemType = detailVo.getItemType();
              /*  if (ObjectUtil.isNotEmpty(detailVo.getAcceptResultId())){
                //根据acceptResultId查询领用结果表对应数据
                MaterialAcceptResultVo materialAcceptResultVo = materialAcceptResultService.get(detailVo.getAcceptResultId());
                if (ObjectUtil.isNotEmpty(materialAcceptResultVo)){
                    MaterialAcceptResultDto acceptResultDto = dozerMapper.map(materialAcceptResultVo, MaterialAcceptResultDto.class);
                    //获取领用结果表未退库数量
                    BigDecimal oldAcceptNumber = acceptResultDto.getAcceptNumber();
                    //更新领用结果表领用数量(减)
                    BigDecimal newAcceptNumber = oldAcceptNumber.subtract(withdrawalNumber);
                    acceptResultDto.setNotRefundNumber(newAcceptNumber);
                    materialAcceptResultService.update(acceptResultDto);
                    log.debug("procAuditSuccess ---> 资产：{},更新前领用结果表：{},更新后领用结果表：{}",acceptResultDto.getId(),oldAcceptNumber,newAcceptNumber);
                }

                }*/
                if (ObjectUtil.isNotEmpty(detailVo.getAssetsId())){
                    if (!StringUtils.isBlank(itemType) && (itemType.equals(ItemTypeEnum.INVENTORY.code) || itemType.equals(ItemTypeEnum.ASSET.code))) {
                        //根据assetsId查询库存台账表对应的数据
                        AssetsVo assetsVo = assetsService.get(detailVo.getAssetsId());
                        AssetsDto assetsDto = dozerMapper.map(assetsVo, AssetsDto.class);
                        //获取当前领用数量
                        BigDecimal oldAcceptNumber = assetsDto.getAcceptNumber();
                        //更新领用数量（减）
                        BigDecimal newAcceptNumber = oldAcceptNumber.subtract(withdrawalNumber);
                        assetsDto.setAcceptNumber(newAcceptNumber);
                        //获取当前可用数量
                        BigDecimal oldUsableNumber = assetsDto.getUsableNumber();
                        //更新可用数量（加）
                        BigDecimal newUsableNumber = oldUsableNumber.add(withdrawalNumber);
                        assetsDto.setUsableNumber(newUsableNumber);
                        //获取当前库存数量
                       /* BigDecimal oldStockNumber = assetsDto.getStockNumber();
                        //更新库存数量（加）
                        BigDecimal newStockNumber = oldStockNumber.add(withdrawalNumber);
                        assetsDto.setStockNumber(newStockNumber);
                        //获取当前入库数量
                        BigDecimal oldStorageNumber = assetsDto.getStorageNumber();
                        //更新入库数量
                        BigDecimal newStorageNumber = oldStorageNumber.add(withdrawalNumber);
                        assetsDto.setStorageNumber(newStorageNumber);*/
                        assetsService.update(assetsDto);

                        //更新详情表数据
                        AssetsDetailVo assetsDetailVo = assetsDetailService.get(detailVo.getAssetsDetailId());
                        AssetsDetailDto assetsDetailDto = dozerMapper.map(assetsDetailVo, AssetsDetailDto.class);
                        //获取详情表当前领用数量
                        BigDecimal oldDetailAcceptNumber = assetsDetailDto.getAcceptNumber();
                        //更新详情表领用数量（减）
                        BigDecimal newDetailAcceptNumber = oldDetailAcceptNumber.subtract(withdrawalNumber);
                        assetsDetailDto.setAcceptNumber(newDetailAcceptNumber);
                        //获取详情表当前可用数量
                        BigDecimal oldDetailUsableNumber = assetsDetailDto.getUsableNumber();
                        //更新详情表可用数量（加）
                        BigDecimal newDetailUsableNumber = oldDetailUsableNumber.add(withdrawalNumber);
                        assetsDetailDto.setUsableNumber(newDetailUsableNumber);
                        //获取详情表当前库存数量
                       /* BigDecimal oldDetailStockNumber = assetsDetailDto.getStockNumber();
                        //更新详情表库存数量（加）
                        BigDecimal newDetailStockNumber = oldDetailStockNumber.add(withdrawalNumber);
                        assetsDetailDto.setStockNumber(newDetailStockNumber);
                        //获取详情表当前入库数量
                        BigDecimal oldDetailStorageNumber = assetsDetailDto.getStorageNumber();
                        //更新详情表入库数量
                        BigDecimal newDetailStorageNumber = oldDetailStorageNumber.add(withdrawalNumber);
                        assetsDetailDto.setStorageNumber(newDetailStorageNumber);*/
                        assetsDetailDto.setAssetUserName(null);
                        assetsDetailDto.setAssetUserId(null);
                        assetsDetailDto.setStatus("已入库");
                        assetsDetailService.update(assetsDetailDto);
                    }else{
                        //根据assetsId查询耗材台账表对应的数据
                        SuppliesVo suppliesVo = suppliesService.get(detailVo.getAssetsId());
                        SuppliesDto suppliesDto = dozerMapper.map(suppliesVo, SuppliesDto.class);
                        //获取当前领用数量
                        BigDecimal oldAcceptNumber = suppliesDto.getAcceptNumber();
                        //更新领用数量（减）
                        BigDecimal newAcceptNumber = oldAcceptNumber.subtract(withdrawalNumber);
                        suppliesDto.setAcceptNumber(newAcceptNumber);//获取当前可用数量
                        BigDecimal oldUsableNumber = suppliesDto.getUsableNumber();
                        //更新可用数量（加）
                        BigDecimal newUsableNumber = oldUsableNumber.add(withdrawalNumber);
                        suppliesDto.setUsableNumber(newUsableNumber);
                        //获取当前库存数量
                       /* BigDecimal oldStockNumber = suppliesDto.getStockNumber();
                        //更新库存数量（加）
                        BigDecimal newStockNumber = oldStockNumber.add(withdrawalNumber);
                        suppliesDto.setStockNumber(newStockNumber);
                        //获取当前入库数量
                        BigDecimal oldStorageNumber = suppliesDto.getStorageNumber();
                        //更新入库数量
                        BigDecimal newStorageNumber = oldStorageNumber.add(withdrawalNumber);
                        suppliesDto.setStorageNumber(newStorageNumber);*/
                        suppliesService.update(suppliesDto);

                        //更新详情表数据
                        SuppliesDetailVo suppliesDetailVo = suppliesDetailService.get(detailVo.getAssetsDetailId());
                        SuppliesDetailDto suppliesDetailDto = dozerMapper.map(suppliesDetailVo, SuppliesDetailDto.class);
                        //获取详情表当前领用数量
                        BigDecimal oldDetailAcceptNumber = suppliesDetailDto.getAcceptNumber();
                        //更新详情表领用数量（减）
                        BigDecimal newDetailAcceptNumber = oldDetailAcceptNumber.subtract(withdrawalNumber);
                        suppliesDetailDto.setAcceptNumber(newDetailAcceptNumber);
                        //获取详情表当前可用数量
                        BigDecimal oldDetailUsableNumber = suppliesDetailDto.getUsableNumber();
                        //更新详情表可用数量（加）
                        BigDecimal newDetailUsableNumber = oldDetailUsableNumber.add(withdrawalNumber);
                        suppliesDetailDto.setUsableNumber(newDetailUsableNumber);
                        //获取详情表当前库存数量
                        /*BigDecimal oldDetailStockNumber = suppliesDetailDto.getStockNumber();
                        //更新详情表库存数量（加）
                        BigDecimal newDetailStockNumber = oldDetailStockNumber.add(withdrawalNumber);
                        suppliesDetailDto.setStockNumber(newDetailStockNumber);
                        //获取详情表当前入库数量
                        BigDecimal oldDetailStorageNumber = suppliesDetailDto.getStorageNumber();
                        //更新详情表入库数量
                        BigDecimal newDetailStorageNumber = oldDetailStorageNumber.add(withdrawalNumber);
                        suppliesDetailDto.setStorageNumber(newDetailStorageNumber);*/
                        suppliesDetailService.update(suppliesDetailDto);
                    }
                }
            }
        }

        return true;

    }

    /**
     * 退库流程保存前执行方法
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public Boolean procSaveBefore(String procInstId) {
        MaterialWithdrawalVo withdrawalVo = this.getByProc(procInstId);
        List<MaterialWithdrawalDetailVo> detailList = withdrawalVo.getMaterialWithdrawalDetailList();

        if (detailList.size()>0){
            for (MaterialWithdrawalDetailVo detailVo : detailList){
                //获取本次退库数量
                BigDecimal withdrawalNumber = detailVo.getWithdrawalNumber();
                //根据acceptResultId查询领用结果表对应数据
                MaterialAcceptResultVo materialAcceptResultVo = materialAcceptResultService.get(detailVo.getAcceptResultId());
                if (ObjectUtil.isNotEmpty(materialAcceptResultVo)){
                    MaterialAcceptResultDto acceptResultDto = dozerMapper.map(materialAcceptResultVo, MaterialAcceptResultDto.class);
                    //获取领用结果表未退库数量
                    BigDecimal oldNotRefundNumber = acceptResultDto.getNotRefundNumber();
                    //更新未退库数量
                    BigDecimal newNotRefundNumber = oldNotRefundNumber.add(withdrawalNumber);
                    acceptResultDto.setNotRefundNumber(newNotRefundNumber);
                    materialAcceptResultService.update(acceptResultDto);
                    log.debug("procSaveBefore ---> 资产：{},更新前结果表未退库数量：{},更新后入库数量：{}",detailVo.getId(),oldNotRefundNumber,newNotRefundNumber);

                }


            }
        }

        return true;
    }
    /**
     * 退库流程保存后
     * @param procInstId
     * @return
     */
    @Transactional
    @Override
    public Boolean procSaveAfter(String procInstId) {
        log.debug("procSaveAfter ---> 资产：{},保存后方法");
        MaterialWithdrawalVo withdrawalVo = this.getByProc(procInstId);
        List<MaterialWithdrawalDetailVo> detailList = withdrawalVo.getMaterialWithdrawalDetailList();
        if (detailList.size()>0){
            for (MaterialWithdrawalDetailVo detailVo : detailList){
                //获取本次退库数量
                BigDecimal withdrawalNumber = detailVo.getWithdrawalNumber();
                //根据acceptResultId查询领用结果表对应数据
                MaterialAcceptResultVo materialAcceptResultVo = materialAcceptResultService.get(detailVo.getAcceptResultId());
                if (ObjectUtil.isNotEmpty(materialAcceptResultVo)){
                    MaterialAcceptResultDto acceptResultDto = dozerMapper.map(materialAcceptResultVo, MaterialAcceptResultDto.class);
                    //获取领用结果表未退库数量
                    BigDecimal oldNotRefundNumber = acceptResultDto.getNotRefundNumber();
                    //更新未退库数量
                    BigDecimal newNotRefundNumber = oldNotRefundNumber.subtract(withdrawalNumber);
                    acceptResultDto.setNotRefundNumber(newNotRefundNumber);
                    materialAcceptResultService.update(acceptResultDto);
                }


            }
        }
        return true;
    }
    /*
    * 获取待退库物资信息
    * */
    @Override
    public Page<MaterialAcceptResultVo> getWithdrawalDetail(MaterialWithdrawalDto materialWithdrawalDto) {
        LambdaQueryWrapper<MaterialAcceptResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(materialWithdrawalDto.getApplyUserId()),MaterialAcceptResult::getCreateUserId,materialWithdrawalDto.getApplyUserId())
                .gt(MaterialAcceptResult::getNotRefundNumber, YesNo.NO.code)
                .orderByDesc(MaterialAcceptResult::getCreateTime);
        MaterialAcceptResultDto materialAcceptResultDto = new MaterialAcceptResultDto();

        Page<MaterialAcceptResult> page = materialAcceptResultMapper.selectPage(materialAcceptResultDto.getPage(), queryWrapper);
        Page<MaterialAcceptResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialAcceptResultVo.class);
        log.debug("查询条数" + pageVo.getTotal());
        return pageVo;
    }
}
