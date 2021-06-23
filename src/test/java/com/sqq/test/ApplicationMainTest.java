package com.sqq.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationMainTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *   直接使用spring中的默认redis的api，如需要一定规则，需自己实现redis的各个api
     */

    @Test
    public void context(){
        redisTemplate.opsForValue().set("name","宋奇奇");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
    @Test
    public void test(){
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(timestamp.getTime());
    }
}
