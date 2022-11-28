package com.netwisd.base.mdm;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.netwisd.base.common.util.AesUtil;
import com.netwisd.common.core.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/13 5:40 下午
 */
@Slf4j
public class MainTest {

    private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();

    @Test
    public void idTest(){
       /* long l = IDENTIFIER_GENERATOR.nextId(this).longValue();
        log.info("生成一个id：{}",l);*/

        /*List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("aff");
        for (String str : list){
            if(str.equals("aff")){
                log.info("str :{}",str);
                list.remove(str);
            }
        }
        log.info("list : {}",JacksonUtil.toJSONString(list));*/

        /*Map<String,String> map = new HashMap<>();
        map.put("a","a");
        map.put("b","b");
        for (Map.Entry<String,String> entry : map.entrySet()){
            if(map.containsKey("a")){
                log.info("remove a!");
                map.remove("a");
            }
        }
        log.info("list : {}",JacksonUtil.toJSONString(map));*/

        String pw = AesUtil.aesEncrypt("admin");
        log.info("pw : {}",pw);
    }
}
