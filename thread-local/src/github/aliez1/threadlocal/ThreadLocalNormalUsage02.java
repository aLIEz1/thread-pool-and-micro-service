package github.aliez1.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1000个线程打印日期,线程池来执行
 * 创建了1000个SimpleDateFormat对象
 *
 * @author zhangfuqi
 * @date 2020/11/25
 */
public class ThreadLocalNormalUsage02 {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(() -> {
                System.out.println(new ThreadLocalNormalUsage02().date(finalI));
            });
        }
        threadPool.shutdown();
    }

    public String date(int seconds) {
        //参数的单位是毫秒，时间从19701.1 00:00:00 GMT秒开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
