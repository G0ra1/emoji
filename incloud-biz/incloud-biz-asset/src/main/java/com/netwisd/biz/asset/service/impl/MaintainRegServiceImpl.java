package com.netwisd.biz.asset.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.biz.asset.dto.MaintainRegDetailDto;
import com.netwisd.biz.asset.dto.MaintainRegDto;
import com.netwisd.biz.asset.entity.MaintainReg;
import com.netwisd.biz.asset.entity.MaintainRegDetail;
import com.netwisd.biz.asset.fegin.DictClient;
import com.netwisd.biz.asset.mapper.MaintainRegMapper;
import com.netwisd.biz.asset.service.MaintainRegDetailService;
import com.netwisd.biz.asset.service.MaintainRegService;
import com.netwisd.biz.asset.vo.MaintainRegDetailVo;
import com.netwisd.biz.asset.vo.MaintainRegVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 维修登记 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-28 17:12:23
 */
@Service
@Slf4j
public class MaintainRegServiceImpl extends BatchServiceImpl<MaintainRegMapper, MaintainReg> implements MaintainRegService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaintainRegMapper maintainRegMapper;

    @Autowired
    private MaintainRegDetailService maintainRegDetailService;

    @Autowired
    private DictClient dictClient;
    /**
    * 单表简单查询操作
    * @param maintainRegDto
    * @return
    */
    @Override
    public Page list(MaintainRegDto maintainRegDto) {
        LambdaQueryWrapper<MaintainReg> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        Page<MaintainReg> page = maintainRegMapper.selectPage(maintainRegDto.getPage(),queryWrapper);
        Page<MaintainRegVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaintainRegVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param maintainRegDto
    * @return
    */
    @Override
    public Page lists(MaintainRegDto maintainRegDto) {
        Page<MaintainRegVo> pageVo = maintainRegMapper.getPageList(maintainRegDto.getPage(),maintainRegDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaintainRegVo get(Long id) {
        MaintainReg maintainReg = super.getById(id);
        MaintainRegVo maintainRegVo = null;
        if(maintainReg !=null){
            maintainRegVo = dozerMapper.map(maintainReg,MaintainRegVo.class);
            //根据id获取maintainRegDetailVoList
            List<MaintainRegDetailVo> maintainRegDetailVoList = maintainRegDetailService.getByFkIdVo(id);
            //设置maintainRegVo，以便构建其对应的子表数据
            maintainRegVo.setMaintainRegDetailList(maintainRegDetailVoList);
        }
        return maintainRegVo;
    }

    /**
    * 保存实体
    * @param maintainRegDto
    * @return
    */
    @Transactional
    @Override
    public void save(MaintainRegDto maintainRegDto) {
        if(StringUtils.isBlank(maintainRegDto.getCode())){
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap entityMap = objectMapper.convertValue(maintainRegDto, HashMap.class);
            String code = dictClient.getRuleValue("maintain_reg","code",entityMap);
            log.debug("规则值：{}",code);
            if (StringUtils.isNotBlank(code)){
                maintainRegDto.setCode(code);
            }
        }
        MaintainReg maintainReg = dozerMapper.map(maintainRegDto,MaintainReg.class);
        super.save(maintainReg);
        saveSubList(maintainRegDto);
    }


    /**
     * 保存集合
     * @param maintainRegDtos
     * @return
     */
    @Transactional
    @Override
    public void saveList(List<MaintainRegDto> maintainRegDtos){
        List<MaintainReg> MaintainRegs = DozerUtils.mapList(dozerMapper, maintainRegDtos, MaintainReg.class);
        super.saveBatch(MaintainRegs);
        for (MaintainRegDto maintainRegDto : maintainRegDtos){
            saveSubList(maintainRegDto);
        }
    }

    /**
     * 保存子表集合
     * @param maintainRegDto
     * @return
     */
    @Transactional
    void saveSubList(MaintainRegDto maintainRegDto){
        //获取其子表集合
        List<MaintainRegDetailDto> maintainRegDetailDtoList = maintainRegDto.getMaintainRegDetailList();
        if(maintainRegDetailDtoList != null && !maintainRegDetailDtoList.isEmpty()){
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(maintainRegDto,maintainRegDetailDtoList);
            maintainRegDetailDtoList.forEach(maintainRegDetailDto -> {
                maintainRegDetailDto.setMaintainRegId(maintainRegDto.getId());
            });
            //调用保存子表的集合方法
            maintainRegDetailService.saveList(maintainRegDetailDtoList);
        }
    }

    /**
     * 修改实体
     * @param maintainRegDto
     * @return
     */
    @Transactional
    @Override
    public void update(MaintainRegDto maintainRegDto) {
        maintainRegDto.setUpdateTime(LocalDateTime.now());
        MaintainReg maintainReg = dozerMapper.map(maintainRegDto,MaintainReg.class);
        super.updateById(maintainReg);
        updateSub(maintainRegDto);
    }

    /**
    * 修改子类实体
    * @param maintainRegDto
    * @return
    */
    @Transactional
    @Override
    public void updateSub(MaintainRegDto maintainRegDto) {
        List<MaintainRegDetailDto> maintainRegDetailDtoList = maintainRegDto.getMaintainRegDetailList();
        if(maintainRegDetailDtoList != null && !maintainRegDetailDtoList.isEmpty()){
            LambdaQueryWrapper<MaintainRegDetail> maintainRegDetailListQueryWrapper = new LambdaQueryWrapper<>();
            maintainRegDetailListQueryWrapper.eq(ObjectUtil.isNotEmpty(maintainRegDto.getId()),MaintainRegDetail::getMaintainRegId,maintainRegDto.getId());
            //根据主实体DTO映射其子表的关联键为其赋值
            //EntityListConvert.convert(maintainRegDto,maintainRegDetailDtoList);
            maintainRegDetailDtoList.forEach(maintainRegDetailDto -> {
                maintainRegDetailDto.setMaintainRegId(maintainRegDto.getId());
            });
            List<MaintainRegDetail> maintainRegDetails = DozerUtils.mapList(dozerMapper, maintainRegDetailDtoList, MaintainRegDetail.class);
            //调用更新方法
            maintainRegDetailService.saveOrUpdateOrDeleteBatch(maintainRegDetailListQueryWrapper,maintainRegDetails,maintainRegDetails.size());
        }
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public void delete(Long id) {
        super.removeById(id);
        maintainRegDetailService.deleteByFkId(id);
    }

    /**
     * 通过外建删除
     * @param id
     * @return
     */
    @Override
    public void deleteByFkId(Long id){
    }

    /**
     * 通过外建获取
     * @param id
     * @return
     */
    @Override
    public List<MaintainRegVo> getByFkIdVo(Long id){
        return null;
    }

    /**
     * 通过流程实例id查询
     * @param procInstId procInstId
     * @return Result
     */
    @Override
    public MaintainRegVo getByProc(String procInstId) {
        LambdaQueryWrapper<MaintainReg> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(MaintainReg::getCamundaProcinsId,procInstId);
        MaintainReg maintainReg = maintainRegMapper.selectOne(lambdaQueryWrapper);
        MaintainRegVo maintainRegVo = null;
        if (maintainReg != null){
            maintainRegVo = dozerMapper.map(maintainReg,MaintainRegVo.class);
            //根据id获取子表
            List<MaintainRegDetailVo> maintainRegDetailVoList = maintainRegDetailService.getByFkIdVo(maintainRegVo.getId());
            //设置maintainRegVo,以便构建其对应的子表数据
            maintainRegVo.setMaintainRegDetailList(maintainRegDetailVoList);
        }
        return maintainRegVo;
    }
}
