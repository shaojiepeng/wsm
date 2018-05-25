package com.wsm.admin;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Test
    public void testPut(){
        valueOperations.set("111","222");
        Assert.assertTrue(redisTemplate.hasKey("111"));
    }


}
