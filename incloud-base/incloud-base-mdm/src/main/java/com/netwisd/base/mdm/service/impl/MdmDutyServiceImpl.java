package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;

import com.netwisd.base.common.mdm.dto.MdmOrgStatusDto;
import com.netwisd.base.mdm.constants.EventConstants;
import com.netwisd.base.mdm.constants.OrgTypeEnum;
import com.netwisd.base.mdm.constants.SortEnum;
import com.netwisd.base.mdm.entity.MdmDuty;
import com.netwisd.base.mdm.entity.MdmOrg;

import com.netwisd.base.mdm.entity.MdmPost;
import com.netwisd.base.mdm.event.MdmEvent;
import com.netwisd.base.mdm.event.MdmPublisher;
import com.netwisd.base.mdm.mapper.MdmDutyMapper;
import com.netwisd.base.mdm.mapper.MdmOrgMapper;
import com.netwisd.base.mdm.service.MdmDutyService;
import com.netwisd.base.mdm.service.MdmDutyUserService;
import com.netwisd.common.core.data.IDto;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.JacksonUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.common.mdm.dto.MdmDutyDto;
import com.netwisd.base.common.mdm.vo.MdmDutyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Service
@Slf4j
public class MdmDutyServiceImpl extends ServiceImpl<MdmDutyMapper, MdmDuty> implements MdmDutyService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmDutyMapper mdmDutyMapper;

    @Autowired
    private MdmDutyUserService mdmDutyUserService;

    @Autowired
    private MdmOrgMapper mdmOrgMapper;

    @Autowired
    private MdmPublisher mdmPublisher;
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
     * @param mdmDutyDto
     * @return
     */
    @Override
    public Page list(MdmDutyDto mdmDutyDto) {
//        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
//        //通过职务名称模糊查询
//        queryWrapper.like(StringUtils.isNotBlank(mdmDutyDto.getDutyName()),MdmDuty::getDutyName,mdmDutyDto.getDutyName());
//        //根据职务序列查询
//        queryWrapper.eq(ObjectUtil.isNotNull(mdmDutyDto.getDutySequId()),MdmDuty::getDutySequId,mdmDutyDto.getDutySequId());
//        queryWrapper.eq(StringUtils.isNotBlank(mdmDutyDto.getDutySequName()),MdmDuty::getDutySequName,mdmDutyDto.getDutySequName());
//        queryWrapper.like(ObjectUtil.isNotNull(mdmDutyDto.getParentOrgId()),MdmDuty::getOrgFullId,mdmDutyDto.getParentOrgId());
//        Page<MdmDuty> page = mdmDutyMapper.selectPage(mdmDutyDto.getPage(),queryWrapper);
//        Page<MdmDutyVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmDutyVo.class);
//        //主岗和兼岗人数 通过查询Duty_user表计算出来并设置值，返回给前端
//        List<MdmDutyVo> mdmDutyVos = pageVo.getRecords();
//        for (MdmDutyVo mdmDutyVo : mdmDutyVos) {
//            mdmDutyVo.setMasterNumber(mdmDutyUserService.masterNumber(mdmDutyVo.getId()));
//            mdmDutyVo.setPartNumber(mdmDutyUserService.partNumber(mdmDutyVo.getId()));
//        }
        Page<MdmDutyVo> pageVo = mdmDutyMapper.getList(mdmDutyDto.getPage(), mdmDutyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        List<MdmDutyVo> records = pageVo.getRecords();
        log.debug("全部"+ JacksonUtil.toJSONString(records));
        return pageVo;
    }

    /**
     * 不分页集合查询
     * @param mdmDutyDto
     * @return
     */
    @Override
    public  List<MdmDutyVo> lists(MdmDutyDto mdmDutyDto) {
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(mdmDutyDto.getDutyLowParentId()),MdmDuty::getDutyLowParentId,mdmDutyDto.getDutyLowParentId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmDutyDto.getDutyLowParentName()),MdmDuty::getDutyLowParentName,mdmDutyDto.getDutyLowParentName());
        queryWrapper.eq(ObjectUtil.isNotNull(mdmDutyDto.getDutyUpParentId()),MdmDuty::getDutyUpParentId,mdmDutyDto.getDutyUpParentId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmDutyDto.getDutyUpParentName()),MdmDuty::getDutyUpParentName,mdmDutyDto.getDutyUpParentName());
        queryWrapper.eq(ObjectUtil.isNotNull(mdmDutyDto.getParentDeptId()),MdmDuty::getParentDeptId,mdmDutyDto.getParentDeptId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmDutyDto.getParentDeptName()),MdmDuty::getParentDeptName,mdmDutyDto.getParentDeptName());
        queryWrapper.eq(ObjectUtil.isNotNull(mdmDutyDto.getDutySequId()),MdmDuty::getDutySequId,mdmDutyDto.getDutySequId());
        queryWrapper.eq(StringUtils.isNotBlank(mdmDutyDto.getDutySequName()),MdmDuty::getDutySequName,mdmDutyDto.getDutySequName());
        queryWrapper.like(ObjectUtil.isNotNull(mdmDutyDto.getParentOrgId()),MdmDuty::getOrgFullId,mdmDutyDto.getParentOrgId());
        queryWrapper.like(StringUtils.isNotBlank(mdmDutyDto.getDutyName()),MdmDuty::getDutyName,mdmDutyDto.getDutyName());
        queryWrapper.like(StringUtils.isNotBlank(mdmDutyDto.getDutyCode()),MdmDuty::getDutyCode,mdmDutyDto.getDutyCode());
        queryWrapper.between(ObjectUtil.isNotNull(mdmDutyDto.getSUpdateTime())&&ObjectUtil.isNotNull(mdmDutyDto.getEUpdateTime()), MdmDuty::getUpdateTime, mdmDutyDto.getSUpdateTime(),mdmDutyDto.getEUpdateTime());
        queryWrapper.orderByAsc(MdmDuty::getSort);
        List<MdmDuty> mdmDutys = mdmDutyMapper.selectList(queryWrapper);
        List<MdmDutyVo> mdmDutyVos = DozerUtils.mapList(dozerMapper, mdmDutys, MdmDutyVo.class);
        log.debug("查询条数:"+mdmDutyVos.size());
        return mdmDutyVos;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public MdmDutyVo get(Long id) {
        MdmDuty mdmDuty = super.getById(id);
        MdmDutyVo mdmDutyVo = null;
        if(mdmDuty !=null){
            mdmDutyVo = dozerMapper.map(mdmDuty,MdmDutyVo.class);
            //设置主岗人数和兼岗人数
            mdmDutyVo.setMasterNumber(mdmDutyUserService.masterNumber(id));
            mdmDutyVo.setPartNumber(mdmDutyUserService.partNumber(id));
        }
        log.debug("查询成功");
        return mdmDutyVo;
    }

    /**
     * 查询同级所有职务
     * @param mdmDutyDto
     * @return
     */
    @Override
    public  List<MdmDutyVo> getSameLevel(MdmDutyDto mdmDutyDto){
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(mdmDutyDto.getParentDeptId()),MdmDuty::getParentDeptId,mdmDutyDto.getParentDeptId());
        //正序
        queryWrapper.orderByAsc(MdmDuty::getSort);
        List<MdmDuty> mdmDutyList = this.list(queryWrapper);
        List<MdmDutyVo> mdmDutyListVo = null;
        if (CollectionUtil.isNotEmpty(mdmDutyList)){
            mdmDutyListVo = DozerUtils.mapList(dozerMapper, mdmDutyList, MdmDutyVo.class);
        }
        return mdmDutyListVo;
    }

    /**
     * 保存实体
     * @param mdmDutyDtos
     * @return
     */
    @Transactional
    @Override
    public Boolean save(List<MdmDutyDto> mdmDutyDtos) {
        if (CollectionUtil.isNotEmpty(mdmDutyDtos)){
            //校验新增的职务中同一部门下名称不能重复
            checkRepeat( mdmDutyDtos);
            //校验同一组织下的职务名称是否重复,并设置排序sort
            checkListRepeat(mdmDutyDtos);
            //判断该职务的上级是否是机构,取出职务对应的上级部门或机构
            Set<Long> parentIds = mdmDutyDtos.stream().map(MdmDutyDto::getParentDeptId).collect(Collectors.toSet());
            List<MdmOrg> mdmOrgs = mdmOrgMapper.selectBatchIds(parentIds);
            Map<Long, List<MdmOrg>> groupMap = mdmOrgs.stream().collect(Collectors.groupingBy(MdmOrg::getId));
            //设置父级组织全路径id和父级组织全路径名称
            //拼装数据
            for (MdmDutyDto dutyDto : mdmDutyDtos) {
                List<MdmOrg> _mdmOrg = groupMap.get(dutyDto.getParentDeptId());
                MdmOrg mdmOrg = _mdmOrg.get(0);
                //如果是机构，那么他的上级机构就是他的ParentDeptId
                if (mdmOrg.getOrgType().longValue() == OrgTypeEnum.ORG.getCode().longValue()){
                    dutyDto.setParentOrgId(dutyDto.getParentDeptId());
                    dutyDto.setParentOrgName(dutyDto.getParentDeptName());
                    dutyDto.setParentOrgFullName(dutyDto.getParentOrgFullName()+defaultJoinName+dutyDto.getParentDeptName());
                }
                if (StringUtils.isBlank(dutyDto.getParentDeptFullName())){
                    dutyDto.setParentDeptFullName(dutyDto.getParentDeptName());
                }else {
                    dutyDto.setParentDeptFullName(dutyDto.getParentDeptFullName() + defaultJoinName + dutyDto.getParentDeptName());
                }
                dutyDto.setOrgFullId(dutyDto.getOrgFullId() + defaultJoinId + dutyDto.getParentDeptId());
                dutyDto.setOrgFullName( dutyDto.getOrgFullName()+ defaultJoinName +dutyDto.getParentDeptName() );
            }
            //保存
            List<MdmDuty> mdmDutys = DozerUtils.mapList(dozerMapper, mdmDutyDtos, MdmDuty.class);
            boolean result = super.saveBatch(mdmDutys);
            if(result){
                log.debug("保存成功");
            }
            return result;
        }else {
            throw new IncloudException("要保存的数据不能为空！");
        }
    }

    /**
     * 单个新增
     * @param mdmDutyDto
     * @return
     */
    @Transactional
    @Override
    public Boolean saveOne(MdmDutyDto mdmDutyDto) {
        //拼凑成list集合，原来是批量加，现在是单个，newList可以用原来的批量代码逻辑
        List<MdmDutyDto> mdmDutyDtos = new ArrayList<>();
        mdmDutyDtos.add(mdmDutyDto);
        Boolean result = save(mdmDutyDtos);
        if(result){
            log.debug("保存成功");
        }
        return true;
    }


    /**
     * 修改实体
     * @param mdmDutyDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(MdmDutyDto mdmDutyDto) {
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDuty::getParentDeptId,mdmDutyDto.getParentDeptId());
        queryWrapper.eq(MdmDuty::getDutyName,mdmDutyDto.getDutyName());
        queryWrapper.ne(MdmDuty::getId,mdmDutyDto.getId());
        Integer count = mdmDutyMapper.selectCount(queryWrapper);
        if (count.intValue() > 0){
            throw new IncloudException("同一部门下职务名称不能重复");
        }
        mdmDutyDto.setUpdateTime(LocalDateTime.now());
        MdmDuty mdmDuty = dozerMapper.map(mdmDutyDto,MdmDuty.class);
        boolean result = super.updateById(mdmDuty);
        //名称修改时Duty_user表改变
        List<MdmDuty> list = new ArrayList<>();
        list.add(mdmDuty);
        mdmDutyUserService.updateByDuty(list);
        if(result){
            log.debug("修改成功");
        }
        //修改角色 同时修改职务人员关系表
        List<MdmDuty> mdmDutyList = new ArrayList<>();
        mdmDutyList.add(mdmDuty);
        mdmPublisher.publish(EventConstants.UpdateUserEvent,mdmDutyList);
        return result;
    }

    /**
     * 通过ID删除
     * @param
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        if(StringUtils.isNotBlank(ids)) {
            List<String> streamStr = Stream.of(ids.split(",")).collect(Collectors.toList());
            //判断该职务是否被引用，已被引用的职务不能被删除
            List<MdmDuty> mdmDutys = mdmDutyMapper.selectBatchIds(streamStr);
            for (MdmDuty mdmDuty : mdmDutys) {
                if (mdmDuty.getIsRef().intValue() == YesNo.YES.code.intValue()) {
                    throw new IncloudException(mdmDuty.getDutyName()+"职务已被引用，不能删除");
                }
            }
            boolean result = super.removeByIds(streamStr);
            //删除对应Duty_user表中对应的关系
            mdmDutyUserService.deleteByDutyId(streamStr);
            if (result) {
                log.debug("删除成功");
            }
            return result;
        }else {
            throw new IncloudException("删除职务的id不能为空！");
        }
    }

    @Override
    public List<MdmDuty> getByIds(List<String> ids) {
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmDuty::getId,ids);
        List<MdmDuty> list = mdmDutyMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 复制职务到另一个部门
     * @param mdmDutyDto
     * @return
     */
    @Transactional
    @Override
    public Boolean copyToOrg(MdmDutyDto mdmDutyDto) {
        //检测职务是否有重复的名称
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDuty::getParentDeptId,mdmDutyDto.getParentDeptId())
                .eq(MdmDuty::getDutyName,mdmDutyDto.getDutyName());
        List<MdmDuty> mdmDutys = mdmDutyMapper.selectList(queryWrapper);
        if (mdmDutys.size() > 0) {
            throw new IncloudException("该部门存在此职务名称，不能复制此职务");
        }
        mdmDutyDto.setId(null);
        mdmDutyDto.setCreateTime(LocalDateTime.now());
        mdmDutyDto.setUpdateTime(LocalDateTime.now());
        //查询这个上级是机构还是组织
        LambdaQueryWrapper<MdmOrg> query = new LambdaQueryWrapper<>();
        query.eq(ObjectUtil.isNotNull(mdmDutyDto.getParentDeptId()),MdmOrg::getId,mdmDutyDto.getParentDeptId());
        MdmOrg mdmOrg = mdmOrgMapper.selectOne(query);
        //如果这个org是机构，那这个职务的上级机构就是这个org
        if (ObjectUtil.isNotNull(mdmOrg) && mdmOrg.getOrgType().intValue() == OrgTypeEnum.ORG.getCode().intValue()){
            mdmDutyDto.setParentOrgId(mdmDutyDto.getParentDeptId());
            mdmDutyDto.setParentOrgName(mdmDutyDto.getParentDeptName());
            mdmDutyDto.setParentOrgFullName(mdmDutyDto.getParentOrgFullName()+defaultJoinName+mdmDutyDto.getParentDeptName());
        }
        if (StringUtils.isBlank(mdmDutyDto.getParentDeptFullName())){
            mdmDutyDto.setParentDeptFullName(mdmDutyDto.getParentDeptName());
        }else {
            mdmDutyDto.setParentDeptFullName(mdmDutyDto.getParentDeptFullName()+defaultJoinName+mdmDutyDto.getParentDeptName());
        }
        mdmDutyDto.setOrgFullId(mdmDutyDto.getOrgFullId()+defaultJoinId+mdmDutyDto.getParentDeptId());
        mdmDutyDto.setOrgFullName(mdmDutyDto.getOrgFullName()+defaultJoinName+mdmDutyDto.getParentDeptName());
        //复制了一个职务到其他部门，说明这个职务在其他部门是新职务，所以是不被引用的
        mdmDutyDto.setIsRef(YesNo.NO.code);
        //设置排序
        LambdaQueryWrapper<MdmDuty> _queryWrapper = new LambdaQueryWrapper<>();
        _queryWrapper.eq(MdmDuty::getParentDeptId,mdmDutyDto.getParentDeptId());
        Integer size = mdmDutyMapper.selectCount(_queryWrapper);
        size++;
        mdmDutyDto.setSort(size);

        MdmDuty mdmDuty = dozerMapper.map(mdmDutyDto, MdmDuty.class);
        boolean result = super.save(mdmDuty);
        if (result){
            log.debug("复制成功");
        }
        return result;
    }

    /**
     * 通过部门id查询已启用的职务
     * @param id
     * @return
     */
    @Override
    public List<MdmDutyVo> getDuty(Long id) {
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(MdmDuty::getOrgFullId,id);
        //启用的职务
        queryWrapper.eq(MdmDuty::getStatus,YesNo.YES.code);
        //正序
        queryWrapper.orderByAsc(MdmDuty::getSort);
        List<MdmDuty> mdmDutys = mdmDutyMapper.selectList(queryWrapper);
        List<MdmDutyVo> mdmDutyVos = DozerUtils.mapList(dozerMapper, mdmDutys, MdmDutyVo.class);
        return mdmDutyVos;
    }

    /**
     * 通过组织id查询所有的职务
     * @param
     * @return
     */
    @Override
    public List<MdmDutyVo> getAllDuty(MdmDutyDto mdmDutyDto) {
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        //机构或部门id
        queryWrapper.like(ObjectUtil.isNotNull(mdmDutyDto.getParentOrgId()),MdmDuty::getOrgFullId,mdmDutyDto.getParentOrgId());
        //职务名称模糊查询
        queryWrapper.like(StringUtils.isNotBlank(mdmDutyDto.getDutyName()),MdmDuty::getDutyName,mdmDutyDto.getDutyName());
        //启用
        queryWrapper.eq(MdmDuty::getStatus,YesNo.YES.code);
        queryWrapper.orderByAsc(MdmDuty::getSort);
        List<MdmDuty> mdmDutys = mdmDutyMapper.selectList(queryWrapper);
        List<MdmDutyVo> mdmDutyVos = null;
        if (CollectionUtil.isNotEmpty(mdmDutys)){
            mdmDutyVos = DozerUtils.mapList(dozerMapper, mdmDutys, MdmDutyVo.class);
        }
        return mdmDutyVos;
    }

    /**
     * 校验新增数据中同一部门的职务名称不能重复
     * @param mdmDutyDto
     */
    void checkRepeat(List<MdmDutyDto> mdmDutyDto){
        Map<Long, List<MdmDutyDto>> groupMap = mdmDutyDto.stream().collect(Collectors.groupingBy(MdmDutyDto::getParentDeptId));
        for (Map.Entry<Long, List<MdmDutyDto>> listEntry : groupMap.entrySet()) {
            List<MdmDutyDto> DutyDtos = listEntry.getValue();
            List<String> list = DutyDtos.stream().map(MdmDutyDto::getDutyName).collect(Collectors.toList());
            Set<String> set = DutyDtos.stream().map(MdmDutyDto::getDutyName).collect(Collectors.toSet());
            if (list.size() != set.size()){
                throw new IncloudException("同一部门下新增的职务中名称不能重复");
            }
        }
    }
    /**
     * 校验同一组织下职务名称不能重复
     * @param mdmDutyDto
     */
    void checkListRepeat(List<MdmDutyDto> mdmDutyDto){
        //拿到部门id，根据部门id去查询所属职务
        Set<Long> parentIds = mdmDutyDto.stream().map(MdmDutyDto::getParentDeptId).collect(Collectors.toSet());
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmDuty::getParentDeptId,parentIds);
        List<MdmDuty> mdmDutys = mdmDutyMapper.selectList(queryWrapper);
        //对比同一组织下是否有相同的职务名称和编号
        for (MdmDutyDto DutyDto : mdmDutyDto) {
            for (MdmDuty mdmDuty : mdmDutys) {
                if (DutyDto.getParentDeptId().longValue() == mdmDuty.getParentDeptId().longValue() && DutyDto.getDutyName().equals(mdmDuty.getDutyName())){
                    throw new IncloudException("同一部门下职务名称已存在："+DutyDto.getDutyName());
                }
            }
        }
        //设置排序,这里已经查出来相关的部门职务，减少数据库查询次数，所以在这里设置排序
        setSort(parentIds,mdmDutys,mdmDutyDto);
    }


    /**
     * 设置排序
     * @param
     */
    private void setSort(Set<Long> parentIds,List<MdmDuty> mdmDutys,List<MdmDutyDto> mdmDutyDto) {
        for (Long parentId : parentIds) {
            List<MdmDuty> list = mdmDutys.stream().filter(d -> d.getParentDeptId().longValue() == parentId.longValue()).collect(Collectors.toList());
            int size = list.size();
            for (MdmDutyDto DutyDto : mdmDutyDto) {
                if (DutyDto.getParentDeptId().longValue() == parentId.longValue()){
                    size++;
                    DutyDto.setSort(size);
                    //新增时的职务，是不被引用的，在这里设置一下
                    DutyDto.setIsRef(YesNo.NO.code);
                }
            }
        }
    }


    /**
     * 部门内职务排序
     * @param sourceId
     * @param targetId
     * @param index
     * @return
     */
    @Override
    public Boolean sortForDept(Long sourceId, Long targetId, String index) {
        MdmDuty source = this.getById(sourceId);
        if(ObjectUtil.isEmpty(source)){
            throw new IncloudException("根据传入的sourceId查询不到相应的职务信息！");
        }
        List<MdmDuty> list = new ArrayList<>();
        if(StrUtil.isNotEmpty(index)){
            if(index.equals(SortEnum.FIRST.value)){
                List<MdmDuty> otherSortList = getListByParent(source.getParentDeptId(), true, source.getId());
                source.setSort(defaultSort);
                list = sortOtherList(otherSortList,defaultSort);
            }else if(index.equals(SortEnum.LAST.value)){
                MdmDuty lastOne = getOneByParent(source.getParentDeptId(), false, null);
                source.setSort(lastOne.getSort() + 1);
            }
        }else {
            MdmDuty target = this.getById(targetId);
            if(ObjectUtil.isEmpty(target)){
                throw new IncloudException("根据传入的targetId查询不到相应的实体！");
            }
            Integer targetIndex = target.getSort();
            List<MdmDuty> otherSortList = getListByParent(source.getParentDeptId(), true, source.getId());
            List<MdmDuty> collect = otherSortList.stream().filter(mdmDuty -> mdmDuty.getSort() >= targetIndex).collect(Collectors.toList());
            list = sortOtherList(collect,targetIndex);
            source.setSort(targetIndex);
        }
        //把自己也加进去；
        MdmDuty mdmDuty = dozerMapper.map(source,MdmDuty.class);
        list.add(mdmDuty);
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
    public List<MdmDuty> getListByParent(Long parentId,Boolean isAsc,Long exclusiveId) {
        LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmDuty::getParentDeptId,parentId);
        if(ObjectUtil.isNotEmpty(exclusiveId)){
            queryWrapper.ne(MdmDuty::getId,exclusiveId);
        }
        if(isAsc){
            queryWrapper.orderByAsc(MdmDuty::getSort);
        }else {
            queryWrapper.orderByDesc(MdmDuty::getSort);
        }
        List<MdmDuty> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 按给定集合和排序index做排序
     * @param otherSortList
     * @param index
     * @return
     */
    List<MdmDuty> sortOtherList(List<MdmDuty> otherSortList,Integer index){
        List<MdmDuty> list = new ArrayList<>();
        for (MdmDuty mdmDuty : otherSortList){
            mdmDuty.setSort(index + 1);
            list.add(mdmDuty);
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
    public MdmDuty getOneByParent(Long parentId,Boolean isAsc,Long exclusiveId) {
        List<MdmDuty> list = getListByParent(parentId, isAsc, exclusiveId);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            Optional<MdmDuty> first = list.stream().findFirst();
            MdmDuty mdmDuty = first != null ? first.get() : null;
            return mdmDuty;
        }
        return null;
    }

    /**
     * 将此职务设置为已被引用
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean isRef(Long id){
        MdmDuty mdmDuty = this.getById(id);
        if (ObjectUtil.isEmpty(mdmDuty)){
            throw new IncloudException("没有对应的职务信息");
        }
        mdmDuty.setIsRef(YesNo.YES.code);
        boolean result = this.updateById(mdmDuty);
        return result;
    }



    @Override
    @Transactional
    public Boolean orgUpdate(List<MdmOrg> mdmOrgList) {
        if(CollectionUtil.isNotEmpty(mdmOrgList)) {
//            //分组获取 只要部门信息
//            List<MdmOrg> deptOrgList = mdmOrgList.stream().filter(d -> d.getOrgType() == OrgTypeEnum.DEPT.code).collect(Collectors.toList());
            //查询部门下所有职务
//            if(CollectionUtil.isNotEmpty(deptOrgList)) {
            //拿到组织的id
            List<Long> deptIds = mdmOrgList.stream().map(MdmOrg::getId).collect(Collectors.toList());
            LambdaQueryWrapper<MdmDuty> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmDuty::getParentDeptId, deptIds);
            List<MdmDuty> mdmDutyList = mdmDutyMapper.selectList(queryWrapper);
            if (CollectionUtil.isNotEmpty(mdmDutyList)){
                Map<Long,List<MdmOrg>> mapGroup = mdmOrgList.stream().collect(Collectors.groupingBy(MdmOrg::getId));
                for (MdmDuty mdmDuty : mdmDutyList) {
                    List<MdmOrg> deptList = mapGroup.get(mdmDuty.getParentDeptId());
                    MdmOrg mdmOrg = deptList.get(0);
                    //组织修改名称时会用到
                    mdmDuty.setParentDeptName(mdmOrg.getOrgName());//对应它的上级id是不会变的，所以就不用再改了
                    //如果该职务的上级是部门，那么它修改的时候，它的上级机构是可能发生变化的，此时需要修改该职务的上级机构
                    //如果该职务的上级是机构，对于职务来讲，它的职务机构还是原来的值不用修改
                    if (mdmOrg.getOrgType().longValue() == OrgTypeEnum.DEPT.getCode().longValue()){
                        mdmDuty.setParentOrgId(mdmOrg.getParentOrgId());
                        mdmDuty.setParentOrgName(mdmOrg.getParentOrgName());
                        mdmDuty.setParentOrgFullName(mdmOrg.getParentOrgFullName());
                    }else {
                        mdmDuty.setParentOrgFullName(mdmOrg.getParentOrgFullName()+defaultJoinName+mdmOrg.getOrgName());
                    }
                    if (StringUtils.isBlank(mdmOrg.getParentDeptFullName() )){
                        mdmDuty.setParentDeptFullName(mdmOrg.getOrgName());
                    }else {
                        mdmDuty.setParentDeptFullName(mdmOrg.getParentDeptFullName() + defaultJoinName+ mdmOrg.getOrgName());
                    }
                    mdmDuty.setOrgFullId( mdmOrg.getOrgFullId()+defaultJoinName +mdmOrg.getId() );
                    mdmDuty.setOrgFullName( mdmOrg.getOrgFullName()+defaultJoinName +mdmOrg.getOrgName() );
                }
                super.updateBatchById(mdmDutyList);
                //修改Duty_user表中职务的orgFullDutyId和orgFullDutyName
                mdmDutyUserService.updateByDuty(mdmDutyList);
            }
//            }
        }
        return true;
    }
    @Override
    @Transactional
    public Boolean dutyUpdate(List<MdmDutyDto> mdmDutyDtos) {
        if (CollectionUtil.isNotEmpty(mdmDutyDtos)){
            //拿到部门id
            List<Long> deptIds = mdmDutyDtos.stream().map(MdmDutyDto::getParentDeptId).collect(Collectors.toList());
            LambdaQueryWrapper<MdmOrg> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(MdmOrg::getId, deptIds);
            List<MdmOrg> mdmDutyList = mdmOrgMapper.selectList(queryWrapper);
            Map<Long,List<MdmOrg>> mapGroup = mdmDutyList.stream().collect(Collectors.groupingBy(MdmOrg::getId));
            for (MdmDutyDto mdmDutyDto : mdmDutyDtos) {
                List<MdmOrg> mdmOrgs = mapGroup.get(mdmDutyDto.getParentDeptId());
                MdmOrg mdmOrg = mdmOrgs.get(0);
                //如果上级是机构，那么职务的机构字段为上级机构的id本身
                if (mdmOrg.getOrgType().longValue() == OrgTypeEnum.ORG.getCode().longValue()){
                    mdmDutyDto.setParentOrgId(mdmDutyDto.getParentDeptId());
                    mdmDutyDto.setParentOrgName(mdmDutyDto.getParentDeptName());
                    mdmDutyDto.setParentOrgFullName(mdmDutyDto.getParentOrgFullName()+defaultJoinName+mdmDutyDto.getParentDeptName());
                }
                if (StringUtils.isBlank(mdmDutyDto.getParentDeptFullName())){
                    mdmDutyDto.setParentDeptFullName(mdmDutyDto.getParentDeptName());
                }else {
                    mdmDutyDto.setParentDeptFullName(mdmDutyDto.getParentDeptFullName()+defaultJoinName+mdmDutyDto.getParentDeptName());
                }
                mdmDutyDto.setOrgFullId(mdmDutyDto.getOrgFullId()+defaultJoinId+mdmDutyDto.getParentDeptId());
                mdmDutyDto.setOrgFullName(mdmDutyDto.getOrgFullName()+defaultJoinName+mdmDutyDto.getParentDeptName());
            }
            List<MdmDuty> mdmDutys = DozerUtils.mapList(dozerMapper, mdmDutyDtos, MdmDuty.class);
            super.updateBatchById(mdmDutys);
            //修改Duty_user表
            mdmDutyUserService.updateByDuty(mdmDutys);
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
            List<MdmDutyDto> mdmDutyDtos = mdmOrgStatusDto.getMdmDutyDtos();
            this.dutyUpdate(mdmDutyDtos);
        }
        //组织撤销
        if(dtoData.containsKey(EventConstants.CanceledOrgEvent)){
            MdmOrgStatusDto mdmOrgStatusDto = (MdmOrgStatusDto) dtoData.get(EventConstants.CanceledOrgEvent);
            List<MdmDutyDto> mdmDutyDtos = mdmOrgStatusDto.getMdmDutyDtos();
            this.dutyUpdate(mdmDutyDtos);
        }
    }
}
