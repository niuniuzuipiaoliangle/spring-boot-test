package com.servyou.test.demo.test.redisTask;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

public class RedisBatchTest extends BaseTest{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private BatchRunRedisUtil batchRunRedisUtil;

    @Test
    public void run() throws Exception {

        //for循环批量添加
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            stringRedisTemplate.opsForValue().set("aaa" + i, "a", 60);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("普通set消耗" + (endTime - startTime) + "毫秒");


        //利用pipeline批量操作
        long startTime2 = System.currentTimeMillis();
        Map map = new HashMap(10000);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1000; j++) {
                map.put("bbb" + j, "b");
            }
            batchRunRedisUtil.batchSetOrExpire(map, 60l);
        }

        long endTime2 = System.currentTimeMillis();
        System.out.println("管道set消耗" + (endTime2 - startTime2) + "毫秒");


        //multiSet()批量操作
        long startTime3 = System.currentTimeMillis();
        Map map2 = new HashMap(10000);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10000; j++) {
                map2.put("ccc" + j, "b");
            }
            batchRunRedisUtil.batchSet(map2);
        }
        long endTime3 = System.currentTimeMillis();
        System.out.println("批量set消耗" + (endTime3 - startTime3) + "毫秒");
    }
}
