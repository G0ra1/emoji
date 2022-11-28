package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.portal.config.PortalPublisher;
import com.netwisd.base.portal.constants.EventConstants;
import com.netwisd.base.portal.entity.PortalPart;
import com.netwisd.base.portal.entity.PortalPortal;
import com.netwisd.base.portal.fegin.BaseMdmClient;
import com.netwisd.base.portal.mapper.PortalPartMapper;
import com.netwisd.base.portal.mapper.PortalPortalMapper;
import com.netwisd.base.portal.entity.PortalContentNews;
import com.netwisd.base.portal.mapper.PortalContentNewsMapper;
import com.netwisd.base.portal.service.PortalContentNewsService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentNewsDto;
import com.netwisd.base.portal.vo.PortalContentNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-16 17:58:47
 */
@Service
@Slf4j
@Component("StartupListener")
public class PortalContentNewsServiceImpl extends ServiceImpl<PortalContentNewsMapper, PortalContentNews> implements PortalContentNewsService{
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentNewsMapper portalContentNewsMapper;

    @Autowired
    private PortalPortalMapper portalPortalMapper;

    @Autowired
    private PortalPartMapper portalPartMapper;

    @Autowired
    private BaseMdmClient baseMdmClient;

//    @Autowired
//    private WfService wfService;

    @Autowired
    PortalPublisher portalPublisher;

    /**
    * 单表简单查询操作
    * @param portalContentNewsDto
    * @return
    */
    @Override
    public Page list(PortalContentNewsDto portalContentNewsDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        LambdaQueryWrapper<PortalContentNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getPortalId()),PortalContentNews::getPortalId,portalContentNewsDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getPartId()),PortalContentNews::getPartId,portalContentNewsDto.getPartId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getPartCode()),PortalContentNews::getPartCode,portalContentNewsDto.getPartCode());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getPartTypeId()),PortalContentNews::getPartTypeId,portalContentNewsDto.getPartTypeId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentNewsDto.getTitle()),PortalContentNews::getTitle,portalContentNewsDto.getTitle());
        queryWrapper.eq(StringUtils.isNotEmpty(portalContentNewsDto.getDataSource()),PortalContentNews::getDataSource,portalContentNewsDto.getDataSource());
//        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getAuditStatus()),PortalContentNews::getAuditStatus,portalContentNewsDto.getAuditStatus());

        if ("thirdPartNews".equals(portalContentNewsDto.getPartCode()) || "thirdPartNotices".equals(portalContentNewsDto.getPartCode())) {
            Long parentOrgId = loginAppUser.getParentOrgId();
            String orgFullId = loginAppUser.getOrgFullId();
            List<String> orgFullIdList = Arrays.asList(orgFullId.split(","));
            Long parentDeptId = loginAppUser.getParentDeptId();
            Long id = loginAppUser.getId();

            if (parentOrgId != 0) {
                //oa新闻
                if ("thirdPartNews".equals(portalContentNewsDto.getPartCode())) {
                    queryWrapper.eq(PortalContentNews::getOaNewsRangeType,1);
                    queryWrapper.or().eq(PortalContentNews::getPortalId,portalContentNewsDto.getPortalId())
                            .eq(PortalContentNews::getPartCode,portalContentNewsDto.getPartCode())
                            .in(PortalContentNews::getOrgRange,orgFullIdList);
                }
                //oa通告
                if ("thirdPartNotices".equals(portalContentNewsDto.getPartCode())) {
                    queryWrapper.in(PortalContentNews::getOrgRange,orgFullIdList);
                    queryWrapper.or().eq(PortalContentNews::getPortalId,portalContentNewsDto.getPortalId())
                            .eq(PortalContentNews::getPartCode,portalContentNewsDto.getPartCode())
                            .like(PortalContentNews::getDeptRange,parentDeptId);
                    queryWrapper.or().eq(PortalContentNews::getPortalId,portalContentNewsDto.getPortalId())
                            .eq(PortalContentNews::getPartCode,portalContentNewsDto.getPartCode())
                            .like(PortalContentNews::getUserRange,id);
                }
            }
        }

        queryWrapper.orderByDesc(PortalContentNews::getCreateTime);
        Page<PortalContentNews> page = portalContentNewsMapper.selectPage(portalContentNewsDto.getPage(),queryWrapper);
        Page<PortalContentNewsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentNewsVo.class);
        //oa新闻、通告 内容url 后面拼接人员id 为了展示使用
        if ("thirdPartNews".equals(portalContentNewsDto.getPartCode()) || "thirdPartNotices".equals(portalContentNewsDto.getPartCode())) {
            if (CollectionUtils.isNotEmpty(pageVo.getRecords())) {
                List<PortalContentNewsVo> records = pageVo.getRecords();
                for (PortalContentNewsVo newsVo : records) {
                    newsVo.setContentUrl(newsVo.getContentUrl()+"&uid="+loginAppUser.getId());
                }
                pageVo.setRecords(records);
            }
        }

        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 不分页集合查询
    * @param portalContentNewsDto
    * @return
    */
    @Override
    public List<PortalContentNewsVo> lists(PortalContentNewsDto portalContentNewsDto) {
        LambdaQueryWrapper<PortalContentNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getPortalId()),PortalContentNews::getPortalId,portalContentNewsDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getPartId()),PortalContentNews::getPartId,portalContentNewsDto.getPartId());
        queryWrapper.like(ObjectUtils.isNotEmpty(portalContentNewsDto.getPartCode()),PortalContentNews::getPartCode,portalContentNewsDto.getPartCode());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getPartTypeId()),PortalContentNews::getPartTypeId,portalContentNewsDto.getPartTypeId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentNewsDto.getTitle()),PortalContentNews::getTitle,portalContentNewsDto.getTitle());
        queryWrapper.eq(StringUtils.isNotEmpty(portalContentNewsDto.getDataSource()),PortalContentNews::getDataSource,portalContentNewsDto.getDataSource());
        queryWrapper.orderByDesc(PortalContentNews::getCreateTime);
//        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentNewsDto.getAuditStatus()),PortalContentNews::getAuditStatus,portalContentNewsDto.getAuditStatus());
        List<PortalContentNews> portalContentNews = portalContentNewsMapper.selectList(queryWrapper);
        List<PortalContentNewsVo> portalContentNewsVos = DozerUtils.mapList(dozerMapper, portalContentNews, PortalContentNewsVo.class);
        log.debug("查询条数:"+portalContentNewsVos.size());
        return portalContentNewsVos;
    }

    @Override
    public Page<PortalContentNewsVo> findForMobile(PortalContentNewsDto portalContentNewsDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Integer isAdmin = 0;
        if (loginAppUser.getId() < 20) {
            isAdmin = 1;
        }
        portalContentNewsDto.setIsAdmin(isAdmin);
        portalContentNewsDto.setCreateUserId(loginAppUser.getId());
        portalContentNewsDto.setCreateUserParentOrgId(loginAppUser.getParentOrgId());
        portalContentNewsDto.setCreateUserParentDeptId(loginAppUser.getParentDeptId());
        Page<PortalContentNewsVo> pageList = portalContentNewsMapper.getPageList(portalContentNewsDto.getPage(), portalContentNewsDto);
        if(CollectionUtils.isNotEmpty(pageList.getRecords())){
            List<PortalContentNewsVo> records = pageList.getRecords();
            records.forEach(record->{
                if ("thirdPartNews".equals(portalContentNewsDto.getPartCode()) || "thirdPartNotices".equals(portalContentNewsDto.getPartCode())) {
                    String contentUrl = record.getContentUrl();
                    if (contentUrl.contains("?")) {
                        record.setContentUrl(contentUrl+"&uid="+loginAppUser.getId());
                    }else {
                        record.setContentUrl(contentUrl + "?uid=" + loginAppUser.getId());
                    }
                }
            });
        }
        return portalContentNewsMapper.getPageList(portalContentNewsDto.getPage(),portalContentNewsDto);
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentNewsVo get(Long id) {
        PortalContentNews portalContentNews = super.getById(id);
        PortalContentNewsVo portalContentNewsVo = null;
        if(portalContentNews !=null){
            portalContentNewsVo = dozerMapper.map(portalContentNews,PortalContentNewsVo.class);
        }
        log.debug("查询成功");
        return portalContentNewsVo;
    }

    /**
    * 保存实体
    * @param portalContentNewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentNewsDto portalContentNewsDto) {
        contrastTitle(portalContentNewsDto.getPartId(),portalContentNewsDto.getTitle());
//        portalContentNewsDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        portalContentNewsDto.setHits(0L);
        if (StringUtils.isEmpty(portalContentNewsDto.getDataSource())) {
            portalContentNewsDto.setDataSource("local");
        }
        PortalContentNews portalContentNews = dozerMapper.map(portalContentNewsDto,PortalContentNews.class);
        boolean result = super.save(portalContentNews);
        if(result){
            log.debug("保存成功");
        }
        //审批完成后通知 生成静态文件
        Map<String,Object> eventData = new HashMap<>();
        eventData.put(EventConstants.NewsToHtmlFileHandle,portalContentNews);
        portalPublisher.publish(eventData);
        return result;
    }

    /**
    * 修改实体
    * @param portalContentNewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentNewsDto portalContentNewsDto) {
        contrastTitle(portalContentNewsDto.getPartId(),portalContentNewsDto.getTitle());
        portalContentNewsDto.setUpdateTime(LocalDateTime.now());
        PortalContentNews portalContentNews = dozerMapper.map(portalContentNewsDto,PortalContentNews.class);
        boolean result = super.updateById(portalContentNews);
        if(result){
            log.debug("修改成功");
        }
        //审批完成后通知 生成静态文件
        Map<String,Object> eventData = new HashMap<>();
        eventData.put(EventConstants.NewsToHtmlFileHandle,portalContentNews);
        portalPublisher.publish(eventData);
        return result;
    }

    @Override
    public Boolean delete(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new IncloudException("请选择对应的信息进行删除!");
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        LambdaQueryWrapper<PortalContentNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PortalContentNews::getId,idsList);
        int delete = portalContentNewsMapper.delete(queryWrapper);
        if (delete > 0) {
            log.debug("新闻通告类内容发布删除成功");
        }else {
            throw new IncloudException("新闻通告类内容发布删除失败");
        }
        return true;
    }

    /**
     * 增加点击量
     * @param id
     * @return
     */
    @Override
    public Boolean addHits(Long id) {
        LambdaQueryWrapper<PortalContentNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentNews::getId,id);
        PortalContentNews portalContentNews = portalContentNewsMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(portalContentNews)) {
            throw new IncloudException("未找到相关信息,点击量无法增加");
        }
        portalContentNews.setHits(portalContentNews.getHits()+1);
        portalContentNewsMapper.updateById(portalContentNews);
        return true;
    }

    @Override
    public Boolean saveNews(PortalContentNewsDto newsDto) {
        //验证字段
        checkNewsDto(newsDto,"add");

        //查找生效门户
        LambdaQueryWrapper<PortalPortal> portalWrapper = new LambdaQueryWrapper<>();
        portalWrapper.eq(PortalPortal::getIsDefault, YesNo.YES.code);
        PortalPortal portalPortal = portalPortalMapper.selectOne(portalWrapper);
        newsDto.setPortalId(portalPortal.getId());
        newsDto.setPortalName(portalPortal.getPortalName());
        //通过门户id查找固定的新闻栏目
        LambdaQueryWrapper<PortalPart> portalPartWrapper = new LambdaQueryWrapper<>();
        portalPartWrapper.eq(PortalPart::getPartCode,newsDto.getPartCode());
        portalPartWrapper.eq(PortalPart::getPortalId,portalPortal.getId());
        PortalPart portalPart = portalPartMapper.selectOne(portalPartWrapper);
        if (ObjectUtils.isEmpty(portalPart)) {
            throw new IncloudException("第三方同步新闻/通告列表失败，未找到code为:"+newsDto.getPartCode()+"的栏目");
        }
        //给新闻信息添加属性
        newsDto.setDataSource("oa");
        newsDto.setPartId(portalPart.getId());
        newsDto.setPartCode(portalPart.getPartCode());
        newsDto.setPartName(portalPart.getPartName());
        newsDto.setPartTypeId(portalPart.getPartTypeId());
        newsDto.setPartTypeName(portalPart.getPartTypeName());
        //添加人员信息
        if (ObjectUtils.isNotEmpty(newsDto.getCreateUserId())) {
            MdmUserVo mdmUserVo = getUserById(newsDto.getCreateUserId());
            if (ObjectUtils.isNotEmpty(mdmUserVo)) {
                newsDto.setCreateUserOrgFullId(mdmUserVo.getOrgFullId());
                newsDto.setCreateUserParentOrgId(mdmUserVo.getParentOrgId());
                newsDto.setCreateUserParentOrgName(mdmUserVo.getParentOrgName());
                newsDto.setCreateUserParentDeptId(mdmUserVo.getParentDeptId());
                newsDto.setCreateUserParentDeptName(mdmUserVo.getParentDeptName());
            }
        }

        PortalContentNews news = dozerMapper.map(newsDto, PortalContentNews.class);
        int insert = portalContentNewsMapper.insert(news);
        if (insert > 0) {
            return true;
        }
        return null;
    }

    @Override
    public Boolean updateNews(PortalContentNewsDto newsDto) {
        //验证字段
        checkNewsDto(newsDto,"update");

        LambdaQueryWrapper<PortalContentNews> newsWrapper = new LambdaQueryWrapper<>();
        newsWrapper.eq(PortalContentNews::getOaId,newsDto.getOaId());
        PortalContentNews news = portalContentNewsMapper.selectOne(newsWrapper);
        if (ObjectUtils.isEmpty(news)) {
            throw new IncloudException("通过oaId未找到相关新闻/通告信息，oaId:"+newsDto.getOaId());
        }
        news.setPartCode(newsDto.getPartCode());
        news.setTitle(newsDto.getTitle());
        news.setContentUrl(newsDto.getContentUrl());
        news.setPlateName(newsDto.getPlateName());
        news.setOaNewsRangeType(newsDto.getOaNewsRangeType());
        news.setOrgRange(newsDto.getOrgRange());
        news.setDeptRange(newsDto.getDeptRange());
        news.setUserRange(newsDto.getUserRange());
        news.setUpdateTime(newsDto.getUpdateTime());
        portalContentNewsMapper.updateById(news);
        return true;
    }

    @Override
    public Boolean deleteNews(String oaId, String partCode) {
        if (StringUtils.isEmpty(oaId)) {
            throw new IncloudException("oaId不能为空");
        }
        if (StringUtils.isBlank(partCode)) {
            throw new IncloudException("请传partCode参数");
        }
        LambdaQueryWrapper<PortalContentNews> newsWrapper = new LambdaQueryWrapper<>();
        newsWrapper.eq(PortalContentNews::getPartCode,partCode);
        newsWrapper.eq(PortalContentNews::getOaId,oaId);
        int delete = portalContentNewsMapper.delete(newsWrapper);
        if (delete == 1) {
            return true;
        }
        if (delete > 1) {
            throw new IncloudException("查找到多条记录");
        }
        return false;
    }

    @Override
    public void updateCreateUserMsg() {
        List<Long> repeatCreateUserIds = portalContentNewsMapper.getRepeatCreateUserId();
        for (Long createUserId : repeatCreateUserIds) {
            if (0 != createUserId) {
                MdmUserVo mdmUserVo = getUserById(createUserId);
                if (ObjectUtils.isNotEmpty(mdmUserVo)) {
                    //修改内容
                    PortalContentNews news = new PortalContentNews();
                    news.setCreateUserOrgFullId(mdmUserVo.getOrgFullId());
                    news.setCreateUserParentOrgId(mdmUserVo.getParentOrgId());
                    news.setCreateUserParentOrgName(mdmUserVo.getParentOrgName());
                    news.setCreateUserParentDeptId(mdmUserVo.getParentDeptId());
                    news.setCreateUserParentDeptName(mdmUserVo.getParentDeptName());
                    //查询条件
                    LambdaQueryWrapper<PortalContentNews> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(PortalContentNews::getCreateUserId,createUserId);

                    portalContentNewsMapper.update(news,queryWrapper);
                }
            }
        }
    }

    public void contrastTitle(Long partId,String title){
        LambdaQueryWrapper<PortalContentNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentNews::getPartId,partId);
        queryWrapper.eq(PortalContentNews::getTitle,title);
        List<PortalContentNews> portalContentNews = portalContentNewsMapper.selectList(queryWrapper);
        if (portalContentNews.size() > 1) {
            throw new IncloudException("同一个栏目下只能有一个标题名称！");
        }
    }

    public void checkNewsDto(PortalContentNewsDto newsDto, String type){
        if (StringUtils.isEmpty(newsDto.getOaId())) {
            throw new IncloudException("请传oaId");
        }
        if (StringUtils.isEmpty(newsDto.getTitle())) {
            throw new IncloudException("请传标题");
        }
        if (StringUtils.isEmpty(newsDto.getPartCode())) {
            throw new IncloudException("请传partCode");
        }
        if (StringUtils.isEmpty(newsDto.getContentUrl())) {
            throw new IncloudException("请传展示url");
        }
        if (StringUtils.isEmpty(newsDto.getPlateName())) {
            throw new IncloudException("请传所属板块");
        }
        if ("thirdPartNews".equals(newsDto.getPartCode()) && null == newsDto.getOaNewsRangeType()) {
            throw new IncloudException("请传新闻范围类型");
        }
        if ("thirdPartNews".equals(newsDto.getPartCode()) && 1 == newsDto.getOaNewsRangeType() && StringUtils.isNotBlank(newsDto.getOrgRange())) {
            throw new IncloudException("添加新闻的时候，如果oaNewsRangeType为1的时候，不用传orgRange字段");
        }
        if ("thirdPartNews".equals(newsDto.getPartCode()) && 2 == newsDto.getOaNewsRangeType() && StringUtils.isEmpty(newsDto.getOrgRange())) {
            throw new IncloudException("添加新闻的时候，如果oaNewsRangeType为2的时候，要传orgRange字段");
        }
        if ("thirdPartNotices".equals(newsDto.getPartCode())
                && StringUtils.isEmpty(newsDto.getOrgRange())
                && StringUtils.isEmpty(newsDto.getDeptRange())
                && StringUtils.isEmpty(newsDto.getUserRange())) {
            throw new IncloudException("请传发布范围");
        }
        if ("add".equals(type)) {
            if (null == newsDto.getCreateUserId()) {
                throw new IncloudException("请传创建人id");
            }
            if (StringUtils.isEmpty(newsDto.getCreateUserName())) {
                throw new IncloudException("请传创建人姓名");
            }
            if (null == newsDto.getCreateTime()) {
                throw new IncloudException("请传创建时间");
            }
        }
        if ("update".equals(type) && null == newsDto.getUpdateTime()) {
            throw new IncloudException("请传修改时间");
        }
    }

    public MdmUserVo getUserById(Long userId){
        Result<MdmUserVo> userVoResult = baseMdmClient.get(userId);
        if (ObjectUtils.isNotEmpty(userVoResult.getData())) {
            return userVoResult.getData();
        }
        return null;
    }

//    /**
//     * 流程展示
//     * @param procViewDto
//     * @return
//     */
//    @Override
//    public PortalContentNewsVo procView(ProcViewDto procViewDto) {
//        PortalContentNews portalContentNews = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
//        return dozerMapper.map(portalContentNews, PortalContentNewsVo.class);
//    }
//
//    /**
//     * 流程保存
//     * @param portalContentNewsDto
//     * @return
//     */
//    @Override
//    @Transactional
//    public Result procSave(PortalContentNewsDto portalContentNewsDto) {
//        log.info("新闻通告类内容申请procSave参数：" + portalContentNewsDto.toString());
//        if(StringUtils.isBlank(portalContentNewsDto.getCamundaProcinsId())) {
//            String maxBizKey = portalContentNewsMapper.getMaxBizKey();
//            log.info("新闻通告类内容申请，最大的maxBizKey:{}",maxBizKey);
//            WfEngineDto wfEngine = portalContentNewsDto.getWfEngine();
//            log.info("新闻通告类内容申请，启动工作流参数：{}",wfEngine);
//            BizInfoDto bizInfoDto = wfEngine.getBizInfoDto();
//            if (ObjectUtils.isEmpty(bizInfoDto)) {
//                bizInfoDto = new BizInfoDto();
//                bizInfoDto.setReason(portalContentNewsDto.getReason());
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("NEWS-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                }else {
//                    String bizKey = "NEWS-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }else {
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("NEWS-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                } else {
//                    String bizKey = "NEWS-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }
//            EngineVo engineVo = wfService.startEngine(portalContentNewsDto);
//            wfService.setWfDto(portalContentNewsDto,engineVo);
//            this.save(portalContentNewsDto);
//            return Result.success(engineVo);
//        } else {
//            //修改业务信息
//            this.update(portalContentNewsDto);
//            return Result.success();
//        }
//    }
//
//    /**
//     * 流程提交
//     * @param portalContentNewsDto
//     */
//    @Override
//    @Transactional
//    public void procBizSubmit(PortalContentNewsDto portalContentNewsDto) {
//        try {
//            log.debug("新闻通告类内容申请procSubmit参数：" + portalContentNewsDto.toString());
//            //验重title，同一个栏目下只能有一个不重复标题
//            LambdaQueryWrapper<PortalContentNews> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(PortalContentNews::getPartId,portalContentNewsDto.getPartId());
//            queryWrapper.eq(PortalContentNews::getTitle,portalContentNewsDto.getTitle());
//            List<PortalContentNews> titleSize = portalContentNewsMapper.selectList(queryWrapper);
//            if (titleSize.size() > 1) {
//                throw new IncloudException("一个栏目下只能有一个不重复标题！");
//            }
//            //根据流程 id 查询出具体的业务信息
//            PortalContentNews portalContentNews = this.queryByProcinsId(portalContentNewsDto.getCamundaProcinsId());
//            if(ObjectUtils.isEmpty(portalContentNews)){
//                throw new IncloudException("没有查询出具体的新闻通告类内容申请信息。");
//            }
//            //如果流程状态是起草，修改状态
//            if((portalContentNews.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
//                //修改状态
//                portalContentNewsDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
//            }
//            this.update(portalContentNewsDto);
//        }catch (Exception e) {
//            throw new IncloudException(e.getMessage());
//        }
//    }
//
//    /**
//     * 流程删除
//     * @param camundaProcinsId
//     * @return
//     */
//    @Override
//    @Transactional
//    public Result procDel(String camundaProcinsId) {
//        PortalContentNews portalContentNews = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentNews)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        super.removeById(portalContentNews.getId());
//        return Result.success();
//    }
//
//    /**
//     * 流程终止
//     * @param camundaProcinsId
//     * @return
//     */
//    @Override
//    @Transactional
//    public Result procStop(String camundaProcinsId) {
//        PortalContentNews portalContentNews = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentNews)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        if (portalContentNews.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
//            throw new IncloudException("流程已完成审批，不能终止！");
//        }
//        portalContentNews.setAuditStatus(AuditStateEnum.TERMINATION.getType());
//        portalContentNewsMapper.updateById(portalContentNews);
//        return Result.success();
//    }
//
//    /**
//     * 流程审批结束
//     * @param camundaProcinsId
//     * @return
//     */
//    @Override
//    @Transactional
//    public Result auditSucceed(String camundaProcinsId) {
//        PortalContentNews portalContentNews = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentNews)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        portalContentNews.setUpdateTime(LocalDateTime.now());
//        portalContentNews.setPassTime(LocalDateTime.now());
//        portalContentNews.setAuditStatus(AuditStateEnum.COMPLETE.getType());
//        portalContentNewsMapper.updateById(portalContentNews);
//        //审批完成后通知 生成静态文件
//        Map<String,Object> eventData = new HashMap<>();
//        eventData.put(EventConstants.NewsToHtmlFileHandle,portalContentNews);
//        portalPublisher.publish(eventData);
//        return Result.success();
//    }
//
//    //根据camundaProcinsId查内容
//    public PortalContentNews queryByProcinsId(String camundaProcinsId){
//        LambdaQueryWrapper<PortalContentNews> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PortalContentNews::getCamundaProcinsId,camundaProcinsId);
//        return portalContentNewsMapper.selectOne(queryWrapper);
//    }


}
