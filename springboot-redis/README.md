> `springboot-redis`：基于SpingBoot 2.x 的默认Lettuce 连接redis Demo。

- 技术概览：springboot 2.X + redis(lettuce)

# 区别
- Jedis在实现上是直接连接的redis server，如果在多线程环境下是非线程安全的
- Lettuce的连接是基于Netty,连接实例可以在多个线程间共享，当多线程使用同一连接实例时，线程安全
