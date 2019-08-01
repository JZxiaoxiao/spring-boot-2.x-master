package com.jun.springbootstorm.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Title: RedisConfiguration
 * @Description:
 * Redis 配置类
 * @Version:1.0.0
 * @author JZxiaoxiao
 * @date 2019年5月7日
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":" + String.valueOf(obj));
            }
            String rsToUse = String.valueOf(sb);
            logger.info("自动生成Redis Key -> [{}]", rsToUse);
            return rsToUse;
        };
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        logger.info("初始化 -> [{}]", "CacheManager RedisCacheManager Start");
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(jedisConnectionFactory);
        return builder.build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        //设置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);//key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        logger.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                logger.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                logger.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                logger.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                logger.error("Redis occur handleCacheClearError：", e);
            }
        };
        return cacheErrorHandler;
    }

    @ConfigurationProperties
    class DataJedisProperties {
        @Value("${spring.redis.host}")
        private String host;
        @Value("${spring.redis.port}")
        private int port;
        @Value("${spring.redis.cluster.nodes}")
        private String clusterNodes;
        @Value("${spring.redis.password}")
        private String password;
        @Value("${spring.redis.cluster.max-redirects}")
        private int maxRedirects;
        @Value("${spring.redis.timeout}")
        private int timeout;
        @Value("${spring.redis.jedis.pool.max-idle}")
        private int maxIdle;
        @Value("${spring.redis.jedis.pool.max-wait}")
        private long maxWaitMillis;

        @Bean
        JedisConnectionFactory jedisConnectionFactory() {
            logger.info("Create JedisConnectionFactory successful");
            JedisConnectionFactory factory = new JedisConnectionFactory();
            if(!StringUtils.isBlank(clusterNodes)){
                //默认集群
                RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
                List<RedisNode> nodeList = new ArrayList<>();
                String[] cNodes = clusterNodes.split(",");
                for(String node : cNodes) {
                    String[] hp = node.split(":");
                    nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
                }
                redisClusterConfiguration.setClusterNodes(nodeList);
                redisClusterConfiguration.setPassword(RedisPassword.of(password));
                redisClusterConfiguration.setMaxRedirects(maxRedirects);
                factory = new JedisConnectionFactory(redisClusterConfiguration);
            }else {
                factory.setHostName(host);
                factory.setPort(port);
                factory.setTimeout(timeout);
                factory.setPassword(password);
            }
            return factory;
        }

        @Bean
        public JedisPool redisPoolFactory() {
            if(!StringUtils.isBlank(clusterNodes)){
                logger.info("JedisPool init successful，clusterNodes -> [{}];", clusterNodes);
            }else {
                logger.info("JedisPool init successful，host -> [{}]；port -> [{}]", host, port);
            }
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
            return jedisPool;
        }
    }
}
