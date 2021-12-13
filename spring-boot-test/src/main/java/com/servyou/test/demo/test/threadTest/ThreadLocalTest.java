package com.servyou.test.demo.test.threadTest;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 聪聪只爱笨笨
 */
public class ThreadLocalTest {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    public static AtomicInteger i = new AtomicInteger();
    private static volatile    boolean flag = true;
    public static void main(String[] args) {
       // 使用固定大小为1的线程池,说明上一个的线程属性会被下一个线程属性复用
        ExecutorService pool = new ThreadPoolExecutor(5,10,5,TimeUnit.SECONDS,new ArrayBlockingQueue<>(9));
        for (int i = 0; i < 20; i++) {
            Mythead mythead = new Mythead();
            pool.execute(mythead);
        }
    }

    private static class Mythead extends Thread{

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis());
            synchronized (new Object()){
                if (flag){
                    // 第一个线程set后,并没有进行remove
                    // 而第二个线程由于某种原因没有进行set操作
                    threadLocal.set(this.getName()+", session info.");
                    flag = false;
                    System.out.println(i.addAndGet(1));
                }
            }


            System.out.println(this.getName()+" 线程是 "+threadLocal.get());
            threadLocal.remove();
            // 但是由于ThreadLocal没有在线程处理结束时及时进行remove清理操作
            // 在高并发场景下,线程池中的线程属性会被下一个线程属性复用
        }
    }
}