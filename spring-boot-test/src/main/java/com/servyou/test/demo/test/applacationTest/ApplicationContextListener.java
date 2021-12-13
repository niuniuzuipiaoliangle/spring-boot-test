package com.servyou.test.demo.test.applacationTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Component
@Slf4j
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("spring初始化完毕");
        if(null == contextRefreshedEvent.getApplicationContext().getParent()) {
            log.debug(">>>>> spring初始化完毕 <<<<<");
            // spring初始化完毕后，通过反射调用所有使用BaseService注解的initMapper方法
            Map<String, Object> baseServices =
            contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BaseService.class);
            for(Object service : baseServices.values()) {
                log.debug(">>>>> {}.initMapper()", service.getClass().getName());
                try {
                    Method initMapper = service.getClass().getMethod("init");
                    initMapper.invoke(service);
                } catch (Exception e) {
                    log.error("初始化BaseService的initMapper方法异常", e);
                    e.printStackTrace();
                }
            }
            // 系统入口初始化
            Map<String, BaseInterface> baseInterfaceBeans =
            contextRefreshedEvent.getApplicationContext().getBeansOfType(BaseInterface.class);
            for(Object service : baseInterfaceBeans.values()) {
                log.debug(">>>>> {}.init()", service.getClass().getName());
                try {
                    Method init = service.getClass().getMethod("init");
                    init.invoke(service);
                    log.debug(init.getName());
                } catch (Exception e) {
                    log.error("初始化BaseInterface的init方法异常", e);
                    e.printStackTrace();
                }
            }
        }
    }
}