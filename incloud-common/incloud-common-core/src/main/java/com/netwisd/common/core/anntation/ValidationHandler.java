package com.netwisd.common.core.anntation;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.netwisd.common.core.constants.VarConstants;
import com.netwisd.common.core.data.IValidation;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @Description: 自定义规则校验器
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 3:30 下午
 */
@Slf4j
@Aspect
@Configuration
@Order(2)
@EnableScheduling
public class ValidationHandler {

    private Integer flag = LicenseEnum.FAIL.value;

    /*@Value("${lic.ip}")
    private String url;*/

    /*@Scheduled(fixedDelay = 600000)
    void check(){
        Socket client = null;
        flag = LicenseEnum.FAIL.value;
        try {
//            //客户端请求与本机在50001端口建立TCP连接
//            client = new Socket(url, 50001);
//            client.setSoTimeout(10000);
//            //获取Socket的输出流，用来发送数据到服务端
//            PrintStream out = new PrintStream(client.getOutputStream());
//            out.println("incloud");
//
//            byte[] b = toByteArray(client.getInputStream());
//
//            if(b.length != 20){
//                throw new IncloudException("获取license数据长度错误！");
//            }
//
//            byte[] time = Arrays.copyOfRange(b, 0,8);
//            byte[] result = Arrays.copyOfRange(b, 8,12);
//            byte[] salt = Arrays.copyOfRange(b, 12,20);
//            long timeValue = ByteConvert.Bytes2Int_LE(time);
//            int resultValue = ByteConvert.Bytes2Int_LE(result);
//            int saltValue = ByteConvert.Bytes2Int_LE(salt);
//
//            long currentTime = System.currentTimeMillis();
//            long _currentTime = currentTime / 1000;
//            //超过5s
//            if(_currentTime - timeValue >= 5000){
//                throw new IncloudException("非法的license获取时间！");
//            }
//            if(timeValue % resultValue != saltValue){
//                throw new IncloudException("非法的license结果！");
//            }
            //最后赋值
            flag = LicenseEnum.SUCCESS.value;
        }catch (Exception e){
            flag = LicenseEnum.ERR.value;
            throw new IncloudException("连接go Tcp连接失败！");
        }finally {
            if(client != null){
                //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
                try {
                    client.close(); //只关闭socket，其关联的输入输出流也会被关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /*@Pointcut("within(com.netwisd..*.controller..*)")
    public void cutPoint4License() {
    }*/

    /*@Around("cutPoint4License()")
    @SneakyThrows
    public Object license(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Class declaringType = signature.getDeclaringType();
        Annotation annotation = declaringType.getAnnotation(License.class);
        if(ObjectUtil.isNotEmpty(annotation)){
            License license =(License) annotation;
            if(flag.intValue()!=LicenseEnum.SUCCESS.value){
                throw new IncloudException(LicenseEnum.FAIL.getMsgFromValue(flag));
            }
            log.info("license:{}",license.incloud());
        }
        return joinPoint.proceed();
    }*/

    /**
     * 第一个*表示匹配任意的方法返回值， ..(两个点)表示零个或多个，第二个..表示controller包及其子包,第三个*表示所有类, 第四个*表示所有方法，第三个..表示方法的任意参数个数
     */
    @Pointcut("execution(public * com.netwisd..*.controller..*.*(..))")
    public void cutPoint() {
    }

    /**
     * 在切入点做校验，环绕的，咱想怎么绕就怎么绕;
     * 反正写的有点绕；
     * @param joinPoint
     */
    @Around("cutPoint()")
    @SneakyThrows
    public Object validate(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] arguments = joinPoint.getArgs();
        for(Parameter parameter: parameters){
            Class<?> type = parameter.getType();
            Validation annotation = parameter.getAnnotation(Validation.class);
            if(ObjectUtil.isNotEmpty(annotation)){
                Object obj = validate(parameter,arguments,annotation);
                if(ObjectUtil.isNotEmpty(obj)){
                    return obj;
                }
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 类型校验
     * @param parameter
     * @param arguments
     * @param annotation
     * @return
     */
    Object validate(Parameter parameter, Object[] arguments, Validation annotation){
        Object targetObjValue = null;
        int modifiers = parameter.getModifiers();
        targetObjValue = arguments[modifiers];
        /*boolean checkEmpty = annotation.checkEmpty();*/
        StringBuffer sb = new StringBuffer();
        Map<String,String> msg = new HashMap();
        if(!validate(targetObjValue,msg,annotation,parameter.getName())){
            //因为是递归调用，执行时，最里面的一层调用会先设置当前param的值，所以外层的msg就去判断一下，有就不再覆盖
            if(msg.size() == 0){
                msg.put("param",parameter.getName());
            }
            String msgs = msg.get("incloud");
            if(StrUtil.isNotEmpty(msgs)){
                sb.append(msg.get("param"));
            }else {
                sb.append("参数:").append(msg.get("param")).append(" 不能为空");
            }
            //改成抛出异常，主要为feign调用时，把异常抛出；
            throw new IncloudException(sb.toString());
            //return Result.failed(sb.toString());
        }
        return null;
    }

    /**
     * 不同类型的数据校验，注意：基本包装类型不去写，是因为基本类型直接拿Obj值去比的；
     * @param obj
     * @param msg
     * @return
     */
    Boolean validate(Object obj, Map<String,String> msg,Validation annotation,String param){
        ValidateResult inex = inOrEx(annotation, param, obj);
        //基本包装类型直接比较
        if(ObjectUtil.isEmpty(obj) && !inex.getResult()){
            return false;
        }else {
            //自定义包装类型; 注意想要使用@Validation就实现这接口，IDto也实现了它；
            if(obj instanceof IValidation) {
                return dtoValidate(obj,msg,annotation);
            }else if(obj instanceof Map){//Map类型
                Map map = (Map)obj;
                if((ObjectUtil.isEmpty(map) || map.size() == 0) &&  !inex.getResult()){
                    return false;
                }else{
                    Collection coll = map.values();
                    return collectionValidate(coll,msg,annotation,param);
                }
            }else if(obj instanceof Collection){//Collection类型
                return collectionValidate(obj,msg,annotation,param);
            }
            //可以再扩展Java内置对象，不过正常情况下，上面几种组合已经适用于绝大多数情况下的数据结构了；
            else {
                return true;
            }
        }
    }

    /**
     * 集合类型的校验
     * @param obj
     * @param msg
     * @return
     */
    Boolean collectionValidate(Object obj,Map<String,String> msg,Validation annotation,String param){
        ValidateResult inex = inOrEx(annotation, param, obj);
        if(ObjectUtil.isEmpty(obj) && !inex.getResult()){
            return false;
        }
        Collection collection = (Collection)obj;
        if((ObjectUtil.isEmpty(collection) || collection.size() == 0) && !inex.getResult()){
            return false;
        }else {
            for(Object innerObj : collection){
                //把集合中元素遍历出来再去validate校验
                return validate(innerObj,msg,annotation,param);
            }
        }
        return true;
    }

    /**
     * dto类型的校验
     * @param obj
     * @param msg
     * @return
     */
    Boolean dtoValidate(Object obj,Map<String,String> msg,Validation annotation){
        Field[] fields = obj.getClass().getDeclaredFields();
        //处理dto的校验
        for(Field field : fields){
            //必须要用@Valid属性才去校验
            Valid valid = field.getAnnotation(Valid.class);
            String name = field.getName();
            Object value = getFieldValueByName(name,obj);
            ValidateResult inex = inOrEx(annotation, name, value);
            String nullMsg = null;
            //如果是默认机制
            if(inex.getIsDefault()){
                if(ObjectUtil.isNotEmpty(valid) && !inex.getResult()){
                    nullMsg = valid.nullMsg();
                    if(check(msg,annotation,name,value,nullMsg)){
                        continue;
                    }else {
                        return false;
                    }
                }
            }else {
                if(!inex.getResult()){
                    if(check(msg,annotation,name,value,nullMsg)){
                        continue;
                    }else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    Boolean check(Map<String,String> msg,Validation annotation,String name,Object value,String nullMsg){
        //获取到其当前属性去validate里校验
        if(!validate(value,msg,annotation,name)){
            //因为是递归调用，执行时，最里面的一层调用会先设置当前param的值，所以外层的msg就去判断一下，有就不再覆盖
            if(msg.size() == 0){
                if(StrUtil.isNotEmpty(nullMsg)){
                    msg.put("param",nullMsg);
                    msg.put("incloud","这只是一个标识；");
                }else {
                    msg.put("param",name);
                }
            }
            return false;
        }
        return true;
    }

    /*Boolean inOrEx(Validation annotation,String param,Object obj){
        return validateExclude(annotation, param, obj);
    }*/

    ValidateResult inOrEx(Validation annotation,String param,Object obj){
        ValidateResult validateResult = new ValidateResult(false,false);
        InOrExResult inResult = validateInclude(annotation, param, obj);
        InOrExResult exResult = validateExclude(annotation, param, obj);
        Boolean in = inResult.getSign();
        Boolean ex = exResult.getSign();
        if(in && ex){
            Boolean inAll = inResult.getIsAll();
            Boolean exAll = exResult.getIsAll();
            if(!inAll && !exAll){
                throw new IncloudException("同一个属性:"+param +"上in和ex不能同时标注！");
            }else if(inAll && !exAll){
                //log.info("属性ALL：{} 不校验",param);
                validateResult = new ValidateResult(true,false);
            }else if(!inAll && exAll){
                //log.info("属性ALL：{} 要校验",param);
                validateResult = new ValidateResult(false,false);
            }else if(inAll && exAll){
                //log.info("属性ALL：{} 默认机制",param);
                validateResult = new ValidateResult(false,true);
            }else {
                //log.info("属性ALL：{} 默认机制",param);
                validateResult = new ValidateResult(false,true);
            }
        }else {
            if(in){
                //log.info("属性：{} 要校验",param);
                validateResult = new ValidateResult(false,false);
            }else if(ex){
                //log.info("属性：{} 不校验",param);
                validateResult = new ValidateResult(true,false);
            }else {
                //log.info("属性：{} 默认机制",param);
                validateResult = new ValidateResult(false,true);
            }
        }
        return validateResult;
    }

    /**
     * 通过注解和目标属性名，属性值去校验
     * @param annotation
     * @param param 检验的参数
     * @param obj 检验的值
     * @return
     */
    InOrExResult validateInclude(Validation annotation,String param,Object obj){
        InOrExResult inOrExResult = new InOrExResult(false,false);
        IncludeAnntation[] include = annotation.include();
        if(ObjectUtil.isNotEmpty(include) && include.length > 0){
            for (IncludeAnntation includeAnntation : include){
                return validateInOrEx(inOrExResult,includeAnntation,null,param,obj);
            }
        }
        return inOrExResult;
    }

    InOrExResult validateInOrEx(InOrExResult inOrExResult ,IncludeAnntation includeAnntation ,ExcludeAnntation excludeAnntation,String param,Object obj){
        String value = ObjectUtil.isNotEmpty(includeAnntation) ? includeAnntation.value() : excludeAnntation.value();
        if(value.equals(VarConstants.ALL)){
            inOrExResult.setIsAll(true);
            inOrExResult.setSign(true);
            return inOrExResult;
        }
        Class<?> clazz = ObjectUtil.isNotEmpty(includeAnntation) ? includeAnntation.clazz() : excludeAnntation.clazz();
        String var = ObjectUtil.isNotEmpty(includeAnntation) ? includeAnntation.var() : excludeAnntation.var();
        String[] strings = ObjectUtil.isNotEmpty(includeAnntation) ? includeAnntation.vars() : excludeAnntation.vars();
        List<String> vars = Arrays.asList(strings);
        //如果开启了成员变量层面的或自定义了类
        if(StrUtil.isNotEmpty(var) && var.equalsIgnoreCase(param)){
            inOrExResult.setSign(true);
            return inOrExResult;
        }
        if(ObjectUtil.isNotEmpty(obj) && obj.getClass() == clazz){
            inOrExResult.setSign(true);
            return inOrExResult;
        }
        if(ObjectUtil.isNotEmpty(vars) && vars.contains(param)){
            inOrExResult.setSign(true);
            return inOrExResult;
        }
        return inOrExResult;
    }

    /**
     * 通过注解和目标属性名，属性值去校验
     * @param annotation
     * @param param
     * @param obj
     * @return
     */
    InOrExResult validateExclude(Validation annotation,String param,Object obj){
        InOrExResult inOrExResult = new InOrExResult(false,false);
        ExcludeAnntation[] exclude = annotation.exclude();
        if(ObjectUtil.isNotEmpty(exclude) && exclude.length > 0){
            for (ExcludeAnntation excludeAnntation : exclude){
                return validateInOrEx(inOrExResult,null,excludeAnntation,param,obj);
            }
        }
        return inOrExResult;
    }

    /**
     * 根据字段获取其值
     * @param name
     * @param obj
     * @return
     */
    public static Object getFieldValueByName(String name, Object obj){
        String firstletter = name.substring(0, 1).toUpperCase();
        String getter = "get" + firstletter + name.substring(1);
        try {
            Method method = obj.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(obj, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    @AllArgsConstructor
    class ValidateResult{
        private Boolean result;
        private Boolean isDefault;
    }

    @Data
    @AllArgsConstructor
    class InOrExResult{
        private Boolean sign;
        private Boolean isAll;
    }

    @AllArgsConstructor
    @Getter
    enum LicenseEnum {
        SUCCESS(1001,"成功"),FAIL(1002,"license过期"),OTHER(1003,"license验证失败"),ERR(1004,"license连接失败");

        //结果
        private Integer value;
        //结果
        private String msg;

        public String getMsgFromValue(Integer value) {
            for (LicenseEnum licenseEnum : LicenseEnum.values()) {
                if(licenseEnum.value.intValue() == value.intValue()){
                    return licenseEnum.msg;
                }
            }
            return null;
        }
    }

}
