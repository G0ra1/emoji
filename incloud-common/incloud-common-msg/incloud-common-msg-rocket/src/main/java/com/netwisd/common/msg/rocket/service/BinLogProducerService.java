package com.netwisd.common.msg.rocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.common.msg.rocket.data.BinLogData;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.PropertyNamingStrategy.*;

@Slf4j
@Service
public class BinLogProducerService {

    @Autowired
    private DefaultMQProducer binLogProducer;

    public void sendBinLogMsg(String table, String opType, List data) {
        try {
            log.info("操作表:{},操作类型:{},操作数据:{}", table, opType, data.size());
            BinLogData binLogData = new BinLogData();
            binLogData.setTable(table);
            binLogData.setType(opType);
            binLogData.setData(data);

            SerializeConfig config = new SerializeConfig();
            config.setPropertyNamingStrategy(SnakeCase);
            //JSON.toJSONStringWithDateFormat(binLogData, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat).getBytes(RemotingHelper.DEFAULT_CHARSET)
            Message msg = new Message(
                    "incloud4BinLog",
                    "*",
                    JSON.toJSONString(binLogData, config, null, JSON.DEFFAULT_DATE_FORMAT, 0, SerializerFeature.UseISO8601DateFormat).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            binLogProducer.sendOneway(msg);
        } catch (Exception e) {
            log.error("Binlog发送失败:{}", e);
        }
    }

    public void sendBinLogMsg(String table, String opType, Object data) {
        try {
            log.info("操作表:{},操作类型:{},操作数据:{}", table, opType, data);
            BinLogData binLogData = new BinLogData();
            binLogData.setTable(table);
            binLogData.setType(opType);
            List listData = new ArrayList<>();
            listData.add(data);
            binLogData.setData(listData);

            SerializeConfig config = new SerializeConfig();
            config.setPropertyNamingStrategy(SnakeCase);
            //JSON.toJSONStringWithDateFormat(binLogData, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat).getBytes(RemotingHelper.DEFAULT_CHARSET)
            Message msg = new Message(
                    "incloud4BinLog",
                    "*",
                    JSON.toJSONString(binLogData, config, null, JSON.DEFFAULT_DATE_FORMAT, 0, SerializerFeature.UseISO8601DateFormat).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            binLogProducer.sendOneway(msg);
        } catch (Exception e) {
            log.error("Binlog发送失败:{}", e);
        }
    }

}
