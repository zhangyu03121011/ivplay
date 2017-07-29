package com.mm.dev.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import redis.clients.jedis.JedisPoolConfig;



/**
 * @ClassName: RedisConfig
 * @Description: ( 配置针对User的RedisTemplate实例 )
 * @author
 * @date 2017/5/2
 * @version v1.1
 */
/**
 *   其实我感觉是这样的，1.5.x 和 1.3.x 改动比较大：
 *   测试时，可能遇到以下坑：
 *   1.pom 需要手动指定版本
 *   2.使用redis 默认已支持存储对象，不要再手动处理
 *   3.如果使用JedisConnectionFactory ，需要将application.properties 中的host 改为hostName ，并
 *   @ConfigurationProperties(prefix = spring.redis)
 *   JedisConnectionFactory jedisConnectionFactory() {}
 *   手动绑定。因为查看源码JedisConnectionFactory ，redis 地址属性为hostName;
 */
@Configuration
@EnableRedisHttpSession
public class RedisConfig {
	
	Logger logger = LoggerFactory.getLogger(RedisConfig.class);
//    @Bean
//    @ConfigurationProperties(prefix = "spring.redis")
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory= new JedisConnectionFactory();
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<Serializable, Serializable> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<Serializable, Serializable> template = new RedisTemplate<Serializable, Serializable>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new RedisObjectSerializer());
//        return template;
//    }
	
	@Bean  
    @ConfigurationProperties(prefix="spring.redis")  
    public JedisPoolConfig getRedisConfig(){  
        JedisPoolConfig config = new JedisPoolConfig();  
        return config;  
    }  
      
    @Bean  
    @ConfigurationProperties(prefix="spring.redis")  
    public JedisConnectionFactory getConnectionFactory(){  
        JedisConnectionFactory factory = new JedisConnectionFactory();  
        JedisPoolConfig config = getRedisConfig();  
        factory.setPoolConfig(config);  
        logger.info("JedisConnectionFactory bean init success.");  
        return factory;  
    }  
      
      
    @Bean  
    public RedisTemplate<?, ?> getRedisTemplate(){  
        RedisTemplate<?,?> template = new StringRedisTemplate(getConnectionFactory());  
        return template;  
    }  

}
