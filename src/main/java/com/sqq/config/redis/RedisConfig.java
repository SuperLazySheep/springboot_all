//package com.sqq.config;
//
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//
//@Configuration
//@EnableCaching
//public class RedisConfig extends CachingConfigurerSupport {
//
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("$spring.redis.port")
//    private int port;
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//    @Value("spring.redis.password")
//    private String password;
//    @Value("spring.redis.jedis.pool.max-active")
//    private int maxActive;
//    @Value("spring.redis.jedis.pool.max-wait")
//    private int maxWait;
//    @Value("spring.redis.jedis.pool.max-idle")
//    private int maxIdle;
//    @Value("spring.redis.jedis.pool.min-idle")
//    private int minIdle;
//
//
//   @Bean
//    public JedisConnectionFactory redisConnectionFactory(){
//       JedisConnectionFactory factory = new JedisConnectionFactory();
//       factory.setHostName(host);
//       factory.setPort(port);
//       //设置连接超时时间
//       factory.setTimeout(timeout);
//       factory.setPassword(password);
//       factory.getPoolConfig().setMaxIdle(maxIdle);
//       factory.getPoolConfig().setMinIdle(minIdle);
//       factory.getPoolConfig().setMaxTotal(maxActive);
//       factory.getPoolConfig().setMaxWaitMillis(maxWait);
//       return factory;
//   }
//
////   @Bean
////    public CacheManager cacheManager(RedisTemplate redisTemplate){
////       RedisCacheManager cacheManager = new RedisCacheManager();
////
////       return cacheManager;
////   }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
