package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.dto.MdmOrgStatusDto;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.vo.*;
import com.netwisd.base.mdm.constants.*;
import com.netwisd.base.mdm.entity.MdmOrg;
import com.netwisd.base.mdm.entity.MdmPost;
import com.netwisd.base.mdm.event.MdmEvent;
import com.netwisd.base.mdm.event.MdmPublisher;
import com.netwisd.base.mdm.mapper.MdmOrgMapper;
import com.netwisd.base.mdm.mapper.MdmPostMapper;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.JacksonUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 岗位 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-25 15:15:37
 */
@Service
@Slf4j
public class MdmPostServiceImpl extends ServiceImpl<MdmPostMapper, MdmPost> implements MdmPostService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmPostMapper mdmPostMapper;

    @Autowired
    private MdmPostUserService  mdmPostUserService;

    @Autowired
    private MdmRolePostService mdmRolePostService;

    @Autowired
    private MdmOrgMapper mdmOrgMapper;

    @Autowired
    private MdmPublisher mdmPublisher;

    @Autowired
    private NeoService neoService;

    @Autowired
    private MdmMqService mdmMqService;

    @Autowired
    @Lazy
    private MdmUserService mdmUserService;

    @Autowired
    @Lazy
    private MdmOrgService mdmOrgService;

    @Value("${spring.rocketmq.postTopics}")
    private String postTopics;

    @Value("${spring.rocketmq.userTopics}")
    private String userTopics;

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
    * 单表简单查询操作
    * @param mdmPostDto
    * @return
    */
    @Override
    public Page list(MdmPostDto mdmPostDto) {
//        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
//        //通过岗位名称模糊查询
//        queryWrapper.like(StringUtils.isNotBlank(mdmPostDto.getPostName()),MdmPost::getPostName,mdmPostDto.getPostName());
//        //根据岗位序列查询
//        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostDto.getPostSequId()),MdmPost::getPostSequId,mdmPostDto.getPostSequId());
//        queryWrapper.eq(StringUtils.isNotBlank(mdmPostDto.getPostSequName()),MdmPost::getPostSequName,mdmPostDto.getPostSequName());
//        queryWrapper.like(ObjectUtil.isNotNull(mdmPostDto.getParentOrgId()),MdmPost::getOrgFullId,mdmPostDto.getParentOrgId());
//        Page<MdmPost> page = mdmPostMapper.selectPage(mdmPostDto.getPage(),queryWrapper);
//        Page<MdmPostVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmPostVo.class);
//        //主岗和兼岗人数 通过查询post_user表计算出来并设置值，返回给前端
//        List<MdmPostVo> mdmPostVos = pageVo.getRecords();
//        for (MdmPostVo mdmPostVo : mdmPostVos) {
//            mdmPostVo.setMasterNumber(mdmPostUserService.masterNumber(mdmPostVo.getId()));
//            mdmPostVo.setPartNumber(mdmPostUserService.partNumber(mdmPostVo.getId()));
//        }
        Page<MdmPostVo> pageVo = mdmPostMapper.getList(mdmPostDto.getPage(), mdmPostDto);
        log.debug("查询条数:"+pageVo.getTotal());
        List<MdmPostVo> records = pageVo.getRecords();
        log.debug("全部"+ JacksonUtil.toJSONString(records));
        return pageVo;
    }

    /**
    * 不分页集合查询
    * @param mdmPostDto
    * @return
    */
    @Override
    public  List<MdmPostVo> lists(MdmPostDto mdmPostDto) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostDto.getPostLowParentId()),MdmPost::getPostLowParentId,mdmPostDto.getPostLowParentId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmPostDto.getPostLowParentName()),MdmPost::getPostLowParentName,mdmPostDto.getPostLowParentName());
        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostDto.getPostUpParentId()),MdmPost::getPostUpParentId,mdmPostDto.getPostUpParentId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmPostDto.getPostUpParentName()),MdmPost::getPostUpParentName,mdmPostDto.getPostUpParentName());
        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostDto.getParentDeptId()),MdmPost::getParentDeptId,mdmPostDto.getParentDeptId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmPostDto.getParentDeptName()),MdmPost::getParentDeptName,mdmPostDto.getParentDeptName());
        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostDto.getPostSequId()),MdmPost::getPostSequId,mdmPostDto.getPostSequId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmPostDto.getPostSequName()),MdmPost::getPostSequName,mdmPostDto.getPostSequName());
        queryWrapper.like(ObjectUtil.isNotNull(mdmPostDto.getParentOrgId()),MdmPost::getOrgFullId,mdmPostDto.getParentOrgId());
        queryWrapper.like(StringUtils.isNotBlank(mdmPostDto.getPostName()),MdmPost::getPostName,mdmPostDto.getPostName());
        queryWrapper.like(StringUtils.isNotBlank(mdmPostDto.getPostCode()),MdmPost::getPostCode,mdmPostDto.getPostCode());
        queryWrapper.between(ObjectUtil.isNotNull(mdmPostDto.getSUpdateTime())&&ObjectUtil.isNotNull(mdmPostDto.getEUpdateTime()),MdmPost::getUpdateTime, mdmPostDto.getSUpdateTime(),mdmPostDto.getEUpdateTime());
        queryWrapper.orderByAsc(MdmPost::getSort);
        List<MdmPost> mdmPosts = mdmPostMapper.selectList(queryWrapper);
        List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPosts, MdmPostVo.class);
        log.debug("查询条数:"+mdmPostVos.size());
        return mdmPostVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmPostVo get(Long id) {
        MdmPost mdmPost = super.getById(id);
        MdmPostVo mdmPostVo = null;
        if(mdmPost !=null){
            mdmPostVo = dozerMapper.map(mdmPost,MdmPostVo.class);
            //设置主岗人数和兼岗人数
            mdmPostVo.setMasterNumber(mdmPostUserService.masterNumber(id));
            mdmPostVo.setPartNumber(mdmPostUserService.partNumber(id));
        }
        log.debug("查询成功");
        return mdmPostVo;
    }

    /**
     * 查询同级所有岗位
     * @param mdmPostDto
     * @return
     */
    @Override
    public  List<MdmPostVo> getSameLevel(MdmPostDto mdmPostDto){
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostDto.getParentDeptId()),MdmPost::getParentDeptId,mdmPostDto.getParentDeptId());
        //正序
        queryWrapper.orderByAsc(MdmPost::getSort);
        List<MdmPost> mdmPostList = this.list(queryWrapper);
        List<MdmPostVo> mdmPostListVo = null;
        if (CollectionUtil.isNotEmpty(mdmPostList)){
            mdmPostListVo = DozerUtils.mapList(dozerMapper, mdmPostList, MdmPostVo.class);
        }
        return mdmPostListVo;
    }

    /**
     * 保存实体
     * @param mdmPostDtos
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean save(List<MdmPostDto> mdmPostDtos) {
        if (CollectionUtil.isNotEmpty(mdmPostDtos)){
            //校验新增的岗位中同一部门下名称不能重复
            checkRepeat( mdmPostDtos);
            //校验同一组织下的岗位名称是否重复,并设置排序sort
            checkListRepeat(mdmPostDtos);
            //判断该岗位的上级是否是机构,取出岗位对应的上级部门或机构
            Set<Long> parentIds = mdmPostDtos.stream().map(MdmPostDto::getParentDeptId).collect(Collectors.toSet());
            List<MdmOrg> mdmOrgs = mdmOrgMapper.selectBatchIds(parentIds);
            Map<Long, List<MdmOrg>> groupMap = mdmOrgs.stream().collect(Collectors.groupingBy(MdmOrg::getId));
            //设置父级组织全路径id和父级组织全路径名称
            //拼装数据
            for (MdmPostDto postDto : mdmPostDtos) {
                List<MdmOrg> _mdmOrg = groupMap.get(postDto.getParentDeptId());
                MdmOrg mdmOrg = _mdmOrg.get(0);
                //如果是机构，那么他的上级机构就是他的ParentDeptId
                if (mdmOrg.getOrgType().longValue() == OrgTypeEnum.ORG.getCode().longValue()){
                   postDto.setParentOrgId(postDto.getParentDeptId());
                   postDto.setParentOrgName(postDto.getParentDeptName());
                   postDto.setParentOrgFullName(postDto.getParentOrgFullName() + defaultJoinName + postDto.getParentDeptName());
                }
                if(StringUtils.isBlank(postDto.getParentDeptFullName())){
                    postDto.setParentDeptFullName(postDto.getParentDeptName());
                }else {
                    postDto.setParentDeptFullName(  postDto.getParentDeptFullName() + defaultJoinName + postDto.getParentDeptName());
                }
                postDto.setOrgFullId(postDto.getOrgFullId() + defaultJoinId + postDto.getParentDeptId());
                postDto.setOrgFullName(postDto.getOrgFullName() + defaultJoinName + postDto.getParentDeptName());

            }
            //保存
            List<MdmPost> mdmPosts = DozerUtils.mapList(dozerMapper, mdmPostDtos, MdmPost.class);
            boolean result = super.saveBatch(mdmPosts);
            if(result){
                log.debug("保存成功");
                //保存岗位neo4j
                //neoService.savePostNode(mdmPosts);
                if(CollectionUtil.isNotEmpty(mdmPosts)) {
                    //转换成Vo发送增量mq
                    List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPosts, MdmPostVo.class);
                    log.debug("岗位保存成功-发送增量MQ");
                    for (MdmPostVo mdmPostVo : mdmPostVos) {
                        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPostVo.getParentDeptId());
                        String topics[] = postTopics.split(",");
                        //发送geps
                        if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                            mdmMqService.sendRocketMq(topics[0], MqTagEnum.ADD.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                        }
                        //发送oa
                        if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                            mdmMqService.sendRocketMq(topics[1], MqTagEnum.ADD.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                        }
                    }
                }
            }
            return result;
        }else {
            throw new IncloudException("要保存的数据不能为空！");
        }
    }

    /**
     * 单个新增
     * @param mdmPostDto
     * @return
     */
    @Transactional
    @Override
    public Boolean saveOne(MdmPostDto mdmPostDto) {
        //拼凑成list集合，原来是批量加，现在是单个，newList可以用原来的批量代码逻辑
        List<MdmPostDto> mdmPostDtos = new ArrayList<>();
        mdmPostDtos.add(mdmPostDto);
        Boolean result = save(mdmPostDtos);
        if(result){
            log.debug("保存成功");
        }
        return true;
    }


    /**
    * 修改实体
    * @param mdmPostDto
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(MdmPostDto mdmPostDto) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPost::getParentDeptId,mdmPostDto.getParentDeptId());
        queryWrapper.eq(MdmPost::getPostName,mdmPostDto.getPostName());
        queryWrapper.ne(MdmPost::getId,mdmPostDto.getId());
        Integer count = mdmPostMapper.selectCount(queryWrapper);
//        if (count.intValue() > 0){
//            throw new IncloudException("同一部门下岗位名称不能重复");
//        }
        mdmPostDto.setUpdateTime(LocalDateTime.now());
        MdmPost mdmPost = dozerMapper.map(mdmPostDto,MdmPost.class);
        boolean result = super.updateById(mdmPost);
        //名称修改时post_user表改变
        List<MdmPost> list = new ArrayList<>();
        list.add(mdmPost);
        //post_user
        mdmPostUserService.updateByPost(list);
        //role_post
        mdmRolePostService.updateByPost(list);
        if(result){
            log.debug("修改成功");
            MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPost.getParentDeptId());
            String _postTopics[] = postTopics.split(",");
            String _userTopics[] = userTopics.split(",");
            //推送geps
            if(null != checkLvTypeOrg.getLvType()) {  //设置机构的级别类型的时候 已经推送过新增
                this.postSendMq(mdmPost,_postTopics[0],_userTopics[0]);
            }
            //推送OA
            if(null != checkLvTypeOrg.getOaLvType()) {
                this.postSendMq(mdmPost,_postTopics[1],_userTopics[1]);
            }
        }
//        //修改角色 同时修改岗位人员关系表
//        List<MdmPost> mdmPostList = new ArrayList<>();
//        mdmPostList.add(mdmPost);
//        mdmPublisher.publish(EventConstants.UpdateUserEvent,mdmPostList);
        return result;
    }

    /**
     * 处理修改Post mq 相关处理
     * @param mdmPost
     * @param _postTopics
     * @param _userTopics
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    public void postSendMq(MdmPost mdmPost,String _postTopics,String _userTopics) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //转换成Vo发送增量mq
        log.debug("岗位修改成功-发送增量MQ");
        MdmPostVo mdmPostVo = dozerMapper.map(mdmPost,MdmPostVo.class);
        mdmMqService.sendRocketMq(_postTopics, MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
        //查询出这个岗下对应的那些人 然后推送每个人下变动mq
//        List<MdmPostUserVo> postUserVos = mdmPostUserService.getUserByPostId(mdmPost.getId());
//        if(CollectionUtil.isNotEmpty(postUserVos)) {
//            //查询出用户详情 推送mq (人员岗位变动 也得发MQ)
//            for (MdmPostUserVo postUserVo : postUserVos) {
//                log.debug("岗位修改-人员岗位发生变化-发送增量MQ");
//                MdmUserVo mdmUserVo = mdmUserService.getUserToMQ(postUserVo.getUserId());
//                mdmMqService.sendRocketMq(_userTopics, MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
//            }
//        }
    }

    /**
    * 通过ID删除
    * @param
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean delete(String ids) {
        if(StringUtils.isNotBlank(ids)) {
            List<String> streamStr = Stream.of(ids.split(",")).collect(Collectors.toList());
            //判断该岗位是否被引用，已被引用的岗位不能被删除
            List<MdmPost> mdmPosts = mdmPostMapper.selectBatchIds(streamStr);
            for (MdmPost mdmPost : mdmPosts) {
                if (mdmPost.getIsRef().intValue() == YesNo.YES.code.intValue()) {
                    throw new IncloudException(mdmPost.getPostName()+"岗位已被引用，不能删除");
                }
            }
            boolean result = super.removeByIds(streamStr);
            //删除对应post_user表中对应的关系
            mdmPostUserService.deleteByPostId(streamStr);
            //删除对应role_post表中对应的关系
            mdmRolePostService.deleteByPostId(streamStr);
            if (result) {
                log.debug("删除成功");
            }
            //删除neo4j
            //neoService.delNodeByMid(NeoNodeEnum.POST.code,streamStr);
            log.debug("岗位删除-发送增量MQ");
            mdmMqService.sendRocketMq(userTopics, MqTagEnum.DEL.code, ids);
            return result;
        }else {
            throw new IncloudException("删除岗位的id不能为空！");
        }
    }

    @Override
    public List<MdmPost> getByIds(List<String> ids) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmPost::getId,ids);
        List<MdmPost> list = mdmPostMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public MdmPost getByPostCode(String postCode) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPost::getPostCode,postCode);
        List<MdmPost> list = mdmPostMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 复制岗位到另一个部门
     * @param mdmPostDto
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean copyToOrg(MdmPostDto mdmPostDto) {
        //检测岗位是否有重复的名称
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPost::getParentDeptId,mdmPostDto.getParentDeptId())
                    .eq(MdmPost::getPostName,mdmPostDto.getPostName());
        List<MdmPost> mdmPosts = mdmPostMapper.selectList(queryWrapper);
        if (mdmPosts.size() > 0) {
            throw new IncloudException("该部门存在此岗位名称，不能复制此岗位");
        }
        mdmPostDto.setId(null);
        mdmPostDto.setCreateTime(LocalDateTime.now());
        mdmPostDto.setUpdateTime(LocalDateTime.now());
        //查询这个上级是机构还是组织
        LambdaQueryWrapper<MdmOrg> query = new LambdaQueryWrapper<>();
        query.eq(ObjectUtil.isNotNull(mdmPostDto.getParentDeptId()),MdmOrg::getId,mdmPostDto.getParentDeptId());
        MdmOrg mdmOrg = mdmOrgMapper.selectOne(query);
        //如果这个org是机构，那这个岗位的上级机构就是这个org
        if (ObjectUtil.isNotNull(mdmOrg) && mdmOrg.getOrgType().intValue() == OrgTypeEnum.ORG.getCode().intValue()){
            mdmPostDto.setParentOrgId(mdmPostDto.getParentDeptId());
            mdmPostDto.setParentOrgName(mdmPostDto.getParentDeptName());
            mdmPostDto.setParentOrgFullName(mdmPostDto.getParentOrgFullName()+defaultJoinName+mdmPostDto.getParentDeptName());
        }
        if(StringUtils.isBlank(mdmPostDto.getParentDeptFullName())){
            mdmPostDto.setParentDeptFullName(mdmPostDto.getParentDeptName());
        }else {
            mdmPostDto.setParentDeptFullName(mdmPostDto.getParentDeptFullName() + defaultJoinName + mdmPostDto.getParentDeptName());
        }
        mdmPostDto.setOrgFullId(mdmPostDto.getOrgFullId()+defaultJoinId+mdmPostDto.getParentDeptId());
        mdmPostDto.setOrgFullName(mdmPostDto.getOrgFullName()+defaultJoinName+mdmPostDto.getParentDeptName());
        //复制了一个岗位到其他部门，说明这个岗位在其他部门是新岗位，所以是不被引用的
        mdmPostDto.setIsRef(YesNo.NO.code);
        //设置排序
        LambdaQueryWrapper<MdmPost> _queryWrapper = new LambdaQueryWrapper<>();
        _queryWrapper.eq(MdmPost::getParentDeptId,mdmPostDto.getParentDeptId());
        Integer size = mdmPostMapper.selectCount(_queryWrapper);
        size++;
        mdmPostDto.setSort(size);

        MdmPost mdmPost = dozerMapper.map(mdmPostDto, MdmPost.class);
        boolean result = super.save(mdmPost);
        if (result){
            //转换成Vo发送增量mq
            log.debug("岗位COPY保存成功-发送增量MQ");
            MdmPostVo mdmPostVo = dozerMapper.map(mdmPostDto, MdmPostVo.class);
            MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPostVo.getParentDeptId());
            String topics[] = postTopics.split(",");
            //发送geps
            if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                mdmMqService.sendRocketMq(topics[0], MqTagEnum.ADD.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
            }
            //发送oa
            if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                mdmMqService.sendRocketMq(topics[1], MqTagEnum.ADD.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
            }
            log.debug("复制成功");
        }
        return result;
    }

    /**
     * 通过部门id查询已启用的岗位
     * @param id
     * @return
     */
    @Override
    public List<MdmPostVo> getPost(Long id) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(MdmPost::getOrgFullId,id);
        //启用的岗位
        queryWrapper.eq(MdmPost::getStatus,YesNo.YES.code);
        //正序
        queryWrapper.orderByAsc(MdmPost::getSort);
        List<MdmPost> mdmPosts = mdmPostMapper.selectList(queryWrapper);
        List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPosts, MdmPostVo.class);
        return mdmPostVos;
    }

    /**
     * 通过组织id查询所有的岗位
     * @param
     * @return
     */
    @Override
    public List<MdmPostVo> getAllPost(MdmPostDto mdmPostDto) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        //机构或部门id
        queryWrapper.like(ObjectUtil.isNotNull(mdmPostDto.getParentOrgId()),MdmPost::getOrgFullId,mdmPostDto.getParentOrgId());
        //岗位名称模糊查询
        queryWrapper.like(StringUtils.isNotBlank(mdmPostDto.getPostName()),MdmPost::getPostName,mdmPostDto.getPostName());
        //启用
        queryWrapper.eq(MdmPost::getStatus,YesNo.YES.code);
        queryWrapper.orderByAsc(MdmPost::getSort);
        List<MdmPost> mdmPosts = mdmPostMapper.selectList(queryWrapper);
        List<MdmPostVo> mdmPostVos = null;
        if (CollectionUtil.isNotEmpty(mdmPosts)){
            mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPosts, MdmPostVo.class);
        }
        return mdmPostVos;
    }

    /**
     * 校验新增数据中同一部门的岗位名称不能重复
     * @param mdmPostDto
     */
    void checkRepeat(List<MdmPostDto> mdmPostDto){
        Map<Long, List<MdmPostDto>> groupMap = mdmPostDto.stream().collect(Collectors.groupingBy(MdmPostDto::getParentDeptId));
        for (Map.Entry<Long, List<MdmPostDto>> listEntry : groupMap.entrySet()) {
            List<MdmPostDto> postDtos = listEntry.getValue();
            List<String> list = postDtos.stream().map(MdmPostDto::getPostName).collect(Collectors.toList());
            Set<String> set = postDtos.stream().map(MdmPostDto::getPostName).collect(Collectors.toSet());
            if (list.size() != set.size()){
                //throw new IncloudException("同一部门下新增的岗位中名称不能重复");
            }
        }
    }
    /**
     * 校验同一组织下岗位名称不能重复
     * @param mdmPostDto
     */
    void checkListRepeat(List<MdmPostDto> mdmPostDto){
        //拿到部门id，根据部门id去查询所属岗位
        Set<Long> parentIds = mdmPostDto.stream().map(MdmPostDto::getParentDeptId).collect(Collectors.toSet());
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmPost::getParentDeptId,parentIds);
        queryWrapper.in(MdmPost::getStatus,YesNo.YES.code);//只判断可用
        List<MdmPost> mdmPosts = mdmPostMapper.selectList(queryWrapper);
        //todo 宏景数据中 有同一部门下 不同岗位code 相同岗位名称的情况 所以不判断同一部门下 重复名称
        //对比同一组织下是否有相同的岗位名称和编号
//        for (MdmPostDto postDto : mdmPostDto) {
//            for (MdmPost mdmPost : mdmPosts) {
//                if (postDto.getParentDeptId().longValue() == mdmPost.getParentDeptId().longValue() && postDto.getPostName().equals(mdmPost.getPostName())){
//                    throw new IncloudException("同一部门下岗位名称已存在："+postDto.getPostName());
//                }
//            }
//        }
        //设置排序,这里已经查出来相关的部门岗位，减少数据库查询次数，所以在这里设置排序
        setSort(parentIds,mdmPosts,mdmPostDto);
    }


    /**
     * 设置排序
     * @param
     */
    private void setSort(Set<Long> parentIds,List<MdmPost> mdmPosts,List<MdmPostDto> mdmPostDto) {
        for (Long parentId : parentIds) {
            List<MdmPost> list = mdmPosts.stream().filter(d -> d.getParentDeptId().longValue() == parentId.longValue()).collect(Collectors.toList());
            int size = list.size();
            for (MdmPostDto postDto : mdmPostDto) {
                if (postDto.getParentDeptId().longValue() == parentId.longValue()){
                    size++;
                    postDto.setSort(size);
                    //新增时的岗位，是不被引用的，在这里设置一下
                    postDto.setIsRef(YesNo.NO.code);
                }
            }
        }
    }


    /**
     * 部门内岗位排序
     * @param sourceId
     * @param targetId
     * @param index
     * @return
     */
    @Override
    public Boolean sortForDept(Long sourceId, Long targetId, String index) {
        MdmPost source = this.getById(sourceId);
        if(ObjectUtil.isEmpty(source)){
            throw new IncloudException("根据传入的sourceId查询不到相应的岗位信息！");
        }
        List<MdmPost> list = new ArrayList<>();
        if(StrUtil.isNotEmpty(index)){
            if(index.equals(SortEnum.FIRST.value)){
                List<MdmPost> otherSortList = getListByParent(source.getParentDeptId(), true, source.getId());
                source.setSort(defaultSort);
                list = sortOtherList(otherSortList,defaultSort);
            }else if(index.equals(SortEnum.LAST.value)){
                MdmPost lastOne = getOneByParent(source.getParentDeptId(), false, null);
                source.setSort(lastOne.getSort() + 1);
            }
        }else {
            MdmPost target = this.getById(targetId);
            if(ObjectUtil.isEmpty(target)){
                throw new IncloudException("根据传入的targetId查询不到相应的实体！");
            }
            Integer targetIndex = target.getSort();
            List<MdmPost> otherSortList = getListByParent(source.getParentDeptId(), true, source.getId());
            List<MdmPost> collect = otherSortList.stream().filter(mdmPost -> mdmPost.getSort() >= targetIndex).collect(Collectors.toList());
            list = sortOtherList(collect,targetIndex);
            source.setSort(targetIndex);
        }
        //把自己也加进去；
        MdmPost mdmPost = dozerMapper.map(source,MdmPost.class);
        list.add(mdmPost);
        boolean result = updateBatchById(list);
        if(result){
            log.info("批量排序成功！");
        }
        return true;
    }

    /**
     * 根据父节点ID，取出其下面排序sort第一条记录
     * @param parentId
     * @param isAsc
     * @param exclusiveId,不包含的记录
     * @return
     */
    @SneakyThrows
    public List<MdmPost> getListByParent(Long parentId,Boolean isAsc,Long exclusiveId) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPost::getParentDeptId,parentId);
        if(ObjectUtil.isNotEmpty(exclusiveId)){
            queryWrapper.ne(MdmPost::getId,exclusiveId);
        }
        if(isAsc){
            queryWrapper.orderByAsc(MdmPost::getSort);
        }else {
            queryWrapper.orderByDesc(MdmPost::getSort);
        }
        List<MdmPost> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 按给定集合和排序index做排序
     * @param otherSortList
     * @param index
     * @return
     */
    List<MdmPost> sortOtherList(List<MdmPost> otherSortList,Integer index){
        List<MdmPost> list = new ArrayList<>();
        for (MdmPost mdmPost : otherSortList){
            mdmPost.setSort(index + 1);
            list.add(mdmPost);
            index++;
        }
        return list;
    }

    /**
     * 根据父节点ID，取出其下面排序sort第一条记录
     * @param parentId
     * @param isAsc
     * @param exclusiveId,不包含的记录
     * @return
     */
    @SneakyThrows
    public MdmPost getOneByParent(Long parentId,Boolean isAsc,Long exclusiveId) {
        List<MdmPost> list = getListByParent(parentId, isAsc, exclusiveId);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            Optional<MdmPost> first = list.stream().findFirst();
            MdmPost mdmPost = first != null ? first.get() : null;
            return mdmPost;
        }
        return null;
    }

    /**
     * 将此岗位设置为已被引用
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean isRef(Long id){
        MdmPost mdmPost = this.getById(id);
        if (ObjectUtil.isEmpty(mdmPost)){
            throw new IncloudException("没有对应的岗位信息");
        }
        mdmPost.setIsRef(YesNo.YES.code);
        boolean result = this.updateById(mdmPost);
        return result;
    }



    @Override
    @Transactional
    @SneakyThrows
    public Boolean orgUpdate(List<MdmOrg> mdmOrgList) {
        if(CollectionUtil.isNotEmpty(mdmOrgList)) {
//            //分组获取 只要部门信息
//            List<MdmOrg> deptOrgList = mdmOrgList.stream().filter(d -> d.getOrgType() == OrgTypeEnum.DEPT.code).collect(Collectors.toList());
              //查询部门下所有岗位
//            if(CollectionUtil.isNotEmpty(deptOrgList)) {
                //拿到组织的id
                List<Long> deptIds = mdmOrgList.stream().map(MdmOrg::getId).collect(Collectors.toList());
                LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(MdmPost::getParentDeptId, deptIds);
                List<MdmPost> mdmPostList = mdmPostMapper.selectList(queryWrapper);
                if (CollectionUtil.isNotEmpty(mdmPostList)){
                    Map<Long,List<MdmOrg>> mapGroup = mdmOrgList.stream().collect(Collectors.groupingBy(MdmOrg::getId));
                    for (MdmPost mdmPost : mdmPostList) {
                        List<MdmOrg> deptList = mapGroup.get(mdmPost.getParentDeptId());
                        MdmOrg mdmOrg = deptList.get(0);
                        //组织修改名称时会用到
                        mdmPost.setParentDeptName(mdmOrg.getOrgName());//对应它的上级id是不会变的，所以就不用再改了
                        //如果该岗位的上级是部门，那么它修改的时候，它的上级机构是可能发生变化的，此时需要修改该岗位的上级机构
                        //如果该岗位的上级是机构，对于岗位来讲，它的岗位机构还是原来的值不用修改
                        if (mdmOrg.getOrgType().longValue() == OrgTypeEnum.DEPT.getCode().longValue()){
                            mdmPost.setParentOrgId(mdmOrg.getParentOrgId());
                            mdmPost.setParentOrgName(mdmOrg.getParentOrgName());
                            mdmPost.setParentOrgFullName(mdmOrg.getParentOrgFullName());
                        }else {
                            mdmPost.setParentOrgFullName(mdmOrg.getParentOrgFullName()+defaultJoinName+mdmOrg.getOrgName());
                        }
                        if (StringUtils.isBlank(mdmOrg.getParentDeptFullName())){
                            mdmPost.setParentDeptFullName(mdmOrg.getOrgName());
                        }else {
                            mdmPost.setParentDeptFullName(mdmOrg.getParentDeptFullName() + defaultJoinName+ mdmOrg.getOrgName());
                        }
                        mdmPost.setOrgFullId(mdmOrg.getOrgFullId()+defaultJoinName +mdmOrg.getId());
                        mdmPost.setOrgFullName(  mdmOrg.getOrgFullName()+defaultJoinName +mdmOrg.getOrgName());
                    }
                    boolean boo = super.updateBatchById(mdmPostList);
                    if(boo) {
                        //转换成Vo发送增量mq
                        log.debug("岗位修改机构成功-发送增量MQ");
                        List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPostList, MdmPostVo.class);
                        if(CollectionUtil.isNotEmpty(mdmPostVos)) {
                            for (MdmPostVo mdmPostVo : mdmPostVos) {
                                MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPostVo.getParentDeptId());
                                String topics[] = postTopics.split(",");
                                //发送geps
                                if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                                    mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                                }
                                //发送oa
                                if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                                    mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                                }
                            }
                        }
                    }
                    //修改post_user表中岗位的orgFullPostId和orgFullPostName
                    mdmPostUserService.updateByPost(mdmPostList);
                    //role_post
                    mdmRolePostService.updateByPost(mdmPostList);
                    if(CollectionUtil.isNotEmpty(mdmPostList)) {
                        for (MdmPost mdmPost : mdmPostList) {
                            List<MdmPostUserVo> postUserVos = mdmPostUserService.getUserByPostId(mdmPost.getId());
                            if(CollectionUtil.isNotEmpty(postUserVos)) {
                                //查询出用户详情 推送mq (人员岗位变动 也得发MQ)
                                for (MdmPostUserVo postUserVo : postUserVos) {
                                    log.debug("岗位修改机构成功-人员岗位发生变化-发送增量MQ");
                                    MdmUserVo mdmUserVo = mdmUserService.getUserToMQ(postUserVo.getUserId());
                                    MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUserVo.getParentDeptId());
                                    String topics[] = userTopics.split(",");
                                    if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                                        mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                                    }
                                    if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                                        mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                                    }
                                }
                                log.debug("修改成功");
                            }
                        }
                    }
                }
//            }
        }
        return true;
    }
    @Override
    @Transactional
    @SneakyThrows
    public Boolean postUpdate(List<MdmPostDto> mdmPostDtos) {
        if (CollectionUtil.isNotEmpty(mdmPostDtos)){
            //拿到部门id
            List<Long> deptIds = mdmPostDtos.stream().map(MdmPostDto::getParentDeptId).collect(Collectors.toList());
            LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmOrg::getId, deptIds);
            List<MdmOrg> mdmOrgList = mdmOrgMapper.selectList(queryWrapper);
            Map<Long,List<MdmOrg>> mapGroup = mdmOrgList.stream().collect(Collectors.groupingBy(MdmOrg::getId));
            for (MdmPostDto mdmPostDto : mdmPostDtos) {
                List<MdmOrg> mdmOrgs = mapGroup.get(mdmPostDto.getParentDeptId());
                MdmOrg mdmOrg = mdmOrgs.get(0);
                //如果上级是机构，那么岗位的机构字段为上级机构的id本身
                if (mdmOrg.getOrgType().longValue() == OrgTypeEnum.ORG.getCode().longValue()){
                    mdmPostDto.setParentOrgId(mdmPostDto.getParentDeptId());
                    mdmPostDto.setParentOrgName(mdmPostDto.getParentDeptName());
                    mdmPostDto.setParentOrgFullName(mdmPostDto.getParentOrgFullName()+defaultJoinName+mdmPostDto.getParentDeptName());
                }
                if (StringUtils.isBlank(mdmPostDto.getParentDeptFullName())){
                    mdmPostDto.setParentDeptFullName(mdmPostDto.getParentDeptName());
                }else {
                    mdmPostDto.setParentDeptFullName(mdmPostDto.getParentDeptFullName()+defaultJoinName+mdmPostDto.getParentDeptName());
                }
                mdmPostDto.setOrgFullId(mdmPostDto.getOrgFullId()+defaultJoinId+mdmPostDto.getParentDeptId());
                mdmPostDto.setOrgFullName(mdmPostDto.getOrgFullName()+defaultJoinName+mdmPostDto.getParentDeptName());
            }
            List<MdmPost> mdmPosts = DozerUtils.mapList(dozerMapper, mdmPostDtos, MdmPost.class);
            boolean boo = super.updateBatchById(mdmPosts);
            if(boo) {
                if(CollectionUtil.isNotEmpty(mdmPosts)) {
                    //转换成Vo发送增量mq
                    log.debug("机构拆分、撤销 岗位设置成功-发送增量MQ");
                    List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPosts, MdmPostVo.class);
                    for (MdmPostVo mdmPostVo : mdmPostVos) {
                        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPostVo.getParentDeptId());
                        String topics[] = postTopics.split(",");
                        //发送geps
                        if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                            mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                        }
                        //发送oa
                        if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                            mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                        }
                    }
                }
            }
            //修改post_user表
            mdmPostUserService.updateByPost(mdmPosts);
            //role_post
            mdmRolePostService.updateByPost(mdmPosts);
            if(CollectionUtil.isNotEmpty(mdmPosts)) {
                for (MdmPost mdmPost : mdmPosts) {
                    List<MdmPostUserVo> postUserVos = mdmPostUserService.getUserByPostId(mdmPost.getId());
                    if(CollectionUtil.isNotEmpty(postUserVos)) {
                        //查询出用户详情 推送mq (人员岗位变动 也得发MQ)
                        for (MdmPostUserVo postUserVo : postUserVos) {
                            log.debug("机构拆分、撤销 岗位设置成功-人员岗位发生变化-发送增量MQ");
                            MdmUserVo mdmUserVo = mdmUserService.getUserToMQ(postUserVo.getUserId());
                            MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmUserVo.getParentDeptId());
                            String topics[] = userTopics.split(",");
                            if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                                mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                            }
                            if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                                mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
                            }
                        }
                        log.debug("修改成功");
                    }
                }
            }
        }
        return true;
    }

    /**
     * 事件监听(机构修改/修改名称/拆分/撤销)
     * @param mdmEvent
     */
    @EventListener(MdmEvent.class)
    @Async("incloudExecutor")
    @SneakyThrows
    public void onApplicationEvent(MdmEvent mdmEvent) {
        Map<String, List> data = mdmEvent.getListMap();
        Map<String, IDto> dtoData = mdmEvent.getDtoMap();
        //组织修改
        if(data.containsKey(EventConstants.UpdateParentOrgEvent)){
            List<MdmOrg> mdmOrgList = (List)data.get(EventConstants.UpdateParentOrgEvent);
            this.orgUpdate(mdmOrgList);
        }
        //组织修改名称
        if (data.containsKey(EventConstants.UpdateNameOrgEvent)){
            List<MdmOrg> mdmOrgList = (List)data.get(EventConstants.UpdateNameOrgEvent);
            this.orgUpdate(mdmOrgList);
        }
        //组织拆分
        if(dtoData.containsKey(EventConstants.BrokenOrgEvent)){
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.BrokenOrgEvent);
            List<MdmPostDto> mdmPostDtos = mdmOrgStatusDto.getMdmPostDtos();
            this.postUpdate(mdmPostDtos);
        }
        //组织撤销
        if(dtoData.containsKey(EventConstants.CanceledOrgEvent)){
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.CanceledOrgEvent);
            List<MdmPostDto> mdmPostDtos = mdmOrgStatusDto.getMdmPostDtos();
            this.postUpdate(mdmPostDtos);
        }
    }
}