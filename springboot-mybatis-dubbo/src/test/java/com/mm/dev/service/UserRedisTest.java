package com.mm.dev.service;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.mm.dev.entity.user.User;

/**
 * @author
 * @version v1.1
 * @ClassName: UserRedisTest
 * @Description: (redis测试存储对象)
 * @date 2017/5/2
 */
public class UserRedisTest extends BaseTest{

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        ValueOperations<String, User> operations =redisTemplate.opsForValue();
        // 保存对象
        User user = new User();
        user.setUserName("zhangxiaoyu2");
        user.setPassWord("123");
        operations.set(user.getUserName(), user);
        Assert.assertEquals("123", operations.get("zhangxiaoyu").getPassWord());

    }

}
