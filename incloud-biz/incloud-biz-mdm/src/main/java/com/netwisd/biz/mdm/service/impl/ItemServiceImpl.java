package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netwisd.biz.mdm.constants.ItemConstants;
import com.netwisd.biz.mdm.constants.MdmConstants;
import com.netwisd.biz.mdm.constants.State;
import com.netwisd.biz.mdm.constants.YesNo;
import com.netwisd.biz.mdm.dto.*;
import com.netwisd.biz.mdm.entity.*;
import com.netwisd.biz.mdm.fegin.DictClient;
import com.netwisd.biz.mdm.mapper.ItemMapper;
import com.netwisd.biz.mdm.service.*;
import com.netwisd.biz.mdm.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Service
@Slf4j
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemClassifyService itemClassifyService;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Autowired
    MdmMqService mdmMqService;

    @Autowired
    private ItemSkuService skuService;

    @Autowired
    private ItemSkuLineService skuLineService;

    @Autowired ItemSkuColumnService skuColumnService;
    @Autowired ItemUnitService unitService;
    @Autowired private DictClient dictClient;

    @Value("${spring.rocketmq.itemTopics}")
    private String itemTopics;

    /**
    * 单表简单查询操作
    * @param itemDto
    * @return
    */
    @Override
    public Page list(ItemDto itemDto) {
        Item item = dozerMapper.map(itemDto,Item.class);
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();
        List<String> codeList=new ArrayList<>();
        codeList.add(itemDto.getClassifyCode());
        //查询分类底下所有层级的数据
        if(ObjectUtil.isNotEmpty(itemDto.getIsDefault())&&ObjectUtil.isNotEmpty(itemDto.getClassifyCode())){
            ItemClassify itemClassify=new ItemClassify();
            itemClassify.setClassifyCode(itemDto.getClassifyCode());
            itemClassifyService.getChildList(codeList,itemClassify);
        }
        //根据实际业务构建具体的参数做查询
        String searchCondition = itemDto.getSearchCondition();
        if(StringUtils.isNotBlank(itemDto.getSearchCondition())){
            queryWrapper.like(Item::getItemCode,itemDto.getSearchCondition()).or()
                    .like(Item::getItemName,itemDto.getSearchCondition()).or()
                    .like(Item::getDesclong,itemDto.getSearchCondition()).or()
                    .like(Item::getDescshort,itemDto.getSearchCondition()).or()
                    .like(Item::getClassifyCode,itemDto.getSearchCondition()).or()
                    .like(Item::getClassifyName,itemDto.getSearchCondition())
                    .and(ObjectUtil.isNotEmpty(itemDto.getClassifyCode()),wrapper -> wrapper.in(Item::getClassifyCode,codeList))
                    .and(ObjectUtil.isNotEmpty(itemDto.getIsDel()),wrapper -> wrapper.eq(Item::getIsDel,itemDto.getIsDel()));
        }else{
            queryWrapper.eq(ObjectUtil.isNotEmpty(itemDto.getItemCode()),Item::getItemCode,itemDto.getItemCode())
                    .like(ObjectUtil.isNotEmpty(itemDto.getItemName()),Item::getItemName,itemDto.getItemName())
                    .eq(ObjectUtil.isNotEmpty(itemDto.getIsDel()),Item::getIsDel,itemDto.getIsDel())
                    .like(ObjectUtil.isNotEmpty(itemDto.getDesclong()),Item::getDesclong,itemDto.getDesclong())
                    .like(ObjectUtil.isNotEmpty(itemDto.getDesclong()),Item::getDescshort,itemDto.getDesclong())
                    .and(ObjectUtil.isNotEmpty(itemDto.getClassifyCode()),wrapper -> wrapper.in(Item::getClassifyCode,codeList));
        }
        Page<Item> page = itemMapper.selectPage(itemDto.getPage(),queryWrapper);
        Page<ItemVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ItemVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * rest方式获取数据
    * @param itemDto
    * @return
    */
    @Override
    public List<ItemVo> lists(ItemDto itemDto) {
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(itemDto)){
            queryWrapper.eq(Item::getItemCode,itemDto.getItemCode())
                    .like(ObjectUtil.isNotEmpty(itemDto.getItemName()),Item::getItemName,itemDto.getItemName())
                    .eq(ObjectUtil.isNotEmpty(itemDto.getClassifyCode()),Item::getClassifyCode,itemDto.getClassifyCode())
                    .eq(ObjectUtil.isNotEmpty(itemDto.getIsDel()),Item::getIsDel,itemDto.getIsDel())
                    .like(ObjectUtil.isNotEmpty(itemDto.getClassifyName()),Item::getClassifyName,itemDto.getClassifyName())
                    .eq(ObjectUtil.isNotEmpty(itemDto.getState()),Item::getState,itemDto.getState())
                    .between(ObjectUtil.isNotNull(itemDto.getSUpdateTime())&&ObjectUtil.isNotNull(itemDto.getEUpdateTime()), Item::getUpdateTime, itemDto.getSUpdateTime(),itemDto.getEUpdateTime());
        }
        List<Item> list=super.list(queryWrapper);
        List<ItemVo> voList=DozerUtils.mapList(dozerMapper,list,ItemVo.class);
        log.debug("查询条数:"+list.size());
        return voList;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ItemVo get(Long id) {
        Item item = super.getById(id);
        ItemVo itemVo = null;
        if(item !=null){
            itemVo = dozerMapper.map(item,ItemVo.class);
            //表单第一行
            List<ItemSku> skuList=skuService.list(Wrappers.<ItemSku>lambdaQuery().eq(ItemSku::getClassifyCode,itemVo.getClassifyCode()).orderByAsc(ItemSku::getSort));
            if(CollectionUtils.isNotEmpty(skuList)){
                List<ItemSkuVo> titles=DozerUtils.mapList(dozerMapper,skuList,ItemSkuVo.class);
                itemVo.setSkuList(titles);
                //表单内容
                List<ItemSkuLine> lines=skuLineService.list(Wrappers.<ItemSkuLine>lambdaQuery().eq(ItemSkuLine::getItemId,id));
                if(CollectionUtils.isNotEmpty(lines)){
                    List<ItemSkuLineVo> lineVoList=DozerUtils.mapList(dozerMapper,lines,ItemSkuLineVo.class);
                    for (ItemSkuLineVo lineVo:lineVoList) {
                        List<ItemSkuColumn> columns=skuColumnService.list(Wrappers.<ItemSkuColumn>lambdaQuery().eq(ItemSkuColumn::getLineId,lineVo.getId()).orderByAsc(ItemSkuColumn::getSkuSort));
                        if(CollectionUtils.isNotEmpty(columns))
                            lineVo.setSkuColumnList(DozerUtils.mapList(dozerMapper,columns, ItemSkuColumnVo.class));
                    }
                    itemVo.setSkuLineList(lineVoList);
                }
            }
            if(YesNo.YES.code==item.getIsUnit()){
                List<ItemUnit> units=unitService.list(Wrappers.<ItemUnit>lambdaQuery().eq(ItemUnit::getItemId,id));
                if(CollectionUtils.isNotEmpty(units))
                    itemVo.setUnitList(DozerUtils.mapList(dozerMapper,units, ItemUnitVo.class));
            }
        }
        log.debug("查询成功");
        return itemVo;
    }

    /**
    * 保存实体
    * @param itemDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ItemDto itemDto) {
        //校验分类是否存在
        ItemClassify itemClassify = itemClassifyService.getOne(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getClassifyCode, itemDto.getClassifyCode()));
        if(ObjectUtil.isNull(itemClassify))
            throw new IncloudException("当前未登录用户，请检查配置相关！");
        if(StringUtils.isEmpty(itemDto.getItemCode())){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap entityMap = objectMapper.convertValue(itemDto, HashMap.class);
                String ruleValue = dictClient.getRuleValue(itemDto.getFormName(), "itemCode", entityMap);
                log.info("规则值:{}", ruleValue);
                if (StrUtil.isNotEmpty(ruleValue)) {
                    itemDto.setItemCode(ruleValue);
                }
            } catch (Exception e) {
                log.error("获取规则编号值失败:{}", e);
            }
        }

        Item item = dozerMapper.map(itemDto,Item.class);
        item.setRoute(ObjectUtil.isNotNull(itemClassify) ? itemClassify.getRoute() : null);
        item.setRouteName(ObjectUtil.isNotNull(itemClassify) ? itemClassify.getRouteName() : null);
        item.setIsCheck(ObjectUtil.isNotNull(itemClassify) ? YesNo.YES.code : YesNo.NO.code);
        item.setCheckExplanation(ObjectUtil.isNotNull(itemClassify) ? "处理成功" : "未找到对应的分类");
        item.setState(State.NORMAL.message);
        item.setIsDel(YesNo.NO.code);
        boolean result = super.save(item);
        List<ItemSkuLineDto> lineDtos=itemDto.getSkuLineList();
        for (ItemSkuLineDto skuLineDto:lineDtos) {
            ItemSkuLine skuLine=dozerMapper.map(skuLineDto,ItemSkuLine.class);
            skuLine.setItemId(item.getId());
            skuLine.setItemCode(item.getItemCode());
            skuLine.setItemName(item.getItemName());
            skuLineService.save(skuLine);
            List<ItemSkuColumnDto> skuColumnDtos= skuLineDto.getSkuColumnList();
            for (ItemSkuColumnDto columnDto:skuColumnDtos) {
                ItemSkuColumn column=dozerMapper.map(columnDto,ItemSkuColumn.class);
                column.setItemId(item.getId());
                column.setItemCode(item.getItemCode());
                column.setItemName(item.getItemName());
                column.setLineId(skuLine.getId());
                skuColumnService.save(column);
            }
        }
        if(YesNo.YES.code==item.getIsUnit()){
            List<ItemUnitDto> unitDtos=itemDto.getUnitList();
            if(ObjectUtil.isNotEmpty(unitDtos)){
                for (ItemUnitDto unitDto:unitDtos) {
                    ItemUnit unit=dozerMapper.map(unitDto,ItemUnit.class);
                    unit.setItemId(item.getId());
                    List<ItemUnit> units=unitService.list(Wrappers.<ItemUnit>lambdaQuery().eq(ItemUnit::getItemId,item.getId()).eq(ItemUnit::getUnitCode,unit.getUnitCode()));
                    if(CollectionUtils.isEmpty(units))
                        unitService.save(unit);
                }
            }
        }
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param itemDto
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(ItemDto itemDto) {
        if(StringUtils.isNotEmpty(itemDto.getDataSourceId()))
            throw new IncloudException("此数据不可修改");
        ItemClassify itemClassify = itemClassifyService.getOne(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getClassifyCode, itemDto.getClassifyCode()));
        if(ObjectUtil.isNull(itemClassify))
            throw new IncloudException("当前未登录用户，请检查配置相关！");
        Item item = dozerMapper.map(itemDto,Item.class);
        boolean result = super.updateById(item);
        List<ItemSkuLineDto> lineDtos=itemDto.getSkuLineList();
        for (ItemSkuLineDto skuLineDto:lineDtos) {
            ItemSkuLine skuLine=dozerMapper.map(skuLineDto,ItemSkuLine.class);
            skuLine.setItemId(item.getId());
            skuLine.setItemCode(item.getItemCode());
            skuLine.setItemName(item.getItemName());
            skuLineService.saveOrUpdate(skuLine);
            List<ItemSkuColumnDto> skuColumnDtos= skuLineDto.getSkuColumnList();
            for (ItemSkuColumnDto columnDto:skuColumnDtos) {
                ItemSkuColumn column=dozerMapper.map(columnDto,ItemSkuColumn.class);
                column.setItemId(item.getId());
                column.setItemCode(item.getItemCode());
                column.setItemName(item.getItemName());
                column.setLineId(skuLine.getId());
                skuColumnService.saveOrUpdate(column);
            }
        }
        if(YesNo.YES.code==item.getIsUnit()){
            List<ItemUnitDto> unitDtos=itemDto.getUnitList();
            if(ObjectUtil.isNotEmpty(unitDtos)){
                for (ItemUnitDto unitDto:unitDtos) {
                    ItemUnit unit=dozerMapper.map(unitDto,ItemUnit.class);
                    unit.setItemId(item.getId());
                    List<ItemUnit> units=unitService.list(Wrappers.<ItemUnit>lambdaQuery().eq(ItemUnit::getItemId,item.getId()).eq(ItemUnit::getUnitCode,unit.getUnitCode()));
                    if(CollectionUtils.isEmpty(units)||units.get(0).getId()==unit.getId())
                        unitService.saveOrUpdate(unit);
                }
            }
        }else{
            unitService.remove(Wrappers.<ItemUnit>lambdaQuery().eq(ItemUnit::getItemId,item.getId()));
        }
         /*List<Item> itemList=new ArrayList<>();
        itemList.add(item);
       String json = JSON.toJSONString(itemList);
        mdmMqService.sendRocketMq(itemTopics,MdmConstants.ITEM_CODE,json);*/
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID伪删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        Item item=super.getById(id);
        item.setIsDel(YesNo.YES.code);
        item.setState(State.NORMAL.message);
        boolean result = super.updateById(item);
        skuLineService.remove(Wrappers.<ItemSkuLine>lambdaQuery().eq(ItemSkuLine::getItemId,id));
        skuColumnService.remove(Wrappers.<ItemSkuColumn>lambdaQuery().eq(ItemSkuColumn::getItemId,id));
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public Boolean saveList(List<ItemDto> itemDtos) {
        List<Item> itemList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(itemDtos)){
            for (ItemDto dto:itemDtos) {
                Item item=dozerMapper.map(dto,Item.class);
                LambdaQueryWrapper<Item> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(ObjectUtil.isNotEmpty(dto.getItemCode()),Item::getItemCode,dto.getItemCode());
                List<Item> resultList=super.list(queryWrapper);
                if(CollectionUtils.isNotEmpty(resultList)){
                    Item itemResult=resultList.get(0);
                    item.setId(itemResult.getId());
                    super.updateById(item);
                }else {
                    itemList.add(item);
                }
            }
            super.saveBatch(itemList);
        }
        return true;
    }

//    @JmsListener(destination= ItemConstants.ITEM_CODE)
    public void receive_cnec22_zh_itemcode(String message)throws Exception{
        String msg= URLDecoder.decode(message, "utf-8");
        msg= msg.substring(12);   // outputValue={....} 去除字符串中的outputValue=
        log.info("增量同步物资数据开始");
        if (StringUtils.isEmpty(msg)) return;
        ItemTransmit itemTransmit=JSONObject.parseObject(msg, ItemTransmit.class);
        List<ItemTransmitDetailDto> itemDtos=itemTransmit.getData();
        log.info("获取到物资数据:{}", itemDtos.size());
        List<Item> itemList=new ArrayList<>();
        itemDtos.forEach(item ->{
            Item itemNew=item.toItem();
            itemNew.setIsDel(State.NORMAL.message.equals(itemNew.getState())?State.NORMAL.code:State.DISABLE.code);
            itemNew.setUpdateTime(LocalDateTime.now());
            LambdaQueryWrapper<Item> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(Item::getItemCode, item.getCode());
            Item itemOld=super.getOne(wrapper);
            if(ObjectUtil.isNotEmpty(itemOld)){
                updateById(itemNew);
            }else {
                itemNew.setCreateTime(LocalDateTime.now());
                save(itemNew);
            }
            //saveOrUpdate(item1, Wrappers.<Item>lambdaQuery().eq(Item::getItemCode, item.getCode()));
        });
        log.info("增量同步物资数据结束");
        List<Item> itemsToMq=new ArrayList<>();
        itemDtos.forEach(item -> {
            ItemClassify itemClassify = itemClassifyService.getOne(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getClassifyCode, item.getCategorycode()));
            Item upItem = new Item();
            upItem.setRoute(ObjectUtil.isNotNull(itemClassify) ? itemClassify.getRoute() : null);
            upItem.setRouteName(ObjectUtil.isNotNull(itemClassify) ? itemClassify.getRouteName() : null);
            upItem.setIsCheck(ObjectUtil.isNotNull(itemClassify) ? YesNo.YES.code : YesNo.NO.code);
            upItem.setClassifyId(ObjectUtil.isNotEmpty(itemClassify)?itemClassify.getId():null);
            upItem.setCheckExplanation(ObjectUtil.isNotNull(itemClassify) ? "处理成功" : "未找到对应的分类");
            update(upItem, Wrappers.<Item>lambdaQuery().eq(Item::getItemCode, item.getCode()));
            itemsToMq.add(upItem);
        });
        log.info("结束");
        //数据推送--mq
        String json = JSON.toJSONString(itemsToMq);
        mdmMqService.sendRocketMq(itemTopics,MdmConstants.ITEM_CODE,json);
    }

    @Override
    @Async
    public void checkItem(){
        List<ItemClassify> itemClassifies=itemClassifyService.list();
        int size = 5000;
        for(int i =1;i<size;i++){
            Page page=new Page();
            page.setSize(size);
            page.setCurrent(i);
            Page<Item> itemPage =itemMapper.selectPage(page,new LambdaQueryWrapper<>());
            List<Item> itemList=itemPage.getRecords();
            itemList.forEach(item -> {
                if(StringUtils.isNotEmpty(item.getClassifyCode())){
                    List<ItemClassify> a=itemClassifies.stream().filter(itemcl->item.getClassifyCode().equals(itemcl.getClassifyCode())).collect(Collectors.toList());
                    ItemClassify itemClassify=new ItemClassify();
                    if(CollectionUtils.isNotEmpty(a)){
                        itemClassify = a.get(0);
                    }
                    Item upItem = new Item();
                    upItem.setRoute(ObjectUtil.isNotNull(itemClassify) ? itemClassify.getRoute() : null);
                    upItem.setRouteName(ObjectUtil.isNotNull(itemClassify) ? itemClassify.getRouteName() : null);
                    upItem.setIsCheck(ObjectUtil.isNotNull(itemClassify) ? YesNo.YES.code : YesNo.NO.code);
                    upItem.setCheckExplanation(ObjectUtil.isNotNull(itemClassify) ? "处理成功" : "未找到对应的分类");
                    upItem.setClassifyId(ObjectUtil.isNotEmpty(itemClassify)?itemClassify.getId():null);
                    update(upItem, Wrappers.<Item>lambdaQuery().eq(Item::getItemCode, item.getItemCode()));
                }
            });
            if (itemList.size()<size)
                return;
        }
    }

    @Override
    public void dealWithItem() {
        log.info("处理计算失败的物资数据");
        list(Wrappers.<Item>lambdaQuery().eq(Item::getIsCheck, YesNo.NO.code)).forEach(item -> {
            ItemClassify itemClassify = itemClassifyService.getOne(Wrappers.<ItemClassify>lambdaQuery().eq(ItemClassify::getClassifyCode, item.getClassifyCode()));
            item.setRoute(ObjectUtil.isNotNull(itemClassify) ? itemClassify.getRoute() : null);
            item.setRouteName(ObjectUtil.isNotNull(itemClassify) ? itemClassify.getRouteName() : null);
            item.setIsCheck(ObjectUtil.isNotNull(itemClassify) ? YesNo.YES.code : YesNo.NO.code);
            item.setCheckExplanation(ObjectUtil.isNotNull(itemClassify) ? "处理成功" : "未找到对应的分类");
            item.setClassifyId(ObjectUtil.isNotEmpty(itemClassify)?itemClassify.getId():null);
            updateById(item);
        });
    }

}
