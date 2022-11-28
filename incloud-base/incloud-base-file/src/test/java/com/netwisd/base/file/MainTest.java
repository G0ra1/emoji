package com.netwisd.base.file;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.text.DecimalFormat;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/8/13 5:40 下午
 */
@Slf4j
public class MainTest {

    private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();

    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups))  + units[digitGroups];
    }

    @Test
    public void test(){
        log.info("计算文件的大小传换为：{}",readableFileSize(1024 *1024 + 100));
    }
}
