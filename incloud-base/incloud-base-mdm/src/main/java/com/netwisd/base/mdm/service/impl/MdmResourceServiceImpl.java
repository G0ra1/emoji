package com.netwisd.base.mdm.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.dto.MdmResourceDto;
import com.netwisd.base.common.mdm.vo.*;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.mdm.constants.AdminEnum;
import com.netwisd.base.mdm.constants.NeoNodeEnum;
import com.netwisd.base.mdm.entity.MdmResource;
import com.netwisd.base.mdm.mapper.MdmResourceMapper;
import com.netwisd.base.mdm.service.*;
import com.netwisd.base.mdm.utils.ResTreeUtil;
import com.netwisd.base.mdm.vo.MdmRoleResourceVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description 资源管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-09 10:39:04
 */
@Service
@Slf4j
public class MdmResourceServiceImpl extends ServiceImpl<MdmResourceMapper, MdmResource> implements MdmResourceService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmResourceMapper mdmResourceMapper;

    @Autowired
    private MdmRoleResourceService mdmRoleResourceService;

    @Autowired
    private NeoService neoService;

    @Autowired
    private MdmPostUserService mdmPostUserService;

    @Autowired
    private MdmRoleUserService mdmRoleUserService;

    @Autowired
    private MdmRolePostService mdmRolePostService;

    /**
     * 默认root信息
     */
    private static Long defaultRootId = 0l;

    /**
     * 默认root信息
     */
    private static String defaultRootName = "root";

    /**
    * 单表简单查询操作
    * @param mdmResourceDto
    * @return
    */
    @Override
    public List list(MdmResourceDto mdmResourceDto) {
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        //如果sysid为0，查全部
//        if (defaultRootId.longValue() != mdmResourceDto.getSysId().longValue()){
//            //首先根据子系统条件查询
//            queryWrapper.eq(ObjectUtil.isNotNull(mdmResourceDto.getSysId()),MdmResource::getSysId,mdmResourceDto.getSysId());
//            queryWrapper.eq(StringUtils.isNotBlank(mdmResourceDto.getSysName()),MdmResource::getSysName,mdmResourceDto.getSysName());
//            queryWrapper.eq(ObjectUtil.isNotNull(mdmResourceDto.getStatus()),MdmResource::getStatus,mdmResourceDto.getStatus());
//        }
        //按照层级倒序排，层级低的放前边
        queryWrapper.orderByDesc(MdmResource::getLevel);
        queryWrapper.ne(MdmResource::getId,defaultRootId);
        List<MdmResource> list = list(queryWrapper);
        //拼装数据
        List<MdmResourceAllVo> resourceAllVos = handlerData(list);
        //排序
        listSort(resourceAllVos);
        log.debug("查询条数:");
        return resourceAllVos;
    }

    /**
     * 排序
     * @param resourceAllVos
     */
    private void listSort(List<MdmResourceAllVo> resourceAllVos) {
        CollectionUtil.sort(resourceAllVos, new Comparator<MdmResourceAllVo>() {
            @Override
            public int compare(MdmResourceAllVo o1, MdmResourceAllVo o2) {
                int i = o1.getSort() - o2.getSort();
                return i;
            }
        });
        sortKids(resourceAllVos);
    }

    private void sortKids(List<MdmResourceAllVo> resourceAllVos) {
        for (MdmResourceAllVo resourceAllVo : resourceAllVos) {
            List<MdmResourceAllVo> kids = resourceAllVo.getKids();
            if (CollectionUtil.isNotEmpty(kids)){
                sortKids(kids);
            }
            CollectionUtil.sort(kids, new Comparator<MdmResourceAllVo>() {
                @Override
                public int compare(MdmResourceAllVo o1, MdmResourceAllVo o2) {
                    int i = o1.getSort() - o2.getSort();
                    return i;
                }
            });
        }
    }

    /**
     * 拼装为树形结构
     * @param list
     */
    private List<MdmResourceAllVo> handlerData(List<MdmResource> list) {
        //用来存放结果的集合
        List<MdmResourceAllVo> result = new ArrayList<>();
        List<MdmResourceAllVo> mdmResourceAllVos = DozerUtils.mapList(dozerMapper, list, MdmResourceAllVo.class);
        Map<Long, MdmResourceAllVo> groupMap = mdmResourceAllVos.stream().collect(Collectors.toMap(MdmResourceAllVo::getId, Function.identity(), (key1, key2) -> key2));
        //遍历结果集，处理数据
        for (MdmResourceAllVo vo : mdmResourceAllVos){
            Long parentId = vo.getParentId();
            MdmResourceAllVo parentObj = groupMap.get(parentId);
            //如果从map中找到其父级，那么就把父级的list里加上自己
            if(ObjectUtil.isNotEmpty(parentObj)){
                //加上自己，因为是引用，所以getKids再add后，引用数据就加入进去了；
                parentObj.getKids().add(vo);
                //从map里把自己干掉，下次不再处理自己——因为列表排序是按level倒序排的，前面的处理的都是最末级层的
                groupMap.remove(vo.getId());
            }
        }
        //最后整合完的map里，就是我们要的结果
        for (Map.Entry<Long,MdmResourceAllVo> entry:groupMap.entrySet()){
            result.add(entry.getValue());
        }
        return result;
    }

    /**
    * 自定义查询操作
    * @param mdmResourceDto
    * @return
    */
    @Override
    public List<MdmResourceVo> lists(MdmResourceDto mdmResourceDto) {
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(MdmResource::getLevel);
        queryWrapper.ne(MdmResource::getId,defaultRootId);
        List<MdmResource> list = mdmResourceMapper.selectList(queryWrapper);
        List<MdmResourceVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, MdmResourceVo.class);
        }
        return listVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmResourceVo get(Long id) {
        MdmResource mdmResource = super.getById(id);
        MdmResourceVo mdmResourceVo = null;
        if(mdmResource !=null){
            mdmResourceVo = dozerMapper.map(mdmResource,MdmResourceVo.class);
        }
        log.debug("查询成功");
        return mdmResourceVo;
    }

    /**
     * 通过id查询自己的子节点
     * @param id
     * @return
     */
    @Override
    public List<MdmResourceVo> getKids(Long id){
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmResource::getParentId,id);
        List<MdmResource> list = this.list(queryWrapper);
        List<MdmResourceVo> mdmResourceVos = null;
        if (CollectionUtil.isNotEmpty(list)){
             mdmResourceVos = DozerUtils.mapList(dozerMapper, list, MdmResourceVo.class);
        }
        return mdmResourceVos;
    }

    /**
    * 保存实体
    * @param mdmResourceDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmResourceDto mdmResourceDto) {
        //校验参数（是否在同级下名称重复）
        checkRepeat(mdmResourceDto,false);
        //设置层级
        setLevel(mdmResourceDto);
        //新增时肯定是没有子集的，设置为0
        mdmResourceDto.setHasKids(YesNo.NO.code);

        MdmResource mdmResource = dozerMapper.map(mdmResourceDto,MdmResource.class);
        boolean result = super.save(mdmResource);
        if(result){
            //保存neo4j资源节点
            //neoService.saveResNode(mdmResource);
            log.debug("保存成功");
        }
        return result;
    }

    @SneakyThrows
    @Transactional
    public void setLevel(MdmResourceDto mdmResourceDto) {
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmResource::getId,mdmResourceDto.getParentId());
        MdmResource mdmResource = this.getOne(queryWrapper);
        Integer level = mdmResource.getLevel();
        level++;
        mdmResourceDto.setLevel(level);
        //那么他的上层级就有了子集
        mdmResource.setHasKids(YesNo.YES.code);
        super.updateById(mdmResource);
    }

    private void checkRepeat(MdmResourceDto mdmResourceDto,Boolean isUpdate) {
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmResource::getParentId,mdmResourceDto.getParentId());
        queryWrapper.eq(MdmResource::getResourceName,mdmResourceDto.getResourceName());
        //除了它自己
        if (isUpdate){
            queryWrapper.ne(MdmResource::getId,mdmResourceDto.getId());
        }
        Integer count = mdmResourceMapper.selectCount(queryWrapper);
        if (count.intValue() > 0){
            throw new IncloudException("在同一父级下资源名称已存在：" + mdmResourceDto.getResourceName());
        }
    }

    /**
    * 修改实体
    * @param mdmResourceDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmResourceDto mdmResourceDto) {
        //校验修改后是否重名
        checkRepeat(mdmResourceDto,true);
        mdmResourceDto.setUpdateTime(LocalDateTime.now());
        MdmResource mdmResource = dozerMapper.map(mdmResourceDto,MdmResource.class);
        boolean result = super.updateById(mdmResource);
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
        MdmResourceVo mdmResourceVo = this.get(id);
        if (mdmResourceVo.getHasKids().intValue() == YesNo.YES.code.intValue()){
            throw new IncloudException("该资源下存在子集不能删除");
        }
//        List<Long> ids = new ArrayList<>();
//        //递归查询所有的子节点
//        getSubList(ids,id);
//        //把自己加进去
//        ids.add(id);
        //获取到自己信息
        try {
            MdmResource mdmResource = this.getById(id);
            if(ObjectUtil.isNotEmpty(mdmResource)){
                //查下父节点除了自己，还有没有别的子节点,如果没有了，那么改下他的hasKids状态
                selectParentHasKidsAndReset(mdmResource.getParentId(),id);
            }
        }catch (Exception e){
            log.error("根据当前ID找不到相应记录，无法删除当前节点！");
        }
        boolean result = super.removeById(id);
        //关系表
        mdmRoleResourceService.deleteByResourceId(id);
        if(result){
            log.debug("删除成功");
        }
        //删除neo4j 关系
        List<String> neoIds = new ArrayList<>();
        neoIds.add(String.valueOf(id));
        //neoService.delNodeByMid(NeoNodeEnum.RES.code,neoIds);
        return result;
    }

    @Override
    public List<MdmResourceVo> getResByIds(List<Long> ids,Long parentId) {
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmResource::getId,ids);
        queryWrapper.eq(null != parentId && parentId > -1L,MdmResource::getParentId,parentId);
        List<MdmResource> list = mdmResourceMapper.selectList(queryWrapper);
        List<MdmResourceVo> listVo = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)){
            listVo = DozerUtils.mapList(dozerMapper, list, MdmResourceVo.class);
        }
        return listVo;
    }

    @SneakyThrows
    @Transactional
    public void selectParentHasKidsAndReset(Long parentId, Long id) {
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmResource::getParentId,parentId);
        queryWrapper.ne(MdmResource::getId,id);
        List<MdmResource> mdmResources = this.list(queryWrapper);
        if (CollectionUtil.isEmpty(mdmResources) || mdmResources.isEmpty()){
            //说明他的父节点除了自己没有其他的子集，修改父节点的hasKids为0
            MdmResource parentMdmResource = getParentMdmResource(parentId);
            parentMdmResource.setHasKids(YesNo.NO.code);
            this.updateById(parentMdmResource);
        }
    }

    private MdmResource getParentMdmResource(Long parentId) {
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmResource::getId,parentId);
        MdmResource mdmResource = this.getOne(queryWrapper);
        if (mdmResource == null){
            throw new IncloudException("该父级不存在");
        }
        return mdmResource;
    }

    /**
     * 递归找到自己的所有子节点
     * @param ids
     * @param id
     */
    void getSubList(List<Long> ids, Long id){
        LambdaQueryWrapper<MdmResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmResource::getParentId,id);
        List<MdmResource> list = list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            list.forEach(mdmResource -> {
                getSubList(ids,mdmResource.getId());
                ids.add(mdmResource.getId());
            });
        }
        log.debug("getSubList 查询成功");
    }

    @Override
    public List<MdmResourceVo> getResByPid(Long pid){
        List resIds = this.getResByUserId();
        //根据资源id查询 查询所有的资源信息
        List<MdmResourceVo> resVos = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(resIds)) {
            resVos = this.getResByIds(resIds,pid);
        }
        //组装树形
        //try {
        //   reusltList =  ResTreeUtil.getTree(0,resVos,"id");
        //}catch (Exception e) {
        //   e.printStackTrace();
        //}
        CollectionUtil.sort(resVos, new Comparator<MdmResourceVo>() {
            @Override
            public int compare(MdmResourceVo t1, MdmResourceVo t2) {
                int i = t1.getSort()-t2.getSort();
                return i;
            }
        });
        return resVos;
    }

    @Override
    public MdmResourceVo checkResByResId(Long resId) {
        List resIds = this.getResByUserId();
        if(CollectionUtil.isNotEmpty(resIds)) {
            if(resIds.contains(resId)) {
                List<MdmResourceVo> resVos = this.getResByIds(Arrays.asList(resId),null);
                if(CollectionUtil.isNotEmpty(resVos)) {
                    MdmResourceVo mdmResourceVo =  resVos.get(0);
                    return mdmResourceVo;
                } else {
                    throw new IncloudException("该资源已经不存在。");
                }
            } else {
                throw new IncloudException("当前按钮权限已失效。");
            }
        } else {
            throw new IncloudException("当前按钮权限已失效。");
        }
    }

    /**
     * 根据当前登录人获取所有去重后的资源id
     * @return
     */
    public List<Long> getResByUserId() {
        List<Long> resIds = new ArrayList<>();
        //获取当前登录人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String strAdmin = loginAppUser.getUsername();
        log.debug("获取资源当前登录人为：" + strAdmin);
        if(strAdmin.equals(AdminEnum.ADMIN.value)) {
            List<MdmResourceVo> _mdmResourceVo =  this.lists(new MdmResourceDto());
            if(CollectionUtil.isNotEmpty(_mdmResourceVo)) {
                log.debug("查询的资源数据为：" + _mdmResourceVo.size());
                List<Long> roleResourceIds = _mdmResourceVo.stream().map(MdmResourceVo::getId).collect(Collectors.toList());
                //资源去重
                resIds =(List) roleResourceIds.stream().distinct().collect(Collectors.toList());
                return resIds;
            }
        }
        //获取当前登录人所有岗位信息
        List<MdmPostUserVo> posts = mdmPostUserService.getPostByUserId(String.valueOf(loginAppUser.getId()));
        List<Long> postIds = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(posts)) {
            postIds = posts.stream().map(MdmPostUserVo::getPostId).collect(Collectors.toList());
        }
        List<Long> roleIds = new ArrayList<>();
        //获取所有人对应的角色
        List<MdmRoleUserVo> roleUserVos = mdmRoleUserService.getRoleUserByUserId(loginAppUser.getId());
        if(CollectionUtil.isNotEmpty(roleUserVos)) {
            List<Long> roleUserIds = roleUserVos.stream().map(MdmRoleUserVo::getRoleId).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(roleUserIds)) {
                roleIds.addAll(roleUserIds);
            }
        }
        //获取所有岗位对应的角色
        List<MdmRolePostVo> rolePostVos = mdmRolePostService.getRolePostByPostIds(postIds);
        if(CollectionUtil.isNotEmpty(rolePostVos)) {
            List<Long> rolePostIds = rolePostVos.stream().map(MdmRolePostVo::getRoleId).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(rolePostIds)) {
                roleIds.addAll(rolePostIds);
            }
        }
        //获取所有角色对应的资源
        if(CollectionUtil.isNotEmpty(roleIds)) {
            //角色去重
            List ids =(List) roleIds.stream().distinct().collect(Collectors.toList());
            List<MdmRoleResourceVo> roleResourceVo = mdmRoleResourceService.getByRoleIds(ids);
            if(CollectionUtil.isNotEmpty(roleResourceVo)) {
                List<Long> roleResourceIds = roleResourceVo.stream().map(MdmRoleResourceVo::getResourceId).collect(Collectors.toList());
                //资源去重
                resIds =(List) roleResourceIds.stream().distinct().collect(Collectors.toList());
                return resIds;
            }
        }
        return resIds;
    }
}
