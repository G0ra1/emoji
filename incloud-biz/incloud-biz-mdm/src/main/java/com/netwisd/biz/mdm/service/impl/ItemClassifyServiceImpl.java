package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwisd.biz.mdm.constants.ItemConstants;
import com.netwisd.biz.mdm.constants.MdmConstants;
import com.netwisd.biz.mdm.constants.State;
import com.netwisd.biz.mdm.constants.YesNo;
import com.netwisd.biz.mdm.dto.ItemSkuDto;
import com.netwisd.biz.mdm.dto.ItemTransmit;
import com.netwisd.biz.mdm.dto.ItemTransmitDetailDto;
import com.netwisd.biz.mdm.entity.Item;
import com.netwisd.biz.mdm.entity.ItemClassify;
import com.netwisd.biz.mdm.entity.ItemSku;
import com.netwisd.biz.mdm.fegin.DictClient;
import com.netwisd.biz.mdm.mapper.ItemClassifyMapper;
import com.netwisd.biz.mdm.service.ItemClassifyService;
import com.netwisd.biz.mdm.service.ItemSkuService;
import com.netwisd.biz.mdm.service.ItemService;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.biz.mdm.vo.ItemClassifyAllVo;
import com.netwisd.biz.mdm.vo.ItemSkuVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.ItemClassifyDto;
import com.netwisd.biz.mdm.vo.ItemClassifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:14:41
 */
@Service
@Slf4j
public class ItemClassifyServiceImpl extends ServiceImpl<ItemClassifyMapper, ItemClassify> implements ItemClassifyService {
    @Autowired
    private Mapper dozerMapper;

    @Value("${spring.rocketmq.itemClassifyTopics}")
    private String itemClassifyTopics;

    @Autowired
    private ItemClassifyMapper itemClassifyMapper;

    @Autowired
    @Lazy
    private ItemService itemService;

    @Autowired
    MdmMqService mdmMqService;

    @Autowired
    private ItemSkuService itemSkuService;

    @Autowired
    private DictClient dictClient;

    /**
     * 默认root信息
     */
    private static String defaultRootId = "";

    /**
    * 单表简单查询操作
    * @param itemClassifyDto
    * @return
    */
    @Override
    public List<ItemClassifyAllVo> list(ItemClassifyDto itemClassifyDto) {
        Integer isDefault = ObjectUtil.isEmpty(itemClassifyDto.getIsDefault()) ? YesNo.NO.getCode().intValue() : itemClassifyDto.getIsDefault();
        ItemClassify itemClassify = dozerMapper.map(itemClassifyDto,ItemClassify.class);
        LambdaQueryWrapper<ItemClassify> queryWrapper = new LambdaQueryWrapper<>();
        if(isDefault == YesNo.NO.getCode().intValue()){
            //根据实际业务构建具体的参数做查询
            queryWrapper.and(ObjectUtil.isNotEmpty(itemClassifyDto.getClassifyCode()), wrapper -> wrapper.eq(ItemClassify::getClassifyCode,itemClassifyDto.getClassifyCode()))
//                    .eq(ObjectUtil.isNotEmpty(itemClassifyDto.getIsDel()),ItemClassify::getIsDel,itemClassifyDto.getIsDel())
                    /*.and(ObjectUtil.isNotEmpty(itemClassifyDto.getParentCode()), wrapper -> wrapper.eq(ItemClassify::getParentCode,itemClassifyDto.getParentCode()))*/
                    .and(wrapper -> wrapper.eq(ItemClassify::getIsDel,YesNo.NO.getCode()).or().isNull(ItemClassify::getIsDel))
                    .eq(ObjectUtil.isNotEmpty(itemClassifyDto.getParentCode()),ItemClassify::getParentCode,itemClassifyDto.getParentCode())
                    .orderByDesc(ItemClassify::getClassifyLevel).orderByAsc(ItemClassify::getClassifyCode);
        }else {
            queryWrapper.eq(ItemClassify::getParentCode,defaultRootId);
        }
        List<ItemClassify> list=list(queryWrapper);
        if(isDefault == YesNo.YES.getCode().intValue()){
            return DozerUtils.mapList(dozerMapper, list, ItemClassifyAllVo.class);
        }
        log.debug("查询条数:"+list.size());
        return handleMdmOrgAllVoList(list);
    }


    List<ItemClassifyAllVo> handleMdmOrgAllVoList(List<ItemClassify> list){
        //如果是条件查询，组装一下数据
        List<ItemClassifyAllVo> result = new ArrayList<>();
        List<ItemClassifyAllVo> mdmOrgAllVos = DozerUtils.mapList(dozerMapper, list, ItemClassifyAllVo.class);
        //构建一个map结构，为了比较方便
        Map<String,ItemClassifyAllVo> map = mdmOrgAllVos.stream().collect(Collectors.toMap(ItemClassifyAllVo::getClassifyCode, Function.identity(),(key1, key2) -> key2));

        //遍历结果集，处理数据
        for (ItemClassifyAllVo vo : mdmOrgAllVos){
            String parentCode = vo.getParentCode();
            ItemClassifyAllVo parentObj = map.get(parentCode);
            //如果从map中找到其父级，那么就把父级的list里加上自己
            if(ObjectUtil.isNotEmpty(parentObj)){
                //加上自己，因为是引用，所以getKids再add后，引用数据就加入进去了；
                parentObj.getKids().add(vo);
                //从map里把自己干掉，下次不再处理自己——因为列表排序是按level倒序排的，前面的处理的都是最末级层的
                map.remove(vo.getClassifyCode());
            }
        }
        //最后整合完的map里，就是我们要的结果
        for (Map.Entry<String,ItemClassifyAllVo> entry:map.entrySet()){
            result.add(entry.getValue());
        }
        return result;
    }

    /**
    * 自定义查询操作
    * @param itemClassifyDto
    * @return
    */
    @Override
    public List<ItemClassifyAllVo> lists(ItemClassifyDto itemClassifyDto) {
        List<ItemClassify> list=this.entityList(itemClassifyDto);
        //如果不是条件查询的话，直接返回
        if(itemClassifyDto.getIsDefault() == YesNo.YES.getCode().intValue()){
            return DozerUtils.mapList(dozerMapper,list,ItemClassifyAllVo.class);
        }

        return handleMdmOrgAllVoList(list);
    }

    public List<ItemClassify> entityList(ItemClassifyDto itemClassifyDto){
        LambdaQueryWrapper<ItemClassify> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(itemClassifyDto)){
            queryWrapper.eq(ObjectUtil.isNotEmpty(itemClassifyDto.getClassifyLevel()),ItemClassify::getClassifyLevel,itemClassifyDto.getClassifyLevel())
                    .eq(ObjectUtil.isNotEmpty(itemClassifyDto.getIsDel()),ItemClassify::getIsDel,itemClassifyDto.getIsDel())
                    .eq(ObjectUtil.isNotEmpty(itemClassifyDto.getClassifyCode()),ItemClassify::getClassifyCode,itemClassifyDto.getClassifyCode())
                    .like(ObjectUtil.isNotEmpty(itemClassifyDto.getClassifyName()),ItemClassify::getClassifyName,itemClassifyDto.getClassifyName())
                    .between(ObjectUtil.isNotNull(itemClassifyDto.getSUpdateTime())&&ObjectUtil.isNotNull(itemClassifyDto.getEUpdateTime()), ItemClassify::getUpdateTime, itemClassifyDto.getSUpdateTime(),itemClassifyDto.getEUpdateTime());
        }
        List<ItemClassify> list=super.list(queryWrapper);
        return list;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ItemClassifyVo get(Long id) {
        ItemClassify itemClassify = super.getById(id);
        ItemClassifyVo itemClassifyVo = null;
        if(itemClassify !=null){
            itemClassifyVo = dozerMapper.map(itemClassify,ItemClassifyVo.class);
            List<ItemSku> skuList=itemSkuService.list(Wrappers.<ItemSku>lambdaQuery().eq(ItemSku::getClassifyId,id));
            if(CollectionUtils.isNotEmpty(skuList)){
                List<ItemSkuVo> skuVos=DozerUtils.mapList(dozerMapper,skuList,ItemSkuVo.class);
                itemClassifyVo.setItemSkuList(skuVos);
            }
        }
        log.debug("查询成功");
        return itemClassifyVo;
    }

    /**
    * 保存实体
    * @param itemClassifyDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ItemClassifyDto itemClassifyDto) {
        if(StringUtils.isEmpty(itemClassifyDto.getClassifyCode())){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap entityMap = objectMapper.convertValue(itemClassifyDto, HashMap.class);
                String ruleValue = dictClient.getRuleValue(itemClassifyDto.getFormName(), "classifyCode", entityMap);
                log.info("规则值:{}", ruleValue);
                if (StrUtil.isNotEmpty(ruleValue)) {
                    itemClassifyDto.setClassifyCode(ruleValue);
                }
            } catch (Exception e) {
                log.error("获取规则编号值失败:{}", e);
            }
        }
        ItemClassify itemClassify = dozerMapper.map(itemClassifyDto,ItemClassify.class);
        itemClassify.setState(State.NORMAL.message);
        itemClassify.setIsDel(YesNo.NO.code);
        boolean result = super.save(itemClassify);
        List<ItemSkuDto> skuDtoList = itemClassifyDto.getItemSkuList();
        if(CollectionUtils.isNotEmpty(skuDtoList)){
            for (ItemSkuDto skuDto:skuDtoList) {
                ItemSku sku=dozerMapper.map(skuDto,ItemSku.class);
                sku.setClassifyId(itemClassify.getId());
                sku.setClassifyName(itemClassify.getClassifyName());
                sku.setClassifyCode(itemClassify.getClassifyCode());
                itemSkuService.save(sku);
            }
        }
        dealThisItemClassify(itemClassify);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param itemClassifyDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ItemClassifyDto itemClassifyDto) {
        if(StringUtils.isNotEmpty(itemClassifyDto.getDataSourceId()))
            throw new IncloudException("此数据不可修改");
        if(StringUtils.isEmpty(itemClassifyDto.getClassifyCode())){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap entityMap = objectMapper.convertValue(itemClassifyDto, HashMap.class);
                String ruleValue = dictClient.getRuleValue(itemClassifyDto.getFormName(), "classifyCode", entityMap);
                log.info("规则值:{}", ruleValue);
                if (StrUtil.isNotEmpty(ruleValue)) {
                    itemClassifyDto.setClassifyCode(ruleValue);
                }
            } catch (Exception e) {
                log.error("获取规则编号值失败:{}", e);
            }
        }
        ItemClassify itemClassify = dozerMapper.map(itemClassifyDto,ItemClassify.class);
        boolean result = super.updateById(itemClassify);

        List<ItemSkuDto> skuDtoList = itemClassifyDto.getItemSkuList();
        //itemSkuService.remove(Wrappers.<ItemSku>lambdaQuery().eq(ItemSku::getClassifyId,itemClassifyDto.getId()));
        if(CollectionUtils.isNotEmpty(skuDtoList)){
            for (ItemSkuDto skuDto:skuDtoList) {
                ItemSku sku=dozerMapper.map(skuDto,ItemSku.class);
                sku.setClassifyId(itemClassify.getId());
                sku.setClassifyName(itemClassify.getClassifyName());
                sku.setClassifyCode(itemClassify.getClassifyCode());
                itemSkuService.saveOrUpdate(sku);
            }
        }

        dealThisItemClassify(itemClassify);
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
        ItemClassify itemClassify=super.getById(id);
        if(StringUtils.isNotEmpty(itemClassify.getDataSourceId())) {
            throw new IncloudException("此数据不可删除");
        }
        itemClassify.setIsDel(YesNo.YES.code);
        itemClassify.setState(State.NORMAL.message);
        boolean result = super.updateById(itemClassify);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public Boolean saveList(List<ItemClassifyDto> itemClassifyDtos) {
        List<ItemClassify> itemClassifies=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(itemClassifyDtos)){
            for (ItemClassifyDto itemClassifyDto:itemClassifyDtos) {
                ItemClassify itemClassifyThis = dozerMapper.map(itemClassifyDto,ItemClassify.class);
                LambdaQueryWrapper<ItemClassify> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ItemClassify::getClassifyCode,itemClassifyDto.getClassifyCode());
                //判断是否已存在
                ItemClassify itemClassify=super.getOne(queryWrapper);
                if(itemClassify!=null){
                    itemClassifyThis.setId(itemClassify.getId());
                    super.updateById(itemClassifyThis);
                }else {
                    itemClassifies.add(itemClassifyThis);
                }
            }
            super.saveBatch(itemClassifies);
        }
        return true;
    }

    @Override
    public List<ItemClassifyVo> getByCodeList(List<String> codes) {
        LambdaQueryWrapper<ItemClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ItemClassify::getClassifyCode,codes);
        List<ItemClassify> itemClassifys=  this.list(queryWrapper);
        List<ItemClassifyVo> itemClassifyVoList = DozerUtils.mapList(dozerMapper, itemClassifys, ItemClassifyVo.class);
        return itemClassifyVoList;
    }

//    @JmsListener(destination=ItemConstants.ITEM_TYPE)
    @SneakyThrows
    public void receive_cnec22_zh_itemtype(String message) throws IOException {
        String msg= URLDecoder.decode(message, "utf-8");
        msg= msg.substring(12);   // outputValue={....} 去除字符串中的outputValue=
        log.info("增量同步物资分类数据开始");
        if (StringUtils.isEmpty(msg)) return;
        ItemTransmit itemTransmit= JSONObject.parseObject(msg, ItemTransmit.class);
        log.info("获取到物资分类数据:{}", itemTransmit.getData().size());
        List<ItemTransmitDetailDto> detailDtos=itemTransmit.getData();
        //分类进行入库
        detailDtos.forEach(itemClassify ->{
            ItemClassify classify=itemClassify.toItemClassify();
            classify.setUpdateTime(LocalDateTime.now());
            LambdaQueryWrapper<ItemClassify> queryWrapper=new LambdaQueryWrapper();
            queryWrapper.eq(ItemClassify::getClassifyCode,classify.getClassifyCode());
            ItemClassify classifyOld=super.getOne(queryWrapper);
            classify.setIsDel(State.NORMAL.message.equals(classify.getState())?State.NORMAL.code:State.DISABLE.code);
            if(ObjectUtil.isNotEmpty(classifyOld)){
                classify.setId(classifyOld.getId());
                updateById(classify);
            }else {
                classify.setCreateTime(LocalDateTime.now());
                save(classify);
            }
            //saveOrUpdate(classify, Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getClassifyCode, itemClassify.getCode()));
        });
        log.info("增量同步物资分类数据结束");
        List<ItemClassify> classifies=dealWithItemClassify();
        //数据推送--mq
        String json = JSON.toJSONString(classifies);
        mdmMqService.sendRocketMq(itemClassifyTopics,MdmConstants.ITEM_CODE,json);
    }

    public List<ItemClassify> dealWithItemClassify() {
        List<ItemClassify> classifies=new ArrayList<>();
        log.info("处理计算失败的物资分类数据");
        List<ItemClassify> itemClassifyList = super.list(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getIsCheck, YesNo.NO.code));
        for (int i = 0; i < itemClassifyList.size(); i++) {
            ItemClassify itemClassify = itemClassifyList.get(i);
            dealThisItemClassify(itemClassify);
            classifies.add(itemClassify);
        }
        itemService.dealWithItem();
        return classifies;
    }

    @Override
    public void getChildList(List<String> codeList,ItemClassify itemClassify) {
        //ItemClassify classify=super.getOne(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getClassifyCode,itemClassify.getClassifyCode()));
        List<ItemClassify> childList=super.list(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getParentCode,itemClassify.getClassifyCode()));
        if(ObjectUtil.isNotEmpty(childList) && !childList.isEmpty()){
            childList.forEach(thisClassify -> {
                getChildList(codeList,thisClassify);
                codeList.add(thisClassify.getClassifyCode());
            });
        }
        log.debug("getChildList 查询成功");
    }

    public void dealThisItemClassify(ItemClassify itemClassify){
        //1级分类
        if (ItemConstants.ONE_PARENT_CODE.equals(itemClassify.getParentCode())) {
            itemClassify.setClassifyLevel(1);
            itemClassify.setRoute(itemClassify.getClassifyCode());
            itemClassify.setRouteName(itemClassify.getClassifyName());
            itemClassify.setIsCheck(YesNo.YES.code);
            itemClassify.setCheckExplanation("处理成功");
            updateById(itemClassify);
            return;
        }
        Set<ItemClassify> allItemClassifyList = new HashSet<>();
        String parentcode = itemClassify.getParentCode();
        ItemClassify thisParent = super.getOne(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getClassifyCode, parentcode));
        itemClassify.setParentName(thisParent.getClassifyName());
        while (true) {
            ItemClassify parentClassify = super.getOne(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getClassifyCode, parentcode));
            if (ObjectUtil.isNull(parentClassify)) {
                ItemClassify upItemClassify = new ItemClassify();
                upItemClassify.setId(itemClassify.getId());
                upItemClassify.setIsCheck(YesNo.NO.code);
                upItemClassify.setCheckExplanation("未找到直属上级分类");
                super.updateById(upItemClassify);
                break;
            }
            if (ItemConstants.ONE_PARENT_CODE.equals(parentClassify.getParentCode())) {
                allItemClassifyList.add(parentClassify);
                allItemClassifyList.add(itemClassify);
                break;
            }
            if (ObjectUtil.isNotNull(parentClassify)) {
                parentcode = parentClassify.getParentCode();
                allItemClassifyList.add(parentClassify);
                allItemClassifyList.add(itemClassify);
            }
        }
        if (CollectionUtil.isNotEmpty(allItemClassifyList)) {
            itemClassify.setClassifyLevel(allItemClassifyList.size());
            //未获取到顶级分类
            if (allItemClassifyList.stream().filter(classify -> ItemConstants.ONE_PARENT_CODE.equals(classify.getParentCode())).findFirst().isPresent()) {
                allItemClassifyList = allItemClassifyList.stream().sorted(Comparator.comparing(ItemClassify::getClassifyCode)).collect(Collectors.toCollection(LinkedHashSet::new));
                String route = StrUtil.join(",", allItemClassifyList.stream().map(ItemClassify::getClassifyCode).collect(Collectors.toList()));
                String routeName = StrUtil.join("/", allItemClassifyList.stream().map(ItemClassify::getClassifyName).collect(Collectors.toList()));

                itemClassify.setRoute(route);
                itemClassify.setRouteName(routeName);
                itemClassify.setIsCheck(YesNo.YES.code);
                itemClassify.setCheckExplanation("处理成功");
                updateById(itemClassify);
            } else {
                itemClassify.setIsCheck(YesNo.NO.code);
                itemClassify.setCheckExplanation("未获取到顶级分类");
                updateById(itemClassify);
            }
        }
    }

}
