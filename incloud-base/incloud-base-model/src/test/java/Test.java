import cn.hutool.core.io.LineHandler;
import cn.hutool.core.lang.Console;

public class Test {

    /*public static void main(String[] args) {
        *//*doWithSession(session -> {
            JschUtil.exec(session, "/root/test.sh", Charset.defaultCharset());
            return true;
        });*//*
        Tailer tailer = new Tailer(FileUtil.file("D:\\test.log"), Tailer.CONSOLE_HANDLER, 2);
        tailer.start(true);
    }*/

    public static class ConsoleLineHandler implements LineHandler {
        @Override
        public void handle(String line) {
            Console.log(line);
        }
    }


    /*public static <T> T doWithSession(Function<Session, T> function) {
        Session session = null;
        try {
            session = JschUtil.getSession("192.168.1.195", 22, "root", "Huawei.com");
            return function.apply(session);
        } catch (JschRuntimeException exception) {
            throw new IncloudException("Shell服务器连接失败");
        } finally {
            JschUtil.close(session);
        }
    }*/

}
