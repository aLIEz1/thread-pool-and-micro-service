package github.aliez1.threadlocal;

/**
 * 演示ThreadLocal用法2：避免传递参数的麻烦
 *
 * @author zhangfuqi
 * @date 2020/11/25
 */
public class ThreadLocalNormalUsage06 {
    public static void main(String[] args) {
        new Service1().process();
    }
}

class Service1 {
    public void process() {
        User user = new User("zhang");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {
    public void process() {
        System.out.println("Service2: " + UserContextHolder.holder.get());
        //调用remove()方法会清空ThreadLocal中保存的对象,Service3则获取不到User对象
        UserContextHolder.holder.remove();
        //重新set()之后后面的拿到的就是新的User对象
        UserContextHolder.holder.set(new User("fu"));
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        System.out.println("Service3: " + UserContextHolder.holder.get());
        UserContextHolder.holder.remove();
    }
}

/**
 * 不是创建时就赋值,调用时set()
 */
class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
