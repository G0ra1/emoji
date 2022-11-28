package com.netwisd.biz.mdm.dto;

import com.netwisd.biz.mdm.constants.YesNo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import com.netwisd.biz.mdm.entity.ItemClassify;
import com.netwisd.biz.mdm.entity.Item;

import java.time.LocalDateTime;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Data
@ApiModel(value = "物资数据传送 Dto")
public class ItemTransmitDetailDto{

    /****************物资*******************/
    private String uuid;
    private String name;
    private String code;
    private String categorycode;
    private String categoryname;
    private String desc10;
    private String desc11;
    private String desc20;//状态
    private String puuid;
    private String descshort;
    private String desclong;
    private String specs;
    private String standard;
    private String material;

    /****************物资分类*******************/
    private String parentcode;
    private String desc1;
    private String desc2;
    private String desc3;
    private String dataSourceId;


    public ItemClassify toItemClassify() {
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setClassifyCode(this.code);
        itemClassify.setParentCode(this.parentcode);
        itemClassify.setClassifyName(this.desc1);
        itemClassify.setDescription(this.desc2);
        itemClassify.setState(this.desc3);
        itemClassify.setIsCheck(YesNo.NO.code);
        itemClassify.setCheckExplanation("未处理");
        itemClassify.setIsAssetNumber("0");
        itemClassify.setDataSourceId(this.uuid);
        itemClassify.setUpdateTime(LocalDateTime.now());
        return itemClassify;
    }
    public Item toItem() {
        Item item = new Item();
        item.setClassifyCode(this.categorycode);
        item.setClassifyName(this.categoryname);
        item.setItemCode(this.code);
        item.setDesclong(this.desclong);
        item.setDescshort(this.descshort);
        item.setUnitCode(this.desc10);
        item.setUnitName(this.desc11);
        item.setState(this.desc20);
        item.setItemName(this.name);
        item.setMaterialQuality(this.material);
        item.setStandard(this.standard);
        item.setSpecs(this.specs);
        item.setIsCheck(YesNo.NO.code);
        item.setCheckExplanation("未处理");
        item.setDataSourceId(this.uuid);
        item.setUpdateTime(LocalDateTime.now());
        return item;
    }
}
