package com.netwisd.base.portal.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author 云数网讯 zouliming@netwisd.com
 * @Description 获取body体，不直接取httpRequest对象中的流，
 * 那是因为流对应的是数据，数据放在内存中，有的是部分放在内存中。
 * read 一次标记一次当前位置（mark position），第二次read就从标记位置继续读（从内存中copy）数据。 所以这就是为什么读了一次第二次是空了。 怎么让它不为空呢？
 * 只要inputstream 中的pos 变成0就可以重写读取当前内存中的数据。javaAPI中有一个方法public void reset() 这个方法就是可以重置pos为起始位置，但是不是所有的IO读取流都可以调用该方法！
 * ServletInputStream是不能调用reset方法，这就导致了只能调用一次getInputStream()
 * @date 2021/9/9 17:21
 */
public class BodyCachingHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;
    private ServletInputStreamWrapper inputStreamWrapper;

    public BodyCachingHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = IOUtils.toByteArray(request.getInputStream());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
        this.inputStreamWrapper = new ServletInputStreamWrapper(byteArrayInputStream);
        resetInputStream();
    }

    private void resetInputStream() {
        this.inputStreamWrapper.setInputStream(new ByteArrayInputStream(this.body != null ? this.body : new byte[0]));
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.inputStreamWrapper;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.inputStreamWrapper));
    }


    @Data
    @AllArgsConstructor
    private static class ServletInputStreamWrapper extends ServletInputStream {

        private InputStream inputStream;

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }
    }
}
