package com.netwisd.biz.mdm.dto;

import com.netwisd.base.common.data.IDto;
import com.netwisd.biz.mdm.entity.Supplier;
import com.netwisd.biz.mdm.entity.SupplierContacts;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Description 供应商数据传送 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Data
@ApiModel(value = "供应商数据传送 Dto")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SupplierTransmit{

    private String supplierid;//供应商ID
    private String suppliercode;//供应商编码
    private String suppliername;//供应商名称
    private String representative;//法人代表/法定代表人
    private String regnumber;//工商注册号
    private String businesslicense;//营业执照号
    private String orgnizationcode;//组织机构代码证号/组织机构代码
    private String registercapital;//注册资金 单位：万元
    private String primarybusiness;//主营业务/经营范围
    private String credential;//资质等级
    private String address;//地址
    private String contactperson;//联系人名
    private String phone;//电话/法人联系电话
    private String mobile;//联系人手机/联系人电话
    private String zipcode;//邮编
    private String property;//单位性质/企业性质
    private String establishdate;//注册日期/成立日期 格式：yyyy-MM-dd
    private String suppliertype;//供应商类型/评价状态0 待审；1 试用；2 合格；3 战略；9 黑名单；
    private String isuse;//供应商状态0：不可用；1：可用
    private String objtypes;//供应商大类（多个时以”,”隔开）1工程；2物资 默认；3专业分包；4劳务分包；5机械采购租赁…
    private String taxtype;//计税类型1：一般计税；2：简易计税
    private String taxpayertype;//纳税人类别1：一般纳税人；2：小规模纳税人；3：其他
    private String taxpayercode;//纳税人识别号
    private String categorycodes;//供应商分类编码
    private String categoryids;//供应商分类ID
    private String categorynames;//供应商分类名称
    private String createdate;//创建时间
    private String updatedate;//更新时间
    private String orgid;//所属组织id
    private String orgname;//所属组织名称

    public Supplier toSupplier()throws java.text.ParseException{
        Supplier supplier=new Supplier();
        supplier.setDataSourceId(this.getSupplierid());
        supplier.setSupplierCode(this.getSuppliercode());
        supplier.setSupplierName(this.getSuppliername());
        supplier.setRepresentative(this.getRepresentative());
        supplier.setRegNumber(this.getRegnumber());
        supplier.setBusinessLicense(this.getBusinesslicense());
        supplier.setOrgnizationCode(this.getOrgnizationcode());
        supplier.setRegisterCapital(this.getRegistercapital());
        supplier.setPrimaryBusiness(this.getPrimarybusiness());
        supplier.setCredential(this.getCredential());
        supplier.setAddress(this.getAddress());
        supplier.setPhone(this.getPhone());
        supplier.setZipcode(this.getZipcode());
        supplier.setProperty(this.getProperty());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isNotEmpty(this.getEstablishdate()))
            supplier.setEstablishDate(simpleDateFormat.parse(this.getEstablishdate()));
        supplier.setIsUse(this.getIsuse());
        supplier.setTaxpayerType(this.getTaxpayertype());
        supplier.setTaxpayerCode(this.getTaxpayercode());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        /*if(StringUtils.isNotEmpty(this.getCreatedate()))
            supplier.setCreateTime(LocalDateTime.parse(this.getCreatedate(),df));
        if(StringUtils.isNotEmpty(this.getUpdatedate()))
            supplier.setUpdateTime(LocalDateTime.parse(this.getUpdatedate(),df));*/
        supplier.setOrgName(this.getOrgname());
        return supplier;
    }

    public SupplierContacts toSupplierContacts(){
        SupplierContacts contacts=new SupplierContacts();
        contacts.setSupplierCode(this.suppliercode);
        contacts.setContactsName(this.getContactperson());
        contacts.setContactsPhone(this.getMobile());
        return contacts;
    }


}
