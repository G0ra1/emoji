package com.netwisd.base.file.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: current package com.netwisd.budgetpro.util
 * @Author: zouliming@netwisd.com
 * @Date: 2020/2/24 9:18 上午
 */
@Slf4j
public class MySqlBackUp {
    /**
     * 数据库备份
     * @param host
     * @param user
     * @param pwd
     * @param port
     * @param dbName
     * @param backPath
     * @param backName
     * @throws Exception
     */
    public static int dbBackUp2File(String host,String user, String pwd, String port, String dbName, String backPath, String backName) throws Exception {
        String pathSql = backPath+backName;
        File fileSql = new File(pathSql);
        //创建备份sql文件
        if (!fileSql.exists()){
            fileSql.createNewFile();
        }
        StringBuffer sb = new StringBuffer();
        //mysqldump的参数组装 —— 配置好mysqldump的环境变量
        sb.append(". ~/.bash_profile \n");
        sb.append("mysqldump --opt")
        .append(" -h"+host)
        .append(" -u"+user)
        .append(" -p"+pwd)
        .append(" -P"+port)
        .append(" "+dbName+" > ");
        sb.append(pathSql);
        log.info("cmd命令为："+sb.toString());
        ProcessBuilder pb = new ProcessBuilder("/bin/sh","-c",sb.toString());
        Process p = pb.start();
        log.info("开始备份："+dbName);
        int exitCode = p.waitFor();
        log.info("备份完成!");
        return exitCode;
    }

    /**
     *
     * @param host
     * @param user
     * @param pwd
     * @param port
     * @param dbName
     * @param args
     * @param bucketName
     * @return
     * @throws Exception
     */
    @SneakyThrows
    public static int dbBackUp2Minio(String host,String user, String pwd, String port, String dbName, String args, String bucketName){
        StringBuffer sb = new StringBuffer();
        sb.append(". ~/.bash_profile \n");
        //mysqldump的参数组装 —— 配置好mysqldump的环境变量
        sb.append("mysqldump --opt")
        .append(" -h"+host)
        .append(" -u"+user)
        .append(" -p"+pwd)
        .append(" -P"+port)
        .append(" "+dbName);

        //minio的参数组装 —— 需要提前配置好mc的minio server连接属性，并且配置好mc的环境变量
        sb.append(" | mc pipe minio/")
        .append(bucketName)
        .append("/")
        .append(args)
        .append(".sql");
        log.info("shell命令为："+sb.toString());

        ProcessBuilder pb = new ProcessBuilder("/bin/sh","-c",sb.toString());
        Process p = pb.start();
        log.info("开始备份："+dbName);
        int exitCode = p.waitFor();
        log.info("备份完成!");
        return exitCode;
    }

    /**
     *
     * @param host
     * @param root
     * @param pwd
     * @param dbName
     * @param filePath
     * @return
     */
    @SneakyThrows
    public static int dbRestoreFromFile(String host,String root,String pwd,String dbName,String filePath){
        StringBuilder sb = new StringBuilder();
        sb.append(". ~/.bash_profile \n")
        .append("mysql")
        .append(" -h"+host)
        .append(" -u"+root)
        .append(" -p"+pwd)
        .append(" "+dbName+" <");
        sb.append(filePath);
        log.info("shell命令为："+sb.toString());
        ProcessBuilder pb = new ProcessBuilder("/bin/sh","-c",sb.toString());
        log.info("开始还原数据");
        Process p = pb.start();
        int exitCode = p.waitFor();
        log.info("还原完成!");
        return exitCode;
    }

    /**
     *
     * @param host
     * @param user
     * @param pwd
     * @param port
     * @param dbName
     * @param bucketName
     * @param sqlFile
     */
    @SneakyThrows
    public static int dbRestoreFromMinio(String host,String user,String pwd,String port,String dbName,String bucketName,String sqlFile){
        StringBuilder sb = new StringBuilder();
        sb.append(". ~/.bash_profile \n")
        .append("mysql")
        .append(" -h"+host)
        .append(" -u"+user)
        .append(" -p"+pwd)
        .append(" -P"+port)
        .append(" "+dbName+" < ")
        .append(sqlFile);
        log.info("shell命令为："+sb.toString());
        ProcessBuilder pb = new ProcessBuilder("/bin/sh","-c",sb.toString());
        log.info("开始还原数据");
        Process p = pb.start();
        int exitCode = p.waitFor();
        log.info("还原完成!");
        return exitCode;
    }

    public static int save(String host,String user, String pwd, String port, String dbName, String args, String bucketName){
        return MySqlBackUp.dbBackUp2Minio(host,user, pwd, port, dbName, args, bucketName);
    }

    public static int load(String host,String user,String pwd,String port,String dbName,String bucketName,String sqlFile){
        return MySqlBackUp.dbRestoreFromMinio(host,user, pwd, port, dbName,bucketName,sqlFile);
    }
}
