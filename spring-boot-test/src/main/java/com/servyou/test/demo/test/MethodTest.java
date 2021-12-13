package com.servyou.test.demo.test;


import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class MethodTest
{
    public static void main(String[] args)
    {
        String [] names ={"tom","tim","allen","alice"};
        Class<?> clazz = Test.class;
        try
        {
            Method method = clazz.getMethod("sayHi", String.class);
            for(String name:names) {
                method.invoke(clazz.newInstance(),name);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
//        String a ="0.0002";
//        System.out.println(String.format("%.2f", Double.valueOf(a) * 100) + "%");
    }

}

class Test
{
    public void sayHi(String name)
    {
        System.out.println("Hi "+name);
    }
}