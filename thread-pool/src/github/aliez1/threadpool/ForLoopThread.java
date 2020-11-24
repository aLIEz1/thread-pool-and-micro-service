package github.aliez1.threadpool;

/**
 * @author zhangfuqi
 * @date 2020/11/24
 */
public class ForLoopThread {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Task()).start();
        }
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println("执行了方法");
        }
    }
}
