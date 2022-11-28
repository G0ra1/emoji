package com.netwisd.base.portal.util;

import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
public class MakeHtml {
    /**
     * 写入服务文件
     * @param htmlStr 富文本框内容
     * @param filePath 文件路径
     * @return
     */
    public static boolean toHtmlFile(String htmlStr, String filePath) {
        try {
            if(StringUtils.isBlank(filePath)) {
                throw new IncloudException("该文件路径不存在。");
            }
            //截取前面的路径 判断是否存在 如果不存在则要创建
            String str = filePath.substring(0, filePath.lastIndexOf("/"));
            File file1 = new File(str);
            if(!file1 .exists()) {
                file1.mkdirs();//创建目录
            }
            //必须设置编码格式不然会出现乱码
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8"));
            bufferedWriter.write("<html>\n<head>\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "<meta content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;\" name=\"viewport\">\n" +
                    "</head>");
            bufferedWriter.newLine();//换行
            bufferedWriter.write(htmlStr);
            bufferedWriter.newLine();//换行
            bufferedWriter.write("</html>");
            bufferedWriter.newLine();//换行
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 生成服务文件路径
     * @param title 文件名
     * @param portalId 门户文件夹
     * @param partCode 栏目文件夹
     * @return
     */
    public static String buildFilePath(String title,Long portalId,Long partCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(portalId);
        sb.append("/");
        sb.append(partCode);
        sb.append("/");
        sb.append(title);
        sb.append(".html");
        return sb.toString();
    }

    /**
     * 生成nginx httpUrl访问路径
     * @param portalFileIp ip地址
     * @param filePath nginx 访问路径
     * @return
     */
    public static String buildHttpUrl(String portalFileIp,String filePath) {
        //todo 默认端口为80 如果部署时不是80 要做修改 也可以改成配置文件方式
        String buildHttpUrl ="http://" + portalFileIp + "/" +filePath;
        return buildHttpUrl;
    }


//    public static void main(String[] args) {
//        long begin = System.currentTimeMillis();
//        String filePath = "d:/PPPPPP/KKKK" + 55 + ".html";// 生成文件地址
//        toHtmlFile("<p>MiniUI - 专业WebUI控件库</p><p>&nbsp;</p><p>快速开发，减少50%代码量</p><p>丰富组件库，高性能、低内存</p><p>支持 Java、.NET、PHP</p><p>支持 IE6+、FireFox、Chrome</p>", filePath);
//        System.out.println("用时:" + (System.currentTimeMillis() - begin) + "ms");
//        //System.out.println(buildFilePath("test","集团门户","集团新闻"));
//    }
}
