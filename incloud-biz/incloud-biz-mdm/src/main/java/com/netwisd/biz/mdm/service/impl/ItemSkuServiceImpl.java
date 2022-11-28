package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.constants.YesNo;
import com.netwisd.biz.mdm.dto.ItemClassifyDto;
import com.netwisd.biz.mdm.dto.ItemSkuDto;
import com.netwisd.biz.mdm.entity.ItemSku;
import com.netwisd.biz.mdm.mapper.ItemSkuMapper;
import com.netwisd.biz.mdm.service.ItemSkuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.vo.ItemSkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 物资分类sku 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 09:12:22
 */
@Service
@Slf4j
public class ItemSkuServiceImpl extends ServiceImpl<ItemSkuMapper, ItemSku> implements ItemSkuService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private ItemSkuMapper itemSkuMapper;

    /**
    * 单表简单查询操作
    * @param itemSkuDto
    * @return
    */
    @Override
    public Page list(ItemSkuDto itemSkuDto) {
        LambdaQueryWrapper<ItemSku> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<ItemSku> page = itemSkuMapper.selectPage(itemSkuDto.getPage(),queryWrapper);
        Page<ItemSkuVo> pageVo = DozerUtils.mapPage(dozerMapper, page, ItemSkuVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public ItemSkuVo get(Long id) {
        ItemSku itemSku = super.getById(id);
        ItemSkuVo itemSkuVo = null;
        if(itemSku !=null){
            itemSkuVo = dozerMapper.map(itemSku, ItemSkuVo.class);
        }
        log.debug("查询成功");
        return itemSkuVo;
    }

    /**
    * 保存实体
    * @param itemSkuDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(ItemSkuDto itemSkuDto) {
        ItemSku itemSku = dozerMapper.map(itemSkuDto, ItemSku.class);
        boolean result = super.save(itemSku);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param itemSkuDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(ItemSkuDto itemSkuDto) {
        itemSkuDto.setUpdateTime(LocalDateTime.now());
        ItemSku itemSku = dozerMapper.map(itemSkuDto, ItemSku.class);
        boolean result = super.updateById(itemSku);
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
     * 通过物资分类id查询
     * @param id
     * @return
     */
    @Transactional
    @Override
    public List<ItemSkuVo> getByClassifyId(Long id) {
        List<ItemSku> skuList=super.list(Wrappers.<ItemSku>lambdaQuery().eq(ItemSku::getClassifyId,id));
        List<ItemSkuVo> skuVos=new ArrayList<ItemSkuVo>();
        if (CollectionUtils.isNotEmpty(skuList))
            skuVos=DozerUtils.mapList(dozerMapper,skuList,ItemSkuVo.class);
        return skuVos;
    }

    @Override
    public Boolean saveOrUpdateBatch(ItemClassifyDto itemClassifyDto) {
        List<ItemSkuDto> skuDtos=itemClassifyDto.getItemSkuList();
        Boolean result=true;
        int i =1;
        for (ItemSkuDto skuDto: skuDtos) {
            ItemSku sku=dozerMapper.map(skuDto,ItemSku.class);
            sku.setClassifyId(itemClassifyDto.getId());
            sku.setClassifyCode(itemClassifyDto.getClassifyCode());
            sku.setClassifyName(itemClassifyDto.getClassifyName());
            sku.setDelFlag(YesNo.NO.getCode().toString());
            sku.setSort(i);
            if(sku.getId()==null){
                result = super.save(sku);
            }else {
                result = super.updateById(sku);
            }
            i++;
        }
        return result;
    }
}
