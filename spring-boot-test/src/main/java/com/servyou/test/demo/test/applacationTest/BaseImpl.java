package com.servyou.test.demo.test.applacationTest;


@BaseService(mes = "初始化类")
public class BaseImpl implements BaseInterface {

    @BaseService(mes = "初始化方法")
    @Override
    public void init() {
        System.out.println("init 执行");
    }
}
