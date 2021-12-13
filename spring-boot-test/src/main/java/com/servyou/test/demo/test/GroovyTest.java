package com.servyou.test.demo.test;

import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 聪聪只爱笨笨
 */
@Slf4j
public class GroovyTest {

    public static void main(String[] args) {
//        String kzztdjlxDm = "11";
//        GroovyShell groovyShell = new GroovyShell();
//        groovyShell.setProperty("kzztdjlxDm",kzztdjlxDm);
//        Map<String,String> scriptsMap = new HashMap<>();
//        scriptsMap.put("kzztdjlxDm",kzztdjlxDm);
//        Object evaluate = groovyShell.evaluate("[kzztdjlxDm].equals(\"1110\")");
//        System.out.println(evaluate);

        printSingleColor("ticp-prov-risk-compute 启动成功！",1,4,"ticp-prov-risk-compute 启动成功！");
        System.out.println("\33[32;4mticp-prov-risk-compute 启动成功！");
        log.info("ticp-prov-risk-compute \33[32;4m启动成功！");
    }

    /**
     *
     * @param pattern 前面的图案 such as "=============="
     * @param code 颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
     * @param n 数字+m：1加粗；3斜体；4下划线
     * @param content 要打印的内容
     */
    public static void printSingleColor(String pattern,int code,int n,String content){
        System.out.format("%s\33[%d;%dm%s%n", pattern, code, n, content);
    }
}
