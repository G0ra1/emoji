package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.wf.dto.WfExpreParamDto;
import com.netwisd.base.wf.entity.WfExpre;
import com.netwisd.base.wf.entity.WfExpreParam;
import com.netwisd.base.wf.feign.UserClient;
import com.netwisd.base.wf.mapper.WfExpreMapper;
import com.netwisd.base.wf.mapper.WfExpreParamMapper;
import com.netwisd.base.wf.service.procdef.WfExpreParamService;
import com.netwisd.base.wf.service.procdef.WfExpreService;
import com.netwisd.base.wf.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfExpreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 表达式维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:22:57
 */
@Service
@Slf4j
public class WfExpreServiceImpl extends ServiceImpl<WfExpreMapper, WfExpre> implements WfExpreService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfExpreMapper wfExpreMapper;

    @Autowired
    WfExpreParamService wfExpreParamService;

    @Autowired
    WfExpreParamMapper wfExpreParamMapper;
    @Autowired
    UserClient userClient;


    /**
    * 单表简单查询操作
    * @param wfExpreDto
    * @return
    */
    @Override
    public Page list(WfExpreDto wfExpreDto) {
        LambdaQueryWrapper<WfExpre> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(wfExpreDto.getExpreReturnType()),WfExpre::getExpreReturnType,wfExpreDto.getExpreReturnType())
                    .like(StrUtil.isNotEmpty(wfExpreDto.getExpreName()),WfExpre::getExpreName,wfExpreDto.getExpreName())
                    .like(StrUtil.isNotEmpty(wfExpreDto.getExpreValue()),WfExpre::getExpreValue,wfExpreDto.getExpreValue());
        Page<WfExpre> page = wfExpreMapper.selectPage(wfExpreDto.getPage(),queryWrapper);
        Page<WfExpreVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfExpreVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfExpreDto
    * @return
    */
    @Override
    public Page lists(WfExpreDto wfExpreDto) {
        Page<WfExpreVo> pageVo = wfExpreMapper.getPageList(wfExpreDto.getPage(),wfExpreDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfExpreVo get(Long id) {
        WfExpre wfExpre = super.getById(id);
        WfExpreVo wfExpreVo = null;
        if(wfExpre !=null){
            wfExpreVo = dozerMapper.map(wfExpre,WfExpreVo.class);
            LambdaQueryWrapper<WfExpreParam> queryEventParamWrapper = new LambdaQueryWrapper<>();
            queryEventParamWrapper.eq(ObjectUtil.isNotEmpty(wfExpreVo.getId()), WfExpreParam::getExpreId, wfExpreVo.getId());
            queryEventParamWrapper.orderByAsc(WfExpreParam::getSequenceNum);
            List<WfExpreParam> wfExpreParamList = wfExpreParamMapper.selectList(queryEventParamWrapper);
            List<WfExpreParamVo> wfExpreParamVoList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(wfExpreParamList)) {
                for (WfExpreParam wfExpreParam : wfExpreParamList) {
                    WfExpreParamVo wfExpreParamVo = dozerMapper.map(wfExpreParam,WfExpreParamVo.class);
                    wfExpreParamVoList.add(wfExpreParamVo);
                }
                wfExpreVo.setExpreParamList(wfExpreParamVoList);
            }
        }
        log.debug("查询成功");
        return wfExpreVo;
    }

    /**
     * 通过ID查询实体
     * @param id
     * @return
     */
    @Override
    public WfExpresVo getWfExpresVo(Long id) {
        WfExpre wfExpre = super.getById(id);
        WfExpreVo wfExpreVo = dozerMapper.map(wfExpre,WfExpreVo.class);
        List<WfExpreParam> byExpreId = wfExpreParamService.getByExpreId(id);
        List<WfExpreParamVo> list = new ArrayList<>();
        dozerMapper.map(byExpreId,list);
        WfExpresVo wfExpresVo = new WfExpresVo();
        wfExpresVo.setWfExpreVo(wfExpreVo);
        wfExpresVo.setList(list);
        log.debug("查询成功");
        return wfExpresVo;
    }

    /**
    * 保存实体
    * @param wfExpreDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfExpreDto wfExpreDto) {
        boolean result = false;
        if(checkRepeat(wfExpreDto)){
            WfExpre wfExpre = dozerMapper.map(wfExpreDto,WfExpre.class);
            result = super.save(wfExpre);
            Long id = wfExpre.getId();
            if(result){
                log.debug("保存成功");
                List<WfExpreParamDto> listDto = wfExpreDto.getExpreParamList();
                List<WfExpreParam> list = new ArrayList();
                for(WfExpreParamDto wfExpreParamDto: listDto){
                    WfExpreParam wfExpreParam = dozerMapper.map(wfExpreParamDto,WfExpreParam.class);

                    wfExpreParam.setExpreId(id);
                    list.add(wfExpreParam);
                }
                result = wfExpreParamService.saveBatch(list);
            }
        }
        return result;
    }

    private Boolean checkRepeat(WfExpreDto wfExpreDto){
        LambdaQueryWrapper<WfExpre> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfExpre::getExpreName,wfExpreDto.getExpreName())
                .eq(WfExpre::getExpreValue,wfExpreDto.getExpreValue());
        Integer count = wfExpreMapper.selectCount(queryWrapper);
        if(count != 0){
            log.debug("已有相同表达式！");
            return false;
        }

        List<WfExpreParamDto> expreParamList = wfExpreDto.getExpreParamList();
        List<String> collect = expreParamList.stream().map(WfExpreParamDto::getParamId).collect(Collectors.toList());
        long num = collect.stream().distinct().count();
        if (collect.size() != num) {
            throw new IncloudException("参数中不能有重复的参数id！");
        }
        return true;
    }

    /**
    * 修改实体
    * @param wfExpreDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfExpreDto wfExpreDto) {
        boolean result = false;
        WfExpre wfExpre = dozerMapper.map(wfExpreDto,WfExpre.class);
        wfExpre.setUpdateTime(LocalDateTime.now());
        result = super.updateById(wfExpre);
        Long id = wfExpre.getId();
        if(result){
            log.debug("保存成功");
            List<WfExpreParamDto> listDto = wfExpreDto.getExpreParamList();
            List<WfExpreParam> list = new ArrayList();
            //有可能有新增的，所以再遍历一次，而不去做直接copy，另外，先删除后新增的方式实测并不比批量update快；而且会造成主键变更问题
            for(WfExpreParamDto wfExpreParamDto: listDto){
                WfExpreParam wfExpreParam = dozerMapper.map(wfExpreParamDto,WfExpreParam.class);
                wfExpreParam.setExpreId(id);
                wfExpreParam.setUpdateTime(LocalDateTime.now());
                list.add(wfExpreParam);
            }
            LambdaQueryWrapper<WfExpreParam> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WfExpreParam::getExpreId,wfExpre.getId());
            result = wfExpreParamService.saveOrUpdateOrDeleteBatch(queryWrapper,list,list.size());
            if(result){
                log.debug("wfExpreParam 批量修改成功");
            }
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param ids
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        if(StringUtils.isNotBlank(ids)) {
            List<String> streamStr = Stream.of(ids.split(",")).collect(Collectors.toList());
            boolean result = super.removeByIds(streamStr);
            if(result){
                log.debug("删除成功");
                streamStr.forEach(d->{
                    //删除参数列表
                    wfExpreParamService.removeByExpreId(Long.valueOf(d));
                });
            }
        } else  {
            throw new IncloudException("ID不能为空！");
        }
        return true;
    }

    @Override
    public List<WfExpreVo> selectBatchIds(List<Long> ids) {
        log.debug("按照多个按钮id查询 参数：" + ids.toString());
        List<WfExpreVo> wfExpreVoList = new ArrayList<>();
        List<WfExpre> wfExpreList = wfExpreMapper.selectBatchIds(ids);
        if(CollectionUtil.isNotEmpty(wfExpreVoList)) {
            for (WfExpre wfExpre : wfExpreList) {
                WfExpreVo wfExpreVo = dozerMapper.map(wfExpre,WfExpreVo.class);
                LambdaQueryWrapper<WfExpreParam> queryEventParamWrapper = new LambdaQueryWrapper<>();
                queryEventParamWrapper.eq(ObjectUtil.isNotEmpty(wfExpreVo.getId()), WfExpreParam::getExpreId, wfExpreVo.getId());
                List<WfExpreParam> wfExpreParamList = wfExpreParamMapper.selectList(queryEventParamWrapper);
                List<WfExpreParamVo> wfExpreParamVoList = new ArrayList<>();
                if(CollectionUtil.isNotEmpty(wfExpreParamList)) {
                    for (WfExpreParam wfExpreParam : wfExpreParamList) {
                        WfExpreParamVo wfExpreParamVo = dozerMapper.map(wfExpreParam,WfExpreParamVo.class);
                        wfExpreParamVoList.add(wfExpreParamVo);
                    }
                    wfExpreVo.setExpreParamList(wfExpreParamVoList);
                }
                wfExpreVoList.add(wfExpreVo);
            }
        }
        log.debug("按照多个按钮id查询 结果：" + wfExpreVoList.toString());
        return wfExpreVoList;
    }

//    @Override
//    public Result getExpressionList() {
//        Result<List<ExpressionVO>> result = userClient.getExpressionList("user-expression-controller");
//        if(result.getCode() == 200) {
//            List<ExpressionVO> expressionVOList = result.getData();
//            if(CollectionUtil.isNotEmpty(expressionVOList)) {
//                for (ExpressionVO expressionVO : expressionVOList) {
//                    WfExpreDto wfExpreDto = new WfExpreDto();
//                    wfExpreDto.setExpreName(expressionVO.getExpressionName());
//                    wfExpreDto.setExpreReturnType(expressionVO.getResultType());
//                    wfExpreDto.setExpreType(ExpressionTypeEnum.INNER.getType());
//                    wfExpreDto.setExpreValue(expressionVO.getExpressionVal());
//                    wfExpreDto.setProcdefTypeId(0L);
//                    wfExpreDto.setProcdefTypeName("所有分类");
//                    List<WfExpreParamDto> expreParamList = new ArrayList<>();
//                    List<ExpressionParameterVO> paramterList = expressionVO.getParamterList();
//                    if(CollectionUtil.isNotEmpty(paramterList)) {
//                        for (int i = 0; i < paramterList.size(); i++) {
//                            WfExpreParamDto wfExpreParamDto = new WfExpreParamDto();
//                            wfExpreParamDto.setParamDesc(paramterList.get(i).getParamDesc());
//                            wfExpreParamDto.setParamId(paramterList.get(i).getParamId());
//                            wfExpreParamDto.setParamName(paramterList.get(i).getParamName());
//                            wfExpreParamDto.setParamType(paramterList.get(i).getParamType());
//                            wfExpreParamDto.setParamVarType(paramterList.get(i).getParamVarType());
//                            wfExpreParamDto.setSequenceNum(i);
//                            expreParamList.add(wfExpreParamDto);
//                        }
//                    }
//                    wfExpreDto.setExpreParamList(expreParamList);
//                    //同步保存接口
//                    this.save(wfExpreDto);
//                }
//            }
//        } else {
//            throw new IncloudException(result.getMsg());
//        }
//        return Result.success();
//    }
}
