package com.servyou.test.demo.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeTest {
    public static class MethodTest {

        /**
         * @param args
         * @throws InvocationTargetException
         * @throws IllegalAccessException
         * @throws IllegalArgumentException
         */
        public static void main(String[] args) throws IllegalArgumentException,
                IllegalAccessException, InvocationTargetException {
            Example example = new Example();

            // Method[] methods = example.getClass().getMethods();//getMethods()与getDeclaredMethods()的区别？！不要忘了
            Method[] methods = example.getClass().getDeclaredMethods();

            for (Method m : methods) {
                System.out.println(m.getName());
                m.setAccessible(false);//Here it is! 将 accessible 标志设置为指示的布尔值，值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查，从而提高了性能。值为 false 则指示反射的对象应该实施 Java 语言访问检查
                m.invoke(example);
            }

        }
    }

    static class Example {
        public void method_1() {
            System.out.println("private method_1 executes!");
        }

    }
}
