package com.servyou.test.demo.test;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
    
    public static AtomicInteger number = new AtomicInteger(0);
    
    public void increase(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        number.getAndIncrement();//获得当前值并且加1

        System.out.println("number is " + number.get());
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        final VolatileTest test = new VolatileTest();
        for(int i = 0 ; i < 10 ; i++){
            new Thread(new Runnable() {

                @Override
                public void run() {
                    test.increase();
                    number.getAndDecrement();

                }
            }).start();
        }

        //若当期依然有子线程没有执行完毕
        while(Thread.activeCount() > 1){
            Thread.yield();//使得当前线程（主线程）让出CPU时间片
        }

        System.out.println("number is " + number.get());
    }

}