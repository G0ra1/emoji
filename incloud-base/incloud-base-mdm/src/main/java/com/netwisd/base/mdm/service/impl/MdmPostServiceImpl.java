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
 * @Description ?????? ????????????...
 * @author ???????????? limengzheng@netwisd.com
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
     * ??????????????????1
     */
    private static Integer defaultSort = 1;
    /**
     * ??????????????????ID
     */
    private static String defaultJoinId = ",";

    /**
     * ????????????????????????
     */
    private static String defaultJoinName = ",";
    /**
    * ????????????????????????
    * @param mdmPostDto
    * @return
    */
    @Override
    public Page list(MdmPostDto mdmPostDto) {
//        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
//        //??????????????????????????????
//        queryWrapper.like(StringUtils.isNotBlank(mdmPostDto.getPostName()),MdmPost::getPostName,mdmPostDto.getPostName());
//        //????????????????????????
//        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostDto.getPostSequId()),MdmPost::getPostSequId,mdmPostDto.getPostSequId());
//        queryWrapper.eq(StringUtils.isNotBlank(mdmPostDto.getPostSequName()),MdmPost::getPostSequName,mdmPostDto.getPostSequName());
//        queryWrapper.like(ObjectUtil.isNotNull(mdmPostDto.getParentOrgId()),MdmPost::getOrgFullId,mdmPostDto.getParentOrgId());
//        Page<MdmPost> page = mdmPostMapper.selectPage(mdmPostDto.getPage(),queryWrapper);
//        Page<MdmPostVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmPostVo.class);
//        //????????????????????? ????????????post_user?????????????????????????????????????????????
//        List<MdmPostVo> mdmPostVos = pageVo.getRecords();
//        for (MdmPostVo mdmPostVo : mdmPostVos) {
//            mdmPostVo.setMasterNumber(mdmPostUserService.masterNumber(mdmPostVo.getId()));
//            mdmPostVo.setPartNumber(mdmPostUserService.partNumber(mdmPostVo.getId()));
//        }
        Page<MdmPostVo> pageVo = mdmPostMapper.getList(mdmPostDto.getPage(), mdmPostDto);
        log.debug("????????????:"+pageVo.getTotal());
        List<MdmPostVo> records = pageVo.getRecords();
        log.debug("??????"+ JacksonUtil.toJSONString(records));
        return pageVo;
    }

    /**
    * ?????????????????????
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
        log.debug("????????????:"+mdmPostVos.size());
        return mdmPostVos;
    }

    /**
    * ??????ID????????????
    * @param id
    * @return
    */
    @Override
    public MdmPostVo get(Long id) {
        MdmPost mdmPost = super.getById(id);
        MdmPostVo mdmPostVo = null;
        if(mdmPost !=null){
            mdmPostVo = dozerMapper.map(mdmPost,MdmPostVo.class);
            //?????????????????????????????????
            mdmPostVo.setMasterNumber(mdmPostUserService.masterNumber(id));
            mdmPostVo.setPartNumber(mdmPostUserService.partNumber(id));
        }
        log.debug("????????????");
        return mdmPostVo;
    }

    /**
     * ????????????????????????
     * @param mdmPostDto
     * @return
     */
    @Override
    public  List<MdmPostVo> getSameLevel(MdmPostDto mdmPostDto){
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(mdmPostDto.getParentDeptId()),MdmPost::getParentDeptId,mdmPostDto.getParentDeptId());
        //??????
        queryWrapper.orderByAsc(MdmPost::getSort);
        List<MdmPost> mdmPostList = this.list(queryWrapper);
        List<MdmPostVo> mdmPostListVo = null;
        if (CollectionUtil.isNotEmpty(mdmPostList)){
            mdmPostListVo = DozerUtils.mapList(dozerMapper, mdmPostList, MdmPostVo.class);
        }
        return mdmPostListVo;
    }

    /**
     * ????????????
     * @param mdmPostDtos
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean save(List<MdmPostDto> mdmPostDtos) {
        if (CollectionUtil.isNotEmpty(mdmPostDtos)){
            //?????????????????????????????????????????????????????????
            checkRepeat( mdmPostDtos);
            //????????????????????????????????????????????????,???????????????sort
            checkListRepeat(mdmPostDtos);
            //???????????????????????????????????????,??????????????????????????????????????????
            Set<Long> parentIds = mdmPostDtos.stream().map(MdmPostDto::getParentDeptId).collect(Collectors.toSet());
            List<MdmOrg> mdmOrgs = mdmOrgMapper.selectBatchIds(parentIds);
            Map<Long, List<MdmOrg>> groupMap = mdmOrgs.stream().collect(Collectors.groupingBy(MdmOrg::getId));
            //???????????????????????????id??????????????????????????????
            //????????????
            for (MdmPostDto postDto : mdmPostDtos) {
                List<MdmOrg> _mdmOrg = groupMap.get(postDto.getParentDeptId());
                MdmOrg mdmOrg = _mdmOrg.get(0);
                //??????????????????????????????????????????????????????ParentDeptId
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
            //??????
            List<MdmPost> mdmPosts = DozerUtils.mapList(dozerMapper, mdmPostDtos, MdmPost.class);
            boolean result = super.saveBatch(mdmPosts);
            if(result){
                log.debug("????????????");
                //????????????neo4j
                //neoService.savePostNode(mdmPosts);
                if(CollectionUtil.isNotEmpty(mdmPosts)) {
                    //?????????Vo????????????mq
                    List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPosts, MdmPostVo.class);
                    log.debug("??????????????????-????????????MQ");
                    for (MdmPostVo mdmPostVo : mdmPostVos) {
                        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPostVo.getParentDeptId());
                        String topics[] = postTopics.split(",");
                        //??????geps
                        if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                            mdmMqService.sendRocketMq(topics[0], MqTagEnum.ADD.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                        }
                        //??????oa
                        if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                            mdmMqService.sendRocketMq(topics[1], MqTagEnum.ADD.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                        }
                    }
                }
            }
            return result;
        }else {
            throw new IncloudException("?????????????????????????????????");
        }
    }

    /**
     * ????????????
     * @param mdmPostDto
     * @return
     */
    @Transactional
    @Override
    public Boolean saveOne(MdmPostDto mdmPostDto) {
        //?????????list????????????????????????????????????????????????newList????????????????????????????????????
        List<MdmPostDto> mdmPostDtos = new ArrayList<>();
        mdmPostDtos.add(mdmPostDto);
        Boolean result = save(mdmPostDtos);
        if(result){
            log.debug("????????????");
        }
        return true;
    }


    /**
    * ????????????
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
//            throw new IncloudException("???????????????????????????????????????");
//        }
        mdmPostDto.setUpdateTime(LocalDateTime.now());
        MdmPost mdmPost = dozerMapper.map(mdmPostDto,MdmPost.class);
        boolean result = super.updateById(mdmPost);
        //???????????????post_user?????????
        List<MdmPost> list = new ArrayList<>();
        list.add(mdmPost);
        //post_user
        mdmPostUserService.updateByPost(list);
        //role_post
        mdmRolePostService.updateByPost(list);
        if(result){
            log.debug("????????????");
            MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPost.getParentDeptId());
            String _postTopics[] = postTopics.split(",");
            String _userTopics[] = userTopics.split(",");
            //??????geps
            if(null != checkLvTypeOrg.getLvType()) {  //???????????????????????????????????? ?????????????????????
                this.postSendMq(mdmPost,_postTopics[0],_userTopics[0]);
            }
            //??????OA
            if(null != checkLvTypeOrg.getOaLvType()) {
                this.postSendMq(mdmPost,_postTopics[1],_userTopics[1]);
            }
        }
//        //???????????? ?????????????????????????????????
//        List<MdmPost> mdmPostList = new ArrayList<>();
//        mdmPostList.add(mdmPost);
//        mdmPublisher.publish(EventConstants.UpdateUserEvent,mdmPostList);
        return result;
    }

    /**
     * ????????????Post mq ????????????
     * @param mdmPost
     * @param _postTopics
     * @param _userTopics
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    public void postSendMq(MdmPost mdmPost,String _postTopics,String _userTopics) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //?????????Vo????????????mq
        log.debug("??????????????????-????????????MQ");
        MdmPostVo mdmPostVo = dozerMapper.map(mdmPost,MdmPostVo.class);
        mdmMqService.sendRocketMq(_postTopics, MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
        //??????????????????????????????????????? ??????????????????????????????mq
//        List<MdmPostUserVo> postUserVos = mdmPostUserService.getUserByPostId(mdmPost.getId());
//        if(CollectionUtil.isNotEmpty(postUserVos)) {
//            //????????????????????? ??????mq (?????????????????? ?????????MQ)
//            for (MdmPostUserVo postUserVo : postUserVos) {
//                log.debug("????????????-????????????????????????-????????????MQ");
//                MdmUserVo mdmUserVo = mdmUserService.getUserToMQ(postUserVo.getUserId());
//                mdmMqService.sendRocketMq(_userTopics, MqTagEnum.EDIT.code, JSON.toJSONString(mdmUserVo, SerializerFeature.WriteMapNullValue));
//            }
//        }
    }

    /**
    * ??????ID??????
    * @param
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean delete(String ids) {
        if(StringUtils.isNotBlank(ids)) {
            List<String> streamStr = Stream.of(ids.split(",")).collect(Collectors.toList());
            //?????????????????????????????????????????????????????????????????????
            List<MdmPost> mdmPosts = mdmPostMapper.selectBatchIds(streamStr);
            for (MdmPost mdmPost : mdmPosts) {
                if (mdmPost.getIsRef().intValue() == YesNo.YES.code.intValue()) {
                    throw new IncloudException(mdmPost.getPostName()+"?????????????????????????????????");
                }
            }
            boolean result = super.removeByIds(streamStr);
            //????????????post_user?????????????????????
            mdmPostUserService.deleteByPostId(streamStr);
            //????????????role_post?????????????????????
            mdmRolePostService.deleteByPostId(streamStr);
            if (result) {
                log.debug("????????????");
            }
            //??????neo4j
            //neoService.delNodeByMid(NeoNodeEnum.POST.code,streamStr);
            log.debug("????????????-????????????MQ");
            mdmMqService.sendRocketMq(userTopics, MqTagEnum.DEL.code, ids);
            return result;
        }else {
            throw new IncloudException("???????????????id???????????????");
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
     * ??????????????????????????????
     * @param mdmPostDto
     * @return
     */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean copyToOrg(MdmPostDto mdmPostDto) {
        //????????????????????????????????????
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmPost::getParentDeptId,mdmPostDto.getParentDeptId())
                    .eq(MdmPost::getPostName,mdmPostDto.getPostName());
        List<MdmPost> mdmPosts = mdmPostMapper.selectList(queryWrapper);
        if (mdmPosts.size() > 0) {
            throw new IncloudException("??????????????????????????????????????????????????????");
        }
        mdmPostDto.setId(null);
        mdmPostDto.setCreateTime(LocalDateTime.now());
        mdmPostDto.setUpdateTime(LocalDateTime.now());
        //???????????????????????????????????????
        LambdaQueryWrapper<MdmOrg> query = new LambdaQueryWrapper<>();
        query.eq(ObjectUtil.isNotNull(mdmPostDto.getParentDeptId()),MdmOrg::getId,mdmPostDto.getParentDeptId());
        MdmOrg mdmOrg = mdmOrgMapper.selectOne(query);
        //????????????org??????????????????????????????????????????????????????org
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
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        mdmPostDto.setIsRef(YesNo.NO.code);
        //????????????
        LambdaQueryWrapper<MdmPost> _queryWrapper = new LambdaQueryWrapper<>();
        _queryWrapper.eq(MdmPost::getParentDeptId,mdmPostDto.getParentDeptId());
        Integer size = mdmPostMapper.selectCount(_queryWrapper);
        size++;
        mdmPostDto.setSort(size);

        MdmPost mdmPost = dozerMapper.map(mdmPostDto, MdmPost.class);
        boolean result = super.save(mdmPost);
        if (result){
            //?????????Vo????????????mq
            log.debug("??????COPY????????????-????????????MQ");
            MdmPostVo mdmPostVo = dozerMapper.map(mdmPostDto, MdmPostVo.class);
            MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPostVo.getParentDeptId());
            String topics[] = postTopics.split(",");
            //??????geps
            if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                mdmMqService.sendRocketMq(topics[0], MqTagEnum.ADD.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
            }
            //??????oa
            if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                mdmMqService.sendRocketMq(topics[1], MqTagEnum.ADD.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
            }
            log.debug("????????????");
        }
        return result;
    }

    /**
     * ????????????id????????????????????????
     * @param id
     * @return
     */
    @Override
    public List<MdmPostVo> getPost(Long id) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(MdmPost::getOrgFullId,id);
        //???????????????
        queryWrapper.eq(MdmPost::getStatus,YesNo.YES.code);
        //??????
        queryWrapper.orderByAsc(MdmPost::getSort);
        List<MdmPost> mdmPosts = mdmPostMapper.selectList(queryWrapper);
        List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPosts, MdmPostVo.class);
        return mdmPostVos;
    }

    /**
     * ????????????id?????????????????????
     * @param
     * @return
     */
    @Override
    public List<MdmPostVo> getAllPost(MdmPostDto mdmPostDto) {
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        //???????????????id
        queryWrapper.like(ObjectUtil.isNotNull(mdmPostDto.getParentOrgId()),MdmPost::getOrgFullId,mdmPostDto.getParentOrgId());
        //????????????????????????
        queryWrapper.like(StringUtils.isNotBlank(mdmPostDto.getPostName()),MdmPost::getPostName,mdmPostDto.getPostName());
        //??????
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
     * ????????????????????????????????????????????????????????????
     * @param mdmPostDto
     */
    void checkRepeat(List<MdmPostDto> mdmPostDto){
        Map<Long, List<MdmPostDto>> groupMap = mdmPostDto.stream().collect(Collectors.groupingBy(MdmPostDto::getParentDeptId));
        for (Map.Entry<Long, List<MdmPostDto>> listEntry : groupMap.entrySet()) {
            List<MdmPostDto> postDtos = listEntry.getValue();
            List<String> list = postDtos.stream().map(MdmPostDto::getPostName).collect(Collectors.toList());
            Set<String> set = postDtos.stream().map(MdmPostDto::getPostName).collect(Collectors.toSet());
            if (list.size() != set.size()){
                //throw new IncloudException("???????????????????????????????????????????????????");
            }
        }
    }
    /**
     * ?????????????????????????????????????????????
     * @param mdmPostDto
     */
    void checkListRepeat(List<MdmPostDto> mdmPostDto){
        //????????????id???????????????id?????????????????????
        Set<Long> parentIds = mdmPostDto.stream().map(MdmPostDto::getParentDeptId).collect(Collectors.toSet());
        LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmPost::getParentDeptId,parentIds);
        queryWrapper.in(MdmPost::getStatus,YesNo.YES.code);//???????????????
        List<MdmPost> mdmPosts = mdmPostMapper.selectList(queryWrapper);
        //todo ??????????????? ?????????????????? ????????????code ??????????????????????????? ?????????????????????????????? ????????????
        //????????????????????????????????????????????????????????????
//        for (MdmPostDto postDto : mdmPostDto) {
//            for (MdmPost mdmPost : mdmPosts) {
//                if (postDto.getParentDeptId().longValue() == mdmPost.getParentDeptId().longValue() && postDto.getPostName().equals(mdmPost.getPostName())){
//                    throw new IncloudException("???????????????????????????????????????"+postDto.getPostName());
//                }
//            }
//        }
        //????????????,??????????????????????????????????????????????????????????????????????????????????????????????????????
        setSort(parentIds,mdmPosts,mdmPostDto);
    }


    /**
     * ????????????
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
                    //???????????????????????????????????????????????????????????????
                    postDto.setIsRef(YesNo.NO.code);
                }
            }
        }
    }


    /**
     * ?????????????????????
     * @param sourceId
     * @param targetId
     * @param index
     * @return
     */
    @Override
    public Boolean sortForDept(Long sourceId, Long targetId, String index) {
        MdmPost source = this.getById(sourceId);
        if(ObjectUtil.isEmpty(source)){
            throw new IncloudException("???????????????sourceId????????????????????????????????????");
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
                throw new IncloudException("???????????????targetId??????????????????????????????");
            }
            Integer targetIndex = target.getSort();
            List<MdmPost> otherSortList = getListByParent(source.getParentDeptId(), true, source.getId());
            List<MdmPost> collect = otherSortList.stream().filter(mdmPost -> mdmPost.getSort() >= targetIndex).collect(Collectors.toList());
            list = sortOtherList(collect,targetIndex);
            source.setSort(targetIndex);
        }
        //????????????????????????
        MdmPost mdmPost = dozerMapper.map(source,MdmPost.class);
        list.add(mdmPost);
        boolean result = updateBatchById(list);
        if(result){
            log.info("?????????????????????");
        }
        return true;
    }

    /**
     * ???????????????ID????????????????????????sort???????????????
     * @param parentId
     * @param isAsc
     * @param exclusiveId,??????????????????
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
     * ????????????????????????index?????????
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
     * ???????????????ID????????????????????????sort???????????????
     * @param parentId
     * @param isAsc
     * @param exclusiveId,??????????????????
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
     * ?????????????????????????????????
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean isRef(Long id){
        MdmPost mdmPost = this.getById(id);
        if (ObjectUtil.isEmpty(mdmPost)){
            throw new IncloudException("???????????????????????????");
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
//            //???????????? ??????????????????
//            List<MdmOrg> deptOrgList = mdmOrgList.stream().filter(d -> d.getOrgType() == OrgTypeEnum.DEPT.code).collect(Collectors.toList());
              //???????????????????????????
//            if(CollectionUtil.isNotEmpty(deptOrgList)) {
                //???????????????id
                List<Long> deptIds = mdmOrgList.stream().map(MdmOrg::getId).collect(Collectors.toList());
                LambdaQueryWrapper<MdmPost> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(MdmPost::getParentDeptId, deptIds);
                List<MdmPost> mdmPostList = mdmPostMapper.selectList(queryWrapper);
                if (CollectionUtil.isNotEmpty(mdmPostList)){
                    Map<Long,List<MdmOrg>> mapGroup = mdmOrgList.stream().collect(Collectors.groupingBy(MdmOrg::getId));
                    for (MdmPost mdmPost : mdmPostList) {
                        List<MdmOrg> deptList = mapGroup.get(mdmPost.getParentDeptId());
                        MdmOrg mdmOrg = deptList.get(0);
                        //??????????????????????????????
                        mdmPost.setParentDeptName(mdmOrg.getOrgName());//??????????????????id??????????????????????????????????????????
                        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        //?????????????????????????????????????????????????????????????????????????????????????????????????????????
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
                        //?????????Vo????????????mq
                        log.debug("????????????????????????-????????????MQ");
                        List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPostList, MdmPostVo.class);
                        if(CollectionUtil.isNotEmpty(mdmPostVos)) {
                            for (MdmPostVo mdmPostVo : mdmPostVos) {
                                MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPostVo.getParentDeptId());
                                String topics[] = postTopics.split(",");
                                //??????geps
                                if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                                    mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                                }
                                //??????oa
                                if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                                    mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                                }
                            }
                        }
                    }
                    //??????post_user???????????????orgFullPostId???orgFullPostName
                    mdmPostUserService.updateByPost(mdmPostList);
                    //role_post
                    mdmRolePostService.updateByPost(mdmPostList);
                    if(CollectionUtil.isNotEmpty(mdmPostList)) {
                        for (MdmPost mdmPost : mdmPostList) {
                            List<MdmPostUserVo> postUserVos = mdmPostUserService.getUserByPostId(mdmPost.getId());
                            if(CollectionUtil.isNotEmpty(postUserVos)) {
                                //????????????????????? ??????mq (?????????????????? ?????????MQ)
                                for (MdmPostUserVo postUserVo : postUserVos) {
                                    log.debug("????????????????????????-????????????????????????-????????????MQ");
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
                                log.debug("????????????");
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
            //????????????id
            List<Long> deptIds = mdmPostDtos.stream().map(MdmPostDto::getParentDeptId).collect(Collectors.toList());
            LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmOrg::getId, deptIds);
            List<MdmOrg> mdmOrgList = mdmOrgMapper.selectList(queryWrapper);
            Map<Long,List<MdmOrg>> mapGroup = mdmOrgList.stream().collect(Collectors.groupingBy(MdmOrg::getId));
            for (MdmPostDto mdmPostDto : mdmPostDtos) {
                List<MdmOrg> mdmOrgs = mapGroup.get(mdmPostDto.getParentDeptId());
                MdmOrg mdmOrg = mdmOrgs.get(0);
                //?????????????????????????????????????????????????????????????????????id??????
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
                    //?????????Vo????????????mq
                    log.debug("????????????????????? ??????????????????-????????????MQ");
                    List<MdmPostVo> mdmPostVos = DozerUtils.mapList(dozerMapper, mdmPosts, MdmPostVo.class);
                    for (MdmPostVo mdmPostVo : mdmPostVos) {
                        MdmOrg checkLvTypeOrg = mdmOrgService.getParentByParentId(mdmPostVo.getParentDeptId());
                        String topics[] = postTopics.split(",");
                        //??????geps
                        if(null != checkLvTypeOrg.getLvType() && 0!=checkLvTypeOrg.getLvType()){
                            mdmMqService.sendRocketMq(topics[0], MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                        }
                        //??????oa
                        if(null != checkLvTypeOrg.getOaLvType() && 0!=checkLvTypeOrg.getOaLvType()){
                            mdmMqService.sendRocketMq(topics[1], MqTagEnum.EDIT.code, JSON.toJSONString(mdmPostVo, SerializerFeature.WriteMapNullValue));
                        }
                    }
                }
            }
            //??????post_user???
            mdmPostUserService.updateByPost(mdmPosts);
            //role_post
            mdmRolePostService.updateByPost(mdmPosts);
            if(CollectionUtil.isNotEmpty(mdmPosts)) {
                for (MdmPost mdmPost : mdmPosts) {
                    List<MdmPostUserVo> postUserVos = mdmPostUserService.getUserByPostId(mdmPost.getId());
                    if(CollectionUtil.isNotEmpty(postUserVos)) {
                        //????????????????????? ??????mq (?????????????????? ?????????MQ)
                        for (MdmPostUserVo postUserVo : postUserVos) {
                            log.debug("????????????????????? ??????????????????-????????????????????????-????????????MQ");
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
                        log.debug("????????????");
                    }
                }
            }
        }
        return true;
    }

    /**
     * ????????????(????????????/????????????/??????/??????)
     * @param mdmEvent
     */
    @EventListener(MdmEvent.class)
    @Async("incloudExecutor")
    @SneakyThrows
    public void onApplicationEvent(MdmEvent mdmEvent) {
        Map<String, List> data = mdmEvent.getListMap();
        Map<String, IDto> dtoData = mdmEvent.getDtoMap();
        //????????????
        if(data.containsKey(EventConstants.UpdateParentOrgEvent)){
            List<MdmOrg> mdmOrgList = (List)data.get(EventConstants.UpdateParentOrgEvent);
            this.orgUpdate(mdmOrgList);
        }
        //??????????????????
        if (data.containsKey(EventConstants.UpdateNameOrgEvent)){
            List<MdmOrg> mdmOrgList = (List)data.get(EventConstants.UpdateNameOrgEvent);
            this.orgUpdate(mdmOrgList);
        }
        //????????????
        if(dtoData.containsKey(EventConstants.BrokenOrgEvent)){
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.BrokenOrgEvent);
            List<MdmPostDto> mdmPostDtos = mdmOrgStatusDto.getMdmPostDtos();
            this.postUpdate(mdmPostDtos);
        }
        //????????????
        if(dtoData.containsKey(EventConstants.CanceledOrgEvent)){
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.CanceledOrgEvent);
            List<MdmPostDto> mdmPostDtos = mdmOrgStatusDto.getMdmPostDtos();
            this.postUpdate(mdmPostDtos);
        }
    }
}