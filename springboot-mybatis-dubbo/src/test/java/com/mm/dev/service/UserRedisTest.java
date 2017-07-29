package com.mm.dev.service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mm.dev.entity.User;

/**
 * @author
 * @version v1.1
 * @ClassName: UserRedisTest
 * @Description: (redis测试存储对象)
 * @date 2017/5/2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        ValueOperations<String, User> operations =redisTemplate.opsForValue();
        // 保存对象
        User user = new User();
        user.setUsername("zhangxiaoyu2");
        user.setPassword("123");
        operations.set(user.getUsername(), user);
        Assert.assertEquals("123", operations.get("zhangxiaoyu").getPassword());

    }

}
