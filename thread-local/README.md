# Thread Local

## 两大使用场景

- 典型场景1：每个线程需要一个独享的对象（通常是工具类，典型需要使用的类又SimpleDateFormat和Random）
- 典型场景2：每个线程内需要保存全局变量（例如在拦截器中获取用户信息），可以让不同的方法直接使用，避免参数传递的麻烦

### SimpleDateFormat进化之路

1. 2个线程分别用自己的SimpleDateFormat，这没问题
2. 后来延伸出30个线程
3. 但是还当需求变成1000个，必然要使用线程池，1000个SimpleDateFormat对象
4. 所有的线程都共用一个SimpleDateFormat对象，发生了线程不安全的问题
5. 我们可以选择加锁，加锁后正常，但是效率低
6. 更好的解决方案是Thread Local，每个线程中都有自己独享的对象
7. ThreadLocal.withInitial()方法初始化ThreadLocal lambada表达式
8. ![image-20201125111806080](C:\Users\super\AppData\Roaming\Typora\typora-user-images\image-20201125111806080.png)

## Thread Local 的两个作用

1. 让某个需要用到的对象在线程间隔离（每个线程都有自己独立的对象）
2. 在任何方法中都可以轻松获取到该对象

> - initial Value： 在Thread Local第一次get的时候把对象给初始化出来，对象的初始化实际可以由我们控制
> - set：如果需要保存到Thread Local里的对象生成时机不由我们随意控制，例如拦截器生成的用户信息。用Thread Local .set直接放到我们的Thread Local中去，一边后续使用

## Thread Local 原理

![image-20201125143229610](C:\Users\super\AppData\Roaming\Typora\typora-user-images\image-20201125143229610.png)

Thread中有属性Thread Local Map，Thread Local Map中有很多Thread Local，其中键为Thread Local的引用，值为我们想存储的对象

Thread Local Map中解决冲突的方法是线性探测法，如果发生冲突，就继续找下一个空位置，而不是使用链表拉链

## Thread Local 注意点

- 内存泄漏：

  - 某个对象不再有用，但是占用的内存不能被回收
  - key为弱引用。 弱引用的特点：如果这个对象只被弱引用关联（没有任何强引用关联），那么这个对象就可以被回收
  - value的泄露：Thread Local Map 的每个Entry都是一个对key弱引用，同时每个Entry都包含了一个对value 的强引用
    - 正常情况下，当线程终止，保存在Thread Local里的value会被垃圾回收，因为没有任何强引用了
    - 但是如果线程不终止（比如线程需要保存很久），那么key对应的value就不能被回收，因为有以下的调用链：
      - Thread->Thread Local Map->Entry(key为null)->Value
    - 因为value和Thread之间还存在这个强引用链路，所以导致value无法回收，就可能出现OOM
    - 扫描key为null的Entry并把对应的value设置为null
    - 如果一个Thread Local不被使用，就可能导致内存的泄露
  - 如何避免内存泄漏？
    - 调用remove方法，就会删除对应的Entry对象，可以避免内存泄漏，所以使用完Thread Local后，应该调用remove方法

- 空指针异常

  - 在进行get之前必须先set，否则可能报空指针异常？

  - ```java
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
    
    ```

  - 