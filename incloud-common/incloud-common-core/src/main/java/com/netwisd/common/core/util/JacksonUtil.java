package com.netwisd.common.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * @Description: jackson工具类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/29 8:19 下午
 */
@Slf4j
public class JacksonUtil {
    /**
     * 转换器
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String DATE_FORMATTER = "yyyy-MM-dd";
    private static final String TIME_FORMATTER = "HH:mm:ss";
    private static final String DATE_TIME_FORMATTER = DATE_FORMATTER + " " + TIME_FORMATTER;

    static {
        objectMapper
                //下划线转驼峰
                .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
                // 允许没有引号的字段名
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                // 允许单引号字段名
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                // 不识别的类型跳过
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // null不生成到json字符串中
                //.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                // 设置时区
                .setTimeZone(TimeZone.getTimeZone("GMT+8"))
                // Date 对象的格式，非 java8 时间
                .setDateFormat(new SimpleDateFormat(DATE_TIME_FORMATTER))
                // 默认忽略值为 null 的属性，暂时不忽略，放开注释即不会序列化为 null 的属性
//				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                // 禁止打印时间为时间戳
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // 禁止使用科学记数法
                .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        // 自定义Java8的时间兼容模块
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 序列化配置,针对java8 时间
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMATTER)));
        // 反序列化配置,针对java8 时间
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMATTER)));

        /*注册模块*/
        objectMapper
                // Java8的时间兼容模块
                .registerModule(javaTimeModule)
                // Jdk8Module() -> 注册jdk8模块
                .registerModule(new Jdk8Module())
                // new ParameterNamesModule() ->
                .registerModule(new ParameterNamesModule());
    }

    /**
     * 将JSON字符串内容转换为集合类型
     *
     * @param <T>     泛型对象
     * @param content JSON字符串内容
     * @param clazz   List泛型中对象类型
     * @return List集合
     */
    public static <T> List<T> parseArray(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, new TypeReference<List<T>>() {
                //
            });
        } catch (Exception e) {
            log.error("JSON反序列化失败：" + e.getMessage(), e);
            throw new IncloudException("JSON反序列化失败：" + e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串内容转换为对象类型
     *
     * @param content   JSON字符串内容
     * @param valueType 值类型
     * @param <T>       对象类型
     * @return 将JSON字符串内容转换为对象类型
     */
    public static <T> T parseObject(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            log.error("JSON反序列化失败：" + e.getMessage(), e);
            throw new IncloudException("JSON反序列化失败：" + e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串内容转换为泛型所对应的类型
     *
     * @param content     JSON字符串内容
     * @param genericType 泛型类型
     * @param <T>         对象类型
     * @return 将JSON字符串内容转换为对象类型
     */
    public static <T> T parseObject(String content, Type genericType) {
        try {
            return objectMapper.readValue(content, TypeFactory.defaultInstance().constructType(genericType));
        } catch (Exception e) {
            log.error("JSON反序列化失败：" + e.getMessage(), e);
            throw new IncloudException("JSON反序列化失败：" + e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串内容转换为对象类型-对象泛型支持
     *
     * @param <T>          泛型类型
     * @param content      JSON字符串内容
     * @param valueTypeRef 泛型类型
     * @return 对象类型
     */
    public static <T> T parseObject(String content, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (Exception e) {
            log.error("JSON反序列化失败：" + e.getMessage(), e);
            throw new IncloudException("JSON反序列化失败：" + e.getMessage(), e);
        }
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param object 对象
     * @return JSON字符串
     */
    public static String toJSONString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("JSON序列化失败：" + e.getMessage(), e);
            throw new IncloudException("JSON序列化失败：" + e.getMessage(), e);
        }
    }

    /**
     * 将来源对象转换为目标对象
     *
     * @param <T>            泛型对象
     * @param fromValue      对象值
     * @param toValueTypeRef 目标对象泛型
     * @return 目标对象
     */
    public static <T> T convertObject(Object fromValue, TypeReference<T> toValueTypeRef) {
        return objectMapper.convertValue(fromValue, toValueTypeRef);
    }

    /**
     * 将来源对象转换为目标对象
     *
     * @param <T>         泛型对象
     * @param fromValue   对象值
     * @param toValueType 目标对象class类型
     * @return 目标对象
     */
    public static <T> T convertObject(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    /**
     * 获取ObjectMapper对象
     *
     * @return 获取ObjectMapper对象
     */
    public static ObjectMapper getMapper() {
        return objectMapper;
    }

}
