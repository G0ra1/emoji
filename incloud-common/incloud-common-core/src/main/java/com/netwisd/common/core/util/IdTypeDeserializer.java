package com.netwisd.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Description: 自定义的ID反序列化实现，用于后台雪花算法生成的20位标准Long主键值转成字符串给前端并反向传回后台时，反序列化为后台Long类型
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/15 3:52 下午
 */
@Slf4j
public class IdTypeDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        boolean sign = false;
        String currentName = null;
        try{
            currentName = jsonParser.currentName();
            if(jsonParser!=null&& StrUtil.isNotEmpty(jsonParser.getText())){
                return Long.valueOf(jsonParser.getText());
            }else {
                sign = true;
                log.error("反序列化的字段：{}的传入值不能为空,",currentName);
            }
        }
        catch(Exception e) {
            log.error("反序列化的字段：{}的传入值不能为空,",currentName);
            throw new IncloudException(e);
        }finally {
            if(sign){
                throw new IncloudException(currentName +"值传入错误");
            }
        }
        return null;
    }
}
