package com.netwisd.biz.asset.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ItemTypeEnum {

    ASSET("1","资产","采购"),
    ARTICLES("2","低值易耗品","耗用"),
    INVENTORY("3","库存","销售"),
    MATERIAL("4","原材料","自制");
    public String code;
    public String type;
    public String usage;

    ItemTypeEnum(String code,String type,String usage){
        this.code = code;
        this.type = type;
        this.usage = usage;
    }
}
