package com.netwisd.biz.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.asset.dto.MaterialDeliveryResultDto;
import com.netwisd.biz.asset.entity.MaterialDeliveryResult;
import com.netwisd.biz.asset.mapper.MaterialDeliveryResultMapper;
import com.netwisd.biz.asset.service.MaterialDeliveryResultService;
import com.netwisd.biz.asset.vo.MaterialDeliveryResultVo;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 资产出库详情表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:56
 */
@Service
@Slf4j
public class MaterialDeliveryResultServiceImpl extends ServiceImpl<MaterialDeliveryResultMapper, MaterialDeliveryResult> implements MaterialDeliveryResultService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MaterialDeliveryResultMapper materialDeliveryResultMapper;

    /**
    * 单表简单查询操作
    * @param materialDeliveryResultDto
    * @return
    */
    @Override
    public Page list(MaterialDeliveryResultDto materialDeliveryResultDto) {
        LambdaQueryWrapper<MaterialDeliveryResult> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MaterialDeliveryResult> page = materialDeliveryResultMapper.selectPage(materialDeliveryResultDto.getPage(),queryWrapper);
        Page<MaterialDeliveryResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDeliveryResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param materialDeliveryResultDto
    * @return
    */
    @Override
    public Page lists(MaterialDeliveryResultDto materialDeliveryResultDto) {
        Page<MaterialDeliveryResultVo> pageVo = materialDeliveryResultMapper.getPageList(materialDeliveryResultDto.getPage(),materialDeliveryResultDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MaterialDeliveryResultVo get(Long id) {
        MaterialDeliveryResult materialDeliveryResult = super.getById(id);
        MaterialDeliveryResultVo materialDeliveryResultVo = null;
        if(materialDeliveryResult !=null){
            materialDeliveryResultVo = dozerMapper.map(materialDeliveryResult,MaterialDeliveryResultVo.class);
        }
        log.debug("查询成功");
        return materialDeliveryResultVo;
    }

    /**
    * 保存实体
    * @param materialDeliveryResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MaterialDeliveryResultDto materialDeliveryResultDto) {
        MaterialDeliveryResult materialDeliveryResult = dozerMapper.map(materialDeliveryResultDto,MaterialDeliveryResult.class);
        boolean result = super.save(materialDeliveryResult);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
     * 保存集合
     * @param materialDeliveryResultDtos
     * @return
     */
    @Override
    public Boolean saveList(List<MaterialDeliveryResultDto> materialDeliveryResultDtos) {
        List<MaterialDeliveryResult> maintDeliveryResults = DozerUtils.mapList(dozerMapper, materialDeliveryResultDtos, MaterialDeliveryResult.class);
        return super.saveBatch(maintDeliveryResults);
    }

    /**
    * 修改实体
    * @param materialDeliveryResultDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MaterialDeliveryResultDto materialDeliveryResultDto) {
        materialDeliveryResultDto.setUpdateTime(LocalDateTime.now());
        MaterialDeliveryResult materialDeliveryResult = dozerMapper.map(materialDeliveryResultDto,MaterialDeliveryResult.class);
        boolean result = super.updateById(materialDeliveryResult);
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
     * 获取可以退库的 出库详情数据
     * @param materialDeliveryResultDto
     * @return
     *
     * 待补充条件：人员、机构、部门等查询
     */
    @Override
    public Page<MaterialDeliveryResultVo> getDeliveryReuslt(MaterialDeliveryResultDto materialDeliveryResultDto) {
        LambdaQueryWrapper<MaterialDeliveryResult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(MaterialDeliveryResult::getNotWithdrawalNumber,0);
        Page<MaterialDeliveryResult> page = materialDeliveryResultMapper.selectPage(materialDeliveryResultDto.getPage(),queryWrapper);
        Page<MaterialDeliveryResultVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MaterialDeliveryResultVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }
}
