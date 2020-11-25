>  线程池好处：
- 复用线程
- 控制资源总量
- 加快响应速度
- 合理利用CPU和内存
- 统一管理

> 线程池状态
- Running：接受任务并处理排队任务
- Shutdown：不接受新任务，但处理排队任务
- Stop：不接受新任务，也不处理排队任务，并中断正在执行的任务
- Tidying：所有任务都已经终止，workerCount为0时，线程会转换到Tidying状态，并运行terminated()钩子方法
- Terminated：terminated()运行完成