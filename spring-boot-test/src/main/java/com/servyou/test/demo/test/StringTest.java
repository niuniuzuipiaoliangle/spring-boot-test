package com.servyou.test.demo.test;

/**
 * @author 聪聪只爱笨笨
 */
public class StringTest {

    public static void main(String[] args) throws InterruptedException {
        StringBuilder str= new StringBuilder("abc" + "de");
        StringBuilder stringBuilder=new StringBuilder().append("abc").append("de1");

        StringBuffer buffer = new StringBuffer();
//        System.out.println(str);
//        System.out.println(stringBuilder.toString());

        for (int i = 0; i <5 ; i++) {
            int finalI = i;
            new Thread(){
                @Override
                public void run() {
                    buffer.append(finalI);
                    System.out.println(buffer.toString());
                }
            }.start();
        }
        Thread.sleep(1000);
        System.out.println(buffer.toString());
    }
}
