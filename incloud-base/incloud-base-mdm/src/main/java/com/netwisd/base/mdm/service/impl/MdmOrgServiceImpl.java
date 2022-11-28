package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.OrgClassEnum;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.dto.MdmOrgStatusDto;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import com.netwisd.base.common.mdm.vo.MdmPostVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.mdm.constants.*;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.netwisd.base.mdm.event.MdmPublisher;
import com.netwisd.base.mdm.mapper.MdmOrgMapper;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.exception.IncloudException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
import java.util.stream.Stream;

/**
 * @Description 组织 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2021-08-14 10:08:12
 */
@Service
@Slf4j
public class MdmOrgServiceImpl extends ServiceImpl<MdmOrgMapper, MdmOrg> implements MdmOrgService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmOrgMapper mdmOrgMapper;

    @Autowired
    private MdmPublisher mdmPublisher;

    @Autowired
    private MdmMqService mdmMqService;

    @Autowired
    private MdmPostService mdmPostService;

    @Autowired
    @Lazy
    private MdmUserService mdmUserService;

    @Value("${spring.rocketmq.orgTopics}")
    private String orgTopics;

    @Value("${spring.rocketmq.userTopics}")
    private String userTopics;

    @Value("${spring.rocketmq.postTopics}")
    private String postTopics;

    /**
     * 默认排序号为1
     */
    private static Integer defaultSort = 1;

    /**
     * 全路径拼接串ID
     */
    private static String defaultJoinId = ",";

    /**
     * 全路径拼接串名称
     */
    private static String defaultJoinName = ",";

    /**
     * 默认root信息
     */
    private static Long defaultRootId = 0l;

    /**
     * 默认root信息
     */
    private static String defaultRootName = "root";

    @Override
    public List<MdmOrgAllVo> lists(MdmOrgDto mdmOrgDto) {
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(mdmOrgDto.getStatus()),MdmOrg::getStatus,mdmOrgDto.getStatus());
        queryWrapper.eq(ObjectUtil.isNotEmpty(mdmOrgDto.getOrgType()),MdmOrg::getOrgType,mdmOrgDto.getOrgType());
        queryWrapper.eq(null != mdmOrgDto.getLvType() && 0 != mdmOrgDto.getLvType(),MdmOrg::getLvType,mdmOrgDto.getLvType());
        queryWrapper.between(ObjectUtil.isNotNull(mdmOrgDto.getSUpdateTime())&&ObjectUtil.isNotNull(mdmOrgDto.getEUpdateTime()), MdmOrg::getUpdateTime, mdmOrgDto.getSUpdateTime(),mdmOrgDto.getEUpdateTime());
        queryWrapper.eq(MdmOrg::getOrgClass,ObjectUtil.isEmpty(mdmOrgDto.getOrgClass())||0 == mdmOrgDto.getOrgClass() ? OrgClassEnum.BIZ.code : mdmOrgDto.getOrgType());
        queryWrapper.orderByAsc(MdmOrg::getSort);
        List<MdmOrg> list = list(queryWrapper);
        List<MdmOrgAllVo> mdmOrgAllVos = DozerUtils.mapList(dozerMapper, list, MdmOrgAllVo.class);
        return mdmOrgAllVos;
    }

    public List<MdmOrg> entityList(MdmOrgDto mdmOrgDto) {
        Integer isDefault = ObjectUtil.isEmpty(mdmOrgDto.getIsDefault()) ? YesNoEnum.NO.getCode().intValue() : mdmOrgDto.getIsDefault();
        mdmOrgDto.setIsDefault(isDefault);
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        if(isDefault == YesNoEnum.NO.getCode().intValue()){
            queryWrapper.like(StrUtil.isNotEmpty(mdmOrgDto.getOrgCode()),MdmOrg::getOrgCode,mdmOrgDto.getOrgCode())
                    .like(StrUtil.isNotEmpty(mdmOrgDto.getOrgName()),MdmOrg::getOrgName,mdmOrgDto.getOrgName())
                    .like(ObjectUtil.isNotEmpty(mdmOrgDto.getParentId()),MdmOrg::getOrgFullId,mdmOrgDto.getParentId())
                    .eq(ObjectUtil.isNotEmpty(mdmOrgDto.getStatus()),MdmOrg::getStatus,mdmOrgDto.getStatus())
                    .eq(ObjectUtil.isNotEmpty(mdmOrgDto.getOrgType()),MdmOrg::getOrgType,mdmOrgDto.getOrgType())
                    .eq(null != mdmOrgDto.getLvType() && 0 != mdmOrgDto.getLvType(),MdmOrg::getLvType,mdmOrgDto.getLvType())
                    .between(ObjectUtil.isNotNull(mdmOrgDto.getSUpdateTime())&&ObjectUtil.isNotNull(mdmOrgDto.getEUpdateTime()), MdmOrg::getUpdateTime, mdmOrgDto.getSUpdateTime(),mdmOrgDto.getEUpdateTime())
                    .eq(MdmOrg::getOrgClass,ObjectUtil.isEmpty(mdmOrgDto.getOrgClass())||0 == mdmOrgDto.getOrgClass() ? OrgClassEnum.BIZ.code : mdmOrgDto.getOrgClass())
                    .ne(MdmOrg::getId,0l)//处理root 不查询 root节点
                    .orderByDesc(MdmOrg::getLevel);
        }else {
            queryWrapper.eq(ObjectUtil.isNotEmpty(mdmOrgDto.getStatus()),MdmOrg::getStatus,mdmOrgDto.getStatus());
            queryWrapper.eq(ObjectUtil.isNotEmpty(mdmOrgDto.getOrgType()),MdmOrg::getOrgType,mdmOrgDto.getOrgType());
            queryWrapper.eq(null != mdmOrgDto.getLvType() && 0 != mdmOrgDto.getLvType(),MdmOrg::getLvType,mdmOrgDto.getLvType());
            queryWrapper.eq(MdmOrg::getOrgClass,ObjectUtil.isEmpty(mdmOrgDto.getOrgClass())||0 == mdmOrgDto.getOrgClass()? OrgClassEnum.BIZ.code : mdmOrgDto.getOrgClass());
            queryWrapper.between(ObjectUtil.isNotNull(mdmOrgDto.getSUpdateTime())&&ObjectUtil.isNotNull(mdmOrgDto.getEUpdateTime()), MdmOrg::getUpdateTime, mdmOrgDto.getSUpdateTime(),mdmOrgDto.getEUpdateTime());
            queryWrapper.eq(MdmOrg::getParentId,defaultRootId);
        }

        queryWrapper.orderByAsc(MdmOrg::getSort);
        List<MdmOrg> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<MdmOrgAllVo> vos(MdmOrgDto mdmOrgDto) {
        List<MdmOrg> orgList = this.entityList(mdmOrgDto);
        List<MdmOrgAllVo> mdmOrgAllVos = DozerUtils.mapList(dozerMapper, orgList, MdmOrgAllVo.class);
        return mdmOrgAllVos;
    }

    /**
    * 单表简单查询操作
    * @param mdmOrgDto
    * @return
    */
    @Override
    public List<MdmOrgAllVo> list(MdmOrgDto mdmOrgDto) {
        List<MdmOrg> list = this.entityList(mdmOrgDto);
        //如果不是条件查询的话，直接返回
        if(mdmOrgDto.getIsDefault() == YesNoEnum.YES.getCode().intValue()){
            return DozerUtils.mapList(dozerMapper, list, MdmOrgAllVo.class);
        }
        return handleMdmOrgAllVoList(list);
    }

    /**
     * 组装MdmOrgAllVoList数据
     * @param list
     * @return
     */
    List<MdmOrgAllVo> handleMdmOrgAllVoList(List<MdmOrg> list){
        //如果是条件查询，组装一下数据
        List<MdmOrgAllVo> result = new ArrayList<>();
        List<MdmOrgAllVo> mdmOrgAllVos = DozerUtils.mapList(dozerMapper, list, MdmOrgAllVo.class);
        //构建一个map结构，为了比较方便
        Map<Long,MdmOrgAllVo> map = mdmOrgAllVos.stream().collect(Collectors.toMap(MdmOrgAllVo::getId, Function.identity(),(key1,key2) -> key2));

        //遍历结果集，处理数据
        for (MdmOrgAllVo vo : mdmOrgAllVos){
            Long parentId = vo.getParentId();
            MdmOrgAllVo parentObj = map.get(parentId);
            //如果从map中找到其父级，那么就把父级的list里加上自己
            if(ObjectUtil.isNotEmpty(parentObj)){
                //加上自己，因为是引用，所以getKids再add后，引用数据就加入进去了；
                parentObj.getKids().add(vo);
                //从map里把自己干掉，下次不再处理自己——因为列表排序是按level倒序排的，前面的处理的都是最末级层的
                map.remove(vo.getId());
            }
        }
        //最后整合完的map里，就是我们要的结果
        for (Map.Entry<Long,MdmOrgAllVo> entry:map.entrySet()){
            result.add(entry.getValue());
        }
        return result;
    }

    /**
     * 组织树
     * @return
     */
    @Override
    public List<MdmOrgAllVo> treeList(Integer orgType) {
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmOrg::getStatus, StatusEnum.RUNNING.code.intValue());
        if(orgType.intValue() == OrgTypeEnum.ORG.code.intValue()){
            queryWrapper.eq(MdmOrg::getOrgType,orgType);
        }
        queryWrapper.orderByDesc(MdmOrg::getLevel).orderByAsc(MdmOrg::getSort);
        List<MdmOrg> list = this.list(queryWrapper);
        List<MdmOrgAllVo> mdmOrgAllVos = handleMdmOrgAllVoList(list);
        //List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, list, MdmOrgVo.class);
        return mdmOrgAllVos;
    }

    /**
    * 自定义查询操作
    * @param mdmOrgDto
    * @return
    */
    @Override
    public Page listAll(MdmOrgDto mdmOrgDto) {
        Page<MdmOrgAllVo> pageVo = mdmOrgMapper.listAll(mdmOrgDto.getPage(),mdmOrgDto.getId(),
                                                mdmOrgDto.getOrgCode(),mdmOrgDto.getOrgName(),mdmOrgDto.getLevel());
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }


    /**
     * 根据父ID查询其一级子机构
     * @param id
     * @return
     */
    @Override
    public List<MdmOrgVo> kids(Long id) {
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmOrg::getParentId,id)
                    .eq(MdmOrg::getStatus,StatusEnum.RUNNING.code).orderByAsc(MdmOrg::getSort);
        List<MdmOrg> list = this.list(queryWrapper);
        List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, list, MdmOrgVo.class);
        return mdmOrgVos;
    }


    /**
     * 排序
     * @param sourceId
     * @param targetId
     * @param index
     * @return
     */
    @Override
    @Transactional
    @SneakyThrows
    public Boolean sort(Long sourceId,Long targetId, String index) {
        MdmOrg source = this.getById(sourceId);
        if(ObjectUtil.isEmpty(source)){
            throw new IncloudException("根据传入的sourceId查询不到相应的实体！");
        }
        List<MdmOrg> list = new ArrayList<>();
        if(StrUtil.isNotEmpty(index)){
            if(index.equals(SortEnum.FIRST.value)){
                List<MdmOrg> otherSortList = getListByParent(source.getParentId(), true, source.getId());
                source.setSort(defaultSort);
                list = sortOtherList(otherSortList,defaultSort);
            }else if(index.equals(SortEnum.LAST.value)){
                MdmOrg lastOne = getOneByParent(source.getParentId(), false, null);
                source.setSort(lastOne.getSort() + 1);
            }
        }else {
            MdmOrg target = this.getById(targetId);
            if(ObjectUtil.isEmpty(target)){
                throw new IncloudException("根据传入的targetId查询不到相应的实体！");
            }
            Integer targetIndex = target.getSort();
            List<MdmOrg> otherSortList = getListByParent(source.getParentId(), true, source.getId());
            List<MdmOrg> collect = otherSortList.stream().filter(mdmOrg -> mdmOrg.getSort() >= targetIndex).collect(Collectors.toList());
            list = sortOtherList(collect,targetIndex);
            source.setSort(targetIndex);
        }
        //把自己也加进去；
        MdmOrg mdmOrg = dozerMapper.map(source,MdmOrg.class);
        list.add(mdmOrg);
        boolean result = updateBatchById(list);
        if(result){
            log.info("批量排序成功！");
            if(CollectionUtil.isNotEmpty(list)) {
                //转换成Vo发送增量mq
                List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, list, MdmOrgVo.class);
                log.debug("修改机构排序-发送增量MQ");
                for (MdmOrgVo mdmOrgVo : mdmOrgVos) {
                    String topics[] = orgTopics.split(",");
                    //判断geps
                    if(null != mdmOrgVo.getLvType() && 0!=mdmOrgVo.getLvType()) {
                        mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                    //判断oa
                    if(null != mdmOrgVo.getOaLvType() && 0!=mdmOrgVo.getOaLvType()) {
                        mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                }
            }
        }
        return null;
    }

    /**
     * 按给定集合和排序index做排序
     * @param otherSortList
     * @param index
     * @return
     */
    List<MdmOrg> sortOtherList(List<MdmOrg> otherSortList,Integer index){
        List<MdmOrg> list = new ArrayList<>();
        for (MdmOrg mdmOrg : otherSortList){
            mdmOrg.setSort(index + 1);
            list.add(mdmOrg);
            index++;
        }
        return list;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmOrgVo get(Long id) {
        //todo 待处理把当前用户和部门信息存入
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        MdmOrg mdmOrg = super.getById(id);
        MdmOrgVo mdmOrgVo = null;
        if(mdmOrg !=null){
            mdmOrgVo = dozerMapper.map(mdmOrg,MdmOrgVo.class);
        }
        log.debug("get 查询成功");
        return mdmOrgVo;
    }

    @Override
    public MdmOrg getByCode(String orgCode) {
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmOrg::getOrgCode,orgCode);
        List<MdmOrg> list = list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 通过主键找父节点
     * @param parentId
     * @return
     */
    @SneakyThrows
    @Override
    public MdmOrg getParentByParentId(Long parentId) {
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmOrg::getId,parentId);
        MdmOrg mdmOrg = null;
        try {
            mdmOrg = this.getOne(queryWrapper);
        }catch (Exception e){
            throw new IncloudException("组织getByParentId查询错误");
        }
        log.debug("getParentByParentId 查询成功");
        return mdmOrg;
    }

    /**
     * 根据父节点ID，取出其下面排序sort第一条记录
     * @param parentId
     * @param isAsc
     * @param exclusiveId,不包含的记录
     * @return
     */
    @SneakyThrows
    public MdmOrg getOneByParent(Long parentId,Boolean isAsc,Long exclusiveId) {
        List<MdmOrg> list = getListByParent(parentId, isAsc, exclusiveId);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            Optional<MdmOrg> first = list.stream().findFirst();
            MdmOrg mdmOrg = first != null ? first.get() : null;
            return mdmOrg;
        }
        return null;
    }

    /**
     * 根据父节点ID，取出其下面排序sort第一条记录
     * @param parentId
     * @param isAsc
     * @param exclusiveId,不包含的记录
     * @return
     */
    @SneakyThrows
    public List<MdmOrg> getListByParent(Long parentId,Boolean isAsc,Long exclusiveId) {
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmOrg::getParentId,parentId);
        if(ObjectUtil.isNotEmpty(exclusiveId)){
            queryWrapper.ne(MdmOrg::getId,exclusiveId);
        }
        if(isAsc){
            queryWrapper.orderByAsc(MdmOrg::getSort);
        }else {
            queryWrapper.orderByDesc(MdmOrg::getSort);
        }
        List<MdmOrg> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 设置当前节点的level
     * @param mdmOrg
     */
    void setCurrentLevelByParent(MdmOrg mdmOrg){
        MdmOrg parentMdmOrg = getParentByParentId(mdmOrg.getParentId());
        //设置当前节点的level
        if(parentMdmOrg != null && parentMdmOrg.getLevel() != null){
            mdmOrg.setLevel(parentMdmOrg.getLevel() + 1);
        }else {
            new IncloudException("父级Level为空！");
        }
    }

    void setParentHasSon(Long parentId,Boolean yesOrNo){
        LambdaUpdateWrapper<MdmOrg> updateWrapper = new LambdaUpdateWrapper<>();
        if(yesOrNo){
            updateWrapper.set(MdmOrg::getHasKids, YesNoEnum.YES.code.intValue());
        }else {
            updateWrapper.set(MdmOrg::getHasKids, YesNoEnum.NO.code.intValue());
        }
        updateWrapper.eq(MdmOrg::getId,parentId);
        boolean update = update(updateWrapper);
        if(update){
            log.info("更新父级有子集标识成功,更新标识为：{}",yesOrNo);
        }
    }

    /**
     * 设置当前节点的level和前节点ID
     * @param mdmOrg
     * @return
     */
    void setCurrentSort(MdmOrg mdmOrg){
        //取出其父节点中同级最后一条记录
        MdmOrg lastOne = getOneByParent(mdmOrg.getParentId(), false,null);
        mdmOrg.setSort(defaultSort);
        if(ObjectUtil.isNotEmpty(lastOne)){
            mdmOrg.setSort(null != mdmOrg.getSort() && 0 != mdmOrg.getSort()? mdmOrg.getSort():lastOne.getSort()+1);
        }
    }

    /**
     * 检查多组织新建时排重
     * @param mdmOrgDtos
     */
    void checkListRepeat(List<MdmOrgDto> mdmOrgDtos){
        Long parentId = null;
        Map<String,Long> map = new HashMap<>();
        Map<String,Long> codeMap = new HashMap<>();
        for(MdmOrgDto mdmOrgDto : mdmOrgDtos){
            String orgName = mdmOrgDto.getOrgName();
            if(ObjectUtil.isNotEmpty(map.get(orgName))){
                //throw new IncloudException("同一父级下的组织名称不能重复，重复组织名称为："+orgName);
            }
            map.put(orgName,mdmOrgDto.getId());
            parentId = Long.valueOf(mdmOrgDto.getParentId().longValue());

//            //验证code 不能重复组织
//            String orgCode = mdmOrgDto.getOrgCode();
//            if(ObjectUtil.isNotEmpty(codeMap.get(orgCode))){
//                throw new IncloudException("不能重复存在组织Code，重复组织Code名称为："+orgCode);
//            }
//            codeMap.put(orgCode,mdmOrgDto.getId());
        }

        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmOrg::getOrgName,map.keySet())
                .eq(MdmOrg::getParentId,parentId);
        List<MdmOrg> list = this.list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list)){
            String repeatName = "";
            for(MdmOrg mdmOrg : list){
                repeatName += mdmOrg.getOrgName() + defaultJoinId;
            }
            //throw new IncloudException("同一父级下的组织名称不能重复，重复组织名称为："+repeatName);
        }

//        //验证重复code
//        LambdaQueryWrapper<MdmOrg> codeQueryWrapper = new LambdaQueryWrapper<>();
//        codeQueryWrapper.in(MdmOrg::getOrgCode,codeMap.keySet());
//        codeQueryWrapper.eq(MdmOrg::getStatus,StatusEnum.RUNNING.code);
//        List<MdmOrg> codeList = this.list(codeQueryWrapper);
//        if(ObjectUtil.isNotEmpty(codeList)){
//            String repeatCode = "";
//            for(MdmOrg mdmOrg : codeList){
//                repeatCode += mdmOrg.getOrgCode() + defaultJoinId;
//            }
//            throw new IncloudException("同一父级下的组织名称不能重复，重复组织Code名称为："+repeatCode);
//        }

    }

    /**
    * 保存实体
    * @param mdmOrgDtos
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean save(List<MdmOrgDto> mdmOrgDtos,boolean isSendMq,boolean isSync) {
        //同级重复检验
        checkListRepeat(mdmOrgDtos);
        MdmOrgDto first = mdmOrgDtos.stream().findFirst().get();
        MdmOrg firstOne = handleFirst(first);
        //如果是同步数据不验证 lvType
        if(!isSync) {
            MdmOrg checkLvTypeOrg = getParentByParentId(firstOne.getParentId());
            if(null == checkLvTypeOrg.getLvType()) {
                throw new IncloudException(first.getOrgName()+"的父级节点必须设置【GEPS级别类型】。");
            }
            if(null == checkLvTypeOrg.getOaLvType()) {
                throw new IncloudException(first.getOrgName()+"的父级节点必须设置【OA级别类型】。");
            }
        }
        Integer firstSoft = firstOne.getSort();
        //要处理的内容里，把第一个去掉，因为已经处理过了；
        mdmOrgDtos.remove(0);
        List<MdmOrg> list = new ArrayList();
        //加了第一个
        list.add(firstOne);
        //把剩下的所有要插入的内容相关的level full信息、sort等都处理一下；
        for (MdmOrgDto mdmOrgDto : mdmOrgDtos){
            firstSoft ++;
            MdmOrg mdmOrg = dozerMapper.map(mdmOrgDto,MdmOrg.class);
            mdmOrg.setLevel(firstOne.getLevel());
            mdmOrg.setOrgFullId(firstOne.getOrgFullId());
            mdmOrg.setOrgFullName(firstOne.getOrgFullName());
            mdmOrg.setParentOrgFullName(firstOne.getParentOrgFullName());
            mdmOrg.setParentDeptFullName(firstOne.getParentDeptFullName());
            mdmOrg.setParentOrgId(firstOne.getParentOrgId());
            mdmOrg.setParentOrgName(firstOne.getParentOrgName());
            //mdmOrg.setSort(firstSoft);
            mdmOrg.setSort(null != mdmOrgDto.getSort() && 0 != mdmOrgDto.getSort()? mdmOrgDto.getSort():firstSoft);
            mdmOrg.setHasKids(YesNoEnum.NO.code.intValue());
            mdmOrg.setStatus(null == mdmOrgDto.getStatus()?StatusEnum.RUNNING.code:mdmOrgDto.getStatus());
            mdmOrg.setOrgClass(ObjectUtil.isEmpty(mdmOrgDto.getOrgClass())||0 == mdmOrgDto.getOrgClass()?OrgClassEnum.BIZ.code:mdmOrgDto.getOrgClass());
            mdmOrg.setQyWeChatDeptId(Integer.valueOf(IdGenerator.getTimestamp()));
            list.add(mdmOrg);
        }
        boolean result = this.saveBatch(list);
        if(result){
            log.debug("机构保存成功");
            if(isSendMq) {
                //转换成Vo发送增量mq
                log.debug("机构保存成功-发送增量MQ");
                if(CollectionUtil.isNotEmpty(list)) {
                    List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, list, MdmOrgVo.class);
                    for (MdmOrgVo mdmOrgVo : mdmOrgVos) {
                        mdmMqService.sendRocketMq(orgTopics, MqTagEnum.ADD.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                }
            }
        }
        return result;
    }
    /*public Boolean save(List<MdmOrgDto> mdmOrgDtos) {
        //同级重复检验
        checkListRepeat(mdmOrgDtos);
        MdmOrgDto first = mdmOrgDtos.stream().findFirst().get();
        boolean result = false;
        if(first.getOrgType().intValue() == OrgTypeEnum.ORG.code.intValue()){
            //组织不能批量操作
            if(mdmOrgDtos.size() > 1){
                throw new IncloudException("组织维护不能批量操作");
            }
            //把第一个处理一下；
            MdmOrg mdmOrg = handleFirst(first);
            result = super.save(mdmOrg);
        }else {
            //把第一个处理一下；
            MdmOrg firstOne = handleFirst(first);
            Integer firstSoft = firstOne.getSort();
            //要处理的内容里，把第一个去掉，因为已经处理过了；
            mdmOrgDtos.remove(0);
            List<MdmOrg> list = new ArrayList();
            //加了第一个
            list.add(firstOne);
            //把剩下的所有要插入的内容相关的level full信息、sort等都处理一下；
            for (MdmOrgDto mdmOrgDto : mdmOrgDtos){
                firstSoft ++;
                MdmOrg mdmOrg = dozerMapper.map(mdmOrgDto,MdmOrg.class);
                mdmOrg.setLevel(firstOne.getLevel());
                mdmOrg.setOrgFullId(firstOne.getOrgFullId());
                mdmOrg.setOrgFullName(firstOne.getOrgFullName());
                mdmOrg.setParentOrgFullName(firstOne.getParentOrgFullName());
                mdmOrg.setParentDeptFullName(firstOne.getParentDeptFullName());
                mdmOrg.setSort(firstSoft);
                mdmOrg.setHasKids(YesNoEnum.NO.code.intValue());
                mdmOrg.setStatus(StatusEnum.RUNNING.code);
                list.add(mdmOrg);
            }
            //批量处理
            result = this.saveBatch(list);
        }
        if(result){
            log.debug("保存成功");
        }
        return result;
    }*/

    /**
     * 处理第一个组织信息
     * @param first
     * @return
     */
    MdmOrg handleFirst(MdmOrgDto first){
        MdmOrg mdmOrg = dozerMapper.map(first,MdmOrg.class);
        setCurrentLevelByParent(mdmOrg);
        //设置全路径信息
        setFullOrgInfo(null,mdmOrg);
        //设置当前数据的sort值
        setCurrentSort(mdmOrg);
        //设置父节点有子集标识
        setParentHasSon(mdmOrg.getParentId(),true);
        mdmOrg.setHasKids(YesNoEnum.NO.code.intValue());
        mdmOrg.setQyWeChatDeptId(Integer.valueOf(IdGenerator.getTimestamp()));
        mdmOrg.setStatus(null == first.getStatus()?StatusEnum.RUNNING.code:first.getStatus());
        mdmOrg.setOrgClass(ObjectUtil.isEmpty(first.getOrgClass())||0 == first.getOrgClass()?OrgClassEnum.BIZ.code:first.getOrgClass());
        return mdmOrg;
    }

    /**
     * 设置全路径信息
     * @param parentMdmOrg
     * @param mdmOrg
     */
    void setFullOrgInfo(MdmOrg parentMdmOrg,MdmOrg mdmOrg){
        Long parentId = mdmOrg.getParentId();
        //如果父类是0，代表是root虚拟根下面的子节点
        if(parentId.longValue() != defaultRootId.longValue()){
            if(ObjectUtil.isEmpty(parentMdmOrg)){
                parentMdmOrg = getParentByParentId(mdmOrg.getParentId());
                if(ObjectUtil.isEmpty(parentMdmOrg)){
                    throw new IncloudException("通过parentId："+mdmOrg.getParentId()+" 找不到相应的父节点，请检查数据！");
                }
            }
            Integer parentOrgType = parentMdmOrg.getOrgType();
            String parentOrgFullId = parentMdmOrg.getOrgFullId();
            String parentOrgFullName = parentMdmOrg.getOrgFullName();
            String orgFullId = StrUtil.isNotEmpty(parentOrgFullId)  ? parentOrgFullId + defaultJoinId + mdmOrg.getParentId() : String.valueOf(mdmOrg.getParentId());
            String orgFullName = StrUtil.isNotEmpty(parentOrgFullName)  ? parentOrgFullName + defaultJoinName + parentMdmOrg.getOrgName() : parentMdmOrg.getOrgName();
            mdmOrg.setOrgFullId(orgFullId);
            mdmOrg.setOrgFullName(orgFullName);
            mdmOrg.setParentName(parentMdmOrg.getOrgName());
            //如果是父级是组织的话，直接取到到orgFullName就行了
            if(parentOrgType.intValue() == OrgTypeEnum.ORG.code.intValue()){
                mdmOrg.setParentOrgFullName(orgFullName);
                mdmOrg.setParentDeptFullName(null);
                mdmOrg.setParentOrgId(parentMdmOrg.getId());
                mdmOrg.setParentOrgName(parentMdmOrg.getOrgName());
            }else {
                //如果是部门的话，就需要处理一下
                String parentOrgFull = parentMdmOrg.getParentOrgFullName();
                String parentDeptFull = parentMdmOrg.getParentDeptFullName();
                String parentDeptFullName = StrUtil.isNotEmpty(parentDeptFull)  ? parentDeptFull + defaultJoinName + parentMdmOrg.getOrgName() : parentMdmOrg.getOrgName();
                mdmOrg.setParentOrgFullName(parentOrgFull);
                mdmOrg.setParentDeptFullName(parentDeptFullName);
                mdmOrg.setParentOrgId(parentMdmOrg.getParentOrgId());
                mdmOrg.setParentOrgName(parentMdmOrg.getParentOrgName());
            }
        }else {
            //父级是root
            mdmOrg.setParentOrgId(defaultRootId);
            mdmOrg.setParentOrgName(defaultRootName);
        }
    }

    /**
     * 设置子节点的相关信息
     * @param orgs
     * @param mdmOrg
     * @param subList
     * @param levelOrFullInfo
     * @return
     */
    List<MdmOrg> setSubListInfoByParent(List<MdmOrg> orgs,MdmOrg mdmOrg,List<MdmOrg> subList,Integer levelOrFullInfo){
        //获取到所有子——包括所有子集的子集等
        if(subList == null){
            subList = getEntityList(mdmOrg.getId());
        }
        if(!subList.isEmpty()){
            //构建一个map结构，为了比较方便
            Map<Long,MdmOrg> map = subList.stream().collect(Collectors.toMap(MdmOrg::getId, Function.identity(),(key1,key2) -> key2));
            setSubList(orgs,subList,map,mdmOrg,levelOrFullInfo);
        }
        return subList;
    }

    /**
     * 递规处理子集的全路径或level或状态
     * @param orgs
     * @param subList
     * @param map
     * @param mdmOrg
     */
    void setSubList(List<MdmOrg> orgs,List<MdmOrg> subList,Map<Long,MdmOrg> map ,MdmOrg mdmOrg,Integer levelOrFullInfo){
        if(!subList.isEmpty()){
            for (MdmOrg subOrg : subList){
                Long parentId = subOrg.getParentId();
                if(map.containsKey(parentId)){
                    //通过parentId获取的是子集里面的第一层
                    MdmOrg mdmOrgInner = map.get(parentId);
                    setSubListRepeat(orgs,subList,map,mdmOrg,mdmOrgInner,levelOrFullInfo);
                }
                if(parentId.longValue() == mdmOrg.getId()){
                    //subOrg本身就是第一层的
                    setSubListRepeat(orgs,subList,map,mdmOrg,subOrg,levelOrFullInfo);
                }
            }
        }
    }

    /**
     * 递规处理子集的全路径
     * @param orgs
     * @param subList
     * @param map
     * @param mdmOrg
     * @param mdmOrgInner
     * @param levelOrFullInfo
     */
    void setSubListRepeat(List<MdmOrg> orgs,List<MdmOrg> subList,Map<Long,MdmOrg> map ,MdmOrg mdmOrg,MdmOrg mdmOrgInner,Integer levelOrFullInfo){
        //子集里面的第一层
        if(levelOrFullInfo.intValue() == HandleTypeEnum.FULLINFO.code.intValue()){
            setFullOrgInfo(mdmOrg,mdmOrgInner);
        }else if(levelOrFullInfo.intValue() == HandleTypeEnum.LEVEL.code.intValue()){
            mdmOrgInner.setLevel(mdmOrg.getLevel() + 1);
        }else if(levelOrFullInfo.intValue() == HandleTypeEnum.STATUS.code.intValue()){
            mdmOrgInner.setStatus(mdmOrg.getStatus());
        }
        orgs.add(mdmOrgInner);
        map.remove(mdmOrgInner.getId());
        setSubList(orgs,subList,map,mdmOrgInner,levelOrFullInfo);
    }


    /**
    * 修改实体
    * @param mdmOrgDto
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(MdmOrgDto mdmOrgDto,boolean isSync) {
        //如果是同步数据不验证 lvType
        if(!isSync) {
            MdmOrg checkLvTypeOrg = getParentByParentId(mdmOrgDto.getParentId());
            if(null == checkLvTypeOrg.getLvType()) {
                throw new IncloudException(mdmOrgDto.getOrgName()+"的父级节点必须设置【级别类型】。");
            }
            if(null == checkLvTypeOrg.getOaLvType()) {
                throw new IncloudException(mdmOrgDto.getOrgName()+"的父级节点必须设置【OA级别类型】。");
            }
        }
        mdmOrgDto.setUpdateTime(LocalDateTime.now());
        MdmOrg mdmOrg = dozerMapper.map(mdmOrgDto,MdmOrg.class);
        boolean result = this.updateById(mdmOrg);
        if(result){
            //转换成Vo发送增量mq
            log.debug("机构修改成功-发送增量MQ");
            MdmOrgVo mdmOrgVo = dozerMapper.map(mdmOrg,MdmOrgVo.class);
            this.orgUpdateHandleMq(mdmOrgVo);
        }
        return result;
    }

    @Override
    @SneakyThrows
    public Boolean justUpdateOrg(MdmOrgDto mdmOrgDto, boolean isSync) {
        //如果是同步数据不验证 lvType
        if(!isSync) {
            MdmOrg checkLvTypeOrg = getParentByParentId(mdmOrgDto.getParentId());
            if(null == checkLvTypeOrg.getLvType()) {
                throw new IncloudException(mdmOrgDto.getOrgName()+"的父级节点必须设置【级别类型】。");
            }
            if(null == checkLvTypeOrg.getOaLvType()) {
                throw new IncloudException(mdmOrgDto.getOrgName()+"的父级节点必须设置【OA级别类型】。");
            }
        }
        mdmOrgDto.setUpdateTime(LocalDateTime.now());
        MdmOrg mdmOrg = dozerMapper.map(mdmOrgDto,MdmOrg.class);
        boolean result = this.updateById(mdmOrg);
        if(result){
            //转换成Vo发送增量mq
            log.debug("机构修改成功-发送增量MQ");
            MdmOrgVo mdmOrgVo = dozerMapper.map(mdmOrg,MdmOrgVo.class);
            this._orgUpdateHandleMq(mdmOrgVo);
        }
        return result;
    }

    /**
     * 修改机构
     * @param mdmOrgVo
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    public void _orgUpdateHandleMq(MdmOrgVo mdmOrgVo) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //查询出数据库中的 机构是否有级别编码 有则发送编辑 没有则发送新增
        boolean isUp = false; //如果不存在值 则一定是新增
        boolean oaIsUp = false; //如果不存在值 则一定是新增
        if(null == mdmOrgVo.getLvType() && null == mdmOrgVo.getLvType()) {
            isUp = true; //如果存在值 说明已经推送过了
        }
        if(null == mdmOrgVo.getOaLvType() && null == mdmOrgVo.getOaLvType()) {
            oaIsUp = true; //如果存在值 说明已经推送过了
        }
        String _orgTopics[] = orgTopics.split(",");
        if(null != mdmOrgVo.getLvType() && 0!= mdmOrgVo.getLvType()) { //修改的时候判断是否 设置 级别类型
            mdmMqService.sendRocketMq(_orgTopics[0], isUp == true?MqTagEnum.ADD.code:MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
        }
        if(null != mdmOrgVo.getOaLvType() && 0!= mdmOrgVo.getOaLvType()) { //修改的时候判断是否 设置 级别类型
            mdmMqService.sendRocketMq(_orgTopics[1], oaIsUp == true?MqTagEnum.ADD.code:MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
        }
    }

    /**
     * 修改机构影响到 人员和岗位
     * @param mdmOrgVo
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    public void orgUpdateHandleMq(MdmOrgVo mdmOrgVo) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //查询出数据库中的 机构是否有级别编码 有则发送编辑 没有则发送新增
        boolean isUp = false; //如果不存在值 则一定是新增
        boolean oaIsUp = false; //如果不存在值 则一定是新增
        if(null == mdmOrgVo.getLvType() && null == mdmOrgVo.getLvType()) {
            isUp = true; //如果存在值 说明已经推送过了
        }
        if(null == mdmOrgVo.getOaLvType() && null == mdmOrgVo.getOaLvType()) {
            oaIsUp = true; //如果存在值 说明已经推送过了
        }
        String _orgTopics[] = orgTopics.split(",");
        String _userTopics[] = userTopics.split(",");
        String _postTopics[] = postTopics.split(",");
        if(null != mdmOrgVo.getLvType() && 0!= mdmOrgVo.getLvType()) { //修改的时候判断是否 设置 级别类型
            mdmMqService.sendRocketMq(_orgTopics[0], isUp == true?MqTagEnum.ADD.code:MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
            log.info("update 修改成功！");
            //如果查询该机构下的所有的用户 以及岗位 mq也推送
            List<MdmUserVo> users = mdmUserService.getUserByDeptId(String.valueOf(mdmOrgVo.getId()));
            if(CollectionUtil.isNotEmpty(users)) {
                for (MdmUserVo user : users) {
                    mdmMqService.sendRocketMq(_userTopics[0], isUp == true?MqTagEnum.ADD.code:MqTagEnum.EDIT.code, JSON.toJSONString(user, SerializerFeature.WriteMapNullValue));
                }
            }
            //岗位
            MdmPostDto mdmPostDto = new MdmPostDto();
            mdmPostDto.setParentDeptId(mdmOrgVo.getId());
            List<MdmPostVo> posts = mdmPostService.lists(mdmPostDto);
            if(CollectionUtil.isNotEmpty(posts)) {
                for (MdmPostVo post : posts) {
                    mdmMqService.sendRocketMq(_postTopics[0], isUp == true?MqTagEnum.ADD.code:MqTagEnum.EDIT.code, JSON.toJSONString(post, SerializerFeature.WriteMapNullValue));
                }
            }
        }

        if(null != mdmOrgVo.getOaLvType() && 0!= mdmOrgVo.getOaLvType()) { //修改的时候判断是否 设置 级别类型
            mdmMqService.sendRocketMq(_orgTopics[1], oaIsUp == true?MqTagEnum.ADD.code:MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
            log.info("update 修改成功！");
            //如果查询该机构下的所有的用户 以及岗位 mq也推送
            List<MdmUserVo> users = mdmUserService.getUserByDeptId(String.valueOf(mdmOrgVo.getId()));
            if(CollectionUtil.isNotEmpty(users)) {
                for (MdmUserVo user : users) {
                    mdmMqService.sendRocketMq(_userTopics[1], oaIsUp == true?MqTagEnum.ADD.code:MqTagEnum.EDIT.code, JSON.toJSONString(user, SerializerFeature.WriteMapNullValue));
                }
            }
            //岗位
            MdmPostDto mdmPostDto = new MdmPostDto();
            mdmPostDto.setParentDeptId(mdmOrgVo.getId());
            List<MdmPostVo> posts = mdmPostService.lists(mdmPostDto);
            if(CollectionUtil.isNotEmpty(posts)) {
                for (MdmPostVo post : posts) {
                    mdmMqService.sendRocketMq(_postTopics[1], oaIsUp == true?MqTagEnum.ADD.code:MqTagEnum.EDIT.code, JSON.toJSONString(post, SerializerFeature.WriteMapNullValue));
                }
            }
        }
    }

    /**
     * 修改父节点信息
     * @param mdmOrgDto
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean updateParent(MdmOrgDto mdmOrgDto) {
        //取一下原数据；
        MdmOrgVo mdmOrgVoSource = get(mdmOrgDto.getId());
        if(ObjectUtil.isEmpty(mdmOrgVoSource)){
            throw new IncloudException("被修改的对象不存在");
        }
        mdmOrgDto.setUpdateTime(LocalDateTime.now());
        MdmOrg mdmOrg = dozerMapper.map(mdmOrgDto,MdmOrg.class);
        List<MdmOrg> orgs = new ArrayList<>();
        //修改level
        log.info("先设置一下自己的level");
        setCurrentLevelByParent(mdmOrg);
        //setLevelAndStatusByParent(orgs,mdmOrg,null,mdmOrg.getStatus(),true);
        log.info("再设置一下子级的所有level");
        List<MdmOrg> subList = setSubListInfoByParent(orgs, mdmOrg,null, HandleTypeEnum.LEVEL.code);
        log.info("设置自己在新父节点下的sort");
        setCurrentSort(mdmOrg);
        //设置新父节点的hasSon标识为true
        log.info("设置新父节点的hasSon标识为true");
        setParentHasSon(mdmOrg.getParentId(),true);
        //查下原父节点除了自己，还有没有别的子节点,如果没有了，那么改下他的hasSon状态
        log.info("查下原父节点除了自己，还有没有别的子节点,如果没有了，那么改下他的hasSon状态");
        selectParentHasSonAndReset(mdmOrgVoSource.getParentId(),mdmOrg.getId());
        log.info("设置自己的全路径！");
        changeFullPath(mdmOrg,orgs,subList);

        //把他自己也加进去
        orgs.add(mdmOrg);
        boolean result = this.updateBatchById(orgs);
        if(result){
            log.info("updateParent 修改成功！");
            if(CollectionUtil.isNotEmpty(orgs)) {
                //转换成Vo发送增量mq
                List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, orgs, MdmOrgVo.class);
                log.debug("updateParent 机构修改成功-发送增量MQ");
                for (MdmOrgVo mdmOrgVo : mdmOrgVos) {
                    String topics[] = orgTopics.split(",");
                    //判断geps
                    if(null != mdmOrgVo.getLvType() && 0!=mdmOrgVo.getLvType()) {
                        mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                    //判断oa
                    if(null != mdmOrgVo.getOaLvType() && 0!=mdmOrgVo.getOaLvType()) {
                        mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                }
            }
        }
        // 这里要事件通知更新关联机构信息的做变动
        mdmPublisher.publish(EventConstants.UpdateParentOrgEvent,orgs);
        return result;
    }

    /**
     * 检查同级父级下是否有重复名称
     * @param mdmOrgDto
     */
    void checkRepeat(MdmOrgDto mdmOrgDto){
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmOrg::getOrgName,mdmOrgDto.getOrgName())
                    .eq(MdmOrg::getParentId,mdmOrgDto.getParentId())
                    .ne(MdmOrg::getId,mdmOrgDto.getId());
        List<MdmOrg> list = this.list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list)){
            //throw new IncloudException("同一父级下的组织名称不能重复，重复名称为："+mdmOrgDto.getOrgName());
        }
        log.info("去重检验完成！");
    }

    /**
     * 修改组织名称
     * @param mdmOrgDto
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean updateOrgName(MdmOrgDto mdmOrgDto) {
        //去重检验
        checkRepeat(mdmOrgDto);
        mdmOrgDto.setUpdateTime(LocalDateTime.now());
        MdmOrg mdmOrg = dozerMapper.map(mdmOrgDto,MdmOrg.class);
        List<MdmOrg> orgs = new ArrayList<>();
        log.info("updateOrgName 改变子机构下的名称全路径！");
        changeFullPath(mdmOrg,orgs,null);
        //把他自己也加进去
        orgs.add(mdmOrg);
        boolean result = this.updateBatchById(orgs);
        if(result){
            log.info("updateOrgName 修改成功！");
            if(CollectionUtil.isNotEmpty(orgs)) {
                //转换成Vo发送增量mq
                List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, orgs, MdmOrgVo.class);
                log.debug("updateOrgName 机构修改成功-发送增量MQ");
                for (MdmOrgVo mdmOrgVo : mdmOrgVos) {
                    String topics[] = orgTopics.split(",");
                    //判断geps
                    if(null != mdmOrgVo.getLvType() && 0!=mdmOrgVo.getLvType()) {
                        mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                    //判断oa
                    if(null != mdmOrgVo.getOaLvType() && 0!=mdmOrgVo.getOaLvType()) {
                        mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                }
            }
        }
        // 这里要事件通知更新关联机构信息的做变动
        mdmPublisher.publish(EventConstants.UpdateNameOrgEvent,orgs);
        return result;
    }

    /**
     * 更新状态
     * @param mdmOrgStatusDto
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean updateStatus(MdmOrgStatusDto mdmOrgStatusDto) {
        MdmOrgDto mdmOrgDto = mdmOrgStatusDto.getMdmOrgDto();
        mdmOrgDto.setUpdateTime(LocalDateTime.now());
        MdmOrg mdmOrg = dozerMapper.map(mdmOrgDto,MdmOrg.class);
        List<MdmOrg> orgs = new ArrayList<>();
        //处理下状态
        setSubListInfoByParent(orgs,mdmOrg,null, HandleTypeEnum.STATUS.code);
        //把他自己也加进去
        orgs.add(mdmOrg);
        boolean result = this.updateBatchById(orgs);

        // 这里要通知删掉部门、岗职位、人员等
        if(mdmOrgDto.getStatus().intValue() == StatusEnum.BROKEN.code.intValue()){
            log.info("组织的拆分事件推送！");
            mdmPublisher.publish(EventConstants.BrokenOrgEvent,mdmOrgStatusDto);
        }else if(mdmOrgDto.getStatus().intValue() == StatusEnum.CANCELED.code.intValue()){
            log.info("组织的撤消事件推送！");
            mdmPublisher.publish(EventConstants.CanceledOrgEvent,mdmOrgStatusDto);
        }else {
            log.error("状态错误 -> {}",mdmOrgDto.getStatus());
        }
        if(result){
            //转换成Vo发送增量mq
            log.info("updateStatus 修改成功！");
            if(CollectionUtil.isNotEmpty(orgs)) {
                //转换成Vo发送增量mq
                List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, orgs, MdmOrgVo.class);
                log.debug("updateStatus 机构修改成功-发送增量MQ");
                for (MdmOrgVo mdmOrgVo : mdmOrgVos) {
                    String topics[] = orgTopics.split(",");
                    //判断geps
                    if(null != mdmOrgVo.getLvType() && 0!=mdmOrgVo.getLvType()) {
                        mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                    //判断oa
                    if(null != mdmOrgVo.getOaLvType() && 0!=mdmOrgVo.getOaLvType()) {
                        mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                    }
                }
            }
        }
        return result;
    }


    /**
     * 修改当前全路径和相应子的全路径
     * @param mdmOrg
     * @param orgs
     * @param subList
     */
    void changeFullPath(MdmOrg mdmOrg,List<MdmOrg> orgs,List<MdmOrg> subList){
        //设置全路径信息
        setFullOrgInfo(null,mdmOrg);
        //递规设置自己下的所有子集的全路径
        setSubListInfoByParent(orgs,mdmOrg,subList, HandleTypeEnum.FULLINFO.code);
    }

    /**
     * 找到自己的所有子节点
     * @param id
     */
    List<MdmOrg> getEntityList(Long id){
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(MdmOrg::getOrgFullId,id)
                .orderByAsc(MdmOrg::getSort);
        List<MdmOrg> list = list(queryWrapper);
        log.debug("getEntityList查询成功");
        return list;
    }

    /**
     * 查下父节点除了自己，还有没有别的子节点,如果没有了，那么改下他的hasSon状态
     * @param parentId
     * @param id
     */
    void selectParentHasSonAndReset(Long parentId,Long id){
        //查下父节点除了自己，还有没有别的子节点
        MdmOrg oneByParent = getOneByParent(parentId, true, id);
        //如果没有了，那么改下他的hasSon状态
        if(oneByParent == null){
            setParentHasSon(parentId,false);
        }
    }

    /**
     * 通过ID删除
     * @param id
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean delete(Long id) {
        List<Long> ids = new ArrayList<>();
        /*List<MdmOrg> entities = new ArrayList<>();*/
        getSubList(ids,id);
        ids.add(id);
        //获取到自己信息
        try {
            MdmOrg mdmOrg = this.getById(id);
            if(ObjectUtil.isNotEmpty(mdmOrg)){
                //entities.add(mdmOrg);
                //查下父节点除了自己，还有没有别的子节点,如果没有了，那么改下他的hasSon状态
                selectParentHasSonAndReset(mdmOrg.getParentId(),id);
            }
        }catch (Exception e){
            log.error("根据当前ID找不到相应记录，无法删除当前节点！");
        }

        boolean result = super.removeByIds(ids);
        // 这里要通知删掉部门、岗职位、人员等
        mdmPublisher.publish(EventConstants.DeleteOrgEvent,Arrays.asList(new Long[]{id}));
        //转换成Vo发送增量mq
        log.debug("删除用户成功-发送增量MQ");
        mdmMqService.sendRocketMq(orgTopics, MqTagEnum.DEL.code, String.valueOf(id));
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    /**
     * 递归找到自己的所有子节点
     * @param ids
     * @param id
     */
    void getSubList(List<Long> ids, Long id){
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmOrg::getParentId,id).orderByAsc(MdmOrg::getSort);
        List<MdmOrg> list = list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            list.forEach(mdmOrg -> {
                getSubList(ids,mdmOrg.getId());
                ids.add(mdmOrg.getId());
                //entities.add(mdmOrg);
            });
        }
        log.debug("getSubList 查询成功");
    }

    @Override
    public List<MdmOrgVo> getOrgByJcOrgIds(String ids) {
        List<MdmOrgVo> vos = new ArrayList<>();
        if(StringUtils.isBlank(ids)) {
            return vos;
        }
        List<String> streamStr = Stream.of(ids.split(",")).collect(Collectors.toList());
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmOrg::getGepsJcOrgId,streamStr).orderByDesc(MdmOrg::getLevel);
        List<MdmOrg> mdmOrgs = mdmOrgMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(mdmOrgs)) {
            vos = DozerUtils.mapList(dozerMapper, mdmOrgs, MdmOrgVo.class);
        }
        return vos;
    }

    @Override
    public List<MdmOrgAllVo> getDeptByOrgId(Long id) {
        if(null == id && 0L == id) {
            throw new IncloudException("id不能为空！");
        }
        MdmOrgVo mdmOrgVo = get(id);
        if(!OrgTypeEnum.ORG.code.equals(mdmOrgVo.getOrgType())) {
            throw new IncloudException("该id不是机构信息！");
        }
        List<Long> ids = new ArrayList<>();
        getSubList2(ids,id);
        if(CollectionUtil.isNotEmpty(ids)) {
            List<MdmOrg> list  = mdmOrgMapper.selectBatchIds(ids);
            return handleMdmOrgAllVoList(list);
        } else {
            return null;
        }
    }

    @Override
    @SneakyThrows
    @Transactional
    public Boolean backoutOrg(Long id) {
        if(null == id || 0L == id) {
            throw new IncloudException("机构id不能为空！");
        }
        MdmOrg mdmOrg = this.getById(id);
        //查询他的父级机构是否可用 不可用 不可恢复
        MdmOrg mdmOrgParentId = this.getById(mdmOrg.getParentId());
        if(null != mdmOrgParentId) {
            if(StatusEnum.RUNNING.code != mdmOrgParentId.getStatus()) {
                throw new IncloudException("父级机构不是运营中，无法恢复！");
            }
        } else {
            throw new IncloudException("不存在父级机构，无法恢复！");
        }
        mdmOrg.setStatus(StatusEnum.RUNNING.code);
        mdmOrg.setUpdateTime(LocalDateTime.now());
        List<MdmOrg> orgs = new ArrayList<>();
        //处理下状态
        setSubListInfoByParent(orgs,mdmOrg,null, HandleTypeEnum.STATUS.code);
        //把他自己也加进去
        orgs.add(mdmOrg);
        boolean result = this.updateBatchById(orgs);
        if(result){
            //转换成Vo发送增量mq
            log.info("updateStatus 修改成功！");
            if(CollectionUtil.isNotEmpty(orgs)) {
                //转换成Vo发送增量mq
                List<MdmOrgVo> mdmOrgVos = DozerUtils.mapList(dozerMapper, orgs, MdmOrgVo.class);
                log.debug("updateStatus 机构修改成功-发送增量MQ");
                for (MdmOrgVo mdmOrgVo : mdmOrgVos) {
                    mdmMqService.sendRocketMq(orgTopics, MqTagEnum.EDIT.code, JSON.toJSONString(mdmOrgVo, SerializerFeature.WriteMapNullValue));
                }
            }
        }
        return result;
    }

    @Override
    public MdmOrg getByQyWeChatId(Integer qyWechatId) {
        LambdaQueryWrapper<MdmOrg> mdmOrgLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mdmOrgLambdaQueryWrapper.eq(MdmOrg::getQyWeChatDeptId,qyWechatId);
        return mdmOrgMapper.selectOne(mdmOrgLambdaQueryWrapper);
    }

    /**
     * 递归找到自己的所有子节点 只递归部门
     * @param ids
     * @param id
     */
    void getSubList2(List<Long> ids, Long id){
        LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmOrg::getOrgType,OrgTypeEnum.DEPT.code);
        queryWrapper.eq(MdmOrg::getStatus,StatusEnum.RUNNING.code);
        queryWrapper.eq(MdmOrg::getParentId,id).orderByAsc(MdmOrg::getSort);
        List<MdmOrg> list = list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            list.forEach(mdmOrg -> {
                getSubList2(ids,mdmOrg.getId());
                ids.add(mdmOrg.getId());
                //entities.add(mdmOrg);
            });
        }
        log.debug("getSubList 查询成功");
    }

}
