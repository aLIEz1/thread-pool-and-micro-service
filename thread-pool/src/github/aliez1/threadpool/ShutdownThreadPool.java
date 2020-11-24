package github.aliez1.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * shutdown 方法不是暴力的关闭线程池的方法，线程仍然会我行我素的执行
 * 但是在shutdown之后再提交新的线程不会执行
 * 只是准备结束
 * isShutdown判断是否准备终止执行
 * isTerminated判断是否终止执行
 * shutdownNow力度大，会返回一个<code>List<Runnable><code/>
 *
 * @author zhangfuqi
 * @date 2020/11/24
 */
public class ShutdownThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + "被中断了");
                }
            });
        }
        Thread.sleep(1500);
        executorService.shutdownNow().forEach(System.out::println);
//        //打印的是3秒钟之内是否运行完毕了
//        System.out.println(executorService.awaitTermination(3L, TimeUnit.SECONDS));
//        //false
//        System.out.println(executorService.isShutdown());
//        executorService.shutdown();
//        //true
//        System.out.println(executorService.isShutdown());
//        //false
//        System.out.println(executorService.isTerminated());
//        Thread.sleep(10000);
//        //true
//        System.out.println(executorService.isTerminated());


        //这条语句不会执行
//        executorService.execute(() -> {
//            System.out.println("新的线程");
//        });
    }
}
