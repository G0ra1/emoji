package com.netwisd.common.code.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.common.code.entity.GenConfig;
import com.netwisd.common.code.entity.ModelConfig;
import com.netwisd.common.code.mapper.GeneratorMapper;
import com.netwisd.common.code.service.GeneratorService;
import com.netwisd.common.code.util.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @Description: current project incloud
 * current package com.netwisd.codegen.service.impl
 * @Author: zouliming
 * @Date: 2020/2/3 1:11 下午
 */
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    GeneratorMapper generatorMapper;
    /**
     * 生成代码
     *
     * @param genConfig 生成配置
     * @return
     */
    @Override
    public byte[] generatorCode(GenConfig genConfig,String templatePacket) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        String tableNames = genConfig.getTableName();
        //后期用vue做一个前端交互，可以支持多个表生成
        for (String tableName : StrUtil.split(tableNames, StrUtil.DASHED)) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(genConfig, table, columns, zip, templatePacket);
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] generatorCode(ModelConfig modelConfig) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        GenUtils.generatorCode(modelConfig,zip,modelConfig.getTemplatePacket());
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    private Map<String, String> queryTable(String tableName) {
        return generatorMapper.queryTable(tableName);
    }

    private List<Map<String, String>> queryColumns(String tableName) {
        return generatorMapper.queryColumns(tableName);
    }
}
