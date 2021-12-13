package com.servyou.test.demo.test.kafkaProConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolConfig
 *
 * @author syf
 * @desc @TODO
 * @date 2019-09-21 14:05
 **/

@Configuration
@EnableAsync
@EnableScheduling
public class ExecutorConfiguration {
    @Value("${fpszjk.task.corePoolSize:10}")
    private int corePoolSize;
    @Value("${fpszjk.task.maxPoolSize:20}")
    private int maxPoolSize;
    @Value("${fpszjk.task.queueCapacity:200}")
    private int queueCapacity;
    @Value("${fpszjk.task.keepAliveSeconds:60}")
    private int keepAliveSeconds;
    @Value("${fpszjk.task.waitForTasksToCompleteOnShutdown:true}")
    private boolean waitForTasksToCompleteOnShutdown;
    @Value("${fpszjk.task.awaitTerminationSeconds:60}")
    private int awaitTerminationSeconds;
    @Value("${fpszjk.task.threadNamePrefix:sjzkPool-}")
    private String threadNamePrefix;

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        return executor;
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        //上传文件大小 50M 50*1024*1024
        resolver.setMaxUploadSize((long)50*1024*1024);
        return resolver;
    }

}
