package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.dto.SupplierDto;
import com.netwisd.biz.mdm.entity.Supplier;
import com.netwisd.biz.mdm.vo.SupplierVo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

/**
 * @Description  功能描述...
 * @author 云数网讯
 * @date 2021-08-27 15:54:49
 */
public interface MdmApiService{
    public void getToken(String code, String state,String accessToken,String expireIn)throws java.io.IOException ;

    public void getSupplierList(int pagenum,int pagesize) throws IOException, ParseException;

    public void getPurchaseListByOrgid(int pagenum,int pagesize,String orgid) throws IOException, java.text.ParseException;
    public void getAuthorize()throws java.io.IOException;
    public void getProjectList(int pagenum,int pagesize,String orgid) throws IOException;
}
