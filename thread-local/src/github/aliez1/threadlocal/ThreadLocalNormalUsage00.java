package github.aliez1.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 2个线程打印日期，没有问题
 *
 * @author zhangfuqi
 * @date 2020/11/25
 */
public class ThreadLocalNormalUsage00 {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(new ThreadLocalNormalUsage00().date(10));
        }).start();
        new Thread(() -> {
            System.out.println(new ThreadLocalNormalUsage00().date(104707));
        }).start();
    }

    public String date(int seconds) {
        //参数的单位是毫秒，时间从19701.1 00:00:00 GMT秒开始计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }
}
