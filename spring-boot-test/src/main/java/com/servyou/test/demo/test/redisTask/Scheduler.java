package com.servyou.test.demo.test.redisTask;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * 新建两个相同的定时任务，模拟多实例
 *
 * @author 聪聪只爱笨笨*/
@Component
@Slf4j
public class Scheduler {

//    spring2.0以下使用
    @Autowired
    private RedisUtil util;


    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Value("${isLoad.enabled:false}")
    private boolean isLoad;

//    @Scheduled(cron = "0/10 * * * * ?")
//    @Async("taskExecutor")
//    public void getAllDate11(){
//        if(isLoad&&util.getLock("K#SkfxdjTaskGen","getting!",5)){
//            try {
//                System.out.println(" getAllDate  11  取得锁开始执行！！！！");
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                util.releaseLock("K#SkfxdjTaskGen");
//                System.out.println("getAllDate释放锁，----------------------------。");
//            }
//        }
//    }
//
    @Scheduled(cron = "0/10 * * * * ?")
//    @Async("taskExecutor")
    public void getAllDate22(){
        if(isLoad){
            String key = "K#SkfxdjTaskGen";
            Lock lock = redisLockRegistry.obtain(key);
            if (!lock.tryLock()) {
                System.out.println("未获取到锁22，----------------------------。");
                return;
            }

            try {
                send();
                System.out.println("取到锁22，----------------------------。");
            } catch (Exception e) {
                log.error("锁22。", e);
            } finally {
                if (null != lock) {
                    lock.unlock();
                    System.out.println("释放锁22，----------------------------。");
                }
            }
        }

    }

    @Scheduled(cron = "0/10 * * * * ?")
//    @Async("taskExecutor")
    public void getAllDate33(){
        if(isLoad){
            String key = "K#SkfxdjTaskGen";
            Lock lock = redisLockRegistry.obtain(key);
            System.out.println();
            System.out.println();
            if (!lock.tryLock()) {
                System.out.println("未获取到锁33，----------------------------。");
                return;
            }

            try {
                send();
                System.out.println("取到锁33，----------------------------。");
            } catch (Exception e) {
                log.error("锁33。", e);
            } finally {
                if (null != lock) {
                    lock.unlock();
                    System.out.println("释放锁33，----------------------------。");
                }
            }
        }
    }

    public void send(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
//            Object.wait(1000);
//            Thread.sleep(1000);
            System.out.println("执行任务-------------------------");
//            Thread.currentThread().notifyAll();
            countDownLatch.countDown();
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}