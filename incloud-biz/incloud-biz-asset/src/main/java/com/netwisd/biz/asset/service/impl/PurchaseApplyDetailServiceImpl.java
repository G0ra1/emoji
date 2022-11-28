package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.biz.asset.constants.ViewerBusinessType;
import com.netwisd.biz.asset.dto.PurchaseApplyDto;
import com.netwisd.biz.asset.entity.*;
import com.netwisd.biz.asset.mapper.PurchaseApplyDetailMapper;
import com.netwisd.biz.asset.service.PurchaseApplyDetailService;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.PurchaseStorageDetailVo;
import com.netwisd.biz.asset.vo.ViewerVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.asset.dto.PurchaseApplyDetailDto;
import com.netwisd.biz.asset.vo.PurchaseApplyDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 购置申请详情 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-20 17:26:16
 */
@Service
@Slf4j
public class PurchaseApplyDetailServiceImpl extends ServiceImpl<PurchaseApplyDetailMapper, PurchaseApplyDetail> implements PurchaseApplyDetailService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PurchaseApplyDetailMapper purchaseApplyDetailMapper;

    @Autowired
    private ViewerService viewerService;

    /**
    * 单表简单查询操作
    * @param purchaseApplyDetailDto
    * @return
    */
    @Override
    public Page list(PurchaseApplyDetailDto purchaseApplyDetailDto) {
        LambdaQueryWrapper<PurchaseApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(AppUserUtil.getLoginAppUser())){
            queryWrapper.eq(PurchaseApplyDetail::getApplyUserOrgId, AppUserUtil.getLoginAppUser().getParentOrgId())
                    .eq(PurchaseApplyDetail::getApplyUserDeptId, AppUserUtil.getLoginAppUser().getParentDeptId())
                    .eq(PurchaseApplyDetail::getApplyUserId, AppUserUtil.getLoginAppUser().getId());
        }

        //指定的查询字段
        String  searchCondition= purchaseApplyDetailDto.getSearchCondition();
        //全局模糊查询
        if(ObjectUtil.isNotEmpty(searchCondition)){
            queryWrapper.and(q ->{
                q.like(PurchaseApplyDetail::getApplyCode,searchCondition)
                        .or(wrapper ->wrapper.like(PurchaseApplyDetail::getItemName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApplyDetail::getItemCode,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApplyDetail::getItemType,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApplyDetail::getClassifyCode,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApplyDetail::getDescshort,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApplyDetail::getUnitName,searchCondition))
                        .or(wrapper ->wrapper.like(PurchaseApplyDetail::getExplanation,searchCondition));
            });
        }
        queryWrapper.like(ObjectUtil.isNotEmpty(purchaseApplyDetailDto.getItemName()),PurchaseApplyDetail::getItemName,purchaseApplyDetailDto.getItemName())
                .like(ObjectUtil.isNotEmpty(purchaseApplyDetailDto.getItemCode()),PurchaseApplyDetail::getItemCode,purchaseApplyDetailDto.getItemCode());

        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        if (ObjectUtil.isNotEmpty(appUserId)) {
                //查看人员查看范围
                ViewerVo viewerVo = viewerService.getUserRange(appUserId, ViewerBusinessType.ASSETS.getCode());

                Long orgId = purchaseApplyDetailDto.getApplyUserOrgId();

                if (ObjectUtil.isNotEmpty(viewerVo)) {
                    queryWrapper.and(q -> {
                        q
                            //可看人员List
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseApplyDetail::getApplyUserId, viewerVo.getUserList()))
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getUserList()), i -> i.in(PurchaseApplyDetail::getCreateUserId, viewerVo.getUserList()))
                            //可看部门List
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseApplyDetail::getApplyUserDeptId, viewerVo.getDeptList()))
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getDeptList()), i -> i.in(PurchaseApplyDetail::getCreateUserParentDeptId, viewerVo.getDeptList()))
                            //可看机构List
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseApplyDetail::getApplyUserOrgId, viewerVo.getOrgList()))
                            .or(viewerVo != null && CollectionUtil.isNotEmpty(viewerVo.getOrgList()), i -> i.in(PurchaseApplyDetail::getCreateUserParentOrgId, viewerVo.getOrgList()));
                });
            }
        }
        queryWrapper.orderByDesc(PurchaseApplyDetail::getApplyTime);
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtil.isNotEmpty(purchaseApplyDetailDto.getStatus()),PurchaseApplyDetail::getStatus, YesNo.YES.getCode());
        Page<PurchaseApplyDetail> page = purchaseApplyDetailMapper.selectPage(purchaseApplyDetailDto.getPage(),queryWrapper);
        Page<PurchaseApplyDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseApplyDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param purchaseApplyDetailDto
    * @return
    */
    @Override
    public Page lists(PurchaseApplyDetailDto purchaseApplyDetailDto) {
        Page<PurchaseApplyDetailVo> pageVo = purchaseApplyDetailMapper.getPageList(purchaseApplyDetailDto.getPage(),purchaseApplyDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PurchaseApplyDetailVo get(Long id) {
        PurchaseApplyDetail purchaseApplyDetail = super.getById(id);
        PurchaseApplyDetailVo purchaseApplyDetailVo = null;
        if(purchaseApplyDetail !=null){
            purchaseApplyDetailVo = dozerMapper.map(purchaseApplyDetail,PurchaseApplyDetailVo.class);
        }
        log.debug("查询成功");
        return purchaseApplyDetailVo;
    }

    /**
    * 保存实体
    * @param purchaseApplyDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PurchaseApplyDetailDto purchaseApplyDetailDto) {
        PurchaseApplyDetail purchaseApplyDetail = dozerMapper.map(purchaseApplyDetailDto,PurchaseApplyDetail.class);
        boolean result = super.save(purchaseApplyDetail);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 保存集合
     * @param purchaseApplyDetailDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean saveList(List<PurchaseApplyDetailDto> purchaseApplyDetailDtos){
        List<PurchaseApplyDetail> purchaseApplyDetails = DozerUtils.mapList(dozerMapper, purchaseApplyDetailDtos, PurchaseApplyDetail.class);
        return super.saveBatch(purchaseApplyDetails);
    }

    /**
    * 修改实体
    * @param purchaseApplyDetailDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PurchaseApplyDetailDto purchaseApplyDetailDto) {
        purchaseApplyDetailDto.setUpdateTime(LocalDateTime.now());
        PurchaseApplyDetail purchaseApplyDetail = dozerMapper.map(purchaseApplyDetailDto,PurchaseApplyDetail.class);
        boolean result = super.updateById(purchaseApplyDetail);
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

    /**
     * 通过applyId删除
     * @param applyId
     * @return
     */
    @Transactional
    @Override
    public Boolean deleteByApplyId(Long applyId) {
        if (ObjectUtil.isNotEmpty(applyId)) {
            LambdaQueryWrapper<PurchaseApplyDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.eq(PurchaseApplyDetail::getApplyId,applyId);
            Integer result =  purchaseApplyDetailMapper.delete(detailLambdaQueryWrapper);
            return null != result && result >= 1;
        }
        return false;
    }


    /**
     * 通过外建获取
     * @param applyId
     * @return
     */
    @Override
    public List<PurchaseApplyDetailVo> getByApplyId(Long applyId){
        LambdaQueryWrapper<PurchaseApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(applyId),PurchaseApplyDetail::getApplyId,applyId);
        List<PurchaseApplyDetail> list = this.list(queryWrapper);
        List<PurchaseApplyDetailVo> purchaseStorageDetailVos = DozerUtils.mapList(dozerMapper, list, PurchaseApplyDetailVo.class);
        return purchaseStorageDetailVos;
    }

    @Override
    public Page getPageByApplyId(Long applyId) {
        LambdaQueryWrapper<PurchaseApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(applyId),PurchaseApplyDetail::getApplyId,applyId);

        Page<PurchaseApplyDetail> page = purchaseApplyDetailMapper.selectPage(new PurchaseApplyDetailDto().getPage(),queryWrapper);
        Page<PurchaseApplyDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseApplyDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public List<PurchaseApplyDetailVo> getByApplyIds(String applyIds) {
        String[] ids = applyIds.split(",");
        LambdaQueryWrapper<PurchaseApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(applyIds),PurchaseApplyDetail::getApplyId,ids);
        queryWrapper.orderByDesc(PurchaseApplyDetail::getApplyTime);
        List<PurchaseApplyDetail> list = this.list(queryWrapper);
        List<PurchaseApplyDetailVo> purchaseStorageDetailVos = DozerUtils.mapList(dozerMapper, list, PurchaseApplyDetailVo.class);
        return purchaseStorageDetailVos;
    }

    @Override
    public Page getPageByApplyIds(String applyIds) {
        String[] ids = applyIds.split(",");
        LambdaQueryWrapper<PurchaseApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ObjectUtil.isNotEmpty(applyIds),PurchaseApplyDetail::getApplyId,ids);
        Page<PurchaseApplyDetail> page = purchaseApplyDetailMapper.selectPage(new PurchaseApplyDetailDto().getPage(),queryWrapper);
        Page<PurchaseApplyDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseApplyDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page getRegisterList(PurchaseApplyDetailDto purchaseApplyDetailDto) {
        LambdaQueryWrapper<PurchaseApplyDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(PurchaseApplyDetail::getApplyNumber , BigDecimal.ZERO);
        queryWrapper.gt(PurchaseApplyDetail::getNotRegisterNumber , BigDecimal.ZERO);
        Page<PurchaseApplyDetail> page = purchaseApplyDetailMapper.selectPage(purchaseApplyDetailDto.getPage(),queryWrapper);
        Page<PurchaseApplyDetailVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PurchaseApplyDetailVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

}
