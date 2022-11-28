package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.dto.ViewerDetailDto;
import com.netwisd.biz.asset.dto.ViewerDto;
import com.netwisd.biz.asset.entity.Viewer;
import com.netwisd.biz.asset.entity.ViewerDetail;
import com.netwisd.biz.asset.mapper.ViewerMapper;
import com.netwisd.biz.asset.service.ViewerDetailService;
import com.netwisd.biz.asset.service.ViewerService;
import com.netwisd.biz.asset.vo.ViewerDetailVo;
import com.netwisd.biz.asset.vo.ViewerVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import com.netwisd.common.db.util.EntityListConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 物资数据权限人员表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@Service
@Slf4j
public class ViewerServiceImpl extends BatchServiceImpl<ViewerMapper, Viewer> implements ViewerService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ViewerMapper viewerMapper;

    @Autowired
    private ViewerDetailService viewerDetailService;

//    @Autowired
//    private MdmClient mdmClient;

    /**
    * 单表简单查询操作
    * @param viewerDto
    * @return
    */
    @Override
    public Page list(ViewerDto viewerDto) {
        LambdaQueryWrapper<Viewer> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<Viewer> page = viewerMapper.selectPage(viewerDto.getPage(),queryWrapper);
        Page<ViewerVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ViewerVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param viewerDto
    * @return
    */
    @Override
    public Page lists(ViewerDto viewerDto) {
        Page<ViewerVo> pageVo = viewerMapper.getPageList(viewerDto.getPage(),viewerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ViewerVo get(Long id) {
        Viewer viewer = super.getById(id);
        ViewerVo viewerVo = null;
        if(viewer !=null){
            viewerVo = dozerMapper.map(viewer,ViewerVo.class);
            //根据id获取viewerDetailVoList
            List<ViewerDetailVo> viewerDetailVoList = viewerDetailService.getByViewerId(id);
            //设置viewerVo，以便构建其对应的子表数据
            viewerVo.setDetailList(viewerDetailVoList);
        }
        return viewerVo;
    }

    /**
    * 保存实体
    * @param viewerDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ViewerDto viewerDto) {
        //Viewer viewer = dozerMapper.map(viewerDto,Viewer.class);
        //return super.save(viewer) && saveSubList(viewerDto);

        Boolean result = true;
        //当前默认都是人员类型 --- 1人员 2角色/岗位
        if(ObjectUtil.isEmpty(viewerDto.getViewerType())){
            viewerDto.setViewerType(1);
        }
        Viewer viewer = dozerMapper.map(viewerDto,Viewer.class);
        //可以选 一次选多个查看人 一个人保存一条查看者记录
        if(StringUtils.isEmpty(viewerDto.getUserIds())){
            throw new IncloudException("未选择查看人！");
        }
        if(CollectionUtil.isEmpty(viewerDto.getDetailList())){
            throw new IncloudException("未选择查看范围！");
        }
        String userIds = viewerDto.getUserIds();
        String userNames = viewerDto.getUserNames();
        String userNameChs = viewerDto.getUserNameChs();
        List<String> userIdList = Arrays.asList(userIds.split(","));
        List<String> userNameList = Arrays.asList(userNames.split(","));
        List<String> userNameChList = Arrays.asList(userNameChs.split(","));
        //如果重复选人保存 更新
        for (int i = 0; i <userIdList.size() ; i++) {
            viewer.setUserId(Long.valueOf(userIdList.get(i)));
            viewer.setUserName(userNameList.get(i));
            viewer.setUserNameCh(userNameChList.get(i));
            //查看 是否有重复人员
            LambdaQueryWrapper<Viewer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Viewer::getViewerType,viewerDto.getViewerType());
            queryWrapper.eq(Viewer::getBusinessType,viewerDto.getBusinessType());
            queryWrapper.eq(Viewer::getUserId, Long.valueOf(userIdList.get(i)));
            List<Viewer> listVie = viewerMapper.selectList(queryWrapper);
            //更新
            if(listVie.size()>0){
                result = super.updateById(viewer) && this.updateSub(viewerDto);
            }else{
                viewer.setId(IdGenerator.getIdGenerator());
                viewerDto.setId(viewer.getId());
                result = super.save(viewer) && saveSubList(viewerDto);
            }
        }
        return result;
    }

    /**
     * 保存集合
     * @param viewerDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<ViewerDto> viewerDtos){
        List<Viewer> Viewers = DozerUtils.mapList(dozerMapper, viewerDtos, Viewer.class);
        super.saveBatch(Viewers);
        for (ViewerDto viewerDto : viewerDtos){
            saveSubList(viewerDto);
        }
    }

    /**
     * 保存子表集合
     * @param viewerDto
     * @return
     */
    @Transactional
    public Boolean saveSubList(ViewerDto viewerDto){
        Boolean result = true ;
        //获取其子表集合
        List<ViewerDetailDto> viewerDetailDtoList = viewerDto.getDetailList();
        if(viewerDetailDtoList != null && !viewerDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(viewerDto,viewerDetailDtoList);
            viewerDetailDtoList.forEach(detailDto -> {
                detailDto.setViewerId(viewerDto.getId());
            });
            //调用保存子表的集合方法
            result = viewerDetailService.saveList(viewerDetailDtoList);
        }
        return result;
    }

    /**
     * 修改实体
     * @param viewerDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(ViewerDto viewerDto) {
        viewerDto.setUpdateTime(LocalDateTime.now());
        Viewer viewer = dozerMapper.map(viewerDto,Viewer.class);
        return super.updateById(viewer) && updateSub(viewerDto);
    }

    /**
    * 修改子类实体
    * @param viewerDto
    * @return
    */
    @Transactional
    @Override
    public Boolean updateSub(ViewerDto viewerDto) {
        Boolean result = true ;
        List<ViewerDetailDto> viewerDetailDtoList = viewerDto.getDetailList();
        if(viewerDetailDtoList != null && !viewerDetailDtoList.isEmpty()){
            LambdaQueryWrapper<ViewerDetail> viewerDetailListQueryWrapper = new LambdaQueryWrapper<>();
            viewerDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(viewerDto.getId()),ViewerDetail::getViewerId,viewerDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            EntityListConvert.convert(viewerDto,viewerDetailDtoList);
            List<ViewerDetail> viewerDetails = DozerUtils.mapList(dozerMapper, viewerDetailDtoList, ViewerDetail.class);
            //调用更新方法
            result =  viewerDetailService.saveOrUpdateOrDeleteBatch(viewerDetailListQueryWrapper,viewerDetails,viewerDetails.size());
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
        return super.removeById(id) && viewerDetailService.deleteByViewerId(id);
    }

    @Override
    public ViewerVo getUserRange(Long userId,Integer businessType) {
        LambdaQueryWrapper<Viewer> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(Viewer::getUserId,userId);
        queryWrapper.eq(Viewer::getBusinessType,businessType);

        List<Viewer> listVie = viewerMapper.selectList(queryWrapper);
        ViewerVo viewerVo = new ViewerVo();

        List<Long> orgIdList = new ArrayList<>();
        List<Long> deptIdist = new ArrayList<>();
        List<Long> userIdList = new ArrayList<>();
        Long appUserId = AppUserUtil.getLoginAppUser().getId();
        userIdList.add(userId);

        if(ObjectUtil.isNotEmpty(appUserId) && (appUserId == userId || appUserId.equals(userId))){
            orgIdList.add(AppUserUtil.getLoginAppUser().getParentOrgId());
            deptIdist.add(AppUserUtil.getLoginAppUser().getParentDeptId());
        }

        if(CollectionUtil.isNotEmpty(listVie)){
            Viewer viewer = listVie.get(0);
            viewerVo = dozerMapper.map(viewer,ViewerVo.class);

            LambdaQueryWrapper<ViewerDetail> queryVisibleRange = new LambdaQueryWrapper<>();
            //根据实际业务构建具体的参数做查询
            queryVisibleRange.eq(ViewerDetail::getViewerId,viewerVo.getId());
            List<ViewerDetail> visibleRangeList = viewerDetailService.list(queryVisibleRange);

            if(CollectionUtil.isNotEmpty(visibleRangeList)){
                visibleRangeList.forEach(visibleRange -> {
                    //人员
                    if(visibleRange.getVisibleType().equals(1)){
                        userIdList.add(visibleRange.getRangeId());
                        //部门
                    }else if(visibleRange.getVisibleType().equals(2)){
                        //递归查询出该部门的所有子部门
                        //MdmOrgVo mdmOrgVo = mdmClient.getOrgOrDept(VisibleRange.getRangeId());
                        //deptIdist.add(VisibleRange.getViewerId());
//                        List<MdmOrgAllVo> list = mdmClient.getDeptOrOrgById(VisibleRange.getRangeId(),2);
//                        if(CollectionUtil.isNotEmpty(list)){
//                            List<Long> listIds = list.stream().map(MdmOrgAllVo::getId).collect(Collectors.toList());
//                            deptIdist.removeAll(listIds);
//                            deptIdist.addAll(listIds);
//                        }
//                        deptIdist.add(visibleRange.getViewerId());
                        deptIdist.add(visibleRange.getRangeId());
                        //机构
                    } else if(visibleRange.getVisibleType().equals(3)){
                        //递归查询出该部门的所有子机构
//                        List<MdmOrgAllVo> list = mdmClient.getDeptOrOrgById(VisibleRange.getRangeId(),3);
//                        if(CollectionUtil.isNotEmpty(list)){
//                            List<Long> listIds = list.stream().map(MdmOrgAllVo::getId).collect(Collectors.toList());
//                            orgIdList.removeAll(listIds);
//                            orgIdList.addAll(listIds);
//                        }
//                        orgIdList.add(visibleRange.getViewerId());
                        orgIdList.add(visibleRange.getRangeId());
                    }

                });
            }
        }
        viewerVo.setOrgList(orgIdList);
        viewerVo.setDeptList(deptIdist);
        viewerVo.setUserList(userIdList);
        return viewerVo;
    }


}
