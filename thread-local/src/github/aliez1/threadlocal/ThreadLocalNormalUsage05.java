package github.aliez1.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1000个线程打印日期,线程池来执行
 * 利用ThreadLocal，给每个线程分配自己的SimpleDateFormat对象，同时保证了线程安全
 * 高效的利用了内存
 *
 * @author zhangfuqi
 * @date 2020/11/25
 */
public class ThreadLocalNormalUsage05 {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                System.out.println(new ThreadLocalNormalUsage05().date(finalI));
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        //参数的单位是毫秒，时间从19701.1 00:00:00 GMT秒开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return dateFormat.format(date);
    }
}

class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
}
