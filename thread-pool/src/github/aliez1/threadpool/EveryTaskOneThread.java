package github.aliez1.threadpool;

/**
 * @author zhangfuqi
 * @date 2020/11/24
 */
public class EveryTaskOneThread {
    public static void main(String[] args) {
        new Thread(new Task()).start();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println("执行了任务");
        }
    }
}
