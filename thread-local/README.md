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

