package github.aliez1.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 30个线程打印日期
 *
 * @author zhangfuqi
 * @date 2020/11/25
 */
public class ThreadLocalNormalUsage01 {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(new ThreadLocalNormalUsage01().date(finalI));
            }).start();
            Thread.sleep(100);
        }
    }

    public String date(int seconds) {
        //参数的单位是毫秒，时间从19701.1 00:00:00 GMT秒开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
