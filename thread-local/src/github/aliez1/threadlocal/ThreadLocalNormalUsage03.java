package github.aliez1.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1000个线程打印日期,线程池来执行
 * 不会重复创建SimpleDateFormat对象 static 为静态变量
 * 所有线程共用一个SimpleDateFormat对象的时候发生了线程安全问题
 * 打印的时间出现了重复
 *
 * @author zhangfuqi
 * @date 2020/11/25
 */
public class ThreadLocalNormalUsage03 {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                System.out.println(new ThreadLocalNormalUsage03().date(finalI));
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        //参数的单位是毫秒，时间从19701.1 00:00:00 GMT秒开始计时
        Date date = new Date(1000 * seconds);
        return dateFormat.format(date);
    }
}
