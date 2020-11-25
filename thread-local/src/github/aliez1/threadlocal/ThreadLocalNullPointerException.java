package github.aliez1.threadlocal;

/**
 * ThreadLocal空指针问题
 *
 * @author zhangfuqi
 * @date 2020/11/25
 */
public class ThreadLocalNullPointerException {
    ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public void setThreadLocal() {
        threadLocal.set(Thread.currentThread().getId());
    }

    public Long getThreadLocal() {
        return threadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalNullPointerException threadLocalNullPointerException = new ThreadLocalNullPointerException();
        //没有设置初值直接get会报空指针异常
        // Long getThreadLocal注意一定要使用包装类型作为返回值，对象类型转换成基本类型出错，拆箱时报错
        System.out.println(threadLocalNullPointerException.getThreadLocal());
        new Thread(()->{
            threadLocalNullPointerException.setThreadLocal();
            System.out.println(threadLocalNullPointerException.getThreadLocal());
        }).start();
        threadLocalNullPointerException.setThreadLocal();
        System.out.println(threadLocalNullPointerException.getThreadLocal());
    }
}
