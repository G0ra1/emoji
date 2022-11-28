package com.netwisd.biz.mdm.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * @Description 同步广联达集采 功能描述...
 * @author 云数网讯 bai@netwisd.com
 * @date 2021-11-09 15:54:49
 */
public interface SyncGldService {

    public void syncGldSupplier()throws java.io.IOException, ParseException;
    public void syncGldPurchase() throws IOException, java.text.ParseException;
    public void syncGldProject()throws IOException, java.text.ParseException;
}
