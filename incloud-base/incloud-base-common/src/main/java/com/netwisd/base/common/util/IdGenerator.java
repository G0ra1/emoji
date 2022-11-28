package com.netwisd.base.common.util;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IdGenerator {
    private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();
    public static Long getIdGenerator() {
        return IDENTIFIER_GENERATOR.nextId(IdGenerator.class).longValue();
    }

    public static void main(String[] args) {
        System.out.println(getIdGenerator());

        for (int a = 0; a < 1000; a++) {
            System.out.println(getTimestamp());
        }
    }

    /**
     * 获取一个8位的随机id
     * @return
     */
    public static String getTimestamp(){
        Calendar c=Calendar.getInstance();
        String time=new SimpleDateFormat("yyyy-MM-ddHHmmss").format(c.getTime()).toString();
        StringBuffer s=new StringBuffer(time.substring(14, 16));
        Long sys=System.currentTimeMillis();
        s.append(sys.toString().substring(11, 13));
        Double tm=Math.random()*10000+1;
        s.append(tm.toString().substring(tm.toString().length()-4, tm.toString().length()));
        return s.toString();
    }

    /**
     * 获取一个6位的随机id
     * @return
     */
    public static String getTimestampFor6Bit(){
        Calendar c=Calendar.getInstance();
        String time=new SimpleDateFormat("yyyy-MM-ddHHmmss").format(c.getTime()).toString();
        StringBuffer s=new StringBuffer(time.substring(14, 16));
        //Long sys=System.currentTimeMillis();
        //s.append(sys.toString().substring(11, 13));
        Double tm=Math.random()*10000+1;
        s.append(tm.toString().substring(tm.toString().length()-4, tm.toString().length()));
        return s.toString();
    }

}
